package model;

import java.math.BigDecimal;
import java.sql.Timestamp; // Sử dụng Timestamp thay vì Date để đồng bộ với JDBC Timestamp
import java.util.Date; // Giữ lại Date nếu bạn muốn dùng nó cho isValid, nhưng Timestamp tốt hơn cho DB

/**
 *
 * @author VU MINH TAN
 */
public class DiscountCode {

    private int id;
    private String code;
    private String description;
    private String type; // Kiểu giảm giá: "PERCENT" hoặc "FIXED"
    private BigDecimal value; // Giá trị giảm giá (ví dụ: 10.0 cho 10% hoặc 50000 cho 50,000 VND)
    private BigDecimal maxDiscount; // Số tiền giảm tối đa (chỉ áp dụng cho PERCENT)
    private BigDecimal minOrderAmount; // Số tiền đơn hàng tối thiểu để áp dụng mã
    private Timestamp startDate; // Ngày bắt đầu hiệu lực
    private Timestamp endDate; // Ngày kết thúc hiệu lực
    private int usageLimit; // Giới hạn số lần sử dụng
    private int usedCount; // Số lần đã sử dụng
    private boolean active; // Trạng thái hoạt động của mã

    public DiscountCode() {
        // Khởi tạo các BigDecimal với giá trị 0 để tránh NullPointerException nếu không được set
        this.value = BigDecimal.ZERO;
        this.maxDiscount = BigDecimal.ZERO;
        this.minOrderAmount = BigDecimal.ZERO;
    }

    public DiscountCode(int id, String code, String description, String type, BigDecimal value,
            BigDecimal maxDiscount, BigDecimal minOrderAmount, Timestamp startDate,
            Timestamp endDate, int usageLimit, int usedCount, boolean active) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.type = type;
        this.value = value;
        this.maxDiscount = maxDiscount;
        this.minOrderAmount = minOrderAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.usageLimit = usageLimit;
        this.usedCount = usedCount;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(BigDecimal maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public BigDecimal getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(BigDecimal minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(int usageLimit) {
        this.usageLimit = usageLimit;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Kiểm tra xem mã giảm giá có hợp lệ về thời gian và giới hạn sử dụng
     * không. Lưu ý: Không kiểm tra minOrderAmount ở đây vì nó phụ thuộc vào
     * tổng đơn hàng cụ thể.
     *
     * @return true nếu mã hợp lệ, false nếu không.
     */
    public boolean isValid() {
        Date now = new Date(); // Lấy thời gian hiện tại
        return active
                && (startDate == null || !now.before(startDate)) // Mã chưa bắt đầu hoặc đã bắt đầu
                && (endDate == null || !now.after(endDate)) // Mã chưa kết thúc hoặc đã kết thúc
                && (usageLimit == 0 || usedCount < usageLimit); // Chưa vượt quá giới hạn sử dụng
    }

    /**
     * Tính toán số tiền được giảm dựa trên tổng đơn hàng.
     *
     * @param orderTotal Tổng giá trị đơn hàng.
     * @return Số tiền được giảm, kiểu BigDecimal.
     */
    public BigDecimal calculateDiscount(BigDecimal orderTotal) {
        BigDecimal discount = BigDecimal.ZERO;
        if (minOrderAmount != null && orderTotal.compareTo(minOrderAmount) < 0) {
            return BigDecimal.ZERO; // Không đủ điều kiện số tiền tối thiểu
        }

        if ("PERCENT".equalsIgnoreCase(type)) { // Sử dụng trường 'type'
            // Tính số tiền giảm theo phần trăm: orderTotal * (value / 100)
            discount = orderTotal.multiply(value).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);

            // Áp dụng giới hạn giảm giá tối đa nếu có
            if (maxDiscount != null && maxDiscount.compareTo(BigDecimal.ZERO) > 0 && discount.compareTo(maxDiscount) > 0) {
                discount = maxDiscount;
            }
        } else if ("FIXED".equalsIgnoreCase(type)) { // Sử dụng trường 'type'
            // Số tiền giảm cố định là 'value'
            discount = value;
        }

        // Đảm bảo số tiền giảm không vượt quá tổng đơn hàng
        if (discount.compareTo(orderTotal) > 0) {
            discount = orderTotal;
        }

        return discount;
    }

    public double getDiscountAmount(double totalOrder) {
        // Nếu đơn hàng không đủ điều kiện tối thiểu
        if (minOrderAmount != null && totalOrder < minOrderAmount.doubleValue()) {
            return 0.0;
        }

        double discount = 0.0;

        if ("PERCENT".equalsIgnoreCase(type)) {
            discount = totalOrder * value.doubleValue() / 100.0;
            if (maxDiscount != null && discount > maxDiscount.doubleValue()) {
                discount = maxDiscount.doubleValue();
            }
        } else if ("FIXED".equalsIgnoreCase(type)) {
            discount = value.doubleValue();
        }

        return Math.min(discount, totalOrder); // Không vượt quá giá trị đơn hàng
    }

}
