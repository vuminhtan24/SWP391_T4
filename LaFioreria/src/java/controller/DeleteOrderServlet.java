/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OrderDAO;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet for handling order deletion requests.
 * @author VU MINH TAN
 */
@WebServlet(name = "DeleteOrderServlet", urlPatterns = {"/deleteOrder"})
public class DeleteOrderServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Typically processes deletion requests from a link.
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
        String message = ""; // Message for the user after deletion attempt

        if (orderIdParam == null || orderIdParam.isEmpty()) {
            message = "Missing order ID for deletion.";
            System.err.println("DeleteOrderServlet: Missing orderIdParam for deletion.");
        } else {
            try {
                int orderId = Integer.parseInt(orderIdParam);
                OrderDAO orderDAO = new OrderDAO();
                boolean deleted = orderDAO.deleteOrder(orderId);

                if (deleted) {
                    message = "Order ID " + orderId + " deleted successfully!";
                    System.out.println("DeleteOrderServlet: " + message);
                } else {
                    message = "Failed to delete order ID " + orderId + ". It might not exist or a database error occurred.";
                    System.err.println("DeleteOrderServlet: " + message);
                }
            } catch (NumberFormatException e) {
                message = "Invalid order ID format for deletion.";
                System.err.println("DeleteOrderServlet: Invalid order ID format: " + orderIdParam + ". " + e.getMessage());
            } catch (Exception e) {
                message = "An unexpected error occurred during deletion.";
                System.err.println("DeleteOrderServlet: Unexpected error during deletion: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        // Redirect back to order management page with a message
        // Encode the message to ensure it's displayed correctly in the URL
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
        response.sendRedirect(request.getContextPath() + "/orderManagement?message=" + encodedMessage);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * This servlet will primarily use GET for simplicity of deletion links,
     * but you could implement POST for more secure deletion forms.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // For simplicity, we'll direct POST requests to GET logic for now.
        // In a production environment, DELETE operations should typically be POST requests
        // with appropriate CSRF protection.
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for handling order deletion operations.";
    }
}
