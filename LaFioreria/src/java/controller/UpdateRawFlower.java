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
        RawFlowerDAO rf = new RawFlowerDAO();
        int raw_id = Integer.parseInt(request.getParameter("raw_id"));
        request.setAttribute("item", rf.getRawFlowerById(raw_id)); // Lấy thông tin sản phẩm, bao gồm cả description
        request.getRequestDispatcher("updaterawflower.jsp").forward(request, response); // Chuyển tiếp đến trang JSP để hiển thị
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
        response.sendRedirect("update_flower?id=" + raw_id);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating Perfume information including description";
    }

}
