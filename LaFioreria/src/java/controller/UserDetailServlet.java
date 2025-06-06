package controller;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.UserManager;

@WebServlet(name="UserDetailServlet", urlPatterns={"/ZeShopper/user-detail"})
public class UserDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Giả sử username được lưu trong session khi user đăng nhập
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null || username.isEmpty()) {
            // Nếu chưa đăng nhập thì redirect về login hoặc trang khác
            response.sendRedirect("login.jsp");
            return;
        }

        UserDAO ud = new UserDAO();
        UserManager um = ud.getUserByUsername(username);

        if (um == null) {
            request.setAttribute("error", "User không tồn tại.");
        } else {
            request.setAttribute("userManager", um);
        }

        request.getRequestDispatcher("DashMin/viewuserdetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null || username.isEmpty()) {
            response.sendRedirect("login.jsp");
            return;
        }

        String password = request.getParameter("pass");
        String fullName = request.getParameter("FullName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone");
        String address = request.getParameter("address");

        UserDAO ud = new UserDAO();
        boolean hasError = false;

        // Validate phone: 10 số bắt đầu bằng 090 hoặc 098
        if (phoneNumber == null || !phoneNumber.matches("^(090|098)\\d{7}$")) {
            request.setAttribute("errorPhone", "Phone phải có 10 chữ số và bắt đầu bằng 090 hoặc 098.");
            hasError = true;
        }

        // Validate email: ít nhất 3 ký tự trước @flower.com
        if (email == null || !email.matches("^[a-zA-Z0-9._%+-]{3,}@flower\\.com$")) {
            request.setAttribute("errorEmail", "Email phải có ít nhất 3 ký tự trước @flower.com.");
            hasError = true;
        }

        // Validate password (nếu có nhập mới)
        String passwordStrength = "";
        if (password != null && !password.isEmpty()) {
            if (password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$")) {
                passwordStrength = "Mạnh";
            } else if (password.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
                passwordStrength = "Trung bình";
            } else if (password.matches("^[a-zA-Z0-9]{7,}$")) {
                passwordStrength = "Yếu";
            } else {
                request.setAttribute("errorPass", "Password không hợp lệ. Tối thiểu 7 ký tự.");
                hasError = true;
            }
            request.setAttribute("passwordStrength", passwordStrength);
        } else {
            // Nếu không đổi password thì lấy password cũ
            UserManager oldUser = ud.getUserByUsername(username);
            password = oldUser != null ? oldUser.getPassword() : "";
        }

        // Validate fullname: ít nhất 4 ký tự, không chứa số
        if (fullName == null || !fullName.matches("^[a-zA-Z\\s]{4,}$")) {
            request.setAttribute("errorFullname", "Họ tên phải ít nhất 4 ký tự và không chứa số.");
            hasError = true;
        }

        // Validate address: chỉ chứa chữ, số và dấu cách
        if (address == null || !address.matches("^[a-zA-Z0-9\\s]+$")) {
            request.setAttribute("errorAddress", "Địa chỉ chỉ chứa chữ, số và khoảng trắng.");
            hasError = true;
        }

        if (hasError) {
            UserManager um = new UserManager();
            um.setUsername(username);
            um.setPassword(password);
            um.setFullname(fullName);
            um.setEmail(email);
            um.setPhone(phoneNumber);
            um.setAddress(address);

            request.setAttribute("userManager", um);
            request.getRequestDispatcher("DashMin/viewuserdetail.jsp").forward(request, response);
            return;
        }

        try {
            User u = new User(username, password, fullName, email, phoneNumber, address, 7);
            ud.Update(u);
            response.sendRedirect("user-detail"); // Redirect về trang xem lại thông tin
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi cập nhật thông tin.");
            request.getRequestDispatcher("DashMin/viewuserdetail.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet quản lý hiển thị và cập nhật thông tin user";
    }
}
