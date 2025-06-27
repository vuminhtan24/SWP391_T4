<%-- 
    Document   : editBouquet
    Created on : May 19, 2025
    Author     : ADMIN
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List,model.Bouquet, model.Category, model.RawFlower"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Chỉnh sửa giỏ hoa - La Fioreria</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Chỉnh sửa giỏ hoa" name="keywords">
    <meta content="Trang chỉnh sửa giỏ hoa cho admin" name="description">

    <!-- Favicon -->
    <link href="${pageContext.request.contextPath}/DashMin/img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="${pageContext.request.contextPath}/DashMin/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="${pageContext.request.contextPath}/DashMin/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="${pageContext.request.contextPath}/DashMin/css/style.css" rel="stylesheet">

    <style>
        .bouquet-img {
            max-height: 600px;
            width: auto;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.1);
            margin-left: 30px;
        }
        .bouquet-info {
            background-color: #ffffff;
            border-radius: 12px;
            padding: 24px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }
        label.form-label {
            margin-bottom: 6px;
            color: #333;
        }
        .table td, .table th {
            vertical-align: middle;
        }
        @media (max-width: 768px) {
            .bouquet-img {
                margin-left: 0;
                max-width: 100%;
                height: auto;
            }
        }
        .flower-select option.already-selected {
            background-color: #e9ecef;
            color: #6c757d;
        }
        .preview-img-wrapper {
            position: relative;
            display: inline-block;
            margin: 5px;
            cursor: pointer;
            border: 2px solid transparent;
            border-radius: 4px;
        }
        .preview-img-wrapper.selected {
            border-color: #0d6efd;
        }
        .preview-img {
            max-width: 100px;
            max-height: 100px;
            object-fit: cover;
            border-radius: 4px;
        }
        .btn-delete-single {
            position: absolute;
            top: -6px;
            right: -6px;
            background: rgba(0,0,0,0.6);
            color: #fff;
            border: none;
            border-radius: 50%;
            width: 20px;
            height: 20px;
            line-height: 16px;
            font-size: 14px;
            text-align: center;
            padding: 0;
        }
        #externalPreview img {
            max-width: 150px;
            max-height: 150px;
            object-fit: cover;
            border-radius: 4px;
            border: 1px solid #ccc;
            margin-right: 10px;
        }
        .highlight-row {
            background-color: #fff3cd !important;
        }
    </style>
