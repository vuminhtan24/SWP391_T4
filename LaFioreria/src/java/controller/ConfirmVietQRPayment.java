/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CartDAO;
import dal.OrderDAO; // Giả định OrderDAO tồn tại và có phương thức getOrderById
import model.Order; // Giả định model Order tồn tại
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;

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

        String orderIdParam = request.getParameter("orderId");
        String amountStrParam = request.getParameter("amount");

        // --- Bắt đầu phần kiểm tra URL manipulation và validate dữ liệu ---
        if (orderIdParam == null || amountStrParam == null || orderIdParam.isEmpty() || amountStrParam.isEmpty()) {
            request.setAttribute("errorMessage", "Thông tin thanh toán không hợp lệ. Vui lòng không thay đổi URL.");
            request.getRequestDispatcher("/ZeShopper/error.jsp").forward(request, response);
            return;
        }

        int orderId;
        double amountFromURL;

        try {
            orderId = Integer.parseInt(orderIdParam);
            amountFromURL = Double.parseDouble(amountStrParam);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Định dạng ID đơn hàng hoặc số tiền không hợp lệ. Vui lòng không thay đổi URL.");
            request.getRequestDispatcher("/ZeShopper/error.jsp").forward(request, response);
            return;
        }

        // Lấy thông tin đơn hàng từ database
        OrderDAO orderDAO = new OrderDAO(); // Khởi tạo OrderDAO
        Order order = null;
        try {
            order = orderDAO.getOrderDetailById(orderId); // Cần có phương thức này trong OrderDAO
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy thông tin đơn hàng từ DB: " + e.getMessage());
            request.setAttribute("errorMessage", "Có lỗi xảy ra khi truy xuất thông tin đơn hàng. Vui lòng thử lại.");
            request.getRequestDispatcher("/ZeShopper/error.jsp").forward(request, response);
            return;
        }

        if (order == null) {
            request.setAttribute("errorMessage", "Đơn hàng không tồn tại. Vui lòng kiểm tra lại ID đơn hàng.");
            request.getRequestDispatcher("/ZeShopper/error.jsp").forward(request, response);
            return;
        }

        // Kiểm tra trạng thái đơn hàng (ví dụ: 1 là "chờ xử lý" hoặc "pending payment")
        // Bạn cần xác định đúng statusId cho trạng thái chờ thanh toán trong hệ thống của bạn.
        // Giả sử statusId = 1 là chờ thanh toán.
        if (order.getStatusId() != 1) {
            request.setAttribute("errorMessage", "Đơn hàng không ở trạng thái chờ thanh toán. Vui lòng kiểm tra lại.");
            request.getRequestDispatcher("/ZeShopper/error.jsp").forward(request, response);
            return;
        }

        // Kiểm tra số tiền có khớp với tổng tiền của đơn hàng không
        double actualOrderTotal;
        try {
            // Giả sử getTotalSell() trả về String, cần parse sang double
            actualOrderTotal = Double.parseDouble(order.getTotalSell())/10;
        } catch (NumberFormatException e) {
            System.err.println("Lỗi khi parse totalSell của đơn hàng từ DB: " + e.getMessage());
            request.setAttribute("errorMessage", "Lỗi dữ liệu tổng tiền đơn hàng. Vui lòng liên hệ hỗ trợ.");
            request.getRequestDispatcher("/ZeShopper/error.jsp").forward(request, response);
            return;
        }

        // --- Debugging logs ---
        System.out.println("DEBUG: amountFromURL (double): " + amountFromURL);
        System.out.println("DEBUG: actualOrderTotal (double) from DB: " + actualOrderTotal);

        // So sánh số tiền từ URL và số tiền thực tế của đơn hàng bằng cách làm tròn về số nguyên
        // Điều này giúp loại bỏ các sai số nhỏ do dấu phẩy động
        long roundedAmountFromURL = Math.round(amountFromURL);
        long roundedActualOrderTotal = Math.round(actualOrderTotal);

        if (roundedAmountFromURL != roundedActualOrderTotal) {
            request.setAttribute("errorMessage", "Số tiền thanh toán không khớp với đơn hàng. Vui lòng không thay đổi URL. (URL: " + amountFromURL + ", DB: " + actualOrderTotal + ")");
            request.getRequestDispatcher("/ZeShopper/error.jsp").forward(request, response);
            return;
        }
        // --- Kết thúc phần kiểm tra URL manipulation và validate dữ liệu ---

        // Lấy thời gian tạo đơn hàng để kiểm tra hạn thanh toán (logic hiện có)
        CartDAO cartDAO = new CartDAO(); // Đổi tên biến để tránh trùng lặp
        java.sql.Timestamp createdAt = cartDAO.getOrderCreatedAt(orderId); // Sử dụng orderId đã được parse

        if (createdAt != null) {
            long nowMillis = System.currentTimeMillis();
            long orderMillis = createdAt.getTime();
            System.out.println("Current time (nowMillis): " + nowMillis + " (" + new java.util.Date(nowMillis) + ")");
            System.out.println("Order created at (createdAt from DB): " + createdAt + " (orderMillis: " + orderMillis + ")");

            long diffMillis = nowMillis - orderMillis;
            long maxAllowedMillis = 15 * 60 * 1000; // 15 phút

            if (diffMillis > maxAllowedMillis) {
                request.setAttribute("errorMessage", "Đơn hàng đã hết thời gian thanh toán. Vui lòng đặt lại đơn mới.");
                request.getRequestDispatcher("/ZeShopper/checkout.jsp").forward(request, response);
                return;
            }
            long remainingTime = maxAllowedMillis - diffMillis;
            System.out.println("Calculated remainingTime (millis): " + remainingTime);
            request.setAttribute("remainingTime", remainingTime);
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
        request.setAttribute("amount", (int) amountFromURL); // Sử dụng amountFromURL đã được validate

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
