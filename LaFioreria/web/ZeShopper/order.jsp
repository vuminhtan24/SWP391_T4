<%-- 
    Document   : order
    Created on : Jul 07, 2025, 10:50 PM
    Author     : xAI (via Grok)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Lịch Sử Đơn Hàng | E-Shopper</title>
        <link href="${pageContext.request.contextPath}/ZeShopper/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/responsive.css" rel="stylesheet"> 
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/ZeShopper/images/ico/favicon.ico">
        <style>
            .order-filter {
                margin-bottom: 15px;
                padding: 10px;
            }
            .order-filter select {
                padding: 8px;
                border-radius: 6px;
                border: 1px solid #ccc;
            }
            .order-item {
                border: 1px solid #eee;
                padding: 15px;
                margin-bottom: 15px;
                border-radius: 4px;
            }
            .order-item h4 {
                margin: 0 0 8px 0;
                font-size: 18px;
            }
            .order-item p {
                margin: 5px 0;
                color: #555;
            }
            .order-status {
                font-weight: bold;
                color: #d9534f;
            }
            .order-status-delivered {
                color: #28a745;
            }
            .order-details {
                margin-top: 15px;
                padding: 15px;
                border: 1px solid #ddd;
                border-radius: 4px;
                display: none;
                background-color: #f9f9f9;
            }
            .order-details.active {
                display: block;
            }
            .error-message {
                color: red;
                margin: 15px 0;
                padding: 10px;
                border: 1px solid #f5c6cb;
                border-radius: 4px;
                background-color: #f8d7da;
            }
            .toggle-details {
                background-color: #5cb85c;
                color: white;
                border: none;
                padding: 8px 15px;
                border-radius: 4px;
                cursor: pointer;
                margin-top: 10px;
                margin-right: 10px;
            }
            .toggle-details:hover {
                background-color: #4cae4c;
            }
            .payment-instructions {
                background-color: #e7f3ff;
                border: 1px solid #b3d7ff;
                padding: 10px;
                margin-top: 10px;
                border-radius: 4px;
            }
            .action-buttons {
                margin-top: 10px;
            }
            .action-buttons a, .action-buttons form {
                display: inline-block;
                margin-right: 10px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/ZeShopper/header.jsp"/>
        
        <div class="container" style="margin-top: 20px;">
            <h2>Lịch Sử Đơn Hàng</h2>

            <div class="order-filter">
                <form action="${pageContext.request.contextPath}/ZeShopper/order" method="get">
                    <label for="order-status-filter">Lọc theo trạng thái:</label>
                    <select id="order-status-filter" name="status" onchange="this.form.submit()">
                        <option value="all" ${selectedStatus == 'all' ? 'selected' : ''}>Tất cả</option>
                        <option value="1" ${selectedStatus == '1' ? 'selected' : ''}>Chờ thanh toán</option>
                        <option value="2" ${selectedStatus == '2' ? 'selected' : ''}>Chờ lấy hàng</option>
                        <option value="3" ${selectedStatus == '3' ? 'selected' : ''}>Đang giao</option>
                        <option value="4" ${selectedStatus == '4' ? 'selected' : ''}>Đã giao</option>
                        <option value="5" ${selectedStatus == '5' ? 'selected' : ''}>Đã hủy</option>
                    </select>
                </form>
            </div>

            <c:if test="${not empty message}">
                <div class="alert alert-success">
                    <i class="fa fa-check-circle"></i> ${message}
                </div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="error-message">
                    <i class="fa fa-exclamation-triangle"></i> ${error}
                </div>
            </c:if>

            <c:if test="${empty orders}">
                <div class="empty-orders" style="text-align: center; padding: 50px; color: #666;">
                    <i class="fa fa-shopping-bag fa-5x" style="color: #ccc;"></i>
                    <h3>Chưa có đơn hàng nào</h3>
                    <p>Hãy mua sắm để tạo đơn hàng mới!</p>
                    <a href="${pageContext.request.contextPath}/product" class="btn btn-primary">Tiếp tục mua sắm</a>
                </div>
            </c:if>

            <c:if test="${not empty orders}">
                <c:forEach var="order" items="${orders}" varStatus="loop">
                    <div class="order-item">
                        <h4>Đơn hàng #${order.orderId}</h4>
                        <p>Ngày đặt: ${order.orderDate != null ? order.orderDate : 'N/A'}</p>
                        <p>Khách hàng: ${order.customerName != null ? order.customerName : 'Khách'}</p>
                        <p>Tổng tiền: <fmt:formatNumber value="${order.totalSell != null ? order.totalSell : 0}" pattern="#,##0" /> ₫</p>
                        <p class="order-status ${order.statusId == 4 ? 'order-status-delivered' : ''}">
                            <c:choose>
                                <c:when test="${order.statusId == 1}">Chờ thanh toán</c:when>
                                <c:when test="${order.statusId == 2}">Chờ lấy hàng</c:when>
                                <c:when test="${order.statusId == 3}">Đang giao</c:when>
                                <c:when test="${order.statusId == 4}">Đã giao</c:when>
                                <c:when test="${order.statusId == 5}">Đã hủy</c:when>
                                <c:otherwise>Không xác định</c:otherwise>
                            </c:choose>
                        </p>
                        <c:if test="${order.statusId == 1 && order.paymentMethod == 'VietQR'}">
                            <form action="${pageContext.request.contextPath}/ZeShopper/order" method="post" style="display: inline;" onsubmit="return confirm('Bạn có chắc muốn hủy đơn hàng này?');">
                                <input type="hidden" name="action" value="cancel">
                                <input type="hidden" name="orderId" value="${order.orderId}">
                                <button type="submit" class="btn btn-danger btn-sm"><i class="fa fa-times"></i> Hủy đơn hàng</button>
                            </form>
                        </c:if>
                        <button class="toggle-details" onclick="toggleDetails('order-details-${order.orderId}')">Xem chi tiết</button>
                        <div class="order-details" id="order-details-${order.orderId}">
                            <h4>Chi tiết đơn hàng</h4>
                            <p>Phương thức thanh toán:
                                <c:choose>
                                    <c:when test="${order.paymentMethod == 'VietQR'}">Chuyển khoản VietQR</c:when>
                                    <c:when test="${order.paymentMethod == 'COD'}">Thanh toán khi nhận hàng (COD)</c:when>
                                    <c:otherwise>${order.paymentMethod}</c:otherwise>
                                </c:choose>
                            </p>
                            <c:if test="${order.statusId == 1 && order.paymentMethod == 'VietQR'}">
                                <div class="payment-instructions">
                                    <p><strong>Hướng dẫn thanh toán VietQR:</strong></p>
                                    <p>Ngân hàng: XXX</p>
                                    <p>Số tài khoản: 123456789</p>
                                    <p>Nội dung chuyển khoản: Đơn hàng #${order.orderId}</p>
                                    <img src="${pageContext.request.contextPath}/images/qr_code.jpg" alt="QR Code" style="max-width: 150px;">
                                </div>
                            </c:if>
                            <c:set var="details" value="${orderDetailsList[loop.index]}" />
                            <c:if test="${empty details}">
                                <p>Không có sản phẩm trong đơn hàng này.</p>
                            </c:if>
                            <c:forEach var="item" items="${details}">
                                <p>
                                    Hoa: ${item.bouquetName != null ? item.bouquetName : 'N/A'} 
                                    (Số lượng: ${item.quantity}, 
                                    Giá mỗi sản phẩm: <fmt:formatNumber value="${item.unitPrice}" pattern="#,##0" /> ₫)                                    
                                </p>
                                <p>Phí giao hàng: 30 000₫</p>
                                <img src="${pageContext.request.contextPath}/upload/BouquetIMG/${item.bouquetImage != null ? item.bouquetImage : 'default.jpg'}" 
                                     alt="${item.bouquetName != null ? item.bouquetName : 'N/A'}" 
                                     style="max-width: 100px; max-height: 100px; margin: 5px 0;">
                                <c:if test="${order.statusId == 4}">
                                    <div class="action-buttons">
                                        <a href="${pageContext.request.contextPath}/ZeShopper/feedback?action=write&orderId=${order.orderId}&bouquetId=${item.bouquetId}" 
                                           class="btn btn-primary btn-sm"><i class="fa fa-comment"></i> Viết đánh giá</a>
                                        <a href="${pageContext.request.contextPath}/productDetail?id=${item.bouquetId}" 
                                           class="btn btn-success btn-sm"><i class="fa fa-shopping-cart"></i> Mua lại</a>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>

        <jsp:include page="/ZeShopper/footer.jsp"/>

        <script>
            function toggleDetails(id) {
                const element = document.getElementById(id);
                element.classList.toggle('active');
            }
        </script>
    </body>
</html>