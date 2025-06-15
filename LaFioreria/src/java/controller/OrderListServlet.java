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
 * @author VU MINH TAN
 */
@WebServlet(name = "OrderListServlet", urlPatterns = {"/orderManagement"})
public class OrderListServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet OrderListServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderListServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrderDAO orderDAO = new OrderDAO();

        // Lấy tham số tìm kiếm (keyword)
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = ""; // Đặt mặc định là rỗng nếu không có
        }

        // Lấy tham số lọc theo trạng thái (statusId)
        Integer statusId = null;
        String statusIdParam = request.getParameter("statusId");
        if (statusIdParam != null && !statusIdParam.isEmpty()) {
            try {
                statusId = Integer.parseInt(statusIdParam);
            } catch (NumberFormatException e) {
                System.err.println("Giá trị statusId không hợp lệ: " + statusIdParam);
                // Có thể đặt thông báo lỗi vào request nếu cần
            }
        }

        // Lấy danh sách đơn hàng dựa trên tìm kiếm và lọc
        List<Order> orders = orderDAO.searchOrders(keyword, statusId);
        // Lấy danh sách tất cả các trạng thái để hiển thị trong dropdown lọc
        List<OrderStatus> statuses = orderDAO.getAllOrderStatuses();

        // Đặt các attribute vào request để JSP có thể truy cập
        request.setAttribute("orders", orders);
        request.setAttribute("statuses", statuses);
        request.setAttribute("currentKeyword", keyword); // Giữ lại từ khóa tìm kiếm trên form
        request.setAttribute("currentStatusId", statusId); // Giữ lại trạng thái đã chọn trên form

        // Chuyển tiếp yêu cầu đến trang JSP để hiển thị danh sách
        request.getRequestDispatcher("/DashMin/orderManagement.jsp").forward(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); 
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
