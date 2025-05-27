<%-- 
    Document   : wishlist
    Created on : May 19, 2025, 8:33:40 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Wishlist | E-Shopper</title>
    <link href="${pageContext.request.contextPath}/ZeShopper/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/ZeShopper/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/ZeShopper/css/main.css" rel="stylesheet">
</head>
<body>

<section id="wishlist_items">
    <div class="container">
        <h2 class="title text-center">My Wishlist</h2>
        <div class="table-responsive cart_info">
            <table class="table table-condensed">
                <thead>
                    <tr class="wishlist_menu">                       
                        <th>Product</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Add to Cart</th>
                        <th>Remove</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${wishlistItems}">
                        <tr>
                            <td class="wishlist_product">
                                <a href="#"><img src="${pageContext.request.contextPath}/images/products/${item.image}" alt="${item.name}" width="100"></a>
                            </td>
                            <td class="wishlist_description">
                                <h4><a href="#">${item.name}</a></h4>
                                <p>Product ID: ${item.id}</p>
                            </td>
                            <td class="wishlist_price">
                                <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="$" />
                            </td>
                            <td class="wishlist_add">
                                <form action="${pageContext.request.contextPath}/addToCart" method="post">
                                    <input type="hidden" name="productId" value="${item.id}" />
                                    <button type="submit" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i> Add</button>
                                </form>
                            </td>
                            <td class="wishlist_delete">
                                <a class="wishlist_delete_btn" href="${pageContext.request.contextPath}/removeFromWishlist?productId=${item.id}"><i class="fa fa-times"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${empty wishlistItems}">
                <div class="alert alert-info text-center">Your wishlist is empty.</div>
            </c:if>
        </div>
    </div>
</section>

<script src="${pageContext.request.contextPath}/ZeShopper/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/ZeShopper/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/ZeShopper/js/main.js"></script>
</body>
</html>

