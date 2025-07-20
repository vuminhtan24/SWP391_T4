<%-- 
    Document   : header
    Created on : Jun 10, 2025, 8:19:07 AM
    Author     : VU MINH TAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <body>
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="contactinfo">
                                <ul class="nav nav-pills">
                                    <li><a href="#"><i class="fa fa-phone"></i> +2 95 01 88 821</a></li>
                                    <li><a href="#"><i class="fa fa-envelope"></i> info@domain.com</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="social-icons pull-right">
                                <ul class="nav navbar-nav">
                                    <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                                    <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                                    <li><a href="#"><i class="fa fa-linkedin"></i></a></li>
                                    <li><a href="#"><i class="fa fa-dribbble"></i></a></li>
                                    <li><a href="#"><i class="fa fa-google-plus"></i></a></li>
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
                                        <li><a href="#">Canada</a></li>
                                        <li><a href="#">UK</a></li>
                                    </ul>
                                </div>

                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle usa" data-toggle="dropdown">
                                        DOLLAR
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Canadian Dollar</a></li>
                                        <li><a href="#">Pound</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-8">
                            <div class="shop-menu pull-right">
                                <ul class="nav navbar-nav">
                                    <c:choose>
                                        <c:when test="${sessionScope.currentAcc != null}">
                                            <input type="hidden" name="user_id" value="${sessionScope.currentAcc.userid}" />
                                            <li class="dropdown">
                                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                                    <i class="fa fa-user"></i> Hello, ${sessionScope.currentAcc.username} <b class="caret"></b>
                                                </a>
                                                <ul class="dropdown-menu">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.currentAcc.getRole() != 7}">
                                                            <li><a href="${pageContext.request.contextPath}/DashMin/admin"><i class="fa fa-id-card"></i> Manage Page</a></li>
                                                            </c:when>
                                                        </c:choose>
                                                    <li><a href="${pageContext.request.contextPath}/viewuserdetailhome"><i class="fa fa-id-card"></i> User Detail</a></li>
                                                    <li><a href="${pageContext.request.contextPath}/ZeShopper/changePassword.jsp"><i class="fa fa-key"></i> Change Password</a></li>
                                                    <li class="divider"></li>
                                                    <li><a href="${pageContext.request.contextPath}/ZeShopper/LogoutServlet"><i class="fa fa-unlock"></i> Logout</a></li>
                                                </ul>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="${pageContext.request.contextPath}/ZeShopper/LoginServlet"><i class="fa fa-lock"></i> Login</a></li>
                                            </c:otherwise>
                                        </c:choose>
                                    <li><a href="${pageContext.request.contextPath}/ZeShopper/order"><i class="fa fa-file-text-o"></i> Order</a></li>
                                    
                                    <li class="dropdown">
                                        <a href="${pageContext.request.contextPath}/checkout" class="dropdown-toggle" data-toggle="dropdown">
                                            <i class="fa fa-shopping-cart"></i> Checkout <b class="caret"></b>
                                        </a>
                                            <ul class="dropdown-menu" style="padding-right: 15px">
                                            
                                            <li><a href="${pageContext.request.contextPath}/checkout"><i class="fa fa-shopping-cart"></i>Checkout Retail Order</a></li>
                                            <li><a href="${pageContext.request.contextPath}/checkout?mode=wholesale"><i class="fa fa-shopping-cart"></i>Checkout WholeSale Order</a></li>
                                            
                                        </ul>
                                    </li>
                                    
                                    <li class="dropdown">
                                        <a href="${pageContext.request.contextPath}/ZeShopper/cart" class="dropdown-toggle" data-toggle="dropdown">
                                            <i class="fa fa-shopping-cart"></i> Cart <b class="caret"></b>
                                        </a>
                                            <ul class="dropdown-menu" style="padding-right: 15px">
                                            
                                            <li><a href="${pageContext.request.contextPath}/ZeShopper/cart"><i class="fa fa-shopping-cart"></i>Cart Retail</a></li>
                                            <li><a href="${pageContext.request.contextPath}/cartWholeSale"><i class="fa fa-shopping-cart"></i>Cart WholeSale</a></li>
                                            
                                        </ul>
                                    </li>
                                    
                                    <li class="dropdown">
                                        <a href="${pageContext.request.contextPath}/wholeSale" class="dropdown-toggle" data-toggle="dropdown">
                                            <i class="fa fa-shopping-cart"></i> WholeSale <b class="caret"></b>
                                        </a>
                                            <ul class="dropdown-menu" style="padding-right: 15px">
                                            
                                            <li><a href="${pageContext.request.contextPath}/wholeSale"><i class="fa fa-shopping-cart"></i>WholeSale</a></li>
                                            <li><a href="${pageContext.request.contextPath}/listQuotation"><i class="fa fa-shopping-cart"></i>List of request for quotation</a></li>
                                            
                                        </ul>
                                    </li>
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
                                    <li><a href="${pageContext.request.contextPath}/home" class="active">Home</a></li>
                                    <li class="dropdown"><a href="#">Shop<i class="fa fa-angle-down"></i></a>
                                        <ul role="menu" class="sub-menu">
                                            <li><a href="${pageContext.request.contextPath}/product">Products</a></li>
                                            <li><a href="${pageContext.request.contextPath}/ZeShopper/checkout.jsp">Checkout</a></li> 
                                            <li><a href="${pageContext.request.contextPath}/ZeShopper/cart.jsp">Cart</a></li> 
                                            <li><a href="${pageContext.request.contextPath}/ZeShopper/login">Login</a></li> 
                                        </ul>
                                    </li> 
                                    <li><a href="${pageContext.request.contextPath}/blog">Blog</a>
                                    </li> 
                                    <li><a href="${pageContext.request.contextPath}/ZeShopper/contact-us.jsp">Contact</a></li>
                                    <li><a href="${pageContext.request.contextPath}/ZeShopper/about-us.jsp">About us</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!--/header-bottom-->
        </header><!--/header-->

        <script>
            function openOrderPopup() {
                document.getElementById("order-popup").style.display = "flex";
                loadOrders("all"); // Load all orders by default
            }
        </script>
    </body>
</html>