/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.RepairOrders;

/**
 *
 * @author Admin
 */
public class RepairOrdersDAO extends BaseDao {

    public List<RepairOrders> getPendingRepairOrders() throws SQLException {
        List<RepairOrders> orders = new ArrayList<>();
        String sql = "SELECT repair_id, bouquet_id, batch_id, reason, created_at, status "
                + "FROM repair_orders WHERE status = 'pending'";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                RepairOrders order = new RepairOrders();
                order.setRepairId(rs.getInt("repair_id"));
                order.setBouquetId(rs.getInt("bouquet_id"));
                order.setBatchId(rs.getInt("batch_id"));
                order.setReason(rs.getString("reason"));
                order.setCreatedAt(rs.getString("created_at"));
                order.setStatus(rs.getString("status"));
                orders.add(order);
            }
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return orders;
    }

    public void completeRepairOrder(int repairId, int bouquetId, int oldBatchId, int newBatchId) throws SQLException {
        try {
            connection = dbc.getConnection();
            connection.setAutoCommit(false);

            // Cập nhật repair_orders
            String updateRepairOrder = "UPDATE repair_orders SET status = 'completed' WHERE repair_id = ?";
            ps = connection.prepareStatement(updateRepairOrder);
            ps.setInt(1, repairId);
            ps.executeUpdate();

            // Cập nhật bouquet_raw (thay batch_id cũ bằng batch_id mới)
            String updateBouquetRaw = "UPDATE bouquet_raw SET batch_id = ? WHERE bouquet_id = ? AND batch_id = ?";
            ps = connection.prepareStatement(updateBouquetRaw);
            ps.setInt(1, newBatchId);
            ps.setInt(2, bouquetId);
            ps.setInt(3, oldBatchId); // Lấy batch_id từ repair_orders
            ps.executeUpdate();

            // Cập nhật bouquet status nếu không còn lô near_expired
            String checkBouquet = "SELECT COUNT(*) FROM bouquet_raw br "
                    + "JOIN flower_batch fb ON br.batch_id = fb.batch_id "
                    + "WHERE br.bouquet_id = ? AND fb.status = 'near_expired'";
            ps = connection.prepareStatement(checkBouquet);
            ps.setInt(1, bouquetId);
            rs = ps.executeQuery();
            rs.next();
            int nearExpiredCount = rs.getInt(1);
            rs.close();

            if (nearExpiredCount == 0) {
                String updateBouquet = "UPDATE bouquet SET status = 'valid' WHERE Bouquet_ID = ?";
                ps = connection.prepareStatement(updateBouquet);
                ps.setInt(1, bouquetId);
                ps.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            try {
                connection.setAutoCommit(true);
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }
}
