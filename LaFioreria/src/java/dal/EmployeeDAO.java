/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.Date;
import model.EmployeeInfo;
import java.time.LocalDate;

/**
 *
 * @author LAPTOP
 */
public class EmployeeDAO extends BaseDao {

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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception ex) {
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
