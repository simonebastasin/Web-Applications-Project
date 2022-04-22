<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 12/04/2022
  Time: 03:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="onlineOrder" type="it.unipd.dei.wa2122.wadteam.resources.OnlineOrder"--%>

<html>
<head>
    <title>Order list | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Order list</h1>

<table>
    <tr>
        <th>ID_Order</th>
        <th>ID_Customer</th>
        <th>Order_Date</th>
        <th>Products</th>
        <th>Status</th>
        <th>Last_Status_Update</th>
    </tr>

    <c:forEach var="onlineOrder" items="${onlineOrderList}">
        <tr>
            <td>${onlineOrder.idOrder}</td>
            <td>${onlineOrder.idCustomer}</td>
            <td>${onlineOrder.ooDateTime.getHumanDate()}</td>
            <td><ul>
                <c:forEach var="product" items="${onlineOrder.products}">
                    <li>${product.alias}, ${product.brand}, ${product.name}, ${product.quantity}</li>
                </c:forEach>
            </ul></td>
            <td>${onlineOrder.status}</td>
            <td>${onlineOrder.status.osDateTime.getHumanDate()}</td>
            <td>
                <a href="<c:url value="/management/orderManagement/editOrder/${onlineOrder.idOrder}"/>">Edit</a>
            </td>
            <td>
                <a href="<c:url value="/management/orderManagement/deleteOrder/${onlineOrder.idOrder}"/>">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<%@ include file="/html/include/footer.html"%>
</body>
</html>