/*
 * Click nbproject/project.properties to edit this template
 */
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import dal.FlowerBatchDAO;
import scheduler.FlowerScheduler;
/**
 * Servlet to handle deleting a flower batch.
 */
@WebServlet(name = "DeleteBatchController", urlPatterns = {"/delete_batch"})
public class DeleteBatchController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            // Get batch_id and flower_id from URL parameters
            String batchIdStr = request.getParameter("batch_id");
            String flowerIdStr = request.getParameter("flower_id");
            if (batchIdStr == null || batchIdStr.trim().isEmpty() || flowerIdStr == null || flowerIdStr.trim().isEmpty()) {
                session.setAttribute("error", "Batch ID or Flower ID is required.");
                response.sendRedirect(request.getContextPath() + "/rawFlowerDetails?flower_id=" + flowerIdStr);
                return;
            }

            int batchId = Integer.parseInt(batchIdStr);
            int flowerId = Integer.parseInt(flowerIdStr);

            // Call DAO to delete flower batch
            FlowerBatchDAO fbDAO = new FlowerBatchDAO();
            fbDAO.deleteFlowerBatch(batchId);

            // Set success message and redirect to flower details
            session.setAttribute("message", "Flower batch deleted successfully!");
            response.sendRedirect(request.getContextPath() + "/rawFlowerDetails?flower_id=" + flowerId);
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid Batch ID or Flower ID.");
            response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "An error occurred while deleting the flower batch: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect to doGet to handle deletion (optional, can be removed if not needed)
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for deleting a flower batch.";
    }
}
