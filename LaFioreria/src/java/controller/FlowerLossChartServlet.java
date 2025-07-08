/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dal.FlowerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.StatResult;

/**
 *
 * @author LAPTOP
 */
@WebServlet(name = "FlowerLossChartServlet", urlPatterns = {"/flowerlossstats"})
public class FlowerLossChartServlet extends HttpServlet {

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
            out.println("<title>Servlet FlowerLossChartServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FlowerLossChartServlet at " + request.getContextPath() + "</h1>");
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

        FlowerDAO dao = new FlowerDAO();
        Gson gson = new Gson();
        Map<String, Integer> discardReasonByCategory = dao.getDiscardReasonByFlowerType();
        List<StatResult> expiredByMonthAndCategory = dao.getExpiredFlowerByMonthAndCategory();

// ⚙️ Dữ liệu cho biểu đồ cột: Lỗi theo loại hoa
        request.setAttribute("discardLabels", gson.toJson(discardReasonByCategory.keySet()));
        request.setAttribute("discardValues", gson.toJson(discardReasonByCategory.values()));

// ⚙️ Dữ liệu cho biểu đồ đường: Hoa hết hạn theo tháng
        request.setAttribute("expiredLabels", gson.toJson(
                expiredByMonthAndCategory.stream().map(StatResult::getLabel).collect(Collectors.toList())
        ));
        request.setAttribute("expiredValues", gson.toJson(
                expiredByMonthAndCategory.stream().map(StatResult::getValue).collect(Collectors.toList())
        ));
        
//        response.sendRedirect("/LaFioreria/DashMin/flower_loss.jsp");
        request.getRequestDispatcher("DashMin/flower_loss.jsp").forward(request, response);
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
