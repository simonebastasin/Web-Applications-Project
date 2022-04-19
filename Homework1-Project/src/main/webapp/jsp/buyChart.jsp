<%--
  Created by IntelliJ IDEA.
  User: gioel
  Date: 07/04/2022
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="productList" type="List<Product>"--%>

<html>
<head>
    <title>Electromechanics shop</title>
</head>

<body>

<c:import url="/jsp/include/header.jsp"/>
<table>
    <tr>
        <th>Product name</th>
        <th>Price Unitary</th>
        <th>Quantity</th>
        <th>Total</th>
    </tr>
    <c:set var="total" value="${0}"/>
    <c:forEach var="product" items="${productList}">
        <tr>
            <td>${product.name}</td>
            <td>${product.finalSalePrice}</td>
            <td>${product.quantity}</td>
            <td>${product.finalSalePrice*product.quantity}</td>
            <c:set var="total" value="${total + product.finalSalePrice*product.quantity}" />
        </tr>
    </c:forEach>
    <tr><td>Total</td><td></td><td></td><td>${total}</td></tr>
</table>

<%@ include file="/html/include/footer.html"%>
</body>
</html>
