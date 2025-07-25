<%-- 
    Document   : rawflower2
    Created on : Jun 12, 2025, 9:36:24 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>DASHMIN - Bootstrap Admin Template</title>
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

        <style>
            a.change-color-qvm:active {
                color: #007bff;
            }

            /* CSS for search form */
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

            .btn {
                padding: 6px 12px;
                border: none;
                border-radius: 4px;
                color: white;
                font-size: 14px;
                cursor: pointer;
                margin-right: 5px;
                transition: background-color 0.2s ease;
            }

            .btn-secondary {
                background-color: #6c757d;
            }

            .btn-secondary:hover {
                background-color: #5a6268;
            }

            .btn-delete {
                background-color: #e74c3c;
            }

            .btn-delete:hover {
                background-color: #c0392b;
            }

            .btn-edit {
                background-color: #3498db;
            }

            .btn-edit:hover {
                background-color: #2980b9;
            }

            .fixed-container {
                /*                width: 1400px;
                                height: 720px;
                                min-width: 1400px;
                                max-width: 1400px;
                                min-height: 720px;
                                max-height: 720px;
                                overflow: auto; */
                display: flex;
                flex-direction: column;
            }

            /* Popup lỗi */
            .error-popup {
                display: none;
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                z-index: 1050;
                max-width: 500px;
                width: 90%;
            }

            .error-popup .error-header {
                font-weight: bold;
                margin-bottom: 10px;
                color: #dc3545;
            }

            .error-popup .error-body {
                color: #333;
                margin-bottom: 15px;
            }

            .error-popup .btn-close {
                position: absolute;
                top: 10px;
                right: 10px;
                font-size: 1.5rem;
                border: none;
                background: none;
                cursor: pointer;
            }

            .error-popup .btn-close:hover {
                color: #c0392b;
            }

            /* CSS for thumbnail images */
            .thumbnail-img {
                width: 60px;
                height: 60px;
                object-fit: cover;
                display: block;
                margin: 0 auto 0 2px; /* Dịch sang trái 10px */
                vertical-align: middle;
            }

            th:nth-child(2), /* Cột Image trong thead */
            td:nth-child(2) { /* Cột Image trong tbody */
                text-align: left; /* Căn trái nội dung cột */
                padding: 8px;
                vertical-align: middle;
            }
            .fixed-container {
                display: flex;
                flex-direction: column;
            }

            .table {
                width: 100%;
                table-layout: auto;
            }

            @media (max-width: 1400px) {
                .fixed-container {
                    padding: 0 10px; /* Thêm padding để tránh tràn trên màn hình nhỏ */
                }
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
                        <a href="${pageContext.request.contextPath}/ViewUserList" class="nav-item nav-link"><i class="fa fa-table me-2"></i>User</a>
                        <a href="${pageContext.request.contextPath}/viewBouquet" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Bouquet</a>
                        <a href="${pageContext.request.contextPath}/DashMin/chart.jsp" class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
                        <a href="${pageContext.request.contextPath}/orderManagement" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Order</a>
                        <a href="${pageContext.request.contextPath}/discount" 
                           class="nav-item nav-link">
                            <i class="fa fa-percentage me-2"></i>Discount
                        </a>
                        <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="nav-item nav-link active"><i class="fa fa-table me-2"></i>FlowerType</a>
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
                                        <img class="rounded-circle" src="${pageContext.request.contextPath}/DashMin/img/user.jpg" alt="" style="width: 40px; height: 40px;">
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
                                <img class="rounded-circle me-lg-2" src="${pageContext.request.contextPath}/DashMin/img/user.jpg" alt="" style="width: 40px; height: 40px;">
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

                <!-- Flower Type Management Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded h-100 p-4 fixed-container">
                        <!-- Header with title and Add Flower Type button -->
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h6 class="mb-0">Flower Type List</h6>
                            <a href="${pageContext.request.contextPath}/addRawFlower" class="btn btn-primary">Add New Flower Type</a>
                        </div>
                        <form action="${pageContext.request.contextPath}/DashMin/rawflower2" method="get" class="search-form">
                            <!-- Search form -->
                            <div style="display:flex; align-items:center;">
                                <select name="activeFilter" style="padding:0.25rem 0.5rem; border:1px solid #ccc; border-radius:0.5rem; margin-right:0.5rem;">
                                    <option value="" ${empty param.activeFilter ? 'selected' : ''}>All</option>
                                    <option value="true" ${param.activeFilter == 'true' ? 'selected' : ''}>Yes</option>
                                    <option value="false" ${param.activeFilter == 'false' ? 'selected' : ''}>No</option>
                                </select>
                                <input type="text" name="flowerName" placeholder="Tìm kiếm loại hoa" value="${fn:escapeXml(param.flowerName)}" style="width:200px; padding:0.25rem 0.5rem; border:1px solid #ccc; border-radius:0.5rem; margin-right:0.5rem;" />
                                <button type="submit" style="padding:0.25rem 0.75rem; border:none; border-radius:0.5rem; background-color:#007bff; color:#fff; cursor:pointer;">Search</button>
                                <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="btn btn-secondary" style="margin-left: 8px;">Clear</a>
                            </div>
                        </form>

                        <!-- Thông báo khi không tìm thấy -->
                        <c:if test="${empty sessionScope.listFT}">
                            <div class="alert alert-info mt-3">No flower types found.</div>
                        </c:if>

                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">
                                        <a href="?page=${currentPage}&sortField=flowerId&sortDir=${sortField eq 'flowerId' and sortDir eq 'asc' ? 'desc' : 'asc'}&flowerName=${fn:escapeXml(param.flowerName)}&activeFilter=${fn:escapeXml(param.activeFilter)}">
                                            ID
                                            <c:if test="${sortField eq 'flowerId'}">
                                                <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                            </c:if>
                                        </a>
                                    </th>
                                    <th scope="col">Image</th>
                                    <th scope="col">
                                        <a href="?page=${currentPage}&sortField=flowerName&sortDir=${sortField eq 'flowerName' and sortDir eq 'asc' ? 'desc' : 'asc'}&flowerName=${fn:escapeXml(param.flowerName)}&activeFilter=${fn:escapeXml(param.activeFilter)}">
                                            Flower Name
                                            <c:if test="${sortField eq 'flowerName'}">
                                                <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                            </c:if>
                                        </a>
                                    </th>
                                    <th scope="col">Active</th>
                                    <th colspan="2">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="flowerType" items="${sessionScope.listFT}" varStatus="status">
                                    <tr>
                                        <td>${flowerType.flowerId}</td>
                                        <td>
                                            <img src="${pageContext.request.contextPath}/upload/FlowerIMG/${fn:escapeXml(flowerType.image)}" 
                                                 alt="${fn:escapeXml(flowerType.flowerName)}" 
                                                 class="thumbnail-img"
                                                 onerror="this.src='${pageContext.request.contextPath}/DashMin/img/no-image.jpg'" />
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/rawFlowerDetails?flower_id=${flowerType.flowerId}" class="change-color-qvm">
                                                ${fn:escapeXml(flowerType.flowerName)}
                                            </a>
                                        </td>
                                        <td>${flowerType.active ? 'Yes' : 'No'}</td>
                                        <td>
                                            <button type="button"
                                                    class="btn btn-delete"
                                                    onclick="if (confirm('Do you want to delete?'))
                                                                location.href = '${pageContext.request.contextPath}/hideRawFlower?flower_id=${flowerType.flowerId}';">
                                                Hide
                                            </button>
                                            <button type="button"
                                                    class="btn btn-edit"
                                                    onclick="location.href = '${pageContext.request.contextPath}/update_flower?flower_id=${flowerType.flowerId}';">
                                                Edit
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <c:if test="${totalPages > 1}">
                            <nav>
                                <ul class="pagination">
                                    <!-- Previous -->
                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                        <a class="page-link"
                                           href="?page=${currentPage - 1}&sortField=${sortField}&sortDir=${sortDir}&flowerName=${fn:escapeXml(param.flowerName)}&activeFilter=${fn:escapeXml(param.activeFilter)}">
                                            Previous
                                        </a>
                                    </li>

                                    <!-- First Page -->
                                    <li class="page-item ${currentPage == 1 ? 'active' : ''}">
                                        <a class="page-link"
                                           href="?page=1&sortField=${sortField}&sortDir=${sortDir}&flowerName=${fn:escapeXml(param.flowerName)}&activeFilter=${fn:escapeXml(param.activeFilter)}">
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
                                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                <a class="page-link"
                                                   href="?page=${i}&sortField=${sortField}&sortDir=${sortDir}&flowerName=${fn:escapeXml(param.flowerName)}&activeFilter=${fn:escapeXml(param.activeFilter)}">
                                                    ${i}
                                                </a>
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
                                               href="?page=${totalPages}&sortField=${sortField}&sortDir=${sortDir}&flowerName=${fn:escapeXml(param.flowerName)}&activeFilter=${fn:escapeXml(param.activeFilter)}">
                                                ${totalPages}
                                            </a>
                                        </li>
                                    </c:if>

                                    <!-- Next -->
                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                        <a class="page-link"
                                           href="?page=${currentPage + 1}&sortField=${sortField}&sortDir=${sortDir}&flowerName=${fn:escapeXml(param.flowerName)}&activeFilter=${fn:escapeXml(param.activeFilter)}">
                                            Next
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>
                    </div>
                </div>
                <c:if test="${not empty sessionScope.message}">
                    <div class="alert alert-success">${fn:escapeXml(sessionScope.message)}</div>
                    <c:remove var="message" scope="session"/>
                </c:if>
                <c:if test="${not empty sessionScope.error}">
                    <div class="alert alert-danger">${fn:escapeXml(sessionScope.error)}</div>
                    <c:remove var="error" scope="session"/>
                </c:if>                       
                <!-- Flower Type Management End -->

                <!-- Footer Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded-top p-4">
                        <div class="row">
                            <div class="col-12 col-sm-6 text-center text-sm-start">
                                © <a href="#">La Fioreria</a>, All Right Reserved. 
                            </div>
                            <div class="col-12 col-sm-6 text-center text-sm-end">
                                Designed By <a href="https://htmlcodex.com">La Fioreria</a>
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