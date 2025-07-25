<%--
    Document   : orderAddItems
    Created on : Jun 16, 2025, 05:00:00 PM
    Author     : VU MINH TAN
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Add Order Items - DASHMIN</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

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
            .product-item-row {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
            }
            .product-item-row > div {
                margin-right: 15px;
            }
            .product-item-row .form-select,
            .product-item-row .form-control {
                width: 250px; /* Adjust width as needed */
            }
            .product-item-row .quantity-input {
                width: 100px; /* Smaller width for quantity */
            }
            /* Căn chỉnh các cột số sang phải */
            .table th.text-end,
            .table td.text-end,
            .price-display {
                text-align: right;
            }
        </style>
        <script>
            let itemCounter = 0;

            // Function to add a new product item row to the form
            function addProductItemRow() {
                const container = document.getElementById('productItemsContainer');
                const newRow = document.createElement('div');
                newRow.classList.add('product-item-row', 'd-flex', 'align-items-center', 'mb-3', 'p-2', 'border', 'rounded');
                newRow.innerHTML = `
                    <div class="col-md-6">
                        <label for="bouquetId_${itemCounter}" class="form-label visually-hidden">Bouquet:</label>
                        <select class="form-select bouquet-select" id="bouquetId_${itemCounter}" name="bouquetId" required>
                            <option value="">-- Select Bouquet --</option>
                            <c:forEach var="bouquet" items="${allBouquets}">
                                <option value="${bouquet.bouquetId}" data-price="${bouquet.price}" data-sellprice="${bouquet.sellPrice}">${bouquet.bouquetName} (Import: <fmt:formatNumber value="${bouquet.price}" pattern="#,##0" /> ₫ - Sell: <fmt:formatNumber value="${bouquet.sellPrice}" pattern="#,##0" /> ₫)</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label for="quantity_${itemCounter}" class="form-label visually-hidden">Quantity:</label>
                        <input type="number" class="form-control quantity-input" id="quantity_${itemCounter}" name="quantity" min="1" value="1" required>
                    </div>
                    <div class="col-md-2 d-flex align-items-center">
                        <button type="button" class="btn btn-danger btn-sm" onclick="removeProductItemRow(this)">Remove</button>
                    </div>
                `;
                container.appendChild(newRow);

                // Attach event listener to the new select element
                newRow.querySelector('.bouquet-select').addEventListener('change', updateBouquetOptions);

                itemCounter++;
                updateBouquetOptions(); // Update options after adding a new row
            }

            // Function to remove a product item row
            function removeProductItemRow(button) {
                button.closest('.product-item-row').remove();
                updateBouquetOptions(); // Update options after removing a row
            }

            // Function to update disabled state of bouquet options across all select boxes
            function updateBouquetOptions() {
                const allSelects = document.querySelectorAll('.bouquet-select');
                const selectedValues = new Set(); // To store currently selected bouquet IDs

                // First, collect all selected values
                allSelects.forEach(select => {
                    if (select.value !== "") {
                        selectedValues.add(select.value);
                    }
                });

                // Then, iterate through all selects again to disable/enable options
                allSelects.forEach(select => {
                    Array.from(select.options).forEach(option => {
                        // Enable all options first (except the empty one)
                        if (option.value !== "") {
                            option.disabled = false;
                        }

                        // If an option's value is in selectedValues AND it's not the current select's value, disable it
                        if (option.value !== "" && selectedValues.has(option.value) && option.value !== select.value) {
                            option.disabled = true;
                        }
                    });
                });
            }


            // Initial setup on page load
            document.addEventListener('DOMContentLoaded', () => {
                // Check if there are any rows rendered by server-side (e.g., after a validation error)
                const existingRows = document.querySelectorAll('#productItemsContainer .product-item-row');
                if (existingRows.length === 0) {
                    addProductItemRow(); // Add one initial row if none exist
                } else {
                    // If rows exist from server-side, attach event listeners and update options
                    existingRows.forEach(row => {
                        row.querySelector('.bouquet-select').addEventListener('change', updateBouquetOptions);
                    });
                    // Set itemCounter based on existing rows
                    itemCounter = existingRows.length;
                    updateBouquetOptions(); // Initial update for server-side rendered rows
                }
            });
        </script>
    </head>
    <body>
        <div class="container-fluid position-relative bg-white d-flex p-0">
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
                                <a href="${pageContext.request.contextPath}/DashMin/button.jsp" class="dropdown-item active">Buttons</a>
                                <a href="${pageContext.request.contextPath}/DashMin/typography.jsp" class="dropdown-item">Typography</a>
                                <a href="${pageContext.request.contextPath}/DashMin/element.jsp" class="dropdown-item">Other Elements</a>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}/DashMin/widget.jsp" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Widgets</a>
                        <a href="${pageContext.request.contextPath}/DashMin/form.jsp" class="nav-item nav-link"><i class="fa fa-keyboard me-2"></i>Forms</a>
                        <a href="${pageContext.request.contextPath}/ViewUserList" class="nav-item nav-link"><i class="fa fa-table me-2"></i>User</a>
                        <a href="${pageContext.request.contextPath}/viewBouquet" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Bouquet</a>
                        <a href="${pageContext.request.contextPath}/DashMin/chart.jsp" class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle active" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Order</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/orderManagement" class="dropdown-item">Order Management</a>
                                <a href="${pageContext.request.contextPath}/orderDetail" class="dropdown-item active">Order Details</a>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="nav-item nav-link"><i class="fa fa-table me-2"></i>RawFlower</a>
                        <a href="${pageContext.request.contextPath}/category" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Category</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-table me-2"></i>Repair Center</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/repairOrders" class="dropdown-item">Repair Orders</a>
                                <a href="${pageContext.request.contextPath}/repairHistory" class="dropdown-item">Repair History</a>
                                <a href="${pageContext.request.contextPath}/listRequest" class="dropdown-item">List Request</a>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}" class="nav-item nav-link"><i class="fa fa-table me-2"></i>La Fioreria</a>
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
            </div>
            <!-- Sidebar End -->

            <!-- Content Start -->
            <div class="content">
                <!-- Navbar Start -->
                <jsp:include page="/DashMin/navbar.jsp"/>
                <!-- Navbar End -->

                <!-- Add Order Items Form Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded p-4">
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <h6 class="mb-0">Add Products to Order ID: ${order.orderId}</h6>
                            <a href="${pageContext.request.contextPath}/orderDetail?orderId=${order.orderId}">View Order Details</a>
                        </div>

                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success" role="alert">
                                ${successMessage}
                            </div>
                        </c:if>
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger" role="alert">
                                ${errorMessage}
                            </div>
                        </c:if>

                        <div class="mb-3">
                            <p><strong>Order Date:</strong> ${order.orderDate}</p>
                            <p><strong>Customer:</strong> ${order.customerName}</p>
                            <p>
                                <strong>Current Total Import:</strong> 
                                <span class="price-display">
                                    <fmt:formatNumber value="${order.totalImport}" pattern="#,##0" /> ₫
                                </span>
                            </p>
                            <p>
                                <strong>Current Total Sell:</strong> 
                                <span class="price-display">
                                    <fmt:formatNumber value="${order.totalSell}" pattern="#,##0" /> ₫
                                </span>
                            </p>
                        </div>
                        
                        <hr>
                        <h6>Current Products in Order:</h6>
                        <c:if test="${empty currentOrderItems}">
                            <p>No products currently in this order.</p>
                        </c:if>
                        <c:if test="${not empty currentOrderItems}">
                            <div class="table-responsive mb-4">
                                <table class="table text-start align-middle table-bordered table-hover mb-0">
                                    <thead>
                                        <tr class="text-dark">
                                            <th scope="col">#</th>
                                            <th scope="col">Image</th>
                                            <th scope="col">Product Name</th>
                                            <th scope="col" class="text-end">Quantity</th>
                                            <th scope="col" class="text-end">Unit Price (Import)</th>
                                            <th scope="col" class="text-end">Unit Price (Sell)</th>
                                            <th scope="col" class="text-end">Subtotal (Import)</th>
                                            <th scope="col" class="text-end">Subtotal (Sell)</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="item" items="${currentOrderItems}" varStatus="loop">
                                            <tr>
                                                <td>${loop.index + 1}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty item.bouquetImage}">
                                                            <img src="${item.bouquetImage}" 
                                                                 alt="${item.bouquetName}" style="width: 50px; height: 50px; object-fit: cover;">
                                                        </c:when>
                                                        <c:otherwise>
                                                            [No Image]
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>${item.bouquetName}</td>
                                                <td class="text-end">${item.quantity}</td>
                                                <td class="text-end">
                                                    <fmt:formatNumber value="${item.unitPrice}" pattern="#,##0" /> ₫
                                                </td>
                                                <td class="text-end">
                                                    <fmt:formatNumber value="${item.sellPrice}" pattern="#,##0" /> ₫
                                                </td>
                                                <td class="text-end">
                                                    <fmt:parseNumber var="qty" value="${item.quantity}" integerOnly="true" />
                                                    <fmt:parseNumber var="priceImport" value="${item.unitPrice}" type="number" />
                                                    <fmt:formatNumber value="${qty * priceImport}" pattern="#,##0" /> ₫
                                                </td>
                                                <td class="text-end">
                                                    <fmt:parseNumber var="priceSell" value="${item.sellPrice}" type="number" />
                                                    <fmt:formatNumber value="${qty * priceSell}" pattern="#,##0" /> ₫
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:if>

                        <hr>
                        <h6>Add New Products:</h6>
                        <form action="${pageContext.request.contextPath}/addOrderItems" method="post">
                            <input type="hidden" name="orderId" value="${order.orderId}">
                            
                            <div id="productItemsContainer">
                                <%-- Dynamic product item rows will be added here by JavaScript --%>
                                <c:if test="${not empty paramValues.bouquetId}">
                                    <c:forEach var="i" begin="0" end="${fn:length(paramValues.bouquetId) - 1}">
                                        <div class="product-item-row d-flex align-items-center mb-3 p-2 border rounded">
                                            <div class="col-md-6">
                                                <label for="bouquetId_${i}" class="form-label visually-hidden">Bouquet:</label>
                                                <select class="form-select" id="bouquetId_${i}" name="bouquetId" required>
                                                    <option value="">-- Select Bouquet --</option>
                                                    <c:forEach var="bouquet" items="${allBouquets}">
                                                        <option value="${bouquet.bouquetId}" 
                                                                data-price="${bouquet.price}" 
                                                                data-sellprice="${bouquet.sellPrice}"
                                                                <c:if test="${paramValues.bouquetId[i] == bouquet.bouquetId}">selected</c:if>>
                                                            ${bouquet.bouquetName} (Import: <fmt:formatNumber value="${bouquet.price}" pattern="#,##0" /> ₫ - Sell: <fmt:formatNumber value="${bouquet.sellPrice}" pattern="#,##0" /> ₫)</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-md-3">
                                                <label for="quantity_${i}" class="form-label visually-hidden">Quantity:</label>
                                                <input type="number" class="form-control quantity-input" id="quantity_${i}" name="quantity" min="1" value="${paramValues.quantity[i]}" required>
                                            </div>
                                            <div class="col-md-2 d-flex align-items-center">
                                                <button type="button" class="btn btn-danger btn-sm" onclick="removeProductItemRow(this)">Remove</button>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <script>
                                        // Set itemCounter to the correct value after server-side rendering
                                        itemCounter = ${fn:length(paramValues.bouquetId)}; 
                                    </script>
                                </c:if>
                            </div>

                            <button type="button" class="btn btn-secondary mt-3" onclick="addProductItemRow()">Add Another Product</button>
                            
                            <div class="d-flex justify-content-end mt-4">
                                <button type="submit" class="btn btn-primary me-2">Save Products</button>
                               
                            </div>
                        </form>
                    </div>
                </div>
                <!-- Add Order Items Form End -->

                <!-- Footer Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded-top p-4">
                        <div class="row">
                            <div class="col-12 col-sm-6 text-center text-sm-start">
                                &copy; <a href="#">Your Site Name</a>, All Rights Reserved. 
                            </div>
                            <div class="col-12 col-sm-6 text-center text-sm-end">
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
    </body>
</html>
