/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import model.Bouquet;
import model.Category;
import model.RawFlower;
import model.BouquetRaw;
import dal.BouquetDAO;
import dal.CategoryDAO;
import dal.RawFlowerDAO;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import model.BouquetImage;

/**
 *
 * @author ADMIN
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB lưu tạm vào disk nếu vượt
        maxFileSize = -1L, // -1 = không giới hạn kích thước mỗi file
        maxRequestSize = -1L // -1 = không giới hạn tổng kích thước request
)
@WebServlet(name = "EditBouquetController", urlPatterns = {"/editBouquet"})
public class EditBouquetController extends HttpServlet {

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
            out.println("<title>Servlet EditBouquetController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditBouquetController at " + request.getContextPath() + "</h1>");
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
        // Lấy ID ưu tiên từ attribute (khi forward từ doPost), nếu không thì từ parameter
        String idStr;
        Object idAttr = request.getAttribute("id");
        if (idAttr != null) {
            idStr = String.valueOf(idAttr);
        } else {
            idStr = request.getParameter("id");
        }
        if (idStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing bouquet ID");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid bouquet ID");
            return;
        }

        BouquetDAO bqdao = new BouquetDAO();
        RawFlowerDAO rfdao = new RawFlowerDAO();
        CategoryDAO cdao = new CategoryDAO();

        Bouquet detailsBQ = bqdao.getBouquetByID(id);
        String cateName = cdao.getCategoryNameByBouquet(id);
        List<RawFlower> allFlowers = rfdao.getRawFlower();
        List<BouquetRaw> bqRaws = bqdao.getFlowerByBouquetID(id);
        List<BouquetImage> images = bqdao.getBouquetImage(id);

        request.setAttribute("images", images);
        request.setAttribute("bouquetDetail", detailsBQ);
        request.setAttribute("cateName", cateName);
        request.setAttribute("allFlowers", allFlowers);
        request.setAttribute("cateList", cdao.getBouquetCategory());
        request.setAttribute("flowerInBQ", bqRaws);
        request.getRequestDispatcher("./DashMin/editBouquet.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Đọc ID ngay đầu, để luôn có giá trị khi fallback
        String idParam = request.getParameter("id");
        int id = -1;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            // Không cần throw, sẽ xử lý ở catch chung
        }

        List<String> savedImageUrls = new ArrayList<>();
        BouquetDAO dao = new BouquetDAO();

        try {
            // phần upload ảnh (giữ nguyên như trước)
            String uploadPath = request.getServletContext().getRealPath("/upload/BouquetIMG");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists() && !uploadDir.mkdirs()) {
                throw new ServletException("Không thể tạo thư mục upload: " + uploadPath);
            }
            Collection<Part> fileParts = request.getParts().stream()
                    .filter(p -> "imageFiles".equals(p.getName())
                    && p.getSubmittedFileName() != null
                    && !p.getSubmittedFileName().isEmpty())
                    .collect(Collectors.toList());
            for (Part part : fileParts) {
                String originalName = Paths.get(part.getSubmittedFileName())
                        .getFileName().toString();
                String newFileName = System.currentTimeMillis() + "_" + originalName;

                // 1) Viết (move) file tạm → thư mục build
                String fullDiskPath = uploadPath + File.separator + newFileName;
                part.write(fullDiskPath);

                // 2) Copy từ build folder → folder dự án (root của webapp)
                //    rootPath sẽ là đường dẫn thực sự đến folder webapp/upload/BouquetIMG
                String rootPath = fullDiskPath.replace("\\build", "");
                Path source = Paths.get(fullDiskPath);
                Path target = Paths.get(rootPath);
                // Tạo thư mục nếu chưa có
                Files.createDirectories(target.getParent());
                // Copy file (thay thế nếu đã tồn tại)
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

                savedImageUrls.add(newFileName);
            }

            // Xử lý raw materials và images
            dao.deleteBouquetRaw(id);
            String[] flowerIds = request.getParameterValues("flowerIds");
            String[] quantities = request.getParameterValues("quantities");
            if (flowerIds != null && quantities != null) {
                for (int i = 0; i < flowerIds.length; i++) {
                    int fid = Integer.parseInt(flowerIds[i]);
                    int quantity = Integer.parseInt(quantities[i]);
                    dao.insertBouquetRaw(new BouquetRaw(id, fid, quantity));
                }
            }
            // Lấy các ảnh cũ được giữ lại từ hidden input
            String[] existingUrls = request.getParameterValues("existingImageUrls");

// Xóa toàn bộ ảnh cũ (trong DB)
            dao.deleteBouquetImage(id);

// Lưu lại các ảnh cũ được giữ lại
            if (existingUrls != null) {
                for (String fullUrl : existingUrls) {
                    String imageName = fullUrl.substring(fullUrl.lastIndexOf("/") + 1);
                    BouquetImage img = new BouquetImage();
                    img.setbouquetId(id);
                    img.setImage_url(imageName);
                    dao.insertBouquetImage(img);
                }
            }

// Lưu ảnh mới (vừa upload)
            if (!savedImageUrls.isEmpty()) {
                for (String url : savedImageUrls) {
                    BouquetImage img = new BouquetImage();
                    img.setbouquetId(id);
                    img.setImage_url(url);
                    dao.insertBouquetImage(img);
                }
            }

            // Cập nhật bouquet
            String category = request.getParameter("category");
            String bqDescription = request.getParameter("bqDescription");
            String totalValueStr = request.getParameter("totalValue");
            int cateID = Integer.parseInt(category);
            String bqName = request.getParameter("bqName");

            if (bqName == null || bqName.trim().isEmpty()) {
                request.setAttribute("id", id);
                request.setAttribute("error", "Bouquet's Name must not be blank!!!");
                doGet(request, response);
                return;
            }

            List<Bouquet> bqL = new ArrayList<>();
            bqL = new BouquetDAO().getAll();
            for (Bouquet bouquet : bqL) {
                if (bouquet.getBouquetId() != id) {
                    if (bouquet.getBouquetName().equalsIgnoreCase(bqName)) {
                        request.setAttribute("id", id);
                        request.setAttribute("error", "Dupplicated Bouquet Name!!! Please try again");
                        doGet(request, response);
                        return;
                    }
                }
            }

            int totalValue = (int) Math.round(Double.parseDouble(totalValueStr));
            dao.updateBouquet(new Bouquet(id, bqName, bqDescription, cateID, totalValue));

            response.sendRedirect(request.getContextPath() + "/bouquetDetails?id=" + id);

        } catch (IllegalStateException ise) {
            log("Upload failed: file vượt quá kích thước cho phép", ise);
            request.setAttribute("error", "Kích thước file quá lớn. Vui lòng chọn file nhỏ hơn.");
            request.setAttribute("id", id);
            doGet(request, response);
        } catch (Exception e) {
            log("Unexpected error", e);
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            request.setAttribute("id", id);
            doGet(request, response);
        }
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
