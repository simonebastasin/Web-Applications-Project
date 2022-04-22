<%--
  Created by IntelliJ IDEA.
  User: gioel
  Date: 07/04/2022
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.unipd.dei.wa2122.wadteam.resources.PaymentMethodOnlineEnum" %>
<%--@elvariable id="product" type="Product"--%>
<%--@elvariable id="onlineOrder" type="OnlineOrder"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="Electromechanics Shop">
    <meta name="author" content="WAD Team">

    <title>Product: ${product.name} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Product: ${product.name}</h1>

Quantity selected: ${product.quantity}<br>

<c:choose>
    <c:when test="${not empty product.discount}">
        Total price: <span  style="text-decoration: line-through;">${product.quantity*product.salePrice}€</span> <span style="color: red;">${product.quantity*product.discountSale}€</span> <br>
    </c:when>
    <c:otherwise>
        Total price: ${product.quantity*product.salePrice}€ <br>
    </c:otherwise>
</c:choose>

<form method="post" action="<c:url value="/buy/confirmed/${product.alias}"/>">
    <label for="payment">Select a payment method: </label>
    <select name="payment" id="payment" autofocus required>
        <option value="${PaymentMethodOnlineEnum.CREDIT_CARD}">Credit Card</option>
        <option value="${PaymentMethodOnlineEnum.APPLE_PAY}">Apple Pay</option>
        <option value="${PaymentMethodOnlineEnum.GOOGLE_PAY}">Google Pay</option>
    </select>

    <input type ="submit" value = "Confirm payment">
</form>

<%@ include file="/html/include/footer.html"%>
</body>
</html>
