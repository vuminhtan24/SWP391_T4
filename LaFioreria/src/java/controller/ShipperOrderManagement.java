package controller;

import dal.OrderDAO;
import model.Order;
import model.User;
import model.OrderStatus;
import model.OrderDetail;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;

@WebServlet(name = "ShipperOrderManagement", urlPatterns = {"/shipperDashboard"})
public class ShipperOrderManagement extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentAcc");

        if (currentUser == null || currentUser.getRole() != 8) {
            response.sendRedirect(request.getContextPath() + "/ZeShopper/LoginServlet?error=unauthorized");
            return;
        }

        int shipperId = currentUser.getUserid();
        OrderDAO orderDAO = new OrderDAO();
        String action = request.getParameter("action");

        // --- Lấy và đặt statuses TRƯỚC BẤT KỲ lệnh forward nào đến JSP cần chúng ---
        // Điều này đảm bảo danh sách 'statuses' luôn có sẵn trong phạm vi request cho JSP
        List<OrderStatus> statuses = orderDAO.getAllOrderStatuses();
        request.setAttribute("statuses", statuses);
        // --- Kết thúc phần lấy trạng thái chung ---

        if (action == null || action.isEmpty() || "list".equals(action)) {
            List<Integer> statusFilter = Arrays.asList(3, 4, 5);

            // Phân trang
            int pageSize = 5;
            int currentPage = 1;
            try {
                String pageParam = request.getParameter("page");
                if (pageParam != null) {
                    currentPage = Integer.parseInt(pageParam);
                }
            } catch (NumberFormatException e) {
                currentPage = 1;
            }

            int offset = (currentPage - 1) * pageSize;

            // Tổng số đơn hàng
            int totalOrders = orderDAO.countOrdersByShipperIdAndStatuses(shipperId, statusFilter);
            int totalPages = (int) Math.ceil((double) totalOrders / pageSize);

            // Lấy danh sách đơn theo trang
            List<Order> orders = orderDAO.getOrdersByShipperIdAndStatusesPaged(shipperId, statusFilter, offset, pageSize);

            request.setAttribute("orders", orders);
            request.setAttribute("statuses", statuses); // dòng này giữ nguyên
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("/DashMin/shipperOrderList.jsp").forward(request, response);
        } else if ("viewDetail".equals(action)) {
            int orderId;
            try {
                orderId = Integer.parseInt(request.getParameter("orderId"));
            } catch (NumberFormatException e) {
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
                if (order.getStatusId() == 3 && newStatusId == 4) { // Từ Shipping (3) sang Delivered (4)
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
            // Sau khi cập nhật trạng thái, chuyển hướng trở lại dashboard, điều này sẽ tải lại danh sách đơn hàng và trạng thái.
            response.sendRedirect(request.getContextPath() + "/shipperDashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/shipperDashboard");
        }
        // Đã loại bỏ việc đặt trạng thái và forward trùng lặp ở đây, vì nó không thể đạt được trong nhiều trường hợp.
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
