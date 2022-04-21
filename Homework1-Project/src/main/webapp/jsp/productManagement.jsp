<%--
  Created by IntelliJ IDEA.
  User: matteolando
  Date: 07/04/2022
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Product List</title>
</head>

<body>

<c:import url="/jsp/include/header.jsp"/>
<h1>Product List</h1>

<div>
    <a href="<c:url value="/management/productManagement/createProduct"/>">Add new product</a>
</div><br>

<table>
    <tr>
        <th>Name</th>
        <th>Alias</th>
        <th>Brand</th>
        <th>Category</th>
        <th>Sale Price</th>
        <th>Quantity</th>
        <th>Evidence</th>
    </tr>


    <c:forEach var="prod" items="${productList}">

        <tr>
            <td><a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a></td>
            <td>${prod.alias}</td>
            <td>${prod.brand}</td>
            <td>${prod.category.name}</td>
            <td>${prod.salePrice}</td>
            <td>${prod.quantity}</td>
            <td>${prod.evidence}</td>
            <td><a href="<c:url value="/management/productManagement/editProduct/${prod.alias}"/>">Edit</a></td>
            <td><a href="<c:url value="/management/productManagement/deleteProduct/${prod.alias}"/>">Delete</a></td>
        </tr>

    </c:forEach>

</table>
<%@ include file="/html/include/footer.html"%>
</body>
</html>
