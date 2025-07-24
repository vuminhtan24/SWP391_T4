<%-- 
    Document   : viewnotifications
    Created on : Jun 27, 2025, 09:25 AM
    Author     : Admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Danh sách thông báo - La Fioreria</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Danh sách thông báo cho admin" name="keywords">
    <meta content="Trang hiển thị toàn bộ thông báo cho admin" name="description">

    <!-- Favicon -->
    <link href="${pageContext.request.contextPath}/DashMin/img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="${pageContext.request.contextPath}/DashMin/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="${pageContext.request.contextPath}/DashMin/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="${pageContext.request.contextPath}/DashMin/css/style.css" rel="stylesheet">
</head>
<body>
    <div class="container-fluid position-relative bg-white d-flex p-0">
        <!-- Sidebar Start -->
        <div class="sidebar pe-4 pb-3">
            <nav class="navbar bg-light navbar-light">
                <a href="${pageContext.request.contextPath}/DashMin/admin" class="navbar-brand mx-4 mb-3">
                    <h3 class="text-primary"><i class="fa fa-hashtag me-2"></i>DASHMIN</h3>
                </a>
                <div class="d-flex align-items-center ms-4 mb-4">
                    <div class="position-relative">
                        <img class="rounded-circle" src="${pageContext.request.contextPath}/DashMin/img/user.jpg" alt="" style="width: 40px; height: 40px;">
                        <div class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"></div>
                    </div>
                    <div class="ms-3">
                        <h6 class="mb-0">Jhon Doe</h6>
                        <span>Admin</span>
                    </div>
                </div>
                <div class="navbar-nav w-100">
                    <a href="${pageContext.request.contextPath}/DashMin/admin" class="nav-item nav-link"><i class="fa fa-tachometer-alt me-2"></i>Dashboard</a>
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Elements</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <a href="${pageContext.request.contextPath}/DashMin/button.jsp" class="dropdown-item">Buttons</a>
                            <a href="${pageContext.request.contextPath}/DashMin/typography.jsp" class="dropdown-item">Typography</a>
                            <a href="${pageContext.request.contextPath}/DashMin/element.jsp" class="dropdown-item">Other Elements</a>
                        </div>
                    </div>
                    <a href="${pageContext.request.contextPath}/DashMin/widget.jsp" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Widgets</a>
                    <a href="${pageContext.request.contextPath}/DashMin/form.jsp" class="nav-item nav-link"><i class="fa fa-keyboard me-2"></i>Forms</a>
                    <a href="${pageContext.request.contextPath}/ViewUserList" class="nav-item nav-link"><i class="fa fa-table me-2"></i>User</a>
                    <a href="${pageContext.request.contextPath}/viewBouquet" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Bouquet</a>
                    <a href="${pageContext.request.contextPath}/DashMin/chart.jsp" class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
                    <a href="${pageContext.request.contextPath}/orderManagement" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Order</a>
                    <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="nav-item nav-link"><i class="fa fa-table me-2"></i>RawFlower</a>
                    <a href="${pageContext.request.contextPath}/category" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Category</a>
                    <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-table me-2"></i>Repair Center</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/repairOrders" class="dropdown-item">Repair Orders</a>
                                <a href="${pageContext.request.contextPath}/repairHistory" class="dropdown-item">Repair History</a>
                            </div>
                    </div>
                    <a href="${pageContext.request.contextPath/listWholeSaleRequest}" class="nav-item nav-link"><i class="fa fa-table me-2"></i>WholeSale Management</a>         
                    <a href="${pageContext.request.contextPath}/DashMin/viewNotifications" class="nav-item nav-link active"><i class="fa fa-bell me-2"></i>Notifications</a>
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="far fa-file-alt me-2"></i>Pages</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <a href="${pageContext.request.contextPath}/DashMin/404.jsp" class="dropdown-item">404 Error</a>
                            <a href="${pageContext.request.contextPath}/DashMin/blank.jsp" class="dropdown-item">Blank Page</a>
                            <a href="${pageContext.request.contextPath}/viewuserdetail" class="dropdown-item">View User Detail</a>
                            <a href="${pageContext.request.contextPath}/adduserdetail" class="dropdown-item">Add new User</a>
                        </div>
                    </div>
                </div>
            </nav>
        </div>
        <!-- Sidebar End -->

        <!-- Content Start -->
        <div class="content">
            <!-- Navbar Start -->
            <jsp:include page="/DashMin/navbar.jsp"/>
            <!-- Navbar End -->

            <!-- Notifications Start -->
            <div class="container-fluid pt-4 px-4">
                <div class="bg-light rounded h-100 p-4">
                    <h6 class="mb-4">Danh sách toàn bộ thông báo</h6>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <c:if test="${not empty notifications}">
                        <button class="btn btn-primary mb-3" onclick="markAllAsRead()">Đánh dấu tất cả đã đọc</button>
                    </c:if>
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Message</th>
                                    <th>Created At</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${empty notifications}">
                                        <tr>
                                            <td colspan="5" class="text-center">Không có thông báo nào.</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="notification" items="${notifications}">
                                            <tr>
                                                <td>${notification.notificationId}</td>
                                                <td>${notification.message}</td>
                                                <td><fmt:formatDate value="${notification.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                                                <td>${notification.status}</td>
                                                <td>
                                                    <c:if test="${notification.status == 'unread'}">
                                                        <button class="btn btn-sm btn-info" onclick="markAsRead(${notification.notificationId})">Mark as Read</button>
                                                    </c:if>
                                                    <button class="btn btn-sm btn-danger" onclick="deleteNotification(${notification.notificationId})">Delete</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <!-- Notifications End -->

            <!-- Footer Start -->
            <div class="container-fluid pt-4 px-4">
                <div class="bg-light rounded-top p-4">
                    <div class="row">
                        <div class="col-12 col-sm-6 text-center text-sm-start">
                            © <a href="#">La Fioreria</a>, All Right Reserved.
                        </div>
                        <div class="col-12 col-sm-6 text-center text-sm-end">
                            Designed By <a href="https://htmlcodex.com">HTML Codex</a>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Footer End -->
        </div>
        <!-- Content End -->

        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
    </div>

    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/DashMin/lib/chart/chart.min.js"></script>
    <script src="${pageContext.request.contextPath}/DashMin/lib/easing/easing.min.js"></script>
    <script src="${pageContext.request.contextPath}/DashMin/lib/waypoints/waypoints.min.js"></script>
    <script src="${pageContext.request.contextPath}/DashMin/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/js/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/js/moment-timezone.min.js"></script>
    <script src="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

    <!-- Template Javascript -->
    <script src="${pageContext.request.contextPath}/DashMin/js/main.js"></script>

    <script>
        function markAsRead(notificationId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/notification?action=markAsRead',
                type: 'POST',
                data: { notificationId: notificationId },
                success: function(response) {
                    if (response === "success") {
                        location.reload();
                    } else {
                        alert("Có lỗi khi đánh dấu đọc: " + response);
                    }
                },
                error: function() {
                    alert("Lỗi kết nối server.");
                }
            });
        }

        function deleteNotification(notificationId) {
            if (confirm("Bạn có chắc muốn xóa thông báo này?")) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/notification?action=delete',
                    type: 'POST',
                    data: { notificationId: notificationId },
                    success: function(response) {
                        if (response === "success") {
                            location.reload();
                        } else {
                            alert("Có lỗi khi xóa thông báo: " + response);
                        }
                    },
                    error: function() {
                        alert("Lỗi kết nối server.");
                    }
                });
            }
        }

        function markAllAsRead() {
            $.ajax({
                url: '${pageContext.request.contextPath}/notification?action=markAllAsRead',
                type: 'POST',
                success: function(response) {
                    if (response === "success") {
                        location.reload();
                    } else {
                        alert("Có lỗi khi đánh dấu tất cả đã đọc: " + response);
                    }
                },
                error: function(xhr, status, error) {
                    alert("Lỗi kết nối server: " + error);
                }
            });
        }
    </script>
</body>
</html>