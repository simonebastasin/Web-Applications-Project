<%--
  Created by IntelliJ IDEA.
  User: matte
  Date: 07/04/2022
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="customer" type="it.unipd.dei.wa2122.wadteam.resources.Customer"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Register | Electromechanics Shop</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Register</li>
  </ol>
</nav>

<form method="post" action="<c:url value="/session/register"/>">
    <div class="mb-3">
        <label for="name" class="form-label">Name:</label>
        <input id="name" type="text" class="form-control" name="name" required placeholder="name">
    </div>
    <div class="mb-3">
        <label for="surname" class="form-label">Surname:</label>
        <input id="surname" type="text" class="form-control" name="surname" required placeholder="surname">
    </div>
    <div class="mb-3">
        <label for="fiscalCode" class="form-label">Fiscal Code:</label>
        <input id="fiscalCode" type="text" class="form-control" name="fiscalCode" required placeholder="xxxxxxxxxxxxxxxx">
    </div>
    <div class="mb-3">
        <label for="email" class="form-label">Email:</label>
        <input id="email" type="email" class="form-control" name="email" required placeholder="name@example.com">
    </div>
    <div class="mb-3">
        <label for="phoneNumber" class="form-label">Phone Number:</label>
        <input id="phoneNumber" type="text" class="form-control" name="phoneNumber" placeholder="+39">
    </div>
    <div class="mb-3">
        <label for="address" class="form-label">Address:</label>
        <input id="address" type="text" class="form-control" name="address" placeholder="Via Padova 1, Padova (PD)">
    </div>
    <div class="mb-3">
        <label for="username" class="form-label">Username:</label>
        <input id="username" type="text" class="form-control" name="username" placeholder="username" required>
    </div>
    <div class="mb-3">
        <label for="password" class="form-label">Password:</label>
        <input id="password" type="text" class="form-control" name="password" placeholder="password" required>
    </div>
    <input type="submit" value="Sign Up" class = "btn btn-primary">
</form>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
