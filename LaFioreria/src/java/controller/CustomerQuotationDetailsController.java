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
@WebServlet(name = "CustomerQuotationDetailsController", urlPatterns = {"/quotationDetails"})
public class CustomerQuotationDetailsController extends HttpServlet {

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
            out.println("<title>Servlet CustomerQuotationDetailsController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerQuotationDetailsController at " + request.getContextPath() + "</h1>");
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
        String userIdStr = request.getParameter("userId").trim();
        String requestDateStr = request.getParameter("requestDate").trim();
        String status = request.getParameter("status").trim();
        String sortBy = request.getParameter("sortBy"); // "quantity" hoặc "status"
        String sortOrder = request.getParameter("sortOrder"); // "asc" hoặc "desc"
        String flowerIDstr = request.getParameter("flowerS");

        Integer flowerID = null;
        if (flowerIDstr != null && !flowerIDstr.trim().isEmpty()) {
            try {
                flowerID = Integer.parseInt(flowerIDstr);
            } catch (Exception e) {
                flowerID = null;
            }
        }

        Integer userId = Integer.parseInt(userIdStr);
        LocalDate requestDate = LocalDate.parse(requestDateStr);

        WholeSaleDAO wsDao = new WholeSaleDAO();
        BouquetDAO bDao = new BouquetDAO();
        UserDAO uDao = new UserDAO();
        FlowerTypeDAO fdao = new FlowerTypeDAO();

        List<FlowerType> listFlower = fdao.getAllFlowerTypes();
        List<Bouquet> listBQ = bDao.getAll();
        List<BouquetImage> listIMG = bDao.getAllBouquetImage();
        List<WholeSale> listWS = new ArrayList<>();
        
        if (flowerID != null) {
            listWS = wsDao.getWholeSaleRequestByFlowerID(flowerID);
        } else {
            listWS = wsDao.getWholeSaleList(userId, requestDate);
        }
        
        int totalWholeSaleOrder = 0;
        
        for (WholeSale wholeSale : listWS) {
            if(wholeSale.getStatus().equalsIgnoreCase(status)){
                totalWholeSaleOrder += wholeSale.getTotal_price();
            }
        }

        User userInfo = uDao.getUserByID(userId);

        Comparator<WholeSale> comparator = null;

        if ("quantity".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(WholeSale::getRequested_quantity);
        } else if ("status".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(WholeSale::getStatus, String.CASE_INSENSITIVE_ORDER);
        }

        if (comparator != null) {
            if ("desc".equalsIgnoreCase(sortOrder)) {
                comparator = comparator.reversed();
            }
            listWS.sort(comparator);
        }
        
        request.setAttribute("totalWholeSaleOrder", totalWholeSaleOrder);
        request.setAttribute("listFlower", listFlower);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortOrder", sortOrder);
        session.setAttribute("listWS", listWS);
        request.setAttribute("listBQ", listBQ);
        request.setAttribute("listIMG", listIMG);
        request.setAttribute("userInfo", userInfo);

        request.getRequestDispatcher("./ZeShopper/quotationDetails.jsp").forward(request, response);
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
