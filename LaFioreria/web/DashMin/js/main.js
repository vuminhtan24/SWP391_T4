(function ($) {
    "use strict";

    // ✅ Đặt các hàm dùng chung ở đây
    window.downloadChartAsImage = function (chart, filename = 'chart.png') {
        if (!chart)
            return;
        const url = chart.toBase64Image();
        const link = document.createElement('a');
        link.href = url;
        link.download = filename;
        link.click();
    };



    $(document).ready(function () {

        // Spinner
        var spinner = function () {
            setTimeout(function () {
                if ($('#spinner').length > 0) {
                    $('#spinner').removeClass('show');
                }
            }, 1);
        };
        spinner();


        // Back to top button
        $(window).scroll(function () {
            if ($(this).scrollTop() > 300) {
                $('.back-to-top').fadeIn('slow');
            } else {
                $('.back-to-top').fadeOut('slow');
            }
        });
        $('.back-to-top').click(function () {
            $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
            return false;
        });


        // Sidebar Toggler
        $('.sidebar-toggler').click(function () {
            $('.sidebar, .content').toggleClass("open");
            return false;
        });


        // Progress Bar
        $('.pg-bar').waypoint(function () {
            $('.progress .progress-bar').each(function () {
                $(this).css("width", $(this).attr("aria-valuenow") + '%');
            });
        }, {offset: '80%'});


        // Calendar
        if ($('#calender').length) {
            $('#calender').datetimepicker({
                inline: true,
                format: 'L'
            });
        }

        // Testimonials carousel
        if ($('.testimonial-carousel').length) {
            $(".testimonial-carousel").owlCarousel({
                autoplay: true,
                smartSpeed: 1000,
                items: 1,
                dots: true,
                loop: true,
                nav: false
            });
        }

        // Worldwide Sales Chart
        if ($("#worldwide-sales").length) {
            var ctx1 = $("#worldwide-sales").get(0).getContext("2d");
            var myChart1 = new Chart(ctx1, {
                type: "bar",
                data: {
                    labels: ["2016", "2017", "2018", "2019", "2020", "2021", "2022"],
                    datasets: [
                        {
                            label: "USA",
                            data: [15, 30, 55, 65, 60, 80, 95],
                            backgroundColor: "rgba(0, 156, 255, .7)"
                        },
                        {
                            label: "UK",
                            data: [8, 35, 40, 60, 70, 55, 75],
                            backgroundColor: "rgba(0, 156, 255, .5)"
                        },
                        {
                            label: "AU",
                            data: [12, 25, 45, 55, 65, 70, 60],
                            backgroundColor: "rgba(0, 156, 255, .3)"
                        }
                    ]
                },
                options: {
                    responsive: true
                }
            });
        }
        // ✅ Hàm an toàn để parse JSON
        function safeParse(jsonStr) {
            try {
                return JSON.parse(jsonStr);
            } catch (e) {
                console.error("Lỗi khi phân tích JSON:", jsonStr, e);
                return [];
            }
        }

        // 📊 Toggle & vẽ biểu đồ cột: Lý do lỗi theo loại hoa
        let barChartDrawn = false;
        $("#toggleBarChart").click(function () {
            const $barCanvas = $("#barByCategory");

            $barCanvas.toggle();

            if (!barChartDrawn && $barCanvas.is(":visible")) {
                const labels = safeParse($barCanvas.attr("data-labels"));
                const values = safeParse($barCanvas.attr("data-values"));

                if (labels.length && values.length) {
                    window.barByCategory = new Chart($barCanvas[0], {
                        type: 'bar',
                        data: {
                            labels: labels,
                            datasets: [{
                                    label: 'Error Count',
                                    data: values,
                                    backgroundColor: '#f44336'
                                }]
                        },
                        options: {
                            responsive: true,
                            plugins: {
                                legend: {position: 'top'},
                                title: {
                                    display: true,
                                    text: '📊 Error Reasons by Flower Type'
                                }
                            },
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    title: {
                                        display: true,
                                        text: 'Quantity'
                                    }
                                }
                            }
                        }

                    });
                    barChartDrawn = true;
                }
            }
        });

        // 📈 Toggle & vẽ biểu đồ đường: Hoa hết hạn theo thời gian
        let lineChartDrawn = false;
        $("#toggleLineChart").click(function () {
            const $lineCanvas = $("#lineExpired");

            $lineCanvas.toggle();

            if (!lineChartDrawn && $lineCanvas.is(":visible")) {
                const labels = safeParse($lineCanvas.attr("data-labels"));
                const values = safeParse($lineCanvas.attr("data-values"));

                if (labels.length && values.length) {
                    window.lineExpired = new Chart($lineCanvas[0], {
                        type: 'line',
                        data: {
                            labels: labels,
                            datasets: [{
                                    label: 'Expired Flowers',
                                    data: values,
                                    borderColor: 'blue',
                                    backgroundColor: 'rgba(0, 0, 255, 0.1)',
                                    tension: 0.4,
                                    fill: true
                                }]
                        },
                        options: {
                            responsive: true,
                            plugins: {
                                legend: {position: 'top'},
                                title: {
                                    display: true,
                                    text: '📈 Expired Flowers Over Time'
                                }
                            },
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    title: {
                                        display: true,
                                        text: 'Number of Flowers'
                                    }
                                },
                                x: {
                                    title: {
                                        display: true,
                                        text: 'Time (Month)'
                                    }
                                }
                            }
                        }

                    });
                    lineChartDrawn = true;
                }
            }
        });





