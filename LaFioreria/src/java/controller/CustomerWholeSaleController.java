/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BouquetDAO;
import dal.WholeSaleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import model.Bouquet;
import model.BouquetImage;
import model.User;
import model.WholeSale;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CustomerWholeSaleController", urlPatterns = {"/wholeSale"})
public class CustomerWholeSaleController extends HttpServlet {

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
            out.println("<title>Servlet CustomerWholeSaleController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerWholeSaleController at " + request.getContextPath() + "</h1>");
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
        User acc = (User) request.getSession().getAttribute("currentAcc");
        if (acc == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = acc.getUserid();

        WholeSaleDAO wsDao = new WholeSaleDAO();
        BouquetDAO bDao = new BouquetDAO();

        List<BouquetImage> images = bDao.getAllBouquetImage();
        List<WholeSale> listWSRequest = wsDao.getWholeSaleRequestByUserID(userId);
        List<Bouquet> listBouquet = bDao.getAll();

        request.setAttribute("listBouquet", listBouquet);
        request.setAttribute("images", images);
        request.setAttribute("wholesaleList", listWSRequest);
        request.getRequestDispatcher("./ZeShopper/wholeSale.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        String act = request.getParameter("act"); // từ query string ?act=confirm

        try {
            WholeSaleDAO wsDao = new WholeSaleDAO();

            if ("update".equalsIgnoreCase(action)) {
                // Xử lý cập nhật đơn hàng
                int userId = Integer.parseInt(request.getParameter("user_id"));
                int bouquetId = Integer.parseInt(request.getParameter("bouquet_id"));
                int quantity = Integer.parseInt(request.getParameter("requested_quantity"));
                String note = request.getParameter("note");

                wsDao.UpdateWholeSaleRequest(userId, bouquetId, quantity, note);
                response.sendRedirect(request.getContextPath() + "/wholeSale");

            } else if ("confirm".equalsIgnoreCase(act)) {
                // Xử lý xác nhận yêu cầu báo giá (chuyển tất cả đơn SHOPPING -> PENDING)
                int userId = Integer.parseInt(request.getParameter("user_id"));

                wsDao.confirmAllRequests(userId); // bạn cần viết hàm này trong DAO
                response.sendRedirect(request.getContextPath() + "/wholeSale?confirmed=true");

            } else {
                // Mặc định nếu không rõ action
                response.sendRedirect(request.getContextPath() + "/wholeSale");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
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
