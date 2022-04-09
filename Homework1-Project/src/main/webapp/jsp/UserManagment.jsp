<%--
  Created by IntelliJ IDEA.
  User: matteolando
  Date: 09/04/2022
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="userList" type="java.util.List<it.unipd.dei.wa2122.wadteam.resources.Employee>"--%>

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
    <th>Details</th>
  </tr>


  size ${userList.size()}
  <c:forEach var="user" items="${userList}">

    <tr>
      <td>${user.username}</td>
      <td>${user.name}</td>
      <td>${user.surname}</td>
      <td>${user.Role}</td>
      <td valign="middle"><button type="button">i</button></td>
    </tr>

  </c:forEach>

</table>

</body>
</html>

