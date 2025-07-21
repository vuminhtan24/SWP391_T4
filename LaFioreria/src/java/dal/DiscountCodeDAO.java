package dal;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.DiscountCode;

public class DiscountCodeDAO extends BaseDao {

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
                int usageLimit = rs.getInt("usage_limit");
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
                discount.setUsageLimit(rs.getInt("usage_limit"));
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

    public List<DiscountCode> getAllDiscountCodes() {
        List<DiscountCode> list = new ArrayList<>();
        String sql = "SELECT * FROM discount_code";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
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
        }
        return list;
    }

    public void deactivateDiscountCode(String code) {
        String sql = "UPDATE discount_code SET status = 0 WHERE code = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, code);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
        }
    }

    public DiscountCode getValidDiscountCode(String code) {
        String sql = "SELECT * FROM discount_code WHERE code = ? AND active = 1 AND "
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
                d.setUsageLimit(usageLimitObj != null ? rs.getInt("usage_limit") : 0); // 0 = không giới hạn

                d.setUsedCount(rs.getInt("used_count"));
                d.setActive(rs.getBoolean("active"));
                return d;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        DiscountCodeDAO dao = new DiscountCodeDAO();
        String inputCode = "SUMMER2024";
        BigDecimal orderTotal = new BigDecimal("1000000");

        // 1. Kiểm tra mã hợp lệ không
        System.out.println("🔎 Đang kiểm tra mã giảm giá: " + inputCode);
        DiscountCode dc = dao.getValidDiscount(inputCode, orderTotal);
        if (dc != null) {
            System.out.println("✅ Mã hợp lệ: " + dc.getCode());
            System.out.println("→ Kiểu: " + dc.getType());
            System.out.println("→ Giá trị: " + dc.getValue());
            System.out.println("→ Mô tả: " + dc.getDescription());
            Integer usageLimit = dc.getUsageLimit();
            System.out.println("→ Dùng tối đa: " + (usageLimit == null ? "Không giới hạn" : usageLimit));

            System.out.println("→ Đã dùng: " + dc.getUsedCount());

            // 2. Tính tiền giảm
            BigDecimal discountAmount = dao.calculateDiscount(dc, orderTotal);
            System.out.println("💸 Số tiền được giảm: " + discountAmount);

            // 3. Cập nhật lượt dùng
            dao.increaseUsedCount(dc.getId());
            System.out.println("✅ Đã tăng lượt dùng mã");

            // 4. Kiểm tra lại thông tin mã sau khi update
            DiscountCode updated = dao.getByCode(inputCode);
            System.out.println("📊 Lượt dùng sau cập nhật: " + updated.getUsedCount());
        } else {
            System.out.println("❌ Mã giảm giá không hợp lệ hoặc không đủ điều kiện.");
        }

        // 5. In ra tất cả các mã giảm giá hiện có
        System.out.println("\n📋 Danh sách tất cả mã giảm giá:");
        List<DiscountCode> allCodes = dao.getAllDiscountCodes();
        for (DiscountCode d : allCodes) {
            System.out.println("➡ " + d.getCode() + " | " + d.getDescription() + " | "
                    + d.getType() + " | " + d.getValue()
                    + " | Trạng thái: " + (d.isActive() ? "Đang hoạt động" : "Không hoạt động"));
        }
    }

}
