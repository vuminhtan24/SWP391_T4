package controller;

import dal.FlowerBatchDAO;
import dal.WarehouseDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.FlowerBatch;
import model.Warehouse;

/**
 * Servlet để lấy danh sách lô hoa theo loại hoa dựa trên ID và render bảng.
 */
@WebServlet(name = "BatchController", urlPatterns = {"/batch"})
public class BatchController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            System.out.println("BatchController: Processing request for flower_id");

            // Lấy tham số flower_id
            String flowerIdStr = request.getParameter("flower_id");
            if (flowerIdStr == null || flowerIdStr.trim().isEmpty()) {
                request.setAttribute("error", "ID loại hoa không được cung cấp.");
                request.getRequestDispatcher("DashMin/flowerbatch.jsp").forward(request, response);
                return;
            }

            // Chuyển đổi flower_id
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

            // Lấy tham số sort
            String sortField = request.getParameter("sortField");
            String sortDir = request.getParameter("sortDir");
            if (sortField == null || !sortField.matches("batchId|unitPrice|importDate|expirationDate|quantity")) {
                sortField = "batchId"; // Default sort field
            }
            if (!"desc".equalsIgnoreCase(sortDir)) {
                sortDir = "asc";
            }

            // Lấy tham số lọc
            String warehouseFilter = request.getParameter("warehouseFilter");
            String statusFilter = request.getParameter("statusFilter");

            // Kiểm tra tham số lọc
            boolean hasWarehouse = (warehouseFilter != null && !warehouseFilter.trim().isEmpty());
            boolean hasStatus = (statusFilter != null && !statusFilter.trim().isEmpty());

            // Log để debug
            System.out.println("Received flower_id: " + flowerId);
            System.out.println("Received warehouseFilter: " + warehouseFilter);
            System.out.println("Received statusFilter: " + statusFilter);

            // Lấy danh sách lô hoa
            FlowerBatchDAO fbDAO = new FlowerBatchDAO();
            List<FlowerBatch> flowerBatches = fbDAO.getFlowerBatchesByFlowerId(flowerId);

            System.out.println("Before filtering, size: " + flowerBatches.size());

            // Lọc danh sách
            if (hasWarehouse || hasStatus) {
                String searchWarehouse = hasWarehouse ? warehouseFilter.trim() : null;
                String searchStatus = hasStatus ? statusFilter.trim() : null;

                flowerBatches = flowerBatches.stream()
                        .filter(b -> {
                            boolean matchWarehouse = !hasWarehouse || (b.getWarehouse() != null && b.getWarehouse().getName() != null
                                    && b.getWarehouse().getName().equalsIgnoreCase(searchWarehouse));
                            boolean matchStatus = !hasStatus || (b.getStatus() != null && b.getStatus().equalsIgnoreCase(searchStatus));
                            return matchWarehouse && matchStatus;
                        })
                        .toList();
                System.out.println("After filtering, size: " + flowerBatches.size());
            }

            // Phân trang
            int pageSize = 6; // Số lô hoa mỗi trang
            int currentPage = 1;
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                try {
                    currentPage = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    currentPage = 1;
                }
            }

            int totalItems = flowerBatches.size();
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);

            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, totalItems);
            List<FlowerBatch> batchPage = totalItems > 0 ? flowerBatches.subList(start, end) : new ArrayList<>();

            // Sắp xếp
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
            flowerBatches = flowerBatches.stream().sorted(cmp).toList();
            batchPage = totalItems > 0 ? flowerBatches.subList(start, end) : new ArrayList<>();

            // Lấy danh sách kho từ WarehouseDAO
            WarehouseDAO warehouseDAO = new WarehouseDAO();
            List<Warehouse> warehouses = warehouseDAO.getAllWarehouse();
            if (warehouses == null || warehouses.isEmpty()) {
                System.out.println("No warehouses found.");
            }

            // Đặt thuộc tính cho JSP
            request.setAttribute("flowerBatches", batchPage);
            request.setAttribute("warehouses", warehouses);
            request.setAttribute("sortField", sortField);
            request.setAttribute("sortDir", sortDir);
            request.setAttribute("warehouseFilter", warehouseFilter);
            request.setAttribute("statusFilter", statusFilter);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);

            // Chuyển tiếp đến JSP
            request.getRequestDispatcher("DashMin/flowerbatch.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi khi lấy danh sách lô hoa: " + e.getMessage());
            request.getRequestDispatcher("DashMin/flowerbatch.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, 
                "Phương thức POST không được hỗ trợ cho danh sách lô hoa.");
    }

    @Override
    public String getServletInfo() {
        return "Servlet để lấy danh sách lô hoa theo loại hoa dựa trên ID với chức năng sắp xếp và lọc.";
    }
}