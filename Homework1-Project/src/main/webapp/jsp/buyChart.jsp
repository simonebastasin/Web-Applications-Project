<%--
  Created by IntelliJ IDEA.
  User: gioel
  Date: 07/04/2022
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.unipd.dei.wa2122.wadteam.resources.OrderStatusEnum" %>
<%@ page import="it.unipd.dei.wa2122.wadteam.resources.PaymentMethodOnlineEnum" %>
<%--@elvariable id="onlineOrder" type="it.unipd.dei.wa2122.wadteam.resources.OnlineOrder"--%>
<%--@elvariable id="onlineInvoice" type="it.unipd.dei.wa2122.wadteam.resources.OnlineInvoice"--%>

<html>
<head>
    <title>Order ID: ${onlineOrder.idOrder} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Order ID: ${onlineOrder.idOrder}</h1>

<table>
    <tr>
        <th>Product name</th>
        <th>Price Unitary</th>
        <th>Quantity</th>
        <th>Total</th>
    </tr>
    <c:forEach var="product" items="${onlineOrder.products}">
        <tr>
            <td>${product.name}</td>
            <td>${product.finalSalePrice}€</td>
            <td>${product.quantity}</td>
            <td>${product.finalSalePrice*product.quantity}€</td>
        </tr>
    </c:forEach>
    <tr><td>Total</td><td></td><td></td><td>${onlineOrder.totalPrice}€</td></tr>
</table>
<c:choose>
    <c:when test="${onlineOrder.status.status eq OrderStatusEnum.OPEN}">
        <form method="post" action="">
            <label for="payment">Select a payment method: </label>
            <select name="payment" id="payment" autofocus required>
                <option value="${PaymentMethodOnlineEnum.CREDIT_CARD}">Credit Card</option>
                <option value="${PaymentMethodOnlineEnum.APPLE_PAY}">Apple Pay</option>
                <option value="${PaymentMethodOnlineEnum.GOOGLE_PAY}">Google Pay</option>
            </select>
            <input type ="submit" value = "Confirm payment">
        </form>
    </c:when>
    <c:otherwise>
        Your payment is now being processed. <br>
        <c:if test="${not empty onlineInvoice}">
            Transaction id: ${onlineInvoice.transactionId} <br>
            Payment method: ${onlineInvoice.paymentType.text} <br>
        </c:if>
        Hang on and check out your <a href="<c:url value="/order/detail/${onlineOrder.idOrder}"/>">order</a>.
    </c:otherwise>
</c:choose>

<%@ include file="/html/include/footer.html"%>
</body>
</html>
