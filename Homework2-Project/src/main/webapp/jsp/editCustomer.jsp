<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 21/04/2022
  Time: 01:32
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

<c:forEach var="customer" items="${customerList}">
    <form method="POST" action="">

        <label for="id">ID:</label><br>
        <input type="text" id="id" name="id" value="${customer.id}" disabled><br>
        <label for="username">Username:</label><br>
        <input type="text" id="username" name="username" value="${customer.username}" disabled><br>
        <label for="name">Name:</label><br>
        <input type="text" id="name" name="name" value="${customer.name}"><br>
        <label for="surname">Surname:</label><br>
        <input type="text" id="surname" name="surname" value="${customer.surname}"><br>
        <label for="fiscalCode">fiscal_code:</label><br>
        <input type="text" id="fiscalCode" name="fiscalCode" value="${customer.fiscalCode}"><br>
        <label for="address">address:</label><br>
        <input type="text" id="address" name="address" value="${customer.address}"><br>
        <label for="email">email:</label><br>
        <input type="text" id="email" name="email" value="${customer.email}"><br>
        <label for="phoneNumber">Phone Number:</label><br>
        <input type="text" id="phoneNumber" name="phoneNumber" value="${customer.phoneNumber}"><br>

        <br>
        <input type="submit" value="Submit">

    </form>
</c:forEach>

<a href="<c:url value="/management/customerManagement"/>">
    Cancel changes
</a>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
