/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

  import jakarta.servlet.ServletException;
  import jakarta.servlet.annotation.WebServlet;
  import jakarta.servlet.http.HttpServlet;
  import jakarta.servlet.http.HttpServletRequest;
  import jakarta.servlet.http.HttpServletResponse;
  import jakarta.servlet.http.HttpSession;
  import java.io.IOException;
  import dal.FlowerTypeDAO;
  import dal.BouquetDAO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "HideRawFlowerServlet", urlPatterns = {"/hideRawFlower"})
public class HideRawFlowerServlet extends HttpServlet {

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
          try {
              String flowerIdStr = request.getParameter("flower_id");
              if (flowerIdStr == null || flowerIdStr.trim().isEmpty()) {
                  session.setAttribute("error", "Flower ID is required.");
                  response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
                  return;
              }

              int flowerId = Integer.parseInt(flowerIdStr);

              // Kiểm tra xem loại hoa có trong giỏ hoa không
              BouquetDAO bouquetDAO = new BouquetDAO(); // Giả định có DAO cho bouquet
              if (!bouquetDAO.isFlowerInBouquet(flowerId)) {
                  session.setAttribute("error", "Cannot deactivate flower type. It is used in one or more bouquets. Please remove it from all bouquets first.");
                  response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
                  return;
              }

              // Nếu không, thực hiện xóa mềm (active = 0)
              FlowerTypeDAO ftDAO = new FlowerTypeDAO();
              ftDAO.deleteFlowerType(flowerId); // Sử dụng phương thức xóa mềm

              session.setAttribute("message", "Flower type deactivated successfully!");
              response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
          } catch (NumberFormatException e) {
              session.setAttribute("error", "Invalid Flower ID.");
              response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
          } catch (Exception e) {
              e.printStackTrace();
              session.setAttribute("error", "An error occurred while deactivating the flower type: " + e.getMessage());
              response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
          }
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
