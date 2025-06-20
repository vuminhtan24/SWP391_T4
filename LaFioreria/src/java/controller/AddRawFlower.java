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
import util.Validate;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@WebServlet(name = "AddRawFlower", urlPatterns = {"/addRawFlower"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB lưu tạm vào disk nếu vượt
        maxFileSize = 10 * 1024 * 1024, // Giới hạn 10MB mỗi file
        maxRequestSize = 50 * 1024 * 1024 // Giới hạn 50MB tổng request
)
public class AddRawFlower extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the JSP to display the add form
        request.getRequestDispatcher("/DashMin/addrawflower.jsp").forward(request, response);
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
            // Get parameters from the form
            String flowerName = request.getParameter("flowerName");
            Part filePart = request.getPart("imageFile"); // Lấy file từ input name="imageFile"

            // Validate flower name
            String flowerNameError = Validate.validateText(flowerName, "Flower Type Name");
            if (flowerNameError == null) {
                flowerNameError = Validate.validateLength(flowerName, "Flower Type Name", 1, 45);
            }

            // Check for duplicate flower name
            FlowerTypeDAO ftDAO = new FlowerTypeDAO();
            if (flowerNameError == null && ftDAO.isFlowerNameExists(flowerName)) {
                flowerNameError = "Flower type name already exists.";
            }

            // Validate image file
            String imageError = null;
            if (filePart == null || filePart.getSize() == 0) {
                imageError = "Please select an image file.";
            } else {
                String contentType = filePart.getContentType();
                if (!contentType.startsWith("image/")) {
                    imageError = "File must be an image (jpg, jpeg, png).";
                } else {
                    String submittedFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    if (!submittedFileName.matches(".*\\.(jpg|jpeg|png|JPG|JPEG|PNG)$")) {
                        imageError = "Image must be in .jpg, .jpeg, or .png format.";
                    }
                }
            }

            // If there are errors, retain data and show popup
            if (flowerNameError != null || imageError != null) {
                request.setAttribute("flowerName", flowerName);
                request.setAttribute("flowerNameError", flowerNameError);
                request.setAttribute("imageError", imageError);
                request.setAttribute("showErrorPopup", true);
                request.getRequestDispatcher("/DashMin/addrawflower.jsp").forward(request, response);
                return;
            }

            // Handle image upload
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

            // Add flower type to database
            ftDAO.addFlowerType(flowerName, imageFileName, true);

            // Set success message and redirect
            session.setAttribute("message", "Flower type added successfully!");
            response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
        } catch (Exception e) {
            // Xóa file đã upload nếu có
            if (imageFileName != null && uploadPath != null) {
                Files.deleteIfExists(Paths.get(uploadPath + File.separator + imageFileName));
                if (rootPath != null) {
                    Files.deleteIfExists(Paths.get(rootPath));
                }
            }
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while adding the flower type: " + e.getMessage());
            request.setAttribute("showErrorPopup", true);
            request.getRequestDispatcher("/DashMin/addrawflower.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding Flower Type information";
    }
}