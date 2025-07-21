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
                <h2 class="mb-4">Scan the QR code to pay</h2>
                <%-- Xóa các khai báo biến Java và thay bằng việc truy cập attributes từ request --%>
                <%-- Các biến này đã được đặt trong ConfirmVietQRPayment.java --%>
                <%
                    // Lấy các giá trị từ request attributes đã được servlet đặt
                    String bankCode = (String) request.getAttribute("bankCode");
                    String accountNumber = (String) request.getAttribute("accountNumber");
                    String accountName = (String) request.getAttribute("accountName");
                    // Lấy amount từ attribute, nó đã là Integer từ servlet
                    int amount = (Integer) request.getAttribute("amount"); 
                    // Lấy orderId từ attribute, nó đã là Integer từ servlet, chuyển sang String
                    String orderId = String.valueOf(request.getAttribute("orderId")); 
                %>
                
                <img class="mb-4"
                     src="https://img.vietqr.io/image/<c:out value="${bankCode}"/>-<c:out value="${accountNumber}"/>-compact2.png?amount=<c:out value="${amount}"/>&addInfo=ORDER<c:out value="${orderId}"/>&accountName=<c:out value="${accountName}"/>"
                     alt="QR Code chuyển khoản" width="300" />

                <c:if test="${not empty remainingTime}">
                    <div class="alert alert-warning mt-3">
                        Vui lòng xác nhận chuyển khoản trong vòng <span id="countdown"></span>.
                    </div>

                    <script>
                        var remainingTime = ${remainingTime};
                        var countdownEl = document.getElementById("countdown");

                        function updateCountdown() {
                            if (remainingTime <= 0) {
                                countdownEl.innerText = "Hết thời gian!";
                                const form = document.querySelector("form");
                                // Disable the button and hide the form when time runs out
                                if (form) { // Check if form exists before trying to access its elements
                                    const submitButton = form.querySelector("button[type='submit']");
                                    if (submitButton) {
                                        submitButton.disabled = true;
                                    }
                                    form.classList.add("d-none");
                                }
                                return;
                            }

                            var minutes = Math.floor(remainingTime / 60000);
                            var seconds = Math.floor((remainingTime % 60000) / 1000);
                            countdownEl.innerText = minutes + " phút " + seconds + " giây";
                            remainingTime -= 1000;
                        }

                        updateCountdown();
                        setInterval(updateCountdown, 1000);
                    </script>
                </c:if>


                <form action="ConfirmVietQRPayment" method="post">
                    <input type="hidden" name="orderId" value="<c:out value="${orderId}"/>" />
                    <button type="submit" class="btn btn-success">I have transferred</button>
                </form>
            </div>
        </div>
        <jsp:include page="/ZeShopper/footer.jsp"/>
    </body>
</html>
