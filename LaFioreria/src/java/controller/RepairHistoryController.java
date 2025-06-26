/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.RepairHistoryDAO;
import model.RepairHistory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet to handle requests for viewing repair history.
 */
@WebServlet(name = "RepairHistoryController", urlPatterns = {"/repairHistory"})
public class RepairHistoryController extends HttpServlet {

    private RepairHistoryDAO repairHistoryDAO;

    @Override
    public void init() {
        repairHistoryDAO = new RepairHistoryDAO();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<RepairHistory> history = repairHistoryDAO.getRepairHistory();
            request.setAttribute("repairHistory", history);
            request.getRequestDispatcher("DashMin/repairhistory.jsp").forward(request, response);
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy lịch sử sửa chữa: " + e.getMessage());
            request.setAttribute("error", "Lỗi tải dữ liệu lịch sử sửa chữa: " + e.getMessage());
            request.getRequestDispatcher("DashMin/repairhistory.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles requests for viewing repair history of bouquets";
    }
}