/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BouquetDAO;
import dal.CategoryDAO;
import dal.RawFlowerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Bouquet;
import model.BouquetImage;
import model.BouquetRaw;
import model.Category;
import model.RawFlower;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "BouquetController", urlPatterns = {"/viewBouquet"})
public class BouquetController extends HttpServlet {

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
            out.println("<title>Servlet BouquetController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BouquetController at " + request.getContextPath() + "</h1>");
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

        String pageParam = request.getParameter("page");
        int currentPage = 1;

        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        int itemsPerPage = 6;
        List<Bouquet> listBouquet = new ArrayList<>();
        List<Category> listCategoryBQ = new ArrayList<>();
        List<BouquetImage> listImage = new ArrayList<>();
        List<RawFlower> listFlower = new ArrayList<>();
        List<BouquetRaw> listBQraw = new ArrayList<>();

        BouquetDAO bdao = new BouquetDAO();
        CategoryDAO cdao = new CategoryDAO();
        RawFlowerDAO fdao = new RawFlowerDAO();

        listBQraw = bdao.getBouquetRaw();
        listFlower = fdao.getAll();
        listCategoryBQ = cdao.getBouquetCategory();
        String name = request.getParameter("bouquetName");
        String sort = request.getParameter("sortField");
        String cateIDstr = request.getParameter("categoryS");
        String flowerID = request.getParameter("flowerS");
        listImage = bdao.getAllBouquetImage();
        
        Integer rawID = null;
        if (flowerID != null && !flowerID.trim().isEmpty()) {
            try {
                rawID = Integer.parseInt(flowerID);
            } catch (NumberFormatException e) {
                rawID = null;
            }
        }
        
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
        boolean hasFlower = (rawID != null && rawID > 0);

        // Lấy list gốc (với hoặc không có filter tìm kiếm)
        if (hasName || hasCate || hasFlower) {
            listBouquet = bdao.searchBouquet(name, null, null, cateID, rawID);
        } else {
            listBouquet = bdao.getAll();
        }

        int totalItems = listBouquet.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

// Cắt danh sách cho trang hiện tại
        int start = (currentPage - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, totalItems);

        List<Bouquet> paginatedList = new ArrayList<>();
        if (start < totalItems) {
            paginatedList = listBouquet.subList(start, end);
        }

// Gửi đến JSP
        request.setAttribute("listBQraw", listBQraw);
        request.setAttribute("listFlower", listFlower);
        request.setAttribute("listImage", listImage);
        request.setAttribute("bouquetName", name);
        request.setAttribute("sortField", sort);
        request.setAttribute("listBouquet", paginatedList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("cateBouquetHome", listCategoryBQ);
        request.getRequestDispatcher("./DashMin/product.jsp").forward(request, response);
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
