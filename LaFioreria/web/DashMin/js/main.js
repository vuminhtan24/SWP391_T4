(function ($) {
    "use strict";

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

// Biểu đồ tỉ lệ đơn hàng theo trạng thái (Donut chart)
        if ($("#orderStatusChart").length) {
            const canvas = document.getElementById('orderStatusChart');
            const ctx = canvas.getContext('2d');
            const labels = JSON.parse(canvas.getAttribute('data-labels') || "[]");
            const data = JSON.parse(canvas.getAttribute('data-values') || "[]");

            if (labels.length && data.length) {
                new Chart(ctx, {
                    type: 'doughnut',
                    data: {
                        labels: labels,
                        datasets: [{
                                label: 'Số lượng đơn hàng',
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
                                text: 'Tỉ lệ đơn hàng theo trạng thái'
                            }
                        }
                    }
                });
            } else {
                console.warn("Không có dữ liệu trạng thái đơn hàng.");
            }
        }




        if (document.getElementById("thisMonthChart")) {
            const labels = JSON.parse(document.getElementById("thisMonthChart").getAttribute("data-labels"));
            const values = JSON.parse(document.getElementById("thisMonthChart").getAttribute("data-values"));

            new Chart(document.getElementById("thisMonthChart"), {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                            label: "Doanh thu theo ngày (tháng này)",
                            data: values,
                            backgroundColor: 'rgba(75, 192, 192, 0.5)',
                            borderColor: 'rgb(75, 192, 192)',
                            borderWidth: 1
                        }]
                },
                options: {
                    scales: {
                        y: {beginAtZero: true}
                    }
                }
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

        function renderChart(canvasId, labels, revenues, orders) {
            const ctx = document.getElementById(canvasId);
            if (!ctx)
                return;

            try {
                const parsedLabels = JSON.parse(ctx.getAttribute("data-labels"));
                const parsedRevenues = JSON.parse(ctx.getAttribute("data-revenues"));
                const parsedOrders = JSON.parse(ctx.getAttribute("data-orders"));

                new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: parsedLabels,
                        datasets: [
                            {
                                label: 'Doanh thu (VND)',
                                data: parsedRevenues,
                                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                                borderColor: 'rgb(54, 162, 235)',
                                borderWidth: 1,
                                yAxisID: 'y'
                            },
                            {
                                label: 'Số đơn hàng',
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
            } catch (err) {
                console.error("Error parsing chart data for " + canvasId, err);
            }
        }

// Gọi khi DOM đã sẵn sàng
        $(document).ready(function () {
            renderChart("monthChart");
            renderChart("yearChart");
            renderChart("weekdayChart");
        });

        // 📊 Thống kê theo tháng trong năm
        if (document.getElementById('statsChart')) {
            const labels = JSON.parse(document.getElementById('statsChart').getAttribute('data-labels'));
            const revenues = JSON.parse(document.getElementById('statsChart').getAttribute('data-revenues'));
            const orders = JSON.parse(document.getElementById('statsChart').getAttribute('data-orders'));

            new Chart(document.getElementById('statsChart'), {
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
                            yAxisID: 'y',
                        },
                        {
                            label: 'Số đơn hàng',
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
                            title: {display: true, text: 'Doanh thu (VND)'},
                            beginAtZero: true
                        },
                        y1: {
                            type: 'linear',
                            position: 'right',
                            title: {display: true, text: 'Số đơn hàng'},
                            grid: {drawOnChartArea: false},
                            beginAtZero: true
                        }
                    }
                }
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
                new Chart(chart, {
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
                                text: 'Doanh thu theo loại hoa (tháng này)'
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
                                    text: 'Doanh thu (VNĐ)'
                                }
                            }
                        }
                    }
                });
            }
        }



    });

})(jQuery);
