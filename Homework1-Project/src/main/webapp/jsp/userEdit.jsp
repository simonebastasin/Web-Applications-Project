<%--
  Created by IntelliJ IDEA.
  User: matte
  Date: 07/04/2022
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="employee" type="it.unipd.dei.wa2122.wadteam.resources.Employee"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="Electromechanics Shop">
    <meta name="author" content="WAD Team">

    <title>Employee: ${employee.username} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Employee: ${employee.username}</h1>

<form method="post" action="<c:url value="/user/modify"/>">
    <ul>
        <li>Name: <input type="text" name="name" value="${employee.name}" required></li>
        <li>Surname: <input type="text" name="surname" value="${employee.surname}" required></li>


        <input type="submit" value="submit">
    </ul>
</form>

<%@ include file="/html/include/footer.html"%>
</body>
</html>
