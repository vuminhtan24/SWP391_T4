<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Cảm ơn vì đơn hàng của bạn</title>
    <link href="${pageContext.request.contextPath}/ZeShopper/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/ZeShopper/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/ZeShopper/css/prettyPhoto.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/ZeShopper/css/price-range.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/ZeShopper/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/ZeShopper/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/ZeShopper/css/responsive.css" rel="stylesheet">
</head>
<body>

<jsp:include page="/ZeShopper/header.jsp"/>

<div class="container mt-5">
    <div class="text-center p-5 bg-light rounded shadow">
        <h2 class="text-success mb-4">🎉 Cảm ơn bạn đã đặt hàng tại LaFioreria!</h2>

        <%
            String orderId = request.getParameter("orderId");
            if (orderId != null) {
        %>
            <p class="lead">Mã đơn hàng của bạn là: <strong>ORDER<%=orderId%></strong></p>
        <%
            }
        %>

        <p>Chúng tôi sẽ sớm liên hệ và xử lý đơn hàng của bạn.</p>
        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary mt-4">Về trang chủ</a>
    </div>
</div>

<jsp:include page="/ZeShopper/footer.jsp"/>

</body>
</html>
