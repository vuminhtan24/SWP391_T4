/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import java.util.Comparator;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CategoryServlet", urlPatterns = {"/category"})
public class CategoryServlet extends HttpServlet {

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
            out.println("<title>Servlet CategoryServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryServlet at " + request.getContextPath() + "</h1>");
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

        try {
            /* ===== 1. Lấy – validate – gán mặc định ===== */
            String pageParam = request.getParameter("page");
            int currentPage = (pageParam != null && pageParam.matches("\\d+"))
                    ? Integer.parseInt(pageParam) : 1;
            if (currentPage <= 0) {
                currentPage = 1;
            }

            String sortField = request.getParameter("sortField");        // >>> sort
            String sortDir = request.getParameter("sortDir");          // >>> sort
            if (!"categoryName".equals(sortField)) {
                sortField = "categoryId";
            }
            if (!"desc".equalsIgnoreCase(sortDir)) {
                sortDir = "asc";
            }

            /* ===== 2. Lấy dữ liệu ===== */
            int itemsPerPage = 6;
            CategoryDAO cdao = new CategoryDAO();

            // tìm kiếm theo tên (nếu có)
            String categoryName = request.getParameter("categoryName");
            List<Category> listCategory = (categoryName != null && !categoryName.trim().isEmpty())
                    ? cdao.searchCategory(categoryName)
                    : cdao.getAll();

            /* ===== 3. Sắp xếp trong Java – 4 dòng là xong ===== */     // >>> sort
            Comparator<Category> cmp = "categoryName".equals(sortField)
                    ? Comparator.comparing(Category::getCategoryName, String.CASE_INSENSITIVE_ORDER)
                    : Comparator.comparingInt(Category::getCategoryId);

            if ("desc".equalsIgnoreCase(sortDir)) {
                cmp = cmp.reversed();
            }
            listCategory.sort(cmp);

            /* ===== 4. Phân trang (như cũ) ===== */
            int totalItems = listCategory.size();
            int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
            int start = (currentPage - 1) * itemsPerPage;
            int end = Math.min(start + itemsPerPage, totalItems);
            List<Category> paginatedList = (start < totalItems)
                    ? listCategory.subList(start, end)
                    : new ArrayList<>();

            /* ===== 5. Gửi sang JSP ===== */
            request.setAttribute("listCategory", paginatedList);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("sortField", sortField);             // >>> sort
            request.setAttribute("sortDir", sortDir);               // >>> sort
            request.setAttribute("totalItems", totalItems);
            request.getRequestDispatcher("./DashMin/category.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "An error occurred while processing the request.");
        }
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
