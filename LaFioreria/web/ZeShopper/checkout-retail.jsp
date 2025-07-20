<%-- 
    Document   : checkout-retail
    Created on : Jul 19, 2025, 3:59:54 PM
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <section id="checkout-retail">
            <h2>Retail Order Summary</h2>
            <c:if test="${empty cartDetails}">
                <div class="empty-cart">
                    <p>Your cart is empty. Add items to proceed.</p>
                    <a href="${pageContext.request.contextPath}/product" class="btn btn-primary">Continue Shopping</a>
                </div>
            </c:if>

            <c:if test="${not empty cartDetails}">
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Image</th>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Total</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${cartDetails}">
                                <tr>
                                    <td>
                                        <c:set var="imageShown" value="false" />
                                        <c:forEach var="img" items="${cartImages}">
                                            <c:if test="${!imageShown and item.getBouquetId() eq img.getbouquetId()}">
                                                <img src="${pageContext.request.contextPath}/upload/BouquetIMG/${img.getImage_url()}" alt="alt" style="width: 100px;"/>
                                                <c:set var="imageShown" value="true" />
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>${item.bouquet.bouquetName}</td>
                                    <td><fmt:formatNumber value="${item.bouquet.sellPrice}" pattern="#,#00" /> ₫</td>
                                    <td>
                                        <form method="post" action="checkout">
                                            <input type="hidden" name="action" value="update">
                                            <input type="hidden" name="bouquetId" value="${item.bouquet.bouquetId}">
                                            <input type="number" name="quantity" value="${item.quantity}" min="1">
                                            <button type="submit" class="btn btn-sm btn-default">Update</button>
                                        </form>
                                    </td>
                                    <td><fmt:formatNumber value="${item.bouquet.sellPrice * item.quantity}" pattern="#,#00" /> ₫</td>
                                    <td>
                                        <form method="post" action="checkout">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="bouquetId" value="${item.bouquet.bouquetId}">
                                            <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="text-right">
                        <h4 id="orderFinalTotal" data-total="${totalAmount}">
                            Total: <fmt:formatNumber value="${totalAmount}" pattern="#,#00" /> ₫
                        </h4>
                    </div>
                </div>
            </c:if>
        </section>
    </body>
</html>
