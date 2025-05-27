/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.TokenForgetPassword;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author VU MINH TAN
 */
public class DAOTokenForget extends DBContext {

    public String getFormatDate(LocalDateTime myDateObj) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }

    public boolean insertTokenForget(TokenForgetPassword tokenForget) {
        String sql = "INSERT INTO tokenForgetPassword (token, expiryTime, isUsed, userId) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tokenForget.getToken());
            ps.setTimestamp(2, Timestamp.valueOf(getFormatDate(tokenForget.getExpiryTime())));
            ps.setBoolean(3, tokenForget.isIsUsed());
            ps.setInt(4, tokenForget.getUserId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    public TokenForgetPassword getTokenPassword(String token){
        String sql = "select * from tokenforgetpassword where token = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, token);
            ResultSet rs =st.executeQuery();
            while(rs.next()){
                return new TokenForgetPassword(
                        rs.getInt("id"), 
                        rs.getInt("userID"),
                        rs.getBoolean("isUsed"),
                        rs.getString("token"),
                        rs.getTimestamp("expiryTime").toLocalDateTime());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    public void updateStatus(TokenForgetPassword token){
        System.out.println("token="+token);
        String sql = "UPDATE tokenforgetpassword SET isUsed = ? WHERE token = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setBoolean(1, token.isIsUsed());
            st.setString(2, token.getToken());
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
     public static void main(String[] args) {
        // Tạo DAO
        DAOTokenForget dao = new DAOTokenForget();

        // Tạo đối tượng token cần update
        TokenForgetPassword token = new TokenForgetPassword();
        token.setToken("4d8f7f9d-9247-485b-92dd-f8fb021c37f8"); // <-- Thay bằng token có thật trong DB
        token.setIsUsed(true);    // Đánh dấu là đã dùng

        // Gọi phương thức update
        dao.updateStatus(token);
    }
}
