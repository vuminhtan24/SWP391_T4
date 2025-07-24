/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.WholeSale;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import model.WholeSaleFlower;

/**
 *
 * @author ADMIN
 */
public class WholeSaleDAO extends BaseDao {

    public List<WholeSale> getAll() {
        List<WholeSale> list = new ArrayList<>();

        String sql = "SELECT * FROM la_fioreria.wholesale_quote_request";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int bouquet_id = rs.getInt("bouquet_id");
                int requested_quantity = rs.getInt("requested_quantity");
                String note = rs.getString("note");
                int quoted_price = rs.getInt("quoted_price");
                int total_price = rs.getInt("total_price");
                LocalDate quoted_at = rs.getDate("quoted_at").toLocalDate();
                LocalDate responded_at = rs.getDate("responded_at").toLocalDate();
                LocalDate created_at = rs.getDate("created_at").toLocalDate();
                String status = rs.getString("status");
                int expense = rs.getInt("expense");
                String request_group_id = rs.getString("request_group_id");

                WholeSale ws = new WholeSale(id, user_id, bouquet_id, requested_quantity, note, quoted_price, total_price, quoted_at, responded_at, created_at, status, expense, request_group_id);
                list.add(ws);
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

    public void insertWholeSaleRequest(WholeSale ws) {
        String sql = "INSERT INTO `la_fioreria`.`wholesale_quote_request`\n"
                + "(`user_id`,\n"
                + " `bouquet_id`,\n"
                + " `requested_quantity`,\n"
                + " `note`,\n"
                + " `quoted_price`,\n"
                + " `total_price`,\n"
                + " `quoted_at`,\n"
                + " `responded_at`,\n"
                + " `created_at`,\n"
                + " `status`,\n"
                + " `expense`,\n"
                + " `request_group_id`)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setInt(1, ws.getUser_id());
            ps.setInt(2, ws.getBouquet_id());
            ps.setInt(3, ws.getRequested_quantity());
            ps.setString(4, ws.getNote());

            // Giá có thể null nếu chưa báo giá, nên xử lý null an toàn
            if (ws.getQuoted_price() != null) {
                ps.setInt(5, ws.getQuoted_price());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            if (ws.getTotal_price() != null) {
                ps.setInt(6, ws.getTotal_price());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            if (ws.getQuoted_at() != null) {
                ps.setDate(7, java.sql.Date.valueOf(ws.getQuoted_at()));
            } else {
                ps.setNull(7, java.sql.Types.DATE);
            }

            if (ws.getResponded_at() != null) {
                ps.setDate(8, java.sql.Date.valueOf(ws.getResponded_at()));
            } else {
                ps.setNull(8, java.sql.Types.DATE);
            }

            if (ws.getExpense() != null) {
                ps.setInt(9, ws.getExpense());
            } else {
                ps.setNull(9, java.sql.Types.INTEGER);
            }

            ps.setDate(9, java.sql.Date.valueOf(ws.getCreated_at()));
            ps.setString(10, ws.getStatus());

            if (ws.getExpense() != null) {
                ps.setInt(11, ws.getExpense());
            } else {
                ps.setNull(11, java.sql.Types.INTEGER);
            }

            ps.setString(12, ws.getRequest_group_id());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error insertWholeSaleRequest: " + e.getMessage());
        } finally {
            try {
                this.closeResources(); // Đóng connection, statement, resultset nếu cần
            } catch (Exception e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public List<WholeSale> getWholeSaleRequestShoppingByUserID(int uid) {
        List<WholeSale> list = new ArrayList<>();

        String sql = "SELECT * FROM la_fioreria.wholesale_quote_request WHERE user_id = ? AND status = 'SHOPPING'";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int bouquet_id = rs.getInt("bouquet_id");
                int requested_quantity = rs.getInt("requested_quantity");
                String note = rs.getString("note");
                int quoted_price = rs.getInt("quoted_price");
                int total_price = rs.getInt("total_price");
                java.sql.Date quotedDateSql = rs.getDate("quoted_at");
                LocalDate quoted_at = (quotedDateSql != null) ? quotedDateSql.toLocalDate() : null;

                java.sql.Date respondedDateSql = rs.getDate("responded_at");
                LocalDate responded_at = (respondedDateSql != null) ? respondedDateSql.toLocalDate() : null;

                java.sql.Date createdDateSql = rs.getDate("created_at");
                LocalDate created_at = (createdDateSql != null) ? createdDateSql.toLocalDate() : null;
                String status = rs.getString("status");
                int expense = rs.getInt("expense");
                String request_group_id = rs.getString("request_group_id");

                WholeSale ws = new WholeSale(id, uid, bouquet_id, requested_quantity, note, quoted_price, total_price, quoted_at, responded_at, created_at, status, expense, request_group_id);
                list.add(ws);
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

    public List<WholeSale> getWholeSaleRequestByUserID(int uid) {
        List<WholeSale> list = new ArrayList<>();

        String sql = "SELECT * FROM la_fioreria.wholesale_quote_request WHERE user_id = ? AND status <> 'SHOPPING'";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int bouquet_id = rs.getInt("bouquet_id");
                int requested_quantity = rs.getInt("requested_quantity");
                String note = rs.getString("note");
                int quoted_price = rs.getInt("quoted_price");
                int total_price = rs.getInt("total_price");
                java.sql.Date quotedDateSql = rs.getDate("quoted_at");
                LocalDate quoted_at = (quotedDateSql != null) ? quotedDateSql.toLocalDate() : null;

                java.sql.Date respondedDateSql = rs.getDate("responded_at");
                LocalDate responded_at = (respondedDateSql != null) ? respondedDateSql.toLocalDate() : null;

                java.sql.Date createdDateSql = rs.getDate("created_at");
                LocalDate created_at = (createdDateSql != null) ? createdDateSql.toLocalDate() : null;
                String status = rs.getString("status");
                int expense = rs.getInt("expense");
                String request_group_id = rs.getString("request_group_id");

                WholeSale ws = new WholeSale(id, uid, bouquet_id, requested_quantity, note, quoted_price, total_price, quoted_at, responded_at, created_at, status, expense, request_group_id);
                list.add(ws);
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

    public List<WholeSale> getWholeSaleRequestByUserIDandBouquetID(int uid, int bid) {
        List<WholeSale> list = new ArrayList<>();

        String sql = "SELECT * FROM la_fioreria.wholesale_quote_request WHERE user_id = ? AND bouquet_id = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int bouquet_id = rs.getInt("bouquet_id");
                int requested_quantity = rs.getInt("requested_quantity");
                String note = rs.getString("note");
                int quoted_price = rs.getInt("quoted_price");
                int total_price = rs.getInt("total_price");
                java.sql.Date quotedDateSql = rs.getDate("quoted_at");
                LocalDate quoted_at = (quotedDateSql != null) ? quotedDateSql.toLocalDate() : null;

                java.sql.Date respondedDateSql = rs.getDate("responded_at");
                LocalDate responded_at = (respondedDateSql != null) ? respondedDateSql.toLocalDate() : null;

                java.sql.Date createdDateSql = rs.getDate("created_at");
                LocalDate created_at = (createdDateSql != null) ? createdDateSql.toLocalDate() : null;
                String status = rs.getString("status");
                int expense = rs.getInt("expense");
                String request_group_id = rs.getString("request_group_id");

                WholeSale ws = new WholeSale(id, uid, bouquet_id, requested_quantity, note, quoted_price, total_price, quoted_at, responded_at, created_at, status, expense, request_group_id);
                list.add(ws);
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

    public void UpdateWholeSaleRequest(int uid, int bid, int quantity, String note) {
        String sql = "UPDATE `la_fioreria`.`wholesale_quote_request`\n"
                + "SET\n"
                + "`requested_quantity` = ?,\n"
                + "`note` = ?\n"
                + "WHERE user_id = ? AND bouquet_id = ?;";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setString(2, note);
            ps.setInt(3, uid);
            ps.setInt(4, bid);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public void confirmAllRequests(int userId) {
        String sql = "UPDATE wholesale_quote_request SET status = 'PENDING' WHERE user_id = ? AND status = 'SHOPPING'";
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

    public List<WholeSale> getWholeSaleSummary() {
        List<WholeSale> list = new ArrayList<>();

        String sql = """
        SELECT 
            user_id,
            created_at,
            request_group_id,
            MIN(quoted_at) AS quoted_at,
            MIN(responded_at) AS responded_at,
            CASE 
                WHEN SUM(CASE WHEN status = 'ACCEPTED' THEN 1 ELSE 0 END) > 0
                     AND SUM(CASE WHEN status NOT IN ('ACCEPTED') THEN 1 ELSE 0 END) = 0 THEN 'ACCEPTED'
                WHEN SUM(CASE WHEN status = 'EMAILED' THEN 1 ELSE 0 END) > 0
                     AND SUM(CASE WHEN status NOT IN ('EMAILED') THEN 1 ELSE 0 END) = 0 THEN 'EMAILED'
                WHEN SUM(CASE WHEN status = 'REJECTED' THEN 1 ELSE 0 END) > 0
                     AND SUM(CASE WHEN status NOT IN ('REJECTED') THEN 1 ELSE 0 END) = 0 THEN 'REJECTED'
                WHEN SUM(CASE WHEN status = 'QUOTED' THEN 1 ELSE 0 END) > 0
                     AND SUM(CASE WHEN status NOT IN ('QUOTED') THEN 1 ELSE 0 END) = 0 THEN 'QUOTED'
                WHEN SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) > 0
                     AND SUM(CASE WHEN status NOT IN ('COMPLETED') THEN 1 ELSE 0 END) = 0 THEN 'COMPLETED'
                WHEN SUM(CASE WHEN status = 'PENDING' THEN 1 ELSE 0 END) > 0 THEN 'PENDING'
                ELSE 'UNKNOWN'
            END AS status
        FROM wholesale_quote_request
        WHERE status <> 'SHOPPING'
        GROUP BY user_id, created_at, request_group_id
        ORDER BY created_at DESC, user_id, request_group_id;
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String requestGroupId = rs.getString("request_group_id");

                LocalDate createdAt = rs.getDate("created_at").toLocalDate();

                LocalDate quotedAt = null;
                java.sql.Date quotedDateSql = rs.getDate("quoted_at");
                if (quotedDateSql != null) {
                    quotedAt = quotedDateSql.toLocalDate();
                }

                LocalDate respondedAt = null;
                java.sql.Date respondedDateSql = rs.getDate("responded_at");
                if (respondedDateSql != null) {
                    respondedAt = respondedDateSql.toLocalDate();
                }

                String resultStatus = rs.getString("status");

                // Giả sử bạn có constructor mới có requestGroupId
                WholeSale ws = new WholeSale(userId, createdAt, quotedAt, respondedAt, resultStatus, requestGroupId);
                list.add(ws);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public List<WholeSale> filterWholeSaleSummary(LocalDate createdAt, String statusFilter) {
        List<WholeSale> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
        SELECT 
            user_id,
            created_at,
            request_group_id,
            MIN(quoted_at) AS quoted_at,
            MIN(responded_at) AS responded_at,
            CASE 
                WHEN SUM(CASE WHEN status = 'ACCEPTED' THEN 1 ELSE 0 END) > 0
                     AND SUM(CASE WHEN status NOT IN ('ACCEPTED') THEN 1 ELSE 0 END) = 0 THEN 'ACCEPTED'
                WHEN SUM(CASE WHEN status = 'EMAILED' THEN 1 ELSE 0 END) > 0
                     AND SUM(CASE WHEN status NOT IN ('EMAILED') THEN 1 ELSE 0 END) = 0 THEN 'EMAILED'
                WHEN SUM(CASE WHEN status = 'PENDING' THEN 1 ELSE 0 END) > 0 THEN 'PENDING'
                WHEN SUM(CASE WHEN status = 'QUOTED' THEN 1 ELSE 0 END) > 0
                     AND SUM(CASE WHEN status = 'PENDING' THEN 1 ELSE 0 END) = 0 THEN 'QUOTED'
                WHEN SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) > 0
                     AND SUM(CASE WHEN status = 'PENDING' THEN 1 ELSE 0 END) = 0 THEN 'COMPLETED'
                ELSE 'UNKNOWN'
            END AS status
        FROM wholesale_quote_request
        WHERE status <> 'SHOPPING'
    """);

        List<Object> parameters = new ArrayList<>();

        if (createdAt != null) {
            sql.append(" AND created_at = ? ");
            parameters.add(java.sql.Date.valueOf(createdAt));
        }

        if (statusFilter != null && !statusFilter.trim().isEmpty()) {
            sql.append(" AND status = ? ");
            parameters.add(statusFilter.trim());
        }

        sql.append(" GROUP BY user_id, created_at, request_group_id ")
                .append(" ORDER BY created_at DESC, user_id, request_group_id ");

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql.toString());

            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String requestGroupId = rs.getString("request_group_id");

                LocalDate quotedAt = null;
                java.sql.Date quotedAtSql = rs.getDate("quoted_at");
                if (quotedAtSql != null) {
                    quotedAt = quotedAtSql.toLocalDate();
                }

                LocalDate respondedAt = null;
                java.sql.Date respondedAtSql = rs.getDate("responded_at");
                if (respondedAtSql != null) {
                    respondedAt = respondedAtSql.toLocalDate();
                }

                LocalDate createdAtVal = rs.getDate("created_at").toLocalDate();
                String resultStatus = rs.getString("status");

                // Cần có constructor mới: WholeSale(int userId, LocalDate createdAt, LocalDate quotedAt, LocalDate respondedAt, String status, String requestGroupId)
                WholeSale ws = new WholeSale(userId, createdAtVal, quotedAt, respondedAt, resultStatus, requestGroupId);
                list.add(ws);
            }

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public List<WholeSale> getWholeSaleList(int uid, LocalDate requestDate, String requestGroupId) {
        List<WholeSale> list = new ArrayList<>();
        String sql = """
        SELECT * FROM la_fioreria.wholesale_quote_request
        WHERE user_id = ?
        AND status <> 'SHOPPING'
        AND created_at = ?
        AND request_group_id = ?
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setDate(2, java.sql.Date.valueOf(requestDate));
            ps.setString(3, requestGroupId);

            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int bouquetId = rs.getInt("bouquet_id");
                int requestedQuantity = rs.getInt("requested_quantity");
                String note = rs.getString("note");
                Integer quotedPrice = rs.getObject("quoted_price") != null ? rs.getInt("quoted_price") : null;
                Integer totalPrice = rs.getObject("total_price") != null ? rs.getInt("total_price") : null;

                LocalDate quotedAt = rs.getDate("quoted_at") != null ? rs.getDate("quoted_at").toLocalDate() : null;
                LocalDate respondedAt = rs.getDate("responded_at") != null ? rs.getDate("responded_at").toLocalDate() : null;
                Integer expense = rs.getObject("expense") != null ? rs.getInt("expense") : null;
                String reqGroupId = rs.getString("request_group_id");

                WholeSale ws = new WholeSale(id, uid, bouquetId, requestedQuantity, note, quotedPrice, totalPrice, quotedAt, respondedAt, requestDate, rs.getString("status"), expense, reqGroupId);
                list.add(ws);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in getWholeSaleList: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public WholeSale getWholeSaleDetail(int uid, LocalDate requestDate, String status, int bid, String requestGroupId) {
        String sql = """
        SELECT * FROM la_fioreria.wholesale_quote_request
        WHERE user_id = ?
        AND created_at = ?
        AND status = ?
        AND bouquet_id = ?
        AND request_group_id = ?
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setDate(2, java.sql.Date.valueOf(requestDate));
            ps.setString(3, status);
            ps.setInt(4, bid);
            ps.setString(5, requestGroupId);

            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int requestedQuantity = rs.getInt("requested_quantity");
                String note = rs.getString("note");
                Integer quotedPrice = rs.getObject("quoted_price") != null ? rs.getInt("quoted_price") : null;
                Integer totalPrice = rs.getObject("total_price") != null ? rs.getInt("total_price") : null;

                LocalDate quotedAt = rs.getDate("quoted_at") != null ? rs.getDate("quoted_at").toLocalDate() : null;
                LocalDate respondedAt = rs.getDate("responded_at") != null ? rs.getDate("responded_at").toLocalDate() : null;

                Integer expense = rs.getObject("expense") != null ? rs.getInt("expense") : null;
                String reqGroupId = rs.getString("request_group_id");

                return new WholeSale(id, uid, bid, requestedQuantity, note, quotedPrice, totalPrice, quotedAt, respondedAt, requestDate, status, expense, reqGroupId);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in getWholeSaleDetail: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<WholeSale> getWholeSaleRequestByFlowerID(int flowerId) {
        List<WholeSale> list = new ArrayList<>();
        String sql = """
        SELECT ws.* FROM la_fioreria.wholesale_quote_request ws
        JOIN la_fioreria.bouquet_raw br ON br.bouquet_id = ws.bouquet_id
        JOIN la_fioreria.flower_batch fb ON fb.batch_id = br.batch_id
        JOIN la_fioreria.flower_type ft ON ft.flower_id = fb.flower_id
        WHERE ft.flower_id = ?
        AND ws.status <> 'SHOPPING'
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, flowerId);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                int bouquetId = rs.getInt("bouquet_id");
                int requestedQuantity = rs.getInt("requested_quantity");
                String note = rs.getString("note");

                Integer quotedPrice = rs.getObject("quoted_price") != null ? rs.getInt("quoted_price") : null;
                Integer totalPrice = rs.getObject("total_price") != null ? rs.getInt("total_price") : null;

                LocalDate quotedAt = rs.getDate("quoted_at") != null ? rs.getDate("quoted_at").toLocalDate() : null;
                LocalDate respondedAt = rs.getDate("responded_at") != null ? rs.getDate("responded_at").toLocalDate() : null;
                LocalDate createdAt = rs.getDate("created_at") != null ? rs.getDate("created_at").toLocalDate() : null;

                String status = rs.getString("status");
                Integer expense = rs.getObject("expense") != null ? rs.getInt("expense") : null;

                String requestGroupId = rs.getString("request_group_id");

                WholeSale ws = new WholeSale(id, userId, bouquetId, requestedQuantity, note, quotedPrice, totalPrice, quotedAt, respondedAt, createdAt, status, expense, requestGroupId);
                list.add(ws);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in getWholeSaleRequestByFlowerID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void assignQuotedPrice(int totalPrice, LocalDate quotedDate, int quotedPrice,
            int uid, LocalDate requestDate, String status,
            int bouquetId, int expense, String requestGroupId) {

        String sql = """
        UPDATE la_fioreria.wholesale_quote_request
        SET
            quoted_price = ?,
            total_price = ?,
            quoted_at = ?,
            status = 'QUOTED',
            expense = ?
        WHERE user_id = ?
          AND created_at = ?
          AND status = ?
          AND bouquet_id = ?
          AND request_group_id = ?
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, quotedPrice);
            ps.setInt(2, totalPrice);
            ps.setDate(3, java.sql.Date.valueOf(quotedDate));
            ps.setInt(4, expense);
            ps.setInt(5, uid);
            ps.setDate(6, java.sql.Date.valueOf(requestDate));
            ps.setString(7, status);
            ps.setInt(8, bouquetId);
            ps.setString(9, requestGroupId);

            int rowsUpdated = ps.executeUpdate();
            System.out.println("Updated " + rowsUpdated + " row(s) for bouquet_id=" + bouquetId + ", user_id=" + uid);

        } catch (SQLException e) {
            System.err.println("SQL Error in assignQuotedPrice: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateQuotedPrice(int totalPrice, LocalDate quotedDate, int quotedPrice,
            int uid, LocalDate requestDate, String status,
            int bouquetId, int expense, String requestGroupId) {

        String sql = """
        UPDATE la_fioreria.wholesale_quote_request
        SET
            quoted_price = ?,
            total_price = ?,
            quoted_at = ?,
            expense = ?
        WHERE user_id = ?
          AND created_at = ?
          AND status = ?
          AND bouquet_id = ?
          AND request_group_id = ?
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, quotedPrice);
            ps.setInt(2, totalPrice);
            ps.setDate(3, java.sql.Date.valueOf(quotedDate));
            ps.setInt(4, expense);
            ps.setInt(5, uid);
            ps.setDate(6, java.sql.Date.valueOf(requestDate));
            ps.setString(7, status);
            ps.setInt(8, bouquetId);
            ps.setString(9, requestGroupId);

            int rowsUpdated = ps.executeUpdate();
            System.out.println("Updated " + rowsUpdated + " row(s) for bouquet_id=" + bouquetId + ", user_id=" + uid);

        } catch (SQLException e) {
            System.err.println("SQL Error in assignQuotedPrice: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<WholeSaleFlower> listFlowerInRequest(String requestGroupId) {
        List<WholeSaleFlower> listWsFlower = new ArrayList<>();
        String sql = """
        SELECT f.*
        FROM la_fioreria.wholesale_quote_flower_detail f
        JOIN la_fioreria.wholesale_quote_request r ON f.wholesale_request_id = r.id
        WHERE r.request_group_id = ?
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, requestGroupId);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int wsId = rs.getInt("wholesale_request_id");
                int bouquetId = rs.getInt("bouquet_id");
                int flowerId = rs.getInt("flower_id");
                int flowerPrice = rs.getInt("flower_ws_price");

                WholeSaleFlower wsFlower = new WholeSaleFlower(id, wsId, bouquetId, flowerId, flowerPrice);
                listWsFlower.add(wsFlower);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return listWsFlower;
    }

    public void upsertFlowerWS(WholeSaleFlower wsFlower) {
        String checkSql = "SELECT COUNT(*) FROM wholesale_quote_flower_detail WHERE wholesale_request_id = ? AND bouquet_id = ? AND flower_id = ?";
        String updateSql = "UPDATE wholesale_quote_flower_detail SET flower_ws_price = ? WHERE wholesale_request_id = ? AND bouquet_id = ? AND flower_id = ?";
        String insertSql = "INSERT INTO wholesale_quote_flower_detail (wholesale_request_id, bouquet_id, flower_id, flower_ws_price) VALUES (?, ?, ?, ?)";

        try {
            connection = dbc.getConnection();

            // Check existence
            ps = connection.prepareStatement(checkSql);
            ps.setInt(1, wsFlower.getWholesale_request_id());
            ps.setInt(2, wsFlower.getBouquet_id());
            ps.setInt(3, wsFlower.getFlower_id());
            rs = ps.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            ps.close();

            if (count > 0) {
                // Update if exists
                ps = connection.prepareStatement(updateSql);
                ps.setInt(1, wsFlower.getFlower_ws_price());
                ps.setInt(2, wsFlower.getWholesale_request_id());
                ps.setInt(3, wsFlower.getBouquet_id());
                ps.setInt(4, wsFlower.getFlower_id());
            } else {
                // Insert if not exists
                ps = connection.prepareStatement(insertSql);
                ps.setInt(1, wsFlower.getWholesale_request_id());
                ps.setInt(2, wsFlower.getBouquet_id());
                ps.setInt(3, wsFlower.getFlower_id());
                ps.setInt(4, wsFlower.getFlower_ws_price());
            }
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

    public List<WholeSale> getWholeSaleQuotedList(int uid, LocalDate requestDate, String requestGroupId) {
        List<WholeSale> list = new ArrayList<>();
        String sql = """
        SELECT * FROM la_fioreria.wholesale_quote_request
        WHERE user_id = ?
          AND status = 'QUOTED'
          AND created_at = ?
          AND request_group_id = ?
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setDate(2, java.sql.Date.valueOf(requestDate));
            ps.setString(3, requestGroupId);

            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int bouquetId = rs.getInt("bouquet_id");
                int requestedQuantity = rs.getInt("requested_quantity");
                String note = rs.getString("note");

                Integer quotedPrice = rs.getObject("quoted_price") != null ? rs.getInt("quoted_price") : null;
                Integer totalPrice = rs.getObject("total_price") != null ? rs.getInt("total_price") : null;

                LocalDate quotedAt = rs.getDate("quoted_at") != null ? rs.getDate("quoted_at").toLocalDate() : null;
                LocalDate respondedAt = rs.getDate("responded_at") != null ? rs.getDate("responded_at").toLocalDate() : null;

                Integer expense = rs.getObject("expense") != null ? rs.getInt("expense") : null;
                String status = rs.getString("status");
                String reqGroupId = rs.getString("request_group_id");

                WholeSale ws = new WholeSale(
                        id, uid, bouquetId, requestedQuantity, note,
                        quotedPrice, totalPrice, quotedAt, respondedAt,
                        requestDate, status, expense, reqGroupId
                );
                list.add(ws);
            }

        } catch (SQLException e) {
            System.err.println("SQL Error in getWholeSaleQuotedList: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public void markAsEmailedForQuotedList(int uid, LocalDate requestDate, String requestGroupId) {
        String sql = """
        UPDATE la_fioreria.wholesale_quote_request
        SET status = 'EMAILED'
        WHERE user_id = ?
          AND created_at = ?
          AND request_group_id = ?
          AND status = 'QUOTED'
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setDate(2, java.sql.Date.valueOf(requestDate));
            ps.setString(3, requestGroupId);

            int updatedRows = ps.executeUpdate();
            System.out.println("Updated rows to EMAILED: " + updatedRows + " (user_id=" + uid + ", request_group_id=" + requestGroupId + ")");
        } catch (SQLException e) {
            System.err.println("Error updating status to EMAILED: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void updateWholeSaleStatusAndRespond(int uid, LocalDate requestDate, String requestGroupId, String newStatus, LocalDate respond) {
        String sql = """
        UPDATE la_fioreria.wholesale_quote_request
        SET status = ?,
        responded_at = ?             
        WHERE user_id = ?
          AND created_at = ?
          AND request_group_id = ?
          AND status = 'EMAILED'
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setDate(2, java.sql.Date.valueOf(respond));
            ps.setInt(3, uid);
            ps.setDate(4, java.sql.Date.valueOf(requestDate));
            ps.setString(5, requestGroupId);

            int updatedRows = ps.executeUpdate();
            System.out.println("Updated rows to '" + newStatus + "': " + updatedRows
                    + " (user_id=" + uid + ", request_group_id=" + requestGroupId + ")");
        } catch (SQLException e) {
            System.err.println("Error updating status to '" + newStatus + "': " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public void completeWholesale(List<WholeSale> listWS) {
        String sql = """
        UPDATE la_fioreria.wholesale_quote_request
        SET status = 'COMPLETED',            
        WHERE user_id = ?
          AND created_at = ?
          AND request_group_id = ?
          AND status = 'EMAILED'
    """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(3, listWS.get(0).getUser_id());
            ps.setDate(4, java.sql.Date.valueOf(listWS.get(0).getCreated_at()));
            ps.setString(5, listWS.get(0).getRequest_group_id());

            int updatedRows = ps.executeUpdate();
            System.out.println("Updated rows to '" + "COMPLETED" + "': " + updatedRows
                    + " (user_id=" + listWS.get(0).getUser_id() + ", request_group_id=" + listWS.get(0).getRequest_group_id() + ")");
        } catch (SQLException e) {
            System.err.println("Error updating status to '" + "COMPLETED" + "': " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void deleteWholeSaleShoppingByUserID(int userId, int bouquetId) {
        String sql = "DELETE FROM la_fioreria.wholesale_quote_request WHERE user_id = ? AND status = 'SHOPPING' AND bouquet_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, bouquetId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public String generateOrGetCurrentRequestGroupId(int userId) {
        String sqlSelect = """
        SELECT request_group_id
        FROM la_fioreria.wholesale_quote_request
        WHERE user_id = ?
          AND status = 'SHOPPING'
        LIMIT 1
    """;

        String sqlInsert = """
        INSERT INTO la_fioreria.wholesale_quote_request 
        (user_id, bouquet_id, requested_quantity, note, created_at, status, request_group_id)
        VALUES (?, NULL, 0, NULL, ?, 'SHOPPING', ?)
    """;

        String groupId = null;

        try {
            connection = dbc.getConnection();

            // Step 1: Check existing SHOPPING group
            ps = connection.prepareStatement(sqlSelect);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                groupId = rs.getString("request_group_id");
            } else {
                // Step 2: Generate new UUID
                groupId = UUID.randomUUID().toString();

                // Optional: Insert a dummy record to track group existence
                PreparedStatement psInsert = connection.prepareStatement(sqlInsert);
                psInsert.setInt(1, userId);
                psInsert.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                psInsert.setString(3, groupId);
                psInsert.executeUpdate();
                psInsert.close();

                System.out.println("✅ Created new request_group_id: " + groupId);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error in generateOrGetCurrentRequestGroupId: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return groupId;
    }

}
