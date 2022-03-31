
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="POST" action="<c:url value="/login"/>">
    <input type="text" name ="identification" id="identification">
    <label for = "identification">username or email</label>
    <br>
    <input type ="password" name ="password" id ="password">
    <label for = "password">password</label>
    <br>
    <select name="usertype" id="usertype">
        <option value ="customer">Customer</option>
        <option value ="employee">Employee</option>
    </select>
    <br>
    <input type ="submit" value = "login">
</form>

</body>
</html>
