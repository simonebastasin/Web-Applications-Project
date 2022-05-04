<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 31/03/2022
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="user" type="it.unipd.dei.wa2122.wadteam.resources.UserCredential"--%>
<%--@elvariable id="employee" type="it.unipd.dei.wa2122.wadteam.resources.Employee"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>User: <c:out value="${employee.username}"/> | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">User: <c:out value="${employee.username}"/></li>
  </ol>
</nav>

<ul>
    <li>Name: ${employee.name}</li>
    <li>Surname: ${employee.surname}</li>
    <li>Role: ${employee.role.toString()}</li>
    <br>
    <a href="<c:url value="/user/modify"/>">Edit</a>
    <a href="<c:url value="/user/password"/>">Change Password</a>
</ul>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>