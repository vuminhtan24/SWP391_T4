package controller;

import dal.FlowerTypeDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.FlowerType;

@WebServlet(name = "RawFlowerServlet2", urlPatterns = {"/DashMin/rawflower2"})
public class RawFlowerServlet2 extends HttpServlet {

    private static final int RECORDS_PER_PAGE = 6; // Number of records per page

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get pagination parameter
            String pageParam = request.getParameter("page");
            int currentPage = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;

            // Get flowerName from request or session
            String flowerName = request.getParameter("flowerName");
            HttpSession session = request.getSession();
            if (flowerName == null) {
                flowerName = (String) session.getAttribute("flowerName");
            }
            if (flowerName == null) {
                flowerName = "";
            }

            // Get flower type list
            FlowerTypeDAO ftDAO = new FlowerTypeDAO();
            List<FlowerType> listFT;

            // Perform search or get all records
            if (!flowerName.trim().isEmpty()) {
                listFT = ftDAO.searchRawFlowerByKeyword(flowerName.trim());
            } else {
                listFT = ftDAO.getAllFlowerTypes();
            }
            System.out.println("Number of records: " + listFT.size());

            // Pagination
            int totalRecords = listFT.size();
            int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);
            currentPage = Math.max(1, Math.min(currentPage, totalPages));
            int startIndex = (currentPage - 1) * RECORDS_PER_PAGE;
            int endIndex = Math.min(startIndex + RECORDS_PER_PAGE, totalRecords);
            List<FlowerType> pagedListFT = totalRecords > 0 ? listFT.subList(startIndex, endIndex) : new ArrayList<>();
            System.out.println("Page " + currentPage + ": from index " + startIndex + " to " + endIndex);

            // Store data in session and request
            session.setAttribute("listFT", pagedListFT);
            session.setAttribute("flowerName", flowerName);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);

            // Forward to JSP
            request.getRequestDispatcher("/DashMin/rawflower.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("message", "Invalid page number format.");
            response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle search from form
        String flowerName = request.getParameter("flowerName");
        System.out.println("POST: flowerName=" + flowerName);

        // Store parameter in session
        HttpSession session = request.getSession();
        session.setAttribute("flowerName", flowerName != null ? flowerName : "");

        // Redirect to reset to page 1
        response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2?page=1");
    }

    @Override
    public String getServletInfo() {
        return "Servlet for managing flower type list, search, and pagination";
    }
}