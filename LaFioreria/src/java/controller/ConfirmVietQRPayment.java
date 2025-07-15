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
import java.security.Timestamp;
import model.Order;
import model.User; // Import User model

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

        HttpSession session = request.getSession();
        Integer orderId = (Integer) session.getAttribute("currentOrderId");
        Integer amount = (Integer) session.getAttribute("currentOrderAmount");

        System.out.println("DEBUG: ConfirmVietQRPayment - Entering doGet method.");
        System.out.println("DEBUG: ConfirmVietQRPayment - Retrieved orderId from session: " + orderId);
        System.out.println("DEBUG: ConfirmVietQRPayment - Retrieved amount from session: " + amount);

        // Clear session attributes immediately after retrieval to prevent stale data
        session.removeAttribute("currentOrderId");
        session.removeAttribute("currentOrderAmount");
        System.out.println("DEBUG: ConfirmVietQRPayment - Removed orderId and amount from session.");

        if (orderId == null || amount == null) {
            System.err.println("ERROR: ConfirmVietQRPayment - orderId or amount is null in session. Redirecting to error page.");
            request.setAttribute("error", "Thông tin đơn hàng không hợp lệ hoặc đã hết hạn. Vui lòng thử lại.");
            request.getRequestDispatcher("/ZeShopper/error.jsp").forward(request, response); // Redirect to an error page
            return;
        }

        // Set QR code specific parameters from the server-side
        request.setAttribute("bankCode", "MB");
        request.setAttribute("accountNumber", "2628612348888");
        request.setAttribute("accountName", "LaFioreria");
        System.out.println("DEBUG: ConfirmVietQRPayment - Set bank details as request attributes.");

        // Get order creation time to check payment deadline
        CartDAO dao = new CartDAO();
        java.sql.Timestamp createdAt = dao.getOrderCreatedAt(orderId); // Use the retrieved orderId
        System.out.println("DEBUG: ConfirmVietQRPayment - Order created at: " + createdAt);

        if (createdAt != null) {
            long nowMillis = System.currentTimeMillis();
            long orderMillis = createdAt.getTime();
            long diffMillis = nowMillis - orderMillis;
            long maxAllowedMillis = 15 * 60 * 1000; // 15 minutes in milliseconds

            if (diffMillis > maxAllowedMillis) {
                System.err.println("ERROR: ConfirmVietQRPayment - Order " + orderId + " has expired. Redirecting to error page.");
                request.setAttribute("error", "Đơn hàng đã hết thời gian thanh toán. Vui lòng đặt lại đơn mới.");
                request.getRequestDispatcher("/ZeShopper/error.jsp").forward(request, response); // Redirect to an error page
                return;
            }
            long remainingTime = maxAllowedMillis - diffMillis;
            request.setAttribute("remainingTime", remainingTime);
            System.out.println("DEBUG: ConfirmVietQRPayment - Remaining time for payment: " + remainingTime + " ms.");
        }

        User user = (User) session.getAttribute("currentAcc"); // Assuming "currentAcc" is used for user object
        if (user != null) {
            request.setAttribute("user", user);
            System.out.println("DEBUG: ConfirmVietQRPayment - User found in session: " + user.getUserid());
        } else {
            System.out.println("DEBUG: ConfirmVietQRPayment - No user found in session.");
        }

        request.setAttribute("orderId", orderId);
        request.setAttribute("amount", amount);
        System.out.println("DEBUG: ConfirmVietQRPayment - Set orderId and amount as request attributes.");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/ZeShopper/vietqr.jsp");
        System.out.println("DEBUG: ConfirmVietQRPayment - Forwarding to /ZeShopper/vietqr.jsp.");
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
        System.out.println("DEBUG: ConfirmVietQRPayment - Entering doPost method for order ID: " + orderId);

        try {
            OrderDAO orderDAO = new OrderDAO();
            orderDAO.updateOrderStatus(orderId, 1); // Update order status to 1 (confirmed payment)
            System.out.println("DEBUG: ConfirmVietQRPayment - Order " + orderId + " status updated to 1 (confirmed payment).");

            System.out.println("Đơn hàng " + orderId + " đã được khách xác nhận chuyển khoản.");

            // Redirect to a thank you page with the order ID
            System.out.println("DEBUG: ConfirmVietQRPayment - Redirecting to " + request.getContextPath() + "/ZeShopper/thanks-you.jsp?orderId=" + orderId);
            response.sendRedirect(request.getContextPath() + "/ZeShopper/thanks-you.jsp?orderId=" + orderId);

        } catch (Exception e) {
            System.err.println("ERROR: ConfirmVietQRPayment - An error occurred during POST for order " + orderId + ". Details: " + e.getMessage());
            e.printStackTrace(); // In toàn bộ stack trace để gỡ lỗi chi tiết
            // Redirect to an error page if something goes wrong
            System.out.println("DEBUG: ConfirmVietQRPayment - Redirecting to " + request.getContextPath() + "/ZeShopper/error.jsp");
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