// Biểu đồ tỉ lệ đơn hàng theo trạng thái (Donut chart)
        if (document.getElementById("orderStatusChart")) {
            const canvas = document.getElementById('orderStatusChart');
            const ctx = canvas.getContext('2d');
            const labels = JSON.parse(canvas.getAttribute('data-labels') || "[]");
            const data = JSON.parse(canvas.getAttribute('data-values') || "[]");

            if (labels.length && data.length) {
                // ✅ Gán vào biến toàn cục để có thể tải PNG
                window.orderStatusChart = new Chart(ctx, {
                    type: 'doughnut',
                    data: {
                        labels: labels,
                        datasets: [{
                                label: 'Number of orders',
                                data: data,
                                backgroundColor: [
                                    '#4CAF50', '#FFC107', '#2196F3', '#FF5722', '#9C27B0'
                                ],
                                borderWidth: 1
                            }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            legend: {position: 'bottom'},
                            title: {
                                display: true,
                                text: 'Order Rate by Status'
                            },
                            // ✅ Nền trắng cho ảnh PNG
                            custom_canvas_background_color: {
                                color: 'white'
                            }
                        }
                    },
                    plugins: [{
                            id: 'custom_canvas_background_color',
                            beforeDraw: (chart) => {
                                const ctx = chart.canvas.getContext('2d');
                                ctx.save();
                                ctx.globalCompositeOperation = 'destination-over';
                                ctx.fillStyle = chart.config.options.plugins.custom_canvas_background_color.color || 'white';
                                ctx.fillRect(0, 0, chart.width, chart.height);
                                ctx.restore();
                            }
                        }]
                });
            } else {
                console.warn("Không có dữ liệu trạng thái đơn hàng.");
            }
        }






        if (document.getElementById("thisMonthChart")) {
            const canvas = document.getElementById("thisMonthChart");
            const labels = JSON.parse(canvas.getAttribute("data-labels"));
            const values = JSON.parse(canvas.getAttribute("data-values"));

            // ✅ Đặt vào biến toàn cục dễ nhớ
            window.thisMonthRevenueChart = new Chart(canvas, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                            label: "Revenue by day (this month)",
                            data: values,
                            backgroundColor: 'rgba(75, 192, 192, 0.5)',
                            borderColor: 'rgb(75, 192, 192)',
                            borderWidth: 1
                        }]
                },
                options: {
                    scales: {
                        y: {beginAtZero: true}
                    },
                    plugins: {
                        custom_canvas_background_color: {
                            color: 'white' // 🎯 Màu nền PNG mong muốn
                        }
                    }

                },
                plugins: [{
                        id: 'custom_canvas_background_color',
                        beforeDraw: (chart) => {
                            const ctx = chart.canvas.getContext('2d');
                            ctx.save();
                            ctx.globalCompositeOperation = 'destination-over';
                            ctx.fillStyle = chart.config.options.plugins.custom_canvas_background_color.color || 'white';
                            ctx.fillRect(0, 0, chart.width, chart.height);
                            ctx.restore();
                        }
                    }]


            });
        }


        // Sales & Revenue Chart
        if ($("#salse-revenue").length) {
            var ctx2 = $("#salse-revenue").get(0).getContext("2d");
            var myChart2 = new Chart(ctx2, {
                type: "line",
                data: {
                    labels: ["2016", "2017", "2018", "2019", "2020", "2021", "2022"],
                    datasets: [
                        {
                            label: "Salse",
                            data: [15, 30, 55, 45, 70, 65, 85],
                            backgroundColor: "rgba(0, 156, 255, .5)",
                            fill: true
                        },
                        {
                            label: "Revenue",
                            data: [99, 135, 170, 130, 190, 180, 270],
                            backgroundColor: "rgba(0, 156, 255, .3)",
                            fill: true
                        }
                    ]
                },
                options: {
                    responsive: true
                }
            });
        }

       
