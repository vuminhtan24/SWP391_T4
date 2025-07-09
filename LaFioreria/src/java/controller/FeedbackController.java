package controller;

import dal.FeedbackDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import model.Feedback;
import model.FeedbackImg;
import model.User;

@WebServlet(name = "FeedbackController", urlPatterns = {"/ZeShopper/feedback"})
public class FeedbackController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        User currentUser = (User) request.getSession().getAttribute("currentAcc");

        if ("write".equals(action)) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));
            FeedbackDAO feedbackDAO = new FeedbackDAO();

            if (currentUser == null) {
                request.setAttribute("error", "Vui lòng đăng nhập để viết phản hồi.");
                response.sendRedirect(request.getContextPath() + "/ZeShopper/login.jsp");
                return;
            }

            if (!feedbackDAO.canWriteFeedback(currentUser.getUserid(), bouquetId, orderId)) {
                request.setAttribute("error", "Bạn không đủ điều kiện để viết phản hồi cho sản phẩm này.");
                request.getRequestDispatcher("/ZeShopper/order.jsp").forward(request, response);
                return;
            }

            request.setAttribute("orderId", orderId);
            request.setAttribute("bouquetId", bouquetId);
            request.getRequestDispatcher("/ZeShopper/feedback-form.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Hành động không hợp lệ.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        User currentUser = (User) request.getSession().getAttribute("currentAcc");

        if ("submitFeedback".equals(action)) {
            if (currentUser == null) {
                response.sendRedirect(request.getContextPath() + "/ZeShopper/login.jsp");
                return;
            }

            int orderId = Integer.parseInt(request.getParameter("orderId"));
            int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            String comment = request.getParameter("comment");

            FeedbackDAO feedbackDAO = new FeedbackDAO();
            if (!feedbackDAO.canWriteFeedback(currentUser.getUserid(), bouquetId, orderId)) {
                request.setAttribute("error", "Bạn không đủ điều kiện để viết phản hồi.");
                request.getRequestDispatcher("/ZeShopper/order.jsp").forward(request, response);
                return;
            }

            int feedbackId = feedbackDAO.insertFeedback(currentUser.getUserid(), bouquetId, orderId, rating, comment);
            if (feedbackId == -1) {
                request.setAttribute("error", "Không thể gửi phản hồi.");
                request.getRequestDispatcher("/ZeShopper/feedback-form.jsp").forward(request, response);
                return;
            }

            // Xử lý ảnh phản hồi (giả sử gửi qua form với tên 'imageUrls')
            String[] imageUrls = request.getParameterValues("imageUrls");
            if (imageUrls != null) {
                for (String imageUrl : imageUrls) {
                    feedbackDAO.insertFeedbackImage(feedbackId, imageUrl);
                }
            }

            request.setAttribute("message", "Gửi phản hồi thành công. Phản hồi sẽ được hiển thị sau khi được duyệt.");
            request.getRequestDispatcher("/ZeShopper/order.jsp").forward(request, response);
        }
    }
}