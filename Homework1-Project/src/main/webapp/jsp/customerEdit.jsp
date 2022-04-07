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
    <title>Customer ${customer.username}</title>
</head>
<body>
<form method="post" action="<c:url value="/user/CUSTOMER/modify/${customer.username}"/>">
    <ul>
        <li>Name: <input type="text" name="name" value="${customer.name}" required></li>
        <li>Surname: <input type="text" name="surname" value="${customer.surname}" required></li>
        <li>Fiscal Code: <input type="text" name="fiscalCode" value="${customer.fiscalCode}" required></li>
       <input type="hidden" name="email" value="${customer.email}" required>
        <li>Phone Number: <input type="text" name="phoneNumber" value="${customer.phoneNumber}"></li>
        <li>Address :<input type="text" name="address" value="${customer.address}"></li>

        <li>Password: <input type="password" id="password "name="password" disabled="true">
            <button onclick="disable()">change password</button>


        </li>

        <input type="hidden" value="${customer.username}" name="username">
        <input type="submit" value="submit">

    </ul>

</form>
<script>
    function disable()
    {
        document.getElementById("password").disabled=true;
    }
    function undisable()
    {
        document.getElementById("password").disabled=false ;
    }
</script>
</body>
</html>
