<%-- 
    Document   : admin-feedback
    Created on : Jul 10, 2025, 03:32 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, model.Feedback, model.FeedbackImg, model.Bouquet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="en_US" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>DASHMIN - Feedback Management</title>
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
            .table-responsive {
                margin-top: 20px;
            }
            .table {
                border-collapse: collapse; /* Đảm bảo không có khoảng cách giữa các ô */
            }
            .table thead th {
                background-color: #007bff;
                color: #ffffff;
                font-weight: bold;
                text-align: center;
                padding: 12px;
                opacity: 1 !important;
                box-sizing: border-box; /* Đảm bảo kích thước tính toán nhất quán */
                vertical-align: middle; /* Căn giữa theo chiều dọc */
            }
            .table tbody td {
                padding: 12px; /* Đồng bộ padding với th */
                text-align: center; /* Đồng bộ căn giữa với th */
                box-sizing: border-box; /* Đảm bảo kích thước tính toán nhất quán */
                vertical-align: middle; /* Căn giữa theo chiều dọc */
            }
            .table tbody tr:hover {
                background-color: #f8f9fa;
            }
            .action-btn {
                margin-right: 5px;
            }
            .feedback-images img {
                max-width: 50px;
                max-height: 50px;
                margin-right: 5px;
                border-radius: 4px;
            }
            .star-rating {
                color: #ffd700;
                font-size: 16px;
            }
            .search-form {
                display: flex;
                justify-content: flex-end;
                align-items: center;
                gap: 8px;
                margin-top: 20px;
                margin-right: 30px;
                padding: 0;
            }
            .search-form input[type="text"] {
                padding: 8px 12px;
                border: 1px solid #ccc;
                border-radius: 20px;
                outline: none;
                transition: border-color .2s;
            }
            .search-form input[type="text"]:focus {
                border-color: #007bff;
            }
            .search-form select {
                padding: 8px 12px;
                border: 1px solid #ccc;
                border-radius: 20px;
                outline: none;
                transition: border-color .2s;
            }
            .search-form select:focus {
                border-color: #007bff;
            }
            .search-form button {
                padding: 8px 16px;
                background-color: #007bff;
                color: #fff;
                border: none;
                border-radius: 20px;
                cursor: pointer;
                transition: background-color .2s;
            }
            .search-form button:hover {
                background-color: #0056b3;
            }
            th.sortable {
                cursor: pointer;
            }
            th.sortable.asc::after {
                content: " ▲";
                color: #ffffff;
            }
            th.sortable.desc::after {
                content: " ▼";
                color: #ffffff;
            }
            .table thead th a {
                color: white !important;
                text-decoration: none;
            }
            .table thead th a:hover {
                color: #dcdcdc !important;
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
                    <a href="${pageContext.request.contextPath}/DashMin/admin" class="navbar-brand mx-4 mb-3">
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
                        <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="nav-item nav-link"><i class="fa fa-table me-2"></i>FlowerType</a>
                        <a href="${pageContext.request.contextPath}/category" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Category</a>
                        <a href="${pageContext.request.contextPath}/orderManagement" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Order</a>
                        <a href="${pageContext.request.contextPath}/discount" 
                                   class="nav-item nav-link">
                                    <i class="fa fa-percentage me-2"></i>Discount
                                </a>
                        <a href="${pageContext.request.contextPath}/adminFeedback" class="nav-item nav-link active"><i class="fa fa-table me-2"></i>Feedback</a>
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
                <nav class="navbar navbar-expand bg-light navbar-light sticky-top px-4 py-0">
                    <a href="${pageContext.request.contextPath}/DashMin/admin" class="navbar-brand d-flex d-lg-none me-4">
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
                                <a href="#" class="dropdown-item text-center">See all messages</a>
                            </div>
                        </div>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                <i class="fa fa-bell me-lg-2"></i>
                                <span class="d-none d-lg-inline-flex">Notification</span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
                                <a href="#" class="dropdown-item">
                                    <h6 class="fw-normal mb-0">Profile updated</h6>
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
                </nav>
                <!-- Navbar End -->

                <!-- Feedback Management Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="row g-4">
                        <div class="col-12">
                            <div class="bg-light rounded h-100 p-4">
                                <h4 class="mb-4">Feedback Management</h4>
                                <c:if test="${not empty message}">
                                    <div class="alert alert-success">${message}</div>
                                </c:if>
                                <form action="${pageContext.request.contextPath}/adminFeedback" method="get" class="search-form">
                                    <div style="display:flex; align-items:center;">
                                        <label for="bouquetId">Bouquet:</label>
                                        <select name="bouquetId" id="bouquetId" onchange="this.form.submit()"
                                                style="width:auto; min-width:max-content; padding:0.25rem 0.5rem; border:1px solid #ccc; border-radius:0.5rem; background-color:#fff;">
                                            <option value="" ${empty bouquetId ? 'selected' : ''}>-- All --</option>
                                            <c:forEach var="bouquet" items="${bouquetList}">
                                                <option value="${bouquet.bouquetId}"
                                                        ${bouquetId eq bouquet.bouquetId ? 'selected' : ''}>
                                                    ${bouquet.bouquetName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div style="display:flex; align-items:center; margin-left:50px;">
                                        <label for="rating">Rating:</label>
                                        <select name="rating" id="rating" onchange="this.form.submit()"
                                                style="width:auto; min-width:max-content; padding:0.25rem 0.5rem; border:1px solid #ccc; border-radius:0.5rem; background-color:#fff;">
                                            <option value="" ${empty rating ? 'selected' : ''}>-- All --</option>
                                            <c:forEach var="i" begin="1" end="5">
                                                <option value="${i}" ${rating eq i ? 'selected' : ''}>${i} Star(s)</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <input type="text" name="feedbackSearch" placeholder="Search feedback" value="${feedbackSearch}"
                                           style="width:200px; padding:0.25rem 0.5rem; border:1px solid #ccc; border-radius:0.5rem; margin-right:0.5rem;" />
                                    <button type="submit" style="padding:0.25rem 0.75rem; border:none; border-radius:0.5rem; background-color:#007bff; color:#fff; cursor:pointer;">
                                        Search
                                    </button>
                                </form>
                                <div class="table-responsive">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th class="sortable" data-type="number">
                                                    <a href="?page=${currentPage}&sortField=feedbackId&sortDir=${sortField eq 'feedbackId' and sortDir eq 'asc' ? 'desc' : 'asc'}&feedbackSearch=${feedbackSearch}&bouquetId=${bouquetId}&rating=${rating}">
                                                        ID
                                                        <c:if test="${sortField eq 'feedbackId'}">
                                                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                                        </c:if>
                                                    </a>
                                                </th>
                                                <th class="sortable" data-type="string">
                                                    <a href="?page=${currentPage}&sortField=bouquetName&sortDir=${sortField eq 'bouquetName' and sortDir eq 'asc' ? 'desc' : 'asc'}&feedbackSearch=${feedbackSearch}&bouquetId=${bouquetId}&rating=${rating}">
                                                        Bouquet Name
                                                        <c:if test="${sortField eq 'bouquetName'}">
                                                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                                        </c:if>
                                                    </a>
                                                </th>
                                                <th class="sortable" data-type="string">
                                                    <a href="?page=${currentPage}&sortField=customerName&sortDir=${sortField eq 'customerName' and sortDir eq 'asc' ? 'desc' : 'asc'}&feedbackSearch=${feedbackSearch}&bouquetId=${bouquetId}&rating=${rating}">
                                                        Customer
                                                        <c:if test="${sortField eq 'customerName'}">
                                                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                                        </c:if>
                                                    </a>
                                                </th>
                                                <th class="sortable" data-type="number">
                                                    <a href="?page=${currentPage}&sortField=rating&sortDir=${sortField eq 'rating' and sortDir eq 'asc' ? 'desc' : 'asc'}&feedbackSearch=${feedbackSearch}&bouquetId=${bouquetId}&rating=${rating}">
                                                        Rating
                                                        <c:if test="${sortField eq 'rating'}">
                                                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                                        </c:if>
                                                    </a>
                                                </th>
                                                <th class="sortable" data-type="string">
                                                    <a href="?page=${currentPage}&sortField=comment&sortDir=${sortField eq 'comment' and sortDir eq 'asc' ? 'desc' : 'asc'}&feedbackSearch=${feedbackSearch}&bouquetId=${bouquetId}&rating=${rating}">
                                                        Comment (First 10 words)
                                                        <c:if test="${sortField eq 'comment'}">
                                                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                                        </c:if>
                                                    </a>
                                                </th>
                                                <th class="sortable" data-type="date">
                                                    <a href="?page=${currentPage}&sortField=created_at&sortDir=${sortField eq 'created_at' and sortDir eq 'asc' ? 'desc' : 'asc'}&feedbackSearch=${feedbackSearch}&bouquetId=${bouquetId}&rating=${rating}">
                                                        Created Date
                                                        <c:if test="${sortField eq 'created_at'}">
                                                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                                        </c:if>
                                                    </a>
                                                </th>
                                                <th class="sortable" data-type="string">
                                                    <a href="?page=${currentPage}&sortField=status&sortDir=${sortField eq 'status' and sortDir eq 'asc' ? 'desc' : 'asc'}&feedbackSearch=${feedbackSearch}&bouquetId=${bouquetId}&rating=${rating}">
                                                        Status
                                                        <c:if test="${sortField eq 'status'}">
                                                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                                        </c:if>
                                                    </a>
                                                </th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="feedback" items="${feedbackList}" varStatus="status">
                                                <tr>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/DashMin/feedbackDetail?id=${feedback.feedbackId}">
                                                            ${feedback.feedbackId}
                                                        </a>
                                                    </td>
                                                    <td>${feedback.bouquetName}</td>
                                                    <td>${feedbackCustomerNames[feedback.feedbackId]}</td>
                                                    <td>
                                                        <c:forEach begin="1" end="${feedback.rating}">
                                                            <i class="fas fa-star star-rating"></i>
                                                        </c:forEach>
                                                    </td>
                                                    <td>
                                                        <c:set var="words" value="${fn:split(feedback.comment, ' ')}"/>
                                                        <c:forEach var="word" items="${words}" begin="0" end="9">
                                                            ${word} 
                                                        </c:forEach>
                                                        <c:if test="${fn:length(words) > 10}">...</c:if>
                                                        </td>
                                                        <td><fmt:formatDate value="${feedback.createdAtAsDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                                    <td>${feedback.status}</td>
                                                    <td>
                                                        <c:if test="${feedback.status == 'pending'}">
                                                            <a href="${pageContext.request.contextPath}/adminFeedback?action=approve&id=${feedback.feedbackId}" class="btn btn-success btn-sm action-btn">Approve</a>
                                                            <a href="${pageContext.request.contextPath}/adminFeedback?action=reject&id=${feedback.feedbackId}" class="btn btn-danger btn-sm action-btn">Reject</a>
                                                        </c:if>
                                                        <c:if test="${feedback.status != 'pending'}">
                                                            <span class="badge bg-secondary">Processed</span>
                                                        </c:if>
                                                    </td>
                                                </tr>                                                
                                            </c:forEach>
                                            <c:if test="${empty feedbackList}">
                                                <tr>
                                                    <td colspan="8" class="text-center">No feedback available.</td>
                                                </tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>
                                <c:if test="${totalPages > 1}">
                                    <div class="mt-auto">
                                        <nav>
                                            <ul class="pagination">
                                                <!-- Previous -->
                                                <c:url var="prevUrl" value="/adminFeedback">
                                                    <c:param name="page" value="${currentPage - 1}"/>
                                                    <c:param name="feedbackSearch" value="${feedbackSearch}"/>
                                                    <c:param name="bouquetId" value="${bouquetId}"/>
                                                    <c:param name="rating" value="${rating}"/>
                                                    <c:param name="sortField" value="${sortField}"/>
                                                    <c:param name="sortDir" value="${sortDir}"/>
                                                </c:url>
                                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                                    <a class="page-link" href="${prevUrl}">Previous</a>
                                                </li>

                                                <!-- First Page -->
                                                <li class="page-item ${currentPage == 1 ? 'active' : ''}">
                                                    <a class="page-link"
                                                       href="?page=1&feedbackSearch=${feedbackSearch}&bouquetId=${bouquetId}&rating=${rating}&sortField=${sortField}&sortDir=${sortDir}">
                                                        1
                                                    </a>
                                                </li>

                                                <!-- Ellipsis -->
                                                <c:if test="${currentPage > 3}">
                                                    <li class="page-item disabled"><a class="page-link" href="#">...</a></li>
                                                    </c:if>

                                                <!-- Page range (currentPage -1, currentPage, currentPage +1) -->
                                                <c:forEach var="i" begin="${currentPage - 1}" end="${currentPage + 1}">
                                                    <c:if test="${i > 1 && i < totalPages}">
                                                        <c:url var="pageUrl" value="/adminFeedback">
                                                            <c:param name="page" value="${i}"/>
                                                            <c:param name="feedbackSearch" value="${feedbackSearch}"/>
                                                            <c:param name="bouquetId" value="${bouquetId}"/>
                                                            <c:param name="rating" value="${rating}"/>
                                                            <c:param name="sortField" value="${sortField}"/>
                                                            <c:param name="sortDir" value="${sortDir}"/>
                                                        </c:url>
                                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                            <a class="page-link" href="${pageUrl}">${i}</a>
                                                        </li>
                                                    </c:if>
                                                </c:forEach>

                                                <!-- Ellipsis -->
                                                <c:if test="${currentPage < totalPages - 2}">
                                                    <li class="page-item disabled"><a class="page-link" href="#">...</a></li>
                                                    </c:if>

                                                <!-- Last Page -->
                                                <c:if test="${totalPages > 1}">
                                                    <li class="page-item ${currentPage == totalPages ? 'active' : ''}">
                                                        <a class="page-link"
                                                           href="?page=${totalPages}&feedbackSearch=${feedbackSearch}&bouquetId=${bouquetId}&rating=${rating}&sortField=${sortField}&sortDir=${sortDir}">
                                                            ${totalPages}
                                                        </a>
                                                    </li>
                                                </c:if>

                                                <!-- Next -->
                                                <c:url var="nextUrl" value="/adminFeedback">
                                                    <c:param name="page" value="${currentPage + 1}"/>
                                                    <c:param name="feedbackSearch" value="${feedbackSearch}"/>
                                                    <c:param name="bouquetId" value="${bouquetId}"/>
                                                    <c:param name="rating" value="${rating}"/>
                                                    <c:param name="sortField" value="${sortField}"/>
                                                    <c:param name="sortDir" value="${sortDir}"/>
                                                </c:url>
                                                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                                    <a class="page-link" href="${nextUrl}">Next</a>
                                                </li>
                                            </ul>
                                        </nav>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Feedback Management End -->

                <!-- Footer Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded-top p-4">
                        <div class="row">
                            <div class="col-12 col-sm-6 text-center text-sm-start">
                                © <a href="#">Your Site Name</a>, All Right Reserved. 
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