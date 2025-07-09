<%-- 
    Document   : feedback-form
    Created on : Jul 09, 2025
    Author     : xAI (via Grok)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Viết Đánh Giá | E-Shopper</title>
        <link href="${pageContext.request.contextPath}/ZeShopper/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/responsive.css" rel="stylesheet">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/ZeShopper/images/ico/favicon.ico">
        <style>
            .feedback-form {
                max-width: 600px;
                margin: 20px auto;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 4px;
                background-color: #f9f9f9;
            }
            .feedback-form h2 {
                margin-bottom: 20px;
                text-align: center;
            }
            .form-group {
                margin-bottom: 15px;
            }
            .form-group label {
                font-weight: bold;
            }
            .form-group input, .form-group textarea {
                width: 100%;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }
            .form-group textarea {
                height: 100px;
                resize: vertical;
            }
            .error-message {
                color: red;
                margin-bottom: 15px;
                text-align: center;
            }
            .submit-button {
                background-color: #5cb85c;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
            }
            .submit-button:hover {
                background-color: #4cae4c;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/ZeShopper/header.jsp"/>

        <div class="container">
            <div class="feedback-form">
                <h2>Viết Đánh Giá Sản Phẩm</h2>
                <c:if test="${not empty error}">
                    <div class="error-message">${error}</div>
                </c:if>
                <c:if test="${not empty message}">
                    <div class="alert alert-success">${message}</div>
                </c:if>
                <form action="${pageContext.request.contextPath}/ZeShopper/feedback" method="post">
                    <input type="hidden" name="action" value="submitFeedback">
                    <input type="hidden" name="orderId" value="${orderId}">
                    <input type="hidden" name="bouquetId" value="${bouquetId}">
                    <div class="form-group">
                        <label for="rating">Đánh giá (1-5 sao):</label>
                        <input type="number" id="rating" name="rating" min="1" max="5" required>
                    </div>
                    <div class="form-group">
                        <label for="comment">Nhận xét:</label>
                        <textarea id="comment" name="comment" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="images">Hình ảnh (tùy chọn):</label>
                        <input type="file" id="images" name="imageUrls" multiple accept="image/*">
                    </div>
                    <button type="submit" class="submit-button">Gửi đánh giá</button>
                </form>
            </div>
        </div>

        <jsp:include page="/ZeShopper/footer.jsp"/>
    </body>
</html>