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
        response.setContentType("application/json"); // Đặt Content-Type cho phản hồi JSON

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

        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (currentUser == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Bạn cần đăng nhập để đặt hàng.\"}");
            return;
        }

        int customerId = currentUser.getUserid();

        // Lấy thông tin từ request (AJAX POST)
        // Các thông tin này sẽ KHÔNG được lưu vào bảng `order`
        // trừ `totalSell`, `totalImport`, `customerId`, `orderDate`, `statusId`
        // theo cấu trúc `CartDAO.insertOrder` gốc của bạn.
        // Bạn có thể dùng chúng cho các mục đích khác (ví dụ: gửi email xác nhận)
        // nhưng chúng sẽ không đi vào DB qua phương thức insertOrder này.
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String addressLine = request.getParameter("addressLine");
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String phoneNumber = request.getParameter("phoneNumber");
        String notes = request.getParameter("notes");
        String paymentMethod = request.getParameter("paymentMethod"); // Lấy phương thức thanh toán

        // In các giá trị nhận được để debug (có thể bỏ comment để kiểm tra)
        System.out.println("DEBUG: Payment Method nhận được: " + paymentMethod);
        System.out.println("DEBUG: Full Name: " + fullName);
        System.out.println("DEBUG: Phone Number: " + phoneNumber);
        System.out.println("DEBUG: Address: " + addressLine + ", " + ward + ", " + district + ", " + province);
        System.out.println("DEBUG: Notes: " + notes);

        String totalSellStr = request.getParameter("totalAmount"); // Lấy tổng tiền bán từ client
        double actualTotalSell; // Tổng tiền thực tế từ client
        try {
            actualTotalSell = Double.parseDouble(totalSellStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Số tiền tổng cộng không hợp lệ.\"}");
            System.err.println("Lỗi chuyển đổi totalAmount từ client: " + totalSellStr); // Debug
            return;
        }

        // Quyết định giá trị totalSell để lưu vào DB dựa trên paymentMethod
        String finalTotalSellToSave;
        // Sử dụng Objects.equals để tránh NullPointerException nếu paymentMethod là null
        if (Objects.equals(paymentMethod, "ewallet")) {
            finalTotalSellToSave = "0"; // Lưu 0đ nếu thanh toán bằng E-wallet
            System.out.println("DEBUG: Phương thức E-wallet, totalSell sẽ là 0."); // Debug
        } else { // Mặc định là COD hoặc các phương thức khác
            finalTotalSellToSave = String.valueOf(actualTotalSell); // Lưu đầy đủ số tiền
            System.out.println("DEBUG: Phương thức khác E-wallet, totalSell sẽ là: " + finalTotalSellToSave); // Debug
        }

        // Tạo đối tượng Order
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderDate(LocalDate.now().toString()); // Sử dụng ngày hiện tại
        order.setTotalSell(finalTotalSellToSave);       // Sử dụng giá trị đã quyết định
        order.setTotalImport(String.valueOf(actualTotalSell / 5)); // Giả định totalImport dựa trên actualTotalSell
        order.setStatusId(1); // Mặc định trạng thái đơn hàng là 1 (chờ xử lý)

        // Không set các trường customerName, customerPhone, customerAddress, paymentMethod, notes vào Order object
        // vì phương thức insertOrder trong CartDAO gốc của bạn không lưu chúng vào DB.
        // Các thông tin này chỉ được truyền từ client và xử lý tại đây (nếu cần cho logic khác).
        CartDAO cartDAO = new CartDAO();
        try {
            // 1. Chèn đơn hàng mới vào DB và lấy orderId được tạo tự động
            int orderId = cartDAO.insertOrder(order);

            if (orderId == -1) {
                throw new Exception("Không thể tạo đơn hàng, insertOrder trả về -1.");
            }
            System.out.println("DEBUG: Đơn hàng #" + orderId + " đã được tạo."); // Debug

            // 2. Lấy danh sách các sản phẩm trong giỏ hàng của khách hàng
            List<CartDetail> cartItems = cartDAO.getCartDetailsByCustomerId(customerId);
            System.out.println("DEBUG: Số lượng sản phẩm trong giỏ hàng: " + cartItems.size()); // Debug

            // 3. Chèn từng sản phẩm trong giỏ hàng vào bảng OrderItem
            for (CartDetail cartItem : cartItems) {
                // Đảm bảo rằng Bouquet và SellPrice đã được load vào CartDetail
                if (cartItem.getBouquet() == null) {
                    BouquetDAO bouquetDAO = new BouquetDAO();
                    cartItem.setBouquet(bouquetDAO.getBouquetFullInfoById(cartItem.getBouquetId()));
                }

                OrderItem orderItem = new OrderItem(
                        orderId,
                        cartItem.getBouquetId(),
                        cartItem.getQuantity(),
                        cartItem.getBouquet().getSellPrice() // Lấy đơn giá bán của sản phẩm
                );
                cartDAO.insertOrderItem(orderItem);
                System.out.println("DEBUG: Đã thêm OrderItem cho Bouquet ID: " + cartItem.getBouquetId()); // Debug
            }

            // 4. Xóa giỏ hàng của khách hàng sau khi đã đặt hàng thành công
            cartDAO.deleteCartByCustomerId(customerId);
            System.out.println("DEBUG: Giỏ hàng của khách hàng " + customerId + " đã được xóa."); // Debug

            // Gửi phản hồi thành công về client
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Đơn hàng của bạn đã được đặt thành công! Mã đơn hàng: " + orderId + "\"}");

        } catch (Exception e) {
            System.err.println("Lỗi khi xử lý đặt hàng: " + e.getMessage());
            e.printStackTrace(); // In stack trace để debug
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Có lỗi xảy ra khi đặt hàng: " + e.getMessage() + "\"}");
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

        int customerId = currentUser.getUserid();
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
