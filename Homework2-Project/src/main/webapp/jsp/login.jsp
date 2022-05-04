
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

<form method="POST" action="<c:url value="/session/login"/>">
    <input type="text" name ="identification" id="identification">
    <label for = "identification">username or email</label>
    <br>
    <input type ="password" name ="password" id ="password">
    <label for = "password">password</label>
    <br>
    <select name="usertype" id="usertype">
        <option value ="${TypeUserEnum.CUSTOMER}">Customer</option>
        <option value ="${TypeUserEnum.EMPLOYEE}">Employee</option>
    </select>
    <br><br>
    <input type ="submit" value = "Login">
</form>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
