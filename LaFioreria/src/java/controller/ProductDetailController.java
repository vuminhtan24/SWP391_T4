/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BouquetDAO;
import dal.CategoryDAO;
import dal.FlowerBatchDAO;
import dal.FlowerTypeDAO;
import dal.RawFlowerDAO;
import dal.FeedbackDAO;
import dal.WholeSaleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import model.Bouquet;
import model.BouquetImage;
import model.BouquetRaw;
import model.Category;
import model.FlowerBatch;
import model.FlowerType;
import model.RawFlower;
import model.Feedback;
import model.WholeSale;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ProductDetailController", urlPatterns = {"/productDetail"})
public class ProductDetailController extends HttpServlet {

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
            out.println("<title>Servlet ProductDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductDetailController at " + request.getContextPath() + "</h1>");
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
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        BouquetDAO bqdao = new BouquetDAO();
        FlowerTypeDAO rfdao = new FlowerTypeDAO();
        CategoryDAO cdao = new CategoryDAO();
        FlowerBatchDAO fbdao = new FlowerBatchDAO();
        FeedbackDAO fdao = new FeedbackDAO();

        Bouquet detailsBQ = bqdao.getBouquetByID(id);
        String cateName = cdao.getCategoryNameByBouquet(id);
        List<FlowerType> allFlowers = rfdao.getAllFlowerTypes();
        List<FlowerBatch> allBatchs = fbdao.getAllFlowerBatches();
        List<BouquetRaw> bqRaws = bqdao.getFlowerBatchByBouquetID(id);
        List<Bouquet> sameCate = bqdao.searchBouquet(null, null, null, detailsBQ.getCid(), null);
        List<BouquetImage> images = bqdao.getBouquetImage(id);
        String cateDes = cdao.getCategoryDesByBouquet(id);
        List<BouquetImage> allImage = bqdao.getAllBouquetImage();
        fbdao.cleanupExpiredSoftHolds();
        int available = bqdao.bouquetAvailable(id);
        // Xử lý feedback
        List<Feedback> feedback = new ArrayList<>();
        try {
            feedback = fdao.getFeedbacksByBouquetId(id);
            System.out.println("Feedback size for bouquetId " + id + ": " + (feedback != null ? feedback.size() : "null"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error fetching feedback for bouquetId " + id + ": " + e.getMessage());
        }
        // Prepare map of feedback images
        Map<Integer, List<String>> feedbackImages = new HashMap<>();
        for (Feedback f : feedback) {
            feedbackImages.put(f.getFeedbackId(), fdao.getFeedbackImageUrls(f.getFeedbackId()));
        }

        // Lấy tên khách hàng cho từng feedback
        Map<Integer, String> feedbackCustomerNames = new HashMap<>();
        for (Feedback f : feedback) {
            String customerName = fdao.getCustomerNameFromOrderOrUser(f.getCustomerId());
            feedbackCustomerNames.put(f.getFeedbackId(), customerName);
        }

        // Loại bỏ bouquet hiện tại khỏi danh sách cùng danh mục
        for (int i = sameCate.size() - 1; i >= 0; i--) {
            Bouquet b = sameCate.get(i);
            if (b.getBouquetId() == detailsBQ.getBouquetId()) {
                sameCate.remove(i);
            }
        }

        // Đặt các thuộc tính vào request
        request.setAttribute("productId", id);
        request.setAttribute("bouquetAvailable", available);
        request.setAttribute("allImage", allImage);
        request.setAttribute("allBatchs", allBatchs);
        request.setAttribute("images", images);
        request.setAttribute("cateDes", cateDes);
        request.setAttribute("listBouquet", sameCate);
        request.setAttribute("bouquetDetail", detailsBQ);
        request.setAttribute("cateName", cateName);
        request.setAttribute("allFlowers", allFlowers);
        request.setAttribute("cateList", cdao.getBouquetCategory());
        request.setAttribute("flowerInBQ", bqRaws);
        request.setAttribute("feedback", feedback);
        request.setAttribute("feedbackCustomerNames", feedbackCustomerNames);
        request.setAttribute("feedbackImages", feedbackImages);

        request.getRequestDispatcher("./ZeShopper/product-details.jsp").forward(request, response);
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
        try {
            String userIdStr = request.getParameter("user_id");
            String bouquetIdStr = request.getParameter("bouquet_id");
            String requestedQuantityStr = request.getParameter("requested_quantity");
            String note = request.getParameter("note");
            String productId = request.getParameter("productId"); // dùng để redirect về đúng trang

            Integer userId = Integer.parseInt(userIdStr);
            Integer bouquetId = Integer.parseInt(bouquetIdStr);
            Integer requestedQuantity = Integer.parseInt(requestedQuantityStr);

            WholeSaleDAO wsDao = new WholeSaleDAO();

            // Lấy danh sách SHOPPING hiện có của user
            List<WholeSale> listWS = wsDao.getWholeSaleRequestShoppingByUserID(userId);

            // Generate request_group_id (nếu cần gom nhóm theo session)
            String requestGroupId = wsDao.generateOrGetCurrentRequestGroupId(userId);

            // Tạo object mới
            WholeSale ws = new WholeSale(
                    userId,
                    bouquetId,
                    requestedQuantity,
                    note,
                    null, // quoted_price
                    null, // total_price
                    null, // quoted_at
                    null, // responded_at
                    LocalDate.now(),
                    "SHOPPING",
                    null, // expense
                    requestGroupId
            );

            boolean isDuplicate = false;

            for (WholeSale wholeSale : listWS) {
                if (wholeSale.getBouquet_id() == bouquetId) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                response.sendRedirect(request.getContextPath() + "/productDetail?id=" + productId + "&addedWholesale=false");
            } else {
                wsDao.insertWholeSaleRequest(ws);
                response.sendRedirect(request.getContextPath() + "/productDetail?id=" + productId + "&addedWholesale=true");
            }

        } catch (Exception e) {
            String productId = request.getParameter("productId"); // dùng để redirect về đúng trang
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/productDetail?id=" + productId + "&error=true");

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
