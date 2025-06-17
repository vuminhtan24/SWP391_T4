<%-- 
    Document   : flowerbatch
    Created on : Jun 17, 2025, 10:51:24 PM
    Author     : [Your Name]
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    /* Table styling from blank.jsp */
    .table td, .table th {
        vertical-align: middle;
    }
</style>
<div class="table-responsive mb-3">
    <table class="table table-bordered align-middle">
        <thead class="table-light">
            <tr>
                <th>Batch ID</th>
                <th>Unit Price (VND)</th>
                <th>Import Date</th>
                <th>Expiration Date</th>
                <th>Quantity</th>
                <th>Hold</th>
                <th>Warehouse</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty error}">
                    <tr>
                        <td colspan="7" class="text-center text-danger">${error}</td>
                    </tr>
                </c:when>
                <c:when test="${flowerBatches == null}">
                    <tr>
                        <td colspan="7" class="text-center">Lỗi dữ liệu lô hoa.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="batch" items="${flowerBatches}">
                        <tr>
                            <td>${batch.batchId}</td>
                            <td>${batch.unitPrice}</td>
                            <td>${batch.importDate}</td>
                            <td>${batch.expirationDate}</td>
                            <td>${batch.quantity}</td>
                            <td>${batch.hold}</td>
                            <td>${batch.warehouse != null ? batch.warehouse.name : 'N/A'}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty flowerBatches}">
                        <tr>
                            <td colspan="7" class="text-center">Không có lô hoa nào cho loại hoa này.</td>
                        </tr>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</div>
