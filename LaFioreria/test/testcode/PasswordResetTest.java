/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testcode;

import dal.DAOAccount;

/**
 *
 * @author LAPTOP
 */
public class PasswordResetTest {

    public static void main(String[] args) {
        String emailToReset = "alice@flower.com";
        String tempPassword = "adimin123456";

        DAOAccount dao = new DAOAccount();
        dao.updatePassword(emailToReset, tempPassword);  // ✅ BCrypt sẽ tự động hash

        System.out.println("Đã cập nhật mật khẩu thành công cho " + emailToReset);
    }
}
