<%-- 
    Document   : blank
    Created on : May 19, 2025, 2:34:20 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List,model.Bouquet, model.Category, model.RawFlower" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
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
            .bouquet-img {
                max-height: 600px;
                width: auto;
                border-radius: 12px;
                box-shadow: 0 4px 16px rgba(0,0,0,0.1);
                margin-left: 30px;
            }

            .bouquet-info {
                background-color: #ffffff;
                border-radius: 12px;
                padding: 24px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            }

            label.form-label {
                margin-bottom: 6px;
                color: #333;
            }

            .table td, .table th {
                vertical-align: middle;
            }

            @media (max-width: 768px) {
                .bouquet-img {
                    margin-left: 0;
                    max-width: 100%;
                    height: auto;
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
                        <a href="${pageContext.request.contextPath}/DashMin/admin.jsp" class="nav-item nav-link"><i class="fa fa-tachometer-alt me-2"></i>Dashboard</a>
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
                        <a href="${pageContext.request.contextPath}/DashMin/table.jsp" class="nav-item nav-link active"><i class="fa fa-table me-2"></i>Tables</a>
                        <a href="${pageContext.request.contextPath}/viewBouquet" class="nav-item nav-link active"><i class="fa fa-table me-2"></i>Bouquet</a>
                        <a href="${pageContext.request.contextPath}/DashMin/chart.jsp" class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="far fa-file-alt me-2"></i>Pages</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/DashMin/404.jsp" class="dropdown-item">404 Error</a>
                                <a href="${pageContext.request.contextPath}/DashMin/blank.jsp" class="dropdown-item">Blank Page</a>
                                <a href="${pageContext.request.contextPath}/viewuserdetail" class="dropdown-item active">View User Detail</a>
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


                <!-- Blank Start -->
                <form action="editBouquet" method="post">
                    <div class="background-qvm" style="background-color: #f3f6f9; margin-top: 20px; margin-left: 20px; margin-right: 20px; border-radius: 5px;">
                        <div class="container-fluid py-5 px-4">
                            <div class="d-flex flex-wrap align-items-start gap-5" style="gap: 100px;">


                                <!-- Cột ảnh -->
                                <div class="flex-shrink-0">
                                    <img
                                        src="${bouquetDetail.getImageUrl()}"
                                        alt="Bouquet Image" 
                                        class="img-fluid bouquet-img"
                                        style="width: 550px; height: 600px; object-fit: cover; border-radius: 12px;">
                                </div>

                                <!-- Cột phải: Nội dung -->
                                <input type="hidden" name="id" value="${bouquetDetail.getBouquetId()}">
                                <div class="flex-grow-1 bouquet-content">
                                    <div class="bouquet-info p-4 shadow-sm rounded bg-white">
                                        <h1 class="fw-bold mb-4 text-primary">
                                            <input type="text" name="bqName" value="${bouquetDetail.getBouquetName()}" required/>    
                                        </h1>

                                        <!-- Thông tin cơ bản -->
                                        <div class="mb-3">
                                            <label class="form-label fw-semibold">Category:</label>
                                            <select name="category" class="form-select">
                                                <c:forEach var="cate" items="${cateList}">
                                                    <option 
                                                        value="${cate.getCategoryId()}" 
                                                        ${cate.getCategoryName() == cateName ? 'selected' : ''}>
                                                        ${cate.getCategoryName()}
                                                    </option>
                                                </c:forEach>   
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label for="imageUrl" class="form-label fw-semibold">Image URL:</label>                                                                                   
                                            <input 
                                                type="url" 
                                                id="imageUrl" 
                                                name="imageUrl" 
                                                class="form-control" 
                                                value="${bouquetDetail.getImageUrl()}" 
                                                required
                                                pattern="https?://.+\.(jpg|jpeg|JPG|JPEG)$" 
                                                title="URL phải kết thúc bằng .jpg hoặc .jpeg" 
                                                />
                                        </div>

                                        <div class="mb-4">
                                            <label class="form-label fw-semibold">Description:</label>
                                            <textarea name="bqDescription" class="form-control" rows="4" required>${bouquetDetail.getDescription()}</textarea>
                                        </div>

                                        <!-- Flower Table -->
                                        <h5 class="mb-3 text-secondary">Flowers in Bouquet</h5>

                                        <div class="table-responsive mb-3">
                                            <table id="flowerTable" class="table table-bordered align-middle">
                                                <thead class="table-light">
                                                    <tr>
                                                        <th>Flower</th>
                                                        <th>Price per Stem</th>
                                                        <th>Quantity</th>
                                                        <th></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <!-- Chỉ duy nhất FOR-EACH, không có row mặc định -->
                                                    <c:forEach var="br" items="${flowerInBQ}">
                                                        <tr>
                                                            <td>
                                                                <select name="flowerIds" class="form-select form-select-sm flower-select">
                                                                    <c:forEach var="f" items="${allFlowers}">
                                                                        <option
                                                                            value="${f.getRawId()}"
                                                                            data-price="${f.getUnitPrice()}"
                                                                            <c:if test="${f.getRawId() eq br.getRaw_id()}">selected</c:if>
                                                                            >${f.getRawName()}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td>
                                                                <span class="form-text price-text">$0.00</span>
                                                                <input type="hidden" class="price-input" name="prices[]" value="0" />
                                                            </td>
                                                            <td>
                                                                <input
                                                                    type="number"
                                                                    name="quantities"
                                                                    value="${br.getQuantity()}"
                                                                    min="1"
                                                                    step="1"
                                                                    required
                                                                    class="form-control form-control-sm quantity-input"
                                                                    />
                                                            </td>
                                                            <td>
                                                                <button type="button" class="btn btn-sm btn-outline-danger remove-btn">
                                                                    &times;
                                                                </button>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                                <tfoot>
                                                    <tr>
                                                        <td colspan="4" class="text-start fw-bold text-primary">
                                                            Total Value:
                                                            <span id="totalValueDisplay">$0.00</span>
                                                            <input type="hidden" id="totalValueInput" name="totalValue" value="0" />
                                                        </td>
                                                    </tr>
                                                </tfoot>
                                            </table>
                                        </div>
                                        <div class="d-flex justify-content-between">
                                            <button type="button" id="addFlowerBtn" class="btn btn-sm btn-outline-primary mb-4">
                                                + Add New Flower
                                            </button>
                                            <button type="submit" class="btn btn-success px-4">Save Bouquet</button>
                                        </div>

                                    </div>
                                </div>

                            </div>
                        </div>
                </form>
                <p>flowerInBQ size: ${fn:length(flowerInBQ)}</p>
                <!-- TEMPLATE để clone (ẩn) -->
                <table style="display:none;">
                    <tbody>
                        <tr id="flowerRowTemplate">
                            <td>
                                <select name="flowerIds" class="form-select form-select-sm flower-select">
                                    <c:forEach var="f" items="${allFlowers}">
                                        <option value="${f.getRawId()}" data-price="${f.getUnitPrice()}">
                                            ${f.getRawName()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <span class="form-text price-text">$0.00</span>
                                <input type="hidden" class="price-input" name="prices[]" value="0" />
                            </td>
                            <td>
                                <input
                                    type="number"
                                    name="quantities"
                                    value="1"
                                    min="1"
                                    step="1"
                                    required
                                    class="form-control form-control-sm quantity-input"
                                    />
                            </td>
                            <td>
                                <button type="button" class="btn btn-sm btn-outline-danger remove-btn">
                                    &times;
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <!-- Blank End -->
            </div>

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
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            // Định dạng tiền tệ
            function formatCurrency(val) {
                return '$' + parseFloat(val).toFixed(2);
            }

            // Cập nhật giá của từng dòng khi chọn hoặc khởi tạo
            function updateRowPrice(row) {
                const sel = row.querySelector('.flower-select');
                const priceInput = row.querySelector('.price-input');
                const priceText = row.querySelector('.price-text');
                const unitPrice = sel.selectedOptions[0].dataset.price;
                priceInput.value = unitPrice;
                priceText.textContent = formatCurrency(unitPrice);
            }

            // Tính lại tổng
            function updateTotalValue() {
                let total = 0;
                document.querySelectorAll('#flowerTable tbody tr').forEach(row => {
                    const price = parseFloat(row.querySelector('.price-input').value) || 0;
                    const qty = parseInt(row.querySelector('.quantity-input').value, 10) || 0;
                    total += price * qty;
                });
                document.getElementById('totalValueDisplay').textContent = formatCurrency(total);
                document.getElementById('totalValueInput').value = total;
            }

            // Gắn event cho 1 dòng: change select, input quantity, click remove
            function bindRowEvents(row) {
                row.querySelector('.flower-select')
                        .addEventListener('change', () => {
                            updateRowPrice(row);
                            updateTotalValue();
                        });
                row.querySelector('.quantity-input')
                        .addEventListener('input', () => updateTotalValue());
                row.querySelector('.remove-btn')
                        .addEventListener('click', () => {
                            row.remove();
                            updateTotalValue();
                        });
            }

            // Khởi tạo: bind và cập nhật cho các dòng đã có sẵn
            document.querySelectorAll('#flowerTable tbody tr').forEach(r => {
                bindRowEvents(r);
                updateRowPrice(r);
            });
            updateTotalValue();

            // Thêm dòng mới khi click nút
            document.getElementById('addFlowerBtn')
                    .addEventListener('click', () => {
                        const tpl = document.getElementById('flowerRowTemplate');
                        const newRow = tpl.cloneNode(true);    // clone <tr>
                        newRow.removeAttribute('id');
                        newRow.style.display = '';             // hiển thị
                        bindRowEvents(newRow);
                        updateRowPrice(newRow);
                        document.querySelector('#flowerTable tbody').appendChild(newRow);
                        updateTotalValue();
                    });
        });
    </script>

</body>
</html>
