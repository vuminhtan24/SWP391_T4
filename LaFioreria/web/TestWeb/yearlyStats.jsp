<%-- 
    Document   : yearlyStats
    Created on : Jun 18, 2025, 9:28:19 AM
    Author     : LAPTOP
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Thống kê theo năm</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            body {
                font-family: Arial;
                padding: 20px;
            }
            canvas {
                max-width: 1000px;
                margin-top: 40px;
            }
        </style>
    </head>
    <body>

        <h2>📊 Thống kê doanh thu và số đơn theo từng năm</h2>

        <!-- Biểu đồ kép -->
        <canvas id="yearlyChart"></canvas>
        <script>
            const labels = <%= request.getAttribute("labelsJson") %>;
            const revenues = <%= request.getAttribute("revenuesJson") %>;
            const orders = <%= request.getAttribute("ordersJson") %>;

            new Chart(document.getElementById('yearlyChart'), {
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
                            backgroundColor: 'rgba(255, 99, 132, 0.6)',
                            borderColor: 'rgb(255, 99, 132)',
                            borderWidth: 1,
                            yAxisID: 'y1'
                        }
                    ]
                },
                options: {
                    responsive: true,
                    interaction: {
                        mode: 'index',
                        intersect: false
                    },
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
        </script>

    </body>
</html>

