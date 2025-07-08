<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="model.StatResult"%>
<html>
    <head>
        <title>Biểu đồ tổn thất hoa</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            canvas {
                max-width: 600px;
                margin-bottom: 40px;
            }
        </style>
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
                            <span>Admin</span>
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

            <h2>Discard Reasons by Flower Type</h2>
            <canvas id="barByCategory"></canvas>

            <h2>Expired Flowers Over Time by Category</h2>
            <canvas id="lineExpired"></canvas>

            <%
                Map<String, Integer> discardReasonByCategory = (Map<String, Integer>) request.getAttribute("discardReasonByCategory");
                List<StatResult> expiredByMonthAndCategory = (List<StatResult>) request.getAttribute("expiredByMonthAndCategory");
            %>

            <script>
                // Bar chart: Lý do lỗi theo loại hoa
                const barLabels = [<% for (String key : discardReasonByCategory.keySet()) { %>"<%= key %>", <% } %>];
                const barData = [<% for (Integer val : discardReasonByCategory.values()) { %><%= val %>, <% } %>];

                new Chart(document.getElementById("barByCategory"), {
                    type: 'bar',
                    data: {
                        labels: barLabels,
                        datasets: [{
                                label: 'Number of Issues',
                                data: barData,
                                backgroundColor: '#f44336'
                            }]
                    },
                    options: {
                        responsive: true
                    }
                });

                // Line chart: Hoa hết hạn theo thời gian theo loại hoa
                const lineLabels = [<% for (StatResult r : expiredByMonthAndCategory) { %>"<%= r.getLabel() %>", <% } %>];
                const lineData = [<% for (StatResult r : expiredByMonthAndCategory) { %><%= r.getValue() %>, <% } %>];

                new Chart(document.getElementById("lineExpired"), {
                    type: 'line',
                    data: {
                        labels: lineLabels,
                        datasets: [{
                                label: 'Expired Flowers',
                                data: lineData,
                                borderColor: 'blue',
                                tension: 0.4,
                                fill: false
                            }]
                    },
                    options: {
                        responsive: true,
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });
            </script>
    </body>
</html>
