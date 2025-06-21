/*
 * Click nbproject/project.properties to edit this template
 */
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

    /**
     * Xử lý HTTP <code>GET</code> để hiển thị chi tiết nguyên liệu.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException nếu xảy ra lỗi liên quan đến servlet
     * @throws IOException      nếu xảy ra lỗi I/O
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            // Get flower_id parameter from request
            String flowerIdStr = request.getParameter("flower_id");
            if (flowerIdStr == null || flowerIdStr.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Flower type ID not provided.");
                return;
            }

            // Convert flower_id to integer
            int flowerId;
            try {
                flowerId = Integer.parseInt(flowerIdStr);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid flower type ID.");
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

            // Get warehouse filter
            String warehouseFilter = request.getParameter("warehouseFilter");
            if (warehouseFilter != null) {
                warehouseFilter = warehouseFilter.trim(); // Gán một lần và không thay đổi sau đó
            } else {
                warehouseFilter = ""; // Gán mặc định nếu null
            }

            // Initialize DAO and get flower type details
            FlowerTypeDAO ftDAO = new FlowerTypeDAO();
            FlowerType flowerType = ftDAO.getFlowerTypeById(flowerId);
            
            FlowerBatchDAO fbDAO = new FlowerBatchDAO();
            List<FlowerBatch> flowerBatches = fbDAO.getFlowerBatchesByFlowerId(flowerId);

            // Filter by warehouse if specified
            if (warehouseFilter != null && !warehouseFilter.trim().isEmpty()) {
                String filter = warehouseFilter.trim();
                flowerBatches.removeIf(batch -> {
                    if (batch == null) {
                        return true; // hoặc false nếu muốn giữ batch null
                    }
                    Warehouse w = batch.getWarehouse();
                    if (w == null || w.getName() == null) {
                        return true;
                    }
                    return !filter.equals(w.getName().trim());
                });
            }

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

            // Get list of warehouses from DAO
            WarehouseDAO whDAO = new WarehouseDAO();
            List<Warehouse> warehouses = whDAO.getAllWarehouse();

            // Check if flower type exists
            if (flowerType == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Flower type with ID " + flowerId + " not found.");
                return;
            }

            // Set attributes for JSP
            request.setAttribute("item", flowerType);
            request.setAttribute("flowerBatches", flowerBatches);
            request.setAttribute("sortField", sortField);
            request.setAttribute("sortDir", sortDir);
            request.setAttribute("warehouses", warehouses);

            // Forward to JSP for displaying details
            request.getRequestDispatcher("DashMin/rawflowerdetails.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                    "An error occurred while retrieving flower type details: " + e.getMessage());
        }
    }

    /**
     * Handles HTTP <code>POST</code>. Currently does not support updating flower type details.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, 
                "POST method is not supported for flower type details.");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet to display detailed information of a flower type based on ID with batch sorting and warehouse filtering.";
    }
}