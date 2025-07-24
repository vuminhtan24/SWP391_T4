package controller;

import dal.BouquetDAO;
import dal.CartDAO;
import dal.CartWholeSaleDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate; // Import lớp LocalDate
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects; // Import Objects để so sánh an toàn hơn
import dal.DiscountCodeDAO;
import dal.WholeSaleDAO;
import jakarta.servlet.http.HttpSession;
import model.Bouquet;
import model.BouquetImage;
import model.CartDetail;
import model.CartWholeSaleDetail;
import model.User;

import model.CheckoutFormData; // Import model mới

import model.DiscountCode;
import model.Order; // Import lớp Order
import model.OrderItem; // Import lớp OrderItem
import model.User;
import model.WholeSale;

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
        String mode = request.getParameter("mode");
        String requestGroupId = request.getParameter("requestGroupId");
        request.setAttribute("mode", mode);

        if ("wholesale".equalsIgnoreCase(mode)) {
            handleWholesaleMode(request, currentUser);
        } else {
            handleRetailMode(request, currentUser);
        }

        // Gán dữ liệu form lưu trong session (nếu có)
        CheckoutFormData savedFormData = (CheckoutFormData) request.getSession().getAttribute("checkoutFormData");
        if (savedFormData != null) {
            request.setAttribute("savedFormData", savedFormData);
            request.getSession().removeAttribute("checkoutFormData");
            System.out.println("DEBUG (doGet): Loaded saved form data from session and removed it.");
        }

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

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String mode = request.getParameter("mode");

        if (action == null || action.isEmpty()) {
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
                case "placeOrder":
                    if (mode == null || mode.equals("retail")) {
                        processOrder(request, response);
                    } else if (mode.equals("wholesale")) {
                        processWholesaleOrder(request, response);
                    }
                    break;
                case "applyDiscount":
                    handleApplyDiscount(request, response, session);
                    break;
                default:
                    request.setAttribute("error", "Hành động không hợp lệ: " + action);
            }
        }

        // Sau khi xử lý, đảm bảo giữ lại mode khi gọi doGet
        if (!response.isCommitted()) {
            if (mode != null) {
                request.setAttribute("mode", mode); // để doGet không bị mất mode
            }
            doGet(request, response);
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

        // Lấy thông tin từ form
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String addressLine = request.getParameter("addressLine");
        String province = request.getParameter("province"); // Tên tỉnh
        String district = request.getParameter("district"); // Tên huyện
        String ward = request.getParameter("ward");         // Tên xã/phường

        // Địa chỉ đầy đủ
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

        String totalSellStr = request.getParameter("totalAmount");

        int actualTotalSell;
        int currentCartTotal = 0;
        // Lấy tổng tiền cuối cùng đã được tính toán (bao gồm giảm giá nếu có) từ session
        Integer finalOrderTotalFromSession = (Integer) request.getSession().getAttribute("finalOrderTotal");

        if (finalOrderTotalFromSession != null) {
            actualTotalSell = finalOrderTotalFromSession;
            currentCartTotal = actualTotalSell - 30000;
            System.out.println("DEBUG (processOrder): Using finalOrderTotal from session: " + actualTotalSell);
        } else {
            // Nếu không có finalOrderTotal trong session (ví dụ: không áp dụng giảm giá hoặc session đã bị xóa)
            // Thì tính toán tổng tiền từ giỏ hàng hiện tại và phí vận chuyển
            
            List<CartDetail> cartItems;
            User userFromSession = (User) request.getSession().getAttribute("currentAcc");

            if (userFromSession != null) {
                CartDAO cartDAO = new CartDAO();
                try {
                    cartItems = cartDAO.getCartDetailsByCustomerId(userFromSession.getUserid());
                } catch (Exception ea) {
                    ea.printStackTrace();
                    request.setAttribute("orderError", "Lỗi khi tải giỏ hàng để đặt đơn.");
                    return;
                }
            } else {
                cartItems = (List<CartDetail>) request.getSession().getAttribute("cart");
            }

            if (cartItems != null) {
                BouquetDAO bDao = new BouquetDAO();
                for (CartDetail cd : cartItems) {
                    if (cd.getBouquet() == null) {
                        cd.setBouquet(bDao.getBouquetFullInfoById(cd.getBouquetId()));
                    }
                    currentCartTotal += cd.getQuantity() * cd.getBouquet().getSellPrice();
                }
            }
            actualTotalSell = currentCartTotal + 30000; // Cộng thêm phí vận chuyển mặc định
            System.out.println("DEBUG (processOrder): Recalculated actualTotalSell (no session final total): " + actualTotalSell);

        }

        // Tạo đơn hàng
        Order order = new Order();
        order.setCustomerId(customerId == -1 ? null : customerId); // null nếu là khách vãng lai
        order.setCustomerName(fullName);
        order.setCustomerPhone(phoneNumber);
        order.setCustomerAddress(fullAddress);
        order.setOrderDate(LocalDate.now().toString());
        order.setTotalSell(String.valueOf(actualTotalSell)); // Lưu tổng tiền đã được tính toán cuối cùng
        order.setTotalImport(String.valueOf(currentCartTotal / 5)); // Giả định lợi nhuận 20%
        order.setPaymentMethod(paymentMethod);
        order.setStatusId(1); // Chờ xử lý
        order.setType("retail");

        CartDAO cartDAO = new CartDAO();
        try {
            int orderId = cartDAO.insertOrder(order);
            if (orderId == -1) {
                throw new Exception("Không thể tạo đơn hàng");
            }

            List<CartDetail> cartItems;
            if (currentUser != null) {
                cartItems = cartDAO.getCartDetailsByCustomerId(customerId);
            } else {
                cartItems = (List<CartDetail>) request.getSession().getAttribute("cart");
            }

            if (cartItems == null || cartItems.isEmpty()) {
                request.setAttribute("orderError", "Giỏ hàng trống.");
                return;
            }

            for (CartDetail cartItem : cartItems) {
                if (cartItem.getBouquet() == null) {
                    BouquetDAO bouquetDAO = new BouquetDAO();
                    cartItem.setBouquet(bouquetDAO.getBouquetFullInfoById(cartItem.getBouquetId()));
                }

                OrderItem orderItem = new OrderItem(
                        orderId,
                        cartItem.getBouquetId(),
                        cartItem.getQuantity(),
                        cartItem.getBouquet().getPrice(),
                        cartItem.getBouquet().getSellPrice()
                );

                System.out.println("DEBUG: Attempting to insert order item for Bouquet ID: " + cartItem.getBouquetId());

                cartDAO.insertOrderItem(orderItem);
            }

            // Xoá giỏ hàng sau khi đặt
            if (currentUser != null) {
                cartDAO.deleteCartByCustomerId(customerId);
            } else {
                request.getSession().removeAttribute("cart");
            }
            // Xóa tất cả các thuộc tính liên quan đến giảm giá và dữ liệu form khỏi session sau khi đặt hàng thành công
            request.getSession().removeAttribute("appliedDiscount");
            request.getSession().removeAttribute("calculatedDiscountAmount");
            request.getSession().removeAttribute("finalOrderTotal");
            request.getSession().removeAttribute("checkoutFormData"); // Xóa dữ liệu form đã lưu
            System.out.println("DEBUG (processOrder): Discount and form data session attributes cleared.");

            if ("vietqr".equals(paymentMethod)) {
                // Redirect to VietQR payment page
                response.sendRedirect(request.getContextPath() + "/ConfirmVietQRPayment?orderId=" + orderId + "&amount=" + actualTotalSell);
            } else {
                request.setAttribute("orderSuccess", "Đơn hàng đã được đặt thành công! Mã đơn hàng: " + orderId);
                request.getRequestDispatcher("./ZeShopper/thanks-you.jsp").forward(request, response);
            }

        } catch (Exception ei) {
            ei.printStackTrace();
            request.setAttribute("orderError", "Có lỗi khi đặt hàng: " + ei.getMessage());
        }
    }

    /**
     * Xử lý thêm sản phẩm vào giỏ hàng.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     */
    private void handleApplyDiscount(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String discountCode = request.getParameter("discountCode");

        // --- Lưu dữ liệu form hiện tại vào session trước khi xử lý giảm giá ---
        CheckoutFormData formData = new CheckoutFormData();
        formData.setEmail(request.getParameter("email"));
        formData.setFullName(request.getParameter("fullName"));
        formData.setAddressLine(request.getParameter("addressLine"));
        // Lấy mã (value) của select box, không phải text
        formData.setProvinceCode(request.getParameter("provinceCode"));
        formData.setDistrictCode(request.getParameter("districtCode"));
        formData.setWardCode(request.getParameter("wardCode"));
        formData.setPhoneNumber(request.getParameter("phoneNumber"));
        formData.setNotes(request.getParameter("notes"));
        formData.setPaymentMethod(request.getParameter("paymentMethod"));
        // formData.setShipToBilling(request.getParameter("shipToBilling") != null); // Nếu bạn có checkbox này

        session.setAttribute("checkoutFormData", formData);
        System.out.println("DEBUG (handleApplyDiscount): Saved form data to session.");
        // --- Kết thúc lưu dữ liệu form ---

        if (discountCode == null || discountCode.trim().isEmpty()) {
            request.setAttribute("discountError", "Mã giảm giá không được để trống.");
            // Giữ lại dữ liệu form trong session để doGet có thể lấy
            return;
        }

        dal.DiscountCodeDAO discountCodeDAO = new dal.DiscountCodeDAO(); // Sử dụng dal.DiscountCodeDAO
        DiscountCode discount = discountCodeDAO.getValidDiscountCode(discountCode);

        // Calculate current total amount from cart details
        List<CartDetail> cartDetails = null; // Khởi tạo null
        User currentUser = (User) request.getSession().getAttribute("currentAcc");
        if (currentUser != null) {
            CartDAO cartDAO = new CartDAO();
            try {
                cartDetails = cartDAO.getCartDetailsByCustomerId(currentUser.getUserid());
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("discountError", "Lỗi khi tải giỏ hàng để áp dụng mã giảm giá.");
                return;
            }
        } else {
            cartDetails = (List<CartDetail>) request.getSession().getAttribute("cart");
        }

        double currentCartTotal = 0.0;
        if (cartDetails != null) {
            BouquetDAO bDao = new BouquetDAO();
            for (CartDetail cd : cartDetails) {
                if (cd.getBouquet() == null) {
                    cd.setBouquet(bDao.getBouquetFullInfoById(cd.getBouquetId()));
                }
                // DEBUG LOG: In ra giá trị từng sản phẩm
                System.out.println("DEBUG (handleApplyDiscount - item): BouquetId: " + cd.getBouquetId()
                        + ", Quantity: " + cd.getQuantity()
                        + ", SellPrice: " + cd.getBouquet().getSellPrice()
                        + ", Item Total: " + (cd.getQuantity() * cd.getBouquet().getSellPrice()));
                currentCartTotal += cd.getQuantity() * cd.getBouquet().getSellPrice();
            }
        }
        System.out.println("DEBUG (handleApplyDiscount): Calculated currentCartTotal (from items): " + currentCartTotal);

        if (discount == null || (discount.getMinOrderAmount() != null && currentCartTotal < discount.getMinOrderAmount().doubleValue())) {
            request.setAttribute("discountError", "Mã giảm giá không hợp lệ hoặc không đủ điều kiện.");
            session.removeAttribute("appliedDiscount"); // Clear any previously applied discount
            session.removeAttribute("calculatedDiscountAmount"); // Clear related attributes
            session.removeAttribute("finalOrderTotal"); // Clear related attributes
            // Giữ lại dữ liệu form trong session để doGet có thể lấy
            return;
        }

        // Calculate discount amount and update total
        double discountAmount = discount.getDiscountAmount(currentCartTotal);
        double finalAmount = currentCartTotal + 30000 - discountAmount; // Assuming 30000 is shipping fee

        // DEBUG LOGS
        System.out.println("DEBUG (handleApplyDiscount): currentCartTotal: " + currentCartTotal);
        System.out.println("DEBUG (handleApplyDiscount): discountAmount: " + discountAmount);
        System.out.println("DEBUG (handleApplyDiscount): finalAmount (subtotal + ship - discount): " + finalAmount);

        // Store in session to be used in processOrder
        session.setAttribute("appliedDiscount", discount);
        session.setAttribute("calculatedDiscountAmount", discountAmount);
        session.setAttribute("finalOrderTotal", finalAmount);

        request.setAttribute("discountSuccess", "Áp dụng mã giảm giá thành công! Giảm: " + String.format("%,.0f", discountAmount) + "₫");
        request.setAttribute("discountAmount", String.format("%,.0f", discountAmount)); // For displaying on JSP
        request.setAttribute("orderFinalTotal", String.format("%,.0f", finalAmount)); // For displaying on JSP

        // The doPost will call doGet to refresh the page with these attributes
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User currentUser = (User) request.getSession().getAttribute("currentAcc");

        int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

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
            request.setAttribute("successMessage", "Sản phẩm đã được thêm vào giỏ hàng!"); // Set success message
            return; // Return to let doPost handle the forward
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

            request.setAttribute("successMessage", "Sản phẩm đã được thêm vào giỏ hàng!"); // Set success message
        } catch (Exception e) { // Bắt Exception chung để xử lý lỗi DB
            System.err.println("Lỗi khi thêm sản phẩm vào giỏ hàng (DB): " + e.getMessage());
            request.setAttribute("errorMessage", "Không thể thêm sản phẩm vào giỏ hàng."); // Set error message
        }
        return; // Return to let doPost handle the forward
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
                    request.setAttribute("successMessage", "Giỏ hàng đã được cập nhật!");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Định dạng số lượng không hợp lệ.");
            }
            // No explicit forward here, doPost will handle the doGet call
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
            request.setAttribute("successMessage", "Giỏ hàng đã được cập nhật!");
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Định dạng đầu vào không hợp lệ.");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Không thể cập nhật sản phẩm trong giỏ hàng.");
            System.err.println("Lỗi khi cập nhật giỏ hàng (DB): " + e.getMessage());
        }
        // No explicit forward here, doPost will handle the doGet call
        return;
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
                    request.setAttribute("successMessage", "Sản phẩm đã được xóa khỏi giỏ hàng!");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Định dạng ID bó hoa không hợp lệ.");
            }
            // No explicit forward here, doPost will handle the doGet call
            return;
        }

        try {
            int customerId = currentUser.getUserid();
            int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));

            CartDAO dao = new CartDAO();
            dao.deleteItem(customerId, bouquetId);
            request.setAttribute("successMessage", "Sản phẩm đã được xóa khỏi giỏ hàng!");
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Định dạng ID bó hoa không hợp lệ.");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Không thể xóa sản phẩm khỏi giỏ hàng.");
            System.err.println("Lỗi khi xóa giỏ hàng (DB): " + e.getMessage());
        }
        // No explicit forward here, doPost will handle the doGet call
        return;
    }

    private void processWholesaleOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {

        User currentUser = (User) request.getSession().getAttribute("currentAcc");

        if (currentUser == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Bạn cần đăng nhập để đặt đơn hàng theo lô.\"}");
            return;
        }

        int userId = currentUser.getUserid();
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String addressLine = request.getParameter("addressLine");
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String paymentMethod = request.getParameter("paymentMethod");
        HttpSession session = request.getSession();

        // Ghép full địa chỉ
        StringBuilder fullAddressBuilder = new StringBuilder();
        if (addressLine != null && !addressLine.trim().isEmpty()) {
            fullAddressBuilder.append(addressLine.trim());
        }
        if (ward != null && !ward.trim().isEmpty()) {
            fullAddressBuilder.append(", ").append(ward.trim());
        }
        if (district != null && !district.trim().isEmpty()) {
            fullAddressBuilder.append(", ").append(district.trim());
        }
        if (province != null && !province.trim().isEmpty()) {
            fullAddressBuilder.append(", ").append(province.trim());
        }
        String fullAddress = fullAddressBuilder.toString();

        // Lấy giỏ hàng theo lô
        CartWholeSaleDAO cwsDAO = new CartWholeSaleDAO();
        List<CartWholeSaleDetail> cartItems = cwsDAO.getCartWholeSaleByUser(userId);

        if (cartItems == null || cartItems.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Giỏ hàng theo lô đang trống.\"}");
            return;
        }

        // Tính tổng bán và tổng nhập (giá nhập thực tế)
        int totalSell = 0;
        int totalImport = 0;

        for (CartWholeSaleDetail item : cartItems) {
            totalSell += item.getTotalValue(); // Tổng tiền khách trả

            totalImport += item.getExpense() * item.getQuantity(); // Giá nhập

        }

        // Tạo đơn hàng
        Order order = new Order();
        order.setCustomerId(userId);
        order.setCustomerName(fullName);
        order.setCustomerPhone(phoneNumber);
        order.setCustomerAddress(fullAddress);
        order.setOrderDate(LocalDate.now().toString());
        order.setTotalSell(String.valueOf(totalSell));
        order.setTotalImport(String.valueOf(totalImport));
        order.setPaymentMethod(paymentMethod);
        order.setStatusId(1); // Chờ xử lý
        order.setType("wholesale");

        CartDAO cartDAO = new CartDAO();

        try {
            int orderId = cartDAO.insertOrder(order);
            if (orderId == -1) {
                throw new Exception("Không thể tạo đơn hàng.");
            }

            for (CartWholeSaleDetail item : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(orderId);
                orderItem.setBouquetId(item.getBouquetID());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setRequest_group_id(item.getRequest_group_id());
                orderItem.setUnitPrice(item.getExpense());
                orderItem.setSellPrice(item.getPricePerUnit()); // Có thể là đơn giá bán theo lô

                cartDAO.insertOrderItem(orderItem);
            }

            // Xoá giỏ hàng wholesale sau khi đặt hàng thành công
            cwsDAO.clearCartWholeSaleByUser(userId);
            List<WholeSale> listWS = (List<WholeSale>) session.getAttribute("listWS");
            WholeSaleDAO wsDao = new WholeSaleDAO();
            wsDao.completeWholesale(listWS);
            // Lưu thông tin để xử lý VietQR nếu cần
            if ("vietqr".equals(paymentMethod)) {
                // Redirect to VietQR payment page
                response.sendRedirect(request.getContextPath() + "/ConfirmVietQRPayment?orderId=" + orderId + "&amount=" + totalSell);
            } else {
                request.setAttribute("orderSuccess", "Đơn hàng đã được đặt thành công! Mã đơn hàng: " + orderId);
                request.getRequestDispatcher("./ZeShopper/thanks-you.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Lỗi xử lý đơn hàng theo lô: " + e.getMessage() + "\"}");
        }
    }

    private void handleRetailMode(HttpServletRequest request, User currentUser) {
        List<CartDetail> cartDetails = new ArrayList<>();
        List<List<BouquetImage>> bqImages = new ArrayList<>();
        BouquetDAO bouquetDAO = new BouquetDAO();

        if (currentUser == null) {
            // Khách vãng lai
            List<CartDetail> sessionCart = (List<CartDetail>) request.getSession().getAttribute("cart");
            if (sessionCart != null) {
                cartDetails = sessionCart;
            }

            for (CartDetail cd : cartDetails) {
                cd.setBouquet(bouquetDAO.getBouquetFullInfoById(cd.getBouquetId()));
                bqImages.add(bouquetDAO.getBouquetImage(cd.getBouquetId()));
            }

            request.setAttribute("isGuest", true);
            request.setAttribute("user", null);
        } else {
            // Khách đã đăng nhập
            try {
                CartDAO cartDAO = new CartDAO();
                cartDetails = cartDAO.getCartDetailsByCustomerId(currentUser.getUserid());

                for (CartDetail cd : cartDetails) {
                    if (cd.getBouquet() == null) {
                        cd.setBouquet(bouquetDAO.getBouquetFullInfoById(cd.getBouquetId()));
                    }
                    bqImages.add(bouquetDAO.getBouquetImage(cd.getBouquetId()));
                }

                request.setAttribute("isGuest", false);
                request.setAttribute("user", currentUser);
            } catch (Exception e) {
                System.err.println("Lỗi khi tải giỏ hàng cho khách hàng " + currentUser.getUserid() + ": " + e.getMessage());
                cartDetails = new ArrayList<>();
                request.setAttribute("error", "Failed to load cart items");
                request.setAttribute("user", currentUser);
                request.setAttribute("isGuest", false);
            }
        }

        double totalAmount = 0;
        int totalItems = 0;

        for (CartDetail item : cartDetails) {
            totalItems += item.getQuantity();
            if (item.getBouquet() != null) {
                totalAmount += item.getQuantity() * item.getBouquet().getSellPrice();
            }
        }

        request.setAttribute("cartDetails", cartDetails);
        request.setAttribute("cartImages", bqImages);
        request.setAttribute("totalItems", totalItems);
        request.setAttribute("totalAmount", totalAmount);
    }

    private void handleWholesaleMode(HttpServletRequest request, User currentUser) {
        if (currentUser == null) {
            request.setAttribute("error", "Vui lòng đăng nhập để sử dụng đơn hàng theo lô.");
            return;
        }

        CartWholeSaleDAO cwsDao = new CartWholeSaleDAO();
        BouquetDAO bouquetDAO = new BouquetDAO();

        List<CartWholeSaleDetail> cartDetails = cwsDao.getCartWholeSaleByUser(currentUser.getUserid());
        List<Bouquet> listBouquet = bouquetDAO.getAll();
        List<BouquetImage> images = bouquetDAO.getAllBouquetImage();

        int totalOrderValue = 0;
        for (CartWholeSaleDetail item : cartDetails) {
            totalOrderValue += item.getTotalValue();
        }

        request.setAttribute("listCartWholeSale", cartDetails);
        request.setAttribute("listBQ", listBouquet);
        request.setAttribute("listIMG", images);
        request.setAttribute("totalOrderValue", totalOrderValue);
        request.setAttribute("user", currentUser);
        request.setAttribute("isGuest", false);
    }

}
