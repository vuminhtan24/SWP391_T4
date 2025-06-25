/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author k16
 */
@WebServlet(name = "BlogManagerController", urlPatterns = {
    "/blogmanager",
    "/blog",
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
            case BASE_PATH + "manager" -> {
                doGetManagerPostList(request, response);
            }
            case BASE_PATH -> {
                doGetPostList(request, response);
            }
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
                doDelete(request, response);
            }
            default ->
                throw new AssertionError();
        }
    }

    private void doGetManagerPostList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("DashMin/blogmanager.jsp").forward(request, response);
    }

    private void doGetPostList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private void doAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private void doEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private void doDelete(HttpServletRequest request, HttpServletResponse response)
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
    }

}
