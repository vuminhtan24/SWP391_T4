package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import dal.FlowerTypeDAO;
import util.Validate;

@WebServlet(name = "AddRawFlower", urlPatterns = {"/addRawFlower"})
public class AddRawFlower extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the JSP to display the add form
        request.getRequestDispatcher("/DashMin/addrawflower.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            // Get parameters from the form
            String flowerName = request.getParameter("flowerName");
            String image = request.getParameter("image");

            // Validate fields
            String flowerNameError = Validate.validateText(flowerName, "Flower Type Name");
            if (flowerNameError == null) {
                // Check length of flowerName (1-45 characters)
                flowerNameError = Validate.validateLength(flowerName, "Flower Type Name", 1, 45);
            }
            String imageError = Validate.validateImageUrl(image);

            // If there are errors, retain data and show popup
            if (flowerNameError != null || imageError != null) {
                request.setAttribute("flowerName", flowerName);
                request.setAttribute("image", image);
                request.setAttribute("flowerNameError", flowerNameError);
                request.setAttribute("imageError", imageError);
                request.setAttribute("showErrorPopup", true);

                request.getRequestDispatcher("/DashMin/addrawflower.jsp").forward(request, response);
                return;
            }

            // Call DAO method to add flower type
            FlowerTypeDAO ftDAO = new FlowerTypeDAO();
            ftDAO.addFlowerType(flowerName, image, true);

            // Set success message and redirect
            session.setAttribute("message", "Flower type added successfully!");
            response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while adding the flower type: " + e.getMessage());
            request.setAttribute("showErrorPopup", true);
            request.getRequestDispatcher("/DashMin/addrawflower.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding Flower Type information";
    }
}