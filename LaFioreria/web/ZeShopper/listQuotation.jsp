<%-- 
    Document   : listQuotation
    Created on : Jul 18, 2025, 1:22:56 AM
    Author     : ADMIN
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
    <head>
        <title>WholeSale Request</title>
        <link href="${pageContext.request.contextPath}/ZeShopper/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/responsive.css" rel="stylesheet">

        <style>
            /* Header cam */
            .table thead {
                background-color: #ff9800;
                color: white;
            }

            /* Căn giữa toàn bộ nội dung */
            .table th,
            .table td {
                vertical-align: middle;
                text-align: center;
            }

            /* Ghi đè sau để chắc chắn */
            .table td.note-cell {
                text-align: left;
                padding-left: 12px;
                max-width: 250px;
                overflow: auto;
                white-space: pre-wrap;
                word-wrap: break-word;
            }

            /* Tên giỏ + mô tả (nếu thêm sau) */
            .bouquet-name {
                font-weight: 600;
                font-size: 16px;
                color: #333;
            }

            /* Ảnh thu nhỏ */
            .table img {
                width: 60px;
                height: 60px;
                object-fit: cover;
                border-radius: 6px;
            }

            /* Nút Update */
            .btn-update {
                background-color: #f0f0f0;
                color: #333;
                border: 1px solid #ccc;
                padding: 4px 10px;
                border-radius: 4px;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .btn-update:hover {
                background-color: #ddd;
            }

            /* Nút Delete */
            .btn-delete {
                background-color: #e74c3c;
                color: #fff;
                border: none;
                padding: 5px 10px;
                border-radius: 4px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .btn-delete:hover {
                background-color: #c0392b;
            }

            .btn-update {
                background-color: #ff9800;
                color: white;
                border: none;
                padding: 6px 12px;
                border-radius: 4px;
                font-size: 14px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .btn-update:hover {
                background-color: #e68900;
            }

            /* Nút Delete: màu đỏ */
            .btn-delete {
                background-color: #e74c3c;
                color: white;
                border: none;
                padding: 6px 12px;
                border-radius: 4px;
                font-size: 14px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .btn-delete:hover {
                background-color: #c0392b;
            }

            /* Tổng thể form */
            .filter-form {
                background-color: #f9f9f9;
                padding: 16px 24px;
                border-radius: 8px;
                border: 1px solid #ddd;
                max-width: 600px;
                margin: 20px auto 20px 0;
                box-shadow: 0 2px 8px rgba(0,0,0,0.05);
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            /* Hàng chứa các filter */
            .filter-row {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                margin-bottom: 16px;
            }

            /* Mỗi filter item */
            .filter-item {
                flex: 1 1 250px;
                display: flex;
                flex-direction: column;
            }

            /* Label */
            .filter-item label {
                font-weight: 600;
                margin-bottom: 6px;
                color: #333;
            }

            /* Input và select */
            .filter-item input,
            .filter-item select {
                padding: 8px 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 14px;
                background-color: white;
                color: #333;
            }

            /* Hàng chứa button */
            .button-row {
                display: flex;
                gap: 12px;
            }

            /* Nút Filter */
            .button-row button[type="submit"] {
                background-color: #198754;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                font-weight: 600;
                transition: background-color 0.3s ease;
            }

            .button-row button[type="submit"]:hover {
                background-color: #157347;
            }

            /* Nút Reset (trong <a>) */
            .button-row a button[type="button"] {
                background-color: #6c757d;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                font-weight: 600;
                transition: background-color 0.3s ease;
            }

            .button-row a button[type="button"]:hover {
                background-color: #5c636a;
            }

        </style>

    </head>
    <body>

        <jsp:include page="/ZeShopper/header.jsp"/>

        <div class="container mt-5">
            <c:choose>
                <c:when test="${param.confirmed eq 'true'}">
                    <div class="alert alert-success text-center p-5 bg-light rounded shadow">
                        <h4>Bạn đã gửi yêu cầu thành công!</h4>
                        <p>Vui lòng đợi chúng tôi phản hồi báo giá.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <form action="listQuotation" method="get" class="filter-form">
                        <div class="filter-row">
                            <!-- Request Date -->
                            <div class="filter-item">
                                <label for="requestDate">Request Date:</label>
                                <input type="date" 
                                       name="requestDate" 
                                       id="requestDate"
                                       value="${param.requestDate != null ? param.requestDate : ''}" />
                            </div>

                            <!-- Status Dropdown -->
                            <div class="filter-item">
                                <label for="status">Status:</label>
                                <select name="status" id="status">
                                    <option value="" ${empty param.status ? 'selected' : ''}>Default</option>
                                    <option value="PENDING" ${param.status == 'PENDING' ? 'selected' : ''}>Pending</option>
                                    <option value="QUOTED" ${param.status == 'QUOTED' ? 'selected' : ''}>Quoted</option>
                                    <option value="ACCEPTED" ${param.status == 'ACCEPTED' ? 'selected' : ''}>Accepted</option>
                                    <option value="REJECTED" ${param.status == 'REJECTED' ? 'selected' : ''}>Rejected</option>
                                    <option value="EMAILED" ${param.status == 'EMAILED' ? 'selected' : ''}>Emailed</option>
                                    <option value="COMPLETED" ${param.status == 'COMPLETED' ? 'selected' : ''}>Complete</option>
                                </select>
                            </div>
                        </div>

                        <!-- Button Row -->
                        <div class="button-row">
                            <button type="submit">Filter</button>
                            <a href="${pageContext.request.contextPath}/listQuotation">
                                <button type="button">Reset</button>
                            </a>
                        </div>
                    </form>
                    <div class="text-center p-5 bg-light rounded shadow">
                        <input type="hidden" name="user_id" value="${sessionScope.currentAcc.userid}" />
                        <c:choose>
                            <c:when test="${not empty requestScope.wholesaleList}">

                                <h4 class="mb-4">Danh sách giỏ hoa đặt theo lô</h4>
                                <c:if test="${not empty requestScope.wholesaleList}">
                                    <table class="table table-bordered table-hover bg-white">
                                        <thead class="thead-dark">
                                            <tr>
                                                <th class="text-center">STT</th>
                                                <th class="text-center">Request Date</th>
                                                <th class="text-center">Quotation date</th>
                                                <th class="text-center">Status</th>
                                                <th class="text-center">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:set var="count" value="0" />
                                            <c:forEach var="item" items="${wholesaleList}">
                                                <c:if test="${item.getUser_id() eq sessionScope.currentAcc.userid}">
                                                    <c:set var="count" value="${count + 1}" />
                                                    <tr>
                                                        <td>${count}</td>

                                                        <!-- Ngày yêu cầu -->
                                                        <td>
                                                            ${item.getCreated_at()}
                                                        </td>

                                                        <!-- Ngày báo giá -->
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${item.getQuoted_at() == null}">
                                                                    not quoted yet
                                                                </c:when>
                                                                <c:otherwise>
                                                                    ${item.getQuoted_at()}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>
                                                            ${item.getStatus()}
                                                        </td>

                                                        <!-- ACTION BUTTONS -->
                                                        <td style="white-space: nowrap; min-width: 150px; text-align: center;">
                                                            <a href="${pageContext.request.contextPath}/quotationDetails?userId=${item.getUser_id()}&requestDate=${item.getCreated_at()}&requestGroupId=${item.getRequest_group_id()}&status=${item.getStatus()}">
                                                                <button type="button" class="btn-update">
                                                                    View Detail
                                                                </button>
                                                            </a>

                                                        </td>
                                                    </tr>
                                                </c:if>
                                            </c:forEach>

                                        </tbody>
                                    </table>
                                </c:if>

                            </c:when>    
                            <c:otherwise>
                                <h3>
                                    You have not placed any quote requests yet!
                                </h3>
                            </c:otherwise>
                        </c:choose>    

                    </div>
                </c:otherwise>                
            </c:choose>

        </div>

        <jsp:include page="/ZeShopper/footer.jsp"/>

    </body>
</html>
