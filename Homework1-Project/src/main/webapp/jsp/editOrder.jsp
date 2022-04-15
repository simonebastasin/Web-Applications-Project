<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 14/04/2022
  Time: 18:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="onlineOrderList" type="it.unipd.dei.wa2122.wadteam.resources.OnlineOrder"--%>

<html>
<head>
    <title>Edit Order</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Edit order</h1>


<c:forEach var="onlineOrder" items="${onlineOrderList}">

<form method="POST" action="">
    <label for="idOrder">ID_Order:</label><br>
    <input type="text" id="idOrder" name="idOrder" value="${onlineOrder.idOrder}" readonly><br>
    <label for="idCustomer">ID_Customer:</label><br>
    <input type="text" id="idCustomer" name="idCustomer" value="${onlineOrder.idCustomer}" readonly><br>
    <label for="orderDate">Order_Date:</label><br>
    <input type="text" id="orderDate" name="orderDate" value="${onlineOrder.ooDateTime}" readonly><br>
    <label for="products">Products:</label><br>


    <input type="text" id="products" name="products" value="${onlineOrder.products.toArray()}" readonly><br>
    <label for="status">Status:</label><br>


    <select id="status" name="status">
        <c:choose>
            <c:when test="${onlineOrder.status != 'Open'}">
                <option value="open">Open</option>
            </c:when>
            <c:otherwise>
                <option value="open" selected>Open</option>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${onlineOrder.status != 'Payment accepted'}">
                <option value="paymentAccepted">Payment accepted</option>
            </c:when>
            <c:otherwise>
                <option value="paymentAccepted" selected>Payment accepted</option>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${onlineOrder.status != 'Shipped'}">
                <option value="shipped">Shipped</option>
            </c:when>
            <c:otherwise>
                <option value="shipped" selected>Shipped</option>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${onlineOrder.status != 'Delivered'}">
                <option value="delivered">Delivered</option>
            </c:when>
            <c:otherwise>
                <option value="delivered" selected>Delivered</option>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${onlineOrder.status != 'Cancelled'}">
                <option value="cancelled">Cancelled</option>
            </c:when>
            <c:otherwise>
                <option value="cancelled" selected>Cancelled</option>
            </c:otherwise>
        </c:choose>

    </select>




    <br>
    <input type="submit" value="Submit">
</form>

</c:forEach>


<%@ include file="/html/include/footer.html"%>
</body>
</html>
