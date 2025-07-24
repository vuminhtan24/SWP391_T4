/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit the template
 */
package controller;

import dal.DiscountCodeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Import HttpSession
import java.math.BigDecimal;
import model.DiscountCode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VU MINH TAN
 */
@WebServlet(name = "UpdateDiscountServlet", urlPatterns = {"/editDiscount", "/updateDiscount"})
public class UpdateDiscountServlet extends HttpServlet {

    private DiscountCodeDAO discountCodeDAO = new DiscountCodeDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet UpdateDiscountServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateDiscountServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * This method is typically used to display the form for editing a discount code.
     * It fetches the discount code by its 'code' parameter and sets it as an attribute
     * for the JSP to render. It also checks for error messages and previously
     * submitted data from the session (in case of a POST redirect).
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Check for errors from a previous POST request (redirected from doPost)
        List<String> errors = (List<String>) session.getAttribute("updateErrors");
        if (errors != null) {
            request.setAttribute("errors", errors); // This sets it to request scope
            session.removeAttribute("updateErrors"); // Clear errors from session after retrieving
            System.out.println("UpdateDiscountServlet - doGet: Retrieved errors from session: " + errors); // Debug print
        } else {
            System.out.println("UpdateDiscountServlet - doGet: No errors found in session."); // Debug print
        }

        // Check for previously submitted data from a POST request (redirected from doPost)
        DiscountCode editDiscount = (DiscountCode) session.getAttribute("editDiscountData");
        if (editDiscount != null) {
            request.setAttribute("editDiscount", editDiscount);
            session.removeAttribute("editDiscountData"); // Clear data from session after retrieving
            System.out.println("UpdateDiscountServlet - doGet: Retrieved editDiscountData from session for code: " + editDiscount.getCode()); // Debug print
        } else {
            // If no data from session, fetch from DB using 'code' parameter
            String code = request.getParameter("code");
            if (code == null || code.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/discount?message=Mã giảm giá không hợp lệ để chỉnh sửa.");
                return;
            }
            editDiscount = discountCodeDAO.getDiscountCodeByCode(code);
            if (editDiscount == null) {
                response.sendRedirect(request.getContextPath() + "/discount?message=Không tìm thấy mã giảm giá để chỉnh sửa.");
                return;
            }
            request.setAttribute("editDiscount", editDiscount);
            System.out.println("UpdateDiscountServlet - doGet: Fetched editDiscount from DB for code: " + editDiscount.getCode()); // Debug print
        }

