<%-- 
    Document   : cart
    Created on : May 19, 2025, 8:44:15 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                <div class="table-responsive cart_info">
                    <table class="table table-condensed">
                        <thead>
                            <tr class="cart_menu">
                                <td class="image">Item</td>
                                <td class="description"></td>
                                <td class="price">Price</td>
                                <td class="quantity">Quantity</td>
                                <td class="total">Total</td>
                                <td></td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="total" value="0"/>
                            <c:forEach var="item" items="${cartDetails}">
                                <tr>
                                    <td class="cart_product">
                                        <img src="${item.bouquet.imageUrl}" alt="${item.bouquet.bouquetName}" width="100">
                                    </td>
                                    <td class="cart_description">
                                        <h4>${item.bouquet.bouquetName}</h4>
                                        <p>${item.bouquet.description}</p>
                                    </td>
                                    <td class="cart_price">
                                        <p>$${item.bouquet.price}</p>
                                    </td>
                                    <td class="cart_quantity">
                                        <div class="cart_quantity_button">
                                            <form action="cart" method="post" style="display: flex;">
                                                <input type="hidden" name="bouquetId" value="${item.bouquet.bouquetId}">
                                                <input type="hidden" name="action" value="update">
                                                <input class="cart_quantity_input" type="number" name="quantity" value="${item.quantity}" min="1" style="width: 50px; text-align: center;">
                                                <button type="submit" class="btn btn-xs">Update</button>
                                            </form>
                                        </div>
                                    </td>
                                    <td class="cart_total">
                                        <p class="cart_total_price">$${item.bouquet.price * item.quantity}</p>
                                    </td>
                                    <td class="cart_delete">
                                        <form action="cart" method="post">
                                            <input type="hidden" name="bouquetId" value="${item.bouquet.bouquetId}">
                                            <input type="hidden" name="action" value="delete">
                                            <button type="submit" class="btn btn-danger btn-sm"><i class="fa fa-times"></i></button>
                                        </form>
                                    </td>
                                </tr>
                                <c:set var="total" value="${total + item.bouquet.price * item.quantity}"/>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </section> <!--/#cart_items-->

        <section id="do_action">
            <div class="container">
                <!--                <div class="heading">
                                    <h3>What would you like to do next?</h3>
                                    <p>Choose if you have a discount code or reward points you want to use or would like to estimate your delivery cost.</p>
                                </div>-->
                <div class="row">
                    <div class="col-sm-6" style="float: right; border: none">
                        <div class="total_area" style=" border: none">
                            <ul>
                                <li style=" background-color:  white"><strong>Total</strong> <span>$${total}</span></li>
                            </ul>
                            <div style="display: flex; justify-content:  end">
                                <a class="btn btn-default check_out" href="javascript:void(0)" onclick="openCheckoutPopup()">Check Out</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="checkoutPopup" class="popup-overlay" style="display: none;">
                <div class="popup-content">
                    <h3>Confirm Your Information</h3>
                    <form action="checkout" method="post">
                        <input type="hidden" name="total" value="${total}" required>
                        <label>Full Name:</label>
                        <input type="text" name="fullName" value="${user.fullname}" required>

                        <label>Email:</label>
                        <input type="email" name="email" value="${user.email}" required>

                        <label>Phone:</label>
                        <input type="text" name="phone" pattern="\d{10}" value="${user.phone}" maxlength="10" required>

                        <label>Address:</label>
                        <input type="text" name="address" value="${user.address}" required>

                        <div class="popup-buttons">
                            <button type="submit" class="popup-btn">Confirm Order</button>
                            <button type="button" onclick="closeCheckoutPopup()" class="popup-btn cancel">Cancel</button>
                        </div>
                    </form>
                </div>
            </div>

        </section><!--/#do_action-->


        <jsp:include page="/ZeShopper/footer.jsp"/>

        <script>
                    function openCheckoutPopup() {
                        document.getElementById("checkoutPopup").style.display = "flex";
                    }

                    function closeCheckoutPopup() {
                        document.getElementById("checkoutPopup").style.display = "none";
                    }
        </script>

    </body>
</html>
