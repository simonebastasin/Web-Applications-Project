<%--
  Created by IntelliJ IDEA.
  User: pasto
  Date: 03/04/2022
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="onlineOrder" type="OnlineOrder"--%>

<html>
<head>
    <title>Order details</title>
</head>
<body>

    <c:import url="/jsp/include/header.jsp"/>
    <h1>Order ID: ${onlineOrder.idOrder}</h1>

    <ul>
    <c:forEach var="prod" items="${onlineOrder.products}">
        <li>
            Product name: ${prod.name} <br>
            Price: ${prod.sale}€ <br>
            Quantity: ${prod.quantity} <br>
            <br>
            <a href="<c:url value="/ticket/create"/>/${prod.alias}">Open Ticket</a>
        </li>
    </c:forEach>
    </ul>

    Date: ${onlineOrder.ooDateTime.getHumanDate()} <br>
    Status: ${onlineOrder.status.status.text} <br>
    Total price: ${onlineOrder.getTotalPrice()} <br>

    <c:choose>
        <c:when test="${onlineOrder.status.status.text != \"Open\" }">
            <input type ="submit" value = "Get invoice">
        </c:when>
    </c:choose>
    <c:choose>
        <c:when test="${onlineOrder.status.status.text != \"Cancelled\"}">
            <input type ="submit" value = "Cancel your order">
        </c:when>
    </c:choose>
    <%@ include file="/html/include/footer.html"%>
</body>
</html>
