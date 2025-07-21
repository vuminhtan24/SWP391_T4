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
import model.WholeSaleFlower;

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
        String requestGroupId = request.getParameter("requestGroupId").trim();  // ✅ mới
        String status = request.getParameter("status").trim();
        String bouquetIdStr = request.getParameter("bouquetId");

        Integer userId = Integer.parseInt(userIdStr);
        LocalDate requestDate = LocalDate.parse(requestDateStr);
        Integer bouquetId = Integer.parseInt(bouquetIdStr);

        WholeSaleDAO wsDao = new WholeSaleDAO();
        BouquetDAO bDao = new BouquetDAO();
        FlowerBatchDAO batchDao = new FlowerBatchDAO();
        FlowerTypeDAO flowerDao = new FlowerTypeDAO();
        CategoryDAO cDao = new CategoryDAO();

        WholeSale ws = wsDao.getWholeSaleDetail(userId, requestDate, status, bouquetId, requestGroupId);  // ✅ thêm group id
        Bouquet b = bDao.getBouquetByID(bouquetId);
        List<BouquetImage> listImage = bDao.getBouquetImage(bouquetId);
        List<BouquetRaw> bqRaws = bDao.getFlowerBatchByBouquetID(bouquetId);
        List<FlowerBatch> allBatches = batchDao.getAllFlowerBatches();
        List<FlowerType> allFlowers = flowerDao.getAllFlowerTypes();
        String cateName = cDao.getCategoryNameByBouquet(bouquetId);
        List<WholeSaleFlower> listWsFlower = wsDao.listFlowerInRequest(requestGroupId);

        request.setAttribute("ListWsFlower", listWsFlower);
        request.setAttribute("userId", userId);
        request.setAttribute("requestDate", requestDate);
        request.setAttribute("requestGroupId", requestGroupId);  // ✅ mới
        request.setAttribute("status", status);
        request.setAttribute("bouquetId", bouquetId);
        request.setAttribute("ws", ws);
        request.setAttribute("bouquet", b);
        request.setAttribute("listImage", listImage);
        request.setAttribute("flowerInBQ", bqRaws);
        request.setAttribute("allBatchs", allBatches);
        request.setAttribute("allFlowers", allFlowers);
        request.setAttribute("cateName", cateName);

        request.getRequestDispatcher("./DashMin/wholeSaleQuotation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        LocalDate requestDate = LocalDate.parse(request.getParameter("requestDate"));
        String requestGroupId = request.getParameter("requestGroupId");
        int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));
        int wsId = Integer.parseInt(request.getParameter("wsId"));
        int requestedQuantity = Integer.parseInt(request.getParameter("requestedQuantity"));
        String status = request.getParameter("status");

        String[] flowerIdStrs = request.getParameterValues("flowerIds[]");
        String[] assignExpenseStrs = request.getParameterValues("assignExpense[]");
        String[] quantityStrs = request.getParameterValues("quantities[]");

        WholeSaleDAO wsDao = new WholeSaleDAO();

        int wholesaleExpensePerUnit = 0;
        int wholesalePricePerUnit = 0;
        int totalWholesalePrice = 0;

        for (int i = 0; i < flowerIdStrs.length; i++) {
            int flowerId = Integer.parseInt(flowerIdStrs[i]);
            int assignExpense = Integer.parseInt(assignExpenseStrs[i]);
            int quantity = Integer.parseInt(quantityStrs[i]);

            wholesaleExpensePerUnit += assignExpense * quantity;

            // Lưu chi tiết hoa
            WholeSaleFlower wsFlower = new WholeSaleFlower();
            wsFlower.setWholesale_request_id(wsId);
            wsFlower.setBouquet_id(bouquetId);
            wsFlower.setFlower_id(flowerId);
            wsFlower.setFlower_ws_price(assignExpense);

            wsDao.upsertFlowerWS(wsFlower);
        }

        wholesalePricePerUnit = (int) (wholesaleExpensePerUnit * 3);
        totalWholesalePrice = wholesalePricePerUnit * requestedQuantity;

        LocalDate quotedDate = LocalDate.now();
        
        if(status.equalsIgnoreCase("PENDING")){
        wsDao.assignQuotedPrice(totalWholesalePrice, quotedDate, wholesalePricePerUnit, userId, requestDate, "PENDING", bouquetId, wholesaleExpensePerUnit, requestGroupId);
        }else if(status.equalsIgnoreCase("QUOTED")){
        wsDao.updateQuotedPrice(totalWholesalePrice, quotedDate, wholesalePricePerUnit, userId, requestDate, "QUOTED", bouquetId, wholesaleExpensePerUnit, requestGroupId);    
        }
        
        request.setAttribute("wholesaleExpensePerUnit", wholesaleExpensePerUnit);
        request.setAttribute("wholesalePricePerUnit", wholesalePricePerUnit);
        request.setAttribute("totalWholesalePrice", totalWholesalePrice);

        response.sendRedirect(request.getContextPath() + "/wholeSaleQuotation"
                + "?userId=" + userId
                + "&requestDate=" + requestDate
                + "&requestGroupId=" + requestGroupId
                + "&status=QUOTED"
                + "&bouquetId=" + bouquetId);

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
