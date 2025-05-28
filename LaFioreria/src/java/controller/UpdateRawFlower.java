/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.RawFlowerDAO;
import java.sql.Date;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UpdateRawFlower", urlPatterns = {"/update_flower"})
public class UpdateRawFlower extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        try {
//            String rawIdParam = request.getParameter("id");
//            if (rawIdParam == null || rawIdParam.trim().isEmpty()) {
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is missing or empty.");
//                return;
//            }
//
//            int raw_id = Integer.parseInt(rawIdParam);
//            if (raw_id <= 0) {
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID must be a positive integer.");
//                return;
//            }
//
//            RawFlowerDAO rf = new RawFlowerDAO();
//            Object item = rf.getRawFlowerById(raw_id);
//            if (item == null) {
//                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found with ID: " + raw_id);
//                return;
//            }
//
//            request.setAttribute("item", item);
//            request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
//        } catch (NumberFormatException e) {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID format: " + e.getMessage());
//        } catch (Exception e) {
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
//        }
        RawFlowerDAO rf = new RawFlowerDAO();
        int raw_id = Integer.parseInt(request.getParameter("raw_id"));
        request.setAttribute("item", rf.getRawFlowerById(raw_id)); // Lấy thông tin sản phẩm, bao gồm cả description
        request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response); // Chuyển tiếp đến trang JSP để hiển thị

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int raw_id = Integer.parseInt(request.getParameter("raw_id"));
        String raw_name = request.getParameter("raw_name");
        int raw_quantity = Integer.parseInt(request.getParameter("raw_quantity"));
        int unitPrice = Integer.parseInt(request.getParameter("unit_price"));
        String expirationDate = request.getParameter("expiration_date");
        Date expiration_date = Date.valueOf(expirationDate);
        int importPrice = Integer.parseInt(request.getParameter("import_price"));
        int warehouse_id = Integer.parseInt(request.getParameter("warehouse_id"));
        int hold = Integer.parseInt(request.getParameter("hold"));
        String imageUrl = request.getParameter("image_url"); // Lấy thông tin description từ form

        // Cập nhật sản phẩm trong CSDL bao gồm cả description
        RawFlowerDAO rf = new RawFlowerDAO();
        rf.updateRawFlower4(raw_id, raw_name, raw_quantity, unitPrice, expiration_date, warehouse_id, imageUrl, hold, importPrice);

        // Chuyển hướng sau khi cập nhật thành công
        response.sendRedirect("update_flower?raw_id=" + raw_id);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating Perfume information including description";
    }

}
