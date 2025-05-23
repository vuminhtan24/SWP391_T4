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
                            <select name="txtRoleList" onchange=document.getElementById("f1").submit()>
                                <option value="0"> All </option>
                                <c:forEach items="${roleList}" var="role">
                                    <option value="${role.getRole_id()}" ${role.getRole_id() == role_id ? 'Selected':''} >${role.getRole_Name()}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>

        </form>
    </body>
</html>
