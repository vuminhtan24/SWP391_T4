package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author FPT University - PRJ30X
 */
public class DBContext {

//    protected Connection connection;

//    public DBContext() {
//
//        try {
//            String url = "jdbc:mysql://localhost:3306/la_fioreria?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
//            String user = "root";
//
//            String pass = "1234";
//
//            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL driver mới
//
////            String url = "jdbc:mysql://localhost:3306/la_fioreria?user=root&password=1234";
////            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            connection = DriverManager.getConnection(url, user, pass);
//
//            System.out.println(" Connected to database!");
//        } catch (ClassNotFoundException | SQLException ex) {
//            ex.printStackTrace(); // để thấy lỗi rõ hơn khi debug
//        }
//    }
//
//    public static void main(String[] args) {
//        DBContext db = new DBContext();
//        if (db.connection != null) {
//            System.out.println("✅ Kết nối thành công");
//        } else {
//            System.out.println("❌ Không kết nối được");
//        }
//    }
    
    ResourceBundle bundle = ResourceBundle.getBundle("constant.Database");
    
    public Connection getConnection() {
        try {
            Class.forName(bundle.getString("drivername"));
            String url = bundle.getString("url");
            String username = bundle.getString("username"); 
            String password = bundle.getString("password");
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (ClassNotFoundException e) {
            String msg = "ClassNotFoundException throw from method getConnection()";
        } catch (SQLException e) {
            String msg = "SQLException throw from method getConnection()";
        } catch (Exception e) {
            String msg = "Unexpected Exception throw from method getConnection()";
        }
        return null;
    }

    //Test out connection
    public static void main(String[] args) {
        DBContext db = new DBContext();
        if(db.getConnection() != null) {
            System.out.println("Ok");
            return;
        }
        
        System.out.println("Not ok");
    }
}

