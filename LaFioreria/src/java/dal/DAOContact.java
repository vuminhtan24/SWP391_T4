/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author VU MINH TAN
 */
public class DAOContact extends DBContext{
    
    public static boolean insertContact(String name, String email, String subject, String message) {
        boolean isSuccess = false;
        String sql = "INSERT INTO Contact (name, email, subject, message) VALUES (?, ?, ?, ?)";
        try (Connection conn = new DBContext().connection;
                PreparedStatement st = conn.prepareStatement(sql)){
            st.setString(1, name);
            st.setString(2, email);
            st.setString(3, subject);
            st.setString(4, message);

            int rowsInserted = st.executeUpdate();
            isSuccess = (rowsInserted > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }
    
}
