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
import java.util.List;
import dal.FlowerBatchDAO;
import dal.WarehouseDAO;
import model.Warehouse;
import util.Validate;

/**
 * Servlet to handle adding a new flower batch for a selected flower.
 */
@WebServlet(name = "AddBatch", urlPatterns = {"/add_batch"})
public class AddBatch extends HttpServlet {
    private final FlowerBatchDAO fbDAO = new FlowerBatchDAO();
    private final WarehouseDAO whDAO = new WarehouseDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            // Get flower_id from URL parameter
            String flowerIdStr = request.getParameter("flower_id");
            System.out.println("AddBatch.doGet: Received flower_id = " + flowerIdStr); // Debug log
            String flowerIdError = Validate.validateNumber(flowerIdStr, "Flower ID");
            if (flowerIdError != null || flowerIdStr == null || flowerIdStr.trim().isEmpty()) {
                session.setAttribute("error", flowerIdError != null ? flowerIdError : "Flower ID is required.");
                response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
                return;
            }

            int flowerId = Integer.parseInt(flowerIdStr);
            if (flowerId <= 0) {
                session.setAttribute("error", "Flower ID must be positive.");
                response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
                return;
            }

            // Set flower_id for JSP
            request.setAttribute("flowerId", flowerId);

            // Get list of warehouses for the form
            List<Warehouse> warehouses = whDAO.getAllWarehouse();
            if (warehouses == null || warehouses.isEmpty()) {
                session.setAttribute("error", "No warehouses available.");
                response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
                return;
            }
            session.setAttribute("listW", warehouses); // Sử dụng session như AddRawFlower

            // Forward to add_batch.jsp
            request.getRequestDispatcher("/DashMin/addbatch.jsp").forward(request, response);
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
        try {
            // Get parameters from the form
            String flowerIdStr = request.getParameter("flower_id");
            String unitPriceStr = request.getParameter("unit_price");
            String importDate = request.getParameter("import_date");
            String expirationDate = request.getParameter("expiration_date");
            String quantityStr = request.getParameter("quantity");
            String holdStr = request.getParameter("hold");
            String warehouseIdStr = request.getParameter("warehouse_id");
            
            // Validate each field using Validate class
            String flowerIdError = Validate.validateNumber(flowerIdStr, "Flower ID");
            String unitPriceError = Validate.validateNumberWithRange(unitPriceStr, "Unit Price", 0, Integer.MAX_VALUE);
            String importDateError = Validate.validateDate(importDate, "Import Date");
            String expirationDateError = Validate.validateDate(expirationDate, "Expiration Date");
            String quantityError = Validate.validateNumberWithRange(quantityStr, "Quantity", 0, Integer.MAX_VALUE);
            String holdError = Validate.validateNumberWithRange(holdStr, "Hold", 0, Integer.MAX_VALUE);
            String warehouseIdError = Validate.validateWarehouseId(warehouseIdStr, whDAO);

            // Check for validation errors
            if (flowerIdError != null || unitPriceError != null || importDateError != null ||
                expirationDateError != null || quantityError != null || holdError != null || warehouseIdError != null) {
                // Retain form data
                request.setAttribute("flowerId", flowerIdStr);
                request.setAttribute("unit_price", unitPriceStr);
                request.setAttribute("import_date", importDate);
                request.setAttribute("expiration_date", expirationDate);
                request.setAttribute("quantity", quantityStr);
                request.setAttribute("hold", holdStr);
                request.setAttribute("warehouse_id", warehouseIdStr);

                // Set error messages
                request.setAttribute("flowerIdError", flowerIdError);
                request.setAttribute("unitPriceError", unitPriceError);
                request.setAttribute("importDateError", importDateError);
                request.setAttribute("expirationDateError", expirationDateError);
                request.setAttribute("quantityError", quantityError);
                request.setAttribute("holdError", holdError);
                request.setAttribute("warehouseIdError", warehouseIdError);

                // Reload warehouses
                List<Warehouse> warehouses = whDAO.getAllWarehouse();
                session.setAttribute("listW", warehouses); // Cập nhật session

                // Forward back to add_batch.jsp with errors
                request.getRequestDispatcher("/DashMin/addbatch.jsp").forward(request, response);
                return;
            }

            // Parse validated inputs
            int flowerId = Integer.parseInt(flowerIdStr);
            int unitPrice = Integer.parseInt(unitPriceStr);
            int quantity = Integer.parseInt(quantityStr);
            int hold = Integer.parseInt(holdStr);
            int warehouseId = Integer.parseInt(warehouseIdStr);

            // Call DAO to add flower batch
            fbDAO.addFlowerBatch(flowerId, unitPrice, importDate, expirationDate, quantity, hold, warehouseId);

            // Set success message and redirect to flower details
            session.setAttribute("message", "Flower batch added successfully!");
            response.sendRedirect(request.getContextPath() + "/rawFlowerDetails?flower_id=" + flowerId);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while adding the flower batch: " + e.getMessage());
            // Retain form data
            request.setAttribute("flowerId", request.getParameter("flower_id"));
            request.setAttribute("unit_price", request.getParameter("unit_price"));
            request.setAttribute("import_date", request.getParameter("import_date"));
            request.setAttribute("expiration_date", request.getParameter("expiration_date"));
            request.setAttribute("quantity", request.getParameter("quantity"));
            request.setAttribute("hold", request.getParameter("hold"));
            request.setAttribute("warehouse_id", request.getParameter("warehouse_id"));
            // Reload warehouses
            List<Warehouse> warehouses = whDAO.getAllWarehouse();
            session.setAttribute("listW", warehouses);
            request.getRequestDispatcher("/DashMin/addbatch.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet to handle adding a new flower batch for a selected flower.";
    }
}
