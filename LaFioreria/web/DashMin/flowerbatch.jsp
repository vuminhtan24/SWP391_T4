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
    /* Căn giữa bảng */
    .table-responsive {
        display: flex;
        justify-content: center;
    }
    .table {
        width: 100%;
        max-width: 1200px; /* Điều chỉnh chiều rộng tối đa theo ý muốn */
        margin: 0 auto; /* Căn giữa bảng */
    }
    /* Màu nền cho th */
    .table th {
        background-color: #e9ecef !important;
        color: #212529 !important;
        font-weight: 700 !important;
    }
    /* Căn giữa nút Add Batch */
    .add-batch-row {
        text-align: center;
        padding: 10px 0; /* Khoảng cách trên dưới */
    }
    .add-batch-row .btn-primary {
        padding: 8px 20px;
        font-size: 16px;
        font-weight: 500;
    }
    /* Styling for filter form */
    .filter-form {
        display: flex;
        justify-content: center;
        margin-bottom: 20px;
        gap: 10px;
    }
    .filter-form select, .filter-form button {
        padding: 8px 12px;
        border: 1px solid #ccc;
        border-radius: 4px;
        font-size: 14px;
    }
    .filter-form button {
        background-color: #007bff;
        color: white;
        border: none;
        cursor: pointer;
    }
    .filter-form button:hover {
        background-color: #0056b3;
    }
</style>
<div class="table-responsive mb-3">
    <!-- Filter form -->
    <form action="${pageContext.request.contextPath}/rawFlowerDetails" method="get" class="filter-form">
        <input type="hidden" name="flower_id" value="${param.flower_id}" />
        <select name="warehouseFilter">
            <option value="">All Warehouses</option>
            <c:forEach var="warehouse" items="${warehouses}">
                <option value="${warehouse.name}" ${param.warehouseFilter == warehouse.name ? 'selected' : ''}>
                    ${warehouse.name}
                </option>
            </c:forEach>
        </select>
        <button type="submit">Filter</button>
    </form>

    <table class="table table-bordered align-middle">
        <thead class="table-light">
            <tr>
                <th scope="col">
                    <a href="?flower_id=${param.flower_id}&sortField=batchId&sortDir=${sortField eq 'batchId' and sortDir eq 'asc' ? 'desc' : 'asc'}&warehouseFilter=${fn:escapeXml(param.warehouseFilter)}">
                        Batch ID
                        <c:if test="${sortField eq 'batchId'}">
                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                        </c:if>
                    </a>
                </th>
                <th scope="col">
                    <a href="?flower_id=${param.flower_id}&sortField=unitPrice&sortDir=${sortField eq 'unitPrice' and sortDir eq 'asc' ? 'desc' : 'asc'}&warehouseFilter=${fn:escapeXml(param.warehouseFilter)}">
                        Unit Price (VND)
                        <c:if test="${sortField eq 'unitPrice'}">
                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                        </c:if>
                    </a>
                </th>
                <th scope="col">
                    <a href="?flower_id=${param.flower_id}&sortField=importDate&sortDir=${sortField eq 'importDate' and sortDir eq 'asc' ? 'desc' : 'asc'}&warehouseFilter=${fn:escapeXml(param.warehouseFilter)}">
                        Import Date
                        <c:if test="${sortField eq 'importDate'}">
                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                        </c:if>
                    </a>
                </th>
                <th scope="col">
                    <a href="?flower_id=${param.flower_id}&sortField=expirationDate&sortDir=${sortField eq 'expirationDate' and sortDir eq 'asc' ? 'desc' : 'asc'}&warehouseFilter=${fn:escapeXml(param.warehouseFilter)}">
                        Expiration Date
                        <c:if test="${sortField eq 'expirationDate'}">
                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                        </c:if>
                    </a>
                </th>
                <th scope="col">
                    <a href="?flower_id=${param.flower_id}&sortField=quantity&sortDir=${sortField eq 'quantity' and sortDir eq 'asc' ? 'desc' : 'asc'}&warehouseFilter=${fn:escapeXml(param.warehouseFilter)}">
                        Quantity
                        <c:if test="${sortField eq 'quantity'}">
                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                        </c:if>
                    </a>
                </th>
                <th scope="col">Hold</th>
                <th scope="col">Warehouse</th>
                <th colspan="2">Action</th>
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
                            <td>${batch.warehouse.name}</td>
                            <td>
                                <button type="button"
                                        class="btn btn-delete"
                                        onclick="if (confirm('Do you want to delete?'))
                                                    location.href = '${pageContext.request.contextPath}/hidebatch?batch_id=${batch.batchId}';">
                                    Delete
                                </button>
                                <button type="button"
                                        class="btn btn-edit"
                                        onclick="location.href = '${pageContext.request.contextPath}/update_batch?batch_id=${batch.batchId}';">
                                    Edit
                                </button>
                            </td>
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