// ✅ Tạo biến toàn cục lưu biểu đồ theo id
        window.chartInstances = window.chartInstances || {};

        function renderChart(canvasId) {
            const ctx = document.getElementById(canvasId);
            if (!ctx)
                return;

            try {
                // ✅ Hủy biểu đồ cũ nếu đã tồn tại
                if (window.chartInstances[canvasId]) {
                    window.chartInstances[canvasId].destroy();
                }

                const parsedLabels = JSON.parse(ctx.getAttribute("data-labels"));
                const parsedRevenues = JSON.parse(ctx.getAttribute("data-revenues"));
                const parsedOrders = JSON.parse(ctx.getAttribute("data-orders"));

                const chartInstance = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: parsedLabels,
                        datasets: [
                            {
                                label: 'Revenue (VND)',
                                data: parsedRevenues,
                                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                                borderColor: 'rgb(54, 162, 235)',
                                borderWidth: 1,
                                yAxisID: 'y'
                            },
                            {
                                label: 'Order Number',
                                data: parsedOrders,
                                backgroundColor: 'rgba(255, 159, 64, 0.6)',
                                borderColor: 'rgb(255, 159, 64)',
                                borderWidth: 1,
                                yAxisID: 'y1'
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
                                beginAtZero: true,
                                title: {display: true, text: 'Revenue (VND)'}
                            },
                            y1: {
                                type: 'linear',
                                position: 'right',
                                beginAtZero: true,
                                grid: {drawOnChartArea: false},
                                title: {display: true, text: 'Order Number'}
                            }
                        },
                        plugins: {
                            custom_canvas_background_color: {
                                color: 'white'
                            }
                        }
                    },
                    plugins: [{
                            id: 'custom_canvas_background_color',
                            beforeDraw: (chart) => {
                                const ctx = chart.canvas.getContext('2d');
                                ctx.save();
                                ctx.globalCompositeOperation = 'destination-over';
                                ctx.fillStyle = chart.config.options.plugins.custom_canvas_background_color.color || 'white';
                                ctx.fillRect(0, 0, chart.width, chart.height);
                                ctx.restore();
                            }
                        }]
                });

                // ✅ Lưu lại biểu đồ mới vào biến toàn cục
                window.chartInstances[canvasId] = chartInstance;

            } catch (err) {
                console.error("Error parsing chart data for " + canvasId, err);
            }
        }


// Gọi khi DOM đã sẵn sàng
        $(document).ready(function () {
            renderChart("monthChart");
//            renderChart("yearChart");
//            renderChart("weekdayChart");
        });

