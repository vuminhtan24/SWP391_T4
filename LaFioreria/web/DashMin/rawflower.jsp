<%-- 
    Document   : rawflower2
    Created on : May 23, 2025, 9:35:15 AM
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

        <!-- Bootstrap from CDN (chỉ dùng 1 bản) -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="${pageContext.request.contextPath}/DashMin/css/style.css" rel="stylesheet">
        <style>
            /* Bảng đẹp và đồng nhất */
            .table th, .table td {
                vertical-align: middle;
                text-align: center;
                font-size: 14px;
            }

            /* Ảnh thumbnail cho hoa */
            .img-thumbnail {
                width: 60px;
                height: 60px;
                object-fit: cover;
                border-radius: 8px;
            }

            /* Button group cho Action */
            .actions-btn {
                display: flex;
                justify-content: center;
                gap: 0.5rem;
            }

            /* Form tìm kiếm nếu có thêm */
            .search-form {
                display: flex;
                flex-wrap: wrap;
                gap: 1rem;
                align-items: end;
            }

            .form-group label {
                font-size: 13px;
                font-weight: 500;
            }

            /* Tăng padding cho card-body */
            .card-body {
                padding: 1.5rem;
            }

            /* Nút thêm hoa nổi bật hơn */
            .btn-light.btn-sm {
                font-weight: 500;
            }

            /* Responsive: bảng cuộn tốt trên thiết bị nhỏ */
            .table-responsive {
                overflow-x: auto;
            }

            /* Tăng độ rõ khi hover vào dòng */
            .table-hover tbody tr:hover {
                background-color: #f3f6fa;
            }

            .btn-edit {
                background-color: #3498db;
            }

            .btn-edit:hover {
                background-color: #2980b9;
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

            /* Ẩn lỗi text trong form */
            .error {
                display: none;
            }
        </style>        
    </head>

    <body>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

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
                <jsp:include page="/DashMin/navbar.jsp"/> <!-- nav bar -->

                <!-- Raw Flower Management Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="card shadow-sm rounded-4">
                        <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center rounded-top-4">
                            <h4 class="mb-0">🌸 Raw Flowers Management</h4>
                            <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addFlowerModal">
                                Add New Product
                            </button>
                        </div>
                        <div class="card-body bg-light rounded-bottom-4">
                            <div class="table-responsive">
                                <!-- Add Perfume Modal -->
                                <div class="modal fade" id="addFlowerModal" tabindex="-1" aria-labelledby="addFlowerModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="addFlowerModalLabel">Add New Flower</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <!-- Popup lỗi -->
                                                <div id="errorPopup" class="error-popup">
                                                    <button type="button" class="btn-close" onclick="closePopup()">×</button>
                                                    <div class="error-header">Validation Errors</div>
                                                    <div class="error-body">
                                                        <c:if test="${not empty rawNameError}">
                                                            <p><strong>Raw Flower Name:</strong> ${rawNameError}</p>
                                                        </c:if>
                                                        <c:if test="${not empty unitPriceError}">
                                                            <p><strong>Unit Price:</strong> ${unitPriceError}</p>
                                                        </c:if>
                                                        <c:if test="${not empty importPriceError}">
                                                            <p><strong>Import Price:</strong> ${importPriceError}</p>
                                                        </c:if>
                                                        <c:if test="${not empty imageUrlError}">
                                                            <p><strong>Image URL:</strong> ${imageUrlError}</p>
                                                        </c:if>
                                                        <c:if test="${not empty warehouseIdError}">
                                                            <p><strong>Warehouse:</strong> ${warehouseIdError}</p>
                                                        </c:if>
                                                        <c:if test="${not empty error}">
                                                            <p><strong>Error:</strong> ${error}</p>
                                                        </c:if>
                                                    </div>
                                                </div>

                                                <!-- Hiển thị thông báo chung (nếu cần) -->
                                                <c:if test="${not empty sessionScope.error}">
                                                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                                        ${sessionScope.error}
                                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                                    </div>
                                                    <c:remove var="error" scope="session"/>
                                                </c:if>

                                                <!-- Form Add Perfume -->
                                                <form action="${pageContext.request.contextPath}/addRawFlower" method="post" class="mt-3">
                                                    <div class="form-group">
                                                        <label for="rawName" class="form-label">Raw Flower Name:</label>
                                                        <input type="text" id="rawName" name="rawName" class="form-control" 
                                                               value="${rawName != null ? rawName : sessionScope.rawName}" placeholder="Enter raw flower name" maxlength="45">
                                                    </div>

                                                    <div class="row mb-3">
                                                        <div class="col-sm-6">
                                                            <label for="unitPrice" class="form-label">Unit Price (VND):</label>
                                                            <input type="text" id="unitPrice" name="unitPrice" class="form-control" 
                                                                   value="${unitPrice != null ? unitPrice : sessionScope.unitPrice}" placeholder="Enter unit price">
                                                        </div>
                                                        <div class="col-sm-6">
                                                            <label for="importPrice" class="form-label">Import Price (VND):</label>
                                                            <input type="text" id="importPrice" name="importPrice" class="form-control" 
                                                                   value="${importPrice != null ? importPrice : sessionScope.importPrice}" placeholder="Enter import price">
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label for="imageUrl" class="form-label">Image URL:</label>
                                                        <input type="text" id="imageUrl" name="imageUrl" class="form-control" 
                                                               value="${imageUrl != null ? imageUrl : sessionScope.imageUrl}" placeholder="Enter image URL">
                                                    </div>

                                                    <div class="form-group">
                                                        <label for="warehouseId" class="form-label">Warehouse:</label>
                                                        <select id="warehouseId" name="warehouseId" class="form-select">
                                                            <c:forEach items="${sessionScope.listW}" var="w">
                                                                <option value="${w.warehouseId}" ${warehouseId != null ? (warehouseId == w.warehouseId ? 'selected' : '') : (sessionScope.warehouseId == w.warehouseId ? 'selected' : '')}>
                                                                    ${w.name}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>

                                                    <div class="mt-3 text-center">
                                                        <button type="submit" class="btn btn-primary">Add Flower</button>
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Thêm phần hiển thị thông báo -->
                                <c:if test="${not empty sessionScope.message}">
                                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                                        ${sessionScope.message}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    </div>
                                    <c:remove var="message" scope="session"/> <!-- Xóa thông báo sau khi hiển thị -->
                                </c:if>

                                <!-- Form lựa chọn sắp xếp -->
                                <div class="mb-3">
                                    <form action="${pageContext.request.contextPath}/DashMin/rawflower2" method="get" class="d-flex gap-3 align-items-center">
                                        <div class="form-group">
                                            <label for="sortBy" class="form-label">Sắp xếp bởi:</label>
                                            <select id="sortBy" name="sortBy" class="form-select">
                                                <option value="" ${empty sessionScope.sortBy ? 'selected' : ''}>None</option>
                                                <option value="unit_price" ${sessionScope.sortBy == 'unit_price' ? 'selected' : ''}>Giá bán</option>
                                                <option value="import_price" ${sessionScope.sortBy == 'import_price' ? 'selected' : ''}>Giá mua</option>
                                                <option value="quantity" ${sessionScope.sortBy == 'quantity' ? 'selected' : ''}>Số lượng</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="sortOrder" class="form-label">Sắp xếp theo:</label>
                                            <select id="sortOrder" name="sortOrder" class="form-select">
                                                <option value="asc" ${sessionScope.sortOrder == 'asc' ? 'selected' : ''}>Tăng dần</option>
                                                <option value="desc" ${sessionScope.sortOrder == 'desc' ? 'selected' : ''}>Giảm dần</option>
                                            </select>
                                        </div>
                                        <button type="submit" class="btn btn-primary">Sort</button>
                                    </form>
                                </div>
                                <table id="productTable" class="table table-hover align-middle table-bordered border-secondary-subtle">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Thumbnail</th>
                                            <th>Name</th>
                                            <th>Unit Price</th>
                                            <th>Quantity</th>
                                            <th>Expiration Date</th>
                                            <th>Import Price</th>
                                            <th>Warehouse ID</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${sessionScope.listRF}" var="item">
                                            <tr>
                                                <td>${item.rawId}</td>
                                                <td><img src="${item.imageUrl}" class="img-thumbnail" alt="${item.rawName}"></td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/rawFlowerDetails?raw_id=${item.rawId}" class="category-link">
                                                        ${item.rawName}
                                                    </a>
                                                </td>
                                                <td>${item.unitPrice} VND</td>
                                                <td>${item.availableQuantity}</td>
                                                <td>${item.expirationDate}</td>
                                                <td>${item.importPrice} VND</td>
                                                <th>${item.warehouse.name}</th>
                                                <td class="actions-btn">
                                                    <a href="${pageContext.request.contextPath}/update_flower?raw_id=${item.rawId}" class="btn btn-edit btn-sm">Edit</a>
                                                    <form action="${pageContext.request.contextPath}/hideRawFlower" method="post" style="display:inline;" onsubmit="return confirmHide();">
                                                        <input type="hidden" name="id" value="${item.rawId}"/>
                                                        <button type="submit" class="btn btn-danger btn-sm">Hide</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
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
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.1/js/jquery.dataTables.min.js"></script>
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
            $(document).ready(function () {
                $('#productTable').DataTable({
                    "paging": true,
                    "searching": true,
                    "ordering": false,
                    "info": true,
                    "pageLength": 6,
                    "lengthChange": false,
                    "language": {
                        "paginate": {
                            "previous": "Previous",
                            "next": "Next"
                        },
                        "emptyTable": "No data available in table",
                    }
                });
            });
        </script> 
        <script>
            function confirmHide() {
                return confirm("Are you sure you want to hide this product?");
            }
        </script>
        <script>
            document.addEventListener('DOMContentLoaded', function() {
                var showErrorPopup = ${requestScope.showErrorPopup != null ? requestScope.showErrorPopup : false};
                console.log("Show Error Popup: " + showErrorPopup);
                if (showErrorPopup) {
                    $('#addFlowerModal').modal('show');
                    var errorPopup = document.getElementById('errorPopup');
                    errorPopup.style.display = 'block';
                }
            });

            function closePopup() {
                var errorPopup = document.getElementById('errorPopup');
                errorPopup.style.display = 'none';
            }

            setTimeout(closePopup, 5000);
        </script>
        <style>
            /* Khoảng cách giữa các nút phân trang */
            .dataTables_wrapper .dataTables_paginate .paginate_button {
                margin: 0 5px;
                padding: 5px 10px;
                border-radius: 5px;
                color: #007bff;
                background-color: #f8f9fa;
                border: 1px solid #ddd;
            }

            /* Đổi màu nền khi di chuột qua */
            .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
                background-color: #007bff;
                color: white;
            }

            /* Kiểu dáng nút hiện tại */
            .dataTables_wrapper .dataTables_paginate .paginate_button.current {
                background-color: #007bff;
                color: white;
            }

            /* Tăng khoảng cách giữa dòng "Showing..." và dòng phân trang */
            .dataTables_wrapper .dataTables_info {
                margin-bottom: 20px;
            }

            /* Đảm bảo dòng phân trang không bị ảnh hưởng */
            .dataTables_wrapper .dataTables_paginate {
                margin-top: 20px;
            }
        </style>
    </body>
</html>