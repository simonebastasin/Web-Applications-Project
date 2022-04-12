<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 12/04/2022
  Time: 00:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Employee Management</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Employee List</h1>

<div>
    <a href="<c:url value="/management/userManagement/createEmployee"/>">
        Add new employee
    </a>
</div><br>

<table>

    <tr>
        <th>Username</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Role</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>

    <c:forEach var="employee" items="${employeeList}">
        <tr>
            <td>${employee.username}</td>
            <td>${employee.name}</td>
            <td>${employee.surname}</td>
            <td>${employee.role}</td>
            <td>
                <a href="<c:url value="/management/userManagement"/>">Edit</a>
            </td>
            <td>
                <form method="GET" action="<c:url value="/management/userManagement/deleteEmployee"/>">
                    <input type ="hidden" name = "employeeToDelete" value = ${employee.username}>
                    <input type ="submit" value = "Delete">
                </form>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>