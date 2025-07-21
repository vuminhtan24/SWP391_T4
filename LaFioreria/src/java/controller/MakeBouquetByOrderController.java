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
import dal.FlowerBatchDAO;
import dal.FlowerTypeDAO;
import dal.OrderDAO;
import dal.RawFlowerDAO;
import dal.WholeSaleDAO;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import model.BouquetImage;
import model.FlowerBatch;
import model.FlowerType;
import model.Order;
import model.OrderDetail;
import model.OrderItem;
import model.RequestFlower;
import model.WholeSaleFlower;
import model.WholesaleOrderDetail;

/**
 *
 * @author ADMIN
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB lưu tạm vào disk nếu vượt
        maxFileSize = -1L, // -1 = không giới hạn kích thước mỗi file
        maxRequestSize = -1L // -1 = không giới hạn tổng kích thước request
)
@WebServlet(name = "MakeBouquetByOrderController", urlPatterns = {"/makeBouquet"})
public class MakeBouquetByOrderController extends HttpServlet {

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
            out.println("<title>Servlet MakeBouquetByOrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MakeBouquetByOrderController at " + request.getContextPath() + "</h1>");
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

        String bouquetIdStr = request.getParameter("BouquetId");
        String orderIdStr = request.getParameter("OrderId");
        String orderItemIdStr = request.getParameter("OrderItemID");
        String orderType = request.getParameter("orderType");

        Integer bouquetId = Integer.parseInt(bouquetIdStr);
        Integer orderId = Integer.parseInt(orderIdStr);
        Integer orderItemId = Integer.parseInt(orderItemIdStr);

        BouquetDAO bDao = new BouquetDAO();
        OrderDAO oDao = new OrderDAO();
        WholeSaleDAO wsDao = new WholeSaleDAO();
        FlowerBatchDAO batchDao = new FlowerBatchDAO();
        FlowerTypeDAO flowerDao = new FlowerTypeDAO();
        CategoryDAO cDao = new CategoryDAO();

        // Thông tin chung
        Bouquet bouquet = bDao.getBouquetByID(bouquetId);
        List<BouquetImage> listImage = bDao.getBouquetImage(bouquetId);
        List<BouquetRaw> bqRaws = bDao.getFlowerBatchByBouquetID(bouquetId);
        List<FlowerBatch> allBatches = batchDao.getAllFlowerBatches();
        List<FlowerType> allFlowers = flowerDao.getAllFlowerTypes();
        List<RequestFlower> rqFlower = oDao.getRequestFlowerByOrder(orderId, orderItemId);

        String cateName = cDao.getCategoryNameByBouquet(bouquetId);
        OrderItem orderItem = oDao.getOrderItemByID(orderItemId, orderId, bouquetId);
        
        // 👉 Nếu wholesale, lấy thêm dữ liệu hoa được báo giá
        List<WholesaleOrderDetail> wholesaleDetails = new ArrayList<>();
        List<OrderDetail> orderDetails = new ArrayList<>();
        
        if (orderType != null && orderType.equalsIgnoreCase("wholesale")) {
            wholesaleDetails = oDao.getWholesaleOrderDetailsByOrder(orderId, orderItemId, bouquetId);

            boolean allAdded = true;

            for (WholesaleOrderDetail wholesaleDetail : wholesaleDetails) {
                boolean found = false;
                for (RequestFlower rf : rqFlower) {
                    if (rf.getFlowerId() == wholesaleDetail.getFlowerId()
                            && rf.getStatus() != null && rf.getStatus().equalsIgnoreCase("done")) {
                        found = true;
                        break;  // Tìm thấy 1 match là đủ cho hoa này
                    }
                }
                if (!found) {
                    allAdded = false;  // Chỉ cần 1 hoa không match là cả đơn không đủ
                    break;
                }
            }

            if (allAdded) {
                // CHỈ cập nhật nếu order_item hiện tại chưa phải là 'done'
                if (!"done".equalsIgnoreCase(orderItem.getStatus())) {
                    oDao.updateOrderItemStatus(orderItemId, "Added");

                    // Cập nhật lại thông tin orderItem sau khi update
                    orderItem = oDao.getOrderItemByID(orderItemId, orderId, bouquetId);
                }
            }
            request.setAttribute("allAdded", allAdded);
        } else if (orderType != null && orderType.equalsIgnoreCase("retail")) {
            orderDetails = oDao.getOrderItemsByOrderId(orderItemId);
        }
        
        request.setAttribute("orderDetails", orderDetails);
        request.setAttribute("orderItem", orderItem);
        request.setAttribute("orderType", orderType);
        request.setAttribute("orderItem", orderItem);
        request.setAttribute("bouquetId", bouquetId);
        request.setAttribute("bouquet", bouquet);
        request.setAttribute("listImage", listImage);
        request.setAttribute("flowerInBQ", bqRaws);
        request.setAttribute("allBatchs", allBatches);
        request.setAttribute("allFlowers", allFlowers);
        request.setAttribute("cateName", cateName);
        request.setAttribute("wholesaleDetails", wholesaleDetails);

        request.getRequestDispatcher("./DashMin/makeBouquetByOrder.jsp").forward(request, response);
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

        String[] flowerIds = request.getParameterValues("flowerIds[]");
        String[] totalFlowerQuantities = request.getParameterValues("totalFlowerQuantities[]");
        String[] flowerWholesalePrices = request.getParameterValues("flowerWholesalePrices[]");
        String bouquetIdStr = request.getParameter("bouquetId");
        String orderIdStr = request.getParameter("orderId");
        String orderItemIdStr = request.getParameter("orderItemId");
        String orderType = request.getParameter("orderType");
        String[] batchIds = request.getParameterValues("batchIds[]");
        String action = request.getParameter("action");

        int bouquetId = Integer.parseInt(bouquetIdStr);
        int orderId = Integer.parseInt(orderIdStr);
        int orderItemId = Integer.parseInt(orderItemIdStr);

        OrderDAO oDao = new OrderDAO();
        FlowerBatchDAO fbDao = new FlowerBatchDAO();

        boolean addedAtLeastOne = false;

        if (action.equalsIgnoreCase("request")) {
            if (flowerIds != null && totalFlowerQuantities != null && flowerWholesalePrices != null) {
                for (int i = 0; i < flowerIds.length; i++) {
                    int flowerId = Integer.parseInt(flowerIds[i]);
                    int quantity = Integer.parseInt(totalFlowerQuantities[i]); // quantity chính là tổng số hoa cần
                    int price = Integer.parseInt(flowerWholesalePrices[i]);

                    RequestFlower rf = new RequestFlower();
                    rf.setOrderId(orderId);
                    rf.setOrderItemId(orderItemId);
                    rf.setFlowerId(flowerId);
                    rf.setQuantity(quantity);
                    rf.setPrice(price);

                    if (!oDao.isDuplicateRequest(orderId, orderItemId, flowerId)) {
                        oDao.addRequest(rf);
                        addedAtLeastOne = true;  // Đánh dấu đã thêm ít nhất 1 dòng
                    }
                }
            }

            if (addedAtLeastOne) {
                oDao.updateOrderItemStatus(orderItemId, "Requested");
            }
            response.sendRedirect(request.getContextPath() + "/makeBouquet?BouquetId=" + bouquetId + "&OrderId=" + orderId + "&OrderItemID=" + orderItemId + "&orderType=" + orderType);

        } else if (action.equalsIgnoreCase("complete")) {
            oDao.completeBouquetCreation(orderItemId, orderId, bouquetId);
            if (totalFlowerQuantities != null && batchIds != null && flowerIds != null) {
                for (int i = 0; i < batchIds.length; i++) {
                    int flowerId = Integer.parseInt(flowerIds[i]);
                    int quantity = Integer.parseInt(totalFlowerQuantities[i]); // quantity chính là tổng số hoa cần
                    int batchId = Integer.parseInt(batchIds[i]);

                    fbDao.reduceBatchQuantity(quantity, flowerId, batchId);
                }
            }
            response.sendRedirect(request.getContextPath() + "/orderDetail?orderId=" + orderId);

        }

        // ✅ Sau khi insert xong, redirect hoặc forward về trang nào đó
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
