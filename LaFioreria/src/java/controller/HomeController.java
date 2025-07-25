/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BouquetDAO;
import dal.CategoryDAO;
import dal.FlowerTypeDAO;
import dal.RawFlowerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Bouquet;
import model.BouquetImage;
import model.Category;
import model.FlowerType;
import model.RawFlower;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

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
            out.println("<title>Servlet HomeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeController at " + request.getContextPath() + "</h1>");
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
        List<Category> listCategoryBQ = new ArrayList<>();
        List<FlowerType> listFlower = new ArrayList<>();
        List<Bouquet> listMostSellBouquet = new ArrayList<>();

        BouquetDAO bdao = new BouquetDAO();
        CategoryDAO cdao = new CategoryDAO();
        FlowerTypeDAO fdao = new FlowerTypeDAO();

        listFlower = fdao.getAllFlowerTypes();
        request.setAttribute("listFlowerHome", listFlower);

        listCategoryBQ = cdao.getBouquetCategory();
        listMostSellBouquet = bdao.getMostSellBouquet2();
        List<BouquetImage> images = bdao.getAllBouquetImage();

        Map<Integer, Integer> bouquetAvailableMap = new HashMap<>();
        for (Bouquet b : listMostSellBouquet) {
            int available = bdao.bouquetAvailable(b.getBouquetId());  // Lấy available theo từng bouquet
            bouquetAvailableMap.put(b.getBouquetId(), available);
        }

        request.setAttribute("images", images);
        request.setAttribute("listMostSellBouquet", listMostSellBouquet);
        request.setAttribute("cateBouquetHome", listCategoryBQ);
        request.setAttribute("bouquetAvailableMap", bouquetAvailableMap);

        request.getRequestDispatcher("./ZeShopper/home.jsp").forward(request, response);
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
