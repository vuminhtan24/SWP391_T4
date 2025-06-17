/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.SalesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.SalesRecord;
import com.google.gson.Gson;

/**
 *
 * @author LAPTOP
 */
@WebServlet(name = "SalesReportServlet", urlPatterns = {"/salesreportservlet"})
public class SalesReportServlet extends HttpServlet {

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
            out.println("<title>Servlet SalesReportServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SalesReportServlet at " + request.getContextPath() + "</h1>");
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
        SalesDAO dao = new SalesDAO();

        // ✅ 1. Danh sách đơn hàng trong tháng hiện tại
        List<SalesRecord> salesList = dao.getSalesReportThisMonth();

        // ✅ 2. Doanh thu hôm nay
        double todayRevenue = dao.getTodayRevenue();

        // ✅ 3. Tổng doanh thu toàn thời gian
        double totalRevenue = dao.getTotalRevenue();

        // ✅ 4. Tổng doanh thu tháng hiện tại
        double thisMonthRevenue = dao.getThisMonthRevenue();

        // ✅ 5. Số đơn hàng hôm nay
        int todayOrders = dao.getTodayOrderCount();

        // ✅ 6. Biểu đồ doanh thu theo ngày (tháng hiện tại)
        Map<String, Double> revenueByDate = dao.getRevenueGroupedThisMonth();
        String labelsJson = new Gson().toJson(new ArrayList<>(revenueByDate.keySet()));
        String valuesJson = new Gson().toJson(new ArrayList<>(revenueByDate.values()));

        // (Tùy chọn) nếu lọc theo categoryId từ JSP (dropdown):
        String categoryParam = request.getParameter("categoryId");
        if (categoryParam != null) {
            int categoryId = Integer.parseInt(categoryParam);
            Map<String, Double> revenueByCategory = dao.getRevenueGroupedByCategory(categoryId);
            labelsJson = new Gson().toJson(new ArrayList<>(revenueByCategory.keySet()));
            valuesJson = new Gson().toJson(new ArrayList<>(revenueByCategory.values()));
        }

        // ✅ Gửi tất cả dữ liệu về salesReport.jsp
        request.setAttribute("salesList", salesList);
        request.setAttribute("todayRevenue", todayRevenue);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("thisMonthRevenue", thisMonthRevenue);
        request.setAttribute("todayOrders", todayOrders);
        request.setAttribute("labelsJson", labelsJson);
        request.setAttribute("valuesJson", valuesJson);

        request.getRequestDispatcher("TestWeb/salesReport.jsp").forward(request, response);
    
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
