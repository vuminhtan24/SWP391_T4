package dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.DiscountCode; // Đảm bảo import đúng model của bạn

public class DiscountCodeDAO extends BaseDao { // Giả sử BaseDao là lớp cơ sở để quản lý kết nối DB

    // Phương thức hiện có để lấy tất cả (có thể không cần dùng trực tiếp nữa nếu dùng getFilteredDiscountCodesPaged)
    public List<DiscountCode> getAllDiscountCodes() {
        List<DiscountCode> list = new ArrayList<>();
        String sql = "SELECT * FROM discount_code"; // Thay thế bằng tên bảng thực tế của bạn
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToDiscountCode(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all discount codes: " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return list;
    }

    /**
     * Counts the total number of discount codes based on search term and filter
     * status.
     *
     * @param searchTerm The term to search for in code or description.
     * @param filterStatus The status to filter by (e.g., "active", "inactive",
     * "expired", "upcoming", "used_up").
     * @return The total count of matching discount codes.
     */
    public int countFilteredDiscountCodes(String searchTerm, String filterStatus) {
        int count = 0;
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) FROM discount_code WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // Add search condition
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sqlBuilder.append(" AND (code LIKE ? OR description LIKE ?)");
            params.add("%" + searchTerm.trim() + "%");
            params.add("%" + searchTerm.trim() + "%");
        }

