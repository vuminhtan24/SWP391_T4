/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CartDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
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
        if (currentUser == null) {
            // Ch?a login, chuy?n h??ng v? trang login
            response.sendRedirect("./login.jsp");
            return;
        }
        int customerId = currentUser.getUserid();
        CartDAO cartDAO = new CartDAO();
        List<CartDetail> cartDetails = cartDAO.getCartDetailsByCustomerId(customerId);

        // ??y d? li?u vào request attribute
        request.setAttribute("cartDetails", cartDetails);
        request.setAttribute("user", currentUser);

        // Forward sang trang JSP ?? hi?n th? gi? hàng
        request.getRequestDispatcher("./cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

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
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
        }

    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int customerId = ((User) request.getSession().getAttribute("currentAcc")).getUserid();
        int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        CartDAO dao = new CartDAO();
        System.out.println("nam");

        // Kiểm tra đã có chưa
        CartDetail existing = dao.getCartItem(customerId, bouquetId);
        if (existing != null) {
            int newQuantity = existing.getQuantity() + quantity;
            dao.updateQuantity(customerId, bouquetId, newQuantity);
        } else {
            dao.insertItem(customerId, bouquetId, quantity);
        }

        response.setContentType("application/json");
        response.getWriter().write("{\"status\": \"added\"}");

    }
    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int customerId = ((User) request.getSession().getAttribute("currentAcc")).getUserid();
        int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        CartDAO dao = new CartDAO();
        dao.updateQuantity(customerId, bouquetId, quantity);

        response.sendRedirect("cart");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int customerId = ((User) request.getSession().getAttribute("currentAcc")).getUserid();
        int bouquetId = Integer.parseInt(request.getParameter("bouquetId"));

        CartDAO dao = new CartDAO();
        dao.deleteItem(customerId, bouquetId);

        response.sendRedirect("/ZeShopper/cart");
    }

}
