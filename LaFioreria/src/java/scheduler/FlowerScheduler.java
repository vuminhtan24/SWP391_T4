package scheduler;

import dal.BaseDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FlowerScheduler extends BaseDao {
    public static void main(String[] args) {
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
            // Lấy connection từ DBContext (kế thừa từ BaseDao)
            connection = dbc.getConnection();

            // 1. Xóa lô hoa hết hạn
            String deleteExpiredBatches = "DELETE FROM la_fioreria.flower_batch WHERE expiration_date < ? AND (hold IS NULL OR hold = 0)";
            ps = connection.prepareStatement(deleteExpiredBatches);
            ps.setString(1, currentDate);
            int deletedRows = ps.executeUpdate();
            System.out.println("Đã xóa " + deletedRows + " lô hoa hết hạn.");

            // 2. Cập nhật trạng thái và tìm lô hoa gần hết hạn
            String updateNearExpired = "UPDATE la_fioreria.flower_batch SET status = 'near_expired' WHERE expiration_date = DATE_ADD(?, INTERVAL 1 DAY) AND status != 'near_expired'";
            ps = connection.prepareStatement(updateNearExpired);
            ps.setString(1, currentDate);
            ps.executeUpdate();

            // 3. Tìm lô hoa gần hết hạn
            String findNearExpired = "SELECT batch_id, flower_id FROM la_fioreria.flower_batch WHERE status = 'near_expired'";
            ps = connection.prepareStatement(findNearExpired);
            rs = ps.executeQuery();

            while (rs.next()) {
                int batchId = rs.getInt("batch_id");
                int flowerId = rs.getInt("flower_id");
                System.out.println("Lô hoa gần hết hạn: batch_id=" + batchId);

                // 4. Tìm giỏ hoa chứa lô hoa gần hết hạn
                String findBouquets = "SELECT bouquet_id FROM la_fioreria.bouquet_raw WHERE batch_id = ?";
                ps = connection.prepareStatement(findBouquets);
                ps.setInt(1, batchId);
                ResultSet bouquetRs = ps.executeQuery();

                while (bouquetRs.next()) {
                    int bouquetId = bouquetRs.getInt("bouquet_id");
                    System.out.println("Giỏ hoa " + bouquetId + " cần sửa vì chứa lô hoa: " + batchId);

                    // 5. Cập nhật trạng thái giỏ hoa
                    String updateBouquet = "UPDATE la_fioreria.bouquet SET status = 'needs_repair' WHERE Bouquet_ID = ?";
                    ps = connection.prepareStatement(updateBouquet);
                    ps.setInt(1, bouquetId);
                    ps.executeUpdate();

                    // 6. Tạo lệnh sửa trong bảng repair_orders
                    String insertRepairOrder = "INSERT INTO la_fioreria.repair_orders (bouquet_id, batch_id, reason) VALUES (?, ?, ?)";
                    ps = connection.prepareStatement(insertRepairOrder);
                    ps.setInt(1, bouquetId);
                    ps.setInt(2, batchId);
                    ps.setString(3, "Giỏ hoa chứa lô hoa gần hết hạn: batch_id=" + batchId);
                    ps.executeUpdate();
                }
                bouquetRs.close();
            }

            // 7. Cập nhật trạng thái lô hoa còn lại
            String updateFreshBatches = "UPDATE la_fioreria.flower_batch SET status = 'fresh' WHERE expiration_date > ? AND status != 'fresh'";
            ps = connection.prepareStatement(updateFreshBatches);
            ps.setString(1, currentDate);
            ps.executeUpdate();

            System.out.println("Hoàn thành kiểm tra trạng thái lô hoa.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}