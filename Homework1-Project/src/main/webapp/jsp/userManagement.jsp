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
  <title>User List</title>
</head>

<body>

<c:import url="/jsp/include/header.jsp"/>
<h1>User List</h1>
<table>



  <tr>
    <th>Username</th>
    <th>Name</th>
    <th>Surname</th>
    <th>Role</th>
    <th>Edit</th>
    <th>Delete</th>

  </tr>


  <c:forEach var="user" items="${employeeList}">

    <tr>
      <td>${user.username}</td>
      <td>${user.name}</td>
      <td>${user.surname}</td>
      <td>${user.role}</td>
      <td>
      <form method="POST" action="<c:url value="/Admin/UserManagmentServlet/delete/${user.username}"/>">
        <input type ="submit" value = "i">
      </form>
      </td>
      <td>
        <a href="<c:url value="/Admin/UserManagmentServlet/delete/${user.username}"/>">X</a>
      </td>

    </tr>

  </c:forEach>

</table>

</body>
</html>

