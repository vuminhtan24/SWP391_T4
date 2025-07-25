<%-- 
    Document   : flowerbatch
    Created on : Jun 17, 2025, 10:51:24 PM
    Author     : [Your Name]
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
    .table td, .table th {
        vertical-align: middle;
    }
    .table-responsive {
        display: flex;
        justify-content: center;
        flex-direction: column;
        align-items: center;
    }
    .table {
        width: 100%;
        max-width: 1200px;
        margin: 0 auto;
    }
    .table th {
        background-color: #e9ecef !important;
        color: #212529 !important;
        font-weight: 700 !important;
    }
    .add-batch-row {
        text-align: center;
        padding: 10px 0;
    }
    .add-batch-row .btn-primary {
        padding: 8px 20px;
        font-size: 16px;
        font-weight: 500;
    }
    .filter-form {
        display: flex;
        justify-content: flex-start;
        align-items: center;
        margin-bottom: 20px;
        gap: 12px;
        width: 100%;
    }
    .filter-form select {
        padding: 8px 12px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 15px;
        background-color: #fff;
        min-width: 200px;
    }
    .filter-form select:focus {
        outline: none;
        border-color: #007bff;
    }
    .filter-form button {
        padding: 8px 16px;
        border: none;
        border-radius: 6px;
        background-color: #007bff;
        color: #fff;
        cursor: pointer;
    }
    .filter-form button:hover {
        background-color: #0056b3;
    }
    .btn {
        padding: 6px 12px;
        border: none;
        border-radius: 4px;
        color: white;
        font-size: 14px;
        cursor: pointer;
        margin-right: 5px;
    }
    .btn-delete {
        background-color: #e74c3c;
    }
    .btn-delete:hover {
        background-color: #c0392b;
    }
    .btn-edit {
        background-color: #3498db;
    }
    .btn-edit:hover {
        background-color: #2980b9;
    }
</style>

