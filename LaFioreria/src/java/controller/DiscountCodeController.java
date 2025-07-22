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

        // Lấy trang hiện tại
        int page = 1;
        String pageRaw = request.getParameter("page");
        try {
            if (pageRaw != null) {
                page = Integer.parseInt(pageRaw);
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        int totalItems = dao.countAllDiscountCodes();
        int totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);
        int offset = (page - 1) * PAGE_SIZE;

        List<DiscountCode> list = dao.getDiscountCodesPaged(offset, PAGE_SIZE);

        request.setAttribute("discountCodes", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

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
            if (code.isEmpty() || description.isEmpty() || type.isEmpty()
                    || valueRaw == null || maxDiscountRaw == null || minOrderRaw == null
                    || startRaw == null || endRaw == null || usageLimitRaw == null) {

                request.setAttribute("error", "Vui lòng điền đầy đủ thông tin hợp lệ.");
                List<DiscountCode> list = dao.getAllDiscountCodes();
                request.setAttribute("discountCodes", list);
                request.getRequestDispatcher("/DashMin/discount_list.jsp").forward(request, response);
                return;
            }

            try {
                DiscountCode dc = new DiscountCode();
                dc.setCode(code);
                dc.setDescription(description);
                dc.setType(type);
                dc.setValue(new BigDecimal(valueRaw.trim()));
                dc.setMaxDiscount(new BigDecimal(maxDiscountRaw.trim()));
                dc.setMinOrderAmount(new BigDecimal(minOrderRaw.trim()));
                dc.setStartDate(Timestamp.valueOf(startRaw.trim().replace("T", " ") + ":00"));
                dc.setEndDate(Timestamp.valueOf(endRaw.trim().replace("T", " ") + ":00"));
                dc.setUsageLimit(Integer.parseInt(usageLimitRaw.trim()));

                dao.insertDiscountCode(dc);
                response.sendRedirect("discount");

            } catch (Exception e) {
                request.setAttribute("error", "Dữ liệu không hợp lệ hoặc sai định dạng.");
                List<DiscountCode> list = dao.getAllDiscountCodes();
                request.setAttribute("discountCodes", list);
                request.getRequestDispatcher("/DashMin/discount_list.jsp").forward(request, response);
            }

        } else if ("deactivate".equals(action)) {
            dao.deactivateDiscountCode(request.getParameter("code"));
            response.sendRedirect("discount");
        }
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
