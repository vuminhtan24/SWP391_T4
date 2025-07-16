/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import jakarta.mail.Session;
import model.BouquetImage;
import model.FlowerBatch;
import model.FlowerType;
import model.RequestDisplay;
import model.RequestFlower;
/**
 *
 * @author ADMIN
 */
@WebServlet(name = "RequestDetailController", urlPatterns = {"/requestDetail"})
public class RequestDetailController extends HttpServlet {

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
            out.println("<title>Servlet RequestDetailController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RequestDetailController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        
        String orderIdStr = request.getParameter("orderId");
        String orderItemIdStr = request.getParameter("orderItemId");
        String action = request.getParameter("action");
        int orderId = Integer.parseInt(orderIdStr);
        int orderItemId = Integer.parseInt(orderItemIdStr);
        OrderDAO odao = new OrderDAO();
        FlowerTypeDAO ftdao = new FlowerTypeDAO();
        List<RequestDisplay> listRequest = odao.gettAllRequestList(null, null, null, null);
        List<RequestFlower> listFlower = odao.getRequestFlowerByOrder(orderId, orderItemId);
        List<FlowerType> listFlowerType = ftdao.getAllFlowerTypes();
        
        for (RequestDisplay requestDisplay : listRequest) {
            if(requestDisplay.getOrderId() == orderId && requestDisplay.getOrderItemId() == orderItemId){
                request.setAttribute("requestDate", requestDisplay.getRequestDate());
            }
        }
        
        request.setAttribute("listRequest", listRequest);
        request.setAttribute("listFlower", listFlower);
        request.setAttribute("listFlowerType", listFlowerType);
        session.setAttribute("orderId", orderId);
        session.setAttribute("orderItemId", orderItemId);
        session.setAttribute("addFlowerAgree", true);
        request.getRequestDispatcher("./DashMin/RequestDetail.jsp").forward(request, response);
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
        processRequest(request, response);
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
