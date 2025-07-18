/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.DAOAccount;
import dal.DAOTokenForget;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import model.TokenForgetPassword;
import model.User;

/**
 *
 * @author VU MINH TAN
 */
@WebServlet("/ZeShopper/requestPassword")
public class requestPassword extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet requestPassword</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet requestPassword at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    DAOAccount daoAcc = new DAOAccount();
    String email = request.getParameter("email");

    email = (email != null) ? email.trim() : "";

    if (email.isEmpty() || !email.matches("\\S+")) {
        returnInputValue(request, response, email, "Email must not be empty or contain spaces");
        return;
    }
    if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
        returnInputValue(request, response, email, "Invalid email format");
        return;
    }

    User user = daoAcc.getAccountByEmail(email);
    if (user == null) {
        returnInputValue(request, response, email, "Email does not exist or is not registered");
        return;
    }

    resetService service = new resetService();
    String token = service.generateToken();
    String linkReset = "http://localhost:8080/LaFioreria/ZeShopper/resetPassword?token=" + token;

    TokenForgetPassword newTokenForget = new TokenForgetPassword(
            user.getUserid(), false, token, service.expireDateTime());

    DAOTokenForget daoToken = new DAOTokenForget();
    boolean isInsert = daoToken.insertTokenForget(newTokenForget);
    if (!isInsert) {
        returnInputValue(request, response, email, "Server error. Please try again later.");
        return;
    }

    boolean isSend = service.sendEmail(email, linkReset, user.getFullname());
    if (!isSend) {
        returnInputValue(request, response, email, "Cannot send reset email. Please try again.");
        return;
    }

    request.setAttribute("email", email);
    request.setAttribute("mess", "Password reset request sent. Please check your email.");
    request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
}

public void returnInputValue(HttpServletRequest request,
        HttpServletResponse response, String email, String mess)
        throws ServletException, IOException {
    request.setAttribute("email", email);
    request.setAttribute("mess", mess);
    request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
}



    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
