<%-- 
    Document   : blog
    Created on : May 19, 2025, 8:43:46 AM
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
        <title>Blog | E-Shopper</title>
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
                    <div class="col-sm-3">
                        <div class="left-sidebar">
                            <!-- Blog Categories -->
                            <h2>Blog Categories</h2>
                            <div class="panel-group category-products" id="accordian">

                                <!-- All Categories Link -->
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a href="blog?page=1" class="${empty param.categoryId ? 'active' : ''}">
                                                All Categories
                                            </a>
                                        </h4>
                                    </div>
                                </div>
                                <c:forEach var="category" items="${cList}">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a href="blog?categoryId=${category.categoryId}&page=1">
                                                    ${category.categoryName}
                                                </a>
                                            </h4>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div><!--/category-products-->

                            <!-- Search and Filter -->
                            <div class="search-filter">
                                <h2>Search & Filter</h2>
                                <form action="blog" method="get" class="well">
                                    <div class="form-group">
                                        <input type="text" name="search" class="form-control" 
                                               placeholder="Search blogs..." value="${param.search}">
                                    </div>

                                    <div class="form-group">
                                        <select name="sortBy" class="form-control">
                                            <option value="created_at" ${param.sortBy == 'created_at' ? 'selected' : ''}>Date Created</option>
                                            <option value="updated_at" ${param.sortBy == 'updated_at' ? 'selected' : ''}>Date Updated</option>
                                            <option value="title" ${param.sortBy == 'title' ? 'selected' : ''}>Title</option>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <select name="sort" class="form-control">
                                            <option value="DESC" ${param.sort == 'DESC' ? 'selected' : ''}>Newest First</option>
                                            <option value="ASC" ${param.sort == 'ASC' ? 'selected' : ''}>Oldest First</option>
                                        </select>
                                    </div>

                                    <input type="hidden" name="categoryId" value="${param.categoryId}">
                                    <input type="hidden" name="page" value="1">
                                    <div class="filter-btn">
                                        <button type="submit" class="btn btn-primary btn-block">Apply Filter</button>
                                        <a class="btn btn-primary btn-block" href="${pageContext.request.contextPath}/blog">Clear Filter</a>
                                    </div>
                                </form>
                            </div>

                            <!--                            <div class="shipping text-center">shipping
                                                            <img src="${pageContext.request.contextPath}/ZeShopper/images/home/shipping.jpg" alt="" />
                                                        </div>/shipping-->
                        </div>
                    </div>

                    <div class="col-sm-9">
                        <div class="blog-post-area">
                            <h2 class="title text-center">
                                <c:choose>
                                    <c:when test="${not empty param.search}">
                                        Search Results for "${param.search}"
                                    </c:when>
                                    <c:when test="${not empty selectedCategory}">
                                        ${selectedCategory.name} Blogs
                                    </c:when>
                                    <c:otherwise>
                                        Latest From our Blog
                                    </c:otherwise>
                                </c:choose>
                                <small class="text-muted">(${totalCount} blog(s) found)</small>
                            </h2>

                            <!-- Check if blogs exist -->
                            <c:choose>
                                <c:when test="${not empty blogs}">
                                    <!-- Loop through blogs -->
                                    <c:forEach var="blog" items="${blogs}">
                                        <div class="single-blog-post" style="margin-bottom: 20px; padding: 20px; border: solid 1px #cccccc; border-radius: 20px;">
                                            <h2>${blog.title}</h2>
                                            <div class="post-meta">
                                                <ul>
                                                    <li><i class="fa fa-user"></i> Author: ${blog.owner.fullname}</li>
                                                    <li><i class="fa fa-calendar"></i> 
                                                        <fmt:formatDate value="${blog.created_at}" pattern="MMM dd, yyyy" />
                                                    </li>
                                                    <li><i class="fa fa-clock-o"></i> 
                                                        <fmt:formatDate value="${blog.created_at}" pattern="HH:mm" />
                                                    </li>
                                                </ul>
                                            </div>

                                            <!-- Blog Image -->
                                            <c:if test="${not empty blog.img_url}">
                                                <a href="${pageContext.request.contextPath}/blog/detail?bid=${blog.blogId}">
                                                    <img src="${blog.img_url}" alt="${blog.title}" 
                                                         style="max-width: 100%; height: 200px; object-fit: cover;">
                                                </a>
                                            </c:if>

                                            <!-- Pre-context or excerpt -->
                                            <p>
                                                <c:choose>
                                                    <c:when test="${not empty blog.pre_context}">
                                                        ${blog.pre_context}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${fn:length(blog.context) > 200}">
                                                                ${fn:substring(blog.context, 0, 200)}...
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${blog.context}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
                                            </p>

                                            <a class="btn btn-primary" style="border-radius: 10px;" href="${pageContext.request.contextPath}/blog/detail?bid=${blog.blogId}">Read More</a>
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <!-- No blogs found -->
                                    <div class="single-blog-post text-center">
                                        <h3>No blogs found</h3>
                                        <p>
                                            <c:choose>
                                                <c:when test="${not empty param.search}">
                                                    No blogs match your search criteria. Try different keywords.
                                                </c:when>
                                                <c:otherwise>
                                                    No blogs are available at the moment. Please check back later.
                                                </c:otherwise>
                                            </c:choose>
                                        </p>
                                        <a href="blog" class="btn btn-primary">View All Blogs</a>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <!-- Pagination -->
                            <c:if test="${totalPages > 1}">
                                <div class="pagination-area">
                                    <ul class="pagination">
                                        <!-- Previous Page -->
                                        <c:if test="${currentPage > 1}">
                                            <li>
                                                <a href="blog?page=${currentPage - 1}&search=${param.search}&sortBy=${param.sortBy}&sort=${param.sort}&categoryId=${param.categoryId}">
                                                    <i class="fa fa-angle-double-left"></i>
                                                </a>
                                            </li>
                                        </c:if>

                                        <!-- Page Numbers -->
                                        <c:forEach var="i" begin="1" end="${totalPages}">
                                            <li>
                                                <a href="blog?page=${i}&search=${param.search}&sortBy=${param.sortBy}&sort=${param.sort}&categoryId=${param.categoryId}" 
                                                   class="${i == currentPage ? 'active' : ''}">${i}</a>
                                            </li>
                                        </c:forEach>

                                        <!-- Next Page -->
                                        <c:if test="${currentPage < totalPages}">
                                            <li>
                                                <a href="blog?page=${currentPage + 1}&search=${param.search}&sortBy=${param.sortBy}&sort=${param.sort}&categoryId=${param.categoryId}">
                                                    <i class="fa fa-angle-double-right"></i>
                                                </a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </div>
                            </c:if>
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