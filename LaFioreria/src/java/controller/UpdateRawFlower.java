/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.RawFlowerDAO;
import dal.WarehouseDAO;
import java.sql.Date;
import java.util.List;
import model.Warehouse;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UpdateRawFlower", urlPatterns = {"/update_flower"})
public class UpdateRawFlower extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RawFlowerDAO rf = new RawFlowerDAO();
        WarehouseDAO wdao = new WarehouseDAO();
        int raw_id = Integer.parseInt(request.getParameter("raw_id"));
        request.setAttribute("item", rf.getRawFlowerById(raw_id));
        List<Warehouse> warehouses = wdao.getAllWarehouse();
        request.getSession().setAttribute("listW", warehouses);
        request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int raw_id = Integer.parseInt(request.getParameter("raw_id"));
            String raw_name = request.getParameter("raw_name");
            String raw_quantityStr = request.getParameter("raw_quantity");
            String unitPriceStr = request.getParameter("unit_price");
            String expirationDate = request.getParameter("expiration_date");
            String importPriceStr = request.getParameter("import_price");
            String warehouseIdStr = request.getParameter("warehouse_id");
            String holdStr = request.getParameter("hold");
            String imageUrl = request.getParameter("image_url");

            // Validate inputs
            if (raw_name == null || raw_name.trim().isEmpty()) {
                request.setAttribute("ms", "Error: Product name cannot be empty.");
                request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
                return;
            }
            if (imageUrl == null || imageUrl.trim().isEmpty()) {
                request.setAttribute("ms", "Error: Image URL cannot be empty.");
                request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
                return;
            }

            // Parse numeric and date fields with checks
            int raw_quantity, unitPrice, importPrice, warehouse_id, hold;
            Date expiration_date;
            try {
                raw_quantity = raw_quantityStr != null ? Integer.parseInt(raw_quantityStr) : 0;
                unitPrice = unitPriceStr != null ? Integer.parseInt(unitPriceStr) : 0;
                importPrice = importPriceStr != null ? Integer.parseInt(importPriceStr) : 0;
                warehouse_id = warehouseIdStr != null ? Integer.parseInt(warehouseIdStr) : 0;
                hold = holdStr != null ? Integer.parseInt(holdStr) : 0;
                expiration_date = expirationDate != null && !expirationDate.isEmpty() ? Date.valueOf(expirationDate) : null;
            } catch (NumberFormatException e) {
                request.setAttribute("ms", "Error: Invalid numeric value provided.");
                request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
                return;
            } catch (IllegalArgumentException e) {
                request.setAttribute("ms", "Error: Invalid date format for expiration date.");
                request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
                return;
            }

            // Ensure non-negative values
            if (unitPrice < 0 || importPrice < 0 || raw_quantity < 0 || hold < 0 || warehouse_id <= 0) {
                request.setAttribute("ms", "Error: Numeric values must be non-negative and warehouse ID must be valid.");
                request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
                return;
            }

            // Update the database
            RawFlowerDAO rf = new RawFlowerDAO();
            rf.updateRawFlower4(raw_id, raw_name, raw_quantity, unitPrice, expiration_date, warehouse_id, imageUrl, hold, importPrice);

            // Redirect on success
            response.sendRedirect("update_flower?raw_id=" + raw_id);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ms", "Error: An unexpected error occurred during the update.");
            request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating Perfume information including description";
    }

}
