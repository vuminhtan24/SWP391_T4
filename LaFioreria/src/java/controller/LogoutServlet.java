package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ZeShopper/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Không tạo mới session nếu không tồn tại
        if (session != null) {
            session.invalidate(); // Xoá toàn bộ session
        }
        response.sendRedirect(request.getContextPath() + "/ZeShopper/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Cho phép logout qua POST
    }

    @Override
    public String getServletInfo() {
        return "Handles user logout by invalidating session and redirecting to login page.";
    }
}
