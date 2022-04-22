<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="customer" type="it.unipd.dei.wa2122.wadteam.resources.Customer"--%>
<%--@elvariable id="user" type="it.unipd.dei.wa2122.wadteam.resources.UserCredential"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="Electromechanics Shop">
    <meta name="author" content="WAD Team">

    <title>Change password ${user.identification} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Change password</h1>

<form method="post" action="<c:url value="/user/password"/>">
    <ul>
        <li>Old Password: <input type="password" name="oldPassword" required></li>
        <br>
        <li>newPassword: <input type="password" name="newPassword" required></li>

        <input type="submit" value="submit">
    </ul>
</form>

<%@ include file="/html/include/footer.html"%>
</body>
</html>

