/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CustomerDAO;
import dal.EmployeeDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import model.CustomerInfo;
import model.EmployeeInfo;
import model.User;

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

        // GỌI ID MỚI
        int nextUserId = ud.getNextUserId();  // ➤ Hàm này bạn phải tự viết trong DAO

        request.setAttribute("roleNames", roleNames);
        request.setAttribute("idValue", nextUserId);  // ➤ Gửi ID sang JSP

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

        String name_raw = request.getParameter("name");
        String password = request.getParameter("pass");
        String passwordStrength = "";

        String fullName = request.getParameter("FullName");
        String email = request.getParameter("email");
        String phone_Number = request.getParameter("phone");
        String Address = request.getParameter("address");
        String role_raw = request.getParameter("option");

        UserDAO ud = new UserDAO();

        boolean hasError = false;

        if (!name_raw.matches("^(?=.{2,50}$)(?! )[a-zA-Z]+(?: [a-zA-Z]+)*$")) {
            request.setAttribute("errorName", "Name must not contain characters except letters and spaces, from 2 to 50 characters.");
            hasError = true;
        }

        final int MIN_LEN = 8;
        final int MAX_LEN = 32;

        if (password.length() < MIN_LEN || password.length() > MAX_LEN) {
            request.setAttribute("error", "Password must be from " + MIN_LEN + " to " + MAX_LEN + " characters.");
            hasError = true;
        } else {
            String strongRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>/?])[A-Za-z\\d!@#$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>/?]{" + MIN_LEN + "," + MAX_LEN + "}$";
            String mediumRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{" + MIN_LEN + "," + MAX_LEN + "}$";
            String weakRegex = "^[A-Za-z\\d]{7," + MAX_LEN + "}$";

            if (password.matches(strongRegex)) {
                passwordStrength = "Strong";
            } else if (password.matches(mediumRegex)) {
                passwordStrength = "Medium";
            } else if (password.matches(weakRegex)) {
                passwordStrength = "Weak";
            } else {
                request.setAttribute("error", "Password Invalid. Must contain letters, numbers (and special characters if needed) from " + MIN_LEN + " to " + MAX_LEN + " characters.");
                hasError = true;
            }
        }

        request.setAttribute("passwordStrength", passwordStrength);

        if (fullName == null || fullName.trim().isEmpty()) {
            request.setAttribute("errorFullname", "Full name is required.");
            hasError = true;
        } else if (!fullName.matches("^(?!\\s)(?!.*\\s{2,})(?=.{4,50}$)[A-Za-zÀ-ỹà-ỹ\\s]+(?<!\\s)$")) {
            request.setAttribute("errorFullname", "Full name must be 4-50 characters, only letters, no digits, no double spaces.");
            hasError = true;
        }

        final int MAX_EMAIL_LEN = 100;

        String trimmedEmail = email.trim();

        if (trimmedEmail.length() > MAX_EMAIL_LEN) {
            request.setAttribute("errorEmail", "Email is too long. Max 100 characters.");
            hasError = true;

        } else if (trimmedEmail.contains(" ")) {  // ⛔ Không chứa khoảng trắng
            request.setAttribute("errorEmail", "Email cannot contain spaces.");
            hasError = true;

        } else if (!trimmedEmail.matches("^[a-zA-Z0-9._%+-]{3,64}@[a-zA-Z0-9-]{2,253}\\.[a-zA-Z]{2,}$")) {
            request.setAttribute("errorEmail", "Email is invalid. Format: example@domain.com");
            hasError = true;
        }

        if (!phone_Number.matches("^(0)\\d{9}$")) {
            request.setAttribute("errorPhone", "Phone number must be 10 digits and start with 0.");
            hasError = true;
        } else if (ud.isPhoneExist(phone_Number)) {
            request.setAttribute("errorPhone", "Phone number already exists.");
            hasError = true;
        }

        final int MIN_ADDRESS_LEN = 5;
        final int MAX_ADDRESS_LEN = 150;

        String trimmedAddress = Address.trim();

        if (trimmedAddress.isEmpty()) {
            request.setAttribute("errorAddress", "Address cannot be empty or only spaces.");
            hasError = true;

        } else if (trimmedAddress.length() < MIN_ADDRESS_LEN || trimmedAddress.length() > MAX_ADDRESS_LEN) {
            request.setAttribute("errorAddress",
                    "Address must be between " + MIN_ADDRESS_LEN + " and " + MAX_ADDRESS_LEN + " characters.");
            hasError = true;

        } else if (trimmedAddress.contains("  ")) { // ⛔ chứa 2 khoảng trắng liên tiếp
            request.setAttribute("errorAddress", "Address cannot contain consecutive spaces.");
            hasError = true;

        } else if (!trimmedAddress.matches("^[a-zA-Z0-9\\s,./\\-À-ỹà-ỹ]+$")) {
            request.setAttribute("errorAddress",
                    "Address must contain only letters, digits, spaces, and some common punctuation.");
            hasError = true;
        }

        if (hasError) {
            setAttributes(request, name_raw, password, fullName, email, phone_Number, Address, role_raw);
            request.setAttribute("roleNames", ud.getRoleNames());
            request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
            return;
        }

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

        User u = new User(name_raw, password, fullName, email, phone_Number, Address, role);
        ud.insertNewUser(u);

        if ("Customer".equals(role_raw)) {
            String customerCode = request.getParameter("customerCode");
            String joinDate = request.getParameter("joinDate");
            String loyaltyPoint_raw = request.getParameter("loyaltyPoint");
            String birthday = request.getParameter("birthday");
            String gender = request.getParameter("gender");

            int loyaltyPoint = loyaltyPoint_raw != null && !loyaltyPoint_raw.isEmpty()
                    ? Integer.parseInt(loyaltyPoint_raw) : 0;
            CustomerDAO cid = new CustomerDAO();
            CustomerInfo ci = new CustomerInfo(cid.getLatestInsertedUserId(), customerCode, joinDate, loyaltyPoint, birthday, gender);
            cid.insert(ci);
            response.sendRedirect("viewuserdetail");

        } else {
            String employeeCode = request.getParameter("employeeCode");
            String contractType = request.getParameter("contractType");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String department = request.getParameter("department");
            String position = request.getParameter("position");

            LocalDate startDate = null;
            LocalDate endDate = null;

            if (startDateStr != null && !startDateStr.isEmpty()) {
                startDate = LocalDate.parse(startDateStr);  // ISO format yyyy-MM-dd
            }

            if (endDateStr != null && !endDateStr.isEmpty()) {
                endDate = LocalDate.parse(endDateStr);
            }

            EmployeeDAO eid = new EmployeeDAO();
            int newUserId = -1;
            try {
                newUserId = ud.getLatestInsertedUserId();
                if (newUserId == -1) {
                    throw new Exception("Failed to get latest inserted user ID.");
                }
            } catch (Exception e) { // Ghi log lỗi
                // Ghi log lỗi
                request.setAttribute("error", "Failed to retrieve user ID. Cannot continue.");
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }
// Sau khi đã có userId an toàn, tạo đối tượng EmployeeInfo
            EmployeeInfo ei = new EmployeeInfo(newUserId, employeeCode, contractType, startDate, endDate, department, position);
            eid.insert(ei);

        }
    }

    private void setAttributes(HttpServletRequest request, String name, String pass,
            String fullName, String email, String phone, String address, String role) {
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
