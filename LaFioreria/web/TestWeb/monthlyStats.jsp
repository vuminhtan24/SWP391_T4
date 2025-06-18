<%-- 
    Document   : monthlyStats
    Created on : Jun 18, 2025, 9:13:25 AM
    Author     : LAPTOP
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.Year" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Thống kê theo tháng</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            body {
                font-family: Arial;
                padding: 20px;
            }
            form {
                margin-bottom: 20px;
            }
            canvas {
                max-width: 1000px;
                margin-top: 40px;
            }
        </style>
    </head>
    <body>

        <h2>📊 Thống kê theo tháng trong năm</h2>

        <!-- 🔽 Chọn năm -->
        <form method="get" action="monthly-stats">
            Năm:
            <select name="year">
                <%
                    int currentYear = Year.now().getValue();
                    int selectedYear = (int) request.getAttribute("selectedYear");
                    for (int y = currentYear - 5; y <= currentYear + 1; y++) {
                %>
                <option value="<%= y %>" <%= (y == selectedYear ? "selected" : "") %>>Năm <%= y %></option>
                <%
                    }
                %>
            </select>
            <input type="submit" value="Xem">
        </form>

        <!-- ✅ Biểu đồ kép: Doanh thu và Đơn hàng -->
        <canvas id="statsChart"></canvas>
        <script>
            const labels = <%= request.getAttribute("labelsJson") %>;
            const revenues = <%= request.getAttribute("revenuesJson") %>;
            const orders = <%= request.getAttribute("ordersJson") %>;

            new Chart(document.getElementById('statsChart'), {
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
                            yAxisID: 'y',
                        },
                        {
                            label: 'Số đơn hàng',
                            data: orders,
                            backgroundColor: 'rgba(255, 99, 132, 0.6)',
                            borderColor: 'rgb(255, 99, 132)',
                            borderWidth: 1,
                            yAxisID: 'y1',
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
                            title: {display: true, text: 'Doanh thu (VND)'},
                            beginAtZero: true
                        },
                        y1: {
                            type: 'linear',
                            position: 'right',
                            title: {display: true, text: 'Số đơn hàng'},
                            grid: {drawOnChartArea: false},
                            beginAtZero: true
                        }
                    }
                }
            });
        </script>

    </body>
</html>

