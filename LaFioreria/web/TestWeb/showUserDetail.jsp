<%-- 
    Document   : showUserDetail
    Created on : May 21, 2025, 5:18:45 PM
    Author     : LAPTOP
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show User Detail Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <table border="1">

            <tbody>
                <c:forEach items="${userIds}" var="id">
                    <tr>
                        <td>
                            <a href="viewuserdetail?id=${id}">${id}</a>
                        </td>
                    </tr>           
                </c:forEach> 
            </tbody>
        </table>

        <c:if test="${empty userIds}">
            <p>No userIds found</p>
        </c:if>
        <c:if test="${not empty userIds}">
            <p>User IDs are available</p>
        </c:if>


        <form action="viewuserdetail" method="POST">
            <table border="1">

                <tbody>
                    <tr>
                        <td>User ID: </td>
                        <td>
                            <input type="text" name="id" value="${userManager.userid}" readonly="">
                        </td>
                    </tr>
                    <tr>
                        <td>User Name: </td>
                        <td>
                            <input type="text" name="name" value="${userManager.username}">
                        </td>
                    </tr>
                    <tr>
                        <td>Password: </td>
                        <td>
                            <input type="text" name="pass" value="${userManager.password}">

                        </td>
                    </tr>
                    <tr>
                        <td>Full Name: </td>
                        <td>
                            <input type="text" name="FullName" value="${userManager.fullname}">
                        </td>
                    </tr>
                    <tr>
                        <td>Email: </td>
                        <td>
                            <input type="text" name="email" value="${userManager.email}">
                        </td>
                    </tr>
                    <tr>
                        <td>Phone Number: </td>
                        <td>
                            <input type="type" name="phone" value="${userManager.phone}">
                        </td>
                    </tr>
                    <tr>
                        <td>Address: </td>
                        <td>
                            <input type="type" name="address" value="${userManager.address}">
                        </td>
                    </tr>
                    <tr>
                        <td>Role: </td>
                        <td>
                            <input type="type" name="role" value="${userManager.role}" readonly="">
                        </td>
                    </tr>
                    <tr>

                        <td>
                            <select name="option">
                                <c:forEach items="${roleNames}" var="role">
                                    <option value="${role}">${role}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td><input type="submit" name="ud" value="UPDATE"></td>
                    </tr>
                </tbody>
            </table>           
        </form>



    </body>
</html>
