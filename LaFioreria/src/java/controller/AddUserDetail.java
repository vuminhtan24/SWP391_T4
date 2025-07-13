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

        if ("Customer".equals(finalRole)) {
            customerError = validateCustomer(request);
        } else if (!"Guest".equals(finalRole)) {
            employeeError = validateEmployee(request);
        }

// B3. Nếu không có lỗi thì insert DB
        if (!userError && !customerError && !employeeError) {

            // 1. Check lại nếu username đã tồn tại (do lần trước lỗi nhưng vẫn insert user)
            if (ud.isUsernameExist(name)) {
                request.setAttribute("errorName", "Tên đăng nhập đã tồn tại (có thể do lần gửi trước đã insert user).");
                setAttributes(request, name, password, fullName, email, phone, address, finalRole);
                request.setAttribute("roleNames", ud.getRoleNames());
                request.getRequestDispatcher("DashMin/addnewuserdetail.jsp").forward(request, response);
                return;
            }

            // 2. Insert user → lấy ID
            User user = new User(name, password, fullName, email, phone, address, getRoleId(finalRole));
            int newUserId = ud.insertUserAndReturnId(user);

            if (newUserId == -1) {
                request.setAttribute("error", "Không thể thêm user vào CSDL.");
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

    }

    private boolean validateUser(HttpServletRequest request, String name, String password,
            String fullName, String email, String phone, String address, UserDAO ud) {
        boolean hasError = false;

        if (!name.matches("^(?=.{2,50}$)(?! )[a-zA-Z]+(?: [a-zA-Z]+)*$")) {
            request.setAttribute("errorName", "Tên không hợp lệ.");
            hasError = true;
        } else if (ud.isUsernameExist(name)) {
            request.setAttribute("errorName", "Tên đăng nhập đã tồn tại.");
            hasError = true;
        }

        if (password.length() < 8 || password.length() > 32) {
            request.setAttribute("error", "Mật khẩu phải từ 8-32 ký tự.");
            hasError = true;
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            request.setAttribute("errorFullname", "Họ tên bắt buộc.");
            hasError = true;
        }

        if (email == null || email.trim().length() > 100 || email.contains(" ")
                || !email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            request.setAttribute("errorEmail", "Email không hợp lệ.");
            hasError = true;
        }

        if (!phone.matches("^(0)\\d{9}$") || ud.isPhoneExist(phone)) {
            request.setAttribute("errorPhone", "Số điện thoại không hợp lệ hoặc đã tồn tại.");
            hasError = true;
        }

        if (address == null || address.trim().length() < 5) {
            request.setAttribute("errorAddress", "Địa chỉ không hợp lệ.");
            hasError = true;
        }

        return hasError;
    }

    private boolean validateCustomer(HttpServletRequest request) {
        boolean hasError = false;

        String code = request.getParameter("customerCode");
        String joinDate = request.getParameter("joinDate");
        String loyaltyPointStr = request.getParameter("loyaltyPoint");
        String birthday = request.getParameter("birthday");
        String gender = request.getParameter("gender");

        if (code == null || !code.matches("^CUST\\d{4,10}$")) {
            request.setAttribute("errorCustomerCode", "Mã khách hàng bắt đầu bằng 'CUST' theo sau là 4-10 chữ số.");
            hasError = true;
        }

        if (joinDate == null || joinDate.isEmpty()) {
            request.setAttribute("errorJoinDate", "Ngày tham gia không được bỏ trống.");
            hasError = true;
        } else {
            try {
                LocalDate join = LocalDate.parse(joinDate);
                if (join.isAfter(LocalDate.now())) {
                    request.setAttribute("errorJoinDate", "Ngày tham gia không được ở tương lai.");
                    hasError = true;
                }
            } catch (Exception e) {
                request.setAttribute("errorJoinDate", "Định dạng ngày tham gia không hợp lệ.");
                hasError = true;
            }
        }

        try {
            int point = Integer.parseInt(loyaltyPointStr);
            if (point < 0 || point > 100000) {
                request.setAttribute("errorLoyaltyPoint", "Điểm phải từ 0 đến 100000.");
                hasError = true;
            }
        } catch (Exception e) {
            request.setAttribute("errorLoyaltyPoint", "Điểm phải là số.");
            hasError = true;
        }

        if (birthday != null && !birthday.isEmpty()) {
            try {
                LocalDate bday = LocalDate.parse(birthday);
                if (bday.isAfter(LocalDate.now().minusYears(10))) {
                    request.setAttribute("errorBirthday", "Phải ít nhất 10 tuổi.");
                    hasError = true;
                }
            } catch (Exception e) {
                request.setAttribute("errorBirthday", "Ngày sinh không hợp lệ.");
                hasError = true;
            }
        }

        if (gender == null || !(gender.equals("Male") || gender.equals("Female") || gender.equals("Other"))) {
            request.setAttribute("errorGender", "Vui lòng chọn giới tính.");
            hasError = true;
        }

        return hasError;
    }

    private boolean validateEmployee(HttpServletRequest request) {
        boolean hasError = false;

        String code = request.getParameter("employeeCode");
        String contractType = request.getParameter("contractType");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String department = request.getParameter("department");
        String position = request.getParameter("position");

        if (code == null || !code.matches("^EMP\\d{4,10}$")) {
            request.setAttribute("errorEmployeeCode", "Mã nhân viên phải bắt đầu bằng 'EMP' theo sau là 4-10 chữ số.");
            hasError = true;
        }

        if (contractType == null || !contractType.matches("^(Full-time|Part-time|Freelance|Contract)$")) {
            request.setAttribute("errorContractType", "Loại hợp đồng không hợp lệ.");
            hasError = true;
        }

        try {
            LocalDate start = LocalDate.parse(startDateStr);
            if (start.isAfter(LocalDate.now().plusDays(1))) {
                request.setAttribute("errorStartDate", "Ngày bắt đầu không được ở tương lai.");
                hasError = true;
            }
        } catch (Exception e) {
            request.setAttribute("errorStartDate", "Ngày bắt đầu không hợp lệ.");
            hasError = true;
        }

        if (endDateStr != null && !endDateStr.isEmpty()) {
            try {
                LocalDate end = LocalDate.parse(endDateStr);
                LocalDate start = LocalDate.parse(startDateStr);
                if (end.isBefore(start)) {
                    request.setAttribute("errorEndDate", "Ngày kết thúc phải sau ngày bắt đầu.");
                    hasError = true;
                }
            } catch (Exception e) {
                request.setAttribute("errorEndDate", "Ngày kết thúc không hợp lệ.");
                hasError = true;
            }
        }

        if (department == null || department.trim().length() < 2 || department.length() > 50) {
            request.setAttribute("errorDepartment", "Phòng ban phải từ 2–50 ký tự.");
            hasError = true;
        }

        if (position == null || position.trim().length() < 2 || position.length() > 50) {
            request.setAttribute("errorPosition", "Chức vụ phải từ 2–50 ký tự.");
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
