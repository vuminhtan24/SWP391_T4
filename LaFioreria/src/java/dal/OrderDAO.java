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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.OrderItem;
import model.OrderStatusCount;
import model.RequestDisplay;
import model.RequestFlower;
import model.WholesaleOrderDetail;

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
        List<Object> params = new ArrayList<>();

        // Base SELECT query
        String baseSelect = """
        SELECT o.order_id, o.order_date, o.customer_id,
               COALESCE(u.Fullname, 'Guest') AS customer_name,
               o.total_import, o.total_sell, o.status_id, os.status_name,
               o.shipper_id, s.Fullname AS shipper_name,
               o.payment_method, o.type
        FROM `order` o
        LEFT JOIN `user` u ON o.customer_id = u.User_ID
        JOIN `order_status` os ON o.status_id = os.order_status_id
        LEFT JOIN `user` s ON o.shipper_id = s.User_ID
        WHERE 1=1
    """;

        // Base COUNT query
        String baseCount = """
        SELECT COUNT(o.order_id)
        FROM `order` o
        LEFT JOIN `user` u ON o.customer_id = u.User_ID
        JOIN `order_status` os ON o.status_id = os.order_status_id
        LEFT JOIN `user` s ON o.shipper_id = s.User_ID
        WHERE 1=1
    """;

        // Build WHERE clause
        StringBuilder whereClause = new StringBuilder();

        if (keyword != null && !keyword.trim().isEmpty()) {
            whereClause.append(" AND (CAST(o.order_id AS CHAR) LIKE ? OR u.Fullname LIKE ?)");
            String likeKeyword = "%" + keyword.trim() + "%";
            params.add(likeKeyword);
            params.add(likeKeyword);
        }

        if (statusId != null && statusId != 0) {
            whereClause.append(" AND o.status_id = ?");
            params.add(statusId);
        }

        // Determine ORDER BY clause
        String orderByColumn = switch (sortField) {
            case "orderId" ->
                "o.order_id";
            case "orderDate" ->
                "o.order_date";
            case "customerName" ->
                "u.Fullname";
            case "totalSell" ->
                "o.total_sell";
            case "statusName" ->
                "os.status_name";
            case "shipperName" ->
                "s.Fullname";
            default ->
                "o.order_date";
        };

        String orderClause = " ORDER BY " + orderByColumn
                + ("desc".equalsIgnoreCase(sortOrder) ? " DESC" : " ASC")
                + (orderByColumn.equals("o.order_id") ? "" : ", o.order_id DESC");

        // Add LIMIT
        String limitClause = "";
        if (pageIndex > 0 && pageSize > 0) {
            limitClause = " LIMIT ? OFFSET ?";
        }

        // Final SQLs
        String countSql = baseCount + whereClause;
        String dataSql = baseSelect + whereClause + orderClause + limitClause;

        try {
            connection = dbc.getConnection();

            // COUNT query
            try (PreparedStatement countPs = connection.prepareStatement(countSql)) {
                setParams(countPs, params);
                try (ResultSet countRs = countPs.executeQuery()) {
                    if (countRs.next()) {
                        this.noOfRecords = countRs.getInt(1);
                    }
                }
            }

            // DATA query
            try (PreparedStatement psData = connection.prepareStatement(dataSql)) {
                int paramIndex = setParams(psData, params);
                if (pageIndex > 0 && pageSize > 0) {
                    psData.setInt(paramIndex++, pageSize);
                    psData.setInt(paramIndex, (pageIndex - 1) * pageSize);
                }

                System.out.println("[DEBUG] Executing dataSql: " + dataSql);
                System.out.println("[DEBUG] Params: " + params);

                try (ResultSet rs = psData.executeQuery()) {
                    while (rs.next()) {
                        listOrders.add(new Order(
                                rs.getInt("order_id"),
                                rs.getString("order_date") != null ? rs.getString("order_date").trim() : null,
                                rs.getObject("customer_id") != null ? rs.getInt("customer_id") : -1,
                                rs.getString("customer_name"),
                                null, null,
                                rs.getString("total_sell"),
                                rs.getString("total_import"),
                                rs.getInt("status_id"),
                                rs.getString("status_name"),
                                rs.getObject("shipper_id") != null ? rs.getInt("shipper_id") : null,
                                rs.getString("shipper_name"),
                                rs.getString("payment_method"),
                                rs.getString("type")
                        ));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] searchOrders SQL → " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("[WARN] Error closing resources: " + e.getMessage());
            }
        }

        return listOrders;
    }

    private int setParams(PreparedStatement ps, List<Object> params) throws SQLException {
        int index = 1;
        for (Object param : params) {
            ps.setObject(index++, param);
        }
        return index;
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
        String sql = "SELECT o.order_id, o.order_date, o.customer_id, "
                + "COALESCE(u.Fullname, o.customer_name) AS customer_name, "
                + "COALESCE(u.Phone, o.customer_phone) AS customer_phone, "
                + "COALESCE(u.Address, o.customer_address) AS customer_address, "
                + "o.total_sell, o.total_import, "
                + "o.status_id, os.status_name, "
                + "o.shipper_id, s.Fullname AS shipper_name, o.payment_method, o.type "
                + "FROM `order` o "
                + "LEFT JOIN `user` u ON o.customer_id = u.User_ID "
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
                        rs.getString("order_date"),
                        rs.getObject("customer_id") != null ? rs.getInt("customer_id") : null,
                        rs.getString("customer_name"),
                        rs.getString("customer_phone"),
                        rs.getString("customer_address"),
                        rs.getString("total_sell"),
                        rs.getString("total_import"),
                        rs.getInt("status_id"),
                        rs.getString("status_name"),
                        rs.getObject("shipper_id") != null ? rs.getInt("shipper_id") : null,
                        rs.getString("shipper_name"),
                        rs.getString("payment_method"),
                        rs.getString("type")
                );
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getOrderDetailById: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources in getOrderDetailById: " + e.getMessage());
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
        String sql = "SELECT \n"
                + "    oi.order_item_id,\n"
                + "    oi.order_id,\n"
                + "    oi.bouquet_id,\n"
                + "    b.bouquet_name,\n"
                + "    (\n"
                + "      SELECT bi2.image_url\n"
                + "      FROM bouquet_images bi2\n"
                + "      WHERE bi2.bouquet_id = oi.bouquet_id\n"
                + "      ORDER BY bi2.image_url ASC\n"
                + "      LIMIT 1\n"
                + "    ) AS image_url,\n"
                + "    oi.quantity,\n"
                + "    oi.unit_price,\n"
                + "    oi.sellPrice,\n"
                + "    oi.status\n"
                + "FROM order_item oi\n"
                + "JOIN bouquet b \n"
                + "  ON oi.bouquet_id = b.bouquet_id\n"
                + "WHERE oi.order_id = ?;";
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
                        rs.getString("unit_price"),
                        rs.getInt("sellPrice"),
                        rs.getString("status")
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
    public boolean updateOrder(int orderId, String total_import, String total_sell, int statusId, Integer shipperId) {
        String sql = "UPDATE `order` SET total_import = ?, total_sell = ?, status_id = ?, shipper_id = ? WHERE order_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, total_import);
            ps.setString(2, total_sell);
            ps.setInt(3, statusId);
            if (shipperId == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, shipperId);
            }
            ps.setInt(5, orderId);
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
        PreparedStatement psPayment = null;
        PreparedStatement psShipment = null;
        PreparedStatement psOrder = null;

        try {
            connection = dbc.getConnection();
            connection.setAutoCommit(false); // Bắt đầu transaction

            // 1. Xóa từ bảng order_item
            String deleteOrderItemsSql = "DELETE FROM `order_item` WHERE order_id = ?";
            psOrderItem = connection.prepareStatement(deleteOrderItemsSql);
            psOrderItem.setInt(1, orderId);
            psOrderItem.executeUpdate();

            // 2. Xóa từ bảng payment
            String deletePaymentSql = "DELETE FROM `payment` WHERE order_id = ?";
            psPayment = connection.prepareStatement(deletePaymentSql);
            psPayment.setInt(1, orderId);
            psPayment.executeUpdate();

            // 3. Xóa từ bảng shipment
            String deleteShipmentSql = "DELETE FROM `shipment` WHERE order_id = ?";
            psShipment = connection.prepareStatement(deleteShipmentSql);
            psShipment.setInt(1, orderId);
            psShipment.executeUpdate();

            // 4. Xóa từ bảng order
            String deleteOrderSql = "DELETE FROM `order` WHERE order_id = ?";
            psOrder = connection.prepareStatement(deleteOrderSql);
            psOrder.setInt(1, orderId);
            int rowsAffected = psOrder.executeUpdate();

            connection.commit(); // Thành công, commit transaction
            return rowsAffected > 0;

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback nếu có lỗi
                }
            } catch (SQLException ex) {
                System.err.println("Error rolling back transaction: " + ex.getMessage());
            }
            System.err.println("SQL Error while deleting order (ID: " + orderId + "): " + e.getMessage());
            return false;

        } finally {
            try {
                if (psOrderItem != null) {
                    psOrderItem.close();
                }
                if (psPayment != null) {
                    psPayment.close();
                }
                if (psShipment != null) {
                    psShipment.close();
                }
                if (psOrder != null) {
                    psOrder.close();
                }
                if (connection != null && !connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }
                this.closeResources(); // Đóng connection
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
//    Nếu payment là COD thì chuyển thẳng đến status là processing (đang cắm hoa)
//    còn nếu là VietQR thì sẽ status = 1 sẽ chờ thanh toán rồi mới đến bước processing
    public int addOrder(Order order) {
        String sql = "INSERT INTO `order` (order_date, customer_id, total_import, total_sell, status_id, shipper_id, payment_method) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int totalImport = Integer.parseInt(order.getTotalImport());
            int totalSell = totalImport * 5; // Giữ logic tính total_sell như cũ

            // Xác định status_id dựa trên payment_method
            int statusId = "VietQR".equals(order.getPaymentMethod()) ? 1 : 2; // 1: Chờ thanh toán, 2: Chờ lấy hàng

            ps.setString(1, order.getOrderDate());
            ps.setInt(2, order.getCustomerId());
            ps.setInt(3, totalImport);
            ps.setInt(4, totalSell);
            ps.setInt(5, statusId); // Dùng statusId tự động
            if (order.getShipperId() == null) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, order.getShipperId());
            }
            ps.setString(7, order.getPaymentMethod());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
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
        String sql = "INSERT INTO `order_item` (order_id, bouquet_id, quantity, unit_price, sellPrice) VALUES (?, ?, ?, ?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderItem.getOrderId());
            ps.setInt(2, orderItem.getBouquetId());
            ps.setInt(3, orderItem.getQuantity());
            ps.setString(4, orderItem.getUnitPrice());
            ps.setDouble(5, orderItem.getSellPrice());
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
        String sql = "UPDATE `order` SET total_import  = ? WHERE order_id = ?";
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
        if (statusIds == null || statusIds.isEmpty()) {
            return orders;
        }

        StringBuilder sql = new StringBuilder(
                "SELECT o.order_id, o.order_date, o.customer_id, "
                + "COALESCE(u.Fullname, o.customer_name) AS customer_name, "
                + "COALESCE(u.Phone, o.customer_phone) AS customer_phone, "
                + "COALESCE(u.Address, o.customer_address) AS customer_address, "
                + "o.total_sell, o.total_import, o.status_id, os.status_name, "
                + "o.shipper_id, s.Fullname AS shipper_name, o.payment_method "
                + "FROM `order` o "
                + "LEFT JOIN `user` u ON o.customer_id = u.User_ID "
                + "JOIN `order_status` os ON o.status_id = os.order_status_id "
                + "LEFT JOIN `user` s ON o.shipper_id = s.User_ID "
                + "WHERE o.shipper_id = ? AND o.status_id IN ("
        );

        for (int i = 0; i < statusIds.size(); i++) {
            sql.append("?");
            if (i < statusIds.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(") ORDER BY o.order_date DESC");

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql.toString());
            ps.setInt(1, shipperId);
            for (int i = 0; i < statusIds.size(); i++) {
                ps.setInt(i + 2, statusIds.get(i)); // i+2 because 1st param is shipperId
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getString("order_date"),
                        rs.getObject("customer_id") != null ? rs.getInt("customer_id") : null,
                        rs.getString("customer_name"),
                        rs.getString("customer_phone"),
                        rs.getString("customer_address"),
                        rs.getString("total_sell"),
                        rs.getString("total_import"),
                        rs.getInt("status_id"),
                        rs.getString("status_name"),
                        rs.getObject("shipper_id") != null ? rs.getInt("shipper_id") : null,
                        rs.getString("shipper_name"),
                        rs.getString("payment_method")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            System.err.println("getOrdersByShipperIdAndStatuses ERROR: " + e.getMessage());
            e.printStackTrace();
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
        String sql = "SELECT\n"
                + "    b.Bouquet_ID,\n"
                + "    b.Bouquet_Name,\n"
                + "    MIN(bi.image_url) AS image_url,\n" // Using MIN() to aggregate image_url
                + "    b.Price,\n"
                + "    b.sellPrice,\n"
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
                bouquet.setBouquetId(rs.getInt("Bouquet_ID"));
                bouquet.setBouquetName(rs.getString("Bouquet_Name"));
                bouquet.setPrice(rs.getInt("Price"));
                bouquet.setSellPrice(rs.getInt("sellPrice"));
                bouquet.setDescription(rs.getString("Description"));
                bouquet.setCid(rs.getInt("CID"));
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

    public boolean updateTotalImportAndSell(int orderId, String totalImport, String totalSell) {
        String sql = "UPDATE `order` SET total_import = ?, total_sell = ? WHERE order_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, totalImport);
            ps.setString(2, totalSell);
            ps.setInt(3, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTotalImport(int orderId, int totalImport) {
        String sql = "UPDATE `order` SET total_import = ? WHERE order_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, totalImport);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating total import: " + e.getMessage());
            return false;
        }
    }

    public OrderItem getBouquetQuantityInOrder(int orderItemId, int orderId, int bouquetId) {
        String sql = "SELECT\n"
                + "  order_item_id,\n"
                + "  order_id,\n"
                + "  bouquet_id,\n"
                + "  quantity,\n"
                + "  sellPrice,   \n"
                + "  status   \n"
                + "FROM order_item\n"
                + "WHERE order_item_id = ?\n"
                + "AND order_id = ?\n"
                + "AND bouquet_id = ?";
        OrderItem item = null;
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            // Thiết lập tham số
            ps.setInt(1, orderItemId);
            ps.setInt(2, orderId);
            ps.setInt(3, bouquetId);
            rs = ps.executeQuery();
            // Lấy kết quả
            if (rs.next()) {
                item = new OrderItem();
                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setBouquetId(rs.getInt("bouquet_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("sellPrice"));
                item.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return item;
    }

    public void completeBouquetCreation(int orderItemId, int orderId, int bouquetId) {
        String sql = """
        UPDATE order_item
        SET 
            status = 'done'
        WHERE
            order_item_id = ?
            AND order_id = ?
            AND bouquet_id = ?;
        """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderItemId);
            ps.setInt(2, orderId);
            ps.setInt(3, bouquetId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating order_item status: " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace(); // Ghi log lỗi đóng tài nguyên
            }
        }

    }

    public void updateOrderStatusAfterMakingBouquet(int orderId) {
        String sql = "UPDATE la_fioreria.order o\n"
                + "JOIN la_fioreria.order_status os ON o.status_id = os.order_status_id\n"
                + "SET o.status_id = 3\n"
                + "WHERE o.order_id = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error updating order_item status: " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace(); // Ghi log lỗi đóng tài nguyên
            }
        }
    }

    public boolean isDuplicateRequest(int orderId, int orderItemId, int flowerId) {
        String sql = "SELECT COUNT(*) AS cnt FROM `la_fioreria`.`requestflower` "
                + "WHERE Order_ID = ? AND Order_Item_ID = ? AND Flower_ID = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setInt(2, orderItemId);
            ps.setInt(3, flowerId);
            rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("cnt");
                return count > 0;  // Trả về true nếu đã tồn tại ít nhất 1 dòng
            }
        } catch (SQLException e) {
            System.err.println("SQL Error while checking duplicate request: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void addRequest(RequestFlower rf) {
        String sql = "INSERT INTO `la_fioreria`.`requestflower`\n"
                + "(`Order_ID`,\n"
                + "`Order_Item_ID`,\n"
                + "`Flower_ID`,\n"
                + "`Quantity`,\n"
                + "`Request_Creation_Date`,\n"
                + "`price`)\n"
                + "VALUES\n"
                + "(?, ?, ?, ?, ?, ?);";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, rf.getOrderId());
            ps.setInt(2, rf.getOrderItemId());
            ps.setInt(3, rf.getFlowerId());
            ps.setInt(4, rf.getQuantity());
            ps.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
            ps.setInt(6, rf.getPrice());
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace(); // Ghi log lỗi đóng tài nguyên
            }
        }
    }

    public void updateOrderItemStatus(int orderItemId, String status) {
        String sql = "UPDATE la_fioreria.order_item SET status = ? WHERE order_item_id = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, orderItemId);

            int updated = ps.executeUpdate();
            System.out.println("Updated order_item_id " + orderItemId + ". Rows affected: " + updated);

        } catch (SQLException e) {
            System.err.println("SQL Error while updating order item status to EMAILED: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateRequestQuantity(int orderId, int orderItemId, int flowerId, int quantity) {
        String sql = "UPDATE `la_fioreria`.`requestflower`\n"
                + "SET\n"
                + "`Quantity` = ?\n"
                + "WHERE `Order_ID` = ? AND `Order_Item_ID` = ? AND `Flower_ID` = ?;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, orderId);
            ps.setInt(3, orderItemId);
            ps.setInt(4, flowerId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace(); // Ghi log lỗi đóng tài nguyên
            }
        }
    }

    public List<RequestFlower> getRequestFlowerByOrder(int orderId, int orderItemId) {
        List<RequestFlower> listRequest = new ArrayList<>();
        String sql = "SELECT * FROM la_fioreria.requestflower\n"
                + "WHERE Order_ID = ?\n"
                + "AND Order_Item_ID = ?;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setInt(2, orderItemId);
            rs = ps.executeQuery();
            while (rs.next()) {
                int flowerId = rs.getInt("Flower_ID");
                int quantity = rs.getInt("Quantity");
                String status = rs.getString("Status");
                LocalDate requestDate = rs.getDate("Request_Creation_Date").toLocalDate();
                java.sql.Date sqlConfirmDate = rs.getDate("Request_Confirmation_Date");
                LocalDate confirmDate = (sqlConfirmDate != null) ? sqlConfirmDate.toLocalDate() : null;
                int price = rs.getInt("price");

                RequestFlower rf = new RequestFlower(orderId, orderItemId, flowerId, quantity, status, requestDate, confirmDate, price);
                listRequest.add(rf);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }

        return listRequest;
    }

    public List<RequestDisplay> gettAllRequestList(String flowerName, Date requesFlowerDate, Date confirmRequestDate, String status) {
        List<RequestDisplay> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT \n"
                + "    rf.Order_ID,\n"
                + "    rf.Order_Item_ID,\n"
                + "    GROUP_CONCAT(ft.Flower_Name SEPARATOR ', ') AS Flower_Names,\n"
                + "    ANY_VALUE(rf.Request_Creation_Date) AS Request_Date,\n"
                + "    MAX(rf.Request_Confirmation_Date) AS Confirm_Date,\n"
                + "    CASE\n"
                + "        WHEN SUM(rf.Status = 'reject') > 0 THEN 'reject'\n"
                + "        WHEN SUM(rf.Status = 'done') > 0 AND SUM(rf.Status = 'pending') > 0 THEN 'doing'\n"
                + "        WHEN SUM(rf.Status = 'pending') > 0 THEN 'pending'\n"
                + "        WHEN SUM(rf.Status = 'done') = COUNT(*) THEN 'done'\n"
                + "        ELSE 'unknown'\n"
                + "    END AS Status\n"
                + "FROM requestflower rf\n"
                + "JOIN flower_type ft ON rf.Flower_ID = ft.Flower_ID"
        );

        // Build dynamic WHERE clause
        List<String> conditions = new ArrayList<>();
        if (flowerName != null && !flowerName.trim().isEmpty()) {
            conditions.add("ft.Flower_Name LIKE ?");
        }
        if (requesFlowerDate != null) {
            conditions.add("rf.Request_Creation_Date = ?");
        }
        if (confirmRequestDate != null) {
            conditions.add("rf.Request_Confirmation_Date = ?");
        }
        if (status != null && !status.trim().isEmpty()) {
            conditions.add("rf.Status = ?");
        }

        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }

        sql.append(" GROUP BY rf.Order_ID, rf.Order_Item_ID;");

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql.toString());

            // Set parameters
            int paramIndex = 1;
            if (flowerName != null && !flowerName.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + flowerName + "%");
            }
            if (requesFlowerDate != null) {
                ps.setDate(paramIndex++, new java.sql.Date(requesFlowerDate.getTime()));
            }
            if (confirmRequestDate != null) {
                ps.setDate(paramIndex++, new java.sql.Date(confirmRequestDate.getTime()));
            }
            if (status != null && !status.trim().isEmpty()) {
                ps.setString(paramIndex++, status);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("Order_ID");
                int orderItemId = rs.getInt("Order_Item_ID");
                String flowerNames = rs.getString("Flower_Names");
                LocalDate requestDate = rs.getDate("Request_Date").toLocalDate();
                java.sql.Date sqlConfirmDate = rs.getDate("Confirm_Date");
                LocalDate confirmDate = (sqlConfirmDate != null) ? sqlConfirmDate.toLocalDate() : null;
                String resultStatus = rs.getString("Status");

                RequestDisplay rd = new RequestDisplay(orderId, orderItemId, flowerNames, requestDate, confirmDate, resultStatus);
                list.add(rd);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }

        return list;
    }

    public void confirmFlowerRequest(int orderId, int orderItemId, int flowerId) {
        String sql = "UPDATE `la_fioreria`.`requestflower`\n"
                + "SET\n"
                + "`Status` = 'done',\n"
                + "`Request_Confirmation_Date` = ?\n"
                + "WHERE `Order_ID` = ? AND `Order_Item_ID` = ? AND `Flower_ID` = ?;";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);

            // Gán các giá trị cho ? trong câu SQL
            ps.setDate(1, new java.sql.Date(System.currentTimeMillis())); // Ngày hôm nay
            ps.setInt(2, orderId);
            ps.setInt(3, orderItemId);
            ps.setInt(4, flowerId);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating order_item status: " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace(); // Ghi log lỗi đóng tài nguyên
            }
        }
    }

    public List<WholesaleOrderDetail> getWholesaleOrderDetailsByOrder(int orderId, int orderItemId, int bouquetId) {
        List<WholesaleOrderDetail> list = new ArrayList<>();
        String sql = """
        SELECT 
            o.order_id,
            o.customer_id,
            o.order_date,
            oi.order_item_id,
            oi.bouquet_id,
            oi.unit_price AS bouquet_expense,
            oi.sellPrice AS bouquet_sell_price,
            wqfd.flower_id,
            wqfd.flower_ws_price,
            bqRaw.quantity AS flower_quantity_in_bouquet,
            (bqRaw.quantity * oi.quantity) AS total_flower_quantity
        FROM la_fioreria.order_item oi
        JOIN la_fioreria.order o 
            ON oi.order_id = o.order_id
        JOIN la_fioreria.wholesale_quote_request wqr 
            ON oi.request_group_id = wqr.request_group_id 
            AND oi.bouquet_id = wqr.bouquet_id
        JOIN la_fioreria.wholesale_quote_flower_detail wqfd 
            ON wqfd.wholesale_request_id = wqr.id 
            AND wqfd.bouquet_id = wqr.bouquet_id
        JOIN la_fioreria.bouquet_raw bqRaw 
            ON bqRaw.bouquet_id = oi.bouquet_id
        JOIN la_fioreria.flower_batch fb
            ON fb.batch_id = bqRaw.batch_id 
            AND fb.flower_id = wqfd.flower_id
        WHERE o.type = 'wholesale'
          AND o.order_id = ?
          AND oi.order_item_id = ?
          AND oi.bouquet_id = ?;
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setInt(2, orderItemId);
            ps.setInt(3, bouquetId);

            rs = ps.executeQuery();
            while (rs.next()) {
                WholesaleOrderDetail detail = new WholesaleOrderDetail();
                detail.setOrderId(rs.getInt("order_id"));
                detail.setCustomerId(rs.getInt("customer_id"));
                detail.setOrderDate(rs.getDate("order_date").toLocalDate());
                detail.setOrderItemId(rs.getInt("order_item_id"));
                detail.setBouquetId(rs.getInt("bouquet_id"));
                detail.setBouquetExpense(rs.getInt("bouquet_expense"));
                detail.setBouquetSellPrice(rs.getInt("bouquet_sell_price"));
                detail.setFlowerId(rs.getInt("flower_id"));
                detail.setFlowerWholesalePrice(rs.getInt("flower_ws_price"));
                detail.setFlowerQuantityInBouquet(rs.getInt("flower_quantity_in_bouquet"));
                detail.setTotalFlowerQuantity(rs.getInt("total_flower_quantity"));

                list.add(detail);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error while getting wholesale order details: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return list;
    }

    public String getRequestGroupIdByOrderItem(int orderItemId) {
        String sql = """
        SELECT oi.request_group_id
        FROM la_fioreria.order_item oi
        WHERE oi.order_item_id = ?
    """;
        String requestGroupId = null;
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderItemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                requestGroupId = rs.getString("request_group_id");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getRequestGroupIdByOrderItem: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return requestGroupId;
    }

    public OrderItem getOrderItemByID(int orderItemId, int orderId, int bouquetId) {
        String sql = "SELECT *\n"
                + "FROM order_item\n"
                + "WHERE order_item_id = ?\n"
                + "AND order_id = ?\n"
                + "AND bouquet_id = ?";
        OrderItem item = null;
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            // Thiết lập tham số
            ps.setInt(1, orderItemId);
            ps.setInt(2, orderId);
            ps.setInt(3, bouquetId);
            rs = ps.executeQuery();
            // Lấy kết quả
            if (rs.next()) {
                item = new OrderItem();
                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setBouquetId(rs.getInt("bouquet_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("unit_price"));
                item.setSellPrice(rs.getDouble("sellPrice"));
                item.setStatus(rs.getString("status"));
                item.setRequest_group_id(rs.getString("request_group_id"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return item;
    }

    public static void main(String[] args) {
        OrderDAO cartDAO = new OrderDAO();

        int testOrderId = 1; // 📝 Thay ID này bằng 1 ID tồn tại trong DB
        Order order = cartDAO.getOrderDetailById(31);

        System.out.println(cartDAO.getWholesaleOrderDetailsByOrder(57, 63, 2));

        if (order != null) {
            System.out.println("Thông tin đơn hàng:");
            System.out.println("ID: " + order.getOrderId());
            System.out.println("Ngày đặt: " + order.getOrderDate());
            System.out.println("Khách hàng: " + order.getCustomerName());
            System.out.println("SĐT: " + order.getCustomerPhone());
            System.out.println("Địa chỉ: " + order.getCustomerAddress());
            System.out.println("Tổng tiền: " + order.getTotalSell());
            System.out.println("Phương thức thanh toán: " + order.getPaymentMethod());
            System.out.println("Trạng thái: " + order.getStatusName());
            System.out.println("Shipper: " + order.getShipperName());
        } else {
            System.out.println("Không tìm thấy đơn hàng với ID: " + testOrderId);
        }
    }
}
