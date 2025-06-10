package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.RawFlowerDAO;
import dal.WarehouseDAO;
import java.sql.Date;
import java.util.List;
import model.Warehouse;
import util.Validate;

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
        HttpSession session = request.getSession();
        try {
            // Lấy tham số từ form
            String rawIdStr = request.getParameter("raw_id");
            String rawName = request.getParameter("raw_name");
            String rawQuantityStr = request.getParameter("raw_quantity");
            String unitPriceStr = request.getParameter("unit_price");
            String expirationDate = request.getParameter("expiration_date");
            String importPriceStr = request.getParameter("import_price");
            String warehouseIdStr = request.getParameter("warehouse_id");
            String holdStr = request.getParameter("hold");
            String imageUrl = request.getParameter("image_url");

            // Khởi tạo WarehouseDAO
            WarehouseDAO wh = new WarehouseDAO();

            // Validate các field
            String rawNameError = Validate.validateLength(rawName, "Raw Flower Name", 1, 45);
            String unitPriceError = Validate.validateNumberWithRange(unitPriceStr, "Unit Price", 1, Integer.MAX_VALUE);
            String importPriceError = Validate.validateNumberWithRange(importPriceStr, "Import Price", 1, Integer.MAX_VALUE);
            String imageUrlError = Validate.validateImageUrl(imageUrl);
            String warehouseIdError = Validate.validateWarehouseId(warehouseIdStr, wh);

            // Nếu có lỗi, giữ dữ liệu và hiển thị popup
            if (rawNameError != null || unitPriceError != null || importPriceError != null || 
                imageUrlError != null || warehouseIdError != null) {
                request.setAttribute("rawId", rawIdStr);
                request.setAttribute("rawName", rawName);
                request.setAttribute("rawQuantity", rawQuantityStr);
                request.setAttribute("unitPrice", unitPriceStr);
                request.setAttribute("expirationDate", expirationDate);
                request.setAttribute("importPrice", importPriceStr);
                request.setAttribute("warehouseId", warehouseIdStr);
                request.setAttribute("hold", holdStr);
                request.setAttribute("imageUrl", imageUrl);
                request.setAttribute("rawNameError", rawNameError);
                request.setAttribute("unitPriceError", unitPriceError);
                request.setAttribute("importPriceError", importPriceError);
                request.setAttribute("imageUrlError", imageUrlError);
                request.setAttribute("warehouseIdError", warehouseIdError);
                request.setAttribute("showErrorPopup", true);

                RawFlowerDAO rf = new RawFlowerDAO();
                request.setAttribute("item", rf.getRawFlowerById(Integer.parseInt(rawIdStr)));
                request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
                return;
            }

            // Xóa các lỗi và dữ liệu trong session nếu validate thành công
            session.removeAttribute("rawNameError");
            session.removeAttribute("unitPriceError");
            session.removeAttribute("importPriceError");
            session.removeAttribute("imageUrlError");
            session.removeAttribute("warehouseIdError");
            session.removeAttribute("rawId");
            session.removeAttribute("rawName");
            session.removeAttribute("rawQuantity");
            session.removeAttribute("unitPrice");
            session.removeAttribute("expirationDate");
            session.removeAttribute("importPrice");
            session.removeAttribute("warehouseId");
            session.removeAttribute("hold");
            session.removeAttribute("imageUrl");

            // Chuyển đổi dữ liệu
            int rawId = Integer.parseInt(rawIdStr);
            int rawQuantity = Integer.parseInt(rawQuantityStr);
            int unitPrice = Integer.parseInt(unitPriceStr);
            Date expirationDateParsed = expirationDate != null && !expirationDate.isEmpty() ? Date.valueOf(expirationDate) : null;
            int importPrice = Integer.parseInt(importPriceStr);
            int warehouseId = Integer.parseInt(warehouseIdStr);
            int hold = Integer.parseInt(holdStr);

            // Cập nhật nguyên liệu
            RawFlowerDAO rf = new RawFlowerDAO();
            rf.updateRawFlower4(rawId, rawName, rawQuantity, unitPrice, expirationDateParsed, warehouseId, imageUrl, hold, importPrice);

            // Thông báo thành công và chuyển hướng
            session.setAttribute("message", "Raw flower updated successfully!");
            response.sendRedirect("DashMin/rawflower2");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while updating the raw flower: " + e.getMessage());
            request.setAttribute("showErrorPopup", true);
            RawFlowerDAO rf = new RawFlowerDAO();
            request.setAttribute("item", rf.getRawFlowerById(Integer.parseInt(request.getParameter("raw_id"))));
            request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating Raw Flower information";
    }
}