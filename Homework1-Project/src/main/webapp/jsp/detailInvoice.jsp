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
<html>
<head>
  <title>Invoice Detail</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>
<h1> Invoice Detail </h1>
<hr>

  <h2>Invoice ID: ${onlineInvoice.id}</h2>
  <li>Transaction ID: ${onlineInvoice.transactionId}</li>
  <li>Payment Type: ${onlineInvoice.paymentType.text}</li>
  <li>Date: ${onlineInvoice.oiDate.humanDate}</li>
  <li>Price: ${onlineInvoice.totalPrice}</li>

  <h2>Product list</h2>
  <b>Order ID: ${onlineInvoice.idOrder.idOrder}</b>

    <table>
      <tr>
        <th>Product name</th>
        <th>Price</th>
        <th>Quantity</th>
      </tr>
      <c:forEach var="prod" items="${onlineInvoice.idOrder.products}">
        <tr>
          <td> ${prod.name} </td>
          <td>${prod.salePrice}€ </td>
          <td>${prod.quantity} </td>
        </tr>
      </c:forEach>

      <tr>
        <td>Total</td>
        <td></td>
        <td>${onlineInvoice.totalPrice}</td>
      </tr>
    </table>

    Date: ${onlineInvoice.idOrder.ooDateTime.getHumanDate()} <br>
    Status: ${onlineInvoice.idOrder.status.status.text} <br>
    Total price: ${onlineInvoice.idOrder.getTotalPrice()}€ <br>

<%@ include file="/html/include/footer.html"%>
</body>
</html>