</head>
<body>
    <div class="container-fluid position-relative bg-white d-flex p-0">
        <!-- Spinner Start -->
        <div id="spinner" class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
            <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
                <span class="sr-only">Đang tải...</span>
            </div>
        </div>
        <!-- Spinner End -->

        <!-- Sidebar Start -->
        <div class="sidebar pe-4 pb-3">
            <nav class="navbar bg-light navbar-light">
                <a href="${pageContext.request.contextPath}/DashMin/admin.jsp" class="navbar-brand mx-4 mb-3">
                    <h3 class="text-primary"><i class="fa fa-hashtag me-2"></i>DASHMIN</h3>
                </a>
                <div class="d-flex align-items-center ms-4 mb-4">
                    <div class="position-relative">
                        <img class="rounded-circle" src="${pageContext.request.contextPath}/DashMin/img/user.jpg" alt="" style="width: 40px; height: 40px;">
                        <div class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"></div>
                    </div>
                    <div class="ms-3">
                        <h6 class="mb-0">Jhon Doe</h6>
                        <span>Admin</span>
                    </div>
                </div>
                <div class="navbar-nav w-100">
                    <a href="${pageContext.request.contextPath}/DashMin/admin" class="nav-item nav-link"><i class="fa fa-tachometer-alt me-2"></i>Dashboard</a>
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Elements</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <a href="${pageContext.request.contextPath}/DashMin/button.jsp" class="dropdown-item">Buttons</a>
                            <a href="${pageContext.request.contextPath}/DashMin/typography.jsp" class="dropdown-item">Typography</a>
                            <a href="${pageContext.request.contextPath}/DashMin/element.jsp" class="dropdown-item">Other Elements</a>
                        </div>
                    </div>
                    <a href="${pageContext.request.contextPath}/DashMin/widget.jsp" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Widgets</a>
                    <a href="${pageContext.request.contextPath}/DashMin/form.jsp" class="nav-item nav-link"><i class="fa fa-keyboard me-2"></i>Forms</a>
                    <a href="${pageContext.request.contextPath}/ViewUserList" class="nav-item nav-link"><i class="fa fa-table me-2"></i>User</a>
                    <a href="${pageContext.request.contextPath}/viewBouquet" class="nav-item nav-link active"><i class="fa fa-table me-2"></i>Bouquet</a>
                    <a href="${pageContext.request.contextPath}/DashMin/chart.jsp" class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
                    <a href="${pageContext.request.contextPath}/orderManagement" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Order</a>
                    <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="nav-item nav-link"><i class="fa fa-table me-2"></i>RawFlower</a>
                    <a href="${pageContext.request.contextPath}/category" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Category</a>
                    <a href="${pageContext.request.contextPath}/repairOrders" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Repair Orders</a>
                    <a href="${pageContext.request.contextPath}" class="nav-item nav-link"><i class="fa fa-table me-2"></i>La Fioreria</a>
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="far fa-file-alt me-2"></i>Pages</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <a href="${pageContext.request.contextPath}/DashMin/404.jsp" class="dropdown-item">404 Error</a>
                            <a href="${pageContext.request.contextPath}/DashMin/blank.jsp" class="dropdown-item">Blank Page</a>
                            <a href="${pageContext.request.contextPath}/viewuserdetail" class="dropdown-item">View User Detail</a>
                            <a href="${pageContext.request.contextPath}/adduserdetail" class="dropdown-item">Add new User</a>
                        </div>
                    </div>
                </div>
            </nav>
        </div>
        <!-- Sidebar End -->

        <!-- Content Start -->
        <div class="content">
            <jsp:include page="/DashMin/navbar.jsp"/> <!-- nav bar -->

            <a href="${pageContext.request.contextPath}/viewBouquet" style="margin: 15px">Return to bouquet list</a>
            <!-- Blank Start -->
            <form action="editBouquet" method="post" enctype="multipart/form-data">
                <input type="hidden" name="id" value="${bouquetDetail.getBouquetId()}">
                <input type="hidden" name="repairId" value="${param.repairId}">
                <input type="hidden" name="oldBatchId" value="${param.oldBatchId}">
                <input type="hidden" name="fromRepair" value="${param.fromRepair}">
                <div class="background-qvm" style="background-color: #f3f6f9; margin: 20px; border-radius: 5px;">
                    <div class="container-fluid py-5 px-4">
                        <c:if test="${param.fromRepair == 'true'}">
                            <div class="alert alert-warning">
                                Yêu cầu sửa #${param.repairId}: Vui lòng thay thế lô hoa (ID: ${param.oldBatchId}) bằng lô mới cùng loại hoa và trạng thái fresh.
                            </div>
                        </c:if>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>
                        <div class="d-flex align-items-start" style="gap: 20px;">
                            <!-- Cột trái: Ảnh -->
                            <div class="flex-shrink-0" style="flex: 0 0 33.333%; max-width: 33.333%;">
                                <c:set var="imageShown" value="false" />
                                <c:forEach var="imageFiles" items="${images}">
                                    <c:if test="${!imageShown}">
                                        <img src="${pageContext.request.contextPath}/upload/BouquetIMG/${imageFiles.image_url}"
                                             alt="Ảnh giỏ hoa" class="img-fluid bouquet-img-first" />
                                        <c:set var="imageShown" value="true" />
                                    </c:if>
                                </c:forEach>
                                <div id="serverImageInputs">
                                    <c:forEach var="imageFiles" items="${images}">
                                        <input type="hidden" name="existingImageUrls"
                                               value="${pageContext.request.contextPath}/upload/BouquetIMG/${imageFiles.image_url}" />
                                    </c:forEach>
                                </div>
                                <div class="container py-4">
                                    <div class="mb-3">
                                        <label for="imageFiles" class="form-label">Upload image (maximum 5)</label>
                                        <input type="file" id="imageFiles" name="imageFiles" class="form-control" accept=".jpg,.jpeg,.png" multiple
                                               <c:if test="${empty images}">required</c:if> />
                                        <small class="form-text text-muted">Maximum 5 images (.jpg, .jpeg, .png).</small>
                                    </div>
                                </div>
                                <div class="modal fade" id="previewModal" tabindex="-1" aria-labelledby="previewModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered modal-lg">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title">Image Preview</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                            </div>
                                            <div class="modal-body">
                                                <div id="previewContainer" class="d-flex flex-wrap"></div>
                                            </div>
                                            <div class="modal-footer">
                                                <button id="btnUploadMore" type="button" class="btn btn-primary me-auto">Upload more image</button>
                                                <button id="btnDeleteSelected" type="button" class="btn btn-danger">Delete selected</button>
                                                <button id="btnAccept" type="button" class="btn btn-success">Accept</button>
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Cột phải: Nội dung -->
                            <div class="flex-grow-1 bouquet-content" style="flex: 1;">
                                <div class="bouquet-info p-4 shadow-sm rounded bg-white">
                                    <h1 class="fw-bold mb-4 text-primary">
                                        <input type="text" name="bqName" value="${bouquetDetail.getBouquetName()}" maxlength="35" required/>
                                    </h1>
                                    <div class="mb-3">
                                        <label class="form-label fw-semibold">Category:</label>
                                        <select name="category" class="form-select">
                                            <c:forEach var="cate" items="${cateList}">
                                                <option value="${cate.getCategoryId()}" ${cate.getCategoryName() == cateName ? 'selected' : ''}>
                                                    ${cate.getCategoryName()}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <div class="mb-4">
                                            <label class="form-label fw-semibold">Description:</label>
                                            <textarea name="bqDescription" class="form-control" rows="4" required>${bouquetDetail.getDescription()}</textarea>
                                        </div>
                                        <label class="form-label fw-semibold">Status:</label>
                                        <select name="status" class="form-select" disabled>
                                            <option value="valid" ${bouquetDetail.getStatus() == 'valid' ? 'selected' : ''}>valid</option>
                                            <option value="invalid" ${bouquetDetail.getStatus() == 'needs_repair' ? 'selected' : ''}>needs repair</option>
                                        </select>
                                        <h5 class="mb-3 text-secondary">Flower in bouquet</h5>
                                        <div class="table-responsive mb-3">
                                            <table id="flowerTable" class="table table-bordered align-middle">
                                                <thead class="table-light">
                                                    <tr>
                                                        <th>Flower</th>
                                                        <th>Flower Batch</th>
                                                        <th>Price per Stem</th>
                                                        <th>Quantity</th>
                                                        <th></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="br" items="${flowerInBQ}">
                                                        <tr data-batch-id="${br.getBatchId()}" data-quantity="${br.getQuantity()}">
                                                            <td>
                                                                <select name="flowerIds" class="form-select form-select-sm flower-select">
                                                                    <option value="" disabled>-- Choose Flower --</option>
                                                                    <c:forEach var="f" items="${allFlowers}">
                                                                        <option value="${f.getFlowerId()}">${f.getFlowerName()}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td>
                                                                <select name="batchIds" class="form-select form-select-sm batch-select">
                                                                    <option value="" disabled>-- Choose Flower Batch --</option>
                                                                </select>
                                                            </td>
                                                            <td>
                                                                <span class="form-text price-text">0.00 VND</span>
                                                                <input type="hidden" class="price-input" name="prices[]" value="0" />
                                                            </td>
                                                            <td>
                                                                <input type="number" name="quantities" class="form-control form-control-sm quantity-input" />
                                                            </td>
                                                            <td>
                                                                <button type="button" class="btn btn-sm btn-outline-danger remove-btn">×</button>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                                <tfoot>
                                                    <tr>
                                                        <td colspan="5" class="text-start fw-bold text-primary">
                                                            Price: <span id="totalValueDisplay">0.00 VND</span>
                                                            <input type="hidden" id="totalValueInput" name="totalValue" value="0" />
                                                            <br/>
                                                            Sell Price: <span id="sellValueDisplay">0.00 VND</span>
                                                            <input type="hidden" id="sellValueInput" name="sellValue" value="0" />
                                                        </td>
                                                    </tr>
                                                </tfoot>
                                            </table>
                                        </div>
                                        <div class="d-flex justify-content-between">
                                            <button type="button" id="addFlowerBtn" class="btn btn-sm btn-outline-primary mb-4">+ Add more flower</button>
                                            <button type="submit" class="btn btn-success px-4">Save Bouquet</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <!-- Template để clone -->
                <table style="display:none;">
                    <tbody>
                        <tr id="flowerRowTemplate">
                            <td>
                                <select name="flowerIds" class="form-select form-select-sm flower-select">
                                    <option value="" disabled selected>-- Choose Flower --</option>
                                    <c:forEach var="f" items="${allFlowers}">
                                        <option value="${f.getFlowerId()}">${f.getFlowerName()}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <select name="batchIds" class="form-select form-select-sm batch-select">
                                    <option value="" disabled selected>-- Choose Flower Batch --</option>
                                </select>
                            </td>
                            <td>
                                <span class="form-text price-text">0.00 VND</span>
                                <input type="hidden" class="price-input" name="prices[]" value="0" />
                            </td>
                            <td>
                                <input type="number" name="quantities" value="1" min="1" step="1" required class="form-control form-control-sm quantity-input" />
                            </td>
                            <td>
                                <button type="button" class="btn btn-sm btn-outline-danger remove-btn">×</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <!-- Footer Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded-top p-4">
                        <div class="row">
                            <div class="col-12 col-sm-6 text-center text-sm-start">
                                © <a href="#">La Fioreria</a>, Mọi quyền được bảo lưu.
                            </div>
                            <div class="col-12 col-sm-6 text-center text-sm-end">
                                Thiết kế bởi <a href="https://htmlcodex.com">HTML Codex</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Footer End -->
                <!-- Back to Top -->
                <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
            </div>

            <!-- JavaScript Libraries -->
            <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
            <script src="${pageContext.request.contextPath}/DashMin/lib/chart/chart.min.js"></script>
            <script src="${pageContext.request.contextPath}/DashMin/lib/easing/easing.min.js"></script>
            <script src="${pageContext.request.contextPath}/DashMin/lib/waypoints/waypoints.min.js"></script>
            <script src="${pageContext.request.contextPath}/DashMin/lib/owlcarousel/owl.carousel.min.js"></script>
            <script src="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/js/moment.min.js"></script>
            <script src="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/js/moment-timezone.min.js"></script>
            <script src="${pageContext.request.contextPath}/DashMin/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>
            <script src="${pageContext.request.contextPath}/DashMin/js/main.js"></script>

            <script>
                const allBatchs = [
                    <c:forEach var="batch" items="${allBatchs}" varStatus="loop">
                        {
                            batchID: '${batch.getBatchId()}',
                            flowerID: '${batch.getFlowerId()}',
                            importDate: '${batch.getImportDate()}',
                            expirationDate: '${batch.getExpirationDate()}',
                            unitPrice: ${batch.getUnitPrice()},
                            status: '${batch.getStatus()}'
                        }<c:if test="${!loop.last}">,</c:if>
                    </c:forEach>
                ];
            </script>

            <script>
                document.addEventListener('DOMContentLoaded', () => {
                    const SELL_MULTIPLIER = 5;
                    const oldBatchId = '${param.oldBatchId}';

                    function formatCurrency(val) {
                        return parseFloat(val).toFixed(2) + ' VND';
                    }

                    function getSelectedFlowers() {
                        const map = new Map();
                        document.querySelectorAll('.flower-select').forEach((sel, idx) => {
                            if (sel.value) {
                                map.set(sel.value, (map.get(sel.value) || []).concat(idx));
                            }
                        });
                        return map;
                    }

                    function updateFlowerOptions() {
                        const used = getSelectedFlowers();
                        document.querySelectorAll('.flower-select').forEach((sel, i) => {
                            Array.from(sel.options).forEach(opt => {
                                if (!opt.value) return;
                                const taken = used.has(opt.value) && !used.get(opt.value).includes(i);
                                opt.disabled = taken;
                                opt.classList.toggle('already-selected', taken);
                            });
                        });
                    }

                    function fillBatchOptions(row, flowerID) {
                        const batchSel = row.querySelector('.batch-select');
                        batchSel.innerHTML = '<option value="" disabled selected>-- Chọn lô --</option>';
                        allBatchs.forEach(b => {
                            if (b.flowerID === flowerID && b.status === 'fresh') {
                                const opt = document.createElement('option');
                                opt.value = b.batchID;
                                opt.dataset.price = b.unitPrice;
                                opt.textContent = b.importDate + ' đến ' + b.expirationDate;
                                if (oldBatchId && b.batchID === oldBatchId) {
                                    opt.disabled = true; // Vô hiệu hóa oldBatchId
                                    opt.textContent += ' (Lô cũ - Không chọn)';
                                }
                                batchSel.appendChild(opt);
                            }
                        });
                    }

                    function updateRowPrice(row) {
                        const batchSel = row.querySelector('.batch-select');
                        const priceIn = row.querySelector('.price-input');
                        const priceTxt = row.querySelector('.price-text');
                        const sel = batchSel.selectedOptions[0];
                        const unit = sel ? parseFloat(sel.dataset.price) : 0;
                        priceIn.value = unit;
                        priceTxt.textContent = formatCurrency(unit);
                    }

                    function updateTotals() {
                        let total = 0;
                        document.querySelectorAll('#flowerTable tbody tr').forEach(row => {
                            const price = parseFloat(row.querySelector('.price-input').value) || 0;
                            const qty = parseInt(row.querySelector('.quantity-input').value, 10) || 0;
                            total += price * qty;
                        });
                        document.getElementById('totalValueDisplay').textContent = formatCurrency(total);
                        document.getElementById('totalValueInput').value = total;
                        const sell = total * SELL_MULTIPLIER;
                        document.getElementById('sellValueDisplay').textContent = formatCurrency(sell);
                        document.getElementById('sellValueInput').value = sell;
                    }

                    function bindRow(row) {
                        const flowerSel = row.querySelector('.flower-select');
                        const batchSel = row.querySelector('.batch-select');
                        const qtyInput = row.querySelector('.quantity-input');
                        const removeBtn = row.querySelector('.remove-btn');

                        flowerSel.addEventListener('change', () => {
                            fillBatchOptions(row, flowerSel.value);
                            updateFlowerOptions();
                            updateRowPrice(row);
                            updateTotals();
                        });

                        batchSel.addEventListener('change', () => {
                            if (oldBatchId && batchSel.value === oldBatchId) {
                                alert('Bạn phải chọn lô khác để thay thế lô cũ (ID: ' + oldBatchId + ').');
                                batchSel.value = '';
                            }
                            updateRowPrice(row);
                            updateTotals();
                        });

                        qtyInput.addEventListener('input', updateTotals);

                        removeBtn.addEventListener('click', () => {
                            row.remove();
                            updateFlowerOptions();
                            updateTotals();
                        });
                    }

                    // Khởi tạo các dòng hiện có
                    document.querySelectorAll('#flowerTable tbody tr').forEach(row => {
                        const batchID = row.dataset.batchId;
                        const qty = row.dataset.quantity;
                        const batch = allBatchs.find(b => b.batchID === batchID);

                        if (batch) {
                            const flowerSel = row.querySelector('.flower-select');
                            const batchSel = row.querySelector('.batch-select');
                            const qtyInput = row.querySelector('.quantity-input');

                            flowerSel.value = batch.flowerID;
                            fillBatchOptions(row, batch.flowerID);
                            batchSel.value = batchID;
                            updateRowPrice(row);
                            qtyInput.value = qty;

                            if (oldBatchId && batchID === oldBatchId) {
                                row.classList.add('highlight-row');
                                batchSel.focus();
                            }
                        }

                        bindRow(row);
                    });

                    updateFlowerOptions();
                    updateTotals();

                    // Thêm hoa mới
                    document.getElementById('addFlowerBtn').addEventListener('click', () => {
                        const tpl = document.getElementById('flowerRowTemplate');
                        const newRow = tpl.cloneNode(true);
                        newRow.removeAttribute('id');
                        newRow.style.display = '';
                        newRow.querySelector('.flower-select').value = '';
                        newRow.querySelector('.batch-select').innerHTML = '<option value="" disabled selected>-- Chọn lô --</option>';
                        newRow.querySelector('.price-text').textContent = '0.00 VND';
                        newRow.querySelector('.price-input').value = 0;
                        newRow.querySelector('.quantity-input').value = 1;
                        document.querySelector('#flowerTable tbody').appendChild(newRow);
                        bindRow(newRow);
                        updateFlowerOptions();
                        updateTotals();
                    });

                    // Xử lý preview ảnh
                    const MAX_FILES = 5;
                    const fileInput = document.getElementById('imageFiles');
                    const previewModal = new bootstrap.Modal(document.getElementById('previewModal'));
                    const previewContainer = document.getElementById('previewContainer');
                    const btnUploadMore = document.getElementById('btnUploadMore');
                    const btnDeleteSelected = document.getElementById('btnDeleteSelected');
                    const btnAccept = document.getElementById('btnAccept');
                    const serverImageInputsDiv = document.getElementById('serverImageInputs');
                    const triggerImg = document.querySelector('.bouquet-img-first');

                    let serverImageUrls = [
                        <c:forEach items="${images}" var="img" varStatus="status">
                            '${pageContext.request.contextPath}/upload/BouquetIMG/${img.image_url}'<c:if test="${!status.last}">,</c:if>
                        </c:forEach>
                    ];
                    let acceptedFiles = [];
                    let selectedIndices = new Set();

                    function getCurrentItems() {
                        const items = [];
                        serverImageUrls.forEach(url => items.push({url}));
                        acceptedFiles.forEach(file => items.push({file}));
                        return items.slice(0, MAX_FILES);
                    }

                    function updateServerInputs() {
                        serverImageInputsDiv.innerHTML = '';
                        serverImageUrls.forEach(url => {
                            const inp = document.createElement('input');
                            inp.type = 'hidden';
                            inp.name = 'existingImageUrls';
                            inp.value = url;
                            serverImageInputsDiv.appendChild(inp);
                        });
                    }

                    function updateInputFiles() {
                        const dt = new DataTransfer();
                        acceptedFiles.forEach(f => dt.items.add(f));
                        fileInput.files = dt.files;
                    }

                    function renderPreview() {
                        const items = getCurrentItems();
                        previewContainer.innerHTML = '';
                        items.forEach((item, idx) => {
                            const wrap = document.createElement('div');
                            wrap.className = 'preview-img-wrapper';
                            if (selectedIndices.has(idx)) wrap.classList.add('selected');
                            wrap.addEventListener('click', () => {
                                if (selectedIndices.has(idx)) {
                                    selectedIndices.delete(idx);
                                    wrap.classList.remove('selected');
                                } else {
                                    selectedIndices.add(idx);
                                    wrap.classList.add('selected');
                                }
                                btnDeleteSelected.disabled = selectedIndices.size === 0;
                            });
                            const img = document.createElement('img');
                            img.className = 'preview-img';
                            img.src = item.file ? URL.createObjectURL(item.file) : item.url;
                            wrap.appendChild(img);
                            const btnDel = document.createElement('button');
                            btnDel.type = 'button';
                            btnDel.className = 'btn-delete-single';
                            btnDel.innerText = '×';
                            btnDel.addEventListener('click', ev => {
                                ev.stopPropagation();
                                if (item.file) acceptedFiles = acceptedFiles.filter(f => f !== item.file);
                                else serverImageUrls = serverImageUrls.filter(u => u !== item.url);
                                updateInputFiles();
                                updateServerInputs();
                                selectedIndices.clear();
                                renderPreview();
                            });
                            wrap.appendChild(btnDel);
                            previewContainer.appendChild(wrap);
                        });
                        btnDeleteSelected.disabled = true;
                    }

                    if (triggerImg) {
                        triggerImg.addEventListener('click', () => {
                            selectedIndices.clear();
                            updateServerInputs();
                            updateInputFiles();
                            renderPreview();
                            previewModal.show();
                        });
                    }

                    btnUploadMore.addEventListener('click', () => fileInput.click());
                    fileInput.addEventListener('change', e => {
                        const newFiles = Array.from(e.target.files).filter(f => f.type.startsWith('image/'));
                        const slots = MAX_FILES - (serverImageUrls.length + acceptedFiles.length);
                        if (slots <= 0) {
                            alert(`Bạn chỉ được tải tối đa ${MAX_FILES} ảnh.`);
                        } else {
                            acceptedFiles = acceptedFiles.concat(newFiles.slice(0, slots));
                            updateInputFiles();
                            renderPreview();
                        }
                        e.target.value = '';
                    });

                    btnDeleteSelected.addEventListener('click', () => {
                        Array.from(selectedIndices).sort((a, b) => b - a).forEach(idx => {
                            const item = getCurrentItems()[idx];
                            if (item.file) acceptedFiles = acceptedFiles.filter(f => f !== item.file);
                            else serverImageUrls = serverImageUrls.filter(u => u !== item.url);
                        });
                        selectedIndices.clear();
                        updateInputFiles();
                        updateServerInputs();
                        renderPreview();
                    });

                    btnAccept.addEventListener('click', () => {
                        updateInputFiles();
                        updateServerInputs();
                        previewModal.hide();
                    });
                });
            </script>
</body>
</html>