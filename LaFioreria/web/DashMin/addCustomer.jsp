<!-- DashMin/addCustomer.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head><title>Add New Customer</title></head>
    <body>
        <h3>Add Customer Details</h3>
        <form action="adduserdetail" method="post">
            <table class="table table-bordered">
                <!-- ✅ Include phần dùng chung từ addnewuser.jsp -->
                <jsp:include page="/DashMin/addnewuserdetail.jsp" />

                <!-- 🧩 Thông tin riêng của Customer -->
                <tr>
                    <td>Customer Code:</td>
                    <td><input type="text" name="customerCode" class="form-control"></td>
                </tr>
                <tr>
                    <td>Join Date:</td>
                    <td><input type="date" name="joinDate" class="form-control"></td>
                </tr>
                <tr>
                    <td>Loyalty Points:</td>
                    <td><input type="number" name="loyaltyPoint" class="form-control"></td>
                </tr>
                <tr>
                    <td>Birthday:</td>
                    <td><input type="date" name="birthday" class="form-control"></td>
                </tr>
                <tr>
                    <td>Gender:</td>
                    <td>
                        <select name="gender" class="form-select">
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Other">Other</option>
                        </select>
                    </td>
                </tr>

                <!-- Ẩn role để servlet biết đây là Customer -->
                <tr>
                    <td colspan="2">
                        <input type="hidden" name="option" value="Customer">
                        <input type="submit" class="btn btn-primary" value="Add Customer">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
