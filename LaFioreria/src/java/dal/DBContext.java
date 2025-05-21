package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author FPT University - PRJ30X
 */
public class DBContext {

    protected Connection connection;

    public DBContext() {

        //@Students: You are allowed to edit user, pass, url variables to fit 
        //your system configuration
        //You can also add more methods for Database Interaction tasks. 
        //But we recommend you to do it in another class
        // For example : StudentDBContext extends DBContext , 
        //where StudentDBContext is located in dal package, 

        try {
//            String url = "jdbc:mysql://localhost:3306/la_fioreria?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String pass = "KHAi@2692004";

            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL driver mới

            String url = "jdbc:mysql://localhost:3306/la_fioreria?user=root&password=KHAi@2692004";
//            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, user, pass);

            System.out.println(" Connected to database!");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace(); // để thấy lỗi rõ hơn khi debug
        }
    }

    public static void main(String[] args) {
        DBContext db = new DBContext();
        if (db.connection != null) {
            System.out.println("✅ Kết nối thành công");
        } else {
            System.out.println("❌ Không kết nối được");
        }
    }
}

