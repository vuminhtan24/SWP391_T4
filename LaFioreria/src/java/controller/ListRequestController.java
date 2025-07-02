/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import model.Bouquet;
import model.Category;
import model.BouquetRaw;
import dal.BouquetDAO;
import dal.CategoryDAO;
import dal.FlowerBatchDAO;
import dal.FlowerTypeDAO;
import dal.OrderDAO;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.mail.Session;
import model.BouquetImage;
import model.FlowerBatch;
import model.FlowerType;
import model.RequestDisplay;
import model.RequestFlower;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ListRequestController", urlPatterns = {"/listRequest"})
public class ListRequestController extends HttpServlet {

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
            out.println("<title>Servlet ListRequestController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListRequestController at " + request.getContextPath() + "</h1>");
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
        OrderDAO odao = new OrderDAO();

// Lấy tham số từ request
        String flowerNameSearch = request.getParameter("flowerNameSearch");
        String requestDateStr = request.getParameter("requestDate");
        String confirmDateStr = request.getParameter("confirmDate");
        String status = request.getParameter("status");

// Khởi tạo biến ngày
        Date requestDate = null;
        Date confirmDate = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            if (requestDateStr != null && !requestDateStr.isEmpty()) {
                requestDate = sdf.parse(requestDateStr);
            }
            if (confirmDateStr != null && !confirmDateStr.isEmpty()) {
                confirmDate = sdf.parse(confirmDateStr);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Có thể log lỗi hoặc hiển thị thông báo người dùng
        }

// Gọi DAO với tham số lọc
        List<RequestDisplay> listRequest = odao.gettAllRequestList(
                flowerNameSearch,
                requestDate,
                confirmDate,
                (status != null && !status.trim().isEmpty()) ? status : null
        );

// Truyền dữ liệu sang JSP
        request.setAttribute("listRequest", listRequest);
        request.getRequestDispatcher("./DashMin/listRequest.jsp").forward(request, response);
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
        processRequest(request, response);
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
