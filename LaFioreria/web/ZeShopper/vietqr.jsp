<%-- 
    Document   : vietqr
    Created on : Jun 29, 2025, 9:24:59 PM
    Author     : VU MINH TAN
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <link href="${pageContext.request.contextPath}/ZeShopper/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/responsive.css" rel="stylesheet"> 
        <title>Thanh toán VietQR</title>
    </head>
    <body>
        <jsp:include page="/ZeShopper/header.jsp"/>
        <div class="container mt-5">
            <div class="text-center">
                <h2 class="mb-4">Quét mã QR để thanh toán</h2>

                <%
                    String bankCode = "MB";
                    String accountNumber = "2628612348888";
                    String accountName = "LaFioreria";
                    int amount = Integer.parseInt(request.getParameter("amount"));
                    String orderId = request.getParameter("orderId");
                %>

                <img class="mb-4" src="https://img.vietqr.io/image/<%=bankCode%>-<%=accountNumber%>-compact2.png?amount=<%=amount%>&addInfo=ORDER<%=orderId%>&accountName=<%=accountName%>"
                     alt="QR Code chuyển khoản" width="300" />

                <form action="ConfirmVietQRPayment" method="post">
                    <input type="hidden" name="orderId" value="<%=orderId%>" />
                    <button type="submit" class="btn btn-success">Tôi đã chuyển khoản</button>
                </form>
            </div>
        </div>
        <jsp:include page="/ZeShopper/footer.jsp"/>
    </body>
</html>
