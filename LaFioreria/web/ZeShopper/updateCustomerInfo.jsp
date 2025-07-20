<%-- 
    Document   : updateCustomerInfo
    Created on : Jul 21, 2025, 4:26:38 AM
    Author     : LAPTOP
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%
    User currentUser = (User) session.getAttribute("currentAcc");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Update Customer Info</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    </head>
    <body class="bg-light">
        <div class="container mt-5">
            <h2 class="mb-4">Update Your Information</h2>

            <form action="updatecustomerInfoservlet" method="post" class="border p-4 bg-white rounded shadow-sm">
                <div class="mb-3">
                    <label for="birthday" class="form-label">Birthday:</label>
                    <input type="date" id="birthday" name="birthday" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="gender" class="form-label">Gender:</label>
                    <select name="gender" id="gender" class="form-select" required>
                        <option value="">-- Select Gender --</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Update Info</button>
            </form>

            <% 
                String message = (String) request.getAttribute("message");
                if (message != null) { 
            %>
            <div class="alert alert-info mt-4"><%= message %></div>
            <% 
                } 
            %>
        </div>
    </body>
</html>

