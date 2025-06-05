/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.TokenForgetPassword;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author VU MINH TAN
 */
public class DAOTokenForget extends BaseDao {

// Assuming you have instance variables like:
// private Connection connection;
// private PreparedStatement ps;
// private ResultSet rs;
// private DBC dbc; // Your database connection helper
// And a method closeResources() that closes rs, ps, and connection.
    public String getFormatDate(LocalDateTime myDateObj) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return myDateObj.format(myFormatObj);
    }

    public boolean insertTokenForget(TokenForgetPassword tokenForget) {
        String sql = "INSERT INTO tokenForgetPassword (token, expiryTime, isUsed, userId) VALUES (?, ?, ?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, tokenForget.getToken().trim());
            // You can pass a LocalDateTime directly into Timestamp.valueOf(...)
            ps.setTimestamp(2, Timestamp.valueOf(getFormatDate(tokenForget.getExpiryTime())));
            ps.setBoolean(3, tokenForget.isIsUsed());
            ps.setInt(4, tokenForget.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return false;
    }

    public TokenForgetPassword getTokenPassword(String token) {
        String sql = "SELECT * FROM tokenForgetPassword WHERE token = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, token.trim());
            rs = ps.executeQuery();
            if (rs.next()) {
                return new TokenForgetPassword(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getBoolean("isUsed"),
                        rs.getString("token").trim(),
                        rs.getTimestamp("expiryTime").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }

    public void updateStatus(TokenForgetPassword token) {
        String sql = "UPDATE tokenForgetPassword SET isUsed = ? WHERE token = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setBoolean(1, token.isIsUsed());
            ps.setString(2, token.getToken().trim());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public static void main(String[] args) {
        // Tạo DAO
        DAOTokenForget dao = new DAOTokenForget();
//
//        // Tạo đối tượng token cần update
//        TokenForgetPassword token = new TokenForgetPassword();
//        token.setToken("4d8f7f9d-9247-485b-92dd-f8fb021c37f8"); // <-- Thay bằng token có thật trong DB
//        token.setIsUsed(true);    // Đánh dấu là đã dùng
//        
//        // Gọi phương thức update
//        dao.updateStatus(token);
        TokenForgetPassword tokenForget = new TokenForgetPassword();
        tokenForget.setId(100);
        tokenForget.setUserId(1);
        tokenForget.setExpiryTime(LocalDateTime.now());
        tokenForget.setIsUsed(false);
        tokenForget.setToken("TEST");
        
        
        System.out.println(dao.insertTokenForget(tokenForget));
    }
}
