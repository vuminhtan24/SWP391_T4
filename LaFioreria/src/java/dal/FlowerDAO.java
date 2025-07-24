/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author LAPTOP
 */
import java.sql.*;
import java.util.*;
import model.StatResult;

public class FlowerDAO extends BaseDao {

    // 1. Lý do lỗi theo loại hoa
//    public Map<String, Integer> getDiscardReasonByFlowerType() {
//        Map<String, Integer> map = new LinkedHashMap<>();
//        String sql = "SELECT ft.flower_name, COUNT(*) AS count "
//                + "FROM flower_discard fd "
//                + "JOIN flower_batch fb ON fd.batch_id = fb.batch_id "
//                + "JOIN flower_type ft ON fb.flower_id = ft.flower_id "
//                + "GROUP BY ft.flower_name";
//        try {
//            connection = dbc.getConnection();
//            ps = connection.prepareStatement(sql);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                map.put(rs.getString("flower_name"), rs.getInt("count"));
//            }
//        } catch (SQLException e) {
//        }
//        return map;
//    }
    public Map<String, Integer> getDiscardReasonByFlowerType() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT ft.flower_name, COUNT(*) AS count "
                + "FROM repair_history rh "
                + "JOIN flower_batch fb ON rh.old_batch_id = fb.batch_id "
                + "JOIN flower_type ft ON fb.flower_id = ft.flower_id "
                + "GROUP BY ft.flower_name";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("flower_name"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }

// 2. Hoa hết hạn theo thời gian theo loại hoa (line chart)
//    public List<StatResult> getExpiredFlowerByMonthAndCategory() {
//        List<StatResult> list = new ArrayList<>();
//        String sql = "SELECT DATE_FORMAT(fd.discard_date, '%Y-%m') AS month, "
//                + "ft.flower_name, SUM(fd.quantity) AS total_expired "
//                + "FROM flower_discard fd "
//                + "JOIN flower_batch fb ON fd.batch_id = fb.batch_id "
//                + "JOIN flower_type ft ON fb.flower_id = ft.flower_id "
//                + "WHERE fd.reason = 'Quá hạn' "
//                + "GROUP BY month, ft.flower_name ORDER BY month";
//        try {
//            connection = dbc.getConnection();
//            ps = connection.prepareStatement(sql);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                String label = rs.getString("month") + " - " + rs.getString("flower_name");
//                int totalExpired = rs.getInt("total_expired");
//
//                StatResult r = new StatResult();
//                r.setLabel(label);
//                r.setValue(totalExpired);
//                list.add(r);
//            }
//
//            // Dữ liệu mẫu (nếu cần)
//            list.add(new StatResult("2025-06 - Rose", 0, 0) {
//                {
//                    setValue(10);
//                }
//            });
//            list.add(new StatResult("2025-07 - Lily", 0, 0) {
//                {
//                    setValue(15);
//                }
//            });
//
//        } catch (SQLException e) {
//        }
//        return list;
//    }
    public List<StatResult> getExpiredFlowerByMonthAndCategory() {
        List<StatResult> list = new ArrayList<>();
        String sql = "SELECT DATE_FORMAT(fb.expiration_date, '%Y-%m') AS month, "
                + "ft.flower_name, COUNT(*) AS total_expired_batches "
                + "FROM flower_batch fb "
                + "JOIN flower_type ft ON fb.flower_id = ft.flower_id "
                + "WHERE fb.status = 'expired' "
                + "GROUP BY month, ft.flower_name "
                + "ORDER BY month, ft.flower_name";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String label = rs.getString("month") + " - " + rs.getString("flower_name");
                int totalExpired = rs.getInt("total_expired_batches");

                StatResult r = new StatResult();
                r.setLabel(label);
                r.setValue(totalExpired);
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Đừng bỏ trống khối catch
        } finally {
            try {
                closeResources(); // Đảm bảo bạn có closeConnection, ResultSet, Statement
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public static void main(String[] args) {
        testFlowerCharts();
    }

    public static void testFlowerCharts() {
        FlowerDAO dao = new FlowerDAO();

        System.out.println("==== 1. Lý do loại bỏ theo loại hoa ====");
        Map<String, Integer> discardByType = dao.getDiscardReasonByFlowerType();
        if (discardByType != null && !discardByType.isEmpty()) {
            for (Map.Entry<String, Integer> entry : discardByType.entrySet()) {
                System.out.println("Loại hoa: " + entry.getKey() + " - Số lỗi: " + entry.getValue());
            }
        } else {
            System.out.println("Không có dữ liệu loại bỏ theo loại hoa.");
        }

        System.out.println("\n==== 2. Hoa hết hạn theo tháng và loại hoa ====");
        List<StatResult> expiredList = dao.getExpiredFlowerByMonthAndCategory();
        if (expiredList != null && !expiredList.isEmpty()) {
            for (StatResult r : expiredList) {
                System.out.println("Thời gian: " + r.getLabel() + " - Số lượng hết hạn: " + r.getValue());
            }
        } else {
            System.out.println("Không có dữ liệu hoa hết hạn theo thời gian.");
        }
    }

}
