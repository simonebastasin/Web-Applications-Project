<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 12/04/2022
  Time: 05:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="onlineOrder" type="it.unipd.dei.wa2122.wadteam.resources.OnlineOrder"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Delete Order | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Delete Order</li>
  </ol>
</nav>

Order you are deleting:<br>
<br>
<table>
    <tr>
        <th>ID Order</th>
        <th>ID Customer</th>
        <th>Order Date</th>
        <th>Status</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>${onlineOrder.idOrder}</td>
        <td>${onlineOrder.idCustomer}</td>
        <td>${onlineOrder.ooDateTime.getHumanDate()}</td>
        <td>${onlineOrder.status}</td>
        <td>${onlineOrder.status.description}</td>
    </tr>
</table><br>
With these products:<br>
<br>
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
</table><br>
Sure to delete?<br>

<br>
<form method="POST" action="">
    <input type="submit" value="Yes">
</form>
<a href="<c:url value="/management/orderManagement"/>">
    <input type="submit" value="No"/>
</a>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>