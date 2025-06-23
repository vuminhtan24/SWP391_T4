package dal;

import model.Order;
import model.User;
import model.OrderStatus;
import model.OrderDetail;
import model.Bouquet; // Import Bouquet model
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.OrderStatusCount;

/**
 * Data Access Object (DAO) for Order related operations. Handles database
 * interactions for Order, User (Shipper), and OrderStatus entities.
 *
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
     * Searches and filters orders by keyword and status, supporting pagination
     * and dynamic sorting.
     *
     * @param keyword Search keyword.
     * @param statusId Order status ID.
     * @param pageIndex Current page number (starts from 1).
     * @param pageSize Number of records per page.
     * @param sortField Column name to sort by (e.g., "orderDate",
     * "totalAmount", "customerName").
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
                        null, // Customer Phone not available in this query, setting to null
                        null, // Customer Address not available in this query, setting to null
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
     * @return Order object containing detailed information, or null if not
     * found.
     */
    public Order getOrderDetailById(int orderId) {
        Order order = null;
        String sql = "SELECT o.order_id, o.order_date, o.customer_id, u.Fullname AS customer_name, u.Phone AS customer_phone, u.Address AS customer_address, "
                + "o.total_amount, o.status_id, os.status_name, o.shipper_id, s.Fullname AS shipper_name "
                + "FROM `order` o "
                + "JOIN `user` u ON o.customer_id = u.User_ID "
                + "JOIN `order_status` os ON o.status_id = os.order_status_id "
                + "LEFT JOIN `user` s ON o.shipper_id = s.User_ID "
                + "WHERE o.order_id = ?";

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
                        rs.getString("customer_phone"), // Read customer phone
                        rs.getString("customer_address"), // Read customer address
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
     * Retrieves a list of items (products) in a specific order. This method
     * joins the `order_item` table with the `bouquet` table to get detailed
     * product information.
     *
     * @param orderId ID of the order.
     * @return List of OrderDetail objects.
     */
    public List<OrderDetail> getOrderItemsByOrderId(int orderId) {
        List<OrderDetail> orderItems = new ArrayList<>();
        // Modified SQL to join with bouquet_images to get image_url
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.bouquet_id, "
                + "b.bouquet_name, bi.image_url, oi.quantity, oi.unit_price " // Changed from b.image_url to bi.image_url
                + "FROM `order_item` oi "
                + "JOIN `bouquet` b ON oi.bouquet_id = b.bouquet_id "
                + "LEFT JOIN `bouquet_images` bi ON b.Bouquet_ID = bi.Bouquet_ID " // Added LEFT JOIN for bouquet_images
                + "WHERE oi.order_id = ?";
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
                        rs.getString("image_url"), // Map the image_url from bouquet_images
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

    public List<OrderStatusCount> getOrderStatusCounts() {
        List<OrderStatusCount> list = new ArrayList<>();
        String sql = "SELECT os.status_name, COUNT(*) AS total "
                + "FROM `order` o "
                + "JOIN order_status os ON o.status_id = os.order_status_id "
                + "GROUP BY os.status_name";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String status = rs.getString("status_name");
                int count = rs.getInt("total");
                list.add(new OrderStatusCount(status, count));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Updates the status (status_id) of an order.
     *
     * @param orderId ID of the order to update.
     * @param newStatusId New status ID.
     * @return true if update is successful (rows affected > 0), false
     * otherwise.
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
     * @param shipperId ID of the shipper to assign. If null, the order will not
     * have a shipper assigned.
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
     * Deletes an order and its associated order items. This method explicitly
     * deletes from `order_item` first, then from `order` to handle cases where
     * ON DELETE CASCADE is not configured.
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
                if (psOrderItem != null) {
                    psOrderItem.close();
                }
                if (psOrder != null) {
                    psOrder.close();
                }
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
     * Adds a new order to the database.
     *
     * @param order The Order object containing the details for the new order.
     * @return The generated order_id if successful, -1 otherwise.
     */
    public int addOrder(Order order) {
        // Updated SQL to include customer_phone and customer_address
        String sql = "INSERT INTO `order` (order_date, customer_id, total_amount, status_id, shipper_id) VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1;
        try {
            connection = dbc.getConnection();
            // Use Statement.RETURN_GENERATED_KEYS to get the auto-incremented ID
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getOrderDate());
            ps.setInt(2, order.getCustomerId());
            ps.setString(3, order.getTotalAmount()); // Store as String if DB column is VARCHAR/DECIMAL
            ps.setInt(4, order.getStatusId());
            if (order.getShipperId() == null) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, order.getShipperId());
            }

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1); // Get the auto-generated ID
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error while adding new order: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources after add operation: " + e.getMessage());
            }
        }
        return generatedId;
    }

    /**
     * Adds a new order item (bouquet) to a specific order in the database. This
     * method is called after the main order is created.
     *
     * @param orderItem The OrderDetail object containing the bouquetId,
     * quantity, and unitPrice. The orderId should already be set in this
     * object.
     * @return true if the order item is added successfully, false otherwise.
     */
    public boolean addOrderItem(OrderDetail orderItem) {
        String sql = "INSERT INTO `order_item` (order_id, bouquet_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderItem.getOrderId());
            ps.setInt(2, orderItem.getBouquetId());
            ps.setInt(3, orderItem.getQuantity());
            ps.setString(4, orderItem.getUnitPrice()); // Ensure unitPrice is handled as String/VARCHAR
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error while adding order item: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources after adding order item: " + e.getMessage());
            }
        }
    }

    /**
     * Updates the total amount of an existing order. This can be called after
     * adding/removing order items.
     *
     * @param orderId The ID of the order to update.
     * @param newTotalAmount The new total amount as a String.
     * @return true if the update is successful, false otherwise.
     */
    public boolean updateTotalAmount(int orderId, String newTotalAmount) {
        String sql = "UPDATE `order` SET total_amount = ? WHERE order_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, newTotalAmount);
            ps.setInt(2, orderId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error while updating total amount for order (ID: " + orderId + "): " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources after updating total amount: " + e.getMessage());
            }
        }
    }

    /**
     * Retrieves a list of orders assigned to a specific shipper and optionally
     * filtered by statuses. This method is specifically for the shipper's
     * dashboard to view their assigned orders.
     *
     * @param shipperId The User_ID of the shipper.
     * @param statusIds A list of order status IDs to filter by. If null or
     * empty, no status filter is applied.
     * @return List of Order objects.
     */
    public List<Order> getOrdersByShipperIdAndStatuses(int shipperId, List<Integer> statusIds) {
        List<Order> orders = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o.order_id, o.order_date, o.customer_id, u.Fullname AS customer_name, ");
        sql.append("u.Phone AS customer_phone, u.Address AS customer_address, "); // ADDED: Select customer address
        sql.append("o.total_amount, o.status_id, os.status_name, o.shipper_id, s.Fullname AS shipper_name ");
        sql.append("FROM `order` o ");
        sql.append("JOIN `user` u ON o.customer_id = u.User_ID ");
        sql.append("JOIN `order_status` os ON o.status_id = os.order_status_id ");
        sql.append("LEFT JOIN `user` s ON o.shipper_id = s.User_ID ");
        sql.append("WHERE o.shipper_id = ? "); // Primary filter for shipper ID

        // Dynamically add placeholders for status IDs if provided
        if (statusIds != null && !statusIds.isEmpty()) {
            sql.append("AND o.status_id IN (");
            for (int i = 0; i < statusIds.size(); i++) {
                sql.append("?");
                if (i < statusIds.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(") ");
        }
        sql.append("ORDER BY o.order_date DESC;"); // Order by date descending, thêm dấu chấm phẩy

        // --- DEBUG LOG START ---
        System.out.println("DEBUG (OrderDAO): Executing SQL Query: " + sql.toString());
        System.out.println("DEBUG (OrderDAO): Parameters -> shipperId: " + shipperId + ", statusIds: " + statusIds);
        // --- DEBUG LOG END ---

        try {
            connection = dbc.getConnection();
            if (connection == null) {
                System.err.println("ERROR (OrderDAO): Database connection is NULL.");
                return orders; // Return empty list if no connection
            }

            ps = connection.prepareStatement(sql.toString());
            int paramIndex = 1;
            ps.setInt(paramIndex++, shipperId); // Set the shipperId parameter first

            // Set dynamic statusId parameters
            if (statusIds != null && !statusIds.isEmpty()) {
                for (Integer statusId : statusIds) {
                    ps.setInt(paramIndex++, statusId);
                }
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                // Đọc shipper_id và shipper_name an toàn với nullable
                Integer retrievedShipperId = (Integer) rs.getObject("shipper_id");
                String retrievedShipperName = rs.getString("shipper_name");

                orders.add(new Order(
                        rs.getInt("order_id"),
                        rs.getString("order_date") != null ? rs.getString("order_date").trim() : null,
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_phone"), // Read customer phone
                        rs.getString("customer_address"), // ADDED: Read customer address
                        rs.getString("total_amount"),
                        rs.getInt("status_id"),
                        rs.getString("status_name"),
                        retrievedShipperId,
                        retrievedShipperName
                ));
            }
            // --- DEBUG LOG START ---
            System.out.println("DEBUG (OrderDAO): Successfully retrieved " + orders.size() + " orders for shipper " + shipperId);
            // --- DEBUG LOG END ---
        } catch (SQLException e) {
            System.err.println("ERROR (OrderDAO): SQL Error while getting orders for shipper (ID: " + shipperId + "): " + e.getMessage());
            e.printStackTrace(); // Print full stack trace for detailed debugging
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return orders;
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

    /**
     * Retrieves a list of all users with the 'Customer' role. This is useful
     * for assigning customers to new orders.
     *
     * @return List of User objects representing customers.
     */
    public List<User> getAllCustomers() {
        List<User> customers = new ArrayList<>();
        String sql = "SELECT User_ID, Username, Fullname, Email, Phone, Address, role FROM `user` WHERE role = 7"; // Added Address

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User customer = new User();
                customer.setUserid(rs.getInt("User_ID"));
                customer.setUsername(rs.getString("Username"));
                customer.setFullname(rs.getString("Fullname"));
                customer.setEmail(rs.getString("Email"));
                customer.setPhone(rs.getString("Phone"));
                customer.setAddress(rs.getString("Address")); // Set Address
                customer.setRole(rs.getInt("role"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error while getting customer list: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return customers;
    }

    /**
     * Retrieves a list of all bouquets. Used for populating product selection
     * dropdowns.
     *
     * @return List of Bouquet objects.
     */
    public List<Bouquet> getAllBouquets() {
        List<Bouquet> bouquets = new ArrayList<>();
        // Modified SQL query to select 'price' instead of 'current_price'
        // Using LEFT JOIN to ensure all bouquets are returned even if they don't have an image in bouquet_images
        // Adding MIN(bi.image_url) to resolve "only_full_group_by" issue and get one image_url per bouquet
        String sql = "SELECT\n"
                + "    b.Bouquet_ID,\n"
                + "    b.Bouquet_Name,\n"
                + "    MIN(bi.image_url) AS image_url,\n" // Using MIN() to aggregate image_url
                + "    b.Price,\n"
                + "    b.Description,\n"
                + "    b.CID\n"
                + "FROM\n"
                + "    bouquet AS b\n"
                + "LEFT JOIN\n"
                + "    bouquet_images AS bi ON b.Bouquet_ID = bi.Bouquet_ID\n"
                + "GROUP BY b.Bouquet_ID, b.Bouquet_Name, b.Price, b.Description, b.CID;"; // Include all non-aggregated columns in GROUP BY
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Bouquet bouquet = new Bouquet();
                bouquet.setBouquetId(rs.getInt("bouquet_id"));
                bouquet.setBouquetName(rs.getString("bouquet_name"));
                bouquet.setPrice(rs.getInt("price"));
                bouquet.setDescription(rs.getString("Description"));
                bouquet.setCid(rs.getInt("cid"));
                bouquets.add(bouquet);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error while getting bouquet list: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return bouquets;
    }

    public boolean markDelivered(int orderId, String deliveryConfirmationImagePath) {
        boolean updated = false;
        String sql = "UPDATE `order` SET status_id = ?, delivery_confirmation_image_path = ? WHERE order_id = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, 4); // Giả sử 4 là status "Đã giao hàng"
            ps.setString(2, deliveryConfirmationImagePath);
            ps.setInt(3, orderId);

            int rowsAffected = ps.executeUpdate();
            updated = (rowsAffected > 0);

        } catch (SQLException e) {
            System.err.println("SQL Error while updating delivery status: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return updated;
    }

    public boolean rejectOrder(int orderId, String reason, String cancellationImagePath) {
        boolean updated = false;
        String sql = "UPDATE `order` SET status_id = ?, cancellation_reason = ?, cancellation_image_path = ? WHERE order_id = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, 5); // Giả sử 5 là mã trạng thái 'Đã hủy' hoặc 'Từ chối giao hàng'
            ps.setString(2, reason); // Gán giá trị cho cột lý do
            ps.setString(3, cancellationImagePath); // Gán giá trị cho cột ảnh
            ps.setInt(4, orderId);

            int rowsAffected = ps.executeUpdate();
            updated = (rowsAffected > 0);

        } catch (SQLException e) {
            System.err.println("SQL Error while canceling order: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return updated;
    }

    public static void main(String[] args) {
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.getAllBouquets();
    }
}
