package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.RepairOrders;

public class RepairOrdersDAO extends BaseDao {

    public List<RepairOrders> getPendingRepairOrders() throws SQLException {
        List<RepairOrders> orders = new ArrayList<>();
        String sql = "SELECT ro.repair_id, ro.bouquet_id, b.bouquet_name, ro.batch_id, ft.flower_name, ro.reason, ro.created_at, ro.status "
                + "FROM repair_orders ro "
                + "JOIN bouquet b ON ro.bouquet_id = b.Bouquet_ID "
                + "JOIN flower_batch fb ON ro.batch_id = fb.batch_id "
                + "JOIN flower_type ft ON fb.flower_id = ft.flower_id "
                + "WHERE ro.status = 'pending'";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                RepairOrders order = new RepairOrders();
                order.setRepairId(rs.getInt("repair_id"));
                order.setBouquetId(rs.getInt("bouquet_id"));
                order.setBouquetName(rs.getString("bouquet_name"));
                order.setBatchId(rs.getInt("batch_id"));
                order.setFlowerName(rs.getString("flower_name"));
                order.setReason(rs.getString("reason"));
                order.setCreatedAt(rs.getTimestamp("created_at").toString()); // Sử dụng Timestamp thay vì String
                order.setStatus(rs.getString("status"));
                orders.add(order);
            }
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                System.out.println("Lỗi đóng tài nguyên: " + e.getMessage());
            }
        }
        return orders;
    }

    public void completeRepairOrder(int repairId, int bouquetId, int oldBatchId, int newBatchId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = dbc.getConnection();
            connection.setAutoCommit(false);

            // Lấy thông tin hoa cho old_batch_id
            String getOldBatchSQL = "SELECT fb.flower_id, ft.flower_name "
                    + "FROM flower_batch fb "
                    + "JOIN flower_type ft ON fb.flower_id = ft.flower_id "
                    + "WHERE fb.batch_id = ?";
            ps = connection.prepareStatement(getOldBatchSQL);
            ps.setInt(1, oldBatchId);
            rs = ps.executeQuery();
            String oldFlowerName = null;
            int oldFlowerId = -1;
            if (rs.next()) {
                oldFlowerId = rs.getInt("flower_id");
                oldFlowerName = rs.getString("flower_name");
            } else {
                throw new SQLException("Lô hoa cũ không tồn tại: batch_id=" + oldBatchId);
            }
            rs.close();

            // Kiểm tra new_batch_id (nếu có)
            String newFlowerName = null;
            if (newBatchId != -1) {
                String checkNewBatchSQL = "SELECT fb.flower_id, ft.flower_name, fb.status "
                        + "FROM flower_batch fb "
                        + "JOIN flower_type ft ON fb.flower_id = ft.flower_id "
                        + "WHERE fb.batch_id = ?";
                ps = connection.prepareStatement(checkNewBatchSQL);
                ps.setInt(1, newBatchId);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("Lô hoa mới không tồn tại: batch_id=" + newBatchId);
                }
                int newFlowerId = rs.getInt("flower_id");
                newFlowerName = rs.getString("flower_name");
                String newStatus = rs.getString("status");
                rs.close();
                if (!"fresh".equals(newStatus)) {
                    throw new SQLException("Lô hoa mới phải có trạng thái 'fresh': batch_id=" + newBatchId);
                }
                // Kiểm tra cùng loại hoa bằng flower_id
                if (newFlowerId != oldFlowerId) {
                    throw new SQLException("Lô hoa mới phải cùng loại với lô hoa cũ");
                }
            }

            // Cập nhật trạng thái repair_orders thành completed
            String updateRepairOrderSQL = "UPDATE repair_orders SET status = 'completed' WHERE repair_id = ?";
            ps = connection.prepareStatement(updateRepairOrderSQL);
            ps.setInt(1, repairId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Không tìm thấy yêu cầu sửa: repair_id=" + repairId);
            }

            // Ghi lịch sử sửa chữa
            String insertHistorySQL = "INSERT INTO repair_history (repair_id, bouquet_id, old_batch_id, new_batch_id, old_flower_name, new_flower_name, updated_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
            ps = connection.prepareStatement(insertHistorySQL);
            ps.setInt(1, repairId);
            ps.setInt(2, bouquetId);
            ps.setInt(3, oldBatchId);
            if (newBatchId == -1) {
                ps.setNull(4, java.sql.Types.INTEGER);
                ps.setNull(6, java.sql.Types.VARCHAR);
            } else {
                ps.setInt(4, newBatchId);
                ps.setString(6, newFlowerName); // new_flower_name giống old_flower_name
            }
            ps.setString(5, oldFlowerName);
            ps.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
                closeResources();
            } catch (Exception e) {
                System.err.println("Lỗi đóng tài nguyên: " + e.getMessage());
            }
        }
    }

    public boolean isSameFlowerType(int batchId1, int batchId2) throws SQLException {
        String sql = "SELECT f1.flower_id AS flower_id1, f2.flower_id AS flower_id2 "
                + "FROM flower_batch f1, flower_batch f2 "
                + "WHERE f1.batch_id = ? AND f2.batch_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, batchId1);
            ps.setInt(2, batchId2);
            rs = ps.executeQuery();
            if (rs.next()) {
                int flowerId1 = rs.getInt("flower_id1");
                int flowerId2 = rs.getInt("flower_id2");
                return flowerId1 == flowerId2 && flowerId1 != 0;
            }
            return false; // Một hoặc cả hai lô không tồn tại
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                System.out.println("Lỗi đóng tài nguyên: " + e.getMessage());
            }
        }
    }
}
