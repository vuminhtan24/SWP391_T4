package controller;

import dal.FeedbackDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Feedback;

@WebServlet(name = "FeedbackDetailServlet", urlPatterns = {"/DashMin/feedbackDetail"})
public class FeedbackDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String feedbackIdParam = request.getParameter("id");
        if (feedbackIdParam == null || feedbackIdParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Feedback ID is required.");
            return;
        }

        try {
            int feedbackId = Integer.parseInt(feedbackIdParam);
            FeedbackDAO feedbackDAO = new FeedbackDAO();
            Feedback feedback = feedbackDAO.getFeedbackById(feedbackId);
            List<String> feedbackImages = feedbackDAO.getFeedbackImageUrls(feedbackId);

            if (feedback == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Feedback not found.");
                return;
            }

            request.setAttribute("feedback", feedback);
            request.setAttribute("feedbackImages", feedbackImages);
            request.getRequestDispatcher("/DashMin/feedback-detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid feedback ID format.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Có thể mở rộng cho POST nếu cần
    }
}