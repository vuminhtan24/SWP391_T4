/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DAOAccount;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Random;
import model.User;
import util.Validate;

/**
 *
 * @author HP
 */
@WebServlet("/ZeShopper/Register")
public class Register extends HttpServlet {

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
            out.println("<title>Servlet Register</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Register at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        DAOAccount daoAcc = new DAOAccount();
        String username = request.getParameter("username");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String confirmPassword = request.getParameter("confirmPassword");
        String messRegister;

        if (username.isEmpty()
                || fullname.isEmpty()
                || email.isEmpty()
                || phone.isEmpty()
                || address.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {
            messRegister = "Please fill all of field!";
            returnInputValue(request, response, email, fullname, "", phone, address, messRegister);
            return;
        }
        if (isBlank(username) || isBlank(fullname) || isBlank(email)
                || isBlank(phone) || isBlank(address)
                || isBlank(password) || isBlank(confirmPassword)) {
            messRegister = "Fields must not be empty or contain only spaces";
            returnInputValue(request, response, email, fullname, "", phone, address, messRegister);
            return;
        }
        if(username.contains(" ")){
            messRegister = "Username must not contain spaces";
            returnInputValue(request, response, email, fullname, "", phone, address, messRegister);
            return;
        }

        if (daoAcc.getAccountByEmail(email) != null) {
            messRegister = "This account have exist!";
            returnInputValue(request, response, email, fullname, "", phone, address, messRegister);
            return;
        }
        if (!Validate.isValidPhoneNumber(phone)) {
            messRegister = "Phone number invalid!";
            returnInputValue(request, response, email, fullname, username, "", address, messRegister);
            return;
        }
        if (!password.equals(confirmPassword)) {
            messRegister = "Password and Confirm Password do not match!";
            returnInputValue(request, response, email, fullname, username, phone, address, messRegister);
            return;
        }
        if (password.length() < 6) {
            messRegister = "Password must be at least 6 characters!";
            returnInputValue(request, response, email, fullname, username, phone, address, messRegister);
            return;
        }
        User tempUser = new User(username, password, fullname, email, phone, address, 7);
        // Sinh OTP
        Random rand = new Random();
        int otp = 100000 + rand.nextInt(900000);

        // Lưu vào session
        session.setAttribute("otp", otp);
        session.setAttribute("tempUser", tempUser);
        try {
            SendMail.send(email, "OTP Verification", "Your OTP code is: " + otp);
        } catch (Exception e) {
            e.printStackTrace();
            messRegister = "Can't send OTP. Please try again later.";
            returnInputValue(request, response, email, fullname, username, phone, address, messRegister);
            return;
        }

        response.sendRedirect("verifyOTP.jsp");
//        User acc = new User(username, password, fullname, email, phone, address, 7);
//        boolean isCreate = daoAcc.createAccount(acc);
//        if (!isCreate) {
//            messRegister = "Create account unsuccess!";
//            returnInputValue(request, response, email, fullname, username, phone, address, messRegister);
//            return;
//        } else {
//            session.setAttribute("currentAcc", acc);
//            response.sendRedirect(request.getContextPath() + "/home");
//        }
    }

    public void returnInputValue(HttpServletRequest request,
            HttpServletResponse response, String email, String fullname, String username,
            String phone, String address, String messRegister)
            throws ServletException, IOException {
        request.setAttribute("email", email);
        request.setAttribute("fullname", fullname);
        request.setAttribute("username", username);
        request.setAttribute("phone", phone);
        request.setAttribute("address", address);
        request.setAttribute("messRegister", messRegister);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    public boolean isBlank(String input) {
    return input == null || input.trim().isEmpty(); // true nếu input là null hoặc toàn dấu cách
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
