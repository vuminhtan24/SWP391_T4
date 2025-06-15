/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Order;
import model.User;
import model.OrderStatus;
import model.OrderDetail; 
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
        return searchOrders(null, null, 1, 10, "orderDate", "desc");
    }

    /**
     * Searches and filters orders by keyword and status, supporting pagination and dynamic sorting.
     *
     * @param keyword Search keyword.
     * @param statusId Order status ID.
     * @param pageIndex Current page number (starts from 1).
     * @param pageSize Number of records per page.
     * @param sortField Column name to sort by (e.g., "orderDate", "totalAmount", "customerName").
     * @param sortOrder Sort order ("asc" for ascending, "desc" for descending).
     * @return List of orders matching the criteria and pagination.
     */
    public List<Order> searchOrders(String keyword, Integer statusId, int pageIndex, int pageSize, String sortField, String sortOrder) {
        List<Order> listOrders = new ArrayList<>();
        
        StringBuilder countSql = new StringBuilder();
        countSql.append("SELECT COUNT(o.order_id) FROM `order` o ");
        countSql.append("JOIN `user` u ON o.customer_id = u.User_ID ");
        countSql.append("JOIN `order_status` os ON o.status_id = os.order_status_id ");
        countSql.append("LEFT JOIN `user` s ON o.shipper_id = s.User_ID ");
        countSql.append("WHERE 1=1 ");

        StringBuilder dataSql = new StringBuilder();
        dataSql.append("SELECT o.order_id, o.order_date, o.customer_id, u.Fullname AS customer_name, ");
        dataSql.append("o.total_amount, o.status_id, os.status_name, o.shipper_id, s.Fullname AS shipper_name ");
        dataSql.append("FROM `order` o ");
        dataSql.append("JOIN `user` u ON o.customer_id = u.User_ID ");
        dataSql.append("JOIN `order_status` os ON o.status_id = os.order_status_id ");
        dataSql.append("LEFT JOIN `user` s ON o.shipper_id = s.User_ID ");
        dataSql.append("WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
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
                orderByColumnName = "o.order_date"; 
        }

        if ("desc".equalsIgnoreCase(sortOrder)) {
            dataSql.append(" ORDER BY ").append(orderByColumnName).append(" DESC");
        } else {
            dataSql.append(" ORDER BY ").append(orderByColumnName).append(" ASC");
        }
        
        if (!orderByColumnName.equals("o.order_id")) {
             dataSql.append(", o.order_id DESC"); 
        }
        
        if (pageIndex > 0 && pageSize > 0) {
            dataSql.append(" LIMIT ? OFFSET ?");
        }

        try {
            connection = dbc.getConnection();

            PreparedStatement countPs = connection.prepareStatement(countSql.toString());
            for (int i = 0; i < params.size(); i++) {
                countPs.setObject(i + 1, params.get(i));
            }
            ResultSet countRs = countPs.executeQuery();
            if (countRs.next()) {
                this.noOfRecords = countRs.getInt(1); 
            }
            countRs.close();
            countPs.close();

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
            System.err.println("SQL Error while searching/filtering/paginating orders: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return listOrders;
    }

    /**
     * Retrieves details of a specific order by ID.
     *
     * @param orderId ID of the order to retrieve details for.
     * @return Order object containing detailed information, or null if not found.
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
            System.err.println("SQL Error while getting order details (ID: " + orderId + "): " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return order;
    }

    /**
     * Retrieves a list of items (products) in a specific order.
     * This method joins the `order_item` table with the `bouquet` table to get detailed product information.
     *
     * @param orderId ID of the order.
     * @return List of OrderDetail objects.
     */
    public List<OrderDetail> getOrderItemsByOrderId(int orderId) {
        List<OrderDetail> orderItems = new ArrayList<>();
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.bouquet_id, " +
                     "b.bouquet_name, b.image_url, oi.quantity, oi.unit_price " +
                     "FROM `order_item` oi " + 
                     "JOIN `bouquet` b ON oi.bouquet_id = b.bouquet_id " +
                     "WHERE oi.order_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                OrderDetail item = new OrderDetail(
                    rs.getInt("order_item_id"), 
                    rs.getInt("order_id"),
                    rs.getInt("bouquet_id"),
                    rs.getString("bouquet_name"),
                    rs.getString("image_url"), 
                    rs.getInt("quantity"),
                    rs.getString("unit_price") 
                );
                orderItems.add(item);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error while getting order items for order (ID: " + orderId + "): " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return orderItems;
    }

    /**
     * Updates information of an order (total amount, status, shipper).
     *
     * @param orderId ID of the order to update.
     * @param totalAmount New total amount.
     * @param statusId New status ID.
     * @param shipperId New shipper ID (can be null if not assigned).
     * @return true if update is successful, false otherwise.
     */
    public boolean updateOrder(int orderId, String totalAmount, int statusId, Integer shipperId) {
        String sql = "UPDATE `order` SET total_amount = ?, status_id = ?, shipper_id = ? WHERE order_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, totalAmount);
            ps.setInt(2, statusId);
            if (shipperId == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, shipperId);
            }
            ps.setInt(4, orderId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error while updating order (ID: " + orderId + "): " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    /**
     * Updates the status (status_id) of an order.
     *
     * @param orderId ID of the order to update.
     * @param newStatusId New status ID.
     * @return true if update is successful (rows affected > 0), false otherwise.
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
            System.err.println("SQL Error while updating order status (ID: " + orderId + ", Status: " + newStatusId + "): " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    /**
     * Assigns or unassigns a shipper to an order.
     *
     * @param orderId ID of the order.
     * @param shipperId ID of the shipper to assign. If null, the order will not have a shipper assigned.
     * @return true if update is successful, false otherwise.
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
            System.err.println("SQL Error while assigning shipper to order (ID: " + orderId + ", Shipper: " + shipperId + "): " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    /**
     * Deletes an order and its associated order items.
     * This method explicitly deletes from `order_item` first, then from `order`
     * to handle cases where ON DELETE CASCADE is not configured.
     *
     * @param orderId ID of the order to delete.
     * @return true if deletion is successful, false otherwise.
     */
    public boolean deleteOrder(int orderId) {
        PreparedStatement psOrderItem = null;
        PreparedStatement psOrder = null;
        try {
            connection = dbc.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // 1. Delete associated order items first
            String deleteOrderItemsSql = "DELETE FROM `order_item` WHERE order_id = ?";
            psOrderItem = connection.prepareStatement(deleteOrderItemsSql);
            psOrderItem.setInt(1, orderId);
            psOrderItem.executeUpdate(); // No need to check rowsAffected here, just ensure no error

            // 2. Then delete the order itself
            String deleteOrderSql = "DELETE FROM `order` WHERE order_id = ?";
            psOrder = connection.prepareStatement(deleteOrderSql);
            psOrder.setInt(1, orderId);
            int rowsAffected = psOrder.executeUpdate();

            connection.commit(); // Commit transaction if all successful
            return rowsAffected > 0;
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback on error
                }
            } catch (SQLException ex) {
                System.err.println("Error rolling back transaction: " + ex.getMessage());
            }
            System.err.println("SQL Error while deleting order (ID: " + orderId + "): " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (psOrderItem != null) psOrderItem.close();
                if (psOrder != null) psOrder.close();
                if (connection != null && !connection.getAutoCommit()) {
                    connection.setAutoCommit(true); // Reset auto-commit
                }
                this.closeResources(); // Close connection
            } catch (Exception e) {
                System.err.println("Error closing resources after delete operation: " + e.getMessage());
            }
        }
    }


    /**
     * Retrieves a list of all users with the 'Shipper' role.
     *
     * @return List of User objects representing shippers.
     */
    public List<User> getAllShippers() {
        List<User> shippers = new ArrayList<>();
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
                shipper.setEmail(rs.getString("Email"));   
                shipper.setPhone(rs.getString("Phone"));   
                shipper.setRole(rs.getInt("role"));
                shippers.add(shipper);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error while getting shipper list: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return shippers;
    }

    /**
     * Retrieves a list of all order statuses from the `order_status` table.
     *
     * @return List of OrderStatus objects.
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
            System.err.println("SQL Error while getting order status list: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return statuses;
    }

    public static void main(String[] args) {
        OrderDAO orderDAO = new OrderDAO();

        // Test deleteOrder (Example: Delete order with ID 999 - change to an existing ID for real test)
        System.out.println("\n--- Test Delete Order (ID: 3) ---");
        // IMPORTANT: Change 3 to an actual Order ID you want to delete for testing.
        // Make sure you have a test order that you can safely delete.
        boolean deleted = orderDAO.deleteOrder(3); 
        if (deleted) {
            System.out.println("Order ID 3 deleted successfully.");
        } else {
            System.out.println("Order ID 3 deletion failed or order not found.");
        }

        // Test getOrderItemsByOrderId
        System.out.println("\n--- Test Get Order Items for Order ID 1 ---");
        List<OrderDetail> items = orderDAO.getOrderItemsByOrderId(1);
        if (items.isEmpty()) {
            System.out.println("No products found for order ID 1.");
        } else {
            items.forEach(System.out::println);
        }

        // Test updateOrder
        System.out.println("\n--- Test Update Order (Order ID: 1) ---");
        boolean updated = orderDAO.updateOrder(1, "150000.00", 2, 802); 
        if (updated) {
            System.out.println("Order ID 1 updated successfully.");
            Order updatedOrder = orderDAO.getOrderDetailById(1);
            if (updatedOrder != null) {
                System.out.println("Order information after update: " + updatedOrder);
            }
        } else {
            System.out.println("Order ID 1 update failed.");
        }

        // Test searchOrders with pagination (e.g., page 1, size 2)
        System.out.println("\n--- Orders (Page 1, Size 2, Sorted by Date DESC) ---");
        List<Order> paginatedOrders = orderDAO.searchOrders(null, null, 1, 2, "orderDate", "desc");
        if (paginatedOrders.isEmpty()) {
            System.out.println("No orders found for pagination test.");
        } else {
            paginatedOrders.forEach(System.out::println);
            System.out.println("Total records: " + orderDAO.getNoOfRecords());
        }
        
        // Original tests (kept for reference)
        System.out.println("\n--- Check order details (ID 1) ---");
        Order order1 = orderDAO.getOrderDetailById(1);
        if (order1 != null) {
            System.out.println(order1);
        } else {
            System.out.println("Order ID 1 not found.");
        }

        System.out.println("\n--- Shipper List ---");
        List<User> shippers = orderDAO.getAllShippers();
        if (shippers.isEmpty()) {
            System.out.println("No shippers found.");
        } else {
            for (User shipper : shippers) {
                System.out.println(shipper);
            }
        }

        System.out.println("\n--- Order Status List ---");
        List<OrderStatus> statuses = orderDAO.getAllOrderStatuses();
        if (statuses.isEmpty()) {
            System.out.println("No order statuses found.");
        } else {
            for (OrderStatus status : statuses) {
                System.out.println(status);
            }
        }
    }
}
