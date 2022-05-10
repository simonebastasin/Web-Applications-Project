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
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Edit Customer</li>
  </ol>
</nav>

<form method="post" action="<c:url value="/user/modify"/>">

    <div class="mb-3">
        <label for="name" class="form-label">Name: </label>
        <input type="text" id="name" name="name" class="form-control" value="${customer.name}" required>
    </div>
    <div class="mb-3">
        <label for="surname" class="form-label">Surname: </label>
        <input type="text" id="surname" name="surname" class="form-control" value="${customer.surname}" required>
    </div>
    <div class="mb-3">
        <label for="fiscalCode" class="form-label">Fiscal Code: </label>
        <input type="text" id="fiscalCode" name="fiscalCode" class="form-control" value="${customer.fiscalCode}" required>
    </div>
    <div class="mb-3">
        <label for="phoneNumber" class="form-label">Phone Number: </label>
        <input type="text" id="phoneNumber" name="phoneNumber" class="form-control" value="${customer.phoneNumber}">
    </div>
    <div class="mb-3">
        <label for="address" class="form-label">Address: </label>
        <input type="text" id="address" name="address" class="form-control" value="${customer.address}">
    </div>

    <input type="submit" value="Submit" class = "btn btn-primary">
    <a href="<c:url value="/user/info"/>" class="btn btn-danger">Cancel changes</a>

</form>

</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
