<%--
    Document   : orderEdit
    Created on : Jun 16, 2025, 12:00:00 AM
    Author     : VU MINH TAN
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Edit Order - DASHMIN</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

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
                    <a href="${pageContext.request.contextPath}/DashMin/admin.jsp" class="navbar-brand mx-4 mb-3">
                        <h3 class="text-primary"><i class="fa fa-hashtag me-2"></i>DASHMIN</h3>
                    </a>
                    <div class="d-flex align-items-center ms-4 mb-4">
                        <div class="position-relative">
                            <img class="rounded-circle" src="${pageContext.request.contextPath}/DashMin/img/user.jpg" alt="" style="width: 40px; height: 40px;">
                            <div class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"></div>
                        </div>
                        <div class="ms-3">
                            <h6 class="mb-0">${sessionScope.currentAcc.getFullname()}</h6>
                            <span>Admin</span>
                        </div>
                    </div>
                    <c:choose>
                        <c:when test="${sessionScope.currentAcc.getRole() != 2}">
                            <div class="navbar-nav w-100">
                                <a href="${pageContext.request.contextPath}/DashMin/admin" class="nav-item nav-link"><i class="fa fa-tachometer-alt me-2"></i>Dashboard</a>
                                <div class="nav-item dropdown">
                                    <a href="${pageContext.request.contextPath}/ViewUserList" class="nav-item nav-link"><i class="fa fa-table me-2"></i>User</a>
                                    <a href="${pageContext.request.contextPath}/viewBouquet" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Bouquet</a>
                                    <a href="${pageContext.request.contextPath}/DashMin/chart.jsp" class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
                                    <div class="nav-item dropdown">
                                        <a href="#" class="nav-link dropdown-toggle active" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Order</a>
                                        <div class="dropdown-menu bg-transparent border-0">
                                            <a href="${pageContext.request.contextPath}/orderManagement" class="dropdown-item">Order Management</a>
                                            <a href="${pageContext.request.contextPath}/orderDetail" class="dropdown-item active">Order Details</a>
                                        </div>
                                    </div>
                                    <a href="${pageContext.request.contextPath}/discount" 
                                       class="nav-item nav-link">
                                        <i class="fa fa-percentage me-2"></i>Discount
                                    </a>
                                    <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="nav-item nav-link"><i class="fa fa-table me-2"></i>RawFlower</a>
                                    <a href="${pageContext.request.contextPath}/category" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Category</a>
                                    <div class="nav-item dropdown">
                                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-table me-2"></i>Repair Center</a>
                                        <div class="dropdown-menu bg-transparent border-0">
                                            <a href="${pageContext.request.contextPath}/repairOrders" class="dropdown-item">Repair Orders</a>
                                            <a href="${pageContext.request.contextPath}/repairHistory" class="dropdown-item">Repair History</a>
                                            <a href="${pageContext.request.contextPath}/listRequest" class="dropdown-item">List Request</a>
                                        </div>
                                    </div>
                                    <a href="${pageContext.request.contextPath}/listWholeSaleRequest" class="nav-item nav-link"><i class="fa fa-table me-2"></i>WholeSale</a>                   
                                    <a href="${pageContext.request.contextPath}" class="nav-item nav-link"><i class="fa fa-table me-2"></i>La Fioreria</a>
                                    <div class="nav-item dropdown">
                                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="far fa-file-alt me-2"></i>Pages</a>
                                        <div class="dropdown-menu bg-transparent border-0">
                                            <a href="${pageContext.request.contextPath}/DashMin/404.jsp" class="dropdown-item">404 Error</a>
                                            <a href="${pageContext.request.contextPath}/DashMin/blank.jsp" class="dropdown-item">Blank Page</a>
                                            <a href="${pageContext.request.contextPath}/viewuserdetail" class="dropdown-item">View User Detail</a>
                                            <a href="${pageContext.request.contextPath}/adduserdetail" class="dropdown-item">Add new User </a>
                                        </div>
                                    </div>
                                </div>
                        </nav>
                    </div>
                </c:when>  
                <c:otherwise>
                    <div class="navbar-nav w-100">
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle active" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Order</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/orderManagement" class="dropdown-item active">Order Management</a>
                                <a href="${pageContext.request.contextPath}/orderDetail" class="dropdown-item">Order Details</a>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="nav-item nav-link"><i class="fa fa-table me-2"></i>RawFlower</a>

                        <a href="${pageContext.request.contextPath}" class="nav-item nav-link"><i class="fa fa-table me-2"></i>La Fioreria</a>
                    </div>
                </nav>
            </div>
        </c:otherwise>    
    </c:choose>  
    <!-- Sidebar End -->

    <!-- Content Start -->
    <div class="content">
        <!-- Navbar Start -->
        <jsp:include page="/DashMin/navbar.jsp"/>
        <!-- Navbar End -->

        <!-- Edit Order Form Start -->
        <div class="container-fluid pt-4 px-4">
            <div class="bg-light rounded p-4">
                <div class="d-flex align-items-center justify-content-between mb-4">
                    <h6 class="mb-0">Edit Order ID: ${order.orderId}</h6>
                    <a href="${pageContext.request.contextPath}/orderDetail?orderId=${order.orderId}">Back to Details</a>
                </div>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger" role="alert">
                        ${errorMessage}
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/orderDetail" method="post">
                    <input type="hidden" name="orderId" value="${order.orderId}">

                    <div class="mb-3">
                        <label for="orderDate" class="form-label">Order Date:</label>
                        <input type="text" class="form-control" id="orderDate" value="${order.orderDate}" readonly>
                    </div>

                    <div class="mb-3">
                        <label for="customerName" class="form-label">Customer Name:</label>
                        <input type="text" class="form-control" id="customerName" value="${order.customerName}" readonly>
                    </div>

                    <div class="mb-3">
                        <label for="totalImport" class="form-label">Total Import:</label>
                        <input type="text" class="form-control" id="totalImport" name="totalImport"
                               value="${order.totalImport}" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="totalSell" class="form-label">Total Sell:</label>
                        <input type="text" class="form-control" id="totalSell" name="totalSell"
                               value="${order.totalSell}" readonly>
                    </div>

                    <div class="mb-3">
                        <label for="statusId" class="form-label">Status:</label>
                        <select class="form-select" id="statusId" name="statusId" required>
                            <c:forEach var="status" items="${statuses}">
                                <option value="${status.statusId}" ${order.statusId == status.statusId ? 'selected' : ''}>
                                    ${status.statusName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="shipperId" class="form-label">Shipper:</label>
                        <select class="form-select" id="shipperId" name="shipperId">
                            <option value="0" ${order.shipperId == null || order.shipperId == 0 ? 'selected' : ''}>-- Select Shipper --</option>
                            <c:forEach var="shipper" items="${shippers}">
                                <option value="${shipper.userid}" ${order.shipperId != null && order.shipperId == shipper.userid ? 'selected' : ''}>
                                    ${shipper.fullname} (${shipper.username})
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="d-flex justify-content-end">
                        <button type="submit" class="btn btn-primary me-2">Save Changes</button>
                        <a href="${pageContext.request.contextPath}/orderDetail?orderId=${order.orderId}" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
        <!-- Edit Order Form End -->

        <!-- Footer Start -->
        <div class="container-fluid pt-4 px-4">
            <div class="bg-light rounded-top p-4">
                <div class="row">
                    <div class="col-12 col-sm-6 text-center text-sm-start">
                        &copy; <a href="#">Your Site Name</a>, All Rights Reserved. 
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
</body>
</html>
