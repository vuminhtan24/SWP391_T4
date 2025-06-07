/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Warehouse;

/**
 *
 * @author Admin
 */
public class WarehouseDAO extends BaseDao { // DBContext để lấy biến connection

    // Assuming you have instance variables:
// private Connection connection;
// private PreparedStatement ps;
// private ResultSet rs;
// private DBC dbc; // Your DB helper class
// And a method closeResources() that closes rs, ps, and connection.
    public List<Warehouse> getAllWarehouse() {
        List<Warehouse> list = new ArrayList<>();
        String sql = "SELECT * FROM la_fioreria.warehouse";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Warehouse w = new Warehouse();
                w.setWarehouseId(rs.getInt("Warehouse_ID"));
                w.setName(rs.getString("name").trim());
                w.setAddress(rs.getString("address").trim());
                w.setManagerId(rs.getInt("Manager_Id"));
                list.add(w);
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

    public Warehouse getWarehouseById(int id) {
        String sql = "SELECT * FROM la_fioreria.warehouse WHERE Warehouse_ID = ?";
        Warehouse w = null;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                w = new Warehouse();
                w.setWarehouseId(rs.getInt("Warehouse_ID"));
                w.setName(rs.getString("name").trim());
                w.setAddress(rs.getString("address").trim());
                w.setManagerId(rs.getInt("Manager_Id"));
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

        return w;
    }

    public void addWarehouse(String name, String address, int manager_id) {
        String sql = "INSERT INTO la_fioreria.warehouse (Name, address, Manager_Id) VALUES (?, ?, ?)";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, name.trim());
            ps.setString(2, address.trim());
            ps.setInt(3, manager_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public static void main(String[] args) {
        WarehouseDAO dao = new WarehouseDAO();
        System.out.println(dao.getWarehouseById(2));
    }
}
