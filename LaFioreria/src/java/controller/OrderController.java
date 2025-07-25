package controller;

import dal.FeedbackDAO;
import dal.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Feedback;
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
            request.setAttribute("error", "Please login to view order history.");
            request.getRequestDispatcher("/ZeShopper/order.jsp").forward(request, response);
            return;
        }

        // Lấy customerId
        Integer customerId = currentUser.getUserid();
        if (customerId == null) {
            request.setAttribute("error", "No client ID found in the login session.");
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
                request.setAttribute("error", "Invalid status format.");
                request.getRequestDispatcher("/ZeShopper/order.jsp").forward(request, response);
                return;
            }
        }

        try {
            OrderDAO orderDAO = new OrderDAO();
            FeedbackDAO feedbackDAO = new FeedbackDAO();
            // Lấy danh sách đơn hàng, lọc theo customerId và statusId
            List<Order> orders = orderDAO.searchOrders(null, statusId, 1, Integer.MAX_VALUE, "orderDate", "desc");
            orders.removeIf(order -> order.getCustomerId() != customerId);

            // Lấy chi tiết đơn hàng cho tất cả đơn hàng
            List<List<OrderDetail>> orderDetailsList = new ArrayList<>();
            for (Order order : orders) {
                List<OrderDetail> details = orderDAO.getOrderItemsByOrderId(order.getOrderId());
                orderDetailsList.add(details);
            }
            
            // Check feedback eligibility for each order item
            Map<String, Boolean> canWriteFeedbackMap = new HashMap<>();
            for (Order order : orders) {
                for (OrderDetail detail : orderDAO.getOrderItemsByOrderId(order.getOrderId())) {
                    int bouquetId = detail.getBouquetId();
                    boolean canWrite = feedbackDAO.canWriteFeedback(currentUser.getUserid(), bouquetId, order.getOrderId());
                    String key = order.getOrderId() + "-" + bouquetId; 
                    canWriteFeedbackMap.put(key, canWrite);
                }
            }

            // Đặt dữ liệu vào request
            request.setAttribute("orders", orders);
            request.setAttribute("orderDetailsList", orderDetailsList);
            request.setAttribute("selectedStatus", status != null ? status : "all");
            request.setAttribute("canWriteFeedbackMap", canWriteFeedbackMap);
        } catch (Exception e) {
            request.setAttribute("error", "Unable to load order list: " + e.getMessage());
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
                request.setAttribute("error", "Please login to cancel order.");
                doGet(request, response);
                return;
            }
            try {
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                OrderDAO orderDAO = new OrderDAO();
                Order order = orderDAO.getOrderDetailById(orderId);
                if (order.getCustomerId() == currentUser.getUserid() && order.getStatusId() == 1 && "VietQR".equals(order.getPaymentMethod())) {
                    orderDAO.updateOrderStatus(orderId, 5); // 5: Đã hủy
                    request.setAttribute("message", "Order cancellation successful.");
                } else {
                    request.setAttribute("error", "This order cannot be cancelled..");
                }
            } catch (Exception e) {
                request.setAttribute("error", "Order cannot be cancelled: " + e.getMessage());
            }
        }
        doGet(request, response);
    }
}
