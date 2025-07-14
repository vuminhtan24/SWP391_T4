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
@WebServlet(name = "ConfirmVietQRPayment", urlPatterns = {"/ConfirmVietQRPayment"}) // Add WebServlet annotation if not present
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
            response.sendRedirect("checkout.jsp"); // Redirect to checkout if parameters are missing
            return;
        }

        // Set QR code specific parameters from the server-side
        request.setAttribute("bankCode", "MB");
        request.setAttribute("accountNumber", "2628612348888");
        request.setAttribute("accountName", "LaFioreria");

        // Get order creation time to check payment deadline
        CartDAO dao = new CartDAO();
        java.sql.Timestamp createdAt = dao.getOrderCreatedAt(Integer.parseInt(orderId));

        if (createdAt != null) {
            long nowMillis = System.currentTimeMillis();
            long orderMillis = createdAt.getTime();
            long diffMillis = nowMillis - orderMillis;
            long maxAllowedMillis = 15 * 60 * 1000; // 15 minutes in milliseconds

            if (diffMillis > maxAllowedMillis) {
                request.setAttribute("error", "Đơn hàng đã hết thời gian thanh toán. Vui lòng đặt lại đơn mới.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }
            long remainingTime = maxAllowedMillis - diffMillis;
            request.setAttribute("remainingTime", remainingTime);
        }

        // Parse amount
        int amount = 0;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("checkout.jsp"); // Redirect if amount is not a valid number
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentAcc"); // Assuming "currentAcc" is used for user object
        if (user != null) {
            request.setAttribute("user", user);
        }

        request.setAttribute("orderId", orderId);
        request.setAttribute("amount", amount);

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
            orderDAO.updateOrderStatus(orderId, 1); // Update order status to 1 (confirmed payment)

            System.out.println("Đơn hàng " + orderId + " đã được khách xác nhận chuyển khoản.");

            // Redirect to a thank you page with the order ID
            response.sendRedirect(request.getContextPath() + "/ZeShopper/thanks-you.jsp?orderId=" + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            // Redirect to an error page if something goes wrong
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
