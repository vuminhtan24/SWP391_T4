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
@WebServlet(name = "DeleteCategoryController", urlPatterns = {"/deleteCategory"})
public class DeleteCategoryController extends HttpServlet {

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
            out.println("<title>Servlet DeleteCategoryController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteCategoryController at " + request.getContextPath() + "</h1>");
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
            String id = request.getParameter("id");
            if (id == null || id.trim().isEmpty()) {
                request.getSession().setAttribute("message", "Error: Category ID is missing or empty.");
                response.sendRedirect(request.getContextPath() + "/category");
                return;
            }

            int categoryID = Integer.parseInt(id);
            if (categoryID <= 0) {
                request.getSession().setAttribute("message", "Error: Category ID must be a positive integer.");
                response.sendRedirect(request.getContextPath() + "/category");
                return;
            }

            // Kiểm tra danh mục có tồn tại không
            CategoryDAO cdao = new CategoryDAO();
            Category category = cdao.getCategoryById(categoryID);
            if (category == null) {
                request.getSession().setAttribute("message", "Error: Category not found with ID: " + categoryID);
                response.sendRedirect(request.getContextPath() + "/category");
                return;
            }

            // Xóa danh mục
            boolean success = cdao.deleteCategory(categoryID);
            if (!success) {
                throw new Exception("Failed to delete category due to database error.");
            }

            // Lưu thông báo thành công vào session
            request.getSession().setAttribute("message", "Category deleted successfully!");

            // Chuyển hướng về trang danh sách danh mục
            response.sendRedirect(request.getContextPath() + "/category");
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("message", "Error: Invalid category ID format: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/category");
        } catch (Exception e) {
            System.out.println("Error in DeleteCategoryController doGet: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("message", "Error: An error occurred while processing the request.");
            response.sendRedirect(request.getContextPath() + "/category");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method is not supported for this endpoint.");
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
