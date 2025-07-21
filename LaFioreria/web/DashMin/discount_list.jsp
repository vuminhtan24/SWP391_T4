<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Discount Management</title>
        <link href="${pageContext.request.contextPath}/DashMin/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/DashMin/css/style.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
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
    <!-- Sidebar End -->

    <!-- Content Start -->
    <div class="content">
        <jsp:include page="/DashMin/navbar.jsp"/>

        <!-- Discount Management Start -->
        <div class="container-fluid pt-4 px-4">
            <div class="bg-light text-center rounded p-4">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h6 class="mb-0">Discount Code Management</h6>
                    <a href="${pageContext.request.contextPath}/addDiscount.jsp" class="btn btn-success">Add Discount</a>
                </div>

                <c:if test="${not empty param.message}">
                    <div class="alert alert-info alert-dismissible fade show" role="alert">
                        ${param.message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <div class="table-responsive">
                    <table class="table table-bordered table-hover text-start align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>Code</th>
                                <th>Description</th>
                                <th>Type</th>
                                <th>Value</th>
                                <th>Max Discount</th>
                                <th>Min Order</th>
                                <th>Start</th>
                                <th>End</th>
                                <th>Used/Limit</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="dc" items="${discountCodes}">
                                <tr>
                                    <td>${dc.code}</td>
                                    <td>${dc.description}</td>
                                    <td>${dc.type}</td>
                                    <td>${dc.value}</td>
                                    <td><c:out value="${dc.maxDiscount != null ? dc.maxDiscount : '-'}"/></td>
                                    <td><c:out value="${dc.minOrderAmount != null ? dc.minOrderAmount : '-'}"/></td>
                                    <td><fmt:formatDate value="${dc.startDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                                    <td><fmt:formatDate value="${dc.endDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                                    <td>
                                        <c:out value="${dc.usedCount}" /> /
                                        <c:out value="${dc.usageLimit != null ? dc.usageLimit : '∞'}" />
                                    </td>
                                    <td>
                                        <span class="badge bg-${dc.active ? 'success' : 'secondary'}">
                                            ${dc.active ? 'Active' : 'Inactive'}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="editDiscount?code=${dc.code}" class="btn btn-sm btn-info">Edit</a>
                                        <a href="deactivateDiscount?code=${dc.code}" class="btn btn-sm btn-danger"
                                           onclick="return confirm('Deactivate this code?');">Deactivate</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <!-- Discount Management End -->

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
</div>

<!-- JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
