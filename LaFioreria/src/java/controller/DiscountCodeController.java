/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DiscountCodeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import model.DiscountCode;
import java.sql.Timestamp;
import java.util.Date; // Import java.util.Date for current time comparison

/**
 *
 * @author VU MINH TAN
 */
@WebServlet(name = "DiscountCodeController", urlPatterns = {"/discount"})
public class DiscountCodeController extends HttpServlet {

    DiscountCodeDAO dao = new DiscountCodeDAO();

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
            out.println("<title>Servlet DiscountCodeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DiscountCodeController at " + request.getContextPath() + "</h1>");
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

        final int PAGE_SIZE = 4; // số dòng mỗi trang

        // Lấy tham số tìm kiếm và lọc từ request
        String searchTerm = request.getParameter("search");
        String filterStatus = request.getParameter("status");

        // Lấy trang hiện tại
        int page = 1;
        String pageRaw = request.getParameter("page");
        try {
            if (pageRaw != null) {
                page = Integer.parseInt(pageRaw);
            }
        } catch (NumberFormatException e) {
            // Log the error or handle it appropriately, e.g., set page to 1
            System.err.println("Invalid page number format: " + e.getMessage());
            page = 1;
        }

        // Lấy tổng số mục đã lọc và tìm kiếm
        // Cần một phương thức mới trong DAO để đếm số lượng mã giảm giá theo tìm kiếm và lọc
        int totalItems = dao.countFilteredDiscountCodes(searchTerm, filterStatus);
        int totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);
        int offset = (page - 1) * PAGE_SIZE;

        // Lấy danh sách mã giảm giá đã lọc và phân trang
        // Cần một phương thức mới trong DAO để lấy danh sách mã giảm giá theo tìm kiếm, lọc và phân trang
        List<DiscountCode> list = dao.getFilteredDiscountCodesPaged(searchTerm, filterStatus, offset, PAGE_SIZE);

        // Đặt các thuộc tính vào request để JSP có thể truy cập
        request.setAttribute("discountCodes", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("search", searchTerm); // Giữ lại giá trị tìm kiếm
        request.setAttribute("status", filterStatus); // Giữ lại giá trị lọc trạng thái

        request.getRequestDispatcher("/DashMin/discount_list.jsp").forward(request, response);
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

        String action = request.getParameter("action");
        String redirectUrl = "discount"; // Default redirect URL

        // Preserve search and status parameters for redirection
        String searchTerm = request.getParameter("search");
        String filterStatus = request.getParameter("status");
        String pageRaw = request.getParameter("page");

        // Build query string for redirection
        StringBuilder queryString = new StringBuilder();
        if (searchTerm != null && !searchTerm.isEmpty()) {
            queryString.append("search=").append(searchTerm).append("&");
        }
        if (filterStatus != null && !filterStatus.isEmpty()) {
            queryString.append("status=").append(filterStatus).append("&");
        }
        if (pageRaw != null && !pageRaw.isEmpty()) {
            queryString.append("page=").append(pageRaw).append("&");
        }
        
        // Remove trailing '&' if any
        if (queryString.length() > 0 && queryString.charAt(queryString.length() - 1) == '&') {
            queryString.setLength(queryString.length() - 1);
        }

        if (queryString.length() > 0) {
            redirectUrl += "?" + queryString.toString();
        }


        if ("add".equals(action)) {
            String code = request.getParameter("code") != null ? request.getParameter("code").trim() : "";
            String description = request.getParameter("description") != null ? request.getParameter("description").trim() : "";
            String type = request.getParameter("type") != null ? request.getParameter("type").trim() : "";
            String valueRaw = request.getParameter("value");
            String maxDiscountRaw = request.getParameter("maxDiscount");
            String minOrderRaw = request.getParameter("minOrder");
            String startRaw = request.getParameter("start");
            String endRaw = request.getParameter("end");
            String usageLimitRaw = request.getParameter("usageLimit");

            // Gán lại dữ liệu để hiển thị nếu lỗi
            request.setAttribute("code", code);
            request.setAttribute("description", description);
            request.setAttribute("type", type);
            request.setAttribute("value", valueRaw);
            request.setAttribute("maxDiscount", maxDiscountRaw);
            request.setAttribute("minOrder", minOrderRaw);
            request.setAttribute("start", startRaw);
            request.setAttribute("end", endRaw);
            request.setAttribute("usageLimit", usageLimitRaw);

            // Basic validation
            // Note: maxDiscount, minOrder, usageLimit can be null/empty if not required.
            // Adjust validation based on your business rules.
            if (code.isEmpty() || description.isEmpty() || type.isEmpty() || valueRaw == null || startRaw == null || endRaw == null) {
                request.setAttribute("error", "Vui lòng điền đầy đủ thông tin bắt buộc (Code, Description, Type, Value, Start Date, End Date).");
                // Re-fetch and forward to doGet logic to display existing filters/search
                doGet(request, response); // Call doGet to re-render with error and current filters
                return;
            }

            try {
                DiscountCode dc = new DiscountCode();
                dc.setCode(code);
                dc.setDescription(description);
                dc.setType(type);
                dc.setValue(new BigDecimal(valueRaw.trim()));
                
                // Handle optional fields
                dc.setMaxDiscount(maxDiscountRaw != null && !maxDiscountRaw.trim().isEmpty() ? new BigDecimal(maxDiscountRaw.trim()) : null);
                dc.setMinOrderAmount(minOrderRaw != null && !minOrderRaw.trim().isEmpty() ? new BigDecimal(minOrderRaw.trim()) : null);
                dc.setUsageLimit(usageLimitRaw != null && !usageLimitRaw.trim().isEmpty() ? Integer.parseInt(usageLimitRaw.trim()) : null);

                dc.setStartDate(Timestamp.valueOf(startRaw.trim().replace("T", " ") + ":00"));
                dc.setEndDate(Timestamp.valueOf(endRaw.trim().replace("T", " ") + ":00"));
                
                // Assuming new discount codes are active by default
                dc.setActive(true); 

                dao.insertDiscountCode(dc);
                response.sendRedirect(redirectUrl); // Redirect with existing filters
                return;

            } catch (Exception e) {
                System.err.println("Error adding discount code: " + e.getMessage());
                request.setAttribute("error", "Dữ liệu không hợp lệ hoặc sai định dạng: " + e.getMessage());
                // Re-fetch and forward to doGet logic to display existing filters/search
                doGet(request, response); // Call doGet to re-render with error and current filters
                return;
            }

        } else if ("deactivate".equals(action)) {
            String codeToDeactivate = request.getParameter("code");
            if (codeToDeactivate != null && !codeToDeactivate.isEmpty()) {
                dao.deactivateDiscountCode(codeToDeactivate);
                response.sendRedirect(redirectUrl); // Redirect with existing filters
            } else {
                request.setAttribute("error", "Mã giảm giá để hủy kích hoạt không hợp lệ.");
                doGet(request, response);
            }
        } else if ("delete".equals(action)) {
            String codeToDelete = request.getParameter("code");
            if (codeToDelete != null && !codeToDelete.isEmpty()) {
                dao.deleteDiscountCode(codeToDelete);
                response.sendRedirect(redirectUrl); // Redirect with existing filters
            } else {
                request.setAttribute("error", "Mã giảm giá để xóa không hợp lệ.");
                doGet(request, response);
            }
        } else {
            // If action is unknown or not handled, redirect to discount page
            response.sendRedirect(redirectUrl);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Controller for Discount Code Management";
    }// </editor-fold>

}
