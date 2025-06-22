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
public class CartDAO extends BaseDao {
    
    public CartDetail getCartItem(int customerId, int bouquetId) {
        String sql = "SELECT * FROM cartdetails WHERE customer_id = ? AND bouquet_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setInt(2, bouquetId);
            rs = ps.executeQuery();
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
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return null;
    }
    
    public void updateQuantity(int customerId, int bouquetId, int quantity) {
        String sql = "UPDATE cartdetails SET quantity = ? WHERE customer_id = ? AND bouquet_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, customerId);
            ps.setInt(3, bouquetId);
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
    
    public void deleteItem(int customerId, int bouquetId) {
        String sql = "DELETE FROM cartdetails WHERE customer_id = ? AND bouquet_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setInt(2, bouquetId);
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
    
    public void insertItem(int customerId, int bouquetId, int quantity) {
        String sql = "INSERT INTO cartdetails (customer_id, bouquet_id, quantity) VALUES (?, ?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setInt(2, bouquetId);
            ps.setInt(3, quantity);
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
    
    public List<CartDetail> getCartDetailsByCustomerId(int customerId) {
        List<CartDetail> list = new ArrayList<>();
        String sql = "SELECT \n"
                + "    cd.cart_id, cd.customer_id, cd.bouquet_id, cd.quantity,\n"
                + "    b.bouquet_name, b.description, b.cid, b.price,\n"
                + "    bi.image_url\n"
                + "FROM\n"
                + "    cartdetails cd\n"
                + "    JOIN bouquet b ON cd.bouquet_id = b.Bouquet_ID\n"
                + "    JOIN bouquet_images bi ON bi.Bouquet_ID = b.Bouquet_ID\n"
                + "WHERE\n"
                + "    customer_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();
            while (rs.next()) {
                CartDetail cd = new CartDetail();
                Bouquet b = new Bouquet();
                
                cd.setCartId(rs.getInt("cart_id"));
                cd.setCustomerId(rs.getInt("customer_id"));
                cd.setBouquetId(rs.getInt("bouquet_id"));
                cd.setQuantity(rs.getInt("quantity"));
                
                b.setBouquetId(rs.getInt("bouquet_id"));
                b.setBouquetName(rs.getString("bouquet_name"));
                b.setDescription(rs.getString("description"));
                b.setCid(rs.getInt("cid"));
                b.setPrice(rs.getInt("price"));
                b.setImageUrl(rs.getString("image_url"));
                
                cd.setBouquet(b);
                
                list.add(cd);
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
    
    public Bouquet getBouquetById(int bouquetId) {
        String sql = """
        SELECT bouquet_id, bouquet_name,
               description, image_url, cid, price
        FROM bouquet
        WHERE bouquet_id = ?
    """;
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bouquetId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Bouquet bouquet = new Bouquet();
                bouquet.setBouquetId(rs.getInt("bouquet_id"));
                bouquet.setBouquetName(rs.getString("bouquet_name"));
                bouquet.setDescription(rs.getString("description"));
//                bouquet.setImageUrl(rs.getString("image_url"));
                bouquet.setCid(rs.getInt("cid"));
                bouquet.setPrice(rs.getInt("price"));
                return bouquet;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return null;
    }
    
    public int insertOrder(Order order) {
        String sql = "INSERT INTO `order` (order_date, customer_id, total_amount, status_id) VALUES (?, ?, ?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getOrderDate());
            ps.setInt(2, order.getCustomerId());
            ps.setString(3, order.getTotalAmount());
            ps.setInt(4, 1); // status_id = 1 (processing)
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return -1;
    }
    
    public void insertOrderItem(OrderItem item) {
        String sql = "INSERT INTO order_item (order_id, bouquet_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getBouquetId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getUnitPrice());
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
    
    public void deleteCartByCustomerId(int customerId) {
        String sql = "DELETE FROM cartdetails WHERE customer_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, customerId);
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
    
}
