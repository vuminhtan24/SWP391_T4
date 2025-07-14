package controller;

import dal.BouquetDAO;
import dal.CartDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate; // Import lớp LocalDate
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects; // Import Objects để so sánh an toàn hơn

import model.BouquetImage;
import model.CartDetail;
import model.Order; // Import lớp Order
import model.OrderItem; // Import lớp OrderItem
import model.User;

/**
 * Servlet xử lý các yêu cầu liên quan đến Checkout. Bao gồm hiển thị giỏ hàng,
 * cập nhật, xóa và đặt hàng.
 *
 * @author Legion
 */
@WebServlet(name = "CheckOutController", urlPatterns = {"/checkout"})
public class CheckOutController extends HttpServlet {

    /**
     * Xử lý yêu cầu GET để hiển thị trang checkout và giỏ hàng.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("currentAcc");
        List<CartDetail> cartDetails = new ArrayList<>();
        BouquetDAO bouDao = new BouquetDAO();

        // Xử lý giỏ hàng cho khách vãng lai (chưa đăng nhập)
        if (currentUser == null) {
            BouquetDAO bDao = new BouquetDAO();
            List<CartDetail> sessionCart = (List<CartDetail>) request.getSession().getAttribute("cart");
            if (sessionCart != null) {
                cartDetails = sessionCart;
            }

            List<List<BouquetImage>> bqImages = new ArrayList<>();

            if (!cartDetails.isEmpty()) {
                for (CartDetail cd : cartDetails) {
                    // Lấy thông tin chi tiết của Bouquet để hiển thị giá, mô tả, v.v.
                    cd.setBouquet(bDao.getBouquetFullInfoById(cd.getBouquetId()));
                    bqImages.add(bouDao.getBouquetImage(cd.getBouquetId()));
                }
            }

            // Đặt các thuộc tính cho JSP
            request.setAttribute("cartDetails", cartDetails);
            request.setAttribute("cartImages", bqImages);
            request.setAttribute("user", null); // Người dùng là khách
            request.setAttribute("isGuest", true);

        } else { // Xử lý giỏ hàng cho người dùng đã đăng nhập
            try {
                int customerId = currentUser.getUserid();
                CartDAO cartDAO = new CartDAO();
                cartDetails = cartDAO.getCartDetailsByCustomerId(customerId);

                List<List<BouquetImage>> bqImages = new ArrayList<>();

                if (!cartDetails.isEmpty()) {
                    for (CartDetail cd : cartDetails) {
                        // Lấy thông tin chi tiết của Bouquet nếu chưa được load
                        if (cd.getBouquet() == null) {
                            BouquetDAO bDao = new BouquetDAO(); // Tạo lại DAO nếu cần
                            cd.setBouquet(bDao.getBouquetFullInfoById(cd.getBouquetId()));
                        }
                        bqImages.add(bouDao.getBouquetImage(cd.getBouquetId()));
                    }
                }

                request.setAttribute("cartImages", bqImages);
                request.setAttribute("cartDetails", cartDetails);
                request.setAttribute("user", currentUser);
                request.setAttribute("isGuest", false);

            } catch (Exception e) {
                System.err.println("Lỗi khi tải giỏ hàng cho khách hàng " + currentUser.getUserid() + ": " + e.getMessage());
                request.setAttribute("error", "Failed to load cart items");
                request.setAttribute("cartDetails", new ArrayList<>()); // Trả về giỏ hàng trống nếu có lỗi
                request.setAttribute("user", currentUser);
                request.setAttribute("isGuest", false);
            }
        }

        double totalAmount = 0.0;
        int totalItems = 0;

        // Tính toán tổng số tiền và tổng số lượng item
        for (CartDetail item : cartDetails) {
            totalItems += item.getQuantity();
            if (item.getBouquet() != null) {
                totalAmount += item.getQuantity() * item.getBouquet().getSellPrice(); // Sử dụng sellPrice
            }
        }

        request.setAttribute("totalItems", totalItems);
        request.setAttribute("totalAmount", totalAmount); // Đặt totalAmount để JSP có thể truy cập

        request.getRequestDispatcher("./ZeShopper/checkout.jsp").forward(request, response);
    }

    /**
     * Xử lý yêu cầu POST. Dựa vào tham số "action" để thực hiện các hành động
     * khác nhau như thêm, cập nhật, xóa sản phẩm trong giỏ hàng, hoặc đặt hàng.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Đảm bảo encoding để đọc dữ liệu tiếng Việt nếu có
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json"); // Luôn đặt Content-Type cho phản hồi JSON

        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            // Nếu không có action, giả định đây là yêu cầu đặt hàng (placeOrder)
            processOrder(request, response);
        } else {
            switch (action) {
                case "add":
                    add(request, response);
                    break;
                case "update":
                    update(request, response);
                    break;
                case "delete":
                    delete(request, response);
                    break;
                case "placeOrder": // Case cho hành động đặt hàng
                    processOrder(request, response);
                    break;
                default:
                    // Gửi lỗi nếu action không hợp lệ
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"status\": \"error\", \"message\": \"Hành động không hợp lệ: " + action + "\"}");
            }
        }
    }

    /**
     * Phương thức xử lý logic đặt hàng và lưu vào cơ sở dữ liệu.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    private void processOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("currentAcc");
        int customerId = (currentUser != null) ? currentUser.getUserid() : -1; // Khách vãng lai = -1
        String paymentMethod = request.getParameter("paymentMethod");
        HttpSession session = request.getSession(); // Get session to store order details

        System.out.println("DEBUG: Entering processOrder method.");
        System.out.println("DEBUG: Payment Method: " + paymentMethod);

        // Lấy thông tin từ form
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String addressLine = request.getParameter("addressLine");
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");

        System.out.println("DEBUG: Customer Info - Name: " + fullName + ", Phone: " + phoneNumber);

        // Địa chỉ chỉ lấy district và province
        StringBuilder fullAddressBuilder = new StringBuilder();
        if (addressLine != null && !addressLine.trim().isEmpty()) {
            fullAddressBuilder.append(addressLine.trim());
        }
        if (ward != null && !ward.trim().isEmpty()) {
            if (fullAddressBuilder.length() > 0) {
                fullAddressBuilder.append(", ");
        }
            fullAddressBuilder.append(ward.trim());
        }
        if (district != null && !district.trim().isEmpty()) {
            if (fullAddressBuilder.length() > 0) {
                fullAddressBuilder.append(", ");
        }
            fullAddressBuilder.append(district.trim());
        }
        if (province != null && !province.trim().isEmpty()) {
            if (fullAddressBuilder.length() > 0) {
                fullAddressBuilder.append(", ");
        }
            fullAddressBuilder.append(province.trim());
        }
        String fullAddress = fullAddressBuilder.toString();
        System.out.println("DEBUG: Full Address: " + fullAddress);

        String totalSellStr = request.getParameter("totalAmount");
        double actualTotalSell;
        try {
            actualTotalSell = Double.parseDouble(totalSellStr);
            System.out.println("DEBUG: Total Amount (parsed): " + actualTotalSell);
        } catch (NumberFormatException e) {
            System.err.println("ERROR: NumberFormatException for totalAmount: " + totalSellStr);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Tổng tiền không hợp lệ.\"}");
            return;
        }

        // Tạo đơn hàng
        Order order = new Order();
        order.setCustomerId(customerId == -1 ? null : customerId); // null nếu là khách vãng lai
        order.setCustomerName(fullName);
        order.setCustomerPhone(phoneNumber);
        order.setCustomerAddress(fullAddress);
        order.setOrderDate(LocalDate.now().toString());
        order.setTotalSell(String.valueOf(actualTotalSell));
        order.setTotalImport(String.valueOf(actualTotalSell / 5)); // Giả định lợi nhuận 20%
        order.setPaymentMethod(paymentMethod);
        order.setStatusId(1); // Chờ xử lý

        CartDAO cartDAO = new CartDAO();
        try {
            System.out.println("DEBUG: Attempting to insert order.");
            int orderId = cartDAO.insertOrder(order);
            if (orderId == -1) {
                System.err.println("ERROR: Failed to insert order, orderId is -1. Check database insertion logic.");
                throw new Exception("Không thể tạo đơn hàng");
            }
            System.out.println("DEBUG: Order inserted successfully. Order ID: " + orderId);


            List<CartDetail> cartItems;
            if (currentUser != null) {
                System.out.println("DEBUG: Getting cart details for logged-in user: " + customerId);
                cartItems = cartDAO.getCartDetailsByCustomerId(customerId);
            } else {
                System.out.println("DEBUG: Getting cart details from session for guest user.");
                cartItems = (List<CartDetail>) request.getSession().getAttribute("cart");
            }

            if (cartItems == null || cartItems.isEmpty()) {
                System.err.println("ERROR: Cart is empty or null after retrieval.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"status\": \"error\", \"message\": \"Giỏ hàng trống.\"}");
                return;
            }
            System.out.println("DEBUG: Cart items count: " + cartItems.size());


            for (CartDetail cartItem : cartItems) {
                System.out.println("DEBUG: Processing cart item Bouquet ID: " + cartItem.getBouquetId() + ", Quantity: " + cartItem.getQuantity());
                if (cartItem.getBouquet() == null) {
                    BouquetDAO bouquetDAO = new BouquetDAO();
                    cartItem.setBouquet(bouquetDAO.getBouquetFullInfoById(cartItem.getBouquetId()));
                    System.out.println("DEBUG: Loaded Bouquet info for ID: " + cartItem.getBouquetId());
                }

                OrderItem orderItem = new OrderItem(
                        orderId,
                        cartItem.getBouquetId(),
                        cartItem.getQuantity(),
                        cartItem.getBouquet().getSellPrice()
                );
                System.out.println("DEBUG: Attempting to insert order item for Bouquet ID: " + cartItem.getBouquetId());
                cartDAO.insertOrderItem(orderItem);
                System.out.println("DEBUG: Order item inserted successfully.");
            }

            // Xoá giỏ hàng sau khi đặt
            if (currentUser != null) {
                System.out.println("DEBUG: Deleting cart for logged-in user: " + customerId);
                cartDAO.deleteCartByCustomerId(customerId);
                System.out.println("DEBUG: Cart deleted for logged-in user.");
            } else {
                System.out.println("DEBUG: Removing cart from session for guest user.");
                request.getSession().removeAttribute("cart");
                System.out.println("DEBUG: Cart removed from session.");
            }

            // Always return JSON response
            if ("vietqr".equals(paymentMethod)) {
                // Store orderId and amount in session for secure retrieval by ConfirmVietQRPayment
                session.setAttribute("currentOrderId", orderId);
                session.setAttribute("currentOrderAmount", (int) actualTotalSell); // Cast to int if amount is always integer for QR
                System.out.println("DEBUG: Stored orderId " + orderId + " and amount " + (int)actualTotalSell + " in session.");
                // Return JSON with redirect URL
                String redirectUrl = request.getContextPath() + "/ConfirmVietQRPayment";
                response.getWriter().write("{\"status\": \"success\", \"paymentMethod\": \"vietqr\", \"orderId\": " + orderId + ", \"amount\": " + (int)actualTotalSell + ", \"redirectUrl\": \"" + redirectUrl + "\"}");
                System.out.println("DEBUG: Sent JSON success response with redirectUrl for VietQR payment.");

            } else {
                response.getWriter().write("{\"status\": \"success\", \"message\": \"Đơn hàng đã được đặt thành công! Mã đơn hàng: " + orderId + "\"}");
                System.out.println("DEBUG: Sent JSON success response for non-VietQR payment.");
            }

        } catch (Exception e) {
            System.err.println("ERROR: An unexpected error occurred during order processing. Details: " + e.getMessage());
            e.printStackTrace(); // In toàn bộ stack trace để gỡ lỗi chi tiết
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Có lỗi khi đặt hàng: " + e.getMessage() + "\"}");
            System.out.println("DEBUG: Sent JSON error response.");
        }
    }

    /**
     * Xử lý thêm sản phẩm vào giỏ hàng.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     */
    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User currentUser = (User) request.getSession().getAttribute("currentAcc");

