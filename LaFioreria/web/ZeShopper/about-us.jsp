<%-- 
    Document   : about-us
    Created on : May 24, 2025, 11:43:21 PM
    Author     : VU MINH TAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <style>
            .about-image {
                width: 500px;
                max-width: 90%;
                height: auto;
                border-radius: 16px;
                box-shadow: 0 8px 24px rgba(0, 0, 0, 0.4);
                transition: transform 0.3s ease;
            }

            .about-image:hover {
                transform: scale(1.02);
            }

        </style>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Contact | E-Shopper</title>
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
    </head><!--/head-->

    <body>
        <jsp:include page="/ZeShopper/header.jsp"/>

        <div id="about-page" class="container-fluid py-5" style="background: url('images/about/background.jpg') no-repeat center center / cover;">
            <div class="container">
                <h2 class="title text-center mb-5 text-white">About <strong>Us</strong></h2>
                <div class="row align-items-center">
                    <!-- Text Left -->
                    <div class="col-md-6 text-white">
                        <h3>Who We Are</h3>
                        <p>
                            We are LaFioreria – a floral brand dedicated to bringing beauty and emotions into every moment of your life.
                            Founded by passionate flower lovers, we prioritize both product quality and customer service.
                        </p>
                        <p>
                            Our creative and devoted team continually innovates to deliver unique, elegant, and meaningful floral designs.
                            Share your memorable moments with us through exquisite and heartfelt bouquets.
                        </p>

                        <h3 class="mt-4">Our Mission</h3>
                        <p>
                            To provide customers with the most beautiful fresh flowers, helping to create unforgettable memories.
                            We believe that every bouquet tells a story, and our mission is to tell it perfectly.
                        </p>

                        <h3 class="mt-4">Our Vision</h3>
                        <p>
                            To become the leading flower brand in Vietnam, loved and trusted for our quality, creativity, and dedicated service.
                        </p>
                    </div>

                    <!-- Image Right -->
                    <div class="col-md-6 text-center">
                        <img src="images/about/bouquet.jpg" 
                             alt="LaFioreria Flowers" 
                             class="img-fluid rounded shadow about-image">
                    </div>

                </div>

                <div class="row mt-5 text-white">
                    <div class="col-md-6">
                        <h3>Our Location</h3>
                        <p>FPT University, Hoa Lac Hi-Tech Park</p>
                        <p>Km29 Thang Long Boulevard, Thach That, Hanoi</p>
                        <p>Mobile: +0919994398</p>
                        <p>Email: alice@flower.com</p>
                    </div>
                    <div class="col-md-6">
                        <h3>Follow Us</h3>
                        <ul class="list-inline">
                            <li class="list-inline-item"><a href="#"><i class="fa fa-facebook text-white fa-2x"></i></a></li>
                            <li class="list-inline-item"><a href="#"><i class="fa fa-twitter text-white fa-2x"></i></a></li>
                            <li class="list-inline-item"><a href="#"><i class="fa fa-google-plus text-white fa-2x"></i></a></li>
                            <li class="list-inline-item"><a href="#"><i class="fa fa-youtube text-white fa-2x"></i></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>



        <jsp:include page="/ZeShopper/footer.jsp"/>

    </body>
</html>
