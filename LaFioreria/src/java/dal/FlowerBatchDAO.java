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
import model.FlowerBatch;

/**
 *
 * @author Admin
 */
public class FlowerBatchDAO extends BaseDao {

    // Lấy tất cả lô hoa
    public List<FlowerBatch> getAllFlowerBatches() {
        List<FlowerBatch> list = new ArrayList<>();
        String sql = "SELECT * FROM la_fioreria.flower_batch";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                FlowerBatch fb = new FlowerBatch();
                WarehouseDAO wdao = new WarehouseDAO();
                fb.setBatchId(rs.getInt("batch_id"));
                fb.setFlowerId(rs.getInt("flower_id"));
                fb.setUnitPrice(rs.getInt("unit_price"));
                fb.setImportDate(rs.getString("import_date"));
                fb.setExpirationDate(rs.getString("expiration_date"));
                fb.setQuantity(rs.getInt("quantity"));
                fb.setHold(rs.getInt("hold"));
                fb.setStatus(rs.getString("status")); // NEW
                fb.setWarehouse(wdao.getWarehouseById(rs.getInt("warehouse_id")));
                list.add(fb);
            }
        } catch (SQLException e) {
            System.err.println("FlowerBatchDAO: SQLException in getAllFlowerBatches - " + e.getMessage());
            throw new RuntimeException("Failed to retrieve flower batches", e);
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return list;
    }

    // Lấy lô hoa theo ID
    public FlowerBatch getFlowerBatchById(int batchId) {
        String sql = "SELECT * FROM la_fioreria.flower_batch WHERE batch_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, batchId);
            rs = ps.executeQuery();
            if (rs.next()) {
                FlowerBatch fb = new FlowerBatch();
                WarehouseDAO wdao = new WarehouseDAO();
                fb.setBatchId(rs.getInt("batch_id"));
                fb.setFlowerId(rs.getInt("flower_id"));
                fb.setUnitPrice(rs.getInt("unit_price"));
                fb.setImportDate(rs.getString("import_date"));
                fb.setExpirationDate(rs.getString("expiration_date"));
                fb.setQuantity(rs.getInt("quantity"));
                fb.setHold(rs.getInt("hold"));
                fb.setStatus(rs.getString("status")); // NEW
                fb.setWarehouse(wdao.getWarehouseById(rs.getInt("warehouse_id")));
                return fb;
            }
        } catch (SQLException e) {
            System.err.println("FlowerBatchDAO: SQLException in getFlowerBatchById - " + e.getMessage());
            throw new RuntimeException("Failed to retrieve flower batch", e);
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }

    // Thêm lô hoa mới
    public void addFlowerBatch(int flowerId, int unitPrice, String importDate, String expirationDate,
            int quantity, int hold, int warehouseId, String status) {
        String sql = "INSERT INTO la_fioreria.flower_batch (flower_id, unit_price, import_date, expiration_date, quantity, hold, warehouse_id, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, flowerId);
            ps.setInt(2, unitPrice);
            ps.setString(3, importDate);
            ps.setString(4, expirationDate);
            ps.setInt(5, quantity);
            ps.setInt(6, hold);
            ps.setInt(7, warehouseId);
            ps.setString(8, status); // NEW
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("FlowerBatchDAO: SQLException in addFlowerBatch - " + e.getMessage());
            throw new RuntimeException("Failed to add flower batch", e);
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    // Cập nhật lô hoa
    public void updateFlowerBatch(int batchId, int unitPrice, String importDate, String expirationDate,
            int quantity, int hold, int warehouseId) {
        String sql = "UPDATE la_fioreria.flower_batch SET unit_price = ?, import_date = ?, "
                + "expiration_date = ?, quantity = ?, hold = ?, warehouse_id = ? WHERE batch_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, unitPrice);
            ps.setString(2, importDate);
            ps.setString(3, expirationDate);
            ps.setInt(4, quantity);
            ps.setInt(5, hold);
            ps.setInt(6, warehouseId);
            ps.setInt(7, batchId);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("FlowerBatchDAO: SQLException in updateFlowerBatch - " + e.getMessage());
            throw new RuntimeException("Failed to update flower batch", e);
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    // Xóa lô hoa
    public void deleteFlowerBatch(int batchId) {
        String sql = "DELETE FROM la_fioreria.flower_batch WHERE batch_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, batchId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("FlowerBatchDAO: SQLException in deleteFlowerBatch - " + e.getMessage());
            throw new RuntimeException("Failed to delete flower batch", e);
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    // Lấy danh sách lô hoa theo flower_id
    public List<FlowerBatch> getFlowerBatchesByFlowerId(int flowerId) {
        List<FlowerBatch> list = new ArrayList<>();
        String sql = "SELECT * FROM la_fioreria.flower_batch WHERE flower_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, flowerId);
            rs = ps.executeQuery();
            while (rs.next()) {
                FlowerBatch fb = new FlowerBatch();
                WarehouseDAO wdao = new WarehouseDAO();
                fb.setBatchId(rs.getInt("batch_id"));
                fb.setFlowerId(rs.getInt("flower_id"));
                fb.setUnitPrice(rs.getInt("unit_price"));
                fb.setImportDate(rs.getString("import_date"));
                fb.setExpirationDate(rs.getString("expiration_date"));
                fb.setQuantity(rs.getInt("quantity"));
                fb.setHold(rs.getInt("hold"));
                fb.setStatus(rs.getString("status")); // NEW
                fb.setWarehouse(wdao.getWarehouseById(rs.getInt("warehouse_id")));
                list.add(fb);
            }
        } catch (SQLException e) {
            System.err.println("FlowerBatchDAO: SQLException in getFlowerBatchesByFlowerId - " + e.getMessage());
            throw new RuntimeException("Failed to retrieve flower batches by flower ID", e);
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return list;
    }

    public void reduceBatchQuantity(int quantity, int flowerId, int batchId) {
        String sql = "UPDATE la_fioreria.flower_batch\n"
                + "SET quantity = quantity - ?\n"
                + "WHERE flower_id = ?\n"
                + "AND batch_id = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, flowerId);
            ps.setInt(3, batchId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("FlowerBatchDAO: SQLException in reduceBatchQuantity - " + e.getMessage());
            throw new RuntimeException("Failed to delete flower batch", e);
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }

    }

    public void insertSoftHold(int customerId) {
        String sql = """
            INSERT INTO flower_batch_allocation (
                batch_id, flower_id, order_id, quantity, status, created_at, cart_id
            )
            SELECT fb.batch_id, fb.flower_id, NULL, br.quantity * cd.quantity, 'soft_hold', NOW(), cd.cart_id
            FROM cartdetails cd
            JOIN bouquet_raw br ON cd.bouquet_id = br.bouquet_id
            JOIN flower_batch fb ON fb.batch_id = br.batch_id
            WHERE cd.customer_id = ?
        """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("FlowerBatchDAO: Error in insertSoftHold - " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public void cleanupExpiredSoftHolds() {
        String sql = """
            UPDATE flower_batch_allocation
            SET status = 'cancelled'
            WHERE status = 'soft_hold'
              AND created_at < NOW() - INTERVAL 30 MINUTE
        """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            int updated = ps.executeUpdate();
            System.out.println("Đã hủy " + updated + " bản ghi soft_hold hết hạn");
        } catch (SQLException e) {
            System.err.println("FlowerBatchDAO: Error in cleanupExpiredSoftHolds - " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public void cancelSoftHoldByCartId(int cartId) {
        String sql = """
            UPDATE flower_batch_allocation
            SET status = 'cancelled'
            WHERE cart_id = ? AND status = 'soft_hold'
        """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cartId);
            int affectedRows = ps.executeUpdate();
            System.out.println("Đã hủy " + affectedRows + " soft_hold theo cart_id=" + cartId);
        } catch (SQLException e) {
            System.err.println("FlowerBatchDAO: Error in cancelSoftHoldByCartId - " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public void confirmSoftHoldByCustomerId(int customerId, int orderId) {
        String sql = """
        UPDATE flower_batch_allocation
        SET status = 'confirmed', order_id = ?
        WHERE status = 'soft_hold'
          AND cart_id IN (
              SELECT cart_id FROM cartdetails WHERE customer_id = ?
          )
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setInt(2, customerId);
            int updated = ps.executeUpdate();
            System.out.println("[DEBUG] confirmSoftHoldByCustomerId → Updated rows: " + updated);
        } catch (SQLException e) {
            System.err.println("[ERROR] FlowerBatchDAO: confirmSoftHoldByCustomerId failed → " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to confirm soft hold allocations", e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("[WARN] FlowerBatchDAO: Failed to close resources → " + e.getMessage());
            }
        }
    }

}
