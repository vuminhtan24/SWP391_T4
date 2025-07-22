/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.SQLException;
import model.EmployeeInfo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LAPTOP
 */
public class EmployeeDAO extends BaseDao {

    public List<EmployeeInfo> getAll() {
        List<EmployeeInfo> list = new ArrayList<>();
        String sql = "SELECT * FROM employee_info";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                EmployeeInfo e = new EmployeeInfo(
                        rs.getInt("User_ID"),
                        rs.getString("Employee_Code"),
                        rs.getString("Contract_Type"),
                        rs.getDate("Start_Date") != null ? rs.getDate("Start_Date").toLocalDate() : null,
                        rs.getDate("End_Date") != null ? rs.getDate("End_Date").toLocalDate() : null,
                        rs.getString("Department"),
                        rs.getString("Position")
                );
                list.add(e);
            }
        } catch (SQLException e) {
        } finally {
            try {
                closeResources();
            } catch (Exception ex) {
            }
        }
        return list;
    }

    public List<EmployeeInfo> getFilteredEmployees(String keyword, String department, String sortColumn, String sortOrder, int offset, int limit) {
        List<EmployeeInfo> list = new ArrayList<>();
        String sql = "SELECT * FROM employee_info WHERE 1=1";

        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND (Employee_Code LIKE ? OR Position LIKE ?)";
        }

        if (department != null && !department.isEmpty()) {
            sql += " AND Department = ?";
        }

        if (sortColumn != null && !sortColumn.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
            sql += " ORDER BY " + sortColumn + " " + sortOrder;
        } else {
            sql += " ORDER BY User_ID ASC";
        }

        sql += " LIMIT ? OFFSET ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            int i = 1;
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(i++, "%" + keyword + "%");
                ps.setString(i++, "%" + keyword + "%");
            }

            if (department != null && !department.isEmpty()) {
                ps.setString(i++, department);
            }

            ps.setInt(i++, limit);
            ps.setInt(i++, offset);

            rs = ps.executeQuery();
            while (rs.next()) {
                EmployeeInfo e = new EmployeeInfo(
                        rs.getInt("User_ID"),
                        rs.getString("Employee_Code"),
                        rs.getString("Contract_Type"),
                        rs.getDate("Start_Date") != null ? rs.getDate("Start_Date").toLocalDate() : null,
                        rs.getDate("End_Date") != null ? rs.getDate("End_Date").toLocalDate() : null,
                        rs.getString("Department"),
                        rs.getString("Position")
                );
                list.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<EmployeeInfo> getExpiredContracts() throws SQLException {
        List<EmployeeInfo> expiredEmployees = new ArrayList<>();
        String sql = "SELECT * FROM employee_info WHERE End_Date < CURDATE()";

        try {

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                EmployeeInfo emp = new EmployeeInfo();
                emp.setUserId(rs.getInt("User_ID"));
                emp.setEmployeeCode(rs.getString("Employee_Code"));
                emp.setContractType(rs.getString("Contract_Type"));
                emp.setStartDate(rs.getDate("Start_Date").toLocalDate());
                emp.setEndDate(rs.getDate("End_Date").toLocalDate());
                emp.setDepartment(rs.getString("Department"));
                emp.setPosition(rs.getString("Position"));
                expiredEmployees.add(emp);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return expiredEmployees;
    }

    public List<EmployeeInfo> getEmployeesExpiringInDays(int daysBefore) {
        List<EmployeeInfo> list = new ArrayList<>();
        String sql = "SELECT * FROM employee_info "
                + "WHERE DATEDIFF(End_Date, CURDATE()) BETWEEN 0 AND ?";
        try {
            connection = dbc.getConnection(); // thay bằng cách bạn mở kết nối
            ps = connection.prepareStatement(sql);
            ps.setInt(1, daysBefore);
            rs = ps.executeQuery();
            while (rs.next()) {
                EmployeeInfo e = new EmployeeInfo();
                e.setUserId(rs.getInt("User_ID"));
                e.setEmployeeCode(rs.getString("Employee_Code"));
                e.setContractType(rs.getString("Contract_Type"));
                e.setStartDate(rs.getDate("Start_Date").toLocalDate());
                e.setEndDate(rs.getDate("End_Date").toLocalDate());
                e.setDepartment(rs.getString("Department"));
                e.setPosition(rs.getString("Position"));
                list.add(e);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public int countFilteredEmployees(String keyword, String department) {
        String sql = "SELECT COUNT(*) FROM employee_info WHERE 1=1";
        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND (Employee_Code LIKE ? OR Position LIKE ?)";
        }
        if (department != null && !department.isEmpty()) {
            sql += " AND Department = ?";
        }

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);

            int i = 1;
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(i++, "%" + keyword + "%");
                ps.setString(i++, "%" + keyword + "%");
            }

            if (department != null && !department.isEmpty()) {
                ps.setString(i++, department);
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public EmployeeInfo getByUserId(int userId) {
        EmployeeInfo ei = null;
        String sql = "SELECT * FROM employee_info WHERE User_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                ei = new EmployeeInfo(
                        rs.getInt("User_ID"),
                        rs.getString("Employee_Code"),
                        rs.getString("Contract_Type"),
                        rs.getDate("Start_Date") != null ? rs.getDate("Start_Date").toLocalDate() : null,
                        rs.getDate("End_Date") != null ? rs.getDate("End_Date").toLocalDate() : null,
                        rs.getString("Department"),
                        rs.getString("Position")
                );
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getByUserId (EmployeeDAO): " + e.getMessage());
        }
        return ei;
    }

    public boolean exist(int userId) {
        String sql = "SELECT 1 FROM employee_info WHERE User_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Lỗi exist (EmployeeDAO): " + e.getMessage());
        }
        return false;
    }

    public void insert(EmployeeInfo e) {
        String sql = "INSERT INTO employee_info (User_ID, Employee_Code, Contract_Type, Start_Date, End_Date, Department, Position) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, e.getUserId());
            ps.setString(2, e.getEmployeeCode());
            ps.setString(3, e.getContractType());
            ps.setDate(4, e.getStartDate() != null ? java.sql.Date.valueOf(e.getStartDate()) : null);
            ps.setDate(5, e.getEndDate() != null ? java.sql.Date.valueOf(e.getEndDate()) : null);

            ps.setString(6, e.getDepartment());
            ps.setString(7, e.getPosition());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Lỗi insert (EmployeeDAO): " + ex.getMessage());
        }
    }

    public void update(EmployeeInfo e) {
        String sql = "UPDATE employee_info SET Employee_Code = ?, Contract_Type = ?, Start_Date = ?, End_Date = ?, Department = ?, Position = ? WHERE User_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, e.getEmployeeCode());
            ps.setString(2, e.getContractType());
            ps.setDate(3, e.getStartDate() != null ? java.sql.Date.valueOf(e.getStartDate()) : null);
            ps.setDate(4, e.getEndDate() != null ? java.sql.Date.valueOf(e.getEndDate()) : null);
            ps.setString(5, e.getDepartment());
            ps.setString(6, e.getPosition());
            ps.setInt(7, e.getUserId());
            ps.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Lỗi update (EmployeeDAO): " + ex.getMessage());
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        // Test insert
        EmployeeInfo emp = new EmployeeInfo(
                1, "EMP001", "Full-Time",
                LocalDate.of(2024, 1, 10),
                LocalDate.of(2026, 1, 10),
                "IT", "Developer"
        );

        if (dao.exist(emp.getUserId())) {
            dao.update(emp);
            System.out.println("Employee updated.");
        } else {
            dao.insert(emp);
            System.out.println("Employee inserted.");
        }

        // Test getByUserId
        EmployeeInfo fetched = dao.getByUserId(1);
        if (fetched != null) {
            System.out.println("Employee fetched: " + fetched.getEmployeeCode());
        } else {
            System.out.println("Không tìm thấy nhân viên với ID: 1");
        }
    }
}
