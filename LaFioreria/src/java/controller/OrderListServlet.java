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
   private static final int DEFAULT_PAGE_SIZE = 10;
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
            keyword = "";
        }

        // Lấy tham số lọc trạng thái
        Integer statusId = null;
        String statusIdParam = request.getParameter("statusId");
        if (statusIdParam != null && !statusIdParam.isEmpty()) {
            try {
                statusId = Integer.parseInt(statusIdParam);
            } catch (NumberFormatException e) {
                System.err.println("Giá trị statusId không hợp lệ: " + statusIdParam);
            }
        }

        // Lấy tham số phân trang
        int pageIndex = 1; // Mặc định là trang 1
        String pageIndexParam = request.getParameter("page");
        if (pageIndexParam != null && !pageIndexParam.isEmpty()) {
            try {
                pageIndex = Integer.parseInt(pageIndexParam);
            } catch (NumberFormatException e) {
                System.err.println("Giá trị pageIndex không hợp lệ: " + pageIndexParam);
                // Đặt lại pageIndex về 1 nếu không hợp lệ để tránh lỗi
                pageIndex = 1;
            }
        }
        int pageSize = DEFAULT_PAGE_SIZE; // Hoặc lấy từ request nếu có
        // Ví dụ: String pageSizeParam = request.getParameter("size");
        // if (pageSizeParam != null && !pageSizeParam.isEmpty()) { try { pageSize = Integer.parseInt(pageSizeParam); } catch (NumberFormatException e) { } }


        // Lấy tham số sắp xếp
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        if (sortField == null || sortField.isEmpty()) {
            sortField = "orderDate"; // Mặc định sắp xếp theo ngày đặt hàng
        }
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "desc"; // Mặc định giảm dần (mới nhất lên đầu)
        }

        // Lấy danh sách đơn hàng dựa trên tìm kiếm, lọc, phân trang và sắp xếp
        List<Order> orders = orderDAO.searchOrders(keyword, statusId, pageIndex, pageSize, sortField, sortOrder);
        
        // Lấy tổng số bản ghi (sau khi tìm kiếm/lọc) từ DAO
        int totalRecords = orderDAO.getNoOfRecords();
        
        // Tính toán tổng số trang
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // Lấy danh sách tất cả các trạng thái để hiển thị trong dropdown lọc
        List<OrderStatus> statuses = orderDAO.getAllOrderStatuses();

        // Đặt các attribute vào request để JSP có thể truy cập
        request.setAttribute("orders", orders);
        request.setAttribute("statuses", statuses);
        request.setAttribute("currentKeyword", keyword);
        request.setAttribute("currentStatusId", statusId);
        request.setAttribute("currentPage", pageIndex);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize); // Để JSP biết pageSize hiện tại
        request.setAttribute("sortField", sortField); // Giữ lại trường sắp xếp hiện tại
        request.setAttribute("sortOrder", sortOrder); // Giữ lại thứ tự sắp xếp hiện tại

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
