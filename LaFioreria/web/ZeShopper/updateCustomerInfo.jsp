<%-- 
    Document   : updateCustomerInfo
    Created on : Jul 21, 2025, 4:26:38 AM
    Author     : LAPTOP
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%
    User currentUser = (User) session.getAttribute("currentAcc");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Update Customer Info</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
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
    </head>
    <body class="bg-light">

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


        <div class="container-fluid">
            <div class="row">

                <!-- Sidebar trái -->
                <div class="col-sm-3">
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
                <div class="col-sm-9">
                    <div class="panel panel-default">
                        <div class="panel-heading bg-success text-white text-center">
                            <h4 class="mb-0">Update Your Profile</h4>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-8 col-md-offset-2">
                                    <form action="updatecustomerInfoservlet" method="post"
                                          class="border p-4 bg-white rounded shadow-sm">

                                        <!-- Birthday -->
                                        <div class="form-group">
                                            <label for="birthday">Birthday:</label>
                                            <input type="date" id="birthday" name="birthday" class="form-control input-lg" required>
                                        </div>

                                        <!-- Gender -->
                                        <div class="form-group">
                                            <label for="gender">Gender:</label>
                                            <select name="gender" id="gender" class="form-control input-lg" required>
                                                <option value="">-- Select Gender --</option>
                                                <option value="Male">Male</option>
                                                <option value="Female">Female</option>
                                                <option value="Other">Other</option>
                                            </select>
                                        </div>

                                        <div class="text-center">
                                            <button type="submit" class="btn btn-primary btn-lg mt-3">Update Info</button>
                                        </div>
                                    </form>

                                    <% 
                                        String message = (String) request.getAttribute("message");
                                        if (message != null) { 
                                    %>
                                    <div class="alert alert-info mt-4 text-center"><%= message %></div>
                                    <% 
                                        } 
                                    %>
                                </div>
                            </div>
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

