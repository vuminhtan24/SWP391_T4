<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head><title>Add New Employee</title></head>
    <body>
        <h3>Add Employee Details</h3>
        <form action="adduserdetail" method="post">
            <table class="table table-bordered">
                <!-- ✅ Phần dùng chung (name, pass, fullname, email, phone, address...) -->
                <jsp:include page="/DashMin/addnewuserdetail.jsp" />

                <!-- 🧩 Thông tin riêng của Employee -->
                <tr>
                    <td>Employee Code:</td>
                    <td><input type="text" name="employeeCode" class="form-control"></td>
                </tr>
                <tr>
                    <td>Contract Type:</td>
                    <td><input type="text" name="contractType" class="form-control"></td>
                </tr>
                <tr>
                    <td>Start Date:</td>
                    <td><input type="date" name="startDate" class="form-control"></td>
                </tr>
                <tr>
                    <td>End Date:</td>
                    <td><input type="date" name="endDate" class="form-control"></td>
                </tr>
                <tr>
                    <td>Department:</td>
                    <td><input type="text" name="department" class="form-control"></td>
                </tr>
                <tr>
                    <td>Position:</td>
                    <td><input type="text" name="position" class="form-control"></td>
                </tr>

                <!-- Role ẩn (vì đã xác định là employee) -->
                <tr>
                    <td colspan="2">
                        <input type="hidden" name="option" value="Seller">
                        <input type="submit" class="btn btn-primary" value="Add Employee">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
