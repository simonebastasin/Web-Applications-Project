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
<html>
<head>
    <title>Register</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>
<form method="post" action="<c:url value="/session/register"/>">

    <ul>
        <li>Name: <input type="text" name="name"  required></li>
        <li>Surname: <input type="text" name="surname"  required></li>
        <li>Fiscal Code: <input type="text" name="fiscalCode"  required></li>
        <li>Email: <input type="email" name="email" required></li>
        <li>Phone Number: <input type="text" name="phoneNumber" ></li>
        <li>Address :<input type="text" name="address" ></li>
        <li>Username: <input type="text" name="username"  required></li>
        <li>Password: <input type="text" name="password"  required></li>

        </li>

        <input type="submit" value="submit">

    </ul>

</form>
<%@ include file="/html/include/footer.html"%>
</body>
</html>
