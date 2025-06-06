/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
        response.sendRedirect(request.getContextPath() +"/home");
        return;
    }

    // Check cookies
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
        // Bạn có thể kiểm tra savedEmail trong DB và auto-login nếu hợp lệ
        User user = new DAOAccount().getAccountByEmail(savedEmail);
        if (user != null) {
            request.getSession().setAttribute("currentAcc", user);
            response.sendRedirect("home");
            return;
        }
    }

    // Nếu không tự động login được
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

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            request.setAttribute("messLogin", "Please enter username and password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        DAOAccount daoAcc = new DAOAccount();
        User user = daoAcc.validate(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("currentAcc", user);
            
            if ("on".equals(remember)) {
            Cookie emailCookie = new Cookie("userEmail", username);

            emailCookie.setMaxAge(60 * 60 * 24 * 7); // 7 ngày

            response.addCookie(emailCookie);
        }

            int roleId = user.getRole();
            String role = daoAcc.getRoleNameById(roleId);
            switch (role) {
                case "Admin":
                case "Sales Manager":
                case "Seller":
                case "Marketer":
                case "Warehouse Staff":
                    response.sendRedirect(request.getContextPath() + "/DashMin/admin");

                    break;
                case "Guest":
                case "Customer":
                    response.sendRedirect("/LaFioreria/home");
                    break;
                default:
                    response.sendRedirect("login.jsp");
                    break;
            }
        } else {
            request.setAttribute("messLogin", "Invalid gmail or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    public void returnInputValue(HttpServletRequest request,
            HttpServletResponse response,  String username)
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
