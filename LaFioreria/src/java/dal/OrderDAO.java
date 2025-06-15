/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Order;
import model.User;
import model.OrderStatus;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Order related operations.
 * Handles database interactions for Order, User (Shipper), and OrderStatus entities.
 * @author VU MINH TAN
 */
public class OrderDAO extends BaseDao {

    private int noOfRecords;

    public int getNoOfRecords() {
        return noOfRecords;
    }

    public List<Order> getAllOrders() {
        return searchOrders(null, null, 1, 10, "orderDate", "desc"); // Đã sửa sortField mặc định là "orderDate"
    }

    public List<Order> searchOrders(String keyword, Integer statusId, int pageIndex, int pageSize, String sortField, String sortOrder) {
        List<Order> listOrders = new ArrayList<>();
        
        // 1. Xây dựng câu truy vấn SQL để đếm tổng số bản ghi
        StringBuilder countSql = new StringBuilder();
        countSql.append("SELECT COUNT(o.order_id) FROM `order` o ");
        countSql.append("JOIN `user` u ON o.customer_id = u.User_ID ");
        countSql.append("JOIN `order_status` os ON o.status_id = os.order_status_id ");
        countSql.append("LEFT JOIN `user` s ON o.shipper_id = s.User_ID ");
        countSql.append("WHERE 1=1 ");

        // 2. Xây dựng câu truy vấn SQL để lấy dữ liệu có phân trang
        StringBuilder dataSql = new StringBuilder();
        dataSql.append("SELECT o.order_id, o.order_date, o.customer_id, u.Fullname AS customer_name, ");
        dataSql.append("o.total_amount, o.status_id, os.status_name, o.shipper_id, s.Fullname AS shipper_name ");
        dataSql.append("FROM `order` o ");
        dataSql.append("JOIN `user` u ON o.customer_id = u.User_ID ");
        dataSql.append("JOIN `order_status` os ON o.status_id = os.order_status_id ");
        dataSql.append("LEFT JOIN `user` s ON o.shipper_id = s.User_ID ");
        dataSql.append("WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        // Thêm điều kiện tìm kiếm và lọc vào cả hai câu truy vấn
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Cẩn thận với kiểu dữ liệu khi dùng LIKE. order_id và total_amount có thể cần CAST sang CHAR.
            // MySQL's LIKE operator implicitly converts numbers to strings for comparison.
            // Casting explicitly ensures consistent behavior across different DBs/versions.
            String searchCondition = "AND (CAST(o.order_id AS CHAR) LIKE ? OR u.Fullname LIKE ? OR CAST(o.total_amount AS CHAR) LIKE ?) ";
            countSql.append(searchCondition);
            dataSql.append(searchCondition);
            String likeKeyword = "%" + keyword + "%";
            params.add(likeKeyword);
            params.add(likeKeyword);
            params.add(likeKeyword);
        }

        if (statusId != null && statusId != 0) {
            String statusCondition = "AND o.status_id = ? ";
            countSql.append(statusCondition);
            dataSql.append(statusCondition);
            params.add(statusId);
        }

        // Xử lý sắp xếp động
        String orderByColumnName = "";
        switch (sortField) {
            case "orderId":
                orderByColumnName = "o.order_id";
                break;
            case "orderDate":
                orderByColumnName = "o.order_date";
                break;
            case "customerName":
                orderByColumnName = "u.Fullname"; 
                break;
            case "totalAmount":
                orderByColumnName = "o.total_amount";
                break;
            case "statusName":
                orderByColumnName = "os.status_name"; 
                break;
            case "shipperName":
                orderByColumnName = "s.Fullname"; 
                break;
            default:
                orderByColumnName = "o.order_date"; // Mặc định sắp xếp theo ngày đặt hàng
        }

        // Kiểm tra thứ tự sắp xếp (asc/desc)
        if ("desc".equalsIgnoreCase(sortOrder)) {
            dataSql.append(" ORDER BY ").append(orderByColumnName).append(" DESC");
        } else {
            dataSql.append(" ORDER BY ").append(orderByColumnName).append(" ASC");
        }
        
        // Thêm sắp xếp phụ theo order_id để đảm bảo thứ tự ổn định khi các giá trị của cột chính trùng nhau
        if (!orderByColumnName.equals("o.order_id")) {
             dataSql.append(", o.order_id DESC"); // Thêm ID để ổn định sắp xếp
        }
        

        // Thêm LIMIT và OFFSET cho câu truy vấn dữ liệu nếu pageIndex và pageSize hợp lệ
        if (pageIndex > 0 && pageSize > 0) {
            dataSql.append(" LIMIT ? OFFSET ?");
        }

        try {
            connection = dbc.getConnection();

            // Thực thi câu truy vấn đếm tổng số bản ghi trước
            PreparedStatement countPs = connection.prepareStatement(countSql.toString());
            for (int i = 0; i < params.size(); i++) {
                countPs.setObject(i + 1, params.get(i));
            }
            ResultSet countRs = countPs.executeQuery();
            if (countRs.next()) {
                this.noOfRecords = countRs.getInt(1); // Lấy tổng số bản ghi
            }
            countRs.close();
            countPs.close();

            // Thực thi câu truy vấn lấy dữ liệu có phân trang
            ps = connection.prepareStatement(dataSql.toString());
            int paramIndex = 1;
            for (Object param : params) {
                ps.setObject(paramIndex++, param);
            }
            if (pageIndex > 0 && pageSize > 0) {
                ps.setInt(paramIndex++, pageSize);
                ps.setInt(paramIndex++, (pageIndex - 1) * pageSize);
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                listOrders.add(new Order(
                        rs.getInt("order_id"),
                        rs.getString("order_date") != null ? rs.getString("order_date").trim() : null,
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("total_amount") != null ? rs.getString("total_amount").trim() : null,
                        rs.getInt("status_id"),
                        rs.getString("status_name"),
                        rs.getObject("shipper_id") != null ? rs.getInt("shipper_id") : null,
                        rs.getString("shipper_name")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi tìm kiếm/lọc/phân trang đơn hàng: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }
        return listOrders;
    }


    /**
     * Lấy chi tiết một đơn hàng cụ thể theo ID, bao gồm tên khách hàng, tên trạng thái,
     * và thông tin shipper (nếu có).
     *
     * @param orderId ID của đơn hàng cần lấy chi tiết.
     * @return Đối tượng Order chứa thông tin chi tiết, hoặc null nếu không tìm thấy.
     */
    public Order getOrderDetailById(int orderId) {
        Order order = null;
        String sql = "SELECT o.order_id, o.order_date, o.customer_id, u.Fullname AS customer_name, " +
                     "o.total_amount, o.status_id, os.status_name, o.shipper_id, s.Fullname AS shipper_name " +
                     "FROM `order` o " +
                     "JOIN `user` u ON o.customer_id = u.User_ID " +
                     "JOIN `order_status` os ON o.status_id = os.order_status_id " +
                     "LEFT JOIN `user` s ON o.shipper_id = s.User_ID " +
                     "WHERE o.order_id = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
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

    /**
     * Cập nhật trạng thái (status_id) của một đơn hàng.
     *
     * @param orderId ID của đơn hàng cần cập nhật.
     * @param newStatusId ID trạng thái mới.
     * @return true nếu cập nhật thành công (số hàng bị ảnh hưởng > 0), false nếu ngược lại.
     */
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

    /**
     * Gán hoặc hủy gán shipper cho một đơn hàng.
     *
     * @param orderId ID của đơn hàng.
     * @param shipperId ID của shipper cần gán. Nếu là null, đơn hàng sẽ không được gán shipper.
     * @return true nếu cập nhật thành công, false nếu ngược lại.
     */
    public boolean assignShipper(int orderId, Integer shipperId) {
        String sql = "UPDATE `order` SET shipper_id = ? WHERE order_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            if (shipperId == null) {
                ps.setNull(1, java.sql.Types.INTEGER);
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

    /**
     * Lấy danh sách tất cả các người dùng có vai trò là 'Shipper'.
     * Yêu cầu bảng `user` có cột `role` và `Username` để xác định vai trò và tên đăng nhập.
     *
     * @return Danh sách các đối tượng User đại diện cho shipper.
     */
    public List<User> getAllShippers() {
        List<User> shippers = new ArrayList<>();
        // Giữ nguyên role = 8 và email/phone casing như bạn đã cung cấp
        String sql = "SELECT User_ID, Username, Fullname, Email, Phone, role FROM `user` WHERE role = 8"; 

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User shipper = new User();
                shipper.setUserid(rs.getInt("User_ID"));
                shipper.setUsername(rs.getString("Username"));
                shipper.setFullname(rs.getString("Fullname"));
                // Giữ nguyên casing cho email và phone như bạn đã cung cấp
                shipper.setEmail(rs.getString("Email"));   
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

    /**
     * Lấy danh sách tất cả các trạng thái đơn hàng từ bảng `order_status`.
     *
     * @return Danh sách các đối tượng OrderStatus.
     */
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

        // Test searchOrders with pagination (e.g., page 1, size 2)
        System.out.println("\n--- Orders (Page 1, Size 2, Sorted by Date DESC) ---");
        List<Order> paginatedOrders = orderDAO.searchOrders(null, null, 1, 2, "orderDate", "desc");
        if (paginatedOrders.isEmpty()) {
            System.out.println("No orders found for pagination test.");
        } else {
            paginatedOrders.forEach(System.out::println);
            System.out.println("Total records: " + orderDAO.getNoOfRecords());
        }

        // Test searchOrders with keyword and pagination, sorted by customer name ASC
        System.out.println("\n--- Search Orders (keyword 'Grace', Page 1, Size 1, Sorted by Customer Name ASC) ---");
        List<Order> searchedPaginatedOrders = orderDAO.searchOrders("Grace", null, 1, 1, "customerName", "asc");
        if (searchedPaginatedOrders.isEmpty()) {
            System.out.println("No orders found for keyword 'Grace' with pagination.");
        } else {
            searchedPaginatedOrders.forEach(System.out::println);
            System.out.println("Total records for 'Grace': " + orderDAO.getNoOfRecords());
        }

        // Test searchOrders with statusId and pagination, sorted by total amount DESC
        System.out.println("\n--- Filter Orders (Status ID 1, Page 1, Size 2, Sorted by Total Amount DESC) ---");
        List<Order> filteredPaginatedOrders = orderDAO.searchOrders(null, 1, 1, 2, "totalAmount", "desc");
        if (filteredPaginatedOrders.isEmpty()) {
            System.out.println("No orders found for Status ID 1 with pagination.");
        } else {
            filteredPaginatedOrders.forEach(System.out::println);
            System.out.println("Total records for Status ID 1: " + orderDAO.getNoOfRecords());
        }

        // Original tests (kept for reference)
        System.out.println("\n--- Kiểm tra chi tiết đơn hàng (ID 1) ---");
        Order order1 = orderDAO.getOrderDetailById(1);
        if (order1 != null) {
            System.out.println(order1);
        } else {
            System.out.println("Không tìm thấy đơn hàng ID 1.");
        }

        System.out.println("\n--- Danh sách Shipper ---");
        List<User> shippers = orderDAO.getAllShippers();
        if (shippers.isEmpty()) {
            System.out.println("Không tìm thấy shipper nào.");
        } else {
            for (User shipper : shippers) {
                System.out.println(shipper);
            }
        }

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
