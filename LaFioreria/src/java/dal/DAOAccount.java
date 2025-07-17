package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

public class DAOAccount extends BaseDao {

    // Assuming you have instance variables like:
// private Connection connection;
// private PreparedStatement ps;
// private ResultSet rs;
// private DBC dbc; // Your database connection helper
// And a method closeResources() that closes rs, ps, and connection.
    public Vector<User> getAllAccount() {
        Vector<User> listAccount = new Vector<>();
        String sql = "SELECT * FROM la_fioreria.user";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                listAccount.add(new User(
                        rs.getInt("User_ID"),
                        rs.getString("username").trim(),
                        rs.getString("password").trim(),
                        rs.getString("fullname").trim(),
                        rs.getString("email").trim(),
                        rs.getString("phone").trim(),
                        rs.getString("address").trim(),
                        rs.getInt("Role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return listAccount;
    }

    public boolean deleteAccount(int userId) {
        String sql = "DELETE FROM la_fioreria.user WHERE User_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return false;
    }

    public boolean createAccount(User acc) {
        String sql = "INSERT INTO la_fioreria.user (username, password, fullname, email, phone, address, Role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, acc.getUsername());
            String hashedPassword = BCrypt.hashpw(acc.getPassword(), BCrypt.gensalt());
            ps.setString(2, hashedPassword);
            ps.setString(3, acc.getFullname());
            ps.setString(4, acc.getEmail());
            ps.setString(5, acc.getPhone());
            ps.setString(6, acc.getAddress());
            ps.setInt(7, acc.getRole());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return false;
    }
     public User validateEmail(String email, String inputPassword) {
        String sql = "SELECT * FROM la_fioreria.user WHERE email = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                String hashedPasswordFromDB = rs.getString("password").trim();

                // So sánh mật khẩu người dùng nhập với mật khẩu đã mã hóa trong DB
                if (BCrypt.checkpw(inputPassword, hashedPasswordFromDB)) {
                    return new User(
                            rs.getInt("User_ID"),
                            rs.getString("username").trim(),
                            rs.getString("password").trim(), // hoặc hashedPasswordFromDB
                            rs.getString("fullname").trim(),
                            rs.getString("email").trim(),
                            rs.getString("phone").trim(),
                            rs.getString("address").trim(),
                            rs.getInt("Role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }
    public User validate(String username, String inputPassword) {
        String sql = "SELECT * FROM la_fioreria.user WHERE username = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                String hashedPasswordFromDB = rs.getString("password").trim();

                // So sánh mật khẩu người dùng nhập với mật khẩu đã mã hóa trong DB
                if (BCrypt.checkpw(inputPassword, hashedPasswordFromDB)) {
                    return new User(
                            rs.getInt("User_ID"),
                            rs.getString("username").trim(),
                            rs.getString("password").trim(), // hoặc hashedPasswordFromDB
                            rs.getString("fullname").trim(),
                            rs.getString("email").trim(),
                            rs.getString("phone").trim(),
                            rs.getString("address").trim(),
                            rs.getInt("Role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }

    public User getAccountById(int userId) {
        String sql = "SELECT * FROM la_fioreria.user WHERE User_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("User_ID"),
                        rs.getString("username").trim(),
                        rs.getString("password").trim(),
                        rs.getString("fullname").trim(),
                        rs.getString("email").trim(),
                        rs.getString("phone").trim(),
                        rs.getString("address").trim(),
                        rs.getInt("Role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }

    public String getRoleNameById(int roleId) {
        String sql = "SELECT Role_name FROM la_fioreria.role WHERE Role_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, roleId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Role_name").trim();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return "Unknown";
    }

    public User getAccountByEmail(String email) {
        String sql = "SELECT * FROM la_fioreria.user WHERE email = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("User_ID"),
                        rs.getString("username").trim(),
                        rs.getString("password").trim(),
                        rs.getString("fullname").trim(),
                        rs.getString("email").trim(),
                        rs.getString("phone").trim(),
                        rs.getString("address").trim(),
                        rs.getInt("Role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }

    public boolean updateAccount(User acc) {
        String sql = "UPDATE la_fioreria.user SET username = ?, fullname = ?, address = ?, phone = ?, roleId = ? WHERE User_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getFullname());
            ps.setString(3, acc.getAddress());
            ps.setString(4, acc.getPhone());
            ps.setInt(5, acc.getRole());
            ps.setInt(6, acc.getUserid());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return false;
    }

    public void updatePassword(String email, String password) {
        String sql = "UPDATE la_fioreria.user SET password = ? WHERE email = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            ps.setString(1, hashedPassword);
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public boolean changePassword(User acc) {
        String sql = "UPDATE la_fioreria.user SET password = ? WHERE User_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            String hashedPassword = BCrypt.hashpw(acc.getPassword(), BCrypt.gensalt());
            ps.setString(1, hashedPassword);
            ps.setInt(2, acc.getUserid());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return false;
    }

    public boolean updatePasswordById(int userId, String hashedPassword) {
        String sql = "UPDATE la_fioreria.user SET password = ? WHERE User_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, hashedPassword);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // Tạo đối tượng DAO
        DAOAccount userDAO = new DAOAccount();

        // Thông tin test
        String testUsername = "admin01";
        String testPassword = "admin123"; // đây là mật khẩu gốc chưa băm

        // Gọi hàm validate
        User user = userDAO.validate(testUsername, testPassword);

        // In kết quả
        if (user != null) {
            System.out.println("Đăng nhập thành công:");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Full Name: " + user.getFullname());
        } else {
            System.out.println("Sai tài khoản hoặc mật khẩu.");
        }
    }
}
