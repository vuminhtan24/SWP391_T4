package controller;

import dal.FeedbackDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jakarta.servlet.http.Part;
import java.util.Arrays;
import java.util.stream.Collectors;
import model.Feedback;
import model.FeedbackImg;
import model.User;

@WebServlet(name = "FeedbackController", urlPatterns = {"/ZeShopper/feedback"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB lưu tạm vào disk nếu vượt
        maxFileSize = 1024 * 1024 * 5, // 5MB giới hạn mỗi file
        maxRequestSize = 1024 * 1024 * 15 // 15MB giới hạn tổng request
)
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        User currentUser = (User) request.getSession().getAttribute("currentAcc");

        if ("submitFeedback".equals(action)) {
            if (currentUser == null) {
                response.sendRedirect(request.getContextPath() + "/ZeShopper/login.jsp");
                return;
            }

            // Kiểm tra và parse các tham số
            String orderIdStr = request.getParameter("orderId");
            String bouquetIdStr = request.getParameter("bouquetId");
            String ratingStr = request.getParameter("rating");
            String comment = request.getParameter("comment");

            // Kiểm tra tham số rỗng hoặc null
            if (orderIdStr == null || orderIdStr.trim().isEmpty()
                    || bouquetIdStr == null || bouquetIdStr.trim().isEmpty()
                    || ratingStr == null || ratingStr.trim().isEmpty()) {
                request.setAttribute("error", "Dữ liệu không hợp lệ. Vui lòng thử lại.");
                request.getRequestDispatcher("/ZeShopper/feedback-form.jsp").forward(request, response);
                return;
            }

            // Parse tham số
            int orderId, bouquetId, rating;
            try {
                orderId = Integer.parseInt(orderIdStr);
                bouquetId = Integer.parseInt(bouquetIdStr);
                rating = Integer.parseInt(ratingStr);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại thông tin.");
                request.getRequestDispatcher("/ZeShopper/feedback-form.jsp").forward(request, response);
                return;
            }

            // Kiểm tra số ký tự của comment
            if (comment == null || comment.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập nhận xét.");
                request.setAttribute("orderId", orderId);
                request.setAttribute("bouquetId", bouquetId);
                request.getRequestDispatcher("/ZeShopper/feedback-form.jsp").forward(request, response);
                return;
            }

            if (comment.length() > 300) {
                request.setAttribute("error", "Nhận xét không được vượt quá 300 ký tự (kể cả khoảng trắng).");
                request.setAttribute("orderId", orderId);
                request.setAttribute("bouquetId", bouquetId);
                request.getRequestDispatcher("/ZeShopper/feedback-form.jsp").forward(request, response);
                return;
            }

            // Kiểm tra điều kiện viết feedback
            FeedbackDAO feedbackDAO = new FeedbackDAO();
            if (!feedbackDAO.canWriteFeedback(currentUser.getUserid(), bouquetId, orderId)) {
                request.setAttribute("error", "Bạn không đủ điều kiện để viết phản hồi.");
                request.getRequestDispatcher("/ZeShopper/order.jsp").forward(request, response);
                return;
            }

            // Kiểm tra feedback đã tồn tại
            List<Feedback> existingFeedbacks = feedbackDAO.getFeedbacksByBouquetId(bouquetId);
            for (Feedback f : existingFeedbacks) {
                if (f.getCustomerId() == currentUser.getUserid()
                        && ("approved".equals(f.getStatus()) || "rejected".equals(f.getStatus()))) {
                    request.setAttribute("error", "Bạn đã gửi phản hồi cho sản phẩm này rồi.");
                    request.getRequestDispatcher("/ZeShopper/order.jsp").forward(request, response);
                    return;
                }
            }

            // Xử lý upload ảnh
            List<String> savedImageUrls = new ArrayList<>();
            String uploadPath = request.getServletContext().getRealPath("/upload/FeedbackIMG");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists() && !uploadDir.mkdirs()) {
                request.setAttribute("error", "Không thể tạo thư mục upload.");
                request.setAttribute("orderId", orderId);
                request.setAttribute("bouquetId", bouquetId);
                request.getRequestDispatcher("/ZeShopper/feedback-form.jsp").forward(request, response);
                return;
            }

            Collection<Part> fileParts = request.getParts().stream()
                    .filter(p -> "imageFiles".equals(p.getName())
                    && p.getSubmittedFileName() != null
                    && !p.getSubmittedFileName().isEmpty())
                    .collect(Collectors.toList());

            if (fileParts.size() > 3) {
                request.setAttribute("error", "Bạn chỉ được upload tối đa 3 ảnh.");
                request.setAttribute("orderId", orderId);
                request.setAttribute("bouquetId", bouquetId);
                request.getRequestDispatcher("/ZeShopper/feedback-form.jsp").forward(request, response);
                return;
            }

            for (Part part : fileParts) {
                String originalName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                String newFileName = System.currentTimeMillis() + "_" + originalName;

                String fullDiskPath = uploadPath + File.separator + newFileName;
                part.write(fullDiskPath);

                String rootPath = fullDiskPath.replace("\\build", "");
                Path source = Paths.get(fullDiskPath);
                Path target = Paths.get(rootPath);
                Files.createDirectories(target.getParent());
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

                savedImageUrls.add(newFileName);
            }

            // Thêm feedback
            int feedbackId = feedbackDAO.insertFeedback(currentUser.getUserid(), bouquetId, orderId, rating, comment);
            if (feedbackId == -1) {
                request.setAttribute("error", "Không thể gửi phản hồi.");
                request.setAttribute("orderId", orderId);
                request.setAttribute("bouquetId", bouquetId);
                request.getRequestDispatcher("/ZeShopper/feedback-form.jsp").forward(request, response);
                return;
            }

            // Lưu ảnh feedback
            for (String url : savedImageUrls) {
                FeedbackImg img = new FeedbackImg();
                img.setFeedbackId(feedbackId);
                img.setImageUrl(url);
                feedbackDAO.insertFeedbackImage(feedbackId, url);
            }

            request.setAttribute("message", "Cảm ơn bạn đã dành thời gian đánh giá sản phẩm của shop!(ΦзΦ) ❤️");
            request.getRequestDispatcher("/ZeShopper/order.jsp").forward(request, response);
        }
    }
}
