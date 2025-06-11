package util;

import constant.IConstant;
import dal.WarehouseDAO;
import jakarta.servlet.http.Part;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Warehouse;

/**
 *
 * @author ADMIN
 */
public class Validate {

    public static final boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(IConstant.REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static final boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(IConstant.REGEX_PHONE_NUMBER);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static final boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(IConstant.REGEX_PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static final boolean isImageFile(Part part) {
        String contentType = part.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
    
    // Kiểm tra số (không chứa chữ hoặc ký tự đặc biệt)
    public static String validateNumber(String number, String fieldName) {
        if (number == null || number.trim().isEmpty()) {
            return fieldName + " is required.";
        }
        if (number.trim().matches("^\\s+$")) {
            return fieldName + " cannot contain only spaces.";
        }
        Pattern pattern = Pattern.compile(IConstant.REGEX_NUMBER);
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            return fieldName + " must contain only digits.";
        }
        return null; // Hợp lệ
    }

    // Kiểm tra số với giới hạn giá trị
    public static String validateNumberWithRange(String number, String fieldName, int min, int max) {
        String error = validateNumber(number, fieldName);
        if (error != null) {
            return error;
        }
        try {
            int value = Integer.parseInt(number);
            if (value < min || value > max) {
                return fieldName + " must be between " + min + " and " + max + ".";
            }
        } catch (NumberFormatException e) {
            return fieldName + " must be a valid number.";
        }
        return null; // Hợp lệ
    }

    // Kiểm tra văn bản (chỉ chứa chữ và khoảng trắng)
    public static String validateText(String text, String fieldName) {
        if (text == null || text.trim().isEmpty()) {
            return fieldName + " is required.";
        }
        if (text.trim().matches("^\\s+$")) {
            return fieldName + " cannot contain only spaces.";
        }
        Pattern pattern = Pattern.compile(IConstant.REGEX_TEXT);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            return fieldName + " can only contain letters and spaces.";
        }
        return null; // Hợp lệ
    }

    // Kiểm tra độ dài chuỗi
    public static String validateLength(String value, String fieldName, int minLength, int maxLength) {
        if (value == null || value.trim().isEmpty()) {
            return fieldName + " is required.";
        }
        if (value.trim().matches("^\\s+$")) {
            return fieldName + " cannot contain only spaces.";
        }
        if (value.length() < minLength || value.length() > maxLength) {
            return fieldName + " must be between " + minLength + " and " + maxLength + " characters.";
        }
        return null; // Hợp lệ
    }
    
    // Kiểm tra URL hình ảnh
    public static String validateImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return "Image URL is required.";
        }
        if (imageUrl.trim().matches("^\\s+$")) {
            return "Image URL cannot contain only spaces.";
        }
        if (imageUrl.length() > 200) {
            return "Image URL must be less than 200 characters.";
        }
        Pattern pattern = Pattern.compile(IConstant.REGEX_URL);
        Matcher matcher = pattern.matcher(imageUrl);
        if (!matcher.matches()) {
            return "Invalid image URL format.";
        }
        return null; // Hợp lệ
    }

    // Kiểm tra warehouseId
    public static String validateWarehouseId(String warehouseIdStr, WarehouseDAO wh) {
        String error = validateNumber(warehouseIdStr, "Warehouse ID");
        if (error != null) {
            return error;
        }
        try {
            int warehouseId = Integer.parseInt(warehouseIdStr);
            if (warehouseId <= 0) {
                return "Warehouse ID must be a positive integer.";
            }
            // Kiểm tra warehouseId có tồn tại không
            Warehouse warehouse = wh.getWarehouseById(warehouseId);
            if (warehouse == null) {
                return "Warehouse ID does not exist.";
            }
        } catch (NumberFormatException e) {
            return "Warehouse ID must be a valid number.";
        }
        return null; // Hợp lệ
    }
    
    // Kiểm tra description (không giới hạn độ dài, thêm nhiều ký tự đặc biệt)
    public static String validateDescription(String description) {
       System.out.println("Validating description: [" + (description != null ? description.replaceAll("[^\\p{Print}]", "?") : "null") + "]");
        if (description == null || description.trim().isEmpty()) {
            return null; // Hợp lệ (cho phép rỗng)
        }
        // Kiểm tra nếu toàn bộ là khoảng trắng
        if (description.trim().matches("^\\s+$")) {
            return "Description cannot contain only spaces.";
        }
        // Kiểm tra tất cả ký tự in được
        Pattern pattern = Pattern.compile("^[\\p{Print}]*$");
        Matcher matcher = pattern.matcher(description);
        if (!matcher.matches()) {
            return "Description contains invalid characters.";
        }
        return null; // Hợp lệ
    }
}