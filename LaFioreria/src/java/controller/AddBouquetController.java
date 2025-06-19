/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;

import java.io.File;
import java.io.IOException;
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
@WebServlet(name = "AddBouquetController", urlPatterns = {"/addBouquet"})
public class AddBouquetController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddBouquetController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddBouquetController at " + request.getContextPath() + "</h1>");
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
        RawFlowerDAO rdao = new RawFlowerDAO();
        CategoryDAO cdao = new CategoryDAO();
        List<Category> cAll = cdao.getBouquetCategory();
        List<RawFlower> all = rdao.getRawFlower();
        // 1. Tất cả hoa để đổ vào dropdown
        request.setAttribute("cateBouquetHome", cAll);
        request.setAttribute("flowerInBouquet", all);

        request.getRequestDispatcher("./DashMin/addBouquet.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     *
     *
     * /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    List<String> savedImageUrls = new ArrayList<>();

    // Lấy context path (VD: "/LaFioreria")
    String contextPath = request.getContextPath();

    // Đường dẫn vật lý để lưu ảnh vào webapp/upload/BouquetIMG/
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

    // Lấy dữ liệu từ form
    String bouquetName = request.getParameter("bouquetName");
    String description = request.getParameter("description");
    int cid = Integer.parseInt(request.getParameter("category"));
    double totalValue = Double.parseDouble(request.getParameter("totalValue"));
    int price = (int) Math.round(totalValue);
    
    if(bouquetName == null || bouquetName.trim().isEmpty()){
        request.setAttribute("err", "Bouquet's Name must not be blank!!!");
            doGet(request, response);
            return;
    }
    // Kiểm tra trùng tên
    BouquetDAO dao = new BouquetDAO();
    for (Bouquet b : dao.getAll()) {
        if (bouquetName.equalsIgnoreCase(b.getBouquetName())) {
            request.setAttribute("err", "Dupplicated Bouquet's name!!! Please try again!");
            doGet(request, response);
            return;
        }
    }

    // Tạo đối tượng Bouquet và lưu vào DB
    Bouquet bouquet = new Bouquet();
    bouquet.setBouquetName(bouquetName);
    bouquet.setDescription(description);
    bouquet.setCid(cid);
    bouquet.setPrice(price);
    int bouquetId = dao.insertBouquet(bouquet);

    if (bouquetId <= 0) {
        request.setAttribute("error", "Không lưu được bouquet.");
        request.getRequestDispatcher("/DashMin/blank.jsp").forward(request, response);
        return;
    }

    // Lưu ảnh vào bảng BouquetImage
    for (String url : savedImageUrls) {
        BouquetImage img = new BouquetImage();
        img.setbouquetId(bouquetId);
        img.setImage_url(url); // Đường dẫn bắt đầu bằng /LaFioreria/upload/BouquetIMG/...
        dao.insertBouquetImage(img);
    }

    // Xử lý nguyên liệu nếu có
    String[] flowerIds = request.getParameterValues("flowerIds");
    String[] quantities = request.getParameterValues("quantities");
    if (flowerIds != null && quantities != null && flowerIds.length == quantities.length) {
        try {
            for (int i = 0; i < flowerIds.length; i++) {
                int flowerID = Integer.parseInt(flowerIds[i]);
                int quantity = Integer.parseInt(quantities[i]);
                BouquetRaw raw = new BouquetRaw();
                raw.setBouquet_id(bouquetId);
                raw.setRaw_id(flowerID);
                raw.setQuantity(quantity);
                dao.insertBouquetRaw(raw);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi raw materials: " + e.getMessage());
            request.getRequestDispatcher("/DashMin/blank.jsp").forward(request, response);
            return;
        }
    }

    // Redirect về danh sách
    response.sendRedirect(contextPath + "/viewBouquet");
}


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
