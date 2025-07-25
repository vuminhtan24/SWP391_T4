package scheduler;

import dal.BaseDao;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FlowerScheduler extends BaseDao {

    public static void main(String[] args) {
        FlowerScheduler job = new FlowerScheduler();

        // **Must** open connection before calling getAdminEmails(),
        // as the method uses the `connection` field
        job.connection = job.dbc.getConnection();
        List<String> mails = job.getAdminEmails();
        System.out.println("Admin emails: " + mails);

        // Create scheduler
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Run job every 24 hours (86400 seconds)
        scheduler.scheduleAtFixedRate(new FlowerScheduler()::checkFlowerBatches, 0, 86400, TimeUnit.SECONDS);
    }

    public void checkFlowerBatches() {
        System.out.println("Starting flower batch status check: " + new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());

        try {
            // Get connection from DBContext
            connection = dbc.getConnection();

            // 1. Mark expired flower batches
            String deleteExpiredBatches = "UPDATE flower_batch SET status = 'expired' WHERE expiration_date < ? AND (hold IS NULL OR hold = 0)";
            ps = connection.prepareStatement(deleteExpiredBatches);
            ps.setString(1, currentDate);
            int deletedRows = ps.executeUpdate();
            System.out.println("Marked " + deletedRows + " expired flower batches.");

            // 2. Update near-expired batches
            String updateNearExpired = "UPDATE flower_batch SET status = 'near_expired' WHERE expiration_date = DATE_ADD(?, INTERVAL 1 DAY) AND status != 'near_expired'";
            ps = connection.prepareStatement(updateNearExpired);
            ps.setString(1, currentDate);
            int updatedRows = ps.executeUpdate();
            System.out.println("Updated " + updatedRows + " flower batches to near_expired.");

            // 3. Find near-expired flower batches
            String findNearExpired = "SELECT batch_id, flower_id FROM flower_batch WHERE status = 'near_expired'";
            try (PreparedStatement nearExpiredPs = connection.prepareStatement(findNearExpired); ResultSet nearExpiredRs = nearExpiredPs.executeQuery()) {
                while (nearExpiredRs.next()) {
                    int batchId = nearExpiredRs.getInt("batch_id");
                    int flowerId = nearExpiredRs.getInt("flower_id");
                    System.out.println("Near-expired flower batch: batch_id=" + batchId);

                    // 4. Find bouquets containing near-expired flower batches
                    String findBouquets = "SELECT bouquet_id FROM bouquet_raw WHERE batch_id = ?";
                    try (PreparedStatement bouquetsPs = connection.prepareStatement(findBouquets)) {
                        bouquetsPs.setInt(1, batchId);
                        try (ResultSet bouquetRs = bouquetsPs.executeQuery()) {
                            while (bouquetRs.next()) {
                                int bouquetId = bouquetRs.getInt("bouquet_id");
                                System.out.println("Bouquet " + bouquetId + " needs repair due to flower batch: " + batchId);

                                // 5. Update bouquet status
                                String updateBouquet = "UPDATE bouquet SET status = 'needs_repair' WHERE Bouquet_ID = ?";
                                try (PreparedStatement updateBouquetPs = connection.prepareStatement(updateBouquet)) {
                                    updateBouquetPs.setInt(1, bouquetId);
                                    updateBouquetPs.executeUpdate();
                                }

                                // 6. Check for existing pending repair orders
                                String checkExistingOrder = "SELECT COUNT(*) FROM repair_orders WHERE bouquet_id = ? AND batch_id = ? AND status = 'pending'";
                                try (PreparedStatement checkOrderPs = connection.prepareStatement(checkExistingOrder)) {
                                    checkOrderPs.setInt(1, bouquetId);
                                    checkOrderPs.setInt(2, batchId);
                                    try (ResultSet checkRs = checkOrderPs.executeQuery()) {
                                        checkRs.next();
                                        int count = checkRs.getInt(1);

                                        if (count == 0) {
                                            // Create new repair order
                                            String insertRepairOrder = "INSERT INTO repair_orders (bouquet_id, batch_id, reason) VALUES (?, ?, ?)";
                                            try (PreparedStatement insertOrderPs = connection.prepareStatement(insertRepairOrder)) {
                                                insertOrderPs.setInt(1, bouquetId);
                                                insertOrderPs.setInt(2, batchId);
                                                insertOrderPs.setString(3, "Bouquet contains near-expired flower batch: batch_id=" + batchId);
                                                insertOrderPs.executeUpdate();
                                                System.out.println("Created new repair order for bouquet_id=" + bouquetId + ", batch_id=" + batchId);

                                                // Send email notification to admins
                                                sendEmailToAdmins(bouquetId, batchId);
                                                addNotification(bouquetId, batchId, "Bouquet " + bouquetId + " needs repair due to near-expired flower batch " + batchId + ".");
                                            }
                                        } else {
                                            System.out.println("Skipped creating repair order as a pending order exists for bouquet_id=" + bouquetId + ", batch_id=" + batchId);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 7. Update status of remaining flower batches
            String updateFreshBatches = "UPDATE flower_batch SET status = 'fresh' WHERE expiration_date > DATE_ADD(?, INTERVAL 1 DAY) AND status != 'fresh'";
            ps = connection.prepareStatement(updateFreshBatches);
            ps.setString(1, currentDate);
            int freshRows = ps.executeUpdate();
            System.out.println("Updated " + freshRows + " flower batches to fresh.");

            // 8. Check stock levels and orders
            checkStockLevelsAndOrders();

            System.out.println("Completed flower batch status check.");
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendEmailToAdmins(int bouquetId, int batchId) {
        // Email configuration
        String from = "hoang.trungkien2110@gmail.com"; // Replace with your email
        String password = "jnto tzhj pvvd fvfm"; // Replace with App Password
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Create session
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        // Get admin emails from user table
        List<String> adminEmails = getAdminEmails();

        if (adminEmails.isEmpty()) {
            System.out.println("No admin emails found for notification.");
            return;
        }

        // Send email to each admin
        for (String to : adminEmails) {
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Repair Request for Bouquet: Bouquet ID " + bouquetId);
                message.setText("Dear Admin,\n\n"
                        + "A repair request has been created for a bouquet:\n"
                        + "- Bouquet ID: " + bouquetId + "\n"
                        + "- Flower Batch ID: " + batchId + "\n"
                        + "- Reason: Bouquet contains near-expired flower batch\n"
                        + "- Time: " + new Date() + "\n\n"
                        + "Please review and take action.\n"
                        + "Best regards,\nLa Fioreria System");

                Transport.send(message);
                System.out.println("Sent email notification to: " + to);
            } catch (MessagingException e) {
                System.err.println("Error sending email to " + to + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void checkStockLevelsAndOrders() throws SQLException {
        checkLowQuantityBatches();
        checkOrdersVsStock();
    }

    private void checkLowQuantityBatches() throws SQLException {
        String checkLowQuantity = "SELECT b.batch_id, b.quantity, b.flower_id, t.flower_name "
                + "FROM flower_batch b "
                + "JOIN flower_type t ON t.flower_id = b.flower_id "
                + "WHERE b.quantity < 10 "
                + "ORDER BY b.batch_id, b.quantity, b.flower_id, t.flower_name";
        try (PreparedStatement ps = connection.prepareStatement(checkLowQuantity); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int batchId = rs.getInt("batch_id");
                int quantity = rs.getInt("quantity");
                int flowerId = rs.getInt("flower_id");
                String flowerName = rs.getString("flower_name");
                System.out.println("Flower batch " + batchId + " (" + flowerName + ") has low quantity: " + quantity + " < 10");
                sendEmailToAdminsForStock(batchId, flowerId, flowerName, "low_quantity");
                addNotification(-1, batchId, "Flower batch " + batchId + " (" + flowerName + ") has low quantity: " + quantity + ". Please restock.");
            }
        }
    }

    private void checkOrdersVsStock() throws SQLException {
        String checkOrdersVsStock = "SELECT b.Bouquet_ID, b.bouquet_name, need.total_needed, stock.total_stock, "
                + "(stock.total_stock - need.total_needed) AS diff "
                + "FROM bouquet b "
                + "LEFT JOIN ( "
                + "    SELECT oi.bouquet_id, SUM(oi.quantity * br.quantity) AS total_needed "
                + "    FROM la_fioreria.order_item oi "
                + "    JOIN bouquet_raw br ON oi.bouquet_id = br.bouquet_id "
                + "    JOIN la_fioreria.`order` o ON oi.order_id = o.order_id "
                + "    JOIN order_status s ON o.status_id = s.order_status_id "
                + "    WHERE s.status_name = 'pending' "
                + "    GROUP BY oi.bouquet_id "
                + ") AS need ON need.bouquet_id = b.Bouquet_ID "
                + "LEFT JOIN ( "
                + "    SELECT br.bouquet_id, SUM(fb.quantity) AS total_stock "
                + "    FROM bouquet_raw br "
                + "    JOIN flower_batch fb ON fb.batch_id = br.batch_id "
                + "    GROUP BY br.bouquet_id "
                + ") AS stock ON stock.bouquet_id = b.Bouquet_ID "
                + "WHERE need.total_needed IS NOT NULL "
                + "HAVING diff < 0 "
                + "ORDER BY diff ASC";
        try (PreparedStatement ps = connection.prepareStatement(checkOrdersVsStock); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int bouquetId = rs.getInt("Bouquet_ID");
                String bouquetName = rs.getString("bouquet_name");
                int totalNeeded = rs.getInt("total_needed");
                int totalStock = rs.getInt("total_stock");
                int diff = rs.getInt("diff");
                System.out.println("Bouquet " + bouquetName + " (ID: " + bouquetId + ") needs " + totalNeeded
                        + " flowers, stock " + totalStock + ", difference " + diff);
                sendEmailToAdminsForStock(-1, -1, bouquetName, "low_stock");
                addNotification(-1, -1, "Bouquet " + bouquetName + " (ID: " + bouquetId + ") needs " + totalNeeded
                        + " flowers, stock " + totalStock + ". Please restock flower batches.");
            }
        }
    }

    private void sendEmailToAdminsForStock(int batchId, int flowerId, String name, String reasonType) {
        String from = "hoang.trungkien2110@gmail.com";
        String password = "jnto tzhj pvvd fvfm";
        String host = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        List<String> adminEmails = getAdminEmails();

        if (adminEmails.isEmpty()) {
            System.out.println("No admin emails found for notification.");
            return;
        }

        String subject = "Restock Request for Flower Batch";
        String body = "Dear Admin,\n\n";
        if ("low_quantity".equals(reasonType)) {
            body += "Flower batch ID " + batchId + " (" + name + ") has low quantity.\n"
                    + "Please restock flower batch for flower type ID " + flowerId + ".\n";
        } else if ("low_stock".equals(reasonType)) {
            body += "Bouquet " + name + " needs additional flowers due to order quantity exceeding stock.\n"
                    + "Please restock flower batches to meet demand.\n";
        }
        body += "- Time: " + new Date() + "\n\n"
                + "Best regards,\nLa Fioreria System";

        for (String to : adminEmails) {
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);
                System.out.println("Sent restock notification email to: " + to);
            } catch (MessagingException e) {
                System.err.println("Error sending email to " + to + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private List<String> getAdminEmails() {
        List<String> emails = new ArrayList<>();
        String sql = "SELECT Email FROM user WHERE Role = 1 AND status = 'active'";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String email = rs.getString("Email");
                if (email != null && !email.isEmpty()) {
                    emails.add(email);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving admin emails: " + e.getMessage());
            e.printStackTrace();
        }
        return emails;
    }

    private void addNotification(int bouquetId, int batchId, String message) throws SQLException {
        // Get user_id of admins (assuming the first user_id is used as a representative)
        String getAdminId = "SELECT user_id FROM user WHERE Role = 1 AND status = 'active'";
        ps = connection.prepareStatement(getAdminId);
        rs = ps.executeQuery();
        String insertNotification = "INSERT INTO notifications (user_id, message, created_at, status) VALUES (?, ?, ?, ?)";
        PreparedStatement insertPs = connection.prepareStatement(insertNotification);
        boolean anyInserted = false;
        while (rs.next()) {
            int userId = rs.getInt("user_id");
            insertPs.setInt(1, userId);
            insertPs.setString(2, message);
            insertPs.setTimestamp(3, new java.sql.Timestamp(new Date().getTime()));
            insertPs.setString(4, "unread");
            insertPs.executeUpdate();
            anyInserted = true;
        }
        rs.close();
        insertPs.close();
        if (anyInserted) {
            System.out.println("Added notification for all admins.");
        } else {
            System.out.println("No admins found to add notification.");
        }
    }
}