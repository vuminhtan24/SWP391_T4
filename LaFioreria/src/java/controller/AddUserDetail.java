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
import java.util.Random;
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
        int nextUserId = ud.getNextUserId();

        request.setAttribute("roleNames", roleNames);
        request.setAttribute("idValue", nextUserId);

        String employeeCode = generateEmployeeCode();
        request.setAttribute("employeeCode", employeeCode);
        String customerCode = generateCustomerCode();
        request.setAttribute("customerCode", customerCode);

        request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
    }

    private String generateEmployeeCode() {
        String prefix = "EMP";
        int randomNum = new Random().nextInt(90000) + 10000; // Tạo số từ 10000–99999
        return prefix + randomNum; // Ví dụ: EMP58392
    }

    private String generateCustomerCode() {
        String prefix = "CUST";
        int randomNum = new Random().nextInt(10000); // 0 đến 9999
        return prefix + String.format("%04d", randomNum); // zero-padded 4 số
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

        UserDAO ud = new UserDAO();

        // 🧩 B1. Lấy dữ liệu từ form
        String name = request.getParameter("name");
        String password = request.getParameter("pass");
        String fullName = request.getParameter("FullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String role_raw = request.getParameter("option");
        String currentRole = request.getParameter("currentRole");

        String finalRole = (role_raw != null && !role_raw.equals(currentRole)) ? role_raw : currentRole;

        // 🧩 B2. Validate từng nhóm
        boolean userError = validateUser(request, name, password, fullName, email, phone, address, ud);
        boolean customerError = false;
        boolean employeeError = false;

        System.out.println("=== SUBMIT ADD USER ===");
        System.out.println("Username: " + name);
        System.out.println("Role: " + finalRole);

        if ("Customer".equals(finalRole)) {
            customerError = validateCustomer(request);

            // Nếu không lỗi định dạng thì mới kiểm tra trùng
            if (!customerError && ud.isCustomerCodeExist(request.getParameter("customerCode"))) {
                request.setAttribute("errorCustomerCode", "This customer code already exists. Please use a different one.");
                customerError = true;
            }

        } else if (!"Guest".equals(finalRole)) {
            employeeError = validateEmployee(request);

            // Nếu không lỗi định dạng thì mới kiểm tra trùng
            if (!employeeError && ud.isEmployeeCodeExist(request.getParameter("employeeCode"))) {
                request.setAttribute("errorEmployeeCode", "This employee code already exists. Please use a different one.");
                employeeError = true;
            }
        } // Nếu validateUser không có lỗi, mới check username trùng
        if (!userError && ud.isUsernameExist(name)) {
            request.setAttribute("errorName", "This username is already taken. Please choose another one.");
            userError = true;
        }

        System.out.println("User validation error: " + userError);
        System.out.println("Customer validation error: " + customerError);
        System.out.println("Employee validation error: " + employeeError);
// B3. Nếu không có lỗi thì insert DB
        if (!userError && !customerError && !employeeError) {

            System.out.println(">>> All validation passed. Proceeding to insert...");
            // 1. Check lại nếu username đã tồn tại (do lần trước lỗi nhưng vẫn insert user)
            if (ud.isUsernameExist(name)) {
                System.out.println(">>> Username already exists in DB");
                request.setAttribute("errorName", "The login name already exists (maybe because the previous submission inserted a user).");
                setAttributes(request, name, password, fullName, email, phone, address, finalRole);
                request.setAttribute("roleNames", ud.getRoleNames());
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            // 2. Insert user → lấy ID
            User user = new User(name, password, fullName, email, phone, address, getRoleId(finalRole));
            int newUserId = ud.insertUserAndReturnId(user);

            System.out.println(">>> Inserted user ID: " + newUserId);

            if (newUserId == -1) {

                System.out.println(">>> Failed to insert user.");

                request.setAttribute("error", "Unable to add user to database.");
                setAttributes(request, name, password, fullName, email, phone, address, finalRole);
                request.setAttribute("roleNames", ud.getRoleNames());
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            // 3. Insert Customer
            if ("Customer".equals(finalRole)) {
                CustomerInfo ci = new CustomerInfo(
                        newUserId,
                        request.getParameter("customerCode"),
                        request.getParameter("joinDate"),
                        Integer.parseInt(request.getParameter("loyaltyPoint")),
                        request.getParameter("birthday"),
                        request.getParameter("gender")
                );
                new CustomerDAO().insert(ci);

                // 4. Insert Employee
            } else if (!"Guest".equals(finalRole)) {
                EmployeeInfo ei = new EmployeeInfo(
                        newUserId,
                        request.getParameter("employeeCode"),
                        request.getParameter("contractType"),
                        LocalDate.parse(request.getParameter("startDate")),
                        request.getParameter("endDate").isEmpty() ? null : LocalDate.parse(request.getParameter("endDate")),
                        request.getParameter("department"),
                        request.getParameter("position")
                );
                new EmployeeDAO().insert(ei);
            }

            response.sendRedirect("viewuserdetail");

        } else {
            System.out.println(">>> Validation failed. Re-displaying form.");
            // Giữ lại dữ liệu
            setAttributes(request, name, password, fullName, email, phone, address, finalRole);
            if ("Customer".equals(finalRole)) {
                request.setAttribute("customerCode", request.getParameter("customerCode"));
                request.setAttribute("joinDate", request.getParameter("joinDate"));
                request.setAttribute("loyaltyPoint", request.getParameter("loyaltyPoint"));
                request.setAttribute("birthday", request.getParameter("birthday"));
                request.setAttribute("gender", request.getParameter("gender"));
            } else if (!"Guest".equals(finalRole)) {
                request.setAttribute("employeeCode", request.getParameter("employeeCode"));
                request.setAttribute("contractType", request.getParameter("contractType"));
                request.setAttribute("startDate", request.getParameter("startDate"));
                request.setAttribute("endDate", request.getParameter("endDate"));
                request.setAttribute("department", request.getParameter("department"));
                request.setAttribute("position", request.getParameter("position"));
            }

            request.setAttribute("roleNames", ud.getRoleNames());
            request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
        }

        if (userError || customerError || employeeError) {
            System.out.println("There are validation errors:");
            request.getParameterMap().forEach((k, v) -> System.out.println(k + " = " + String.join(",", v)));
        }

    }

    private boolean validateUser(HttpServletRequest request, String name, String password,
            String fullName, String email, String phone, String address, UserDAO ud) {
        boolean hasError = false;

        System.out.println("=== START validateUser ===");
        System.out.println("Input - Username: " + name);
        System.out.println("Input - Password: " + password);
        System.out.println("Input - Full Name: " + fullName);
        System.out.println("Input - Email: " + email);
        System.out.println("Input - Phone: " + phone);
        System.out.println("Input - Address: " + address);

        // Validate Username
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("errorName", "Please enter a username.");
            System.out.println("❌ Username is empty");
            hasError = true;
        } else if (name.length() < 3 || name.length() > 30) {
            request.setAttribute("errorName", "Username must be between 3 and 30 characters.");
            System.out.println("❌ Username length invalid");
            hasError = true;
        } else if (!name.matches("^[a-zA-Z0-9._-]+$")) {
            request.setAttribute("errorName", "Username can only contain letters, numbers, dots (.), hyphens (-), and underscores (_).");
            System.out.println("❌ Username format invalid");
            hasError = true;
        }

        // Validate Password
        if (password == null || password.isEmpty()) {
            request.setAttribute("errorPassword", "Please enter a password.");
            System.out.println("❌ Password is empty");
            hasError = true;
        } else if (password.length() < 8 || password.length() > 32) {
            request.setAttribute("errorPassword", "Password must be between 8 and 32 characters.");
            System.out.println("❌ Password length invalid");
            hasError = true;
        } else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")) {
            request.setAttribute("errorPassword", "Password must include at least one uppercase letter, one lowercase letter, and one number.");
            System.out.println("❌ Password format invalid (missing upper/lower/number)");
            hasError = true;
        }

        // Validate Full Name
        if (fullName == null || fullName.trim().isEmpty()) {
            request.setAttribute("errorFullname", "Please enter your full name.");
            System.out.println("❌ Full name is empty");
            hasError = true;
        } else if (fullName.length() < 2 || fullName.length() > 50) {
            request.setAttribute("errorFullname", "Full name must be between 2 and 50 characters.");
            System.out.println("❌ Full name length invalid");
            hasError = true;
        } else if (!fullName.matches("^[a-zA-Z]+( [a-zA-Z]+)*$")) {
            request.setAttribute("errorFullname", "Full name can only contain letters and spaces (e.g., John Smith).");
            System.out.println("❌ Full name format invalid");
            hasError = true;
        }

        // Validate Email
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("errorEmail", "Please enter your email address.");
            System.out.println("❌ Email is empty");
            hasError = true;
        } else if (email.length() > 100) {
            request.setAttribute("errorEmail", "Email must not exceed 100 characters.");
            System.out.println("❌ Email too long");
            hasError = true;
        } else if (email.contains(" ") || !email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            request.setAttribute("errorEmail", "Please enter a valid email address (e.g., example@mail.com) without spaces.");
            System.out.println("❌ Email format invalid");
            hasError = true;
        }

        // Validate Phone
        if (phone == null || phone.trim().isEmpty()) {
            request.setAttribute("errorPhone", "Please enter your phone number.");
            System.out.println("❌ Phone is empty");
            hasError = true;
        } else if (!phone.matches("^0\\d{9}$")) {
            request.setAttribute("errorPhone", "Phone number must start with 0 and contain exactly 10 digits (e.g., 0901234567).");
            System.out.println("❌ Phone format invalid");
            hasError = true;
        } else if (ud.isPhoneExist(phone)) {
            request.setAttribute("errorPhone", "This phone number is already in use. Please use another number.");
            System.out.println("❌ Phone already exists in DB");
            hasError = true;
        }

        // Validate Address
        if (address == null || address.trim().isEmpty()) {
            request.setAttribute("errorAddress", "Please enter your address.");
            System.out.println("❌ Address is empty");
            hasError = true;
        } else if (address.length() < 5 || address.length() > 255) {
            request.setAttribute("errorAddress", "Address must be between 5 and 255 characters.");
            System.out.println("❌ Address length invalid");
            hasError = true;
        }

        System.out.println("✅ validateUser finished. hasError = " + hasError);
        return hasError;
    }

    private boolean validateCustomer(HttpServletRequest request) {
        boolean hasError = false;

        UserDAO ud = new UserDAO();
        String code = request.getParameter("customerCode");
        String joinDate = request.getParameter("joinDate");
        String loyaltyPointStr = request.getParameter("loyaltyPoint");
        String birthday = request.getParameter("birthday");
        String gender = request.getParameter("gender");

        // Validate Customer Code
        if (code == null || code.trim().isEmpty()) {
            request.setAttribute("errorCustomerCode", "Please enter a customer code.");
            hasError = true;
        } else if (!code.matches("^CUST\\d{4}$")) {
            request.setAttribute("errorCustomerCode", "Customer code must start with 'CUST' followed by 4 digits (e.g., CUST1234).");
            hasError = true;
        } else if (ud.isCustomerCodeExist(code)) { // Bạn cần tự viết hàm này trong DAO
            request.setAttribute("errorCustomerCode", "This customer code already exists. Please use a different one.");
            hasError = true;
        }

        // Validate Join Date
        if (joinDate == null || joinDate.trim().isEmpty()) {
            request.setAttribute("errorJoinDate", "Please enter the join date.");
            hasError = true;
        } else {
            try {
                LocalDate join = LocalDate.parse(joinDate);
                if (join.isAfter(LocalDate.now())) {
                    request.setAttribute("errorJoinDate", "Join date cannot be in the future.");
                    hasError = true;
                }
            } catch (Exception e) {
                request.setAttribute("errorJoinDate", "Invalid date format for join date. Please use YYYY-MM-DD.");
                hasError = true;
            }
        }

        // Validate Loyalty Point
        if (loyaltyPointStr == null || loyaltyPointStr.trim().isEmpty()) {
            request.setAttribute("errorLoyaltyPoint", "Please enter loyalty points.");
            hasError = true;
        } else {
            try {
                int point = Integer.parseInt(loyaltyPointStr);
                if (point < 0 || point > 100000) {
                    request.setAttribute("errorLoyaltyPoint", "Loyalty points must be between 0 and 100,000.");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorLoyaltyPoint", "Loyalty points must be a valid integer.");
                hasError = true;
            }
        }

        // Validate Birthday (optional, but must be valid if provided)
        if (birthday != null && !birthday.trim().isEmpty()) {
            try {
                LocalDate bday = LocalDate.parse(birthday);
                if (bday.isAfter(LocalDate.now().minusYears(10))) {
                    request.setAttribute("errorBirthday", "Customer must be at least 10 years old.");
                    hasError = true;
                }
            } catch (Exception e) {
                request.setAttribute("errorBirthday", "Invalid date format for birthday. Please use YYYY-MM-DD.");
                hasError = true;
            }
        }

        // Validate Gender
        if (gender == null || gender.trim().isEmpty()) {
            request.setAttribute("errorGender", "Please select a gender.");
            hasError = true;
        } else if (!(gender.equals("Male") || gender.equals("Female") || gender.equals("Other"))) {
            request.setAttribute("errorGender", "Invalid gender selected. Please choose Male, Female, or Other.");
            hasError = true;
        }

        return hasError;
    }

    private boolean validateEmployee(HttpServletRequest request) {
        boolean hasError = false;

        UserDAO ud = new UserDAO();
        String code = request.getParameter("employeeCode");
        String contractType = request.getParameter("contractType");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String department = request.getParameter("department");
        String position = request.getParameter("position");

        // Validate Employee Code
        if (code == null || code.trim().isEmpty()) {
            request.setAttribute("errorEmployeeCode", "Please enter an employee code.");
            hasError = true;
        } else if (!code.matches("^EMP\\d{4,10}$")) {
            request.setAttribute("errorEmployeeCode", "Employee code must start with 'EMP' followed by 4 to 10 digits (e.g., EMP1234).");
            hasError = true;
        } else if (ud.isEmployeeCodeExist(code)) {
            request.setAttribute("errorEmployeeCode", "This employee code already exists. Please use a different one.");
            hasError = true;
        }

        // Validate Contract Type
        if (contractType == null || contractType.trim().isEmpty()) {
            request.setAttribute("errorContractType", "Please select a contract type.");
            hasError = true;
        } else if (!contractType.matches("^(Full-time|Part-time|Freelance|Contract)$")) {
            request.setAttribute("errorContractType", "Invalid contract type. Please select from: Full-time, Part-time, Freelance, or Contract.");
            hasError = true;
        }

        // Validate Start Date
        if (startDateStr == null || startDateStr.trim().isEmpty()) {
            request.setAttribute("errorStartDate", "Please enter a start date.");
            hasError = true;
        } else {
            try {
                LocalDate start = LocalDate.parse(startDateStr);
                if (start.isAfter(LocalDate.now().plusDays(1))) {
                    request.setAttribute("errorStartDate", "Start date cannot be in the future.");
                    hasError = true;
                }
            } catch (Exception e) {
                request.setAttribute("errorStartDate", "Invalid start date format. Please use YYYY-MM-DD.");
                hasError = true;
            }
        }

        // Validate End Date (optional)
        if (endDateStr != null && !endDateStr.trim().isEmpty()) {
            try {
                LocalDate start = LocalDate.parse(startDateStr);
                LocalDate end = LocalDate.parse(endDateStr);
                if (end.isBefore(start)) {
                    request.setAttribute("errorEndDate", "End date must be after the start date.");
                    hasError = true;
                }
            } catch (Exception e) {
                request.setAttribute("errorEndDate", "Invalid end date format. Please use YYYY-MM-DD.");
                hasError = true;
            }
        }

        // Validate Department
        if (department == null || department.trim().isEmpty()) {
            request.setAttribute("errorDepartment", "Please enter the department.");
            hasError = true;
        } else if (department.length() < 2 || department.length() > 50) {
            request.setAttribute("errorDepartment", "Department name must be between 2 and 50 characters.");
            hasError = true;
        }

        // Validate Position
        if (position == null || position.trim().isEmpty()) {
            request.setAttribute("errorPosition", "Please enter the position.");
            hasError = true;
        } else if (position.length() < 2 || position.length() > 50) {
            request.setAttribute("errorPosition", "Position name must be between 2 and 50 characters.");
            hasError = true;
        }

        return hasError;
    }

    public int getRoleId(String roleName) {
        return switch (roleName) {
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
                0; // hoặc -1 nếu muốn báo lỗi khi không hợp lệ
        };
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
