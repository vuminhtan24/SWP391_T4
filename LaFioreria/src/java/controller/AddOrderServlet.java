/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OrderDAO;
import model.Order;
import model.User;
import model.OrderStatus;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate; 
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet for handling adding new orders (main order record).
 * @author VU MINH TAN
 */
@WebServlet(name = "AddOrderServlet", urlPatterns = {"/addOrder"})
public class AddOrderServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Displays the form for adding a new order.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        OrderDAO orderDAO = new OrderDAO();
        List<User> customers = orderDAO.getAllCustomers();
        List<OrderStatus> statuses = orderDAO.getAllOrderStatuses();
        List<User> shippers = orderDAO.getAllShippers(); 

        request.setAttribute("customers", customers);
        request.setAttribute("statuses", statuses);
        request.setAttribute("shippers", shippers);
        request.setAttribute("currentDate", LocalDate.now().toString()); 

        request.getRequestDispatcher("/DashMin/orderAdd.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * Processes the submission of the new order form and redirects to add items.
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

        String orderDate = request.getParameter("orderDate");
        String customerIdParam = request.getParameter("customerId");
        // totalAmount will be "0.00" initially, updated later when items are added
        String totalAmountParam = "0.00"; // FIX: Set totalAmountParam directly here, don't get from request
        String statusIdParam = request.getParameter("statusId");
        String shipperIdParam = request.getParameter("shipperId");

        String errorMessage = null;
        int customerId = -1;
        int statusId = -1;
        Integer shipperId = null;

        // Input validation
        if (orderDate == null || orderDate.isEmpty()) {
            errorMessage = "Order Date is required.";
        } else if (customerIdParam == null || customerIdParam.isEmpty() || customerIdParam.equals("0")) {
            errorMessage = "Customer is required.";
        } else if (statusIdParam == null || statusIdParam.isEmpty() || statusIdParam.equals("0")) {
            errorMessage = "Status is required.";
        }
        // FIX: Removed validation for totalAmountParam as it's now set internally to "0.00"

        try {
            if (errorMessage == null) { 
                customerId = Integer.parseInt(customerIdParam);
                statusId = Integer.parseInt(statusIdParam);
                if (shipperIdParam != null && !shipperIdParam.isEmpty() && !shipperIdParam.equals("0")) {
                    shipperId = Integer.parseInt(shipperIdParam);
                }
                // No need to parse Double.parseDouble(totalAmountParam) here as it's now fixed "0.00"
            }
        } catch (NumberFormatException e) {
            errorMessage = "Invalid number format for Customer ID or Status ID."; // Adjusted message
            System.err.println("AddOrderServlet (POST): Number parsing error: " + e.getMessage());
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            OrderDAO orderDAO = new OrderDAO();
            request.setAttribute("customers", orderDAO.getAllCustomers());
            request.setAttribute("statuses", orderDAO.getAllOrderStatuses());
            request.setAttribute("shippers", orderDAO.getAllShippers());
            request.setAttribute("currentDate", LocalDate.now().toString()); 
            
            request.setAttribute("selectedCustomerId", customerIdParam);
            request.setAttribute("selectedStatusId", statusIdParam);
            request.setAttribute("selectedShipperId", shipperIdParam);
            // request.setAttribute("submittedTotalAmount", totalAmountParam); // No longer needed for re-population
            request.setAttribute("submittedOrderDate", orderDate);

            request.getRequestDispatcher("/DashMin/orderAdd.jsp").forward(request, response);
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        // Create order with initial total amount as "0.00"
        Order newOrder = new Order(0, orderDate, customerId, null, totalAmountParam, statusId, null, shipperId, null);

        int newOrderId = orderDAO.addOrder(newOrder);

        if (newOrderId != -1) {
            String successMessage = "Main order record added successfully with ID: " + newOrderId + ". Now, add products to this order.";
            String encodedSuccessMessage = URLEncoder.encode(successMessage, StandardCharsets.UTF_8.toString());
            // Redirect to a new servlet to add order items
            response.sendRedirect(request.getContextPath() + "/addOrderItems?orderId=" + newOrderId + "&successMessage=" + encodedSuccessMessage);
        } else {
            errorMessage = "Failed to add new order record. Please try again.";
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("customers", orderDAO.getAllCustomers());
            request.setAttribute("statuses", orderDAO.getAllOrderStatuses());
            request.setAttribute("shippers", orderDAO.getAllShippers());
            request.setAttribute("currentDate", LocalDate.now().toString()); 
            
            request.setAttribute("selectedCustomerId", customerIdParam);
            request.setAttribute("selectedStatusId", statusIdParam);
            request.setAttribute("selectedShipperId", shipperIdParam);
            // request.setAttribute("submittedTotalAmount", totalAmountParam); // No longer needed for re-population
            request.setAttribute("submittedOrderDate", orderDate);
            
            request.getRequestDispatcher("/DashMin/orderAdd.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding new orders (main record).";
    }
}
