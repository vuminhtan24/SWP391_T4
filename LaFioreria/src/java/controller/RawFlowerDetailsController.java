package controller;

import dal.FlowerTypeDAO;
import dal.FlowerBatchDAO;
import dal.WarehouseDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.FlowerType;
import model.FlowerBatch;
import model.Warehouse;

/**
 * Servlet để hiển thị chi tiết thông tin nguyên liệu (Raw Flower) dựa trên ID.
 */
@WebServlet(name = "RawFlowerDetailsController", urlPatterns = {"/rawFlowerDetails"})
public class RawFlowerDetailsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            // Log để debug
            System.out.println("RawFlowerDetailsController: Processing request");

            // Lấy tham số flower_id
            String flowerIdStr = request.getParameter("flower_id");
            if (flowerIdStr == null || flowerIdStr.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Flower type ID not provided.");
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
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid flower type ID: " + e.getMessage());
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

            // Lấy chi tiết loại hoa
            FlowerTypeDAO ftDAO = new FlowerTypeDAO();
            FlowerType flowerType = ftDAO.getFlowerTypeById(flowerId);
            if (flowerType == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Flower type with ID " + flowerId + " not found.");
                return;
            }

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

            // Lấy danh sách kho
            WarehouseDAO whDAO = new WarehouseDAO();
            List<Warehouse> warehouses = whDAO.getAllWarehouse();
            if (warehouses == null || warehouses.isEmpty()) {
                System.out.println("No warehouses found.");
            }

            // Đặt thuộc tính cho JSP
            request.setAttribute("item", flowerType);
            request.setAttribute("flowerBatches", batchPage);
            request.setAttribute("warehouses", warehouses);
            request.setAttribute("sortField", sortField);
            request.setAttribute("sortDir", sortDir);
            request.setAttribute("warehouseFilter", warehouseFilter);
            request.setAttribute("statusFilter", statusFilter);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);

            // Chuyển tiếp đến rawflowerdetails.jsp
            request.getRequestDispatcher("DashMin/rawflowerdetails.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                    "An error occurred while retrieving flower type details: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, 
                "POST method is not supported for flower type details.");
    }

    @Override
    public String getServletInfo() {
        return "Servlet to display detailed information of a flower type based on ID with batch sorting and filtering.";
    }
}