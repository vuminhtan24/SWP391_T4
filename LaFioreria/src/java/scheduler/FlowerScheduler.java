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
            String deleteExpiredBatches = "DELETE FROM flower_batch WHERE expiration_date < ? AND (hold IS NULL OR hold = 0)";
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
            ps = connection.prepareStatement(findNearExpired);
            rs = ps.executeQuery();

            while (rs.next()) {
                int batchId = rs.getInt("batch_id");
                int flowerId = rs.getInt("flower_id");
                System.out.println("Lô hoa gần hết hạn: batch_id=" + batchId);

                // 4. Tìm giỏ hoa chứa lô hoa gần hết hạn
                String findBouquets = "SELECT bouquet_id FROM bouquet_raw WHERE batch_id = ?";
                ps = connection.prepareStatement(findBouquets);
                ps.setInt(1, batchId);
                ResultSet bouquetRs = ps.executeQuery();

                while (bouquetRs.next()) {
                    int bouquetId = bouquetRs.getInt("bouquet_id");
                    System.out.println("Giỏ hoa " + bouquetId + " cần sửa vì chứa lô hoa: " + batchId);

                    // 5. Cập nhật trạng thái giỏ hoa
                    String updateBouquet = "UPDATE bouquet SET status = 'needs_repair' WHERE Bouquet_ID = ?";
                    ps = connection.prepareStatement(updateBouquet);
                    ps.setInt(1, bouquetId);
                    ps.executeUpdate();

                    // 6. Kiểm tra xem đã có yêu cầu pending chưa
                    String checkExistingOrder = "SELECT COUNT(*) FROM repair_orders WHERE bouquet_id = ? AND batch_id = ? AND status = 'pending'";
                    ps = connection.prepareStatement(checkExistingOrder);
                    ps.setInt(1, bouquetId);
                    ps.setInt(2, batchId);
                    ResultSet checkRs = ps.executeQuery();
                    checkRs.next();
                    int count = checkRs.getInt(1);
                    checkRs.close();

                    if (count == 0) {
                        // Tạo lệnh sửa mới
                        String insertRepairOrder = "INSERT INTO repair_orders (bouquet_id, batch_id, reason) VALUES (?, ?, ?)";
                        ps = connection.prepareStatement(insertRepairOrder);
                        ps.setInt(1, bouquetId);
                        ps.setInt(2, batchId);
                        ps.setString(3, "Giỏ hoa chứa lô hoa gần hết hạn: batch_id=" + batchId);
                        ps.executeUpdate();
                        System.out.println("Đã tạo yêu cầu sửa mới cho bouquet_id=" + bouquetId + ", batch_id=" + batchId);

                        // Gửi email thông báo cho admin
                        sendEmailToAdmins(bouquetId, batchId);
                    } else {
                        System.out.println("Bỏ qua tạo yêu cầu sửa vì đã có yêu cầu pending cho bouquet_id=" + bouquetId + ", batch_id=" + batchId);
                    }
                }
                bouquetRs.close();
            }

            // 7. Cập nhật trạng thái lô hoa còn lại
            String updateFreshBatches = "UPDATE flower_batch SET status = 'fresh' WHERE expiration_date > DATE_ADD(?, INTERVAL 1 DAY) AND status != 'fresh'";
            ps = connection.prepareStatement(updateFreshBatches);
            ps.setString(1, currentDate);
            int freshRows = ps.executeUpdate();
            System.out.println("Đã cập nhật " + freshRows + " lô hoa thành fresh.");

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
                message.setText("Kính gửi Admin,\n\n" +
                        "Một yêu cầu sửa giỏ hoa đã được tạo:\n" +
                        "- Giỏ hoa ID: " + bouquetId + "\n" +
                        "- Lô hoa ID: " + batchId + "\n" +
                        "- Lý do: Giỏ hoa chứa lô hoa gần hết hạn\n" +
                        "- Thời gian: " + new Date() + "\n\n" +
                        "Vui lòng kiểm tra và xử lý.\n" +
                        "Trân trọng,\nHệ thống La Fioreria");

                Transport.send(message);
                System.out.println("Đã gửi email thông báo đến: " + to);
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
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }        
        return emails;
    }

}