<div class="table-responsive mb-3">
    <form action="${pageContext.request.contextPath}/rawFlowerDetails" method="get" class="filter-form">
        <input type="hidden" name="flower_id" value="${param.flower_id}" />
        <input type="hidden" name="page" value="${currentPage}" />
        <!-- Bộ lọc kho -->
        <div style="display:flex; align-items:center;">
            <label for="warehouseFilter" style="margin-right:0.5rem; white-space:nowrap;">Warehouse:</label>
            <select name="warehouseFilter" id="warehouseFilter" onchange="this.form.submit()">
                <option value="" ${empty param.warehouseFilter ? 'selected' : ''}>-- Tất cả kho --</option>
                <c:forEach var="warehouse" items="${warehouses}">
                    <option value="${warehouse.name}" ${param.warehouseFilter == warehouse.name ? 'selected' : ''}>${warehouse.name}</option>
                </c:forEach>
            </select>
        </div>
        <!-- Bộ lọc trạng thái -->
        <div style="display:flex; align-items:center; margin-left:50px;">
            <label for="statusFilter" style="margin-right:0.5rem; white-space:nowrap;">Trạng thái:</label>
            <select name="statusFilter" id="statusFilter" onchange="this.form.submit()">
                <option value="" ${empty param.statusFilter ? 'selected' : ''}>-- Tất cả trạng thái --</option>
                <option value="fresh" ${param.statusFilter == 'fresh' ? 'selected' : ''}>Fresh</option>
                <option value="near_expired" ${param.statusFilter == 'near_expired' ? 'selected' : ''}>Near_expired</option>
                <option value="expired" ${param.statusFilter == 'expired' ? 'selected' : ''}>Expired</option>
            </select>
        </div>
    </form>

    <table class="table table-bordered align-middle">
        <thead class="table-light">
            <tr>
                <th scope="col">
                    <a href="?flower_id=${param.flower_id}&sortField=batchId&sortDir=${sortField eq 'batchId' and sortDir eq 'asc' ? 'desc' : 'asc'}&warehouseFilter=${fn:escapeXml(param.warehouseFilter)}&statusFilter=${fn:escapeXml(param.statusFilter)}&page=${currentPage}">
                        Batch ID
                        <c:if test="${sortField eq 'batchId'}">
                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                        </c:if>
                    </a>
                </th>
                <th scope="col">
                    <a href="?flower_id=${param.flower_id}&sortField=unitPrice&sortDir=${sortField eq 'unitPrice' and sortDir eq 'asc' ? 'desc' : 'asc'}&warehouseFilter=${fn:escapeXml(param.warehouseFilter)}&statusFilter=${fn:escapeXml(param.statusFilter)}&page=${currentPage}">
                        Unit Price (VND)
                        <c:if test="${sortField eq 'unitPrice'}">
                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                        </c:if>
                    </a>
                </th>
                <th scope="col">
                    <a href="?flower_id=${param.flower_id}&sortField=importDate&sortDir=${sortField eq 'importDate' and sortDir eq 'asc' ? 'desc' : 'asc'}&warehouseFilter=${fn:escapeXml(param.warehouseFilter)}&statusFilter=${fn:escapeXml(param.statusFilter)}&page=${currentPage}">
                        Import Date
                        <c:if test="${sortField eq 'importDate'}">
                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                        </c:if>
                    </a>
                </th>
                <th scope="col">
                    <a href="?flower_id=${param.flower_id}&sortField=expirationDate&sortDir=${sortField eq 'expirationDate' and sortDir eq 'asc' ? 'desc' : 'asc'}&warehouseFilter=${fn:escapeXml(param.warehouseFilter)}&statusFilter=${fn:escapeXml(param.statusFilter)}&page=${currentPage}">
                        Expiration Date
                        <c:if test="${sortField eq 'expirationDate'}">
                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                        </c:if>
                    </a>
                </th>
                <th scope="col">
                    <a href="?flower_id=${param.flower_id}&sortField=quantity&sortDir=${sortField eq 'quantity' and sortDir eq 'asc' ? 'desc' : 'asc'}&warehouseFilter=${fn:escapeXml(param.warehouseFilter)}&statusFilter=${fn:escapeXml(param.statusFilter)}&page=${currentPage}">
                        Quantity
                        <c:if test="${sortField eq 'quantity'}">
                            <i class="bi bi-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                        </c:if>
                    </a>
                </th>
                <th scope="col">Hold</th>
                <th scope="col">Warehouse</th>
                <th scope="col">Status</th>
                <th colspan="2">Action</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty error}">
                    <tr>
                        <td colspan="9" class="text-center text-danger">${error}</td>
                    </tr>
                </c:when>
                <c:when test="${flowerBatches == null}">
                    <tr>
                        <td colspan="9" class="text-center">Lỗi dữ liệu lô hoa.</td>
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
                            <td>${batch.status}</td>
                            <td>
                                <button type="button" class="btn btn-delete"
                                        onclick="if (confirm('Do you want to delete?'))
                                                    location.href = '${pageContext.request.contextPath}/delete_batch?batch_id=${batch.batchId}&flower_id=${param.flower_id}';">
                                    Delete
                                </button>
                                <button type="button" class="btn btn-edit"
                                        onclick="location.href = '${pageContext.request.contextPath}/update_batch?batch_id=${batch.batchId}';">
                                    Edit
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty flowerBatches}">
                        <tr>
                            <td colspan="9" class="text-center">Không tìm thấy lô hoa nào phù hợp với bộ lọc.</td>
                        </tr>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

    <c:if test="${totalPages > 1}">
        <div class="mt-auto">
            <nav>
                <ul class="pagination">
                    <!-- Previous -->
                    <c:url var="prevUrl" value="batch">
                        <c:param name="page" value="${currentPage - 1}" />
                        <c:param name="flower_id" value="${param.flower_id}" />
                        <c:param name="warehouseFilter" value="${param.warehouseFilter}" />
                        <c:param name="statusFilter" value="${param.statusFilter}" />
                        <c:param name="sortField" value="${sortField}" />
                        <c:param name="sortDir" value="${sortDir}" />
                    </c:url>
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${prevUrl}">Previous</a>
                    </li>

                    <!-- Pages -->
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <c:url var="pageUrl" value="batch">
                            <c:param name="page" value="${i}" />
                            <c:param name="flower_id" value="${param.flower_id}" />
                            <c:param name="warehouseFilter" value="${param.warehouseFilter}" />
                            <c:param name="statusFilter" value="${param.statusFilter}" />
                            <c:param name="sortField" value="${sortField}" />
                            <c:param name="sortDir" value="${sortDir}" />
                        </c:url>
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="${pageUrl}">${i}</a>
                        </li>
                    </c:forEach>

                    <!-- Next -->
                    <c:url var="nextUrl" value="batch">
                        <c:param name="page" value="${currentPage + 1}" />
                        <c:param name="flower_id" value="${param.flower_id}" />
                        <c:param name="warehouseFilter" value="${param.warehouseFilter}" />
                        <c:param name="statusFilter" value="${param.statusFilter}" />
                        <c:param name="sortField" value="${sortField}" />
                        <c:param name="sortDir" value="${sortDir}" />
                    </c:url>
                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="${nextUrl}">Next</a>
                    </li>
                </ul>
            </nav>
        </div>
    </c:if>
</div>