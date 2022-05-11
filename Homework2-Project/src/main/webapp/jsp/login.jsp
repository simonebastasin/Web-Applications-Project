
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="it.unipd.dei.wa2122.wadteam.resources.TypeUserEnum" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Login | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Login</li>
  </ol>
</nav>

<form method="POST" action="<c:url value="/session/login"/>" class="row g-3 needs-validation" novalidate>
    <div class="form-floating mb-3">
        <input type="text" class="form-control" id="identification" name="identification" required>
        <label for="identification">Username or Email </label>
        <div class="invalid-feedback">Insert your username or email.</div>

    </div>
    <div class="form-floating mb-3">
        <input type="password" class="form-control" id="password" name="password" required>
        <label for="password">Password </label>
        <div class="invalid-feedback">Insert your password.</div>

    </div>

    <div class="form-check">
        <input type="radio" class="form-check-input" id="CUSTOMER" name="usertype" required value="${TypeUserEnum.CUSTOMER}">
        <label class="form-check-label" for="CUSTOMER">Customer</label>
    </div>
    <div class="form-check mb-3">
        <input type="radio" class="form-check-input" id="EMPLOYEE" name="usertype" required value="${TypeUserEnum.EMPLOYEE}">
        <label class="form-check-label" for="EMPLOYEE">Employee</label>
        <div class="invalid-feedback">Select the type of user you are.</div>
    </div>

    <button class="btn btn-primary" type="submit">Login</button>

</form>


</div>
<c:import url="/jsp/include/footer.jsp"/>
<script src="<c:url value="/js/formValidation.js"/>"></script>
</body>
</html>
