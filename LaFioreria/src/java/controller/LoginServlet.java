/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CustomerDAO;
import dal.DAOAccount;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ZeShopper/LoginServlet")
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
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
        if (session != null && session.getAttribute("currentAcc") != null) {
            // If already logged in, redirect based on role
            User user = (User) session.getAttribute("currentAcc");
            DAOAccount daoAcc = new DAOAccount();
            String role = daoAcc.getRoleNameById(user.getRole());

            // Redirect based on role to prevent re-displaying login.jsp
            redirectBasedOnRole(request, response, role);
            return;
        }

        // Check cookies for remember me
        Cookie[] cookies = request.getCookies();
        String savedEmail = null;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("userEmail".equals(c.getName())) {
                    savedEmail = c.getValue();
                }
            }
        }

        if (savedEmail != null) {
            User user = new DAOAccount().getAccountByEmail(savedEmail);
            if (user != null) {
                request.getSession().setAttribute("currentAcc", user);
                DAOAccount daoAcc = new DAOAccount(); // Re-instantiate DAO for role check
                String role = daoAcc.getRoleNameById(user.getRole());
                redirectBasedOnRole(request, response, role);
                return;
            }
        }

        // If not auto-logged in, forward to login page
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("rememberMe");

        // Input validation (removed redundant check for spaces)
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            request.setAttribute("messLogin", "Please enter username and password.");
            request.setAttribute("username", username);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        if (username.contains(" ") || password.contains(" ")) {
            request.setAttribute("messLogin", "Username and password must not contain spaces.");
            request.setAttribute("username", username); // Retain username
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        DAOAccount daoAcc = new DAOAccount();
        User user = daoAcc.validate(username, password); // Assuming validate returns User object

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("currentAcc", user);
            session.setAttribute("userId", user.getUserid());
            if ("on".equals(remember)) {
                Cookie emailCookie = new Cookie("userEmail", username);
                emailCookie.setMaxAge(60 * 60 * 24 * 7); // 7 days
                response.addCookie(emailCookie);
            }

            CustomerDAO customerDao = new CustomerDAO();
            int currentPoint = customerDao.getLoyaltyPointByUserId(user.getUserid());
            boolean updated = customerDao.updateLoyaltyPointByUserId(user.getUserid(), currentPoint + 25);
            if (!updated) {
                System.err.println("Fail Update loyaltyPoint!!!");
            }
            String role = daoAcc.getRoleNameById(user.getRole());
            redirectBasedOnRole(request, response, role); // Call helper method for redirection

        } else {
            request.setAttribute("username", username);
            request.setAttribute("messLogin", "Invalid username or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    /**
     * Helper method to redirect user based on their role.
     *
     * @param request servlet request
     * @param response servlet response
     * @param role The role name of the logged-in user.
     * @throws IOException if an I/O error occurs
     */
    private void redirectBasedOnRole(HttpServletRequest request, HttpServletResponse response, String role) throws IOException {
        switch (role) {
            case "Admin":
                response.sendRedirect(request.getContextPath() + "/DashMin/admin");
                break;
            case "Sales Manager":
                response.sendRedirect(request.getContextPath() + "/saleManagerDashboard");
                break;
            case "Seller":
            case "Marketer":
            case "Warehouse Staff":
                response.sendRedirect(request.getContextPath() + "/DashMin/admin");
                break;
            case "Shipper": // FIX: Separate case for Shipper
                response.sendRedirect(request.getContextPath() + "/shipperDashboard"); // Redirect to new Shipper page
                break;
            case "Guest":
            case "Customer":
                response.sendRedirect(request.getContextPath() + "/home"); // Assuming /home is your public homepage
                break;
            default:
                // Fallback for unknown roles or errors, redirect to login again
                // It's good practice to have a clearer error or a general user dashboard
                response.sendRedirect(request.getContextPath() + "/ZeShopper/LoginServlet?error=unknownRole");
                break;
        }
    }

    public void returnInputValue(HttpServletRequest request,
            HttpServletResponse response, String username)
            throws ServletException, IOException {
        request.setAttribute("username", username);
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
