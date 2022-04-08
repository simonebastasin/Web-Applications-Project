<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="customer" type="it.unipd.dei.wa2122.wadteam.resources.Customer"--%>
<html>
<head>
    <title>Change password ${customer.username}</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>
<form method="post" action="<c:url value="/user/CUSTOMER/password/${customer.username}"/>">
    <ul>
        <li>Old Password: <input type="password" name="oldPassword" required></li>
        <li>newPassword: <input type="password" name="newPassword" required></li>
        <input type="hidden" value="${customer.username}" name="username">
        <input type="submit" value="submit">

    </ul>

</form>

</body>
</html>

