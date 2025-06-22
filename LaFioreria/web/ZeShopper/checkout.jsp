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
                <c:if test="${empty cartDetails}">
                    <div class="review-payment">
                        <h4>Nothing in your cart, so that you cannot checkout!</h4>
                    </div>
                </c:if>
                <c:if test="${not empty cartDetails}">
                    <div class="step-one">
                        <h2 class="heading">Step 1: Account Options</h2>
                    </div>
                    <div class="checkout-options">
                        <h3>New User</h3>
                        <p>Checkout options</p>
                        <ul class="nav">
                            <li>
                                <label><input type="checkbox"> Register Account</label>
                            </li>
                            <li>
                                <label><input type="checkbox"> Guest Checkout</label>
                            </li>
                            <li>
                                <a href=""><i class="fa fa-times"></i>Cancel</a>
                            </li>
                        </ul>
                    </div>
                    <div class="register-req">
                        <p>Please use **Register and Checkout** to easily access your order history, or continue with **Guest Checkout**.</p>
                    </div>
                    <div class="shopper-informations">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="shopper-info">
                                    <p>Shopper Information</p>
                                    <form>
                                        <input type="text" placeholder="Full Name">
                                        <input type="text" placeholder="Email Address">
                                        <input type="password" placeholder="Password">
                                        <input type="password" placeholder="Confirm Password">
                                    </form>
                                    <a class="btn btn-primary" href="">Continue as Guest</a>
                                    <a class="btn btn-primary" href="">Register & Checkout</a>
                                </div>
                            </div>
                            <div class="col-sm-5 clearfix">
                                <div class="bill-to">
                                    <p>Billing Address</p>
                                    <div class="form-one">
                                        <form>
                                            <input type="text" placeholder="Company Name (Optional)">
                                            <input type="text" placeholder="Email *">
                                            <input type="text" placeholder="Title (e.g., Mr., Ms.)">
                                            <input type="text" placeholder="First Name *">
                                            <input type="text" placeholder="Middle Name (Optional)">
                                            <input type="text" placeholder="Last Name *">
                                            <input type="text" placeholder="Address Line 1 * (e.g., House No., Street Name)">
                                            <input type="text" placeholder="Address Line 2 (e.g., Apartment, Alley, Building)">
                                        </form>
                                    </div>
                                    <div class="form-two">
                                        <form>
                                            <select id="provinceCitySelect">
                                                <option value="">-- Select Province/City * --</option>
                                                <%-- Options will be loaded dynamically by JavaScript --%>
                                            </select>

                                            <select id="districtSelect" disabled>
                                                <option value="">-- Select District * --</option>
                                                <%-- Options will be loaded dynamically by JavaScript --%>
                                            </select>

                                            <select id="wardSelect" disabled>
                                                <option value="">-- Select Ward/Commune * --</option>
                                                <%-- Options will be loaded dynamically by JavaScript --%>
                                            </select>

                                            <input type="text" placeholder="Zip / Postal Code (Optional)">
                                            <input type="text" placeholder="Phone Number *"> 
                                            <input type="text" placeholder="Mobile Phone (Optional)">
                                            <input type="text" placeholder="Fax (Optional)">
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="order-message">
                                    <p>Shipping Order Notes</p>
                                    <textarea name="message"  placeholder="Notes about your order, special notes for delivery (e.g., preferred delivery time, leave at security desk)" rows="16"></textarea>
                                    <label><input type="checkbox"> Ship to this billing address</label>
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
                                    <%--<c:set var="vat" value="${total * 10 / 100}"/>--%>
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
<!--                                            <tr>
                                                <td>Tax (VAT)</td>
                                                <td><p><fmt:formatNumber value="${vat}" pattern="#,##0" /> ₫</p></td>
                                            </tr>-->
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
                            <label><input type="checkbox"> Cash on Delivery (COD)</label>
                        </span>
                        <span>
                            <label><input type="checkbox"> Bank Transfer</label>
                        </span>
                        <span>
                            <label><input type="checkbox"> Credit/Debit Card (via Payment Gateway)</label>
                        </span>
                        <span>
                            <label><input type="checkbox"> E-wallet (e.g., MoMo, ZaloPay)</label>
                        </span>
                    </div>
                    <div class="text-right">
                        <a class="btn btn-primary" href="">Place Order</a>
                    </div>
                </c:if>
            </div>
        </section> <jsp:include page="/ZeShopper/footer.jsp"/> 

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <%-- Hoặc nếu bạn thích cục bộ: <script src="${pageContext.request.contextPath}/ZeShopper/js/jquery.js"></script> --%>

        <script src="${pageContext.request.contextPath}/ZeShopper/js/bootstrap.min.js"></script>

        <script src="${pageContext.request.contextPath}/ZeShopper/js/jquery.scrollUp.min.js"></script>
        <script src="${pageContext.request.contextPath}/ZeShopper/js/jquery.prettyPhoto.js"></script>
        <script src="${pageContext.request.contextPath}/ZeShopper/js/price-range.js"></script>
        <script src="${pageContext.request.contextPath}/ZeShopper/js/contact.js"></script>

        <script src="${pageContext.request.contextPath}/ZeShopper/js/main.js"></script>

        <script>


            $(document).ready(function () {
                let provincesData = {};
                let districtsData = {};
                let wardsData = {};
                const basePath = "${pageContext.request.contextPath}/ZeShopper/data/";

                function loadData(url, type) {
                    return $.getJSON(url)
                            .done(function (data) {
                                if (type === 'provinces')
                                    provincesData = data;
                                else if (type === 'districts')
                                    districtsData = data;
                                else if (type === 'wards')
                                    wardsData = data;
                                console.log(`${type} loaded.`, Object.keys(data).length, 'items');
                            })
                            .fail(function (jqXHR, textStatus, errorThrown) {
                                console.error(`Failed to load ${type}:`, textStatus, errorThrown);
                            });
                }

                function populateProvinces() {
                    let options = '<option value="">-- Select Province/City * --</option>';
                    console.log("provincesData at start of populateProvinces:", provincesData);
                    for (const code in provincesData) {
                        if (provincesData.hasOwnProperty(code)) {
                            const p = provincesData[code];
                            console.log("Current p object:", p);
                            options += `<option value="${p.code}">${p.name_with_type}</option>`;
                        }
                    }
                    console.log("Populating provinces with options:", options); // Thêm dòng này để gỡ lỗi
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
                        options += `<option value="${d.code}">${d.name_with_type}</option>`;
                    });
                    $('#districtSelect').html(options).prop('disabled', filteredDistricts.length === 0);
                    $('#wardSelect').html('<option value="">-- Select Ward/Commune * --</option>').prop('disabled', true);
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
                        options += `<option value="${w.code}">${w.name_with_type}</option>`;
                    });
                    $('#wardSelect').html(options).prop('disabled', filteredWards.length === 0);
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
                    }
                });

                $('#districtSelect').on('change', function () {
                    const selectedDistrictCode = $(this).val();
                    if (selectedDistrictCode) {
                        populateWards(selectedDistrictCode);
                    } else {
                        $('#wardSelect').html('<option value="">-- Select Ward/Commune * --</option>').prop('disabled', true);
                    }
                });
            });
        </script>
        <%-- <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"></script> --%>
        <%-- <script type="text/javascript" src="js/gmaps.js"></script> --%>
    </body>
</html>