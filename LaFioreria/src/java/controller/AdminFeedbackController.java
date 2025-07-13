package controller;

import dal.BouquetDAO;
import dal.FeedbackDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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

        // Sort parameters
        String sortField = request.getParameter("sortField");
        String sortDir = request.getParameter("sortDir");
        if (sortField == null || (!"feedbackId".equals(sortField) && !"bouquetName".equals(sortField) && !"customerName".equals(sortField) &&
                !"rating".equals(sortField) && !"comment".equals(sortField) && !"created_at".equals(sortField) && !"status".equals(sortField))) {
            sortField = "feedbackId";
        }
        if (sortDir == null || (!"asc".equalsIgnoreCase(sortDir) && !"desc".equalsIgnoreCase(sortDir))) {
            sortDir = "asc";
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

        // Sorting
        Comparator<Feedback> cmp = Comparator.comparing(Feedback::getFeedbackId); // Default sort by ID
        switch (sortField) {
            case "bouquetName":
                cmp = Comparator.comparing(Feedback::getBouquetName, String.CASE_INSENSITIVE_ORDER);
                break;
            case "customerName":
                cmp = Comparator.comparing(f -> feedbackCustomerNames.get(f.getFeedbackId()), String.CASE_INSENSITIVE_ORDER);
                break;
            case "rating":
                cmp = Comparator.comparingInt(Feedback::getRating);
                break;
            case "comment":
                cmp = Comparator.comparing(Feedback::getComment, String.CASE_INSENSITIVE_ORDER);
                break;
            case "created_at":
                cmp = Comparator.comparing(Feedback::getCreated_at);
                break;
            case "status":
                cmp = Comparator.comparing(Feedback::getStatus, String.CASE_INSENSITIVE_ORDER);
                break;
        }
        if ("desc".equalsIgnoreCase(sortDir)) {
            cmp = cmp.reversed();
        }
        feedbackList.sort(cmp);

        // Pagination
        int totalItems = feedbackList.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
        currentPage = Math.max(1, Math.min(currentPage, totalPages));
        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);
        List<Feedback> paginatedList = totalItems > 0 ? feedbackList.subList(startIndex, endIndex) : new ArrayList<>();

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
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortDir", sortDir);
        request.setAttribute("bouquetList", bouquetList);
        request.getRequestDispatcher("/DashMin/feedback.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Can be extended for POST if needed
    }
}