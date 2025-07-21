<%-- 
    Document   : blank
    Created on : May 19, 2025, 2:34:20 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List,model.Bouquet, model.Category, model.RawFlower"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />
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
                            <h6 class="mb-0">${sessionScope.currentAcc.getFullname()}</h6>
                            <span>Admin</span>
                        </div>
                    </div>
                    <c:choose>
                        <c:when test="${sessionScope.currentAcc.getRole() != 2}">
                            <div class="navbar-nav w-100">
                                <a href="${pageContext.request.contextPath}/DashMin/admin" class="nav-item nav-link"><i class="fa fa-tachometer-alt me-2"></i>Dashboard</a>
                                <div class="nav-item dropdown">
                                    <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Elements</a>
                                    <div class="dropdown-menu bg-transparent border-0">
                                        <a href="${pageContext.request.contextPath}/DashMin/button.jsp" class="dropdown-item active">Buttons</a>
                                        <a href="${pageContext.request.contextPath}/DashMin/typography.jsp" class="dropdown-item">Typography</a>
                                        <a href="${pageContext.request.contextPath}/DashMin/element.jsp" class="dropdown-item">Other Elements</a>
                                    </div>
                                </div>
                                <a href="${pageContext.request.contextPath}/DashMin/widget.jsp" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Widgets</a>
                                <a href="${pageContext.request.contextPath}/DashMin/form.jsp" class="nav-item nav-link"><i class="fa fa-keyboard me-2"></i>Forms</a>
                                <a href="${pageContext.request.contextPath}/ViewUserList" class="nav-item nav-link"><i class="fa fa-table me-2"></i>User</a>
                                <a href="${pageContext.request.contextPath}/viewBouquet" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Bouquet</a>
                                <a href="${pageContext.request.contextPath}/DashMin/chart.jsp" class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
                                <div class="nav-item dropdown">
                                    <a href="#" class="nav-link dropdown-toggle active" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Order</a>
                                    <div class="dropdown-menu bg-transparent border-0">
                                        <a href="${pageContext.request.contextPath}/orderManagement" class="dropdown-item">Order Management</a>
                                        <a href="${pageContext.request.contextPath}/orderDetail" class="dropdown-item active">Order Details</a>
                                    </div>
                                </div>
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
                </c:when>  
                <c:otherwise>
                    <div class="navbar-nav w-100">
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle active" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Order</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/orderManagement" class="dropdown-item active">Order Management</a>
                                <a href="${pageContext.request.contextPath}/orderDetail" class="dropdown-item">Order Details</a>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="nav-item nav-link"><i class="fa fa-table me-2"></i>RawFlower</a>

                        <a href="${pageContext.request.contextPath}" class="nav-item nav-link"><i class="fa fa-table me-2"></i>La Fioreria</a>
                    </div>
                </nav>
            </div>
        </c:otherwise>    
    </c:choose>  
    <!-- Sidebar End -->


    <!-- Content Start -->
    <div class="content">
        <jsp:include page="/DashMin/navbar.jsp"/> <!-- nav bar -->

        <!-- Blank Start -->
        <!-- WholeSale Quotation Start -->
        <div class="container" style="margin-top: 50px">
            <div class="row" style="display: flex; flex-wrap: wrap;">
                <!-- Cột ảnh -->
                <!-- Hiển thị ảnh chính và nút chuyển ảnh -->
                <div class="col-md-4" style="flex: 0 0 35%; max-width: 35%;">
                    <div class="position-relative d-inline-block mb-3">
                        <!-- Nút Prev -->


                        <!-- Ảnh chính -->
                        <img id="mainImage"
                             src="${pageContext.request.contextPath}/upload/BouquetIMG/${listImage[0].image_url}"
                             alt="Bouquet Image"
                             class="img-fluid bouquet-img mb-2"
                             style="width: 550px; max-height: 100%; object-fit: cover; border-radius: 12px;" />

                        <!-- Nút Next -->


                        <!-- Link mở popup tất cả ảnh: chuyển vào trong div này -->

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
                                        <c:forEach var="imageUrl" items="${listImage}">
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
                </div>

                <!-- Cột phải: Nội dung -->
                <form class="col-md-8" action="makeBouquet" method="post" style="flex: 0 0 65%; max-width: 65%; width: 100%;">
                    <input type="hidden" name="orderType" value="${orderType}">
                    <input type="hidden" name="bouquetId" value="${bouquet.getBouquetId()}">
                    <input type="hidden" name="orderId" value="${orderItem.getOrderId()}">
                    <input type="hidden" name="orderItemId" value="${orderItem.getOrderItemId()}">

                    <div class="p-4 shadow-sm rounded bg-white w-100">
                        <h1 class="fw-bold mb-4 text-primary">${bouquet.getBouquetName()}</h1>

                        <!-- Thông tin cơ bản -->
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Status:</label>
                            <h6>${orderItem.getStatus()}</h6>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Requested Quantity:</label>
                            <h6>${orderItem.getQuantity()}</h6>
                        </div>

                        <!-- Flower Table -->
                        <h5 class="mb-3 text-secondary">Flowers in Bouquet</h5>

                        <div class="table-responsive mb-3">
                            <c:if test="${orderType eq 'wholesale'}">
                                <c:if test="${not allAdded}">
                                    <table id="flowerTable" class="table table-bordered align-middle w-100">
                                        <thead class="table-light">
                                            <tr>
                                                <th>Flower Name</th>
                                                <th>Quantity in one Bouquet</th>
                                                <th>Quoted Price</th>
                                                <th>Total Quantity Needed</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="wsDetail" items="${wholesaleDetails}">
                                                <tr>
                                                    <td>
                                                        <c:forEach var="flower" items="${allFlowers}">
                                                            <c:if test="${flower.getFlowerId() eq wsDetail.getFlowerId()}">
                                                                ${flower.getFlowerName()}
                                                            </c:if>
                                                        </c:forEach>
                                                        <input type="hidden" name="flowerIds[]" value="${wsDetail.getFlowerId()}" />
                                                    </td>
                                                    <td>
                                                        ${wsDetail.getFlowerQuantityInBouquet()}
                                                        <input type="hidden" name="flowerQuantities[]" value="${wsDetail.getFlowerQuantityInBouquet()}" />
                                                    </td>
                                                    <td>
                                                        <fmt:formatNumber value="${wsDetail.getFlowerWholesalePrice()}" type="number" groupingUsed="true" /> ₫
                                                        <input type="hidden" name="flowerWholesalePrices[]" value="${wsDetail.getFlowerWholesalePrice()}" />
                                                    </td>
                                                    <td>
                                                        ${wsDetail.getTotalFlowerQuantity()}
                                                        <input type="hidden" name="totalFlowerQuantities[]" value="${wsDetail.getTotalFlowerQuantity()}" />
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <td colspan="4">
                                                    <div class="d-flex flex-column flex-md-row justify-content-between align-items-start flex-wrap w-100">
                                                        <div class="summary-results fw-bold text-primary">
                                                            <div>
                                                                WholeSale Order Expense per Unit:
                                                                <fmt:formatNumber value="${orderItem.getUnitPrice()}" type="number" groupingUsed="true" /> ₫
                                                            </div>
                                                            <div>
                                                                WholeSale Order Price per Unit:
                                                                <fmt:formatNumber value="${orderItem.getSellPrice()}" type="number" groupingUsed="true" /> ₫
                                                            </div>
                                                            <div>
                                                                Total WholeSale Order Price:
                                                                <fmt:formatNumber value="${orderItem.getSellPrice() * orderItem.getQuantity()}" type="number" groupingUsed="true" /> ₫
                                                            </div>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </c:if>
                                <c:if test="${allAdded}">
                                    <table id="flowerTable" class="table table-bordered align-middle w-100">
                                        <thead class="table-light">
                                            <tr>
                                                <th>Flower Name</th>
                                                <th>Flower Batch</th>
                                                <th>Quantity in one Bouquet</th>
                                                <th>Quoted Price</th>
                                                <th>Total Quantity Needed</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="wsDetail" items="${wholesaleDetails}">
                                                <tr>
                                                    <td>
                                                        <c:forEach var="flower" items="${allFlowers}">
                                                            <c:if test="${flower.getFlowerId() eq wsDetail.getFlowerId()}">
                                                                ${flower.getFlowerName()}
                                                            </c:if>
                                                        </c:forEach>
                                                        <input type="hidden" name="flowerIds[]" value="${wsDetail.getFlowerId()}" />
                                                    </td>
                                                    <td>
                                                        <c:set var="latestBatch" value="" />
                                                        <c:set var="latestDate" value="" />
                                                        <c:forEach var="fbBatch" items="${allBatchs}">
                                                            <c:if test="${fbBatch.flowerId eq wsDetail.flowerId and fbBatch.unitPrice eq wsDetail.flowerWholesalePrice}">
                                                                <c:if test="${empty latestDate or fbBatch.importDate gt latestDate}">
                                                                    <c:set var="latestDate" value="${fbBatch.importDate}" />
                                                                    <c:set var="latestBatch" value="${fbBatch}" />
                                                                </c:if>
                                                            </c:if>
                                                        </c:forEach>

                                                        <!-- Sau vòng lặp, dùng latestBatch -->
                                                        ${latestBatch.importDate} to ${latestBatch.expirationDate}
                                                        <input type="hidden" name="batchIds[]" value="${latestBatch.batchId}" />

                                                    </td>    
                                                    <td>
                                                        ${wsDetail.getFlowerQuantityInBouquet()}
                                                        <input type="hidden" name="flowerQuantities[]" value="${wsDetail.getFlowerQuantityInBouquet()}" />
                                                    </td>
                                                    <td>
                                                        <fmt:formatNumber value="${wsDetail.getFlowerWholesalePrice()}" type="number" groupingUsed="true" /> ₫
                                                        <input type="hidden" name="flowerWholesalePrices[]" value="${wsDetail.getFlowerWholesalePrice()}" />
                                                    </td>
                                                    <td>
                                                        ${wsDetail.getTotalFlowerQuantity()}
                                                        <input type="hidden" name="totalFlowerQuantities[]" value="${wsDetail.getTotalFlowerQuantity()}" />
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <td colspan="4">
                                                    <div class="d-flex flex-column flex-md-row justify-content-between align-items-start flex-wrap w-100">
                                                        <div class="summary-results fw-bold text-primary">
                                                            <div>
                                                                WholeSale Order Expense per Unit:
                                                                <fmt:formatNumber value="${orderItem.getUnitPrice()}" type="number" groupingUsed="true" /> ₫
                                                            </div>
                                                            <div>
                                                                WholeSale Order Price per Unit:
                                                                <fmt:formatNumber value="${orderItem.getSellPrice()}" type="number" groupingUsed="true" /> ₫
                                                            </div>
                                                            <div>
                                                                Total WholeSale Order Price:
                                                                <fmt:formatNumber value="${orderItem.getSellPrice() * orderItem.getQuantity()}" type="number" groupingUsed="true" /> ₫
                                                            </div>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </tfoot>
                                    </table>   
                                </c:if>
                            </c:if>
                            <c:if test="${orderType eq 'retail'}">
                                <table id="flowerTable" class="table table-bordered align-middle w-100">
                                    <thead class="table-light">
                                        <tr>
                                            <th>Flower Name</th>
                                            <th>Flower Batch</th>
                                            <th>Quantity in one Bouquet</th>
                                            <th>Quoted Price</th>
                                            <th>Total Quantity Needed</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="rtDetail" items="${orderDetails}">
                                            <tr>
                                                <td>
                                                    <c:forEach var="flower" items="${allFlowers}">
                                                        <c:if test="${flower.getFlowerId() eq wsDetail.getFlowerId()}">
                                                            ${flower.getFlowerName()}
                                                        </c:if>
                                                    </c:forEach>
                                                    <input type="hidden" name="flowerIds[]" value="${wsDetail.getFlowerId()}" />
                                                </td>
                                                <td>
                                                    <c:set var="latestBatch" value="" />
                                                    <c:set var="latestDate" value="" />
                                                    <c:forEach var="fbBatch" items="${allBatchs}">
                                                        <c:if test="${fbBatch.flowerId eq wsDetail.flowerId and fbBatch.unitPrice eq wsDetail.flowerWholesalePrice}">
                                                            <c:if test="${empty latestDate or fbBatch.importDate gt latestDate}">
                                                                <c:set var="latestDate" value="${fbBatch.importDate}" />
                                                                <c:set var="latestBatch" value="${fbBatch}" />
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>

                                                    <!-- Sau vòng lặp, dùng latestBatch -->
                                                    ${latestBatch.importDate} to ${latestBatch.expirationDate}
                                                    <input type="hidden" name="batchIds[]" value="${latestBatch.batchId}" />

                                                </td>    
                                                <td>
                                                    ${wsDetail.getFlowerQuantityInBouquet()}
                                                    <input type="hidden" name="flowerQuantities[]" value="${wsDetail.getFlowerQuantityInBouquet()}" />
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${wsDetail.getFlowerWholesalePrice()}" type="number" groupingUsed="true" /> ₫
                                                    <input type="hidden" name="flowerWholesalePrices[]" value="${wsDetail.getFlowerWholesalePrice()}" />
                                                </td>
                                                <td>
                                                    ${wsDetail.getTotalFlowerQuantity()}
                                                    <input type="hidden" name="totalFlowerQuantities[]" value="${wsDetail.getTotalFlowerQuantity()}" />
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <td colspan="4">
                                                <div class="d-flex flex-column flex-md-row justify-content-between align-items-start flex-wrap w-100">
                                                    <div class="summary-results fw-bold text-primary">
                                                        <div>
                                                            WholeSale Order Expense per Unit:
                                                            <fmt:formatNumber value="${orderItem.getUnitPrice()}" type="number" groupingUsed="true" /> ₫
                                                        </div>
                                                        <div>
                                                            WholeSale Order Price per Unit:
                                                            <fmt:formatNumber value="${orderItem.getSellPrice()}" type="number" groupingUsed="true" /> ₫
                                                        </div>
                                                        <div>
                                                            Total WholeSale Order Price:
                                                            <fmt:formatNumber value="${orderItem.getSellPrice() * orderItem.getQuantity()}" type="number" groupingUsed="true" /> ₫
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </tfoot>
                                </table>  
                            </c:if>
                        </div>

                        <!-- Buttons -->
                        <div class="d-flex flex-column flex-md-row justify-content-between align-items-center w-100 mt-3">
                            <c:choose>
                                <c:when test="${orderItem.getStatus() eq 'not done'}">
                                    <button type="submit" name="action" value="request" class="btn btn-success mb-2 mb-md-0">
                                        Request Flower for WholeSale Order
                                    </button>
                                </c:when>
                                <c:when test="${orderItem.getStatus() eq 'Requested'}">
                                    <p>Wait for Admin</p>
                                </c:when>
                                <c:when test="${orderItem.getStatus() eq 'Added'}">
                                    <button type="submit" name="action" value="complete" class="btn btn-success mb-2 mb-md-0">
                                        Complete WholeSale Order
                                    </button>
                                </c:when>    
                                <c:otherwise>
                                    <p>You have completed this order</p>
                                </c:otherwise>   
                            </c:choose>
                            <a href="${pageContext.request.contextPath}/orderDetail?orderId=${orderItem.getOrderId()}"
                               class="btn btn-secondary">
                                Back to List
                            </a>
                        </div>
                    </div>
                </form>

            </div>
        </div> 
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
