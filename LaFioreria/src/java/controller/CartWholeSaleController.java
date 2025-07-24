/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BouquetDAO;
import dal.CartDAO;
import dal.CartWholeSaleDAO;
import dal.WholeSaleDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import model.Bouquet;
import model.BouquetImage;
import model.CartDetail;
import model.CartWholeSaleDetail;
import model.User;
import model.WholeSale;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CartWholeSaleController", urlPatterns = {"/cartWholeSale"})
public class CartWholeSaleController extends HttpServlet {

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
            out.println("<title>Servlet CartWholeSaleController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartWholeSaleController at " + request.getContextPath() + "</h1>");
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
        
        if(session.getAttribute("currentAcc") == null){
            request.setAttribute("error", "You need login to go to this feature!!!");
            request.setAttribute("isGuest", true);
            request.getRequestDispatcher("./ZeShopper/cartWholeSale.jsp").forward(request, response);
            return;
        }
        User currentUser = (User) session.getAttribute("currentAcc");
        CartWholeSaleDAO cwsDao = new CartWholeSaleDAO();
        BouquetDAO bDao = new BouquetDAO();

        List<BouquetImage> images = bDao.getAllBouquetImage();
        List<CartWholeSaleDetail> cartDetail = cwsDao.getCartWholeSaleByUser(currentUser.getUserid());
        List<Bouquet> listBouquet = bDao.getAll();

        int totalOrderValue = 0;
        for (CartWholeSaleDetail cartWholeSaleDetail : cartDetail) {
            totalOrderValue += cartWholeSaleDetail.getTotalValue();
        }
        
        String requestGroupId = null;
        for (CartWholeSaleDetail cartWholeSaleDetail : cartDetail) {
            requestGroupId = cartWholeSaleDetail.getRequest_group_id();
            break;
        }
        
        request.setAttribute("requestGroupId", requestGroupId);
        request.setAttribute("totalOrderValue", totalOrderValue);
        request.setAttribute("listBQ", listBouquet);
        request.setAttribute("listIMG", images);
        request.setAttribute("listCartWholeSale", cartDetail);
        request.getRequestDispatcher("./ZeShopper/cartWholeSale.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentAcc");
        String requestDateStr = request.getParameter("requestDate");
        String requestGroupId = request.getParameter("requestGroupId").trim(); 

        LocalDate requestDate = LocalDate.parse(requestDateStr);

        if (currentUser == null) {
            request.setAttribute("error", "You need login to go to this feature!!!");
            request.getRequestDispatcher("/cartWholeSale").forward(request, response);
            return;
        }

        List<WholeSale> listWS = (List<WholeSale>) session.getAttribute("listWS");

        if (listWS == null || listWS.isEmpty()) {
            request.setAttribute("error", "Không có sản phẩm nào để thêm vào giỏ.");
            request.getRequestDispatcher("/cartWholeSale").forward(request, response);
            return;
        }

        int userId = currentUser.getUserid();
        CartWholeSaleDAO dao = new CartWholeSaleDAO();
        WholeSaleDAO wsDao = new WholeSaleDAO();

        int addedCount = 0;
        for (WholeSale item : listWS) {
            if (item.getStatus().equalsIgnoreCase("EMAILED")) {
                boolean isValidQuote = item.getQuoted_price() != null && item.getQuoted_price() > 0
                        && item.getTotal_price() != null && item.getTotal_price() > 0;
                boolean isNotExist = !dao.hasPendingWholesale(userId, item.getBouquet_id());

                if (isValidQuote && isNotExist) {
                    dao.insertCartWholeSaleItem(
                            userId,
                            item.getBouquet_id(),
                            item.getRequested_quantity(),
                            item.getQuoted_price(),
                            item.getTotal_price(),
                            item.getExpense(),
                            item.getRequest_group_id()
                    );

                    addedCount++;
                }
            }
        }

        if (addedCount > 0) {
            wsDao.updateWholeSaleStatusAndRespond(userId, requestDate, requestGroupId, "ACCEPTED", LocalDate.now());
            response.sendRedirect(request.getContextPath() + "/cartWholeSale");
        } else {
            request.setAttribute("error", "Không có sản phẩm mới nào được thêm vì tất cả đã tồn tại trong giỏ.");
            request.getRequestDispatcher("./ZeShopper/quotationDetails.jsp").forward(request, response);
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
