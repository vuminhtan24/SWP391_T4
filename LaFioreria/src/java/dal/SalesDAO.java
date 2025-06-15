/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.SalesRecord;

/**
 *
 * @author LAPTOP
 */
public class SalesDAO extends BaseDao {

    public Map<String, Double> getRevenueByDateThisMonth() {
        Map<String, Double> map = new LinkedHashMap<>();
        String sql = "SELECT o.order_date, SUM(oi.quantity * oi.unit_price) AS daily_revenue "
                + "FROM `order` o JOIN order_item oi ON o.order_id = oi.order_id "
                + "WHERE MONTH(o.order_date) = MONTH(CURDATE()) AND YEAR(o.order_date) = YEAR(CURDATE()) "
                + "GROUP BY o.order_date ORDER BY o.order_date";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("order_date"), rs.getDouble("daily_revenue"));
            }
        } catch (SQLException e) {
        }
        return map;
    }

    public double getTodayRevenue() {
        String sql = "SELECT SUM(oi.quantity * oi.unit_price) AS today_revenue "
                + "FROM `order` o JOIN order_item oi ON o.order_id = oi.order_id "
                + "WHERE o.order_date = CURDATE()";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("today_revenue");
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public double getTotalRevenue() {
        String sql = "SELECT SUM(oi.quantity * oi.unit_price) AS total_revenue "
                + "FROM `order` o JOIN order_item oi ON o.order_id = oi.order_id";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_revenue");
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public List<SalesRecord> getSalesReportThisMonth() {
        List<SalesRecord> list = new ArrayList<>();
        String sql = "SELECT o.order_id, o.order_date, u.Fullname AS customer_name, "
                + "b.bouquet_name, c.category_name, oi.quantity, oi.unit_price, "
                + "(oi.quantity * oi.unit_price) AS total_price, os.status_name "
                + "FROM `order` o "
                + "JOIN order_item oi ON o.order_id = oi.order_id "
                + "JOIN bouquet b ON oi.bouquet_id = b.Bouquet_ID "
                + "JOIN category c ON b.cid = c.category_id "
                + "JOIN user u ON o.customer_id = u.User_ID "
                + "JOIN order_status os ON o.status_id = os.order_status_id "
                + "WHERE MONTH(o.order_date) = MONTH(CURDATE()) "
                + "AND YEAR(o.order_date) = YEAR(CURDATE()) "
                + "ORDER BY o.order_date DESC";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                SalesRecord r = new SalesRecord();
                r.setOrderID(rs.getInt("order_id"));
                r.setOrderDate(rs.getString("order_date"));
                r.setCustomerName(rs.getString("customer_name"));
                r.setProductName(rs.getString("bouquet_name"));
                r.setCategoryName(rs.getString("category_name"));
                r.setQuantity(rs.getInt("quantity"));
                r.setUnitPrice(rs.getDouble("unit_price"));
                r.setTotalPrice(rs.getDouble("total_price"));
                r.setStatus(rs.getString("status_name"));
                list.add(r);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public int getTodayOrderCount() {
        String sql = "SELECT COUNT(DISTINCT o.order_id) AS today_orders "
                + "FROM `order` o WHERE o.order_date = CURDATE()";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("today_orders");
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public double getThisMonthRevenue() {
        String sql = "SELECT SUM(oi.quantity * oi.unit_price) AS total_monthly_revenue "
                + "FROM `order` o JOIN order_item oi ON o.order_id = oi.order_id "
                + "WHERE MONTH(o.order_date) = MONTH(CURDATE()) "
                + "AND YEAR(o.order_date) = YEAR(CURDATE())";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_monthly_revenue");
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    // 6. Doanh thu nhóm theo từng ngày (tất cả thời gian)
    public Map<String, Double> getRevenueGroupedAllTime() {
        Map<String, Double> map = new LinkedHashMap<>();
        String sql = "SELECT o.order_date, SUM(oi.quantity * oi.unit_price) AS daily_revenue "
                + "FROM `order` o JOIN order_item oi ON o.order_id = oi.order_id "
                + "GROUP BY o.order_date ORDER BY o.order_date DESC";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("order_date"), rs.getDouble("daily_revenue"));
            }
        } catch (SQLException e) {
        }
        return map;
    }

    public Map<String, Double> getRevenueGroupedThisMonth() {
        Map<String, Double> map = new LinkedHashMap<>();
        String sql = "SELECT o.order_date, SUM(oi.quantity * oi.unit_price) AS daily_revenue "
                + "FROM `order` o JOIN order_item oi ON o.order_id = oi.order_id "
                + "WHERE MONTH(o.order_date) = MONTH(CURDATE()) "
                + "AND YEAR(o.order_date) = YEAR(CURDATE()) "
                + "GROUP BY o.order_date ORDER BY o.order_date DESC";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("order_date"), rs.getDouble("daily_revenue"));
            }
        } catch (SQLException e) {
        }
        return map;
    }

    // 8. Doanh thu theo ngày + lọc theo category
    public Map<String, Double> getRevenueGroupedByCategory(int categoryId) {
        Map<String, Double> map = new LinkedHashMap<>();
        String sql = "SELECT o.order_date, SUM(oi.quantity * oi.unit_price) AS daily_revenue "
                + "FROM `order` o JOIN order_item oi ON o.order_id = oi.order_id "
                + "JOIN bouquet b ON oi.bouquet_id = b.Bouquet_ID "
                + "WHERE MONTH(o.order_date) = MONTH(CURDATE()) "
                + "AND YEAR(o.order_date) = YEAR(CURDATE()) "
                + "AND b.cid = ? "
                + "GROUP BY o.order_date ORDER BY o.order_date DESC";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("order_date"), rs.getDouble("daily_revenue"));
            }
        } catch (SQLException e) {
        }
        return map;
    }
}
