<%-- 
    Document   : blank
    Created on : May 19, 2025, 2:34:20 PM
    Author     : ADMIN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

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
            .table th, .table td {
                vertical-align: middle;
                text-align: center;
            }

            .form-select, .form-control {
                min-width: 150px;
            }

            form#f1 table {
                margin-bottom: 0;
            }

            .btn-group a.btn {
                font-weight: 600;
                box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            }

            .btn-primary {
                background-color: #0d6efd;
                border-color: #0d6efd;
            }

            .btn-danger {
                background-color: #dc3545;
                border-color: #dc3545;
            }

            .btn-outline-primary {
                min-width: 40px;
            }

            .table th {
                background-color: #e9ecef;
                color: #495057;
            }

            .bg-light {
                background-color: #f8f9fa !important;
            }

            .pagination-container {
                text-align: center;
                padding-top: 20px;
            }

            .p-4 {
                padding: 2rem !important;
            }

            h6.mb-4 {
                font-size: 1.25rem;
                font-weight: bold;
                color: #333;
            }
        </style>

        <style>
            .btn-outline-primary.active {
                background-color: #0d6efd;
                color: white;
                border-color: #0d6efd;
            }

            .btn-outline-primary:hover {
                background-color: #0d6efd;
                color: white;
            }
        </style>

        <style>
            .btn-gradient-primary {
                background: linear-gradient(135deg, #4f93ce, #28527a);
                color: #fff;
                border: none;
                transition: 0.3s;
            }

            .btn-gradient-primary:hover {
                background: linear-gradient(135deg, #28527a, #4f93ce);
                color: #fff;
            }

            .form-label {
                font-size: 0.95rem;
            }

            @media (max-width: 767px) {
                .form-label {
                    margin-bottom: 0.25rem;
                }
            }
        </style>

        <style>
            .table thead th {
                background: #eaf4fc;
                color: #333;
                font-weight: 600;
                font-size: 0.95rem;
                vertical-align: middle;
            }

            .table td {
                vertical-align: middle;
                font-size: 0.9rem;
                word-wrap: break-word;
            }

            .btn-action {
                font-size: 0.8rem;
                padding: 4px 10px;
                border-radius: 20px;
                font-weight: 500;
                border: none;
                transition: 0.3s;
            }

            .btn-reject {
                background-color: #f44336;
                color: #fff;
            }

            .btn-reject:hover {
                background-color: #d32f2f;
            }

            .btn-delete {
                background-color: #ff9800;
                color: #fff;
            }

            .btn-delete:hover {
                background-color: #ef6c00;
            }

            .btn-view {
                background-color: #03a9f4;
                color: #fff;
            }

            .btn-view:hover {
                background-color: #0288d1;
            }

            /* Responsive fix nếu bảng rộng */
            .table-responsive {
                overflow-x: auto;
            }

            /* Shadow tổng thể */
            .table-container {
                background: #ffffff;
                border-radius: 12px;
                padding: 20px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.06);
            }

            @media (max-width: 768px) {
                .btn-action {
                    width: 100%;
                    margin-bottom: 5px;
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
                        <a href="${pageContext.request.contextPath}/ViewUserList" class="nav-item nav-link active"><i class="fa fa-table me-2"></i>User</a>
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
                                <a href="${pageContext.request.contextPath}/listRequest" class="dropdown-item">List Request</a>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}/listWholeSaleRequest" class="nav-item nav-link"><i class="fa fa-table me-2"></i>WholeSale</a>                   
                        <a href="${pageContext.request.contextPath}" class="nav-item nav-link"><i class="fa fa-table me-2"></i>La Fioreria</a>
                        <a href="${pageContext.request.contextPath}/blogmanager" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Blog Manager</a>
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
                <!-- Bọc toàn bộ bằng container -->
                <div class="container-fluid mt-4">
                    <div class="row g-4">
                        <!-- Add User Button -->
                        <div class="col-sm-12 col-xl-6">
                            <div class="bg-light rounded h-100 p-4 shadow-sm">
                                <div class="btn-group" role="group">
                                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/adduserdetail">Add new User detail</a>
                                </div>
                            </div>
                        </div>

                        <!-- Filter Form -->
                        <!-- Form Filter đẹp hơn, bố cục rõ, responsive -->
                        <div class="col-12">
                            <div class="bg-white border rounded p-4 shadow-sm">
                                <form id="f1" action="${pageContext.request.contextPath}/ViewUserList" method="post">
                                    <div class="row g-3">
                                        <!-- Role -->
                                        <div class="col-12 col-md-3">
                                            <label class="form-label fw-semibold">Role:</label>
                                            <select class="form-select" name="txtRoleList" onchange="document.getElementById('f1').submit()">
                                                <option value="0" <c:if test="${roleId == 0}">selected</c:if>>All</option>
                                                <c:forEach items="${roleList}" var="role">
                                                    <option value="${role.role_id}" <c:if test="${role.role_id == roleId}">selected</c:if>>${role.role_Name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <!-- Search name -->
                                        <div class="col-12 col-md-3">
                                            <label class="form-label fw-semibold">Search name:</label>
                                            <input type="text" name="txtSearchName" value="${keyword}" class="form-control" placeholder="Search">
                                        </div>

                                        <!-- Sort field -->
                                        <div class="col-12 col-md-2">
                                            <label class="form-label fw-semibold">Sort by:</label>
                                            <select class="form-select" name="sortField">
                                                <option value="User_ID" <c:if test="${sortField == 'User_ID'}">selected</c:if>>User ID</option>
                                                <option value="Username" <c:if test="${sortField == 'Username'}">selected</c:if>>User Name</option>
                                                <option value="Password" <c:if test="${sortField == 'Password'}">selected</c:if>>Password</option>
                                                <option value="Fullname" <c:if test="${sortField == 'Fullname'}">selected</c:if>>Full Name</option>
                                                <option value="Email" <c:if test="${sortField == 'Email'}">selected</c:if>>Email</option>
                                                <option value="Phone" <c:if test="${sortField == 'Phone'}">selected</c:if>>Phone</option>
                                                <option value="Address" <c:if test="${sortField == 'Address'}">selected</c:if>>Address</option>
                                                <option value="Role_name" <c:if test="${sortField == 'Role_name'}">selected</c:if>>Role</option>
                                                </select>
                                            </div>

                                            <!-- Sort order -->
                                            <div class="col-12 col-md-2">
                                                <label class="form-label fw-semibold">Order:</label>
                                                <select class="form-select" name="sortOrder">
                                                    <option value="asc" <c:if test="${sortOrder == 'asc'}">selected</c:if>>Ascending</option>
                                                <option value="desc" <c:if test="${sortOrder == 'desc'}">selected</c:if>>Descending</option>
                                                </select>
                                            </div>

                                            <!-- Search Button -->
                                            <div class="col-12 col-md-2 d-flex align-items-end">
                                                <button type="submit" class="btn btn-secondary">
                                                    <i class="bi bi-search"></i> Search
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>

                            <div>
                                <a href="${pageContext.request.contextPath}/viewemployeeservlet" class="btn btn-primary">
                                <i class="fas fa-users"></i> Employee Manager
                            </a>

                        </div>
                        <!-- Table View -->
                        <!-- Table View -->
                        <div class="col-12">
                            <div class="table-container">
                                <h6 class="mb-4">View User List Table</h6>
                                <div class="table-responsive">
                                    <table class="table table-bordered align-middle">
                                        <thead>
                                            <tr>
                                                <th>User ID</th>
                                                <th>User Name</th>
                                                <th>Password</th>
                                                <th>Full Name</th>
                                                <th>Email</th>
                                                <th>Phone</th>
                                                <th>Address</th>
                                                <th>Role</th>
                                                <th colspan="3">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${userManagerList}" var="user">
                                                <tr>
                                                    <td>${user.userid}</td>
                                                    <td>${user.username}</td>
                                                    <td>
                                                        <input type="password" value="${user.password}" readonly style="border: none; background: none;" />
                                                    </td>

                                                    <td>${user.fullname}</td>
                                                    <td>${user.email}</td>
                                                    <td>${user.phone}</td>
                                                    <td>${user.address}</td>
                                                    <td>${user.role}</td>
                                                    <td>
                                                        <form action="${pageContext.request.contextPath}/rejectuserlist" method="post" onsubmit="return confirm('Confirm reject this user ?');">
                                                            <input type="hidden" name="userId" value="${user.userid}" />
                                                            <button class="btn btn-warning" type="submit">Reject</button>
                                                        </form>
                                                    </td>
                                                    <td>
                                                        <form action="${pageContext.request.contextPath}/DeleteUserListServlet" method="post" onsubmit="return confirm('Confirm Delete this user ?');">
                                                            <input type="hidden" name="userId" value="${user.userid}" />
                                                            <button class="btn btn-danger" type="submit">Delete</button>
                                                        </form>
                                                    </td>
                                                    <td>
                                                        <form action="${pageContext.request.contextPath}/viewuserdetail" method="get">
                                                            <input type="hidden" name="id" value="${user.userid}" />
                                                            <button class="btn btn-primary" type="submit">View</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>


                        <!-- PAGINATION -->
                        <%
                            int currentPage = (Integer) request.getAttribute("currentPage");
                            int totalPages = (Integer) request.getAttribute("totalPages");

                            int maxDisplayPages = 5;
                            int startPage = currentPage - maxDisplayPages / 2;
                            if (startPage < 1) startPage = 1;

                            int endPage = startPage + maxDisplayPages - 1;
                            if (endPage > totalPages) {
                                endPage = totalPages;
                                startPage = endPage - maxDisplayPages + 1;
                                if (startPage < 1) startPage = 1;
                            }
                        %>

                        <div class="text-center mt-4">
                            <c:if test="${totalPages > 1}">
                                <form action="${pageContext.request.contextPath}/ViewUserList" method="post" id="paginationForm" class="d-flex flex-wrap justify-content-center gap-2">
                                    <input type="hidden" name="txtSearchName" value="${keyword}" />
                                    <input type="hidden" name="txtRoleList" value="${roleId}" />
                                    <input type="hidden" name="sortField" value="${sortField}" />
                                    <input type="hidden" name="sortOrder" value="${sortOrder}" />

                                    <c:if test="${currentPage > 1}">
                                        <button class="btn btn-outline-primary" type="submit" name="page" value="1"><<</button>
                                        <button class="btn btn-outline-primary" type="submit" name="page" value="${currentPage - 1}"><</button>
                                    </c:if>

                                    <%
                                        for (int i = startPage; i <= endPage; i++) {
                                    %>
                                    <button class="btn btn-outline-primary" type="submit" name="page" value="<%= i %>"
                                            <%= (i == currentPage ? "style='font-weight:bold;'" : "") %>>
                                        <%= i %>
                                    </button>
                                    <%
                                        }
                                    %>

                                    <c:if test="${currentPage < totalPages}">
                                        <button class="btn btn-outline-primary" type="submit" name="page" value="${currentPage + 1}">></button>
                                        <button class="btn btn-outline-primary" type="submit" name="page" value="${totalPages}">>></button>
                                    </c:if>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Blank End -->


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
</body>
</html>
