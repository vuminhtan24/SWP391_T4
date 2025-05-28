/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
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
        
        String name = request.getParameter("rawName");
        int unitPrice = Integer.parseInt(request.getParameter("unitPrice"));
        int importPrice = Integer.parseInt(request.getParameter("importPrice"));
        String imageUrl = request.getParameter("imageUrl");
        int warehouseId = Integer.parseInt(request.getParameter("warehouseId"));
        // Gọi phương thức DAO với đúng thứ tự tham số
        RawFlowerDAO rf = new RawFlowerDAO();
        rf.addRawFlower1(name, unitPrice, warehouseId, imageUrl, importPrice); // truyền tên file và description vào

        response.sendRedirect("DashMin/rawflower2");
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
