<%-- 
    Document   : addrawflower
    Created on : Jun 12, 2025, 10:34:20 PM
    Author     : [Your Name]
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>DASHMIN - Add Raw Flower</title>
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

            <!-- Form Start -->
            <div class="container-fluid py-5">
                <div class="row justify-content-center">
                    <div class="col-xl-8 col-lg-10">
                        <div class="card shadow-sm">
                            <div class="card-header bg-primary text-white">
                                <h4 class="mb-0">Add New Raw Flower</h4>
                            </div>
                            <div class="card-body">
                                <form action="${pageContext.request.contextPath}/addRawFlower" method="post">
                                    <div class="row g-3">
                                        <!-- Raw Flower Name -->
                                        <div class="col-md-6">
                                            <label for="rawName" class="form-label">Raw Flower Name</label>
                                            <input type="text" id="rawName" name="rawName" class="form-control"
                                                   value="${requestScope.rawName}" placeholder="Enter flower name" required/>
                                            <c:if test="${not empty requestScope.rawNameError}">
                                                <small class="text-danger">${requestScope.rawNameError}</small>
                                            </c:if>
                                        </div>
                                        <!-- Image URL -->
                                        <div class="col-md-6">
                                            <label for="imageUrl" class="form-label">Image URL</label>
                                            <input type="url" id="imageUrl" name="imageUrl" class="form-control"
                                                   value="${requestScope.imageUrl}" placeholder="https://example.com/image.jpg"
                                                   pattern="https?://.+\.(jpg|jpeg|JPG|JPEG)$"
                                                   title="URL must end with .jpg or .jpeg" required/>
                                            <c:if test="${not empty requestScope.imageUrlError}">
                                                <small class="text-danger">${requestScope.imageUrlError}</small>
                                            </c:if>
                                        </div>
                                        <!-- Unit Price -->
                                        <div class="col-md-6">
                                            <label for="unitPrice" class="form-label">Unit Price (VND)</label>
                                            <input type="number" id="unitPrice" name="unitPrice" class="form-control"
                                                   value="${requestScope.unitPrice}" placeholder="Enter unit price" min="1" required/>
                                            <c:if test="${not empty requestScope.unitPriceError}">
                                                <small class="text-danger">${requestScope.unitPriceError}</small>
                                            </c:if>
                                        </div>
                                        <!-- Import Price -->
                                        <div class="col-md-6">
                                            <label for="importPrice" class="form-label">Import Price (VND)</label>
                                            <input type="number" id="importPrice" name="importPrice" class="form-control"
                                                   value="${requestScope.importPrice}" placeholder="Enter import price" min="1" required/>
                                            <c:if test="${not empty requestScope.importPriceError}">
                                                <small class="text-danger">${requestScope.importPriceError}</small>
                                            </c:if>
                                        </div>
                                        <!-- Quantity -->
                                        <div class="col-md-6">
                                            <label for="rawQuantity" class="form-label">Quantity</label>
                                            <input type="number" id="rawQuantity" name="rawQuantity" class="form-control"
                                                   value="${requestScope.rawQuantity}" placeholder="Enter quantity" min="0" required/>
                                            <c:if test="${not empty requestScope.rawQuantityError}">
                                                <small class="text-danger">${requestScope.rawQuantityError}</small>
                                            </c:if>
                                        </div>
                                        <!-- Expiration Date -->
                                        <div class="col-md-6">
                                            <label for="expirationDate" class="form-label">Expiration Date</label>
                                            <input type="date" id="expirationDate" name="expirationDate" class="form-control"
                                                   value="${requestScope.expirationDate}" required/>
                                            <c:if test="${not empty requestScope.expirationDateError}">
                                                <small class="text-danger">${requestScope.expirationDateError}</small>
                                            </c:if>
                                        </div>
                                        <!-- Warehouse -->
                                        <div class="col-md-6">
                                            <label for="warehouseId" class="form-label">Warehouse</label>
                                            <select id="warehouseId" name="warehouseId" class="form-select" required>
                                                <option value="">Select warehouse</option>
                                                <c:forEach var="w" items="${sessionScope.listW}">
                                                    <option value="${w.warehouseId}" ${requestScope.warehouseId == w.warehouseId ? 'selected' : ''}>
                                                        ${w.name}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                            <c:if test="${not empty requestScope.warehouseIdError}">
                                                <small class="text-danger">${requestScope.warehouseIdError}</small>
                                            </c:if>
                                        </div>
                                    </div>

                                    <!-- Submit -->
                                    <div class="mt-4 text-end">
                                        <button type="submit" class="btn btn-success px-4">Add Raw Flower</button>
                                        <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="btn btn-secondary px-4">Cancel</a>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Error Popup -->
                    <c:if test="${requestScope.showErrorPopup}">
                        <div class="error-popup" style="display: block;">
                            <button class="btn-close" onclick="this.parentElement.style.display='none';">×</button>
                            <div class="error-header">Error</div>
                            <div class="error-body">
                                <c:if test="${not empty requestScope.error}">
                                    ${requestScope.error}<br>
                                </c:if>
                                <c:if test="${not empty requestScope.rawNameError}">
                                    ${requestScope.rawNameError}<br>
                                </c:if>
                                <c:if test="${not empty requestScope.unitPriceError}">
                                    ${requestScope.unitPriceError}<br>
                                </c:if>
                                <c:if test="${not empty requestScope.importPriceError}">
                                    ${requestScope.importPriceError}<br>
                                </c:if>
                                <c:if test="${not empty requestScope.imageUrlError}">
                                    ${requestScope.imageUrlError}<br>
                                </c:if>
                                <c:if test="${not empty requestScope.rawQuantityError}">
                                    ${requestScope.rawQuantityError}<br>
                                </c:if>
                                <c:if test="${not empty requestScope.expirationDateError}">
                                    ${requestScope.expirationDateError}<br>
                                </c:if>
                                <c:if test="${not empty requestScope.warehouseIdError}">
                                    ${requestScope.warehouseIdError}<br>
                                </c:if>
                            </div>
                        </div>
                    </c:if>
                </div>
                <!-- Form End -->

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