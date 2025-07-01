<%-- 
    Document   : admin
    Created on : May 19, 2025, 2:39:53 PM
    Author     : ADMIN
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.time.Year" %>
<%@page import="model.StatResult"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>DASHMIN - Bootstrap Admin Template</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- ✅ CDN: Owl Carousel Stylesheet -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.min.css">

        <!-- ✅ CDN: Tempus Dominus Stylesheet -->
        <link href="https://cdn.jsdelivr.net/npm/tempusdominus-bootstrap-4@5.39.0/build/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <!-- Customized Bootstrap Stylesheet -->
        <link href="${pageContext.request.contextPath}/DashMin/css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="${pageContext.request.contextPath}/DashMin/css/style.css" rel="stylesheet">
    </head>


    <body>
        <%@ page import="model.User" %>
        <%
            User acc = (User) session.getAttribute("currentAcc");
            if (acc == null || acc.getRole() != 1) {
                response.sendRedirect(request.getContextPath() + "/ZeShopper/LoginServlet");
                return;
            }
        %>


        <div class="container-fluid position-relative bg-white d-flex p-0">
            <!-- Spinner Start -->
            <div id="spinner" class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
                <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
                    <span class="sr-only">Loading...</span>
                </div>
            </div>
            <!-- Spinner End -->


            <!-- Sidebar Start -->
            <div class="sidebar pe-4 pb-3">
                <nav class="navbar bg-light navbar-light">
                    <a href="${pageContext.request.contextPath}/DashMin/admin" class="navbar-brand mx-4 mb-3">
                        <h3 class="text-primary"><i class="fa fa-hashtag me-2"></i>Lafioreria</h3>
                    </a>
                    <div class="d-flex align-items-center ms-4 mb-4">
                        <div class="position-relative">
                            <img class="rounded-circle" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">
                            <div class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"></div>
                        </div>
                        <div class="ms-3">
                            <h6 class="mb-0">${sessionScope.currentAcc.username}</h6>
                            <span>${sessionScope.currentAcc.getRole()}</span>
                        </div>
                    </div>
                    <div class="navbar-nav w-100">
                        <a href="${pageContext.request.contextPath}/DashMin/admin" class="nav-item nav-link active"><i class="fa fa-tachometer-alt me-2"></i>Dashboard</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Elements</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/DashMin/button.jsp" class="dropdown-item">Buttons</a>
                                <a href="${pageContext.request.contextPath}/DashMin/typography.jsp" class="dropdown-item">Typography</a>
                                <a href="${pageContext.request.contextPath}/DashMin/element.jsp" class="dropdown-item">Other Elements</a>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}/DashMin/widget.jsp" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Widgets</a>
                        <a href="${pageContext.request.contextPath}/DashMin/form.jsp" class="nav-item nav-link"><i class="fa fa-keyboard me-2"></i>Forms</a>
                        <a href="${pageContext.request.contextPath}/ViewUserList" class="nav-item nav-link"><i class="fa fa-table me-2"></i>User</a>
                        <a href="${pageContext.request.contextPath}/viewBouquet" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Bouquet</a>
                        <a href="${pageContext.request.contextPath}/DashMin/chart.jsp" class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
                        <a href="${pageContext.request.contextPath}/orderManagement" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Order</a>
                        <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="nav-item nav-link"><i class="fa fa-table me-2"></i>RawFlower</a>
                        <a href="${pageContext.request.contextPath}/category" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Category</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-table me-2"></i>Repair Center</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/repairOrders" class="dropdown-item">Repair Orders</a>
                                <a href="${pageContext.request.contextPath}/repairHistory" class="dropdown-item">Repair History</a>
                                <a href="${pageContext.request.contextPath}/listRequest" class="dropdown-item">List Request</a>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}" class="nav-item nav-link"><i class="fa fa-table me-2"></i>La Fioreria</a>


                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="far fa-file-alt me-2"></i>Pages</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/DashMin/404.jsp" class="dropdown-item">404 Error</a>
                                <a href="${pageContext.request.contextPath}/DashMin/blank.jsp" class="dropdown-item">Blank Page</a>
                                <a href="${pageContext.request.contextPath}/viewuserdetail" class="dropdown-item">View User Detail</a>
                                <a href="${pageContext.request.contextPath}/adduserdetail" class="dropdown-item">Add new User </a>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>
            <!-- Sidebar End -->


            <!-- Content Start -->
            <div class="content">
                <jsp:include page="/DashMin/navbar.jsp"/> <!-- nav bar -->

                <!-- 📈 Doanh thu theo loại hoa (tháng này) -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded p-4">
                        <h2 class="mb-4">🌸 Flower sales (this month)</h2>
                        <div class="mb-3 d-flex gap-3 flex-wrap">
                            <a href="${pageContext.request.contextPath}/flowerqualitystatsservlet" class="btn btn-outline-success">
                                🌼 Quality Flower State
                            </a>
                            <a href="${pageContext.request.contextPath}/flowerlossstats" class="btn btn-outline-danger">
                                💐 Loss Flower
                            </a>
                        </div>

                        <form method="get" action="${pageContext.request.contextPath}/DashMin/admin" class="mb-4">
                            <label class="mb-2 fw-bold">Select flower type:</label>
                            <div class="row">
                                <c:set var="noSelection" value="${empty paramValues.cid}" />
                                <c:forEach var="cat" items="${categoryList}" varStatus="status">
                                    <c:if test="${status.index % 2 == 0}">
                                        <!-- Bắt đầu một hàng mới sau mỗi 2 phần tử -->
                                        <div class="w-100"></div>
                                    </c:if>
                                    <div class="col-md-6 mb-2">
                                        <c:set var="checked" value="false" />
                                        <c:if test="${noSelection}">
                                            <c:set var="checked" value="true" />
                                        </c:if>
                                        <c:forEach var="selected" items="${paramValues.cid}">
                                            <c:if test="${selected == cat.categoryId}">
                                                <c:set var="checked" value="true" />
                                            </c:if>
                                        </c:forEach>

                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" name="cid" value="${cat.categoryId}"
                                                   id="cat${cat.categoryId}"
                                                   <c:if test="${checked}">checked</c:if> />
                                            <label class="form-check-label" for="cat${cat.categoryId}">
                                                ${cat.categoryName}
                                            </label>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <button type="submit" class="btn btn-primary mt-3">View chart</button>
                        </form>


                        <c:if test="${not empty labelsJson}">
                            <canvas id="categoryRevenueChart"
                                    data-labels='${labelsJson}'
                                    data-categories='${categoryListJson}'
                                    <c:forEach var="cat" items="${categoryList}">
                                        <c:set var="dataKey" value="${cat.categoryName}_data"/>
                                        <c:if test="${not empty requestScope[dataKey]}">
                                            data-data_${cat.categoryName}='${requestScope[dataKey]}'
                                        </c:if>

                                    </c:forEach>
                                    style="width:100%; height:300px;">
                            </canvas>
                            <!-- Nút tải biểu đồ doanh thu theo loại hoa -->
                            <button onclick="downloadChartAsImage(window.categoryRevenueChart, 'category_revenue.png')" 
                                    class="btn btn-outline-primary mt-3">
                                📥 Download by Category
                            </button>

                        </c:if>

                    </div>
                </div>

                <!-- Sale & Revenue Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="row g-4">
                        <div class="col-sm-6 col-xl-3">
                            <div class="bg-light rounded d-flex align-items-center justify-content-between p-4">
                                <i class="fa fa-chart-line fa-3x text-primary"></i>
                                <div class="ms-3">
                                    <p class="mb-2">Today's orders</p>
                                    <h6 class="mb-0"><%= request.getAttribute("todayOrders") %></h6>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xl-3">
                            <div class="bg-light rounded d-flex align-items-center justify-content-between p-4">
                                <i class="fa fa-chart-bar fa-3x text-primary"></i>
                                <div class="ms-3">
                                    <p class="mb-2">Total Revenue</p>
                                    <h6 class="mb-0">$<%= request.getAttribute("totalRevenue") %></h6>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xl-3">
                            <div class="bg-light rounded d-flex align-items-center justify-content-between p-4">
                                <i class="fa fa-chart-area fa-3x text-primary"></i>
                                <div class="ms-3">
                                    <p class="mb-2">Today Revenue</p>
                                    <h6 class="mb-0"><%= request.getAttribute("todayOrders") %></h6>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xl-3">
                            <div class="bg-light rounded d-flex align-items-center justify-content-between p-4">
                                <i class="fa fa-chart-pie fa-3x text-primary"></i>
                                <div class="ms-3">
                                    <p class="mb-2">this Month Revenue</p>
                                    <h6 class="mb-0"><%= String.format("%,.0f", request.getAttribute("thisMonthRevenue")) %> VND</h6>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Sale & Revenue End -->


                <!-- Sales & Revenue Chart Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="row g-4">
                        <!-- Doanh thu tháng này -->
                        <div class="col-sm-12 col-xl-6">
                            <div class="bg-light text-center rounded p-4 h-100">

                                <h4 class="mb-0">💰 Revenue this month</h4>
                                <a href="${pageContext.request.contextPath}/revenuebydateservlet">Show All</a>

                                <canvas id="thisMonthChart"
                                        data-labels='<%= request.getAttribute("thisMonthLabels") %>'
                                        data-values='<%= request.getAttribute("thisMonthValues") %>'
                                        style="width: 100%; height: 300px;"></canvas>
                                <!-- Nút tải ảnh biểu đồ Doanh thu tháng này -->
                                <button onclick="downloadChartAsImage(window.thisMonthRevenueChart, 'monthly_revenue.png')"
                                        class="btn btn-outline-primary mt-3">
                                    📥 Download This Month
                                </button>

                            </div>
                        </div>

                        <!-- Salse & Revenue -->
                        <div class="col-sm-12 col-xl-6">
                            <div class="bg-light text-center rounded p-4 h-100">
                                <h4 class="mb-3">3. Revenue and number of orders by day of the week</h4>
                                <canvas id="weekdayChart" style="width: 100%; height: 300px;"></canvas>
                                <!-- Nút tải biểu đồ theo thứ trong tuần -->
                                <button onclick="downloadChartAsImage(window.weekdayStatsChart, 'weekday_revenue.png')" 
                                        class="btn btn-outline-primary mt-3">
                                    📥 Download by Weekday
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Sales & Revenue Chart End -->

                <!-- 📊 Biểu đồ tổng hợp theo tháng, năm, thứ -->
                <div class="container-fluid pt-4 px-4">
                    <div class="row g-4">
                        <!-- 📆 Doanh thu theo tháng -->
                        <div class="col-sm-12 col-xl-6">
                            <div class="bg-light text-center rounded p-4 h-100">
                                <h4 class="mb-3">1. Revenue and number of orders by month – Year <%= request.getAttribute("monthYear") %></h4>
                                <canvas id="monthChart" style="width: 100%; height: 300px;"></canvas>

                                <button onclick="downloadChartAsImage(window.statsChart, 'monthly_stats.png')"
                                        class="btn btn-outline-primary mt-3">
                                    📥 Download Monthly Stats
                                </button>
                            </div>
                        </div>

                        <!-- 📅 Doanh thu theo năm -->
                        <div class="col-sm-12 col-xl-6">
                            <div class="bg-light text-center rounded p-4 h-100">
                                <h4 class="mb-3">2. Revenue and number of orders by year</h4>
                                <a href="${pageContext.request.contextPath}/revenuealltimeservlet">Show All</a>
                                <canvas id="yearChart" style="width: 100%; height: 300px;"></canvas>
                                <!-- Nút tải biểu đồ theo năm -->
                                <button onclick="downloadChartAsImage(window.yearStatsChart, 'yearly_revenue.png')" 
                                        class="btn btn-outline-primary mt-3">
                                    📥 Download by Year
                                </button>

                            </div>
                        </div>

                        <!-- 🗓️ Doanh thu theo thứ -->
                        <!--                        <div class="col-12">
                                                    <div class="bg-light text-center rounded p-4 h-100">
                                                        <h4 class="mb-3">3. Doanh thu & số đơn theo thứ trong tuần</h4>
                                                        <canvas id="weekdayChart" style="width: 100%; height: 300px;"></canvas>
                                                         Nút tải biểu đồ theo thứ trong tuần 
                                                        <button onclick="downloadChartAsImage(window.weekdayStatsChart, 'doanhthu_trongtuan.png')" 
                                                                class="btn btn-outline-info mt-3">
                                                            📥 Tải biểu đồ theo thứ
                                                        </button>
                        
                                                    </div>
                                                </div>-->
                    </div>
                </div>

                <!-- 📊 Thống kê theo tháng trong năm -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded p-4">
                        <h2 class="mb-4">📊 Statistics by month of the year</h2>

                        <!-- 🔽 Chọn năm -->
                        <form method="get" action="${pageContext.request.contextPath}/DashMin/admin" class="mb-4">
                            <label for="year">Năm:</label>
                            <select name="year" class="form-select d-inline-block w-auto">
                                <%
                                    int currentYear = Year.now().getValue();
                                    int selectedYear = (int) request.getAttribute("selectedYear");
                                    for (int y = currentYear - 5; y <= currentYear + 1; y++) {
                                %>
                                <option value="<%= y %>" <%= (y == selectedYear ? "selected" : "") %>>Year <%= y %></option>
                                <%
                                    }
                                %>
                            </select>
                            <button type="submit" class="btn btn-primary">View</button>
                        </form>

                        <!-- ✅ Biểu đồ kép: Doanh thu và Đơn hàng -->
                        <canvas id="statsChart"
                                data-labels='<%= request.getAttribute("labelsJson") %>'
                                data-revenues='<%= request.getAttribute("revenuesJson") %>'
                                data-orders='<%= request.getAttribute("ordersJson") %>'
                                style="max-width:100%; height:300px;">
                        </canvas>
                        <button onclick="downloadChartAsImage(window.statsChart, 'monthly_stats.png')"
                                class="btn btn-outline-primary mt-3">
                            📥 Download Monthly Stats
                        </button>
                    </div>
                </div>


                <c:if test="${not empty statusCounts}">
                    <div class="container-fluid pt-4 px-4">
                        <div class="bg-light rounded p-4 shadow-sm">
                            <h2 class="mb-4 text-center">📊 Order Rate by Status</h2>

                            <!-- Card chứa biểu đồ -->
                            <div class="card mx-auto" style="max-width: 400px;">
                                <div class="card-body d-flex flex-column align-items-center">
                                    <canvas id="orderStatusChart"
                                            data-labels='[<c:forEach var="s" items="${statusCounts}" varStatus="loop">
                                                "${s.statusName}"<c:if test="${!loop.last}">,</c:if>
                                            </c:forEach>]'
                                            data-values='[<c:forEach var="s" items="${statusCounts}" varStatus="loop">
                                                ${s.total}<c:if test="${!loop.last}">,</c:if>
                                            </c:forEach>]'
                                            style="width: 100%; height: auto; max-height: 300px;">
                                    </canvas>

                                    <button onclick="downloadChartAsImage(window.orderStatusChart, 'order_status.png')" 
                                            class="btn btn-outline-primary mt-4 w-100">
                                        📥 Download Order Status
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>


                <!-- Sales Chart End -->


                <!-- Recent Sales Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light text-center rounded p-4">
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <h6 class="mb-0">Recent Salse</h6>
                        </div>
                        <div class="table-responsive">
                            <h3 class="mt-4">🧾 Order Details This Month</h3>
                            <form method="get" action="${pageContext.request.contextPath}/DashMin/admin">
                                <label for="filter">Filter by:</label>
                                <select name="filter" id="filter" onchange="this.form.submit()">
                                    <option value="week" ${param.filter == 'week' ? 'selected' : ''}>This week</option>
                                    <option value="month" ${param.filter == 'month' ? 'selected' : ''}>This month</option>
                                    <option value="year" ${param.filter == 'year' ? 'selected' : ''}>This year</option>
                                    <option value="all" ${param.filter == 'all' ? 'selected' : ''}>All</option>
                                </select>

                            </form>
                            <table border="1" cellspacing="0" cellpadding="8" class="table table-bordered table-striped">
                                <thead>
                                    <tr style="background-color: #f0f0f0;">
                                        <th>Order ID</th>
                                        <th>Order Date</th>
                                        <th>Customer</th>
                                        <th>Product</th>
                                        <th>Category</th>
                                        <th>Quantity</th>
                                        <th>Unit Price</th>
                                        <th>Total</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach var="r" items="${salesListfilter}">
                                        <tr>
                                            <td>${r.orderID}</td>
                                            <td>${r.orderDate}</td>
                                            <td>${r.customerName}</td>
                                            <td>${r.productName}</td>
                                            <td>${r.categoryName}</td>
                                            <td>${r.quantity}</td>
                                            <td>${r.unitPrice}</td>
                                            <td>${r.totalPrice}</td>
                                            <td>${r.status}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                </div>



                <!-- Recent Sales End -->


                <!-- Widgets Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="row g-4">
                        <div class="col-sm-12 col-md-6 col-xl-4">
                            <div class="h-100 bg-light rounded p-4">
                                <div class="d-flex align-items-center justify-content-between mb-2">
                                    <h6 class="mb-0">Messages</h6>
                                    <a href="contact-list">Show All</a>
                                </div>

                                <c:forEach var="c" items="${messages}" varStatus="loop" end="3"> 
                                    <div class="d-flex align-items-center ${loop.last ? 'pt-3' : 'border-bottom py-3'}">
                                        <img class="rounded-circle flex-shrink-0" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">
                                        <div class="w-100 ms-3">
                                            <div class="d-flex w-100 justify-content-between">
                                                <h6 class="mb-0">${c.name}</h6>
                                                <small><fmt:formatDate value="${c.createdAt}" pattern="HH:mm dd/MM/yyyy" /></small>
                                            </div>
                                            <style>
                                                .message-snippet {
                                                    display: -webkit-box;
                                                    -webkit-line-clamp: 1; /* số dòng muốn hiển thị */
                                                    -webkit-box-orient: vertical;
                                                    overflow: hidden;
                                                    text-overflow: ellipsis;
                                                    word-break: break-word;
                                                    max-height: 1.2em; /* tương ứng với 1 dòng */
                                                }
                                            </style>

                                            <span class="message-snippet">${c.message}</span>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6 col-xl-4">
                            <div class="h-100 bg-light rounded p-4">
                                <div class="d-flex align-items-center justify-content-between mb-4">
                                    <h6 class="mb-0">Calender</h6>
                                    <a href="">Show All</a>
                                </div>
                                <div id="calender"></div>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6 col-xl-4">
                            <div class="h-100 bg-light rounded p-4">
                                <div class="d-flex align-items-center justify-content-between mb-4">
                                    <h6 class="mb-0">To Do List</h6>
                                    <a href="">Show All</a>
                                </div>
                                <div class="d-flex mb-2">
                                    <input class="form-control bg-transparent" type="text" placeholder="Enter task">
                                    <button type="button" class="btn btn-primary ms-2">Add</button>
                                </div>
                                <div class="d-flex align-items-center border-bottom py-2">
                                    <input class="form-check-input m-0" type="checkbox">
                                    <div class="w-100 ms-3">
                                        <div class="d-flex w-100 align-items-center justify-content-between">
                                            <span>Short task goes here...</span>
                                            <button class="btn btn-sm"><i class="fa fa-times"></i></button>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center border-bottom py-2">
                                    <input class="form-check-input m-0" type="checkbox">
                                    <div class="w-100 ms-3">
                                        <div class="d-flex w-100 align-items-center justify-content-between">
                                            <span>Short task goes here...</span>
                                            <button class="btn btn-sm"><i class="fa fa-times"></i></button>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center border-bottom py-2">
                                    <input class="form-check-input m-0" type="checkbox" checked>
                                    <div class="w-100 ms-3">
                                        <div class="d-flex w-100 align-items-center justify-content-between">
                                            <span><del>Short task goes here...</del></span>
                                            <button class="btn btn-sm text-primary"><i class="fa fa-times"></i></button>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center border-bottom py-2">
                                    <input class="form-check-input m-0" type="checkbox">
                                    <div class="w-100 ms-3">
                                        <div class="d-flex w-100 align-items-center justify-content-between">
                                            <span>Short task goes here...</span>
                                            <button class="btn btn-sm"><i class="fa fa-times"></i></button>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center pt-2">
                                    <input class="form-check-input m-0" type="checkbox">
                                    <div class="w-100 ms-3">
                                        <div class="d-flex w-100 align-items-center justify-content-between">
                                            <span>Short task goes here...</span>
                                            <button class="btn btn-sm"><i class="fa fa-times"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Widgets End -->


                <!-- Footer Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded-top p-4">
                        <div class="row">
                            <div class="col-12 col-sm-6 text-center text-sm-start">
                                &copy; <a href="#">Your Site Name</a>, All Right Reserved. 
                            </div>
                            <div class="col-12 col-sm-6 text-center text-sm-end">
                                <!--/*** This template is free as long as you keep the footer author’s credit link/attribution link/backlink. If you'd like to use the template without the footer author’s credit link/attribution link/backlink, you can purchase the Credit Removal License from "https://htmlcodex.com/credit-removal". Thank you for your support. ***/-->
                                Designed By <a href="https://htmlcodex.com">HTML Codex</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Footer End -->
            </div>
            <!-- Content End -->


            <!-- Back to Top -->
            <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
        </div>

        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <!-- Bootstrap -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>

        <!-- Chart.js -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

        <!-- Waypoints -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/waypoints/4.0.1/jquery.waypoints.min.js"></script>

        <!-- Easing -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>

        <!-- Owl Carousel -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>

        <!-- Moment + Tempus Dominus -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/tempusdominus-bootstrap-4@5.39.0/build/js/tempusdominus-bootstrap-4.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/tempusdominus-bootstrap-4@5.39.0/build/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <!-- Gán dữ liệu vào canvas qua data-attributes để JS đọc -->
        <script>
                                    document.getElementById("monthChart").setAttribute("data-labels", '<%= request.getAttribute("monthLabels") %>');
                                    document.getElementById("monthChart").setAttribute("data-revenues", '<%= request.getAttribute("monthRevenues") %>');
                                    document.getElementById("monthChart").setAttribute("data-orders", '<%= request.getAttribute("monthOrders") %>');

                                    document.getElementById("yearChart").setAttribute("data-labels", '<%= request.getAttribute("yearLabels") %>');
                                    document.getElementById("yearChart").setAttribute("data-revenues", '<%= request.getAttribute("yearRevenues") %>');
                                    document.getElementById("yearChart").setAttribute("data-orders", '<%= request.getAttribute("yearOrders") %>');

                                    document.getElementById("weekdayChart").setAttribute("data-labels", '<%= request.getAttribute("weekdayLabels") %>');
                                    document.getElementById("weekdayChart").setAttribute("data-revenues", '<%= request.getAttribute("weekdayRevenues") %>');
                                    document.getElementById("weekdayChart").setAttribute("data-orders", '<%= request.getAttribute("weekdayOrders") %>');
        </script>
        <!-- Template Javascript -->
        <script src="${pageContext.request.contextPath}/DashMin/js/main.js"></script>
    </body>
</html>
