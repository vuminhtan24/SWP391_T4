/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dal.RawFlowerDAO;
import dal.WarehouseDAO;
import model.RawFlower;
import model.Warehouse;

/**
 *
 * @author Admin
 */
public class RawFlowerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Fetch brand and category lists for the search form
        WarehouseDAO wh = new WarehouseDAO();

        // Retrieve search parameters from the request
        String warehouseId = request.getParameter("warehouse");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        // Convert parameters from String to appropriate data types
        Integer warehouseIdInt = parseInteger(warehouseId, "All");
        Integer minPriceInteger = parseInteger(minPrice, "All");
        Integer maxPriceInteger = parseInteger(maxPrice, "All");
        Date startDate = parseDate(startDateStr);
        Date endDate = parseDate(endDateStr);

        // Fetch the filtered perfume list
        RawFlowerDAO rf = new RawFlowerDAO();
        List<RawFlower> listRF = rf.getRawFlowerByFilter(warehouseIdInt, minPriceInteger, maxPriceInteger, startDate, endDate);

        // Check if the request is for JSON response by looking at the 'Accept' header
        String acceptHeader = request.getHeader("Accept");
        if (acceptHeader != null && acceptHeader.contains("application/json")) {
            response.setContentType("application/json;charset=UTF-8");

            // Use Gson to serialize the list to JSON
        } else {
            // Use request attributes to pass data to the JSP
            request.setAttribute("listRF", listRF);

            // Forward the request to the JSP for rendering HTML view

            request.getRequestDispatcher("/DashMin/rawflower.jsp").forward(request, response);

        }
    }

    // Helper method to parse integers safely
    private Integer parseInteger(String value, String exclude) {
        if (value != null && !value.equals(exclude)) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Helper method to parse doubles safely
    private Double parseDouble(String value) {
        if (value != null && !value.isEmpty()) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Helper method to parse dates safely
    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return new Date(sdf.parse(dateStr).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);  // Reuse doGet logic for POST requests
    }

    @Override
    public String getServletInfo() {
        return "Servlet for searching perfumes with filters";
    }
    
}
