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
import model.Category;
import model.SalesRecord;
import model.StatResult;

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

    public List<SalesRecord> getSalesReport(String filterType) {
        List<SalesRecord> list = new ArrayList<>();
        String condition = "";

        switch (filterType) {
            case "week":
                condition = "WEEK(o.order_date, 1) = WEEK(CURDATE(), 1) AND YEAR(o.order_date) = YEAR(CURDATE())";
                break;
            case "month":
                condition = "MONTH(o.order_date) = MONTH(CURDATE()) AND YEAR(o.order_date) = YEAR(CURDATE())";
                break;
            case "year":
                condition = "YEAR(o.order_date) = YEAR(CURDATE())";
                break;
            case "all":
            default:
                condition = "1=1"; // không lọc gì cả
                break;
        }

        String sql = "SELECT o.order_id, o.order_date, u.Fullname AS customer_name, "
                + "b.bouquet_name, c.category_name, oi.quantity, oi.unit_price, "
                + "(oi.quantity * oi.unit_price) AS total_price, os.status_name "
                + "FROM `order` o "
                + "JOIN order_item oi ON o.order_id = oi.order_id "
                + "JOIN bouquet b ON oi.bouquet_id = b.Bouquet_ID "
                + "JOIN category c ON b.cid = c.category_id "
                + "LEFT JOIN user u ON o.customer_id = u.User_ID " // 👈 Sửa ở đây
                + "JOIN order_status os ON o.status_id = os.order_status_id "
                + "WHERE " + condition + " "
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

    public Map<String, Double> getRevenueGroupedBetweenDates(String from, String to) {
        Map<String, Double> map = new LinkedHashMap<>();
        String sql = "SELECT o.order_date, SUM(oi.quantity * oi.unit_price) AS daily_revenue "
                + "FROM `order` o JOIN order_item oi ON o.order_id = oi.order_id "
                + "WHERE o.order_date BETWEEN ? AND ? "
                + "GROUP BY o.order_date ORDER BY o.order_date";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, from);
            ps.setString(2, to);
            rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("order_date"), rs.getDouble("daily_revenue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("order_date"), rs.getDouble("daily_revenue"));
            }
        } catch (SQLException e) {
        }
        return map;
    }

    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT category_id, category_name FROM category";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryName(rs.getString("category_name"));
                list.add(c);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public List<StatResult> getMonthlyStats(int year) {
        List<StatResult> list = new ArrayList<>();
        String sql = "SELECT MONTH(o.order_date) AS month, "
                + "COUNT(DISTINCT o.order_id) AS total_orders, "
                + "SUM(oi.quantity * oi.unit_price) AS total_revenue "
                + "FROM `order` o JOIN order_item oi ON o.order_id = oi.order_id "
                + "WHERE YEAR(o.order_date) = ? "
                + "GROUP BY MONTH(o.order_date) ORDER BY MONTH(o.order_date)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, year);
            rs = ps.executeQuery();
            while (rs.next()) {
                String label = "Month " + rs.getInt("month");
                double revenue = rs.getDouble("total_revenue");
                int orders = rs.getInt("total_orders");
                list.add(new StatResult(label, revenue, orders));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public List<StatResult> getYearlyStats() {
        List<StatResult> list = new ArrayList<>();
        String sql = "SELECT YEAR(o.order_date) AS year, "
                + "COUNT(DISTINCT o.order_id) AS total_orders, "
                + "SUM(oi.quantity * oi.unit_price) AS total_revenue "
                + "FROM `order` o JOIN order_item oi ON o.order_id = oi.order_id "
                + "GROUP BY YEAR(o.order_date) ORDER BY year";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String label = "Year " + rs.getInt("year");
                double revenue = rs.getDouble("total_revenue");
                int orders = rs.getInt("total_orders");
                list.add(new StatResult(label, revenue, orders));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public List<StatResult> getWeekdayStats() {
        List<StatResult> list = new ArrayList<>();
        String sql = "SELECT DAYNAME(o.order_date) AS weekday, "
                + "COUNT(DISTINCT o.order_id) AS total_orders, "
                + "SUM(oi.quantity * oi.unit_price) AS total_revenue "
                + "FROM `order` o JOIN order_item oi ON o.order_id = oi.order_id "
                + "GROUP BY weekday "
                + "ORDER BY FIELD(weekday, 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday')";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String label = rs.getString("weekday");
                double revenue = rs.getDouble("total_revenue");
                int orders = rs.getInt("total_orders");
                list.add(new StatResult(label, revenue, orders));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public Map<String, Integer> getDiscardReasonStats() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT reason, SUM(quantity) AS total_discarded "
                + "FROM flower_discard GROUP BY reason ORDER BY total_discarded DESC";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("reason"), rs.getInt("total_discarded"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map<String, Integer> getTotalImportVsWaste() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sqlImport = "SELECT SUM(quantity) AS total_imported FROM flower_batch";
        String sqlWaste = "SELECT SUM(quantity) AS total_discarded FROM flower_discard";
        try {
            connection = dbc.getConnection();

            // Tổng nhập
            ps = connection.prepareStatement(sqlImport);
            rs = ps.executeQuery();
            int imported = 0;
            if (rs.next()) {
                imported = rs.getInt("total_imported");
            }

            // Tổng hỏng
            ps = connection.prepareStatement(sqlWaste);
            rs = ps.executeQuery();
            int wasted = 0;
            if (rs.next()) {
                wasted = rs.getInt("total_discarded");
            }

            // Tính dùng được = nhập - hỏng
            map.put("Available (for sale)", imported - wasted);
            map.put("Loss (damaged/overdue/rejected)", wasted);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

}
