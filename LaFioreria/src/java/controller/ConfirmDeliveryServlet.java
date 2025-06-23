/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import model.User;

/**
 *
 * @author VU MINH TAN
 */
@WebServlet(name = "ConfirmDeliveryServlet", urlPatterns = {"/DashMin/ConfirmDeliveryServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB lưu tạm vào disk nếu vượt
        maxFileSize = -1L, // -1 = không giới hạn kích thước mỗi file
        maxRequestSize = -1L // -1 = không giới hạn tổng kích thước request
)
public class ConfirmDeliveryServlet extends HttpServlet {

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
            out.println("<title>Servlet ConfirmDeliveryServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConfirmDeliveryServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentAcc");

        if (user == null || user.getRole() != 8) {
            System.out.println("Redirecting to: " + request.getContextPath() + "/ZeShopper/LoginServlet");
            response.sendRedirect(request.getContextPath() + "/ZeShopper/LoginServlet?error=unauthorized");
            return;
        }

        String orderIdStr = request.getParameter("orderId");
        int orderId;

        try {
            orderId = Integer.parseInt(orderIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("shipperOrderDetails?orderId=" + orderIdStr + "&error=invalid_order");
            return;
        }

        // Nhận file ảnh
        Part filePart = request.getPart("deliveryProof");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Đường dẫn lưu file thực tế (ví dụ: /uploads/)
        String uploadPath = getServletContext().getRealPath("/upload/ConfirmDeliveryIMG");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String savedFilePath = uploadPath + File.separator + fileName;
        filePart.write(savedFilePath);
        // 2) Copy từ build folder → folder dự án (root của webapp)
        //    rootPath sẽ là đường dẫn thực sự đến folder webapp/upload/BouquetIMG
        String rootPath = savedFilePath.replace("\\build", "");
        Path source = Paths.get(savedFilePath);
        Path target = Paths.get(rootPath);
        // Tạo thư mục nếu chưa có
                Files.createDirectories(target.getParent());
                // Copy file (thay thế nếu đã tồn tại)
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);


        // Update trạng thái đơn hàng và lưu đường dẫn ảnh
        OrderDAO dao = new OrderDAO();
        boolean success = dao.markDelivered(orderId, "/upload/ConfirmDeliveryIMG" + fileName);

        if (success) {
            response.sendRedirect("shipperOrderDetails?orderId=" + orderId + "&success=delivered");

        } else {
            response.sendRedirect("shipperOrderDetails?orderId=" + orderId + "&error=delivery_failed");
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
