<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Biểu đồ Chất lượng Hoa</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            .chart-container {
                width: 400px;
                height: 400px;
                margin: 20px auto;
            }
        </style>
    </head>
    <body>
        <h2 style="text-align: center;">Tỷ lệ tổn thất hoa (Pie Chart)</h2>
        <div class="chart-container">
            <canvas id="pieChart"></canvas>
        </div>

        <h2 style="text-align: center;">Hoa hỏng theo lý do (Bar Chart)</h2>
        <div class="chart-container">
            <canvas id="barChart"></canvas>
        </div>

        <%
            Map<String, Integer> importVsWasteStats = (Map<String, Integer>) request.getAttribute("importVsWasteStats");
            Map<String, Integer> discardReasonStats = (Map<String, Integer>) request.getAttribute("discardReasonStats");

            StringBuilder pieLabels = new StringBuilder();
            StringBuilder pieData = new StringBuilder();
            for (Map.Entry<String, Integer> entry : importVsWasteStats.entrySet()) {
                pieLabels.append("\"").append(entry.getKey()).append("\",");
                pieData.append(entry.getValue()).append(",");
            }

            StringBuilder barLabels = new StringBuilder();
            StringBuilder barData = new StringBuilder();
            for (Map.Entry<String, Integer> entry : discardReasonStats.entrySet()) {
                barLabels.append("\"").append(entry.getKey()).append("\",");
                barData.append(entry.getValue()).append(",");
            }
        %>

        <script>
            const pieChart = new Chart(document.getElementById('pieChart'), {
                type: 'pie',
                data: {
                    labels: [<%= pieLabels.toString().replaceAll(",$", "") %>],
                    datasets: [{
                            label: 'Tỷ lệ hoa',
                            data: [<%= pieData.toString().replaceAll(",$", "") %>],
                            backgroundColor: ['#4caf50', '#f44336', '#ff9800', '#9c27b0']
                        }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false
                }
            });

            const barChart = new Chart(document.getElementById('barChart'), {
                type: 'bar',
                data: {
                    labels: [<%= barLabels.toString().replaceAll(",$", "") %>],
                    datasets: [{
                            label: 'Số lượng bị loại',
                            data: [<%= barData.toString().replaceAll(",$", "") %>],
                            backgroundColor: '#f44336'
                        }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    indexAxis: 'y',
                    scales: {
                        x: {
                            beginAtZero: true
                        }
                    }
                }
            });
        </script>
    </body>
</html>
