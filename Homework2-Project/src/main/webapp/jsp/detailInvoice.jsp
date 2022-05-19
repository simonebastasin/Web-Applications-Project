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
    <c:import url="/jsp/include/head.jsp"/>

    <title>Invoice Detail ${onlineInvoice.id} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
    <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
        <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
            <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
            <li class="breadcrumb-item"><a href="<c:url value="/invoice/list"/>">Invoice List</a></li>
            <li class="breadcrumb-item active" aria-current="page">Invoice Detail ${onlineInvoice.id}</li>
        </ol>
    </nav>

    <div id="invoice">
        <div class="d-none d-print-block">Electromechanics Shop</div>
        <div class=" mx-auto w-lg-50 bg-white p-3 rounded">
        <h3>ID INVOICE # ${onlineInvoice.id}</h3>
        <ul class="list-group list-group-flush">
            <li class="list-group-item">Transaction ID: ${onlineInvoice.transactionId}</li>
            <li class="list-group-item">Payment Type: ${onlineInvoice.paymentType.text}</li>
            <li class="list-group-item">Date: ${onlineInvoice.oiDate.humanDate}</li>
            <li class="list-group-item">Total Price: ${String.format("%.2f", onlineInvoice.totalPrice)}€</li>
        </ul>
        <br>

        <h3>Product list</h3>
        <h5>ID ORDER # ${onlineInvoice.idOrder.idOrder}</h5>

        <table class="table">
            <thead>
            <tr>
                <th scope="col">Product name</th>
                <th scope="col">Price</th>
                <th scope="col">Quantity</th>
                <th scope="col">Total price</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="prod" items="${onlineInvoice.idOrder.products}">
                <tr>
                    <td> ${prod.name} </td>
                    <td>${String.format("%.2f", prod.salePrice)}€</td>
                    <td>${prod.quantity} </td>
                    <td>${String.format("%.2f", prod.quantity * prod.salePrice)}€</td>
                </tr>
            </c:forEach>

            <tr>
                <td>Total</td>
                <td></td>
                <td></td>
                <td>${String.format("%.2f", onlineInvoice.idOrder.getTotalPrice())}€</td>
            </tr>
            </tbody>
        </table>


            Date: ${onlineInvoice.idOrder.ooDateTime.getHumanDate()}<br>
            Total price: ${String.format("%.2f", onlineInvoice.totalPrice)}€<br>
            <div class="mb-3">
                <button class="btn btn-primary" onclick="printDiv('invoice')">Print Invoice</button>
            </div>
        </div>
    </div>
</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
