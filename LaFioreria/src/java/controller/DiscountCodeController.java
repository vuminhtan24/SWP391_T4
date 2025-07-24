package controller;

import dal.DiscountCodeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import model.DiscountCode;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet(name = "DiscountCodeController", urlPatterns = {"/discount"})
public class DiscountCodeController extends HttpServlet {

    DiscountCodeDAO dao = new DiscountCodeDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DiscountCodeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DiscountCodeController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final int PAGE_SIZE = 4;

        String searchTerm = request.getParameter("search");
        String filterStatus = request.getParameter("status");

        int page = 1;
        String pageRaw = request.getParameter("page");
        try {
            if (pageRaw != null) {
                page = Integer.parseInt(pageRaw);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid page number format: " + e.getMessage());
            page = 1;
        }

        int totalItems = dao.countFilteredDiscountCodes(searchTerm, filterStatus);
        int totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);
        int offset = (page - 1) * PAGE_SIZE;

        List<DiscountCode> list = dao.getFilteredDiscountCodesPaged(searchTerm, filterStatus, offset, PAGE_SIZE);

        request.setAttribute("discountCodes", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("search", searchTerm);
        request.setAttribute("status", filterStatus);

        request.getRequestDispatcher("/DashMin/discount_list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String redirectUrl = "discount";

        String searchTerm = request.getParameter("search");
        String filterStatus = request.getParameter("status");
        String pageRaw = request.getParameter("page");

        StringBuilder queryString = new StringBuilder();
        if (searchTerm != null && !searchTerm.isEmpty()) {
            queryString.append("search=").append(searchTerm).append("&");
        }
        if (filterStatus != null && !filterStatus.isEmpty()) {
            queryString.append("status=").append(filterStatus).append("&");
        }
        if (pageRaw != null && !pageRaw.isEmpty()) {
            queryString.append("page=").append(pageRaw).append("&");
        }

        if (queryString.length() > 0 && queryString.charAt(queryString.length() - 1) == '&') {
            queryString.setLength(queryString.length() - 1);
        }

        if (queryString.length() > 0) {
            redirectUrl += "?" + queryString.toString();
        }

        if ("add".equals(action)) {
            String code = request.getParameter("code") != null ? request.getParameter("code").trim() : "";
            String description = request.getParameter("description") != null ? request.getParameter("description").trim() : "";
            String type = request.getParameter("type") != null ? request.getParameter("type").trim() : "";
            String valueRaw = request.getParameter("value");
            String maxDiscountRaw = request.getParameter("maxDiscount");
            String minOrderRaw = request.getParameter("minOrder");
            String startRaw = request.getParameter("start");
            String endRaw = request.getParameter("end");
            String usageLimitRaw = request.getParameter("usageLimit");

            // Gán lại dữ liệu để hiển thị nếu lỗi
            request.setAttribute("code", code);
            request.setAttribute("description", description);
            request.setAttribute("type", type);
            request.setAttribute("value", valueRaw);
            request.setAttribute("maxDiscount", maxDiscountRaw);
            request.setAttribute("minOrder", minOrderRaw);
            request.setAttribute("start", startRaw);
            request.setAttribute("end", endRaw);
            request.setAttribute("usageLimit", usageLimitRaw);

            // Basic validation
            if (code.isEmpty() || description.isEmpty() || type.isEmpty() || valueRaw == null || startRaw == null || endRaw == null) {
                request.setAttribute("error", "Vui lòng điền đầy đủ thông tin bắt buộc (Code, Description, Type, Value, Start Date, End Date).");
                doGet(request, response);
                return;
            }

            // --- THÊM LOGIC KIỂM TRA TRÙNG LẶP TẠI ĐÂY ---
            if (dao.isDiscountCodeExists(code)) { // Gọi phương thức kiểm tra trong DAO
                request.setAttribute("error", "Mã giảm giá '" + code + "' đã tồn tại. Vui lòng chọn mã khác.");
                doGet(request, response); // Quay lại trang với lỗi và dữ liệu đã nhập
                return;
            }
            // --- KẾT THÚC LOGIC KIỂM TRA TRÙNG LẶP ---

            try {
                DiscountCode dc = new DiscountCode();
                dc.setCode(code);
                dc.setDescription(description);
                dc.setType(type);
                dc.setValue(new BigDecimal(valueRaw.trim()));

                dc.setMaxDiscount(maxDiscountRaw != null && !maxDiscountRaw.trim().isEmpty() ? new BigDecimal(maxDiscountRaw.trim()) : null);
                dc.setMinOrderAmount(minOrderRaw != null && !minOrderRaw.trim().isEmpty() ? new BigDecimal(minOrderRaw.trim()) : null);
                dc.setUsageLimit(usageLimitRaw != null && !usageLimitRaw.trim().isEmpty() ? Integer.parseInt(usageLimitRaw.trim()) : null);

                dc.setStartDate(Timestamp.valueOf(startRaw.trim().replace("T", " ") + ":00"));
                dc.setEndDate(Timestamp.valueOf(endRaw.trim().replace("T", " ") + ":00"));
                if (dc.getStartDate().after(dc.getEndDate())) {
                    request.setAttribute("error", "Ngày bắt đầu không được sau ngày kết thúc.");
                    doGet(request, response);
                    return;
                }
                dc.setActive(true);

                dao.insertDiscountCode(dc);
                response.sendRedirect(redirectUrl);
                return;

            } catch (Exception e) {
                System.err.println("Error adding discount code: " + e.getMessage());
                request.setAttribute("error", "Dữ liệu không hợp lệ hoặc sai định dạng: " + e.getMessage());
                doGet(request, response);
                return;
            }

        } else if ("deactivate".equals(action)) {
            String codeToDeactivate = request.getParameter("code");
            if (codeToDeactivate != null && !codeToDeactivate.isEmpty()) {
                dao.deactivateDiscountCode(codeToDeactivate);
                response.sendRedirect(redirectUrl);
            } else {
                request.setAttribute("error", "Mã giảm giá để hủy kích hoạt không hợp lệ.");
                doGet(request, response);
            }
        } else if ("delete".equals(action)) {
            String codeToDelete = request.getParameter("code");
            if (codeToDelete != null && !codeToDelete.isEmpty()) {
                dao.deleteDiscountCode(codeToDelete);
                response.sendRedirect(redirectUrl);
            } else {
                request.setAttribute("error", "Mã giảm giá để xóa không hợp lệ.");
                doGet(request, response);
            }
        } else {
            response.sendRedirect(redirectUrl);
        }
    }

    @Override
    public String getServletInfo() {
        return "Controller for Discount Code Management";
    }
}
