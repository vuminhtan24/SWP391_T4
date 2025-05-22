/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testcode;

import dal.UserDAO;
import java.util.List;
import model.User;
import model.UserManager;

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
        User u = new User(8, "naruto", "1234", "Uzumaki naruto", "naruto@flower.com", "090112301", "konoha village", 7);
        ud.Update(u);
        UserManager um = ud.getUserById(8);
        System.out.println(um.toString());
        List<String> lst = ud.getRoleNames();
        for (int i = 0; i < lst.size(); i++) {
            String get = lst.get(i);
            System.out.print(get + " ");
        }
//
//    }

    }
}
