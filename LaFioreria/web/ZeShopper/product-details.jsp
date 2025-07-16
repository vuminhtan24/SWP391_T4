<%-- 
    Document   : product-details
    Created on : May 19, 2025, 8:46:26 AM
    Author     : ADMIN
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
        <title>Chi Tiết Sản Phẩm | E-Shopper</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"/>
        <link href="${pageContext.request.contextPath}/ZeShopper/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/responsive.css" rel="stylesheet">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/ZeShopper/images/ico/favicon.ico">
        <style>
            .product-card {
                background-color: #fff;
                border: 1px solid #ededed;
                box-shadow: 0 2px 4px rgba(0,0,0,0.05);
                border-radius: 4px;
                overflow: hidden;
                display: flex;
                flex-direction: column;
                height: 100%;
            }
            .product-card__image {
                width: 100%;
                height: 200px;
                background-color: #f9f9f9;
                display: flex;
                align-items: center;
                justify-content: center;
                overflow: hidden;
            }
            .product-card__image img {
                width: auto;
                height: 100%;
                object-fit: contain;
                display: block;
            }
            .product-card__body {
                padding: 15px;
                flex: 1;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
            }
            .product-card__title {
                font-size: 1.4rem;
                color: #337ab7;
                margin: 0 0 10px 0;
                text-align: center;
                line-height: 1.2;
            }
            .product-card__title a {
                color: inherit;
                text-decoration: none;
            }
            .product-card__title a:hover {
                text-decoration: underline;
            }
            .product-card__price {
                font-size: 1rem;
                color: #777;
                text-align: center;
                margin: 0 0 15px 0;
            }
            .product-card__button {
                width: 100%;
                font-size: 1rem;
                background-color: #f5f5f0;
                color: #444;
                border: 1px solid #eee;
                padding: 10px 0;
                text-align: center;
                border-radius: 2px;
                transition: background-color 0.2s ease;
            }
            .product-card__button i {
                margin-right: 5px;
            }
            .product-card__button:hover {
                background-color: #e8e8dc;
                text-decoration: none;
                color: #333;
            }
            .carousel .col-sm-4 {
                margin-bottom: 30px;
            }
            .arrow-btn {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                background-color: transparent;
                border: none;
                font-size: 36px;
                color: #bbb;
                cursor: pointer;
                padding: 0;
                margin: 0;
                transition: color 0.2s ease;
                z-index: 10;
            }
            .arrow-btn:hover {
                color: #333;
            }
            .left-arrow {
                left: 5px;
            }
            .right-arrow {
                right: 5px;
            }
            .view-product {
                position: relative;
                max-width: 100%;
                overflow: hidden;
            }
            .main-image-fixed {
                width: 100%;
                max-width: 100%;
                height: 500px;
                object-fit: cover;
                border-radius: 12px;
                display: block;
                margin: 0 auto;
            }
            .feedback-item {
                border-bottom: 1px solid #eee;
                padding: 15px 0;
                margin-bottom: 15px;
            }
            .feedback-item:last-child {
                border-bottom: none;
            }
            .feedback-rating {
                color: #f1c40f;
                font-size: 18px;
            }
            .feedback-comment {
                margin: 10px 0;
                color: #555;
            }
            .feedback-images img {
                max-width: 100px;
                max-height: 100px;
                margin-right: 10px;
                border-radius: 4px;
            }
            .no-feedbacks {
                text-align: center;
                color: #666;
                padding: 20px;
            }
            .success-toast {
                display: none;
                position: fixed;
                bottom: 30px;
                right: 30px;
                background-color: #4CAF50;
                color: white;
                padding: 16px 24px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,0.2);
                font-size: 16px;
                z-index: 9999;
                animation: fadein 0.5s;
            }
            @keyframes fadein {
                from {
                    opacity: 0;
                    bottom: 10px;
                }
                to {
                    opacity: 1;
                    bottom: 30px;
                }
            }

            #wholesaleModal .modal-dialog {
                max-width: 450px;
                border-radius: 12px;
            }

            #wholesaleModal .modal-content {
                border-radius: 12px;
                padding: 24px 24px 16px;
            }

            #wholesaleModal .modal-title {
                font-size: 22px;
                font-weight: 600;
                margin-bottom: 12px;
                text-align: center;
            }

            #wholesaleModal h4 {
                font-size: 18px;
                font-weight: 500;
                margin-bottom: 12px;
            }

            #wholesaleModal img {
                width: 220px;
                height: 220px;
                object-fit: cover;
                border-radius: 8px;
                margin: 0 auto 16px;
                display: block;
            }

            #wholesaleModal .form-group {
                margin-bottom: 16px;
            }

            #wholesaleModal input[type="number"] {
                width: 100%;
                max-width: 100%;
                text-align: center;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 6px;
            }

            #wholesaleModal .modal-footer {
                display: flex;
                justify-content: space-between;
                padding-top: 16px;
            }

            #wholesaleModal .modal-footer button {
                width: 48%;
                font-size: 15px;
                padding: 10px 0;
                border-radius: 6px;
            }

            #wholesaleModal .btn-secondary {
                background-color: #e74c3c;
                border-color: #e74c3c;
                color: white;
            }

            #wholesaleModal .btn-warning {
                background-color: #f39c12;
                border-color: #f39c12;
                color: white;
            }

            #wholesaleError {
                margin-top: 6px;
                display: block;
                font-size: 13px;
            }
        </style>

    </head>
    <body>
        <jsp:include page="/ZeShopper/header.jsp"/>

        <section>
            <div class="container">
                <div class="row">
                    <div class="col-sm-3">
                        <div class="left-sidebar">
                            <div class="shipping text-center">
                                <img src="${pageContext.request.contextPath}/ZeShopper/images/home/shipping.jpg" alt="" />
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-9 padding-right">
                        <div class="product-details">
                            <div class="col-sm-5">
                                <div class="view-product position-relative text-center">
                                    <button id="prevImage" class="arrow-btn left-arrow position-absolute start-0 top-50 translate-middle-y" aria-label="Previous image" style="z-index: 2; background: none; border: none; font-size: 32px; color: #333;">❮</button>
                                    <img id="mainImage" src="${pageContext.request.contextPath}/upload/BouquetIMG/${images[0].image_url}" alt="Bouquet Image" class="img-fluid bouquet-img mb-2 main-image-fixed" style="width: 100%; max-width: 400px; height: 500px; object-fit: cover; border-radius: 12px; cursor: pointer;" data-toggle="modal" data-target="#allImagesModal"/>
                                    <button id="nextImage" class="arrow-btn right-arrow position-absolute end-0 top-50 translate-middle-y" aria-label="Next image" style="z-index: 2; background: none; border: none; font-size: 32px; color: #333;">❯</button>
                                </div>
                            </div>

                            <div class="modal fade" id="allImagesModal" tabindex="-1" role="dialog" aria-labelledby="allImagesModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-scrollable modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">Tất Cả Hình Ảnh</h5>
                                            <button type="button" class="close" data-dismiss="modal">×</button>
                                        </div>
                                        <div class="modal-body" style="max-height: 80vh; overflow-y: auto; text-align: center; position: relative;">
                                            <button id="modalPrev" class="arrow-btn left-arrow position-absolute start-0 top-50 translate-middle-y" aria-label="Previous modal image" style="z-index: 2; background: none; border: none; font-size: 48px; color: #333;">❮</button>
                                            <img id="modalImage" src="" alt="Modal Bouquet Image" class="img-fluid bouquet-img mb-2 main-image-fixed" style="max-width: 100%; object-fit: cover;"/>
                                            <button id="modalNext" class="arrow-btn right-arrow position-absolute end-0 top-50 translate-middle-y" aria-label="Next modal image" style="z-index: 2; background: none; border: none; font-size: 48px; color: #333;">❯</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-sm-7">
                                <div class="product-information">
                                    <h2>${bouquetDetail.bouquetName}</h2>
                                    <span>
                                        <span><fmt:formatNumber value="${bouquetDetail.sellPrice}" pattern="#,##0" /> ₫</span>
                                        <form id="addToCartForm">
                                            <label class="popup-label">Số lượng:</label>
                                            <input id="popup-quantity" type="number" name="quantity" value="1" min="1" max="${bouquetAvailable}" required class="popup-input">
                                            <input type="hidden" name="bouquetId" id="popup-id" value="${bouquetDetail.bouquetId}">
                                            <div class="popup-buttons" style="white-space: nowrap;">
                                                <button type="submit"
                                                        class="btn btn-fefault cart"
                                                        style="display: inline-block; padding: 8px 12px; white-space: nowrap;">
                                                    <i class="fa fa-shopping-cart"></i> Thêm
                                                </button>

                                                <a href="#"
                                                   class="btn btn-fefault cart"
                                                   style="display: inline-block; padding: 8px 12px; white-space: nowrap;"
                                                   data-toggle="modal"
                                                   data-target="#wholesaleModal">
                                                    <i class="fa fa-shopping-cart"></i> Đặt nhiều
                                                </a>

                                            </div>


                                            <p id="quantity-error" style="color: red; font-weight: bold">${error}</p>
                                        </form>
                                    </span>
                                    <p><b>Số lượng trong kho: </b>${bouquetAvailable}</p>
                                    <p><b>Tình trạng:</b> Mới</p>
                                    <p><b>Thương hiệu:</b> La Fioreria</p>
                                    <a href=""><img src="${pageContext.request.contextPath}/ZeShopper/images/product-details/share.png" class="share img-responsive" alt="" /></a>
                                </div>
                                <c:if test="${param.addedWholesale eq 'true'}">
                                    <div class="alert alert-success">
                                        ✅ Đã thêm vào đơn đặt số lượng lớn thành công. Bạn có thể xem lại trong mục
                                        <a href="${pageContext.request.contextPath}/wholeSale">Yêu cầu báo giá</a>.
                                    </div>
                                </c:if>
                            </div>
                        </div>

                        <div class="category-tab shop-details-tab">
                            <div class="col-sm-12">
                                <ul class="nav nav-tabs">
                                    <li class="active"><a href="#details" data-toggle="tab">Chi tiết</a></li>
                                    <li><a href="#reviews" data-toggle="tab">Đánh giá</a></li>
                                </ul>
                            </div>
                            <div class="tab-content">
                                <div class="tab-pane fade active in" id="details">
                                    <div class="col-sm-12">
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <div class="product-image-wrapper">
                                                    <div class="single-products">
                                                        <div class="productinfo text-center">
                                                            <c:forEach items="${images}" var="img" varStatus="status">
                                                                <c:if test="${status.index == 0}">
                                                                    <img src="${pageContext.request.contextPath}/upload/BouquetIMG/${img.image_url}" alt="" />
                                                                </c:if>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-9">
                                                <div class="product-details">
                                                    <h2>${bouquetDetail.bouquetName}</h2>
                                                    <p><strong>Danh mục:</strong> ${cateName}</p>
                                                    <p><strong>Giá:</strong> <fmt:formatNumber value="${bouquetDetail.sellPrice}" pattern="#,##0" /> ₫</p>
                                                    <p><b>Số lượng trong kho: </b>${bouquetAvailable}</p>
                                                    <p><strong>Hoa trong bó:</strong>
                                                        <c:forEach var="br" items="${flowerInBQ}" varStatus="status">
                                                            <c:forEach var="fb" items="${allBatchs}">
                                                                <c:if test="${fb.batchId == br.batchId}">
                                                                    <c:forEach var="f" items="${allFlowers}">
                                                                        <c:if test="${f.flowerId == fb.flowerId}">
                                                                            ${br.quantity} ${f.flowerName}
                                                                            <c:if test="${!status.last}">,</c:if>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:forEach>
                                                    </p>
                                                    <p><strong>Mô tả bó hoa:</strong></p>
                                                    <p>${bouquetDetail.description}</p>
                                                    <p><strong>Mô tả danh mục:</strong></p>
                                                    <p>${cateDes}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="tab-pane fade" id="reviews">
                                    <div class="col-sm-12">
                                        <h3>Đánh giá sản phẩm</h3>
                                        <c:if test="${empty feedback}">
                                            <div class="no-feedbacks">
                                                <p>Chưa có đánh giá nào cho sản phẩm này.</p>
                                            </div>
                                        </c:if>
                                        <c:forEach var="feedback" items="${feedback}">
                                            <div class="feedback-item">
                                                <p>
                                                    <strong>${feedbackCustomerNames[feedback.feedbackId]}</strong> - 
                                                    <fmt:formatDate value="${feedback.createdAtAsDate}" pattern="dd/MM/yyyy HH:mm"/> - 
                                                    <span class="feedback-rating">
                                                        <c:forEach begin="1" end="${feedback.rating}">
                                                            <i class="fa fa-star"></i>
                                                        </c:forEach>
                                                        <c:forEach begin="${feedback.rating + 1}" end="5">
                                                            <i class="fa fa-star-o"></i>
                                                        </c:forEach>
                                                    </span>
                                                </p>
                                                <p class="feedback-comment">${feedback.comment}</p>
                                                <div class="feedback-images">
                                                    <c:forEach var="img" items="${feedbackImages[feedback.feedbackId]}">
                                                        <img src="${pageContext.request.contextPath}/upload/FeedbackIMG/${img}" alt="Feedback Image">
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="recommended_items">
                            <h2 class="title text-center">Sản phẩm cùng danh mục</h2>
                            <div id="recommended-item-carousel" class="carousel slide" data-ride="carousel">
                                <div class="carousel-inner">
                                    <c:forEach var="lb" items="${listBouquet}" varStatus="status">
                                        <c:if test="${status.index % 3 == 0}">
                                            <div class="item ${status.index == 0 ? 'active' : ''}">
                                                <div class="row">
                                                </c:if>
                                                <div class="col-sm-4">
                                                    <div class="product-card">
                                                        <div class="product-card__image">
                                                            <c:forEach items="${allImage}" var="img">
                                                                <c:if test="${lb.bouquetId == img.bouquetId}">
                                                                    <img src="${pageContext.request.contextPath}/upload/BouquetIMG/${img.image_url}" alt="${lb.bouquetName}" />
                                                                </c:if>
                                                            </c:forEach>
                                                        </div>
                                                        <div class="product-card__body">
                                                            <h3 class="product-card__title">
                                                                <a href="${pageContext.request.contextPath}/productDetail?id=${lb.bouquetId}">${lb.bouquetName}</a>
                                                            </h3>
                                                            <p class="product-card__price">Giá: <fmt:formatNumber value="${lb.sellPrice}" pattern="#,##0" /> ₫</p>
                                                            <a href="#" class="btn product-card__button"><i class="fa fa-shopping-cart"></i> Thêm vào giỏ</a>
                                                        </div>
                                                    </div>
                                                </div>
                                                <c:if test="${status.index % 3 == 2 || status.last}">
                                                </div>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                                <a class="left recommended-item-control" href="#recommended-item-carousel" data-slide="prev">
                                    <i class="fa fa-angle-left"></i>
                                </a>
                                <a class="right recommended-item-control" href="#recommended-item-carousel" data-slide="next">
                                    <i class="fa fa-angle-right"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="wholesaleModal" tabindex="-1" role="dialog" aria-labelledby="wholesaleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Yêu cầu báo giá theo lô</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">×</button>
                        </div>
                        <form id="wholesaleForm" method="post" action="${pageContext.request.contextPath}/productDetail">
                            <input type="hidden" name="productId" value="${productId}" />
                            <input type="hidden" name="user_id" value="${sessionScope.currentAcc.userid}" />
                            <div class="modal-body text-center">
                                <h4 style="margin-bottom: 10px;">${bouquetDetail.bouquetName}</h4>
                                <img src="${pageContext.request.contextPath}/upload/BouquetIMG/${images[0].image_url}"
                                     alt="Bouquet"
                                     style="width: 200px; height: 200px; object-fit: cover; border-radius: 8px; margin-bottom: 15px;" />

                                <div class="form-group">
                                    <label for="wholesaleQuantity"><strong>Số lượng muốn đặt:</strong></label>
                                    <input type="number" min="50" name="requested_quantity" class="form-control text-center" id="wholesaleQuantity" required />
                                    <small class="text-danger d-none" id="wholesaleError">Vui lòng nhập số lượng >= 50</small>
                                </div>

                                <!-- GHI CHÚ -->
                                <div class="form-group">
                                    <label for="wholesaleNote"><strong>Ghi chú thêm (nếu có):</strong></label>
                                    <textarea name="note" id="wholesaleNote" class="form-control" rows="3" placeholder="Ví dụ: cần giao gấp, hoa phải tươi, giao theo nhiều đợt, v.v."></textarea>
                                </div>

                                <input type="hidden" name="bouquet_id" value="${bouquetDetail.bouquetId}" />
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                                <button type="submit" class="btn btn-warning">Yêu cầu báo giá</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>


        </section>

        <jsp:include page="/ZeShopper/footer.jsp"/>

        <div id="success-popup" class="success-toast">Đã thêm vào giỏ hàng thành công!</div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script>
            document.getElementById("wholesaleForm").addEventListener("submit", function (e) {
                const qty = parseInt(document.getElementById("wholesaleQuantity").value);
                const error = document.getElementById("wholesaleError");

                if (isNaN(qty) || qty < 50) {
                    e.preventDefault();
                    error.classList.remove("d-none");
                } else {
                    error.classList.add("d-none");
                }
            });
        </script>

        <script>
            document.getElementById("addToCartForm").addEventListener("submit", function (e) {
                e.preventDefault();
                const bouquetId = document.getElementById("popup-id").value;
                const quantityInput = document.getElementById("popup-quantity");
                const quantity = parseInt(quantityInput.value);
                const errorDisplay = document.getElementById("quantity-error");
                const availableQuantity = ${bouquetAvailable};

                errorDisplay.innerText = "";
                if (quantity > availableQuantity) {
                    errorDisplay.innerText = `Bạn chỉ có thể đặt tối đa ${availableQuantity} sản phẩm.`;
                    return;
                }

                const formData = new URLSearchParams();
                formData.append("action", "add");
                formData.append("bouquetId", bouquetId);
                formData.append("quantity", quantity);

                fetch("${pageContext.request.contextPath}/ZeShopper/cart", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded"},
                    body: formData.toString()
                })
                        .then(res => res.json())
                        .then(data => {
                            if (data.status === "added") {
                                showSuccessPopup("Đã thêm vào giỏ hàng thành công!");
                            } else {
                                errorDisplay.innerText = "Lỗi: " + data.message;
                            }
                        })
                        .catch(err => {
                            console.error("Error adding to cart:", err);
                            errorDisplay.innerText = "Có lỗi xảy ra.";
                        });
            });

            function showSuccessPopup(message) {
                const successBox = document.getElementById("success-popup");
                successBox.innerText = message;
                successBox.style.display = "block";
                setTimeout(() => {
                    successBox.style.display = "none";
                }, 3000);
            }

            $(function () {
                var imageUrls = [
            <c:forEach items="${images}" var="img" varStatus="status">
                "${pageContext.request.contextPath}/upload/BouquetIMG/${img.image_url}"<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                        ];
                        var currentIndex = 0;
                        var $mainImage = $('#mainImage');
                        var $prevBtn = $('#prevImage');
                        var $nextBtn = $('#nextImage');
                        var $modal = $('#allImagesModal');
                        var $modalImage = $('#modalImage');
                        var $modalPrev = $('#modalPrev');
                        var $modalNext = $('#modalNext');

                        $prevBtn.on('click', function () {
                            if (currentIndex > 0) {
                                currentIndex--;
                                $mainImage.attr('src', imageUrls[currentIndex]);
                            }
                        });
                        $nextBtn.on('click', function () {
                            if (currentIndex < imageUrls.length - 1) {
                                currentIndex++;
                                $mainImage.attr('src', imageUrls[currentIndex]);
                            }
                        });

                        $mainImage.on('click', function () {
                            $modalImage.attr('src', imageUrls[currentIndex]);
                        });

                        $modal.on('show.bs.modal', function () {
                            $modalImage.attr('src', imageUrls[currentIndex]);
                        });

                        $modalPrev.on('click', function () {
                            if (currentIndex > 0) {
                                currentIndex--;
                                $modalImage.attr('src', imageUrls[currentIndex]);
                                $mainImage.attr('src', imageUrls[currentIndex]);
                            }
                        });
                        $modalNext.on('click', function () {
                            if (currentIndex < imageUrls.length - 1) {
                                currentIndex++;
                                $modalImage.attr('src', imageUrls[currentIndex]);
                                $mainImage.attr('src', imageUrls[currentIndex]);
                            }
                        });
                    });
        </script>
    </body>
</html>