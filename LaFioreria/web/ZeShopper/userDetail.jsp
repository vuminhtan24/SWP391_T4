<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List,model.Bouquet, model.Category, model.RawFlower" %>
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
        <title>Product Details | E-Shopper</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"/>
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
            /* 1. Toàn bộ card viền trắng, đổ nhẹ shadow như Product List */
            .product-card {
                background-color: #fff;
                border: 1px solid #ededed;
                /* Tạo shadow nhẹ giống Product List */
                box-shadow: 0 2px 4px rgba(0,0,0,0.05);
                border-radius: 4px;
                overflow: hidden;
                display: flex;
                flex-direction: column;
                height: 100%;
            }

            /* 2. Khung ảnh cố định chiều cao, nhưng ảnh hiển thị đầy đủ (object-fit: contain) */
            .product-card__image {
                width: 100%;
                height: 200px;       /* Giữ đúng cao 200px cho tất cả ảnh */
                background-color: #f9f9f9; /* Màu nền nhạt nếu ảnh không lấp đầy khung */
                display: flex;
                align-items: center;
                justify-content: center;
                overflow: hidden;
            }

            .product-card__image img {
                width: auto;
                height: 100%;
                object-fit: contain;
                display: block;
            }

            /* 3. Phần body chứa title/price/button */
            .product-card__body {
                padding: 15px;
                flex: 1;             /* Chiếm hết không gian còn lại trong card */
                display: flex;
                flex-direction: column;
                justify-content: space-between;
            }

            /* 4. Title (tên sản phẩm) giống Product List: màu xanh, font-size lớn */
            .product-card__title {
                font-size: 1.4rem;          /* tương đương ~22px */
                color: #337ab7;             /* màu xanh giống Bootstrap “link-primary” */
                margin: 0 0 10px 0;
                text-align: center;
                line-height: 1.2;
            }

            .product-card__title a {
                color: inherit;
                text-decoration: none;
            }
            .product-card__title a:hover {
                text-decoration: underline;
            }

            /* 5. Price: nhỏ hơn title, màu xám đậm */
            .product-card__price {
                font-size: 1rem;
                color: #777;
                text-align: center;
                margin: 0 0 15px 0;
            }

            /* 6. Button “Add to cart”: full-width, màu nền vàng nhạt giống Product List */
            .product-card__button {
                width: 100%;
                font-size: 1rem;
                background-color: #f5f5f0;  /* màu nền giống “#f5f5f0” */
                color: #444;                /* màu chữ xám đậm */
                border: 1px solid #eee;
                padding: 10px 0;
                text-align: center;
                border-radius: 2px;
                transition: background-color 0.2s ease;
            }

            .product-card__button i {
                margin-right: 5px;
            }

            .product-card__button:hover {
                background-color: #e8e8dc;  /* hơi đậm hơn một chút */
                text-decoration: none;
                color: #333;
            }

            /* Khi hiển thị ở col-sm-4: cho margin-bottom để card cách nhau */
            .carousel .col-sm-4 {
                margin-bottom: 30px;
            }
            .user-detail-form {
                background-color: #fff8f5; /* nền nhẹ nhàng */
                border-radius: 16px;
                padding: 30px;
                max-width: 600px;
                margin: 30px auto;
                box-shadow: 0 6px 18px rgba(0, 0, 0, 0.06);
                font-family: 'Segoe UI', sans-serif;
            }

            .user-detail-form h1 {
                text-align: center;
                color: #d96c75; /* hồng nhạt */
                margin-bottom: 20px;
                font-size: 26px;
            }

            .user-detail-form table {
                width: 100%;
                border-collapse: separate;
                border-spacing: 0 12px; /* tạo khoảng cách giữa các dòng */
            }

            .user-detail-form td {
                padding: 10px;
                font-size: 15px;
                color: #444;
                vertical-align: middle;
            }

            .user-detail-form input[type="text"] {
                width: 100%;
                padding: 10px 14px;
                border: 1px solid #f0cfcf;
                border-radius: 10px;
                font-size: 14px;
                background-color: #fff;
                box-sizing: border-box;
                transition: border-color 0.2s ease;
            }

            .user-detail-form input[type="text"]:focus {
                border-color: #d96c75;
                outline: none;
                box-shadow: 0 0 5px rgba(217, 108, 117, 0.2);
            }

            .user-detail-form input[readonly] {
                background-color: #fdf0f0;
                color: #888;
                cursor: not-allowed;
            }

            /* Optional: nhấn nhẹ các dòng */
            .user-detail-form tr {
                background-color: #fff;
                border-radius: 12px;
            }

            /* Tăng tính nhẹ nhàng cho input */
            .user-detail-form input {
                font-family: inherit;
                font-weight: 400;
            }
        </style>
    </head><!--/head-->

    <body>
        <jsp:include page="/ZeShopper/header.jsp"/>

        <div class="container mt-5" style="max-width: 600px;">
            <form action="${pageContext.request.contextPath}/viewuserdetailhome" method="POST"
                  onsubmit="return confirm('Are you sure? Please think again before updating your information.');">
                <input type="hidden" name="id" value="${userManager.userid}">

                <!-- Username -->
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" name="name" class="form-control" id="username"
                           placeholder="Username" value="${userManager.username}">
                    <c:if test="${not empty errorName}">
                        <small class="form-text text-danger">${errorName}</small>
                    </c:if>
                </div>

                <!-- Password -->
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" name="pass" class="form-control" id="password"
                           placeholder="Password" value="${userManager.password}">
                    <c:if test="${not empty errorPass}">
                        <small class="form-text text-danger">${errorPass}</small>
                    </c:if>

                    <c:if test="${not empty passwordStrength}">
                        <small class="form-text"
                               style="color: ${passwordStrength == 'Mạnh' ? 'green' : passwordStrength == 'Trung bình' ? 'orange' : 'red'};">
                            Mật khẩu: ${passwordStrength}
                        </small>
                    </c:if>
                </div>

                <!-- Full Name -->
                <div class="form-group">
                    <label for="fullname">Full Name</label>
                    <input type="text" name="FullName" class="form-control" id="fullname"
                           placeholder="Full Name" value="${userManager.fullname}">
                    <c:if test="${not empty errorFullname}">
                        <small class="form-text text-danger">${errorFullname}</small>
                    </c:if>
                </div>

                <!-- Email -->
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="text" name="email" class="form-control" id="email"
                           placeholder="Email" value="${userManager.email}">
                    <c:if test="${not empty errorEmail}">
                        <small class="form-text text-danger">${errorEmail}</small>
                    </c:if>
                </div>

                <!-- Phone -->
                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="text" name="phone" class="form-control" id="phone"
                           placeholder="Phone Number" value="${userManager.phone}">
                    <c:if test="${not empty errorPhone}">
                        <small class="form-text text-danger">${errorPhone}</small>
                    </c:if>
                </div>

                <!-- Address -->
                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" name="address" class="form-control" id="address"
                           placeholder="Address" value="${userManager.address}">
                    <c:if test="${not empty errorAddress}">
                        <small class="form-text text-danger">${errorAddress}</small>
                    </c:if>
                </div>

                <!-- Submit -->
                <div class="form-group text-center">
                    <button type="submit" class="btn btn-primary" name="updateform">UPDATE</button>
                </div>
            </form>
        </div>


        <jsp:include page="/ZeShopper/footer.jsp"/>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </body>
</html>