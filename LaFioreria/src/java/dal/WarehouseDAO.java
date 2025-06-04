/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Warehouse;

/**
 *
 * @author Admin
 */
public class WarehouseDAO extends DBContext { // DBContext để lấy biến connection

    public List<Warehouse> getAllWarehouse() {
        List<Warehouse> list = new ArrayList<>();
        String sql = "SELECT * FROM la_fioreria.warehouse";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Warehouse w = new Warehouse(); // Tạo mới đối tượng cho mỗi bản ghi
                w.setWarehouseId(rs.getInt("Warehouse_ID"));
                w.setName(rs.getString("name").trim()); // Thêm .trim() để loại bỏ khoảng trắng thừa
                w.setAddress(rs.getString("address"));
                w.setManagerId(rs.getInt("Manager_Id"));
                list.add(w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Warehouse getWarehouseById(int id) {
        String sql = "SELECT * FROM la_fioreria.warehouse WHERE Warehouse_ID = ?";
        Warehouse w = new Warehouse();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                w.setWarehouseId(rs.getInt("Warehouse_ID"));
                w.setName(rs.getString("name"));
                w.setAddress(rs.getString("address"));
                w.setManagerId(rs.getInt("Manager_Id"));
                return w;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addWarehouse(String name, String address, int manager_id) {
        String sql = "INSERT INTO la_fioreria.warehouse (Name, address, Manager_Id) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setInt(3, manager_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        WarehouseDAO dao = new WarehouseDAO();
        System.out.println(dao.getWarehouseById(2));
    }
}
