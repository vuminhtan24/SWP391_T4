<%-- 
    Document   : blank
    Created on : May 19, 2025, 2:34:20 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List,model.Bouquet, model.Category, model.RawFlower"%>
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

            .flower-select option.already-selected {
                background-color: #e9ecef;  /* xám nhạt */
                color: #6c757d;             /* chữ xám đậm hơn một chút */
            }

            .preview-img-wrapper {
                position: relative;
                display: inline-block;
                margin: 5px;
                cursor: pointer;
                border: 2px solid transparent;
                border-radius: 4px;
            }
            .preview-img-wrapper.selected {
                border-color: #0d6efd;
            }
            .preview-img {
                max-width: 100px;
                max-height: 100px;
                object-fit: cover;
                border-radius: 4px;
            }
            .btn-delete-single {
                position: absolute;
                top: -6px;
                right: -6px;
                background: rgba(0,0,0,0.6);
                color: #fff;
                border: none;
                border-radius: 50%;
                width: 20px;
                height: 20px;
                line-height: 16px;
                font-size: 14px;
                text-align: center;
                padding: 0;
            }
            /* Ngoại hình ảnh preview bên ngoài */
            #externalPreview img {
                max-width: 150px;
                max-height: 150px;
                object-fit: cover;
                border-radius: 4px;
                border: 1px solid #ccc;
                margin-right: 10px;
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
                        <a href="${pageContext.request.contextPath}/viewBouquet" class="nav-item nav-link "><i class="fa fa-table me-2"></i>Bouquet</a>
                        <a href="${pageContext.request.contextPath}/DashMin/chart.jsp" class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
                        <a href="${pageContext.request.contextPath}/orderManagement" class="nav-item nav-link active"><i class="fa fa-th me-2"></i>Order</a>
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

                <!-- Blank Start -->
                <form action="makeBouquet" method="post" enctype="multipart/form-data">

                    <input type="hidden" name="BouquetId" value="${BouquetId}" />
                    <input type="hidden" name="OrderId" value="${OrderId}" />
                    <input type="hidden" name="OrderItemID" value="${OrderItemID}" />
                    <div class="background-qvm" style="background-color: #f3f6f9; margin-top: 20px; margin-left: 20px; margin-right: 20px; border-radius: 5px;">

                        <div class="container-fluid py-5 px-4">
                            <div style="display: block; width: 100%;">

                                <!-- Cột trái: Ảnh -->
                                <div style="width: 100%; display: flex; justify-content: center; margin-bottom: 24px;">
                                    <div style="display: flex; gap: 16px; align-items: center; max-width: 1000px; flex-wrap: wrap;">

                                        <!-- Ảnh -->
                                        <div style="width: 220px; flex-shrink: 0;">
                                            <c:set var="imageShown" value="false" />
                                            <c:forEach var="imageFiles" items="${images}">
                                                <c:if test="${!imageShown}">
                                                    <img
                                                        src="${pageContext.request.contextPath}/upload/BouquetIMG/${imageFiles.image_url}"
                                                        alt="Bouquet Image"
                                                        class="img-fluid bouquet-img-first"
                                                        style="width: 100%; border-radius: 8px; object-fit: cover;" />
                                                    <c:set var="imageShown" value="true" />
                                                </c:if>
                                            </c:forEach>
                                        </div>

                                        <!-- Customer Request -->
                                        <div style="flex-grow: 1; background-color: #f8f9fa; border: 1px solid #dee2e6; border-radius: 4px; padding: 12px 16px;">
                                            <h6 class="fw-semibold" style="margin: 0;">Customer Request</h6>
                                            <label class="form-label fw-semibold" style="margin-right: 8px; vertical-align: middle;">
                                                Number of Bouquets requested by customer:
                                            </label>
                                            <h6 style="display: inline-block; vertical-align: middle; margin: 0;">
                                                ${oi.getQuantity()}
                                            </h6>
                                        </div>
                                    </div>
                                </div>

                                <!-- Lưu lại ảnh đã có sẵn -->
                                <div id="serverImageInputs">
                                    <c:forEach var="imageFiles" items="${images}">
                                        <input type="hidden" name="existingImageUrls"
                                               value="${pageContext.request.contextPath}/upload/BouquetIMG/${imageFiles.image_url}" />
                                    </c:forEach>
                                </div>

                                <!-- Input upload ảnh (ẩn) -->
                                <div class="container py-4" style="display: none;">
                                    <div class="mb-3">
                                        <label for="imageFiles" class="form-label">Upload Images (max 5)</label>
                                        <input type="file" id="imageFiles" name="imageFiles"
                                               class="form-control" accept=".jpg,.jpeg,.png" multiple
                                               <c:if test="${empty images}"></c:if> />
                                               <small class="form-text text-muted">
                                                   Bạn có thể chọn tối đa 5 ảnh (.jpg, .jpeg, .png).
                                               </small>
                                        </div>
                                    </div>

                                    <!-- Modal preview ảnh -->
                                    <div class="modal fade" id="previewModal" tabindex="-1" aria-labelledby="previewModalLabel" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered modal-lg">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title">Images Preview</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <div id="previewContainer" class="d-flex flex-wrap"></div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button id="btnUploadMore"    type="button" class="btn btn-primary me-auto">Upload More Images</button>
                                                    <button id="btnDeleteSelected"type="button" class="btn btn-danger">Delete Selected</button>
                                                    <button id="btnAccept"        type="button" class="btn btn-success">Accept</button>
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <h6 style="color: red">${error}</h6>


                                <!-- Cột phải: Nội dung -->
                                <div style="width: 100%;">
                                    <input type="hidden" name="id" value="${bouquetDetail.getBouquetId()}">
                                    <div class="bouquet-content" style="width: 100%;">
                                        <div class="bouquet-info p-4 shadow-sm rounded bg-white">
                                            <h1 class="fw-bold mb-4 text-primary">
                                                ${bouquetDetail.getBouquetName()}   
                                            </h1>

                                            <!-- Thông tin cơ bản -->
                                            <div class="mb-3">
                                                <label class="form-label fw-semibold" style="display: inline-block; margin-right: 8px; vertical-align: middle;">
                                                    Category:
                                                </label>
                                                <h6 style="display: inline-block; vertical-align: middle;">
                                                    ${cateName}
                                                </h6>


                                                <div class="mb-4">
                                                    <label class="form-label fw-semibold">Description:</label>
                                                    <textarea name="bqDescription" class="form-control" rows="4" readonly>${bouquetDetail.getDescription()}</textarea>
                                                </div>

                                                <label class="form-label fw-semibold" style="display: inline-block; margin-right: 8px; vertical-align: middle;">
                                                    Status:
                                                </label>
                                                <h6 style="display: inline-block; vertical-align: middle;">
                                                    ${bouquetDetail.getStatus()}
                                                </h6>
                                                </br>

                                                <!-- Flower Table -->
                                                <h5 class="mb-3 text-secondary">Flowers in Bouquet</h5>

                                                <div class="table-responsive mb-3">
                                                    <table id="flowerTable" class="table table-bordered align-middle">
                                                        <thead class="table-light">
                                                            <tr>
                                                                <th>Flower</th>
                                                                <th>Flower Batch</th>
                                                                <th>Unit Price</th>
                                                                <th>Quantity</th>
                                                                <th>Quantity available</th>
                                                                <th>Batch Status</th>
                                                                <th>Flowers needed in ${oi.quantity} Bouquet</th>
                                                                <th></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="br" items="${flowerInBQ}">
                                                                <tr data-batch-id="${br.batchId}" data-quantity="${br.quantity}">
                                                                    <td>
                                                                        <select name="flowerIds" class="form-select form-select-sm flower-select" disabled>
                                                                            <option value="" disabled>-- Select Flower --</option>
                                                                            <c:forEach var="f" items="${allFlowers}">
                                                                                <option value="${f.flowerId}">${f.flowerName}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                        <input type="hidden" name="flowerIds" class="hidden-flower-id" />
                                                                    </td>
                                                                    <td>
                                                                        <select name="batchIds" class="form-select form-select-sm batch-select" disabled>
                                                                            <option value="" disabled>-- Select Batch --</option>
                                                                        </select>
                                                                        <input type="hidden" name="batchIds" value="${br.batchId}" />
                                                                    </td>
                                                                    <td>
                                                                        <span class="form-text price-text">0.00</span>
                                                                        <input type="hidden" class="price-input" name="prices[]" value="0" />
                                                                    </td>
                                                                    <td>
                                                                        <input type="number" name="quantities" class="form-control form-control-sm quantity-input" readonly />
                                                                    </td>
                                                                    <td>
                                                                        <span class="stock-text" name="stock">0</span>
                                                                    </td>
                                                                    <td>
                                                                        <span class="status-text"></span>
                                                                    </td>
                                                                    <td>
                                                                        <span class="needed-text">
                                                                            ${br.quantity * oi.quantity}
                                                                            <c:set var="needed" value="${br.quantity * oi.quantity}" />
                                                                            <c:out value="${needed}" />
                                                                        </span>
                                                                        <input type="hidden" name="flowerNeeded" class="needed-input" value="${br.quantity * oi.quantity}" />
                                                                    </td>
                                                                    <td>
                                                                        <button type="button" class="btn btn-sm btn-outline-danger remove-btn" style="display:none;" disabled>&times;</button>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                        <tfoot>
                                                            <tr>
                                                                <!-- Bên trái: message -->
                                                                <td colspan="3" class="text-start align-middle">
                                                                    <!-- Thông báo stockMessage sẽ do JS xử lý -->
                                                                    <span id="stockMessage" class="fw-semibold"></span>

                                                                    <!-- Nút chỉnh sửa (ẩn mặc định) -->
                                                                    <button type="button" id="editBouquetBtn" style="
                                                                            display: none;
                                                                            margin-top: 8px;
                                                                            padding: 8px 16px;
                                                                            background-color: orange;
                                                                            color: white;
                                                                            border: none;
                                                                            border-radius: 4px;
                                                                            font-weight: bold;
                                                                            cursor: pointer;">
                                                                        Edit Bouquet Order
                                                                    </button>
                                                                </td>

                                                                <!-- Bên phải: Total Cost + Sell Price -->
                                                                <td colspan="5" class="text-end fw-bold text-primary">
                                                                    Total Cost in 1 Bouquet: <span id="totalValueDisplay">0.00 VND</span>
                                                                    <input type="hidden" id="totalValueInput" name="totalValue" value="0" />
                                                                    <br/>
                                                                    Sell Price in 1 Bouquet: <span id="sellValueDisplay">0.00 VND</span>
                                                                    <input type="hidden" id="sellValueInput" name="sellValue" value="0" />
                                                                </td>
                                                            </tr>
                                                        </tfoot>
                                                    </table>

                                                    <div class="mt-3 text-end fw-bold">
                                                        <p>
                                                            Total Cost in Order: 
                                                            <span id="orderCostDisplay">0 ₫</span>
                                                            <input type="hidden" id="orderCostInput" name="orderCost" value="0" />
                                                        </p>
                                                        <p>
                                                            Sell Price in Order: 
                                                            <span id="orderSellDisplay">0 ₫</span>
                                                            <input type="hidden" id="orderSellInput" name="orderSell" value="0" />
                                                        </p>
                                                    </div>

                                                    <div class="d-flex justify-content-between">
                                                        <button type="button" id="addFlowerBtn" class="btn btn-sm btn-outline-primary mb-4" style="display: none" disabled>
                                                            + Add New Flower
                                                        </button>
                                                        <div style="
                                                             display: flex;
                                                             justify-content: space-between;
                                                             align-items: center;
                                                             width: 100%;
                                                             margin-top: 10px;
                                                             ">

                                                            <!-- Bên trái: Nút Back -->
                                                            <a href="${pageContext.request.contextPath}/orderDetail?orderId=${OrderId}" style="
                                                               display: inline-block;
                                                               padding: 8px 16px;
                                                               background-color: white;
                                                               color: #009cff;
                                                               border: 2px solid #009cff;
                                                               text-decoration: none;
                                                               border-radius: 4px;
                                                               font-weight: bold;
                                                               ">
                                                                Back to Order Details
                                                            </a>

                                                            <!-- Bên phải: Nút hoặc Message -->
                                                            <div>
                                                                <c:if test="${oi.status eq 'not done'}">
                                                                    <!-- Nút hoàn tất (ẩn mặc định) -->
                                                                    <button id="btnCompleteBouquet" type="submit" name="action" value="confirm" style="
                                                                            display: none;
                                                                            padding: 8px 16px;
                                                                            background-color: green;
                                                                            color: white;
                                                                            border: none;
                                                                            border-radius: 4px;
                                                                            font-weight: bold;">
                                                                        Complete bouquet creation
                                                                    </button>
                                                                    
                                                                    <!-- Nút yêu cầu thêm hoa (ẩn mặc định) -->
                                                                    <button id="btnRequestFlower" type="submit" name="action" value="request" style="
                                                                            display: none;
                                                                            padding: 8px 16px;
                                                                            background-color: red;
                                                                            color: white;
                                                                            border: none;
                                                                            border-radius: 4px;
                                                                            font-weight: bold;">
                                                                        Request more Flower
                                                                    </button>
                                                                </c:if>


                                                                <c:if test="${oi.status eq 'done'}">
                                                                    <h6 style="
                                                                        color: green;
                                                                        margin: 0;
                                                                        font-weight: bold;
                                                                        text-align: right;">
                                                                        You have completed this order
                                                                    </h6>
                                                                </c:if>
                                                            </div>

                                                        </div>
                                                    </div>

                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                    </form>
                                    <!-- TEMPLATE để clone (ẩn) -->

                                    <table style="display:none;">
                                        <tbody>
                                            <tr id="flowerRowTemplate">
                                                <td>
                                                    <select name="flowerIds" class="form-select form-select-sm flower-select">
                                                        <option value="" disabled selected>-- Select Flower --</option>
                                                        <c:forEach var="f" items="${allFlowers}">
                                                            <option value="${f.flowerId}">${f.flowerName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <select name="batchIds" class="form-select form-select-sm batch-select">
                                                        <option value="" disabled selected>-- Select Batch --</option>
                                                    </select>
                                                </td>
                                                <td>
                                                    <span class="form-text price-text">0.00 VND</span>
                                                    <input type="hidden" class="price-input" name="prices[]" value="0" />
                                                </td>
                                                <td>
                                                    <input type="number"
                                                           name="quantities"
                                                           value="1"
                                                           min="1" step="1"
                                                           required
                                                           class="form-control form-control-sm quantity-input" />
                                                </td>
                                                <td>
                                                    <span class="stock-text">0</span>
                                                </td>
                                                <td>
                                                    <span class="status-text"></span>
                                                </td>
                                                <td>
                                                    <span class="needed-text">0</span>
                                                </td>
                                                <td>
                                                    <button type="button" class="btn btn-sm btn-outline-danger remove-btn">&times;</button>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>



                                    <!-- Blank End -->
                                </div>
                            </div>   
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

                        <!-- Content End -->


                        <!-- Back to Top -->
                        <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>


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
                            const BOUQUET_QTY = ${oi.quantity};
                            const ORDER_DONE = '${oi.status}' === 'done';
                            const allBatchs = [
                            <c:forEach var="batch" items="${allBatchs}" varStatus="loop">
                            {
                            batchID: '${batch.batchId}',
                                    flowerID: '${batch.flowerId}',
                                    importDate: '${batch.importDate}',
                                    expirationDate: '${batch.expirationDate}',
                                    unitPrice: ${batch.unitPrice},
                                    quantity: ${batch.quantity},
                                    status: '${batch.status}'
                            }<c:if test="${!loop.last}">,</c:if>
                            </c:forEach>
                            ];
                            document.addEventListener('DOMContentLoaded', () => {
                                const SELL_MULTIPLIER = 5;
                                function formatCurrency(val) {
                                    return new Intl.NumberFormat('vi-VN', {
                                        style: 'currency',
                                        currency: 'VND'
                                    }).format(val);
                                }

                                function fillBatchOptions(row, flowerID) {
                                    const batchSel = row.querySelector('.batch-select');
                                    batchSel.innerHTML = '<option value="" disabled selected>-- Select Batch --</option>';
                                    allBatchs.forEach(b => {
                                        if (b.flowerID == flowerID) {
                                            const opt = document.createElement('option');
                                            opt.value = b.batchID;
                                            opt.dataset.price = b.unitPrice;
                                            opt.dataset.status = b.status;
                                            opt.dataset.stock = b.quantity;
                                            opt.textContent = b.importDate + ' to ' + b.expirationDate;
                                            batchSel.appendChild(opt);
                                        }
                                    });
                                }

                                function updateRow(row) {
                                    const sel = row.querySelector('.batch-select').selectedOptions[0] || {};
                                    const unit = parseFloat(sel.dataset.price) || 0;
                                    const stock = parseInt(sel.dataset.stock, 10) || 0;
                                    const status = sel.dataset.status || '';
                                    const perQty = parseInt(row.querySelector('.quantity-input').value, 10) || 0;
                                    // Unit price
                                    row.querySelector('.price-input').value = unit;
                                    row.querySelector('.price-text').textContent = formatCurrency(unit);
                                    // Stock
                                    row.querySelector('.stock-text').textContent = stock;
                                    // Status
                                    row.querySelector('.status-text').textContent = status;
                                    // Flowers needed
                                    const needed = perQty * BOUQUET_QTY;
                                    row.querySelector('.needed-text').textContent = needed;
                                    row.querySelector('.needed-input').value = needed;
                                    // Cập nhật hidden input cho flowerId
                                    let flowerId = row.querySelector('.flower-select').value;
                                    let flowerHidden = row.querySelector('input[name="flowerIds"]');
                                    if (!flowerHidden) {
                                        flowerHidden = document.createElement('input');
                                        flowerHidden.type = 'hidden';
                                        flowerHidden.name = 'flowerIds';
                                        row.appendChild(flowerHidden);
                                    }
                                    flowerHidden.value = flowerId;
                                    updateTotals();
                                }

                                function updateTotals() {
                                    let total = 0;
                                    let insufficientFlowers = [];
                                    let nonFreshFlowers = [];
                                    document.querySelectorAll('#flowerTable tbody tr').forEach(row => {
                                        const price = parseFloat(row.querySelector('.price-input').value) || 0;
                                        const qty = parseInt(row.querySelector('.quantity-input').value, 10) || 0;
                                        total += price * qty;
                                        const needed = qty * BOUQUET_QTY;
                                        const batchSel = row.querySelector('.batch-select');
                                        const flowerSel = row.querySelector('.flower-select');
                                        const sel = batchSel.selectedOptions[0];
                                        const available = sel ? parseInt(sel.dataset.stock || 0, 10) : 0;
                                        // Check stock
                                        if (needed > available && flowerSel && flowerSel.selectedIndex > 0) {
                                            const flowerName = flowerSel.options[flowerSel.selectedIndex].text.trim();
                                            if (!insufficientFlowers.includes(flowerName)) {
                                                insufficientFlowers.push(flowerName);
                                            }
                                        }

                                        // Check non-fresh batch
                                        const status = sel ? sel.dataset.status || '' : '';
                                        if (status.toLowerCase() !== 'fresh' && flowerSel && flowerSel.selectedIndex > 0) {
                                            const flowerName = flowerSel.options[flowerSel.selectedIndex].text.trim();
                                            if (!nonFreshFlowers.includes(flowerName)) {
                                                nonFreshFlowers.push(flowerName);
                                            }
                                        }
                                    });
                                    // Total cost
                                    document.getElementById('totalValueDisplay').textContent = formatCurrency(total);
                                    document.getElementById('totalValueInput').value = total;
                                    const sell = total * SELL_MULTIPLIER;
                                    document.getElementById('sellValueDisplay').textContent = formatCurrency(sell);
                                    document.getElementById('sellValueInput').value = sell;
                                    const orderCost = total * BOUQUET_QTY;
                                    document.getElementById('orderCostDisplay').textContent = formatCurrency(orderCost);
                                    document.getElementById('orderCostInput').value = orderCost;
                                    const orderSell = sell * BOUQUET_QTY;
                                    document.getElementById('orderSellDisplay').textContent = formatCurrency(orderSell);
                                    document.getElementById('orderSellInput').value = orderSell;
                                    // Stock message
                                    const messageEl = document.getElementById('stockMessage');
                                    const editBtn = document.getElementById('editBouquetBtn');
                                    let stockStatus = 0; // Mặc định là không đủ

                                    if (ORDER_DONE) {
                                        // Nếu đơn hàng đã hoàn tất, luôn hiển thị thành công
                                        messageEl.textContent = 'You can make this bouquet order';
                                        messageEl.className = 'text-success fw-semibold';
                                        stockStatus = 1;
                                        if (editBtn)
                                            editBtn.style.display = 'none';
                                    } else {
                                        if (insufficientFlowers.length === 0) {
                                            messageEl.textContent = 'You can make this bouquet order';
                                            messageEl.className = 'text-success fw-semibold';
                                            stockStatus = 1;
                                            if (editBtn)
                                                editBtn.style.display = 'none';
                                        } else {
                                            messageEl.textContent = 'There are not enough flowers in stock to fulfill this order. Insufficient: ' + insufficientFlowers.join(', ');
                                            messageEl.className = 'text-danger fw-semibold';
                                            stockStatus = 0;
                                            if (editBtn)
                                                editBtn.style.display = 'inline-block';
                                        }
                                    }

                                    const btnComplete = document.getElementById('btnCompleteBouquet');
                                    const btnRequest = document.getElementById('btnRequestFlower');

                                    if (ORDER_DONE) {
                                        if (btnComplete)
                                            btnComplete.style.display = 'none';
                                        if (btnRequest)
                                            btnRequest.style.display = 'none';
                                    } else {
                                        if (insufficientFlowers.length === 0) {
                                            if (btnComplete)
                                                btnComplete.style.display = 'inline-block';
                                            if (btnRequest)
                                                btnRequest.style.display = 'none';
                                        } else {
                                            if (btnComplete)
                                                btnComplete.style.display = 'none';
                                            if (btnRequest)
                                                btnRequest.style.display = 'inline-block';
                                        }
                                    }
                                }

                                function bindRow(row) {
                                    const flowerSel = row.querySelector('.flower-select');
                                    const batchSel = row.querySelector('.batch-select');
                                    const qtyInput = row.querySelector('.quantity-input');
                                    const removeBtn = row.querySelector('.remove-btn');
                                    flowerSel.addEventListener('change', () => {
                                        fillBatchOptions(row, flowerSel.value);
                                        updateRow(row);
                                    });
                                    batchSel.addEventListener('change', () => updateRow(row));
                                    qtyInput.addEventListener('input', () => updateRow(row));
                                    removeBtn.addEventListener('click', () => {
                                        row.remove();
                                        updateTotals();
                                    });
                                }

                                // Init existing rows
                                document.querySelectorAll('#flowerTable tbody tr').forEach(row => {
                                    const batchID = row.dataset.batchId;
                                    const brQty = parseInt(row.dataset.quantity, 10);
                                    const batch = allBatchs.find(b => b.batchID == batchID);
                                    if (batch) {
                                        row.querySelector('.flower-select').value = batch.flowerID;
                                        fillBatchOptions(row, batch.flowerID);
                                        row.querySelector('.batch-select').value = batchID;
                                        row.querySelector('.quantity-input').value = brQty;
                                    }

                                    bindRow(row);
                                    updateRow(row);
                                });
                                // Add new flower row
                                document.getElementById('addFlowerBtn').addEventListener('click', () => {
                                    const tpl = document.getElementById('flowerRowTemplate');
                                    const newRow = tpl.cloneNode(true);
                                    newRow.removeAttribute('id');
                                    newRow.style.display = '';
                                    newRow.querySelector('.flower-select').value = '';
                                    newRow.querySelector('.batch-select').innerHTML = '<option value="" disabled selected>-- Select Batch --</option>';
                                    newRow.querySelector('.quantity-input').value = 1;
                                    newRow.querySelector('.price-text').textContent = formatCurrency(0);
                                    newRow.querySelector('.price-input').value = 0;
                                    newRow.querySelector('.stock-text').textContent = '0';
                                    newRow.querySelector('.status-text').textContent = '';
                                    newRow.querySelector('.needed-text').textContent = '0';
                                    document.querySelector('#flowerTable tbody').appendChild(newRow);
                                    bindRow(newRow);
                                });
                                // Edit button: Enable batch-select
                                const editBtn = document.getElementById('editBouquetBtn');
                                if (editBtn) {
                                    editBtn.addEventListener('click', () => {
                                        document.querySelectorAll('.batch-select').forEach(sel => {
                                            sel.disabled = false;
                                            sel.classList.add('border-warning'); // tuỳ chọn: highlight viền vàng
                                        });

                                    });
                                }
                            });
                        </script>

                        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                        <script>
                            document.addEventListener('DOMContentLoaded', () => {
                                const MAX_FILES = 5;
                                const fileInput = document.getElementById('imageFiles');
                                const previewModal = new bootstrap.Modal(document.getElementById('previewModal'));
                                const previewContainer = document.getElementById('previewContainer');
                                const btnUploadMore = document.getElementById('btnUploadMore');
                                const btnDeleteSelected = document.getElementById('btnDeleteSelected');
                                const btnAccept = document.getElementById('btnAccept');
                                const serverImageInputsDiv = document.getElementById('serverImageInputs');
                                const triggerImg = document.querySelector('.bouquet-img-first');
                                let serverImageUrls = [
                            <c:forEach items="${images}" var="img" varStatus="status">
                                '${pageContext.request.contextPath}/upload/BouquetIMG/${img.image_url}'<c:if test="${!status.last}">,</c:if>
                            </c:forEach>
                                        ];
                                        let acceptedFiles = [];
                                        let selectedIndices = new Set();
                                        function getCurrentItems() {
                                            const items = [];
                                            serverImageUrls.forEach(url => items.push({url}));
                                            acceptedFiles.forEach(file => items.push({file}));
                                            return items.slice(0, MAX_FILES);
                                        }

                                        function updateServerInputs() {
                                            serverImageInputsDiv.innerHTML = '';
                                            serverImageUrls.forEach(url => {
                                                const inp = document.createElement('input');
                                                inp.type = 'hidden';
                                                inp.name = 'existingImageUrls';
                                                inp.value = url;
                                                serverImageInputsDiv.appendChild(inp);
                                            });
                                        }

                                        function updateInputFiles() {
                                            const dt = new DataTransfer();
                                            acceptedFiles.forEach(f => dt.items.add(f));
                                            fileInput.files = dt.files;
                                        }

                                        function renderPreview() {
                                            const items = getCurrentItems();
                                            previewContainer.innerHTML = '';
                                            items.forEach((item, idx) => {
                                                const wrap = document.createElement('div');
                                                wrap.className = 'preview-img-wrapper';
                                                if (selectedIndices.has(idx))
                                                    wrap.classList.add('selected');
                                                wrap.addEventListener('click', () => {
                                                    if (selectedIndices.has(idx)) {
                                                        selectedIndices.delete(idx);
                                                        wrap.classList.remove('selected');
                                                    } else {
                                                        selectedIndices.add(idx);
                                                        wrap.classList.add('selected');
                                                    }
                                                    btnDeleteSelected.disabled = selectedIndices.size === 0;
                                                });
                                                const img = document.createElement('img');
                                                img.className = 'preview-img';
                                                img.src = item.file ? URL.createObjectURL(item.file) : item.url;
                                                wrap.appendChild(img);
                                                const btnDel = document.createElement('button');
                                                btnDel.type = 'button';
                                                btnDel.className = 'btn-delete-single';
                                                btnDel.innerText = '×';
                                                btnDel.addEventListener('click', ev => {
                                                    ev.stopPropagation();
                                                    if (item.file)
                                                        acceptedFiles = acceptedFiles.filter(f => f !== item.file);
                                                    else
                                                        serverImageUrls = serverImageUrls.filter(u => u !== item.url);
                                                    updateInputFiles();
                                                    updateServerInputs();
                                                    selectedIndices.clear();
                                                    renderPreview();
                                                });
                                                wrap.appendChild(btnDel);
                                                previewContainer.appendChild(wrap);
                                            });
                                            btnDeleteSelected.disabled = true;
                                        }

                                        // Khi click vào ảnh đầu tiên .bouquet-img-first
                                        if (triggerImg) {
                                            triggerImg.addEventListener('click', () => {
                                                selectedIndices.clear();
                                                updateServerInputs();
                                                updateInputFiles();
                                                renderPreview();
                                                previewModal.show();
                                            });
                                        }

                                        btnUploadMore.addEventListener('click', () => fileInput.click());
                                        fileInput.addEventListener('change', e => {
                                            const newFiles = Array.from(e.target.files).filter(f => f.type.startsWith('image/'));
                                            const slots = MAX_FILES - (serverImageUrls.length + acceptedFiles.length);
                                            if (slots <= 0) {
                                                alert(`Bạn chỉ được tối đa ${MAX_FILES} ảnh.`);
                                            } else {
                                                acceptedFiles = acceptedFiles.concat(newFiles.slice(0, slots));
                                                updateInputFiles();
                                                renderPreview();
                                            }
                                            e.target.value = '';
                                        });
                                        btnDeleteSelected.addEventListener('click', () => {
                                            Array.from(selectedIndices)
                                                    .sort((a, b) => b - a)
                                                    .forEach(idx => {
                                                        const item = getCurrentItems()[idx];
                                                        if (item.file)
                                                            acceptedFiles = acceptedFiles.filter(f => f !== item.file);
                                                        else
                                                            serverImageUrls = serverImageUrls.filter(u => u !== item.url);
                                                    });
                                            selectedIndices.clear();
                                            updateInputFiles();
                                            updateServerInputs();
                                            renderPreview();
                                        });
                                        btnAccept.addEventListener('click', () => {
                                            updateInputFiles();
                                            updateServerInputs();
                                            previewModal.hide();
                                        });
                                    });
                        </script>
                        </body>
                        </html>
