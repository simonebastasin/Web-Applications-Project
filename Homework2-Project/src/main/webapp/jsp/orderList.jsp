<%--
  Created by IntelliJ IDEA.
  User: pasto
  Date: 02/04/2022
  Time: 19:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="onlineOrderList" type="java.util.List<OnlineOrder>"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Your Order List | Electromechanics Shop</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Your Order List</li>
  </ol>
</nav>

<c:forEach var="order" items="${onlineOrderList}">
    <h2>Order ID: ${order.idOrder}</h2>
        <a href="<c:url value="/order/detail"/>/${order.idOrder}">Details</a>
    <ul>
    <c:forEach var="prod" items="${order.products}">
        <li>
            Product name: ${prod.name} <br>
            Price: ${prod.salePrice}€ <br>
            Quantity: ${prod.quantity} <br>
        </li>
    </c:forEach>
    </ul>
    Date: ${order.ooDateTime.getHumanDate()} <br>
    Status: ${order.status.status.text} <br>
    Total price: ${order.getTotalPrice()}€ <br>
</c:forEach>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
