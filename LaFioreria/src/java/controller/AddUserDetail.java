/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.User;
import model.UserManager;

/**
 *
 * @author LAPTOP
 */
public class AddUserDetail extends HttpServlet {

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
            out.println("<title>Servlet AddUserDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddUserDetail at " + request.getContextPath() + "</h1>");
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

        UserDAO ud = new UserDAO();
        List<String> roleNames = ud.getRoleNames();
        request.setAttribute("roleNames", roleNames);
        request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);

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
            throws ServletException, IOException {
        String id_raw = request.getParameter("id");
        String name_raw = request.getParameter("name");
        String password = request.getParameter("pass");
        String passwordStrength = "";

        String fullName = request.getParameter("FullName");
        String email = request.getParameter("email");
        String phone_Number = request.getParameter("phone");
        String Address = request.getParameter("address");
        String role_raw = request.getParameter("option");

        UserDAO ud = new UserDAO();

        try {
            int id = Integer.parseInt(id_raw);

            if (id <= 0) {
                List<String> roleNames = ud.getRoleNames();
                request.setAttribute("roleNames", roleNames);

                request.setAttribute("errorID", "ID must be a natural number greater than 0.");
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            if (!phone_Number.matches("^(090|098)\\d{7}$")) {
                List<String> roleNames = ud.getRoleNames();
                request.setAttribute("roleNames", roleNames);

                request.setAttribute("errorPhone", "Phone number must be 10 digits and start with 090 or 098.");
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            if (!email.matches("^[a-zA-Z0-9._%+-]{3,}@flower\\.com$")) {
                List<String> roleNames = ud.getRoleNames();
                request.setAttribute("roleNames", roleNames);

                request.setAttribute("errorEmail", "Email must be at least 3 characters before @flower.com");
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            if (password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$")) {
                passwordStrength = "Mạnh";
                request.setAttribute("passwordStrength", passwordStrength);
            } else if (password.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
                passwordStrength = "Trung bình";
                request.setAttribute("passwordStrength", passwordStrength);
            } else if (password.matches("^[a-zA-Z0-9]{7,}$")) {
                passwordStrength = "Yếu";
                request.setAttribute("passwordStrength", passwordStrength);
            } else {
                List<String> roleNames = ud.getRoleNames();
                request.setAttribute("roleNames", roleNames);
                request.setAttribute("error", "Password không hợp lệ. Tối thiểu 7 ký tự.");
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            if (!fullName.matches("^[a-zA-Z\\s]{4,}$")) {
                List<String> roleNames = ud.getRoleNames();
                request.setAttribute("roleNames", roleNames);

                request.setAttribute("errorFullname", "Full name must be at least 4 characters and contain no digits.");
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }
            if (!name_raw.matches("^[a-zA-Z\\s]+$")) {
                List<String> roleNames = ud.getRoleNames();
                request.setAttribute("roleNames", roleNames);
                request.setAttribute("errorName", "Name must not contain digits.");
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }
            if (!Address.matches("^[a-zA-Z0-9\\s]+$")) {
                List<String> roleNames = ud.getRoleNames();
                request.setAttribute("roleNames", roleNames);
                request.setAttribute("errorAddress", "Address must contain only letters, digits, and spaces.");
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            int role = 0;
            switch (role_raw) {
                case "Admin" ->
                    role = 1;
                case "Sales Manager" ->
                    role = 2;
                case "Seller" ->
                    role = 3;
                case "Marketer" ->
                    role = 4;
                case "Warehouse Staff" ->
                    role = 5;
                case "Guest" ->
                    role = 6;
                case "Customer" ->
                    role = 7;

            }

            UserManager um = ud.getUserById(id);

            if (um == null) {
                User u = new User(id, name_raw, password, fullName, email, phone_Number, Address, role);
                ud.insertUser(u);
                response.sendRedirect("viewuserdetail");

            } else {
                request.setAttribute("error", id + " exited");

                List<String> roleNames = ud.getRoleNames();
                request.setAttribute("roleNames", roleNames);

                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            // Ghi log nếu cần
            System.err.println("Lỗi định dạng ID: " + e.getMessage());

            // Gửi thông báo lỗi chung cho giao diện (nếu muốn)
            request.setAttribute("error", "Invalid input data. Please try again.");

            // Gửi lại roleNames để hiển thị lại form
            List<String> roleNames = ud.getRoleNames();
            request.setAttribute("roleNames", roleNames);

            // Quay lại form
            request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
        } catch (ServletException | IOException e) { // Ghi log nội bộ (hoặc dùng Logger nếu có)
            // 👈 chỉ nên dùng khi debug, không để trong production
            // 👈 chỉ nên dùng khi debug, không để trong production

            // Thông báo lỗi tổng quát
            request.setAttribute("error", "An unexpected error occurred. Please try again.");

            // Truyền lại roleNames nếu muốn giữ lại form
            List<String> roleNames = ud.getRoleNames();
            request.setAttribute("roleNames", roleNames);

            // Quay lại form
            request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
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
