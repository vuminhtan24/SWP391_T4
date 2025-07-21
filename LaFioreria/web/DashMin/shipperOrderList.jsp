<%--
    Document   : shipperOrderList
    Created on : Jun 17, 2025
    Author     : Vu Minh Tan
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Shipper Orders - DASHMIN</title>
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
        <style>
            .word-wrap-td {
                white-space: normal;
                word-wrap: break-word; /* For older browsers */
                overflow-wrap: break-word; /* Standard property */
            }
            .no-wrap-td {
                white-space: nowrap;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid position-relative bg-white d-flex p-0">
            <div class="sidebar pe-4 pb-3">
                <nav class="navbar bg-light navbar-light">
                    <a href="${pageContext.request.contextPath}/shipperDashboard" class="navbar-brand mx-4 mb-3">
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
                    <div class="bg-light text-center rounded p-4">
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <h6 class="mb-0">My Orders</h6>
                        </div>

                        <%-- Display success/error message from session --%>
                        <%
                            String message = (String) session.getAttribute("message");
                            String errorMessage = (String) session.getAttribute("errorMessage");
                            if (message != null) {
                                session.removeAttribute("message");
                            }
                            if (errorMessage != null) {
                                session.removeAttribute("errorMessage");
                            }
                        %>
                        <c:if test="${not empty message}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                ${message}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                ${errorMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>

                        <div class="table-responsive">
                            <table class="table text-start align-middle table-bordered table-hover mb-0">
                                <thead>
                                    <tr class="text-dark">
                                        <th scope="col">Order ID</th>
                                        <th scope="col">Order Date</th>
                                        <th scope="col">Customer Name</th>
                                        <th scope="col">Customer Phone</th>
                                        <th scope="col">Customer Address</th>
                                        <th scope="col">Total Amount</th>
                                        <th scope="col">Status</th>
                                        <th scope="col">Payment Method</th> <%-- THÊM CỘT MỚI Ở ĐÂY --%>
                                        <th scope="col">Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${empty orders}">
                                        <tr>
                                            <td colspan="9">No orders found.</td> <%-- Cập nhật colspan lên 9 --%>
                                        </tr>
                                    </c:if>
                                    <c:forEach var="order" items="${orders}">
                                        <tr>
                                            <td>${order.orderId}</td>
                                            <td class="no-wrap-td">${order.orderDate}</td>
                                            <td>${order.customerName}</td>
                                            <td>${order.customerPhone}</td>
                                            <td class="word-wrap-td">${order.customerAddress}</td>
                                            <td><fmt:formatNumber value="${order.totalSell}" type="number" minFractionDigits="0" maxFractionDigits="2" /> VNĐ</td>
                                            <td>
                                                ${order.statusName}
                                            </td>
                                            <td><c:choose>
                                                    <c:when test="${order.paymentMethod == 'vietqr'}">Banking</c:when>
                                                    <c:when test="${order.paymentMethod == 'cod'}">COD</c:when>
                                                    <c:otherwise>Null</c:otherwise>
                                                </c:choose></td> <%-- HIỂN THỊ DỮ LIỆU CỘT MỚI Ở ĐÂY --%>
                                            <td>
                                                <a class="btn btn-sm btn-primary"
                                                   href="${pageContext.request.contextPath}/shipperDashboard?action=viewDetail&orderId=${order.orderId}">Details</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <c:if test="${totalPages > 1}">
                                <div class="mt-4 d-flex justify-content-center">
                                    <nav>
                                        <ul class="pagination">

                                            <%-- Nút trang đầu và trước --%>
                                            <c:if test="${currentPage > 1}">
                                                <li class="page-item">
                                                    <a class="page-link" href="shipperDashboard?page=1">&laquo;</a>
                                                </li>
                                                <li class="page-item">
                                                    <a class="page-link" href="shipperDashboard?page=${currentPage - 1}">&lsaquo;</a>
                                                </li>
                                            </c:if>

                                            <%-- Hiển thị max 5 trang gần currentPage --%>
                                            <c:set var="start" value="${currentPage - 2}" />
                                            <c:set var="end" value="${currentPage + 2}" />

                                            <c:if test="${start < 1}">
                                                <c:set var="end" value="${end + (1 - start)}" />
                                                <c:set var="start" value="1" />
                                            </c:if>
                                            <c:if test="${end > totalPages}">
                                                <c:set var="start" value="${start - (end - totalPages)}" />
                                                <c:set var="end" value="${totalPages}" />
                                            </c:if>
                                            <c:if test="${start < 1}">
                                                <c:set var="start" value="1" />
                                            </c:if>

                                            <c:forEach begin="${start}" end="${end}" var="i">
                                                <c:choose>
                                                    <c:when test="${i == currentPage}">
                                                        <li class="page-item active"><span class="page-link">${i}</span></li>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <li class="page-item"><a class="page-link" href="shipperDashboard?page=${i}">${i}</a></li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>

                                            <%-- Nút trang sau và cuối --%>
                                            <c:if test="${currentPage < totalPages}">
                                                <li class="page-item">
                                                    <a class="page-link" href="shipperDashboard?page=${currentPage + 1}">&rsaquo;</a>
                                                </li>
                                                <li class="page-item">
                                                    <a class="page-link" href="shipperDashboard?page=${totalPages}">&raquo;</a>
                                                </li>
                                            </c:if>

                                        </ul>
                                    </nav>
                                </div>
                            </c:if>


                        </div>

                        <%-- Pagination Section (if needed for shipper, otherwise remove) --%>
                        <%-- Assuming for shipper, you might not need full pagination controls if the list is usually short.
                             If you do, you'd need to pass currentPage, totalPages from ShipperOrderManagementServlet.
                             For now, I'm keeping the list simple without pagination. --%>
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