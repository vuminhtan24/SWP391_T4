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
        User u = new User(10, "Madara", "1234", "Uchiha Madara", "Madara@flower.com", "090111234", "konoha village", 2);
//        ud.Update(u);
        ud.insertUser(u);
        UserManager um = ud.getUserById(10);
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
