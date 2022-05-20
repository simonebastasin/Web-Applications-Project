<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 12/04/2022
  Time: 00:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="employee" type="it.unipd.dei.wa2122.wadteam.resources.Employee"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Delete Employee | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav class="custom-breadcrumb-divider" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Delete Employee</li>
  </ol>
</nav>

Employee you are deleting:<br>
<br>
<table>
    <tr>
        <th>Username</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Role</th>
    </tr>
    <tr>
        <td>${employee.username}</td>
        <td>${employee.name}</td>
        <td>${employee.surname}</td>
        <td>${employee.role}</td>
    </tr>
</table><br>
Sure to delete?<br>

<br>
<form method="POST" action="">
    <input type ="submit" value = "Yes">
</form>
<a href="<c:url value="/management/employeeManagement"/>">
    <input type ="submit" value = "No"/>
</a>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>