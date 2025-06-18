/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dal.SalesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import model.StatResult;

/**
 *
 * @author LAPTOP
 */
@WebServlet(name = "MonthlyStatsServlet", urlPatterns = {"/monthlystatsservlet"})
public class MonthlyStatsServlet extends HttpServlet {

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
            out.println("<title>Servlet MonthlyStatsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MonthlyStatsServlet at " + request.getContextPath() + "</h1>");
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
        // ✅ Nhận năm từ request (hoặc dùng năm hiện tại)
        String yearParam = request.getParameter("year");
        int year = (yearParam != null && !yearParam.isEmpty())
                ? Integer.parseInt(yearParam)
                : Year.now().getValue();

        // ✅ Lấy danh sách StatResult từ DAO
        SalesDAO dao = new SalesDAO();
        List<StatResult> stats = dao.getMonthlyStats(year);

        // ✅ Tách label, revenue, orderCount thành 3 list riêng để vẽ biểu đồ
        List<String> labels = new ArrayList<>();
        List<Double> revenues = new ArrayList<>();
        List<Integer> orders = new ArrayList<>();

        for (StatResult stat : stats) {
            labels.add(stat.getLabel());
            revenues.add(stat.getRevenue());
            orders.add(stat.getOrderCount());
        }

        // ✅ Convert sang JSON
        Gson gson = new Gson();
        String labelsJson = gson.toJson(labels);
        String revenuesJson = gson.toJson(revenues);
        String ordersJson = gson.toJson(orders);

        // ✅ Gửi dữ liệu sang JSP
        request.setAttribute("labelsJson", labelsJson);
        request.setAttribute("revenuesJson", revenuesJson);
        request.setAttribute("ordersJson", ordersJson);
        request.setAttribute("selectedYear", year);

        request.getRequestDispatcher("TestWeb/monthlyStats.jsp").forward(request, response);
    
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
     * @return a String containing servlet description
     */
    @Override
public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
