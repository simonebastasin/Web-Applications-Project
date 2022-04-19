<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 12/04/2022
  Time: 03:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="order" type="it.unipd.dei.wa2122.wadteam.resources.OnlineOrder"--%>

<html>
<head>
    <title>Order Management</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Order List</h1>

<table>

    <tr>
        <th>ID_Order</th>
        <th>ID_Customer</th>
        <th>Order_Date</th>
        <th>Products</th>
        <th>Status</th>
    </tr>

    <c:forEach var="order" items="${onlineOrderList}">
        <tr>
            <td>${order.idOrder}</td>
            <td>${order.idCustomer}</td>
            <td>${order.ooDateTime.getHumanDate()}</td>
            <td>${order.products}</td>
            <td>${order.status}</td>
            <td>
                <a href="<c:url value="/management/orderManagement/editOrder/${order.idOrder}"/>">Edit</a>
            </td>
            <td>
                <a href="<c:url value="/management/orderManagement/deleteOrder/${order.idOrder}"/>">Delete</a>
            </td>
        </tr>
    </c:forEach>

</table>

<%@ include file="/html/include/footer.html"%>
</body>
</html>