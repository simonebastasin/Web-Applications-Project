<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 12/04/2022
  Time: 00:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="employee" type="it.unipd.dei.wa2122.wadteam.resources.Employee"--%>

<html>
<head>
    <title>Delete Product</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Delete Product</h1>

Product you are deleting:<br><br>
<table>
    <tr>
        <th>Alias</th>
        <th>Name</th>
        <th>Brand</th>
        <th>Category</th>
    </tr>
    <tr>
        <td>${product.alias}</td>
        <td>${product.name}</td>
        <td>${product.brand}</td>
        <td>${product.category.name}</td>
    </tr>
</table><br>
Are you sure to delete this product?<br><br>

<form method="POST" action="">
    <input type ="submit" value = "Yes">
</form>
<a href="<c:url value="/management/productManagement"/>">
    <input type ="submit" value = "No"/>
</a>

<%@ include file="/html/include/footer.html"%>
</body>
</html>