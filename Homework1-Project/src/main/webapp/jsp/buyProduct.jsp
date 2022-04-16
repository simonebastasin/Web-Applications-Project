<%--
  Created by IntelliJ IDEA.
  User: gioel
  Date: 07/04/2022
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="product" type="Product"--%>

<html>
<head>
    <title>Electromechanics shop</title>
</head>

<body>

<c:import url="/jsp/include/header.jsp"/>

<h1>Product: ${product.name}</h1>

Quantity selected: <c:out value="${pageContext.request.getParameter('quantity')}"/> <br>

<c:choose>
    <c:when test="${not empty product.discount}">
        Total price: <span  style="text-decoration: line-through;"><c:out value="${product.salePrice*pageContext.request.getParameter('quantity')}"/>€</span> <span style="color: red;"><c:out value="${product.discountSale*pageContext.request.getParameter('quantity')}"/>€</span> <br>
    </c:when>
    <c:otherwise>
        Total price: <c:out value="${product.salePrice*pageContext.request.getParameter('quantity')}€"/> <br>
    </c:otherwise>
</c:choose>

<form method="post" action="<c:url value="/buy/confirmed/${product.alias}"/>">
    <label for="payment">Select a payment method: </label>
    <select name="payment" id="payment" autofocus required>
        <option value="Credit card">Credit card</option>
        <option value="Apple pay">Apple pay</option>
        <option value="PayPal">PayPal</option>
        <option value="Postepay">Postepay</option>
        <option value="Nexi">Nexi</option>
    </select>

    <input type="hidden" value="${pageContext.request.getParameter('quantity')}" name="quantity">
    <input type ="submit" value = "Confirm payment">
</form>
<%@ include file="/html/include/footer.html"%>
</body>
</html>
