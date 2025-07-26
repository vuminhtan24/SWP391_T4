<%-- 
    Document   : login
    Created on : May 19, 2025, 8:38:41 AM
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
        <title>Login | E-Shopper</title>
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

        <section id="form" style="margin-top: 0px"><!--form-->
            <div class="container">
                <div class="row">
                    <div class="col-sm-4 col-sm-offset-1">
                        <div class="login-form"><!--login form-->
                            <h2>Change Password</h2>
                            <form action="ChangePassword" method="post">
                                <input type="email" value="${email}" placeholder="Email Address" name="email"/>
                                <input type="password" placeholder="Current Password" name="currentPassword"/>
                                <input type="password" placeholder="New Password" name="newPassword"/>
                                <input type="password" placeholder="Cofirm New Password" name="confirmNewPassword"/>
                                <h3 class="text-danger text-center">${messChangePassword}</h3>
                                <button type="submit" class="btn btn-default">Change</button>
                                <div style="margin-top: 10px;">
                                    <a href="requestPassword">Forgot your password?</a>
                                </div>

                            </form>
                        </div><!--/login form-->
                    </div>
                </div>
            </div>
        </section><!--/form-->


        <jsp:include page="/ZeShopper/footer.jsp"/>

    </body>
</html>
