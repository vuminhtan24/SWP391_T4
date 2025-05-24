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
import java.util.ArrayList;
import java.util.List;
import model.Role;
import model.UserManager;

/**
 *
 * @author LAPTOP
 */
public class ViewUserList extends HttpServlet {

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
            out.println("<title>Servlet ViewUserList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewUserList at " + request.getContextPath() + "</h1>");
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

        try {
            UserDAO ud = new UserDAO();
            List<UserManager> um = ud.getAllUserManager();
            request.setAttribute("userManagerList", um);
            List<Role> r = ud.getAllRole();
            request.setAttribute("roleList", r);

            request.getRequestDispatcher("TestWeb/showUserList.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            System.out.println(e);
        }

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

        try {
            UserDAO ud = new UserDAO();
            List<Role> roleList = ud.getAllRole();
            request.setAttribute("roleList", roleList);

            String roleId_raw = request.getParameter("txtRoleList");

            System.out.println("roleId_raw = " + roleId_raw);

            if (roleId_raw == null) {
                roleId_raw = "0";
            }
            
            try {
                int roleId = Integer.parseInt(roleId_raw);
                List<UserManager> userList = new ArrayList<>();
                String kw = request.getParameter("txtSearchName");
                request.setAttribute("keyword", kw);
                if (roleId == 0) {
                    userList = ud.getAllUserManager();
                    if (kw != null && !kw.trim().isEmpty()) {
                        userList = ud.getUserByRoleIdSearchName(0, kw);
                    }
                } else {
                    userList = ud.getUserByRoleId(roleId);
                    if (kw != null && !kw.trim().isEmpty()) {
                        userList = ud.getUserByRoleIdSearchName(roleId, kw);
                    }
                }

                request.setAttribute("userManagerList", userList);
                request.getRequestDispatcher("TestWeb/showUserList.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                System.out.println(e);
                System.out.println("role id is not integer");
            }

        } catch (NumberFormatException e) {
            System.out.println(e);
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
