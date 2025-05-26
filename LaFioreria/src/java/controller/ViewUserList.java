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

//        try {
//            UserDAO ud = new UserDAO();
//            List<UserManager> um = ud.getAllUserManager();
//            request.setAttribute("userManagerList", um);
//            List<Role> r = ud.getAllRole();
//            request.setAttribute("roleList", r);
//
//            request.getRequestDispatcher("TestWeb/showUserList.jsp").forward(request, response);
//        } catch (ServletException | IOException e) {
//            System.out.println(e);
//        }
        try {
            int page = 1;
            int recordsPerPage = 5; // Hoặc số bản ghi bạn muốn trên 1 trang

// Lấy số trang hiện tại từ request
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    page = 1; // fallback nếu lỗi
                }
            }

// Tính offset (vị trí bắt đầu lấy bản ghi)
            int offset = (page - 1) * recordsPerPage;

// Lấy dữ liệu từ form tìm kiếm, sort
            String sortField = request.getParameter("sortField");
            String sortOrder = request.getParameter("sortOrder");
            String keyword = request.getParameter("txtSearchName");
            String roleId_raw = request.getParameter("txtRoleList");

            if (roleId_raw == null || roleId_raw.trim().isEmpty()) {
                roleId_raw = "0";
            }
            int roleId = Integer.parseInt(roleId_raw);

            if (sortField == null || sortField.isEmpty()) {
                sortField = "User_ID";
            }
            if (sortOrder == null || sortOrder.isEmpty()) {
                sortOrder = "asc";
            }

// Gọi DAO lấy danh sách theo phân trang
            UserDAO ud = new UserDAO();
            List<Role> roleList = ud.getAllRole();
            List<UserManager> userList = ud.getSortedUsersWithPaging(roleId, keyword, sortField, sortOrder, offset, recordsPerPage);
            int totalRecords = ud.getTotalUserCount(roleId, keyword);
            int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

// Truyền về JSP
            request.setAttribute("userManagerList", userList);
            request.setAttribute("roleList", roleList);
            request.setAttribute("keyword", keyword);
            request.setAttribute("roleId", roleId);
            request.setAttribute("sortField", sortField);
            request.setAttribute("sortOrder", sortOrder);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("TestWeb/showUserList.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.out.println(e);
            System.out.println("role id is not integer");
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
            int page = 1;
            int recordsPerPage = 5; // Hoặc số bản ghi bạn muốn trên 1 trang

// Lấy số trang hiện tại từ request
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    page = 1; // fallback nếu lỗi
                }
            }

// Tính offset (vị trí bắt đầu lấy bản ghi)
            int offset = (page - 1) * recordsPerPage;

// Lấy dữ liệu từ form tìm kiếm, sort
            String sortField = request.getParameter("sortField");
            String sortOrder = request.getParameter("sortOrder");
            String keyword = request.getParameter("txtSearchName");
            String roleId_raw = request.getParameter("txtRoleList");

            if (roleId_raw == null || roleId_raw.trim().isEmpty()) {
                roleId_raw = "0";
            }
            int roleId = Integer.parseInt(roleId_raw);

            if (sortField == null || sortField.isEmpty()) {
                sortField = "User_ID";
            }
            if (sortOrder == null || sortOrder.isEmpty()) {
                sortOrder = "asc";
            }

// Gọi DAO lấy danh sách theo phân trang
            UserDAO ud = new UserDAO();
            List<Role> roleList = ud.getAllRole();
            List<UserManager> userList = ud.getSortedUsersWithPaging(roleId, keyword, sortField, sortOrder, offset, recordsPerPage);
            int totalRecords = ud.getTotalUserCount(roleId, keyword);
            int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

// Truyền về JSP
            request.setAttribute("userManagerList", userList);
            request.setAttribute("roleList", roleList);
            request.setAttribute("keyword", keyword);
            request.setAttribute("roleId", roleId);
            request.setAttribute("sortField", sortField);
            request.setAttribute("sortOrder", sortOrder);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("TestWeb/showUserList.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.out.println(e);
            System.out.println("role id is not integer");
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
