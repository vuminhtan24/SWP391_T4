<%-- 
    Document   : blogmanager
    Created on : Jun 25, 2025, 7:38:33 AM
    Author     : k16
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Blog Manager</title>
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
        <link href="${pageContext.request.contextPath}/ZeShopper/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .table-th {
                font-size: 18px;
                color: black;
            }

            /* Enhanced Filter Section Styling */
            .filter-card {
                background: #ffffff;
                border-radius: 0.5rem;
                box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
                border: 1px solid rgba(0, 0, 0, 0.125);
                margin-bottom: 1.5rem;
            }

            .filter-card-header {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                padding: 1rem 1.25rem;
                border-radius: 0.5rem 0.5rem 0 0;
                margin-bottom: 0;
                border-bottom: none;
            }

            .filter-card-header h5 {
                margin: 0;
                font-weight: 600;
                display: flex;
                align-items: center;
            }

            .filter-card-header i {
                margin-right: 0.5rem;
            }

            .category-list {
                padding: 1.25rem;
            }

            .category-item {
                display: block;
                padding: 0.75rem 1rem;
                margin-bottom: 0.5rem;
                color: #6c757d;
                text-decoration: none;
                border-radius: 0.375rem;
                transition: all 0.3s ease;
                background: #f8f9fa;
                border: 1px solid #e9ecef;
            }

            .category-item:hover {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                text-decoration: none;
                transform: translateY(-2px);
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .category-item.active {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                font-weight: 600;
            }

            .add {
                display: block;
                padding: 0.75rem 1rem;
                margin-bottom: 0.5rem;
                color: black;
                text-decoration: none;
                border-radius: 0.375rem;
                background: linear-gradient(135deg, #66ff66 0%, white 100%);
                background-size: 200% 200%;
                background-position: 0% 50%;
                border: 1px solid #e9ecef;
                transition: all 0.3s ease, background-position 0.5s ease;
            }

            .add:hover {
                background-position: 100% 50%;
                color: black;
                text-decoration: none;
                transform: translateY(-2px);
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }


            .search-filter-form {
                padding: 1.25rem;
                background: #f8f9fa;
                border-radius: 0.5rem;
                margin-top: 1rem;
            }

            .form-group {
                margin-bottom: 1rem;
            }

            .form-group label {
                font-weight: 600;
                color: #495057;
                margin-bottom: 0.5rem;
                display: block;
            }

            .form-control {
                border-radius: 0.375rem;
                border: 1px solid #ced4da;
                transition: all 0.3s ease;
            }

            .form-control:focus {
                border-color: #667eea;
                box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
            }

            .filter-buttons {
                display: flex;
                gap: 0.75rem;
                margin-top: 1.5rem;
            }

            .btn-gradient {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                border: none;
                color: white;
                border-radius: 0.375rem;
                transition: all 0.3s ease;
            }

            .btn-gradient:hover {
                transform: translateY(-2px);
                box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
                color: white;
            }

            .btn-outline-gradient {
                background: transparent;
                border: 2px solid #667eea;
                color: #667eea;
                border-radius: 0.375rem;
                transition: all 0.3s ease;
            }

            .btn-outline-gradient:hover {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                transform: translateY(-2px);
            }

            .stats-card {
                background: white;
                border-radius: 0.5rem;
                padding: 1.5rem;
                box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
                border: 1px solid rgba(0, 0, 0, 0.125);
                margin-bottom: 1.5rem;
            }

            .stats-card h6 {
                color: #6c757d;
                font-weight: 600;
                margin-bottom: 0.5rem;
            }

            .stats-number {
                font-size: 2rem;
                font-weight: 700;
                color: #667eea;
                margin: 0;
            }

            .table-container {
                background: white;
                border-radius: 0.5rem;
                box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
                overflow: hidden;
                border: 1px solid rgba(0, 0, 0, 0.125);
            }

            .table-header {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                padding: 1rem 1.25rem;
                margin: 0;
            }

            .table-header h5 {
                margin: 0;
                font-weight: 600;
                display: flex;
                align-items: center;
                justify-content: space-between;
            }

            .pagination-area {
                padding: 1.25rem;
                background: #f8f9fa;
                border-top: 1px solid #e9ecef;
            }

            .pagination {
                justify-content: center;
                margin: 0;
            }

            .pagination .page-link {
                color: #667eea;
                border-color: #e9ecef;
                border-radius: 0.375rem;
                margin: 0 0.25rem;
            }

            .pagination .page-link:hover {
                background: #667eea;
                border-color: #667eea;
                color: white;
            }

            .pagination .active .page-link {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                border-color: #667eea;
            }

            .action-buttons {
                display: flex;
                gap: 0.5rem;
            }

            .btn-sm {
                padding: 0.375rem 0.75rem;
                font-size: 0.875rem;
            }

            .swal-wide {
                width: 600px !important;
                font-size: 1.25rem;
            }

            .swal2-confirm, .swal2-cancel {
                font-size: 1.1rem !important;
                padding: 0.75rem 1.5rem !important;
            }

            .swal2-title {
                font-size: 1.5rem !important;
            }

            .swal2-html-container {
                font-size: 1.2rem !important;
            }
            .swal-wide {
                width: 600px !important;
            }

            /* Title */
            .swal2-title {
                font-size: 3rem !important;
            }

            /* Text content */
            .swal2-html-container {
                font-size: 2rem !important;
            }

            /* Confirm and cancel buttons */
            .swal2-confirm,
            .swal2-cancel {
                font-size: 1.6rem !important;
                padding: 1rem 2rem !important;
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
                                <a href="${pageContext.request.contextPath}/listRequest" class="dropdown-item">List Request</a>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}/listWholeSaleRequest" class="nav-item nav-link"><i class="fa fa-table me-2"></i>WholeSale</a>                       
                        <a href="${pageContext.request.contextPath}" class="nav-item nav-link"><i class="fa fa-table me-2"></i>La Fioreria</a>
                        <a href="${pageContext.request.contextPath}/blogmanager" class="nav-item nav-link active"><i class="fa fa-table me-2"></i>Blog Manager</a>
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

                <!-- Main Content -->
                <div class="container-fluid pt-4 px-4">
                    <div class="row">
                        <!-- Filter Section -->
                        <div class="col-lg-3 col-md-4">
                            <!-- Blog Categories Card -->
                            <div class="filter-card">
                                <div class="filter-card-header">
                                    <h5><i class="fas fa-list"></i>Blog Categories</h5>
                                </div>
                                <div class="category-list">
                                    <a href="${pageContext.request.contextPath}/blog/add" 
                                       class="add">
                                        <i class="fas fa-plus me-2"></i>Add Blog
                                    </a>
                                    <!-- All Categories Link -->
                                    <a href="blogmanager?page=1" class="category-item ${empty param.categoryId ? 'active' : ''}">
                                        <i class="fas fa-th-large me-2"></i>All Categories
                                    </a>
                                    <c:forEach var="category" items="${cList}">
                                        <a href="blogmanager?categoryId=${category.categoryId}&page=1" 
                                           class="category-item ${param.categoryId == category.categoryId ? 'active' : ''}">
                                            <i class="fas fa-tag me-2"></i>${category.categoryName}
                                        </a>
                                    </c:forEach>
                                </div>
                            </div>

                            <!-- Search and Filter Card -->
                            <div class="filter-card">
                                <div class="filter-card-header">
                                    <h5><i class="fas fa-search"></i>Search & Filter</h5>
                                </div>
                                <div class="search-filter-form">
                                    <form action="blogmanager" method="get">
                                        <div class="form-group">
                                            <label for="search">Search Blogs</label>
                                            <input type="text" id="search" name="search" class="form-control" 
                                                   placeholder="Enter keywords..." value="${param.search}">
                                        </div>

                                        <div class="form-group">
                                            <label for="sortBy">Sort By</label>
                                            <select id="sortBy" name="sortBy" class="form-control">
                                                <option value="created_at" ${param.sortBy == 'created_at' ? 'selected' : ''}>Date Created</option>
                                                <option value="updated_at" ${param.sortBy == 'updated_at' ? 'selected' : ''}>Date Updated</option>
                                                <option value="title" ${param.sortBy == 'title' ? 'selected' : ''}>Title</option>
                                            </select>
                                        </div>

                                        <div class="form-group">
                                            <label for="status">Status</label>
                                            <select id="status" name="status" class="form-control">
                                                <option value="" ${param.status == '' ? 'selected' : ''}>All</option>
                                                <option value="Active" ${param.status == 'Active' ? 'selected' : ''}>Active</option>
                                                <option value="Hidden" ${param.status == 'Hidden' ? 'selected' : ''}>Hidden</option>
                                                <option value="Deleted" ${param.status == 'Deleted' ? 'selected' : ''}>Deleted</option>
                                            </select>
                                        </div>

                                        <div class="form-group">
                                            <label for="sort">Order</label>
                                            <select id="sort" name="sort" class="form-control">
                                                <option value="DESC" ${param.sort == 'DESC' ? 'selected' : ''}>Newest First</option>
                                                <option value="ASC" ${param.sort == 'ASC' ? 'selected' : ''}>Oldest First</option>
                                            </select>
                                        </div>

                                        <input type="hidden" name="categoryId" value="${param.categoryId}">
                                        <input type="hidden" name="page" value="1">

                                        <div class="filter-buttons">
                                            <button type="submit" class="btn btn-gradient flex-fill">
                                                <i class="fas fa-filter me-1"></i>Apply Filter
                                            </button>
                                            <a class="btn btn-outline-gradient flex-fill" href="${pageContext.request.contextPath}/blogmanager">
                                                <i class="fas fa-times me-1"></i>Clear
                                            </a>
                                        </div>
                                    </form>
                                </div>
                            </div>

                            <div class="stats-card">
                                <h6><i class="fas fa-chart-bar me-2"></i>Quick Stats</h6>
                                <div class="row text-center">
                                    <div class="col-12 mb-3">
                                        <p class="stats-number">${fn:length(blogs)}</p>
                                        <small class="text-muted">Blogs Shown</small>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Blog List Section -->
                        <div class="col-lg-9 col-md-8">
                            <div class="table-container">
                                <div class="table-header">
                                    <h5>
                                        <span><i class="fas fa-blog me-2"></i>Blog Management</span>
                                        <span class="badge bg-light text-dark">${fn:length(blogs)} blogs</span>
                                    </h5>
                                </div>

                                <c:choose>
                                    <c:when test="${not empty blogs}">
                                        <div class="table-responsive">
                                            <table class="table table-hover mb-0">
                                                <thead class="table-light">
                                                    <tr>
                                                        <th class="table-th">ID</th>
                                                        <th class="table-th">Title</th>
                                                        <th class="table-th">Thumbnail</th>
                                                        <th class="table-th">Preview</th>
                                                        <th class="table-th">Status</th>
                                                        <th class="table-th">Author</th>
                                                        <th class="table-th">Created</th>
                                                        <th class="table-th">Updated</th>
                                                        <th class="table-th">Actions</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="blog" items="${blogs}">
                                                        <tr>
                                                            <td><strong>#${blog.blogId}</strong></td>
                                                            <td>
                                                                <div style="max-width: 200px;">
                                                                    <strong>${fn:substring(blog.title, 0, 25)} ${fn:length(blog.title) > 25 ? '...' : ''}</strong>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <img src="${pageContext.request.contextPath}/upload/BlogIMG/${blog.img_url}" alt="${blog.title}" 
                                                                     class="rounded" width="60" height="60" 
                                                                     style="object-fit: cover;"/>
                                                            </td>
                                                            <td>
                                                                <div style="max-width: 150px;">
                                                                    <small class="text-muted">
                                                                        ${fn:substring(blog.pre_context, 0, 25)}${fn:length(blog.pre_context) > 25 ? '...' : ''}
                                                                    </small>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${blog.status == 'Active'}">
                                                                        <span class="badge bg-success">Active</span>
                                                                    </c:when>
                                                                    <c:when test="${blog.status == 'Hidden'}">
                                                                        <span class="badge bg-warning">Hidden</span>
                                                                    </c:when>
                                                                    <c:when test="${blog.status == 'Deleted'}">
                                                                        <span class="badge bg-danger">Deleted</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge bg-secondary">${blog.status}</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <small class="text-muted">
                                                                    <i class="fas fa-user me-1"></i>${blog.owner.fullname}
                                                                </small>
                                                            </td>
                                                            <td>
                                                                <small>
                                                                    <fmt:formatDate value="${blog.created_at}" pattern="dd MMM, yyyy" />
                                                                    <br>
                                                                    <span class="text-muted">
                                                                        <fmt:formatDate value="${blog.created_at}" pattern="HH:mm" />
                                                                    </span>
                                                                </small>
                                                            </td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${not empty blog.updated_at}">
                                                                        <small>
                                                                            <fmt:formatDate value="${blog.updated_at}" pattern="dd MMM, yyyy" />
                                                                            <br>
                                                                            <span class="text-muted">
                                                                                <fmt:formatDate value="${blog.updated_at}" pattern="HH:mm" />
                                                                            </span>
                                                                        </small>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <small class="text-muted">Not updated</small>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <div class="action-buttons">
                                                                    <a href="${pageContext.request.contextPath}/blog/edit?bid=${blog.blogId}" 
                                                                       class="btn btn-outline-primary" title="Edit">
                                                                        <i class="fas fa-edit"></i>
                                                                    </a>
                                                                    <button class="btn btn-outline-danger" 
                                                                            onclick="confirmDelete(${blog.blogId})" 
                                                                            title="Delete">
                                                                        <i class="fas fa-trash"></i>
                                                                    </button>
                                                                    <a href="${pageContext.request.contextPath}/blog/detail?bid=${blog.blogId}" 
                                                                       class="btn btn-outline-info" title="View" target="_blank">
                                                                        <i class="fas fa-eye"></i>
                                                                    </a>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="text-center py-5">
                                            <div class="mb-4">
                                                <i class="fas fa-blog fa-4x text-muted"></i>
                                            </div>
                                            <h4 class="text-muted">No blogs found</h4>
                                            <p class="text-muted">
                                                <c:choose>
                                                    <c:when test="${not empty param.search}">
                                                        No blogs match your search criteria. Try different keywords.
                                                    </c:when>
                                                    <c:otherwise>
                                                        No blogs are available at the moment. Please check back later.
                                                    </c:otherwise>
                                                </c:choose>
                                            </p>
                                            <a href="blogmanager" class="btn btn-gradient">
                                                <i class="fas fa-list me-2"></i>View All Blogs
                                            </a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                                <!-- Pagination -->
                                <c:if test="${totalPages > 1}">
                                    <div class="pagination-area">
                                        <nav aria-label="Blog pagination">
                                            <ul class="pagination mb-0">
                                                <!-- Previous Page -->
                                                <c:if test="${currentPage > 1}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="blogmanager?page=${currentPage - 1}&search=${param.search}&sortBy=${param.sortBy}&sort=${param.sort}&categoryId=${param.categoryId}&search=${param.status}">
                                                            <i class="fas fa-chevron-left"></i>
                                                        </a>
                                                    </li>
                                                </c:if>

                                                <!-- Page Numbers -->
                                                <c:forEach var="i" begin="1" end="${totalPages}">
                                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                        <a class="page-link" href="blogmanager?page=${i}&search=${param.search}&sortBy=${param.sortBy}&sort=${param.sort}&categoryId=${param.categoryId}&search=${param.status}">
                                                            ${i}
                                                        </a>
                                                    </li>
                                                </c:forEach>

                                                <!-- Next Page -->
                                                <c:if test="${currentPage < totalPages}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="blogmanager?page=${currentPage + 1}&search=${param.search}&sortBy=${param.sortBy}&sort=${param.sort}&categoryId=${param.categoryId}&search=${param.status}">
                                                            <i class="fas fa-chevron-right"></i>
                                                        </a>
                                                    </li>
                                                </c:if>
                                            </ul>
                                        </nav>

                                        <!-- Pagination Info -->
                                        <div class="text-center mt-3">
                                            <small class="text-muted">
                                                Page ${currentPage} of ${totalPages}
                                                <c:if test="${not empty blogs}">
                                                    | Showing ${fn:length(blogs)} blogs
                                                </c:if>
                                            </small>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Footer Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded-top p-4">
                        <div class="row">
                            <div class="col-12 col-sm-6 text-center text-sm-start">
                                &copy; <a href="${pageContext.request.contextPath}">Lafioreria</a>, All Right Reserved. 
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Footer End -->
            </div>
            <!-- Content End -->
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
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <script>
                                                                                function confirmDelete(blogId) {
                                                                                    const formData = new FormData();
                                                                                    formData.append('bid', blogId);

                                                                                    Swal.fire({
                                                                                        title: 'Are you sure?',
                                                                                        text: "This action cannot be undone!",
                                                                                        icon: 'warning',
                                                                                        showCancelButton: true,
                                                                                        confirmButtonColor: '#d33',
                                                                                        cancelButtonColor: '#3085d6',
                                                                                        confirmButtonText: 'Yes, delete it!',
                                                                                        cancelButtonText: 'Cancel',
                                                                                        customClass: {
                                                                                            popup: 'swal-wide'
                                                                                        }
                                                                                    }).then((result) => {
                                                                                        if (result.isConfirmed) {
                                                                                            fetch(`${pageContext.request.contextPath}/blog/delete`, {
                                                                                                method: 'POST',
                                                                                                body: formData
                                                                                            })
                                                                                                    .then(response => response.json())
                                                                                                    .then(result => {
                                                                                                        if (result.ok === true) {
                                                                                                            Swal.fire({
                                                                                                                title: 'Deleted!',
                                                                                                                text: result.message || 'The blog has been deleted.',
                                                                                                                icon: 'success',
                                                                                                                customClass: {popup: 'swal-wide'}
                                                                                                            }).then(() => location.reload());
                                                                                                        } else {
                                                                                                            Swal.fire({
                                                                                                                title: 'Error!',
                                                                                                                text: result.message || 'Something went wrong.',
                                                                                                                icon: 'error',
                                                                                                                customClass: {popup: 'swal-wide'}
                                                                                                            });
                                                                                                        }
                                                                                                    })
                                                                                                    .catch(error => {
                                                                                                        console.error('Fetch error:', error);
                                                                                                        Swal.fire({
                                                                                                            title: 'Error!',
                                                                                                            text: 'Could not connect to server.',
                                                                                                            icon: 'error',
                                                                                                            customClass: {popup: 'swal-wide'}
                                                                                                        });
                                                                                                    });
                                                                                        }
                                                                                    });
                                                                                }

                                                                                // Auto-hide spinner
                                                                                $(document).ready(function () {
                                                                                    setTimeout(function () {
                                                                                        $('#spinner').removeClass('show');
                                                                                    }, 1000);
                                                                                });

                                                                                // Enhanced table interactions
                                                                                $('.table tbody tr').hover(function () {
                                                                                    $(this).addClass('table-active');
                                                                                }, function () {
                                                                                    $(this).removeClass('table-active');
                                                                                });

                                                                                // Form validation
                                                                                $('form').on('submit', function () {
                                                                                    const submitBtn = $(this).find('button[type="submit"]');
                                                                                    const originalText = submitBtn.html();
                                                                                    submitBtn.html('<i class="fas fa-spinner fa-spin me-1"></i>Applying...');
                                                                                    submitBtn.prop('disabled', true);

                                                                                    // Re-enable after 3 seconds in case of issues
                                                                                    setTimeout(function () {
                                                                                        submitBtn.html(originalText);
                                                                                        submitBtn.prop('disabled', false);
                                                                                    }, 3000);
                                                                                });
        </script>
    </body>
</html>