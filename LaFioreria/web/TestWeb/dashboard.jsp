<%-- 
    Document   : dashboard
    Created on : Jun 18, 2025, 10:00:59 AM
    Author     : LAPTOP
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>📊 Dashboard Thống kê tổng hợp</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            body {
                font-family: Arial;
                padding: 20px;
            }
            .section {
                margin-bottom: 60px;
            }
            canvas {
                max-width: 100%;
                min-height: 300px;
            }
            .chart-container {
                overflow-x: auto;
            }
        </style>
    </head>
    <body>

        <h1>📈 Dashboard Thống kê</h1>

        <div class="stat-boxes" style="display: flex; gap: 20px; margin: 20px 0;">
            <div class="stat" style="background:#f9f9f9;padding:15px;border-radius:8px;text-align:center;flex:1">
                <h3><%= request.getAttribute("todayOrders") %></h3>
                <p>🛒 Đơn hàng hôm nay</p>
            </div>
            <div class="stat" style="background:#f0f0f0;padding:15px;border-radius:8px;text-align:center;flex:1">
                <h3><%= String.format("%,.0f", request.getAttribute("thisMonthRevenue")) %> VNĐ</h3>
                <p>💰 Doanh thu tháng này</p>
            </div>
        </div>
        <canvas id="thisMonthChart" style="max-width: 1000px; height: 300px;"></canvas>
        <script>
            const labels = <%= request.getAttribute("thisMonthLabels") %>;
            const values = <%= request.getAttribute("thisMonthValues") %>;

            new Chart(document.getElementById("thisMonthChart"), {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                            label: "Doanh thu theo ngày (tháng này)",
                            data: values,
                            backgroundColor: 'rgba(75, 192, 192, 0.5)',
                            borderColor: 'rgb(75, 192, 192)',
                            borderWidth: 1
                        }]
                },
                options: {
                    scales: {
                        y: {beginAtZero: true}
                    }
                }
            });
        </script>



        <!-- 🔽 Bộ lọc năm -->
        <form method="get" action="${pageContext.request.contextPath}/dashboardstatsservlet" style="margin-bottom: 20px;">
            Năm bắt đầu:
            <select name="fromYear">
                <% int currentYear = java.time.Year.now().getValue();
           for (int y = currentYear - 10; y <= currentYear + 1; y++) { %>
                <option value="<%= y %>" <%= ("" + y).equals(request.getParameter("fromYear")) ? "selected" : "" %>>Năm <%= y %></option>
                <% } %>
            </select>

            Năm kết thúc:
            <select name="toYear">
                <% for (int y = currentYear - 10; y <= currentYear + 1; y++) { %>
                <option value="<%= y %>" <%= ("" + y).equals(request.getParameter("toYear")) ? "selected" : "" %>>Năm <%= y %></option>
                <% } %>
            </select>

            <input type="submit" value="Lọc">
        </form>

        <form action="${pageContext.request.contextPath}/revenuebydateservlet" method="get">
            <input type="submit" value="📊 Xem doanh thu theo ngày trong tháng">
        </form>


        <!-- 📆 Theo tháng -->
        <div class="section">
            <h2>1. Doanh thu và số đơn theo tháng – Năm <%= request.getAttribute("monthYear") %></h2>
            <div class="chart-container">
                <canvas id="monthChart" style="min-width: 1000px;"></canvas>
            </div>
        </div>

        <!-- 📅 Theo năm -->
        <div class="section">
            <h2>2. Doanh thu và số đơn theo từng năm</h2>
            <div class="chart-container">
                <canvas id="yearChart" style="min-width: 1000px;"></canvas>
            </div>
        </div>

        <!-- 🗓️ Theo thứ -->
        <div class="section">
            <h2>3. Doanh thu và số đơn theo thứ trong tuần</h2>
            <div class="chart-container">
                <canvas id="weekdayChart" style="min-width: 800px;"></canvas>
            </div>
        </div>

        <script>
            function renderChart(canvasId, labels, revenues, orders, labelPrefix = '') {
                new Chart(document.getElementById(canvasId), {
                    type: 'bar',
                    data: {
                        labels: labels,
                        datasets: [
                            {
                                label: 'Doanh thu (VND)',
                                data: revenues,
                                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                                borderColor: 'rgb(54, 162, 235)',
                                borderWidth: 1,
                                yAxisID: 'y'
                            },
                            {
                                label: 'Số đơn hàng',
                                data: orders,
                                backgroundColor: 'rgba(255, 159, 64, 0.6)',
                                borderColor: 'rgb(255, 159, 64)',
                                borderWidth: 1,
                                yAxisID: 'y1'
                            }
                        ]
                    },
                    options: {
                        responsive: true,
                        interaction: {mode: 'index', intersect: false},
                        scales: {
                            y: {
                                type: 'linear',
                                position: 'left',
                                beginAtZero: true,
                                title: {display: true, text: 'Doanh thu (VND)'}
                            },
                            y1: {
                                type: 'linear',
                                position: 'right',
                                beginAtZero: true,
                                grid: {drawOnChartArea: false},
                                title: {display: true, text: 'Số đơn hàng'}
                            }
                        }
                    }
                });
            }

            renderChart("monthChart", <%= request.getAttribute("monthLabels") %>, <%= request.getAttribute("monthRevenues") %>, <%= request.getAttribute("monthOrders") %>);
            renderChart("yearChart", <%= request.getAttribute("yearLabels") %>, <%= request.getAttribute("yearRevenues") %>, <%= request.getAttribute("yearOrders") %>);
            renderChart("weekdayChart", <%= request.getAttribute("weekdayLabels") %>, <%= request.getAttribute("weekdayRevenues") %>, <%= request.getAttribute("weekdayOrders") %>);
        </script>

        <h3>🧾 Bảng chi tiết đơn hàng trong tháng</h3>
        <table border="1" cellspacing="0" cellpadding="8">
            <thead>
                <tr style="background-color: #f0f0f0;">
                    <th>Order ID</th>
                    <th>Ngày đặt</th>
                    <th>Khách hàng</th>
                    <th>Sản phẩm</th>
                    <th>Loại</th>
                    <th>Số lượng</th>
                    <th>Đơn giá</th>
                    <th>Tổng</th>
                    <th>Trạng thái</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="r" items="${salesList}">
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

        <h3>4. Doanh thu theo ngày trong tháng hiện tại</h3>
        <canvas id="dailyChart"></canvas>
        <script>
            const dailyLabels = <%= request.getAttribute("dailyLabels") %>;
            const dailyValues = <%= request.getAttribute("dailyValues") %>;

            new Chart(document.getElementById("dailyChart"), {
                type: "line",
                data: {
                    labels: dailyLabels,
                    datasets: [{
                            label: "Doanh thu theo ngày (VND)",
                            data: dailyValues,
                            borderColor: "rgba(75, 192, 192, 1)",
                            backgroundColor: "rgba(75, 192, 192, 0.2)",
                            fill: true,
                            tension: 0.3
                        }]
                },
                options: {
                    scales: {
                        y: {beginAtZero: true}
                    }
                }
            });
        </script>

    </body>
</html>