// 📊 Thống kê theo tháng trong năm
        const statsChartElem = document.getElementById('statsChart');
        if (statsChartElem) {
            const labels = JSON.parse(statsChartElem.getAttribute('data-labels'));
            const revenues = JSON.parse(statsChartElem.getAttribute('data-revenues'));
            const orders = JSON.parse(statsChartElem.getAttribute('data-orders'));

            // ✅ Gán biểu đồ vào biến toàn cục để có thể gọi từ các nút (download/export)
            window.statsChart = new Chart(statsChartElem, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [
                        {
                            label: 'Revenue (VND)',
                            data: revenues,
                            backgroundColor: 'rgba(54, 162, 235, 0.6)',
                            borderColor: 'rgb(54, 162, 235)',
                            borderWidth: 1,
                            yAxisID: 'y',
                        },
                        {
                            label: 'Order Number',
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
                            title: {display: true, text: 'Revenue (VND)'},
                            beginAtZero: true
                        },
                        y1: {
                            type: 'linear',
                            position: 'right',
                            title: {display: true, text: 'Order Number'},
                            grid: {drawOnChartArea: false},
                            beginAtZero: true
                        }
                    },
                    plugins: {
                        custom_canvas_background_color: {
                            color: 'white' // 🎯 Màu nền PNG mong muốn
                        }
                    }
                },
                plugins: [{
                        id: 'custom_canvas_background_color',
                        beforeDraw: (chart) => {
                            const ctx = chart.canvas.getContext('2d');
                            ctx.save();
                            ctx.globalCompositeOperation = 'destination-over';
                            ctx.fillStyle = chart.config.options.plugins.custom_canvas_background_color.color || 'white';
                            ctx.fillRect(0, 0, chart.width, chart.height);
                            ctx.restore();
                        }
                    }]

            });
        }


// Biểu đồ: Doanh thu & số đơn theo từng năm
        const yearChartElem = document.getElementById('yearChart');
        if (yearChartElem) {
            const labels = JSON.parse(yearChartElem.getAttribute('data-labels'));
            const revenues = JSON.parse(yearChartElem.getAttribute('data-revenues'));
            const orders = JSON.parse(yearChartElem.getAttribute('data-orders'));

            // ✅ Gán vào biến toàn cục để tải PNG
            window.yearStatsChart = new Chart(yearChartElem, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [
                        {
                            label: 'Revenue (VND)',
                            data: revenues,
                            backgroundColor: 'rgba(75, 192, 192, 0.5)',
                            borderColor: 'rgb(75, 192, 192)',
                            borderWidth: 1,
                            yAxisID: 'y'
                        },
                        {
                            label: 'Order Number',
                            data: orders,
                            backgroundColor: 'rgba(255, 159, 64, 0.6)',
                            borderColor: 'rgb(255, 159, 64)',
                            borderWidth: 1,
                            yAxisID: 'y1'
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
                            beginAtZero: true,
                            title: {display: true, text: 'Revenue (VND)'}
                        },
                        y1: {
                            type: 'linear',
                            position: 'right',
                            beginAtZero: true,
                            grid: {drawOnChartArea: false},
                            title: {display: true, text: 'Order Number'}
                        }
                    },
                    plugins: {
                        custom_canvas_background_color: {
                            color: 'white' // 🎯 Màu nền PNG mong muốn
                        }
                    }
                },
                plugins: [{
                        id: 'custom_canvas_background_color',
                        beforeDraw: (chart) => {
                            const ctx = chart.canvas.getContext('2d');
                            ctx.save();
                            ctx.globalCompositeOperation = 'destination-over';
                            ctx.fillStyle = chart.config.options.plugins.custom_canvas_background_color.color || 'white';
                            ctx.fillRect(0, 0, chart.width, chart.height);
                            ctx.restore();
                        }
                    }]
            });
        }


