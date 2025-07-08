<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List, com.google.gson.Gson" %>
<!DOCTYPE html>
<html>
    <head>
        <title>All-Time Revenue</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background: #fefefe;
            }

            .revenue-section {
                background: linear-gradient(to bottom right, #fff6f9, #f0faff);
                border-radius: 1rem;
                box-shadow: 0 4px 12px rgba(0,0,0,0.05);
                padding: 2rem;
            }

            .table-responsive {
                max-height: 400px;
                overflow-y: auto;
            }

            .chart-container {
                height: 400px;
            }
        </style>
    </head>
    <body>

        <div class="container my-5">
            <div class="revenue-section">
                <h2 class="text-center text-primary mb-4">📊 All-Time Revenue (Or Filter by Date Range)</h2>

                <!-- 🔽 Form lọc -->
                <form action="${pageContext.request.contextPath}/revenuealltimeservlet" method="get" class="row g-3 mb-4 justify-content-center">
                    <div class="col-md-3">
                        <label class="form-label">From</label>
                        <input type="date" name="from" class="form-control"
                               value="<%= request.getAttribute("fromDate") != null ? request.getAttribute("fromDate") : "" %>">
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">To</label>
                        <input type="date" name="to" class="form-control"
                               value="<%= request.getAttribute("toDate") != null ? request.getAttribute("toDate") : "" %>">
                    </div>
                    <div class="col-md-2 d-flex align-items-end">
                        <button type="submit" class="btn btn-outline-primary w-100">Filter</button>
                    </div>
                </form>

                <!-- Biểu đồ + bảng -->
                <div class="row g-4">
                    <!-- Biểu đồ -->
                    <div class="col-lg-9">
                        <div class="card h-100">
                            <div class="card-body">
                                <h5 class="card-title text-center">📈 Revenue Chart</h5>
                                <canvas id="allTimeChart" class="chart-container"></canvas>
                                <div class="text-end mt-3">
                                    <button onclick="downloadChartImage()" class="btn btn-sm btn-outline-success">
                                        📥 Download PNG
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Bảng -->
                    <div class="col-lg-3">
                        <div class="card h-100">
                            <div class="card-body">
                                <h6 class="card-title text-center">📋 Revenue Table</h6>
                                <div class="table-responsive mt-3">
                                    <table class="table table-sm table-bordered table-hover text-center mb-0">
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
            const values = <%= request.getAttribute("valuesJson") %>;

            const allTimeChart = new Chart(document.getElementById("allTimeChart"), {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                            label: 'Revenue (VNĐ)',
                            data: values,
                            borderColor: 'rgb(75, 192, 192)',
                            backgroundColor: 'rgba(75, 192, 192, 0.2)',
                            tension: 0.3,
                            fill: true
                        }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {position: 'top'},
                        title: {
                            display: false
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            title: {display: true, text: 'VNĐ'}
                        }
                    }
                },
                plugins: [{
                        id: 'custom_canvas_background_color',
                        beforeDraw: (chart) => {
                            const ctx = chart.canvas.getContext('2d');
                            ctx.save();
                            ctx.globalCompositeOperation = 'destination-over';
                            ctx.fillStyle = 'white';
                            ctx.fillRect(0, 0, chart.width, chart.height);
                            ctx.restore();
                        }
                    }]
            });

            function downloadChartImage() {
                const imageUrl = allTimeChart.toBase64Image();
                const link = document.createElement('a');
                link.href = imageUrl;
                link.download = 'revenue_all_time.png';
                link.click();
            }
        </script>

    </body>
</html>
