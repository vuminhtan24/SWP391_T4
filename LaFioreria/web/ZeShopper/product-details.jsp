<%-- 
    Document   : product-details
    Created on : May 19, 2025, 8:46:26 AM
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
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Product Details | E-Shopper</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"/>
        <link href="${pageContext.request.contextPath}/ZeShopper/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/responsive.css" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/ZeShopper/images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/ZeShopper/images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/ZeShopper/images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/ZeShopper/images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/ZeShopper/images/ico/apple-touch-icon-57-precomposed.png">
        <style>
            /* 1. Toàn bộ card viền trắng, đổ nhẹ shadow như Product List */
            .product-card {
                background-color: #fff;
                border: 1px solid #ededed;
                /* Tạo shadow nhẹ giống Product List */
                box-shadow: 0 2px 4px rgba(0,0,0,0.05);
                border-radius: 4px;
                overflow: hidden;
                display: flex;
                flex-direction: column;
                height: 100%;
            }

            /* 2. Khung ảnh cố định chiều cao, nhưng ảnh hiển thị đầy đủ (object-fit: contain) */
            .product-card__image {
                width: 100%;
                height: 200px;       /* Giữ đúng cao 200px cho tất cả ảnh */
                background-color: #f9f9f9; /* Màu nền nhạt nếu ảnh không lấp đầy khung */
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

            /* 3. Phần body chứa title/price/button */
            .product-card__body {
                padding: 15px;
                flex: 1;             /* Chiếm hết không gian còn lại trong card */
                display: flex;
                flex-direction: column;
                justify-content: space-between;
            }

            /* 4. Title (tên sản phẩm) giống Product List: màu xanh, font-size lớn */
            .product-card__title {
                font-size: 1.4rem;          /* tương đương ~22px */
                color: #337ab7;             /* màu xanh giống Bootstrap “link-primary” */
                margin: 0 0 10px 0;
                text-align: center;
                line-height: 1.2;
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

            .product-card__title a {
                color: inherit;
                text-decoration: none;
            }
            .product-card__title a:hover {
                text-decoration: underline;
            }

            /* 5. Price: nhỏ hơn title, màu xám đậm */
            .product-card__price {
                font-size: 1rem;
                color: #777;
                text-align: center;
                margin: 0 0 15px 0;
            }

            /* 6. Button “Add to cart”: full-width, màu nền vàng nhạt giống Product List */
            .product-card__button {
                width: 100%;
                font-size: 1rem;
                background-color: #f5f5f0;  /* màu nền giống “#f5f5f0” */
                color: #444;                /* màu chữ xám đậm */
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
                background-color: #e8e8dc;  /* hơi đậm hơn một chút */
                text-decoration: none;
                color: #333;
            }

            /* Khi hiển thị ở col-sm-4: cho margin-bottom để card cách nhau */
            .carousel .col-sm-4 {
                margin-bottom: 30px;
            }

        </style>
    </head><!--/head-->

    <body>
        <jsp:include page="/ZeShopper/header.jsp"/>

        <section>
            <div class="container">
                <div class="row">
                    <div class="col-sm-3">
                        <div class="left-sidebar">

                            <div class="shipping text-center"><!--shipping-->
                                <img src="${pageContext.request.contextPath}/ZeShopper/images/home/shipping.jpg" alt="" />
                            </div><!--/shipping-->

                        </div>
                    </div>

                    <div class="col-sm-9 padding-right">
                        <div class="product-details"><!--product-details-->
                            <div class="col-sm-5">
                                <div class="view-product">
                                    <img src="${bouquetDetail.getImageUrl()}" alt="" />                                   
                                </div>
                                <div id="similar-product" class="carousel slide" data-ride="carousel">

                                    <!-- Controls -->
                                    <a class="left item-control" href="#similar-product" data-slide="prev">
                                        <i class="fa fa-angle-left"></i>
                                    </a>
                                    <a class="right item-control" href="#similar-product" data-slide="next">
                                        <i class="fa fa-angle-right"></i>
                                    </a>
                                </div>

                            </div>
                            <div class="col-sm-7">
                                <div class="product-information"><!--/product-information-->
                                    <img src="images/product-details/new.jpg" class="newarrival" alt="" />
                                    <h2>${bouquetDetail.getBouquetName()}</h2>
                                    <img src=""/>
                                    <span>
                                        <span>${bouquetDetail.getPrice()} VND</span>


                                        <form id="addToCartForm">
                                            <label>Quantity:</label>
                                            <label class="popup-label">Quantity:</label>
                                            <input id="popup-quantity" type="number" name="quantity" value="1" min="1" required class="popup-input">

                                            <input type="hidden" name="bouquetId" id="popup-id" value="${bouquetDetail.getBouquetId()}">
                                            <div class="popup-buttons">
                                                <button type="submit" class="btn btn-fefault cart">
                                                    <i class="fa fa-shopping-cart"></i>
                                                    Add to cart
                                                </button>
                                            </div>
                                        </form>



                                    </span>
                                    <p><b>Availability:</b> In Stock</p>
                                    <p><b>Condition:</b> New</p>
                                    <p><b>Brand:</b>La Fioreria</p>
                                    <a href=""><img src="${pageContext.request.contextPath}/ZeShopper/images/product-details/share.png" class="share img-responsive"  alt="" /></a>
                                </div><!--/product-information-->
                            </div>
                        </div><!--/product-details-->

                        <div class="category-tab shop-details-tab"><!--category-tab-->
                            <div class="col-sm-12">
                                <ul class="nav nav-tabs">
                                    <li class="active">
                                        <a href="#details" data-toggle="tab">Details</a>
                                    </li>
                                    <li>
                                        <a href="#reviews" data-toggle="tab">Reviews</a>
                                    </li>
                                </ul>
                            </div>
                            <div class="tab-content">
                                <div class="tab-pane fade active in" id="details" >
                                    <div class="col-sm-12">
                                        <div class="row">
                                            <!-- Cột bên trái: ảnh sản phẩm -->
                                            <div class="col-sm-3">
                                                <div class="product-image-wrapper">
                                                    <div class="single-products">
                                                        <div class="productinfo text-center">
                                                            <img src="${bouquetDetail.getImageUrl()}" alt="${bouquetDetail.bouquetName}" class="img-responsive" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- Cột bên phải: thông tin chi tiết -->
                                            <div class="col-sm-9">
                                                <div class="product-details">
                                                    <h2>${bouquetDetail.getBouquetName()}</h2>
                                                    <p><strong>Category:</strong> ${cateName}</p>
                                                    <p><strong>Price:</strong> ${bouquetDetail.getPrice()} VND</p>
                                                    <p><strong>Flowers in Bouquet:</strong>
                                                        <c:forEach var="br" items="${flowerInBQ}" varStatus="status">
                                                            <c:forEach var="f" items="${allFlowers}">
                                                                <c:if test="${f.getRawId() eq br.getRaw_id()}">
                                                                    ${br.getQuantity()} bông hoa ${f.getRawName()}<c:if test="${!status.last}">, </c:if>
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:forEach>
                                                    </p>
                                                    <p><strong>Bouquet Description:</strong></p>
                                                    <p>${bouquetDetail.getDescription()}</p>
                                                    <p><strong>Category Description:</strong></p>
                                                    <p>${cateDes}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="tab-pane fade" id="reviews" >
                                    <div class="col-sm-12">
                                        <ul>
                                            <li><a href=""><i class="fa fa-user"></i>EUGEN</a></li>
                                            <li><a href=""><i class="fa fa-clock-o"></i>12:41 PM</a></li>
                                            <li><a href=""><i class="fa fa-calendar-o"></i>31 DEC 2014</a></li>
                                        </ul>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p>
                                        <p><b>Write Your Review</b></p>

                                        <form action="#">
                                            <span>
                                                <input type="text" placeholder="Your Name"/>
                                                <input type="email" placeholder="Email Address"/>
                                            </span>
                                            <textarea name="" ></textarea>
                                            <b>Rating: </b> <img src="${pageContext.request.contextPath}/ZeShopper/images/product-details/rating.png" alt="" />
                                            <button type="button" class="btn btn-default pull-right">
                                                Submit
                                            </button>
                                        </form>
                                    </div>
                                </div>

                            </div>
                        </div><!--/category-tab-->

                        <div class="recommended_items">
                            <h2 class="title text-center">Same Category Products</h2>

                            <div id="recommended-item-carousel" class="carousel slide" data-ride="carousel">
                                <div class="carousel-inner">
                                    <c:forEach var="lb" items="${requestScope.listBouquet}" varStatus="status">
                                        <!-- Mở slide mới mỗi 3 sản phẩm -->
                                        <c:if test="${status.index % 3 == 0}">
                                            <div class="item ${status.index == 0 ? 'active' : ''}">
                                                <div class="row">
                                                </c:if>

                                                <!-- Mỗi sản phẩm đặt trong col-sm-4 -->
                                                <div class="col-sm-4">
                                                    <div class="product-card">
                                                        <!-- Image container -->
                                                        <div class="product-card__image">
                                                            <img src="${lb.getImageUrl()}"
                                                                 alt="${lb.getBouquetName()}" />
                                                        </div>

                                                        <!-- Thông tin phía dưới ảnh -->
                                                        <div class="product-card__body">
                                                            <!-- Tên sản phẩm (blue, to hơn) -->
                                                            <h3 class="product-card__title">
                                                                <a href="${pageContext.request.contextPath}/productDetail?id=${lb.getBouquetId()}">
                                                                    ${lb.getBouquetName()}
                                                                </a>
                                                            </h3>

                                                            <!-- Giá (màu xám) -->
                                                            <p class="product-card__price">
                                                                Price: ${lb.getPrice()} VND
                                                            </p>

                                                            <!-- Nút Add to cart (full-width) -->
                                                            <a href="#" class="btn product-card__button">
                                                                <i class="fa fa-shopping-cart"></i> Add to cart
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>

                                                <!-- Đóng row/slide khi đủ 3 sản phẩm hoặc đến cuối list -->
                                                <c:if test="${status.index % 3 == 2 || status.last}">
                                                </div>  <!-- đóng .row -->
                                            </div>  <!-- đóng .item -->
                                        </c:if>
                                    </c:forEach>
                                </div> <!-- /.carousel-inner -->

                                <!-- Controls -->
                                <a class="left recommended-item-control" href="#recommended-item-carousel" data-slide="prev">
                                    <i class="fa fa-angle-left"></i>
                                </a>
                                <a class="right recommended-item-control" href="#recommended-item-carousel" data-slide="next">
                                    <i class="fa fa-angle-right"></i>
                                </a>
                            </div> <!-- /.carousel -->
                        </div> <!-- /.recommended_items -->


                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="/ZeShopper/footer.jsp"/>

        <div id="success-popup" class="success-toast">Added to cart successfully!</div>
        <script>
            document.getElementById("addToCartForm").addEventListener("submit", function (e) {
                e.preventDefault(); // Ngăn form reload

                const bouquetId = document.getElementById("popup-id").value;
                const quantity = document.getElementById("popup-quantity").value;
                
                console.log(document.getElementById("popup-id"))

                const formData = new URLSearchParams();
                formData.append("action", "add");
                formData.append("bouquetId", bouquetId);
                formData.append("quantity", quantity);

                for (const [key, value] of formData.entries()) {
                    console.log(key, `:`, value);
                }

                fetch("ZeShopper/cart", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: formData.toString()
                })
                        .then(res => res.json())
                        .then(data => {
                            
                            
                            if (data.status === "added") {
                                showSuccessPopup("Added to cart successfully!");
                            } else {
                                alert("Error: " + data.status);
                            }
                        })
                        .catch(err => {
                            console.error("Error adding to cart:", err);
                            alert("Something went wrong.");
                        });
            });

            function showSuccessPopup(message) {
                const successBox = document.getElementById("success-popup");
                console.log(successBox)
                successBox.innerText = message;
                successBox.style.display = "block";

                // Ẩn sau 3 giây
                setTimeout(() => {
                    successBox.style.display = "none";
                }, 3000);
            }
        </script>



        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </body>
</html>
