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
import model.Role;
import model.User;
import model.UserManager;

/**
 *
 * @author ADMIN
 */
public class UserDAO extends DBContext {

    public List<User> getAll() {
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

    public List<String> getRoleNames() {
        List<String> listRole = new ArrayList<>();

        String sql = "select Role_name from role;";

        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            
            while(rs.next()){
                String Role_name = rs.getString("Role_name");
                listRole.add(Role_name);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listRole;
    }
    

    
    public List<Integer> getIds() {

        List<Integer> ids = new ArrayList<>();
        String sql = "select user_id from user;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("user_id");
                ids.add(id);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return ids;
    }

    public UserManager getUserById(int id) {
        String sql = "select u.User_ID,u.Username,u.Password,u.Fullname,u.Email,u.Phone,u.Address,r.Role_name from user u join role r on u.Role = r.Role_id where u.User_ID = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String user_name = rs.getString("username");
                String password = rs.getString("password");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String role = rs.getString("role_name");
                UserManager user = new UserManager(id, user_name, password, fullname, email, phone, address, role);
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {

        UserDAO ud = new UserDAO();
//        List<Integer> lst = ud.getIds();
//        for (int i = 0; i < lst.size(); i++) {
//            Integer get = lst.get(i);
//            System.out.println(get);
//        }
//        UserManager um = ud.getUserById(1);
//        System.out.println(um.toString());
        
    }

}
