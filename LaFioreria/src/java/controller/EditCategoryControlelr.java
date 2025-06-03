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

/**
 *
 * @author Admin
 */
@WebServlet(name = "EditCategoryControlelr", urlPatterns = {"/editCategory"})
public class EditCategoryControlelr extends HttpServlet {

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
            out.println("<title>Servlet EditCategoryControlelr</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditCategoryControlelr at " + request.getContextPath() + "</h1>");
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
            // Lấy tham số id từ request
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Category ID is missing or empty.");
                return;
            }

            int id = Integer.parseInt(idStr);
            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Category ID must be a positive integer.");
                return;
            }

            // Lấy thông tin danh mục từ CategoryDAO
            CategoryDAO cdao = new CategoryDAO();
            Category category = cdao.getCategoryById(id);
            if (category == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Category not found with ID: " + id);
                return;
            }

            // Gửi thông tin danh mục đến JSP để hiển thị trên form chỉnh sửa
            request.setAttribute("category", category);
            request.getRequestDispatcher("./DashMin/editcategory.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid category ID format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error in EditCategoryController doGet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            String idStr = request.getParameter("id");
            String categoryName = request.getParameter("categoryName");
            String description = request.getParameter("description");

            // Kiểm tra dữ liệu đầu vào
            if (idStr == null || idStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Category ID is missing or empty.");
            }
            if (categoryName == null || categoryName.trim().isEmpty()) {
                throw new IllegalArgumentException("Category name is required.");
            }

            int id = Integer.parseInt(idStr);
            if (id <= 0) {
                throw new IllegalArgumentException("Category ID must be a positive integer.");
            }

            // Tạo đối tượng Category với dữ liệu mới
            Category category = new Category(id, categoryName, description != null ? description : "");

            // Cập nhật danh mục trong cơ sở dữ liệu
            CategoryDAO cdao = new CategoryDAO();
            cdao.updateCategory(category);

            // Lưu thông báo thành công vào session
            request.getSession().setAttribute("message", "Category updated successfully!");

            // Chuyển hướng về trang danh sách danh mục
            response.sendRedirect(request.getContextPath() + "/category");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        } catch (Exception e) {
            System.out.println("Error in EditCategoryController doPost: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }
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
