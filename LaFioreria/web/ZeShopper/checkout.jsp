<%-- 
    Document   : checkout
    Created on : May 19, 2025, 8:44:49 AM
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
        <title>Checkout | E-Shopper</title>
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
                animation: fadein-bottom 0.5s;
            }

            .error-toast {
                display: none;
                position: fixed;
                top: 30px;
                right: 30px;
                background-color: #f44336;
                color: white;
                padding: 16px 24px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,0.2);
                font-size: 16px;
                z-index: 9999;
                animation: fadein-top 0.5s;
            }

            @keyframes fadein-bottom {
                from {
                    opacity: 0;
                    bottom: 10px;
                }
                to {
                    opacity: 1;
                    bottom: 30px;
                }
            }

            @keyframes fadein-top {
                from {
                    opacity: 0;
                    top: 10px;
                }
                to {
                    opacity: 1;
                    top: 30px;
                }
            }

            /* Validation Styles */
            .form-group {
                margin-bottom: 15px;
                position: relative;
            }

            .error-message {
                color: #d9534f;
                font-size: 12px;
                margin-top: 5px;
                display: none;
            }

            .input-error {
                border: 1px solid #d9534f !important;
                box-shadow: 0 0 0 0.2rem rgba(217, 83, 79, 0.25) !important;
            }

            .input-valid {
                border: 1px solid #5cb85c !important;
                box-shadow: 0 0 0 0.2rem rgba(92, 184, 92, 0.25) !important;
            }

            .required-field::after {
                content: " *";
                color: #d9534f;
            }

            .validation-summary {
                background-color: #f2dede;
                border: 1px solid #ebccd1;
                color: #a94442;
                padding: 15px;
                margin-bottom: 20px;
                border-radius: 4px;
                display: none;
            }

            .validation-summary h4 {
                margin-top: 0;
                margin-bottom: 10px;
            }

            .validation-summary ul {
                margin-bottom: 0;
                padding-left: 20px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/ZeShopper/header.jsp"/>

        <section id="cart_items">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="#">Home</a></li>
                        <li class="active">Checkout</li>
                    </ol>
                </div>
                
                <!-- Validation Summary -->
                <div id="validation-summary" class="validation-summary">
                    <h4>Please correct the following errors:</h4>
                    <ul id="validation-errors"></ul>
                </div>

                <c:if test="${empty cartDetails}">
                    <div class="review-payment">
                        <h4>Nothing in your cart, so that you cannot checkout!</h4>
                    </div>
                </c:if>
                <c:if test="${not empty cartDetails}">
                    <div class="register-req">
                        <p>You can login to track your order history, or continue with guest checkout!</p>
                    </div>
                    <div class="shopper-informations">
                        <div class="row">
                            <div class="col-sm-5 clearfix">
                                <div class="bill-to">
                                    <p>Billing Address</p>
                                    <div class="form-one">
                                        <form id="billing-form">
                                            <div class="form-group">
                                                <input name="email" type="email" placeholder="Email *" value="${user.email}" class="form-control" id="email-input">
                                                <div class="error-message" id="email-error"></div>
                                            </div>
                                            
                                            <div class="form-group">
                                                <input name="fullName" type="text" placeholder="Full Name *" value="${user.fullname}" class="form-control" id="fullname-input">
                                                <div class="error-message" id="fullname-error"></div>
                                            </div>
                                            
                                            <div class="form-group">
                                                <input name="addressLine" type="text" placeholder="Address Line *" value="${user.address}" class="form-control" id="address-input">
                                                <div class="error-message" id="address-error"></div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="form-two">
                                        <form>
                                            <div class="form-group">
                                                <select name="province" id="provinceCitySelect" class="form-control">
                                                    <option value="">-- Select Province/City * --</option>
                                                </select>
                                                <div class="error-message" id="province-error"></div>
                                            </div>

                                            <div class="form-group">
                                                <select name="district" id="districtSelect" disabled class="form-control">
                                                    <option value="">-- Select District * --</option>
                                                </select>
                                                <div class="error-message" id="district-error"></div>
                                            </div>

                                            <div class="form-group">
                                                <select name="ward" id="wardSelect" disabled class="form-control">
                                                    <option value="">-- Select Ward/Commune * --</option>
                                                </select>
                                                <div class="error-message" id="ward-error"></div>
                                            </div>

                                            <div class="form-group">
                                                <input name="phoneNumber" type="text" placeholder="Phone Number *" value="${user.phone}" class="form-control" id="phone-input"> 
                                                <div class="error-message" id="phone-error"></div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="order-message">
                                    <p>Shipping Order Notes</p>
                                    <div class="form-group">
                                        <textarea name="message" placeholder="Notes about your order, special notes for delivery (e.g., preferred delivery time, leave at security desk)" rows="16" class="form-control" id="notes-input"></textarea>
                                        <div class="error-message" id="notes-error"></div>
                                    </div>
                                    <label><input type="checkbox" id="ship-to-billing"> Ship to this billing address</label>
                                </div>	
                            </div>					
                        </div>
                    </div>
                </c:if>

                <div class="review-payment">
                    <h2>Review & Payment</h2>
                </div>

                <div class="table-responsive cart_info">
                    <c:if test="${empty cartDetails}">
                        <div class="empty-cart">
                            <i class="fa fa-shopping-cart fa-5x" style="color: #ccc;"></i>
                            <h3>Your cart is empty</h3>
                            <p>Add some beautiful bouquets to your cart to get started!</p>
                            <a href="${pageContext.request.contextPath}/product" class="btn btn-primary">Continue Shopping</a>
                        </div>
                    </c:if>
                    <c:if test="${not empty cartDetails}">
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
                                            <img src="${pageContext.request.contextPath}/upload/BouquetIMG/${item.bouquet.imageUrl}" alt="${item.bouquet.bouquetName}" width="100">
                                        </td>
                                        <td class="cart_description">
                                            <h4>${item.bouquet.bouquetName}</h4>
                                            <p>${item.bouquet.description}</p>
                                        </td>
                                        <td class="cart_price">
                                            <p><fmt:formatNumber value="${item.bouquet.price}" pattern="#,##0" /> ₫</p>
                                        </td>
                                        <td class="cart_quantity">
                                            <div class="cart_quantity_button">
                                                <form action="checkout" method="post" style="display: flex;">
                                                    <input type="hidden" name="bouquetId" value="${item.bouquet.bouquetId}">
                                                    <input type="hidden" name="action" value="update">
                                                    <input class="cart_quantity_input" type="number" name="quantity" value="${item.quantity}" min="1" style="width: 50px; text-align: center;">
                                                    <button type="submit" class="btn btn-xs">Update</button>
                                                </form>
                                            </div>
                                        </td>
                                        <td class="cart_total">
                                            <p class="cart_total_price"><fmt:formatNumber value="${item.bouquet.price * item.quantity}" pattern="#,##0" /> ₫</p>
                                        </td>
                                        <td class="cart_delete">
                                            <form action="checkout" method="post">
                                                <input type="hidden" name="bouquetId" value="${item.bouquet.bouquetId}">
                                                <input type="hidden" name="action" value="delete">
                                                <button type="submit" class="btn btn-danger btn-sm"><i class="fa fa-times"></i></button>
                                            </form>
                                        </td>
                                    </tr>
                                    <c:set var="total" value="${total + item.bouquet.price * item.quantity}"/>
                                </c:forEach>
                                <tr>
                                    <c:set var="ship" value="30000"/>
                                    <td colspan="4">&nbsp;</td>
                                    <td colspan="2">
                                        <table class="table table-condensed total-result">
                                            <tr>
                                                <td>Cart Subtotal</td>
                                                <td><p><fmt:formatNumber value="${total}" pattern="#,##0" /> ₫</p></td>
                                            </tr>
                                            <tr class="shipping-cost">
                                                <td>Shipping Fee</td>
                                                <td><fmt:formatNumber value="${ship}" pattern="#,##0" /> ₫</td>										
                                            </tr>
                                            <tr>
                                                <td>Total</td>
                                                <td><span><p><fmt:formatNumber value="${total + ship}" pattern="#,##0" /> ₫</p></span></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </c:if>
                </div>
                <c:if test="${not empty cartDetails}">
                    <div class="payment-options">
                        <span>
                            <label><input name="paymentMethod" type="radio" value="cod" id="payment-cod"> Cash on Delivery (COD)</label>
                        </span>
                        <span>
                            <label><input name="paymentMethod" type="radio" value="ewallet" id="payment-ewallet"> E-wallet (e.g., MoMo, ZaloPay)</label>
                        </span>
                        <div class="error-message" id="payment-error"></div>
                    </div>
                    <div class="text-right">
                        <button class="btn btn-primary" onclick="submitOrder()" id="place-order-btn">Place Order</button>
                    </div>
                </c:if>
            </div>
        </section> 
        
        <jsp:include page="/ZeShopper/footer.jsp"/> 

        <div id="success-popup" class="">Added to cart successfully!</div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/ZeShopper/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/ZeShopper/js/jquery.scrollUp.min.js"></script>
        <script src="${pageContext.request.contextPath}/ZeShopper/js/jquery.prettyPhoto.js"></script>
        <script src="${pageContext.request.contextPath}/ZeShopper/js/price-range.js"></script>
        <script src="${pageContext.request.contextPath}/ZeShopper/js/contact.js"></script>
        <script src="${pageContext.request.contextPath}/ZeShopper/js/main.js"></script>

        <script>
            const ValidationUtils = {
                isValidEmail: function(email) {
                    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                    return emailRegex.test(email);
                },
                
                isValidPhoneNumber: function(phone) {
                    const phoneRegex = /^0[3|5|7|8|9][0-9]{8,9}$/;
                    return phoneRegex.test(phone.replace(/\s+/g, ''));
                },
                
                isValidName: function(name) {
                    const nameRegex = /^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵýỷỹ\s]+$/;
                    return nameRegex.test(name) && name.trim().length >= 2;
                },
                
                showError: function(fieldId, message) {
                    const field = document.getElementById(fieldId);
                    const errorDiv = document.getElementById(fieldId.replace('-input', '') + '-error');
                    
                    if (field) {
                        field.classList.add('input-error');
                        field.classList.remove('input-valid');
                    }
                    
                    if (errorDiv) {
                        errorDiv.textContent = message;
                        errorDiv.style.display = 'block';
                    }
                },
                
                showSuccess: function(fieldId) {
                    const field = document.getElementById(fieldId);
                    const errorDiv = document.getElementById(fieldId.replace('-input', '') + '-error');
                    
                    if (field) {
                        field.classList.add('input-valid');
                        field.classList.remove('input-error');
                    }
                    
                    if (errorDiv) {
                        errorDiv.style.display = 'none';
                    }
                },
                
                clearValidation: function(fieldId) {
                    const field = document.getElementById(fieldId);
                    const errorDiv = document.getElementById(fieldId.replace('-input', '') + '-error');
                    
                    if (field) {
                        field.classList.remove('input-error', 'input-valid');
                    }
                    
                    if (errorDiv) {
                        errorDiv.style.display = 'none';
                    }
                }
            };

            function setupRealTimeValidation() {
                $('#email-input').on('blur', function() {
                    const email = $(this).val().trim();
                    if (!email) {
                        ValidationUtils.showError('email-input', 'Email is required');
                    } else if (!ValidationUtils.isValidEmail(email)) {
                        ValidationUtils.showError('email-input', 'Please enter a valid email address');
                    } else {
                        ValidationUtils.showSuccess('email-input');
                    }
                });

                $('#fullname-input').on('blur', function() {
                    const fullName = $(this).val().trim();
                    if (!fullName) {
                        ValidationUtils.showError('fullname-input', 'Full name is required');
                    } else if (!ValidationUtils.isValidName(fullName)) {
                        ValidationUtils.showError('fullname-input', 'Please enter a valid name (at least 2 characters)');
                    } else {
                        ValidationUtils.showSuccess('fullname-input');
                    }
                });

                $('#address-input').on('blur', function() {
                    const address = $(this).val().trim();
                    if (!address) {
                        ValidationUtils.showError('address-input', 'Address is required');
                    } else if (address.length < 10) {
                        ValidationUtils.showError('address-input', 'Please enter a more detailed address (at least 10 characters)');
                    } else {
                        ValidationUtils.showSuccess('address-input');
                    }
                });

                $('#phone-input').on('blur', function() {
                    const phone = $(this).val().trim();
                    if (!phone) {
                        ValidationUtils.showError('phone-input', 'Phone number is required');
                    } else if (!ValidationUtils.isValidPhoneNumber(phone)) {
                        ValidationUtils.showError('phone-input', 'Please enter a valid Vietnamese phone number (e.g., 0901234567)');
                    } else {
                        ValidationUtils.showSuccess('phone-input');
                    }
                });

                $('#provinceCitySelect').on('change', function() {
                    const province = $(this).val();
                    if (!province) {
                        ValidationUtils.showError('provinceCitySelect', 'Please select a province/city');
                    } else {
                        ValidationUtils.showSuccess('provinceCitySelect');
                    }
                });

                $('#districtSelect').on('change', function() {
                    const district = $(this).val();
                    if (!district && !$(this).prop('disabled')) {
                        ValidationUtils.showError('districtSelect', 'Please select a district');
                    } else if (district) {
                        ValidationUtils.showSuccess('districtSelect');
                    }
                });

                $('#wardSelect').on('change', function() {
                    const ward = $(this).val();
                    if (!ward && !$(this).prop('disabled')) {
                        ValidationUtils.showError('wardSelect', 'Please select a ward/commune');
                    } else if (ward) {
                        ValidationUtils.showSuccess('wardSelect');
                    }
                });

                $('input[name="paymentMethod"]').on('change', function() {
                    if ($('input[name="paymentMethod"]:checked').length > 0) {
                        $('#payment-error').hide();
                    }
                });
            }

            function validateForm() {
                let isValid = true;
                const errors = [];

                const email = $('#email-input').val().trim();
                if (!email) {
                    ValidationUtils.showError('email-input', 'Email is required');
                    errors.push('Email is required');
                    isValid = false;
                } else if (!ValidationUtils.isValidEmail(email)) {
                    ValidationUtils.showError('email-input', 'Please enter a valid email address');
                    errors.push('Please enter a valid email address');
                    isValid = false;
                } else {
                    ValidationUtils.showSuccess('email-input');
                }

                const fullName = $('#fullname-input').val().trim();
                if (!fullName) {
                    ValidationUtils.showError('fullname-input', 'Full name is required');
                    errors.push('Full name is required');
                    isValid = false;
                } else if (!ValidationUtils.isValidName(fullName)) {
                    ValidationUtils.showError('fullname-input', 'Please enter a valid name');
                    errors.push('Please enter a valid full name');
                    isValid = false;
                } else {
                    ValidationUtils.showSuccess('fullname-input');
                }

                const address = $('#address-input').val().trim();
                if (!address) {
                    ValidationUtils.showError('address-input', 'Address is required');
                    errors.push('Address is required');
                    isValid = false;
                } else if (address.length < 10) {
                    ValidationUtils.showError('address-input', 'Please enter a more detailed address');
                    errors.push('Please enter a more detailed address');
                    isValid = false;
                } else {
                    ValidationUtils.showSuccess('address-input');
                }

                const province = $('#provinceCitySelect').val();
                if (!province) {
                    ValidationUtils.showError('provinceCitySelect', 'Please select a province/city');
                    errors.push('Province/City is required');
                    isValid = false;
                } else {
                    ValidationUtils.showSuccess('provinceCitySelect');
                }

                const district = $('#districtSelect').val();
                if (!district && !$('#districtSelect').prop('disabled')) {
                    ValidationUtils.showError('districtSelect', 'Please select a district');
                    errors.push('District is required');
                    isValid = false;
                } else if (district) {
                    ValidationUtils.showSuccess('districtSelect');
                }

                const ward = $('#wardSelect').val();
                if (!ward && !$('#wardSelect').prop('disabled')) {
                    ValidationUtils.showError('wardSelect', 'Please select a ward/commune');
                    errors.push('Ward/Commune is required');
                    isValid = false;
                } else if (ward) {
                    ValidationUtils.showSuccess('wardSelect');
                }

                const phone = $('#phone-input').val().trim();
                if (!phone) {
                    ValidationUtils.showError('phone-input', 'Phone number is required');
                    errors.push('Phone number is required');
                    isValid = false;
                } else if (!ValidationUtils.isValidPhoneNumber(phone)) {
                    ValidationUtils.showError('phone-input', 'Please enter a valid phone number');
                    errors.push('Please enter a valid Vietnamese phone number');
                    isValid = false;
                } else {
                    ValidationUtils.showSuccess('phone-input');
                }

                const paymentMethod = $('input[name="paymentMethod"]:checked').val();
                if (!paymentMethod) {
                    $('#payment-error').text('Please select a payment method').show();
                    errors.push('Payment method is required');
                    isValid = false;
                } else {
                    $('#payment-error').hide();
                }

                if (errors.length > 0) {
                    $('#validation-errors').empty();
                    errors.forEach(error => {
                        $('#validation-errors').append('<li>' + error + '</li>');
                    });
                    $('#validation-summary').show();
                    $('html, body').animate({
                        scrollTop: $('#validation-summary').offset().top - 100
                    }, 500);
                } else {
                    $('#validation-summary').hide();
                }

                return isValid;
            }

            function submitOrder() {
                if (validateForm()) {
                    $('#place-order-btn').prop('disabled', true).text('Processing...');
                    
                    const orderData = {
                        email: $('#email-input').val().trim(),
                        fullName: $('#fullname-input').val().trim(),
                        addressLine: $('#address-input').val().trim(),
                        province: $('#provinceCitySelect option:selected').text(),
                        district: $('#districtSelect option:selected').text(),
                        ward: $('#wardSelect option:selected').text(),
                        phoneNumber: $('#phone-input').val().trim(),
                        notes: $('#notes-input').val().trim(),
                        paymentMethod: $('input[name="paymentMethod"]:checked').val(),
                        shipToBilling: $('#ship-to-billing').is(':checked')
                    };
                    
                    console.log('Order Data:', orderData);
                    
                    setTimeout(() => {
                        showPopup('Order placed successfully! You will receive a confirmation email shortly.', 'success');
                        $('#place-order-btn').prop('disabled', false).text('Place Order');
                        
                    }, 2000);
                    
                } else {
                    showPopup('Please correct the errors in the form before submitting.', 'error');
                }
            }

            $(document).ready(function () {
                let provincesData = {};
                let districtsData = {};
                let wardsData = {};
                const basePath = "${pageContext.request.contextPath}/ZeShopper/data/";

                setupRealTimeValidation();

                function loadData(url, type) {
                    return $.getJSON(url)
                            .done(function (data) {
                                if (type === 'provinces')
                                    provincesData = data;
                                else if (type === 'districts')
                                    districtsData = data;
                                else if (type === 'wards')
                                    wardsData = data;
                                console.log(type + ` loaded.`, Object.keys(data).length, 'items');
                            })
                            .fail(function (jqXHR, textStatus, errorThrown) {
                                console.error(`Failed to load ` + type + `:`, textStatus, errorThrown);
                            });
                }

                function populateProvinces() {
                    let options = '<option value="">-- Select Province/City * --</option>';
                    console.log("provincesData at start of populateProvinces:", provincesData);
                    for (const code in provincesData) {
                        if (provincesData.hasOwnProperty(code)) {
                            const p = provincesData[code];
                            console.log("Current p object:", p);
                            options += `<option value="` + p.code + `">` + p.name_with_type + `</option>`;
                        }
                    }
                    console.log("Populating provinces with options:", options);
                    $('#provinceCitySelect').html(options);
                }

                function populateDistricts(provinceCode) {
                    let options = '<option value="">-- Select District * --</option>';
                    const filteredDistricts = [];
                    for (const code in districtsData) {
                        if (districtsData.hasOwnProperty(code)) {
                            const d = districtsData[code];
                            if (d.parent_code === provinceCode) {
                                filteredDistricts.push(d);
                            }
                        }
                    }
                    filteredDistricts.forEach(d => {
                        options += `<option value="` + d.code + `">` + d.name_with_type + `</option>`;
                    });
                    $('#districtSelect').html(options).prop('disabled', filteredDistricts.length === 0);
                    $('#wardSelect').html('<option value="">-- Select Ward/Commune * --</option>').prop('disabled', true);
                    
                    ValidationUtils.clearValidation('districtSelect');
                    ValidationUtils.clearValidation('wardSelect');
                }

                function populateWards(districtCode) {
                    let options = '<option value="">-- Select Ward/Commune * --</option>';
                    const filteredWards = [];
                    for (const code in wardsData) {
                        if (wardsData.hasOwnProperty(code)) {
                            const w = wardsData[code];
                            if (w.parent_code === districtCode) {
                                filteredWards.push(w);
                            }
                        }
                    }
                    filteredWards.forEach(w => {
                        options += `<option value="` + w.code + `">` + w.name_with_type + `</option>`;
                    });
                    $('#wardSelect').html(options).prop('disabled', filteredWards.length === 0);
                    
                    ValidationUtils.clearValidation('wardSelect');
                }

                $.when(
                        loadData(basePath + 'tinh_tp.json', 'provinces'),
                        loadData(basePath + 'quan_huyen.json', 'districts'),
                        loadData(basePath + 'xa_phuong.json', 'wards')
                        ).done(function () {
                    populateProvinces();
                });

                $('#provinceCitySelect').on('change', function () {
                    const selectedProvinceCode = $(this).val();
                    if (selectedProvinceCode) {
                        populateDistricts(selectedProvinceCode);
                    } else {
                        $('#districtSelect').html('<option value="">-- Select District * --</option>').prop('disabled', true);
                        $('#wardSelect').html('<option value="">-- Select Ward/Commune * --</option>').prop('disabled', true);
                        ValidationUtils.clearValidation('districtSelect');
                        ValidationUtils.clearValidation('wardSelect');
                    }
                });

                $('#districtSelect').on('change', function () {
                    const selectedDistrictCode = $(this).val();
                    if (selectedDistrictCode) {
                        populateWards(selectedDistrictCode);
                    } else {
                        $('#wardSelect').html('<option value="">-- Select Ward/Commune * --</option>').prop('disabled', true);
                        ValidationUtils.clearValidation('wardSelect');
                    }
                });

                $('input, select, textarea').on('input change', function() {
                    const fieldId = $(this).attr('id');
                    if (fieldId && $(this).hasClass('input-error')) {
                        ValidationUtils.clearValidation(fieldId);
                    }
                });

                $(document).on('keypress', function(e) {
                    if (e.which === 13 && !$(e.target).is('textarea')) {
                        e.preventDefault();
                        submitOrder();
                    }
                });
            });

            function submit() {
                submitOrder();
            }

            function showPopup(message, type) {
                const successBox = document.getElementById("success-popup");
                if (type === 'success') {
                    successBox.className = 'success-toast';
                } else {
                    successBox.className = 'error-toast';
                }
                successBox.innerText = message;
                successBox.style.display = "block";
                
                setTimeout(() => {
                    successBox.classList.add('toast-fadeout');
                    setTimeout(() => {
                        successBox.style.display = "none";
                        successBox.classList.remove('toast-fadeout');
                    }, 300);
                }, 2700);
            }

            const FormUtils = {
                resetForm: function() {
                    $('#billing-form')[0].reset();
                    $('#provinceCitySelect').val('').trigger('change');
                    $('#notes-input').val('');
                    $('input[name="paymentMethod"]').prop('checked', false);
                    $('#ship-to-billing').prop('checked', false);
                    
                    $('.input-error, .input-valid').removeClass('input-error input-valid');
                    $('.error-message').hide();
                    $('#validation-summary').hide();
                },
                
                getFormData: function() {
                    return {
                        email: $('#email-input').val().trim(),
                        fullName: $('#fullname-input').val().trim(),
                        addressLine: $('#address-input').val().trim(),
                        province: $('#provinceCitySelect option:selected').text(),
                        provinceCode: $('#provinceCitySelect').val(),
                        district: $('#districtSelect option:selected').text(),
                        districtCode: $('#districtSelect').val(),
                        ward: $('#wardSelect option:selected').text(),
                        wardCode: $('#wardSelect').val(),
                        phoneNumber: $('#phone-input').val().trim(),
                        notes: $('#notes-input').val().trim(),
                        paymentMethod: $('input[name="paymentMethod"]:checked').val(),
                        shipToBilling: $('#ship-to-billing').is(':checked')
                    };
                },
                
                populateForm: function(data) {
                    if (data.email) $('#email-input').val(data.email);
                    if (data.fullName) $('#fullname-input').val(data.fullName);
                    if (data.addressLine) $('#address-input').val(data.addressLine);
                    if (data.phoneNumber) $('#phone-input').val(data.phoneNumber);
                    if (data.notes) $('#notes-input').val(data.notes);
                    if (data.paymentMethod) $('input[name="paymentMethod"][value="' + data.paymentMethod + '"]').prop('checked', true);
                    if (data.shipToBilling) $('#ship-to-billing').prop('checked', data.shipToBilling);
                }
            };
        </script>
    </body>
</html>