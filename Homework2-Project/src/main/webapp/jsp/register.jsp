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
    <ul>
        <li><label for="name">Name: </label> <input id="name" type="text" name="name"  required></li>
        <li><label for="surname">Surname: </label> <input id="surname" type="text" name="surname"  required></li>
        <li><label for="fiscalCode">Fiscal Code: </label><input id="fiscalCode" type="text" name="fiscalCode"  required></li>
        <li><label for="email">Email: </label><input id="email" type="email" name="email" required></li>
        <li><label for="phoneNumber">Phone Number: </label><input id="phoneNumber" type="text" name="phoneNumber" ></li>
        <li><label for="address">Address: </label><input id="address" type="text" name="address" ></li>
        <li><label for="username">Username: </label><input id="username" type="text" name="username"  required></li>
        <li><label for="password">Password: </label><input id="password" type="password" name="password"  required></li>
    </ul>
    <input type="submit" value="Sign Up">
</form>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
