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
        <title>Add New User</title>
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

                        <a href="${pageContext.request.contextPath}/viewBouquet" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Bouquet</a>
                        <a href="${pageContext.request.contextPath}/DashMin/chart.jsp" class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
                        <a href="${pageContext.request.contextPath}/orderManagement" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Order</a>
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
                        <a href="${pageContext.request.contextPath}/listWholeSaleRequest" class="nav-item nav-link"><i class="fa fa-table me-2"></i>WholeSale</a>                   
                        <a href="${pageContext.request.contextPath}" class="nav-item nav-link"><i class="fa fa-table me-2"></i>La Fioreria</a>


                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="far fa-file-alt me-2"></i>Pages</a>
                            <div class="dropdown-menu bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/DashMin/404.jsp" class="dropdown-item">404 Error</a>
                                <a href="${pageContext.request.contextPath}/DashMin/blank.jsp" class="dropdown-item">Blank Page</a>
                                <a href="${pageContext.request.contextPath}/viewuserdetail" class="dropdown-item">View User Detail</a>
                                <a href="${pageContext.request.contextPath}/adduserdetail" class="dropdown-item active">Add new User </a>
                            </div>
                        </div>
                    </div>
                </nav>
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

                <div class="col-sm-12 col-xl-6">
                    <div class="bg-light rounded h-100 p-4">
                        <form action="adduserdetail" method="post">
                            <table class="table table-striped">
                                <tbody>
                                    <!-- Username -->
                                    <tr>
                                        <td>User Name:</td>
                                        <td>
                                            <input type="text" name="name" class="form-control" placeholder="User Name"
                                                   value="${param.name}" aria-label="User Name">
                                            <c:if test="${not empty errorName}">
                                                <p style="color:red;">${errorName}</p>
                                            </c:if>
                                        </td>
                                    </tr>

                                    <!-- Password -->
                                    <tr>
                                        <td>Password:</td>
                                        <td>
                                            <input type="text" name="pass" class="form-control" placeholder="Password"
                                                   value="${param.pass}" aria-label="Password">
                                            <small class="form-text text-success">
                                                Password must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number. Length 8-32 characters.
                                            </small>
                                            <c:if test="${not empty errorPassword}">
                                                <p style="color:red;">${errorPassword}</p>
                                            </c:if>
                                        </td>
                                    </tr>

                                    <!-- Full Name -->
                                    <tr>
                                        <td>Full Name:</td>
                                        <td>
                                            <input type="text" name="FullName" class="form-control" placeholder="Full Name"
                                                   value="${param.FullName}" aria-label="Full Name">
                                            <c:if test="${not empty errorFullname}">
                                                <p style="color:red;">${errorFullname}</p>
                                            </c:if>
                                        </td>
                                    </tr>

                                    <!-- Email -->
                                    <tr>
                                        <td>Email:</td>
                                        <td>
                                            <input type="text" name="email" class="form-control" placeholder="Email"
                                                   value="${param.email}" aria-label="Email">
                                            <c:if test="${not empty errorEmail}">
                                                <p style="color:red;">${errorEmail}</p>
                                            </c:if>
                                        </td>
                                    </tr>

                                    <!-- Phone Number -->
                                    <tr>
                                        <td>Phone Number:</td>
                                        <td>
                                            <input type="text" name="phone" class="form-control" placeholder="Phone Number"
                                                   value="${param.phone}" aria-label="Phone Number">
                                            <c:if test="${not empty errorPhone}">
                                                <p style="color:red;">${errorPhone}</p>
                                            </c:if>
                                        </td>
                                    </tr>

                                    <!-- Address -->
                                    <tr>
                                        <td>Address:</td>
                                        <td>
                                            <input type="text" name="address" class="form-control" placeholder="Address"
                                                   value="${param.address}" aria-label="Address">
                                            <c:if test="${not empty errorAddress}">
                                                <p style="color:red;">${errorAddress}</p>
                                            </c:if>
                                        </td>
                                    </tr>

                                    <!-- Role Selection -->
                                    <tr>
                                        <td>Role:</td>
                                        <td>
                                            <select name="option" class="form-select" onchange="handleRoleChange(this)">
                                                <c:forEach items="${roleNames}" var="role">
                                                    <option value="${role}" ${role == selectedRole ? 'selected' : ''}>${role}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                </tbody>


                                <!-- 👤 Trường Customer -->
                                <tbody id="customerFields" style="display:none;">
                                    <tr>
                                        <td>Customer Code:</td>
                                        <td>
                                            <input name="customerCode" class="form-control" value="${customerCode != null ? customerCode : ''}"/>
                                            <span class="text-danger">${errorCustomerCode}</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Join Date:</td>
                                        <td>
                                            <input type="date" name="joinDate" class="form-control" value="${joinDate != null ? joinDate : ''}"/>
                                            <span class="text-danger">${errorJoinDate}</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Loyalty Point:</td>
                                        <td>
                                            <input type="number" name="loyaltyPoint" class="form-control" value="${loyaltyPoint != null ? loyaltyPoint : ''}"/>
                                            <span class="text-danger">${errorLoyaltyPoint}</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Birthday:</td>
                                        <td>
                                            <input type="date" name="birthday" class="form-control" value="${birthday != null ? birthday : ''}"/>
                                            <span class="text-danger">${errorBirthday}</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Gender:</td>
                                        <td>
                                            <select name="gender" class="form-select">
                                                <option value="Male" ${gender == 'Male' ? 'selected' : ''}>Male</option>
                                                <option value="Female" ${gender == 'Female' ? 'selected' : ''}>Female</option>
                                                <option value="Other" ${gender == 'Other' ? 'selected' : ''}>Other</option>
                                            </select>
                                            <span class="text-danger">${errorGender}</span>
                                        </td>
                                    </tr>
                                </tbody>



                                <!-- 👔 Trường Employee -->
                                <tbody id="employeeFields" style="display:none;">
                                    <tr>
                                        <td>Employee Code:</td>
                                        <td>
                                            <input name="employeeCode" class="form-control" value="${employeeCode != null ? employeeCode : ''}"/>
                                            <span class="text-danger">${errorEmployeeCode}</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Contract Type:</td>
                                        <td>
                                            <input name="contractType" class="form-control" value="${contractType != null ? contractType : ''}"/>
                                            <span class="text-danger">${errorContractType}</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Start Date:</td>
                                        <td>
                                            <input type="date" name="startDate" class="form-control" value="${startDate != null ? startDate : ''}"/>
                                            <span class="text-danger">${errorStartDate}</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>End Date:</td>
                                        <td>
                                            <input type="date" name="endDate" class="form-control" value="${endDate != null ? endDate : ''}"/>
                                            <span class="text-danger">${errorEndDate}</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Department:</td>
                                        <td>
                                            <input name="department" class="form-control" value="${department != null ? department : ''}"/>
                                            <span class="text-danger">${errorDepartment}</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Position:</td>
                                        <td>
                                            <input name="position" class="form-control" value="${position != null ? position : ''}"/>
                                            <span class="text-danger">${errorPosition}</span>
                                        </td>
                                    </tr>
                                </tbody>



                                <!-- ✅ Nút submit -->
                                <tr>
                                    <td colspan="2" style="text-align: right;">
                                        <input type="submit" class="btn btn-primary" value="Add User"/>
                                    </td>
                                </tr>
                                </tbody>
                            </table>

                            <c:if test="${not empty error}">
                                <p style="color:red">${error}</p>
                            </c:if>
                        </form>
                        <script>
                            window.onload = function () {
                                const selectedRole = "${selectedRole}";
                                handleRoleChange({value: selectedRole});
                            };

                            function handleRoleChange(e) {
                                const role = e.value;
                                const customerFields = document.getElementById("customerFields");
                                const employeeFields = document.getElementById("employeeFields");

                                if (role === "Customer") {
                                    customerFields.style.display = "table-row-group";
                                    employeeFields.style.display = "none";
                                } else if (role === "Guest") {
                                    customerFields.style.display = "none";
                                    employeeFields.style.display = "none";
                                } else {
                                    customerFields.style.display = "none";
                                    employeeFields.style.display = "table-row-group";
                                }
                            }
                        </script>

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
