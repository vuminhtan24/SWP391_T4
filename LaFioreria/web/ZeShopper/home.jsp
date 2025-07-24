<%-- 
    Document   : home
    Created on : May 19, 2025, 8:36:30 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List,model.Bouquet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="vi_VN" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Home | E-Shopper</title>
        <link href="${pageContext.request.contextPath}/ZeShopper/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/responsive.css" rel="stylesheet"> 
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/ZeShopper/images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/ZeShopper/images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/ZeShopper/images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/ZeShopper/images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/ZeShopper/images/ico/apple-touch-icon-57-precomposed.png">
        <style>
            .popup-overlay {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5);
                display: flex;
                align-items: center;
                justify-content: center;
                z-index: 9999;
                animation: fadeIn 0.3s ease-in-out;
            }

            .popup-content {
                background: #ffffff;
                padding: 25px 30px;
                border-radius: 12px;
                max-width: 400px;
                width: 90%;
                position: relative;
                box-shadow: 0 0 10px rgba(0,0,0,0.2);
                animation: scaleUp 0.3s ease;
            }

            .popup-img {
                width: 100%;
                max-height: 200px;
                object-fit: contain;
                border-radius: 8px;
                margin-bottom: 15px;
            }

            .popup-price {
                font-weight: bold;
                margin: 5px 0;
            }

            .popup-description {
                font-size: 14px;
                color: #555;
                margin-bottom: 15px;
            }

            .popup-label {
                display: block;
                font-weight: 500;
                margin-bottom: 5px;
            }

            .popup-input {
                width: 100%;
                padding: 8px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 6px;
            }

            .popup-buttons {
                display: flex;
                justify-content: space-between;
                gap: 10px;
            }

            .popup-btn {
                flex: 1;
                padding: 10px;
                border: none;
                border-radius: 6px;
                background-color: #28a745;
                color: white;
                font-weight: bold;
                cursor: pointer;
                transition: background 0.2s;
            }

            .popup-btn.cancel {
                background-color: #dc3545;
            }

            .popup-btn:hover {
                filter: brightness(0.9);
            }

            .close-btn {
                position: absolute;
                top: 10px;
                right: 15px;
                font-size: 22px;
                cursor: pointer;
                color: #aaa;
                transition: color 0.2s;
            }
            .most-sell-container {
                position: relative;
                margin-top: 20px;
                padding: 0 40px;
            }

            .most-sell-row {
                display: flex;
                overflow-x: auto;
                gap: 16px;
                scroll-behavior: smooth;
                padding: 10px 0;
                height: auto;
            }

            .most-sell-item {
                flex: 0 0 250px;
                box-sizing: border-box;
                display: flex;
                flex-direction: column;
                height: auto;
            }

            .product-image-wrapper {
                flex: 1;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                height: auto;
                padding: 12px;
                border: 1px solid #eee;
                border-radius: 8px;
                background-color: #fff;
                box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
                transition: transform 0.2s ease;
            }

            .product-image-wrapper:hover {
                transform: translateY(-4px);
            }

            .productinfo img {
                width: 100%;
                height: 180px;
                object-fit: cover;
                border-radius: 6px;
                margin-bottom: 10px;
            }

            .productinfo h2 {
                font-size: 16px;
                margin-bottom: 8px;
                color: #333;
                font-weight: 600;
            }

            .productinfo p {
                font-size: 14px;
                color: #777;
                margin-bottom: 10px;
            }

            .productinfo button.add-to-cart {
                background-color: #28a745;
                color: white;
                border: none;
                padding: 8px 14px;
                border-radius: 4px;
                cursor: pointer;
                font-weight: 500;
                transition: background-color 0.2s;
            }

            .productinfo button.add-to-cart:hover {
                background-color: #218838;
            }

            .most-sell-arrow {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                background: transparent;      /* Bỏ nền */
                color: #333;                  /* Màu chữ chính */
                border: none;                 /* Bỏ viền */
                padding: 0 8px;               /* Nhỏ gọn ngang */
                font-size: 22px;              /* Cỡ chữ vừa phải */
                font-weight: bold;
                cursor: pointer;
                z-index: 10;
                line-height: 1;
            }

            .most-sell-arrow:hover {
                color: orange;                /* Đổi màu khi hover */
            }

            .most-sell-arrow.left {
                left: -5px;                   /* Căn chỉnh vị trí trái */
            }

            .most-sell-arrow.right {
                right: -5px;                  /* Căn chỉnh vị trí phải */
            }

        </style>

    </head><!--/head-->
    <body>
        <jsp:include page="/ZeShopper/header.jsp"/>

        <div class="row">
            <form action="${pageContext.request.contextPath}/product" method="get">
                <div style="margin-bottom: 10px; display: flex; align-items: center; margin-left: 1130px">
                    <input
                        type="text"
                        name="bouquetName"
                        placeholder="Tìm kiếm sản phẩm"
                        value="${param.bouquetName != null ? param.bouquetName : ''}"
                        style="
                        width: 200px;
                        padding: 5px 10px;
                        border-radius: 20px;
                        border: 1px solid #ccc;
                        font-size: 16px;
                        outline: none;
                        "
                        />
                    <button
                        type="submit"
                        style="
                        background-color: orange;
                        color: white;
                        padding: 5px 10px;
                        border: none;
                        border-radius: 20px;
                        cursor: pointer;
                        font-size: 16px;
                        margin-left: 10px;
                        /* không cần margin-top */
                        "
                        >
                        Search
                    </button>
                </div>
            </form>        

            <section id="slider"><!--slider-->
                <div class="container">
                    <div class="row">
                        <div class="col-sm-12">
                            <div id="slider-carousel" class="carousel slide" data-ride="carousel">
                                <ol class="carousel-indicators">
                                    <li data-target="#slider-carousel" data-slide-to="0" class="active"></li>
                                    <li data-target="#slider-carousel" data-slide-to="1"></li>
                                    <li data-target="#slider-carousel" data-slide-to="2"></li>
                                </ol>

                                <div class="carousel-inner">
                                    <div class="item active">
                                        <div class="col-sm-6">
                                            <h1><span>L</span>afioreria</h1>
                                            <h2>Fresh flowers for every moment</h2>
                                            <p>Choose elegant bouquets to express your true feelings to your loved ones.</p>
                                            <button type="button" class="btn btn-default get">Get it now</button>
                                        </div>
                                        <div class="col-sm-6">
                                            <img src="${pageContext.request.contextPath}/ZeShopper/images/home/bouquet1.jpg"  class="girl img-responsive" alt="" />
                                        </div>
                                    </div>
                                    <div class="item">
                                        <div class="col-sm-6">
                                            <h1><span>L</span>afioreria</h1>
                                            <h2>100% Responsive Design</h2>
                                            <p>Create a unique bouquet for your special occasions – birthdays, anniversaries, or a heartfelt thank-you. </p>
                                            <button type="button" class="btn btn-default get">Get it now</button>
                                        </div>
                                        <div class="col-sm-6">
                                            <img src="${pageContext.request.contextPath}/ZeShopper/images/home/bouquet2.jpg" style="width: 300px;" class="girl img-responsive" alt="" />
                                        </div>
                                    </div>

                                    <div class="item">
                                        <div class="col-sm-6">
                                            <h1><span>L</span>afioreria</h1>
                                            <h2>Fast and reliable flower delivery</h2>
                                            <p>Trusted delivery service to help you send love on time, anywhere.</p>
                                            <button type="button" class="btn btn-default get">Get it now</button>
                                        </div>
                                        <div class="col-sm-6">
                                            <img src="${pageContext.request.contextPath}/ZeShopper/images/home/bouquet3.jpg" style="width: 267px;" class="girl img-responsive" alt="" />
                                        </div>
                                    </div>

                                </div>

                                <a href="#slider-carousel" class="left control-carousel hidden-xs" data-slide="prev">
                                    <i class="fa fa-angle-left"></i>
                                </a>
                                <a href="#slider-carousel" class="right control-carousel hidden-xs" data-slide="next">
                                    <i class="fa fa-angle-right"></i>
                                </a>
                            </div>

                        </div>
                    </div>
                </div>
            </section><!--/slider-->

            <section>
                <div class="container">


                    <div class="col-sm-3">

                        <div class="left-sidebar">
                            <div class="container" style="margin-bottom: 20px;">
                                <!-- Dòng chứa các category -->
                                <div class="row" style="
                                     display: flex;
                                     flex-wrap: wrap;
                                     justify-content: flex-start;
                                     padding: 12px;
                                     ">

                                    <!-- Styled Header -->
                                    <div style="
                                         display: flex;
                                         align-items: center;
                                         width: 100%;
                                         margin-bottom: 16px;
                                         ">
                                        <hr style="flex:1; border:0; border-top:1px solid #ccc; margin:0;" />
                                        <i class="fa fa-star" aria-hidden="true" style="margin:0 8px; color:#888;"></i>
                                        <span style="
                                              font-size: 20px;
                                              font-weight: bold;
                                              color: #444;
                                              text-transform: uppercase;
                                              ">
                                            Category
                                        </span>
                                        <hr style="flex:1; border:0; border-top:1px solid #ccc; margin:0;" />
                                    </div>
                                    <c:forEach var="category" items="${cateBouquetHome}">
                                        <!-- Mỗi category chiếm 3/12 = 4 cột trên màn hình md+, 6/12 = 2 cột trên xs -->
                                        <div class="col-xs-6 col-sm-3 text-center mb-4">
                                            <!-- Link dẫn tới trang xử lý danh mục, truyền categoryId -->
                                            <a href="${pageContext.request.contextPath}/product?categoryId=${category.categoryId}" class="text-decoration-none">                                             
                                                <!-- Tên category -->
                                                <h5 class="mt-2" style="color:#555; font-size: large;">${category.categoryName}</h5>
                                            </a>
                                        </div>
                                    </c:forEach>
                                </div>

                                <!-- Dòng chứa các Flower -->
                                <div class="row" style="
                                     display: flex;
                                     flex-wrap: wrap;
                                     justify-content: flex-start;
                                     padding: 12px;
                                     ">

                                    <!-- Styled Header -->
                                    <div style="
                                         display: flex;
                                         align-items: center;
                                         width: 100%;
                                         margin-bottom: 16px;
                                         ">
                                        <hr style="flex:1; border:0; border-top:1px solid #ccc; margin:0;" />
                                        <i class="fa fa-star" aria-hidden="true" style="margin:0 8px; color:#888;"></i>
                                        <span style="
                                              font-size: 20px;
                                              font-weight: bold;
                                              color: #444;
                                              text-transform: uppercase;
                                              ">
                                            Flower
                                        </span>
                                        <hr style="flex:1; border:0; border-top:1px solid #ccc; margin:0;" />
                                    </div>
                                    <!-- END Styled Header -->

                                    <c:forEach var="flower" items="${listFlowerHome}" varStatus="status">
                                        <div
                                            class="flower-item"
                                            data-index="${status.index}"
                                            style="
                                            flex: 0 0 20%;
                                            max-width: 20%;
                                            box-sizing: border-box;
                                            padding: 8px;
                                            text-align: center;
                                            display: ${status.index < 5 ? 'block' : 'none'};
                                            ">
                                            <a href="${pageContext.request.contextPath}/product?flowerID=${flower.getFlowerId()}" class="text-decoration-none">
                                                <img
                                                    src="${pageContext.request.contextPath}/upload/FlowerIMG/${flower.getImage()}"
                                                    alt="${flower.getFlowerName()}"
                                                    style="
                                                    width: 150px;
                                                    height: 150px;
                                                    object-fit: cover;
                                                    border-radius: 50%;
                                                    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                                                    display: block;
                                                    margin: 0 auto 8px;
                                                    ">
                                                <h5 style="color: #555; margin: 0;">
                                                    ${flower.getFlowerName()}
                                                </h5>
                                            </a>
                                        </div>
                                    </c:forEach>

                                    <!-- Nút View More & Show Less -->
                                    <div style="flex: 0 0 100%; text-align: center; margin-top: 10px;">
                                        <button
                                            id="viewMoreFlowers"
                                            type="button"
                                            aria-label="View more Flower"
                                            style="
                                            border-radius: 4px;
                                            padding: 8px 16px;
                                            background-color: #007bff;
                                            color: white;
                                            border: none;
                                            cursor: pointer;
                                            margin-right: 8px;
                                            ">
                                            View more Flower
                                        </button>
                                        <button
                                            id="showLessFlowers"
                                            type="button"
                                            aria-label="Show less Flower"
                                            style="
                                            border-radius: 4px;
                                            padding: 8px 16px;
                                            background-color: #6c757d;
                                            color: white;
                                            border: none;
                                            cursor: pointer;
                                            display: none;
                                            ">
                                            Show less Flower
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Popup giữ nguyên chức năng -->
                        <div id="popup" class="popup-overlay" style="display:none;">
                            <div class="popup-content">
                                <span class="close-btn" onclick="closePopup()">&times;</span>
                                <form id="addToCartForm">
                                    <h3 id="popup-name"></h3>
                                    <img id="popup-image" src="" alt="" class="popup-img">
                                    <p id="popup-price" class="popup-price"></p>
                                    <p>Available: <span id="popup-available"></span></p> <!-- ✅ moved available here -->
                                    <p id="popup-description" class="popup-description"></p>

                                    <label class="popup-label">Quantity:</label>
                                    <input id="popup-quantity" type="number" name="quantity" value="1" min="1" required class="popup-input">

                                    <input type="hidden" name="bouquetId" id="popup-id">
                                    <div class="popup-buttons">
                                        <button type="submit" class="popup-btn">Add to Cart</button>
                                        <button type="button" onclick="closePopup()" class="popup-btn cancel">Cancel</button>
                                    </div>
                                </form>
                            </div>
                        </div>


                        <!-- Most Sell -->
                        <div>
                            <div class="col-sm-3">
                                <div class="left-sidebar">
                                    <div class="container" style="margin-bottom: 20px;">
                                        <div class="most-sell-container">
                                            <div style="
                                                 display: flex;
                                                 align-items: center;
                                                 width: 100%;
                                                 margin-bottom: 16px;
                                                 ">
                                                <hr style="flex:1; border:0; border-top:1px solid #ccc; margin:0;" />
                                                <i class="fa fa-star" aria-hidden="true" style="margin:0 8px; color:#888;"></i>
                                                <span style="
                                                      font-size: 20px;
                                                      font-weight: bold;
                                                      color: #444;
                                                      text-transform: uppercase;
                                                      ">
                                                    Most Product Sell
                                                </span>
                                                <hr style="flex:1; border:0; border-top:1px solid #ccc; margin:0;" />
                                            </div>

                                            <!-- Mũi tên điều hướng -->
                                            <button class="most-sell-arrow left" onclick="scrollMostSell(-1)">&#10094;</button>
                                            <button class="most-sell-arrow right" onclick="scrollMostSell(1)">&#10095;</button>

                                            <!-- Danh sách sản phẩm Most Sell -->
                                            <div class="most-sell-row" id="mostSellRow">
                                                <c:forEach items="${requestScope.listMostSellBouquet}" var="lb">
                                                    <c:set var="available" value="${bouquetAvailableMap[lb.bouquetId]}" />
                                                    <c:if test="${lb.getStatus() eq 'valid' and available != null and available gt 0}">
                                                    <div class="most-sell-item">
                                                        <div class="product-image-wrapper">
                                                            <div class="single-products">
                                                                <div class="productinfo text-center">
                                                                    <c:set var="imageShown" value="false" />
                                                                    <c:forEach items="${images}" var="img">
                                                                        <c:if test="${!imageShown and lb.getBouquetId() == img.getbouquetId()}">
                                                                            <img
                                                                                src="${pageContext.request.contextPath}/upload/BouquetIMG/${img.getImage_url()}"
                                                                                alt="${lb.getBouquetName()}"
                                                                                style="width: 100%; height: 180px; object-fit: cover; border-radius: 6px; margin-bottom: 10px;" />
                                                                            <c:set var="imageShown" value="true" />
                                                                        </c:if>
                                                                    </c:forEach>

                                                                    <h2 style="font-size: 16px; margin-bottom: 8px;">
                                                                        <a href="${pageContext.request.contextPath}/productDetail?id=${lb.getBouquetId()}"
                                                                           style="color: #333; text-decoration: none;">
                                                                            ${lb.getBouquetName()}
                                                                        </a>
                                                                    </h2>

                                                                    <p style="margin-bottom: 10px;">Price: <fmt:formatNumber value="${lb.getSellPrice()}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫</p>

                                                                    

                                                                    <button type="button"
                                                                            class="btn btn-default add-to-cart"
                                                                            <c:forEach items="${images}" var="cimg">
                                                                                <c:if test="${lb.getBouquetId() == cimg.getbouquetId()}">
                                                                                    onclick="openPopup(
                                                                                                    '${lb.getBouquetId()}',
                                                                                                    '${lb.getBouquetName()}',
                                                                                                    '${pageContext.request.contextPath}/upload/BouquetIMG/${cimg.getImage_url()}',
                                                                                                                    '${lb.getSellPrice()}',
                                                                                                                    '${available}'  // Truyền available vào
                                                                                                                    )"
                                                                                </c:if>
                                                                            </c:forEach>
                                                                            style="background-color: #5cb85c; color: white; border: none; padding: 8px 12px; border-radius: 4px; cursor: pointer;">
                                                                        <i class="fa fa-shopping-cart" aria-hidden="true"></i> Add to cart
                                                                    </button>

                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    </c:if>                
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>    
            </section>


            <jsp:include page="/ZeShopper/footer.jsp"/>
            <div id="success-popup" class="success-toast">Added to cart successfully!</div>

            <script>
                document.addEventListener('DOMContentLoaded', function () {
                    var btnMore = document.getElementById('viewMoreFlowers');
                    var btnLess = document.getElementById('showLessFlowers');
                    var items = document.querySelectorAll('.flower-item');

                    // Hiển thị toàn bộ
                    btnMore.addEventListener('click', function (e) {
                        e.preventDefault();
                        items.forEach(function (el) {
                            el.style.display = 'block';
                        });
                        btnMore.style.display = 'none';
                        btnLess.style.display = 'inline-block';
                    });

                    // Thu gọn lại 5 đầu
                    btnLess.addEventListener('click', function (e) {
                        e.preventDefault();
                        items.forEach(function (el) {
                            var idx = parseInt(el.getAttribute('data-index'), 10);
                            el.style.display = (idx < 5 ? 'block' : 'none');
                        });
                        btnLess.style.display = 'none';
                        btnMore.style.display = 'inline-block';
                        // scroll lên đầu grid nếu cần
                        // btnMore.scrollIntoView({ behavior: 'smooth', block: 'start' });
                    });
                });
            </script>

            <script>
                function formatVND(price) {
                    return new Intl.NumberFormat('vi-VN', {
                        style: 'decimal',
                        maximumFractionDigits: 0
                    }).format(price) + ' ₫';
                }

                function openPopup(id, name, imageUrl, price, available) {
                    document.getElementById("popup-id").value = id;
                    document.getElementById("popup-name").textContent = name;
                    document.getElementById("popup-image").src = imageUrl;
                    document.getElementById("popup-price").textContent = "Price: " + formatVND(price);

                    // ✅ Show available correctly in span
                    document.getElementById("popup-available").textContent = available;

                    // ✅ Optional: nếu bạn còn muốn hiển thị description bên dưới nữa
                    document.getElementById("popup-description").textContent = ""; // hoặc giữ nguyên nếu có nội dung

                    // ✅ Giới hạn max quantity không vượt quá available
                    const quantityInput = document.getElementById("popup-quantity");
                    quantityInput.value = 1;
                    quantityInput.max = available;
                    quantityInput.setAttribute("max", available);

                    document.getElementById("popup").style.display = "flex";
                }


                function closePopup() {
                    document.getElementById("popup").style.display = "none";
                    document.getElementById("popup-quantity").value = 1;
                }
            </script>
            <script>
                document.getElementById("addToCartForm").addEventListener("submit", function (e) {
                    e.preventDefault(); // Ngăn form reload

                    const bouquetId = document.getElementById("popup-id").value;
                    const quantity = document.getElementById("popup-quantity").value;

                    const formData = new URLSearchParams();
                    formData.append("action", "add");
                    formData.append("bouquetId", bouquetId);
                    formData.append("quantity", quantity);

                    for (const [key, value] of formData.entries()) {
                        console.log(key, `:`, value);
                    }

                    fetch("${pageContext.request.contextPath}/ZeShopper/cart", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        },
                        body: formData.toString()
                    })
                            .then(res => res.json())
                            .then(data => {
                                if (data.status === "added") {
                                    closePopup();
                                    showSuccessPopup("Added to cart successfully!");
                                } else if (data.status === "exceed_available") {
                                    alert("You cannot add more than the available quantity.");
                                } else {
                                    alert("Error: " + data.message);
                                }
                            })
                            .catch(err => {
                                console.error("Error adding to cart:", err);
                                alert("Something went wrong.");
                            });
                });

                function showSuccessPopup(message) {
                    const successBox = document.getElementById("success-popup");
                    successBox.innerText = message;
                    successBox.style.display = "block";

                    // Ẩn sau 3 giây
                    setTimeout(() => {
                        successBox.style.display = "none";
                    }, 3000);
                }
            </script>
            <script>
                function scrollMostSell(direction) {
                    const row = document.getElementById('mostSellRow');
                    const scrollAmount = 270; // bằng độ rộng mỗi item + gap
                    row.scrollBy({
                        left: direction * scrollAmount,
                        behavior: 'smooth'
                    });
                }
            </script>

    </body>
</html>
