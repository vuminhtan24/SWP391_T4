/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BouquetDAO;
import dal.WholeSaleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import model.Bouquet;
import model.BouquetImage;
import model.User;
import model.WholeSale;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "WholeSaleListController", urlPatterns = {"/listWholeSaleRequest"})
public class WholeSaleListController extends HttpServlet {

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
            out.println("<title>Servlet WholeSaleListController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WholeSaleListController at " + request.getContextPath() + "</h1>");
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
        WholeSaleDAO wsDao = new WholeSaleDAO();

        String requestDateStr = request.getParameter("requestDate");
        String status = request.getParameter("status");

        LocalDate requestDate = null;
        if (requestDateStr != null && !requestDateStr.trim().isEmpty()) {
            try {
                requestDate = LocalDate.parse(requestDateStr.trim());
            } catch (Exception e) {
                request.setAttribute("dateError", "Invalid date format.");
            }
        }

        // Trim status if not null
        if (status != null) {
            status = status.trim();
            if (status.isEmpty()) {
                status = null;
            }
        }

        // Gọi DAO có filter
        List<WholeSale> listWS = wsDao.getWholeSaleSummary(requestDate, status);

        // Gửi dữ liệu sang view
        request.setAttribute("listWS", listWS);
        request.setAttribute("requestDate", requestDateStr); // để giữ lại trên giao diện
        request.setAttribute("status", status);

        request.getRequestDispatcher("./DashMin/listWholeSale.jsp").forward(request, response);
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
