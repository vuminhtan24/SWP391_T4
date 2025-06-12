/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BouquetDAO;
import dal.CategoryDAO;
import dal.RawFlowerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Bouquet;
import model.Category;
import model.RawFlower;
import model.BouquetRaw;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AddBouquetController", urlPatterns = {"/addBouquet"})
public class AddBouquetController extends HttpServlet {

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
            out.println("<title>Servlet AddBouquetController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddBouquetController at " + request.getContextPath() + "</h1>");
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
        RawFlowerDAO rdao = new RawFlowerDAO();
        CategoryDAO cdao = new CategoryDAO();
        List<Category> cAll = cdao.getBouquetCategory();
        List<RawFlower> all = rdao.getRawFlower();
        // 1. Tất cả hoa để đổ vào dropdown
        request.setAttribute("cateBouquetHome", cAll);
        request.setAttribute("flowerInBouquet", all);

        request.getRequestDispatcher("./DashMin/addBouquet.jsp").forward(request, response);
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
        // 1. Lấy dữ liệu từ form
        String bouquetName = request.getParameter("bouquetName");
        String imageUrl = request.getParameter("imageUrl");
        String description = request.getParameter("description");
        int cid = Integer.parseInt(request.getParameter("category"));
        double totalValueDbl = Double.parseDouble(request.getParameter("totalValue"));
        int price = (int) Math.round(totalValueDbl);
        BouquetDAO dao = new BouquetDAO();
        
        String[] flowerIds = request.getParameterValues("flowerIds");
        String[] quantities = request.getParameterValues("quantities");
        
        for (Bouquet bouquet : dao.getAll()) {
            if(bouquetName.equalsIgnoreCase(bouquet.getBouquetName())){
                request.setAttribute("err", "Duplicated name. Please enter another name!!!");
                doGet(request, response);
                return;
            }
        }
        // 2. Tạo và insert Bouquet
        Bouquet bouquet = new Bouquet();
        bouquet.setBouquetName(bouquetName);
        bouquet.setDescription(description);
        bouquet.setImageUrl(imageUrl);
        bouquet.setCid(cid);
        bouquet.setPrice(price);

        int bouquetId = dao.insertBouquet(bouquet);  // <-- lấy ID sinh ra

        if (bouquetId <= 0) {
            // chèn thất bại, có thể throw hoặc forward lỗi
            request.setAttribute("error", "Không lưu được bouquet");
            request.getRequestDispatcher("/DashMin/blank.jsp")
                    .forward(request, response);
            return;
        }

        // 3. Insert các BouquetRaw
        if (flowerIds != null && quantities != null
                && flowerIds.length == quantities.length) {
            try {
                for (int i = 0; i < flowerIds.length; i++) {
                    int flowerID = Integer.parseInt(flowerIds[i]);
                    int quantity = Integer.parseInt(quantities[i]);

                    BouquetRaw rawBQ = new BouquetRaw();
                    rawBQ.setBouquet_id(bouquetId);    // <-- gán đúng bouquet_id
                    rawBQ.setRaw_id(flowerID);
                    rawBQ.setQuantity(quantity);

                    dao.insertBouquetRaw(rawBQ);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Dữ liệu không hợp lệ");
                request.getRequestDispatcher("/DashMin/blank.jsp")
                        .forward(request, response);
                return;
            } catch (Exception e) {
                request.setAttribute("error", "Lỗi cơ sở dữ liệu: " + e.getMessage());
                request.getRequestDispatcher("/DashMin/blank.jsp")
                        .forward(request, response);
                return;
            }
        }

        // 4. Forward về trang product
        response.sendRedirect(request.getContextPath() + "/viewBouquet");
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
