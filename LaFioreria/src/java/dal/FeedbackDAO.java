package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedbackDAO extends BaseDao {

    public boolean hasCustomerPurchasedBouquet(int customerId, int bouquetId) {
        String sql = "SELECT COUNT(*) FROM `order` o " +
                     "JOIN order_item oi ON o.order_id = oi.order_id " +
                     "WHERE o.customer_id = ? AND oi.bouquet_id = ? AND o.status_id = 4"; // Shipped or Delivered
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, customerId);
            stmt.setInt(2, bouquetId);
            ResultSet rs = stmt.executeQuery();
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

    public int insertFeedback(int customerId, int bouquetId, int rating, String comment) {
        String sql = "INSERT INTO feedback (customer_id, bouquet_id, rating, comment) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, customerId);
            stmt.setInt(2, bouquetId);
            stmt.setInt(3, rating);
            stmt.setString(4, comment);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return feedback_id
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
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, feedbackId);
            stmt.setString(2, imageUrl);
            stmt.executeUpdate();
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
}