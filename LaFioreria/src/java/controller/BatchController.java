/*
 * Click nbproject/project.properties to edit this template
 */
package controller;

import dal.FlowerBatchDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Comparator;
import model.FlowerBatch;

/**
 * Servlet để lấy danh sách lô hoa theo loại hoa dựa trên ID và render bảng.
 */
@WebServlet(name = "BatchController", urlPatterns = {"/batch"})
public class BatchController extends HttpServlet {

    /**
     * Xử lý HTTP <code>GET</code> để lấy danh sách lô hoa và chuyển tiếp đến JSP fragment.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu xảy ra lỗi servlet
     * @throws IOException nếu xảy ra lỗi I/O
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            // Log để debug
            System.out.println("BatchController: Processing request for flower_id");

            // Lấy tham số flower_id từ request
            String flowerIdStr = request.getParameter("flower_id");
            if (flowerIdStr == null || flowerIdStr.trim().isEmpty()) {
                request.setAttribute("error", "ID loại hoa không được cung cấp.");
                request.getRequestDispatcher("DashMin/flowerbatch.jsp").forward(request, response);
                return;
            }

            // Chuyển đổi flower_id thành số nguyên
            int flowerId;
            try {
                flowerId = Integer.parseInt(flowerIdStr);
                if (flowerId <= 0) {
                    throw new NumberFormatException("ID loại hoa phải là số dương.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID loại hoa không hợp lệ: " + e.getMessage());
                request.getRequestDispatcher("DashMin/flowerbatch.jsp").forward(request, response);
                return;
            }

            // Get sort parameters
            String sortField = request.getParameter("sortField");
            String sortDir = request.getParameter("sortDir");
            if (sortField == null || !sortField.matches("batchId|unitPrice|importDate|expirationDate|quantity")) {
                sortField = "batchId"; // Default sort field
            }
            if (!"desc".equalsIgnoreCase(sortDir)) {
                sortDir = "asc";
            }

            // Lấy danh sách lô hoa theo flower_id
            FlowerBatchDAO fbDAO = new FlowerBatchDAO();
            List<FlowerBatch> flowerBatches = fbDAO.getFlowerBatchesByFlowerId(flowerId);

            // Sorting
            Comparator<FlowerBatch> cmp;
            switch (sortField) {
                case "unitPrice":
                    cmp = Comparator.comparing(FlowerBatch::getUnitPrice);
                    break;
                case "importDate":
                    cmp = Comparator.comparing(FlowerBatch::getImportDate);
                    break;
                case "expirationDate":
                    cmp = Comparator.comparing(FlowerBatch::getExpirationDate);
                    break;
                case "quantity":
                    cmp = Comparator.comparing(FlowerBatch::getQuantity);
                    break;
                default: // batchId
                    cmp = Comparator.comparingInt(FlowerBatch::getBatchId);
                    break;
            }
            if ("desc".equalsIgnoreCase(sortDir)) {
                cmp = cmp.reversed();
            }
            flowerBatches.sort(cmp);

            // Đặt thuộc tính cho JSP
            request.setAttribute("flowerBatches", flowerBatches);
            request.setAttribute("sortField", sortField);
            request.setAttribute("sortDir", sortDir);

            // Chuyển tiếp đến JSP fragment
            request.getRequestDispatcher("DashMin/flowerbatch.jsp").forward(request, response);
        } catch (Exception e) {
            // Log lỗi chi tiết
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi khi lấy danh sách lô hoa: " + e.getMessage());
            request.getRequestDispatcher("DashMin/flowerbatch.jsp").forward(request, response);
        }
    }

    /**
     * Xử lý HTTP <code>POST</code>. Hiện tại không hỗ trợ POST.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException nếu xảy ra lỗi servlet
     * @throws IOException      nếu xảy ra lỗi I/O
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, 
                "Phương thức POST không được hỗ trợ cho danh sách lô hoa.");
    }

    /**
     * Trả về mô tả ngắn của servlet.
     *
     * @return Chuỗi chứa mô tả servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet để lấy danh sách lô hoa theo loại hoa dựa trên ID với chức năng sắp xếp.";
    }
}