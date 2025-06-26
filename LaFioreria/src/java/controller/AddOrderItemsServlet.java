package controller;

import dal.OrderDAO;
import model.Order;
import model.OrderDetail; 
import model.Bouquet;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet for handling adding items to an existing order.
 *
 * @author VU MINH TAN
 */
@WebServlet(name = "AddOrderItemsServlet", urlPatterns = {"/addOrderItems"})
public class AddOrderItemsServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method. Displays the form to add items
     * to a specific order.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String orderIdParam = request.getParameter("orderId");
        if (orderIdParam == null || orderIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/orderManagement?errorMessage=" + URLEncoder.encode("Order ID is missing for adding items.", StandardCharsets.UTF_8.toString()));
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/orderManagement?errorMessage=" + URLEncoder.encode("Invalid Order ID format for adding items.", StandardCharsets.UTF_8.toString()));
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.getOrderDetailById(orderId);
        if (order == null) {
            response.sendRedirect(request.getContextPath() + "/orderManagement?errorMessage=" + URLEncoder.encode("Order not found with ID: " + orderId + " for adding items.", StandardCharsets.UTF_8.toString()));
            return;
        }

        List<OrderDetail> currentOrderItems = orderDAO.getOrderItemsByOrderId(orderId);
        List<Bouquet> allBouquets = orderDAO.getAllBouquets();

        request.setAttribute("order", order);
        request.setAttribute("currentOrderItems", currentOrderItems);
        request.setAttribute("allBouquets", allBouquets);

        String successMessage = request.getParameter("successMessage");
        if (successMessage != null && !successMessage.isEmpty()) {
            request.setAttribute("successMessage", successMessage);
        }
        String errorMessage = request.getParameter("errorMessage");
        if (errorMessage != null && !errorMessage.isEmpty()) {
            request.setAttribute("errorMessage", errorMessage);
        }

        request.getRequestDispatcher("/DashMin/orderAddItems.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method. Processes the submission of
     * the form to add items to an order.
     *
     * @param request servlet request
     * @param response servlet servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String orderIdParam = request.getParameter("orderId");
        String[] bouquetIds = request.getParameterValues("bouquetId");
        String[] quantities = request.getParameterValues("quantity");

        String errorMessage = null;
        int orderId = -1;

        if (orderIdParam == null || orderIdParam.isEmpty()) {
            errorMessage = "Order ID is missing.";
        } else {
            try {
                orderId = Integer.parseInt(orderIdParam);
            } catch (NumberFormatException e) {
                errorMessage = "Invalid Order ID format.";
            }
        }

        if (errorMessage == null && (bouquetIds == null || bouquetIds.length == 0)) {
            errorMessage = "No products selected to add.";
        }

        OrderDAO orderDAO = new OrderDAO();

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("order", orderDAO.getOrderDetailById(orderId));
            request.setAttribute("currentOrderItems", orderDAO.getOrderItemsByOrderId(orderId));
            request.setAttribute("allBouquets", orderDAO.getAllBouquets());
            request.getRequestDispatcher("/DashMin/orderAddItems.jsp").forward(request, response);
            return;
        }

        boolean allItemsAdded = true;
        double currentTotalImport = 0.0;
        double currentTotalSellDouble = 0.0; // Sử dụng biến double để tính toán, sau đó ép kiểu int nếu cần cho OrderDetail

        List<Bouquet> allBouquetsData = orderDAO.getAllBouquets();

        for (int i = 0; i < bouquetIds.length; i++) {
            try {
                int bouquetId = Integer.parseInt(bouquetIds[i]);
                int quantity = Integer.parseInt(quantities[i]);

                if (quantity <= 0) {
                    errorMessage = "Quantity must be a positive number for all items.";
                    allItemsAdded = false;
                    break;
                }

                double importPrice = -1; 
                int sellPrice = -1; // ✅ THAY ĐỔI: sellPrice là int

                for (Bouquet b : allBouquetsData) {
                    if (b.getBouquetId() == bouquetId) {
                        importPrice = b.getPrice();
                        sellPrice = b.getSellPrice(); // ✅ Lấy sellPrice là int
                        break;
                    }
                }

                if (importPrice <= 0 || sellPrice <= 0) {
                    errorMessage = "Could not find valid price (import or sell) for bouquet ID: " + bouquetId;
                    allItemsAdded = false;
                    break;
                }

                // ✅ THAY ĐỔI: Tạo OrderDetail với unitPrice là String và sellPrice là int
                OrderDetail newItem = new OrderDetail(orderId, bouquetId, quantity, String.valueOf(importPrice), sellPrice); 
                if (!orderDAO.addOrderItem(newItem)) {
                    errorMessage = "Failed to add item for bouquet ID: " + bouquetId;
                    allItemsAdded = false;
                    break;
                }

                currentTotalImport += importPrice * quantity;
                currentTotalSellDouble += sellPrice * quantity; // Tính toán tổng tiền bán dưới dạng double

            } catch (NumberFormatException e) {
                errorMessage = "Invalid number format for bouquet ID or quantity.";
                allItemsAdded = false;
                break;
            } catch (Exception e) {
                errorMessage = "An unexpected error occurred.";
                e.printStackTrace();
                allItemsAdded = false;
                break;
            }
        }

        if (allItemsAdded && errorMessage == null) {
            String formattedImport = String.format("%.2f", currentTotalImport);
            String formattedSell = String.format("%.2f", currentTotalSellDouble); // ✅ Sử dụng double cho String.format

            Order currentOrder = orderDAO.getOrderDetailById(orderId);
            if (currentOrder != null) {
                orderDAO.updateOrder(orderId, formattedImport, formattedSell, currentOrder.getStatusId(), currentOrder.getShipperId());
            } else {
                errorMessage = "Failed to retrieve current order for total update.";
            }
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("order", orderDAO.getOrderDetailById(orderId));
            request.setAttribute("currentOrderItems", orderDAO.getOrderItemsByOrderId(orderId));
            request.setAttribute("allBouquets", orderDAO.getAllBouquets());
            request.getRequestDispatcher("/DashMin/orderAddItems.jsp").forward(request, response);
        } else {
            String successMessage = "Products added successfully to order ID " + orderId + "!";
            String encodedSuccessMessage = URLEncoder.encode(successMessage, StandardCharsets.UTF_8.toString());
            response.sendRedirect(request.getContextPath() + "/orderDetail?orderId=" + orderId + "&successMessage=" + encodedSuccessMessage);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding items to an existing order.";
    }
}
