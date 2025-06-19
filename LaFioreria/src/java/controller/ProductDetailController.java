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
import java.util.List;
import model.Bouquet;
import model.BouquetRaw;
import model.Category;
import model.RawFlower;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ProductDetailController", urlPatterns = {"/productDetail"})
public class ProductDetailController extends HttpServlet {

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
            out.println("<title>Servlet ProductDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductDetailController at " + request.getContextPath() + "</h1>");
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
        String idStr = request.getParameter("id");

        int id = Integer.parseInt(idStr);
        BouquetDAO bqdao = new BouquetDAO();
        RawFlowerDAO rfdao = new RawFlowerDAO();
        CategoryDAO cdao = new CategoryDAO();

        Bouquet detailsBQ = bqdao.getBouquetByID(id);
        String cateName = cdao.getCategoryNameByBouquet(id);
        List<RawFlower> allFlowers = rfdao.getRawFlower();
        List<BouquetRaw> bqRaws = bqdao.getFlowerByBouquetID(id);
        List<Bouquet> sameCate = bqdao.searchBouquet(null, null, null, detailsBQ.getCid(), null);
        String cateDes = cdao.getCategoryDesByBouquet(id);
        
        for (int i = sameCate.size() - 1; i >= 0; i--) {
            Bouquet b = sameCate.get(i);
            if (b.getBouquetId() == detailsBQ.getBouquetId()) {
                sameCate.remove(i);
            }
        }
        
        request.setAttribute("cateDes", cateDes);
        request.setAttribute("listBouquet", sameCate);
        request.setAttribute("bouquetDetail", detailsBQ);
        request.setAttribute("cateName", cateName);
        request.setAttribute("allFlowers", allFlowers);
        request.setAttribute("cateList", cdao.getBouquetCategory());
        request.setAttribute("flowerInBQ", bqRaws);
        request.getRequestDispatcher("./ZeShopper/product-details.jsp").forward(request, response);
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
