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

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Order ID: ${onlineOrder.idOrder} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Order ID: ${onlineOrder.idOrder}</li>
  </ol>
</nav>
    <div class="card-body">
    <div class="mx-auto w-50">
        <div class="card mt-3 mb-3">

<table >
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
        </div>
        </div>

<c:choose>
    <c:when test="${onlineOrder.status.status eq OrderStatusEnum.OPEN}">
        <form method="post" action="">
            <label for="payment">Select a payment method: </label>
            <select name="payment" id="payment" autofocus required>
                <option value="${PaymentMethodOnlineEnum.CREDIT_CARD}">Credit Card</option>
                <option value="${PaymentMethodOnlineEnum.APPLE_PAY}">Apple Pay</option>
                <option value="${PaymentMethodOnlineEnum.GOOGLE_PAY}">Google Pay</option>
            </select>
            <input type ="submit" value = "Confirm payment" id="confirmPayment" class="btn btn-primary " >
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

    </div>
</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
