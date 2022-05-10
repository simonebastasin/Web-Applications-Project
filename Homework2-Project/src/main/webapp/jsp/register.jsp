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
    <div class="form-floating mb-3">
        <input id="name" type="text" class="form-control" name="name" required placeholder="name">
        <label for="name">Name:</label>
    </div>
    <div class="form-floating mb-3">
        <input id="surname" type="text" class="form-control" name="surname" required placeholder="surname">
        <label for="surname">Surname:</label>
    </div>
    <div class="form-floating mb-3">
        <input id="fiscalCode" type="text" class="form-control" name="fiscalCode" required placeholder="xxxxxxxxxxxxxxxx">
        <label for="fiscalCode">Fiscal Code:</label>
    </div>
    <div class="form-floating mb-3">
        <input id="email" type="email" class="form-control" name="email" required placeholder="name@example.com">
        <label for="email">Email:</label>
    </div>
    <div class="form-floating mb-3">
        <input id="phoneNumber" type="text" class="form-control" name="phoneNumber" placeholder="+39">
        <label for="phoneNumber">Phone Number:</label>
    </div>
    <div class="form-floating mb-3">
        <input id="address" type="text" class="form-control" name="address" placeholder="Via Padova 1, Padova (PD)">
        <label for="address">Address:</label>
    </div>
    <div class="form-floating mb-3">
        <input id="username" type="text" class="form-control" name="username" placeholder="username" required>
        <label for="username">Username:</label>
    </div>
    <div class="form-floating mb-3">
        <input id="password" type="text" class="form-control" name="password" placeholder="password" required>
        <label for="password">Password:</label>
    </div>
    <input type="submit" value="Sign Up" class = "btn btn-primary">
</form>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
