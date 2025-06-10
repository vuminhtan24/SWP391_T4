package controller;

import dal.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import util.Validate;

/**
 *
 * @author Admin
 */
@WebServlet(name = "EditCategoryController", urlPatterns = {"/editCategory"})
public class EditCategoryController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditCategoryController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditCategoryController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
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

            CategoryDAO cdao = new CategoryDAO();
            Category category = cdao.getCategoryById(id);
            if (category == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Category not found with ID: " + id);
                return;
            }

            request.setAttribute("category", category);
            request.getRequestDispatcher("/DashMin/editcategory.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid category ID format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error in doGet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            String idStr = request.getParameter("id");
            String categoryName = request.getParameter("categoryName");
            String description = request.getParameter("description");

            System.out.println("Received data - idStr: " + idStr + ", categoryName: " + categoryName + ", description: " + description);

            if (idStr == null || idStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Category ID is missing or empty.");
            }
            int id = Integer.parseInt(idStr);
            if (id <= 0) {
                throw new IllegalArgumentException("Category ID must be a positive integer.");
            }

            String categoryNameError = Validate.validateText(categoryName, "Category Name");
            if (categoryNameError == null) {
                categoryNameError = Validate.validateLength(categoryName, "Category Name", 3, 100);
            }
            String descriptionError = null;
            CategoryDAO cdao = new CategoryDAO();
            Category oldCategory = cdao.getCategoryById(id);
            if (description != null && !description.trim().isEmpty() && !description.trim().equals(oldCategory.getDescription())) {
                descriptionError = Validate.validateDescription(description);
            }

            System.out.println("Validation - categoryNameError: " + categoryNameError + ", descriptionError: " + descriptionError);

            request.setAttribute("categoryId", id);
            request.setAttribute("categoryName", categoryName);
            request.setAttribute("description", description);

            if (categoryNameError != null || descriptionError != null) {
                request.setAttribute("categoryNameError", categoryNameError);
                request.setAttribute("descriptionError", descriptionError);
                request.setAttribute("showErrorPopup", true);
                request.getRequestDispatcher("/DashMin/editcategory.jsp").forward(request, response);
                return;
            }

            session.removeAttribute("categoryNameError");
            session.removeAttribute("descriptionError");
            session.removeAttribute("categoryId");
            session.removeAttribute("categoryName");
            session.removeAttribute("description");

            Category category = new Category(id, categoryName, description != null ? description : "");
            boolean success = cdao.updateCategory(category);

            System.out.println("Update result - success: " + success);

            if (!success) {
                request.setAttribute("error", "Failed to update category. Please check if the category name already exists or database connection.");
                request.setAttribute("showErrorPopup", true);
                request.getRequestDispatcher("/DashMin/editcategory.jsp").forward(request, response);
                return;
            }

            session.setAttribute("message", "Category updated successfully!");
            response.sendRedirect(request.getContextPath() + "/category");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("showErrorPopup", true);
            request.getRequestDispatcher("/DashMin/editcategory.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Error in doPost: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing the request: " + e.getMessage());
            request.setAttribute("showErrorPopup", true);
            request.getRequestDispatcher("/DashMin/editcategory.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for editing categories with validation";
    }
}