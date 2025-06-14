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
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ADMIN
 */
public class UserDAO extends BaseDao {

    // Assuming you have instance variables:
// private Connection connection;
// private PreparedStatement ps;
// private ResultSet rs;
// private DBC dbc; // Your database connection helper
// And a method closeResources() that closes rs, ps, and connection.
    public List<User> getAll() {
        List<User> listUser = new ArrayList<>();
        String sql = "SELECT * FROM la_fioreria.user";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
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
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }

        return listUser;
    }

    public UserManager getUserByUsername(String username) {
        UserManager user = null;
        String sql = "SELECT username, password, fullname, email, phone, address FROM Users WHERE username = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new UserManager();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullname(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
            }

        } catch (SQLException e) {
        }

        return user;
    }

    public void Update(User u) {
        String sql = "UPDATE la_fioreria.user SET Username = ?, Password = ?, Fullname = ?, Email = ?, Phone = ?, Address = ?, Role = ? WHERE User_ID = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, u.getUsername().trim());
            ps.setString(2, u.getPassword().trim());
            ps.setString(3, u.getFullname().trim());
            ps.setString(4, u.getEmail().trim());
            ps.setString(5, u.getPhone().trim());
            ps.setString(6, u.getAddress().trim());
            ps.setInt(7, u.getRole());
            ps.setInt(8, u.getUserid());
            ps.executeUpdate();
        } catch (SQLException e) {
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public List<Role> getAllRole() {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT * FROM la_fioreria.role";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Role_id");
                String role_name = rs.getString("Role_name").trim();
                Role r = new Role(id, role_name);
                list.add(r);
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

        return list;
    }

    public List<String> getRoleNames() {
        List<String> listRole = new ArrayList<>();
        String sql = "SELECT Role_name FROM la_fioreria.role";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String role_name = rs.getString("Role_name").trim();
                listRole.add(role_name);
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

        return listRole;
    }

    public List<Integer> getIds() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT User_ID FROM la_fioreria.user";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("User_ID");
                ids.add(id);
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

        return ids;
    }

    public UserManager getUserById(int id) {
        String sql
                = "SELECT u.User_ID, u.Username, u.Password, u.Fullname, u.Email, u.Phone, u.Address, r.Role_name "
                + "FROM la_fioreria.user u "
                + "JOIN la_fioreria.role r ON u.Role = r.Role_id "
                + "WHERE u.User_ID = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                String user_name = rs.getString("Username").trim();
                String password = rs.getString("Password").trim();
                String fullname = rs.getString("Fullname").trim();
                String email = rs.getString("Email").trim();
                String phone = rs.getString("Phone").trim();
                String address = rs.getString("Address").trim();
                String role = rs.getString("Role_name").trim();
                return new UserManager(id, user_name, password, fullname, email, phone, address, role);
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

    public User getUserByID(int userId) {

        User user = null;
        String sql = "SELECT * FROM la_fioreria.user WHERE User_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserid(rs.getInt("User_ID"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setFullname(rs.getString("Fullname"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));
                user.setAddress(rs.getString("Address"));
                user.setRole(rs.getInt("Role"));
            }

        } catch (SQLException e) {
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return user;
    }

    public List<UserManager> getAllUserManager() {
        List<UserManager> list = new ArrayList<>();
        String sql
                = "SELECT u.User_ID, u.Username, u.Password, u.Fullname, u.Email, u.Phone, u.Address, r.Role_name "
                + "FROM la_fioreria.user u "
                + "JOIN la_fioreria.role r ON u.Role = r.Role_id "
                + "ORDER BY u.User_ID";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String user_name = rs.getString("Username").trim();
                String password = rs.getString("Password").trim();
                String fullname = rs.getString("Fullname").trim();
                String email = rs.getString("Email").trim();
                String phone = rs.getString("Phone").trim();
                String address = rs.getString("Address").trim();
                String role = rs.getString("Role_name").trim();
                UserManager user = new UserManager(id, user_name, password, fullname, email, phone, address, role);
                list.add(user);
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

        return list;
    }

    public List<UserManager> getUserByRoleId(int role_id) {
        List<UserManager> list = new ArrayList<>();
        String sql
                = "SELECT u.User_ID, u.Username, u.Password, u.Fullname, u.Email, u.Phone, u.Address, r.Role_name "
                + "FROM la_fioreria.user u "
                + "JOIN la_fioreria.role r ON u.Role = r.Role_id "
                + "WHERE r.Role_id = ? "
                + "ORDER BY u.User_ID";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, role_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String user_name = rs.getString("Username").trim();
                String password = rs.getString("Password").trim();
                String fullname = rs.getString("Fullname").trim();
                String email = rs.getString("Email").trim();
                String phone = rs.getString("Phone").trim();
                String address = rs.getString("Address").trim();
                String role = rs.getString("Role_name").trim();
                UserManager user = new UserManager(id, user_name, password, fullname, email, phone, address, role);
                list.add(user);
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

        return list;
    }

    public List<UserManager> getUserByRoleIdSearchName(int role_id, String kw) {
        List<UserManager> list = new ArrayList<>();
        boolean filterByRole = (role_id != 0);
        String sql;

        if (filterByRole) {
            sql
                    = "SELECT u.User_ID, u.Username, u.Password, u.Fullname, u.Email, u.Phone, u.Address, r.Role_name "
                    + "FROM la_fioreria.user u "
                    + "JOIN la_fioreria.role r ON u.Role = r.Role_id "
                    + "WHERE u.Username LIKE ? AND r.Role_id = ? "
                    + "ORDER BY u.User_ID";
        } else {
            sql
                    = "SELECT u.User_ID, u.Username, u.Password, u.Fullname, u.Email, u.Phone, u.Address, r.Role_name "
                    + "FROM la_fioreria.user u "
                    + "JOIN la_fioreria.role r ON u.Role = r.Role_id "
                    + "WHERE u.Username LIKE ? "
                    + "ORDER BY u.User_ID";
        }

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + kw.trim() + "%");
            if (filterByRole) {
                ps.setInt(2, role_id);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String user_name = rs.getString("Username").trim();
                String password = rs.getString("Password").trim();
                String fullname = rs.getString("Fullname").trim();
                String email = rs.getString("Email").trim();
                String phone = rs.getString("Phone").trim();
                String address = rs.getString("Address").trim();
                String role = rs.getString("Role_name").trim();
                UserManager user = new UserManager(id, user_name, password, fullname, email, phone, address, role);
                list.add(user);
            }
        } catch (SQLException e) {
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }

        return list;
    }

    public List<UserManager> getSortedUsers(int roleId, String keyword, String sortField, String sortOrder) {
        List<UserManager> list = new ArrayList<>();

        if (sortField == null || sortField.isEmpty()) {
            sortField = "Fullname";
        }
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "asc";
        }

        List<String> validFields = Arrays.asList(
                "User_ID", "Username", "Password", "Fullname", "Email", "Phone", "Address", "Role_name"
        );
        if (!validFields.contains(sortField)) {
            sortField = "User_ID";
        }
        if (!sortOrder.equalsIgnoreCase("asc") && !sortOrder.equalsIgnoreCase("desc")) {
            sortOrder = "asc";
        }

        StringBuilder sql = new StringBuilder(
                "SELECT u.User_ID, u.Username, u.Password, u.Fullname, u.Email, u.Phone, u.Address, r.Role_name "
                + "FROM la_fioreria.user u "
                + "JOIN la_fioreria.role r ON u.Role = r.Role_id "
                + "WHERE 1=1"
        );

        if (roleId != 0) {
            sql.append(" AND r.Role_id = ?");
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND u.Fullname LIKE ?");
        }
        sql.append(" ORDER BY ").append(sortField).append(" ").append(sortOrder);

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql.toString());

            int index = 1;
            if (roleId != 0) {
                ps.setInt(index++, roleId);
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(index++, "%" + keyword.trim() + "%");
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                UserManager u = new UserManager();
                u.setUserid(rs.getInt("User_ID"));
                u.setUsername(rs.getString("Username").trim());
                u.setPassword(rs.getString("Password").trim());
                u.setFullname(rs.getString("Fullname").trim());
                u.setEmail(rs.getString("Email").trim());
                u.setPhone(rs.getString("Phone").trim());
                u.setAddress(rs.getString("Address").trim());
                u.setRole(rs.getString("Role_name").trim());
                list.add(u);
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

        return list;
    }

    public List<UserManager> getSortedUsersWithPaging(int roleId, String keyword, String sortField, String sortOrder, int offset, int limit) {
        List<UserManager> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT u.User_ID, u.Username, u.Password, u.Fullname, u.Email, u.Phone, u.Address, r.Role_name "
                + "FROM la_fioreria.user u "
                + "JOIN la_fioreria.role r ON u.Role = r.Role_id "
                + "WHERE (u.status IS NULL OR u.status != 'rejected')"
        );

        if (roleId != 0) {
            sql.append(" AND r.Role_id = ?");
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND u.Fullname LIKE ?");
        }
        sql.append(" ORDER BY ").append(sortField).append(" ").append(sortOrder);
        sql.append(" LIMIT ?, ?");

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql.toString());

            int idx = 1;
            if (roleId != 0) {
                ps.setInt(idx++, roleId);
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(idx++, "%" + keyword.trim() + "%");
            }
            ps.setInt(idx++, offset);
            ps.setInt(idx, limit);

            rs = ps.executeQuery();
            while (rs.next()) {
                UserManager u = new UserManager();
                u.setUserid(rs.getInt("User_ID"));
                u.setUsername(rs.getString("Username").trim());
                u.setPassword(rs.getString("Password").trim());
                u.setFullname(rs.getString("Fullname").trim());
                u.setEmail(rs.getString("Email").trim());
                u.setPhone(rs.getString("Phone").trim());
                u.setAddress(rs.getString("Address").trim());
                u.setRole(rs.getString("Role_name").trim());
                list.add(u);
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

        return list;
    }

    public int getTotalUserCount(int roleId, String keyword) {
        int total = 0;
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM la_fioreria.user u "
                + "JOIN la_fioreria.role r ON u.Role = r.Role_id "
                + "WHERE (u.status IS NULL OR u.status != 'rejected')"
        );

        if (roleId != 0) {
            sql.append(" AND r.Role_id = ?");
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND u.Fullname LIKE ?");
        }

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql.toString());

            int idx = 1;
            if (roleId != 0) {
                ps.setInt(idx++, roleId);
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(idx++, "%" + keyword.trim() + "%");
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
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

        return total;
    }

    public void insertUser(User u) {
        String sql
                = "INSERT INTO la_fioreria.user "
                + "(User_ID, Username, Password, Fullname, Email, Phone, Address, Role) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, u.getUserid());
            ps.setString(2, u.getUsername().trim());
            String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
            ps.setString(3, hashedPassword);
            ps.setString(4, u.getFullname().trim());
            ps.setString(5, u.getEmail().trim());
            ps.setString(6, u.getPhone().trim());
            ps.setString(7, u.getAddress().trim());
            ps.setInt(8, u.getRole());
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

    public void insertNewUser(User u) {
        String sql = "INSERT INTO la_fioreria.user "
                + "(Username, Password, Fullname, Email, Phone, Address, Role) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, u.getUsername().trim());
            ps.setString(2, u.getPassword().trim());String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
            ps.setString(3, hashedPassword);
            ps.setString(4, u.getEmail().trim());
            ps.setString(5, u.getPhone().trim());
            ps.setString(6, u.getAddress().trim());
            ps.setInt(7, u.getRole());

            ps.executeUpdate();
        } catch (SQLException e) {
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public int getNextUserId() {
        int nextId = 1;
        String sql = "SELECT MAX(User_ID) FROM la_fioreria.user";
        try {
            connection = dbc.getConnection();

            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                nextId = rs.getInt(1) + 1; // Tăng thêm 1
            }
        } catch (SQLException e) {
            // Có thể log lỗi ra file

        }
        return nextId;
    }

    public void rejectUser(int userId) {

        String sql = "UPDATE user SET status = 'rejected' where User_ID = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public void delete(int id) {

        String sql = "delete from user "
                + "where User_ID = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
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

        UserDAO ud = new UserDAO();

        User user1 = new User(
            13, // User_ID duy nhất
            "shipper",
            "123",
            "Vu Minh Tan",
            "vuminhtan2004@gmail.com",
            "0901234567",
            "123 Main St, Anytown",
            8
        );
        ud.insertNewUser(user1);

    }

}
