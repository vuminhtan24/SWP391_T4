/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BouquetDAO;
import dal.OrderDAO;
import model.Order;
import model.User;
import model.OrderStatus;
import model.OrderDetail;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BouquetImage;

/**
 * This ser handles displaying order details and order editing functionality.
 *
 * @author VU MINH TAN
 */
@WebServlet(name = "OrderDetailServlet", urlPatterns = {"/orderDetail"})
public class OrderDetailServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method. Processes requests to display
     * order details or the order editing form.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set encoding for request and response to handle UTF-8 characters
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String orderIdParam = request.getParameter("orderId");
        String action = request.getParameter("action");

        if (orderIdParam == null || orderIdParam.isEmpty()) {
            System.out.println("OrderDetailServlet: orderIdParam is null or empty. Redirecting to orderManagement.");
            response.sendRedirect(request.getContextPath() + "/orderManagement");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdParam);
        } catch (NumberFormatException e) {
            System.err.println("OrderDetailServlet: Invalid Order ID: " + orderIdParam + ". Redirecting to orderManagement.");
            response.sendRedirect(request.getContextPath() + "/orderManagement");
            return;
        }

        OrderDAO orderDAO = new OrderDAO();

        List<OrderDetail> orderItems = orderDAO.getOrderItemsByOrderId(orderId);
        boolean allDone = true;

        for (OrderDetail item : orderItems) {
            if (!"done".equalsIgnoreCase(item.getStatus())) {
                allDone = false;
                break;
            }
        }

        BouquetDAO bdao = new BouquetDAO();
        Order order = orderDAO.getOrderDetailById(orderId);

        if (allDone && !"Cancelled".equalsIgnoreCase(order.getStatusName()) && !"Delivered".equalsIgnoreCase(order.getStatusName())) {
            orderDAO.updateOrderStatusAfterMakingBouquet(orderId);

            Integer availableShipperId = orderDAO.getAvailableShipperId();
            if (availableShipperId == null) {
                System.out.println("⚠️ Tất cả shipper đã đủ tải. Gán shipper ít đơn nhất.");
                availableShipperId = orderDAO.getLeastBusyShipperId(); // Viết hàm này riêng
            }
            if (availableShipperId != null) {
                orderDAO.assignShipper(orderId, availableShipperId);
                System.out.println("Assigned shipper ID " + availableShipperId + " to order ID " + orderId);
            } else {
                System.out.println("No available shipper found for order ID " + orderId);
            }
        }

        List<BouquetImage> images = bdao.getAllBouquetImage();
        if (order == null) {
            request.setAttribute("errorMessage", "Order not found with ID: " + orderId);
            System.err.println("OrderDetailServlet: Order not found for ID: " + orderId);
            request.getRequestDispatcher("/DashMin/error.jsp").forward(request, response);
            return;
        }

        // Retrieve success message if any from previous redirect
        String successMessage = request.getParameter("successMessage");
        if (successMessage != null && !successMessage.isEmpty()) {
            request.setAttribute("successMessage", successMessage);
        }

        if ("edit".equals(action)) {
            if (order.getStatusName().equalsIgnoreCase("Pending")) {

            }
            List<User> shippers = orderDAO.getAllShippers();
            List<OrderStatus> statuses = orderDAO.getAllOrderStatuses();

            request.setAttribute("images", images);
            request.setAttribute("order", order);
            request.setAttribute("orderItems", orderItems);
            request.setAttribute("shippers", shippers);
            request.setAttribute("statuses", statuses);
            request.getRequestDispatcher("/DashMin/orderEdit.jsp").forward(request, response);
        } else {
            request.setAttribute("images", images);
            request.setAttribute("order", order);
            request.setAttribute("orderItems", orderItems);
            request.getRequestDispatcher("/DashMin/orderDetail.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method. Processes requests to update
     * order information from the editing form.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String orderIdParam = request.getParameter("orderId");
        String totalImportParam = request.getParameter("totalImport");
        String totalSellParam = request.getParameter("totalSell");
        String statusIdParam = request.getParameter("statusId");
        String shipperIdParam = request.getParameter("shipperId");

        int orderId = -1;
        int statusId = -1;
        Integer shipperId = null;
        String errorMessage = null;

        try {
            if (orderIdParam != null && !orderIdParam.isEmpty()) {
                orderId = Integer.parseInt(orderIdParam);
            } else {
                errorMessage = "Missing Order ID.";
            }

            if (totalImportParam == null || totalImportParam.isEmpty()) {
                errorMessage = "Missing total import.";
            }

            if (totalSellParam == null || totalSellParam.isEmpty()) {
                errorMessage = "Missing total sell.";
            }

            if (statusIdParam != null && !statusIdParam.isEmpty()) {
                statusId = Integer.parseInt(statusIdParam);
            } else {
                errorMessage = "Missing order status.";
            }

            if (shipperIdParam != null && !shipperIdParam.isEmpty() && !shipperIdParam.equals("0")) {
                shipperId = Integer.parseInt(shipperIdParam);
            }

        } catch (NumberFormatException e) {
            errorMessage = "Invalid number format in update data.";
            System.err.println("OrderDetailServlet (POST): Number parsing error: " + e.getMessage());
        }

        // Step 2: If there's an error during parsing or missing parameters, re-display the form
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            System.err.println("OrderDetailServlet (POST): Error during initial parameter parsing/validation: " + errorMessage);

            OrderDAO orderDAO = new OrderDAO();
            if (orderId != -1) {
                request.setAttribute("order", orderDAO.getOrderDetailById(orderId));
                request.setAttribute("orderItems", orderDAO.getOrderItemsByOrderId(orderId));
                request.setAttribute("shippers", orderDAO.getAllShippers());
                request.setAttribute("statuses", orderDAO.getAllOrderStatuses());
                request.getRequestDispatcher("/DashMin/orderEdit.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/DashMin/error.jsp").forward(request, response);
            }
            return;
        }

        // Step 3: If no errors, proceed with order update
        OrderDAO orderDAO = new OrderDAO();
        boolean updated = orderDAO.updateOrder(orderId, totalImportParam, totalSellParam, statusId, shipperId);

        if (updated) {
            System.out.println("OrderDetailServlet (POST): Order ID " + orderId + " updated successfully.");
            String encodedSuccessMessage = URLEncoder.encode("Order updated successfully!", StandardCharsets.UTF_8.toString());
            response.sendRedirect(request.getContextPath() + "/orderDetail?orderId=" + orderId + "&successMessage=" + encodedSuccessMessage);
        } else {
            System.err.println("OrderDetailServlet (POST): Update failed for order ID " + orderId + ".");
            request.setAttribute("errorMessage", "Order update failed. Please try again.");
            Order order = orderDAO.getOrderDetailById(orderId);
            List<OrderDetail> orderItems = orderDAO.getOrderItemsByOrderId(orderId);
            List<User> shippers = orderDAO.getAllShippers();
            List<OrderStatus> statuses = orderDAO.getAllOrderStatuses();

            request.setAttribute("order", order);
            request.setAttribute("orderItems", orderItems);
            request.setAttribute("shippers", shippers);
            request.setAttribute("statuses", statuses);
            request.getRequestDispatcher("/DashMin/orderEdit.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for displaying and editing order details";
    }
}
