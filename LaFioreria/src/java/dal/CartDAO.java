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

    public void insertQuotedToCart(List<CartDetail> items) {
        String sql = "INSERT INTO cartdetails (customer_id, bouquet_id, quantity) VALUES (?, ?, ?)";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);

            for (CartDetail item : items) {
                ps.setInt(1, item.getCustomerId());
                ps.setInt(2, item.getBouquetId());
                ps.setInt(3, item.getQuantity());
                ps.addBatch(); // Thêm vào batch
            }

            ps.executeBatch(); // Thực thi toàn bộ batch
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace();
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
                b.setPrice(rs.getInt("price"));
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
        int orderId = -1;
        String sql = """
        INSERT INTO `order` (order_date, customer_id, customer_name, customer_phone, customer_address,
                             total_sell, total_import, status_id, payment_method, type)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setDate(1, Date.valueOf(order.getOrderDate()));

            if (order.getCustomerId() != null && order.getCustomerId() != -1) {
                ps.setInt(2, order.getCustomerId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            ps.setString(3, order.getCustomerName());
            ps.setString(4, order.getCustomerPhone());
            ps.setString(5, order.getCustomerAddress());

            // Parse int safely
            int totalSell = Integer.parseInt(order.getTotalSell().replaceAll("[^\\d]", ""));
            int totalImport = Integer.parseInt(order.getTotalImport().replaceAll("[^\\d]", ""));
            ps.setInt(6, totalSell);
            ps.setInt(7, totalImport);

            ps.setInt(8, order.getStatusId());
            ps.setString(9, order.getPaymentMethod());
            ps.setString(10, order.getType());

            System.out.println("[DEBUG] Executing insertOrder with: "
                    + "date=" + order.getOrderDate()
                    + ", customerId=" + order.getCustomerId()
                    + ", totalSell=" + totalSell
                    + ", totalImport=" + totalImport);

            int rowsAffected = ps.executeUpdate();
            System.out.println("[DEBUG] insertOrder → rowsAffected = " + rowsAffected);

            if (rowsAffected > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    orderId = rs.getInt(1);
                    System.out.println("[DEBUG] insertOrder → Generated orderId = " + orderId);
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] insertOrder → " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                System.err.println("[WARN] insertOrder → Failed to close resources: " + e.getMessage());
            }
        }

        return orderId;
    }

    public Timestamp getOrderCreatedAt(int orderId) {
        String sql = "SELECT created_at FROM `order` WHERE order_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("created_at");
            }
        } catch (SQLException e) {
            System.err.println("getOrderCreatedAt ERROR: " + e.getMessage());
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return null;
    }

    public boolean insertOrderItem(OrderItem item) {
        String sql = """
        INSERT INTO order_item (order_id, bouquet_id, quantity, unit_price, sellPrice, request_group_id)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getBouquetId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getUnitPrice());
            ps.setDouble(5, item.getSellPrice());
            ps.setString(6, item.getRequest_group_id());
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[DEBUG] insertOrderItem → Inserted: orderId=" + item.getOrderId()
                        + ", bouquetId=" + item.getBouquetId()
                        + ", quantity=" + item.getQuantity()
                        + ", unitPrice=" + item.getUnitPrice()
                        + ", sellPrice=" + item.getSellPrice());
                return true;
            } else {
                System.err.println("[ERROR] insertOrderItem → No rows inserted for orderId=" + item.getOrderId()
                        + ", bouquetId=" + item.getBouquetId());
                return false;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] insertOrderItem → " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("[WARN] insertOrderItem → Error closing resources: " + e.getMessage());
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

    public Integer getCartIdByCustomerAndBouquet(int customerId, int bouquetId) {
        String sql = """
        SELECT cart_id
        FROM cartdetails
        WHERE customer_id = ? AND bouquet_id = ?
        LIMIT 1
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setInt(2, bouquetId);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("cart_id");
            }
        } catch (SQLException e) {
            System.err.println("CartDAO: SQLException in getCartIdByCustomerAndBouquet - " + e.getMessage());
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // Ignore silently
            }
        }

        return null;
    }

}
