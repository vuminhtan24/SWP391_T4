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
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import util.Validate;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AddCategoryController", urlPatterns = {"/addCategory"})
public class AddCategoryController extends HttpServlet {

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
            out.println("<title>Servlet AddCategoryController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddCategoryController at " + request.getContextPath() + "</h1>");
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
            // Chuyển hướng đến JSP để hiển thị form thêm danh mục
            request.getRequestDispatcher("./DashMin/addcategory.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Error in AddCategoryController doGet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            // Lấy dữ liệu từ form
            String categoryName = request.getParameter("categoryName");
            String description = request.getParameter("description");

            // Validate các field
            String categoryNameError = Validate.validateText(categoryName, "Category Name");
            if (categoryNameError == null) {
                // Kiểm tra độ dài categoryName (3-100 ký tự)
                categoryNameError = Validate.validateLength(categoryName, "Category Name", 3, 100);
            }
            String descriptionError = Validate.validateDescription(description);

            // Lưu dữ liệu đã nhập vào session để giữ giá trị
            session.setAttribute("categoryName", categoryName);
            session.setAttribute("description", description);

            // Nếu có lỗi, lưu thông báo lỗi vào session và hiển thị lại form
            if (categoryNameError != null || descriptionError != null) {
                session.setAttribute("categoryNameError", categoryNameError);
                session.setAttribute("descriptionError", descriptionError);
                request.getRequestDispatcher("./DashMin/addcategory.jsp").forward(request, response);
                return;
            }

            // Xóa các lỗi và dữ liệu trong session nếu validate thành công
            session.removeAttribute("categoryNameError");
            session.removeAttribute("descriptionError");
            session.removeAttribute("categoryName");
            session.removeAttribute("description");

            // Tạo đối tượng Category
            Category category = new Category();
            category.setCategoryName(categoryName);
            category.setDescription(description != null ? description : "");

            // Thêm danh mục vào cơ sở dữ liệu
            CategoryDAO cdao = new CategoryDAO();
            boolean success = cdao.insertCategory(category);

            if (!success) {
                session.setAttribute("error", "Failed to add category.");
                request.getRequestDispatcher("./DashMin/addcategory.jsp").forward(request, response);
                return;
            }

            // Lưu thông báo thành công vào session
            session.setAttribute("message", "Category added successfully!");

            // Chuyển hướng về trang danh sách danh mục
            response.sendRedirect(request.getContextPath() + "/category");
        } catch (Exception e) {
            System.out.println("Error in AddCategoryController doPost: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("error", "An error occurred while processing the request.");
            request.getRequestDispatcher("./DashMin/addcategory.jsp").forward(request, response);
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
