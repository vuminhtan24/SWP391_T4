/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BouquetDAO;
import dal.CategoryDAO;
import dal.FlowerBatchDAO;
import dal.FlowerTypeDAO;
import dal.UserDAO;
import dal.WholeSaleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Bouquet;
import model.BouquetImage;
import model.BouquetRaw;
import model.FlowerBatch;
import model.FlowerType;
import model.User;
import model.WholeSale;
import model.WholeSaleFlower;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CustomerQuotationListController", urlPatterns = {"/listQuotation"})
public class CustomerQuotationListController extends HttpServlet {

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
            out.println("<title>Servlet CustomerQuotationListController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerQuotationListController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        User acc = (User) request.getSession().getAttribute("currentAcc");
        if (acc == null) {
            response.sendRedirect(request.getContextPath() + "/ZeShopper/LoginServlet");
            return;
        }
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

        int userId = acc.getUserid();

        WholeSaleDAO wsDao = new WholeSaleDAO();
        BouquetDAO bDao = new BouquetDAO();

        List<BouquetImage> images = bDao.getAllBouquetImage();
        List<WholeSale> listWSRequest = wsDao.getUserWholeSaleSummary(userId, requestDate, status);
        List<Bouquet> listBouquet = bDao.getAll();

        request.setAttribute("listBouquet", listBouquet);
        request.setAttribute("images", images);
        request.setAttribute("wholesaleList", listWSRequest);
        request.getRequestDispatcher("./ZeShopper/listQuotation.jsp").forward(request, response);
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
