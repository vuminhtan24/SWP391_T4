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
        <h1>User List</h1>

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
                                <option value="User_ID" <c:if test="${sortField == 'User_ID'}">selected</c:if>>User ID</option>
                                <option value="Username" <c:if test="${sortField == 'Username'}">selected</c:if>>User Name</option>
                                <option value="Password" <c:if test="${sortField == 'Password'}">selected</c:if>>Password</option>
                                <option value="Fullname" <c:if test="${sortField == 'Fullname'}">selected</c:if>>Full Name</option>
                                <option value="Email" <c:if test="${sortField == 'Email'}">selected</c:if>>Email</option>
                                <option value="Phone" <c:if test="${sortField == 'Phone'}">selected</c:if>>Phone</option>
                                <option value="Address" <c:if test="${sortField == 'Address'}">selected</c:if>>Address</option>
                                <option value="Role_name" <c:if test="${sortField == 'Role_name'}">selected</c:if>>Role</option>
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
                        <th>Full Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Address</th>
                        <th>Role</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${userManagerList}" var="user">
                    <tr>
                        <td>${user.userid}</td>
                        <td>${user.username}</td>
                        <td>${user.password}</td>
                        <td>${user.fullname}</td>
                        <td>${user.email}</td>
                        <td>${user.phone}</td>
                        <td>${user.address}</td>
                        <td>${user.role}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- PAGINATION -->
        <div style="margin-top: 20px;">
            <c:if test="${totalPages > 1}">
                <form action="ViewUserList" method="post" id="paginationForm">
                    <!-- preserve current filters -->
                    <input type="hidden" name="txtSearchName" value="${keyword}" />
                    <input type="hidden" name="txtRoleList" value="${roleId}" />
                    <input type="hidden" name="sortField" value="${sortField}" />
                    <input type="hidden" name="sortOrder" value="${sortOrder}" />

                    <c:set var="prevPage" value="${currentPage - 1}" />
                    <c:set var="nextPage" value="${currentPage + 1}" />

                    <c:if test="${currentPage > 1}">
                        <button type="submit" name="page" value="1"><<</button>
                        <button type="submit" name="page" value="${prevPage}"><</button>
                    </c:if>

                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <button type="submit" name="page" value="${i}"
                                <c:if test="${i == currentPage}">style="font-weight:bold;"</c:if>>
                            ${i}
                        </button>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <button type="submit" name="page" value="${nextPage}">></button>
                        <button type="submit" name="page" value="${totalPages}">>></button>
                    </c:if>
                </form>
            </c:if>
        </div>

    </body>
</html>

