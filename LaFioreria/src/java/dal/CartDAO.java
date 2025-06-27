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

    public static void main(String[] args) {
        CartDAO cartDAO = new CartDAO();

        Order guestOrder = new Order();
        guestOrder.setOrderDate("2025-06-27");
        guestOrder.setCustomerId(null);  // Không có customer ID => khách vãng lai
        guestOrder.setCustomerName("Nguyễn Văn A");
        guestOrder.setCustomerPhone("0901234567");
        guestOrder.setCustomerAddress("123 Đường Láng, Đống Đa, Hà Nội");
        guestOrder.setTotalSell("1200000");
        guestOrder.setTotalImport("700000");

        int generatedOrderId = cartDAO.insertOrder(guestOrder);
        if (generatedOrderId != -1) {
            System.out.println("Thêm đơn hàng KHÁCH VÃNG LAI thành công, order_id = " + generatedOrderId);
        } else {
            System.err.println("Thêm đơn hàng KHÁCH VÃNG LAI thất bại.");
        }
    }

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
                + "    b.bouquet_name, b.description, b.cid, b.price, b.sellPrice\n"
                + "FROM\n"
                + "    cartdetails cd\n"
                + "    JOIN bouquet b ON cd.bouquet_id = b.Bouquet_ID\n"
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
                b.setSellPrice(rs.getInt("sellPrice"));

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
               description, cid, price
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
        String sqlWithCustomer = "INSERT INTO `order` (order_date, customer_id, total_sell, total_import, status_id) VALUES (?, ?, ?, ?, ?)";
        String sqlGuest = "INSERT INTO `order` (order_date, guest_name, guest_phone, guest_address, total_sell, total_import, status_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = dbc.getConnection();

            if (order.getCustomerId() != null) {
                // 👉 KHÁCH ĐĂNG NHẬP
                ps = connection.prepareStatement(sqlWithCustomer, PreparedStatement.RETURN_GENERATED_KEYS);
                int index = 1;
                ps.setString(index++, order.getOrderDate());
                ps.setInt(index++, order.getCustomerId());
                ps.setDouble(index++, Double.parseDouble(order.getTotalSell()));
                ps.setDouble(index++, Double.parseDouble(order.getTotalImport()));
                ps.setInt(index++, 1); // status_id = 1 (Pending)

            } else {
                // 👉 KHÁCH VÃNG LAI
                ps = connection.prepareStatement(sqlGuest, PreparedStatement.RETURN_GENERATED_KEYS);
                int index = 1;
                ps.setString(index++, order.getOrderDate());
                ps.setString(index++, order.getCustomerName());
                ps.setString(index++, order.getCustomerPhone());
                ps.setString(index++, order.getCustomerAddress());
                ps.setDouble(index++, Double.parseDouble(order.getTotalSell()));
                ps.setDouble(index++, Double.parseDouble(order.getTotalImport()));
                ps.setInt(index++, 1); // status_id = 1 (Pending)
            }

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Trả về order_id mới
            }

        } catch (SQLException e) {
            System.err.println("SQL Error in insertOrder: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Lỗi chuyển đổi số trong insertOrder: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources in insertOrder finally: " + e.getMessage());
            }
        }

        return -1; // Trả về -1 nếu insert thất bại
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
