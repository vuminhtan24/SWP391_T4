/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author ADMIN
 */
public class UserDAO extends DBContext{
    public List<User> getAll(){
        List<User> listUser = new ArrayList<>();
        
        String sql = "Select * from la_fioreria.user";
        
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int user_id = rs.getInt("User_ID");
                String username = rs.getString("Username").trim();
                String password = rs.getString("Password").trim();
                String fullname = rs.getString("Fullname").trim();
                String email = rs.getString("Email").trim();
                String phone = rs.getString("Phone").trim();
                String address = rs.getString("Address").trim();
                int role = rs.getInt("Role");
                User newUser = new User(user_id, username, password, fullname, email, phone, address, role);
                listUser.add(newUser);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return listUser;
    }
    
}
