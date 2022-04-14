<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 14/04/2022
  Time: 18:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="onlineOrder" type="it.unipd.dei.wa2122.wadteam.resources.OnlineOrder"--%>

<html>
<head>
    <title>Edit Order</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Edit order</h1>

<form method="POST" action="">
    <label for="idOrder">ID_Order:</label><br>
    <input type="text" id="idOrder" name="idOrder" value="${onlineOrder.idOrder}" readonly><br>
    <label for="idCustomer">ID_Customer:</label><br>
    <input type="text" id="idCustomer" name="idCustomer" value="${onlineOrder.idCustomer}" readonly><br>
    <label for="orderDate">Order_Date:</label><br>
    <input type="text" id="orderDate" name="orderDate" value="${onlineOrder.ooDateTime}" readonly><br>
    <label for="products">Products:</label><br>
    <input type="text" id="products" name="products" value="${onlineOrder.products}" readonly><br>
    <label for="status">Status:</label><br>
    <select id="status" name="status" selected="${onlineOrder.status}">
        <option value="open">Open</option>
        <option value="paymentAccepted">Payment accepted</option>
        <option value="shipped">Shipped</option>
        <option value="delivered">Delivered</option>
        <option value="cancelled">Cancelled</option>
    </select><br>
    <input type="submit" value="Submit">
</form>
<%@ include file="/html/include/footer.html"%>
</body>
</html>
