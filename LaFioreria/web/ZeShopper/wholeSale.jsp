<%-- 
    Document   : wholeSale
    Created on : Jul 13, 2025, 11:56:18 PM
    Author     : ADMIN
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
    <head>
        <title>Cảm ơn vì đơn hàng của bạn</title>
        <link href="${pageContext.request.contextPath}/ZeShopper/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/responsive.css" rel="stylesheet">

        <style>
            /* Header cam */
            .table thead {
                background-color: #ff9800;
                color: white;
            }

            /* Căn giữa toàn bộ nội dung */
            .table th,
            .table td {
                vertical-align: middle;
                text-align: center;
            }

            /* Ghi đè sau để chắc chắn */
            .table td.note-cell {
                text-align: left;
                padding-left: 12px;
                max-width: 250px;
                overflow: auto;
                white-space: pre-wrap;
                word-wrap: break-word;
            }

            /* Tên giỏ + mô tả (nếu thêm sau) */
            .bouquet-name {
                font-weight: 600;
                font-size: 16px;
                color: #333;
            }

            /* Ảnh thu nhỏ */
            .table img {
                width: 60px;
                height: 60px;
                object-fit: cover;
                border-radius: 6px;
            }

            /* Nút Update */
            .btn-update {
                background-color: #f0f0f0;
                color: #333;
                border: 1px solid #ccc;
                padding: 4px 10px;
                border-radius: 4px;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .btn-update:hover {
                background-color: #ddd;
            }

            /* Nút Delete */
            .btn-delete {
                background-color: #e74c3c;
                color: #fff;
                border: none;
                padding: 5px 10px;
                border-radius: 4px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .btn-delete:hover {
                background-color: #c0392b;
            }

            .btn-update {
                background-color: #ff9800;
                color: white;
                border: none;
                padding: 6px 12px;
                border-radius: 4px;
                font-size: 14px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .btn-update:hover {
                background-color: #e68900;
            }

            /* Nút Delete: màu đỏ */
            .btn-delete {
                background-color: #e74c3c;
                color: white;
                border: none;
                padding: 6px 12px;
                border-radius: 4px;
                font-size: 14px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .btn-delete:hover {
                background-color: #c0392b;
            }
        </style>

    </head>
    <body>

        <jsp:include page="/ZeShopper/header.jsp"/>

        <div class="container mt-5">
            <c:choose>
                <c:when test="${param.confirmed eq 'true'}">
                    <div class="alert alert-success text-center p-5 bg-light rounded shadow">
                        <h4>Bạn đã gửi yêu cầu thành công!</h4>
                        <p>Vui lòng đợi chúng tôi phản hồi báo giá.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="text-center p-5 bg-light rounded shadow">
                        <input type="hidden" name="user_id" value="${sessionScope.currentAcc.userid}" />
                        <h4 class="mb-4">Danh sách giỏ hoa đặt theo lô</h4>

                        <c:if test="${not empty requestScope.wholesaleList}">
                            <table class="table table-bordered table-hover bg-white">
                                <thead class="thead-dark">
                                    <tr>
                                        <th class="text-center">STT</th>
                                        <th class="text-center">Tên giỏ</th>
                                        <th class="text-center">Ảnh</th>
                                        <th class="text-center">Số lượng</th>
                                        <th class="text-center">Ghi chú</th>
                                        <th class="text-center" colspan="2">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="count" value="0" />
                                    <c:forEach var="item" items="${wholesaleList}">
                                        <c:if test="${item.getStatus() eq 'SHOPPING'}">
                                            <c:set var="count" value="${count + 1}" />
                                            <tr>
                                                <td>${count}</td>

                                                <!-- TÊN GIỎ HOA -->
                                                <td>
                                                    <c:set var="bouquetName" value="" />
                                                    <c:forEach var="bouquet" items="${listBouquet}">
                                                        <c:if test="${item.getBouquet_id() eq bouquet.getBouquetId()}">
                                                            <c:set var="bouquetName" value="${bouquet.getBouquetName()}" />
                                                            ${bouquet.getBouquetName()}
                                                        </c:if>
                                                    </c:forEach>
                                                </td>

                                                <!-- ẢNH GIỎ HOA -->
                                                <td>
                                                    <c:set var="imageUrl" value="" />
                                                    <c:set var="imageShown" value="false" />
                                                    <c:forEach var="img" items="${images}">
                                                        <c:if test="${!imageShown and item.getBouquet_id() == img.getbouquetId()}">
                                                            <c:set var="imageUrl" value="${img.getImage_url()}" />
                                                            <img src="${pageContext.request.contextPath}/upload/BouquetIMG/${img.getImage_url()}" alt="alt"
                                                                 style="width: 60px; height: 60px;" />
                                                            <c:set var="imageShown" value="true" />
                                                        </c:if>
                                                    </c:forEach>
                                                </td>

                                                <!-- SỐ LƯỢNG + GHI CHÚ -->
                                                <td>${item.requested_quantity}</td>
                                                <td class="note-cell" style="text-align: left; padding-left: 12px;">${item.note}</td>

                                                <!-- ACTION BUTTONS -->
                                                <td style="white-space: nowrap; min-width: 150px; text-align: center;">
                                                    <button type="button"
                                                            class="btn-update"
                                                            data-toggle="modal"
                                                            data-target="#updateModal"
                                                            data-name="${bouquetName}"
                                                            data-image="${pageContext.request.contextPath}/upload/BouquetIMG/${imageUrl}"
                                                            data-quantity="${item.requested_quantity}"
                                                            data-bouquet-id="${item.bouquet_id}"
                                                            data-note="${item.note}">
                                                        Update
                                                    </button>

                                                    <button type="button" class="btn-delete">Delete</button>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>

                                </tbody>
                            </table>
                        </c:if>

                        <!-- Nút xác nhận gửi danh sách đặt theo lô -->
                        <form method="post" action="${pageContext.request.contextPath}/wholeSale?act=confirm">
                            <input type="hidden" name="user_id" value="${sessionScope.currentAcc.userid}" />
                            <button type="submit" class="btn btn-success btn-lg mt-3">
                                ✅ Xác nhận yêu cầu báo giá
                            </button>
                        </form>    

                        <!-- Modal -->
                        <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Cập nhật đơn hàng</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">×</button>
                                    </div>
                                    <form id="updateForm" method="post" action="${pageContext.request.contextPath}/wholeSale">
                                        <input type="hidden" name="user_id" value="${sessionScope.currentAcc.userid}" />
                                        <input type="hidden" id="modalBouquetId" name="bouquet_id" />
                                        <div class="modal-body text-center">
                                            <h4 id="modalBouquetName" class="mb-3"></h4>
                                            <img id="modalBouquetImage" src="" alt="Bouquet" class="mb-3" style="width: 180px; height: 180px; object-fit: cover; border-radius: 8px;" />

                                            <div class="form-group">
                                                <label for="updateQuantity"><strong>Số lượng:</strong></label>
                                                <input type="number" class="form-control text-center" name="requested_quantity" id="updateQuantity" required>
                                                <small id="updateQuantityError" class="text-danger d-none font-weight-bold"></small>
                                            </div>

                                            <div class="form-group">
                                                <label for="updateNote"><strong>Ghi chú:</strong></label>
                                                <textarea class="form-control" name="note" id="updateNote" rows="3"></textarea>
                                            </div>

                                            <!-- Có thể thêm hidden input chứa id đơn hàng nếu cần -->
                                            <input type="hidden" name="wholesale_id" id="updateWholesaleId">
                                        </div>
                                        <div class="modal-footer">
                                            <button name="action" type="submit" value="update" class="btn btn-warning">Update</button>
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:otherwise>                
            </c:choose>

        </div>

        <jsp:include page="/ZeShopper/footer.jsp"/>

        <script>
            $('#updateModal').on('show.bs.modal', function (event) {
                const button = $(event.relatedTarget); // Nút gọi modal

                const name = button.data('name');
                const image = button.data('image');
                const quantity = button.data('quantity');
                const note = button.data('note');
                const bouquetId = button.data('bouquet-id'); // 👈 Lấy bouquet_id

                const modal = $(this);
                modal.find('#modalBouquetName').text(name);
                modal.find('#modalBouquetImage').attr('src', image);
                modal.find('#updateQuantity').val(quantity);
                modal.find('#updateNote').val(note);
                modal.find('#modalBouquetId').val(bouquetId); // 👈 Gán vào hidden input
            });
        </script>
        <script>
            document.getElementById("updateForm").addEventListener("submit", function (e) {
                const qtyInput = document.getElementById("updateQuantity");
                const errorText = document.getElementById("updateQuantityError");

                const qty = parseInt(qtyInput.value);

                if (isNaN(qty) || qty < 50) {
                    e.preventDefault(); // Ngăn form submit
                    errorText.textContent = "Bạn nhập số lượng ít hơn 50, vui lòng nhập lại";
                    errorText.classList.remove("d-none");
                } else {
                    errorText.classList.add("d-none"); // Ẩn thông báo lỗi nếu hợp lệ
                }
            });
        </script>



    </body>
</html>
