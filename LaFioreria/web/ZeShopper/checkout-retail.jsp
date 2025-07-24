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
                    <i class="fa fa-shopping-cart fa-5x" style="color: #ccc;"></i>
                    <h3>Your Retail cart is empty</h3>
                    <p>Add some beautiful bouquets to your cart to get started!</p>
                    <a href="${pageContext.request.contextPath}/product" class="btn btn-primary">Continue Shopping</a>
                </div>
            </c:if>

            <c:if test="${not empty cartDetails}">
                <table class="cart-table">
                    <thead>
                        <tr>
                            <th>Item Detail</th>
                            <th></th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Total</th>
                            <th>Remove</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="total" value="0" />
                        <c:forEach var="item" items="${cartDetails}">
                            <tr>
                                <td class="cart_product">
                                    <c:forEach items="${cartImages}" var="imgLst">
                                        <c:set var="found" value="false"/>
                                        <c:forEach items="${imgLst}" var="img">
                                            <c:if test="${img.bouquetId == item.bouquetId && !found}">
                                                <img src="${pageContext.request.contextPath}/upload/BouquetIMG/${img.image_url}" alt="${item.bouquet.bouquetName}" width="100">
                                                <c:set var="found" value="true"/>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </td>
                                <td class="cart_description">
                                    <h4>${item.bouquet.bouquetName}</h4>
                                    <p>${item.bouquet.description}</p>
                                </td>
                                <td class="cart_price">
                                    <p><fmt:formatNumber value="${item.bouquet.sellPrice}" pattern="#,##0" /> ₫</p>
                                </td>
                                <td class="cart_quantity">
                                    <div class="cart_quantity_button">
                                        <form action="checkout" method="post">
                                            <input type="hidden" name="mode" value="retail">
                                            <input type="hidden" name="bouquetId" value="${item.bouquet.bouquetId}">
                                            <input type="hidden" name="action" value="update">
                                            <input class="cart_quantity_input" type="number" name="quantity" value="${item.quantity}" min="1" style="width: 50px; text-align: center;">
                                            <button type="submit" class="btn btn-xs">Update</button>
                                        </form>
                                    </div>
                                </td>
                                <td class="cart_total">
                                    <p class="cart_total_price"><fmt:formatNumber value="${item.bouquet.sellPrice * item.quantity}" pattern="#,##0" /> ₫</p>
                                </td>
                                <td class="cart_delete">
                                    <form action="checkout" method="post">
                                        <input type="hidden" name="mode" value="retail">
                                        <input type="hidden" name="bouquetId" value="${item.bouquet.bouquetId}">
                                        <input type="hidden" name="action" value="delete">
                                        <button type="submit" class="btn btn-danger btn-sm"><i class="fa fa-times"></i></button>
                                    </form>
                                </td>
                            </tr>
                            <c:set var="total" value="${total + item.bouquet.sellPrice * item.quantity}" />
                        </c:forEach>

                        <!-- Tổng kết -->
                        <c:set var="ship" value="30000" />
                        <tr>
                            <td colspan="6">
                                <table class="table table-condensed total-result">
                                    <!-- Mã giảm giá -->
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.currentAcc}">
                                            <form action="checkout" method="post" id="discountForm">
                                                <input type="hidden" name="mode" value="retail">
                                                <input type="hidden" name="action" value="applyDiscount">
                                                <input type="text" name="discountCode" placeholder="Discount code"
                                                       id="discountCodeInput"
                                                       style="margin-bottom: 10px;">
                                                <button type="submit"
                                                        style=" padding: 8px 14px;
                                                        margin-left: 10px;
                                                        margin-bottom: 10px;
                                                        background-color: #ff7f00;
                                                        border: none;
                                                        border-radius: 5px;
                                                        color: white;
                                                        font-size: 14px;
                                                        cursor: pointer;
                                                        transition: background-color 0.3s ease;">Apply</button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <p style="color: red; margin: 10px 0;">
                                                Please <a href="login.jsp">login</a> if you have discount code.
                                            </p>
                                        </c:otherwise>
                                    </c:choose>

                                    <tr>
                                        <td>Cart Subtotal</td>
                                        <td><p><fmt:formatNumber value="${total}" pattern="#,##0" /> ₫</p></td>
                                    </tr>
                                    <tr class="shipping-cost">
                                        <td>Shipping Fee</td>
                                        <td><fmt:formatNumber value="${ship}" pattern="#,##0" /> ₫</td>
                                    </tr>
                                    <c:if test="${not empty calculatedDiscountAmount}">
                                        <tr>
                                            <td>Discount:</td>
                                            <td>- <fmt:formatNumber value="${calculatedDiscountAmount}" pattern="#,##0" /> ₫</td>
                                        </tr>
                                    </c:if>
                                    <tr>
                                        <td><strong>Total</strong></td>
                                        <td>
                                            <!-- Chuyển đổi finalOrderTotal thành số nguyên -->
                                            <fmt:parseNumber var="finalOrderTotalInt" value="${not empty finalOrderTotal ? finalOrderTotal : (total + ship)}" integerOnly="true" />
                                            <span id="orderFinalTotal" style="font-weight: bold; font-size: 15px;">
                                                <p style="margin: 0;"><fmt:formatNumber value="${finalOrderTotalInt}" pattern="#,##0" /> ₫</p>
                                            </span>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <div class="payment-options mt-3">
                    <span>
                        <label style="margin-left: 5px; margin-top: 17px;"><input name="paymentMethod" type="radio" value="cod" id="payment-cod"
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
