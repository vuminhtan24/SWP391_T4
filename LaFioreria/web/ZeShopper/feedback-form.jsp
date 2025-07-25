<%-- 
    Document   : feedback-form
    Created on : Jul 09, 2025
    Author     : Admin
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
            .form-group input[type="text"], .form-group textarea {
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
            .star-rating {
                direction: rtl;
                font-size: 30px;
                unicode-bidi: bidi-override;
                text-align: left;
                display: inline-block;
            }
            .star-rating input {
                display: none;
            }
            .star-rating label {
                color: #ccc;
                cursor: pointer;
            }
            .star-rating input:checked ~ label,
            .star-rating label:hover,
            .star-rating label:hover ~ label {
                color: #fc0;
            }
            .star-rating input:checked + label {
                color: #fc0;
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
                <form action="${pageContext.request.contextPath}/ZeShopper/feedback"
                      method="post"
                      enctype="multipart/form-data"
                      onsubmit="return validateComment()">

                    <input type="hidden" name="action" value="submitFeedback">
                    <input type="hidden" name="orderId" value="${orderId}">
                    <input type="hidden" name="bouquetId" value="${bouquetId}">

                    <div class="form-group">
                        <label>Đánh giá:</label>
                        <div class="star-rating">
                            <input type="radio" id="star5" name="rating" value="5" required <c:if test="${param.rating == '5'}">checked</c:if>><label for="star5">★</label>
                            <input type="radio" id="star4" name="rating" value="4" <c:if test="${param.rating == '4'}">checked</c:if>><label for="star4">★</label>
                            <input type="radio" id="star3" name="rating" value="3" <c:if test="${param.rating == '3'}">checked</c:if>><label for="star3">★</label>
                            <input type="radio" id="star2" name="rating" value="2" <c:if test="${param.rating == '2'}">checked</c:if>><label for="star2">★</label>
                            <input type="radio" id="star1" name="rating" value="1" <c:if test="${param.rating == '1'}">checked</c:if>><label for="star1">★</label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="comment">Nhận xét:</label>
                        <textarea id="comment" name="comment" required oninput="updateCharCount()" maxlength="300">${param.comment}</textarea>
                        <small id="wordCount" style="display:block; margin-top: 5px; color: #666;">Số ký tự: 0/300</small>
                        <div id="errorMessage" style="color: red; display: none;"></div>
                    </div>

                    <div class="form-group">
                        <label for="images">Hình ảnh (tối đa 3 ảnh, định dạng .jpg, .jpeg, .png):</label>
                        <input type="file" id="images" name="imageFiles" multiple accept=".jpg,.jpeg,.png">
                        <small class="form-text text-muted">Bạn chỉ có thể upload tối đa 3 ảnh.</small>
                    </div>

                    <button type="submit" class="submit-button">Gửi đánh giá</button>
                </form>
            </div>
        </div>

        <jsp:include page="/ZeShopper/footer.jsp"/>
        <script>
            function updateCharCount() {
                const comment = document.getElementById('comment').value;
                const charCount = comment.length;
                const wordCountElement = document.getElementById('wordCount');
                wordCountElement.textContent = `Số ký tự: ${charCount}/300`;
                wordCountElement.style.color = charCount > 300 ? 'red' : '#666';
            }

            function validateComment() {
                const comment = document.getElementById('comment').value;
                const errorMessage = document.getElementById('errorMessage');

                if (!comment.trim()) {
                    errorMessage.textContent = "Vui lòng nhập nhận xét.";
                    errorMessage.style.display = 'block';
                    return false;
                }

                if (comment.length > 300) {
                    errorMessage.textContent = "Nhận xét không được vượt quá 300 ký tự (kể cả khoảng trắng).";
                    errorMessage.style.display = 'block';
                    return false;
                }

                errorMessage.style.display = 'none';
                return true;
            }

            // Cập nhật số ký tự ban đầu khi tải trang
            window.onload = function() {
                updateCharCount();
            };
        </script>
    </body>
</html>