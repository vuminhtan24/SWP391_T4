<%-- 
    Document   : blank
    Created on : May 19, 2025, 2:34:20 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List,model.Bouquet,model.RawFlower" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>DASHMIN - Bootstrap Admin Template</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

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
            a.change-color-qvm:active {
                color: #007bff;
            }
        </style>

    </head>

    <body>
        <div class="container-fluid position-relative bg-white d-flex p-0">
            <!-- Spinner Start -->
            <div id="spinner" class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
                <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
                    <span class="sr-only">Loading...</span>
                </div>
            </div>
            <!-- Spinner End -->


            <!-- Sidebar Start -->
            <div class="sidebar pe-4 pb-3">
                <nav class="navbar bg-light navbar-light">
                    <a href="${pageContext.request.contextPath}/DashMin/admin" class="navbar-brand mx-4 mb-3">
                        <h3 class="text-primary"><i class="fa fa-hashtag me-2"></i>DASHMIN</h3>
                    </a>
                    <div class="d-flex align-items-center ms-4 mb-4">
                        <div class="position-relative">
                            <img class="rounded-circle" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">
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
                        <a href="${pageContext.request.contextPath}" class="nav-item nav-link active"><i class="fa fa-table me-2"></i>La Fioreria</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="far fa-file-alt me-2"></i>Pages</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/DashMin/404.jsp" class="dropdown-item">404 Error</a>
                                <a href="${pageContext.request.contextPath}/DashMin/blank.jsp" class="dropdown-item">Blank Page</a>
                                <a href="${pageContext.request.contextPath}/viewuserdetail" class="dropdown-item">View User Detail</a>
                                <a href="${pageContext.request.contextPath}/adduserdetail" class="dropdown-item">Add new User </a>
                            </div>
                        </div>
                    </div>
                </nav>
                </nav>
            </div>
            <!-- Sidebar End -->


            <!-- Content Start -->
            <div class="content">
                <jsp:include page="/DashMin/navbar.jsp"/> <!-- nav bar -->
                <div class="container-fluid py-5">
                    <div class="row justify-content-center">
                        <div class="col-xl-8 col-lg-10">
                            <div class="card shadow-sm">
                                <div class="card-header bg-primary text-white">
                                    <h4 class="mb-0">Create New Bouquet</h4>
                                </div>
                                <div class="card-body">
                                    <form action="${pageContext.request.contextPath}/addBouquet" method="post" enctype="multipart/form-data">
                                        <div class="row g-3">
                                            <!-- Bouquet Name -->
                                            <div class="col-md-6">
                                                <label for="bouquetName" class="form-label">Bouquet Name</label>
                                                <input
                                                    type="text"
                                                    id="bouquetName"
                                                    name="bouquetName"
                                                    class="form-control"
                                                    placeholder="Enter bouquet name"
                                                    maxlength="35"
                                                    required
                                                    value="${fn:escapeXml(param.bouquetName)}"
                                                    />
                                                <p style="color: red">${fn:escapeXml(requestScope.err)}</p>
                                            </div>

                                            <!-- Image URL -->
                                            <div class="col-md-6">
                                                <label for="imageFiles" class="form-label">Upload Images</label>
                                                <input 
                                                    type="file" 
                                                    id="imageFiles" 
                                                    name="imageFiles" 
                                                    class="form-control" 
                                                    accept=".jpg,.jpeg, .png" 
                                                    multiple
                                                    required
                                                    />
                                                <small class="form-text text-muted">
                                                    Bạn có thể chọn nhiều ảnh (.jpg, .jpeg, .png) cùng lúc.
                                                </small>
                                            </div>

                                            <!-- Description -->
                                            <div class="col-12">
                                                <label for="description" class="form-label">Description</label>
                                                <textarea
                                                    id="description"
                                                    name="description"
                                                    class="form-control"
                                                    rows="3"
                                                    placeholder="Describe your bouquet..."
                                                    >${fn:escapeXml(param.description)}</textarea>
                                            </div>
                                            <!-- Category -->
                                            <div class="col-md-6">
                                                <label for="category" class="form-label">Category</label>
                                                <select id="category" name="category" class="form-select">

                                                    <c:forEach var="c" items="${cateBouquetHome}">
                                                        <option value="${c.categoryId}"
                                                                <c:if test="${param.category eq c.categoryId}">selected</c:if>>
                                                            ${c.categoryName}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>

                                        <!-- Flower Table -->
                                        <!-- Flower Table -->
                                        <h5 class="mb-3 mt-4">Flowers in Bouquet</h5>
                                        <table id="flowerTable" class="table table-striped table-hover align-middle">
                                            <thead class="table-dark">
                                                <tr>
                                                    <th>Flower</th>
                                                    <th>Price per Stem</th>
                                                    <th>Quantity</th>
                                                    <th style="width:50px;"></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <!-- Dòng mặc định cũng dùng placeholder -->
                                                <tr>
                                                    <td>
                                                        <select name="flowerIds" class="form-select form-select-sm flower-select" onchange="updatePrice(this)">
                                                            <option value="0" disabled selected>-- Select Flower --</option>
                                                            <c:forEach var="f" items="${flowerInBouquet}">
                                                                <option value="${f.getRawId()}" data-price="${f.getUnitPrice()}">
                                                                    ${f.getRawName()}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <span class="form-text price-text">0.00 VND</span>
                                                        <input type="hidden" class="price-input" name="prices[]" value="0" />
                                                    </td>
                                                    <td>
                                                        <input type="number" name="quantities" value="1" min="1" step="1"
                                                               required class="form-control form-control-sm" oninput="calculateTotal()" />
                                                    </td>
                                                    <td>
                                                        <button type="button" class="btn btn-sm btn-outline-danger"
                                                                onclick="this.closest('tr').remove(); calculateTotal(); refreshAllOptions();">
                                                            &times;
                                                        </button>
                                                    </td>
                                                </tr>
                                            </tbody>
                                            <tfoot>
                                                <tr>
                                                    <td colspan="4">
                                                        <div class="mt-3 text-start fw-bold" style="color: #1e40af;">
                                                            Total Value: <span id="totalValueDisplay">0.00 VND</span>
                                                        </div>
                                                        <input type="hidden" id="totalValueInput" name="totalValue" value="0" />
                                                    </td>
                                                </tr>
                                            </tfoot>
                                        </table>

                                        <div class="text-end mb-4">
                                            <button type="button" id="addFlowerBtn" class="btn btn-outline-primary btn-sm">
                                                + Add Flower
                                            </button>
                                        </div>

                                        <!-- Submit -->
                                        <div class="mt-4 text-end">
                                            <button type="submit" class="btn btn-success px-4">Save Bouquet</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Hidden Template -->
                <table style="display:none;">
                    <tbody>
                        <tr id="flowerRowTemplate">
                            <td>
                                <select name="flowerIds" class="form-select form-select-sm flower-select" onchange="updatePrice(this)">
                                    <option value="" disabled selected>-- Select Flower --</option>
                                    <c:forEach var="f" items="${flowerInBouquet}">
                                        <option value="${f.getRawId()}" data-price="${f.getUnitPrice()}">
                                            ${f.getRawName()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <span class="form-text price-text">0.00 VND</span>
                                <input type="hidden" class="price-input" name="prices[]" value="0" />
                            </td>
                            <td>
                                <input type="number" name="quantities" value="1" min="1" step="1"
                                       class="form-control form-control-sm" oninput="calculateTotal()" />
                            </td>
                            <td>
                                <button type="button" class="btn btn-sm btn-outline-danger"
                                        onclick="this.closest('tr').remove(); calculateTotal(); refreshAllOptions();">
                                    &times;
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>



                <!-- Footer Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded-top p-4">
                        <div class="row">
                            <div class="col-12 col-sm-6 text-center text-sm-start">
                                &copy; <a href="#">Your Site Name</a>, All Right Reserved. 
                            </div>
                            <div class="col-12 col-sm-6 text-center text-sm-end">
                                <!--/*** This template is free as long as you keep the footer author’s credit link/attribution link/backlink. If you'd like to use the template without the footer author’s credit link/attribution link/backlink, you can purchase the Credit Removal License from "https://htmlcodex.com/credit-removal". Thank you for your support. ***/-->
                                Designed By <a href="https://htmlcodex.com">HTML Codex</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Footer End -->
            </div>
            <!-- Content End -->


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

        <!-- Template Javascript -->
        <script src="${pageContext.request.contextPath}/DashMin/js/main.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                            function updatePrice(sel) {
                                                // khi user chọn 1 hoa (không phải placeholder)
                                                if (!sel.value)
                                                    return;
                                                const price = parseFloat(sel.selectedOptions[0].dataset.price || 0);
                                                const row = sel.closest('tr');
                                                row.querySelector('.price-text').textContent = price.toFixed(2) + ' VND';
                                                row.querySelector('.price-input').value = price;
                                                calculateTotal();
                                                refreshAllOptions();
                                            }

                                            function calculateTotal() {
                                                let total = 0;
                                                document.querySelectorAll('#flowerTable tbody tr').forEach(r => {
                                                    const p = parseFloat(r.querySelector('.price-input').value) || 0;
                                                    const q = parseInt(r.querySelector('input[name="quantities"]').value) || 0;
                                                    total += p * q;
                                                });
                                                document.getElementById('totalValueDisplay').textContent = total.toFixed(2) + ' VND';
                                                document.getElementById('totalValueInput').value = total.toFixed(2);
                                            }

                                            function refreshAllOptions() {
                                                const selects = Array.from(document.querySelectorAll('select.flower-select'));
                                                const chosen = new Set(
                                                        selects
                                                        .map(s => s.value)
                                                        .filter(v => v)  // bỏ placeholder
                                                        );
                                                selects.forEach(sel => {
                                                    sel.querySelectorAll('option').forEach(opt => {
                                                        if (opt.value && chosen.has(opt.value) && opt.value !== sel.value) {
                                                            opt.disabled = true;
                                                            opt.style.backgroundColor = '#e9ecef';
                                                        } else {
                                                            opt.disabled = false;
                                                            opt.style.backgroundColor = '';
                                                        }
                                                    });
                                                });
                                            }

                                            function attachEvents(row) {
                                                const sel = row.querySelector('select.flower-select');
                                                const del = row.querySelector('.btn-outline-danger');
                                                const qty = row.querySelector('input[name="quantities"]');
                                                sel.addEventListener('change', () => updatePrice(sel));
                                                qty.addEventListener('input', calculateTotal);
                                                del.addEventListener('click', () => {
                                                    row.remove();
                                                    calculateTotal();
                                                    refreshAllOptions();
                                                });
                                            }

                                            document.addEventListener('DOMContentLoaded', () => {
                                                // ban đầu gắn event cho row mặc định
                                                document.querySelectorAll('#flowerTable tbody tr').forEach(r => attachEvents(r));
                                                calculateTotal();
                                                refreshAllOptions();

                                                // add new row
                                                document.getElementById('addFlowerBtn').addEventListener('click', () => {
                                                    const tpl = document.getElementById('flowerRowTemplate');
                                                    const newRow = tpl.cloneNode(true);
                                                    newRow.removeAttribute('id');
                                                    newRow.style.display = '';
                                                    document.querySelector('#flowerTable tbody').appendChild(newRow);
                                                    attachEvents(newRow);
                                                    calculateTotal();
                                                    refreshAllOptions();
                                                });
                                            });
        </script>

    </body>
</html>
