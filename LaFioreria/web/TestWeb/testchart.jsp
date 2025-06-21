<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Test Chart</title>
    <!-- Nạp thư viện Chart.js và jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <h2>Biểu đồ Test</h2>
    
    <canvas id="worldwide-sales" style="width:100%; height:400px;"></canvas>

    <script>
        $(document).ready(function () {
            var canvas = document.getElementById("worldwide-sales");
            if (canvas) {
                var ctx = canvas.getContext("2d");
                new Chart(ctx, {
                    type: "bar",
                    data: {
                        labels: ["2016", "2017", "2018", "2019", "2020", "2021", "2022"],
                        datasets: [
                            {
                                label: "USA",
                                data: [15, 30, 55, 65, 60, 80, 95],
                                backgroundColor: "rgba(0, 156, 255, 0.7)"
                            },
                            {
                                label: "UK",
                                data: [8, 35, 40, 60, 70, 55, 75],
                                backgroundColor: "rgba(0, 156, 255, 0.5)"
                            },
                            {
                                label: "AU",
                                data: [12, 25, 45, 55, 65, 70, 60],
                                backgroundColor: "rgba(0, 156, 255, 0.3)"
                            }
                        ]
                    },
                    options: {
                        responsive: true
                    }
                });
            } else {
                console.error("Canvas not found!");
            }
        });
    </script>
</body>
</html>
