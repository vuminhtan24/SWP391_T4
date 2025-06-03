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
public class ViewUserDetail extends HttpServlet {

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
            out.println("<title>Servlet ViewUserDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewUserDetail at " + request.getContextPath() + "</h1>");
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
        List<Integer> userIds = ud.getIds();
        request.setAttribute("userIds", userIds);

        List<String> roleNames = ud.getRoleNames();
        request.setAttribute("roleNames", roleNames);

        String id_raw = request.getParameter("id");
        String search_raw = request.getParameter("txtSearch");
        request.setAttribute("kw", search_raw);
        if (id_raw != null && !id_raw.isEmpty()) {
            try {
                int id = Integer.parseInt(id_raw);
                UserManager um = ud.getUserById(id);
                request.setAttribute("userManager", um);
            } catch (NumberFormatException e) {
                System.out.println(e);
            }

        }

        if (search_raw != null && !search_raw.isEmpty()) {
            try {
                int id = Integer.parseInt(search_raw);
                UserManager um = ud.getUserById(id);
                request.setAttribute("userManager", um);
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
        }

        request.getRequestDispatcher("DashMin/viewuserdetail.jsp").forward(request, response);
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
        String fullName = request.getParameter("FullName");
        String email = request.getParameter("email");
        String phone_Number = request.getParameter("phone");
        String Address = request.getParameter("address");
        String role_raw = request.getParameter("option");

        UserDAO ud = new UserDAO();

        try {
            int id = Integer.parseInt(id_raw);

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

            boolean hasError = false;

            if (id <= 0) {
                request.setAttribute("errorID", "ID must be a natural number greater than 0.");
                hasError = true;
            }

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

            if (!Address.matches("^[a-zA-Z0-9\\s]+$")) {
                request.setAttribute("errorAddress", "Address must contain only letters, digits, and spaces.");
                hasError = true;
            }

            // Nếu có lỗi thì forward về lại trang edituserdetail.jsp
            if (hasError) {
                // Gán lại userManager để giữ dữ liệu form khi có lỗi
                UserManager um = new UserManager();
                um.setUserid(Integer.parseInt(id_raw));
                um.setUsername(name_raw);
                um.setPassword(password);
                um.setFullname(fullName);
                um.setEmail(email);
                um.setPhone(phone_Number);
                um.setAddress(Address);
                um.setRole(role_raw); // Nếu role_raw là String, hoặc bạn có thể chuyển sang int như trước

                request.setAttribute("userManager", um);  // Đây là bước quan trọng

                // Truyền lại roleNames để dropdown hiển thị
                List<String> roleNames = ud.getRoleNames();
                request.setAttribute("roleNames", roleNames);

                // Gửi lại trang với thông tin lỗi và dữ liệu form đã nhập
                request.getRequestDispatcher("DashMin/viewuserdetail.jsp").forward(request, response);
                return;
            }

            // Không có lỗi thì update
            User u = new User(id, name_raw, password, fullName, email, phone_Number, Address, role);
            ud.Update(u);
            response.sendRedirect("viewuserdetail");

        } catch (NumberFormatException e) {
            System.out.println("Lỗi định dạng ID: " + e.getMessage());

            request.setAttribute("error", "Invalid input format.");

            List<String> roleNames = ud.getRoleNames();
            request.setAttribute("roleNames", roleNames);

            request.getRequestDispatcher("DashMin/viewuserdetail.jsp").forward(request, response);
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