        request.getRequestDispatcher("/DashMin/discount_list.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * This method processes the form submission for updating a discount code,
     * including validation of input data.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        List<String> errors = new ArrayList<>();

        String code = request.getParameter("code"); // Code is usually read-only for update
        String description = request.getParameter("description");
        String type = request.getParameter("type");
        String valueStr = request.getParameter("value");
        String maxDiscountStr = request.getParameter("maxDiscount");
        String minOrderAmountStr = request.getParameter("minOrderAmount");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String usageLimitStr = request.getParameter("usageLimit");
        String activeStr = request.getParameter("active");

        // Create a DiscountCode object to hold current values for re-display in case of error
        DiscountCode dc = new DiscountCode();
        dc.setCode(code);
        dc.setDescription(description);
        dc.setType(type);
        // For checkbox, set active status based on parameter presence
        dc.setActive("on".equals(activeStr) || "true".equalsIgnoreCase(activeStr));


        // --- Validation ---
        if (code == null || code.trim().isEmpty()) {
            errors.add("Mã giảm giá không được để trống.");
        }
        if (description == null || description.trim().isEmpty()) {
            errors.add("Mô tả không được để trống.");
        }
        if (type == null || (!type.equals("PERCENT") && !type.equals("FIXED") && !type.equals("AMOUNT"))) { // Assuming "AMOUNT" is also a valid type
            errors.add("Loại giảm giá không hợp lệ.");
        }
        if (valueStr == null || valueStr.trim().isEmpty()) {
            errors.add("Giá trị giảm giá không được để trống.");
        }
        if (startDateStr == null || startDateStr.trim().isEmpty()) {
            errors.add("Ngày bắt đầu không được để trống.");
        }
        if (endDateStr == null || endDateStr.trim().isEmpty()) {
            errors.add("Ngày kết thúc không được để trống.");
        }

        BigDecimal value = null;
        try {
            if (valueStr != null && !valueStr.trim().isEmpty()) {
                value = new BigDecimal(valueStr.trim());
                if (value.compareTo(BigDecimal.ZERO) <= 0) {
                    errors.add("Giá trị giảm giá phải là số dương.");
                }
                if (type != null && type.equals("PERCENT") && (value.compareTo(new BigDecimal("100")) > 0 || value.compareTo(BigDecimal.ZERO) < 0)) {
                    errors.add("Giá trị phần trăm phải từ 0 đến 100.");
                }
                dc.setValue(value);
            }
        } catch (NumberFormatException e) {
            errors.add("Giá trị giảm giá không hợp lệ.");
        }

        BigDecimal maxDiscount = null;
        try {
            if (maxDiscountStr != null && !maxDiscountStr.trim().isEmpty()) {
                maxDiscount = new BigDecimal(maxDiscountStr.trim());
                if (maxDiscount.compareTo(BigDecimal.ZERO) < 0) {
                    errors.add("Giảm tối đa không được là số âm.");
                }
                dc.setMaxDiscount(maxDiscount);
            } else {
                dc.setMaxDiscount(null); // Set to null if empty
            }
        } catch (NumberFormatException e) {
            errors.add("Giảm tối đa không hợp lệ.");
        }

        BigDecimal minOrderAmount = null;
        try {
            if (minOrderAmountStr != null && !minOrderAmountStr.trim().isEmpty()) {
                minOrderAmount = new BigDecimal(minOrderAmountStr.trim());
                if (minOrderAmount.compareTo(BigDecimal.ZERO) < 0) {
                    errors.add("Đơn hàng tối thiểu không được là số âm.");
                }
                dc.setMinOrderAmount(minOrderAmount);
            } else {
                dc.setMinOrderAmount(null); // Set to null if empty
            }
        } catch (NumberFormatException e) {
            errors.add("Đơn hàng tối thiểu không hợp lệ.");
        }
        
        Integer usageLimit = null;
        try {
            if (usageLimitStr != null && !usageLimitStr.trim().isEmpty()) {
                usageLimit = Integer.parseInt(usageLimitStr.trim());
                if (usageLimit < 0) {
                    errors.add("Giới hạn lượt dùng không được là số âm.");
                }
                dc.setUsageLimit(usageLimit);
            } else {
                dc.setUsageLimit(0); // Set to null if empty, matching DB schema for nullable column
            }
        } catch (NumberFormatException e) {
            errors.add("Giới hạn lượt dùng không hợp lệ.");
        }


        Timestamp startDate = null;
        Timestamp endDate = null;
        try {
            if (startDateStr != null && !startDateStr.trim().isEmpty()) {
                startDate = Timestamp.valueOf(startDateStr + " 00:00:00");
                dc.setStartDate(startDate);
            }
        } catch (IllegalArgumentException e) {
            errors.add("Ngày bắt đầu không hợp lệ (định dạng yyyy-MM-dd).");
        }

        try {
            if (endDateStr != null && !endDateStr.trim().isEmpty()) {
                endDate = Timestamp.valueOf(endDateStr + " 23:59:59");
                dc.setEndDate(endDate);
            }
        } catch (IllegalArgumentException e) {
            errors.add("Ngày kết thúc không hợp lệ (định dạng yyyy-MM-dd).");
        }
        
        // Date logic validation: startDate must be before or equal to endDate
        if (startDate != null && endDate != null && startDate.after(endDate)) {
            errors.add("Ngày bắt đầu phải trước hoặc bằng ngày kết thúc.");
        }

        // If there are errors, store them in session and redirect to doGet
        if (!errors.isEmpty()) {
            System.out.println("UpdateDiscountServlet - doPost: Validation errors found: " + errors); // Debug print
            session.setAttribute("updateErrors", errors);
            
            // Fetch original DiscountCode to preserve its ID and usedCount
            // then merge with submitted data to re-display
            DiscountCode originalDc = discountCodeDAO.getDiscountCodeByCode(code);
            if (originalDc != null) {
                // Preserve ID and usedCount from original
                dc.setId(originalDc.getId());
                dc.setUsedCount(originalDc.getUsedCount());
            }
            session.setAttribute("editDiscountData", dc); // Store the (partially) filled DC

            response.sendRedirect(request.getContextPath() + "/editDiscount?code=" + code);
            return;
        }

        // If all validations pass, proceed with the update
        try {
            // Fetch the original discount code to preserve its ID and usedCount
            // (assuming these are not passed in the form for update)
            DiscountCode originalDc = discountCodeDAO.getDiscountCodeByCode(code);
            if (originalDc != null) {
                dc.setId(originalDc.getId()); // Preserve ID
                dc.setUsedCount(originalDc.getUsedCount()); // Preserve used count
                // If you have other fields not updated by the form, set them from originalDc
            } else {
                errors.add("Không tìm thấy mã giảm giá gốc để cập nhật.");
                session.setAttribute("updateErrors", errors);
                session.setAttribute("editDiscountData", dc);
                response.sendRedirect(request.getContextPath() + "/editDiscount?code=" + code);
                return;
            }

            discountCodeDAO.updateDiscountCode(dc);
            session.setAttribute("message", "Cập nhật mã giảm giá thành công!"); // Use session for success message
            System.out.println("UpdateDiscountServlet - doPost: Discount code updated successfully: " + code); // Debug print
            response.sendRedirect(request.getContextPath() + "/discount");
        } catch (Exception e) {
            System.err.println("UpdateDiscountServlet - doPost: Error updating discount code: " + e.getMessage());
            errors.add("Lỗi khi cập nhật mã giảm giá: " + e.getMessage());
            session.setAttribute("updateErrors", errors);
            
            // Fetch original DiscountCode to preserve its ID and usedCount
            DiscountCode originalDc = discountCodeDAO.getDiscountCodeByCode(code);
            if (originalDc != null) {
                dc.setId(originalDc.getId());
                dc.setUsedCount(originalDc.getUsedCount());
            }
            session.setAttribute("editDiscountData", dc); // Keep the entered data

            response.sendRedirect(request.getContextPath() + "/editDiscount?code=" + code);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for updating discount codes with validation";
    }// </editor-fold>

}
