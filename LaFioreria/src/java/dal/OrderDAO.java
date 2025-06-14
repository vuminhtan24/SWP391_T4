/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Order;

/**
 *
 * @author VU MINH TAN
 */
public class OrderDAO extends BaseDao{
    public List<Order> getAllOrders() {
        List<Order> listAccount = new ArrayList<>();
        String sql = "SELECT order_id, order_date, customer_id, total_amount, status_id FROM `order`";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                listAccount.add(new Order(
                        rs.getInt("order_id"),
                        rs.getString("order_date").trim(),
                        rs.getInt("customer_id"),
                        rs.getString("total_amount").trim(),
                        rs.getInt("status_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return listAccount;
    }
    public static void main(String[] args) {
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("Không có đơn hàng nào được tìm thấy.");
        } else {
            System.out.println("Danh sách tất cả các đơn hàng:");
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }
}
