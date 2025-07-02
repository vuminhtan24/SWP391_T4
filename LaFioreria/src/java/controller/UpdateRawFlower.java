package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import dal.FlowerTypeDAO;
import model.FlowerType;
import util.Validate;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@WebServlet(name = "UpdateRawFlower", urlPatterns = {"/update_flower"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB lưu tạm vào disk nếu vượt
        maxFileSize = 10 * 1024 * 1024, // Giới hạn 10MB mỗi file
        maxRequestSize = 50 * 1024 * 1024 // Giới hạn 50MB tổng request
)
public class UpdateRawFlower extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FlowerTypeDAO ftDAO = new FlowerTypeDAO();
        int flowerId = Integer.parseInt(request.getParameter("flower_id"));
        FlowerType flowerType = ftDAO.getFlowerTypeById(flowerId);
        if (flowerType == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy loại hoa với ID " + flowerId);
            return;
        }
        request.setAttribute("item", flowerType);
        request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String imageFileName = null;
        String uploadPath = null;
        String fullDiskPath = null;
        String rootPath = null;

        try {
            // Lấy tham số từ form
            String flowerIdStr = request.getParameter("flower_id");
            String flowerName = request.getParameter("flower_name");
            Part filePart = request.getPart("imageFile");
            String currentImage = request.getParameter("current_image");

            // Validate các field
            String flowerNameError = Validate.validateLength(flowerName, "Tên loại hoa", 1, 45);
            String imageError = null;
            
            FlowerTypeDAO ftDAO = new FlowerTypeDAO();
            if (flowerNameError == null && ftDAO.isFlowerNameExists(flowerName)) {
                flowerNameError = "Flower type name already exists.";
            }

            // Validate file ảnh nếu có upload
            if (filePart != null && filePart.getSize() > 0) {
                String contentType = filePart.getContentType();
                if (!contentType.startsWith("image/")) {
                    imageError = "File phải là ảnh (jpg, jpeg, png).";
                } else {
                    String submittedFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    if (!submittedFileName.matches(".*\\.(jpg|jpeg|png|JPG|JPEG|PNG)$")) {
                        imageError = "Ảnh phải có định dạng .jpg, .jpeg hoặc .png.";
                    }
                }
            }

            // Nếu có lỗi, giữ dữ liệu và hiển thị popup
            if (flowerNameError != null || imageError != null) {
                request.setAttribute("flowerId", flowerIdStr);
                request.setAttribute("flowerName", flowerName);
                request.setAttribute("flowerNameError", flowerNameError);
                request.setAttribute("imageError", imageError);
                request.setAttribute("showErrorPopup", true);

                request.setAttribute("item", ftDAO.getFlowerTypeById(Integer.parseInt(flowerIdStr)));
                request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
                return;
            }

            // Xóa các lỗi và dữ liệu trong session nếu validate thành công
            session.removeAttribute("flowerNameError");
            session.removeAttribute("imageError");
            session.removeAttribute("flowerId");
            session.removeAttribute("flowerName");

            // Chuyển đổi dữ liệu
            int flowerId = Integer.parseInt(flowerIdStr);

            // Lấy thông tin hiện tại của FlowerType để giữ giá trị active
            FlowerType currentFlowerType = ftDAO.getFlowerTypeById(flowerId);
            if (currentFlowerType == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy loại hoa với ID " + flowerId);
                return;
            }

            // Xử lý file ảnh nếu có upload
            String imageToUpdate = currentImage; // Giữ ảnh hiện tại nếu không upload ảnh mới
            if (filePart != null && filePart.getSize() > 0) {
                // Lưu file ảnh mới
                uploadPath = request.getServletContext().getRealPath("/upload/FlowerIMG");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists() && !uploadDir.mkdirs()) {
                    throw new ServletException("Không thể tạo thư mục upload: " + uploadPath);
                }

                String originalName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                imageFileName = System.currentTimeMillis() + "_" + originalName;
                fullDiskPath = uploadPath + File.separator + imageFileName;
                filePart.write(fullDiskPath);

                // Copy từ build folder → folder dự án (root của webapp)
                rootPath = fullDiskPath.replace("\\build", "");
                Path source = Paths.get(fullDiskPath);
                Path target = Paths.get(rootPath);
                Files.createDirectories(target.getParent());
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

                imageToUpdate = imageFileName; // Cập nhật tên file mới

                // Xóa file ảnh cũ nếu có
                if (currentImage != null && !currentImage.isEmpty()) {
                    Files.deleteIfExists(Paths.get(uploadPath + File.separator + currentImage));
                    Files.deleteIfExists(Paths.get(rootPath.replace(imageFileName, currentImage)));
                }
            }

            // Cập nhật loại hoa
            ftDAO.updateFlowerType(flowerId, flowerName, imageToUpdate, currentFlowerType.isActive());

            // Thông báo thành công và chuyển hướng
            session.setAttribute("message", "Cập nhật loại hoa thành công!");
            response.sendRedirect("DashMin/rawflower2");
        } catch (Exception e) {
            // Xóa file đã upload nếu có lỗi
            if (imageFileName != null && uploadPath != null) {
                Files.deleteIfExists(Paths.get(uploadPath + File.separator + imageFileName));
                if (rootPath != null) {
                    Files.deleteIfExists(Paths.get(rootPath));
                }
            }
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi khi cập nhật loại hoa: " + e.getMessage());
            request.setAttribute("showErrorPopup", true);
            FlowerTypeDAO ftDAO = new FlowerTypeDAO();
            request.setAttribute("item", ftDAO.getFlowerTypeById(Integer.parseInt(request.getParameter("flower_id"))));
            request.getRequestDispatcher("DashMin/updaterawflower.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating Raw Flower information";
    }
}