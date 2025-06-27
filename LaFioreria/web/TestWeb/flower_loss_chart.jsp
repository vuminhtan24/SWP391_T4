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
        <h2>Lý do lỗi theo loại hoa</h2>
        <canvas id="barByCategory"></canvas>

        <h2>Hoa hết hạn theo thời gian theo loại hoa</h2>
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
                            label: 'Số lỗi',
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
                            label: 'Hoa hết hạn',
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
