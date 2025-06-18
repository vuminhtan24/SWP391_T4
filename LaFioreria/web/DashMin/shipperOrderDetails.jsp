<%--
    Document   : orderDetail
    Created on : Jun 15, 2025, 12:24:18 AM
    Author     : VU MINH TAN
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Order Details - DASHMIN</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <link href="${pageContext.request.contextPath}/DashMin/img/favicon.ico" rel="icon">

        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">

        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath}/DashMin/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <link href="${pageContext.request.contextPath}/DashMin/css/bootstrap.min.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath}/DashMin/css/style.css" rel="stylesheet">
    </head>
    <body>
        <div class="container-fluid position-relative bg-white d-flex p-0">
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
                            <%-- Assuming currentAcc is available in session --%>
                            <c:set var="user" value="${sessionScope.currentAcc}" />
                            <h6 class="mb-0">${user.fullname}</h6>
                            <span>Shipper</span> <%-- Hardcode role as Shipper for this view --%>
                        </div>
                    </div>
                    <div class="navbar-nav w-100">
                        <a href="${pageContext.request.contextPath}/shipperDashboard" class="nav-item nav-link active"><i class="fa fa-truck me-2"></i>My Orders</a>
                        <%-- Add other shipper specific navigation links here if needed --%>
                        <a href="${pageContext.request.contextPath}/ZeShopper/LogoutServlet" class="nav-item nav-link"><i class="fa fa-sign-out-alt me-2"></i>Log Out</a>
                    </div>
                </nav>
            </div>
            <div class="content">
                <jsp:include page="/DashMin/navbar.jsp"/>
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded p-4">
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <h6 class="mb-0">Order Details</h6>
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

                        <c:if test="${order != null}">
                            <div class="row">
                                <div class="col-md-6">
                                    <p><strong>Order ID:</strong> ${order.orderId}</p>
                                    <p><strong>Order Date:</strong> ${order.orderDate}</p>
                                    <p><strong>Customer ID:</strong> ${order.customerId}</p>
                                    <p><strong>Customer Name:</strong> ${order.customerName}</p>
                                </div>
                                <div class="col-md-6">
                                    <p><strong>Total Amount:</strong> ${order.totalAmount}</p>
                                    <p><strong>Status:</strong> ${order.statusName}</p>
                                    <p><strong>Shipper ID:</strong> ${order.shipperId != null ? order.shipperId : "Not Assigned"}</p>
                                    <p><strong>Shipper Name:</strong> ${order.shipperName != null ? order.shipperName : "Not Assigned"}</p>
                                </div>
                            </div>
                            
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
                                                <th scope="col">Unit Price</th>
                                                <th scope="col">Subtotal</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="item" items="${orderItems}" varStatus="loop">
                                                <tr>
                                                    <td>${loop.index + 1}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty item.bouquetImage}">
                                                                <img src="${item.bouquetImage}" 
                                                                     alt="${item.bouquetName}" style="width: 50px; height: 50px; object-fit: cover;">
                                                            </c:when>
                                                            <c:otherwise>
                                                                [Image of No image]
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>${item.bouquetName}</td>
                                                    <td>${item.quantity}</td>
                                                    <td>${item.unitPrice}</td>
                                                    <td>
                                                        <fmt:parseNumber var="qty" value="${item.quantity}" integerOnly="true" />
                                                        <fmt:parseNumber var="price" value="${item.unitPrice}" type="number" />
                                                        <fmt:formatNumber value="${qty * price}" type="number" maxFractionDigits="2" />
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:if>
                            
                            <div class="mt-4 text-end">                                
                                <a href="${pageContext.request.contextPath}/shipperDashboard" class="btn btn-secondary ms-2">Back</a>
                            </div>

                        </c:if>
                        <c:if test="${order == null}">
                            <p>Unable to load order details.</p>
                        </c:if>
                    </div>
                </div>
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
                </div>
            <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
        </div>

        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/DashMin/lib/chart/chart.min.js"></script>
        <script src="${pageContext.request.contextPath}/DashMin/lib/easing/easing.min.js"></script>
        <script src="${pageContext.request.contextPath}/DashMin/lib/waypoints/waypoints.min.js"></script>
        <script src="${pageContext.request.contextPath}/DashMin/lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/js/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/js/moment-timezone.min.js"></script>
        <script src="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

        <script src="${pageContext.request.contextPath}/DashMin/js/main.js"></script>
    </body>
</html>