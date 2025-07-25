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
@WebServlet(name = "ProductController", urlPatterns = {"/product"})
public class ProductController extends HttpServlet {

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
            out.println("<title>Servlet ProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductController at " + request.getContextPath() + "</h1>");
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

        List<Bouquet> listBouquet = new ArrayList<>();
        List<Category> listCategoryBQ = new ArrayList<>();
        List<FlowerType> listFlower = new ArrayList<>();

        BouquetDAO bdao = new BouquetDAO();
        CategoryDAO cdao = new CategoryDAO();
        FlowerTypeDAO fdao = new FlowerTypeDAO();

        listFlower = fdao.getAllFlowerTypes();
        request.setAttribute("listFlower", listFlower);
        listCategoryBQ = cdao.getBouquetCategory();
        request.setAttribute("cateBouquetHome", listCategoryBQ);
        List<BouquetImage> images = bdao.getAllBouquetImage();

        String name = request.getParameter("bouquetName");
        String cateIDstr = request.getParameter("categoryId");
        String maxValue = request.getParameter("maxPrice");
        String minValue = request.getParameter("minPrice");
        String flowerIDstr = request.getParameter("flowerID");

        Integer flowerID = null;
        if (flowerIDstr != null && !flowerIDstr.trim().isEmpty()) {
            try {
                flowerID = Integer.parseInt(flowerIDstr);
            } catch (Exception e) {
                flowerID = null;
            }
        }

        int max = 10000000;
        int min = 0;

        try {
            if (minValue != null && !minValue.isEmpty()) {
                min = Integer.parseInt(minValue);
            }
            if (maxValue != null && !maxValue.isEmpty()) {
                max = Integer.parseInt(maxValue);
            }
        } catch (NumberFormatException e) {
            min = 0;
            max = 10000000;
        }

        request.setAttribute("minPrice", min);
        request.setAttribute("maxPrice", max);

        Integer cateID = null;
        if (cateIDstr != null && !cateIDstr.trim().isEmpty()) {
            try {
                cateID = Integer.parseInt(cateIDstr);
            } catch (NumberFormatException e) {
                cateID = null;
            }
        }

        boolean hasName = (name != null && !name.trim().isEmpty());
        boolean hasCate = (cateID != null && cateID > 0);
        boolean hasFlower = (flowerID != null && flowerID > 0);

        Integer minFilter = (min == 0) ? null : min;
        Integer maxFilter = (max == 10000000) ? null : max;

        if (hasName || hasCate || minFilter != null || maxFilter != null || hasFlower) {
            String searchName = hasName ? name.trim() : null;
            Integer searchCate = hasCate ? cateID : null;

            listBouquet = bdao.searchBouquet(searchName, minFilter, maxFilter, searchCate, flowerID);
        } else {
            listBouquet = bdao.getAll();
        }

        // Lọc trước khi phân trang
        Map<Integer, Integer> bouquetAvailableMap = new HashMap<>();
        List<Bouquet> filteredBouquetList = new ArrayList<>();
        for (Bouquet bq : listBouquet) {
            int available = bdao.bouquetAvailable(bq.getBouquetId());
            if ("valid".equalsIgnoreCase(bq.getStatus()) && available > 0) {
                bouquetAvailableMap.put(bq.getBouquetId(), available);
                filteredBouquetList.add(bq);
            }
        }

        // Phân trang
        int pageSize = 6;
        int currentPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        int totalItems = filteredBouquetList.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, totalItems);
        List<Bouquet> bouquetPage = filteredBouquetList.subList(start, end);

        request.setAttribute("bouquetAvailableMap", bouquetAvailableMap);
        request.setAttribute("bouquetAvailable", bdao.allBouquetAvailable());
        request.setAttribute("images", images);
        request.setAttribute("listBouquet", bouquetPage);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        if (bouquetPage.isEmpty()) {
            request.setAttribute("error", "There aren't any Bouquet. Please try again!!!");
        }

        request.getRequestDispatcher("./ZeShopper/shop.jsp").forward(request, response);
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
