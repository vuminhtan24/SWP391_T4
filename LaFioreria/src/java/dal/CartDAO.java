/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.CartDetail;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Bouquet;
import model.Order;
import model.OrderItem;

/**
 *
 * @author Legion
 */
public class CartDAO extends DBContext {

    public CartDetail getCartItem(int customerId, int bouquetId) {
        String sql = "SELECT * FROM cartdetails WHERE customer_id = ? AND bouquet_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, bouquetId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CartDetail(
                        rs.getInt("cart_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("bouquet_id"),
                        rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateQuantity(int customerId, int bouquetId, int quantity) {
        String sql = "UPDATE cartdetails SET quantity = ? WHERE customer_id = ? AND bouquet_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, customerId);
            stmt.setInt(3, bouquetId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(int customerId, int bouquetId) {
        String sql = "DELETE FROM cartdetails WHERE customer_id = ? AND bouquet_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, bouquetId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertItem(int customerId, int bouquetId, int quantity) {
        String sql = "INSERT INTO cartdetails (customer_id, bouquet_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, bouquetId);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CartDetail> getCartDetailsByCustomerId(int customerId) {
        List<CartDetail> list = new ArrayList<>();
        String sql = "SELECT cart_id, customer_id, bouquet_id, quantity FROM cartdetails WHERE customer_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartDetail cd = new CartDetail();
                cd.setCartId(rs.getInt("cart_id"));
                cd.setCustomerId(rs.getInt("customer_id"));
                cd.setBouquetId(rs.getInt("bouquet_id"));
                cd.setQuantity(rs.getInt("quantity"));

                list.add(cd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Bouquet getBouquetById(int bouquetId) {
        String sql = """
        SELECT bouquet_id, bouquet_name, created_at, expiration_date, created_by,
               description, image_url, cid, price
        FROM bouquet
        WHERE bouquet_id = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, bouquetId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Bouquet bouquet = new Bouquet();
                bouquet.setBouquetId(rs.getInt("bouquet_id"));
                bouquet.setBouquetName(rs.getString("bouquet_name"));
                bouquet.setCreatedAt(rs.getDate("created_at"));
                bouquet.setExpirationDate(rs.getDate("expiration_date"));
                bouquet.setCreatedBy(rs.getInt("created_by"));
                bouquet.setDescription(rs.getString("description"));
                bouquet.setImageUrl(rs.getString("image_url"));
                bouquet.setCid(rs.getInt("cid"));
                bouquet.setPrice(rs.getInt("price"));

                return bouquet;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // nếu không tìm thấy
    }

    public int insertOrder(Order order) throws SQLException {
        String sql = "INSERT INTO `order` (order_date, customer_id, total_amount, status_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, order.getOrderDate());
            ps.setInt(2, order.getCustomerId());
            ps.setString(3, order.getTotalAmount());
            ps.setInt(4, 1); // mặc định status_id = 1 (đang xử lý)
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // order_id vừa tạo
                }
            }
        }
        return -1;
    }

    public void insertOrderItem(OrderItem item) throws SQLException {
        String sql = "INSERT INTO order_item (order_id, bouquet_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getBouquetId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getUnitPrice());

            ps.executeUpdate();
        }
    }

    public void deleteCartByCustomerId(int customerId) throws SQLException {
        String sql = "DELETE FROM cartdetails WHERE customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ps.executeUpdate();
        }
    }

}
