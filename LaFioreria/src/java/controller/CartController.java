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
@WebServlet(name = "CartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action) {
            case "add":
                add(request, response);
                break;
            
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

}
