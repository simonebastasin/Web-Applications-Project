<%--
  Created by IntelliJ IDEA.
  User: Gianpietro
  Date: 18/04/2022
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="onlineInvoice" type="List<it.unipd.dei.wa2122.wadteam.resources.OnlineInvoice>"--%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="description" content="Electromechanics Shop">
  <meta name="author" content="WAD Team">

  <title>Invoice Detail | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Invoice Detail</li>
  </ol>
</nav>

<h2>Invoice ID: ${onlineInvoice.id}</h2>
<li>Transaction ID: ${onlineInvoice.transactionId}</li>
<li>Payment Type: ${onlineInvoice.paymentType.text}</li>
<li>Date: ${onlineInvoice.oiDate.humanDate}</li>
<li>Total Price: ${onlineInvoice.totalPrice}€</li>

<h2>Product list</h2>
<b>Order ID: ${onlineInvoice.idOrder.idOrder}</b>

<table>
  <tr>
    <th>Product name</th>
    <th>Price</th>
    <th>Quantity</th>
    <th>Total price</th>
  </tr>
  <c:forEach var="prod" items="${onlineInvoice.idOrder.products}">
    <tr>
      <td> ${prod.name} </td>
      <td>${prod.salePrice}€ </td>
      <td>${prod.quantity} </td>
      <td>${prod.quantity * prod.salePrice}</td>
    </tr>
  </c:forEach>

  <tr>
    <td>Total</td>
    <td></td>
    <td></td>
    <td>${onlineInvoice.idOrder.getTotalPrice()}€</td>
  </tr>
</table>

Date: ${onlineInvoice.idOrder.ooDateTime.getHumanDate()} <br>
Status: ${onlineInvoice.idOrder.status.status.text} <br>
Total price: ${onlineInvoice.totalPrice}€ <br>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
