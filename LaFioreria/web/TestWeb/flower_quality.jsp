<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    User acc = (User) session.getAttribute("currentAcc");
    if (acc == null || acc.getRole() != 1) {
        response.sendRedirect(request.getContextPath() + "/ZeShopper/LoginServlet");
        return;
    }

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
    if (pieLabels.length() > 0) pieLabels.setLength(pieLabels.length() - 1);
    if (pieData.length() > 0) pieData.setLength(pieData.length() - 1);
    if (barLabels.length() > 0) barLabels.setLength(barLabels.length() - 1);
    if (barData.length() > 0) barData.setLength(barData.length() - 1);
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Flower Quality Chart</title>

        <!-- Bootstrap CSS -->
        <link href="<c:url value='/DashMin/css/bootstrap.min.css'/>" rel="stylesheet" />

        <!-- Optional Icons (minimal set) -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet" />

        <!-- Chart.js CDN -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

        <style>
            .chart-container {
                width: 100%;
                max-width: 350px;
                height: 250px;
                margin: 20px auto;
            }
        </style>
    </head>
    <body class="bg-light">
        <div class="container py-4">
            <div class="row g-4">
                <!-- Pie Chart -->
                <a href="${pageContext.request.contextPath}/flowerqualitystatsservlet" class="btn btn-outline-success">
                    🌼 Quality Flower State
                </a>
                <div class="col-md-6">
                    <div class="bg-white rounded shadow-sm p-3">
                        <h5 class="text-center">Flower Loss Rate (Pie Chart)</h5>
                        <div class="chart-container">
                            <canvas id="pieChart"></canvas>
                        </div>
                    </div>
                </div>

                <!-- Bar Chart -->
                <div class="col-md-6">
                    <div class="bg-white rounded shadow-sm p-3">
                        <h5 class="text-center">Flowers Damaged by Reason (Bar Chart)</h5>
                        <div class="chart-container">
                            <canvas id="barChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Chart Render Script -->
        <script>
            new Chart(document.getElementById("pieChart"), {
                type: "pie",
                data: {
                    labels: [<%= pieLabels.toString() %>],
                    datasets: [{
                            label: "Flower ratio",
                            data: [<%= pieData.toString() %>],
                            backgroundColor: ["#4caf50", "#f44336", "#ff9800", "#9c27b0"]
                        }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false
                }
            });

            new Chart(document.getElementById("barChart"), {
                type: "bar",
                data: {
                    labels: [<%= barLabels.toString() %>],
                    datasets: [{
                            label: "Number of eliminations",
                            data: [<%= barData.toString() %>],
                            backgroundColor: "#f44336"
                        }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    indexAxis: "y",
                    scales: {
                        x: {beginAtZero: true}
                    }
                }
            });
        </script>
    </body>
</html>
