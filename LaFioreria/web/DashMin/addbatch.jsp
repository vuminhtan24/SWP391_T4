<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Add Flower Batch - DASHMIN</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <link href="${pageContext.request.contextPath}/DashMin/img/favicon.ico" rel="icon">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/DashMin/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/DashMin/css/style.css" rel="stylesheet">
        <style>
            .content {
                padding: 30px;
                background-color: #f8f9fa;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                max-width: 800px;
                margin: 0 auto;
            }
            h1.h2 {
                font-size: 32px;
                color: #007bff;
                font-weight: 600;
                text-align: center;
                margin-bottom: 30px;
                border-bottom: 2px solid #007bff;
                padding-bottom: 10px;
            }
            .form-label {
                font-weight: 500;
                color: #333;
                margin-bottom: 5px;
                font-size: 16px;
            }
            .form-control, .form-select {
                border-radius: 5px;
                border: 1px solid #ced4da;
                padding: 8px 12px;
                font-size: 16px;
            }
            .form-control:focus, .form-select:focus {
                border-color: #007bff;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
            }
            .error {
                color: #dc3545;
                font-size: 14px;
                margin-top: 5px;
            }
            .general-error {
                color: #dc3545;
                font-size: 14px;
                margin-top: 5px;
                text-align: center;
                background-color: #f8d7da;
                padding: 8px;
                border-radius: 5px;
            }
            .btn-primary {
                background-color: #007bff;
                border-color: #007bff;
                padding: 8px 20px;
                font-weight: 500;
            }
            .btn-primary:hover {
                background-color: #0056b3;
                border-color: #0056b3;
            }
            .btn-secondary {
                background-color: #6c757d;
                border-color: #6c757d;
                padding: 8px 20px;
            }
            .btn-secondary:hover {
                background-color: #5a6268;
                border-color: #5a6268;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid position-relative bg-white d-flex p-0">
            <div class="content">
                <h1 class="h2">Add Flower Batch</h1>
                <input type="hidden" name="addFlowerAgree" value="${sessionScope.addFlowerAgree}">
                <!-- Display general error message -->
                <c:if test="${not empty error}">
                    <div class="general-error">${error}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/add_batch" method="post">
                    <input type="hidden" name="flower_id" value="${flowerId}">

                    <c:choose>

                        <c:when test="${sessionScope.addFlowerAgree eq true}">
                            <div class="mb-3">
                                <label for="unit_price" class="form-label">Unit Price (VND)</label>
                                <input type="number" id="unit_price" name="unit_price" class="form-control" 
                                       value="${unit_price}" required min="0" step="1">
                                <c:if test="${not empty unitPriceError}">
                                    <div class="error">${unitPriceError}</div>
                                </c:if>
                            </div>

                            <div class="mb-3">
                                <label for="import_date" class="form-label">Import Date</label>
                                <input type="date" id="import_date" name="import_date" class="form-control" 
                                       value="${requestDate}" required>
                                <c:if test="${not empty importDateError}">
                                    <div class="error">${importDateError}</div>
                                </c:if>
                            </div>

                            <div class="mb-3">
                                <label for="expiration_date" class="form-label">Expiration Date</label>
                                <input type="date" id="expiration_date" name="expiration_date" class="form-control" 
                                       value="${expiration_date}" required>
                                <c:if test="${not empty expirationDateError}">
                                    <div class="error">${expirationDateError}</div>
                                </c:if>
                            </div>

                            <div class="mb-3">
                                <label for="quantity" class="form-label">Quantity</label>
                                <input type="number" id="quantity" name="quantity" class="form-control" 
                                       value="${requestQuantity}" required min="${requestQuantity}" step="1">
                                <c:if test="${not empty quantityError}">
                                    <div class="error">${quantityError}</div>
                                </c:if>
                            </div>
                        </c:when>


                        <c:otherwise>
                            <div class="mb-3">
                                <label for="unit_price" class="form-label">Unit Price (VND)</label>
                                <input type="number" id="unit_price" name="unit_price" class="form-control" 
                                       value="${unit_price}" required min="0" step="1">
                                <c:if test="${not empty unitPriceError}">
                                    <div class="error">${unitPriceError}</div>
                                </c:if>
                            </div>

                            <div class="mb-3">
                                <label for="import_date" class="form-label">Import Date</label>
                                <input type="date" id="import_date" name="import_date" class="form-control" 
                                       value="${import_date}" required>
                                <c:if test="${not empty importDateError}">
                                    <div class="error">${importDateError}</div>
                                </c:if>
                            </div>

                            <div class="mb-3">
                                <label for="expiration_date" class="form-label">Expiration Date</label>
                                <input type="date" id="expiration_date" name="expiration_date" class="form-control" 
                                       value="${expiration_date}" required>
                                <c:if test="${not empty expirationDateError}">
                                    <div class="error">${expirationDateError}</div>
                                </c:if>
                            </div>

                            <div class="mb-3">
                                <label for="quantity" class="form-label">Quantity</label>
                                <input type="number" id="quantity" name="quantity" class="form-control" 
                                       value="${quantity}" required min="0" step="1">
                                <c:if test="${not empty quantityError}">
                                    <div class="error">${quantityError}</div>
                                </c:if>
                            </div>
                        </c:otherwise>
                    </c:choose>


                    <div class="mb-3">
                        <label for="hold" class="form-label">Hold</label>
                        <input type="number" id="hold" name="hold" class="form-control" 
                               value="${hold != null ? hold : '0'}" required min="0" step="1">
                        <c:if test="${not empty holdError}">
                            <div class="error">${holdError}</div>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label for="warehouse_id" class="form-label">Warehouse</label>
                        <select id="warehouse_id" name="warehouse_id" class="form-select" required>
                            <option value="">Select Warehouse</option>
                            <c:if test="${not empty sessionScope.listW}">
                                <c:forEach var="warehouse" items="${sessionScope.listW}">
                                    <option value="${warehouse.warehouseId}" 
                                            ${warehouse.warehouseId == warehouse_id ? 'selected' : ''}>
                                        ${warehouse.name}
                                    </option>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty sessionScope.listW}">
                                <option value="" disabled>No warehouses available</option>
                            </c:if>
                        </select>
                        <c:if test="${not empty warehouseIdError}">
                            <div class="error">${warehouseIdError}</div>
                        </c:if>
                    </div>

                    <div class="d-flex justify-content-center gap-3">
                        <button type="submit" name="action" value="addbatch" class="btn btn-primary">Add Batch</button>
                            <c:choose>
                                <c:when test="${sessionScope.addFlowerAgree eq true}">
                                    <a href="${pageContext.request.contextPath}/requestDetail?orderId=${orderId}&orderItemId=${orderItemId}" class="btn btn-secondary">Cancel</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/rawFlowerDetails?flower_id=${flowerId}" class="btn btn-secondary">Cancel</a>
                                </c:otherwise>
                            </c:choose>
                    </div>
                </form>

            </div>
        </div>

        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>