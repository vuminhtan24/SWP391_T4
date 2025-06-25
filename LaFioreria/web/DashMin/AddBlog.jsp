<%-- 
    Document   : AddBlog
    Created on : Jun 25, 2025, 4:36:28 PM
    Author     : k16
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Add New Blog Post</title>
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

        <!-- Customized Bootstrap Stylesheet -->
        <link href="${pageContext.request.contextPath}/DashMin/css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="${pageContext.request.contextPath}/DashMin/css/style.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/bootstrap.min.css" rel="stylesheet">

        <!-- TinyMCE -->
        <script src="https://cdn.tiny.cloud/1/1qk6lietypbc7xlhdhrz0o4y11p4j7xvk0baumrxhcja768a/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>

        <style>
            /* Additional styles for the form */
            .form-card {
                background: white;
                border-radius: 0.5rem;
                box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
                border: 1px solid rgba(0, 0, 0, 0.125);
                overflow: hidden;
            }

            .form-card-header {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                padding: 1.5rem;
                margin: 0;
            }

            .form-card-header h4 {
                margin: 0;
                font-weight: 600;
                display: flex;
                align-items: center;
            }

            .form-card-body {
                padding: 2rem;
            }

            .form-row {
                margin-bottom: 1.5rem;
            }

            .form-label {
                font-weight: 600;
                color: #495057;
                margin-bottom: 0.75rem;
                display: block;
                font-size: 1.6rem;
            }

            .required::after {
                content: " *";
                color: #dc3545;
            }

            .form-control, .form-select {
                border-radius: 0.375rem;
                border: 1px solid #ced4da;
                padding: 0.75rem 1rem;
                font-size: 1.6rem;
                transition: all 0.3s ease;
            }

            .form-control:focus, .form-select:focus {
                border-color: #667eea;
                box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
            }

            .form-actions {
                background: #f8f9fa;
                padding: 1.5rem 2rem;
                border-top: 1px solid #e9ecef;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .btn-success-gradient {
                background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
                border: none;
                color: white;
                padding: 0.75rem 2rem;
                border-radius: 0.375rem;
                font-weight: 600;
                transition: all 0.3s ease;
            }

            .btn-success-gradient:hover {
                transform: translateY(-2px);
                box-shadow: 0 4px 12px rgba(40, 167, 69, 0.4);
                color: white;
            }

            .image-preview {
                max-width: 200px;
                max-height: 200px;
                border-radius: 0.375rem;
                border: 2px dashed #ced4da;
                padding: 1rem;
                text-align: center;
                display: none;
            }

            .image-preview img {
                max-width: 100%;
                height: auto;
                border-radius: 0.25rem;
            }

            .character-count {
                font-size: 1rem;
                color: #6c757d;
                text-align: right;
                margin-top: 0.25rem;
            }

            .breadcrumb-section {
                background: #f8f9fa;
                padding: 1rem 0;
                margin-bottom: 2rem;
                border-radius: 0.5rem;
            }

            .breadcrumb {
                background: none;
                padding: 0;
                margin: 0;
            }

            .breadcrumb-item a {
                color: #667eea;
                text-decoration: none;
            }

            .breadcrumb-item a:hover {
                text-decoration: underline;
            }

            .custom-modal {
                display: none;
                position: fixed;
                z-index: 9999;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5);
            }

            .custom-modal-content {
                background-color: white;
                margin: 15% auto;
                padding: 20px;
                border-radius: 10px;
                width: 90%;
                max-width: 400px;
                text-align: center;
                box-shadow: 0 4px 12px rgba(0,0,0,0.3);
            }

            .close-btn {
                float: right;
                font-size: 24px;
                font-weight: bold;
                cursor: pointer;
            }

            .success-header {
                color: green;
            }

            .error-header {
                color: red;
            }

            button {
                margin-top: 15px;
                padding: 8px 16px;
                border: none;
                background-color: #444;
                color: white;
                border-radius: 5px;
                cursor: pointer;
            }

            button:hover {
                background-color: #000;
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
                        <a href="${pageContext.request.contextPath}/viewBouquet" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Bouquet</a>
                        <a href="${pageContext.request.contextPath}/DashMin/chart.jsp" class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
                        <a href="${pageContext.request.contextPath}/orderManagement" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Order</a>
                        <a href="${pageContext.request.contextPath}/DashMin/rawflower2" class="nav-item nav-link"><i class="fa fa-table me-2"></i>RawFlower</a>
                        <a href="${pageContext.request.contextPath}/category" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Category</a>
                        <a href="${pageContext.request.contextPath}" class="nav-item nav-link"><i class="fa fa-table me-2"></i>La Fioreria</a>
                        <a href="${pageContext.request.contextPath}/blogmanager" class="nav-item nav-link active"><i class="fa fa-table me-2"></i>Blog Manager</a>
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
                <nav class="navbar navbar-expand bg-light navbar-light sticky-top px-4 py-0">
                    <a href="${pageContext.request.contextPath}/DashMin/admin.jsp" class="navbar-brand d-flex d-lg-none me-4">
                        <h2 class="text-primary mb-0"><i class="fa fa-hashtag"></i></h2>
                    </a>
                    <a href="#" class="sidebar-toggler flex-shrink-0">
                        <i class="fa fa-bars"></i>
                    </a>
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

                <!-- Main Content -->
                <div class="container-fluid pt-4 px-4">
                    <!-- Breadcrumb -->
                    <div class="breadcrumb-section">
                        <div class="container-fluid">
                            <nav aria-label="breadcrumb">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item">
                                        <a href="${pageContext.request.contextPath}/DashMin/admin.jsp">
                                            <i class="fas fa-home me-1"></i>Dashboard
                                        </a>
                                    </li>
                                    <li class="breadcrumb-item">
                                        <a href="${pageContext.request.contextPath}/blogmanager">Blog Manager</a>
                                    </li>
                                    <li class="breadcrumb-item active" aria-current="page">Add New Blog Post</li>
                                </ol>
                            </nav>
                        </div>
                    </div>

                    <!-- Add Blog Form -->
                    <div class="row">
                        <div class="col-12">
                            <div class="form-card">
                                <div class="form-card-header">
                                    <h4>
                                        <i class="fas fa-plus-circle me-2"></i>Create New Blog Post
                                    </h4>
                                </div>

                                <form action="${pageContext.request.contextPath}/blog/add" method="POST" enctype="multipart/form-data" id="blogForm">
                                    <div class="form-card-body">
                                        <div class="row">
                                            <!-- Title -->
                                            <div class="col-md-8">
                                                <div class="form-row">
                                                    <label for="title" class="form-label required">Blog Title</label>
                                                    <input type="text" class="form-control" id="title" name="title" 
                                                           placeholder="Enter blog post title..." maxlength="100" required>
                                                    <div class="character-count">
                                                        <span id="titleCount">0</span>/100 characters
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- Category -->
                                            <div class="col-md-4">
                                                <div class="form-row">
                                                    <label for="category" class="form-label required">Category</label>
                                                    <select class="form-select" id="category" name="cid" required>
                                                        <option value="">Select Category</option>
                                                        <!-- You'll need to populate this from your categories table -->
                                                        <c:forEach var="category" items="${cList}">
                                                            <option value="${category.categoryId}">${category.categoryName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <!-- Featured Image -->
                                            <div class="col-md-6">
                                                <div class="form-row">
                                                    <label for="image" class="form-label">Featured Image</label>
                                                    <input type="file" class="form-control" id="image" name="image" 
                                                           accept="image/*" onchange="previewImage(this)">
                                                    <small class="text-muted">Recommended size: 800x600px. Max size: 5MB</small>
                                                    <div class="image-preview mt-3" id="imagePreview">
                                                        <img id="previewImg" src="" alt="Preview">
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- Status -->
                                            <div class="col-md-3">
                                                <div class="form-row">
                                                    <label for="status" class="form-label required">Status</label>
                                                    <select class="form-select" id="status" name="status" required>
                                                        <option value="Active" selected>Active</option>
                                                        <option value="Hidden">Hidden</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <!-- Author (Hidden field, will be set from session) -->
                                            <div class="col-md-3">
                                                <div class="form-row">
                                                    <label class="form-label">Author</label>
                                                    <input type="text" class="form-control" value="Current User" readonly>
                                                    <input id="author" type="hidden" name="author_id" value="${sessionScope.currentAcc.userid != null ? sessionScope.currentAcc.userid : 1}">
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Short Description / Pre-context -->
                                        <div class="form-row">
                                            <label for="preContext" class="form-label required">Short Description</label>
                                            <textarea class="form-control" id="preContext" name="pre_context" rows="3" 
                                                      placeholder="Write a brief description or excerpt for this blog post..." 
                                                      maxlength="200" required></textarea>
                                            <div class="character-count">
                                                <span id="preContextCount">0</span>/200 characters
                                            </div>
                                            <small class="text-muted">This will appear as a preview on the blog listing page.</small>
                                        </div>

                                        <!-- Main Content -->
                                        <div class="form-row">
                                            <label for="content" class="form-label required">Blog Content</label>
                                            <textarea class="form-control" id="content" name="context" rows="15" 
                                                      placeholder="" required></textarea>
                                            <small class="text-muted">Use the rich text editor to format your content with images, links, and styling.</small>
                                        </div>
                                    </div>

                                    <!-- Form Actions -->
                                    <div class="form-actions">
                                        <div>
                                            <a href="${pageContext.request.contextPath}/blogmanager" class="btn btn-outline-gradient">
                                                <i class="fas fa-arrow-left me-2"></i>Back to Blog Manager
                                            </a>
                                        </div>
                                        <div class="action-buttons">
                                            <button id="submitbtn" type="button" class="btn btn-success-gradient" onclick="doSubmit()">
                                                <i class="fas fa-paper-plane me-2"></i>Publish Blog Post
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Footer Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded-top p-4">
                        <div class="row">
                            <div class="col-12 col-sm-6 text-center text-sm-start">
                                &copy; <a href="${pageContext.request.contextPath}">Lafioreria</a>, All Right Reserved. 
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Footer End -->
            </div>
            <!-- Content End -->
        </div>

        <!-- Success Modal -->
        <div id="successModal" class="custom-modal">
            <div class="custom-modal-content">
                <span class="close-btn" onclick="closeModal('successModal')">&times;</span>
                <h2 class="success-header">✅ Success</h2>
                <p>Your blog post has been saved successfully!</p>
                <button onclick="redirectAfterSuccess()">OK</button>
            </div>
        </div>

        <!-- Error Modal -->
        <div id="errorModal" class="custom-modal">
            <div class="custom-modal-content">
                <span class="close-btn" onclick="closeModal('errorModal')">&times;</span>
                <h2 class="error-header">❌ Error</h2>
                <p id="errorMessage">An error occurred while saving the blog post.</p>
                <button onclick="closeModal('errorModal')">Close</button>
            </div>
        </div>

        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>

        <!-- Template Javascript -->
        <script src="${pageContext.request.contextPath}/DashMin/js/main.js"></script>

        <script>
                    // Initialize TinyMCE Rich Text Editor
                    tinymce.init({
                        selector: '#content',
                        height: 400,
                        menubar: true,
                        plugins: [
                            'advlist autolink lists link image charmap print preview anchor',
                            'searchreplace visualblocks code fullscreen',
                            'insertdatetime media table paste code help wordcount'
                        ],
                        toolbar: 'undo redo | formatselect | ' +
                                'bold italic backcolor | alignleft aligncenter ' +
                                'alignright alignjustify | bullist numlist outdent indent | ' +
                                'removeformat | help | link image media | code preview fullscreen',
                        content_css: '//www.tiny.cloud/css/codepen.min.css',
                        setup: function (editor) {
                            editor.on('change', function () {
                                editor.save();
                            });
                        }
                    });

                    // Character counters
                    function updateCharacterCount(inputId, countId, maxLength) {
                        const input = document.getElementById(inputId);
                        const counter = document.getElementById(countId);

                        input.addEventListener('input', function () {
                            const currentLength = this.value.length;
                            counter.textContent = currentLength;

                            if (currentLength > maxLength * 0.9) {
                                counter.parentElement.style.color = '#dc3545';
                            } else if (currentLength > maxLength * 0.7) {
                                counter.parentElement.style.color = '#ffc107';
                            } else {
                                counter.parentElement.style.color = '#6c757d';
                            }
                        });
                    }

                    // Initialize character counters
                    updateCharacterCount('title', 'titleCount', 100);
                    updateCharacterCount('preContext', 'preContextCount', 200);
                    updateCharacterCount('metaDescription', 'metaDescCount', 160);

                    // Image preview function
                    function previewImage(input) {
                        const preview = document.getElementById('imagePreview');
                        const previewImg = document.getElementById('previewImg');

                        if (input.files && input.files[0]) {
                            const reader = new FileReader();

                            reader.onload = function (e) {
                                previewImg.src = e.target.result;
                                preview.style.display = 'block';
                            }

                            reader.readAsDataURL(input.files[0]);
                        } else {
                            preview.style.display = 'none';
                        }
                    }

                    function doSubmit() {
                        tinymce.triggerSave(); // Sync TinyMCE content

                        const title = document.getElementById('title').value.trim();
                        const category = document.getElementById('category').value;
                        const preContext = document.getElementById('preContext').value.trim();
                        const content = document.getElementById('content').value.trim();
                        const status = document.getElementById('status').value.trim();
                        const author = '${sessionScope.currentAcc.userid}';
                        const imageInput = document.getElementById('image');

                        if (!title || !category || !preContext || !content) {
                            showError('Please fill in all required fields.');
                            return;
                        }

                        if (!imageInput.files || !imageInput.files[0]) {
                            showError('Please upload an image.');
                            return;
                        }

                        // Button loading state
                        const btn = document.getElementById('submitbtn');
                        const originalText = btn.innerHTML;
                        btn.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Publishing...';
                        btn.disabled = true;

                        // Build FormData
                        const formData = new FormData();
                        formData.append('title', title);
                        formData.append('category', category);
                        formData.append('preContext', preContext);
                        formData.append('content', content);
                        formData.append('status', status);
                        formData.append('author', author);
                        formData.append('image', imageInput.files[0]);

                        fetch('${pageContext.request.contextPath}/blog/add', {
                            method: 'POST',
                            body: formData
                        })
                                .then(response => response.text())
                                .then(result => {
                                    if (result === 'success') {
                                        showSuccess();
                                    } else {
                                        showError(result || 'An unknown error occurred.');
                                    }
                                })
                                .catch(error => {
                                    console.error('Error:', error);
                                    showError('A network error occurred.');
                                })
                                .finally(() => {
                                    btn.innerHTML = originalText;
                                    btn.disabled = false;
                                });
                    }

                    function showSuccess() {
                        document.getElementById('successModal').style.display = 'block';
                    }

                    function showError(message) {
                        document.getElementById('errorMessage').textContent = message;
                        document.getElementById('errorModal').style.display = 'block';
                    }

                    function closeModal(id) {
                        document.getElementById(id).style.display = 'none';
                    }

                    // Auto-hide spinner
                    $(document).ready(function () {
                        setTimeout(function () {
                            $('#spinner').removeClass('show');
                        }, 1000);
                    });

                    // Form auto-save (optional)
                    let autoSaveTimer;
                    function startAutoSave() {
                        autoSaveTimer = setInterval(function () {
                            // Save form data to localStorage for recovery
                            const formData = {
                                title: document.getElementById('title').value,
                                category: document.getElementById('category').value,
                                preContext: document.getElementById('preContext').value,
                                content: tinymce.get('content').getContent(),
                                tags: document.getElementById('tags').value,
                                metaKeywords: document.getElementById('metaKeywords').value,
                                metaDescription: document.getElementById('metaDescription').value
                            };

                            localStorage.setItem('blogDraft', JSON.stringify(formData));
                            console.log('Auto-saved draft');
                        }, 30000); // Auto-save every 30 seconds
                    }
        </script>
    </body>
</html>