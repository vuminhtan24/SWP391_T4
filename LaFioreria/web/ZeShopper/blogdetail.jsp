<%-- 
    Document   : blogdetail
    Created on : Jun 25, 2025, 8:30:37 AM
    Author     : k16
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
        <title>Blog Detail | E-Shopper</title>
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
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/ZeShopper/images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/ZeShopper/images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/ZeShopper/images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/ZeShopper/images/ico/apple-touch-icon-57-precomposed.png">
        <style>
            .filter-btn {
                display: flex;
                gap: 10px;
            }
        </style>
    </head><!--/head-->

    <body>
        <jsp:include page="/ZeShopper/header.jsp"/>

        <section>
            <div class="container">
                <div class="row">
                    <div class="col-sm-9">
                        <div class="blog-post-area">
                            <!-- Blog post content -->
                            <div class="single-blog-post">
                                <h2>${blog.title}</h2>
                                <div class="post-meta">
                                    <ul>
                                        <li><i class="fa fa-user"></i> ${blog.owner.userid}</li>
                                        <li><i class="fa fa-calendar"></i> 
                                            <fmt:formatDate value="${blog.created_at}" pattern="MMM dd, yyyy" />
                                        </li>
                                        <li><i class="fa fa-clock-o"></i> 
                                            <fmt:formatDate value="${blog.created_at}" pattern="HH:mm" />
                                        </li>
                                    </ul>
                                </div>

                                <c:if test="${not empty blog.img_url}">
                                    <a href="${pageContext.request.contextPath}/blog/detail?bid=${blog.blogId}">
                                        <img src="${blog.img_url}" alt="${blog.title}" 
                                             style="max-width: 100%; height: 200px; object-fit: cover;">
                                    </a>
                                </c:if>

                                <div class="post-content">
                                    ${blog.context}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <!-- Sidebar content -->
                        <div class="left-sidebar">
                            <!-- Recent posts, categories, etc. -->
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="/ZeShopper/footer.jsp"/>

        <script src="${pageContext.request.contextPath}/ZeShopper/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/ZeShopper/js/bootstrap.min.js"></script>
    </body>
</html>