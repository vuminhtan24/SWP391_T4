/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Order;
import model.OrderStatus;
import model.User;

/**
 *
 * @author VU MINH TAN
 */
@WebServlet(name = "OrderDetailServlet", urlPatterns = {"/orderDetail"}) 
public class OrderDetailServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet OrderDetailServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderDetailServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrderDAO orderDAO = new OrderDAO();
        String orderIdParam = request.getParameter("orderId");
        int orderId = 0;

        // Kiểm tra và parse Order ID
        if (orderIdParam != null && !orderIdParam.isEmpty()) {
            try {
                orderId = Integer.parseInt(orderIdParam);
            } catch (NumberFormatException e) {
                // Xử lý lỗi nếu orderId không phải là số: chuyển hướng về trang danh sách
                System.err.println("Order ID không hợp lệ: " + orderIdParam + ". Chuyển hướng về trang quản lý.");
                response.sendRedirect(request.getContextPath() + "/orderManagement");
                return;
            }
        } else {
            // Nếu không có Order ID được cung cấp, chuyển hướng về trang danh sách
            System.err.println("Không có Order ID được cung cấp. Chuyển hướng về trang quản lý.");
            response.sendRedirect(request.getContextPath() + "/orderManagement");
            return;
        }

        // Lấy chi tiết đơn hàng, danh sách shippers và statuses từ DAO
        Order order = orderDAO.getOrderDetailById(orderId);
        List<User> shippers = orderDAO.getAllShippers();
        List<OrderStatus> statuses = orderDAO.getAllOrderStatuses();

        // Đặt các đối tượng vào request scope để JSP có thể truy cập
        request.setAttribute("order", order);
        request.setAttribute("shippers", shippers);
        request.setAttribute("statuses", statuses);

        // Chuyển tiếp yêu cầu đến trang JSP để hiển thị chi tiết
        request.getRequestDispatcher("/DashMin/orderDetail.jsp").forward(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrderDAO orderDAO = new OrderDAO();

        String orderIdParam = request.getParameter("orderId");
        String newStatusIdParam = request.getParameter("newStatusId");
        String shipperIdParam = request.getParameter("shipperId"); // Có thể là chuỗi rỗng nếu chọn "-- Chọn Shipper --"

        int orderId = 0;
        int newStatusId = 0;
        Integer shipperId = null; // Sử dụng Integer để có thể lưu trữ null nếu không chọn shipper

        try {
            orderId = Integer.parseInt(orderIdParam);
            newStatusId = Integer.parseInt(newStatusIdParam);
            // Kiểm tra shipperIdParam có rỗng không trước khi parse
            if (shipperIdParam != null && !shipperIdParam.isEmpty()) {
                shipperId = Integer.parseInt(shipperIdParam);
            }
        } catch (NumberFormatException e) {
            System.err.println("Lỗi parse dữ liệu đầu vào khi cập nhật đơn hàng: " + e.getMessage());
            request.getSession().setAttribute("errorMessage", "Dữ liệu đầu vào không hợp lệ. Vui lòng kiểm tra lại.");
            response.sendRedirect(request.getContextPath() + "/orderDetail?orderId=" + orderIdParam);
            return;
        }

        boolean statusUpdated = orderDAO.updateOrderStatus(orderId, newStatusId);
        boolean shipperAssigned = orderDAO.assignShipper(orderId, shipperId);

        if (statusUpdated || shipperAssigned) { // Chỉ cần một trong hai thành công là coi là cập nhật
            request.getSession().setAttribute("successMessage", "Cập nhật đơn hàng thành công!");
        } else {
            request.getSession().setAttribute("errorMessage", "Cập nhật đơn hàng thất bại. Vui lòng kiểm tra lại.");
        }

        // Chuyển hướng người dùng trở lại trang chi tiết đơn hàng sau khi cập nhật
        response.sendRedirect(request.getContextPath() + "/orderDetail?orderId=" + orderId);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
