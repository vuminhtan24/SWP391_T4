/*
 * Click nbproject/project.properties to edit this template
 */
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import dal.FlowerBatchDAO;
import dal.WarehouseDAO;
import model.Warehouse;
import model.FlowerBatch;
import util.Validate;

/**
 * Servlet to handle updating a flower batch.
 */
@WebServlet(name = "UpdateBatchController", urlPatterns = {"/update_batch"})
public class UpdateBatchController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FlowerBatchDAO fbDAO = new FlowerBatchDAO();
        HttpSession session = request.getSession();
        try {
            // Get batch_id from URL parameter
            String batchIdStr = request.getParameter("batch_id");
            System.out.println("UpdateBatchController.doGet: batch_id=" + batchIdStr); // Debug log
            if (batchIdStr == null || batchIdStr.trim().isEmpty()) {
                session.setAttribute("error", "Batch ID is required.");
                response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
                return;
            }

            int batchId = Integer.parseInt(batchIdStr);
            if (batchId <= 0) {
                session.setAttribute("error", "Batch ID must be positive.");
                response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
                return;
            }

            FlowerBatch flowerBatch = fbDAO.getFlowerBatchById(batchId);
            if (flowerBatch == null || flowerBatch.getFlowerId() <= 0) {
                session.setAttribute("error", "Batch with ID " + batchId + " not found or invalid flower ID.");
                response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
                return;
            }

            // Set flower batch for JSP
            request.setAttribute("item", flowerBatch);
            System.out.println("UpdateBatchController.doGet: flowerId=" + flowerBatch.getFlowerId()); // Debug log

            // Get list of warehouses for the form
            WarehouseDAO whDAO = new WarehouseDAO();
            List<Warehouse> warehouses = whDAO.getAllWarehouse();
            if (warehouses == null || warehouses.isEmpty()) {
                System.out.println("Warehouses is null or empty");
                session.setAttribute("error", "No warehouses available.");
                response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
                return;
            } else {
                warehouses.forEach(w -> System.out.println("Warehouse ID: " + w.getWarehouseId() + ", Name: " + w.getName()));
            }
            session.setAttribute("listW", warehouses); // Lưu vào session

            // Forward to updatebatch.jsp
            request.getRequestDispatcher("/DashMin/updatebatch.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.out.println("UpdateBatchController.doGet: Invalid batch ID - " + e.getMessage());
            session.setAttribute("error", "Invalid batch ID.");
            response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        FlowerBatchDAO fbDAO = new FlowerBatchDAO();
        try {
            // Get parameters from the form
            String batchIdStr = request.getParameter("batch_id");
            String unitPriceStr = request.getParameter("unit_price");
            String importDate = request.getParameter("import_date");
            String expirationDate = request.getParameter("expiration_date");
            String quantityStr = request.getParameter("quantity");
            String holdStr = request.getParameter("hold");
            String warehouseIdStr = request.getParameter("warehouse_id");

            // Debug log
            System.out.println("UpdateBatchController.doPost: batch_id=" + batchIdStr +
                    ", unit_price=" + unitPriceStr +
                    ", import_date=" + importDate +
                    ", expiration_date=" + expirationDate +
                    ", quantity=" + quantityStr +
                    ", hold=" + holdStr +
                    ", warehouse_id=" + warehouseIdStr);

            // Validate each field using Validate class
            String batchIdError = Validate.validateNumber(batchIdStr, "Batch ID");
            String unitPriceError = Validate.validateNumberWithRange(unitPriceStr, "Unit Price", 0, Integer.MAX_VALUE);
            String importDateError = Validate.validateDate(importDate, "Import Date");
            String expirationDateError = Validate.validateDate(expirationDate, "Expiration Date");
            String quantityError = Validate.validateNumberWithRange(quantityStr, "Quantity", 0, Integer.MAX_VALUE);
            String holdError = Validate.validateNumberWithRange(holdStr, "Hold", 0, Integer.MAX_VALUE);
            String warehouseIdError = Validate.validateWarehouseId(warehouseIdStr, new WarehouseDAO());

            // Check date relationship (import_date <= expiration_date)
            String dateRelationError = null;
            if (importDateError == null && expirationDateError == null) {
                try {
                    LocalDate importLocalDate = LocalDate.parse(importDate);
                    LocalDate expirationLocalDate = LocalDate.parse(expirationDate);
                    if (importLocalDate.isAfter(expirationLocalDate)) {
                        dateRelationError = "Ngày nhập phải nhỏ hơn hoặc bằng ngày hết hạn.";
                    }
                } catch (Exception e) {
                    dateRelationError = "Lỗi định dạng ngày. Vui lòng kiểm tra lại.";
                }
            }

            // Debug log for validation errors
            System.out.println("Validation errors: batchIdError=" + batchIdError +
                    ", unitPriceError=" + unitPriceError +
                    ", importDateError=" + importDateError +
                    ", expirationDateError=" + expirationDateError +
                    ", quantityError=" + quantityError +
                    ", holdError=" + holdError +
                    ", warehouseIdError=" + warehouseIdError +
                    ", dateRelationError=" + dateRelationError);

            // Check for validation errors
            if (batchIdError != null || unitPriceError != null || importDateError != null ||
                expirationDateError != null || quantityError != null || holdError != null ||
                warehouseIdError != null || dateRelationError != null) {
                // Retain form data
                request.setAttribute("batch_id", batchIdStr);
                request.setAttribute("unit_price", unitPriceStr);
                request.setAttribute("import_date", importDate);
                request.setAttribute("expiration_date", expirationDate);
                request.setAttribute("quantity", quantityStr);
                request.setAttribute("hold", holdStr);
                request.setAttribute("warehouse_id", warehouseIdStr);

                // Set error messages
                request.setAttribute("batchIdError", batchIdError);
                request.setAttribute("unitPriceError", unitPriceError);
                request.setAttribute("importDateError", importDateError);
                request.setAttribute("expirationDateError", expirationDateError);
                request.setAttribute("quantityError", quantityError);
                request.setAttribute("holdError", holdError);
                request.setAttribute("warehouseIdError", warehouseIdError);
                request.setAttribute("dateRelationError", dateRelationError);

                // Set flower batch for JSP to retain status and other fields
                int batchId = batchIdStr != null && !batchIdStr.trim().isEmpty() ? Integer.parseInt(batchIdStr) : 0;
                FlowerBatch flowerBatch = fbDAO.getFlowerBatchById(batchId);
                if (flowerBatch != null) {
                    request.setAttribute("item", flowerBatch);
                } else {
                    request.setAttribute("error", "Batch with ID " + batchId + " not found.");
                }

                // Get list of warehouses for the form
                WarehouseDAO whDAO = new WarehouseDAO();
                List<Warehouse> warehouses = whDAO.getAllWarehouse();
                session.setAttribute("listW", warehouses); // Cập nhật session

                // Forward back to updatebatch.jsp with errors
                request.getRequestDispatcher("/DashMin/updatebatch.jsp").forward(request, response);
                return;
            }

            // Parse validated inputs
            int batchId = Integer.parseInt(batchIdStr);
            int unitPrice = Integer.parseInt(unitPriceStr);
            int quantity = Integer.parseInt(quantityStr);
            int hold = Integer.parseInt(holdStr);
            int warehouseId = Integer.parseInt(warehouseIdStr);

            // Optional: Warn if batch may be deleted by scheduler
            LocalDate currentDate = LocalDate.now();
            LocalDate expDate = LocalDate.parse(expirationDate);
            if (expDate.isBefore(currentDate) && hold == 0) {
                session.setAttribute("warning", "This batch has expired and has no hold. It may be deleted by the scheduler soon.");
            }

            // Call DAO to update flower batch (status will be handled by scheduler)
            fbDAO.updateFlowerBatch(batchId, unitPrice, importDate, expirationDate, quantity, hold, warehouseId);

            // Set success message and redirect to flower details
            session.setAttribute("message", "Flower batch updated successfully!");
            String flowerId = request.getParameter("flower_id");
            if (flowerId == null || flowerId.trim().isEmpty()) {
                // Fallback: Get flowerId from FlowerBatch
                FlowerBatch flowerBatch = fbDAO.getFlowerBatchById(batchId);
                flowerId = String.valueOf(flowerBatch.getFlowerId());
            }
            response.sendRedirect(request.getContextPath() + "/rawFlowerDetails?flower_id=" + flowerId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid numeric input: " + e.getMessage());
            // Retain form data
            request.setAttribute("batch_id", request.getParameter("batch_id"));
            request.setAttribute("unit_price", request.getParameter("unit_price"));
            request.setAttribute("import_date", request.getParameter("import_date"));
            request.setAttribute("expiration_date", request.getParameter("expiration_date"));
            request.setAttribute("quantity", request.getParameter("quantity"));
            request.setAttribute("hold", request.getParameter("hold"));
            request.setAttribute("warehouse_id", request.getParameter("warehouse_id"));
            // Set flower batch for JSP
            String batchIdStr = request.getParameter("batch_id");
            if (batchIdStr != null && !batchIdStr.trim().isEmpty()) {
                try {
                    int batchId = Integer.parseInt(batchIdStr);
                    FlowerBatch flowerBatch = fbDAO.getFlowerBatchById(batchId);
                    if (flowerBatch != null) {
                        request.setAttribute("item", flowerBatch);
                    }
                } catch (NumberFormatException ex) {
                    // Ignore if batchIdStr is invalid
                }
            }
            // Get list of warehouses
            WarehouseDAO whDAO = new WarehouseDAO();
            List<Warehouse> warehouses = whDAO.getAllWarehouse();
            session.setAttribute("listW", warehouses);
            request.getRequestDispatcher("/DashMin/updatebatch.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while updating the flower batch: " + e.getMessage());
            // Retain form data
            request.setAttribute("batch_id", request.getParameter("batch_id"));
            request.setAttribute("unit_price", request.getParameter("unit_price"));
            request.setAttribute("import_date", request.getParameter("import_date"));
            request.setAttribute("expiration_date", request.getParameter("expiration_date"));
            request.setAttribute("quantity", request.getParameter("quantity"));
            request.setAttribute("hold", request.getParameter("hold"));
            request.setAttribute("warehouse_id", request.getParameter("warehouse_id"));
            // Set flower batch for JSP
            String batchIdStr = request.getParameter("batch_id");
            if (batchIdStr != null && !batchIdStr.trim().isEmpty()) {
                try {
                    int batchId = Integer.parseInt(batchIdStr);
                    FlowerBatch flowerBatch = fbDAO.getFlowerBatchById(batchId);
                    if (flowerBatch != null) {
                        request.setAttribute("item", flowerBatch);
                    }
                } catch (NumberFormatException ex) {
                    // Ignore if batchIdStr is invalid
                }
            }
            // Get list of warehouses
            WarehouseDAO whDAO = new WarehouseDAO();
            List<Warehouse> warehouses = whDAO.getAllWarehouse();
            session.setAttribute("listW", warehouses);
            request.getRequestDispatcher("/DashMin/updatebatch.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating flower batch information.";
    }
}