        // Add status filter condition
        if (filterStatus != null && !filterStatus.isEmpty()) {
            Date now = new Date();
            Timestamp currentTimestamp = new Timestamp(now.getTime());

            switch (filterStatus) {
                case "active":
                    sqlBuilder.append(" AND active = TRUE AND start_date <= ? AND end_date >= ? AND (usage_limit IS NULL OR used_count < usage_limit)");
                    params.add(currentTimestamp);
                    params.add(currentTimestamp);
                    break;
                case "inactive":
                    sqlBuilder.append(" AND active = FALSE");
                    break;
                case "expired":
                    sqlBuilder.append(" AND end_date < ?");
                    params.add(currentTimestamp);
                    break;
                case "upcoming":
                    sqlBuilder.append(" AND start_date > ?");
                    params.add(currentTimestamp);
                    break;
                case "used_up":
                    sqlBuilder.append(" AND usage_limit IS NOT NULL AND used_count >= usage_limit");
                    break;
                // "All" case is handled by default (no additional WHERE clause)
            }
        }

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sqlBuilder.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error counting filtered discount codes: " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return count;
    }

    /**
     * Retrieves a paginated list of discount codes based on search term and
     * filter status.
     *
     * @param searchTerm The term to search for in code or description.
     * @param filterStatus The status to filter by (e.g., "active", "inactive",
     * "expired", "upcoming", "used_up").
     * @param offset The starting offset for pagination.
     * @param pageSize The number of items to retrieve per page.
     * @return A list of matching discount codes.
     */
    public List<DiscountCode> getFilteredDiscountCodesPaged(String searchTerm, String filterStatus, int offset, int pageSize) {
        List<DiscountCode> list = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM discount_code WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // Add search condition
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sqlBuilder.append(" AND (code LIKE ? OR description LIKE ?)");
            params.add("%" + searchTerm.trim() + "%");
            params.add("%" + searchTerm.trim() + "%");
        }

        // Add status filter condition
        if (filterStatus != null && !filterStatus.isEmpty()) {
            Date now = new Date();
            Timestamp currentTimestamp = new Timestamp(now.getTime());

            switch (filterStatus) {
                case "active":
                    sqlBuilder.append(" AND active = TRUE AND start_date <= ? AND end_date >= ? AND (usage_limit IS NULL OR used_count < usage_limit)");
                    params.add(currentTimestamp);
                    params.add(currentTimestamp);
                    break;
                case "inactive":
                    sqlBuilder.append(" AND active = FALSE");
                    break;
                case "expired":
                    sqlBuilder.append(" AND end_date < ?");
                    params.add(currentTimestamp);
                    break;
                case "upcoming":
                    sqlBuilder.append(" AND start_date > ?");
                    params.add(currentTimestamp);
                    break;
                case "used_up":
                    sqlBuilder.append(" AND usage_limit IS NOT NULL AND used_count >= usage_limit");
                    break;
            }
        }

        sqlBuilder.append(" ORDER BY start_date DESC LIMIT ? OFFSET ?"); // Order and paginate
        params.add(pageSize);
        params.add(offset);

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sqlBuilder.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToDiscountCode(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting filtered discount codes paged: " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return list;
    }

    // Phương thức ánh xạ ResultSet sang đối tượng DiscountCode
    private DiscountCode mapResultSetToDiscountCode(ResultSet rs) throws SQLException {
        DiscountCode dc = new DiscountCode();
        dc.setId(rs.getInt("id"));
        dc.setCode(rs.getString("code"));
        dc.setDescription(rs.getString("description"));
        dc.setType(rs.getString("discount_type"));
        dc.setValue(rs.getBigDecimal("discount_value"));
        dc.setMaxDiscount(rs.getBigDecimal("max_discount"));
        dc.setMinOrderAmount(rs.getBigDecimal("min_order_amount"));
        dc.setStartDate(rs.getTimestamp("start_date"));
        dc.setEndDate(rs.getTimestamp("end_date"));
        dc.setUsageLimit(rs.getObject("usage_limit") != null ? rs.getInt("usage_limit") : null); // Handle nullable Integer
        dc.setUsedCount(rs.getInt("used_count"));
        dc.setActive(rs.getBoolean("active"));
        return dc;
    }

    public DiscountCode getValidDiscount(String code, BigDecimal orderTotal) {
        DiscountCode discount = null;
        String sql = "SELECT * FROM discount_code "
                + "WHERE code = ? AND active = TRUE "
                + "AND start_date <= NOW() AND end_date >= NOW()";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            if (rs.next()) {
                BigDecimal minOrder = rs.getBigDecimal("min_order_amount");
                // Check for null usage_limit before getting int
                Integer usageLimitObj = (Integer) rs.getObject("usage_limit");
                int usageLimit = (usageLimitObj != null) ? usageLimitObj.intValue() : 0; // 0 for unlimited
                int used = rs.getInt("used_count");

                boolean validOrderAmount = (minOrder == null || orderTotal.compareTo(minOrder) >= 0);
                boolean validUsage = (usageLimit == 0 || used < usageLimit);

                if (validOrderAmount && validUsage) {
                    discount = new DiscountCode();
                    discount.setId(rs.getInt("id"));
                    discount.setCode(rs.getString("code").trim());
                    discount.setType(rs.getString("discount_type").trim());
                    discount.setValue(rs.getBigDecimal("discount_value"));

                    BigDecimal maxDiscount = rs.getBigDecimal("max_discount");
                    if (maxDiscount != null) {
                        discount.setMaxDiscount(maxDiscount);
                    }

                    if (minOrder != null) {
                        discount.setMinOrderAmount(minOrder);
                    }

                    discount.setDescription(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return discount;
    }

    public BigDecimal calculateDiscount(DiscountCode dc, BigDecimal orderTotal) {
        BigDecimal discount = BigDecimal.ZERO;
        try {
            if (dc.getType().equalsIgnoreCase("PERCENT")) {
                discount = orderTotal.multiply(dc.getValue()).divide(new BigDecimal("100"));
                if (dc.getMaxDiscount() != null && discount.compareTo(dc.getMaxDiscount()) > 0) {
                    discount = dc.getMaxDiscount();
                }
            } else if (dc.getType().equalsIgnoreCase("FIXED")) {
                discount = dc.getValue();
            }

            if (discount.compareTo(orderTotal) > 0) {
                discount = orderTotal;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return discount;
    }

    public void increaseUsedCount(int discountCodeId) {
        String sql = "UPDATE discount_code SET used_count = used_count + 1 WHERE id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, discountCodeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public DiscountCode getByCode(String code) {
        String sql = "SELECT * FROM discount_code WHERE code = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            if (rs.next()) {
                DiscountCode discount = new DiscountCode();
                discount.setId(rs.getInt("id"));
                discount.setCode(rs.getString("code").trim());
                discount.setDescription(rs.getString("description"));
                discount.setType(rs.getString("discount_type"));
                discount.setValue(rs.getBigDecimal("discount_value"));
                discount.setMaxDiscount(rs.getBigDecimal("max_discount"));
                discount.setMinOrderAmount(rs.getBigDecimal("min_order_amount"));
                discount.setStartDate(rs.getTimestamp("start_date"));
                discount.setEndDate(rs.getTimestamp("end_date"));

                Object usageLimitObj = rs.getObject("usage_limit");
                discount.setUsageLimit(usageLimitObj != null ? rs.getInt("usage_limit") : null); // Set to null if DB value is null

                discount.setUsedCount(rs.getInt("used_count"));
                discount.setActive(rs.getBoolean("active"));
                return discount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }

    public void deactivateDiscountCode(String code) {
        String sql = "UPDATE discount_code SET active = FALSE WHERE code = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, code);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public void incrementUsedCount(String code) {
        String sql = "UPDATE discount_code SET used_count = used_count + 1 WHERE code = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, code);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public DiscountCode getValidDiscountCode(String code) {
        String sql = "SELECT * FROM discount_code WHERE code = ? AND active = TRUE AND "
                + "(start_date IS NULL OR start_date <= NOW()) AND "
                + "(end_date IS NULL OR end_date >= NOW()) AND "
                + "(usage_limit IS NULL OR used_count < usage_limit)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            if (rs.next()) {
                DiscountCode d = new DiscountCode();
                d.setId(rs.getInt("id"));
                d.setCode(rs.getString("code"));
                d.setDescription(rs.getString("description"));
                d.setType(rs.getString("discount_type"));
                d.setValue(rs.getBigDecimal("discount_value"));
                d.setMaxDiscount(rs.getBigDecimal("max_discount"));
                d.setMinOrderAmount(rs.getBigDecimal("min_order_amount"));
                d.setStartDate(rs.getTimestamp("start_date"));
                d.setEndDate(rs.getTimestamp("end_date"));

                Object usageLimitObj = rs.getObject("usage_limit");
                d.setUsageLimit(usageLimitObj != null ? rs.getInt("usage_limit") : null); // Set to null if DB value is null

                d.setUsedCount(rs.getInt("used_count"));
                d.setActive(rs.getBoolean("active"));
                return d;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }

    public void insertDiscountCode(DiscountCode dc) {
        String sql = "INSERT INTO discount_code (code, description, discount_type, discount_value, max_discount, "
                + "min_order_amount, start_date, end_date, usage_limit, used_count, active) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, dc.getCode());
            ps.setString(2, dc.getDescription());
            ps.setString(3, dc.getType());
            ps.setBigDecimal(4, dc.getValue());
            ps.setBigDecimal(5, dc.getMaxDiscount());
            ps.setBigDecimal(6, dc.getMinOrderAmount());
            ps.setTimestamp(7, dc.getStartDate());
            ps.setTimestamp(8, dc.getEndDate());
            ps.setObject(9, dc.getUsageLimit());
            ps.setInt(10, 0); // Ban đầu chưa dùng
            ps.setBoolean(11, true); // Mặc định hoạt động
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public void updateDiscountCode(DiscountCode dc) {
        String sql = "UPDATE discount_code SET description = ?, discount_type = ?, discount_value = ?, "
                + "max_discount = ?, min_order_amount = ?, start_date = ?, end_date = ?, usage_limit = ?, active = ? "
                + "WHERE code = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, dc.getDescription());
            ps.setString(2, dc.getType());
            ps.setBigDecimal(3, dc.getValue());
            ps.setBigDecimal(4, dc.getMaxDiscount());
            ps.setBigDecimal(5, dc.getMinOrderAmount());
            ps.setTimestamp(6, dc.getStartDate());
            ps.setTimestamp(7, dc.getEndDate());
            ps.setObject(8, dc.getUsageLimit());
            ps.setBoolean(9, dc.isActive());
            ps.setString(10, dc.getCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public int countAllDiscountCodes() {
        String sql = "SELECT COUNT(*) FROM discount_code";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return 0;
    }

    public void deleteDiscountCode(String code) {
        String sql = "DELETE FROM discount_code WHERE code = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, code);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    // Lấy danh sách mã giảm giá có phân trang và sắp xếp mới nhất
    public List<DiscountCode> getDiscountCodesPaged(int offset, int pageSize) {
        List<DiscountCode> list = new ArrayList<>();
        String sql = "SELECT * FROM discount_code ORDER BY start_date DESC LIMIT ? OFFSET ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            rs = ps.executeQuery();
            while (rs.next()) {
                DiscountCode d = new DiscountCode(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("description"),
                        rs.getString("discount_type"),
                        rs.getBigDecimal("discount_value"),
                        rs.getBigDecimal("max_discount"),
                        rs.getBigDecimal("min_order_amount"),
                        rs.getTimestamp("start_date"),
                        rs.getTimestamp("end_date"),
                        rs.getObject("usage_limit") != null ? rs.getInt("usage_limit") : null,
                        rs.getInt("used_count"),
                        rs.getBoolean("active")
                );
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return list;
    }

    public DiscountCode getDiscountCodeByCode(String code) {
        String sql = "SELECT * FROM discount_code WHERE code = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new DiscountCode(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("description"),
                        rs.getString("discount_type"),
                        rs.getBigDecimal("discount_value"),
                        rs.getBigDecimal("max_discount"),
                        rs.getBigDecimal("min_order_amount"),
                        rs.getTimestamp("start_date"),
                        rs.getTimestamp("end_date"),
                        rs.getObject("usage_limit") != null ? rs.getInt("usage_limit") : null,
                        rs.getInt("used_count"),
                        rs.getBoolean("active")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }

    public boolean isDiscountCodeExists(String code) {
        String sql = "SELECT COUNT(*) FROM discount_code WHERE code = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking discount code existence: " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return false;
    }

    public static void main(String[] args) {
        DiscountCodeDAO dao = new DiscountCodeDAO();
        List<DiscountCode> list = dao.getAllDiscountCodes();

        System.out.println("📋 Danh sách mã giảm giá:");
        for (DiscountCode d : list) {
            System.out.println("——————————————");
            System.out.println("🔑 Mã: " + d.getCode());
            System.out.println("📝 Mô tả: " + d.getDescription());
            System.out.println("💰 Kiểu: " + d.getType());
            System.out.println("📉 Giá trị: " + d.getValue());
            System.out.println("🔺 Giảm tối đa: " + d.getMaxDiscount());
            System.out.println("� Đơn tối thiểu: " + d.getMinOrderAmount());
            System.out.println("📆 Bắt đầu: " + d.getStartDate());
            System.out.println("📆 Kết thúc: " + d.getEndDate());
            System.out.println("🔄 Dùng tối đa: " + d.getUsageLimit());
            System.out.println("📊 Đã dùng: " + d.getUsedCount());
            System.out.println("✅ Còn hoạt động: " + d.isActive());
        }
    }
}
