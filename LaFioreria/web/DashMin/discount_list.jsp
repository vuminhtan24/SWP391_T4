<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- Removed fmt:setLocale value="vi_VN" as we want English --%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Discount Management</title>
        <link href="${pageContext.request.contextPath}/DashMin/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/DashMin/css/style.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container-fluid position-relative bg-white d-flex p-0">
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
                            <h6 class="mb-0">${sessionScope.currentAcc.getFullname()}</h6>
                            <span>Admin</span>
                        </div>
                    </div>
                    <c:choose>
                        <c:when test="${sessionScope.currentAcc.getRole() != 2}">
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
                                <a href="${pageContext.request.contextPath}/discount" 
                                   class="nav-item nav-link ${pageContext.request.servletPath eq '/discount_list.jsp' ? 'active' : ''}">
                                    <i class="fa fa-percentage me-2"></i>Discount
                                </a>
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
                </c:when>  
                <c:otherwise>
                    <div class="navbar-nav w-100">
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle active" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Order</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/orderManagement" class="dropdown-item active">Order Management</a>
                                <a href="${pageContext.request.contextPath}/orderDetail" class="dropdown-item">Order Details</a>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="nav-item nav-link"><i class="fa fa-table me-2"></i>RawFlower</a>

                        <a href="${pageContext.request.contextPath}" class="nav-item nav-link"><i class="fa fa-table me-2"></i>La Fioreria</a>
                    </div>
                </nav>
            </div>
        </c:otherwise>  
    </c:choose>  
    <div class="content">
        <jsp:include page="/DashMin/navbar.jsp"/>

        <div class="container-fluid pt-4 px-4">
            <div class="bg-light rounded p-4">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h6 class="mb-0">Discount Code Management</h6>
                </div>

                <form action="${pageContext.request.contextPath}/discount" method="post"
                      class="row gy-2 gx-2 bg-white border p-3 mb-4 rounded shadow-sm align-items-end">
                    <input type="hidden" name="action" value="add"/>

                    <div class="col-md-2">
                        <label class="form-label small">Code</label>
                        <input type="text" name="code" class="form-control form-control-sm" placeholder="ABC"
                               value="${code != null ? code : ''}" required/>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label small">Description</label>
                        <input type="text" name="description" class="form-control form-control-sm"
                               value="${description != null ? description : ''}" required/>
                    </div>
                    <div class="col-md-1">
                        <label class="form-label small">Type</label>
                        <select name="type" class="form-select form-select-sm">
                            <option value="PERCENT" ${type == 'PERCENT' ? 'selected' : ''}>%</option>
                            <option value="FIXED" ${type == 'FIXED' ? 'selected' : ''}>₫</option>
                        </select>
                    </div>
                    <div class="col-md-1">
                        <label class="form-label small">Value</label>
                        <input type="number" name="value" class="form-control form-control-sm" step="0.01"
                               value="${value != null ? value : ''}" required/>
                    </div>
                    <div class="col-md-1">
                        <label class="form-label small">Max</label>
                        <input type="number" name="maxDiscount" class="form-control form-control-sm" step="0.01"
                               value="${maxDiscount != null ? maxDiscount : ''}"/>
                    </div>
                    <div class="col-md-1">
                        <label class="form-label small">Min Order</label>
                        <input type="number" name="minOrder" class="form-control form-control-sm" step="0.01"
                               value="${minOrder != null ? minOrder : ''}"/>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label small">Start Date</label>
                        <input type="datetime-local" name="start" class="form-control form-control-sm"
                               value="${start != null ? start : ''}" required/>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label small">End Date</label>
                        <input type="datetime-local" name="end" class="form-control form-control-sm"
                               value="${end != null ? end : ''}" required/>
                    </div>
                    <div class="col-md-1">
                        <label class="form-label small">Usage Limit</label>
                        <input type="number" name="usageLimit" class="form-control form-control-sm"
                               value="${usageLimit != null ? usageLimit : ''}"/>
                    </div>
                    <div class="col-md-1 d-grid">
                        <button type="submit" class="btn btn-sm btn-primary">Add</button>
                    </div>

                    <!-- THÔNG BÁO LỖI -->
                    <c:if test="${not empty error}">
                        <div class="col-12">
                            <div class="alert alert-danger mt-2 mb-0 py-2 px-3">${error}</div>
                        </div>
                    </c:if>
                </form>

                <c:if test="${not empty param.message}">
                    <div class="alert alert-info alert-dismissible fade show" role="alert">
                        ${param.message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                <c:if test="${not empty editDiscount}">
                    <div class="card mb-4">
                        <div class="card-header bg-warning text-white">
                            <h5 class="mb-0">Chỉnh sửa mã giảm giá: ${editDiscount.code}</h5>
                        </div>
                        <div class="card-body">
                            <form action="updateDiscount" method="post">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label>Mã</label>
                                        <input type="text" name="code" class="form-control" value="${editDiscount.code}" readonly>
                                    </div>
                                    <div class="col-md-6">
                                        <label>Mô tả</label>
                                        <input type="text" name="description" class="form-control" value="${editDiscount.description}">
                                    </div>
                                    <div class="col-md-6">
                                        <label>Loại</label>
                                        <select name="type" class="form-select">
                                            <option value="PERCENT" ${editDiscount.type == 'PERCENT' ? 'selected' : ''}>Phần trăm</option>
                                            <option value="AMOUNT" ${editDiscount.type == 'AMOUNT' ? 'selected' : ''}>Số tiền</option>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <label>Giá trị</label>
                                        <input type="number" step="0.01" name="value" class="form-control" value="${editDiscount.value}">
                                    </div>
                                    <div class="col-md-6">
                                        <label>Giảm tối đa</label>
                                        <input type="number" step="0.01" name="maxDiscount" class="form-control" value="${editDiscount.maxDiscount}">
                                    </div>
                                    <div class="col-md-6">
                                        <label>Đơn hàng tối thiểu</label>
                                        <input type="number" step="0.01" name="minOrderAmount" class="form-control" value="${editDiscount.minOrderAmount}">
                                    </div>
                                    <div class="col-md-6">
                                        <label>Ngày bắt đầu</label>
                                        <input type="date" name="startDate" class="form-control"
                                               value="${editDiscount.startDate.toLocalDateTime().toLocalDate()}">
                                    </div>
                                    <div class="col-md-6">
                                        <label>Ngày kết thúc</label>
                                        <input type="date" name="endDate" class="form-control"
                                               value="${editDiscount.endDate.toLocalDateTime().toLocalDate()}">
                                    </div>
                                    <div class="col-md-6">
                                        <label>Giới hạn lượt dùng</label>
                                        <input type="number" name="usageLimit" class="form-control"
                                               value="${editDiscount.usageLimit}">
                                    </div>
                                    <div class="col-md-6">
                                        <label>Kích hoạt</label>
                                        <div class="form-check mt-2">
                                            <input type="checkbox" name="active" class="form-check-input" id="activeCheck"
                                                   ${editDiscount.active ? 'checked' : ''}>
                                            <label class="form-check-label" for="activeCheck">Đang hoạt động</label>
                                        </div>
                                    </div>
                                </div>
                                <%-- START: Khối hiển thị thông báo lỗi --%>
                                <c:if test="${not empty errors}">
                                    <div class="alert alert-danger mt-2 mb-0 py-2 px-3">
                                        <h6>Lỗi nhập liệu:</h6>
                                        <ul>
                                            <c:forEach var="errorMsg" items="${errors}">
                                                <li>${errorMsg}</li>
                                                </c:forEach>
                                        </ul>
                                    </div>
                                </c:if>
                                <%-- END: Khối hiển thị thông báo lỗi --%>

                                <%-- START: Khối hiển thị thông báo thành công --%>
                                <c:if test="${not empty sessionScope.message}">
                                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                                        ${sessionScope.message}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                    </div>
                                    <%-- Quan trọng: Xóa thông báo khỏi session sau khi hiển thị --%>
                                    <% session.removeAttribute("message"); %>
                                </c:if>
                                <%-- END: Khối hiển thị thông báo thành công --%>
                                <div class="mt-4">
                                    <button type="submit" class="btn btn-success">Lưu thay đổi</button>
                                    <a href="/LaFioreria/discount" class="btn btn-secondary">Huỷ</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </c:if>
                ---
                <div class="row mb-4">
                    <div class="col-12">
                        <form action="${pageContext.request.contextPath}/discount" method="get" class="row g-3 align-items-end">
                            <div class="col-md-4">
                                <label for="searchCode" class="form-label small">Search by Code or Description</label>
                                <input type="text" class="form-control form-control-sm" id="searchCode" name="search"
                                       placeholder="Enter code or description" value="${param.search}">
                            </div>
                            <div class="col-md-2">
                                <label for="filterStatus" class="form-label small">Filter by Status</label>
                                <select class="form-select form-select-sm" id="filterStatus" name="status">
                                    <option value="">All</option>
                                    <option value="active" ${param.status == 'active' ? 'selected' : ''}>Active</option>
                                    <option value="inactive" ${param.status == 'inactive' ? 'selected' : ''}>Inactive</option>
                                </select>
                            </div>
                            <div class="col-md-auto">
                                <button type="submit" class="btn btn-sm btn-secondary">Apply Filters</button>
                                <a href="${pageContext.request.contextPath}/discount" class="btn btn-sm btn-outline-secondary">Clear Filters</a>
                            </div>
                        </form>
                    </div>
                </div>
                ---
                <div class="table-responsive">
                    <table class="table table-bordered table-hover text-start align-middle">
                        <thead class="table-dark text-center">
                            <tr>
                                <th>Code</th>
                                <th>Description</th>
                                <th>Type</th>
                                <th>Value</th>
                                <th>Max Discount</th>
                                <th>Min Order</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>Usage</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="dc" items="${discountCodes}">
                                <tr class="text-center">
                                    <td>${dc.code}</td>
                                    <td>${dc.description}</td>
                                    <td>
                                        <span class="badge bg-${dc.type == 'PERCENT' ? 'info' : 'warning'}">
                                            ${dc.type == 'PERCENT' ? '%' : '₫'}
                                        </span>
                                    </td>
                                    <td>${dc.value}</td>
                                    <td><c:out value="${dc.maxDiscount != null ? dc.maxDiscount : '-'}"/></td>
                                    <td><c:out value="${dc.minOrderAmount != null ? dc.minOrderAmount : '-'}"/></td>
                                    <td><fmt:formatDate value="${dc.startDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    <td><fmt:formatDate value="${dc.endDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    <td>
                                        ${dc.usedCount} /
                                        <c:out value="${dc.usageLimit != null ? dc.usageLimit : '∞'}"/>
                                    </td>
                                    <td>
                                        <span class="badge rounded-pill bg-${dc.active ? 'success' : 'secondary'}">
                                            <i class="fas fa-${dc.active ? 'check' : 'times'} me-1"></i>
                                            ${dc.active ? 'Active' : 'Inactive'}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="editDiscount?code=${dc.code}" class="btn btn-sm btn-outline-primary me-1" title="Edit">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <form action="discount" method="post" style="display:inline;">
                                            <input type="hidden" name="action" value="deactivate" />
                                            <input type="hidden" name="code" value="${dc.code}" />
                                            <button type="submit" class="btn btn-sm btn-outline-danger"
                                                    onclick="return confirm('Are you sure you want to deactivate this code?');"
                                                    title="Deactivate">
                                                <i class="fas fa-ban"></i>
                                            </button>
                                        </form>

                                        <form action="discount" method="post" style="display:inline;" 
                                              onsubmit="return confirm('Are you sure to delete this code?');">
                                            <input type="hidden" name="action" value="delete"/>
                                            <input type="hidden" name="code" value="${dc.code}"/>
                                            <button class="btn btn-sm btn-outline-danger" title="Delete">
                                                <i class="fas fa-trash-alt"></i>
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${totalPages > 1}">
                        <nav class="mt-3">
                            <ul class="pagination pagination-sm justify-content-end">
                                <c:forEach var="i" begin="1" end="${totalPages}">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="discount?page=${i}">${i}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </nav>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="container-fluid pt-4 px-4">
            <div class="bg-light rounded-top p-4">
                <div class="row">
                    <div class="col-12 col-sm-6 text-center text-sm-start">
                        &copy; <a href="#">La Fioreria Admin</a>, All Rights Reserved.
                    </div>
                    <div class="col-12 col-sm-6 text-center text-sm-end">
                        Designed By <a href="https://htmlcodex.com">HTML Codex</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>