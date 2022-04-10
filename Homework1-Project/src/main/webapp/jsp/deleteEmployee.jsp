<%--
  Created by IntelliJ IDEA.
  User: matteolando
  Date: 10/04/2022
  Time: 21:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Delete Employee</title>
</head>
<body>

<c:forEach var="user" items="${employeeList}">
    Are you sure you want to delete the employee ${user.username}?
    <form method="POST" action="<c:url value="/Admin/UserManagmentServlet/delete/${user.username}"/>">
        <p>Evidence:</p><br>
        <div>
            <input type="radio" id="yes" name="sure" value="yes">
            <label for="yes">Yes</label>

            <input type="radio" id="no" name="sure" value="no" checked>
            <label for="no">No</label>
        </div>

        <input type ="submit" value = "i">
    </form>

</c:forEach>




</body>
</html>
