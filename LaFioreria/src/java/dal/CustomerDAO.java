/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.SQLException;
import java.util.Date;
import model.CustomerInfo;

/**
 *
 * @author LAPTOP
 */
public class CustomerDAO extends BaseDao {

    public CustomerInfo getByUserId(int userId) {
        CustomerInfo ci = null;
        String sql = "SELECT * FROM customer_info WHERE User_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                ci = new CustomerInfo(
                        rs.getInt("User_ID"),
                        rs.getString("Customer_Code"),
                        rs.getString("Join_Date"),
                        rs.getInt("Loyalty_Point"),
                        rs.getString("Birthday"),
                        rs.getString("Gender")
                );
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getByUserId (CustomerDAO): " + e.getMessage());
        }
        return ci;
    }

    public boolean updateBirthdayAndGender(int userId, String birthdayStr, String gender) {
        String sql = "UPDATE customer_info SET Birthday = ?, Gender = ? WHERE User_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);

            java.sql.Date birthday = java.sql.Date.valueOf(birthdayStr);

            ps.setDate(1, birthday);
            ps.setString(2, gender);
            ps.setInt(3, userId);
            return ps.executeUpdate() > 0;
        } catch (IllegalArgumentException e) {
            System.err.println("Birthday format must be yyyy-MM-dd");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getLoyaltyPointByUserId(int userId) {
        String sql = "SELECT Loyalty_Point FROM customer_info WHERE User_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setInt(1, userId);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt("Loyalty_Point");
                }
            } catch (Exception e) {
                e.getMessage();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateLoyaltyPointByUserId(int userId, int newLoyaltyPoint) {
        String sql = "UPDATE customer_info SET Loyalty_Point = ? WHERE User_ID = ?";
        try {

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, newLoyaltyPoint);
            ps.setInt(2, userId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean exist(int userId) {
        String sql = "SELECT 1 FROM customer_info WHERE User_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Lỗi exist (CustomerDAO): " + e.getMessage());
        }
        return false;
    }

    public void insert(CustomerInfo c) {
        String sql = "INSERT INTO customer_info (User_ID, Customer_Code, Join_Date, Loyalty_Point, Birthday, Gender) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, c.getUserId());
            ps.setString(2, c.getCustomerCode());
            ps.setString(3, c.getJoinDate());
            ps.setInt(4, c.getLoyaltyPoint());
            if (c.getBirthday() != null && !c.getBirthday().isEmpty()) {
                ps.setDate(5, java.sql.Date.valueOf(c.getBirthday()));
            } else {
                ps.setNull(5, java.sql.Types.DATE);
            }

            if (c.getGender() != null && !c.getGender().isEmpty()) {
                ps.setString(6, c.getGender());
            } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi insert (CustomerDAO): " + e.getMessage());
        }
    }

    public int getLatestInsertedUserId() {
        int userId = -1;
        String sql = "SELECT MAX(User_ID) FROM la_fioreria.user";

        try {
            connection = dbc.getConnection();  // dbc là đối tượng DatabaseContext hoặc tương đương
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                userId = rs.getInt(1);  // Lấy giá trị MAX(User_ID)
            }
        } catch (SQLException e) {
        } finally {
            try {
                closeResources();  // Nếu bạn có phương thức đóng kết nối
            } catch (Exception e) {
            }
        }

        return userId;
    }

    public void update(CustomerInfo c) {
        String sql = "UPDATE customer_info SET Customer_Code = ?, Join_Date = ?, Loyalty_Point = ?, Birthday = ?, Gender = ? WHERE User_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, c.getCustomerCode());
            ps.setString(2, c.getJoinDate());
            ps.setInt(3, c.getLoyaltyPoint());
            ps.setString(4, c.getBirthday());
            ps.setString(5, c.getGender());
            ps.setInt(6, c.getUserId());
            ps.executeUpdate();
            System.out.println("SQL: " + sql);
            System.out.println("User_ID = " + c.getUserId());

        } catch (SQLException e) {
            System.out.println("Lỗi update (CustomerDAO): " + e.getMessage());
        }
    }

    public int getLatestCustomerId() throws SQLException {
        String sql = "SELECT MAX(ID) FROM Customer_info"; // giả sử bạn có ID tự tăng
        try {
            connection = dbc.getConnection();  // dbc là đối tượng DatabaseContext hoặc tương đương
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // trả về giá trị MAX(ID)
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();

        // Test dữ liệu
        int testUserId = 6; // thay bằng User_ID có trong bảng user
        CustomerInfo newCustomer = new CustomerInfo(
                testUserId,
                "KH099",
                "2025-06-27",
                150,
                "2003-05-15",
                "Female"
        );

        // 1. Kiểm tra tồn tại
        if (dao.exist(testUserId)) {
            System.out.println("Customer existed → Update thử:");
            dao.update(newCustomer);
        } else {
            System.out.println("Customer chưa tồn tại → Insert mới:");
            dao.insert(newCustomer);
        }

        // 2. Lấy lại để kiểm tra
        CustomerInfo result = dao.getByUserId(testUserId);
        if (result != null) {
            System.out.println("Thông tin sau thao tác:");
            System.out.println("User ID: " + result.getUserId());
            System.out.println("Customer Code: " + result.getCustomerCode());
            System.out.println("Join Date: " + result.getJoinDate());
            System.out.println("Loyalty Point: " + result.getLoyaltyPoint());
            System.out.println("Birthday: " + result.getBirthday());
            System.out.println("Gender: " + result.getGender());
        } else {
            System.out.println("Không tìm thấy khách hàng với ID: " + testUserId);
        }
    }

}