        int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (currentUser == null) {
            List<CartDetail> cart = (List<CartDetail>) request.getSession().getAttribute("cart");
            if (cart == null) {
                cart = new LinkedList<>();
            }

            CartDetail existingItem = null;
            for (CartDetail item : cart) {
                if (item.getBouquetId() == bouquetId) {
                    existingItem = item;
                    break;
                }
            }

            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
            } else {
                CartDetail newItem = new CartDetail();
                newItem.setCustomerId(-1); // ID khách hàng ảo cho session cart
                newItem.setBouquetId(bouquetId);
                newItem.setQuantity(quantity);
                cart.add(newItem);
            }

            request.getSession().setAttribute("cart", cart);
            response.getWriter().write("{\"status\": \"added\", \"message\": \"Sản phẩm đã được thêm vào giỏ hàng (session)\"}");
            return;
        }

        Integer customerId = (currentUser == null) ? null : currentUser.getUserid();
        CartDAO dao = new CartDAO();

        try {
            CartDetail existing = dao.getCartItem(customerId, bouquetId);
            if (existing != null) {
                int newQuantity = existing.getQuantity() + quantity;
                dao.updateQuantity(customerId, bouquetId, newQuantity);
            } else {
                dao.insertItem(customerId, bouquetId, quantity);
            }

            response.getWriter().write("{\"status\": \"added\", \"message\": \"Sản phẩm đã được thêm vào giỏ hàng (DB)\"}");
        } catch (Exception e) { // Bắt Exception chung để xử lý lỗi DB
            System.err.println("Lỗi khi thêm sản phẩm vào giỏ hàng (DB): " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Không thể thêm sản phẩm vào giỏ hàng.\"}");
        }
    }

    /**
     * Xử lý cập nhật số lượng sản phẩm trong giỏ hàng.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     */
    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User currentUser = (User) request.getSession().getAttribute("currentAcc");

        if (currentUser == null) {
            try {
                int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                List<CartDetail> cart = (List<CartDetail>) request.getSession().getAttribute("cart");
                if (cart != null) {
                    // Dùng Iterator để xóa an toàn khi lặp
                    cart.removeIf(item -> {
                        if (item.getBouquetId() == bouquetId) {
                            if (quantity <= 0) {
                                return true; // Xóa item nếu số lượng <= 0
                            } else {
                                item.setQuantity(quantity);
                            }
                        }
                        return false;
                    });
                    request.getSession().setAttribute("cart", cart);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Định dạng số lượng không hợp lệ.");
            }
            // Chuyển hướng lại trang checkout để cập nhật hiển thị
            response.sendRedirect("checkout");
            return;
        }

        try {
            int customerId = currentUser.getUserid();
            int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            CartDAO dao = new CartDAO();

            if (quantity <= 0) {
                dao.deleteItem(customerId, bouquetId);
            } else {
                dao.updateQuantity(customerId, bouquetId, quantity);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Định dạng đầu vào không hợp lệ.");
        } catch (Exception e) {
            request.setAttribute("error", "Không thể cập nhật sản phẩm trong giỏ hàng.");
            System.err.println("Lỗi khi cập nhật giỏ hàng (DB): " + e.getMessage());
        }
        // Chuyển hướng lại trang checkout để cập nhật hiển thị
        response.sendRedirect("checkout");
    }

    /**
     * Xử lý xóa sản phẩm khỏi giỏ hàng.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User currentUser = (User) request.getSession().getAttribute("currentAcc");

        if (currentUser == null) {
            try {
                int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));

                List<CartDetail> cart = (List<CartDetail>) request.getSession().getAttribute("cart");
                if (cart != null) {
                    cart.removeIf(item -> item.getBouquetId() == bouquetId);
                    request.getSession().setAttribute("cart", cart);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Định dạng ID bó hoa không hợp lệ.");
            }
            // Chuyển hướng lại trang checkout để cập nhật hiển thị
            request.getRequestDispatcher("./ZeShopper/checkout.jsp").forward(request, response);
            return;
        }

        try {
            int customerId = currentUser.getUserid();
            int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));

            CartDAO dao = new CartDAO();
            dao.deleteItem(customerId, bouquetId);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Định dạng ID bó hoa không hợp lệ.");
        } catch (Exception e) {
            request.setAttribute("error", "Không thể xóa sản phẩm khỏi giỏ hàng.");
            System.err.println("Lỗi khi xóa giỏ hàng (DB): " + e.getMessage());
        }
        // Chuyển hướng lại trang checkout để cập nhật hiển thị
        request.getRequestDispatcher("./ZeShopper/checkout.jsp").forward(request, response);
    }
}
