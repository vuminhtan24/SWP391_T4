<%-- 
    Document   : revenueAllTime
    Created on : Jun 18, 2025, 3:21:45 PM
    Author     : LAPTOP
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List, com.google.gson.Gson" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Doanh thu toàn thời gian</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            body {
                font-family: Arial;
                padding: 20px;
            }
            form {
                margin-bottom: 30px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
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

        <h2>📊 Doanh thu toàn thời gian (hoặc lọc theo khoảng)</h2>

        <!-- 🔽 Form chọn khoảng thời gian -->
        <form action="${pageContext.request.contextPath}/revenuealltimeservlet" method="get">
            Từ: <input type="date" name="from" value="<%= request.getAttribute("fromDate") != null ? request.getAttribute("fromDate") : "" %>">
            Đến: <input type="date" name="to" value="<%= request.getAttribute("toDate") != null ? request.getAttribute("toDate") : "" %>">
            <input type="submit" value="Lọc">
        </form>

        <!-- Biểu đồ doanh thu -->
        <canvas id="allTimeChart" style="max-width: 1000px; height: 400px;"></canvas>
        <script>
            const labels = <%= request.getAttribute("labelsJson") %>;
            const values = <%= request.getAttribute("valuesJson") %>;

            new Chart(document.getElementById("allTimeChart"), {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                            label: 'Doanh thu (VND)',
                            data: values,
                            borderColor: 'rgb(75, 192, 192)',
                            backgroundColor: 'rgba(75, 192, 192, 0.2)',
                            fill: true,
                            tension: 0.3
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
