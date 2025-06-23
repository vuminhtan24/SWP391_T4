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
        String sql = "INSERT INTO la_fioreria.flower_batch (flower_id, unit_price, import_date, expiration_date, quantity, hold, warehouse_id, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
                                  int quantity, int hold, int warehouseId, String status) {
        String sql = "UPDATE la_fioreria.flower_batch SET unit_price = ?, import_date = ?, " +
                     "expiration_date = ?, quantity = ?, hold = ?, warehouse_id = ?, status = ? WHERE batch_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, unitPrice);
            ps.setString(2, importDate);
            ps.setString(3, expirationDate);
            ps.setInt(4, quantity);
            ps.setInt(5, hold);
            ps.setInt(6, warehouseId);
            ps.setString(7, status); // NEW
            ps.setInt(8, batchId);
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
}

