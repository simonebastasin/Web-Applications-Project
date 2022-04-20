<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 14/04/2022
  Time: 18:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.unipd.dei.wa2122.wadteam.resources.OrderStatusEnum" %>
<%--@elvariable id="onlineOrder" type="it.unipd.dei.wa2122.wadteam.resources.OnlineOrder"--%>

<html>
<head>
    <title>Edit Order</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Edit order</h1>


<c:forEach var="onlineOrder" items="${onlineOrderList}">
    <form method="POST" action="">

        <label for="idOrder">ID Order:</label><br>
        <input type="text" id="idOrder" name="idOrder" value="${onlineOrder.idOrder}" disabled><br>
        <label for="idCustomer">ID Customer:</label><br>
        <input type="text" id="idCustomer" name="idCustomer" value="${onlineOrder.idCustomer}" disabled><br>
        <label for="orderDate">Order Date:</label><br>
        <input type="text" id="orderDate" name="orderDate" value="${onlineOrder.ooDateTime.getHumanDate()}" disabled><br>

        Products:
        <table>
            <tr>
                <th>Alias</th>
                <th>Brand</th>
                <th>Name</th>
                <th>Quantity</th>
                <th>Sale Price</th>
            </tr>
            <c:forEach var="product" items="${onlineOrder.products}">
                <tr>
                    <td>${product.alias}</td>
                    <td>${product.brand}</td>
                    <td>${product.name}</td>
                    <td>${product.quantity}</td>
                    <td>${product.salePrice}</td>
                </tr>
            </c:forEach>
        </table>

        <label for="status">Status:</label><br>
        <select id="status" name="status">
            <c:choose>
                <c:when test="${onlineOrder.status.status != OrderStatusEnum.OPEN}">
                    <option value="${OrderStatusEnum.OPEN}">Open</option>
                </c:when>
                <c:otherwise>
                    <option value="${OrderStatusEnum.OPEN}" selected>Open</option>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${onlineOrder.status.status != OrderStatusEnum.PAYMENT_ACCEPTED}">
                    <option value="${OrderStatusEnum.PAYMENT_ACCEPTED}">Payment accepted</option>
                </c:when>
                <c:otherwise>
                    <option value="${OrderStatusEnum.PAYMENT_ACCEPTED}" selected>Payment accepted</option>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${onlineOrder.status.status != OrderStatusEnum.SHIPPED}">
                    <option value="${OrderStatusEnum.SHIPPED}">Shipped</option>
                </c:when>
                <c:otherwise>
                    <option value="${OrderStatusEnum.SHIPPED}" selected>Shipped</option>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${onlineOrder.status.status != OrderStatusEnum.DELIVERED}">
                    <option value="${OrderStatusEnum.DELIVERED}">Delivered</option>
                </c:when>
                <c:otherwise>
                    <option value="${OrderStatusEnum.DELIVERED}" selected>Delivered</option>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${onlineOrder.status.status != OrderStatusEnum.CANCELLED}">
                    <option value="${OrderStatusEnum.CANCELLED}">Cancelled</option>
                </c:when>
                <c:otherwise>
                    <option value="${OrderStatusEnum.CANCELLED}" selected>Cancelled</option>
                </c:otherwise>
            </c:choose>
        </select><br>

        <label for="statusDate">Last status Update:</label><br>
        <input type="text" id="statusDate" name="statusDate" value="${onlineOrder.status.osDateTime.humanDate}" disabled><br>
        <label for="description">Description:</label><br>
        <input type="text" id="description" name="description" value="${onlineOrder.status.description}"><br>

        <br>
        <input type="submit" value="Submit">

    </form>
</c:forEach>


<%@ include file="/html/include/footer.html"%>
</body>
</html>
