/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.OrderStatus;
import model.User;

/**
 *
 * @author VU MINH TAN
 */
public class OrderDAO extends BaseDao{
    public List<Order> getAllOrders() {
        List<Order> listOrders = new ArrayList<>();
        String sql = "SELECT o.order_id, o.order_date, o.customer_id, u.Fullname AS customer_name, " + // Đổi u.User_Name thành u.Fullname
                     "o.total_amount, o.status_id, os.status_name, o.shipper_id, s.Fullname AS shipper_name " + // Đổi s.User_Name thành s.Fullname
                     "FROM `order` o " +
                     "JOIN `user` u ON o.customer_id = u.User_ID " +
                     "JOIN `order_status` os ON o.status_id = os.order_status_id " +
                     "LEFT JOIN `user` s ON o.shipper_id = s.User_ID";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng Order với constructor đầy đủ
                listOrders.add(new Order(
                        rs.getInt("order_id"),
                        // Xử lý null an toàn cho các trường String trước khi trim()
                        rs.getString("order_date") != null ? rs.getString("order_date").trim() : null,
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"), // Lấy tên khách hàng
                        rs.getString("total_amount") != null ? rs.getString("total_amount").trim() : null,
                        rs.getInt("status_id"),
                        rs.getString("status_name"),   // Lấy tên trạng thái
                        // Lấy shipper_id (có thể null), sử dụng getObject để xử lý null Integer
                        rs.getObject("shipper_id") != null ? rs.getInt("shipper_id") : null,
                        rs.getString("shipper_name")   // Lấy tên shipper
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return listOrders;
    }
    public Order getOrderDetailById(int orderId) {
        Order order = null;
        String sql = "SELECT o.order_id, o.order_date, o.customer_id, u.Fullname AS customer_name, " + // Đổi u.User_Name thành u.Fullname
                     "o.total_amount, o.status_id, os.status_name, o.shipper_id, s.Fullname AS shipper_name " + // Đổi s.User_Name thành s.Fullname
                     "FROM `order` o " +
                     "JOIN `user` u ON o.customer_id = u.User_ID " +
                     "JOIN `order_status` os ON o.status_id = os.order_status_id " +
                     "LEFT JOIN `user` s ON o.shipper_id = s.User_ID " +
                     "WHERE o.order_id = ?"; // Thêm điều kiện WHERE để lấy một đơn hàng cụ thể

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId); // Set tham số cho câu lệnh WHERE
            rs = ps.executeQuery();

            if (rs.next()) {
                order = new Order(
                        rs.getInt("order_id"),
                        rs.getString("order_date") != null ? rs.getString("order_date").trim() : null,
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("total_amount") != null ? rs.getString("total_amount").trim() : null,
                        rs.getInt("status_id"),
                        rs.getString("status_name"),
                        rs.getObject("shipper_id") != null ? rs.getInt("shipper_id") : null,
                        rs.getString("shipper_name")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy chi tiết đơn hàng (ID: " + orderId + "): " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }
        return order;
    }
    public boolean updateOrderStatus(int orderId, int newStatusId) {
        String sql = "UPDATE `order` SET status_id = ? WHERE order_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, newStatusId);
            ps.setInt(2, orderId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi cập nhật trạng thái đơn hàng (ID: " + orderId + ", Status: " + newStatusId + "): " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }
    }
    public boolean assignShipper(int orderId, Integer shipperId) { // shipperId dùng Integer để có thể truyền giá trị null
        String sql = "UPDATE `order` SET shipper_id = ? WHERE order_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            if (shipperId == null) {
                ps.setNull(1, java.sql.Types.INTEGER); // Đặt giá trị NULL cho cột số nguyên
            } else {
                ps.setInt(1, shipperId);
            }
            ps.setInt(2, orderId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi gán shipper cho đơn hàng (ID: " + orderId + ", Shipper: " + shipperId + "): " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }
    }
    public List<User> getAllShippers() {
        List<User> shippers = new ArrayList<>();
        String sql = "SELECT User_ID, Username, Fullname, Email, phone, role FROM `user` WHERE role = 8"; // Giả định role_id = 3 cho Shipper

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User shipper = new User();
                shipper.setUserid(rs.getInt("User_ID"));
                shipper.setUsername(rs.getString("Username"));
                shipper.setFullname(rs.getString("Fullname"));
                shipper.setEmail(rs.getString("email"));   
                shipper.setPhone(rs.getString("Phone")); 
                shipper.setRole(rs.getInt("role"));
                shippers.add(shipper);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy danh sách shipper: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }
        return shippers;
    }
    public List<OrderStatus> getAllOrderStatuses() {
        List<OrderStatus> statuses = new ArrayList<>();
        String sql = "SELECT order_status_id, status_name FROM `order_status` ORDER BY order_status_id";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                OrderStatus status = new OrderStatus();
                status.setStatusId(rs.getInt("order_status_id"));
                status.setStatusName(rs.getString("status_name"));
                statuses.add(status);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy danh sách trạng thái đơn hàng: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }
        return statuses;
    }
    public static void main(String[] args) {
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("Không có đơn hàng nào được tìm thấy.");
        } else {
            System.out.println("Danh sách tất cả các đơn hàng:");
            for (Order order : orders) {
                System.out.println(order);
            }
        }

        // --- Kiểm tra phương thức getOrderDetailById ---
        System.out.println("\n--- Kiểm tra chi tiết đơn hàng (ID 1) ---");
        Order order1 = orderDAO.getOrderDetailById(1); // Thay 1 bằng ID đơn hàng có trong DB
        if (order1 != null) {
            System.out.println(order1);
        } else {
            System.out.println("Không tìm thấy đơn hàng ID 1.");
        }

        // --- Kiểm tra phương thức getAllShippers ---
        System.out.println("\n--- Danh sách Shipper ---");
        List<User> shippers = orderDAO.getAllShippers();
        if (shippers.isEmpty()) {
            System.out.println("Không tìm thấy shipper nào.");
        } else {
            for (User shipper : shippers) {
                System.out.println(shipper);
            }
        }

        // --- Kiểm tra phương thức getAllOrderStatuses ---
        System.out.println("\n--- Danh sách Trạng thái Đơn hàng ---");
        List<OrderStatus> statuses = orderDAO.getAllOrderStatuses();
        if (statuses.isEmpty()) {
            System.out.println("Không tìm thấy trạng thái đơn hàng nào.");
        } else {
            for (OrderStatus status : statuses) {
                System.out.println(status);
            }
        }

        
    }
}
