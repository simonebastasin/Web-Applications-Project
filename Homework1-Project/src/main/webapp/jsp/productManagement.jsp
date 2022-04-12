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
    <a href="<c:url value="/management/productManagement/createProduct"/>">Add a new product</a>
</div><br>

<table>
    <tr>
        <th>Name</th>
        <th>Brand</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Evidence</th>
    </tr>


    <c:forEach var="prod" items="${productList}">

        <tr>
            <td><a href="<c:url value="/productDetail/${prod.alias}"/>">${prod.name}</a></td>
            <td>${prod.brand}</td>
            <td>${prod.sale}</td>
            <td>${prod.quantity}</td>
            <td>${prod.evidence}</td>
            <td><button type="button">Modifica</button></td>
        </tr>

    </c:forEach>

</table>
<%@ include file="/html/include/footer.html"%>
</body>
</html>
