<%--
  Created by IntelliJ IDEA.
  User: pasto
  Date: 03/04/2022
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.unipd.dei.wa2122.wadteam.resources.OrderStatusEnum" %>
<%--@elvariable id="onlineOrder" type="OnlineOrder"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Order details | Electromechanics Shop</title>
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

<ul>
<c:forEach var="prod" items="${onlineOrder.products}">
    <li>
        Product name: ${prod.name} <br>
        Price: ${prod.salePrice}€ <br>
        Quantity: ${prod.quantity} <br>
        <br>
        <a href="<c:url value="/ticket/create"/>/${prod.alias}">Open Ticket</a>
    </li>
</c:forEach>
</ul>

Date: ${onlineOrder.ooDateTime.getHumanDate()} <br>
Status: ${onlineOrder.status.status.text} <br>
Total price: ${onlineOrder.getTotalPrice()}€ <br>

<c:if test="${onlineOrder.status.status ne OrderStatusEnum.OPEN }">
    <a href="<c:url value="/invoice/order/${onlineOrder.idOrder}"/>">Invoice</a>
</c:if>
<c:if test="${onlineOrder.status.status eq OrderStatusEnum.OPEN }">
    <a href="<c:url value="/buy/pay/${onlineOrder.idOrder}"/>">Pay</a>
    <a href="<c:url value="/buy/cancel/${onlineOrder.idOrder}"/>">Cancel</a>
</c:if>
<c:if test="${onlineOrder.status.status eq OrderStatusEnum.PAYMENT_ACCEPTED }">
    <a href="<c:url value="/buy/cancel/${onlineOrder.idOrder}"/>">Cancel</a>
</c:if>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
