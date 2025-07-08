/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.EmployeeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.EmployeeInfo;

/**
 *
 * @author LAPTOP
 */
@WebServlet(name = "ViewEmployeeServlet", urlPatterns = {"/viewemployeeservlet"})
public class ViewEmployeeServlet extends HttpServlet {

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
            out.println("<title>Servlet ViewEmployeeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewEmployeeServlet at " + request.getContextPath() + "</h1>");
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

        String keyword = request.getParameter("search");
        keyword = (keyword != null) ? keyword.trim() : null;

        String department = request.getParameter("department");
        department = (department != null) ? department.trim() : null;

        String sort = request.getParameter("sort");
        sort = (sort != null) ? sort.trim() : null;

        String order = request.getParameter("order");
        order = (order != null) ? order.trim() : null;

        System.out.println("keyword = " + keyword);
        System.out.println("department = " + department);
        System.out.println("sort = " + sort);
        System.out.println("order = " + order);

        String page_raw = request.getParameter("page");

        int page = (page_raw == null || page_raw.isEmpty()) ? 1 : Integer.parseInt(page_raw);
        int pageSize = 5;
        int offset = (page - 1) * pageSize;

        EmployeeDAO dao = new EmployeeDAO();
        List<EmployeeInfo> employeeList = dao.getFilteredEmployees(
                keyword, department, sort, order, offset, pageSize
        );

        int totalRows = dao.countFilteredEmployees(keyword, department);
        int totalPages = (int) Math.ceil((double) totalRows / pageSize);

        request.setAttribute("employeeList", employeeList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("search", keyword);
        request.setAttribute("department", department);
        request.setAttribute("sort", sort);
        request.setAttribute("order", order);

        request.getRequestDispatcher("DashMin/employee_list.jsp").forward(request, response);
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
        processRequest(request, response);
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
