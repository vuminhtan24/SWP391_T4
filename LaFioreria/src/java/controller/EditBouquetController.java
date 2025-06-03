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
@WebServlet(name = "EditBouquetController", urlPatterns = {"/editBouquet"})
public class EditBouquetController extends HttpServlet {

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
            out.println("<title>Servlet EditBouquetController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditBouquetController at " + request.getContextPath() + "</h1>");
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

        request.setAttribute("bouquetDetail", detailsBQ);
        request.setAttribute("cateName", cateName);
        request.setAttribute("allFlowers", allFlowers);
        request.setAttribute("cateList", cdao.getBouquetCategory());
        request.setAttribute("flowerInBQ", bqRaws);
        request.getRequestDispatcher("./DashMin/editBouquet.jsp").forward(request, response);
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
        BouquetDAO dao = new BouquetDAO();
        
        String category = request.getParameter("category");
        String imageUrl = request.getParameter("imageUrl");
        String bqDescription = request.getParameter("bqDescription");
        String flowerIds[] = request.getParameterValues("flowerIds");
        String quantities[] = request.getParameterValues("quantities");
        String totalValueStr = request.getParameter("totalValue");
        String idStr = request.getParameter("id");
        String bqName = request.getParameter("bqName");

        int cateID = Integer.parseInt(category);
        int id = Integer.parseInt(idStr);
        int totalValue = Integer.parseInt(totalValueStr);
        
        dao.deleteBouquetRaw(id);
        if (flowerIds != null && quantities != null) {
            for (int i = 0; i < flowerIds.length; i++) {
                int fid = Integer.parseInt(flowerIds[i]);
                int quantity = Integer.parseInt(quantities[i]);

                BouquetRaw br = new BouquetRaw(id, fid, quantity);
                dao.insertBouquetRaw(br);
            }
        }

        Bouquet b = new Bouquet(id, bqName, bqDescription, imageUrl, cateID, totalValue);
        dao.updateBouquet(b);

        response.sendRedirect(request.getContextPath() + "/viewBouquet");

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
