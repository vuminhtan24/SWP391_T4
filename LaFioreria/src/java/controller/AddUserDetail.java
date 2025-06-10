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

            // Check ID
            if (id <= 0) {
                setAttributes(request, id_raw, name_raw, password, fullName, email, phone_Number, Address, role_raw);
                request.setAttribute("errorID", "ID must be a natural number greater than 0.");
                request.setAttribute("roleNames", ud.getRoleNames());
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            // Phone validation
            if (!phone_Number.matches("^(0)\\d{9}$")) {
                setAttributes(request, id_raw, name_raw, password, fullName, email, phone_Number, Address, role_raw);
                request.setAttribute("errorPhone", "Phone number must be 10 digits and start with 0.");
                request.setAttribute("roleNames", ud.getRoleNames());
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            // Email validation
            if (!email.matches("^[a-zA-Z0-9._%+-]{3,}@[a-zA-Z0-9-]{2,}\\.[a-zA-Z]{2,}$")) {
                setAttributes(request, id_raw, name_raw, password, fullName, email, phone_Number, Address, role_raw);
                request.setAttribute("errorEmail", "Email Invalid, Email form ...@...com/vn/..");
                request.setAttribute("roleNames", ud.getRoleNames());
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            // Password strength
            if (password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$")) {
                passwordStrength = "Mạnh";
            } else if (password.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
                passwordStrength = "Trung bình";
            } else if (password.matches("^[a-zA-Z0-9]{7,}$")) {
                passwordStrength = "Yếu";
            } else {
                setAttributes(request, id_raw, name_raw, password, fullName, email, phone_Number, Address, role_raw);
                request.setAttribute("error", "Password không hợp lệ. Tối thiểu 7 ký tự.");
                request.setAttribute("roleNames", ud.getRoleNames());
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }
            request.setAttribute("passwordStrength", passwordStrength);

            // Full name validation
            if (!fullName.matches("^(?=.*[a-zA-Z])[a-zA-Z\\s]{4,}$")) {
                setAttributes(request, id_raw, name_raw, password, fullName, email, phone_Number, Address, role_raw);
                request.setAttribute("errorFullname", "Full name must be at least 4 characters and contain no digits.");
                request.setAttribute("roleNames", ud.getRoleNames());
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            // Name validation
            if (!name_raw.matches("^[a-zA-Z\\s]+$")) {
                setAttributes(request, id_raw, name_raw, password, fullName, email, phone_Number, Address, role_raw);
                request.setAttribute("errorName", "Name must not contain digits.");
                request.setAttribute("roleNames", ud.getRoleNames());
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            // Address validation
            if (!Address.matches("^[a-zA-Z0-9\\s]+$")) {
                setAttributes(request, id_raw, name_raw, password, fullName, email, phone_Number, Address, role_raw);
                request.setAttribute("errorAddress", "Address must contain only letters, digits, and spaces.");
                request.setAttribute("roleNames", ud.getRoleNames());
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            // Chuyển role từ text sang số
            int role = switch (role_raw) {
                case "Admin" ->
                    1;
                case "Sales Manager" ->
                    2;
                case "Seller" ->
                    3;
                case "Marketer" ->
                    4;
                case "Warehouse Staff" ->
                    5;
                case "Guest" ->
                    6;
                case "Customer" ->
                    7;
                default ->
                    0;
            };

            // Check ID tồn tại
            UserManager um = ud.getUserById(id);
            if (um == null) {
                User u = new User(id, name_raw, password, fullName, email, phone_Number, Address, role);
                ud.insertUser(u);
                response.sendRedirect("viewuserdetail");
            } else {
                setAttributes(request, id_raw, name_raw, password, fullName, email, phone_Number, Address, role_raw);
                request.setAttribute("error", id + " đã tồn tại.");
                request.setAttribute("roleNames", ud.getRoleNames());
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            setAttributes(request, id_raw, name_raw, password, fullName, email, phone_Number, Address, role_raw);
            request.setAttribute("error", "Invalid input data. Please try again.");
            request.setAttribute("roleNames", ud.getRoleNames());
            request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            setAttributes(request, id_raw, name_raw, password, fullName, email, phone_Number, Address, role_raw);
            request.setAttribute("error", "An unexpected error occurred. Please try again.");
            request.setAttribute("roleNames", ud.getRoleNames());
            request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
        }
    }

// ✅ Hàm hỗ trợ: giữ lại giá trị đã nhập
    private void setAttributes(HttpServletRequest request, String id, String name, String pass,
            String fullName, String email, String phone, String address, String role) {
        request.setAttribute("idValue", id);
        request.setAttribute("nameValue", name);
        request.setAttribute("passValue", pass);
        request.setAttribute("fullNameValue", fullName);
        request.setAttribute("emailValue", email);
        request.setAttribute("phoneValue", phone);
        request.setAttribute("addressValue", address);
        request.setAttribute("selectedRole", role);
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
