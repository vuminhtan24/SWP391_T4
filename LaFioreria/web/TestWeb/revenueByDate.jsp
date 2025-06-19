<%-- 
    Document   : revenueByDate
    Created on : Jun 18, 2025, 10:35:23 AM
    Author     : LAPTOP
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.google.gson.Gson" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Doanh thu theo ngày</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            body {
                font-family: Arial;
                padding: 20px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 30px;
            }
            th, td {
                border: 1px solid #ccc;
                padding: 8px;
                text-align: center;
            }
            th {
                background-color: #f4f4f4;
            }
        </style>
    </head>
    <body>

        <h2>📆 Doanh thu từng ngày trong tháng hiện tại</h2>

        <!-- Tổng doanh thu -->
        <h3>💰 Doanh thu tổng quan</h3>
        <ul>
            <li>📅 Doanh thu hôm nay: <strong><%= String.format("%,.0f", request.getAttribute("todayRevenue")) %> VNĐ</strong></li>
            <li>📊 Tổng doanh thu toàn thời gian: <strong><%= String.format("%,.0f", request.getAttribute("totalRevenue")) %> VNĐ</strong></li>
        </ul>

        <!-- Biểu đồ -->
        <canvas id="dailyChart" style="max-width: 1000px; height: 400px;"></canvas>
        <script>
            const labels = <%= request.getAttribute("labelsJson") %>;
            const data = <%= request.getAttribute("valuesJson") %>;

            new Chart(document.getElementById("dailyChart"), {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                            label: 'Doanh thu (VND)',
                            data: data,
                            borderColor: 'rgb(75, 192, 192)',
                            backgroundColor: 'rgba(75, 192, 192, 0.2)',
                            tension: 0.3,
                            fill: true
                        }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true,
                            title: {display: true, text: "VNĐ"}
                        }
                    }
                }
            });
        </script>

        <!-- Bảng chi tiết -->
        <h3>📋 Bảng doanh thu từng ngày</h3>
        <table>
            <thead>
                <tr>
                    <th>Ngày</th>
                    <th>Doanh thu</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<String> days = new com.google.gson.Gson().fromJson((String) request.getAttribute("labelsJson"), List.class);
                    List<Double> values = new com.google.gson.Gson().fromJson((String) request.getAttribute("valuesJson"), List.class);
                    for (int i = 0; i < days.size(); i++) {
                %>
                <tr>
                    <td><%= days.get(i) %></td>
                    <td><%= String.format("%,.0f", values.get(i)) %> VNĐ</td>
                </tr>
                <% } %>
            </tbody>
        </table>

    </body>
</html>

