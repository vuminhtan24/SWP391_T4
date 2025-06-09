/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbproject/project.properties to edit this template
 */
package controller;

import dal.RawFlowerDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.RawFlower;

/**
 * Servlet để hiển thị chi tiết thông tin nguyên liệu (Raw Flower) dựa trên ID.
 */
@WebServlet(name = "RawFlowerDetailsController", urlPatterns = {"/rawFlowerDetails"})
public class RawFlowerDetailsController extends HttpServlet {

    /**
     * Xử lý HTTP <code>GET</code> để hiển thị chi tiết nguyên liệu.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException nếu xảy ra lỗi liên quan đến servlet
     * @throws IOException      nếu xảy ra lỗi I/O
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            // Lấy tham số raw_id từ request
            String rawIdStr = request.getParameter("raw_id");
            if (rawIdStr == null || rawIdStr.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã nguyên liệu không được cung cấp.");
                return;
            }

            // Chuyển đổi raw_id thành số nguyên
            int rawId;
            try {
                rawId = Integer.parseInt(rawIdStr);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã nguyên liệu không hợp lệ.");
                return;
            }

            // Khởi tạo DAO và lấy chi tiết nguyên liệu
            RawFlowerDAO rf = new RawFlowerDAO();
            RawFlower rawFlower = rf.getRawFlowerById(rawId);

            // Kiểm tra nguyên liệu có tồn tại không
            if (rawFlower == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy nguyên liệu với mã " + rawId + ".");
                return;
            }

            // Đặt thuộc tính cho JSP
            request.setAttribute("item", rawFlower);

            // Chuyển tiếp đến JSP hiển thị chi tiết
            request.getRequestDispatcher("DashMin/rawflowerdetails.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                    "Đã xảy ra lỗi khi lấy chi tiết nguyên liệu: " + e.getMessage());
        }
    }

    /**
     * Xử lý HTTP <code>POST</code>. Hiện tại không hỗ trợ cập nhật chi tiết nguyên liệu.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException nếu xảy ra lỗi liên quan đến servlet
     * @throws IOException      nếu xảy ra lỗi I/O
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, 
                "Phương thức POST không được hỗ trợ cho chi tiết nguyên liệu.");
    }

    /**
     * Trả về mô tả ngắn của servlet.
     *
     * @return Chuỗi chứa mô tả servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet để hiển thị chi tiết thông tin nguyên liệu dựa trên ID.";
    }
}