<%-- 
    Document   : rawflower
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
                width: 1400px;
                height: 720px;
                min-width: 1400px;
                max-width: 1400px;
                min-height: 720px;
                max-height: 720px;
                overflow: auto;
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
                object-fit: cover; /* Cắt ảnh để lấp đầy khung, giữ tỷ lệ */
                display: block;
                margin: 0 auto; /* Căn giữa ảnh trong ô */
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
                        <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="nav-item nav-link active"><i class="fa fa-table me-2"></i>RawFlower</a>
                        <a href="${pageContext.request.contextPath}/category" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Category</a>
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

                <!-- Raw Flower Management Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded h-100 p-4 fixed-container">
                        <!-- Header with title and Add Bouquet button -->
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h6 class="mb-0">Raw Flower List</h6>
                            <a href="${pageContext.request.contextPath}/addRawFlower" class="btn btn-primary">Add New Product</a>
                        </div>
                        <form action="${pageContext.request.contextPath}/DashMin/rawflower2" method="get"
                              style="display:flex; justify-content:space-between; align-items:center; margin-bottom:1rem; width:100%;">
                            <!-- Phần sort ở bên trái -->
                            <div style="display:flex; align-items:center;">
                                <label for="sortField" style="margin-right:0.5rem; white-space:nowrap;">
                                    Sắp xếp theo:
                                </label>
                                <select name="sortField"
                                        id="sortField"
                                        onchange="this.form.submit()"
                                        style="
                                        width:auto;
                                        min-width:max-content;
                                        padding:0.25rem 0.5rem;
                                        border:1px solid #ccc;
                                        border-radius:0.5rem;
                                        background-color:#fff;
                                        ">
                                    <option value="">-- Mặc định --</option>
                                    <option value="unit_price_asc" ${param.sortField == 'unit_price_asc' ? 'selected' : ''}>Giá bán tăng dần</option>
                                    <option value="unit_price_desc" ${param.sortField == 'unit_price_desc' ? 'selected' : ''}>Giá bán giảm dần</option>
                                    <option value="import_price_asc" ${param.sortField == 'import_price_asc' ? 'selected' : ''}>Giá mua tăng dần</option>
                                    <option value="import_price_desc" ${param.sortField == 'import_price_desc' ? 'selected' : ''}>Giá mua giảm dần</option>
                                    <option value="quantity_asc" ${param.sortField == 'quantity_asc' ? 'selected' : ''}>Số lượng tăng dần</option>
                                    <option value="quantity_desc" ${param.sortField == 'quantity_desc' ? 'selected' : ''}>Số lượng giảm dần</option>
                                </select>
                            </div>

                            <!-- Phần search ở bên phải -->
                            <div style="display:flex; align-items:center;">
                                <input type="text"
                                       name="rawFlowerName"
                                       placeholder="Tìm kiếm sản phẩm"
                                       value="${param.rawFlowerName}"
                                       style="
                                       width:200px;
                                       padding:0.25rem 0.5rem;
                                       border:1px solid #ccc;
                                       border-radius:0.5rem;
                                       margin-right:0.5rem;
                                       " />

                                <button type="submit"
                                        style="
                                        padding:0.25rem 0.75rem;
                                        border:none;
                                        border-radius:0.5rem;
                                        background-color:#007bff;
                                        color:#fff;
                                        cursor:pointer;
                                        ">
                                    Search
                                </button>
                            </div>
                        </form>

                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Image</th>
                                    <th scope="col">Raw Flower Name</th>
                                    <th scope="col">Unit Price</th>
                                    <th scope="col">Quantity</th>
                                    <th scope="col">Expiration Date</th>
                                    <th scope="col">Import Price</th>
                                    <th scope="col">Warehouse</th>
                                    <th colspan="2">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="rawFlower" items="${sessionScope.listRF}" varStatus="status">
                                    <tr>
                                        <td>${rawFlower.rawId}</td>
                                        <td>
                                            <img src="${rawFlower.imageUrl}" alt="Raw Flower Image" class="thumbnail-img" />
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/rawFlowerDetails?raw_id=${rawFlower.rawId}" class="change-color-qvm">
                                                ${rawFlower.rawName}
                                            </a>
                                        </td>
                                        <td>${rawFlower.unitPrice} VND</td>
                                        <td>${rawFlower.availableQuantity}</td>
                                        <td>${rawFlower.expirationDate}</td>
                                        <td>${rawFlower.importPrice} VND</td>
                                        <td>${rawFlower.warehouse.name}</td>
                                        <td>
                                            <button type="button"
                                                    class="btn btn-delete"
                                                    onclick="if (confirm('Do you want to delete?'))
                                                                location.href = '${pageContext.request.contextPath}/deleteRawFlower?raw_id=${rawFlower.rawId}';">
                                                Delete
                                            </button>
                                            <button type="button"
                                                    class="btn btn-edit"
                                                    onclick="location.href = '${pageContext.request.contextPath}/update_flower?raw_id=${rawFlower.rawId}';">
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
                                    <c:url var="prevUrl" value="/DashMin/rawflower2">
                                        <c:param name="page" value="${currentPage - 1}" />
                                        <c:param name="rawFlowerName" value="${param.rawFlowerName}" />
                                        <c:param name="sortField" value="${param.sortField}" />
                                    </c:url>
                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="${prevUrl}">Previous</a>
                                    </li>

                                    <!-- Các trang -->
                                    <c:forEach var="i" begin="1" end="${totalPages}">
                                        <c:url var="pageUrl" value="/DashMin/rawflower2">
                                            <c:param name="page" value="${i}" />
                                            <c:param name="rawFlowerName" value="${param.rawFlowerName}" />
                                            <c:param name="sortField" value="${param.sortField}" />
                                        </c:url>
                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="${pageUrl}">${i}</a>
                                        </li>
                                    </c:forEach>

                                    <!-- Next -->
                                    <c:url var="nextUrl" value="/DashMin/rawflower2">
                                        <c:param name="page" value="${currentPage + 1}" />
                                        <c:param name="rawFlowerName" value="${param.rawFlowerName}" />
                                        <c:param name="sortField" value="${param.sortField}" />
                                    </c:url>
                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                        <a class="page-link" href="${nextUrl}">Next</a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>
                    </div>
                </div>
                <!-- Raw Flower Management End -->

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