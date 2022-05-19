<%--
  Created by IntelliJ IDEA.
  User: matte
  Date: 07/04/2022
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="customer" type="it.unipd.dei.wa2122.wadteam.resources.Customer"--%>
<%--@elvariable id="user" type="it.unipd.dei.wa2122.wadteam.resources.UserCredential"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Customer: ${customer.username} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Customer: ${customer.username}</li>
  </ol>
</nav>

<h5>User Info</h5>
<ul class="list-group list-group-flush">
    <li class="list-group-item">Name: ${customer.name}</li>
    <li class="list-group-item">Surname: ${customer.surname}</li>
    <li class="list-group-item">Address: ${customer.address}</li>
    <li class="list-group-item">Phone number: ${customer.phoneNumber}</li>
    <li class="list-group-item">Email: ${customer.email}</li>
    <li class="list-group-item">FiscalCode: ${customer.fiscalCode}</li>
</ul>
<br>

<a href="<c:url value="/user/modify"/>" class="btn" type="button">
    <i class="fa-solid fa-pen-to-square text-primary"></i>
    <span class="text-primary">Edit</span>
</a>
<a href="<c:url value="/user/password"/>" class="btn" type="button">
    <i class="fa-solid fa-key text-danger"></i>
    <span class="text-danger">Change Password</span>
</a>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
