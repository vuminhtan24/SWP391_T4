/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OrderDAO;
import model.Order;
import model.User;
import model.OrderStatus;
import model.OrderDetail; // Import OrderDetail
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet này xử lý việc hiển thị chi tiết đơn hàng và chức năng chỉnh sửa đơn hàng.
 * @author VU MINH TAN
 */
@WebServlet(name = "OrderDetailServlet", urlPatterns = {"/orderDetail"})
public class OrderDetailServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Xử lý yêu cầu hiển thị chi tiết đơn hàng hoặc form chỉnh sửa đơn hàng.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderIdParam = request.getParameter("orderId");
        String action = request.getParameter("action"); // Tham số để xác định hành động (view hoặc edit)

        if (orderIdParam == null || orderIdParam.isEmpty()) {
            System.out.println("OrderDetailServlet: orderIdParam là null hoặc rỗng. Chuyển hướng về orderManagement.");
            response.sendRedirect(request.getContextPath() + "/orderManagement");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdParam);
        } catch (NumberFormatException e) {
            System.err.println("OrderDetailServlet: Order ID không hợp lệ: " + orderIdParam + ". Chuyển hướng về orderManagement.");
            response.sendRedirect(request.getContextPath() + "/orderManagement");
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.getOrderDetailById(orderId);
        List<OrderDetail> orderItems = orderDAO.getOrderItemsByOrderId(orderId); // Lấy danh sách sản phẩm

        // ===============================================
        // DÒNG DEBUG QUAN TRỌNG: Kiểm tra dữ liệu ở Servlet
        // ===============================================
        System.out.println("=============================================");
        System.out.println("DEBUG: OrderDetailServlet.doGet cho Order ID: " + orderId);
        System.out.println("DEBUG: Thông tin Order: " + (order != null ? order.toString() : "null"));
        System.out.println("DEBUG: Kích thước danh sách OrderItems: " + orderItems.size());
        if (!orderItems.isEmpty()) {
            for (OrderDetail item : orderItems) {
                System.out.println("DEBUG:   - Item: " + item.getBouquetName() + ", Số lượng: " + item.getQuantity() + ", Giá đơn vị: " + item.getUnitPrice());
            }
        } else {
             System.out.println("DEBUG: Danh sách OrderItems trống hoặc null.");
        }
        System.out.println("=============================================");
        // ===============================================

        if (order == null) {
            request.setAttribute("errorMessage", "Không tìm thấy đơn hàng với ID: " + orderId);
            System.err.println("OrderDetailServlet: Không tìm thấy Order cho ID: " + orderId);
            request.getRequestDispatcher("/DashMin/error.jsp").forward(request, response);
            return;
        }

        if ("edit".equals(action)) {
            List<User> shippers = orderDAO.getAllShippers();
            List<OrderStatus> statuses = orderDAO.getAllOrderStatuses();

            request.setAttribute("order", order);
            request.setAttribute("orderItems", orderItems); // Truyền danh sách sản phẩm
            request.setAttribute("shippers", shippers);
            request.setAttribute("statuses", statuses);
            request.getRequestDispatcher("/DashMin/orderEdit.jsp").forward(request, response);
        } else {
            request.setAttribute("order", order);
            request.setAttribute("orderItems", orderItems); // Truyền danh sách sản phẩm
            request.getRequestDispatcher("/DashMin/orderDetail.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * Xử lý yêu cầu cập nhật thông tin đơn hàng từ form chỉnh sửa.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String orderIdParam = request.getParameter("orderId");
        String totalAmountParam = request.getParameter("totalAmount");
        String statusIdParam = request.getParameter("statusId");
        String shipperIdParam = request.getParameter("shipperId"); 

        int orderId = -1; // Khởi tạo orderId với giá trị mặc định không hợp lệ
        int statusId = -1; // Khởi tạo statusId với giá trị mặc định không hợp lệ
        Integer shipperId = null; 
        String errorMessage = null; // Biến để lưu thông báo lỗi

        // Bước 1: Parse các tham số và kiểm tra lỗi định dạng/thiếu
        try {
            if (orderIdParam != null && !orderIdParam.isEmpty()) {
                orderId = Integer.parseInt(orderIdParam);
            } else {
                errorMessage = "Thiếu ID đơn hàng.";
            }

            if (totalAmountParam == null || totalAmountParam.isEmpty()) {
                errorMessage = "Thiếu tổng tiền."; // Có thể muốn kiểm tra định dạng số cho totalAmountParam nếu nó là số
            }
            
            if (statusIdParam != null && !statusIdParam.isEmpty()) {
                statusId = Integer.parseInt(statusIdParam);
            } else {
                errorMessage = "Thiếu trạng thái đơn hàng.";
            }
            
            if (shipperIdParam != null && !shipperIdParam.isEmpty() && !shipperIdParam.equals("0")) { 
                shipperId = Integer.parseInt(shipperIdParam);
            }
        } catch (NumberFormatException e) {
            errorMessage = "Định dạng số không hợp lệ trong dữ liệu cập nhật.";
            System.err.println("OrderDetailServlet (POST): Lỗi parse số: " + e.getMessage());
        }

        // Bước 2: Nếu có lỗi trong quá trình parse hoặc thiếu tham số, hiển thị lại form
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            System.err.println("OrderDetailServlet (POST): Error during initial parameter parsing/validation: " + errorMessage);

            // Cố gắng tải lại dữ liệu hiện có để hiển thị lại form
            OrderDAO orderDAO = new OrderDAO();
            if (orderId != -1) { // Chỉ tải lại nếu orderId đã được parse hợp lệ
                request.setAttribute("order", orderDAO.getOrderDetailById(orderId));
                request.setAttribute("orderItems", orderDAO.getOrderItemsByOrderId(orderId));
                request.setAttribute("shippers", orderDAO.getAllShippers());
                request.setAttribute("statuses", orderDAO.getAllOrderStatuses());
                request.getRequestDispatcher("/DashMin/orderEdit.jsp").forward(request, response);
            } else {
                // Nếu cả orderId cũng không hợp lệ, không thể hiển thị form chỉnh sửa, chuyển hướng về trang lỗi
                request.getRequestDispatcher("/DashMin/error.jsp").forward(request, response);
            }
            return; // Dừng xử lý tại đây
        }

        // Bước 3: Nếu không có lỗi nào ở trên, tiến hành cập nhật đơn hàng
        OrderDAO orderDAO = new OrderDAO();
        boolean updated = orderDAO.updateOrder(orderId, totalAmountParam, statusId, shipperId);

        if (updated) {
            System.out.println("OrderDetailServlet (POST): Đơn hàng ID " + orderId + " đã cập nhật thành công.");
            response.sendRedirect(request.getContextPath() + "/orderDetail?orderId=" + orderId + "&successMessage=Đơn hàng đã được cập nhật thành công!");
        } else {
            System.err.println("OrderDetailServlet (POST): Cập nhật thất bại cho đơn hàng ID " + orderId + ".");
            request.setAttribute("errorMessage", "Cập nhật đơn hàng thất bại. Vui lòng thử lại.");
            // Tải lại dữ liệu để hiển thị form nếu cập nhật database thất bại
            Order order = orderDAO.getOrderDetailById(orderId);
            List<OrderDetail> orderItems = orderDAO.getOrderItemsByOrderId(orderId); 
            List<User> shippers = orderDAO.getAllShippers();
            List<OrderStatus> statuses = orderDAO.getAllOrderStatuses();

            request.setAttribute("order", order);
            request.setAttribute("orderItems", orderItems); 
            request.setAttribute("shippers", shippers);
            request.setAttribute("statuses", statuses);
            request.getRequestDispatcher("/DashMin/orderEdit.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for displaying and editing order details";
    }
}
