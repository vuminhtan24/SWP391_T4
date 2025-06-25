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
@WebServlet(name = "RejectDeliveryServlet", urlPatterns = {"/DashMin/RejectDeliveryServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = -1L,
        maxRequestSize = -1L
)
public class RejectDeliveryServlet extends HttpServlet {

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
            out.println("<title>Servlet RejectDeliveryServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RejectDeliveryServlet at " + request.getContextPath() + "</h1>");
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

        // Kiểm tra quyền (giả sử role 8 là shipper hoặc người có quyền từ chối)
        if (user == null || user.getRole() != 8) {
            response.sendRedirect(request.getContextPath() + "/ZeShopper/LoginServlet?error=unauthorized");
            return;
        }

        String orderIdStr = request.getParameter("orderId");
        // Lấy lý do từ chối
        String reason = request.getParameter("rejectReason");
        int orderId;

        try {
            orderId = Integer.parseInt(orderIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("shipperOrderDetails?orderId=" + orderIdStr + "&error=invalid_order");
            return;
        }

        String cancellationImagePath = null; // Biến để lưu đường dẫn ảnh
         // Nhận file ảnh
        Part filePart = request.getPart("cancellationImage");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Đường dẫn lưu file thực tế (ví dụ: /uploads/)
        String uploadPath = getServletContext().getRealPath("/upload/RejectDeliveryIMG/");
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
        boolean success = dao.rejectOrder(orderId, reason,"/upload/RejectDeliveryIMG/" + fileName);



        if (success) {
            response.sendRedirect("shipperOrderDetails?orderId=" + orderId + "&success=rejected");
        } else {
            // Nếu có lỗi, cũng truyền lại đường dẫn ảnh (nếu có) để dễ debug hoặc hiển thị
            response.sendRedirect("shipperOrderDetails?orderId=" + orderId + "&error=reject_failed");
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
