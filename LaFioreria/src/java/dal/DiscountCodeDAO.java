/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.math.BigDecimal;
import java.sql.SQLException;
import model.DiscountCode;

/**
 *
 * @author VU MINH TAN
 */
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
                    discount.setMaxDiscount(rs.getBigDecimal("max_discount"));
                    discount.setMinOrderAmount(minOrder);
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

            // Không để giảm giá lớn hơn tổng đơn
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

    public static void main(String[] args) {
        DiscountCodeDAO dao = new DiscountCodeDAO();

        // Giả sử đơn hàng có tổng tiền 1.000.000 VNĐ
        BigDecimal orderTotal = new BigDecimal("1000000");

        // Nhập mã giảm giá để test
        String inputCode = "SUMMER2024"; // Nhớ thay bằng mã có trong DB

        // Bước 1: Kiểm tra mã có hợp lệ không
        DiscountCode dc = dao.getValidDiscount(inputCode, orderTotal);
        if (dc != null) {
            System.out.println("✅ Mã hợp lệ: " + dc.getCode());
            System.out.println("→ Kiểu: " + dc.getType());
            System.out.println("→ Giá trị: " + dc.getValue());
            System.out.println("→ Mô tả: " + dc.getDescription());

            // Bước 2: Tính tiền giảm
            BigDecimal discountAmount = dao.calculateDiscount(dc, orderTotal);
            System.out.println("→ Tiền được giảm: " + discountAmount);

            // Bước 3: Tăng lượt dùng (test thử)
            dao.increaseUsedCount(dc.getId());
            System.out.println("✅ Đã cập nhật lượt dùng mã.");
        } else {
            System.out.println("❌ Mã giảm giá không hợp lệ hoặc không đủ điều kiện.");
        }
    }
}
