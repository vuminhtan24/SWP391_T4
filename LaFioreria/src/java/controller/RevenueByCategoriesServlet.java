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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import model.Category;

/**
 *
 * @author LAPTOP
 */
@WebServlet(name = "RevenueByCategoriesServlet", urlPatterns = {"/revenuebycategoriesservlet"})
public class RevenueByCategoriesServlet extends HttpServlet {

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
            out.println("<title>Servlet RevenueByCategoriesServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RevenueByCategoriesServlet at " + request.getContextPath() + "</h1>");
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
        Gson gson = new Gson();

        // Lấy danh sách category
        List<Category> categoryList = dao.getAllCategories();
        request.setAttribute("categoryList", categoryList);

        // Lấy danh sách id từ request
        String[] selectedIds = request.getParameterValues("cid");

        if (selectedIds != null && selectedIds.length > 0) {
            Map<String, List<Double>> categoryRevenue = new LinkedHashMap<>();
            Set<String> allDates = new TreeSet<>();

            // Lặp từng category người dùng chọn
            for (String idStr : selectedIds) {
                int cid = Integer.parseInt(idStr);
                Map<String, Double> data = dao.getRevenueGroupedByCategory(cid);

                // Lấy tên category
                String categoryName = "";
                for (Category c : categoryList) {
                    if (c.getCategoryId()== cid) {
                        categoryName = c.getCategoryName();
                        break;
                    }
                }

                allDates.addAll(data.keySet());

                // Ghép vào map
                List<Double> values = new ArrayList<>();
                categoryRevenue.put(categoryName, values);

                // Sẽ thêm dữ liệu sau
                request.setAttribute("selectedIds", selectedIds);
                request.setAttribute("labelsJson", gson.toJson(allDates));
                request.setAttribute("dataMap", categoryRevenue);  // tạm thời rỗng
            }

            // Lặp lại để đồng bộ ngày
            List<String> dateList = new ArrayList<>(allDates);
            for (String idStr : selectedIds) {
                int cid = Integer.parseInt(idStr);
                Map<String, Double> data = dao.getRevenueGroupedByCategory(cid);

                String categoryName = "";
                for (Category c : categoryList) {
                    if (c.getCategoryId() == cid) {
                        categoryName = c.getCategoryName();
                        break;
                    }
                }

                List<Double> values = new ArrayList<>();
                for (String date : dateList) {
                    values.add(data.getOrDefault(date, 0.0));
                }

                // Ghi đè dữ liệu chính xác
                request.setAttribute(categoryName + "_data", gson.toJson(values));
            }

            request.setAttribute("labelsJson", gson.toJson(dateList));
        }

        request.getRequestDispatcher("TestWeb/revenueByCategories.jsp").forward(request, response);
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
