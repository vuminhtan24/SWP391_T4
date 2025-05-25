<%-- 
    Document   : showUserList
    Created on : May 23, 2025, 5:05:40 PM
    Author     : LAPTOP
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>

        <form id="f1" action="ViewUserList" method="POST">

            <table border="1">

                <tbody>
                    <tr>
                        <td>Role: </td>
                        <td>
                            <select name="txtRoleList" onchange="document.getElementById('f1').submit()">
                                <option value="0" <c:if test="${roleId == 0}">selected</c:if>>All</option>
                                <c:forEach items="${roleList}" var="role">
                                    <option value="${role.role_id}" <c:if test="${role.role_id == roleId}">selected</c:if>>
                                        ${role.role_Name}
                                    </option>
                                </c:forEach>
                            </select>

                        </td>
                    </tr>

                    <tr>
                        <td>Search name: </td>
                        <td><input type="text" name="txtSearchName" value="${keyword}"></td>
                        <td>
                            <select name="sortField">
                                <option value="User_ID" <c:if test="${sortField == 'userid'}">selected</c:if>>User ID</option>
                                <option value="Username" <c:if test="${sortField == 'username'}">selected</c:if>>User Name</option>
                                <option value="Password" <c:if test="${sortField == 'password'}">selected</c:if>>Password</option>
                                <option value="Fullname" <c:if test="${sortField == 'fullname'}">selected</c:if>>Full Name</option>
                                <option value="Email" <c:if test="${sortField == 'email'}">selected</c:if>>Email</option>
                                <option value="Phone" <c:if test="${sortField == 'phone'}">selected</c:if>>Phone</option>
                                <option value="Address" <c:if test="${sortField == 'address'}">selected</c:if>>Address</option>
                                <option value="Role_name" <c:if test="${sortField == 'role'}">selected</c:if>>Role</option>
                                </select>
                            </td>
                            <td>
                                <select name="sortOrder">
                                    <option value="asc" <c:if test="${sortOrder == 'asc'}">selected</c:if>>Ascending</option>
                                <option value="desc" <c:if test="${sortOrder == 'desc'}">selected</c:if>>Descending</option>
                                </select>
                            </td>
                            <td>
                                <input type="submit" value="SEARCH" name="button">
                            </td>
                        </tr>
                    </tbody>
                </table>

            </form>

            <table border="1">
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>User Name</th>
                        <th>Password</th>
                        <th>FUll Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Address</th>
                        <th>Role</th>
                    </tr>
                </thead>
                <tbody>

                <c:forEach items="${userManagerList}" var="user">
                    <tr>
                        <td>${user.getUserid()}</td>
                        <td>${user.getUsername()}</td>
                        <td>${user.getPassword()}</td>
                        <td>${user.getFullname()}</td>
                        <td>${user.getEmail()}</td>
                        <td>${user.getPhone()}</td>
                        <td>${user.getAddress()}</td>
                        <td>${user.getRole()}</td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>

    </body>
</html>
