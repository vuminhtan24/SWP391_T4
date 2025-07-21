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
        List<DiscountCode> list = dao.getAllDiscountCodes();
        request.setAttribute("discountCodes", list);
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
            DiscountCode dc = new DiscountCode();
            dc.setCode(request.getParameter("code").trim());
            dc.setDescription(request.getParameter("description").trim());
            dc.setType(request.getParameter("type"));
            dc.setValue(new BigDecimal(request.getParameter("value")));
            dc.setMaxDiscount(new BigDecimal(request.getParameter("maxDiscount")));
            dc.setMinOrderAmount(new BigDecimal(request.getParameter("minOrder")));
            dc.setStartDate(Timestamp.valueOf(request.getParameter("start")));
            dc.setEndDate(Timestamp.valueOf(request.getParameter("end")));
            dc.setUsageLimit(Integer.parseInt(request.getParameter("usageLimit")));

            dao.insertDiscountCode(dc);
            response.sendRedirect("discount"); // reload lại
        } else if ("deactivate".equals(action)) {
            dao.deactivateDiscountCode(request.getParameter("code"));
            response.sendRedirect("discount");
        }
        // thêm xử lý update tương tự
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
