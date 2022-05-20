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

    <title>Edit Customer | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav class="custom-breadcrumb-divider" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Edit Customer</li>
  </ol>
</nav>
<div id="liveAlertPlaceholder" class="sticky-top"></div>
<form class="row g-3 needs-validation" novalidate id="editUserForm">

    <div class="form-floating mb-3">
        <input type="text" id="name" name="name" class="form-control" value="${customer.name}" required placeholder="name">
        <label for="name">Name </label>
        <div class="invalid-feedback">Insert your name.</div>
    </div>
    <div class="form-floating mb-3">
        <input type="text" id="surname" name="surname" class="form-control" value="${customer.surname}" required placeholder="surname">
        <label for="surname">Surname </label>
        <div class="invalid-feedback">Insert your surname.</div>
    </div>
    <div class="form-floating mb-3">
        <input type="text" id="fiscalCode" name="fiscalCode" class="form-control" value="${customer.fiscalCode}" required placeholder="xxxxxxxxxxxxxxxx">
        <label for="fiscalCode">Fiscal Code </label>
        <div class="invalid-feedback">Insert your fiscal code.</div>
    </div>
    <div class="form-floating mb-3">
        <input type="text" id="phoneNumber" name="phoneNumber" class="form-control" value="${customer.phoneNumber}" placeholder="+39">
        <label for="phoneNumber">Phone Number </label>
    </div>
    <div class="form-floating mb-3">
        <input type="text" id="address" name="address" class="form-control" value="${customer.address}" placeholder="Via Padova 1, Padova (PD)">
        <label for="address">Address </label>
    </div>

    <div class="mb-3">
        <button class="btn btn-outline-primary" type="submit">Submit</button>
        <a href="<c:url value="/user/info"/>" class="btn btn-outline-danger" id="cancel">Cancel changes</a>
    </div>

</form>

</div>
<c:import url="/jsp/include/footer.jsp"/>
<script src="<c:url value="/js/user.js"/>"></script>
</body>
</html>
