package controller;

import dal.NotificationDAO;
import model.Notification;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.User;

@WebServlet(name = "NotificationController", urlPatterns = {"/notification"})
public class NotificationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NotificationDAO dao = new NotificationDAO();
        HttpSession session = request.getSession(false);
        try {
            List<Notification> notifications = dao.getAll();
            request.setAttribute("notifications", notifications);
            request.setAttribute("unreadNotifications", notifications);
            request.getRequestDispatcher("/DashMin/viewnotifications.jsp").forward(request, response);
        } catch (SQLException e) {
            log("Lỗi lấy thông báo", e);
            throw new ServletException("Không thể lấy thông báo", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        NotificationDAO dao = new NotificationDAO();
        response.setContentType("text/plain");

        try {
            HttpSession session = request.getSession(false);
            Integer userId = null;
            if (session != null && session.getAttribute("currentAcc") != null) {
                User user = (User) session.getAttribute("currentAcc");
                userId = user.getUserid(); // Lấy userId từ currentAcc
            }
            if (userId == null) {
                response.getWriter().write("error: not logged in");
                return;
            }

            if ("markAsRead".equals(action)) {
                int notificationId = Integer.parseInt(request.getParameter("notificationId"));
                dao.updateStatus(notificationId, "read");
                response.getWriter().write("success");
            } else if ("delete".equals(action)) {
                int notificationId = Integer.parseInt(request.getParameter("notificationId"));
                dao.deleteNotification(notificationId);
                response.getWriter().write("success");
            } else if ("markAllAsRead".equals(action)) {
                List<Notification> notifications = dao.getUnreadNotificationsByUser(userId);
                dao.markNotificationsAsRead(notifications);
                response.getWriter().write("success");
            } else {
                response.getWriter().write("error: invalid action");
            }
        } catch (SQLException e) {
            log("Lỗi xử lý thông báo", e);
            response.getWriter().write("error: database error - " + e.getMessage());
        } catch (NumberFormatException e) {
            response.getWriter().write("error: invalid notification id");
            log("Lỗi định dạng notificationId", e);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles notification-related requests";
    }
}
