package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dal.RawFlowerDAO;
import model.RawFlower;
import dal.WarehouseDAO;
import model.Warehouse;

@WebServlet(name = "RawFlowerServlet2", urlPatterns = {"/DashMin/rawflower2"})
public class RawFlowerServlet2 extends HttpServlet {

    private static final int RECORDS_PER_PAGE = 6; // Số bản ghi trên mỗi trang

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy danh sách kho
            WarehouseDAO wh = new WarehouseDAO();
            List<Warehouse> listWarehouse = wh.getAllWarehouse();
            System.out.println("Số kho: " + listWarehouse.size());

            // Lấy tham số phân trang, sắp xếp và tìm kiếm
            String pageParam = request.getParameter("page");
            int currentPage = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
            String sortField = request.getParameter("sortField");
            String rawFlowerName = request.getParameter("rawFlowerName");

            // Ánh xạ sortField thành sortBy và sortOrder
            String sortBy = null;
            String sortOrder = null;
            if (sortField != null && !sortField.isEmpty()) {
                switch (sortField) {
                    case "unit_price_asc":
                        sortBy = "unit_price";
                        sortOrder = "asc";
                        break;
                    case "unit_price_desc":
                        sortBy = "unit_price";
                        sortOrder = "desc";
                        break;
                    case "import_price_asc":
                        sortBy = "import_price";
                        sortOrder = "asc";
                        break;
                    case "import_price_desc":
                        sortBy = "import_price";
                        sortOrder = "desc";
                        break;
                    case "quantity_asc":
                        sortBy = "quantity";
                        sortOrder = "asc";
                        break;
                    case "quantity_desc":
                        sortBy = "quantity";
                        sortOrder = "desc";
                        break;
                }
            }
            System.out.println("Tham số: page=" + currentPage + ", sortField=" + sortField + ", sortBy=" + sortBy + ", sortOrder=" + sortOrder + ", rawFlowerName=" + rawFlowerName);

            // Kiểm tra tham số sắp xếp hợp lệ
            boolean validSort = sortBy != null && sortOrder != null &&
                    sortBy.matches("^(unit_price|import_price|quantity)$") &&
                    sortOrder.matches("^(asc|desc)$");

            // Lấy danh sách nguyên liệu hoa
            RawFlowerDAO rf = new RawFlowerDAO();
            List<RawFlower> listRF;

            // Thực hiện tìm kiếm hoặc lấy tất cả bản ghi
            if (rawFlowerName != null && !rawFlowerName.trim().isEmpty()) {
                listRF = rf.searchRawFlowerByKeyword(rawFlowerName.trim());
            } else {
                listRF = rf.getRawFlower();
            }
            System.out.println("Số bản ghi trước sắp xếp: " + listRF.size());

            // Áp dụng sắp xếp trong bộ nhớ nếu hợp lệ
            if (validSort) {
                Comparator<RawFlower> comparator = switch (sortBy) {
                    case "unit_price" -> Comparator.comparingInt(RawFlower::getUnitPrice);
                    case "import_price" -> Comparator.comparingInt(RawFlower::getImportPrice);
                    case "quantity" -> Comparator.comparingInt(RawFlower::getRawQuantity);
                    default -> null;
                };
                if (comparator != null) {
                    if ("desc".equalsIgnoreCase(sortOrder)) {
                        comparator = comparator.reversed();
                    }
                    listRF.sort(comparator);
                    System.out.println("Đã sắp xếp theo " + sortBy + " " + sortOrder);
                }
            } else {
                System.out.println("Không áp dụng sắp xếp (giữ nguyên danh sách từ DAO)");
            }

            // Phân trang
            int totalRecords = listRF.size();
            int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);
            currentPage = Math.max(1, Math.min(currentPage, totalPages));
            int startIndex = (currentPage - 1) * RECORDS_PER_PAGE;
            int endIndex = Math.min(startIndex + RECORDS_PER_PAGE, totalRecords);
            List<RawFlower> pagedListRF = totalRecords > 0 ? listRF.subList(startIndex, endIndex) : new ArrayList<>();
            System.out.println("Trang " + currentPage + ": từ chỉ số " + startIndex + " đến " + endIndex);

            // Lưu dữ liệu vào phiên và request
            HttpSession session = request.getSession();
            session.setAttribute("listW", listWarehouse);
            session.setAttribute("listRF", pagedListRF);
            session.setAttribute("sortField", sortField);
            session.setAttribute("rawFlowerName", rawFlowerName);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);

            // Chuyển tiếp đến JSP
            request.getRequestDispatcher("/DashMin/rawflower.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("message", "Định dạng số trang không hợp lệ.");
            response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", "Đã xảy ra lỗi: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý sắp xếp hoặc tìm kiếm từ form
        String sortField = request.getParameter("sortField");
        String rawFlowerName = request.getParameter("rawFlowerName");
        System.out.println("POST: sortField=" + sortField + ", rawFlowerName=" + rawFlowerName);

        // Lưu tham số vào phiên
        HttpSession session = request.getSession();
        session.setAttribute("sortField", sortField);
        session.setAttribute("rawFlowerName", rawFlowerName);

        // Chuyển hướng để reset về trang 1
        response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2?page=1");
    }

    @Override
    public String getServletInfo() {
        return "Servlet quản lý danh sách nguyên liệu hoa, sắp xếp, tìm kiếm và phân trang";
    }
}