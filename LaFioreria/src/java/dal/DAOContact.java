/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Contact;
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
    public List<Contact> getAllContacts() {
    List<Contact> contacts = new ArrayList<>();
    String sql = "SELECT * FROM Contact ORDER BY created_at DESC"; // Sắp xếp mới nhất trước

    try (Connection conn = new DBContext().connection;
         PreparedStatement st = conn.prepareStatement(sql);
         ResultSet rs = st.executeQuery()) {

        while (rs.next()) {
            Contact c = new Contact();
            c.setId(rs.getInt("contact_id"));
            c.setName(rs.getString("name"));
            c.setEmail(rs.getString("email"));
            c.setSubject(rs.getString("subject"));
            c.setMessage(rs.getString("message"));
            c.setCreatedAt(rs.getTimestamp("created_at"));
            contacts.add(c);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return contacts;
}
    
}
