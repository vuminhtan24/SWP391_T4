<%-- 
    Document   : addrawflower
    Created on : May 25, 2025, 10:38:37 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add Raw Flower - La Fioreria</title>
        <script src="https://kit.fontawesome.com/48a04e355d.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">
        <style>
            .gradient-form {
                background-color: #f8f9fa;
            }
            .logo {
                width: 150px;
                height: auto;
            }
            .card {
                background-color: #fff;
                box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
                border-radius: 15px;
            }
            .form-control:focus {
                border-color: #6cb2eb;
                box-shadow: 0 0 0 0.25rem rgba(108, 178, 235, 0.25);
            }
            .btn-primary {
                background-color: #6cb2eb;
                border-color: #6cb2eb;
            }
            .btn-primary:hover {
                background-color: #559ad8;
                border-color: #559ad8;
            }
            .form-label {
                font-weight: bold;
            }
        </style>
    </head>
    <body>

        <section class="h-100 gradient-form">
            <div class="container py-5">
                <div class="row justify-content-center align-items-center">
                    <div class="col-lg-8">
                        <div class="card">
                            <div class="card-body p-4">
                                <div class="text-center">
<!--                                    <img src="images/logo.jpg" alt="La Fioreria Logo" class="logo">-->
                                    <h4 class="mt-3 mb-4">🌸 La Fioreria</h4>
                                </div>
                                <form enctype="multipart/form-data" action="addRawFlower" method="post">
                                    <h1 class="mb-4">Add Raw Flower</h1>

                                    <div class="mb-3">
                                        <label class="form-label">Raw Flower Name:</label>
                                        <input type="text" class="form-control" name="rawName" placeholder="Enter raw flower name" required>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Quantity:</label>
                                        <input type="number" class="form-control" name="rawQuantity" min="0" required>
                                    </div>

                                    <div class="row mb-3">
                                        <div class="col-sm-6">
                                            <label class="form-label">Unit Price ($):</label>
                                            <input type="number" class="form-control" name="unitPrice" min="0" required>
                                        </div>
                                        <div class="col-sm-6">
                                            <label class="form-label">Import Price ($):</label>
                                            <input type="number" class="form-control" name="importPrice" min="0" required>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Expiration Date:</label>
                                        <input type="date" class="form-control" name="expirationDate" required>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Warehouse ID:</label>
                                        <select class ="form-select" name="warehouseId" required>
                                            <option value="1">Main Warehouse</option>
                                            <option value="2">Backup Warehouse</option>
                                        </select>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Image URL:</label>
                                        <input type="text" class="form-control" name="imageUrl" placeholder="Enter image URL" required>
                                    </div>

                                    <div class="text-center">
                                        <button class="btn btn-primary btn-block" type="submit">
                                            <i class="fas fa-plus me-2"></i> Add Raw Flower
                                        </button>
                                        <span class="text-danger mt-2">${requestScope.ms}</span>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>


