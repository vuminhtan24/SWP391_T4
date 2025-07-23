<%-- 
    Document   : WholeSaleDetails
    Created on : Jul 14, 2025, 4:12:14 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List,model.Bouquet, model.Category, model.RawFlower"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="vi_VN" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>DASHMIN - Bootstrap Admin Template</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

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
        <style>
            .container {
                margin: 20px auto 0 auto;
                padding: 24px;
                background-color: #f4f6f8;
                border-radius: 12px;
                font-family: 'Segoe UI', sans-serif;

                max-width: calc(100% - 40px);
                width: 100%;
                box-sizing: border-box;

                display: flex;
                flex-direction: column;
                justify-content: flex-start;

                height: 730px; /* ngăn scroll trang, đủ không gian */
                overflow-y: auto;                /* bảng cuộn nếu quá cao */
            }

            .heading {
                font-size: 18px;
                font-weight: 600;
                margin-bottom: 16px;
                color: #222;
            }

            /* Bảng chính */
            .styled-table {
                width: 100%;
                border-collapse: separate;
                border-spacing: 0;
                font-size: 14px;
                background-color: #f9fbfc;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 1px 3px rgba(0,0,0,0.05);
            }

            /* Phần thead */
            .styled-table thead {
                background-color: #f0f2f5;
            }

            /* Ô trong thead */
            .styled-table thead td {
                font-weight: 600;
                color: #444;
                padding: 12px 16px;
                border-bottom: 1px solid #e0e0e0;
                text-align: left;
            }

            /* Ô trong tbody */
            .styled-table tbody td {
                padding: 12px 16px;
                color: #333;
                border-bottom: 1px solid #f0f0f0;
            }

            /* Hover dòng */
            .styled-table tbody tr:hover td {
                background-color: #e9f3ff;
                transition: background-color 0.2s ease;
            }

            /* Dòng cuối không có border dưới */
            .styled-table tbody tr:last-child td {
                border-bottom: none;
            }

            /* Phần bổ sung để giữ phân trang đứng yên */
            .styled-table tbody {
                display: block;
                min-height: 300px; /* Bạn có thể điều chỉnh chiều cao này nếu muốn */
            }

            /* Đảm bảo thead và tr hoạt động đúng trong block tbody */
            .styled-table thead,
            .styled-table tbody tr {
                display: table;
                width: 100%;
                table-layout: fixed;
            }


            .filter-form {
                width: 100%;
                margin-bottom: 20px;
            }

            .filter-row {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                align-items: flex-end;
            }

            .filter-item {
                display: flex;
                flex-direction: column;
                min-width: 200px;
            }

            .filter-item label {
                font-weight: bold;
                margin-bottom: 4px;
            }

            .filter-item input,
            .filter-item select {
                padding: 6px 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            .button-row {
                margin-top: 15px;
                display: flex;
                gap: 12px;
            }

            .button-row button {
                padding: 8px 16px;
                border: none;
                background-color: #1976d2;
                color: white;
                border-radius: 4px;
                cursor: pointer;
            }

            .button-row button[type="button"] {
                background-color: #757575;
            }

            .button-row button:hover {
                opacity: 0.9;
            }

        </style>
    </head>

    <body>
        <div class="container-fluid position-relative bg-white d-flex p-0">
            <!-- Spinner Start -->
            <div id="spinner" class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
                <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
                    <span class="sr-only">Loading...</span>
                </div>
            </div>
            <!-- Spinner End -->


            <!-- Sidebar Start -->
            <div class="sidebar pe-4 pb-3">
                <nav class="navbar bg-light navbar-light">
                    <a href="${pageContext.request.contextPath}/DashMin/admin.jsp" class="navbar-brand mx-4 mb-3">
                        <h3 class="text-primary"><i class="fa fa-hashtag me-2"></i>DASHMIN</h3>
                    </a>
                    <div class="d-flex align-items-center ms-4 mb-4">
                        <div class="position-relative">
                            <img class="rounded-circle" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">
                            <div class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"></div>
                        </div>
                        <div class="ms-3">
                            <h6 class="mb-0">Jhon Doe</h6>
                            <span>Admin</span>
                        </div>
                    </div>
                    <div class="navbar-nav w-100">
                        <a href="${pageContext.request.contextPath}/DashMin/admin" class="nav-item nav-link"><i class="fa fa-tachometer-alt me-2"></i>Dashboard</a>
                        <a href="${pageContext.request.contextPath}/ViewUserList" class="nav-item nav-link"><i class="fa fa-table me-2"></i>User</a>
                        <a href="${pageContext.request.contextPath}/viewBouquet" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Bouquet</a>
                        <a href="${pageContext.request.contextPath}/DashMin/chart.jsp" class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
                        <a href="${pageContext.request.contextPath}/orderManagement" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Order</a>
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
                </nav>
            </div>
            <!-- Sidebar End -->


            <!-- Content Start -->
            <div class="content">
                <!-- Navbar Start -->
                <nav class="navbar navbar-expand bg-light navbar-light sticky-top px-4 py-0">
                    <a href="${pageContext.request.contextPath}/DashMin/admin.jsp" class="navbar-brand d-flex d-lg-none me-4">
                        <h2 class="text-primary mb-0"><i class="fa fa-hashtag"></i></h2>
                    </a>
                    <a href="#" class="sidebar-toggler flex-shrink-0">
                        <i class="fa fa-bars"></i>
                    </a>
                    <form class="d-none d-md-flex ms-4">
                        <input class="form-control border-0" type="search" placeholder="Search">
                    </form>
                    <div class="navbar-nav align-items-center ms-auto">
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                <i class="fa fa-envelope me-lg-2"></i>
                                <span class="d-none d-lg-inline-flex">Message</span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
                                <a href="#" class="dropdown-item">
                                    <div class="d-flex align-items-center">
                                        <img class="rounded-circle" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">
                                        <div class="ms-2">
                                            <h6 class="fw-normal mb-0">Jhon send you a message</h6>
                                            <small>15 minutes ago</small>
                                        </div>
                                    </div>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item">
                                    <div class="d-flex align-items-center">
                                        <img class="rounded-circle" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">
                                        <div class="ms-2">
                                            <h6 class="fw-normal mb-0">Jhon send you a message</h6>
                                            <small>15 minutes ago</small>
                                        </div>
                                    </div>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item">
                                    <div class="d-flex align-items-center">
                                        <img class="rounded-circle" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">
                                        <div class="ms-2">
                                            <h6 class="fw-normal mb-0">Jhon send you a message</h6>
                                            <small>15 minutes ago</small>
                                        </div>
                                    </div>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item text-center">See all message</a>
                            </div>
                        </div>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                <i class="fa fa-bell me-lg-2"></i>
                                <span class="d-none d-lg-inline-flex">Notificatin</span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
                                <a href="#" class="dropdown-item">
                                    <h6 class="fw-normal mb-0">Profile updated</h6>
                                    <small>15 minutes ago</small>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item">
                                    <h6 class="fw-normal mb-0">New user added</h6>
                                    <small>15 minutes ago</small>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item">
                                    <h6 class="fw-normal mb-0">Password changed</h6>
                                    <small>15 minutes ago</small>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item text-center">See all notifications</a>
                            </div>
                        </div>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                <img class="rounded-circle me-lg-2" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">
                                <span class="d-none d-lg-inline-flex">John Doe</span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
                                <a href="#" class="dropdown-item">My Profile</a>
                                <a href="#" class="dropdown-item">Settings</a>
                                <a href="${pageContext.request.contextPath}/ZeShopper/LogoutServlet" class="dropdown-item">Log Out</a>
                            </div>
                        </div>
                    </div>
                </nav>
                <!-- Navbar End -->

                <!-- List Request Start -->
                <div class="container">
                    <!-- Start General Information -->
                    <h4>General Information</h4>
                    <div class="row">
                        <!-- Cột 1: Thông tin cá nhân -->
                        <div class="col-md-6">
                            <p style="margin-bottom: 5px;"><strong>User's fullname:</strong> ${requestScope.userInfo.getFullname()}</p>
                            <p style="margin-bottom: 5px;"><strong>User's email:</strong> ${requestScope.userInfo.getEmail()}</p>
                            <p style="margin-bottom: 5px;"><strong>Phone number:</strong> ${requestScope.userInfo.getPhone()}</p>
                            <p style="margin-bottom: 5px;"><strong>Address:</strong> ${requestScope.userInfo.getAddress()}</p>
                        </div>

                        <!-- Cột 2: Thông tin đơn hàng -->
                        <c:if test="${not empty listWS}">
                            <c:set var="generalItem" value="${listWS[0]}" />
                            <div class="col-md-6">
                                <p style="margin-bottom: 5px;"><strong>Request Date:</strong> ${generalItem.getCreated_at()}</p>

                                <p style="margin-bottom: 5px;"><strong>Quoted Date:</strong>
                                    <c:choose>
                                        <c:when test="${generalItem.getQuoted_at() == null}">
                                            not quoted yet
                                        </c:when>
                                        <c:otherwise>
                                            ${generalItem.getQuoted_at()}
                                        </c:otherwise>
                                    </c:choose>
                                </p>

                                <p style="margin-bottom: 5px;"><strong>Customer Responded at:</strong>
                                    <c:choose>
                                        <c:when test="${generalItem.getResponded_at() == null}">
                                            not responded yet
                                        </c:when>
                                        <c:otherwise>
                                            ${generalItem.getResponded_at()}
                                        </c:otherwise>
                                    </c:choose>
                                </p>

                            </div>
                        </c:if>
                    </div>
                    <!-- End General Information -->

                    <c:if test="${param.sendEmail eq 'fail'}">
                        <div class="alert alert-danger" style="margin-top: 10px;">
                            Quotation sending failed. Please check your email configuration.
                        </div>
                    </c:if>


                    <h6 class="heading">List WholeSale Request</h6>

                    <form action="requestWholeSaleDetails" method="get">
                        <!-- GIỮ LẠI CÁC THAM SỐ ĐANG CÓ -->
                        <input type="hidden" name="userId" value="${fn:trim(param.userId)}" />
                        <input type="hidden" name="requestDate" value="${fn:trim(param.requestDate)}" />
                        <input type="hidden" name="status" value="${fn:trim(param.status)}" />
                        <input type="hidden" name="requestGroupId" value="${fn:trim(param.requestGroupId)}" />
                        <input type="hidden" name="sortBy" value="${param.sortBy}" />
                        <input type="hidden" name="sortOrder" value="${param.sortOrder}" />

                        <div style="display:flex; align-items:center;">
                            <label for="flowerS"
                                   style="margin-right:0.5rem; white-space:nowrap; margin-left:10px">
                                Flower:
                            </label>
                            <select name="flowerS"
                                    id="flowerS"
                                    onchange="this.form.submit()"
                                    style="
                                    width:auto;
                                    min-width:max-content;
                                    padding:0.25rem 0.5rem;
                                    border:1px solid #ccc;
                                    border-radius:0.5rem;
                                    background-color:#fff;
                                    ">
                                <!-- Option mặc định -->
                                <option value=""
                                        <c:if test="${empty param.flowerS}">
                                            selected="selected"
                                        </c:if>>
                                    -- Default --
                                </option>

                                <!-- Duyệt listFlower và hiển thị -->
                                <c:forEach var="flower" items="${listFlower}">
                                    <option value="${flower.getFlowerId()}"
                                            <c:if test="${param.flowerS eq flower.getFlowerId()}">
                                                selected="selected"
                                            </c:if>>
                                        ${flower.getFlowerName()}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>

                    <c:set var="canSendQuotation" value="true" />

                    <c:if test="${param.sendEmail eq 'true'}">
                        <c:set var="canSendQuotation" value="false" />
                    </c:if>

                    <c:forEach var="item" items="${listWS}">
                        <c:if test="${item.status ne 'QUOTED'}">
                            <c:set var="canSendQuotation" value="false" />
                        </c:if>
                        <c:if test="${item.status eq 'EMAIL'}">
                            <c:set var="canSendQuotation" value="false" />
                        </c:if>
                    </c:forEach>


                    <c:choose>
                        <c:when test="${canSendQuotation}">
                            <form action="sendQuotation" method="post" style="margin-top: 20px;">
                                <input type="hidden" name="userId" value="${fn:trim(param.userId)}" />
                                <input type="hidden" name="requestDate" value="${fn:trim(param.requestDate)}" />
                                <input type="hidden" name="requestGroupId" value="${fn:trim(param.requestGroupId)}" />
                                <input type="hidden" name="userEmail" value="${requestScope.userInfo.getEmail()}" />
                                <button type="submit" class="btn btn-success">Send Quotation</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <c:set var="showedEmailMsg" value="false" />
                            <c:set var="showedAcceptedMsg" value="false" />
                            <c:set var="showedRejectedMsg" value="false" />

                            <c:forEach var="item" items="${listWS}">
                                <c:if test="${item.status eq 'EMAILED' and not showedEmailMsg}">
                                    <div class="alert alert-success" style="margin-top: 10px;">
                                        Quotation has been successfully sent to customer!
                                    </div>
                                    <c:set var="showedEmailMsg" value="true" />
                                </c:if>

                                <c:if test="${item.status eq 'ACCEPTED' and not showedAcceptedMsg}">
                                    <div class="alert alert-success" style="margin-top: 10px;">
                                        Customer has accepted the WholeSale Quotation!
                                    </div>
                                    <c:set var="showedAcceptedMsg" value="true" />
                                </c:if>

                                <c:if test="${item.status eq 'REJECTED' and not showedRejectedMsg}">
                                    <div class="alert alert-danger" style="margin-top: 10px;">
                                        Customer has rejected the WholeSale Quotation!
                                    </div>
                                    <c:set var="showedRejectedMsg" value="true" />
                                </c:if>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>


                    <table class="styled-table">
                        <thead>
                            <tr>
                                <td>STT</td>
                                <td>Bouquet Name</td>
                                <td>Bouquet Image</td>
                                <%
                                    String newOrder = "asc";
                                    if ("quantity".equals(request.getAttribute("sortBy")) && "asc".equals(request.getAttribute("sortOrder"))) {
                                        newOrder = "desc";
                                    }
                                %>

                                <td>
                                    <c:url var="sortedUrl" value="/requestWholeSaleDetails">
                                        <c:param name="userId" value="${fn:trim(param.userId)}"/>
                                        <c:param name="requestDate" value="${fn:trim(param.requestDate)}"/>
                                        <c:param name="status" value="${fn:trim(param.status)}"/>
                                        <c:param name="sortBy" value="quantity"/>
                                        <c:param name="sortOrder" value="<%= newOrder %>"/>
                                    </c:url>
                                    <a href="${sortedUrl}">Requested Quantity</a>
                                </td>
                                <td>Note</td>
                                <td>WholeSale Expense per Unit</td>
                                <td>WholeSale Price per Unit</td>
                                <td>Total WholeSale Price</td>                                
                                <%
                                    String newStatusOrder = "asc";
                                    if ("status".equals(request.getAttribute("sortBy")) && "asc".equals(request.getAttribute("sortOrder"))) {
                                        newStatusOrder = "desc";
                                    }
                                %>

                                <td>
                                    <c:url var="sortedStatusUrl" value="/requestWholeSaleDetails">
                                        <c:param name="userId" value="${fn:trim(param.userId)}"/>
                                        <c:param name="requestDate" value="${fn:trim(param.requestDate)}"/>
                                        <c:param name="status" value="${fn:trim(param.status)}"/>
                                        <c:param name="sortBy" value="status"/>
                                        <c:param name="sortOrder" value="<%= newStatusOrder %>"/>
                                    </c:url>
                                    <a href="${sortedStatusUrl}">Status</a>
                                </td>
                                <td></td>
                            </tr>
                        </thead>  
                        <tbody>
                            <c:forEach var="item" items="${listWS}" varStatus="loop">
                                <tr>
                                    <td>${startIndex + loop.index + 1}</td>
                                    <td>
                                        <c:forEach var="bouquet" items="${listBQ}">
                                            <c:if test="${item.getBouquet_id() eq bouquet.getBouquetId()}">
                                                ${bouquet.getBouquetName()}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:set var="imageShown" value="false" />
                                        <c:forEach var="img" items="${listIMG}">
                                            <c:if test="${!imageShown and item.getBouquet_id() eq img.getbouquetId()}">
                                                <img src="${pageContext.request.contextPath}/upload/BouquetIMG/${img.getImage_url()}" alt="alt" style="width: 60px; height: 60px;"/>
                                                <c:set var="imageShown" value="true" />
                                            </c:if>
                                        </c:forEach>
                                    </td>    
                                    <td>${item.getRequested_quantity()}</td>
                                    <td>
                                        ${item.getNote()}
                                    </td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty item.getExpense() and item.getExpense() != 0}">
                                                <fmt:formatNumber value="${item.getExpense()}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫
                                            </c:when>
                                            <c:otherwise>Not Quoted yet</c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty item.getQuoted_price() and item.getQuoted_price() != 0}">
                                                <fmt:formatNumber value="${item.getQuoted_price()}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫
                                            </c:when>
                                            <c:otherwise>Not Quoted yet</c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty item.getTotal_price() and item.getTotal_price() != 0}">
                                                <fmt:formatNumber value="${item.getTotal_price()}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫
                                            </c:when>
                                            <c:otherwise>Not Quoted yet</c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>${item.getStatus()}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${item.getStatus() eq 'PENDING' or item.getStatus() eq 'QUOTED'}">
                                                <a href="${pageContext.request.contextPath}/wholeSaleQuotation?userId=${item.getUser_id()}&requestDate=${item.getCreated_at()}&requestGroupId=${item.getRequest_group_id()}&status=${item.getStatus()}&bouquetId=${item.getBouquet_id()}">
                                                    Quotation
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                Quotation Completed
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>  
                            </c:forEach>
                        </tbody>   
                    </table>

                    <!-- Hiển thị phân trang -->
                    <div class="pagination-container text-center mt-3">
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <c:choose>
                                <c:when test="${i == currentPage}">
                                    <span class="btn btn-sm btn-primary disabled">${i}</span>
                                </c:when>
                                <c:otherwise>
                                    <c:url var="pageUrl" value="/requestWholeSaleDetails">
                                        <c:param name="userId" value="${fn:trim(param.userId)}" />
                                        <c:param name="requestDate" value="${fn:trim(param.requestDate)}" />
                                        <c:param name="status" value="${fn:trim(param.status)}" />
                                        <c:param name="sortBy" value="${sortBy}" />
                                        <c:param name="sortOrder" value="${sortOrder}" />
                                        <c:param name="page" value="${i}" />
                                    </c:url>
                                    <a class="btn btn-sm btn-outline-primary" href="${pageUrl}">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </div>

                <!-- List Request End -->


                <!-- Footer Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded-top p-4">
                        <div class="row">
                            <div class="col-12 col-sm-6 text-center text-sm-start">
                                &copy; <a href="#">Your Site Name</a>, All Right Reserved. 
                            </div>
                            <div class="col-12 col-sm-6 text-center text-sm-end">
                                <!--/*** This template is free as long as you keep the footer author’s credit link/attribution link/backlink. If you'd like to use the template without the footer author’s credit link/attribution link/backlink, you can purchase the Credit Removal License from "https://htmlcodex.com/credit-removal". Thank you for your support. ***/-->
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
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
