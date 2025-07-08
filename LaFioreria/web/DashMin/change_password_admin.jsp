<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Admin - Change Password</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            body {
                background-color: #f8f9fa;
            }
            .card {
                max-width: 500px;
                margin: auto;
                margin-top: 80px;
                border-radius: 15px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            }
            .card-header {
                background-color: #343a40;
                color: white;
                font-weight: 500;
                text-align: center;
                border-radius: 15px 15px 0 0;
            }
            .form-label {
                font-weight: 500;
            }
            .btn-danger {
                width: 100%;
            }
        </style>
    </head>
    <body>

        <div class="card">
            <div class="card-header">
                Admin: Change User Password
            </div>
            <div class="card-body">
                <!-- ✅ Display messages -->
                <c:if test="${not empty param.success}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        ✅ Password updated successfully.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <!-- 🔒 Change Password Form -->
                <form action="${pageContext.request.contextPath}/AdminChangePasswordServlet" method="post">
                    <input type="hidden" name="userId" value="${param.userId != null ? param.userId : userManager.userid}" />

                    <div class="mb-3">
                        <label for="newPassword" class="form-label">New Password</label>
                        <input type="password" class="form-control" name="newPassword" id="newPassword" required>
                    </div>

                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label">Confirm Password</label>
                        <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" required>
                    </div>

                    <button type="submit" class="btn btn-danger">Change Password</button>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
