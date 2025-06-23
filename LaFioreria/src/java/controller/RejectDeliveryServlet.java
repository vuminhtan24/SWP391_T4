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
import java.nio.file.Paths;
import model.User;

/**
 *
 * @author VU MINH TAN
 */
@WebServlet("/DashMin/RejectDeliveryServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class RejectDeliveryServlet extends HttpServlet {
   
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
            out.println("<title>Servlet RejectDeliveryServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RejectDeliveryServlet at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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
        try {
            // Lấy Part của file đã upload
            Part filePart = request.getPart("cancellationImage"); // "cancellationImage" là tên của input type="file" trong form HTML
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Lấy tên file gốc

            if (fileName != null && !fileName.isEmpty()) {
                // Định nghĩa thư mục lưu trữ ảnh trên server
                // KHUYẾN CÁO: Luôn sử dụng một đường dẫn tuyệt đối an toàn bên ngoài thư mục project
                // Ví dụ: /path/to/your/upload/directory/images/cancellations
                // Để đơn giản cho ví dụ này, tôi sẽ lưu vào thư mục webapp/uploads/cancellations
                String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + "cancellations";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
                }

                // Tạo tên file duy nhất để tránh trùng lặp
                // Có thể thêm timestamp hoặc UUID vào tên file
                String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                String filePath = uploadPath + File.separator + uniqueFileName;
                filePart.write(filePath); // Lưu file vào server

                // Lưu đường dẫn tương đối vào database
                // Đảm bảo rằng đường dẫn này có thể truy cập được từ client (qua URL)
                cancellationImagePath = request.getContextPath() + "/uploads/cancellations/" + uniqueFileName;
            }
        } catch (Exception e) {
            System.err.println("Error uploading cancellation image: " + e.getMessage());
            e.printStackTrace();
            // Xử lý lỗi upload ảnh, có thể redirect về trang lỗi
            response.sendRedirect("shipperOrderDetails?orderId=" + orderId + "&error=image_upload_failed");
            return;
        }

        // Gọi hàm DAO đã cập nhật: cancelOrder(orderId, reason, cancellationImagePath)
        OrderDAO dao = new OrderDAO();
        // Giả sử bạn đã đổi tên hàm trong OrderDAO thành cancelOrder
        boolean success = dao.rejectOrder(orderId, reason, cancellationImagePath);

        if (success) {
            response.sendRedirect("shipperOrderDetails?orderId=" + orderId + "&success=rejected");
        } else {
            // Nếu có lỗi, cũng truyền lại đường dẫn ảnh (nếu có) để dễ debug hoặc hiển thị
            response.sendRedirect("shipperOrderDetails?orderId=" + orderId + "&error=reject_failed");
        }
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
