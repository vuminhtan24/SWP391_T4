/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testcode;

import dal.EmployeeDAO;
import java.util.List;
import model.EmployeeInfo;

/**
 *
 * @author LAPTOP
 */
public class EmployeeDAOTest {

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        // Test 1: Không có lọc, không có từ khóa
        List<EmployeeInfo> employeeList = dao.getFilteredEmployees(null, null, null, null, 0, 10);

        System.out.println("Test 1 - All data:");
        for (EmployeeInfo e : employeeList) {
            System.out.println(e);
        }

        // Test 2: Tìm theo keyword "EMP00"
        List<EmployeeInfo> list2 = dao.getFilteredEmployees("EMP00", null, null, null, 0, 10);
        System.out.println("\nTest 2 - Search keyword 'EMP00':");
        for (EmployeeInfo e : list2) {
            System.out.println(e);
        }

        // Test 3: Lọc theo department "Sales"
        List<EmployeeInfo> list3 = dao.getFilteredEmployees(null, "Sales", null, null, 0, 10);
        System.out.println("\nTest 3 - Filter by Department 'Sales':");
        for (EmployeeInfo e : list3) {
            System.out.println(e);
        }

        // Test 4: Tìm theo keyword + department + sắp xếp
        List<EmployeeInfo> list4 = dao.getFilteredEmployees("EMP", "Sales", "Employee_Code", "DESC", 0, 10);
        System.out.println("\nTest 4 - Keyword + Department + Sort:");
        for (EmployeeInfo e : list4) {
            System.out.println(e);
        }

        // Test 5: Đếm số lượng dòng
        int count = dao.countFilteredEmployees("EMP00", "Sales");
        System.out.println("\nTest 5 - Count records with 'EMP00' and 'Sales': " + count);
    }
}
