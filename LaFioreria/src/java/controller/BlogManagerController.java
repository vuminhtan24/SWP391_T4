/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dal.BlogDAO;
import dal.CategoryDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import model.Blog;
import model.Category;
import model.User;

/**
 *
 * @author k16
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB lưu tạm vào disk nếu vượt
        maxFileSize = 10 * 1024 * 1024, // Giới hạn 10MB mỗi file
        maxRequestSize = 50 * 1024 * 1024 // Giới hạn 50MB tổng request
)
@WebServlet(name = "BlogManagerController", urlPatterns = {
    "/blogmanager",
    "/blog",
    "/blog/detail",
    "/blog/add",
    "/blog/edit",
    "/blog/delete"
})
public class BlogManagerController extends HttpServlet {

    private static final String BASE_PATH = "/blog";

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
        String path = request.getServletPath();

        switch (path) {
            case BASE_PATH + "manager" ->
                doGetManagerPostList(request, response);

            case BASE_PATH ->
                doGetPostList(request, response);

            case BASE_PATH + "/detail" ->
                doGetDetail(request, response);

            case BASE_PATH + "/add" -> {
                CategoryDAO cDao = new CategoryDAO();
                List<Category> cList = cDao.getAll();
                request.setAttribute("cList", cList);
                request.getRequestDispatcher("../DashMin/AddBlog.jsp").forward(request, response);
            }

            case BASE_PATH + "/edit" ->
                doGetEdit(request, response);

            default ->
                throw new AssertionError();
        }
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
        String path = request.getServletPath();

        switch (path) {
            case BASE_PATH + "/add" -> {
                doAdd(request, response);
            }
            case BASE_PATH + "/edit" -> {
                doEdit(request, response);
            }
            case BASE_PATH + "/delete" -> {
                doDeletePost(request, response);
            }
            default ->
                throw new AssertionError();
        }
    }

    private void doGetEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bidStr = request.getParameter("bid");
        int bid = Integer.parseInt(bidStr);

        List<String> statusL = new ArrayList<>();
        statusL.add("Active");
        statusL.add("Hidden");
        statusL.add("Deleted");

        BlogDAO bDao = new BlogDAO();
        Blog b = bDao.getBlogById(bid);

        CategoryDAO cDao = new CategoryDAO();
        List<Category> cList = cDao.getAll();
        request.setAttribute("cList", cList);
        request.setAttribute("b", b);
        request.setAttribute("statusL", statusL);
        request.getRequestDispatcher("../DashMin/EditBLog.jsp").forward(request, response);
    }

    private void doGetManagerPostList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
        int limit = 10;
        int offset = (page - 1) * limit;
        String search = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");
        String sort = request.getParameter("sort");
        String status = request.getParameter("status");
        int categoryId = Integer.parseInt(request.getParameter("categoryId") != null && !request.getParameter("categoryId").isBlank() ? request.getParameter("categoryId") : "0");

        if (status == null || status.isBlank()) {
            status = null;
        }

        BlogDAO dao = new BlogDAO();
        CategoryDAO cDao = new CategoryDAO();
        List<Blog> blogs = dao.getAllBlogWithFilter(limit, offset, search, sortBy, sort, categoryId, status);
        fullLoadBlogInformation(blogs);
        int totalCount = dao.getTotalBlogCountWithFilter(search, categoryId, null);

        List<Category> cList = cDao.getAll();

        int totalPages = (int) Math.ceil((double) totalCount / limit);

        request.setAttribute("cList", cList);
        request.setAttribute("blogs", blogs);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("DashMin/blogmanager.jsp").forward(request, response);
    }

    private void doGetDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDAO bDao = new BlogDAO();
        String bidStr = request.getParameter("bid");

        int bid = bidStr != null && !bidStr.isBlank() ? Integer.parseInt(bidStr) : -1;

        Blog b = null;

        b = bDao.getBlogById(bid);

        List<Blog> bList = new LinkedList<>();
        bList.add(b);

        fullLoadBlogInformation(bList);

        request.setAttribute("blog", b);
        request.getRequestDispatcher("../ZeShopper/blogdetail.jsp").forward(request, response);
    }

    private void doGetPostList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
        int limit = 10;
        int offset = (page - 1) * limit;
        String search = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");
        String sort = request.getParameter("sort");
        int categoryId = Integer.parseInt(request.getParameter("categoryId") != null && !request.getParameter("categoryId").isBlank() ? request.getParameter("categoryId") : "0");

        CategoryDAO cDao = new CategoryDAO();
        BlogDAO dao = new BlogDAO();
        List<Blog> blogs = dao.getAllBlogWithFilter(limit, offset, search, sortBy, sort, categoryId, "Active");
        fullLoadBlogInformation(blogs);
        int totalCount = dao.getTotalBlogCountWithFilter(search, categoryId, "Active");
        int totalPages = (int) Math.ceil((double) totalCount / limit);

        List<Category> cList = cDao.getAll();

        request.setAttribute("cList", cList);
        request.setAttribute("blogs", blogs);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("ZeShopper/blog.jsp").forward(request, response);
    }

    private void doAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDAO bDao = new BlogDAO();
        String title = request.getParameter("title");
        String cidStr = request.getParameter("category");
        String status = request.getParameter("status");
        String authorStr = request.getParameter("author");
        String preContext = request.getParameter("preContext");
        String context = request.getParameter("content");

        Part filePart = request.getPart("image");

        String uploadPath = request.getServletContext().getRealPath("/upload/BlogIMG");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new ServletException("Không thể tạo thư mục upload: " + uploadPath);
        }

        String originalName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String imageFileName = System.currentTimeMillis() + "_" + originalName;
        String fullDiskPath = uploadPath + File.separator + imageFileName;
        filePart.write(fullDiskPath);

        String rootPath = fullDiskPath.replace("\\build", "");
        Path source = Paths.get(fullDiskPath);
        Path target = Paths.get(rootPath);
        Files.createDirectories(target.getParent());
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

        Category c = new Category();
        User u = new User();

        c.setCategoryId(Integer.parseInt(cidStr));

        u.setUserid(Integer.parseInt(authorStr));

        Blog b = new Blog();
        b.setCategory(c);
        b.setOwner(u);
        b.setTitle(title);
        b.setPre_context(preContext);
        b.setContext(context);
        b.setStatus(status);
        b.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        b.setImg_url(imageFileName);

        if (bDao.addBlog(b)) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("success");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Could not save blog.");
        }
    }

    private void doEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        Map<String, Object> jsonResponse = new HashMap<>();

        try {
            BlogDAO bDao = new BlogDAO();
            String bidStr = request.getParameter("bid");
            String title = request.getParameter("title");
            String cidStr = request.getParameter("category");
            String status = request.getParameter("status");
            String preContext = request.getParameter("preContext");
            String context = request.getParameter("content");
            Part filePart = request.getPart("image");

            // Validate required parameters
            if (bidStr == null || title == null || cidStr == null
                    || status == null || preContext == null || context == null) {
                jsonResponse.put("ok", false);
                jsonResponse.put("message", "Missing required fields.");
                response.getWriter().write(gson.toJson(jsonResponse));
                return;
            }

            // Get existing blog
            Blog b = bDao.getBlogById(Integer.parseInt(bidStr));
            if (b == null) {
                jsonResponse.put("ok", false);
                jsonResponse.put("message", "Blog not found.");
                response.getWriter().write(gson.toJson(jsonResponse));
                return;
            }

            String imageFileName = b.getImg_url(); // Keep existing image by default

            // Handle image upload if new image is provided
            if (filePart != null && filePart.getSize() > 0) {
                // Validate file size (5MB max)
                long maxFileSize = 5 * 1024 * 1024; // 5MB
                if (filePart.getSize() > maxFileSize) {
                    jsonResponse.put("ok", false);
                    jsonResponse.put("message", "Image file size must be less than 5MB.");
                    response.getWriter().write(gson.toJson(jsonResponse));
                    return;
                }

                // Validate file type
                String contentType = filePart.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    jsonResponse.put("ok", false);
                    jsonResponse.put("message", "Only image files are allowed.");
                    response.getWriter().write(gson.toJson(jsonResponse));
                    return;
                }

                try {
                    // Create upload directory if it doesn't exist
                    String uploadPath = request.getServletContext().getRealPath("/upload/BlogIMG");
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists() && !uploadDir.mkdirs()) {
                        throw new ServletException("Could not create upload directory: " + uploadPath);
                    }

                    // Generate unique filename
                    String originalName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String fileExtension = "";
                    int lastDotIndex = originalName.lastIndexOf('.');
                    if (lastDotIndex > 0) {
                        fileExtension = originalName.substring(lastDotIndex);
                    }
                    imageFileName = System.currentTimeMillis() + "_"
                            + originalName.replaceAll("[^a-zA-Z0-9._-]", "_");

                    // Save file to upload directory
                    String fullDiskPath = uploadPath + File.separator + imageFileName;
                    filePart.write(fullDiskPath);

                    // Copy to project root if needed (for development)
                    String rootPath = fullDiskPath.replace("\\build", "");
                    Path source = Paths.get(fullDiskPath);
                    Path target = Paths.get(rootPath);

                    if (!source.equals(target)) {
                        Files.createDirectories(target.getParent());
                        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                    }

                    // Delete old image file if it exists and is different from new one
                    if (b.getImg_url() != null && !b.getImg_url().equals(imageFileName)) {
                        try {
                            File oldFile = new File(uploadPath + File.separator + b.getImg_url());
                            if (oldFile.exists()) {
                                oldFile.delete();
                            }
                            // Also delete from root if exists
                            File oldRootFile = new File(uploadPath.replace("\\build", "") + File.separator + b.getImg_url());
                            if (oldRootFile.exists()) {
                                oldRootFile.delete();
                            }
                        } catch (Exception e) {
                            // Log but don't fail the operation
                            System.err.println("Could not delete old image file: " + e.getMessage());
                        }
                    }

                } catch (IOException e) {
                    jsonResponse.put("ok", false);
                    jsonResponse.put("message", "Failed to upload image: " + e.getMessage());
                    response.getWriter().write(gson.toJson(jsonResponse));
                    return;
                }
            }

            // Ensure blog has an image (either existing or newly uploaded)
            if (imageFileName == null || imageFileName.trim().isEmpty()) {
                jsonResponse.put("ok", false);
                jsonResponse.put("message", "Blog post must have a featured image.");
                response.getWriter().write(gson.toJson(jsonResponse));
                return;
            }

            // Update blog object
            Category c = new Category();
            c.setCategoryId(Integer.parseInt(cidStr));

            b.setCategory(c);
            b.setTitle(title.trim());
            b.setPre_context(preContext.trim());
            b.setContext(context.trim());
            b.setStatus(status.trim());
            b.setImg_url(imageFileName);
            b.setUpdated_at(Timestamp.valueOf(LocalDateTime.now())); // Use updated_at instead of created_at

            // Save to database
            if (bDao.updateBlog(b)) {
                jsonResponse.put("ok", true);
                jsonResponse.put("message", "Blog updated successfully!");
            } else {
                jsonResponse.put("ok", false);
                jsonResponse.put("message", "Could not save blog to database.");
            }

        } catch (NumberFormatException e) {
            jsonResponse.put("ok", false);
            jsonResponse.put("message", "Invalid blog ID or category ID format.");
        } catch (ServletException | IOException e) { // For debugging
            // For debugging
            jsonResponse.put("ok", false);
            jsonResponse.put("message", "Internal server error: " + e.getMessage());
        }

        response.getWriter().write(gson.toJson(jsonResponse));
    }

    private void doDeletePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        Map<String, Object> jsonResponse = new HashMap<>();

        try {
            BlogDAO bDao = new BlogDAO();
            String bid = request.getParameter("bid");

            if (bid == null || bid.trim().isEmpty()) {
                jsonResponse.put("ok", false);
                jsonResponse.put("message", "Missing or invalid blog ID.");
                response.getWriter().write(gson.toJson(jsonResponse));
                return;
            }

            int blogId = Integer.parseInt(bid);

            Blog b = bDao.getBlogById(blogId);

            if (b == null) {
                jsonResponse.put("ok", false);
                jsonResponse.put("message", "Blog does not existed!");
            } else {
                if (b.getStatus().equalsIgnoreCase("Deleted")) {
                    jsonResponse.put("ok", false);
                    jsonResponse.put("message", "This blog is already been deleted!");
                } else {
                    boolean deleted = bDao.updateBlogStatus(blogId, "Deleted");

                    if (deleted) {
                        jsonResponse.put("ok", true);
                        jsonResponse.put("message", "Blog deleted successfully.");
                    } else {
                        jsonResponse.put("ok", false);
                        jsonResponse.put("message", "Failed to delete blog.");
                    }
                }
            }

        } catch (NumberFormatException e) {
            jsonResponse.put("ok", false);
            jsonResponse.put("message", "Blog ID must be a number.");
        } catch (IOException e) {
            jsonResponse.put("ok", false);
            jsonResponse.put("message", "Internal server error.");
        }

        response.getWriter().write(gson.toJson(jsonResponse));
    }

    //Helper
    private void fullLoadBlogInformation(List<Blog> b) {
        UserDAO uDao = new UserDAO();

        for (Blog bl : b) {
            bl.setOwner(uDao.getUserByID(bl.getOwner().getUserid()));
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
    }

}
