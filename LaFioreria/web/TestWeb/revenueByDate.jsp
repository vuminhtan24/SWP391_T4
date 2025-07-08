<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.google.gson.Gson" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Daily Revenue</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background: #fefefe;
            }

            .revenue-section {
                background: linear-gradient(to bottom right, #fef9f9, #f0faff);
                border-radius: 16px;
                box-shadow: 0 4px 16px rgba(0,0,0,0.05);
                padding: 2rem;
            }

            .card-title {
                color: #d63384;
                font-weight: 600;
            }

            table thead {
                background-color: #e3f2fd;
            }

            table th, table td {
                vertical-align: middle;
            }

            .highlight {
                background-color: #fff0f5;
                border-left: 4px solid #d63384;
                padding: 0.75rem 1.25rem;
                border-radius: 0.5rem;
            }
        </style>
    </head>
    <body>

        <!-- ... HEAD giữ nguyên như bạn đã có ... -->

        <!-- Phần HEAD giữ nguyên -->

        <div class="container my-4">
            <div class="revenue-section">

                <h2 class="text-center mb-4 text-primary">📆 Daily Revenue This Month</h2>

                <!-- Tổng doanh thu -->
                <div class="row mb-4">
                    <div class="col-md-6">
                        <div class="highlight">
                            📅 <strong>Today's Revenue:</strong>
                            <span class="text-success">
                                <%= String.format("%,.0f", request.getAttribute("todayRevenue")) %> VNĐ
                            </span>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="highlight">
                            📊 <strong>Total Revenue:</strong>
                            <span class="text-primary">
                                <%= String.format("%,.0f", request.getAttribute("totalRevenue")) %> VNĐ
                            </span>
                        </div>
                    </div>
                </div>

                <!-- Biểu đồ chiếm 3/4, bảng chiếm 1/4 -->
                <div class="row g-4">
                    <!-- Biểu đồ -->
                    <div class="col-lg-9">
                        <div class="card h-100">
                            <div class="card-body">
                                <h5 class="card-title text-center">📈 Revenue by Date</h5>
                                <canvas id="dailyChart" style="height:320px;"></canvas>
                            </div>
                        </div>
                    </div>

                    <!-- Bảng -->
                    <div class="col-lg-3">
                        <div class="card h-100">
                            <div class="card-body p-2">
                                <h6 class="card-title text-center mb-2">📋 Revenue Table</h6>
                                <div class="table-responsive" style="max-height: 320px; overflow-y: auto;">
                                    <table class="table table-sm table-bordered text-center mb-0">
                                        <thead class="table-light">
                                            <tr>
                                                <th>Date</th>
                                                <th>VNĐ</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                List<String> days = new Gson().fromJson((String) request.getAttribute("labelsJson"), List.class);
                                                List<Double> values = new Gson().fromJson((String) request.getAttribute("valuesJson"), List.class);
                                                for (int i = 0; i < days.size(); i++) {
                                            %>
                                            <tr>
                                                <td><%= days.get(i) %></td>
                                                <td><%= String.format("%,.0f", values.get(i)) %></td>
                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>


        <!-- Biểu đồ JS -->
        <script>
            const labels = <%= request.getAttribute("labelsJson") %>;
            const data = <%= request.getAttribute("valuesJson") %>;

            new Chart(document.getElementById("dailyChart"), {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                            label: 'Revenue (VNĐ)',
                            data: data,
                            borderColor: 'rgb(255, 99, 132)',
                            backgroundColor: 'rgba(255, 99, 132, 0.2)',
                            tension: 0.3,
                            fill: true
                        }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true,
                            title: {
                                display: true,
                                text: "VNĐ"
                            }
                        }
                    }
                }
            });
        </script>

    </body>
</html>
