package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.User;

public class DAOAccount extends DBContext {

    public Vector<User> getAllAccount() {
        String sql = "SELECT * FROM user";
        Vector<User> listAccount = new Vector<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                listAccount.add(new User(
                        rs.getInt("User_ID"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getInt("Role"))
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listAccount;
    }

    public boolean deleteAccount(int userId) {
        String sql = "DELETE FROM [user] WHERE userId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean createAccount(User acc) {
        String sql = "INSERT INTO user (username, password, fullname, email, phone, address, Role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, acc.getUsername());
            st.setString(2, acc.getPassword());
            st.setString(3, acc.getFullname());
            st.setString(4, acc.getEmail());
            st.setString(5, acc.getPhone());
            st.setString(6, acc.getAddress());
            st.setInt(7, acc.getRole());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public User validate(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("User_ID"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getInt("Role"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User getAccountById(int userId) {
        String sql = "SELECT * FROM user WHERE User_ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("User_ID"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getInt("Role"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User getAccountByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("User_ID"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getInt("Role"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updateAccount(User acc) {
        String sql = "UPDATE user SET username = ?, fullname = ?, address = ?, phone = ?, roleId = ? WHERE User_ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, acc.getUsername());
            st.setString(2, acc.getFullname());
            st.setString(3, acc.getAddress());
            st.setString(4, acc.getPhone());
            st.setInt(5, acc.getRole());
            st.setInt(6, acc.getUserid());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean updatePassword(User acc) {
        String sql = "UPDATE user SET password = ? WHERE User_ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, acc.getPassword());
            st.setInt(2, acc.getUserid());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        DAOAccount dao = new DAOAccount();
System.out.println("\n== TEST GET BY EMAIL ==");
System.out.println(dao.getAccountByEmail("bob@flower.com"));     // ✅ manager01
System.out.println(dao.getAccountByEmail("none@abc.com")); 


    }
}
