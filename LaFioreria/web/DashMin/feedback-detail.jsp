<%-- 
    Document   : feedback-detail
    Created on : Jul 15, 2025, 11:15 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="en_US" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>DASHMIN - Feedback Detail</title>
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
        .feedback-img {
            max-height: 600px;
            width: auto;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.1);
            margin-left: 30px;
        }

        .feedback-info {
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
            .feedback-img {
                margin-left: 0;
                max-width: 100%;
                height: auto;
            }
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

        .btn-back {
            background-color: #3498db; /* xanh dương */
        }

        .btn-back:hover {
            background-color: #2980b9;
        }

        .arrow-btn {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            background-color: transparent;
            border: none;
            font-size: 36px;
            color: #bbb;
            cursor: pointer;
            padding: 0;
            margin: 0;
            transition: color 0.2s ease;
            z-index: 10;
        }

        .arrow-btn:hover {
            color: #333;
        }

        .left-arrow {
            left: 10px;
        }

        .right-arrow {
            right: 5px;
        }

        #viewAllImages {
            color: #0d6efd;
            text-decoration: none;
            transition: background-color 0.2s ease, color 0.2s ease;
            padding: 4px 12px;
            border-radius: 6px;
        }

        #viewAllImages:hover {
            background-color: #e0e0e0;
            color: #000000;
            text-decoration: none;
        }
        .star-rating {
                color: #ffd700;
                font-size: 16px;
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
                    <a href="${pageContext.request.contextPath}/viewBouquet" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Bouquet</a>
                    <a href="${pageContext.request.contextPath}/DashMin/chart.jsp" class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
                    <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="nav-item nav-link"><i class="fa fa-table me-2"></i>FlowerType</a>
                    <a href="${pageContext.request.contextPath}/category" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Category</a>
                    <a href="${pageContext.request.contextPath}/orderManagement" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Order</a>
                   <a href="${pageContext.request.contextPath/listWholeSaleRequest}" class="nav-item nav-link"><i class="fa fa-table me-2"></i>WholeSale Management</a>  
                    <a href="${pageContext.request.contextPath}/adminFeedback" class="nav-item nav-link active"><i class="fa fa-table me-2"></i>Feedback</a>
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
            <!-- Navbar Start -->
            <nav class="navbar navbar-expand bg-light navbar-light sticky-top px-4 py-0">
                <a href="${pageContext.request.contextPath}/DashMin/admin" class="navbar-brand d-flex d-lg-none me-4">
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
                                    <img class="rounded-circle" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">
                                    <div class="ms-2">
                                        <h6 class="fw-normal mb-0">Jhon send you a message</h6>
                                        <small>15 minutes ago</small>
                                    </div>
                                </div>
                            </a>
                            <hr class="dropdown-divider">
                            <a href="#" class="dropdown-item text-center">See all messages</a>
                        </div>
                    </div>
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                            <i class="fa fa-bell me-lg-2"></i>
                            <span class="d-none d-lg-inline-flex">Notification</span>
                        </a>
                        <div class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
                            <a href="#" class="dropdown-item">
                                <h6 class="fw-normal mb-0">Profile updated</h6>
                                <small>15 minutes ago</small>
                            </a>
                            <hr class="dropdown-divider">
                            <a href="#" class="dropdown-item text-center">See all notifications</a>
                        </div>
                    </div>
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
                            <img class="rounded-circle me-lg-2" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">
                            <span class="d-none d-lg-inline-flex">John Doe</span>
                        </a>
                        <div class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
                            <a href="#" class="dropdown-item">My Profile</a>
                            <a href="#" class="dropdown-item">Settings</a>
                            <a href="${pageContext.request.contextPath}/ZeShopper/LogoutServlet" class="dropdown-item">Log Out</a>
                        </div>
                    </div>
                </nav>
                <!-- Navbar End -->

                <!-- Feedback Detail Start -->
                <div class="background-qvm" style="background-color: #f3f6f9; margin-top: 20px; margin-left: 20px; margin-right: 20px; border-radius: 5px;">
                    <div class="container-fluid py-5 px-4">
                        <div class="d-flex flex-wrap align-items-start gap-5" style="gap: 100px;">

                            <!-- Cột ảnh -->
                            <c:if test="${not empty feedbackImages and not empty feedbackImages[0]}">
                                <div class="position-relative d-inline-block mb-3">
                                    <!-- Nút Prev -->
                                    <button id="prevImage" class="arrow-btn left-arrow" aria-label="Previous image" style="margin-left: 25px;">
                                        ❮
                                    </button>

                                    <!-- Ảnh chính -->
                                    <img id="mainImage" src="${pageContext.request.contextPath}/upload/FeedbackIMG/${feedbackImages[0]}" 
                                         alt="Feedback Image" class="img-fluid feedback-img mb-2" 
                                         style="width: 550px; height: 600px; object-fit: cover; border-radius: 12px;" />

                                    <!-- Nút Next -->
                                    <button id="nextImage" class="arrow-btn right-arrow" aria-label="Next image">
                                        ❯
                                    </button>

                                    <!-- Link mở popup tất cả ảnh -->
                                    <div class="text-center mt-2">
                                        <a href="#" id="viewAllImages" class="btn btn-link">View All Images</a>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${empty feedbackImages or empty feedbackImages[0]}">
                                <div class="position-relative d-inline-block mb-3">
                                    <img id="mainImage" src="${pageContext.request.contextPath}/img/no-image.jpg" 
                                         alt="No Image Available" class="img-fluid feedback-img mb-2" 
                                         style="width: 550px; height: 600px; object-fit: cover; border-radius: 12px;" />
                                </div>
                            </c:if>

                            <!-- Modal hiển thị tất cả ảnh -->
                            <div class="modal fade" id="allImagesModal" tabindex="-1" aria-labelledby="allImagesModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-scrollable modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">All Images</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="d-flex flex-wrap gap-3 justify-content-center">
                                                <c:forEach var="imgUrl" items="${feedbackImages}">
                                                    <div class="flex-shrink-0 text-center">
                                                        <img src="${pageContext.request.contextPath}/upload/FeedbackIMG/${imgUrl}" 
                                                             alt="Feedback Image" class="img-fluid feedback-img mb-2" 
                                                             style="width: 300px; height: 300px; object-fit: cover; border-radius: 8px;" />
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Cột phải: Nội dung -->
                            <div class="flex-grow-1 bouquet-content">
                                <div class="feedback-info p-4 shadow-sm rounded bg-white">
                                    <h1 class="fw-bold mb-4 text-primary">
                                        Feedback Detail #${feedback.feedbackId}
                                    </h1>

                                    <!-- Thông tin cơ bản -->
                                    <div class="mb-3">
                                        <label class="form-label fw-semibold">Bouquet Name:</label>
                                        <h6>${feedback.bouquetName}</h6>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label fw-semibold">Customer:</label>
                                        <h6>${feedback.customerName}</h6>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label fw-semibold">Rating:</label>
                                        <h6>
                                            <c:forEach begin="1" end="${feedback.rating}">
                                                <i class="fas fa-star star-rating"></i>
                                            </c:forEach>
                                        </h6>
                                    </div>

                                    <div class="mb-4">
                                        <label class="form-label fw-semibold">Comment:</label>
                                        <textarea name="comment" class="form-control" rows="4" readonly>${feedback.comment}</textarea>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label fw-semibold">Created Date:</label>
                                        <h6><fmt:formatDate value="${feedback.createdAtAsDate}" pattern="dd/MM/yyyy HH:mm"/></h6>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label fw-semibold">Status:</label>
                                        <h6>${feedback.status}</h6>
                                    </div>

                                    <!-- Button Back -->
                                    <div class="d-flex justify-content-end">
                                        <button type="button" class="btn btn-back" 
                                                onclick="location.href='${pageContext.request.contextPath}/adminFeedback'">
                                            Back to Feedback List
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Feedback Detail End -->

                <!-- Footer Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded-top p-4">
                        <div class="row">
                            <div class="col-12 col-sm-6 text-center text-sm-start">
                                © <a href="#">Your Site Name</a>, All Right Reserved. 
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

        <script>
            const imageUrls = [
                <c:forEach var="imgUrl" items="${feedbackImages}" varStatus="status">
                    "${pageContext.request.contextPath}/upload/FeedbackIMG/${imgUrl}"<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];

            let currentIndex = 0;
            const mainImage = document.getElementById("mainImage");
            const prevBtn = document.getElementById("prevImage");
            const nextBtn = document.getElementById("nextImage");

            function updateImage() {
                if (imageUrls.length > 0) {
                    mainImage.src = imageUrls[currentIndex];
                }
            }

            prevBtn.addEventListener("click", () => {
                if (currentIndex > 0) {
                    currentIndex--;
                    updateImage();
                }
            });

            nextBtn.addEventListener("click", () => {
                if (currentIndex < imageUrls.length - 1) {
                    currentIndex++;
                    updateImage();
                }
            });

            const viewAllImagesLink = document.getElementById("viewAllImages");
            const allImagesModal = new bootstrap.Modal(document.getElementById("allImagesModal"));

            viewAllImagesLink.addEventListener("click", (e) => {
                e.preventDefault();
                allImagesModal.show();
            });

            // Khởi tạo ảnh đầu tiên
            updateImage();
        </script>
    </body>
</html>