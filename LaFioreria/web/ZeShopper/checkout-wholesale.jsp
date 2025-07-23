<%-- 
    Document   : checkout-wholesale
    Created on : Jul 19, 2025, 4:00:18 PM
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
        <section id="checkout-wholesale">
            <h2>Wholesale Order Summary</h2>

            <c:if test="${empty listCartWholeSale}">
                <div class="empty-cart">
                    <i class="fa fa-shopping-cart fa-5x" style="color: #ccc;"></i>
                    <h3>Your WholeSale cart is empty</h3>
                    <p>Add some beautiful bouquets to your cart to get started!</p>
                    <a href="${pageContext.request.contextPath}/product" class="btn btn-primary">Continue Shopping</a>
                </div>
            </c:if>

            <c:if test="${not empty listCartWholeSale}">
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Image</th>
                                <th>Product</th>
                                <th>Price/Unit</th>
                                <th>Quantity</th>
                                <th>Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${listCartWholeSale}">
                                <tr>
                                    <td>
                                        <c:set var="imageShown" value="false" />
                                        <c:forEach var="img" items="${listIMG}">
                                            <c:if test="${!imageShown and item.getBouquetID() eq img.getbouquetId()}">
                                                <img src="${pageContext.request.contextPath}/upload/BouquetIMG/${img.getImage_url()}" alt="alt" style="width: 100px; height: 100px;"/>
                                                <c:set var="imageShown" value="true" />
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach var="bq" items="${listBQ}">
                                            <c:if test="${bq.bouquetId eq item.bouquetID}">
                                                ${bq.bouquetName}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td><fmt:formatNumber value="${item.pricePerUnit}" pattern="#,#00" /> ₫</td>
                                    <td>${item.quantity}</td>
                                    <td><fmt:formatNumber value="${item.totalValue}" pattern="#,#00" /> ₫</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="text-right">
                        <p style="font-weight: bold; font-size: 20px;">Total</p>
                        <span id="orderFinalTotal" style="font-weight: bold; font-size: 15px;">
                            <p style="margin: 0;"><fmt:formatNumber value="${totalOrderValue}" pattern="#,#00" /> ₫</p>
                        </span>

                    </div>
                </div>

                <div class="payment-options mt-3">
                    <span>
                        <label><input name="paymentMethod" type="radio" value="cod" id="payment-cod"
                                      ${not empty savedFormData && savedFormData.paymentMethod eq 'cod' ? 'checked' : ''}> 
                            Cash on Delivery (COD)</label>
                    </span>
                    <span>
                        <label><input name="paymentMethod" type="radio" value="vietqr" id="payment-vietqr"
                                      ${not empty savedFormData && savedFormData.paymentMethod eq 'vietqr' ? 'checked' : ''}> 
                            VietQR (QR Transfer)</label>
                    </span>
                    <div class="error-message" id="payment-error"></div>
                </div>
                <div class="text-right mt-3">
                    <button class="btn btn-primary" onclick="submitOrder()" id="place-order-btn">Place Order</button>
                </div>  
            </c:if>

        </section>

    </body>
</html>
