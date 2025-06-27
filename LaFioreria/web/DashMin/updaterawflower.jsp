<%-- 
    Document   : updaterawflower
    Created on : June 05, 2025, 10:12 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Update Raw Flower - DASHMIN</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
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
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Template Stylesheet -->
        <link href="${pageContext.request.contextPath}/DashMin/css/style.css" rel="stylesheet">
        <style>
            /* Tổng thể khu vực Update Raw Flower */
            .container-fluid.pt-4.px-4 .content {
                padding: 30px;
                background-color: #f8f9fa;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                max-width: 1200px;
                margin: 0 auto;
            }

            /* Tiêu đề Product Details */
            .container-fluid.pt-4.px-4 .content h1.h2 {
                font-size: 32px;
                color: #007bff;
                font-weight: 600;
                text-align: center;
                margin-bottom: 30px;
                border-bottom: 2px solid #007bff;
                padding-bottom: 10px;
            }

            /* Hình ảnh sản phẩm */
            .container-fluid.pt-4.px-4 .content .img-fluid.img-thumbnail {
                width: 360px;
                height: 360px;
                object-fit: cover;
                border-radius: 8px;
                border: 2px solid #ddd;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            /* Form nhập liệu */
            .container-fluid.pt-4.px-4 .content .form-label {
                font-weight: 500;
                color: #333;
                margin-bottom: 5px;
                font-size: 18px;
            }

            .container-fluid.pt-4.px-4 .content .form-control,
            .container-fluid.pt-4.px-4 .content .form-select {
                border-radius: 5px;
                border: 1px solid #ced4da;
                padding: 8px 12px;
                font-size: 18px;
                transition: border-color 0.3s ease;
            }

            .container-fluid.pt-4.px-4 .content .form-control:focus,
            .container-fluid.pt-4.px-4 .content .form-select:focus {
                border-color: #007bff;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
            }

            /* Căn chỉnh các trường nhập liệu */
            .container-fluid.pt-4.px-4 .content .row.g-3.align-items-center {
                margin-bottom: 15px;
            }

            .container-fluid.pt-4.px-4 .content .col-md-4,
            .container-fluid.pt-4.px-4 .content .col-md-6 {
                padding: 0 10px;
            }

            /* Nút bấm Update và Reset */
            .container-fluid.pt-4.px-4 .content .btn-primary {
                background-color: #007bff;
                border-color: #007bff;
                padding: 8px 20px;
                font-weight: 500;
                border-radius: 5px;
                transition: background-color 0.3s ease;
            }

            .container-fluid.pt-4.px-4 .content .btn-primary:hover {
                background-color: #0056b3;
                border-color: #0056b3;
            }

            .container-fluid.pt-4.px-4 .content .btn-secondary {
                background-color: #6c757d;
                border-color: #6c757d;
                padding: 8px 20px;
                font-weight: 500;
                border-radius: 5px;
                transition: background-color 0.3s ease;
            }

            .container-fluid.pt-4.px-4 .content .btn-secondary:hover {
                background-color: #5a6268;
                border-color: #5a6268;
            }

            /* Khoảng cách giữa các nút */
            .container-fluid.pt-4.px-4 .content .mt-3 {
                display: flex;
                gap: 10px;
                justify-content: center;
            }

            /* Thông báo lỗi */
            .error {
                display: none; /* Ẩn lỗi text, sử dụng popup thay thế */
                color: #dc3545;
                font-size: 14px;
                margin-top: 5px;
            }

            .error-common {
                display: none; /* Ẩn thông báo lỗi chung */
                color: #dc3545;
                font-size: 14px;
                margin-top: 15px;
                text-align: center;
                background-color: #f8d7da;
                padding: 8px;
                border-radius: 5px;
                border: 1px solid #f5c6cb;
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

            .btn {
                padding: 8px 14px;
                border: none;
                border-radius: 4px;
                color: white;
                font-size: 20px;
                cursor: pointer;
                margin-right: 5px;
                transition: background-color 0.2s ease;
            }

            .btn-edit {
                background-color: #3498db; /* Xanh dương */
            }

            .btn-edit:hover {
                background-color: #2980b9;
            }

            #imagePreview img {
                max-width: 200px;
                margin-top: 10px;
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
                        <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="nav-item nav-link active"><i class="fa fa-table me-2"></i>RawFlower</a>
                        <a href="${pageContext.request.contextPath}/category" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Category</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-table me-2"></i>Repair Center</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/repairOrders" class="dropdown-item">Repair Orders</a>
                                <a href="${pageContext.request.contextPath}/repairHistory" class="dropdown-item">Repair History</a>
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
            </div>
            <!-- Sidebar End -->

            <!-- Content Start -->
            <div class="content">
                <jsp:include page="/DashMin/navbar.jsp"/> <!-- nav bar -->

                <!-- Update Raw Flower -->
                <div class="container-fluid pt-4 px-4">
                    <div class="row bg-light rounded align-items-center justify-content-center mx-0">
                        <div>
                            <div class="content">
                                <h1 class="h2">Edit Raw Flower</h1>

                                <!-- Popup lỗi -->
                                <div id="errorPopup" class="error-popup">
                                    <button type="button" class="btn-close" onclick="closePopup()">×</button>
                                    <div class="error-header">Error Authentication</div>
                                    <div class="error-body">
                                        <c:if test="${not empty flowerNameError}">
                                            <p><strong>Flower Name:</strong> ${fn:escapeXml(flowerNameError)}</p>
                                        </c:if>
                                        <c:if test="${not empty imageError}">
                                            <p><strong>Image File:</strong> ${fn:escapeXml(imageError)}</p>
                                        </c:if>
                                        <c:if test="${not empty error}">
                                            <p><strong>Error:</strong> ${fn:escapeXml(error)}</p>
                                        </c:if>
                                    </div>
                                </div>

                                <form action="${pageContext.request.contextPath}/update_flower" method="post" enctype="multipart/form-data">
                                    <input type="hidden" name="flower_id" value="${flowerId != null ? flowerId : item.flowerId}">
                                    <input type="hidden" name="current_image" value="${fn:escapeXml(item.image)}">
                                    <div class="row mb-3">
                                        <div class="col-md-4">
                                            <img class="img-fluid img-thumbnail" 
                                                 src="${pageContext.request.contextPath}/upload/FlowerIMG/${fn:escapeXml(item.image)}" 
                                                 alt="${fn:escapeXml(flowerName != null ? flowerName : item.flowerName)}"
                                                 onerror="this.src='${pageContext.request.contextPath}/DashMin/img/no-image.jpg'">
                                        </div>
                                        <div class="col-md-8">
                                            <div class="mb-3">
                                                <label for="flower_name" class="form-label">Flower Name</label>
                                                <input type="text" id="flower_name" name="flower_name" class="form-control" 
                                                       value="${fn:escapeXml(item.flowerName)}" 
                                                       placeholder="Nhập tên loại hoa" required>
                                                <c:if test="${not empty flowerNameError}">
                                                    <small class="text-danger">${fn:escapeXml(flowerNameError)}</small>
                                                </c:if>
                                            </div>
                                            <div class="mb-3">
                                                <label for="current_image" class="form-label">Current Image File</label>
                                                <input type="text" id="current_image" class="form-control" 
                                                       value="${pageContext.request.contextPath}/upload/FlowerIMG/${fn:escapeXml(item.image)}" readonly>
                                            </div>
                                            <div class="mb-3">
                                                <label for="imageFile" class="form-label">Upload New Image (Optional)</label>
                                                <input type="file" id="imageFile" name="imageFile" class="form-control" 
                                                       accept=".jpg,.jpeg,.png">
                                                <small class="form-text text-muted">Only .jpg, .jpeg, or .png files allowed (max 10MB).</small>
                                                <c:if test="${not empty imageError}">
                                                    <small class="text-danger">${fn:escapeXml(imageError)}</small>
                                                </c:if>
                                                <div id="imagePreview"></div>
                                            </div>
                                            <div class="mt-3">
                                                <button type="submit" class="btn btn-primary">Edit</button>
                                                <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="btn btn-secondary">Cancel</a>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Update Raw Flower End -->

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
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/DashMin/lib/chart/chart.min.js"></script>
        <script src="${pageContext.request.contextPath}/DashMin/lib/easing/easing.min.js"></script>
        <script src="${pageContext.request.contextPath}/DashMin/lib/waypoints/waypoints.min.js"></script>
        <script src="${pageContext.request.contextPath}/DashMin/lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/js/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/js/moment-timezone.min.js"></script>
        <script src="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>     
        <!-- Template Main Script -->
        <script src="${pageContext.request.contextPath}/DashMin/js/main.js"></script>

        <script>
            document.addEventListener('DOMContentLoaded', function() {
                var showErrorPopup = ${requestScope.showErrorPopup != null ? requestScope.showErrorPopup : false};
                console.log("Show Error Popup: " + showErrorPopup);
                if (showErrorPopup) {
                    var errorPopup = document.getElementById('errorPopup');
                    errorPopup.style.display = 'block';
                }

                // Image preview
                document.getElementById('imageFile').addEventListener('change', function(e) {
                    const file = e.target.files[0];
                    const preview = document.getElementById('imagePreview');
                    preview.innerHTML = ''; // Xóa preview cũ
                    if (file) {
                        if (file.size > 10 * 1024 * 1024) { // 10MB
                            alert('File size exceeds 10MB limit.');
                            e.target.value = ''; // Xóa file chọn
                            return;
                        }
                        const reader = new FileReader();
                        reader.onload = function(e) {
                            const img = document.createElement('img');
                            img.src = e.target.result;
                            img.style.maxWidth = '200px';
                            preview.appendChild(img);
                        };
                        reader.readAsDataURL(file);
                    }
                });
            });

            function closePopup() {
                var errorPopup = document.getElementById('errorPopup');
                errorPopup.style.display = 'none';
            }

            setTimeout(closePopup, 5000);
        </script>
    </body>
</html>