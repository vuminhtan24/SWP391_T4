/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OrderDAO;
import model.Order;
import model.OrderDetail;
import model.Bouquet;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet for handling adding items to an existing order.
 * @author VU MINH TAN
 */
@WebServlet(name = "AddOrderItemsServlet", urlPatterns = {"/addOrderItems"})
public class AddOrderItemsServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Displays the form to add items to a specific order.
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

        String orderIdParam = request.getParameter("orderId");
        if (orderIdParam == null || orderIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/orderManagement?errorMessage=" + URLEncoder.encode("Order ID is missing for adding items.", StandardCharsets.UTF_8.toString()));
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/orderManagement?errorMessage=" + URLEncoder.encode("Invalid Order ID format for adding items.", StandardCharsets.UTF_8.toString()));
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.getOrderDetailById(orderId);
        if (order == null) {
            response.sendRedirect(request.getContextPath() + "/orderManagement?errorMessage=" + URLEncoder.encode("Order not found with ID: " + orderId + " for adding items.", StandardCharsets.UTF_8.toString()));
            return;
        }

        List<OrderDetail> currentOrderItems = orderDAO.getOrderItemsByOrderId(orderId);
        List<Bouquet> allBouquets = orderDAO.getAllBouquets();

        request.setAttribute("order", order);
        request.setAttribute("currentOrderItems", currentOrderItems);
        request.setAttribute("allBouquets", allBouquets);

        // Pass success/error messages from redirect (if any)
        String successMessage = request.getParameter("successMessage");
        if (successMessage != null && !successMessage.isEmpty()) {
            request.setAttribute("successMessage", successMessage);
        }
        String errorMessage = request.getParameter("errorMessage");
        if (errorMessage != null && !errorMessage.isEmpty()) {
            request.setAttribute("errorMessage", errorMessage);
        }

        request.getRequestDispatcher("/DashMin/orderAddItems.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * Processes the submission of the form to add items to an order.
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
        String[] bouquetIds = request.getParameterValues("bouquetId"); // Array for multiple items
        String[] quantities = request.getParameterValues("quantity"); // Array for multiple quantities

        String errorMessage = null;
        int orderId = -1;

        if (orderIdParam == null || orderIdParam.isEmpty()) {
            errorMessage = "Order ID is missing.";
        } else {
            try {
                orderId = Integer.parseInt(orderIdParam);
            } catch (NumberFormatException e) {
                errorMessage = "Invalid Order ID format.";
            }
        }

        if (errorMessage == null && (bouquetIds == null || bouquetIds.length == 0)) {
            errorMessage = "No products selected to add.";
        }
        
        OrderDAO orderDAO = new OrderDAO();
        
        // If there's an error, set attributes and forward back
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("order", orderDAO.getOrderDetailById(orderId));
            request.setAttribute("currentOrderItems", orderDAO.getOrderItemsByOrderId(orderId));
            request.setAttribute("allBouquets", orderDAO.getAllBouquets());
            request.getRequestDispatcher("/DashMin/orderAddItems.jsp").forward(request, response);
            return;
        }

        boolean allItemsAdded = true;
        double currentTotalAmount = 0.0; // To calculate the new total amount for the order

        // Get current total amount first, if it exists
        Order existingOrder = orderDAO.getOrderDetailById(orderId);
        if (existingOrder != null && existingOrder.getTotalAmount() != null && !existingOrder.getTotalAmount().isEmpty()) {
            try {
                currentTotalAmount = Double.parseDouble(existingOrder.getTotalAmount());
            } catch (NumberFormatException e) {
                System.err.println("AddOrderItemsServlet: Existing total amount for order " + orderId + " is not a valid number: " + existingOrder.getTotalAmount());
                currentTotalAmount = 0.0; // Reset if invalid
            }
        }
        
        // Get prices for all bouquets once to avoid repeated DB calls in loop
        List<Bouquet> allBouquetsData = orderDAO.getAllBouquets();

        for (int i = 0; i < bouquetIds.length; i++) {
            try {
                int bouquetId = Integer.parseInt(bouquetIds[i]);
                int quantity = Integer.parseInt(quantities[i]);

                if (quantity <= 0) {
                    errorMessage = "Quantity must be a positive number for all items.";
                    allItemsAdded = false;
                    break;
                }

                // Find the bouquet to get its current price
                String unitPrice = null;
                for (Bouquet b : allBouquetsData) {
                    if (b.getBouquetId() == bouquetId) {
                        unitPrice = String.valueOf(b.getPrice()); // FIX: Use getPrice() and convert to String
                        break;
                    }
                }

                if (unitPrice == null) {
                    errorMessage = "Could not find price for selected bouquet ID: " + bouquetId;
                    allItemsAdded = false;
                    break;
                }
                
                // Add the item to order_item table
                OrderDetail newItem = new OrderDetail(0, orderId, bouquetId, null, null, quantity, unitPrice);
                if (!orderDAO.addOrderItem(newItem)) {
                    errorMessage = "Failed to add item for bouquet ID: " + bouquetId;
                    allItemsAdded = false;
                    break;
                }
                
                // Accumulate total amount
                currentTotalAmount += (Double.parseDouble(unitPrice) * quantity);

            } catch (NumberFormatException e) {
                errorMessage = "Invalid number format for bouquet ID or quantity.";
                allItemsAdded = false;
                break;
            } catch (Exception e) {
                errorMessage = "An unexpected error occurred while adding an item.";
                System.err.println("AddOrderItemsServlet: Error adding item: " + e.getMessage());
                e.printStackTrace();
                allItemsAdded = false;
                break;
            }
        }
        
        // Update the order's total amount if all items were added successfully
        if (allItemsAdded && errorMessage == null) {
            String newTotalAmountFormatted = String.format("%.2f", currentTotalAmount); // Format to 2 decimal places
            if (!orderDAO.updateTotalAmount(orderId, newTotalAmountFormatted)) {
                errorMessage = "Items added, but failed to update order total amount.";
                // Even if total amount update fails, items are added, so we still redirect to details page.
                // The error message will indicate the issue.
            }
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("order", orderDAO.getOrderDetailById(orderId));
            request.setAttribute("currentOrderItems", orderDAO.getOrderItemsByOrderId(orderId));
            request.setAttribute("allBouquets", orderDAO.getAllBouquets());
            request.getRequestDispatcher("/DashMin/orderAddItems.jsp").forward(request, response);
        } else {
            String successMessage = "Products added successfully to order ID " + orderId + "!";
            String encodedSuccessMessage = URLEncoder.encode(successMessage, StandardCharsets.UTF_8.toString());
            response.sendRedirect(request.getContextPath() + "/orderDetail?orderId=" + orderId + "&successMessage=" + encodedSuccessMessage);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding items to an existing order.";
    }
}
