package controller;

import dal.CartDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import model.CartDetail;
import model.Order;
import model.OrderItem;
import model.User;

/**
 *
 * @author Legion
 */
@WebServlet(name = "CheckOutController", urlPatterns = {"/ZeShopper/checkout"})
public class CheckOutController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin từ form
//        String fullName = request.getParameter("fullName");
//        String email = request.getParameter("email");
//        String phone = request.getParameter("phone");
//        String address = request.getParameter("address");

        // Lấy user từ session
        User currentUser = (User) request.getSession().getAttribute("currentAcc");
        int customerId = currentUser.getUserid();

        String totalAmount = request.getParameter("total");

        // Tạo đơn hàng
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderDate(LocalDate.now().toString());
        order.setTotalAmount(totalAmount);
        order.setStatusId(1); // mặc định

        try {
            CartDAO dao = new CartDAO();
            int orderId = dao.insertOrder(order);
            CartDAO cartDAO = new CartDAO();
            List<CartDetail> cartItems = cartDAO.getCartDetailsByCustomerId(customerId);
            for (CartDetail c : cartItems) {
                OrderItem item = new OrderItem(orderId, c.getBouquetId(), c.getQuantity(), c.getBouquet().getPrice());
                dao.insertOrderItem(item);
            }
            dao.deleteCartByCustomerId(customerId);
            response.sendRedirect("./thanks-you.jsp");
        } catch (IOException e) {
            response.sendRedirect("./404.jsp");
        }
    }

}
