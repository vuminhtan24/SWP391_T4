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
import model.FlowerType;

/**
 *
 * @author Admin
 */

public class FlowerTypeDAO extends BaseDao {
    
    // Lấy tất cả loại hoa
    public List<FlowerType> getAllFlowerTypes() {
        List<FlowerType> list = new ArrayList<>();
        String sql = "SELECT * FROM la_fioreria.flower_type";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                FlowerType ft = new FlowerType();
                ft.setFlowerId(rs.getInt("flower_id"));
                ft.setFlowerName(rs.getString("flower_name") != null ? rs.getString("flower_name").trim() : "");
                ft.setImage(rs.getString("image") != null ? rs.getString("image").trim() : "");
                ft.setActive(rs.getBoolean("active"));
                list.add(ft);
            }
        } catch (SQLException e) {
            System.err.println("FlowerTypeDAO: SQLException in getAllFlowerTypes - " + e.getMessage());
            throw new RuntimeException("Failed to retrieve flower types", e);
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return list;
    }

    // Lấy loại hoa theo ID
    public FlowerType getFlowerTypeById(int flowerId) {
        String sql = "SELECT * FROM la_fioreria.flower_type WHERE flower_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, flowerId);
            rs = ps.executeQuery();
            if (rs.next()) {
                FlowerType ft = new FlowerType();
                ft.setFlowerId(rs.getInt("flower_id"));
                ft.setFlowerName(rs.getString("flower_name") != null ? rs.getString("flower_name").trim() : "");
                ft.setImage(rs.getString("image") != null ? rs.getString("image").trim() : "");
                ft.setActive(rs.getBoolean("active"));
                return ft;
            }
        } catch (SQLException e) {
            System.err.println("FlowerTypeDAO: SQLException in getFlowerTypeById - " + e.getMessage());
            throw new RuntimeException("Failed to retrieve flower type", e);
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }

    // Thêm loại hoa mới
    public void addFlowerType(String flowerName, String image, boolean active) {
        String sql = "INSERT INTO la_fioreria.flower_type (flower_name, image, active) VALUES (?, ?, 1)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, flowerName);
            ps.setString(2, image);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("FlowerTypeDAO: SQLException in addFlowerType - " + e.getMessage());
            throw new RuntimeException("Failed to add flower type", e);
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    // Cập nhật loại hoa
    public void updateFlowerType(int flowerId, String flowerName, String image, boolean active) {
        String sql = "UPDATE la_fioreria.flower_type SET flower_name = ?, image = ? WHERE flower_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, flowerName);
            ps.setString(2, image);
            ps.setInt(3, flowerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("FlowerTypeDAO: SQLException in updateFlowerType - " + e.getMessage());
            throw new RuntimeException("Failed to update flower type", e);
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    // Xóa mềm loại hoa (active = 0)
    public void deleteFlowerType(int flowerId) {
        String sql = "UPDATE la_fioreria.flower_type SET active = 0 WHERE flower_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, flowerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("FlowerTypeDAO: SQLException in deleteFlowerType - " + e.getMessage());
            throw new RuntimeException("Failed to delete flower type", e);
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }
    
    public List<FlowerType> searchRawFlowerByKeyword(String keyword) {
        List<FlowerType> list = new ArrayList<>();
        String sql = "SELECT * FROM la_fioreria.raw_flower WHERE raw_name LIKE ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + keyword.trim() + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                FlowerType ft = new FlowerType();

                ft.setFlowerId(rs.getInt("flower_id"));
                ft.setFlowerName(rs.getString("flower_name").trim());
                ft.setImage(rs.getString("image").trim());
                ft.setActive(rs.getBoolean("active"));

                list.add(ft);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
