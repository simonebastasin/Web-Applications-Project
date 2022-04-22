
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="it.unipd.dei.wa2122.wadteam.resources.TypeUserEnum" %>
<html>
<head>
    <title>Login | Electromechanics Shop</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Login</h1>

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

<%@ include file="/html/include/footer.html"%>
</body>
</html>
