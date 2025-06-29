/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testcode;

import dal.CustomerDAO;
import model.CustomerInfo;

/**
 *
 * @author LAPTOP
 */
public class CustomerDAOTest {

    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();

        // Tạo dữ liệu giả lập để test update
        CustomerInfo testCustomer = new CustomerInfo();
        testCustomer.setUserId(1); // ID của user cần update (phải tồn tại trong DB)
        testCustomer.setCustomerCode("CUST999");
        testCustomer.setJoinDate("2023-01-01");
        testCustomer.setLoyaltyPoint(999);
        testCustomer.setBirthday("2000-05-05");
        testCustomer.setGender("Male");

        // Gọi update
        customerDAO.update(testCustomer);

        System.out.println("Đã gọi update() cho CustomerInfo:");
        System.out.println("User ID: " + testCustomer.getUserId());
        System.out.println("Code: " + testCustomer.getCustomerCode());
        System.out.println("Join Date: " + testCustomer.getJoinDate());
        System.out.println("Loyalty Point: " + testCustomer.getLoyaltyPoint());
        System.out.println("Birthday: " + testCustomer.getBirthday());
        System.out.println("Gender: " + testCustomer.getGender());
    }
}

