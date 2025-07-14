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

                WholeSale ws = new WholeSale(id, user_id, bouquet_id, requested_quantity, note, quoted_price, total_price, quoted_at, responded_at, created_at, status);
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
                + " `status`)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

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

            ps.setDate(9, java.sql.Date.valueOf(ws.getCreated_at()));
            ps.setString(10, ws.getStatus());

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

    public List<WholeSale> getWholeSaleRequestByUserID(int uid) {
        List<WholeSale> list = new ArrayList<>();

        String sql = "SELECT * FROM la_fioreria.wholesale_quote_request WHERE user_id = ?";

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

                WholeSale ws = new WholeSale(id, uid, bouquet_id, requested_quantity, note, quoted_price, total_price, quoted_at, responded_at, created_at, status);
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

                WholeSale ws = new WholeSale(id, uid, bouquet_id, requested_quantity, note, quoted_price, total_price, quoted_at, responded_at, created_at, status);
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


    public static void main(String[] args) {
        WholeSaleDAO dao = new WholeSaleDAO();
        List<WholeSale> list = dao.getWholeSaleRequestByUserID(13);
        dao.UpdateWholeSaleRequest(13, 2, 60, "test update");
        System.out.println(list);
    }
}
