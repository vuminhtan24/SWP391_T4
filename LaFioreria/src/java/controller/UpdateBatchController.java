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
        try {
            // Get batch_id from URL parameter
            String batchIdStr = request.getParameter("batch_id");
            if (batchIdStr == null || batchIdStr.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Batch ID is required.");
                return;
            }

            int batchId = Integer.parseInt(batchIdStr);
            FlowerBatch flowerBatch = fbDAO.getFlowerBatchById(batchId);
            if (flowerBatch == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Batch with ID " + batchId + " not found.");
                return;
            }

            // Set flower batch for JSP
            request.setAttribute("item", flowerBatch);

            // Get list of warehouses for the form
            WarehouseDAO whDAO = new WarehouseDAO();
            List<Warehouse> warehouses = whDAO.getAllWarehouse();
            if (warehouses == null) {
                System.out.println("Warehouses is null");
            } else {
                warehouses.forEach(w -> System.out.println("Warehouse ID: " + w.getWarehouseId() + ", Name: " + w.getName()));
            }
            request.getSession().setAttribute("listW", warehouses); // Lưu vào session

            // Forward to updatebatch.jsp
            request.getRequestDispatcher("/DashMin/updatebatch.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid batch ID.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
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

            // Validate each field using Validate class
            String batchIdError = Validate.validateNumber(batchIdStr, "Batch ID");
            String unitPriceError = Validate.validateNumberWithRange(unitPriceStr, "Unit Price", 0, Integer.MAX_VALUE);
            String importDateError = Validate.validateDate(importDate, "Import Date");
            String expirationDateError = Validate.validateDate(expirationDate, "Expiration Date");
            String quantityError = Validate.validateNumberWithRange(quantityStr, "Quantity", 0, Integer.MAX_VALUE);
            String holdError = Validate.validateNumberWithRange(holdStr, "Hold", 0, Integer.MAX_VALUE);
            String warehouseIdError = Validate.validateWarehouseId(warehouseIdStr, new WarehouseDAO());

            // Check for validation errors
            if (batchIdError != null || unitPriceError != null || importDateError != null ||
                expirationDateError != null || quantityError != null || holdError != null || warehouseIdError != null) {
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

                // Get list of warehouses for the form
                WarehouseDAO whDAO = new WarehouseDAO();
                List<Warehouse> warehouses = whDAO.getAllWarehouse();
                request.getSession().setAttribute("listW", warehouses); // Cập nhật session

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
            fbDAO.updateFlowerBatch(batchId, unitPrice, importDate, expirationDate, quantity, hold, warehouseId, null);

            // Set success message and redirect to flower details
            session.setAttribute("message", "Flower batch updated successfully!");
            String flowerId = request.getParameter("flower_id");
            response.sendRedirect(request.getContextPath() + "/rawFlowerDetails?flower_id=" + flowerId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid numeric input: " + e.getMessage());
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
            // Get list of warehouses
            WarehouseDAO whDAO = new WarehouseDAO();
            List<Warehouse> warehouses = whDAO.getAllWarehouse();
            request.getSession().setAttribute("listW", warehouses);
            request.getRequestDispatcher("/DashMin/updatebatch.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating flower batch information.";
    }
}