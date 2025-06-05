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
public class DAOContact extends BaseDao {

    // Assuming you have instance variables like:
// private Connection connection;
// private PreparedStatement ps;
// private ResultSet rs;
// private DBC dbc; // Your database connection helper
// And a method closeResources() that closes rs, ps, and connection.
    public boolean insertContact(String name, String email, String subject, String message) {
        boolean isSuccess = false;
        String sql = "INSERT INTO Contact (name, email, subject, message) VALUES (?, ?, ?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, name.trim());
            ps.setString(2, email.trim());
            ps.setString(3, subject.trim());
            ps.setString(4, message.trim());

            int rowsInserted = ps.executeUpdate();
            isSuccess = (rowsInserted > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return isSuccess;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM Contact ORDER BY created_at DESC"; // Newest first

        try {
            connection = new DBContext().getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Contact c = new Contact();
                c.setId(rs.getInt("contact_id"));
                c.setName(rs.getString("name").trim());
                c.setEmail(rs.getString("email").trim());
                c.setSubject(rs.getString("subject").trim());
                c.setMessage(rs.getString("message").trim());
                c.setCreatedAt(rs.getTimestamp("created_at"));
                contacts.add(c);
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

        return contacts;
    }

}
