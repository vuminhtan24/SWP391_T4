package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import dal.RawFlowerDAO;
import dal.WarehouseDAO;
import model.Warehouse;
import util.Validate;

@WebServlet(name = "AddRawFlower", urlPatterns = {"/addRawFlower"})
public class AddRawFlower extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh sách kho để hiển thị trong form
        WarehouseDAO wdao = new WarehouseDAO();
        List<Warehouse> warehouses = wdao.getAllWarehouse();
        request.getSession().setAttribute("listW", warehouses);
        request.getRequestDispatcher("/DashMin/addrawflower.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            // Lấy tham số từ form
            String rawName = request.getParameter("rawName");
            String unitPriceStr = request.getParameter("unitPrice");
            String importPriceStr = request.getParameter("importPrice");
            String imageUrl = request.getParameter("imageUrl");
            String warehouseIdStr = request.getParameter("warehouseId");
            String rawQuantityStr = request.getParameter("rawQuantity");
            String expirationDateStr = request.getParameter("expirationDate");
            
            // Khởi tạo WarehouseDAO
            WarehouseDAO wh = new WarehouseDAO();

            // Validate các field
            String rawNameError = Validate.validateText(rawName, "Raw Flower Name");
            if (rawNameError == null) {
                // Kiểm tra độ dài categoryName (3-100 ký tự)
                rawNameError = Validate.validateLength(rawName, "Raw Flower Name", 1, 45);
            }
            String unitPriceError = Validate.validateNumberWithRange(unitPriceStr, "Unit Price", 1, Integer.MAX_VALUE);
            String importPriceError = Validate.validateNumberWithRange(importPriceStr, "Import Price", 1, Integer.MAX_VALUE);
            String imageUrlError = Validate.validateImageUrl(imageUrl);
            String warehouseIdError = Validate.validateWarehouseId(warehouseIdStr, wh);
            String rawQuantityError = Validate.validateNumberWithRange(rawQuantityStr, "Quantity", 0, Integer.MAX_VALUE);
            String expirationDateError = Validate.validateDate(expirationDateStr, "Expiration Date");

            // Nếu có lỗi, giữ dữ liệu và hiển thị popup
            if (rawNameError != null || unitPriceError != null || importPriceError != null ||
                    imageUrlError != null || warehouseIdError != null || rawQuantityError != null ||
                    expirationDateError != null) {
                request.setAttribute("rawName", rawName);
                request.setAttribute("unitPrice", unitPriceStr);
                request.setAttribute("importPrice", importPriceStr);
                request.setAttribute("imageUrl", imageUrl);
                request.setAttribute("warehouseId", warehouseIdStr);
                request.setAttribute("rawQuantity", rawQuantityStr);
                request.setAttribute("expirationDate", expirationDateStr);
                request.setAttribute("rawNameError", rawNameError);
                request.setAttribute("unitPriceError", unitPriceError);
                request.setAttribute("importPriceError", importPriceError);
                request.setAttribute("imageUrlError", imageUrlError);
                request.setAttribute("warehouseIdError", warehouseIdError);
                request.setAttribute("rawQuantityError", rawQuantityError);
                request.setAttribute("expirationDateError", expirationDateError);
                request.setAttribute("showErrorPopup", true);

                List<Warehouse> warehouses = wh.getAllWarehouse();
                session.setAttribute("listW", warehouses);
                request.getRequestDispatcher("/DashMin/addrawflower.jsp").forward(request, response);
                return;
            }

            // Chuyển đổi dữ liệu
            int unitPrice = Integer.parseInt(unitPriceStr);
            int importPrice = Integer.parseInt(importPriceStr);
            int warehouseId = Integer.parseInt(warehouseIdStr);
            int rawQuantity = Integer.parseInt(rawQuantityStr);
            Date expirationDate = Date.valueOf(expirationDateStr);

            // Gọi phương thức DAO để thêm nguyên liệu
            RawFlowerDAO rf = new RawFlowerDAO();
            rf.addRawFlower(rawName, rawQuantity, unitPrice, expirationDate, warehouseId, imageUrl, 0, importPrice);
            // Thông báo thành công và chuyển hướng
            session.setAttribute("message", "Raw flower added successfully!");
            response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while adding the raw flower: " + e.getMessage());
            request.setAttribute("showErrorPopup", true);
            WarehouseDAO wdao = new WarehouseDAO();
            List<Warehouse> warehouses = wdao.getAllWarehouse();
            session.setAttribute("listW", warehouses);
            request.getRequestDispatcher("/DashMin/addrawflower.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding Raw Flower information";
    }
}