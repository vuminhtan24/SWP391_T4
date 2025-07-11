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

        if (ud.isUsernameExist(name_raw)) {
            request.setAttribute("errorName", "Username already exists.");
            hasError = true;
        }

        final int MIN_LEN = 8;
        final int MAX_LEN = 32;

        if (password.length() < MIN_LEN || password.length() > MAX_LEN) {
            request.setAttribute("error", "Password must be from " + MIN_LEN + " to " + MAX_LEN + " characters.");
            hasError = true;
        } else {
            String strongRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&+=]).{8,32}$";
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

// ✅ Lấy currentRole ẩn từ form (giá trị ban đầu)
        String currentRole = request.getParameter("currentRole");

// ✅ Nếu người dùng không đổi role thì giữ nguyên role ban đầu
        String finalRole = (role_raw != null && !role_raw.equals(currentRole)) ? role_raw : currentRole;

// ✅ Mapping role name → role ID
        int role = switch (finalRole) {
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

        if (hasError) {
            setAttributes(request, name_raw, password, fullName, email, phone_Number, Address, role_raw);

            // ✅ Giữ lại thông tin Customer
            if ("Customer".equals(finalRole)) {
                request.setAttribute("customerCode", request.getParameter("customerCode"));
                request.setAttribute("joinDate", request.getParameter("joinDate"));
                request.setAttribute("loyaltyPoint", request.getParameter("loyaltyPoint"));
                request.setAttribute("birthday", request.getParameter("birthday"));
                request.setAttribute("gender", request.getParameter("gender"));
            } else {
                // ✅ Giữ lại thông tin Employee
                request.setAttribute("employeeCode", request.getParameter("employeeCode"));
                request.setAttribute("contractType", request.getParameter("contractType"));
                request.setAttribute("startDate", request.getParameter("startDate"));
                request.setAttribute("endDate", request.getParameter("endDate"));
                request.setAttribute("department", request.getParameter("department"));
                request.setAttribute("position", request.getParameter("position"));
            }

            request.setAttribute("roleNames", ud.getRoleNames());
            request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
            return;
        }

        User u = new User(name_raw, password, fullName, email, phone_Number, Address, role);
        int newUserId = ud.insertUserAndReturnId(u);
        if (newUserId == -1) {
            request.setAttribute("error", "Không thể thêm user vào database.");
            request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
            return;
        }

        if ("Customer".equals(finalRole)) {
            String customerCode = request.getParameter("customerCode");
            String joinDate = request.getParameter("joinDate");
            String loyaltyPoint_raw = request.getParameter("loyaltyPoint");
            String birthday = request.getParameter("birthday");
            String gender = request.getParameter("gender");

            boolean customerError = false;

            if (customerCode == null || !customerCode.matches("^CUST\\d{4,10}$")) {
                request.setAttribute("errorCustomerCode", "Customer code must start with 'CUST' followed by 4–10 digits (e.g., CUST1001).");
                customerError = true;
            }

            if (joinDate == null || joinDate.isEmpty()) {
                request.setAttribute("errorJoinDate", "Join date is required.");
                customerError = true;
            } else {
                LocalDate join = LocalDate.parse(joinDate);
                if (join.isAfter(LocalDate.now())) {
                    request.setAttribute("errorJoinDate", "Join date cannot be in the future.");
                    customerError = true;
                }
            }

            int loyaltyPoint = 0;
            if (loyaltyPoint_raw != null && !loyaltyPoint_raw.isEmpty()) {
                try {
                    loyaltyPoint = Integer.parseInt(loyaltyPoint_raw);
                    if (loyaltyPoint < 0 || loyaltyPoint > 100000) {
                        request.setAttribute("errorLoyaltyPoint", "Loyalty point must be between 0 and 100000.");
                        customerError = true;
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("errorLoyaltyPoint", "Loyalty point must be a valid number.");
                    customerError = true;
                }
            }

            if (birthday != null && !birthday.isEmpty()) {
                try {
                    LocalDate birthDate = LocalDate.parse(birthday);
                    if (birthDate.isAfter(LocalDate.now().minusYears(10))) {
                        request.setAttribute("errorBirthday", "Customer must be at least 10 years old.");
                        customerError = true;
                    }
                } catch (Exception e) {
                    request.setAttribute("errorBirthday", "Birthday format must be yyyy-MM-dd.");
                    customerError = true;
                }
            }

            if (gender == null || !(gender.equals("Male") || gender.equals("Female") || gender.equals("Other"))) {
                request.setAttribute("errorGender", "Gender must be selected.");
                customerError = true;
            }

            if (customerError) {
                // Giữ lại dữ liệu đã nhập
                setAttributes(request, name_raw, password, fullName, email, phone_Number, Address, role_raw);
                request.setAttribute("customerCode", customerCode);
                request.setAttribute("joinDate", joinDate);
                request.setAttribute("loyaltyPoint", loyaltyPoint_raw);
                request.setAttribute("birthday", birthday);
                request.setAttribute("gender", gender);
                request.setAttribute("roleNames", ud.getRoleNames());

                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            // Sau khi kiểm tra xong mới insert vào DB
            CustomerDAO cid = new CustomerDAO();
            CustomerInfo ci = new CustomerInfo(newUserId, customerCode, joinDate, loyaltyPoint, birthday, gender);
            cid.insert(ci);
            response.sendRedirect("viewuserdetail");
        } else {
            String employeeCode = request.getParameter("employeeCode");
            String contractType = request.getParameter("contractType");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String department = request.getParameter("department");
            String position = request.getParameter("position");

            boolean employeeError = false;
            LocalDate startDate = null;
            LocalDate endDate = null;

            if (employeeCode == null || !employeeCode.matches("^EMP\\d{4,10}$")) {
                request.setAttribute("errorEmployeeCode", "Employee code must start with 'EMP' followed by 4–10 digits (e.g., EMP1001).");
                employeeError = true;
            }

            if (contractType == null || contractType.trim().isEmpty()) {
                request.setAttribute("errorContractType", "Contract type is required.");
                employeeError = true;
            } else if (!contractType.matches("^(Full-time|Part-time|Freelance|Contract)$")) {
                request.setAttribute("errorContractType", "Contract type must be one of: Full-time, Part-time, Freelance, Contract.");
                employeeError = true;
            }

            if (startDateStr == null || startDateStr.isEmpty()) {
                request.setAttribute("errorStartDate", "Start date is required.");
                employeeError = true;
            } else {
                try {
                    startDate = LocalDate.parse(startDateStr);
                    if (startDate.isAfter(LocalDate.now().plusDays(1))) {
                        request.setAttribute("errorStartDate", "Start date cannot be in the future.");
                        employeeError = true;
                    }
                } catch (Exception e) {
                    request.setAttribute("errorStartDate", "Invalid start date format (yyyy-MM-dd expected).");
                    employeeError = true;
                }
            }

            if (endDateStr != null && !endDateStr.isEmpty()) {
                try {
                    endDate = LocalDate.parse(endDateStr);
                    if (startDate != null && endDate.isBefore(startDate)) {
                        request.setAttribute("errorEndDate", "End date cannot be before start date.");
                        employeeError = true;
                    }
                } catch (Exception e) {
                    request.setAttribute("errorEndDate", "Invalid end date format (yyyy-MM-dd expected).");
                    employeeError = true;
                }
            }

            if (department == null || department.trim().length() < 2 || department.length() > 50) {
                request.setAttribute("errorDepartment", "Department must be 2–50 characters.");
                employeeError = true;
            }

            if (position == null || position.trim().length() < 2 || position.length() > 50) {
                request.setAttribute("errorPosition", "Position must be 2–50 characters.");
                employeeError = true;
            }

            if (employeeError) {
                setAttributes(request, name_raw, password, fullName, email, phone_Number, Address, role_raw);

                // Giữ lại dữ liệu đã nhập
                request.setAttribute("employeeCode", employeeCode);
                request.setAttribute("contractType", contractType);
                request.setAttribute("startDate", startDateStr);
                request.setAttribute("endDate", endDateStr);
                request.setAttribute("department", department);
                request.setAttribute("position", position);
                request.setAttribute("roleNames", ud.getRoleNames());

                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            // Sau khi hợp lệ, tiến hành insert
            EmployeeDAO eid = new EmployeeDAO();
            EmployeeInfo ei = new EmployeeInfo(newUserId, employeeCode, contractType, startDate, endDate, department, position);
            eid.insert(ei);
            response.sendRedirect("viewuserdetail");
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
