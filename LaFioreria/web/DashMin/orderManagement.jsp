<%--
    Document   : orderManagement
    Created on : Jun 15, 2025, 12:24:18 AM
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
        <title>Order Management - DASHMIN</title>
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

        <script>
            // Function to show a custom confirmation dialog instead of alert/confirm
            function showCustomConfirm(message, callback) {
                // Remove any existing modals
                const existingModal = document.getElementById('customConfirmModal');
                if (existingModal) {
                    existingModal.remove();
                }

                // Create modal HTML
                const modalHtml = `
                    <div class="modal fade" id="customConfirmModal" tabindex="-1" aria-labelledby="customConfirmModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header bg-warning text-white">
                                    <h5 class="modal-title" id="customConfirmModalLabel">Confirmation</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <p>${message}</p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                    <button type="button" class="btn btn-danger" id="confirmDeleteBtn">Delete</button>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
                document.body.insertAdjacentHTML('beforeend', modalHtml);

                const customConfirmModal = new bootstrap.Modal(document.getElementById('customConfirmModal'));
                customConfirmModal.show();

                document.getElementById('confirmDeleteBtn').onclick = function () {
                    customConfirmModal.hide();
                    callback(true);
                };

                // Handle modal closing via close button or backdrop click
                document.getElementById('customConfirmModal').addEventListener('hidden.bs.modal', function (event) {
                    // Only call callback(false) if it hasn't been called by the confirm button
                    if (!document.getElementById('confirmDeleteBtn').onclick) { // Check if onclick was unset
                        callback(false);
                    }
                    document.getElementById('customConfirmModal').remove(); // Clean up modal
                });
            }

            function confirmDelete(orderId) {
                showCustomConfirm('Are you sure you want to delete order ID ' + orderId + '?', function (confirmed) {
                    if (confirmed) {
                        window.location.href = '${pageContext.request.contextPath}/deleteOrder?orderId=' + orderId;
                    }
                });
                return false; // Prevent default link behavior
            }
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
                                <a href="${pageContext.request.contextPath}/orderManagement" class="dropdown-item active">Order Management</a>
                                <a href="${pageContext.request.contextPath}/orderDetail" class="dropdown-item">Order Details</a>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="nav-item nav-link"><i class="fa fa-table me-2"></i>RawFlower</a>
                        <a href="${pageContext.request.contextPath}/category" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Category</a>
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

                <!-- Order List Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light text-center rounded p-4">
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <h6 class="mb-0">Order Management</h6>
                            <a href="${pageContext.request.contextPath}/addOrder" class="btn btn-success">Add New Order</a>
                        </div>

                        <%-- Display success/error message from deletion --%>
                        <c:if test="${not empty param.message}">
                            <div class="alert alert-info alert-dismissible fade show" role="alert">
                                ${param.message}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>

                        <%-- Search, Filter, and Sort Form --%>
                        <div class="mb-4">
                            <form action="${pageContext.request.contextPath}/orderManagement" method="get" class="d-flex flex-wrap align-items-center">
                                <div class="input-group me-2 mb-2" style="max-width: 300px;">
                                    <input type="text" class="form-control" placeholder="Search by ID, customer, total sell..."
                                           name="keyword" value="${currentKeyword != null ? currentKeyword : ''}">
                                    <button class="btn btn-primary" type="submit">Search</button>
                                </div>
                                <div class="me-2 mb-2" style="max-width: 200px;">
                                    <select class="form-select" name="statusId" onchange="this.form.submit()">
                                        <option value="0" ${currentStatusId == null || currentStatusId == 0 ? 'selected' : ''}>-- Filter by Status --</option>
                                        <c:forEach var="status" items="${statuses}">
                                            <option value="${status.statusId}" ${currentStatusId != null && currentStatusId == status.statusId ? 'selected' : ''}>
                                                ${status.statusName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="me-2 mb-2" style="max-width: 200px;">
                                    <select class="form-select" name="sortField" onchange="this.form.submit()">
                                        <option value="orderDate" ${sortField == 'orderDate' ? 'selected' : ''}>Sort by Date</option>
                                        <option value="orderId" ${sortField == 'orderId' ? 'selected' : ''}>Sort by ID</option>
                                        <option value="customerName" ${sortField == 'customerName' ? 'selected' : ''}>Sort by Customer</option>
                                        <option value="totalImport" ${sortField == 'totalImport' ? 'selected' : ''}>Sort by Total Import</option>
                                        <option value="totalSell" ${sortField == 'totalSell' ? 'selected' : ''}>Sort by Total Sell</option>
                                        <option value="statusName" ${sortField == 'statusName' ? 'selected' : ''}>Sort by Status</option>
                                        <option value="shipperName" ${sortField == 'shipperName' ? 'selected' : ''}>Sort by Shipper</option>
                                    </select>
                                </div>
                                <div class="me-2 mb-2" style="max-width: 150px;">
                                    <select class="form-select" name="sortOrder" onchange="this.form.submit()">
                                        <option value="desc" ${sortOrder == 'desc' ? 'selected' : ''}>Descending</option>
                                        <option value="asc" ${sortOrder == 'asc' ? 'selected' : ''}>Ascending</option>
                                    </select>
                                </div>
                                <%-- Hidden fields to preserve keyword, statusId, and page when only sortField/sortOrder changes --%>
                                <input type="hidden" name="keyword" value="${currentKeyword}" />
                                <input type="hidden" name="statusId" value="${currentStatusId}" />
                                <input type="hidden" name="page" value="${currentPage}" />
                            </form>
                        </div>

                        <div class="table-responsive">
                            <table class="table text-start align-middle table-bordered table-hover mb-0">
                                <thead>
                                    <tr class="text-dark">
                                        <%-- Utility function to create URL for sortable column headers --%>
                                        <c:url var="baseSortUrl" value="/orderManagement">
                                            <c:param name="keyword" value="${currentKeyword}" />
                                            <c:param name="statusId" value="${currentStatusId}" />
                                            <c:param name="page" value="${currentPage}" />
                                        </c:url>

                                        <th scope="col">
                                            <a href="${baseSortUrl}&sortField=orderId&sortOrder=${sortField eq 'orderId' && sortOrder eq 'asc' ? 'desc' : 'asc'}">
                                                Order ID
                                                <c:if test="${sortField eq 'orderId'}">
                                                    <i class="fa fa-sort-${sortOrder eq 'asc' ? 'up' : 'down'}"></i>
                                                </c:if>
                                            </a>
                                        </th>
                                        <th scope="col">
                                            <a href="${baseSortUrl}&sortField=orderDate&sortOrder=${sortField eq 'orderDate' && sortOrder eq 'asc' ? 'desc' : 'asc'}">
                                                Order Date
                                                <c:if test="${sortField eq 'orderDate'}">
                                                    <i class="fa fa-sort-${sortOrder eq 'asc' ? 'up' : 'down'}"></i>
                                                </c:if>
                                            </a>
                                        </th>
                                        <th scope="col">
                                            <a href="${baseSortUrl}&sortField=customerName&sortOrder=${sortField eq 'customerName' && sortOrder eq 'asc' ? 'desc' : 'asc'}">
                                                Customer
                                                <c:if test="${sortField eq 'customerName'}">
                                                    <i class="fa fa-sort-${sortOrder eq 'asc' ? 'up' : 'down'}"></i>
                                                </c:if>
                                            </a>
                                        </th>
                                        <th scope="col">
                                            <a href="${baseSortUrl}&sortField=totalImport&sortOrder=${sortField eq 'totalImport' && sortOrder eq 'asc' ? 'desc' : 'asc'}">
                                                Total Import
                                                <c:if test="${sortField eq 'totalSell'}">
                                                    <i class="fa fa-sort-${sortOrder eq 'asc' ? 'up' : 'down'}"></i>
                                                </c:if>
                                            </a>
                                        </th>
                                        <th scope="col">
                                            <a href="${baseSortUrl}&sortField=totalSell&sortOrder=${sortField eq 'totalSell' && sortOrder eq 'asc' ? 'desc' : 'asc'}">
                                                Total Sell
                                                <c:if test="${sortField eq 'totalSell'}">
                                                    <i class="fa fa-sort-${sortOrder eq 'asc' ? 'up' : 'down'}"></i>
                                                </c:if>
                                            </a>
                                        </th>

                                        <th scope="col">
                                            <a href="${baseSortUrl}&sortField=statusName&sortOrder=${sortField eq 'statusName' && sortOrder eq 'asc' ? 'desc' : 'asc'}">
                                                Status
                                                <c:if test="${sortField eq 'statusName'}">
                                                    <i class="fa fa-sort-${sortOrder eq 'asc' ? 'up' : 'down'}"></i>
                                                </c:if>
                                            </a>
                                        </th>
                                        <th scope="col">
                                            <a href="${baseSortUrl}&sortField=shipperName&sortOrder=${sortField eq 'shipperName' && sortOrder eq 'asc' ? 'desc' : 'asc'}">
                                                Shipper
                                                <c:if test="${sortField eq 'shipperName'}">
                                                    <i class="fa fa-sort-${sortOrder eq 'asc' ? 'up' : 'down'}"></i>
                                                </c:if>
                                            </a>
                                        </th>
                                        <th scope="col">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${empty orders}">
                                        <tr>
                                            <td colspan="7">No orders found.</td>
                                        </tr>
                                    </c:if>
                                    <c:forEach var="order" items="${orders}">
                                        <tr>
                                            <td>${order.orderId}</td>
                                            <td>${order.orderDate}</td>
                                            <td>${order.customerName}</td>
                                            <td>${order.totalImport}</td>
                                            <td>${order.totalSell}</td>
                                            <td>${order.statusName}</td>
                                            <td>${order.shipperName != null ? order.shipperName : "Not Assigned"}</td>
                                            <td>
                                                <a class="btn btn-sm btn-primary"
                                                   href="${pageContext.request.contextPath}/orderDetail?orderId=${order.orderId}">Details</a>
                                                <a class="btn btn-sm btn-info"
                                                   href="${pageContext.request.contextPath}/orderDetail?orderId=${order.orderId}&action=edit">Edit</a>
                                                <a class="btn btn-sm btn-danger" href="#"
                                                   onclick="return confirmDelete(${order.orderId});">Delete</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <%-- Pagination Section --%>
                        <div class="d-flex justify-content-center mt-4">
                            <nav>
                                <ul class="pagination">
                                    <c:set var="queryParams" value="" />
                                    <c:if test="${not empty currentKeyword}">
                                        <c:set var="queryParams" value="${queryParams}&keyword=${currentKeyword}" />
                                    </c:if>
                                    <c:if test="${currentStatusId != null && currentStatusId != 0}">
                                        <c:set var="queryParams" value="${queryParams}&statusId=${currentStatusId}" />
                                    </c:if>
                                    <c:if test="${not empty sortField}">
                                        <c:set var="queryParams" value="${queryParams}&sortField=${sortField}" />
                                    </c:if>
                                    <c:if test="${not empty sortOrder}">
                                        <c:set var="queryParams" value="${queryParams}&sortOrder=${sortOrder}" />
                                    </c:if>

                                    <%-- Previous Button --%>
                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/orderManagement?page=${currentPage - 1}${queryParams}" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>

                                    <%-- Page Numbers --%>
                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                                            <a class="page-link" href="${pageContext.request.contextPath}/orderManagement?page=${i}${queryParams}">${i}</a>
                                        </li>
                                    </c:forEach>

                                    <%-- Next Button --%>
                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/orderManagement?page=${currentPage + 1}${queryParams}" aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </div>

                    </div>
                </div>
                <!-- Order List End -->

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
