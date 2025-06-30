/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Order;
import model.OrderStatus;
import model.User;


/**
 *
 * @author ADMIN
 */
@WebServlet(name = "SaleManagerDashboardController", urlPatterns = {"/saleManagerDashboard"})
public class SaleManagerDashboardController extends HttpServlet {
    private static final int DEFAULT_PAGE_SIZE = 10; // Thay đổi giá trị này thành 8
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SaleManagerDashboardController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaleManagerDashboardController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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

        // Get search keyword
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }

        // Get status filter parameter
        Integer statusId = null;
        String statusIdParam = request.getParameter("statusId");
        if (statusIdParam != null && !statusIdParam.isEmpty()) {
            try {
                statusId = Integer.parseInt(statusIdParam);
            } catch (NumberFormatException e) {
                System.err.println("Giá trị statusId không hợp lệ: " + statusIdParam);
                // Continue with statusId = null if invalid
            }
        }

        // Get pagination parameters
        int pageIndex = 1; // Default to page 1
        String pageIndexParam = request.getParameter("page");
        if (pageIndexParam != null && !pageIndexParam.isEmpty()) {
            try {
                pageIndex = Integer.parseInt(pageIndexParam);
            } catch (NumberFormatException e) {
                System.err.println("Giá trị pageIndex không hợp lệ: " + pageIndexParam);
                pageIndex = 1; // Reset to 1 if invalid
            }
        }
        
        // FIX: Ensure pageSize is correctly read from DEFAULT_PAGE_SIZE or request
        // If you had a parameter for page size (e.g., from a dropdown), you'd read it here.
        // For now, we're using the fixed DEFAULT_PAGE_SIZE.
        int pageSize = DEFAULT_PAGE_SIZE; 
        // Debugging output for pageSize
        System.out.println("DEBUG: pageSize (from servlet): " + pageSize);

        // Get sort parameters
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        if (sortField == null || sortField.isEmpty()) {
            sortField = "orderDate"; // Default sort by order date
        }
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "desc"; // Default sort descending (newest first)
        }

        // Retrieve order list based on criteria
        List<Order> orders = orderDAO.searchOrders(keyword, statusId, pageIndex, pageSize, sortField, sortOrder);
        // Debugging output for actual number of orders returned
        System.out.println("DEBUG: Number of orders returned by DAO: " + orders.size());
        
        // Get total number of records (after search/filter) from DAO
        int totalRecords = orderDAO.getNoOfRecords();
        // Debugging output for totalRecords
        System.out.println("DEBUG: Total Records (from DAO): " + totalRecords);
        
        // Calculate total pages
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        // Debugging output for totalPages
        System.out.println("DEBUG: Total Pages calculated: " + totalPages);

        // Get all order statuses for filter dropdown
        List<OrderStatus> statuses = orderDAO.getAllOrderStatuses();

        // Set attributes for JSP
        request.setAttribute("orders", orders);
        request.setAttribute("statuses", statuses);
        request.setAttribute("currentKeyword", keyword);
        request.setAttribute("currentStatusId", statusId);
        request.setAttribute("currentPage", pageIndex);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize); // Pass pageSize to JSP
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);

        // Forward request to JSP for display
        request.getRequestDispatcher("/DashMin/saleManagerDashboard.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
