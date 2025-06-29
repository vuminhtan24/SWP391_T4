/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.PrintWriter;

import java.io.File;
import java.io.IOException;
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
import model.BouquetRaw;
import dal.BouquetDAO;
import dal.CategoryDAO;
import dal.FlowerBatchDAO;
import dal.FlowerTypeDAO;
import dal.OrderDAO;
import jakarta.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javax.mail.Session;
import model.BouquetImage;
import model.FlowerBatch;
import model.FlowerType;
import model.RequestFlower;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "RequestFlowerController", urlPatterns = {"/requestFlower"})
public class RequestFlowerController extends HttpServlet {

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
            out.println("<title>Servlet RequestFlowerController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RequestFlowerController at " + request.getContextPath() + "</h1>");
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
        FlowerBatchDAO fbdao = new FlowerBatchDAO();
        FlowerTypeDAO ftdao = new FlowerTypeDAO();
        CategoryDAO cdao = new CategoryDAO();
        OrderDAO odao = new OrderDAO();
        List<Category> cAll = cdao.getBouquetCategory();
        List<FlowerBatch> allBatchs = fbdao.getAllFlowerBatches();
        List<FlowerType> allFlowers = ftdao.getAllFlowerTypes();
        String orderIdStr = request.getParameter("orderId");
        String orderItemIdStr = request.getParameter("orderItemId");

        int orderId = Integer.parseInt(orderIdStr);
        int orderItemId = Integer.parseInt(orderItemIdStr);

        List<RequestFlower> listRequest = odao.getRequestFlowerByOrder(orderId, orderItemId);
        for (RequestFlower requestFlower : listRequest) {
            request.setAttribute("requestDate", requestFlower.getRequestCreationDate());
            break;
        }

        // 1. Tất cả hoa để đổ vào dropdown
        request.setAttribute("orderId", orderId);
        request.setAttribute("orderItemId", orderItemId);
        request.setAttribute("listRequest", listRequest);
        request.setAttribute("cateBouquetHome", cAll);
        request.setAttribute("allBatchs", allBatchs);
        request.setAttribute("allFlowers", allFlowers);

        request.getRequestDispatcher("./DashMin/requestFlowerForOrder.jsp").forward(request, response);
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
        try {
            HttpSession session = request.getSession();
            String[] flowerRequestIdsStr = request.getParameterValues("flowerRequestIds");
            String[] quantityRequestStr = request.getParameterValues("quantityRequest");
            String[] flowerNeededIdsStr = request.getParameterValues("flowerNeededIds");
            String[] quantityNeededStr = request.getParameterValues("quantityNeeded");
            String action = request.getParameter("action");
            int orderId = Integer.parseInt(request.getParameter("orderId").trim());
            int orderItemId = Integer.parseInt(request.getParameter("orderItemId").trim());

            OrderDAO odao = new OrderDAO();

            if ("sendRequest".equalsIgnoreCase(action)) {
                if (flowerNeededIdsStr != null && quantityNeededStr != null
                        && flowerRequestIdsStr != null && quantityRequestStr != null) {

                    int len = flowerRequestIdsStr.length;
                    if (quantityRequestStr.length != len || flowerNeededIdsStr.length != len || quantityNeededStr.length != len) {
                        request.setAttribute("error", "Invalid input: mismatched array lengths.");
                        doGet(request, response);
                        return;
                    }

                    for (int i = 0; i < len; i++) {
                        int flowerRequest = Integer.parseInt(flowerRequestIdsStr[i].trim());
                        int quantityRequest = Integer.parseInt(quantityRequestStr[i].trim());
                        int flowerNeeded = Integer.parseInt(flowerNeededIdsStr[i].trim());
                        int quantityNeeded = Integer.parseInt(quantityNeededStr[i].trim());

                        if (flowerRequest < flowerNeeded) {
                            request.setAttribute("error", "Request number cannot be less than needed number");
                            doGet(request, response);
                            return;
                        }

                        odao.updateRequestQuantity(orderId, orderItemId, flowerRequest, quantityRequest);
                    }
                    session.setAttribute("RequestSent", "You have successfully sent a request to add more flowers.");
                    response.sendRedirect("/LaFioreria/orderDetail?orderId=" + orderId);
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
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
