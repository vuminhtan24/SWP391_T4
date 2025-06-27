/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BouquetDAO;
import dal.CartDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import model.BouquetImage;
import model.CartDetail;
import model.User;

/**
 *
 * @author Legion
 */
@WebServlet(name = "CartController", urlPatterns = {"/ZeShopper/cart"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("currentAcc");
        List<CartDetail> cartDetails = new ArrayList<>();
        BouquetDAO bouDao = new BouquetDAO();

        if (currentUser == null) {
            BouquetDAO bDao = new BouquetDAO();
            List<CartDetail> sessionCart = (List<CartDetail>) request.getSession().getAttribute("cart");
            if (sessionCart != null) {
                cartDetails = sessionCart;
            }

            List<List<BouquetImage>> bqImages = new ArrayList<>();

            if (!cartDetails.isEmpty()) {
                for (CartDetail cd : cartDetails) {
                    cd.setBouquet(bDao.getBouquetFullInfoById(cd.getBouquetId()));

                    bqImages.add(bouDao.getBouquetImage(cd.getBouquetId()));
                }
            }

            // Set attributes for JSP
            request.setAttribute("cartDetails", cartDetails);
            request.setAttribute("cartImages", bqImages);
            request.setAttribute("user", null);
            request.setAttribute("isGuest", true);

        } else {
            try {
                int customerId = currentUser.getUserid();
                CartDAO cartDAO = new CartDAO();
                cartDetails = cartDAO.getCartDetailsByCustomerId(customerId);

                List<List<BouquetImage>> bqImages = new ArrayList<>();

                for (CartDetail cd : cartDetails) {
                    bqImages.add(bouDao.getBouquetImage(cd.getBouquetId()));
                }

                request.setAttribute("cartImages", bqImages);
                request.setAttribute("cartDetails", cartDetails);
                request.setAttribute("user", currentUser);
                request.setAttribute("isGuest", false);

            } catch (Exception e) {
                request.setAttribute("error", "Failed to load cart items");
                request.setAttribute("cartImages", new ArrayList<>());
                request.setAttribute("cartDetails", new ArrayList<>());
                request.setAttribute("user", currentUser);
                request.setAttribute("isGuest", false);
            }
        }

        double totalAmount = 0.0;
        int totalItems = 0;

        for (CartDetail item : cartDetails) {
            totalItems += item.getQuantity();
            // totalAmount += item.getQuantity() * item.getBouquet().getPrice();
        }

        request.setAttribute("totalItems", totalItems);
        request.setAttribute("totalAmount", totalAmount);

        request.getRequestDispatcher("./cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action) {
            case "add" ->
                add(request, response);
            case "update" ->
                update(request, response);
            case "delete" ->
                delete(request, response);
            default ->
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
        }

    }

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
                newItem.setCustomerId(-1);
                newItem.setBouquetId(bouquetId);
                newItem.setQuantity(quantity);
                cart.add(newItem);
            }

            request.getSession().setAttribute("cart", cart);
            response.getWriter().write("{\"status\": \"added\", \"message\": \"Item added to cart\"}");
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

            response.getWriter().write("{\"status\": \"added\", \"message\": \"Item added to cart\"}");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Failed to add item to cart\"}");
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User currentUser = (User) request.getSession().getAttribute("currentAcc");

        if (currentUser == null) {
            try {
                int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                List<CartDetail> cart = (List<CartDetail>) request.getSession().getAttribute("cart");
                if (cart != null) {
                    for (CartDetail item : cart) {
                        if (item.getBouquetId() == bouquetId) {
                            if (quantity <= 0) {
                                cart.remove(item);
                            } else {
                                item.setQuantity(quantity);
                            }
                            break;
                        }
                    }
                    request.getSession().setAttribute("cart", cart);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid quantity format");
            }

            response.sendRedirect("cart");
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
            request.setAttribute("error", "Invalid input format");
        } catch (Exception e) {
            request.setAttribute("error", "Failed to update cart item");
        }

        response.sendRedirect("cart");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
                request.setAttribute("error", "Invalid bouquet ID format");
            }

            response.sendRedirect("cart");
            return;
        }

        try {
            int customerId = currentUser.getUserid();
            int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));

            CartDAO dao = new CartDAO();
            dao.deleteItem(customerId, bouquetId);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid bouquet ID format");
        } catch (Exception e) {
            request.setAttribute("error", "Failed to delete cart item");
        }

        response.sendRedirect("cart");
    }
}