// Biểu đồ: Doanh thu & số đơn theo thứ trong tuần
        const weekdayChartElem = document.getElementById('weekdayChart');
        if (weekdayChartElem) {
            const labels = JSON.parse(weekdayChartElem.getAttribute('data-labels'));
            const revenues = JSON.parse(weekdayChartElem.getAttribute('data-revenues'));
            const orders = JSON.parse(weekdayChartElem.getAttribute('data-orders'));

            // ✅ Gán vào biến toàn cục để dùng nút tải PNG
            window.weekdayStatsChart = new Chart(weekdayChartElem, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [
                        {
                            label: 'Revenue (VND)',
                            data: revenues,
                            backgroundColor: 'rgba(153, 102, 255, 0.6)',
                            borderColor: 'rgb(153, 102, 255)',
                            borderWidth: 1,
                            yAxisID: 'y'
                        },
                        {
                            label: 'Order Number',
                            data: orders,
                            backgroundColor: 'rgba(255, 205, 86, 0.6)',
                            borderColor: 'rgb(255, 205, 86)',
                            borderWidth: 1,
                            yAxisID: 'y1'
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
                            beginAtZero: true,
                            title: {display: true, text: 'Revenue (VND)'}
                        },
                        y1: {
                            type: 'linear',
                            position: 'right',
                            beginAtZero: true,
                            grid: {drawOnChartArea: false},
                            title: {display: true, text: 'Order Number'}
                        }
                    },
                    plugins: {
                        custom_canvas_background_color: {
                            color: 'white' // 🎯 Màu nền PNG mong muốn
                        }
                    }
                },
                plugins: [{
                        id: 'custom_canvas_background_color',
                        beforeDraw: (chart) => {
                            const ctx = chart.canvas.getContext('2d');
                            ctx.save();
                            ctx.globalCompositeOperation = 'destination-over';
                            ctx.fillStyle = chart.config.options.plugins.custom_canvas_background_color.color || 'white';
                            ctx.fillRect(0, 0, chart.width, chart.height);
                            ctx.restore();
                        }
                    }]
            });
        }


        // Line Chart
        if ($("#line-chart").length) {
            var ctx3 = $("#line-chart").get(0).getContext("2d");
            var myChart3 = new Chart(ctx3, {
                type: "line",
                data: {
                    labels: [50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150],
                    datasets: [{
                            label: "Salse",
                            fill: false,
                            backgroundColor: "rgba(0, 156, 255, .3)",
                            data: [7, 8, 8, 9, 9, 9, 10, 11, 14, 14, 15]
                        }]
                },
                options: {
                    responsive: true
                }
            });
        }

        // Bar Chart
        if ($("#bar-chart").length) {
            var ctx4 = $("#bar-chart").get(0).getContext("2d");
            var myChart4 = new Chart(ctx4, {
                type: "bar",
                data: {
                    labels: ["Italy", "France", "Spain", "USA", "Argentina"],
                    datasets: [{
                            backgroundColor: [
                                "rgba(0, 156, 255, .7)",
                                "rgba(0, 156, 255, .6)",
                                "rgba(0, 156, 255, .5)",
                                "rgba(0, 156, 255, .4)",
                                "rgba(0, 156, 255, .3)"
                            ],
                            data: [55, 49, 44, 24, 15]
                        }]
                },
                options: {
                    responsive: true
                }
            });
        }

        // Pie Chart
        if ($("#pie-chart").length) {
            var ctx5 = $("#pie-chart").get(0).getContext("2d");
            var myChart5 = new Chart(ctx5, {
                type: "pie",
                data: {
                    labels: ["Italy", "France", "Spain", "USA", "Argentina"],
                    datasets: [{
                            backgroundColor: [
                                "rgba(0, 156, 255, .7)",
                                "rgba(0, 156, 255, .6)",
                                "rgba(0, 156, 255, .5)",
                                "rgba(0, 156, 255, .4)",
                                "rgba(0, 156, 255, .3)"
                            ],
                            data: [55, 49, 44, 24, 15]
                        }]
                },
                options: {
                    responsive: true
                }
            });
        }

        // Doughnut Chart
        if ($("#doughnut-chart").length) {
            var ctx6 = $("#doughnut-chart").get(0).getContext("2d");
            var myChart6 = new Chart(ctx6, {
                type: "doughnut",
                data: {
                    labels: ["Italy", "France", "Spain", "USA", "Argentina"],
                    datasets: [{
                            backgroundColor: [
                                "rgba(0, 156, 255, .7)",
                                "rgba(0, 156, 255, .6)",
                                "rgba(0, 156, 255, .5)",
                                "rgba(0, 156, 255, .4)",
                                "rgba(0, 156, 255, .3)"
                            ],
                            data: [55, 49, 44, 24, 15]
                        }]
                },
                options: {
                    responsive: true
                }
            });
        }
