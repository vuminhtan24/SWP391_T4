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
</style>
<div class="table-responsive mb-3">
    <table class="table table-bordered align-middle">
        <thead class="table-light">
            <tr>
                <th scope="col">Batch ID</th>
                <th scope="col">Unit Price (VND)</th>
                <th scope="col">Import Date</th>
                <th scope="col">Expiration Date</th>
                <th scope="col">Quantity</th>
                <th scope="col">Hold</th>
                <th scope="col">Warehouse</th>
                <th colspan="2">Action</th>
            </tr>
        </thead>
        <!-- Hàng chứa nút Add Batch -->
<!--        <tr class="add-batch-row">
            <td colspan="8">
                <button type="button" class="btn btn-primary" 
                        onclick="location.href='${pageContext.request.contextPath}/add_batch?flower_id=${param.flower_id}'">
                    + Add Batch
                </button>  
            </td>
        </tr>-->
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
