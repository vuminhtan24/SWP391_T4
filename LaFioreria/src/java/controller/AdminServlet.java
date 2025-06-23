/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dal.DAOContact;
import dal.OrderDAO;
import dal.SalesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Year;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import model.Category;
import model.Contact;
import model.OrderStatusCount;
import model.SalesRecord;
import model.StatResult;

/**
 *
 * @author VU MINH TAN
 */
@WebServlet(name = "AdminServlet", urlPatterns = {"/DashMin/admin"})
public class AdminServlet extends HttpServlet {

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
            out.println("<title>Servlet AdminServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAOContact Dao = new DAOContact();
        List<Contact> contactList = Dao.getAllContacts();
        request.setAttribute("messages", contactList);

        SalesDAO dao = new SalesDAO();
        Gson gson = new Gson();

        OrderDAO oDAO = new OrderDAO();
        // ✅ Xử lý năm được chọn cho biểu đồ thống kê theo tháng
        String yearParam = request.getParameter("year");
        int selectedYear = (yearParam != null && !yearParam.isEmpty())
                ? Integer.parseInt(yearParam)
                : Year.now().getValue();

        List<StatResult> stats = dao.getMonthlyStats(selectedYear);
        List<String> labels = new ArrayList<>();
        List<Double> revenues = new ArrayList<>();
        List<Integer> orders = new ArrayList<>();
        for (StatResult stat : stats) {
            labels.add(stat.getLabel());
            revenues.add(stat.getRevenue());
            orders.add(stat.getOrderCount());
        }
        request.setAttribute("labelsJson", gson.toJson(labels));
        request.setAttribute("revenuesJson", gson.toJson(revenues));
        request.setAttribute("ordersJson", gson.toJson(orders));
        request.setAttribute("selectedYear", selectedYear);

        String fromParam = request.getParameter("fromYear");
        String toParam = request.getParameter("toYear");
        int currentYear = Year.now().getValue();
        int fromYear = (fromParam != null && !fromParam.isEmpty()) ? Integer.parseInt(fromParam) : currentYear - 5;
        int toYear = (toParam != null && !toParam.isEmpty()) ? Integer.parseInt(toParam) : currentYear;

        List<StatResult> monthStats = dao.getMonthlyStats(toYear);
        request.setAttribute("monthYear", toYear);
        request.setAttribute("monthLabels", gson.toJson(getLabels(monthStats)));
        request.setAttribute("monthRevenues", gson.toJson(getRevenues(monthStats)));
        request.setAttribute("monthOrders", gson.toJson(getOrders(monthStats)));

        List<StatResult> allYears = dao.getYearlyStats();
        List<StatResult> filteredYears = new ArrayList<>();
        for (StatResult stat : allYears) {
            String label = stat.getLabel();
            int year = Integer.parseInt(label.replace("Năm ", "").trim());
            if (year >= fromYear && year <= toYear) {
                filteredYears.add(stat);
            }
        }
        request.setAttribute("yearLabels", gson.toJson(getLabels(filteredYears)));
        request.setAttribute("yearRevenues", gson.toJson(getRevenues(filteredYears)));
        request.setAttribute("yearOrders", gson.toJson(getOrders(filteredYears)));

        List<StatResult> weekdayStats = dao.getWeekdayStats();
        request.setAttribute("weekdayLabels", gson.toJson(getLabels(weekdayStats)));
        request.setAttribute("weekdayRevenues", gson.toJson(getRevenues(weekdayStats)));
        request.setAttribute("weekdayOrders", gson.toJson(getOrders(weekdayStats)));

        List<SalesRecord> detailedSales = dao.getSalesReportThisMonth();
        request.setAttribute("salesList", detailedSales);

        Map<String, Double> revenueByDate = dao.getRevenueByDateThisMonth();
        request.setAttribute("dailyLabels", gson.toJson(new ArrayList<>(revenueByDate.keySet())));
        request.setAttribute("dailyValues", gson.toJson(new ArrayList<>(revenueByDate.values())));

        int todayOrders = dao.getTodayOrderCount();
        double thisMonthRevenue = dao.getThisMonthRevenue();
        double totalRevenue = dao.getTotalRevenue();
        request.setAttribute("todayOrders", todayOrders);
        request.setAttribute("thisMonthRevenue", thisMonthRevenue);
        request.setAttribute("totalRevenue", totalRevenue);

        Map<String, Double> thisMonth = dao.getRevenueGroupedThisMonth();
        request.setAttribute("thisMonthLabels", gson.toJson(new ArrayList<>(thisMonth.keySet())));
        request.setAttribute("thisMonthValues", gson.toJson(new ArrayList<>(thisMonth.values())));

// ✅ Thống kê theo loại hoa (tháng này)
        List<Category> categoryList = dao.getAllCategories();
        request.setAttribute("categoryList", categoryList);

// ✅ Convert sẵn categoryList sang JSON để main.js dùng
        request.setAttribute("categoryListJson", gson.toJson(categoryList));

        String[] selectedIds = request.getParameterValues("cid");
        if (selectedIds != null && selectedIds.length > 0) {
            Set<String> allDates = new TreeSet<>();

            // Map để chứa tạm các dữ liệu revenue theo từng category name
            Map<String, List<Double>> categoryData = new LinkedHashMap<>();

            for (String idStr : selectedIds) {
                int cid = Integer.parseInt(idStr);
                Map<String, Double> data = dao.getRevenueGroupedByCategory(cid);

                // Tìm tên category theo id
                String categoryName = "";
                for (Category c : categoryList) {
                    if (c.getCategoryId() == cid) {
                        categoryName = c.getCategoryName();
                        break;
                    }
                }

                // Gom tất cả các ngày lại (dùng để làm labels)
                allDates.addAll(data.keySet());

                // Tạm lưu dữ liệu để xử lý sau
                categoryData.put(categoryName, data != null ? new ArrayList<>(data.values()) : new ArrayList<>());
            }

            // Chuẩn hóa lại tất cả dữ liệu để đảm bảo đúng labels (ngày)
            List<String> dateList = new ArrayList<>(allDates);

            for (String idStr : selectedIds) {
                int cid = Integer.parseInt(idStr);
                Map<String, Double> data = dao.getRevenueGroupedByCategory(cid);

                String categoryName = "";
                for (Category c : categoryList) {
                    if (c.getCategoryId() == cid) {
                        categoryName = c.getCategoryName();
                        break;
                    }
                }

                List<Double> values = new ArrayList<>();
                for (String date : dateList) {
                    values.add(data != null ? data.getOrDefault(date, 0.0) : 0.0);
                }

                // ✅ Đây là điểm quan trọng: phải set thuộc tính theo tên category để main.js lấy được
                request.setAttribute(categoryName + "_data", gson.toJson(values));
            }

            // ✅ Gửi labels (tức danh sách ngày) để vẽ trục X
            request.setAttribute("labelsJson", gson.toJson(dateList));
        }
        String filter = request.getParameter("filter");
        if (filter == null) {
            filter = "month"; // mặc định là tháng này
        }

        List<SalesRecord> salesListbyfilter = dao.getSalesReport(filter);
        request.setAttribute("salesListfilter", salesListbyfilter);
        request.setAttribute("filter", filter); // để hiển thị lại filter đã chọn

        List<OrderStatusCount> statusCounts = oDAO.getOrderStatusCounts();
        request.setAttribute("statusCounts", statusCounts);

        request.getRequestDispatcher("/DashMin/admin.jsp").forward(request, response);
    }

    private List<String> getLabels(List<StatResult> list) {
        List<String> labels = new ArrayList<>();
        for (StatResult s : list) {
            labels.add(s.getLabel());
        }
        return labels;
    }

    private List<Double> getRevenues(List<StatResult> list) {
        List<Double> data = new ArrayList<>();
        for (StatResult s : list) {
            data.add(s.getRevenue());
        }
        return data;
    }

    private List<Integer> getOrders(List<StatResult> list) {
        List<Integer> data = new ArrayList<>();
        for (StatResult s : list) {
            data.add(s.getOrderCount());
        }
        return data;
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
