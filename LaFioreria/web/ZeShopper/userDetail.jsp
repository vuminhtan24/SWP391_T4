<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Detail</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f7f9fc;
            margin: 0;
            padding: 0;
        }

        h1 {
            text-align: center;
            margin-top: 30px;
            color: #333;
        }

        .container {
            width: 500px;
            margin: 40px auto;
            background: #fff;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        td {
            padding: 12px 8px;
            vertical-align: middle;
        }

        td:first-child {
            font-weight: bold;
            width: 35%;
            color: #444;
        }

        input[type="text"], input[type="type"] {
            width: 100%;
            padding: 8px 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }

        input[readonly] {
            background-color: #f0f0f0;
            color: #555;
        }
    </style>
</head>
<body>
    <h1>User Detail</h1>
    <div class="container">
        <form action="viewuserdetailhome" method="POST">
            <table>
                <tbody>
                    <tr>
                        <td>User ID:</td>
                        <td><input type="text" name="id" value="${userManager.userid}" readonly></td>
                    </tr>
                    <tr>
                        <td>User Name:</td>
                        <td><input type="text" name="name" value="${userManager.username}"></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="text" name="pass" value="${userManager.password}"></td>
                    </tr>
                    <tr>
                        <td>Full Name:</td>
                        <td><input type="text" name="FullName" value="${userManager.fullname}"></td>
                    </tr>
                    <tr>
                        <td>Email:</td>
                        <td><input type="text" name="email" value="${userManager.email}"></td>
                    </tr>
                    <tr>
                        <td>Phone Number:</td>
                        <td><input type="text" name="phone" value="${userManager.phone}"></td>
                    </tr>
                    <tr>
                        <td>Address:</td>
                        <td><input type="text" name="address" value="${userManager.address}"></td>
                    </tr>
                </tbody>
            </table>
        </form>
    </div>
</body>
</html>
