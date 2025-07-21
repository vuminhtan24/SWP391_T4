/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BouquetDAO;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;
import model.WholeSale;
import dal.WholeSaleDAO;
import model.Bouquet;
import model.BouquetImage;
import model.User;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "SendQuotationController", urlPatterns = {"/sendQuotation"})
public class SendQuotationController extends HttpServlet {

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
            out.println("<title>Servlet SendQuotationController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendQuotationController at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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

        int userId = Integer.parseInt(request.getParameter("userId"));
        String email = request.getParameter("userEmail");
        LocalDate requestDate = LocalDate.parse(request.getParameter("requestDate"));
        String requestGroupId = request.getParameter("requestGroupId");

        WholeSaleDAO dao = new WholeSaleDAO();
        List<WholeSale> quotedList = dao.getWholeSaleQuotedList(userId, requestDate, requestGroupId);

        if (quotedList == null || quotedList.isEmpty()) {
            response.sendRedirect("requestWholeSaleDetails?userId=" + userId
                    + "&requestDate=" + requestDate
                    + "&requestGroupId=" + requestGroupId
                    + "&status=QUOTED&sendEmail=fail");
            return;
        }

        String emailContent = generateEmailContent(request, requestDate, requestGroupId, quotedList);

        final String fromEmail = "quangvmhe181542@fpt.edu.vn";
        final String password = "nhcq rkmw uulk nkwm";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, "La Fioreria"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Order quotation information on " + requestDate);
            message.setContent(emailContent, "text/html; charset=UTF-8");

            Transport.send(message);

            dao.markAsEmailedForQuotedList(userId, requestDate, requestGroupId);

            response.sendRedirect("requestWholeSaleDetails?userId=" + userId
                    + "&requestDate=" + requestDate
                    + "&requestGroupId=" + requestGroupId
                    + "&status=QUOTED&sendEmail=true");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("requestWholeSaleDetails?userId=" + userId
                    + "&requestDate=" + requestDate
                    + "&requestGroupId=" + requestGroupId
                    + "&status=QUOTED&sendEmail=fail");
        }
    }

    private String generateEmailContent(HttpServletRequest request, LocalDate requestDate, String requestGroupId, List<WholeSale> quotedList) {
        BouquetDAO bDao = new BouquetDAO();
        UserDAO uDao = new UserDAO();
        List<Bouquet> listBQ = bDao.getAll();
        User userInfo = uDao.getUserByID(quotedList.get(0).getUser_id());
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        StringBuilder content = new StringBuilder();
        content.append("<h2>Order quotation information on ").append(requestDate).append("</h2>");
        content.append("<p style='font-size:14px; margin-bottom:15px;'>")
                .append("Dear ").append(userInfo.getFullname()).append("! ")
                .append("La Fioreria would like to send you a quote request on ")
                .append(requestDate);
        content.append("<table border='1' cellpadding='6' cellspacing='0' style='border-collapse: collapse;'>")
                .append("<tr style='background-color:#f2f2f2;'>")
                .append("<th>STT</th>")
                .append("<th>Bouquet Name</th>")
                .append("<th>Requested Quantity</th>")
                .append("<th>Price per Unit</th>")
                .append("<th>Total Value</th>")
                .append("</tr>");

        int stt = 1;
        long totalOrderPrice = 0;

        for (WholeSale ws : quotedList) {
            String bouquetName = listBQ.stream()
                    .filter(bq -> bq.getBouquetId() == ws.getBouquet_id())
                    .map(Bouquet::getBouquetName)
                    .findFirst()
                    .orElse("Unknown");

            Integer quotedPrice = ws.getQuoted_price() != null ? ws.getQuoted_price() : 0;
            Integer totalPrice = ws.getTotal_price() != null ? ws.getTotal_price() : 0;

            totalOrderPrice += totalPrice;

            content.append("<tr>")
                    .append("<td>").append(stt++).append("</td>")
                    .append("<td>").append(bouquetName).append("</td>")
                    .append("<td>").append(ws.getRequested_quantity()).append("</td>")
                    .append("<td>").append(String.format("%,d ₫", quotedPrice)).append("</td>")
                    .append("<td>").append(String.format("%,d ₫", totalPrice)).append("</td>")
                    .append("</tr>");
        }

        content.append("<tr style='font-weight:bold;'>")
                .append("<td colspan='5' style='text-align:right;'>")
                .append("Total WholeSale Order: ").append(String.format("%,d ₫", totalOrderPrice))
                .append("</td>")
                .append("</tr>");
        content.append("</table>");

        content.append("<p style='margin-top:20px;'>")
                .append("<a href='").append(baseUrl).append("' ")
                .append("style='display:inline-block; padding:10px 15px; background-color:#28a745; color:#fff; text-decoration:none; border-radius:5px;'>")
                .append("Go to La Fioreria Shop</a>")
                .append("</p>");

        return content.toString();
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
