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

    </head><!--/head-->

    <body>
        <jsp:include page="/ZeShopper/header.jsp"/>

        <div class="container-fluid">
            <div class="row">

                <!-- Sidebar trái -->
                <div class="col-sm-4">
                    <div class="panel panel-default">
                        <div class="panel-heading bg-primary text-white">
                            <h4 class="text-center">Menu</h4>
                        </div>
                        <div class="panel-body">
                            <nav class="nav nav-pills nav-stacked">
                                <a href="${pageContext.request.contextPath}/viewuserdetailhome" class="nav-link active">
                                    <i class="fa fa-circle-info me-2"></i> Update Info
                                </a><br>
                                <a href="${pageContext.request.contextPath}/ZeShopper/updateCustomerInfo.jsp" class="nav-link">
                                    <i class="fa fa-user-pen me-2"></i> More Info
                                </a>
                            </nav>
                        </div>
                    </div>
                </div>

                <!-- Nội dung chính bên phải -->
                <div class="col-sm-8">
                    <div class="panel panel-default">
                        <div class="panel-heading bg-success text-white text-center">
                            <h4 class="mb-0">Update Your Profile</h4>
                        </div>
                        <div class="panel-body">
                            <form action="${pageContext.request.contextPath}/viewuserdetailhome" method="POST"
                                  onsubmit="return confirm('Are you sure? Please think again before updating your information.');">
                                <input type="hidden" name="id" value="${userManager.userid}">

                                <!-- Username -->
                                <div class="form-group">
                                    <label for="username">Username</label>
                                    <input type="text" name="name" class="form-control" id="username"
                                           placeholder="Username" value="${userManager.username}">
                                    <c:if test="${not empty errorName}">
                                        <span class="text-danger">${errorName}</span>
                                    </c:if>
                                </div>

                                <!-- Password -->
                                <div class="form-group">
                                    <label for="password">Password</label>
                                    <input type="password" name="pass" class="form-control" id="password"
                                           placeholder="Password" value="${userManager.password}">
                                    <c:if test="${not empty errorPass}">
                                        <span class="text-danger">${errorPass}</span>
                                    </c:if>
                                    <c:if test="${not empty passwordStrength}">
                                        <span class="help-block" style="color:
                                              ${passwordStrength == 'Strong' ? 'green' : passwordStrength == 'Medium' ? 'orange' : 'red'};">
                                            password: ${passwordStrength}
                                        </span>
                                    </c:if>
                                </div>

                                <!-- Full Name -->
                                <div class="form-group">
                                    <label for="fullname">Full Name</label>
                                    <input type="text" name="FullName" class="form-control" id="fullname"
                                           placeholder="Full Name" value="${userManager.fullname}">
                                    <c:if test="${not empty errorFullname}">
                                        <span class="text-danger">${errorFullname}</span>
                                    </c:if>
                                </div>

                                <!-- Email -->
                                <div class="form-group">
                                    <label for="email">Email</label>
                                    <input type="text" name="email" class="form-control" id="email"
                                           placeholder="Email" value="${userManager.email}">
                                    <c:if test="${not empty errorEmail}">
                                        <span class="text-danger">${errorEmail}</span>
                                    </c:if>
                                </div>

                                <!-- Phone -->
                                <div class="form-group">
                                    <label for="phone">Phone Number</label>
                                    <input type="text" name="phone" class="form-control" id="phone"
                                           placeholder="Phone Number" value="${userManager.phone}">
                                    <c:if test="${not empty errorPhone}">
                                        <span class="text-danger">${errorPhone}</span>
                                    </c:if>
                                </div>

                                <!-- Address -->
                                <div class="form-group">
                                    <label for="address">Address</label>
                                    <input type="text" name="address" class="form-control" id="address"
                                           placeholder="Address" value="${userManager.address}">
                                    <c:if test="${not empty errorAddress}">
                                        <span class="text-danger">${errorAddress}</span>
                                    </c:if>
                                </div>

                                <!-- Submit -->
                                <div class="text-center">
                                    <button type="submit" class="btn btn-success" name="updateform">
                                        UPDATE
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

            </div>
        </div>


        <jsp:include page="/ZeShopper/footer.jsp"/>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </body>
</html>