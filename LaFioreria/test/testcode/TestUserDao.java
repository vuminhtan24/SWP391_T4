/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testcode;

import dal.UserDAO;
import java.util.List;

/**
 *
 * @author LAPTOP
 */
public class TestUserDao {

    public static void main(String[] args) {

        UserDAO ud = new UserDAO();
//        List<Integer> lst = ud.getIds();
//        for (int i = 0; i < lst.size(); i++) {
//            Integer get = lst.get(i);
//            System.out.println(get);
//        }
//        UserManager um = ud.getUserById(1);
//        System.out.println(um.toString());
        List<String> lst = ud.getRoleNames();
        for (int i = 0; i < lst.size(); i++) {
            String get = lst.get(i);
            System.out.print(get + " ");
        }

    }
}
