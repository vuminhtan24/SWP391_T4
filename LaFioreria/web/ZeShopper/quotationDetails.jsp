<%-- 
    Document   : quotationDetails
    Created on : Jul 18, 2025, 1:58:07 AM
    Author     : ADMIN
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List,model.Bouquet, model.Category, model.RawFlower"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="vi_VN" />
<html>
    <head>
        <title>Quotation Details</title>
        <link href="${pageContext.request.contextPath}/ZeShopper/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/responsive.css" rel="stylesheet">
    </head>
    <style>

        .heading {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 16px;
            color: #222;
        }

        /* Bảng chính */
        .styled-table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            font-size: 14px;
            background-color: #f9fbfc;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }

        /* Phần thead */
        .styled-table thead {
            background-color: #fe980f;
        }

        /* Ô trong thead */
        .styled-table thead td {
            font-weight: 600;
            color: white;
            padding: 12px 16px;
            border-bottom: 1px solid #e0e0e0;
            text-align: left;
        }

        /* Ô trong tbody */
        .styled-table tbody td {
            padding: 12px 16px;
            color: #333;
            border-bottom: 1px solid #f0f0f0;
        }

        /* Hover dòng */
        .styled-table tbody tr:hover td {
            background-color: #e9f3ff;
            transition: background-color 0.2s ease;
        }

        /* Dòng cuối không có border dưới */
        .styled-table tbody tr:last-child td {
            border-bottom: none;
        }

        /* Phần bổ sung để giữ phân trang đứng yên */
        .styled-table tbody {
            display: block;
            min-height: 300px; /* Bạn có thể điều chỉnh chiều cao này nếu muốn */
        }

        /* Đảm bảo thead và tr hoạt động đúng trong block tbody */
        .styled-table thead,
        .styled-table tbody tr {
            display: table;
            width: 100%;
            table-layout: fixed;
        }


        .filter-form {
            width: 100%;
            margin-bottom: 20px;
        }

        .filter-row {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            align-items: flex-end;
        }

        .filter-item {
            display: flex;
            flex-direction: column;
            min-width: 200px;
        }

        .filter-item label {
            font-weight: bold;
            margin-bottom: 4px;
        }

        .filter-item input,
        .filter-item select {
            padding: 6px 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .button-row {
            margin-top: 15px;
            display: flex;
            gap: 12px;
        }

        .button-row button {
            padding: 8px 16px;
            border: none;
            background-color: #1976d2;
            color: white;
            border-radius: 4px;
            cursor: pointer;
        }

        .button-row button[type="button"] {
            background-color: #757575;
        }

        .button-row button:hover {
            opacity: 0.9;
        }

        .btn-accept {
            background-color: #ff9900; /* Cam */
            color: white;
            border: none;
            padding: 8px 16px;
            margin-right: 10px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        .btn-accept:hover {
            background-color: #e68a00; /* Cam đậm hơn */
        }

        .btn-reject {
            background-color: #e53935; /* Đỏ */
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        .btn-reject:hover {
            background-color: #c62828; /* Đỏ đậm hơn */
        }

        /* Banner thông báo trạng thái dưới bảng */
        .status-banner {
            width: 100%;
            min-height: 80px;
            display: flex;
            align-items: center;         /* Căn giữa theo chiều dọc */
            justify-content: center;     /* Căn giữa theo chiều ngang */
            text-align: center;
            font-weight: bold;
            font-size: 18px;
            margin-top: 30px;
            border-radius: 8px;
            padding: 10px 20px;
        }

        /* Trạng thái: ACCEPTED */
        .status-accepted {
            background-color: #e8f5e9;
            color: #2e7d32;
        }

        /* Trạng thái: REJECTED */
        .status-rejected {
            background-color: #ffebee;
            color: #c62828;
        }

        /* Trạng thái: CHỜ XỬ LÝ (ví dụ EMAILED) */
        .status-waiting {
            background-color: #e3f2fd;
            color: #0d47a1;
        }


    </style>
    <body>

        <jsp:include page="/ZeShopper/header.jsp"/>

        <div class="container mt-5">
            <div class="text-center p-5 bg-light rounded shadow">
                <table class="styled-table">
                    <thead>
                        <tr>
                            <td>STT</td>
                            <td>Bouquet Name</td>
                            <td>Bouquet Image</td>
                            <%
                                String newOrder = "asc";
                                if ("quantity".equals(request.getAttribute("sortBy")) && "asc".equals(request.getAttribute("sortOrder"))) {
                                    newOrder = "desc";
                                }
                            %>

                            <td>
                                <c:url var="sortedUrl" value="/quotationDetails">
                                    <c:param name="userId" value="${fn:trim(param.userId)}"/>
                                    <c:param name="requestDate" value="${fn:trim(param.requestDate)}"/>
                                    <c:param name="status" value="${fn:trim(param.status)}"/>
                                    <c:param name="sortBy" value="quantity"/>
                                    <c:param name="sortOrder" value="<%= newOrder %>"/>
                                </c:url>
                                <a href="${sortedUrl}" style="color: white">Requested Quantity</a>
                            </td>
                            <td>WholeSale Price</td>
                            <td>Total WholeSale Price</td>                                
                            <%
                                String newStatusOrder = "asc";
                                if ("status".equals(request.getAttribute("sortBy")) && "asc".equals(request.getAttribute("sortOrder"))) {
                                    newStatusOrder = "desc";
                                }
                            %>

                            <td>
                                <c:url var="sortedStatusUrl" value="/quotationDetails">
                                    <c:param name="userId" value="${fn:trim(param.userId)}"/>
                                    <c:param name="requestDate" value="${fn:trim(param.requestDate)}"/>
                                    <c:param name="status" value="${fn:trim(param.status)}"/>
                                    <c:param name="sortBy" value="status"/>
                                    <c:param name="sortOrder" value="<%= newStatusOrder %>"/>
                                </c:url>
                                <a href="${sortedStatusUrl}" style="color: white">Status</a>
                            </td>

                        </tr>
                    </thead>  
                    <tbody>
                        <c:forEach var="item" items="${listWS}" varStatus="loop">
                            <tr class="cart-row"
                                data-bouquet-id="${item.getBouquet_id()}"
                                data-quantity="${item.getRequested_quantity()}">
                                <td>${startIndex + loop.index + 1}</td>
                                <td>
                                    <c:forEach var="bouquet" items="${listBQ}">
                                        <c:if test="${item.getBouquet_id() eq bouquet.getBouquetId()}">
                                            ${bouquet.getBouquetName()}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:set var="imageShown" value="false" />
                                    <c:forEach var="img" items="${listIMG}">
                                        <c:if test="${!imageShown and item.getBouquet_id() eq img.getbouquetId()}">
                                            <img src="${pageContext.request.contextPath}/upload/BouquetIMG/${img.getImage_url()}" alt="alt" style="width: 60px; height: 60px;"/>
                                            <c:set var="imageShown" value="true" />
                                        </c:if>
                                    </c:forEach>
                                </td>    
                                <td>${item.getRequested_quantity()}</td>

                                <td>
                                    <c:choose>
                                        <c:when test="${not empty item.getQuoted_price() and item.getQuoted_price() != 0}">
                                            <fmt:formatNumber value="${item.getQuoted_price()}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫
                                        </c:when>
                                        <c:otherwise>Not Quoted yet</c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <c:choose>
                                        <c:when test="${not empty item.getTotal_price() and item.getTotal_price() != 0}">
                                            <fmt:formatNumber value="${item.getTotal_price()}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫
                                        </c:when>
                                        <c:otherwise>Not Quoted yet</c:otherwise>
                                    </c:choose>
                                </td>

                                <td>${item.getStatus()}</td>

                            </tr>  
                        </c:forEach>
                        <tr style="font-weight:bold; background-color:#f9f9f9; font-size: large">
                            <td colspan="7" style="text-align:right;">Total Quote Request Price: 
                                <fmt:formatNumber value="${totalWholeSaleOrder}" type="number" groupingUsed="true" maxFractionDigits="0" /> ₫
                            </td>
                        </tr>    
                    </tbody>   
                </table>
                <c:set var="status" value="${listWS[0].getStatus()}" />
                <c:choose>   
                    <c:when test="${listWS[0].getStatus() eq 'EMAILED'}">
                        <div style="margin-top: 15px; display: flex; justify-content: flex-end;">
                            <form action="cartWholeSale" method="post">
                                <input type="hidden" name="requestDate" value="${listWS[0].getCreated_at()}" />
                                <div class="popup-buttons" style="white-space: nowrap;">
                                    <button type="submit"
                                            class="btn-accept"
                                            style="display: inline-block; padding: 8px 12px; white-space: nowrap;">
                                        <i class="fa fa-shopping-cart"></i> Add All to Cart
                                    </button>
                                </div>
                                <p id="quantity-error" style="color: red; font-weight: bold">${error}</p>
                            </form>

                            <button type="button" class="btn-reject">Reject Order</button>
                        </div>
                    </div>
                </c:when> 
                <c:when test="${listWS[0].getStatus() eq 'ACCEPTED'}">
                    <p class="status-banner status-accepted">You have completed this WholeSale Order</p>
                </c:when>
                <c:when test="${listWS[0].getStatus() eq 'REJECTED'}">
                    <p class="status-banner status-rejected">You have rejected this WholeSale Order</p>
                </c:when>
                <c:otherwise>
                    <p class="status-banner status-waiting">Wait for quoted</p>
                </c:otherwise>
            </c:choose>        
            <!-- List Request End -->
        </div>
    </div>
    <jsp:include page="/ZeShopper/footer.jsp"/>

</body>
</html>

