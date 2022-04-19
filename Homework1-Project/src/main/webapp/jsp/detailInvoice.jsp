<%--
  Created by IntelliJ IDEA.
  User: Gianpietro
  Date: 18/04/2022
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="onlineInvoiceList" type="List<it.unipd.dei.wa2122.wadteam.resources.OnlineInvoice>"--%>
<html>
<head>
  <title>Invoice List</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>

<c:forEach var="invoice" items="${onlineInvoiceList}">
  <li><b>Invoice ID: ${invoice.id}</b></li>
  <li>Transaction ID: ${invoice.transactionId}</li>
  <li>Payment Type: ${invoice.paymentType}</li>
  <li>Price: ${invoice.totalPrice}</li>

  <h2>Product list:</h2>


    <h1>Order ID: ${invoice.idOrder.idOrder}</h1>

    <table>
      <tr>
        <th>Product name</th>
        <th>Price</th>
        <th>Quantity</th>
      </tr>
      <c:forEach var="prod" items="${invoice.idOrder.products}">
        <tr>
          <td> ${prod.name} </td>
          <td>${prod.salePrice}€ </td>
          <td>${prod.quantity} </td>
        </tr>
      </c:forEach>

      <tr>
        <td>Total</td>
        <td></td>
        <td>${invoice.totalPrice}</td>
      </tr>
    </table>

    Date: ${invoice.idOrder.ooDateTime.getHumanDate()} <br>
    Status: ${invoice.idOrder.status.status.text} <br>
    Total price: ${invoice.idOrder.getTotalPrice()}€ <br>

</c:forEach>


<%@ include file="/html/include/footer.html"%>
</body>
</html>
