/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void Update(User u) {

        String sql = "update user set Username = ?, Password = ?, Fullname = ?, Email = ?,Phone = ?,Address = ?, Role = ? where User_ID = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullname());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getPhone());
            ps.setString(6, u.getAddress());
            ps.setInt(7, u.getRole());
            ps.setInt(8, u.getUserid());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<Role> getAllRole() {

        List<Role> list = new ArrayList<>();
        String sql = "select * from role;";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Role_id");
                String role_name = rs.getString("Role_name");
                Role r = new Role(id, role_name);
                list.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<String> getRoleNames() {
        List<String> listRole = new ArrayList<>();

        String sql = "select Role_name from role;";

        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
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

    public List<UserManager> getAllUserManager() {
        String sql = "select u.User_ID,u.Username,u.Password,u.Fullname,u.Email,u.Phone,u.Address,r.Role_name from user u join role r on u.Role = r.Role_id order by u.User_ID;";
        List<UserManager> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String user_name = rs.getString("username");
                String password = rs.getString("password");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String role = rs.getString("role_name");
                UserManager user = new UserManager(id, user_name, password, fullname, email, phone, address, role);
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<UserManager> getUserByRoleId(int role_id) {
        String sql = "SELECT u.User_ID, u.Username, u.Password, u.Fullname, u.Email, u.Phone, u.Address, r.Role_name "
                + "FROM user u "
                + "JOIN role r ON u.Role = r.Role_id "
                + "WHERE r.Role_id = ? "
                + "ORDER BY u.User_ID";
        List<UserManager> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, role_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String user_name = rs.getString("username");
                String password = rs.getString("password");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String role = rs.getString("role_name");
                UserManager user = new UserManager(id, user_name, password, fullname, email, phone, address, role);
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<UserManager> getUserByRoleIdSearchName(int role_id, String kw) {
        List<UserManager> list = new ArrayList<>();
        String sql;
        boolean filterByRole = role_id != 0;

        if (filterByRole) {
            sql = "SELECT u.User_ID, u.Username, u.Password, u.Fullname, u.Email, u.Phone, u.Address, r.Role_name "
                    + "FROM user u "
                    + "JOIN role r ON u.Role = r.Role_id "
                    + "WHERE u.Username LIKE ? AND r.Role_id = ? "
                    + "ORDER BY u.User_ID";
        } else {
            sql = "SELECT u.User_ID, u.Username, u.Password, u.Fullname, u.Email, u.Phone, u.Address, r.Role_name "
                    + "FROM user u "
                    + "JOIN role r ON u.Role = r.Role_id "
                    + "WHERE u.Username LIKE ? "
                    + "ORDER BY u.User_ID";
        }

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + kw + "%"); // Tìm theo từ khóa

            if (filterByRole) {
                ps.setInt(2, role_id); // Chỉ set nếu có dấu ?
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String user_name = rs.getString("username");
                String password = rs.getString("password");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String role = rs.getString("role_name");

                UserManager user = new UserManager(id, user_name, password, fullname, email, phone, address, role);
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }

        return list;
    }

    public List<UserManager> getSortedUsers(int roleId, String keyword, String sortField, String sortOrder) {
        List<UserManager> list = new ArrayList<>();

        // Xử lý sort mặc định
        if (sortField == null || sortField.isEmpty()) {
            sortField = "Fullname";
        }
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "asc";
        }

        // Chỉ cho phép các cột được sort
        List<String> validFields = Arrays.asList("User_ID", "Username", "Password", "Fullname", "Email", "Phone", "Address", "Role_name");
        if (!validFields.contains(sortField)) {
            sortField = "User_ID";
        }
        if (!sortOrder.equalsIgnoreCase("asc") && !sortOrder.equalsIgnoreCase("desc")) {
            sortOrder = "asc";
        }

        try {
            String sql = "SELECT u.User_ID, u.Username, u.Password, u.Fullname, u.Email, u.Phone, u.Address, r.Role_name "
                    + "FROM user u "
                    + "JOIN role r ON u.Role = r.Role_id "
                    + "WHERE 1=1";

            if (roleId != 0) {
                sql += " AND r.Role_id = ?";
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                sql += " AND u.Fullname LIKE ?";
            }

            sql += " ORDER BY " + sortField + " " + sortOrder;

            PreparedStatement ps = connection.prepareStatement(sql);

            int index = 1;
            if (roleId != 0) {
                ps.setInt(index++, roleId);
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(index++, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserManager u = new UserManager();
                u.setUserid(rs.getInt("User_ID"));
                u.setUsername(rs.getString("Username"));
                u.setPassword(rs.getString("Password"));
                u.setFullname(rs.getString("Fullname"));
                u.setEmail(rs.getString("Email"));
                u.setPhone(rs.getString("Phone"));
                u.setAddress(rs.getString("Address"));
                u.setRole(rs.getString("Role_name")); // lấy tên role
                list.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }

        return list;
    }

    public List<UserManager> getSortedUsersWithPaging(int roleId, String keyword, String sortField, String sortOrder, int offset, int limit) {
        List<UserManager> list = new ArrayList<>();
        try {
            String sql = "SELECT u.User_ID, u.Username, u.Password, u.Fullname, u.Email, u.Phone, u.Address, r.Role_name "
                    + "FROM user u JOIN role r ON u.Role = r.Role_id WHERE 1=1 "
                    + "AND (u.status IS NULL OR u.status != 'rejected')";

            if (roleId != 0) {
                sql += " AND r.Role_id = ?";
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                sql += " AND u.Fullname LIKE ?";
            }
            sql += " ORDER BY " + sortField + " " + sortOrder;
            sql += " LIMIT ?, ?";

            PreparedStatement ps = connection.prepareStatement(sql);

            int idx = 1;
            if (roleId != 0) {
                ps.setInt(idx++, roleId);
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(idx++, "%" + keyword + "%");
            }
            ps.setInt(idx++, offset);
            ps.setInt(idx, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserManager u = new UserManager();
                u.setUserid(rs.getInt("User_ID"));
                u.setUsername(rs.getString("Username"));
                u.setPassword(rs.getString("Password"));
                u.setFullname(rs.getString("Fullname"));
                u.setEmail(rs.getString("Email"));
                u.setPhone(rs.getString("Phone"));
                u.setAddress(rs.getString("Address"));
                u.setRole(rs.getString("Role_name"));
                list.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi SQL khi phân trang: " + e.getMessage());
        }

        return list;
    }

    public int getTotalUserCount(int roleId, String keyword) {
        int total = 0;
        try {
            String sql = "SELECT COUNT(*) FROM user u JOIN role r ON u.Role = r.Role_id WHERE 1=1 "
                    + "AND(u.status IS NULL OR u.status != 'rejected')";

            if (roleId != 0) {
                sql += " AND r.Role_id = ?";
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                sql += " AND u.Fullname LIKE ?";
            }

            PreparedStatement ps = connection.prepareStatement(sql);

            int idx = 1;
            if (roleId != 0) {
                ps.setInt(idx++, roleId);
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(idx++, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi đếm số lượng bản ghi: " + e.getMessage());
        }

        return total;
    }

    
    
    public void insertUser(User u) {
        String sql = "insert into user (User_ID,Username,Password,Fullname,Email,Phone,Address,Role) values(?,?,?,?,?,?,?,?);";

        try {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, u.getUserid());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getFullname());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getPhone());
            ps.setString(7, u.getAddress());
            ps.setInt(8, u.getRole());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void rejectUser(int userId){
        String sql = "UPDATE user SET status = 'rejected' where User_ID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    
    public void delete(int id){
        String sql = "delete from user "
                + "where User_ID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {

        UserDAO ud = new UserDAO();

        List<UserManager> users = ud.getSortedUsersWithPaging(
                0, "John", "Fullname", "ASC", 0, 10
        );

        for (UserManager user : users) {
            System.out.println(user);
        }

    }

}
