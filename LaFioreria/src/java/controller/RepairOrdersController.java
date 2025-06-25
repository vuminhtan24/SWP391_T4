/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.RepairOrdersDAO;
import model.RepairOrders;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name = "RepairOrdersController", urlPatterns = {"/repairOrders"})
public class RepairOrdersController extends HttpServlet {

    private RepairOrdersDAO repairOrderDAO;

    @Override
    public void init() {
        repairOrderDAO = new RepairOrdersDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<RepairOrders> orders = repairOrderDAO.getPendingRepairOrders();
            request.setAttribute("repairOrders", orders);
            request.getRequestDispatcher("DashMin/repairorders.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int repairId = Integer.parseInt(request.getParameter("repairId"));
        int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));
        int oldBatchId = Integer.parseInt(request.getParameter("oldBatchId"));
        String newBatchIdStr = request.getParameter("newBatchId");

        try {
            int newBatchId = Integer.parseInt(newBatchIdStr);
            repairOrderDAO.completeRepairOrder(repairId, bouquetId, oldBatchId, newBatchId);
            response.sendRedirect("repairOrders");
        } catch (SQLException | NumberFormatException e) {
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            doGet(request, response);
        }
    }

}
