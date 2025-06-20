package controller;

import dal.FlowerTypeDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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
            int currentPage = (pageParam != null && pageParam.matches("\\d+")) ? Integer.parseInt(pageParam) : 1;
            if (currentPage <= 0) {
                currentPage = 1;
            }

            // Get sort parameters
            String sortField = request.getParameter("sortField");
            String sortDir = request.getParameter("sortDir");
            if (!"flowerName".equals(sortField)) {
                sortField = "flowerId";
            }
            if (!"desc".equalsIgnoreCase(sortDir)) {
                sortDir = "asc";
            }

            // Get filter parameters
            String flowerName = request.getParameter("flowerName");
            String activeFilter = request.getParameter("activeFilter");
            if (flowerName != null) {
                flowerName = flowerName.trim();
            } else {
                flowerName = "";
            }

            // Get flower type list
            FlowerTypeDAO ftDAO = new FlowerTypeDAO();
            List<FlowerType> listFT;
            if (!flowerName.isEmpty() || activeFilter != null) {
                listFT = ftDAO.searchRawFlowerByKeyword(flowerName);
                if (activeFilter != null && !activeFilter.isEmpty()) {
                    listFT.removeIf(ft -> ft.isActive() != Boolean.parseBoolean(activeFilter));
                }
            } else {
                listFT = ftDAO.getAllFlowerTypes();
            }

            // Sorting
            Comparator<FlowerType> cmp = "flowerName".equals(sortField)
                    ? Comparator.comparing(FlowerType::getFlowerName, String.CASE_INSENSITIVE_ORDER)
                    : Comparator.comparingInt(FlowerType::getFlowerId);
            if ("desc".equalsIgnoreCase(sortDir)) {
                cmp = cmp.reversed();
            }
            listFT.sort(cmp);

            // Pagination
            int totalRecords = listFT.size();
            int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);
            currentPage = Math.max(1, Math.min(currentPage, totalPages));
            int startIndex = (currentPage - 1) * RECORDS_PER_PAGE;
            int endIndex = Math.min(startIndex + RECORDS_PER_PAGE, totalRecords);
            List<FlowerType> pagedListFT = totalRecords > 0 ? listFT.subList(startIndex, endIndex) : new ArrayList<>();

            // Store data in session and request
            HttpSession session = request.getSession();
            session.setAttribute("listFT", pagedListFT);
            session.setAttribute("flowerName", flowerName);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("sortField", sortField);
            request.setAttribute("sortDir", sortDir);

            // Forward to JSP
            request.getRequestDispatcher("/DashMin/rawflower.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Invalid page number format.");
            response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle search from form
        String flowerName = request.getParameter("flowerName");
        String activeFilter = request.getParameter("activeFilter");
        if (flowerName != null) {
            flowerName = flowerName.trim();
        } else {
            flowerName = "";
        }

        // Store parameters in session
        HttpSession session = request.getSession();
        session.setAttribute("flowerName", flowerName);
        session.setAttribute("activeFilter", activeFilter);

        // Redirect to reset to page 1
        response.sendRedirect(request.getContextPath() + "/DashMin/rawflower2?page=1");
    }

    @Override
    public String getServletInfo() {
        return "Servlet for managing flower type list, search, sorting, and pagination with active filter";
    }
}