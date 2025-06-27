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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import model.BouquetImage;
import model.FlowerBatch;
import model.FlowerType;
import model.Order;
import model.OrderDetail;
import model.OrderItem;

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
//        Lấy bouquet id
        String bouquetIdStr;
        Object bouquetIdAttr = request.getAttribute("BouquetId");
        if (bouquetIdAttr != null) {
            bouquetIdStr = String.valueOf(bouquetIdAttr);
        } else {
            bouquetIdStr = request.getParameter("BouquetId");
        }
        if (bouquetIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing bouquet ID");
            return;
        }
        int bouquetId;
        try {
            bouquetId = Integer.parseInt(bouquetIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid bouquet ID");
            return;
        }

//        Lấy order id
        String orderIdStr;
        Object orderIdAttr = request.getAttribute("OrderId");
        if (orderIdAttr != null) {
            orderIdStr = String.valueOf(orderIdAttr);
        } else {
            orderIdStr = request.getParameter("OrderId");
        }
        if (orderIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing Order ID");
            return;
        }
        int orderId;
        try {
            orderId = Integer.parseInt(orderIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Order ID");
            return;
        }

//        Lấy order item id
        String orderItemIdStr;
        Object orderItemIdAttr = request.getAttribute("OrderItemID");
        if (orderItemIdAttr != null) {
            orderItemIdStr = String.valueOf(orderItemIdAttr);
        } else {
            orderItemIdStr = request.getParameter("OrderItemID");
        }
        if (orderItemIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing Order Item ID");
            return;
        }
        int orderItemId;
        try {
            orderItemId = Integer.parseInt(orderItemIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Order Item ID");
            return;
        }

        OrderDAO oddao = new OrderDAO();
        BouquetDAO bqdao = new BouquetDAO();
        FlowerTypeDAO ftdao = new FlowerTypeDAO();
        FlowerBatchDAO fbdao = new FlowerBatchDAO();
        CategoryDAO cdao = new CategoryDAO();

        Order order = oddao.getOrderDetailById(orderId);
        OrderItem oi = oddao.getBouquetQuantityInOrder(orderItemId, orderId, bouquetId);
        
        Bouquet detailsBQ = bqdao.getBouquetByID(bouquetId);
        String cateName = cdao.getCategoryNameByBouquet(bouquetId);
        List<FlowerType> allFlowers = ftdao.getAllFlowerTypes();
        List<FlowerBatch> allBatchs = fbdao.getAllFlowerBatches();
        List<BouquetRaw> bqRaws = bqdao.getFlowerBatchByBouquetID(bouquetId);
        List<BouquetImage> images = bqdao.getBouquetImage(bouquetId);

        boolean canMakeAll = true;

        for (BouquetRaw flower : bqRaws) {
            int needed = flower.getQuantity() * oi.getQuantity();
            for (FlowerBatch allBatch : allBatchs) {
                if (flower.getBatchId() == allBatch.getBatchId()) {
                    if (allBatch.getQuantity() < needed) {
                        canMakeAll = false;
                        break;
                    }
                }
            }
        }

        request.setAttribute("BouquetId", bouquetId);
        request.setAttribute("OrderId", orderId);
        request.setAttribute("OrderItemID", orderItemId);
        request.setAttribute("canMakeAll", canMakeAll);
        request.setAttribute("oi", oi);
        request.setAttribute("images", images);
        request.setAttribute("bouquetDetail", detailsBQ);
        request.setAttribute("cateName", cateName);
        request.setAttribute("allFlowers", allFlowers);
        request.setAttribute("allBatchs", allBatchs);
        request.setAttribute("cateList", cdao.getBouquetCategory());
        request.setAttribute("flowerInBQ", bqRaws);
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
        request.setCharacterEncoding("UTF-8");
        String orderItemIdStr = request.getParameter("OrderItemID");
        String orderIdStr = request.getParameter("OrderId");
        String bouquetIdStr = request.getParameter("BouquetId");
        String sellPriceStr = request.getParameter("orderSell");

        int orderItemId;
        int orderId;
        int bouquetId;
        int sellPrice;

        System.out.printf(">>> POST params: BouquetId=%s, OrderId=%s, OrderItemID=%s, orderSell=%s%n",
                request.getParameter("BouquetId"),
                request.getParameter("OrderId"),
                request.getParameter("OrderItemID"),
                request.getParameter("orderSell"));
        try {
            // Validate and parse OrderItemID
            if (orderItemIdStr == null || orderItemIdStr.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing Order Item ID.");
                return;
            }
            orderItemId = Integer.parseInt(orderItemIdStr);

            // Validate and parse OrderId
            if (orderIdStr == null || orderIdStr.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing Order ID.");
                return;
            }
            orderId = Integer.parseInt(orderIdStr);

            // Validate and parse BouquetId
            if (bouquetIdStr == null || bouquetIdStr.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing Bouquet ID.");
                return;
            }
            bouquetId = Integer.parseInt(bouquetIdStr);

            // Validate and parse sellPrice
            if (sellPriceStr == null || sellPriceStr.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing Sell Price.");
                return;
            }
            sellPrice = Integer.parseInt(sellPriceStr);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid numeric format for one or more IDs or Sell Price. Please ensure all values are valid numbers.");
            return;
        }
        OrderDAO oddao = new OrderDAO();
        FlowerBatchDAO fbdao = new FlowerBatchDAO();

        String[] flowerNeedStr = request.getParameterValues("flowerNeeded");
        String[] flowerIdStr = request.getParameterValues("flowerIds");
        String[] batchIdStr = request.getParameterValues("batchIds");

        if (flowerNeedStr != null && flowerIdStr != null && batchIdStr != null) {
            for (int i = 0; i < flowerNeedStr.length; i++) {
                try {
                    int neededQuantity = Integer.parseInt(flowerNeedStr[i].trim());
                    int flowerId = Integer.parseInt(flowerIdStr[i].trim());
                    int batchId = Integer.parseInt(batchIdStr[i].trim());

                    // Gọi hàm DAO
                    fbdao.reduceBatchQuantity(neededQuantity, flowerId, batchId);

                } catch (NumberFormatException e) {
                    System.err.println("Error parsing parameters at index " + i + ": " + e.getMessage());
                    return;
                    // Có thể log lỗi, hoặc bỏ qua dòng lỗi, hoặc thông báo cho người dùng
                }
            }
        } else {
            System.err.println("Missing parameters: One or more of flowerNeeded, flowerIds, batchIds is null");
            return;
        }

        // Proceed with DAO operations if all parameters are valid
        oddao.completeBouquetCreation(orderItemId, orderId, bouquetId, sellPrice);

        response.sendRedirect("/LaFioreria/orderDetail?orderId=" + orderId);
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
