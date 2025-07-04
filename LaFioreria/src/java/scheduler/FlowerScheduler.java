package scheduler;

import dal.BaseDao;
import javax.mail.*;
import javax.mail.internet.*;
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

        // **Phải** mở connection trước khi gọi getAdminEmails(),
        // vì bên trong method đang dùng field `connection`
        job.connection = job.dbc.getConnection();
        List<String> mails = job.getAdminEmails();
        System.out.println("Admin emails: " + mails);
        // Tạo scheduler
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Chạy job mỗi 24 giờ (86400 giây)
        scheduler.scheduleAtFixedRate(new FlowerScheduler()::checkFlowerBatches, 0, 86400, TimeUnit.SECONDS);

    }

    public void checkFlowerBatches() {
        System.out.println("Bắt đầu kiểm tra trạng thái lô hoa: " + new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());

        try {
            // Lấy connection từ DBContext
            connection = dbc.getConnection();

            // 1. Xóa lô hoa hết hạn
            String deleteExpiredBatches = "UPDATE flower_batch status = 'expired' WHERE expiration_date < ? AND (hold IS NULL OR hold = 0)";
            ps = connection.prepareStatement(deleteExpiredBatches);
            ps.setString(1, currentDate);
            int deletedRows = ps.executeUpdate();
            System.out.println("Đã xóa " + deletedRows + " lô hoa hết hạn.");

            // 2. Cập nhật trạng thái gần hết hạn
            String updateNearExpired = "UPDATE flower_batch SET status = 'near_expired' WHERE expiration_date = DATE_ADD(?, INTERVAL 1 DAY) AND status != 'near_expired'";
            ps = connection.prepareStatement(updateNearExpired);
            ps.setString(1, currentDate);
            int updatedRows = ps.executeUpdate();
            System.out.println("Đã cập nhật " + updatedRows + " lô hoa thành near_expired.");

            // 3. Tìm lô hoa gần hết hạn
            String findNearExpired = "SELECT batch_id, flower_id FROM flower_batch WHERE status = 'near_expired'";
            try (PreparedStatement nearExpiredPs = connection.prepareStatement(findNearExpired); ResultSet nearExpiredRs = nearExpiredPs.executeQuery()) {
                while (nearExpiredRs.next()) {
                    int batchId = nearExpiredRs.getInt("batch_id"); // Sửa rs thành nearExpiredRs
                    int flowerId = nearExpiredRs.getInt("flower_id");
                    System.out.println("Lô hoa gần hết hạn: batch_id=" + batchId);

                    // 4. Tìm giỏ hoa chứa lô hoa gần hết hạn
                    String findBouquets = "SELECT bouquet_id FROM bouquet_raw WHERE batch_id = ?";
                    try (PreparedStatement bouquetsPs = connection.prepareStatement(findBouquets)) {
                        bouquetsPs.setInt(1, batchId);
                        try (ResultSet bouquetRs = bouquetsPs.executeQuery()) {
                            while (bouquetRs.next()) {
                                int bouquetId = bouquetRs.getInt("bouquet_id");
                                System.out.println("Giỏ hoa " + bouquetId + " cần sửa vì chứa lô hoa: " + batchId);

                                // 5. Cập nhật trạng thái giỏ hoa
                                String updateBouquet = "UPDATE bouquet SET status = 'needs_repair' WHERE Bouquet_ID = ?";
                                try (PreparedStatement updateBouquetPs = connection.prepareStatement(updateBouquet)) {
                                    updateBouquetPs.setInt(1, bouquetId);
                                    updateBouquetPs.executeUpdate();
                                }

                                // 6. Kiểm tra xem đã có yêu cầu pending chưa
                                String checkExistingOrder = "SELECT COUNT(*) FROM repair_orders WHERE bouquet_id = ? AND batch_id = ? AND status = 'pending'";
                                try (PreparedStatement checkOrderPs = connection.prepareStatement(checkExistingOrder)) {
                                    checkOrderPs.setInt(1, bouquetId);
                                    checkOrderPs.setInt(2, batchId);
                                    try (ResultSet checkRs = checkOrderPs.executeQuery()) {
                                        checkRs.next();
                                        int count = checkRs.getInt(1);

                                        if (count == 0) {
                                            // Tạo lệnh sửa mới
                                            String insertRepairOrder = "INSERT INTO repair_orders (bouquet_id, batch_id, reason) VALUES (?, ?, ?)";
                                            try (PreparedStatement insertOrderPs = connection.prepareStatement(insertRepairOrder)) {
                                                insertOrderPs.setInt(1, bouquetId);
                                                insertOrderPs.setInt(2, batchId);
                                                insertOrderPs.setString(3, "Giỏ hoa chứa lô hoa gần hết hạn: batch_id=" + batchId);
                                                insertOrderPs.executeUpdate();
                                                System.out.println("Đã tạo yêu cầu sửa mới cho bouquet_id=" + bouquetId + ", batch_id=" + batchId);

                                                // Gửi email thông báo cho admin
                                                sendEmailToAdmins(bouquetId, batchId);
                                                addNotification(bouquetId, batchId, "Giỏ hoa " + bouquetId + " cần sửa do lô hoa " + batchId + " gần hết hạn.");
                                            }
                                        } else {
                                            System.out.println("Bỏ qua tạo yêu cầu sửa vì đã có yêu cầu pending cho bouquet_id=" + bouquetId + ", batch_id=" + batchId);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 7. Cập nhật trạng thái lô hoa còn lại
            String updateFreshBatches = "UPDATE flower_batch SET status = 'fresh' WHERE expiration_date > DATE_ADD(?, INTERVAL 1 DAY) AND status != 'fresh'";
            ps = connection.prepareStatement(updateFreshBatches);
            ps.setString(1, currentDate);
            int freshRows = ps.executeUpdate();
            System.out.println("Đã cập nhật " + freshRows + " lô hoa thành fresh.");

            // 8. Kiểm tra số lượng lô hoa và đơn hàng
            checkStockLevelsAndOrders();

            System.out.println("Hoàn thành kiểm tra trạng thái lô hoa.");
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
        // Cấu hình email
        String from = "hoang.trungkien2110@gmail.com"; // Thay bằng email của bạn
        String password = "jnto tzhj pvvd fvfm"; // Thay bằng App Password
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Tạo session
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        // Lấy danh sách email admin từ bảng user
        List<String> adminEmails = getAdminEmails();

        if (adminEmails.isEmpty()) {
            System.out.println("Không tìm thấy email admin để gửi thông báo.");
            return;
        }

        // Gửi email cho từng admin
        for (String to : adminEmails) {
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Yêu cầu sửa giỏ hoa: Bouquet ID " + bouquetId);
                message.setText("Kính gửi Admin,\n\n"
                        + "Một yêu cầu sửa giỏ hoa đã được tạo:\n"
                        + "- Giỏ hoa ID: " + bouquetId + "\n"
                        + "- Lô hoa ID: " + batchId + "\n"
                        + "- Lý do: Giỏ hoa chứa lô hoa gần hết hạn\n"
                        + "- Thời gian: " + new Date() + "\n\n"
                        + "Vui lòng kiểm tra và xử lý.\n"
                        + "Trân trọng,\nHệ thống La Fioreria");

                Transport.send(message);
                System.out.println("Đã gửi email thông báo đến: " + to);
            } catch (MessagingException e) {
                System.err.println("Lỗi gửi email đến " + to + ": " + e.getMessage());
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
                System.out.println("Lô hoa " + batchId + " (" + flowerName + ") có số lượng thấp: " + quantity + " < 10");
                sendEmailToAdminsForStock(batchId, flowerId, flowerName, "low_quantity");
                addNotification(-1, batchId, "Lô hoa " + batchId + " (" + flowerName + ") có số lượng thấp: " + quantity + ". Vui lòng nhập thêm.");
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
                System.out.println("Giỏ hoa " + bouquetName + " (ID: " + bouquetId + ") cần " + totalNeeded
                        + " hoa, tồn kho " + totalStock + ", chênh lệch " + diff);
                sendEmailToAdminsForStock(-1, -1, bouquetName, "low_stock");
                addNotification(-1, -1, "Giỏ hoa " + bouquetName + " (ID: " + bouquetId + ") cần " + totalNeeded
                        + " hoa, tồn kho " + totalStock + ". Vui lòng nhập thêm lô hoa.");
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
            System.out.println("Không tìm thấy email admin để gửi thông báo.");
            return;
        }

        String subject = "Yêu cầu nhập thêm lô hoa";
        String body = "Kính gửi Admin,\n\n";
        if ("low_quantity".equals(reasonType)) {
            body += "Lô hoa ID " + batchId + " (" + name + ") có số lượng thấp.\n"
                    + "Vui lòng nhập thêm lô hoa cho loại hoa ID " + flowerId + ".\n";
        } else if ("low_stock".equals(reasonType)) {
            body += "Giỏ hoa " + name + " cần " + (reasonType.equals("low_stock") ? "thêm hoa" : "") + " vì số lượng đơn hàng vượt tồn kho.\n"
                    + "Vui lòng nhập thêm lô hoa để đáp ứng nhu cầu.\n";
        }
        body += "- Thời gian: " + new Date() + "\n\n"
                + "Trân trọng,\nHệ thống La Fioreria";

        for (String to : adminEmails) {
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);
                System.out.println("Đã gửi email thông báo nhập lô hoa đến: " + to);
            } catch (MessagingException e) {
                System.err.println("Lỗi gửi email đến " + to + ": " + e.getMessage());
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
            System.err.println("Lỗi lấy email admin: " + e.getMessage());
            e.printStackTrace();
        }
        return emails;
    }

    private void addNotification(int bouquetId, int batchId, String message) throws SQLException {
        // Lấy user_id của admin (giả định lấy user_id đầu tiên làm đại diện)
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
            System.out.println("Đã thêm thông báo cho tất cả admin.");
        } else {
            System.out.println("Không tìm thấy admin để thêm thông báo.");
        }
    }

}
