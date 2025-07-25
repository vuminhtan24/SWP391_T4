<%-- 
    Document   : shop
    Created on : May 19, 2025, 8:46:52 AM
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

        <form action="${pageContext.request.contextPath}/product" method="get" onsubmit="return validateRange()">
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

            <section style="width: 100%; box-sizing: border-box; padding: 20px 0;">
                <div class="container" style="display: flex; flex-wrap: wrap;">

                    <!-- LEFT SIDEBAR (25%) -->
                    <div class="left-sidebar" style="
                         flex: 0 0 25%;
                         max-width: 25%;
                         box-sizing: border-box;
                         padding-right: 15px;
                         ">
                        <!-- Category -->
                        <h2>Category</h2>
                        <c:forEach var="category" items="${cateBouquetHome}">
                            <label>
                                <input type="radio"
                                       name="categoryId"
                                       value="${category.categoryId}"
                                       ${param.categoryId == category.categoryId ? 'checked' : ''} />
                                ${category.categoryName}
                            </label><br/>
                        </c:forEach>

                        <!-- Flower -->
                        <h2>Flower</h2>
                        <c:forEach var="flower" items="${listFlower}">
                            <label>
                                <input type="radio"
                                       name="flowerID"
                                       value="${flower.getFlowerId()}"
                                       ${param.flowerID == flower.getFlowerId() ? 'checked' : ''} />
                                ${flower.getFlowerName()}
                            </label><br/>
                        </c:forEach>

                        <!-- Price Range -->
                        <h2 style="font-size: 20px; margin-bottom: 10px; color: #333; text-align: center;">Price Range</h2>
                        <div style="
                             text-align: center;
                             padding: 20px;
                             border: 1px solid #ccc;
                             border-radius: 10px;
                             margin-bottom: 20px;
                             ">

                            <!-- Min Price -->
                            <label for="minPrice" style="display: block; margin-bottom: 5px; color: #555;">Min Price</label>
                            <input
                                type="range"
                                id="minPrice"
                                name="minPrice"
                                min="0"
                                max="10000000"
                                step="1000"
                                value="${minPrice != null ? minPrice : 0}"
                                oninput="updateFormattedPrice('minPrice', 'minPriceOutput')"
                                style="width: 80%; accent-color: orange; margin-bottom: 5px;"
                                >
                            <output id="minPriceOutput" style="display: block; margin-bottom: 20px; color: #555;">
                                <fmt:formatNumber value="${minPrice != null ? minPrice : 0}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫
                            </output>

                            <!-- Max Price -->
                            <label for="maxPrice" style="display: block; margin-bottom: 5px; color: #555;">Max Price</label>
                            <input
                                type="range"
                                id="maxPrice"
                                name="maxPrice"
                                min="0"
                                max="10000000"
                                step="1000"
                                value="${maxPrice != null ? maxPrice : 1000000}"
                                oninput="updateFormattedPrice('maxPrice', 'maxPriceOutput')"
                                style="width: 80%; accent-color: orange; margin-bottom: 5px;"
                                >
                            <output id="maxPriceOutput" style="display: block; margin-bottom: 20px; color: #555;">
                                <fmt:formatNumber value="${maxPrice != null ? maxPrice : 1000000}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫
                            </output>

                            <div id="error" style="color: red; margin-bottom: 10px;"></div>

                        </div>
                        <div style="display: flex; align-items: center; gap: 20px; margin-top: 20px;">
                            <!-- Apply filters -->
                            <button type="submit"
                                    style="background-color:orange;
                                    color:#fff;
                                    padding:10px 20px;
                                    border:none;
                                    border-radius:5px;">
                                Apply filters
                            </button>

                            <!-- Reset Filter: về trang /product không kèm tham số -->
                            <button type="button"
                                    onclick="window.location.href = '${pageContext.request.contextPath}/product';"
                                    style="background-color:orange;
                                    color:#fff;
                                    padding:10px 20px;
                                    border:none;
                                    border-radius:5px;">
                                Reset Filter
                            </button>
                        </div>   
                        </form>
                    </div>
                    <!-- END LEFT SIDEBAR -->

                    <!-- RIGHT CONTENT (75%) -->
                    <div class="col-sm-9 padding-right" style="
                         flex: 0 0 75%;
                         max-width: 75%;
                         box-sizing: border-box;
                         padding-left: 15px;
                         ">
                        <!-- Popup giữ nguyên chức năng -->
                        <!-- Popup Modal -->
                        <div id="popup" class="popup-overlay" style="display:none;">
                            <div class="popup-content">
                                <span class="close-btn" onclick="closePopup()">&times;</span>
                                <form id="addToCartForm">
                                    <h3 id="popup-name"></h3>
                                    <img id="popup-image" src="" alt="" class="popup-img">
                                    <p id="popup-price" class="popup-price"></p>
                                    <p>Available: <span id="popup-available"></span></p>
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

                        <h2 class="title text-center" style="margin-bottom: 20px;">Features Items</h2>
                        <c:if test="${not empty error}">
                            <p style="color:red;">${error}</p>
                        </c:if>
                        <c:forEach items="${requestScope.listBouquet}" var="lb">
                            <c:set var="available" value="${bouquetAvailableMap[lb.bouquetId]}" />
                            <c:if test="${lb.getStatus() eq 'valid' and available != null and available gt 0}">
                            <div class="col-sm-4" style="
                                 box-sizing: border-box;
                                 padding: 10px;
                                 float: left;
                                 width: 33.3333%;
                                 ">
                                <div class="product-image-wrapper" style="border: 1px solid #eee; padding: 10px; border-radius: 4px;">
                                    <div class="single-products">
                                        <div class="productinfo text-center">
                                            <c:set var="imageShown" value="false" />
                                            <c:forEach items="${images}" var="img">
                                                <c:if test="${!imageShown and lb.getBouquetId() == img.getbouquetId()}">
                                                    <img
                                                        src="${pageContext.request.contextPath}/upload/BouquetIMG/${img.getImage_url()}"
                                                        alt=""
                                                        style="
                                                        width: 100%;
                                                        height: auto;
                                                        max-height: 200px;
                                                        object-fit: cover;
                                                        margin-bottom: 10px;
                                                        "
                                                        />
                                                    <c:set var="imageShown" value="true" />
                                                </c:if>
                                            </c:forEach>

                                            <h2 style="font-size: 16px; margin-bottom: 8px;">
                                                <a
                                                    href="${pageContext.request.contextPath}/productDetail?id=${lb.getBouquetId()}"
                                                    style="color: #333; text-decoration: none;"
                                                    >
                                                    ${lb.getBouquetName()}
                                                </a>
                                            </h2>
                                            <p style="margin-bottom: 10px;">Price: <fmt:formatNumber value="${lb.getSellPrice()}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫</p>
                                            <!-- Đây là nút Add to Cart gốc, không thay đổi -->
                                            

                                            <button
                                                type="button"
                                                class="btn btn-default add-to-cart"
                                                <c:forEach items="${images}" var="cimg">
                                                    <c:if test="${lb.getBouquetId() == cimg.getbouquetId()}">
                                                        onclick="openPopup(
                                                                        '${lb.getBouquetId()}',
                                                                        '${lb.getBouquetName()}',
                                                                        '${pageContext.request.contextPath}/upload/BouquetIMG/${cimg.getImage_url()}',
                                                                                        '${lb.getSellPrice()}',
                                                                                        '${available}'
                                                                                        )"
                                                    </c:if>            
                                                </c:forEach>                
                                                style="background-color: #5cb85c; color: white; border: none; padding: 8px 12px; border-radius: 4px; cursor: pointer;"
                                                >
                                                <i class="fa fa-shopping-cart" aria-hidden="true"></i>
                                                Add to cart
                                            </button>

                                        </div>
                                    </div>
                                </div>
                            </div>
                            </c:if>                
                        </c:forEach>
                        <div style="clear: both;"></div>

                        <!-- Phân trang -->
                        <ul class="pagination"
                            style="
                            margin-top: 20px;
                            display: flex;
                            list-style: none;
                            padding: 0;
                            justify-content: flex-end;">
                            <c:if test="${currentPage > 1}">
                                <li style="margin-right: 5px;">
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
                                       </c:url>"
                                       style="display: block; padding: 6px 12px; border: 1px solid #ddd; border-radius: 4px; text-decoration: none;">
                                        &laquo;
                                    </a>
                                </li>
                            </c:if>

                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li style="margin-right: 5px;">
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
                                       </c:url>"
                                       style="
                                       display: block;
                                       padding: 6px 12px;
                                       border: 1px solid #ddd;
                                       border-radius: 4px;
                                       text-decoration: none;
                                       ${(i == currentPage) ? 'background: #007bff; color: white;' : 'color: #007bff;'}
                                       ">
                                        ${i}
                                    </a>
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
                                       </c:url>"
                                       style="display: block; padding: 6px 12px; border: 1px solid #ddd; border-radius: 4px; text-decoration: none;">
                                        &raquo;
                                    </a>
                                </li>
                            </c:if>
                        </ul>
                        <!-- End Phân trang -->

                    </div>
                    <!-- END RIGHT CONTENT -->

                </div>
            </section>
            <jsp:include page="/ZeShopper/footer.jsp"/>


            <div id="success-popup" class="success-toast">Added to cart successfully!</div>

            <script>
                function updateFormattedPrice(inputId, outputId) {
                    const value = document.getElementById(inputId).value;
                    const formatted = new Intl.NumberFormat('vi-VN').format(value) + ' ₫';
                    document.getElementById(outputId).textContent = formatted;
                }
            </script>
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
                    document.getElementById("popup-available").textContent = available;

                    const quantityInput = document.getElementById("popup-quantity");
                    quantityInput.value = 1;
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
    </body>
</html>
