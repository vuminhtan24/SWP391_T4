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
import java.time.format.DateTimeParseException;
import java.util.List;
import model.CustomerInfo;
import model.EmployeeInfo;
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

                int roleId = ud.getRoleIdByUserId(id); // hoặc um.getRoleId()
                if (roleId == 7) { // Customer
                    CustomerDAO cd = new CustomerDAO();
                    CustomerInfo ci = cd.getByUserId(id);
                    request.setAttribute("customerInfo", ci);
                } else if (roleId >= 1 && roleId <= 6 || roleId == 8) { // Nhân viên
                    EmployeeDAO ed = new EmployeeDAO();
                    EmployeeInfo ei = ed.getByUserId(id);
                    request.setAttribute("employeeInfo", ei);
                }
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

        String action = request.getParameter("ud");
        System.out.println("ACTION RECEIVED = " + action);

        if (action == null || !action.equals("UPDATE")) {
            request.setAttribute("error", "Invalid action.");
            request.getRequestDispatcher("DashMin/viewuserdetail.jsp").forward(request, response);
            return;
        }

        // Lấy thông tin chung
        String id_raw = request.getParameter("id");
        String name_raw = request.getParameter("name");
        String password = request.getParameter("pass");
        String fullName = request.getParameter("FullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String role_raw = request.getParameter("option");

        // Thông tin phụ
        String customerCode = request.getParameter("customerCode");
        String joinDate = request.getParameter("joinDate");
        String loyaltyPoint_raw = request.getParameter("loyaltyPoint");
        String birthday = request.getParameter("birthday");
        String gender = request.getParameter("gender");
        String employeeCode = request.getParameter("employeeCode");
        String contractType = request.getParameter("contractType");
        String startDate_raw = request.getParameter("startDate");
        String endDate_raw = request.getParameter("endDate");
        String department = request.getParameter("department");
        String position = request.getParameter("position");

        UserDAO ud = new UserDAO();

        try {
            int id = Integer.parseInt(id_raw);
            int role = 0;
            if (role_raw != null) {
                role = switch (role_raw) {
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
            }

            boolean hasError = false;

            if (id <= 0) {
                request.setAttribute("errorID", "ID must be greater than 0.");
                hasError = true;
            }

            if (name_raw == null || !name_raw.matches("^[a-zA-Z0-9]{4,20}$")) {
                request.setAttribute("errorName", "Name must be 4-20 alphanumeric characters.");
                hasError = true;
            }

            String passwordStrength = "";
            if (password == null || !password.matches("^\\S{8,70}$")) {
                request.setAttribute("errorPass", "Password must be 8-20 characters long and no spaces.");
                hasError = true;
            } else {
                if (password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,20}$")) {
                    passwordStrength = "Strong";
                } else if (password.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$")) {
                    passwordStrength = "Medium";
                } else {
                    passwordStrength = "Weak";
                }
            }
            request.setAttribute("passwordStrength", passwordStrength);

            if (fullName == null || !fullName.matches("^(?!.*\\s{2})(?!\\s)(?!.*\\s$)[a-zA-ZÀ-ỹ\\s]{4,50}$")) {
                request.setAttribute("errorFullname", "Full name must be 4-50 letters only, no double spaces.");
                hasError = true;
            }

            if (email == null || !email.matches("^(?=.{6,50}$)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$")) {
                request.setAttribute("errorEmail", "Invalid email format.");
                hasError = true;
            }

            if (phone == null || !phone.matches("^(03|05|07|08|09)\\d{8}$")) {
                request.setAttribute("errorPhone", "Phone must be 10 digits starting with 03, 05, 07, 08, or 09.");
                hasError = true;
            }

            if (address == null || !address.matches("^(?!\\s)(?!.*\\s{2,})[a-zA-ZÀ-ỹ0-9,./\\-\\s]{5,100}(?<!\\s)$")) {
                request.setAttribute("errorAddress", "Invalid address format.");
                hasError = true;
            }

            if (hasError) {
                // Gửi lại dữ liệu cho người dùng
                request.setAttribute("userManager", new UserManager(id, name_raw, password, fullName, email, phone, address, role_raw));

                if (role == 7) {
                    int loyaltyPoint = parseIntSafe(loyaltyPoint_raw);
                    CustomerInfo ci = new CustomerInfo(id, customerCode, joinDate, loyaltyPoint, birthday, gender);
                    request.setAttribute("customerInfo", ci);
                } else {
                    LocalDate startDate = parseDateSafe(startDate_raw);
                    LocalDate endDate = parseDateSafe(endDate_raw);
                    EmployeeInfo ei = new EmployeeInfo(id, employeeCode, contractType, startDate, endDate, department, position);
                    request.setAttribute("employeeInfo", ei);
                }

                request.setAttribute("roleNames", ud.getRoleNames());
                request.getRequestDispatcher("DashMin/viewuserdetail.jsp").forward(request, response);
                return;
            }

            User user = new User(id, name_raw, password, fullName, email, phone, address, role);
            ud.Update(user);

            if (role == 7) {
                int loyaltyPoint = parseIntSafe(loyaltyPoint_raw);
                CustomerInfo customer = new CustomerInfo(id, customerCode, joinDate, loyaltyPoint, birthday, gender);
                CustomerDAO cd = new CustomerDAO();
                if (cd.exist(id)) {
                    cd.update(customer);
                } else {
                    cd.insert(customer);
                }
            } else {
                LocalDate startDate = parseDateSafe(startDate_raw);
                LocalDate endDate = parseDateSafe(endDate_raw);
                EmployeeInfo employee = new EmployeeInfo(id, employeeCode, contractType, startDate, endDate, department, position);
                EmployeeDAO ed = new EmployeeDAO();
                if (ed.exist(id)) {
                    ed.update(employee);
                } else {
                    ed.insert(employee);
                }
            }

            response.sendRedirect("viewuserdetail");

        } catch (NumberFormatException e) {
            System.out.println("ID format error: " + e.getMessage());
            request.setAttribute("error", "Invalid input format.");
            request.setAttribute("roleNames", ud.getRoleNames());
            request.getRequestDispatcher("DashMin/viewuserdetail.jsp").forward(request, response);
        }
    }

    private int parseIntSafe(String raw) {
        try {
            return (raw != null && !raw.isEmpty()) ? Integer.parseInt(raw) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private LocalDate parseDateSafe(String raw) {
        try {
            return (raw != null && !raw.isEmpty()) ? LocalDate.parse(raw) : null;
        } catch (DateTimeParseException e) {
            return null;
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
