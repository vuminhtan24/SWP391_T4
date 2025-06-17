package controller; // Hoặc package chứa các Servlet của bạn

import dal.OrderDAO;
import model.Order;
import model.OrderDetail;
import model.OrderStatus;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User; 
@WebServlet(name = "ShipperOrderDetailsServlet", urlPatterns = {"/DashMin/shipperOrderDetails"})
public class ShipperOrderDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("user");

        // Kiểm tra xem người dùng có đăng nhập và có phải là Shipper hay không (role = 8)
        if (loggedInUser == null || loggedInUser.getRole() != 8) { // Assuming role 8 is Shipper
            response.sendRedirect("login"); // Chuyển hướng về trang đăng nhập nếu không phải shipper
            return;
        }

        String orderIdParam = request.getParameter("orderId");
        int orderId = 0;
        try {
            orderId = Integer.parseInt(orderIdParam);
        } catch (NumberFormatException e) {
            System.err.println("Invalid orderId parameter: " + orderIdParam);
            response.sendRedirect("errorPage.jsp"); // Hoặc chuyển hướng về dashboard shipper
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        
        // Lấy thông tin chi tiết đơn hàng
        Order order = orderDAO.getOrderDetailById(orderId);
        
        // Lấy danh sách các mặt hàng trong đơn hàng
        List<OrderDetail> orderItems = orderDAO.getOrderItemsByOrderId(orderId);
        
        // Lấy danh sách tất cả các trạng thái đơn hàng (để hiển thị dropdown cho shipper cập nhật)
        List<OrderStatus> orderStatuses = orderDAO.getAllOrderStatuses();

        if (order != null) {
            request.setAttribute("order", order);
            request.setAttribute("orderItems", orderItems);
            request.setAttribute("orderStatuses", orderStatuses);
            request.getRequestDispatcher("shipperOrderDetails.jsp").forward(request, response);
        } else {
            // Xử lý trường hợp không tìm thấy đơn hàng
            System.err.println("Order with ID " + orderId + " not found.");
            response.sendRedirect("errorPage.jsp"); // Hoặc chuyển hướng về dashboard shipper
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý khi shipper cập nhật trạng thái đơn hàng
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("user");

        if (loggedInUser == null || loggedInUser.getRole() != 8) {
            response.sendRedirect("login");
            return;
        }

        String orderIdParam = request.getParameter("orderId");
        String newStatusIdParam = request.getParameter("newStatusId");

        try {
            int orderId = Integer.parseInt(orderIdParam);
            int newStatusId = Integer.parseInt(newStatusIdParam);

            OrderDAO orderDAO = new OrderDAO();
            boolean success = orderDAO.updateOrderStatus(orderId, newStatusId);

            if (success) {
                // Cập nhật thành công, chuyển hướng về trang chi tiết đơn hàng hoặc dashboard
                response.sendRedirect("shipperOrderDetails?orderId=" + orderId + "&success=true");
            } else {
                // Cập nhật thất bại
                response.sendRedirect("shipperOrderDetails?orderId=" + orderId + "&error=true");
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid parameters for status update: " + e.getMessage());
            response.sendRedirect("errorPage.jsp");
        } catch (Exception e) {
            System.err.println("Error updating order status: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("errorPage.jsp");
        }
    }
}