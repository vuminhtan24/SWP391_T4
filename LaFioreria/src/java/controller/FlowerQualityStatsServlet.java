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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author LAPTOP
 */
@WebServlet(name = "FlowerQualityStatsServlet", urlPatterns = {"/flowerqualitystatsservlet"})
public class FlowerQualityStatsServlet extends HttpServlet {

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
            out.println("<title>Servlet FlowerQualityStatsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FlowerQualityStatsServlet at " + request.getContextPath() + "</h1>");
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

        // Lấy dữ liệu từ DAO
        Map<String, Integer> discardReasonStats = dao.getDiscardReasonStats();
        Map<String, Integer> importVsWasteStats = dao.getTotalImportVsWaste();

        // Nếu không có dữ liệu, sử dụng dữ liệu mẫu
        if (discardReasonStats == null || discardReasonStats.isEmpty()) {
            discardReasonStats = new LinkedHashMap<>();
            discardReasonStats.put("Damaged due to temperature", 60);
            discardReasonStats.put("Moldy", 25);
            discardReasonStats.put("Expired", 30);
            discardReasonStats.put("Bruised during transportation", 15);
            discardReasonStats.put("Wrong delivery / Rejected", 20);
        }

        Map<String, String> translationMap = new HashMap<>();
        translationMap.put("Lỗi do nhiệt độ", "Damaged due to temperature");
        translationMap.put("Mốc", "Moldy");
        translationMap.put("Hết hạn", "Expired");
        translationMap.put("Dập nát khi vận chuyển", "Bruised during transportation");
        translationMap.put("Giao sai / bị từ chối", "Wrong delivery / Rejected");

// Chuyển map sang tiếng Anh
        Map<String, Integer> translatedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : discardReasonStats.entrySet()) {
            String key = entry.getKey();
            String englishKey = translationMap.getOrDefault(key, key); // nếu không có thì giữ nguyên
            translatedMap.put(englishKey, entry.getValue());
        }

        if (importVsWasteStats == null || importVsWasteStats.isEmpty()) {
            importVsWasteStats = new LinkedHashMap<>();
            importVsWasteStats.put("Usable (sold)", 800);
            importVsWasteStats.put("Damaged", 100);
            importVsWasteStats.put("Expired", 50);
            importVsWasteStats.put("Wrong delivery / Rejected", 50);
        }

        // Gửi sang JSP
        request.setAttribute("discardReasonStats", translatedMap);
        request.setAttribute("importVsWasteStats", importVsWasteStats);

        // Chuyển sang trang JSP để hiển thị biểu đồ
        request.getRequestDispatcher("TestWeb/flower_quality.jsp").forward(request, response);
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
