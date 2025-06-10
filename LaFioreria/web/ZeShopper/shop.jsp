<%-- 
    Document   : shop
    Created on : May 19, 2025, 8:46:52 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List,model.Bouquet" %>
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
        <title>Shop | E-Shopper</title>
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
            .productinfo {
                min-height: 400px; /* chỉnh tùy theo độ dài nội dung */
                display: flex;
                flex-direction: column;
                justify-content: space-between;
            }

            .productinfo img {
                height: 200px;
                object-fit: cover;
            }

            .single-products {
                height: 100%;
            }

            .product-image-wrapper {
                border: 1px solid #f0f0f0;
                padding: 10px;
                height: 100%;
            }

            .close-btn:hover {
                color: #000;
            }

            @keyframes fadeIn {
                from {
                    opacity: 0;
                }
                to {
                    opacity: 1;
                }
            }
            @keyframes scaleUp {
                from {
                    transform: scale(0.95);
                    opacity: 0;
                }
                to {
                    transform: scale(1);
                    opacity: 1;
                }
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
            .productinfo {
                min-height: 400px; /* chỉnh tùy theo độ dài nội dung */
                display: flex;
                flex-direction: column;
                justify-content: space-between;
            }

            .productinfo img {
                height: 200px;
                object-fit: cover;
            }

            .single-products {
                height: 100%;
            }

            .product-image-wrapper {
                border: 1px solid #f0f0f0;
                padding: 10px;
                height: 100%;
            }

            .category-button {
                background: none;       /* Bỏ màu nền */
                border: none;           /* Bỏ viền */
                padding: 0;             /* Bỏ khoảng đệm */
                margin: 0;              /* Bỏ margin */
                font: inherit;          /* Kế thừa toàn bộ font-family/ font-size/ font-weight từ h4 */
                color: inherit;         /* Kế thừa màu chữ từ h4 */
                cursor: pointer;        /* Vẫn hiện con trỏ tay khi hover */
                text-align: inherit;    /* Kế thừa canh lề (nếu cần) */
                display: inline;        /* Giữ nguyên kiểu inline để không giãn block */
                text-decoration: none;  /* Bỏ gạch chân (nếu có) */
            }

            .category-button:hover {
                text-decoration: underline; /* Hoặc đổi màu, tuỳ thích */
            }

            /* Chỉ ví dụ highlight category đang chọn */
            .selected-category h4 .category-button {
                font-weight: bold;
                color: #d35400;
            }
        </style>
    </head><!--/head-->

    <body>
        <jsp:include page="/ZeShopper/header.jsp"/>

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

            <section id="advertisement">
                <div class="container">
                    <img src="${pageContext.request.contextPath}/ZeShopper/images/shop/advertisement.jpg" alt="" />
                </div>
            </section>

            <section>
                <div class="container">
                    <div class="row">



                        <div class="col-sm-3">
                            <div class="left-sidebar">
                                <h2>Category</h2>
                                <div class="panel-group category-products" id="accordian" style="margin-bottom: 10px"><!--category-productsr-->   


                                    <c:forEach var="category" items="${cateBouquetHome}">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title" style="color: #7d7e82">
                                                    <button
                                                        type="submit"
                                                        name="categoryId"
                                                        value="${category.categoryId}"
                                                        class="category-button">
                                                        ${category.getCategoryName()}
                                                    </button>
                                                </h4>
                                            </div>
                                        </div>
                                    </c:forEach>    

                                </div><!--/category-products-->

                                <!-- Price range -->    
                                <h2 style="text-align: center;">Price Range</h2>   
                                <div style="text-align: center; padding: 20px; border: 1px solid #ccc; border-radius: 10px;">
                                    <!-- Min -->
                                    <label for="minPrice" style="display: block; margin-bottom: 5px;">Min Price</label>
                                    <input
                                        type="range"
                                        id="minPrice"
                                        name="minPrice"
                                        min="0"
                                        max="2000000"
                                        step="1000"
                                        value="${minPrice != null ? minPrice : 0}"
                                        oninput="this.nextElementSibling.value = this.value"
                                        style="width: 80%; accent-color: orange; margin-bottom: 5px;"
                                        >
                                    <output style="display: block; margin-bottom: 15px;">${minPrice != null ? minPrice : 0}</output>

                                    <!-- Max -->
                                    <label for="maxPrice" style="display: block; margin-bottom: 5px;">Max Price</label>
                                    <input
                                        type="range"
                                        id="maxPrice"
                                        name="maxPrice"
                                        min="0"
                                        max="2000000"
                                        step="1000"
                                        value="${maxPrice != null ? maxPrice : 2000000}"
                                        oninput="this.nextElementSibling.value = this.value"
                                        style="width: 80%; accent-color: orange; margin-bottom: 5px;"
                                        >
                                    <output style="display: block; margin-bottom: 20px;"> ${maxPrice != null ? maxPrice : 2000000}</output>

                                    <!-- Error Message -->
                                    <div id="error" style="color: red; margin-bottom: 10px;"></div>

                                    <!-- Submit -->
                                    <input
                                        type="submit"
                                        value="Submit"
                                        style="background-color: orange; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer;"
                                        >                                 
                                    <!-- Price range end -->
                                    </form>
                                </div>

                                <div class="shipping text-center"><!--shipping-->
                                    <img src="${pageContext.request.contextPath}/ZeShopper/images/home/shipping.jpg" alt="" />
                                </div><!--/shipping-->

                            </div>
                        </div>

                        <div class="col-sm-9 padding-right">
                            <div class="features_items"><!--features_items-->
                                <div id="popup" class="popup-overlay" style="display:none;">
                                    <div class="popup-content">
                                        <span class="close-btn" onclick="closePopup()">&times;</span>
                                        <form id="addToCartForm">
                                            <h3 id="popup-name"></h3>
                                            <img id="popup-image" src="" alt="" class="popup-img">
                                            <p id="popup-price" class="popup-price"></p>
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
                                <h2 class="title text-center">Features Items</h2>

                                <c:forEach items="${requestScope.listBouquet}" var="lb">
                                    <div class="col-sm-4">
                                        <div class="product-image-wrapper">
                                            <div class="single-products">
                                                <div class="productinfo text-center">
                                                    <img src="${lb.getImageUrl()}" alt="" />
                                                    <h2>
                                                        <a href="${pageContext.request.contextPath}/productDetail?id=${lb.getBouquetId()}">
                                                            ${lb.getBouquetName()}
                                                        </a>
                                                    </h2>
                                                    <p>Price: ${lb.getPrice()}</p>
                                                    <button 
                                                        type="button"                                   
                                                        class="btn btn-default add-to-cart" 
                                                        onclick="openPopup(
                                                        '${lb.getBouquetId()}',
                                                        '${lb.getBouquetName()}',
                                                        '${lb.getImageUrl()}',
                                                        '${lb.getPrice()}'
                                                        )"
                                                        >
                                                        <i class="fa fa-shopping-cart"></i> Add to cart
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>

                            </div>
                        </div>

                        <ul class="pagination">
                            <c:if test="${currentPage > 1}">
                                <li>
                                    <a href="<c:url value='product'>
                                           <c:param name='page' value='${currentPage - 1}'/>
                                           <c:if test='${not empty param.bouquetName}'>
                                               <c:param name='bouquetName' value='${param.bouquetName}'/>
                                           </c:if>
                                           <c:if test='${not empty param.categoryId}'>
                                               <c:param name='categoryId' value='${param.categoryId}'/>
                                           </c:if>
                                           <c:if test='${not empty param.minPrice}'>
                                               <c:param name='minPrice' value='${param.minPrice}'/>
                                           </c:if>
                                           <c:if test='${not empty param.maxPrice}'>
                                               <c:param name='maxPrice' value='${param.maxPrice}'/>
                                           </c:if>
                                       </c:url>">&laquo;</a>
                                </li>
                            </c:if>

                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="${i == currentPage ? 'active' : ''}">
                                    <a href="<c:url value='product'>
                                           <c:param name='page' value='${i}'/>
                                           <c:if test='${not empty param.bouquetName}'>
                                               <c:param name='bouquetName' value='${param.bouquetName}'/>
                                           </c:if>
                                           <c:if test='${not empty param.categoryId}'>
                                               <c:param name='categoryId' value='${param.categoryId}'/>
                                           </c:if>
                                           <c:if test='${not empty param.minPrice}'>
                                               <c:param name='minPrice' value='${param.minPrice}'/>
                                           </c:if>
                                           <c:if test='${not empty param.maxPrice}'>
                                               <c:param name='maxPrice' value='${param.maxPrice}'/>
                                           </c:if>
                                       </c:url>">${i}</a>
                                </li>
                            </c:forEach>

                            <c:if test="${currentPage < totalPages}">
                                <li>
                                    <a href="<c:url value='product'>
                                           <c:param name='page' value='${currentPage + 1}'/>
                                           <c:if test='${not empty param.bouquetName}'>
                                               <c:param name='bouquetName' value='${param.bouquetName}'/>
                                           </c:if>
                                           <c:if test='${not empty param.categoryId}'>
                                               <c:param name='categoryId' value='${param.categoryId}'/>
                                           </c:if>
                                           <c:if test='${not empty param.minPrice}'>
                                               <c:param name='minPrice' value='${param.minPrice}'/>
                                           </c:if>
                                           <c:if test='${not empty param.maxPrice}'>
                                               <c:param name='maxPrice' value='${param.maxPrice}'/>
                                           </c:if>
                                       </c:url>">&raquo;</a>
                                </li>
                            </c:if>
                        </ul>

                    </div><!--features_items-->
                </div>
                </div>
                </div>
            </section>
            <jsp:include page="/ZeShopper/footer.jsp"/>
            <script src="${pageContext.request.contextPath}/ZeShopper/js/gmaps.js"></script>
            <script src="${pageContext.request.contextPath}/ZeShopper/js/contact.js"></script>
            <script src="${pageContext.request.contextPath}/ZeShopper/js/jquery.js"></script>
            <script src="${pageContext.request.contextPath}/ZeShopper/js/price-range.js"></script>
            <script src="${pageContext.request.contextPath}/ZeShopper/js/jquery.scrollUp.min.js"></script>
            <script src="${pageContext.request.contextPath}/ZeShopper/js/bootstrap.min.js"></script>
            <script src="${pageContext.request.contextPath}/ZeShopper/js/jquery.prettyPhoto.js"></script>
            <script src="${pageContext.request.contextPath}/ZeShopper/js/main.js"></script>


            <div id="success-popup" class="success-toast">Added to cart successfully!</div>


            <script>
                                                                function validateRange() {
                                                                    var min = parseInt(document.getElementById("minPrice").value);
                                                                    var max = parseInt(document.getElementById("maxPrice").value);
                                                                    var errorDiv = document.getElementById("error");

                                                                    if (min > max) {
                                                                        errorDiv.innerText = "Giá trị tối thiểu không được lớn hơn giá trị tối đa.";
                                                                        return false; // Ngăn submit
                                                                    }

                                                                    errorDiv.innerText = ""; // Xóa lỗi nếu hợp lệ
                                                                    return true; // Cho phép submit
                                                                }
            </script>

            <script>
                function openPopup(id, name, imageUrl, price, description) {
                    document.getElementById("popup-id").value = id;
                    document.getElementById("popup-name").textContent = name;
                    document.getElementById("popup-image").src = imageUrl;
                    document.getElementById("popup-price").textContent = "Price: " + price;
                    document.getElementById("popup-description").textContent = description;
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
                                console.log(data)
                                if (data.status === "added") {
                                    closePopup(); // Đóng popup sau khi thêm

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
                    successBox.innerText = message;
                    successBox.style.display = "block";

                    // Ẩn sau 3 giây
                    setTimeout(() => {
                        successBox.style.display = "none";
                    }, 3000);
                }
            </script>
    </body>
</html>
