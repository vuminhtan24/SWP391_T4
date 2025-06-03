<%-- 
    Document   : blank
    Created on : May 19, 2025, 2:34:20 PM
    Author     : ADMIN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>DASHMIN - Bootstrap Admin Template</title>
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
                        <a href="${pageContext.request.contextPath}/DashMin/admin.jsp" class="nav-item nav-link"><i class="fa fa-tachometer-alt me-2"></i>Dashboard</a>
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
                        <a href="${pageContext.request.contextPath}/DashMin/table.jsp" class="nav-item nav-link active"><i class="fa fa-table me-2"></i>Tables</a>
                        <a href="${pageContext.request.contextPath}/DashMin/product.jsp" class="nav-item nav-link active"><i class="fa fa-table me-2"></i>Bouquet</a>
                        <a href="${pageContext.request.contextPath}/DashMin/chart.jsp" class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle active" data-bs-toggle="dropdown"><i class="far fa-file-alt me-2"></i>Pages</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/DashMin/404.jsp" class="dropdown-item">404 Error</a>
                                <a href="${pageContext.request.contextPath}/DashMin/blank.jsp" class="dropdown-item active">Blank Page</a>
                                <a href="${pageContext.request.contextPath}/ViewUserList" class="dropdown-item active">View User List</a>
                                <a href="${pageContext.request.contextPath}/viewuserdetail" class="dropdown-item active">View User </a>
                                <a href="${pageContext.request.contextPath}/adduserdetail" class="dropdown-item active">Add new User </a>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>
            <!-- Sidebar End -->


            <!-- Content Start -->
            <div class="content">
                <!-- Navbar Start -->
                <nav class="navbar navbar-expand bg-light navbar-light sticky-top px-4 py-0">
                    <a href="${pageContext.request.contextPath}/DashMin/admin.jsp" class="navbar-brand d-flex d-lg-none me-4">
                        <h2 class="text-primary mb-0"><i class="fa fa-hashtag"></i></h2>
                    </a>
                    <a href="#" class="sidebar-toggler flex-shrink-0">
                        <i class="fa fa-bars"></i>
                    </a>
                    <form class="d-none d-md-flex ms-4">
                        <input class="form-control border-0" type="search" placeholder="Search">
                    </form>
                    <div class="navbar-nav align-items-center ms-auto">
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                <i class="fa fa-envelope me-lg-2"></i>
                                <span class="d-none d-lg-inline-flex">Message</span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
                                <a href="#" class="dropdown-item">
                                    <div class="d-flex align-items-center">
                                        <img class="rounded-circle" src="${pageContext.request.contextPath}/DashMin/img/user.jpg" alt="" style="width: 40px; height: 40px;">
                                        <div class="ms-2">
                                            <h6 class="fw-normal mb-0">Jhon send you a message</h6>
                                            <small>15 minutes ago</small>
                                        </div>
                                    </div>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item">
                                    <div class="d-flex align-items-center">
                                        <img class="rounded-circle" src="${pageContext.request.contextPath}/DashMin/img/user.jpg" alt="" style="width: 40px; height: 40px;">
                                        <div class="ms-2">
                                            <h6 class="fw-normal mb-0">Jhon send you a message</h6>
                                            <small>15 minutes ago</small>
                                        </div>
                                    </div>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item">
                                    <div class="d-flex align-items-center">
                                        <img class="rounded-circle" src="${pageContext.request.contextPath}/DashMin/img/user.jpg" alt="" style="width: 40px; height: 40px;">
                                        <div class="ms-2">
                                            <h6 class="fw-normal mb-0">Jhon send you a message</h6>
                                            <small>15 minutes ago</small>
                                        </div>
                                    </div>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item text-center">See all message</a>
                            </div>
                        </div>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                <i class="fa fa-bell me-lg-2"></i>
                                <span class="d-none d-lg-inline-flex">Notificatin</span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
                                <a href="#" class="dropdown-item">
                                    <h6 class="fw-normal mb-0">Profile updated</h6>
                                    <small>15 minutes ago</small>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item">
                                    <h6 class="fw-normal mb-0">New user added</h6>
                                    <small>15 minutes ago</small>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item">
                                    <h6 class="fw-normal mb-0">Password changed</h6>
                                    <small>15 minutes ago</small>
                                </a>
                                <hr class="dropdown-divider">
                                <a href="#" class="dropdown-item text-center">See all notifications</a>
                            </div>
                        </div>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                <img class="rounded-circle me-lg-2" src="${pageContext.request.contextPath}/DashMin/img/user.jpg" alt="" style="width: 40px; height: 40px;">
                                <span class="d-none d-lg-inline-flex">John Doe</span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
                                <a href="#" class="dropdown-item">My Profile</a>
                                <a href="#" class="dropdown-item">Settings</a>
                                <a href="${pageContext.request.contextPath}/ZeShopper/LogoutServlet" class="dropdown-item">Log Out</a>
                            </div>
                        </div>
                    </div>
                </nav>
                <!-- Navbar End -->

                <!-- Table Start -->
                <div class="row">
                    <div class="col-sm-12 col-xl-4">
                        <div class="bg-light rounded p-4" style="max-height: 400px; overflow-y: auto;">
                            <div class="d-grid" style="display: grid; grid-template-columns: repeat(5, 1fr); gap: 10px;">
                                <c:forEach items="${userIds}" var="id">
                                    <a class="btn btn-primary text-center" href="viewuserdetail?id=${id}">${id}</a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-12 col-xl-8">
                        <div class="bg-light rounded h-100 p-4">
                            <h6 class="mb-4">View User Detail Table</h6>
                            <c:if test="${empty userIds}">
                                <p>No userIds found</p>
                            </c:if>

                            <div class="col-sm-12 col-xl-6">
                                <div class="bg-light rounded h-100 p-4">
                                    <div class="btn-group" role="group">
                                        <a  class="btn btn-primary" href="${pageContext.request.contextPath}/adduserdetail">Add new User detail</a>
                                    </div>
                                </div>
                            </div>

                            <form action="${pageContext.request.contextPath}/viewuserdetail" method="POST">
                                <table border="0">

                                    <tbody>
                                        <tr>
                                            <td>User ID: </td>
                                            <td>
                                                <input type="text" name="id" class="form-control" placeholder="User ID" aria-label="Username"
                                                       aria-describedby="basic-addon1" value="${userManager.userid}" readonly="">
                                            </td>
                                        </tr>
                                        <c:if test="${not empty errorID}">
                                            <tr>
                                                <td colspan="2"><span style="color:red">${errorID}</span></td>
                                            </tr>
                                        </c:if> 

                                        <tr>
                                            <td>User Name: </td>
                                            <td>
                                                <input type="text" name="name" class="form-control" placeholder="Username" aria-label="Username"
                                                       aria-describedby="basic-addon1" value="${userManager.username}">
                                            </td>
                                        </tr>

                                        <c:if test="${not empty errorName}">
                                            <tr>
                                                <td colspan="2"><span style="color:red">${errorName}</span></td>
                                            </tr>
                                        </c:if>
                                        <tr>
                                            <td>Password: </td>
                                            <td>
                                                <input type="text" name="pass" class="form-control" placeholder="Password" aria-label="Username"
                                                       aria-describedby="basic-addon1" value="${userManager.password}">
                                            </td>
                                        </tr>
                                        <c:if test="${not empty errorPass}">
                                            <tr>
                                                <td colspan="2"><span style="color:red">${errorPass}</span></td>
                                            </tr>
                                        </c:if>
                                        <tr>
                                            <c:if test="${not empty passwordStrength}">
                                        <div style="font-size: small;
                                             color: ${passwordStrength == 'Mạnh' ? 'green' :
                                                      passwordStrength == 'Trung bình' ? 'orange' : 'red'};">
                                            Mật khẩu: ${passwordStrength}
                                        </div>
                                    </c:if>
                                    </tr>
                                    <tr>
                                        <td>Full Name: </td>
                                        <td>
                                            <input type="text" name="FullName" class="form-control" placeholder="Full Name" aria-label="Username"
                                                   aria-describedby="basic-addon1" value="${userManager.fullname}">
                                        </td>

                                    </tr>
                                    <c:if test="${not empty errorFullname}">
                                        <tr>
                                            <td colspan="2"><span style="color:red">${errorFullname}</span></td>
                                        </tr>
                                    </c:if>
                                    <tr>
                                        <td>Email: </td>
                                        <td>
                                            <input type="text" class="form-control" placeholder="Email"
                                                   aria-label="Recipient's username" aria-describedby="basic-addon2" name="email" value="${userManager.email}">
                                        </td>
                                        <td>
                                            <span class="input-group-text" id="basic-addon2">@flower.com</span>
                                        </td>
                                    </tr>
                                    <c:if test="${not empty errorEmail}">
                                        <tr>
                                            <td colspan="2"><span style="color:red">${errorEmail}</span></td>
                                        </tr>
                                    </c:if>
                                    <tr>
                                        <td>Phone Number: </td>
                                        <td>
                                            <input type="type" name="phone" class="form-control" placeholder="Phone Number" aria-label="Username"
                                                   aria-describedby="basic-addon1" value="${userManager.phone}">
                                        </td>
                                    </tr>
                                    <c:if test="${not empty errorPhone}">
                                        <tr>
                                            <td colspan="2"><span style="color:red">${errorPhone}</span></td>
                                        </tr>
                                    </c:if>
                                    <tr>
                                        <td>Address: </td>
                                        <td>
                                            <input type="type" name="address" class="form-control" placeholder="Address" aria-label="Username"
                                                   aria-describedby="basic-addon1" value="${userManager.address}">
                                        </td>
                                    </tr>
                                    <c:if test="${not empty errorAddress}">
                                        <tr>
                                            <td colspan="2"><span style="color:red">${errorAddress}</span></td>
                                        </tr>
                                    </c:if>
                                    <tr>
                                        <td>Role: </td>
                                        <td>
                                            <input type="type" name="role" class="form-control" placeholder="Role" aria-label="Username"
                                                   aria-describedby="basic-addon1" value="${userManager.role}" readonly="">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <select class="form-select mb-3" aria-label="Default select example" name="option">
                                                <c:forEach items="${roleNames}" var="role">
                                                    <option value="${role}">${role}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td><input  class="btn btn-primary"type="submit" name="ud" value="UPDATE"></td>
                                    </tr>
                                    </tbody>
                                </table>           
                            </form>

                        </div>
                    </div>   
                </div>

                <!-- Blank End -->


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
    </body>
</html>
