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

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Order List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Order List</li>
  </ol>
</nav>

<table>
    <thead>
        <tr>
            <th>ID_Order</th>
            <th>ID_Customer</th>
            <th>Order_Date</th>
            <th>Products</th>
            <th>Status</th>
            <th>Last_Status_Update</th>
        </tr>
    </thead>
    <tbody>
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
    </tbody>
</table>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>