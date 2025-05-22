<%-- 
    Document   : addNewUser
    Created on : May 22, 2025, 9:35:29 PM
    Author     : LAPTOP
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="adduserdetail" method="Post">
            <table border="1">

                <tbody>
                    <tr>
                        <td>User ID: </td>
                        <td>
                            <input type="text" name="id">
                        </td>
                    </tr>
                    <tr>
                        <td>User Name: </td>
                        <td>
                            <input type="text" name="name">
                        </td>
                    </tr>
                    <tr>
                        <td>Password: </td>
                        <td>
                            <input type="text" name="pass">

                        </td>
                    </tr>
                    <tr>
                        <td>Full Name: </td>
                        <td>
                            <input type="text" name="FullName">
                        </td>
                    </tr>
                    <tr>
                        <td>Email: </td>
                        <td>
                            <input type="text" name="email">
                        </td>
                    </tr>
                    <tr>
                        <td>Phone Number: </td>
                        <td>
                            <input type="type" name="phone">
                        </td>
                    </tr>
                    <tr>
                        <td>Address: </td>
                        <td>
                            <input type="type" name="address">
                        </td>
                    </tr>

                    <tr>

                        <td>Role: </td>
                        <td>
                            <c:out value="${roleNames}" default="roleNames is NULL or EMPTY" />
                            <select name="option">
                                <c:forEach items="${roleNames}" var="role">
                                    <option value="${role}">${role}</option>
                                </c:forEach>
                            </select>
                            
                        </td>
                    </tr>
                    <tr>
                        <td><input type="submit" name="ad" value="ADD"></td>
                    </tr>
                </tbody>
            </table>
            <c:if test="${not empty error}">
                <p style="color:red">${error}</p>
            </c:if>            
        </form>

    </body>
</html>
