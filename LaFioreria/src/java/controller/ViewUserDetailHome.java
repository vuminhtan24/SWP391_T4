/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import model.User;
import model.UserManager;

/**
 *
 * @author LAPTOP
 */
@WebServlet(name = "ViewUserDetailHome", urlPatterns = {"/viewuserdetailhome"})
public class ViewUserDetailHome extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewUserDetailHome</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewUserDetailHome at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentAcc") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy thông tin user từ session
        User currentUser = (User) session.getAttribute("currentAcc");

        // Nếu bạn cần lấy thông tin mới nhất từ DB (tùy ý)
        UserDAO ud = new UserDAO();
        User um = ud.getUserByID(currentUser.getUserid());  // hoặc currentUser.getId() tùy theo class bạn

        request.setAttribute("userManager", um);
        request.getRequestDispatcher("ZeShopper/userDetail.jsp").forward(request, response);
    }


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)


/**
 * Handles the HTTP <code>POST</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {
        String id_raw = request.getParameter("id");
        String name_raw = request.getParameter("name");
        String password = request.getParameter("pass");
        String fullName = request.getParameter("FullName");
        String email = request.getParameter("email");
        String phone_Number = request.getParameter("phone");

        String address = request.getParameter("address");

        UserDAO ud = new UserDAO();
        HttpSession session = request.getSession();

        try {
            int id = Integer.parseInt(id_raw);
            boolean hasError = false;

            if (!phone_Number.matches("^(090|098)\\d{7}$")) {
                request.setAttribute("errorPhone", "Phone must be 10 digits and start with 090 or 098.");
                hasError = true;
            }

            if (!email.matches("^[a-zA-Z0-9._%+-]{3,}@flower\\.com$")) {
                request.setAttribute("errorEmail", "Email must be at least 3 characters before @flower.com.");
                hasError = true;
            }

            String passwordStrength = "";
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

            if (!fullName.matches("^[a-zA-Z\\s]{4,}$")) {
                request.setAttribute("errorFullname", "Full name must be at least 4 characters and contain no digits.");
                hasError = true;
            }

            if (!name_raw.matches("^[a-zA-Z\\s]+$")) {
                request.setAttribute("errorName", "Name must not contain digits.");
                hasError = true;
            }

            if (!address.matches("^[a-zA-Z0-9\\s]+$")) {
                request.setAttribute("errorAddress", "Address must contain only letters, digits, and spaces.");
                hasError = true;
            }

            // Nếu có lỗi → giữ lại dữ liệu & forward lại
            if (hasError) {
                User user = new User(id, name_raw, password, fullName, email, phone_Number, address, 7);
                request.setAttribute("userManager", user); // giữ lại dữ liệu đã nhập
                request.getRequestDispatcher("ZeShopper/userDetail.jsp").forward(request, response);
                return;
            }

            // Nếu không có lỗi → cập nhật
            User u = new User(id, name_raw, password, fullName, email, phone_Number, address, 7);
            ud.Update(u);

            // Cập nhật lại session nếu đang đăng nhập với user đó
            User current = (User) session.getAttribute("currentAcc");
            if (current != null && current.getUserid() == u.getUserid()) {
                session.setAttribute("currentAcc", u);
            }

            // Gửi thông báo thành công
            session.setAttribute("updateSuccess", "Cập nhật thông tin thành công!");
            response.sendRedirect("viewuserdetailhome");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu không hợp lệ.");
            request.getRequestDispatcher("ZeShopper/userDetail.jsp").forward(request, response);

        String Address = request.getParameter("address");

        UserDAO ud = new UserDAO();

        try {
            int id = Integer.parseInt(id_raw);
            UserManager um = new UserManager(id, name_raw, password, fullName, email, phone_Number, Address, email);

        } catch (Exception e) {

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override

public String getServletInfo() {

        return "Short description";
    }// </editor-fold>

}
