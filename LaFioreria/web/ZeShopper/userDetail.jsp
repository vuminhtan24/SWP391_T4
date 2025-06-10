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
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="contactinfo">
                                <ul class="nav nav-pills">
                                    <li><a href=""><i class="fa fa-phone"></i> +2 95 01 88 821</a></li>
                                    <li><a href=""><i class="fa fa-envelope"></i> info@domain.com</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="social-icons pull-right">
                                <ul class="nav navbar-nav">
                                    <li><a href=""><i class="fa fa-facebook"></i></a></li>
                                    <li><a href=""><i class="fa fa-twitter"></i></a></li>
                                    <li><a href=""><i class="fa fa-linkedin"></i></a></li>
                                    <li><a href=""><i class="fa fa-dribbble"></i></a></li>
                                    <li><a href=""><i class="fa fa-google-plus"></i></a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!--/header_top-->

            <div class="header-middle"><!--header-middle-->
                <div class="container">
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="logo pull-left">
                                <a href="${pageContext.request.contextPath}/home"><img src="${pageContext.request.contextPath}/ZeShopper/images/home/logo1.jpg" style="width:150px;" alt="" /></a>
                            </div>
                            <div class="btn-group pull-right">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle usa" data-toggle="dropdown">
                                        USA
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="">Canada</a></li>
                                        <li><a href="">UK</a></li>
                                    </ul>
                                </div>

                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle usa" data-toggle="dropdown">
                                        DOLLAR
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="">Canadian Dollar</a></li>
                                        <li><a href="">Pound</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-8">
                            <div class="shop-menu pull-right">
                                <ul class="nav navbar-nav">
                                    <c:choose>
                                        <c:when test="${sessionScope.currentAcc != null}">
                                            <li class="dropdown">
                                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                                    <i class="fa fa-user"></i> Hello, ${sessionScope.currentAcc.username} <b class="caret"></b>
                                                </a>
                                                <ul class="dropdown-menu">
                                                    <li><a href="${pageContext.request.contextPath}/viewuserdetailhome"><i class="fa fa-id-card"></i> User Detail</a></li>
                                                    <li><a href="changePassword.jsp"><i class="fa fa-key"></i> Change Password</a></li>
                                                    <li class="divider"></li>
                                                    <li><a href="${pageContext.request.contextPath}/ZeShopper/LogoutServlet"><i class="fa fa-unlock"></i> Logout</a></li>
                                                </ul>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="${pageContext.request.contextPath}/ZeShopper/LoginServlet"><i class="fa fa-lock"></i> Login</a></li>
                                            </c:otherwise>
                                        </c:choose>

                                    <li><a href="${pageContext.request.contextPath}/ZeShopper/wishlist.jsp"><i class="fa fa-star"></i> Wishlist</a></li>
                                    <li><a href="${pageContext.request.contextPath}/ZeShopper/checkout.jsp"><i class="fa fa-crosshairs"></i> Checkout</a></li>
                                    <li><a href="${pageContext.request.contextPath}/ZeShopper/cart.jsp"><i class="fa fa-shopping-cart"></i> Cart</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!--/header-middle-->

            <div class="header-bottom"><!--header-bottom-->
                <div class="container">
                    <div class="row">
                        <div class="col-sm-9">
                            <div class="navbar-header">
                                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                                    <span class="sr-only">Toggle navigation</span>
                                    <span class="icon-bar"></span>
                                    <span class="icon-bar"></span>
                                    <span class="icon-bar"></span>
                                </button>
                            </div>
                            <div class="mainmenu pull-left">
                                <ul class="nav navbar-nav collapse navbar-collapse">
                                    <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
                                    <li class="dropdown"><a href="#" class="active">Shop<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="${pageContext.request.contextPath}/product" class="active">Products</a></li>
                                            <li><a href="${pageContext.request.contextPath}/ZeShopper/product-details.jsp">Product Details</a></li> 
                                            <li><a href="${pageContext.request.contextPath}/ZeShopper/checkout.jsp">Checkout</a></li> 
                                            <li><a href="${pageContext.request.contextPath}/ZeShopper/cart.jsp">Cart</a></li> 
                                            <li><a href="${pageContext.request.contextPath}/ZeShopper/LoginServlet">Login</a></li> 
                                        </ul>
                                    </li> 
                                    <li class="dropdown"><a href="#">Blog<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="${pageContext.request.contextPath}/ZeShopper/blog.jsp">Blog List</a></li>
                                            <li><a href="${pageContext.request.contextPath}/ZeShopper/blog-single.jsp">Blog Single</a></li>
                                        </ul>
                                    </li> 
                                    <li><a href="${pageContext.request.contextPath}/ZeShopper/404.jsp">404</a></li>
                                    <li><a href="${pageContext.request.contextPath}/ZeShopper/contact-us.jsp">Contact</a></li>
                                    <li><a href="${pageContext.request.contextPath}/ZeShopper/about-us.jsp">About us</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="search_box pull-right">
                                <input type="text" placeholder="Search"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->

        <div class="container user-detail-form">
            <form action="${pageContext.request.contextPath}/viewuserdetailhome" method="POST" onsubmit="return confirm('Are you sure, Please thing again, you sure that you should UPDATE your information  ?');">
                <table border="0">

                    <tbody>
                        <tr>
                            <td>
                                <input type="hidden" name="id" class="form-control" placeholder="User ID" aria-label="Username"
                                       aria-describedby="basic-addon1" value="${userManager.userid}" readonly="">
                            </td>
                        </tr>
                        <c:if test="${not empty errorID}">
                            <tr>
                                <td colspan="2"><span style="color:red">${errorID}</span></td>
                            </tr>
                        </c:if> 

                        <tr>
                            <td>User Name: </td>
                            <td>
                                <input type="text" name="name" class="form-control" placeholder="Username" aria-label="Username"
                                       aria-describedby="basic-addon1" value="${userManager.username}">
                            </td>
                        </tr>

                        <c:if test="${not empty errorName}">
                            <tr>
                                <td colspan="2"><span style="color:red">${errorName}</span></td>
                            </tr>
                        </c:if>
                        <tr>
                            <td>Password: </td>
                            <td>
                                <input type="text" name="pass" class="form-control" placeholder="Password" aria-label="Username"
                                       aria-describedby="basic-addon1" value="${userManager.password}">
                            </td>
                        </tr>
                        <c:if test="${not empty errorPass}">
                            <tr>
                                <td colspan="2"><span style="color:red">${errorPass}</span></td>
                            </tr>
                        </c:if>
                        <tr>
                            <c:if test="${not empty passwordStrength}">
                                <c:choose>
                                    <c:when test="${passwordStrength == 'Mạnh'}">
                                <div style="font-size: small; color: green;">
                                    Mật khẩu: ${passwordStrength}
                                </div>
                            </c:when>
                            <c:when test="${passwordStrength == 'Trung bình'}">
                                <div style="font-size: small; color: orange;">
                                    Mật khẩu: ${passwordStrength}
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div style="font-size: small; color: red;">
                                    Mật khẩu: ${passwordStrength}
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    </tr>
                    <tr>
                        <td>Full Name: </td>
                        <td>
                            <input type="text" name="FullName" class="form-control" placeholder="Full Name" aria-label="Username"
                                   aria-describedby="basic-addon1" value="${userManager.fullname}">
                        </td>

                    </tr>
                    <c:if test="${not empty errorFullname}">
                        <tr>
                            <td colspan="2"><span style="color:red">${errorFullname}</span></td>
                        </tr>
                    </c:if>
                    <tr>
                        <td>Email: </td>
                        <td>
                            <input type="text" class="form-control" placeholder="Email"
                                   aria-label="Recipient's username" aria-describedby="basic-addon2" name="email" value="${userManager.email}">
                        </td>
                        <td>
                            <span class="input-group-text" id="basic-addon2">@flower.com</span>
                        </td>
                    </tr>
                    <c:if test="${not empty errorEmail}">
                        <tr>
                            <td colspan="2"><span style="color:red">${errorEmail}</span></td>
                        </tr>
                    </c:if>
                    <tr>
                        <td>Phone Number: </td>
                        <td>
                            <input type="type" name="phone" class="form-control" placeholder="Phone Number" aria-label="Username"
                                   aria-describedby="basic-addon1" value="${userManager.phone}">
                        </td>
                    </tr>
                    <c:if test="${not empty errorPhone}">
                        <tr>
                            <td colspan="2"><span style="color:red">${errorPhone}</span></td>
                        </tr>
                    </c:if>
                    <tr>
                        <td>Address: </td>
                        <td>
                            <input type="type" name="address" class="form-control" placeholder="Address" aria-label="Username"
                                   aria-describedby="basic-addon1" value="${userManager.address}">
                        </td>
                    </tr>
                    <c:if test="${not empty errorAddress}">
                        <tr>
                            <td colspan="2"><span style="color:red">${errorAddress}</span></td>
                        </tr>
                    </c:if>

                    <tr>
                        <td><input  class="btn btn-primary"type="submit" name="updateform" value="UPDATE" ></td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>

        <jsp:include page="/ZeShopper/footer.jsp"/>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </body>
</html>