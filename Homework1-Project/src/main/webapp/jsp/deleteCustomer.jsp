<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 21/04/2022
  Time: 01:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="customer" type="it.unipd.dei.wa2122.wadteam.resources.Customer"--%>

<html>
<head>
    <title>Delete customer</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Delete customer</h1>

Customer you are deleting:<br>
<br>
<table>
    <tr>
        <th>ID</th>
        <th>Username</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Fiscal_Code</th>
        <th>Address</th>
        <th>Email</th>
        <th>Phone_Number</th>
    </tr>
    <tr>
        <td>${customer.id}</td>
        <td>${customer.username}</td>
        <td>${customer.name}</td>
        <td>${customer.surname}</td>
        <td>${customer.fiscalCode}</td>
        <td>${customer.address}</td>
        <td>${customer.email}</td>
        <td>${customer.phoneNumber}</td>
    </tr>
</table><br>
Sure to delete?<br>

<br>
<form method="POST" action="">
    <input type ="submit" value = "Yes">
</form>
<a href="<c:url value="/management/customerManagement"/>">
    <input type ="submit" value = "No"/>
</a>

<%@ include file="/html/include/footer.html"%>
</body>
</html>
