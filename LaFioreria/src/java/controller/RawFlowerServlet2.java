/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import dal.RawFlowerDAO;
import model.RawFlower;
import dal.WarehouseDAO;
import model.Warehouse;

/**
 *
 * @author Admin
 */
@WebServlet(name = "RawFlowerServlet2", urlPatterns = {"/DashMin/rawflower2"})
public class RawFlowerServlet2 extends HttpServlet {

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
            out.println("<title>Servlet AdminServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminServlet at " + request.getContextPath() + "</h1>");
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
            // Lấy danh sách kho
            WarehouseDAO wh = new WarehouseDAO();
            List<Warehouse> listWarehouse = wh.getAllWarehouse();

            // Lấy tham số sắp xếp từ request
            String sortBy = request.getParameter("sortBy"); // "price" hoặc "quantity"
            String sortOrder = request.getParameter("sortOrder"); // "asc" hoặc "desc"

            // Lấy danh sách nguyên liệu
            RawFlowerDAO rf = new RawFlowerDAO();
            List<RawFlower> listRF;

            // Kiểm tra tham số hợp lệ
            boolean validSort = true;
            if (sortBy != null && sortOrder != null) {
                if (!sortBy.matches("^(unit_price|import_price|quantity)$") || !sortOrder.matches("^(asc|desc)$")) {
                    validSort = false;
                    request.getSession().setAttribute("message", "Invalid sort option.");
                }
            }

            // Lấy danh sách đã sắp xếp
            if (validSort && sortBy != null && sortOrder != null) {
                listRF = rf.getRawFlowerSorted(sortBy, sortOrder);
            } else {
                listRF = rf.getRawFlower(); // Mặc định nếu không có lựa chọn sắp xếp
            }

            // Lưu dữ liệu vào session
            HttpSession session = request.getSession();
            session.setAttribute("listW", listWarehouse);
            session.setAttribute("listRF", listRF);
            // Lưu trạng thái sắp xếp để JSP giữ giá trị dropdown
            session.setAttribute("sortBy", sortBy);
            session.setAttribute("sortOrder", sortOrder);

            // Chuyển tiếp đến JSP
            request.getRequestDispatcher("rawflower.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", "An error occurred while processing the request.");
            response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
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
    
    public static void main(String[] args) {
        RawFlowerDAO rf = new RawFlowerDAO();
        List<RawFlower> listRF = new ArrayList<>();
        System.out.println(rf.getRawFlower());
    }

}
