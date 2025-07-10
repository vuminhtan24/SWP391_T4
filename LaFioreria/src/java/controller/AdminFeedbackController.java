package controller;

import dal.BouquetDAO;
import dal.FeedbackDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Bouquet;
import model.Feedback;
import model.FeedbackImg;

@WebServlet(name = "AdminFeedbackController", urlPatterns = {"/adminFeedback"})
public class AdminFeedbackController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        BouquetDAO bouquetDAO = new BouquetDAO();

        if ("approve".equals(action) || "reject".equals(action)) {
            int feedbackId = Integer.parseInt(request.getParameter("id"));
            String status = "approve".equals(action) ? "approved" : "rejected";
            feedbackDAO.updateFeedbackStatus(feedbackId, status);
            request.setAttribute("message", "Feedback status updated successfully.");
        }

        // Pagination and filter parameters
        String pageParam = request.getParameter("page");
        int currentPage = 1;
        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }
        int itemsPerPage = 10;

        String search = request.getParameter("feedbackSearch");
        Integer bouquetId = null;
        if (request.getParameter("bouquetId") != null && !request.getParameter("bouquetId").trim().isEmpty()) {
            try {
                bouquetId = Integer.parseInt(request.getParameter("bouquetId"));
            } catch (NumberFormatException e) {
                bouquetId = null;
            }
        }
        Integer rating = null;
        if (request.getParameter("rating") != null && !request.getParameter("rating").trim().isEmpty()) {
            try {
                rating = Integer.parseInt(request.getParameter("rating"));
            } catch (NumberFormatException e) {
                rating = null;
            }
        }

        // Retrieve all feedback with filters
        List<Feedback> feedbackList = feedbackDAO.getAllFeedbacks(search, bouquetId, rating);
        Map<Integer, String> feedbackCustomerNames = new HashMap<>();
        Map<Integer, List<FeedbackImg>> feedbackImages = new HashMap<>();

        for (Feedback f : feedbackList) {
            feedbackCustomerNames.put(f.getFeedbackId(), feedbackDAO.getCustomerNameFromOrderOrUser(f.getCustomerId()));
            feedbackImages.put(f.getFeedbackId(), feedbackDAO.getFeedbackImages(f.getFeedbackId()));
            if (f.getBouquetId() > 0) {
                Bouquet bouquet = bouquetDAO.getBouquetByID(f.getBouquetId());
                f.setBouquetName(bouquet != null ? bouquet.getBouquetName() : "Unknown Bouquet");
            } else {
                f.setBouquetName("Unknown Bouquet");
            }
        }

        int totalItems = feedbackList.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
        int start = (currentPage - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, totalItems);
        List<Feedback> paginatedList = feedbackList.subList(start, end);

        // Get list of bouquets for filter dropdown
        List<Bouquet> bouquetList = bouquetDAO.getAll();

        request.setAttribute("feedbackList", paginatedList);
        request.setAttribute("feedbackCustomerNames", feedbackCustomerNames);
        request.setAttribute("feedbackImages", feedbackImages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("feedbackSearch", search);
        request.setAttribute("bouquetId", bouquetId);
        request.setAttribute("rating", rating);
        request.setAttribute("bouquetList", bouquetList);
        request.getRequestDispatcher("/DashMin/feedback.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Can be extended for POST if needed
    }
}