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

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "WholeSaleQuotation", urlPatterns = {"/wholeSaleQuotation"})
public class WholeSaleQuotationController extends HttpServlet {

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
            out.println("<title>Servlet WholeSaleQuotation</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WholeSaleQuotation at " + request.getContextPath() + "</h1>");
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
        String userIdStr = request.getParameter("userId").trim();
        String requestDateStr = request.getParameter("requestDate").trim();
        String status = request.getParameter("status").trim();
        String bouquetIdStr = request.getParameter("bouquetId");

        Integer userId = Integer.parseInt(userIdStr);
        LocalDate requestDate = LocalDate.parse(requestDateStr);
        Integer bouquetId = Integer.parseInt(bouquetIdStr);

        WholeSaleDAO wsDao = new WholeSaleDAO();
        BouquetDAO bDao = new BouquetDAO();
        FlowerBatchDAO batchdao = new FlowerBatchDAO();
        FlowerTypeDAO flowerdao = new FlowerTypeDAO();
        CategoryDAO cdao = new CategoryDAO();

        WholeSale ws = wsDao.getWholeSaleDetail(userId, requestDate, status, bouquetId);
        Bouquet b = bDao.getBouquetByID(bouquetId);
        List<BouquetImage> listImage = bDao.getBouquetImage(bouquetId);
        List<BouquetRaw> bqRaws = bDao.getFlowerBatchByBouquetID(bouquetId);
        List<FlowerBatch> allBatchs = batchdao.getAllFlowerBatches();
        List<FlowerType> allFlowers = flowerdao.getAllFlowerTypes();
        String cateName = cdao.getCategoryNameByBouquet(bouquetId);

        request.setAttribute("userId", userId);
        request.setAttribute("requestDate", requestDate);
        request.setAttribute("status", status);
        request.setAttribute("bouquetId", bouquetId);
        request.setAttribute("ws", ws);
        request.setAttribute("bouquet", b);
        request.setAttribute("listImage", listImage);
        request.setAttribute("flowerInBQ", bqRaws);
        request.setAttribute("allBatchs", allBatchs);
        request.setAttribute("allFlowers", allFlowers);
        request.setAttribute("cateName", cateName);

        request.getRequestDispatcher("./DashMin/wholeSaleQuotation.jsp").forward(request, response);
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
        String userIdStr = request.getParameter("userId").trim();
        String requestDateStr = request.getParameter("requestDate").trim();
        String status = request.getParameter("status").trim();
        String bouquetIdStr = request.getParameter("bouquetId");
        String wholesalePriceStr = request.getParameter("wholesalePrice");
        String totalWholesaleStr = request.getParameter("totalWholesale");
        String wholesaleExpenseStr = request.getParameter("wholesaleExpense");

        Integer userId = Integer.parseInt(userIdStr);
        LocalDate requestDate = LocalDate.parse(requestDateStr);
        Integer bouquetId = Integer.parseInt(bouquetIdStr);
        Integer wholesalePrice = (int) Double.parseDouble(wholesalePriceStr);
        Integer totalWholesale = (int) Double.parseDouble(totalWholesaleStr);
        Integer wholesaleExpense = (int) Double.parseDouble(wholesaleExpenseStr);

        LocalDate quotedDate = LocalDate.now();

        WholeSaleDAO wsDao = new WholeSaleDAO();

        wsDao.AssignQuotedPrice(totalWholesale, quotedDate, wholesalePrice, userId, requestDate, status, bouquetId);

        request.setAttribute("alert", "You have successfully entered the wholesale price!!!");
        request.setAttribute("assignExpenseList", request.getParameterValues("assignExpense[]"));
        request.setAttribute("wholesaleExpense", wholesaleExpense);     // tính ở controller nếu cần
        request.setAttribute("wholesalePrice", wholesalePrice);
        request.setAttribute("totalWholesale", totalWholesale);
        doGet(request, response);
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
