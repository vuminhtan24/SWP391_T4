<%-- 
    Document   : table
    Created on : May 19, 2025, 2:41:19 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List,model.Bouquet" %>
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

            /* CSS */
            .search-form {
                display: flex;
                justify-content: flex-end; /* đẩy cả form về bên phải */
                align-items: center;
                gap: 8px;                /* khoảng cách giữa input và button */
                margin-top: 20px;               /* tùy chỉnh nếu cần */
                margin-right: 30px;
                padding: 0;
            }

            .search-form input[type="text"] {
                padding: 8px 12px;
                border: 1px solid #ccc;
                border-radius: 20px;     /* bo tròn ô tìm kiếm */
                outline: none;
                transition: border-color .2s;
            }

            .search-form input[type="text"]:focus {
                border-color: #007bff;
            }

            .search-form button {
                padding: 8px 16px;
                background-color: #007bff; /* màu xanh dương */
                color: #fff;
                border: none;
                border-radius: 20px;       /* bo tròn nút */
                cursor: pointer;
                transition: background-color .2s;
            }

            .search-form button:hover {
                background-color: #0056b3; /* xanh đậm khi hover */
            }

            .btn {
                padding: 6px 12px;
                border: none;
                border-radius: 4px;
                color: white;
                font-size: 14px;
                cursor: pointer;
                margin-right: 5px;
                transition: background-color 0.2s ease;
            }

            .btn-delete {
                background-color: #e74c3c; /* đỏ */
            }

            .btn-delete:hover {
                background-color: #c0392b;
            }

            .btn-edit {
                background-color: #3498db; /* xanh dương */
            }

            .btn-edit:hover {
                background-color: #2980b9;
            }

            .fixed-container {
                /* Chiều rộng và chiều cao cố định */
                width: 1400px;        /* hoặc bất kỳ độ rộng bạn muốn */
                height: 720px;        /* hoặc tùy chỉnh theo chiều cao mong muốn */

                /* Chặn co giãn */
                min-width: 1400px;
                max-width: 1400px;
                min-height: 720px;
                max-height: 720px;

                /* Nếu nội dung vượt thì scroll */
                overflow: auto;
            }

            th.sortable {
                cursor: pointer;
            }
            th.sortable.asc::after  {
                content: " ▲";
            }
            th.sortable.desc::after {
                content: " ▼";
            }

            /* Chiều cao cố định 800px (thay 800px bằng giá trị bạn muốn) */
            .fixed-height-container {
                height: 760px;
                /* nếu muốn nội dung vượt vẫn scroll được: */
                overflow-y: auto;
            }
            /* Hoặc chỉ đảm bảo không thấp hơn 800px: */
            .min-height-container {
                min-height: 800px;
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
                <jsp:include page="/DashMin/navbar.jsp"/> <!-- nav bar -->
                <!-- HTML -->

                <!-- Table Start -->
                <div class="container-fluid pt-4 px-4 fixed-height-container">

                    <div class="bg-light rounded h-100 p-4 d-flex flex-column">
                        <!-- Header with title and Add Bouquet button -->
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h6 class="mb-0">Bouquet List</h6>
                            <a href="${pageContext.request.contextPath}/addBouquet" class="btn btn-primary">Add Bouquet</a>
                        </div>
                        <form action="viewBouquet" method="get"
                              style="display:flex; justify-content:flex-start; align-items:center; margin-bottom:1rem; width:100%;">
                            <!-- Phần filter category ở bên trái -->
                            <div style="display:flex; align-items:center;">
                                <label for="categoryS" style="margin-right:0.5rem; white-space:nowrap;">
                                    Category:
                                </label>
                                <select name="categoryS"
                                        id="categoryS"
                                        onchange="this.form.submit()"
                                        style="
                                        width:auto;
                                        min-width:max-content;
                                        padding:0.25rem 0.5rem;
                                        border:1px solid #ccc;
                                        border-radius:0.5rem;
                                        background-color:#fff;
                                        ">
                                    <option value="" <c:if test="${empty param.categoryS}">selected="selected"</c:if>>
                                            -- Mặc định --
                                        </option>
                                    <c:forEach var="cateS" items="${cateBouquetHome}">
                                        <option value="${cateS.getCategoryId()}"
                                                <c:if test="${param.categoryS eq cateS.getCategoryId()}">
                                                    selected="selected"
                                                </c:if>>
                                            ${cateS.getCategoryName()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <!-- Phần filter theo Raw Flower cách 50px -->    
                            <div style="display:flex; align-items:center;">
                                <label for="flowerS"
                                       style="margin-right:0.5rem; white-space:nowrap; margin-left:50px">
                                    Flower:
                                </label>
                                <select name="flowerS"
                                        id="flowerS"
                                        onchange="this.form.submit()"
                                        style="
                                        width:auto;
                                        min-width:max-content;
                                        padding:0.25rem 0.5rem;
                                        border:1px solid #ccc;
                                        border-radius:0.5rem;
                                        background-color:#fff;
                                        ">
                                    <!-- Option mặc định -->
                                    <option value=""
                                            <c:if test="${empty param.flowerS}">
                                                selected="selected"
                                            </c:if>>
                                        -- Mặc định --
                                    </option>

                                    <!-- Duyệt listFlower và hiển thị -->
                                    <c:forEach var="flower" items="${listFlower}">
                                        <option value="${flower.getRawId()}"
                                                <c:if test="${param.flowerS eq flower.getRawId()}">
                                                    selected="selected"
                                                </c:if>>
                                            ${flower.getRawName()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>           

                            <!-- Phần search ở bên phải -->
                            <div style="display:flex; align-items:center; margin-left:auto;">
                                <input type="text"
                                       name="bouquetName"
                                       placeholder="Tìm kiếm sản phẩm"
                                       value="${param.bouquetName}"
                                       style="
                                       width:200px;
                                       padding:0.25rem 0.5rem;
                                       border:1px solid #ccc;
                                       border-radius:0.5rem;
                                       margin-right:0.5rem;
                                       " />
                                <button type="submit"
                                        style="
                                        padding:0.25rem 0.75rem;
                                        border:none;
                                        border-radius:0.5rem;
                                        background-color:#007bff;
                                        color:#fff;
                                        cursor:pointer;
                                        ">
                                    Search
                                </button>
                            </div>
                        </form>


                        <div class="table-responsive flex-grow-1 mb-3" style="overflow-y:auto;">
                            <table class="table" id="bouquetTable">
                                <thead>
                                    <tr>
                                        <th scope="col">STT</th>
                                        <th scope="col">Image</th>
                                        <th scope="col" class="sortable" data-type="string">Bouquet Name</th>
                                        <th scope="col" class="sortable" data-type="string">Category</th>
                                        <th scope="col" class="sortable" data-type="number">Price</th>
                                        <th colspan="2">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="bouquet" items="${listBouquet}" varStatus="status">
                                        <tr>
                                            <td>${(currentPage - 1) * 6 + status.index + 1}</td>
                                            <td>
                                                <c:forEach var="img" items="${listImage}">
                                                    <c:if test="${bouquet.getBouquetId() == img.getbouquetId()}">
                                                        <img src="${pageContext.request.contextPath}/upload/BouquetIMG/${img.getImage_url()}" alt="alt" style="width: 60px; height: 60px;"/>
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/bouquetDetails?id=${bouquet.getBouquetId()}" class="change-color-qvm">
                                                    ${bouquet.getBouquetName()}
                                                </a>
                                            </td>
                                            <td>
                                                <c:set var="matched" value="false"/>
                                                <c:forEach var="cate" items="${cateBouquetHome}">
                                                    <c:if test="${cate.getCategoryId() == bouquet.getCid()}">
                                                        ${cate.getCategoryName()}
                                                        <c:set var="matched" value="true"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${!matched}">Unknown</c:if>
                                                </td>

                                                <td>${bouquet.getPrice()} VND</td>
                                            <td>
                                                <button type="button"
                                                        class="btn btn-delete"
                                                        onclick="if (confirm('Do you want to delete?'))
                                                                    location.href = '${pageContext.request.contextPath}/deleteBouquet?id=${bouquet.getBouquetId()}';">
                                                    Delete
                                                </button>


                                                <button type="button"
                                                        class="btn btn-edit"
                                                        onclick="location.href = '${pageContext.request.contextPath}/editBouquet?id=${bouquet.getBouquetId()}';">
                                                    Edit
                                                </button>
                                            </td>  

                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>    

                        <c:if test="${totalPages > 1}">
                            <div class="mt-auto">
                                <nav>
                                    <ul class="pagination">

                                        <!-- Previous -->
                                        <c:url var="prevUrl" value="viewBouquet">
                                            <c:param name="page"         value="${currentPage - 1}" />
                                            <c:param name="bouquetName"  value="${bouquetName}" />
                                            <c:param name="categoryS"    value="${param.categoryS}" />
                                            <c:if test="${not empty param.flowerS}">
                                                <c:param name="flowerS"    value="${param.flowerS}" />
                                            </c:if>
                                        </c:url>
                                        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                            <a class="page-link" href="${prevUrl}">Previous</a>
                                        </li>

                                        <!-- Các trang -->
                                        <c:forEach var="i" begin="1" end="${totalPages}">
                                            <c:url var="pageUrl" value="viewBouquet">
                                                <c:param name="page"        value="${i}" />
                                                <c:param name="bouquetName" value="${bouquetName}" />
                                                <c:param name="categoryS"   value="${param.categoryS}" />
                                                <c:if test="${not empty param.flowerS}">
                                                    <c:param name="flowerS"   value="${param.flowerS}" />
                                                </c:if>
                                            </c:url>
                                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                <a class="page-link" href="${pageUrl}">${i}</a>
                                            </li>
                                        </c:forEach>

                                        <!-- Next -->
                                        <c:url var="nextUrl" value="viewBouquet">
                                            <c:param name="page"        value="${i}" />
                                            <c:param name="bouquetName" value="${bouquetName}" />
                                            <c:param name="categoryS"   value="${param.categoryS}" />
                                            <c:if test="${not empty param.flowerS}">
                                                <c:param name="flowerS"   value="${param.flowerS}" />
                                            </c:if>
                                        </c:url>
                                        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                            <a class="page-link" href="${nextUrl}">Next</a>
                                        </li>

                                    </ul>
                                </nav>
                            </div>        
                        </c:if>



                    </div>
                </div>
                <!-- Table End -->


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
    <script>
                                                            (function () {
                                                                const table = document.getElementById('bouquetTable');
                                                                const ths = table.querySelectorAll('th.sortable');
                                                                let sortCol = -1, sortDir = 1; // 1 = asc, -1 = desc

                                                                ths.forEach((th, idx) => {
                                                                    th.addEventListener('click', () => {
                                                                        // Xác định index cột thực tế trong row.cells
                                                                        const colIndex = Array.from(th.parentNode.children).indexOf(th);

                                                                        // Toggle direction nếu click lại cùng cột
                                                                        if (sortCol === colIndex)
                                                                            sortDir = -sortDir;
                                                                        else {
                                                                            // reset các dấu ▲▼
                                                                            ths.forEach(h => h.classList.remove('asc', 'desc'));
                                                                            sortCol = colIndex;
                                                                            sortDir = 1;
                                                                        }
                                                                        th.classList.add(sortDir > 0 ? 'asc' : 'desc');

                                                                        const tbody = table.tBodies[0];
                                                                        // Lấy tất cả <tr> thành array
                                                                        const rows = Array.from(tbody.rows);

                                                                        // Xác định type (string/number)
                                                                        const type = th.getAttribute('data-type');

                                                                        rows.sort((a, b) => {
                                                                            let va = a.cells[colIndex].textContent.trim();
                                                                            let vb = b.cells[colIndex].textContent.trim();

                                                                            if (type === 'number') {
                                                                                va = parseFloat(va.replace(/[^\d.-]/g, '')) || 0;
                                                                                vb = parseFloat(vb.replace(/[^\d.-]/g, '')) || 0;
                                                                            } else {
                                                                                va = va.toLowerCase();
                                                                                vb = vb.toLowerCase();
                                                                            }
                                                                            return (va > vb ? 1 : va < vb ? -1 : 0) * sortDir;
                                                                        });

                                                                        // Đưa các row đã sort vào lại tbody
                                                                        rows.forEach(r => tbody.appendChild(r));
                                                                    });
                                                                });
                                                            })();
    </script>

</html>

