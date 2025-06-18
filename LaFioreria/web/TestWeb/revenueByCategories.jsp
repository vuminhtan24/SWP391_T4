<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Category" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List, com.google.gson.Gson" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Doanh thu theo loại hoa (Chọn động)</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    </head>
    <body>

        <h2>📊 Doanh thu các loại hoa (tháng hiện tại)</h2>

        <form method="get" action="revenuebycategoriesservlet">
            <label>Chọn loại hoa:</label><br>
            <c:forEach var="cat" items="${categoryList}">
                <c:set var="checked" value="false" />
                <c:forEach var="selected" items="${paramValues.cid}">
                    <c:if test="${selected == cat.categoryId}">
                        <c:set var="checked" value="true" />
                    </c:if>
                </c:forEach>
                <input type="checkbox" name="cid" value="${cat.categoryId}"
                       <c:if test="${checked}">checked</c:if> />
                ${cat.categoryName} <br>

            </c:forEach>
            <input type="submit" value="Xem biểu đồ">
        </form>

        <%-- Chỉ hiển thị biểu đồ khi có dữ liệu --%>
        <c:if test="${not empty labelsJson}">
            <canvas id="chart" style="max-width: 1000px; height: 400px;"></canvas>
            <script>
                const labels = <%= request.getAttribute("labelsJson") %>;
                const datasets = [];

                <c:forEach var="cat" items="${categoryList}">
                    <c:set var="checked" value="false" />
                    <c:forEach var="selected" items="${paramValues.cid}">
                        <c:if test="${selected == cat.categoryId}">
                            <c:set var="checked" value="true" />
                        </c:if>
                    </c:forEach>

                    <c:if test="${checked}">
                        <c:set var="key" value="${cat.categoryName}_data" />
                datasets.push({
                    label: "${cat.categoryName}",
                    data: ${requestScope[key]},
                    fill: true,
                    tension: 0.3,
                    borderWidth: 2,
                    backgroundColor: 'rgba(' + Math.floor(Math.random() * 255) + ',' +
                            Math.floor(Math.random() * 255) + ',' +
                            Math.floor(Math.random() * 255) + ', 0.2)',
                    borderColor: 'rgb(' + Math.floor(Math.random() * 255) + ',' +
                            Math.floor(Math.random() * 255) + ',' +
                            Math.floor(Math.random() * 255) + ')'
                });

                    </c:if>
                </c:forEach>



                new Chart(document.getElementById("chart"), {
                    type: 'line',
                    data: {
                        labels: labels,
                        datasets: datasets
                    },
                    options: {
                        responsive: true,
                        scales: {
                            y: {
                                beginAtZero: true,
                                title: {display: true, text: 'Doanh thu (VNĐ)'}
                            }
                        }
                    }
                });
            </script>
        </c:if>

    </body>
</html>
