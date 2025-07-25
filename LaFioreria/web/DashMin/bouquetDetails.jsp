<%-- 
    Document   : blank
    Created on : May 19, 2025, 2:34:20 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List,model.Bouquet, model.Category, model.RawFlower, model.BouquetImage" %>
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
                background-color: #e74c3c; /* đỏ */
            }

            .btn-delete:hover {
                background-color: #c0392b;
            }

            .btn-edit {
                background-color: #3498db; /* xanh dương */
            }

            .btn-edit:hover {
                background-color: #2980b9;
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
                background-color: #e74c3c; /* đỏ */
            }

            .btn-delete:hover {
                background-color: #c0392b;
            }

            .btn-edit {
                background-color: #3498db; /* xanh dương */
            }

            .btn-edit:hover {
                background-color: #2980b9;
            }

            .arrow-btn {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                background-color: transparent; /* bỏ nền */
                border: none;                  /* bỏ viền */
                font-size: 36px;
                color: #bbb;
                cursor: pointer;
                padding: 0;                    /* bỏ padding */
                margin: 0;
                transition: color 0.2s ease;
                z-index: 10;
            }

            .arrow-btn:hover {
                color: #333;
            }

            .left-arrow {
                left: 10px; /* Dịch sát mép trái ảnh */
            }

            .right-arrow {
                right: 5px; /* Dịch sát mép phải ảnh */
            }

            #viewAllImages {
                color: #0d6efd; /* màu xanh Bootstrap mặc định */
                text-decoration: none;
                transition: background-color 0.2s ease, color 0.2s ease;
                padding: 4px 12px;
                border-radius: 6px;
            }

            #viewAllImages:hover {
                background-color: #e0e0e0; /* nền xám nhạt khi hover */
                color: #000000; /* chữ đen khi hover */
                text-decoration: none;
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
                        <a href="${pageContext.request.contextPath}/viewBouquet" class="nav-item nav-link active"><i class="fa fa-table me-2"></i>Bouquet</a>
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

                <div class="background-qvm" style="background-color: #f3f6f9; margin-top: 20px; margin-left: 20px; margin-right: 20px; border-radius: 5px;">
                    <div class="container-fluid py-5 px-4">
                        <div class="d-flex flex-wrap align-items-start gap-5" style="gap: 100px;">


                            <!-- Cột ảnh -->
                            <!-- Hiển thị ảnh chính và nút chuyển ảnh -->
                            <div class="position-relative d-inline-block mb-3">
                                <!-- Nút Prev -->
                                <button id="prevImage"
                                        class="arrow-btn left-arrow"
                                        aria-label="Previous image"
                                        style="margin-left: 25px;">
                                    &#10094;
                                </button>

                                <!-- Ảnh chính -->
                                <img id="mainImage"
                                     src="${pageContext.request.contextPath}/upload/BouquetIMG/${images[0].image_url}"
                                     alt="Bouquet Image"
                                     class="img-fluid bouquet-img mb-2"
                                     style="width: 550px; height: 600px; object-fit: cover; border-radius: 12px;" />

                                <!-- Nút Next -->
                                <button id="nextImage"
                                        class="arrow-btn right-arrow"
                                        aria-label="Next image">
                                    &#10095;
                                </button>

                                <!-- Link mở popup tất cả ảnh: chuyển vào trong div này -->
                                <div class="text-center mt-2">
                                    <a href="#" id="viewAllImages" class="btn btn-link">View All Images</a>
                                </div>
                            </div>


                            <!-- Modal hiển thị tất cả ảnh -->
                            <div class="modal fade" id="allImagesModal" tabindex="-1" aria-labelledby="allImagesModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-scrollable modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">All Images</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="d-flex flex-wrap gap-3 justify-content-center">
                                                <c:forEach var="imageUrl" items="${images}">
                                                    <div class="flex-shrink-0 text-center">
                                                        <img
                                                            src="${pageContext.request.contextPath}/upload/BouquetIMG/${imageUrl.image_url}"
                                                            alt="Bouquet Image"
                                                            class="img-fluid bouquet-img mb-2"
                                                            style="width: 300px; height: 300px; object-fit: cover; border-radius: 8px;" />
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Cột phải: Nội dung -->
                            <input type="hidden" name="id" value="${bouquetDetail.getBouquetId()}">
                            <div class="flex-grow-1 bouquet-content">
                                <div class="bouquet-info p-4 shadow-sm rounded bg-white">
                                    <h1 class="fw-bold mb-4 text-primary">
                                        ${bouquetDetail.getBouquetName()}   
                                    </h1>

                                    <!-- Thông tin cơ bản -->                                                                      
                                    <div class="mb-3">
                                        <label class="form-label fw-semibold">Category:</label>
                                        <h6>${cateName}</h6>
                                    </div>

                                    <div class="mb-4">
                                        <label class="form-label fw-semibold">Description:</label>
                                        <textarea name="bqDescription" class="form-control" rows="4" readonly>${bouquetDetail.getDescription()}</textarea>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label fw-semibold">Status: </label>
                                        <h6>${bouquetDetail.getStatus()}</h6>
                                    </div>

                                    <!-- Flower Table -->
                                    <h5 class="mb-3 text-secondary">Flowers in Bouquet</h5>

                                    <div class="table-responsive mb-3">
                                        <table id="flowerTable" class="table table-bordered align-middle">
                                            <thead class="table-light">
                                                <tr>
                                                    <th>Flower</th>
                                                    <th>Flower Batch</th> <!-- ✅ Thêm -->
                                                    <th>Price per Stem</th>
                                                    <th>Quantity</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <!-- Chỉ duy nhất FOR-EACH, không có row mặc định -->
                                                <c:forEach var="br" items="${flowerInBQ}">
                                                    <tr>
                                                        <!-- Flower -->
                                                        <td>
                                                            <input type="hidden" name="flowerIds" value="${br.getBatchId()}" />
                                                            <select class="form-select form-select-sm flower-select" disabled>
                                                                <c:forEach var="fb" items="${allBatchs}">
                                                                    <option
                                                                        value="${fb.getFlowerId()}"
                                                                        data-price="${fb.getUnitPrice()}"
                                                                        <c:if test="${fb.getBatchId() eq br.getBatchId()}">selected</c:if>>
                                                                        <c:forEach var="af" items="${allFlowers}">
                                                                            <c:if test="${af.getFlowerId() eq fb.getFlowerId()}">
                                                                                ${af.getFlowerName()}
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>

                                                        <!-- ✅ Flower Batch -->
                                                        <td>
                                                            <c:forEach var="fb" items="${allBatchs}">
                                                                <c:if test="${fb.getBatchId() eq br.getBatchId()}">
                                                                    <input type="hidden" name="batchIds" value="${fb.getBatchId()}" />
                                                                    <input type="text" class="form-control form-control-sm" value="${fb.getImportDate()} to ${fb.getExpirationDate()}" readonly />
                                                                </c:if>
                                                            </c:forEach>
                                                        </td>

                                                        <!-- Price -->
                                                        <td>
                                                            <span class="form-text price-text"></span>
                                                            <input type="hidden" class="price-input" name="prices[]" value="0" />
                                                        </td>

                                                        <!-- Quantity -->
                                                        <td>
                                                            <input
                                                                type="number"
                                                                name="quantities"
                                                                value="${br.getQuantity()}"
                                                                min="1"
                                                                step="1"
                                                                readonly
                                                                class="form-control form-control-sm quantity-input"
                                                                />
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                            <tfoot>
                                                <tr>
                                                    <td colspan="5" class="text-start fw-bold text-primary">
                                                        Price:
                                                        <span id="totalValueDisplay">0.00 VND</span>
                                                        <input type="hidden" id="totalValueInput" name="totalValue" value="0" />
                                                        </br>
                                                        Sell Price: <span id="sellValueDisplay">0.00 VND</span>
                                                        <input type="hidden" id="sellValueInput"  name="sellValue"  value="0" />
                                                    </td>
                                                </tr>
                                            </tfoot>
                                        </table>

                                    </div>
                                    <div class="d-flex justify-content-between">
                                        <!-- Delete Button -->
                                        <button type="button"
                                                class="btn btn-delete"
                                                onclick="if (confirm('Do you want to delete?'))
                                                            location.href = '${pageContext.request.contextPath}/deleteBouquet?id=${bouquetDetail.getBouquetId()}';">
                                            Delete
                                        </button>

                                        <!-- Edit Button -->
                                        <button type="button"
                                                class="btn btn-edit"
                                                onclick="location.href = '${pageContext.request.contextPath}/editBouquet?id=${bouquetDetail.getBouquetId()}';">
                                            Edit
                                        </button>

                                    </div>

                                </div>
                            </div>

                        </div>
                    </div>


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
                                                    function formatCurrencyVND(amount) {
                                                        return new Intl.NumberFormat('vi-VN', {
                                                            style: 'currency',
                                                            currency: 'VND'
                                                        }).format(amount);
                                                    }
                                                    function updatePrice(selectElement) {
                                                        const selectedOption = selectElement.options[selectElement.selectedIndex];
                                                        const price = selectedOption ? parseFloat(selectedOption.getAttribute('data-price') || "0") : 0;

                                                        const row = selectElement.closest('tr');
                                                        const priceText = row.querySelector('.price-text');
                                                        const priceInput = row.querySelector('.price-input');

                                                        priceText.textContent = formatCurrencyVND(price);
                                                        priceInput.value = price;

                                                        calculateTotal();
                                                    }

                                                    function calculateTotal() {
                                                        let total = 0;
                                                        const rows = document.querySelectorAll('#flowerTable tbody tr');

                                                        rows.forEach(row => {
                                                            const price = parseFloat(row.querySelector('.price-input')?.value || 0);
                                                            const quantity = parseInt(row.querySelector('input[name^="quantities"]')?.value || 0);
                                                            total += price * quantity;
                                                        });

                                                        // Cập nhật Total Price
                                                        document.getElementById('totalValueDisplay').textContent = formatCurrencyVND(total);
                                                        document.getElementById('totalValueInput').value = total.toFixed(2);

                                                        // Tính và cập nhật Sell Price ra ngoài bảng
                                                        const sellTotal = total * 5;
                                                        document.getElementById('sellValueDisplay').textContent = formatCurrencyVND(sellTotal);
                                                        document.getElementById('sellValueInput').value = sellTotal.toFixed(2);
                                                    }

                                                    function attachEventsToRow(row) {
                                                        const select = row.querySelector('.flower-select');
                                                        const quantityInput = row.querySelector('input[name^="quantities"]');
                                                        const deleteButton = row.querySelector('.btn-outline-danger');

                                                        if (select) {
                                                            updatePrice(select);
                                                            select.addEventListener('change', function () {
                                                                updatePrice(this);
                                                            });
                                                        }

                                                        if (quantityInput) {
                                                            quantityInput.addEventListener('input', calculateTotal);
                                                        }

                                                        if (deleteButton) {
                                                            deleteButton.addEventListener('click', function () {
                                                                row.remove();
                                                                calculateTotal(); // ✅ Update lại khi xóa
                                                            });
                                                        }
                                                    }

                                                    document.addEventListener('DOMContentLoaded', function () {
                                                        // Gắn sự kiện cho dòng đầu tiên mặc định
                                                        document.querySelectorAll('#flowerTable tbody tr').forEach(row => {
                                                            attachEventsToRow(row);
                                                        });

                                                        calculateTotal(); // Khởi tạo tổng ban đầu
                                                    });

                                                    document.getElementById('addFlowerBtn').addEventListener('click', function () {
                                                        const template = document.getElementById('flowerRowTemplate');
                                                        const newRow = template.cloneNode(true);
                                                        newRow.removeAttribute('id');
                                                        newRow.style.display = '';

                                                        document.querySelector('#flowerTable tbody').appendChild(newRow);
                                                        attachEventsToRow(newRow);
                                                        calculateTotal();
                                                    });
        </script>

        <script>
            const imageUrls = [
            <c:forEach var="img" items="${images}" varStatus="status">
            "${pageContext.request.contextPath}/upload/BouquetIMG/${img.image_url}"<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                ];

                let currentIndex = 0;
                const mainImage = document.getElementById("mainImage");
                const prevBtn = document.getElementById("prevImage");
                const nextBtn = document.getElementById("nextImage");

                prevBtn.addEventListener("click", () => {
                    if (currentIndex > 0) {
                        currentIndex--;
                        mainImage.src = imageUrls[currentIndex];
                    }
                });

                nextBtn.addEventListener("click", () => {
                    if (currentIndex < imageUrls.length - 1) {
                        currentIndex++;
                        mainImage.src = imageUrls[currentIndex];
                    }
                });

                const viewAllImagesLink = document.getElementById("viewAllImages");
                const allImagesModal = new bootstrap.Modal(document.getElementById("allImagesModal"));

                viewAllImagesLink.addEventListener("click", (e) => {
                    e.preventDefault();
                    allImagesModal.show();
                });
        </script>
    </body>
</html>