// Biểu đồ doanh thu loại hoa theo ngày trong tháng
        if (document.getElementById("categoryRevenueChart")) {
            const chart = document.getElementById("categoryRevenueChart");
            const labels = JSON.parse(chart.getAttribute("data-labels") || "[]");
            const categories = JSON.parse(chart.getAttribute("data-categories") || "[]");

            const datasets = [];

            categories.forEach(cat => {
                const key = "data-data_" + cat.categoryName;
                const raw = chart.getAttribute(key);
                if (raw) {
                    const values = JSON.parse(raw);

                    const r = Math.floor(Math.random() * 200 + 30);
                    const g = Math.floor(Math.random() * 200 + 30);
                    const b = Math.floor(Math.random() * 200 + 30);

                    datasets.push({
                        label: cat.categoryName,
                        data: values,
                        fill: true,
                        tension: 0.3,
                        borderWidth: 2,
                        backgroundColor: `rgba(${r},${g},${b},0.2)`,
                        borderColor: `rgb(${r},${g},${b})`
                    });
                }
            });

            if (datasets.length > 0) {
                // ✅ Gán biểu đồ vào biến toàn cục để có thể tải PNG
                window.categoryRevenueChart = new Chart(chart, {
                    type: 'line',
                    data: {
                        labels: labels,
                        datasets: datasets
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            title: {
                                display: true,
                                text: 'Revenue by flower type (this month)'
                            }
                        },
                        interaction: {
                            mode: 'index',
                            intersect: false
                        },
                        scales: {
                            y: {
                                beginAtZero: true,
                                title: {
                                    display: true,
                                    text: 'Revenue (VND)'
                                }
                            }
                        },
                        plugins: {
                            custom_canvas_background_color: {
                                color: 'white' // 🎯 Màu nền PNG mong muốn
                            }
                        }
                    },
                    plugins: [{
                            id: 'custom_canvas_background_color',
                            beforeDraw: (chart) => {
                                const ctx = chart.canvas.getContext('2d');
                                ctx.save();
                                ctx.globalCompositeOperation = 'destination-over';
                                ctx.fillStyle = chart.config.options.plugins.custom_canvas_background_color.color || 'white';
                                ctx.fillRect(0, 0, chart.width, chart.height);
                                ctx.restore();
                            }
                        }]
                });
            }
        }


        // Chart: Doanh thu toàn thời gian
        if (document.getElementById("allTimeChart")) {
            const labels = JSON.parse(document.getElementById("allTimeChart").getAttribute("data-labels"));
            const values = JSON.parse(document.getElementById("allTimeChart").getAttribute("data-values"));

            window.allTimeChart = new Chart(document.getElementById("allTimeChart"), {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                            label: 'Revenue (VND)',
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
                            title: {
                                display: true,
                                text: 'VNĐ'
                            }
                        }
                    },
                    plugins: {
                        custom_canvas_background_color: {
                            color: 'white'
                        }
                    }
                },
                plugins: [{
                        id: 'custom_canvas_background_color',
                        beforeDraw: (chart) => {
                            const ctx = chart.canvas.getContext('2d');
                            ctx.save();
                            ctx.globalCompositeOperation = 'destination-over';
                            ctx.fillStyle = chart.config.options.plugins.custom_canvas_background_color.color || 'white';
                            ctx.fillRect(0, 0, chart.width, chart.height);
                            ctx.restore();
                        }
                    }]
            });
        }

        // Nút tải biểu đồ PNG
        window.downloadChartImage = function () {
            if (window.allTimeChart) {
                const imageUrl = window.allTimeChart.toBase64Image();
                const link = document.createElement('a');
                link.href = imageUrl;
                link.download = 'doanhthu_toanthoigian.png';
                link.click();
            }
        };

    });

})(jQuery);
