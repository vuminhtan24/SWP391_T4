/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.RepairHistory;

/**
 *
 * @author Admin
 */
public class RepairHistoryDAO extends BaseDao {

    public List<RepairHistory> getRepairHistory() throws SQLException {
        List<RepairHistory> history = new ArrayList<>();
        String sql = "SELECT rh.id, rh.repair_id, rh.bouquet_id, b.bouquet_name, "
                + "rh.old_batch_id, rh.old_flower_name, "
                + "rh.new_batch_id, rh.new_flower_name, "
                + "rh.updated_at "
                + "FROM repair_history rh "
                + "LEFT JOIN bouquet b ON rh.bouquet_id = b.Bouquet_ID";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                RepairHistory record = new RepairHistory();
                record.setId(rs.getInt("id"));
                record.setRepairId(rs.getInt("repair_id"));
                record.setBouquetId(rs.getInt("bouquet_id"));
                record.setBouquetName(rs.getString("bouquet_name"));
                record.setOldBatchId(rs.getInt("old_batch_id"));
                record.setOldFlowerName(rs.getString("old_flower_name"));
                record.setNewBatchId(rs.getInt("new_batch_id") == 0 ? null : rs.getInt("new_batch_id"));
                record.setNewFlowerName(rs.getString("new_flower_name"));
                record.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toString() : null);
                history.add(record);
            }
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                System.err.println("Lỗi đóng tài nguyên: " + e.getMessage());
            }
        }
        return history;
    }

}
