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
@WebServlet(name = "CategoryDetailsController", urlPatterns = {"/categoryDetails"})
public class CategoryDetailsController extends HttpServlet {

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
            out.println("<title>Servlet CategoryDetailsController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryDetailsController at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        try {
            // Lấy tham số ID từ request
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã danh mục không được cung cấp.");
                return;
            }

            // Chuyển đổi ID thành số nguyên
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã danh mục không hợp lệ.");
                return;
            }

            // Khởi tạo DAO và lấy chi tiết danh mục
            CategoryDAO cdao = new CategoryDAO();
            Category category = cdao.getCategoryById(id);

            // Kiểm tra danh mục có tồn tại không
            if (category == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy danh mục với mã " + id + ".");
                return;
            }

            // Đặt thuộc tính cho JSP
            request.setAttribute("category", category);

            // Chuyển tiếp đến JSP
            request.getRequestDispatcher("./DashMin/categoryDetails.jsp").forward(request, response);
        } catch (Exception e) {
            // Xử lý lỗi bất ngờ
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                    "Đã xảy ra lỗi khi lấy chi tiết danh mục: " + e.getMessage());
        }
    }

    /**
     * Xử lý HTTP <code>POST</code>. Hiện tại không hỗ trợ cập nhật chi tiết danh mục.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException nếu xảy ra lỗi liên quan đến servlet
     * @throws IOException      nếu xảy ra lỗi I/O
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, 
                "Phương thức POST không được hỗ trợ cho chi tiết danh mục.");
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
