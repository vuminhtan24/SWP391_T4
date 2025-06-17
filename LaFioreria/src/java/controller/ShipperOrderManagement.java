package controller;

import dal.OrderDAO;
import model.Order;
import model.User;
import model.OrderStatus;
import model.OrderDetail;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException; // Sử dụng jakarta.servlet cho môi trường Jakarta EE 9+
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ShipperOrderManagement", urlPatterns = {"/shipperDashboard"}) // Đã đổi urlPatterns
public class ShipperOrderManagement extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // Giữ UTF-8 cho nội dung

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentAcc");

        if (currentUser == null || currentUser.getRole() != 8) { // Giả sử role 8 là Shipper
            response.sendRedirect(request.getContextPath() + "/ZeShopper/LoginServlet?error=unauthorized");
            return;
        }

        int shipperId = currentUser.getUserid();

        OrderDAO orderDAO = new OrderDAO();
        String action = request.getParameter("action");

        if (action == null || action.isEmpty() || "list".equals(action)) {
            List<Integer> statusFilter = new ArrayList<>();
            statusFilter.add(3); // Shipping (Giả sử)
            statusFilter.add(4); // Delivered (Giả sử)

            List<Order> orders = orderDAO.getOrdersByShipperIdAndStatuses(shipperId, statusFilter);
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/DashMin/shipperOrderList.jsp").forward(request, response);

        } else if ("viewDetail".equals(action)) {
            int orderId;
            try {
                orderId = Integer.parseInt(request.getParameter("orderId"));
            } catch (NumberFormatException e) {
                // Sử dụng session để truyền thông báo lỗi qua redirect
                session.setAttribute("errorMessage", "Invalid Order ID parameter.");
                response.sendRedirect(request.getContextPath() + "/shipperDashboard");
                return;
            }

            Order order = orderDAO.getOrderDetailById(orderId);

            if (order == null || order.getShipperId() == null || order.getShipperId() != shipperId) {
                session.setAttribute("errorMessage", "Unauthorized access or Order not found.");
                response.sendRedirect(request.getContextPath() + "/shipperDashboard");
                return;
            }

            List<OrderDetail> orderItems = orderDAO.getOrderItemsByOrderId(orderId);

            request.setAttribute("order", order);
            request.setAttribute("orderItems", orderItems);
            request.getRequestDispatcher("/DashMin/shipperOrderDetails.jsp").forward(request, response);

        } else if ("updateStatus".equals(action)) {
            int orderId;
            int newStatusId;
            try {
                orderId = Integer.parseInt(request.getParameter("orderId"));
                newStatusId = Integer.parseInt(request.getParameter("newStatusId"));
            } catch (NumberFormatException e) {
                session.setAttribute("errorMessage", "Invalid parameters for status update.");
                response.sendRedirect(request.getContextPath() + "/shipperDashboard");
                return;
            }

            Order order = orderDAO.getOrderDetailById(orderId);

            if (order == null || order.getShipperId() == null || order.getShipperId() != shipperId) {
                session.setAttribute("errorMessage", "You are not authorized to update this order.");
            } else {
                if (order.getStatusId() == 3 && newStatusId == 4) { // From Shipping (3) to Delivered (4)
                    boolean success = orderDAO.updateOrderStatus(orderId, newStatusId);
                    if (success) {
                        session.setAttribute("message", "Order status updated successfully.");
                    } else {
                        session.setAttribute("errorMessage", "Failed to update order status.");
                    }
                } else {
                    session.setAttribute("errorMessage", "Invalid status transition or order not in 'Shipping' status.");
                }
            }
            response.sendRedirect(request.getContextPath() + "/shipperDashboard?action=viewDetail&orderId=" + orderId);
        } else {
            response.sendRedirect(request.getContextPath() + "/shipperDashboard");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Shipper Order Management Servlet";
    }
}