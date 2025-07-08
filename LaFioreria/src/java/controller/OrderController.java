package controller;

import dal.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.OrderDetail;
import model.User;

@WebServlet(name = "OrderController", urlPatterns = {"/ZeShopper/order"})
public class OrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin người dùng hiện tại
        User currentUser = (User) request.getSession().getAttribute("currentAcc");
        if (currentUser == null) {
            request.setAttribute("error", "Vui lòng đăng nhập để xem lịch sử đơn hàng.");
            request.getRequestDispatcher("/ZeShopper/order.jsp").forward(request, response);
            return;
        }

        // Lấy customerId
        Integer customerId = currentUser.getUserid();
        if (customerId == null) {
            request.setAttribute("error", "Không tìm thấy ID khách hàng trong phiên đăng nhập.");
            request.getRequestDispatcher("/ZeShopper/order.jsp").forward(request, response);
            return;
        }

        // Lấy tham số status để lọc
        String status = request.getParameter("status");
        Integer statusId = null;
        if (status != null && !status.trim().isEmpty() && !"all".equals(status)) {
            try {
                statusId = Integer.parseInt(status);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Định dạng trạng thái không hợp lệ.");
                request.getRequestDispatcher("/ZeShopper/order.jsp").forward(request, response);
                return;
            }
        }

        try {
            OrderDAO orderDAO = new OrderDAO();
            // Lấy danh sách đơn hàng, lọc theo customerId và statusId
            List<Order> orders = orderDAO.searchOrders(null, statusId, 1, Integer.MAX_VALUE, "orderDate", "desc");
            orders.removeIf(order -> order.getCustomerId() != customerId);

            // Lấy chi tiết đơn hàng cho tất cả đơn hàng
            List<List<OrderDetail>> orderDetailsList = new ArrayList<>();
            for (Order order : orders) {
                List<OrderDetail> details = orderDAO.getOrderItemsByOrderId(order.getOrderId());
                orderDetailsList.add(details);
            }

            // Đặt dữ liệu vào request
            request.setAttribute("orders", orders);
            request.setAttribute("orderDetailsList", orderDetailsList);
            request.setAttribute("selectedStatus", status != null ? status : "all");

        } catch (Exception e) {
            request.setAttribute("error", "Không thể tải danh sách đơn hàng: " + e.getMessage());
        }

        // Chuyển tiếp đến JSP
        request.getRequestDispatcher("/ZeShopper/order.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("cancel".equals(action)) {
            User currentUser = (User) request.getSession().getAttribute("currentAcc");
            if (currentUser == null) {
                request.setAttribute("error", "Vui lòng đăng nhập để hủy đơn hàng.");
                doGet(request, response);
                return;
            }
            try {
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                OrderDAO orderDAO = new OrderDAO();
                Order order = orderDAO.getOrderDetailById(orderId);
                if (order.getCustomerId() == currentUser.getUserid() && order.getStatusId() == 1 && "VietQR".equals(order.getPaymentMethod())) {
                    orderDAO.updateOrderStatus(orderId, 5); // 5: Đã hủy
                    request.setAttribute("message", "Hủy đơn hàng thành công.");
                } else {
                    request.setAttribute("error", "Không thể hủy đơn hàng này.");
                }
            } catch (Exception e) {
                request.setAttribute("error", "Không thể hủy đơn hàng: " + e.getMessage());
            }
        }
        doGet(request, response);
    }
}
