package dal;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Feedback;
import model.FeedbackImg;

public class FeedbackDAO extends BaseDao {

    public List<Feedback> getAllFeedbacks(String search, Integer bouquetId, Integer rating) {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT DISTINCT f.feedback_id, f.customer_id, f.bouquet_id, f.rating, f.comment, f.created_at, f.status, "
                + "COALESCE(u.Fullname, o.customer_name) AS customer_name "
                + "FROM feedback f "
                + "LEFT JOIN `order` o ON f.customer_id = o.customer_id AND f.order_id = o.order_id "
                + "LEFT JOIN `user` u ON f.customer_id = u.User_ID "
                + "WHERE 1=1 ";

        // Add search condition
        if (search != null && !search.trim().isEmpty()) {
            sql += "AND (f.comment LIKE ? OR COALESCE(u.Fullname, o.customer_name) LIKE ?) ";
        }
        // Add bouquet_id filter
        if (bouquetId != null && bouquetId > 0) {
            sql += "AND f.bouquet_id = ? ";
        }
        // Add rating filter
        if (rating != null && rating > 0) {
            sql += "AND f.rating = ? ";
        }

        sql += "ORDER BY f.created_at DESC";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            int paramIndex = 1;
            if (search != null && !search.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + search + "%");
                ps.setString(paramIndex++, "%" + search + "%");
            }
            if (bouquetId != null && bouquetId > 0) {
                ps.setInt(paramIndex++, bouquetId);
            }
            if (rating != null && rating > 0) {
                ps.setInt(paramIndex++, rating);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setFeedbackId(rs.getInt("feedback_id"));
                feedback.setCustomerId(rs.getInt("customer_id"));
                feedback.setBouquetId(rs.getInt("bouquet_id"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setComment(rs.getString("comment"));
                Timestamp timestamp = rs.getTimestamp("created_at");
                feedback.setCreated_at(timestamp != null ? timestamp.toLocalDateTime() : null);
                feedback.setStatus(rs.getString("status"));
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return feedbacks;
    }

    public Feedback getFeedbackById(int feedbackId) {
        Feedback feedback = null;
        String sql = "SELECT f.feedback_id, f.customer_id, f.bouquet_id, f.order_id, f.rating, f.comment, f.created_at, f.status, "
                + "COALESCE(u.Fullname, o.customer_name, 'Khách') AS customer_name, b.bouquet_name "
                + "FROM feedback f "
                + "LEFT JOIN `order` o ON f.customer_id = o.customer_id AND f.order_id = o.order_id "
                + "LEFT JOIN `user` u ON f.customer_id = u.User_ID "
                + "LEFT JOIN bouquet b ON f.bouquet_id = b.bouquet_id "
                + "WHERE f.feedback_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, feedbackId);
            rs = ps.executeQuery();
            if (rs.next()) {
                feedback = new Feedback();
                feedback.setFeedbackId(rs.getInt("feedback_id"));
                feedback.setCustomerId(rs.getInt("customer_id"));
                feedback.setBouquetId(rs.getInt("bouquet_id"));
                feedback.setOrderId(rs.getInt("order_id"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setComment(rs.getString("comment"));
                Timestamp timestamp = rs.getTimestamp("created_at");
                feedback.setCreated_at(timestamp != null ? timestamp.toLocalDateTime() : null);
                feedback.setStatus(rs.getString("status"));
                feedback.setBouquetName(rs.getString("bouquet_name") != null ? rs.getString("bouquet_name") : "Unknown Bouquet");
                feedback.setCustomerName(rs.getString("customer_name")); // Thêm thuộc tính customerName
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return feedback;
    }

    public boolean hasCustomerPurchasedBouquet(int customerId, int bouquetId) {
        String sql = "SELECT COUNT(*) FROM `order` o "
                + "JOIN order_item oi ON o.order_id = oi.order_id "
                + "WHERE o.customer_id = ? AND oi.bouquet_id = ? AND o.status_id = 4";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setInt(2, bouquetId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return false;
    }

    public boolean canWriteFeedback(int customerId, int bouquetId, int orderId) {
        String sql = "SELECT COUNT(*) FROM `order` o "
                + "JOIN order_item oi ON o.order_id = oi.order_id "
                + "WHERE o.customer_id = ? AND oi.bouquet_id = ? AND o.order_id = ? AND o.status_id = 4 "
                + "AND NOT EXISTS (SELECT 1 FROM feedback f WHERE f.customer_id = ? AND f.bouquet_id = ? AND f.order_id = ?) "
                + "AND o.order_date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setInt(2, bouquetId);
            ps.setInt(3, orderId);
            ps.setInt(4, customerId);
            ps.setInt(5, bouquetId);
            ps.setInt(6, orderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return false;
    }

    public int insertFeedback(int customerId, int bouquetId, int orderId, int rating, String comment) {
        String sql = "INSERT INTO feedback (customer_id, bouquet_id, order_id, rating, comment, created_at, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, 'pending')";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, customerId);
            ps.setInt(2, bouquetId);
            ps.setInt(3, orderId);
            ps.setInt(4, rating);
            ps.setString(5, comment);
            ps.setObject(6, LocalDateTime.now());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to insert feedback: " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return -1;
    }

    public void insertFeedbackImage(int feedbackId, String imageUrl) {
        String sql = "INSERT INTO feedback_images (feedback_id, image_url) VALUES (?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, feedbackId);
            ps.setString(2, imageUrl);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to insert feedback image: " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public List<Feedback> getFeedbacksByBouquetId(int bouquetId) {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT DISTINCT f.feedback_id, f.customer_id, f.bouquet_id, f.rating, f.comment, f.created_at, f.status, "
                + "COALESCE(u.Fullname, o.customer_name) AS customer_name "
                + "FROM feedback f "
                + "LEFT JOIN `order` o ON f.customer_id = o.customer_id "
                + "LEFT JOIN `user` u ON f.customer_id = u.User_ID "
                + "WHERE f.bouquet_id = ? AND f.status = 'approved' "
                + "ORDER BY f.created_at DESC";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bouquetId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setFeedbackId(rs.getInt("feedback_id"));
                feedback.setCustomerId(rs.getInt("customer_id"));
                feedback.setBouquetId(rs.getInt("bouquet_id"));
                feedback.setRating(rs.getInt("rating"));
                String comment = rs.getString("comment"); // Lấy giá trị comment
                feedback.setComment(comment); // Gán vào đối tượng
                Timestamp timestamp = rs.getTimestamp("created_at");
                feedback.setCreated_at(timestamp != null ? timestamp.toLocalDateTime() : null);
                feedback.setStatus(rs.getString("status"));
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return feedbacks;
    }

    public List<String> getFeedbackImageUrls(int feedbackId) {
        List<String> imageUrls = new ArrayList<>();
        String sql = "SELECT image_url FROM feedback_images WHERE feedback_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, feedbackId);
            rs = ps.executeQuery();
            while (rs.next()) {
                imageUrls.add(rs.getString("image_url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return imageUrls;
    }

    public String getCustomerNameFromOrderOrUser(int customerId) {
        String sql = "SELECT COALESCE(u.Fullname, o.customer_name, 'Khách') AS customer_name "
                + "FROM `order` o "
                + "LEFT JOIN `user` u ON o.customer_id = u.User_ID "
                + "WHERE o.customer_id = ? LIMIT 1";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("customer_name");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getCustomerNameFromOrderOrUser: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return "Khách";
    }

    public void updateFeedbackStatus(int feedbackId, String status) {
        String sql = "UPDATE feedback SET status = ? WHERE feedback_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, feedbackId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update feedback status: " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) {
        FeedbackDAO dao = new FeedbackDAO();
        boolean list = dao.canWriteFeedback(12, 12, 51);
        System.out.println(list);
    }
}
