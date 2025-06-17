<%-- 
    Document   : salesReport
    Created on : Jun 17, 2025, 10:44:02 AM
    Author     : LAPTOP
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.SalesRecord" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sales Report</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .stat-boxes { display: flex; gap: 20px; margin-bottom: 30px; }
        .stat { background: #f0f0f0; padding: 20px; border-radius: 10px; flex: 1; text-align: center; }
        .stat h2 { margin: 0; font-size: 24px; color: #333; }
        .stat p { margin: 5px 0 0; font-size: 16px; color: #666; }
        table { width: 100%; border-collapse: collapse; margin-top: 40px; }
        th, td { padding: 10px; border: 1px solid #ccc; text-align: center; }
        th { background-color: #f7f7f7; }
    </style>
</head>
<body>

<h1>📈 Sales Dashboard</h1>

<!-- ✅ 1. Tổng quan -->
<div class="stat-boxes">
    <div class="stat">
        <h2>$<%= request.getAttribute("todayRevenue") %></h2>
        <p>Doanh thu hôm nay</p>
    </div>
    <div class="stat">
        <h2>$<%= request.getAttribute("thisMonthRevenue") %></h2>
        <p>Doanh thu tháng này</p>
    </div>
    <div class="stat">
        <h2>$<%= request.getAttribute("totalRevenue") %></h2>
        <p>Tổng doanh thu</p>
    </div>
    <div class="stat">
        <h2><%= request.getAttribute("todayOrders") %></h2>
        <p>Đơn hàng hôm nay</p>
    </div>
</div>

<!-- ✅ 2. Biểu đồ doanh thu theo ngày -->
<canvas id="revenueChart" width="1000" height="400"></canvas>
<script>
    const labels = <%= request.getAttribute("labelsJson") %>;
    const data = <%= request.getAttribute("valuesJson") %>;

    new Chart(document.getElementById('revenueChart'), {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Doanh thu theo ngày (VND)',
                data: data,
                backgroundColor: 'rgba(75, 192, 192, 0.6)',
                borderColor: 'rgb(75, 192, 192)',
                borderWidth: 1
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

<!-- ✅ 3. Bảng danh sách đơn hàng (Recent Sales) -->
<h2>🧾 Danh sách đơn hàng gần đây</h2>
<table>
    <thead>
        <tr>
            <th>Order ID</th>
            <th>Ngày</th>
            <th>Khách hàng</th>
            <th>Sản phẩm</th>
            <th>Loại</th>
            <th>Số lượng</th>
            <th>Giá</th>
            <th>Tổng</th>
            <th>Trạng thái</th>
        </tr>
    </thead>
    <tbody>
    <%
        List<SalesRecord> list = (List<SalesRecord>) request.getAttribute("salesList");
        for (SalesRecord r : list) {
    %>
        <tr>
            <td><%= r.getOrderID() %></td>
            <td><%= r.getOrderDate() %></td>
            <td><%= r.getCustomerName() %></td>
            <td><%= r.getProductName() %></td>
            <td><%= r.getCategoryName() %></td>
            <td><%= r.getQuantity() %></td>
            <td>$<%= r.getUnitPrice() %></td>
            <td>$<%= r.getTotalPrice() %></td>
            <td><%= r.getStatus() %></td>
        </tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>

