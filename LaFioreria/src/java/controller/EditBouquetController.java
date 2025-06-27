package controller;

import dal.BouquetDAO;
import dal.CategoryDAO;
import dal.FlowerBatchDAO;
import dal.FlowerTypeDAO;
import dal.RepairOrdersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Bouquet;
import model.BouquetImage;
import model.BouquetRaw;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = -1L, // Không giới hạn
        maxRequestSize = -1L // Không giới hạn
)
@WebServlet(name = "EditBouquetController", urlPatterns = {"/editBouquet"})
public class EditBouquetController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getAttribute("id") != null ? String.valueOf(request.getAttribute("id")) : request.getParameter("id");
        if (idStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID giỏ hoa");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID giỏ hoa không hợp lệ");
            return;
        }

        BouquetDAO bqdao = new BouquetDAO();
        FlowerTypeDAO ftdao = new FlowerTypeDAO();
        FlowerBatchDAO fbdao = new FlowerBatchDAO();
        CategoryDAO cdao = new CategoryDAO();

        request.setAttribute("images", bqdao.getBouquetImage(id));
        request.setAttribute("bouquetDetail", bqdao.getBouquetByID(id));
        request.setAttribute("cateName", cdao.getCategoryNameByBouquet(id));
        request.setAttribute("allFlowers", ftdao.getAllFlowerTypes());
        request.setAttribute("allBatchs", fbdao.getAllFlowerBatches());
        request.setAttribute("cateList", cdao.getBouquetCategory());
        request.setAttribute("flowerInBQ", bqdao.getFlowerBatchByBouquetID(id));
        request.getRequestDispatcher("./DashMin/editBouquet.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Đọc tham số
        String idParam = request.getParameter("id");
        String repairIdStr = request.getParameter("repairId");
        String oldBatchIdStr = request.getParameter("oldBatchId");
        String fromRepair = request.getParameter("fromRepair");
        int id = -1;
        int repairId = -1;
        int oldBatchId = -1;
        boolean isFromRepair = "true".equalsIgnoreCase(fromRepair);

        try {
            id = Integer.parseInt(idParam);
            if (isFromRepair) {
                repairId = Integer.parseInt(repairIdStr);
                oldBatchId = Integer.parseInt(oldBatchIdStr);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID không hợp lệ");
            request.setAttribute("id", id);
            doGet(request, response);
            return;
        }

        List<String> savedImageUrls = new ArrayList<>();
        BouquetDAO bouquetDAO = new BouquetDAO();
        RepairOrdersDAO repairOrderDAO = new RepairOrdersDAO();

        try {
            // Xử lý upload ảnh
            String uploadPath = request.getServletContext().getRealPath("/upload/BouquetIMG");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists() && !uploadDir.mkdirs()) {
                throw new ServletException("Không thể tạo thư mục upload: " + uploadPath);
            }
            Collection<Part> fileParts = request.getParts().stream()
                    .filter(p -> "imageFiles".equals(p.getName()) && p.getSubmittedFileName() != null && !p.getSubmittedFileName().isEmpty())
                    .collect(Collectors.toList());
            for (Part part : fileParts) {
                String originalName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                String newFileName = System.currentTimeMillis() + "_" + originalName;
                String fullDiskPath = uploadPath + File.separator + newFileName;
                part.write(fullDiskPath);
                String rootPath = fullDiskPath.replace("\\build", "");
                Path source = Paths.get(fullDiskPath);
                Path target = Paths.get(rootPath);
                Files.createDirectories(target.getParent());
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                savedImageUrls.add(newFileName);
            }

            // Cập nhật bouquet_raw
            bouquetDAO.deleteBouquetRaw(id);
            String[] batchIds = request.getParameterValues("batchIds");
            String[] quantities = request.getParameterValues("quantities");
            int newBatchId = -1;
            if (batchIds != null && quantities != null) {
                boolean oldBatchFound = false;
                for (int i = 0; i < batchIds.length; i++) {
                    int bid = Integer.parseInt(batchIds[i]);
                    int quantity = Integer.parseInt(quantities[i]);
                    bouquetDAO.insertBouquetRaw(new BouquetRaw(id, bid, quantity));
                    if (isFromRepair && bid == oldBatchId) {
                        oldBatchFound = true;
                    } else if (isFromRepair && repairOrderDAO.isSameFlowerType(bid, oldBatchId)) {
                        newBatchId = bid; // Chọn batch mới cùng loại hoa
                    }
                }
                if (isFromRepair && oldBatchFound) {
                    request.setAttribute("error", "Bạn phải thay thế lô hoa cũ (ID: " + oldBatchId + ") bằng lô mới.");
                    request.setAttribute("id", id);
                    doGet(request, response);
                    return;
                }
            }

            // Xử lý ảnh
            bouquetDAO.deleteBouquetImage(id);
            String[] existingUrls = request.getParameterValues("existingImageUrls");
            if (existingUrls != null) {
                for (String fullUrl : existingUrls) {
                    String imageName = fullUrl.substring(fullUrl.lastIndexOf("/") + 1);
                    BouquetImage img = new BouquetImage();
                    img.setbouquetId(id);
                    img.setImage_url(imageName);
                    bouquetDAO.insertBouquetImage(img);
                }
            }
            if (!savedImageUrls.isEmpty()) {
                for (String url : savedImageUrls) {
                    BouquetImage img = new BouquetImage();
                    img.setbouquetId(id);
                    img.setImage_url(url);
                    bouquetDAO.insertBouquetImage(img);
                }
            }

            // Cập nhật bouquet
            String category = request.getParameter("category");
            String bqDescription = request.getParameter("bqDescription");
            String totalValueStr = request.getParameter("totalValue");
            String sellValueStr = request.getParameter("sellValue");
            String bqName = request.getParameter("bqName");
            String status = isFromRepair ? "valid" : request.getParameter("status");

            if (bqName == null || bqName.trim().isEmpty()) {
                request.setAttribute("error", "Tên giỏ hoa không được để trống!");
                request.setAttribute("id", id);
                doGet(request, response);
                return;
            }

            List<Bouquet> bqList = bouquetDAO.getAll();
            for (Bouquet bouquet : bqList) {
                if (bouquet.getBouquetId() != id && bouquet.getBouquetName().equalsIgnoreCase(bqName)) {
                    request.setAttribute("error", "Tên giỏ hoa bị trùng! Vui lòng thử lại.");
                    request.setAttribute("id", id);
                    doGet(request, response);
                    return;
                }
            }

            int cateID = Integer.parseInt(category);
            int totalValue = (int) Math.round(Double.parseDouble(totalValueStr));
            int sellValue = (int) Math.round(Double.parseDouble(sellValueStr));
            bouquetDAO.updateBouquet(new Bouquet(id, bqName, bqDescription, cateID, totalValue, sellValue, status));

            // Xử lý repair order nếu từ repair
            if (isFromRepair) {
                repairOrderDAO.completeRepairOrder(repairId, id, oldBatchId, newBatchId);
            }

            // Chuyển hướng
            response.sendRedirect(request.getContextPath() + (isFromRepair ? "/repairOrders" : "/bouquetDetails?id=" + id));

        } catch (IllegalStateException ise) {
            log("Upload thất bại: file quá lớn", ise);
            request.setAttribute("error", "Kích thước file quá lớn. Vui lòng chọn file nhỏ hơn.");
            request.setAttribute("id", id);
            doGet(request, response);
        } catch (SQLException e) {
            log("Lỗi cơ sở dữ liệu", e);
            request.setAttribute("error", "Lỗi cơ sở dữ liệu: " + e.getMessage());
            request.setAttribute("id", id);
            doGet(request, response);
        } catch (Exception e) {
            log("Lỗi không xác định", e);
            request.setAttribute("error", "Lỗi xảy ra: " + e.getMessage());
            request.setAttribute("id", id);
            doGet(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Chỉnh sửa thông tin giỏ hoa";
    }
}