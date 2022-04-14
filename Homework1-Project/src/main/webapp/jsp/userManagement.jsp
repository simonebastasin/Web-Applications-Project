<%--
  Created by IntelliJ IDEA.
  User: matteolando
  Date: 09/04/2022
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>User Management</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>User List</h1>

<div>
  <a href="<c:url value="/management/userManagement/createEmployee"/>">Add new employee</a>
</div><br>

<table>

  <tr>
    <th>Username</th>
    <th>Name</th>
    <th>Surname</th>
    <th>Role</th>
    <th>Password[debug]</th>
    <th>Edit</th>
    <th>Delete</th>
  </tr>

  <c:forEach var="employee" items="${employeeList}">
    <tr>
      <td>${employee.username}</td>
      <td>${employee.name}</td>
      <td>${employee.surname}</td>
      <td>${employee.role}</td>
      <td>${employee.password}</td>
      <td>
        <a href="<c:url value="/management/userManagement/editEmployee/${employee.username}"/>">Edit</a>
      </td>
      <td>
        <a href="<c:url value="/management/userManagement/editEmployee/${employee.username}"/>">
          Edit
        </a>
      </td>
      <td>
        <a href="<c:url value="/management/userManagement/deleteEmployee/${employee.username}"/>">
          Delete
        </a>
      </td>
    </tr>
  </c:forEach>

</table>

<%@ include file="/html/include/footer.html"%>
</body>
</html>