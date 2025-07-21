/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.CartWholeSaleDetail;

/**
 *
 * @author ADMIN
 */
public class CartWholeSaleDAO extends BaseDao {

    public void insertCartWholeSaleItem(int userId, int bouquetId, int quantity, int pricePerUnit, int totalValue, int expense, String request_group_id) {
        String sql = "INSERT INTO cartwholesaledetails (userID, bouquetID, quantity, pricePerUnit, totalValue, expense, request_group_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, bouquetId);
            ps.setInt(3, quantity);
            ps.setInt(4, pricePerUnit);
            ps.setInt(5, totalValue);
            ps.setInt(6, expense);
            ps.setString(7, request_group_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public boolean hasPendingWholesale(int userId, int bouquetId) {
        String sql = "SELECT 1 FROM cartwholesaledetails WHERE userID = ? AND bouquetID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, bouquetId);
            rs = ps.executeQuery();
            return rs.next(); // Nếu có kết quả ➝ đã tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return false;
    }

    public void clearCartWholeSaleByUser(int userId) {
        String sql = "DELETE FROM cartwholesaledetails WHERE userID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public List<CartWholeSaleDetail> getCartWholeSaleByUser(int userId) {
        List<CartWholeSaleDetail> list = new ArrayList<>();

        String sql = "SELECT * FROM cartwholesaledetails WHERE userID = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                CartWholeSaleDetail item = new CartWholeSaleDetail();
                item.setCartWholeSaleID(rs.getInt("cartWholeSaleID"));
                item.setUserID(rs.getInt("userID"));
                item.setBouquetID(rs.getInt("bouquetID"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPricePerUnit(rs.getInt("pricePerUnit"));
                item.setTotalValue(rs.getInt("totalValue"));
                item.setExpense(rs.getInt("expense"));
                list.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }

        return list;
    }

}
