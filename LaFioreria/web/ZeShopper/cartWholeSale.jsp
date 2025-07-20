<%-- 
    Document   : cartWholeSale
    Created on : Jul 19, 2025, 2:32:56 AM
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
        <title>Cart | E-Shopper</title>
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
        <link rel="shortcut icon" href="images/ico/favicon.ico">
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
                background: rgba(0,0,0,0.6);
                display: flex;
                justify-content: center;
                align-items: center;
                z-index: 999;
            }

            .popup-content {
                background: #fff;
                padding: 25px;
                border-radius: 10px;
                width: 400px;
                max-width: 90%;
                box-shadow: 0 0 15px rgba(0,0,0,0.3);
                animation: fadeIn 0.3s ease;
            }

            .popup-content h3 {
                margin-top: 0;
                text-align: center;
            }

            .popup-content label {
                display: block;
                margin-top: 10px;
                font-weight: bold;
            }

            .popup-content input {
                width: 100%;
                padding: 8px;
                margin-top: 5px;
                box-sizing: border-box;
            }

            .popup-buttons {
                display: flex;
                justify-content: space-between;
                margin-top: 20px;
            }

            .popup-btn {
                padding: 10px 18px;
                background-color: #5cb85c;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }

            .popup-btn.cancel {
                background-color: #d9534f;
            }

            @keyframes fadeIn {
                from {
                    opacity: 0;
                    transform: scale(0.95);
                }
                to {
                    opacity: 1;
                    transform: scale(1);
                }
            }

            .guest-notice {
                background-color: #d9edf7;
                border: 1px solid #bce8f1;
                color: #31708f;
                padding: 15px;
                margin-bottom: 20px;
                border-radius: 4px;
            }

            .empty-cart {
                text-align: center;
                padding: 50px;
                color: #666;
            }

            .cart-switch-buttons {
                display: flex;
                justify-content: flex-end;
                gap: 10px;
                margin-bottom: 20px;
            }

            .btn-retail, .btn-wholesale {
                padding: 10px 20px;
                border-radius: 8px;
                font-weight: bold;
                text-decoration: none;
                color: white;
                display: inline-block;
                transition: background-color 0.3s ease;
            }

            .btn-retail {
                background-color: #5bc0de; /* xanh dương nhạt */
            }

            .btn-retail:hover {
                background-color: #31b0d5;
            }

            .btn-wholesale {
                background-color: #f0ad4e; /* vàng cam */
            }

            .btn-wholesale:hover {
                background-color: #ec971f;
            }
        </style>
    </head><!--/head-->

    <body>
        <jsp:include page="/ZeShopper/header.jsp"/>

        <section id="cart_items">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="#">Home</a></li>
                        <li class="active">Shopping Cart</li>
                    </ol>
                </div>

                <div class="cart-switch-buttons">
                    <a href="${pageContext.request.contextPath}/ZeShopper/cart" class="btn-retail">Retail Cart</a>
                    <a href="${pageContext.request.contextPath}/cartWholeSale" class="btn-wholesale">Wholesale Cart</a>
                </div>

                <!-- Guest User Notice -->
                <c:if test="${isGuest}">
                    <div class="guest-notice">
                        <i class="fa fa-info-circle"></i>
                        You are shopping as a guest. <a href="${pageContext.request.contextPath}/ZeShopper/LoginServlet">Login</a> or <a href="${pageContext.request.contextPath}/ZeShopper/LoginServlet">Register</a> to save your cart and track your orders.
                    </div>
                </c:if>

                <!-- Error Message Display -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">
                        <i class="fa fa-exclamation-triangle"></i> ${error}
                    </div>
                </c:if>

                <!-- Empty Cart Message -->
                <c:if test="${empty listCartWholeSale}">
                    <div class="empty-cart">
                        <i class="fa fa-shopping-cart fa-5x" style="color: #ccc;"></i>
                        <h3>Your cart is empty</h3>
                        <p>Add some beautiful bouquets to your cart to get started!</p>
                        <div style="display: flex; justify-content: center; gap: 10px;">
                            <a href="${pageContext.request.contextPath}/product" class="btn btn-primary">Continue Shopping</a>
                        </div>
                    </div>
                </c:if>

                <!-- Cart Items Table -->
                <c:if test="${not empty listCartWholeSale}">
                    <div class="table-responsive cart_info">
                        <table class="table table-condensed">
                            <thead>
                                <tr class="cart_menu">
                                    <td class="image">Item Detail</td>
                                    <td class="description"></td>
                                    <td class="price">Price</td>
                                    <td class="quantity">Quantity</td>
                                    <td class="total">Total</td>
                                    <td></td>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="total" value="0"/>
                                <c:forEach var="item" items="${listCartWholeSale}">
                                    <tr>
                                        <td class="cart_product">
                                            <c:set var="imageShown" value="false" />
                                            <c:forEach var="img" items="${listIMG}">
                                                <c:if test="${!imageShown and item.getBouquetID() eq img.getbouquetId()}">
                                                    <img src="${pageContext.request.contextPath}/upload/BouquetIMG/${img.getImage_url()}" alt="alt" style="width: 100px;"/>
                                                    <c:set var="imageShown" value="true" />
                                                </c:if>
                                            </c:forEach>
                                        </td>

                                        <td class="cart_description">
                                            <c:forEach var="bouquet" items="${listBQ}">
                                                <c:if test="${item.getBouquetID() eq bouquet.getBouquetId()}">
                                                    <h4>${bouquet.getBouquetName()}</h4>
                                                    <p>${bouquet.getDescription()}</p>
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td class="cart_price">
                                            <fmt:formatNumber value="${item.getPricePerUnit()}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫
                                        </td>
                                        <td class="cart_quantity">
                                            ${item.getQuantity()}
                                        </td>
                                        <td class="cart_total">
                                            <fmt:formatNumber value="${item.getTotalValue()}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>                    
                </c:if>
        </section> <!--/#cart_items-->

        <!-- Checkout Section - Only show if cart has items -->
        <c:if test="${not empty listCartWholeSale}">
            <section id="do_action">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6" style="float: right; border: none">
                            <div class="total_area" style="border: none">
                                <ul>
                                    <li style="background-color: white"><strong>Total</strong> <span><p><fmt:formatNumber value="${totalOrderValue}" pattern="#,##0" /> ₫</p></span></li>
                                </ul>
                                <div style="display: flex; justify-content: end;">
                                    <a class="btn btn-default check_out" href="${pageContext.request.contextPath}/checkout?mode=wholesale">Check Out</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </c:if>

        <jsp:include page="/ZeShopper/footer.jsp"/>

        <script>
            // Close popup when clicking outside
            document.addEventListener('click', function (event) {
                const popup = document.getElementById('checkoutPopup');
                if (popup && event.target === popup) {
                    closeCheckoutPopup();
                }
            });

            // Close popup with Escape key
            document.addEventListener('keydown', function (event) {
                if (event.key === 'Escape') {
                    closeCheckoutPopup();
                }
            });
        </script>
    </body>
</html>
