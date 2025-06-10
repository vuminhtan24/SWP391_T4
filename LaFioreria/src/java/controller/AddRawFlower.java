package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import dal.RawFlowerDAO;
import dal.WarehouseDAO;
import model.Warehouse;
import util.Validate;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AddRawFlower", urlPatterns = {"/addRawFlower"})
public class AddRawFlower extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WarehouseDAO wdao = new WarehouseDAO();
        List<Warehouse> warehouses = wdao.getAllWarehouse();
        request.getSession().setAttribute("listW", warehouses);
        request.getRequestDispatcher("DashMin/rawflower.jsp").forward(request, response); // Sửa đường dẫn
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

            System.out.println("Received data - rawName: " + rawName + ", unitPrice: " + unitPriceStr + 
                             ", importPrice: " + importPriceStr + ", imageUrl: " + imageUrl + 
                             ", warehouseId: " + warehouseIdStr);

            // Khởi tạo WarehouseDAO
            WarehouseDAO wh = new WarehouseDAO();

            // Validate các field
            String rawNameError = null;
            if (rawName != null) {
                rawNameError = Validate.validateLength(rawName, "Raw Flower Name", 1, 45);
                if (rawNameError == null) {
                    rawNameError = Validate.validateText(rawName, "Raw Flower Name");
                }
            } else {
                rawNameError = "Raw Flower Name is required.";
            }
            String unitPriceError = Validate.validateNumberWithRange(unitPriceStr, "Unit Price", 1, Integer.MAX_VALUE);
            String importPriceError = Validate.validateNumberWithRange(importPriceStr, "Import Price", 1, Integer.MAX_VALUE);
            String imageUrlError = Validate.validateImageUrl(imageUrl);
            String warehouseIdError = Validate.validateWarehouseId(warehouseIdStr, wh);

            // Nếu có lỗi, giữ dữ liệu và hiển thị popup
            if (rawNameError != null || unitPriceError != null || importPriceError != null || 
                imageUrlError != null || warehouseIdError != null) {
                request.setAttribute("rawName", rawName);
                request.setAttribute("unitPrice", unitPriceStr);
                request.setAttribute("importPrice", importPriceStr);
                request.setAttribute("imageUrl", imageUrl);
                request.setAttribute("warehouseId", warehouseIdStr);
                request.setAttribute("rawNameError", rawNameError);
                request.setAttribute("unitPriceError", unitPriceError);
                request.setAttribute("importPriceError", importPriceError);
                request.setAttribute("imageUrlError", imageUrlError);
                request.setAttribute("warehouseIdError", warehouseIdError);
                request.setAttribute("showErrorPopup", true);

                WarehouseDAO wdao = new WarehouseDAO();
                List<Warehouse> warehouses = wdao.getAllWarehouse();
                request.getSession().setAttribute("listW", warehouses);
                request.getRequestDispatcher("DashMin/rawflower.jsp").forward(request, response); // Sửa đường dẫn
                return;
            }

            // Xóa các lỗi và dữ liệu trong session nếu validate thành công
            session.removeAttribute("rawNameError");
            session.removeAttribute("unitPriceError");
            session.removeAttribute("importPriceError");
            session.removeAttribute("imageUrlError");
            session.removeAttribute("warehouseIdError");
            session.removeAttribute("rawName");
            session.removeAttribute("unitPrice");
            session.removeAttribute("importPrice");
            session.removeAttribute("imageUrl");
            session.removeAttribute("warehouseId");

            // Chuyển đổi dữ liệu
            int unitPrice = Integer.parseInt(unitPriceStr);
            int importPrice = Integer.parseInt(importPriceStr);
            int warehouseId = Integer.parseInt(warehouseIdStr);

            // Gọi phương thức DAO để thêm nguyên liệu
            RawFlowerDAO rf = new RawFlowerDAO();
            rf.addRawFlower1(rawName, unitPrice, warehouseId, imageUrl, importPrice);

            // Thông báo thành công và chuyển hướng
            session.setAttribute("message", "Raw flower added successfully!");
            response.sendRedirect("DashMin/rawflower2");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while adding the raw flower: " + e.getMessage());
            request.setAttribute("showErrorPopup", true);
            WarehouseDAO wdao = new WarehouseDAO();
            List<Warehouse> warehouses = wdao.getAllWarehouse();
            request.getSession().setAttribute("listW", warehouses);
            request.getRequestDispatcher("DashMin/rawflower.jsp").forward(request, response); // Sửa đường dẫn
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding Raw Flower information";
    }
}