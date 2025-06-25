/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BlogDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Blog;

/**
 *
 * @author k16
 */
@WebServlet(name = "BlogManagerController", urlPatterns = {
    "/blogmanager",
    "/blog",
    "/blog/detail",
    "/blog/add",
    "/blog/edit",
    "/blog/delete"
})
public class BlogManagerController extends HttpServlet {

    private static final String BASE_PATH = "/blog";

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
        String path = request.getServletPath();

        switch (path) {
            case BASE_PATH + "manager" ->
                doGetManagerPostList(request, response);

            case BASE_PATH ->
                doGetPostList(request, response);

            case BASE_PATH + "/detail" ->
                doGetDetail(request, response);

            default ->
                throw new AssertionError();
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
        String path = request.getServletPath();

        switch (path) {
            case BASE_PATH + "/add" -> {
                doAdd(request, response);
            }
            case BASE_PATH + "/edit" -> {
                doEdit(request, response);
            }
            case BASE_PATH + "/delete" -> {
                doDeletePost(request, response);
            }
            default ->
                throw new AssertionError();
        }
    }

    private void doGetManagerPostList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
        int limit = 10;
        int offset = (page - 1) * limit;
        String search = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");
        String sort = request.getParameter("sort");
        int categoryId = Integer.parseInt(request.getParameter("categoryId") != null && !request.getParameter("categoryId").isBlank() ? request.getParameter("categoryId") : "0");

        BlogDAO dao = new BlogDAO();
        List<Blog> blogs = dao.getAllBlogWithFilter(limit, offset, search, sortBy, sort, categoryId, null);
        int totalCount = dao.getTotalBlogCountWithFilter(search, categoryId);

        request.setAttribute("blogs", blogs);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("DashMin/blogmanager.jsp").forward(request, response);
    }

    private void doGetDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDAO bDao = new BlogDAO();
        String bidStr = request.getParameter("bid");

        int bid = bidStr != null && !bidStr.isBlank() ? Integer.parseInt(bidStr) : -1;

        Blog b = null;

        b = bDao.getBlogById(bid);
        fullLoadBlogInformation(b);

        request.setAttribute("blog", b);
        request.getRequestDispatcher("../ZeShopper/blogdetail.jsp").forward(request, response);
    }

    private void doGetPostList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
        int limit = 10;
        int offset = (page - 1) * limit;
        String search = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");
        String sort = request.getParameter("sort");
        int categoryId = Integer.parseInt(request.getParameter("categoryId") != null && !request.getParameter("categoryId").isBlank() ? request.getParameter("categoryId") : "0");

        BlogDAO dao = new BlogDAO();
        List<Blog> blogs = dao.getAllBlogWithFilter(limit, offset, search, sortBy, sort, categoryId, "Active");
        int totalCount = dao.getTotalBlogCountWithFilter(search, categoryId);

        request.setAttribute("blogs", blogs);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("ZeShopper/blog.jsp").forward(request, response);
    }

    private void doAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private void doEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private void doDeletePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    //Helper
    private void fullLoadBlogInformation(Blog b){
        
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
