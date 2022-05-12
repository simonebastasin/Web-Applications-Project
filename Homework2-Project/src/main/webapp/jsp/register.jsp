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

<form method="post" action="<c:url value="/session/register"/>" class="row g-3 needs-validation" novalidate id="formConfirmPassword">
    <div class="form-floating mb-3">
        <input id="name" type="text" class="form-control" name="name" required placeholder="name">
        <label for="name">Name</label>
        <div class="invalid-feedback">Insert your name.</div>
    </div>
    <div class="form-floating mb-3">
        <input id="surname" type="text" class="form-control" name="surname" required placeholder="surname">
        <label for="surname">Surname</label>
        <div class="invalid-feedback">Insert your surname.</div>
    </div>
    <div class="form-floating mb-3">
        <input id="fiscalCode" type="text" class="form-control" name="fiscalCode" required placeholder="fiscal code">
        <label for="fiscalCode">Fiscal Code</label>
        <div class="invalid-feedback">Insert your fiscal code.</div>
    </div>
    <div class="form-floating mb-3">
        <input id="email" type="email" class="form-control" name="email" required placeholder="name@example.com">
        <label for="email">Email</label>
        <div class="invalid-feedback">Insert your email.</div>
    </div>
    <div class="form-floating mb-3">
        <input id="phoneNumber" type="text" class="form-control" name="phoneNumber" placeholder="+39">
        <label for="phoneNumber">Phone Number</label>
    </div>
    <div class="form-floating mb-3">
        <input id="address" type="text" class="form-control" name="address" placeholder="Via Padova 1, Padova (PD)">
        <label for="address">Address</label>
    </div>
    <div class="form-floating mb-3">
        <input id="username" type="text" class="form-control" name="username" placeholder="username" required>
        <label for="username">Username</label>
        <div class="invalid-feedback">Insert your username.</div>
    </div>
    <div class="form-floating mb-3">
        <input id="newPassword" type="password" class="form-control" name="newPassword" placeholder="password" required>
        <label for="newPassword">Password</label>
        <div class="invalid-feedback" id="newPasswordFeedback">Insert the password</div>
    </div>
    <div class="form-floating mb-3">
        <input id="confirmPassword" type="password" class="form-control" name="confirmPassword" placeholder="confirm_password" required>
        <label for="confirmPassword">Confirm Password</label>
        <div class="invalid-feedback" id="confirmPasswordFeedback">Confirm your password</div>
    </div>
    <div class="mb-3">
        <input type="submit" name="submit" id="submit" value="Sign Up" class = "btn btn-outline-primary">
    </div>
</form>


</div>
<c:import url="/jsp/include/footer.jsp"/>
<script src="<c:url value="/js/check-password.js"/>"></script>
</body>
</html>
