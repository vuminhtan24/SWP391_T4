/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DiscountCodeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import model.DiscountCode;
import java.sql.Timestamp;

/**
 *
 * @author VU MINH TAN
 */
public class UpdateDiscountServlet extends HttpServlet {

    private DiscountCodeDAO discountCodeDAO = new DiscountCodeDAO();

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
            out.println("<title>Servlet UpdateDiscountServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateDiscountServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        request.setCharacterEncoding("UTF-8");

        String code = request.getParameter("code");
        String description = request.getParameter("description");
        String type = request.getParameter("type");
        String valueStr = request.getParameter("value");
        String maxDiscountStr = request.getParameter("maxDiscount");
        String minOrderAmountStr = request.getParameter("minOrderAmount");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String usageLimitStr = request.getParameter("usageLimit");
        String activeStr = request.getParameter("active");

        DiscountCode dc = new DiscountCode();

        dc.setCode(code);
        dc.setDescription(description);
        dc.setType(type);

        // Chuyển đổi kiểu dữ liệu
        dc.setValue(new BigDecimal(valueStr));
        dc.setMaxDiscount(new BigDecimal(maxDiscountStr));
        dc.setMinOrderAmount(new BigDecimal(minOrderAmountStr));

        try {
            dc.setStartDate(Timestamp.valueOf(startDateStr + " 00:00:00"));
            dc.setEndDate(Timestamp.valueOf(endDateStr + " 23:59:59"));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("error", "Sai định dạng ngày (yyyy-MM-dd)");
            request.getRequestDispatcher("/DashMin/discount_list.jsp").forward(request, response);
            return;
        }

        if (usageLimitStr == null || usageLimitStr.trim().isEmpty()) {
            dc.setUsageLimit(0);
        } else {
            dc.setUsageLimit(Integer.parseInt(usageLimitStr));
        }

        dc.setActive("on".equals(activeStr) || "true".equalsIgnoreCase(activeStr));

        // Cập nhật vào DB
        discountCodeDAO.updateDiscountCode(dc);

        // Redirect về trang quản lý
        response.sendRedirect("discount");
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
