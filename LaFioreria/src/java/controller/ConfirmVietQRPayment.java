/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CartDAO;
import dal.OrderDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp; // Đã sửa từ java.security.Timestamp sang java.sql.Timestamp

/**
 *
 * @author VU MINH TAN
 */
@WebServlet(name = "ConfirmVietQRPayment", urlPatterns = {"/ConfirmVietQRPayment"})
public class ConfirmVietQRPayment extends HttpServlet {

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
            out.println("<title>Servlet ConfirmVietQRPayment</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConfirmVietQRPayment at " + request.getContextPath() + "</h1>");
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

        String orderId = request.getParameter("orderId");
        String amountStr = request.getParameter("amount");

        if (orderId == null || amountStr == null) {
            // Sửa đường dẫn redirect để đảm bảo đúng context path
            response.sendRedirect(request.getContextPath() + "/ZeShopper/checkout.jsp");
            return;
        }

        // Lấy thời gian tạo đơn hàng để kiểm tra hạn thanh toán
        CartDAO dao = new CartDAO();
        java.sql.Timestamp createdAt = dao.getOrderCreatedAt(Integer.parseInt(orderId));

        if (createdAt != null) {
            long nowMillis = System.currentTimeMillis();
            long orderMillis = createdAt.getTime();
            System.out.println("Current time (nowMillis): " + nowMillis + " (" + new java.util.Date(nowMillis) + ")");
            System.out.println("Order created at (createdAt from DB): " + createdAt + " (orderMillis: " + orderMillis + ")");

            long diffMillis = nowMillis - orderMillis;
            long maxAllowedMillis = 15 * 60 * 1000; // 15 phút

            if (diffMillis > maxAllowedMillis) {
                request.setAttribute("errorMessage", "Đơn hàng đã hết thời gian thanh toán. Vui lòng đặt lại đơn mới.");
                // Sửa đường dẫn forward để đảm bảo đúng context path
                request.getRequestDispatcher("/ZeShopper/checkout.jsp").forward(request, response); // Forward về checkout.jsp
                return;
            }
            long remainingTime = maxAllowedMillis - diffMillis;
            System.out.println("Calculated remainingTime (millis): " + remainingTime);
            request.setAttribute("remainingTime", remainingTime);
        }

        // Parse số tiền
        Integer amount = null; // Thay đổi kiểu dữ liệu thành Integer để tránh NumberFormatException
        try {
            // Chuyển đổi từ String sang Double, sau đó ép kiểu thành Integer
            amount = (int) Double.parseDouble(amountStr); 
        } catch (NumberFormatException e) {
            // Sửa đường dẫn redirect để đảm bảo đúng context path
            response.sendRedirect(request.getContextPath() + "/ZeShopper/checkout.jsp");
            return;
        }

        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");

        if (user != null) {
            request.setAttribute("user", user);
        }

        // Đặt các thông tin cần thiết cho VietQR vào request attributes
        request.setAttribute("bankCode", "MB");
        request.setAttribute("accountNumber", "2628612348888");
        request.setAttribute("accountName", "LaFioreria");
        
        request.setAttribute("orderId", orderId);
        request.setAttribute("amount", amount); // Đặt amount đã được parse vào thuộc tính

        RequestDispatcher dispatcher = request.getRequestDispatcher("/ZeShopper/vietqr.jsp");
        dispatcher.forward(request, response);
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
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            OrderDAO orderDAO = new OrderDAO();
            // Cập nhật trạng thái đơn hàng. Nếu 1 là chờ xử lý, có thể bạn muốn đặt là 2 (đã thanh toán) hoặc 3 (đang giao)
            // Tùy thuộc vào logic kinh doanh của bạn. Tôi giữ nguyên 1 theo code cũ.
            orderDAO.updateOrderStatus(orderId, 1); 

            System.out.println("Đơn hàng " + orderId + " đã được khách xác nhận chuyển khoản.");

            // Sửa đường dẫn redirect để sử dụng request.getContextPath()
            response.sendRedirect(request.getContextPath() + "/ZeShopper/thanks-you.jsp?orderId=" + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            // Sửa đường dẫn redirect để đảm bảo đúng context path
            response.sendRedirect(request.getContextPath() + "/ZeShopper/error.jsp");
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
