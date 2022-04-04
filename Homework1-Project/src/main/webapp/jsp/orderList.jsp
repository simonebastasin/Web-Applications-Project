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

<html>
<head>
    <title>Order list</title>
</head>
<body>
    <c:import url="/jsp/include/header.jsp"/>
    <h1>Your orders</h1>
    <ul>
        <c:forEach var="order" items="${onlineOrderList}">
            <li>
                <b>Order ID: ${order.idOrder}</b>

                <form method="GET" action="<c:url value="/orderList/order"/>">
                    <input type="hidden" name="ID" value="${order.idOrder}">
                    <input type ="submit" value = "details">
                </form>
            </li>
            <ul>
            <c:forEach var="prod" items="${order.products}">
                <li>
                    Product name: ${prod.name} <br>
                    Price: ${prod.sale} <br>
                    Quantity: ${prod.quantity} <br>
                </li>
            </c:forEach>
            </ul>
            Date: ${order.ooDateTime.getHumanDate()} <br>
            Status: ${order.status.status.text} <br>
            Total price: ${order.getTotalPrice()} <br>
        </c:forEach>
    </ul>

</body>
</html>
