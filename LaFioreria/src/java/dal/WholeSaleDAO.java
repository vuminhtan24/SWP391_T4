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

                WholeSale ws = new WholeSale(id, user_id, bouquet_id, requested_quantity, note, quoted_price, total_price, quoted_at, responded_at, created_at, status, expense);
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
                + " `expense`)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

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

                WholeSale ws = new WholeSale(id, uid, bouquet_id, requested_quantity, note, quoted_price, total_price, quoted_at, responded_at, created_at, status, expense);
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

                WholeSale ws = new WholeSale(id, uid, bouquet_id, requested_quantity, note, quoted_price, total_price, quoted_at, responded_at, created_at, status, expense);
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

                WholeSale ws = new WholeSale(id, uid, bouquet_id, requested_quantity, note, quoted_price, total_price, quoted_at, responded_at, created_at, status, expense);
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

        String sql = "SELECT \n"
                + "    user_id,\n"
                + "    created_at,\n"
                + "    MIN(quoted_at) AS quoted_at,\n"
                + "    MIN(responded_at) AS responded_at,\n"
                + "\n"
                + "    CASE \n"
                + "        WHEN SUM(CASE WHEN status = 'ACCEPTED' THEN 1 ELSE 0 END) > 0\n"
                + "             AND SUM(CASE WHEN status NOT IN ('ACCEPTED') THEN 1 ELSE 0 END) = 0 THEN 'ACCEPTED'\n"
                + "        WHEN SUM(CASE WHEN status = 'EMAILED' THEN 1 ELSE 0 END) > 0\n"
                + "             AND SUM(CASE WHEN status NOT IN ('EMAILED') THEN 1 ELSE 0 END) = 0 THEN 'EMAILED'\n"
                + "        WHEN SUM(CASE WHEN status = 'PENDING' THEN 1 ELSE 0 END) > 0 THEN 'PENDING'\n"
                + "        WHEN SUM(CASE WHEN status = 'QUOTED' THEN 1 ELSE 0 END) > 0\n"
                + "             AND SUM(CASE WHEN status = 'PENDING' THEN 1 ELSE 0 END) = 0 THEN 'QUOTED'\n"
                + "        WHEN SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) > 0\n"
                + "             AND SUM(CASE WHEN status = 'PENDING' THEN 1 ELSE 0 END) = 0 THEN 'COMPLETED'\n"
                + "        ELSE 'UNKNOWN'\n"
                + "    END AS `status`\n"
                + "FROM wholesale_quote_request\n"
                + "WHERE status <> 'SHOPPING'\n"
                + "GROUP BY user_id, created_at;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                int user_id = rs.getInt("user_id");

                LocalDate quoted_at = null;
                java.sql.Date quotedDateSql = rs.getDate("quoted_at");
                if (quotedDateSql != null) {
                    quoted_at = quotedDateSql.toLocalDate();
                }

                LocalDate responded_at = null;
                java.sql.Date respondedDateSql = rs.getDate("responded_at");
                if (respondedDateSql != null) {
                    responded_at = respondedDateSql.toLocalDate();
                }

                LocalDate created_at = rs.getDate("created_at").toLocalDate();
                String resultStatus = rs.getString("status");

                WholeSale ws = new WholeSale(user_id, created_at, quoted_at, responded_at, resultStatus);
                list.add(ws);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
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

        StringBuilder sql = new StringBuilder(
                "SELECT \n"
                + "    user_id,\n"
                + "    created_at,\n"
                + "    MIN(quoted_at) AS quoted_at,\n"
                + "    MIN(responded_at) AS responded_at,\n"
                + "\n"
                + "    CASE \n"
                + "        WHEN SUM(CASE WHEN status = 'ACCEPTED' THEN 1 ELSE 0 END) > 0\n"
                + "             AND SUM(CASE WHEN status NOT IN ('ACCEPTED') THEN 1 ELSE 0 END) = 0 THEN 'ACCEPTED'\n"
                + "        WHEN SUM(CASE WHEN status = 'EMAILED' THEN 1 ELSE 0 END) > 0\n"
                + "             AND SUM(CASE WHEN status NOT IN ('EMAILED') THEN 1 ELSE 0 END) = 0 THEN 'EMAILED'\n"
                + "        WHEN SUM(CASE WHEN status = 'PENDING' THEN 1 ELSE 0 END) > 0 THEN 'PENDING'\n"
                + "        WHEN SUM(CASE WHEN status = 'QUOTED' THEN 1 ELSE 0 END) > 0\n"
                + "             AND SUM(CASE WHEN status = 'PENDING' THEN 1 ELSE 0 END) = 0 THEN 'QUOTED'\n"
                + "        WHEN SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) > 0\n"
                + "             AND SUM(CASE WHEN status = 'PENDING' THEN 1 ELSE 0 END) = 0 THEN 'COMPLETED'\n"
                + "        ELSE 'UNKNOWN'\n"
                + "    END AS `status`\n"
                + "FROM wholesale_quote_request\n"
                + "WHERE 1=1\n"
        );

        List<Object> parameters = new ArrayList<>();

        if (createdAt != null) {
            sql.append("AND created_at = ?\n");
            parameters.add(java.sql.Date.valueOf(createdAt));
        }

        if (statusFilter != null && !statusFilter.trim().isEmpty()) {
            sql.append("AND status = ?\n");
            parameters.add(statusFilter.trim());
        }

        sql.append("GROUP BY user_id, created_at\n");

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql.toString());

            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");

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

                WholeSale ws = new WholeSale(userId, createdAtVal, quotedAt, respondedAt, resultStatus);
                list.add(ws);
            }

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public List<WholeSale> getWholeSaleList(int uid, LocalDate requestDate) {
        List<WholeSale> list = new ArrayList<>();
        String sql = "SELECT * FROM la_fioreria.wholesale_quote_request \n"
                + "WHERE user_id = ? \n"
                + "AND status <> 'SHOPPING' \n"
                + "AND created_at = ?;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setDate(2, java.sql.Date.valueOf(requestDate));
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

                String status = rs.getString("status");
                int expense = rs.getInt("expense");

                WholeSale ws = new WholeSale(id, uid, bouquet_id, requested_quantity, note, quoted_price, total_price, quoted_at, responded_at, requestDate, status, expense);
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

    public WholeSale getWholeSaleDetail(int uid, LocalDate requestDate, String status, int bid) {
        String sql = "SELECT * FROM la_fioreria.wholesale_quote_request \n"
                + "WHERE user_id = ?\n"
                + "AND created_at = ?\n"
                + "AND status = ?\n"
                + "AND bouquet_id = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setDate(2, java.sql.Date.valueOf(requestDate));
            ps.setString(3, status);
            ps.setInt(4, bid);
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int requested_quantity = rs.getInt("requested_quantity");
                String note = rs.getString("note");
                int quoted_price = rs.getInt("quoted_price");
                int total_price = rs.getInt("total_price");
                java.sql.Date quotedDateSql = rs.getDate("quoted_at");
                LocalDate quoted_at = (quotedDateSql != null) ? quotedDateSql.toLocalDate() : null;

                java.sql.Date respondedDateSql = rs.getDate("responded_at");
                LocalDate responded_at = (respondedDateSql != null) ? respondedDateSql.toLocalDate() : null;
                int expense = rs.getInt("expense");

                return new WholeSale(id, uid, bid, requested_quantity, note, quoted_price, total_price, quoted_at, responded_at, requestDate, status, expense);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public List<WholeSale> getWholeSaleRequestByFlowerID(int flowerId) {
        List<WholeSale> list = new ArrayList<>();
        String sql = "SELECT ws.* FROM la_fioreria.wholesale_quote_request ws\n"
                + "JOIN la_fioreria.bouquet_raw br ON br.bouquet_id = ws.bouquet_id\n"
                + "JOIN la_fioreria.flower_batch fb ON fb.batch_id = br.batch_id\n"
                + "JOIN la_fioreria.flower_type ft ON ft.flower_id = fb.flower_id\n"
                + "WHERE ft.flower_id = ? \n"
                + "AND ws.status <> 'SHOPPING'";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, flowerId);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
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

                WholeSale ws = new WholeSale(id, user_id, bouquet_id, requested_quantity, note, quoted_price, total_price, quoted_at, responded_at, created_at, status, expense);
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

    public void AssignQuotedPrice(int totalPrice, LocalDate quotedDate, int quotedPrice, int uid, LocalDate requestDate, String status, int bid, int expense) {
        String sql = "UPDATE `la_fioreria`.`wholesale_quote_request`\n"
                + "SET\n"
                + "`quoted_price` = ?,\n"
                + "`total_price` = ?,\n"
                + "`quoted_at` = ?,\n"
                + "`status` = 'QUOTED',\n"
                + "`expense` = ?\n"
                + "WHERE user_id = ?\n"
                + "AND created_at = ?\n"
                + "AND status = ?\n"
                + "AND bouquet_id = ?";

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
            ps.setInt(8, bid);
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

    public List<WholeSaleFlower> listFlowerInRequest() {
        List<WholeSaleFlower> listWsFlower = new ArrayList<>();
        String sql = "SELECT * FROM la_fioreria.wholesale_quote_flower_detail;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int wsId = rs.getInt("wholesale_request_id");
                int bouquetId = rs.getInt("bouquet_id");
                int flower_id = rs.getInt("flower_id");
                int flowerPrice = rs.getInt("flower_ws_price");

                WholeSaleFlower wsFlower = new WholeSaleFlower(id, wsId, bouquetId, flower_id, flowerPrice);
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

    public void insertIntoFlowerWS(WholeSaleFlower wsFlower) {
        String sql = "INSERT INTO `la_fioreria`.`wholesale_quote_flower_detail`\n"
                + "(`wholesale_request_id`,\n"
                + "`bouquet_id`,\n"
                + "`flower_id`,\n"
                + "`flower_ws_price`)\n"
                + "VALUES\n"
                + "(?,?,?,?);";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, wsFlower.getWholesale_request_id());
            ps.setInt(2, wsFlower.getBouquet_id());
            ps.setInt(3, wsFlower.getFlower_id());
            ps.setInt(4, wsFlower.getFlower_ws_price());
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

    public List<WholeSale> getWholeSaleQuotedList(int uid, LocalDate requestDate) {
        List<WholeSale> list = new ArrayList<>();
        String sql = "SELECT * FROM la_fioreria.wholesale_quote_request \n"
                + "WHERE user_id = ? \n"
                + "AND status = 'QUOTED' \n"
                + "AND created_at = ?;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setDate(2, java.sql.Date.valueOf(requestDate));
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

                String status = rs.getString("status");
                int expense = rs.getInt("expense");

                WholeSale ws = new WholeSale(id, uid, bouquet_id, requested_quantity, note, quoted_price, total_price, quoted_at, responded_at, requestDate, status, expense);
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

    public void markAsEmailedForQuotedList(int uid, LocalDate requestDate) {
        String sql = "UPDATE la_fioreria.wholesale_quote_request \n"
                + "SET status = 'EMAILED' \n"
                + "WHERE user_id = ? \n"
                + "AND created_at = ? \n"
                + "AND status = 'QUOTED';";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setDate(2, java.sql.Date.valueOf(requestDate));

            int updatedRows = ps.executeUpdate();
            System.out.println("Updated rows to EMAILED: " + updatedRows);
        } catch (SQLException e) {
            System.out.println("Error updating status to EMAILED: " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public void updateWholeSaleStatus(int uid, LocalDate requestDate, String status) {
        String sql = "UPDATE la_fioreria.wholesale_quote_request \n"
                + "SET status = ? \n"
                + "WHERE user_id = ? \n"
                + "AND created_at = ? \n"
                + "AND status = 'EMAILED';";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, uid);
            ps.setDate(3, java.sql.Date.valueOf(requestDate));
            int updatedRows = ps.executeUpdate();
            System.out.println("Updated rows to EMAILED: " + updatedRows);
        } catch (SQLException e) {
            System.out.println("Error updating status to EMAILED: " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.out.println("Error closing resources: " + e.getMessage());
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
    
    public static void main(String[] args) {
        WholeSaleDAO dao = new WholeSaleDAO();
        List<WholeSale> list = dao.getWholeSaleRequestByUserID(13);
        WholeSale ws = new WholeSale();

        System.out.println(dao.getWholeSaleDetail(13, LocalDate.parse("2025-07-14"), "QUOTED", 2));

    }
}
