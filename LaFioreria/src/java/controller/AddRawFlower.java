/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import dal.RawFlowerDAO;
import dal.WarehouseDAO;
import util.Validate;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AddRawFlower", urlPatterns = {"/addRawFlower"})
public class AddRawFlower extends HttpServlet {

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        request.getRequestDispatcher("DashMin/rawflower2").forward(request, response);
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
        try {
            // Lấy tham số từ form
            String rawName = request.getParameter("rawName");
            String unitPriceStr = request.getParameter("unitPrice");
            String importPriceStr = request.getParameter("importPrice");
            String imageUrl = request.getParameter("imageUrl");
            String warehouseIdStr = request.getParameter("warehouseId");

            // Khởi tạo WarehouseDAO
            WarehouseDAO wh = new WarehouseDAO();

            // Validate các field
            String rawNameError = Validate.validateText(rawName, "Raw Flower Name");
            String unitPriceError = Validate.validateNumberWithRange(unitPriceStr, "Unit Price", 1, Integer.MAX_VALUE);
            String importPriceError = Validate.validateNumberWithRange(importPriceStr, "Import Price", 1, Integer.MAX_VALUE);
            String imageUrlError = Validate.validateImageUrl(imageUrl);
            String warehouseIdError = Validate.validateWarehouseId(warehouseIdStr, wh);

            // Lưu dữ liệu vào session để giữ giá trị đã nhập
            HttpSession session = request.getSession();
            session.setAttribute("rawName", rawName);
            session.setAttribute("unitPrice", unitPriceStr);
            session.setAttribute("importPrice", importPriceStr);
            session.setAttribute("imageUrl", imageUrl);
            session.setAttribute("warehouseId", warehouseIdStr);

            // Nếu có lỗi, lưu thông báo lỗi vào session và chuyển hướng
            if (rawNameError != null || unitPriceError != null || importPriceError != null || 
                imageUrlError != null || warehouseIdError != null) {
                session.setAttribute("rawNameError", rawNameError);
                session.setAttribute("unitPriceError", unitPriceError);
                session.setAttribute("importPriceError", importPriceError);
                session.setAttribute("imageUrlError", imageUrlError);
                session.setAttribute("warehouseIdError", warehouseIdError);
                response.sendRedirect("DashMin/rawflower2");
                return;
            }

            // Xóa các lỗi và dữ liệu trong session nếu validate thành công
            session.removeAttribute("rawNameError");
            session.removeAttribute("unitPriceError");
            session.removeAttribute("importPriceError");
            session.removeAttribute("imageUrlError");
            session.removeAttribute("warehouseIdError");
            session.removeAttribute("rawName");
            session.removeAttribute("unitPrice");
            session.removeAttribute("importPrice");
            session.removeAttribute("imageUrl");
            session.removeAttribute("warehouseId");

            // Chuyển đổi dữ liệu
            int unitPrice = Integer.parseInt(unitPriceStr);
            int importPrice = Integer.parseInt(importPriceStr);
            int warehouseId = Integer.parseInt(warehouseIdStr);

            // Gọi phương thức DAO để thêm nguyên liệu
            RawFlowerDAO rf = new RawFlowerDAO();
            rf.addRawFlower1(rawName, unitPrice, warehouseId, imageUrl, importPrice);

            // Thông báo thành công và chuyển hướng
            session.setAttribute("message", "Raw flower added successfully!");
            response.sendRedirect("DashMin/rawflower2");
        } catch (Exception e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("error", "An error occurred while adding the raw flower.");
            response.sendRedirect("DashMin/rawflower2");
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
