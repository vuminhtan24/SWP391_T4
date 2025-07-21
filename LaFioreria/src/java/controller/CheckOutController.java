package controller;

import dal.BouquetDAO;
import dal.CartDAO;
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
import jakarta.servlet.http.HttpSession;
import model.BouquetImage;
import model.CartDetail;
import model.CheckoutFormData; // Import model mới
import model.DiscountCode;
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

        // Lấy dữ liệu form đã lưu trong session (nếu có) để điền lại
        CheckoutFormData savedFormData = (CheckoutFormData) request.getSession().getAttribute("checkoutFormData");
        if (savedFormData != null) {
            request.setAttribute("savedFormData", savedFormData);
            request.getSession().removeAttribute("checkoutFormData"); // Xóa khỏi session sau khi lấy
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

        // Đảm bảo encoding để đọc dữ liệu tiếng Việt nếu có
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
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
                case "applyDiscount":
                    handleApplyDiscount(request, response, session);
                    break;
                default:
                    request.setAttribute("error", "Hành động không hợp lệ: " + action);
            }
        }
        // Forward to checkout.jsp after processing to display messages
        // This will be called if no specific redirect/forward happened in the action methods
        // For 'placeOrder' with VietQR, a redirect happens, so this won't be reached.
        // For 'add', 'update', 'delete', 'applyDiscount', this will ensure the page reloads with messages.
        if (!response.isCommitted()) { // Check if response is already committed (e.g., by sendRedirect)
            doGet(request, response); // Re-use doGet to populate cart details and forward to checkout.jsp
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

        double actualTotalSell;
        // Lấy tổng tiền cuối cùng đã được tính toán (bao gồm giảm giá nếu có) từ session
        Double finalOrderTotalFromSession = (Double) request.getSession().getAttribute("finalOrderTotal");

        if (finalOrderTotalFromSession != null) {
            actualTotalSell = finalOrderTotalFromSession;
            System.out.println("DEBUG (processOrder): Using finalOrderTotal from session: " + actualTotalSell);
        } else {
            // Nếu không có finalOrderTotal trong session (ví dụ: không áp dụng giảm giá hoặc session đã bị xóa)
            // Thì tính toán tổng tiền từ giỏ hàng hiện tại và phí vận chuyển
            double currentCartTotal = 0.0;
            List<CartDetail> cartItems;
            User userFromSession = (User) request.getSession().getAttribute("currentAcc");

            if (userFromSession != null) {
                CartDAO cartDAO = new CartDAO();
                try {
                    cartItems = cartDAO.getCartDetailsByCustomerId(userFromSession.getUserid());
                } catch (Exception e) {
                    e.printStackTrace();
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
        order.setTotalImport(String.valueOf(actualTotalSell / 5)); // Giả định lợi nhuận 20%
        order.setPaymentMethod(paymentMethod);
        order.setStatusId(1); // Chờ xử lý

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
                        cartItem.getBouquet().getSellPrice()
                );
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

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("orderError", "Có lỗi khi đặt hàng: " + e.getMessage());
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
                System.out.println("DEBUG (handleApplyDiscount - item): BouquetId: " + cd.getBouquetId() + 
                                   ", Quantity: " + cd.getQuantity() + 
                                   ", SellPrice: " + cd.getBouquet().getSellPrice() + 
                                   ", Item Total: " + (cd.getQuantity() * cd.getBouquet().getSellPrice()));
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
}
