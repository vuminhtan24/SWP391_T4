<%--
    Document   : orderDetail
    Created on : Jun 15, 2025, 12:24:18 AM
    Author     : VU MINH TAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Order Details - DASHMIN</title>
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
                                    <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Elements</a>
                                    <div class="dropdown-menu bg-transparent border-0">
                                        <a href="${pageContext.request.contextPath}/DashMin/button.jsp" class="dropdown-item active">Buttons</a>
                                        <a href="${pageContext.request.contextPath}/DashMin/typography.jsp" class="dropdown-item">Typography</a>
                                        <a href="${pageContext.request.contextPath}/DashMin/element.jsp" class="dropdown-item">Other Elements</a>
                                    </div>
                                </div>
                                <a href="${pageContext.request.contextPath}/DashMin/widget.jsp" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Widgets</a>
                                <a href="${pageContext.request.contextPath}/DashMin/form.jsp" class="nav-item nav-link"><i class="fa fa-keyboard me-2"></i>Forms</a>
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

        <!-- Order Detail Start -->
        <div class="container-fluid pt-4 px-4">
            <div class="bg-light rounded p-4">
                <div class="d-flex align-items-center justify-content-between mb-4">
                    <h6 class="mb-0">Order Details</h6>
                    <a href="${pageContext.request.contextPath}/orderManagement">Back to Order Management</a>
                </div>

                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success" role="alert">
                        ${successMessage}
                    </div>
                </c:if>
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger" role="alert">
                        ${errorMessage}
                    </div>
                </c:if>
                <c:if test="${not empty sessionScope.RequestSent}">
                    <div class="alert alert-success" role="alert">
                        ${sessionScope.RequestSent}
                    </div>
                    <c:remove var="RequestSent" scope="session" />
                </c:if>
                <c:if test="${order != null}">
                    <div class="row">
                        <div class="col-md-6">
                            <p><strong>Order ID:</strong> ${order.orderId}</p>
                            <p><strong>Order Date:</strong> ${order.orderDate}</p>
                            <p><strong>Customer ID:</strong> ${order.customerId}</p>
                            <p><strong>Customer Name:</strong> ${order.customerName}</p>
                            <p><strong>Order Type:</strong> ${order.getType()}</p>
                        </div>
                        <div class="col-md-6">
                            <p><strong>Total Import: </strong><fmt:formatNumber value="${order.totalImport}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫</p>
                            <p><strong>Total Sell: </strong><fmt:formatNumber value="${order.totalSell}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫</p>
                            <p><strong>Status:</strong> ${order.statusName}</p>
                            <p><strong>Shipper ID:</strong> ${order.shipperId != null ? order.shipperId : "Not Assigned"}</p>
                            <p><strong>Shipper Name:</strong> ${order.shipperName != null ? order.shipperName : "Not Assigned"}</p>
                        </div>
                    </div>
                    <!-- Delivery Confirmation Image -->
                    <c:if test="${order.statusName == 'Delivered' && not empty order.deliveryProofImage}">
                        <hr class="my-4">
                        <h6 class="mb-3">Delivery Confirmation Image:</h6>
                        <div class="text-center">
                            <img
                                src="${pageContext.request.contextPath}${order.deliveryProofImage}"
                                alt="Bouquet Image"
                                class="img-fluid bouquet-img mb-2"
                                style="width: 300px; height: 300px; object-fit: cover; border-radius: 8px;" />
                        </div>
                    </c:if>
                    <c:if test="${order.statusName == 'Delivered' && empty order.deliveryProofImage}">
                        <hr class="my-4">
                        <h6 class="mb-3">Delivery Confirmation Image:</h6>
                        <p>No delivery proof image available.</p>
                    </c:if>
                    <!-- Reject Delivery Image -->
                    <c:if test="${order.statusName == 'Cancelled' && not empty order.rejectImage}">
                        <hr class="my-4">
                        <h6 class="mb-3">Rejected Delivery Image:</h6>
                        <div class="text-center">
                            <img
                                src="${pageContext.request.contextPath}${order.rejectImage}"
                                alt="Reject Image"
                                class="img-fluid bouquet-img mb-2"
                                style="width: 300px; height: 300px; object-fit: cover; border-radius: 8px;" />
                        </div>
                    </c:if>
                    <c:if test="${order.statusName == 'Cancelled' && empty order.rejectImage}">
                        <hr class="my-4">
                        <h6 class="mb-3">Rejected Delivery Image:</h6>
                        <p>No reject image available.</p>
                    </c:if>
                    <c:if test="${order.statusName == 'Cancelled' && not empty order.rejectReason}">
                        <hr class="my-4">
                        <h6 class="mb-3">Reason for Rejection:</h6>
                        <p>${order.rejectReason}</p>
                    </c:if>

                    <hr class="my-4">
                    <h6 class="mb-3">Purchased Products:</h6>
                    <c:if test="${empty orderItems}">
                        <p>No products found in this order.</p>
                    </c:if>
                    <c:if test="${not empty orderItems}">
                        <div class="table-responsive">
                            <table class="table text-start align-middle table-bordered table-hover mb-0">
                                <thead>
                                    <tr class="text-dark">
                                        <th scope="col">#</th>
                                        <th scope="col">Image</th>
                                        <th scope="col">Product Name</th>
                                        <th scope="col">Quantity</th>
                                        <th scope="col">Expense per Unit</th>
                                        <th scope="col">Total Expense</th>
                                        <th scope="col">Sell Price</th>
                                        <th scope="col">Total Sell Price</th>
                                        <th scope="col">Status</th>
                                        <th scope="col">Make Bouquet</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${orderItems}" varStatus="loop">
                                        <tr>
                                            <td>${loop.index + 1}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty item.bouquetImage}">
                                                        <img src="${pageContext.request.contextPath}/upload/BouquetIMG/${item.bouquetImage}" 
                                                             alt="${item.bouquetName}" style="width: 50px; height: 50px; object-fit: cover;">
                                                    </c:when>
                                                    <c:otherwise>
                                                        [Image of No image]
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${item.bouquetName}</td>
                                            <td>${item.quantity}</td>
                                            <td><fmt:formatNumber value="${item.unitPrice}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫</td>

                                            <td>
                                                <fmt:parseNumber var="qty" value="${item.quantity}" integerOnly="true" />
                                                <fmt:parseNumber var="price" value="${item.unitPrice}" type="number" />
                                                <fmt:formatNumber value="${qty * price}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫</td>
                                            </td>
                                            <td>
                                                <fmt:formatNumber value="${item.getSellPrice()}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫</td>
                                            </td>
                                            <td>
                                                <fmt:parseNumber var="qty" value="${item.quantity}" integerOnly="true" />
                                                <fmt:parseNumber var="sellPrice" value="${item.getSellPrice()}" type="number" />
                                                <fmt:formatNumber value="${qty * sellPrice}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫</td>
                                            </td>
                                            <td>${item.getStatus()}</td>
                                            <c:choose>
                                                <c:when test="${sessionScope.currentAcc.getRole() != 2}">
                                                    <td><button type="button"
                                                                class="btn btn-edit"
                                                                onclick="location.href = '${pageContext.request.contextPath}/makeBouquet?BouquetId=${item.getBouquetId()}&OrderId=${item.getOrderId()}&OrderItemID=${item.getOrderDetailId()}';"
                                                                disabled>
                                                            Not Allowed
                                                        </button></td>
                                                    </c:when>     
                                                    <c:otherwise>
                                                    <td><button type="button"
                                                                class="btn btn-edit"
                                                                onclick="location.href = '${pageContext.request.contextPath}/makeBouquet?BouquetId=${item.getBouquetId()}&OrderId=${item.getOrderId()}&OrderItemID=${item.getOrderDetailId()}&orderType=${order.getType()}';">
                                                            Make Bouquet
                                                        </button></td>
                                                    </c:otherwise>    
                                                </c:choose>             
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                    </c:if>
                    <div class="mt-4 text-end">
                        <a href="${pageContext.request.contextPath}/orderDetail?orderId=${order.orderId}&action=edit" class="btn btn-primary me-2">Edit Order</a>
                        <c:choose>
                            <c:when test="${sessionScope.currentAcc.getRole() != 2}">
                                <a href="${pageContext.request.contextPath}/orderManagement" class="btn btn-secondary">Back</a>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/saleManagerDashboard" class="btn btn-secondary">Back</a>        
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
                <c:if test="${order == null}">
                    <p>Unable to load order details.</p>
                </c:if>
            </div>
        </div>
        <!-- Order Detail End -->

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
