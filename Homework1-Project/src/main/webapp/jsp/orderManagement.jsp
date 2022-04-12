<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 12/04/2022
  Time: 03:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Order Management</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Order List</h1>

<table>

    <tr>
        <th>ID</th>
        <th>ID_Customer</th>
        <th>Date</th>
        <th>Products</th>
        <th>Status</th>
    </tr>

    <c:forEach var="order" items="${orderList}">
        <tr>
            <td>${order.id_order}</td>
            <td>${order.id_customer}</td>
            <td>${order.os_datetime}</td>
            <td>${order.products}</td>
            <td>${order.id_Status}</td>
            <td>
                <a href="<c:url value="/management/orderManagement"/>">Edit</a>
            </td>
            <td>
                <form method="GET" action="<c:url value="/management/orderManagement/deleteOrder"/>">
                    <input type ="hidden" name = "orderToDelete" value = ${order.id_order}>
                    <input type ="submit" value = "Delete">
                </form>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>