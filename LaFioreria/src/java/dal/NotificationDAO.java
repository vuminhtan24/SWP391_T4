package dal;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Notification;

public class NotificationDAO extends BaseDao {

    public List<Notification> getAll() throws SQLException {
        String sql = "SELECT * FROM notifications";
        List<Notification> list = new ArrayList<>();

        try (Connection conn = dbc.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Notification n = new Notification();
                    n.setNotificationId(rs.getInt("notification_id"));
                    n.setUserId(rs.getInt("user_id"));
                    n.setMessage(rs.getString("message"));
                    n.setCreatedAt(rs.getTimestamp("created_at"));
                    n.setStatus(rs.getString("status"));
                    list.add(n);
                }
            }
        }
        return list;
    }

//    public List<Notification> getUnreadNotificationsByUser(int userId) throws SQLException {
//        String sql = "SELECT notification_id, user_id, message, created_at, status "
//                + "FROM notifications WHERE status = 'unread' AND user_id = ?";
//        List<Notification> list = new ArrayList<>();
//
//        try (Connection conn = dbc.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, userId);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    Notification n = new Notification();
//                    n.setNotificationId(rs.getInt("notification_id"));
//                    n.setUserId(rs.getInt("user_id"));
//                    n.setMessage(rs.getString("message"));
//                    n.setCreatedAt(rs.getTimestamp("created_at"));
//                    n.setStatus(rs.getString("status"));
//                    list.add(n);
//                }
//            }
//        }
//        return list;
//    }
    public List<Notification> getUnreadNotificationsByUser(int userId) throws SQLException {
        String sql = "SELECT notification_id, user_id, message, created_at, status "
                + "FROM notifications WHERE status = 'unread' AND user_id = ? ORDER BY created_at DESC";
        List<Notification> list = new ArrayList<>();

        try (Connection conn = dbc.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            System.out.println("Executing query for userId: " + userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Notification n = new Notification();
                    n.setNotificationId(rs.getInt("notification_id"));
                    n.setUserId(rs.getInt("user_id"));
                    n.setMessage(rs.getString("message"));
                    n.setCreatedAt(rs.getTimestamp("created_at"));
                    n.setStatus(rs.getString("status"));
                    list.add(n);
                }
                System.out.println("Found " + list.size() + " unread notifications.");
            }
        }
        return list;
    }

    public void markNotificationsAsRead(List<Notification> list) throws SQLException {
        if (list == null || list.isEmpty()) {
            return;
        }
        String sql = "UPDATE notifications SET status = 'read' WHERE notification_id = ?";
        try (Connection conn = dbc.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int batchSize = 100;
            for (int i = 0; i < list.size(); i++) {
                ps.setInt(1, list.get(i).getNotificationId());
                ps.addBatch();
                if (i % batchSize == 0 || i == list.size() - 1) {
                    ps.executeBatch();
                }
            }
        }
    }

    public void updateStatus(int notificationId, String status) throws SQLException {
        String sql = "UPDATE notifications SET status = ? WHERE notification_id = ?";
        try (Connection conn = dbc.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, notificationId);
            ps.executeUpdate();
        }
    }

    public void deleteNotification(int notificationId) throws SQLException {
        String sql = "DELETE FROM notifications WHERE notification_id = ?";
        try (Connection conn = dbc.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, notificationId);
            ps.executeUpdate();
        }
    }

    public static void main(String[] args) throws SQLException {
        NotificationDAO dao = new NotificationDAO();
        List<Notification> list = dao.getAll();
        System.out.println(list);
    }
}
