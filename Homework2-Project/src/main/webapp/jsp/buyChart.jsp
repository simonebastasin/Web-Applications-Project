<%--
  Created by IntelliJ IDEA.
  User: gioel
  Date: 07/04/2022
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.unipd.dei.wa2122.wadteam.resources.OrderStatusEnum" %>
<%@ page import="it.unipd.dei.wa2122.wadteam.resources.PaymentMethodOnlineEnum" %>
<%--@elvariable id="onlineOrder" type="it.unipd.dei.wa2122.wadteam.resources.OnlineOrder"--%>
<%--@elvariable id="onlineInvoice" type="it.unipd.dei.wa2122.wadteam.resources.OnlineInvoice"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Order ID: ${onlineOrder.idOrder} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Order ID: ${onlineOrder.idOrder}</li>
  </ol>
</nav>



<div class="mx-auto w-lg-50">
    <div class="card mt-3 mb-3">
        <div class="card-header">
            <div class="row">
                <div class="col">
                    Product name
                </div>
                <div class="col">
                    Price Unitary
                </div>
                <div class="col">
                    Quantity
                </div>
                <div class="col">
                    Total
                </div>
            </div>
        </div>
        <ul class="list-group list-group-flush">
            <c:forEach var="product" items="${onlineOrder.products}">
                <li class="list-group-item">
                    <div class="row">
                        <div class="col">
                                ${product.name}
                        </div>
                        <div class="col">
                                ${product.finalSalePrice}€
                        </div>
                        <div class="col">
                                ${product.quantity}
                        </div>
                        <div class="col">
                                ${product.finalSalePrice*product.quantity}€
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
        <div class="card-footer">
            <div class="row">
                <div class="col">
                    Total
                </div>
                <div class="col">
                </div>
                <div class="col">
                </div>
                <div class="col">
                    ${String.format("%.2f", onlineOrder.totalPrice)}€
                </div>
            </div>
        </div>
    </div>







<c:choose>
    <c:when test="${onlineOrder.status.status eq OrderStatusEnum.OPEN}">


                    <form method="post" action="">
                        <div class="card mt-3 mb-3">
                            <div class="card-body">
                                <div class="row align-items-center">
                                    <div class="col">
                                        <label for="payment">Select a payment method: </label>
                                    </div>
                                    <div class="col">
                                        <select class="form-select" name="payment" id="payment" autofocus required>
                                            <option value="${PaymentMethodOnlineEnum.CREDIT_CARD}">Credit Card</option>
                                            <option value="${PaymentMethodOnlineEnum.APPLE_PAY}">Apple Pay</option>
                                            <option value="${PaymentMethodOnlineEnum.GOOGLE_PAY}">Google Pay</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="d-flex justify-content-center">
                            <input type ="submit" value = "Confirm payment" id="confirmPayment" class="btn btn-primary" >
                        </div>
                    </form>


    </c:when>
    <c:otherwise>
    <div class="card mt-3 mb-3">
        <div class="card-body">
            <c:if test="${not empty onlineInvoice}">
                <div class="row">
                    <div class="col">
                        Transaction id:
                    </div>
                    <div class="col">
                            ${onlineInvoice.transactionId}
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        Payment method:
                    </div>
                    <div class="col">
                            ${onlineInvoice.paymentType.text}
                    </div>
                </div>
            </c:if>
        </div>
        <div class="card-footer">
            Your payment is now being processed.
            Hang on and check out your <a href="<c:url value="/order/detail/${onlineOrder.idOrder}"/>" class="card-link">order</a>.
        </div>
    </div>
    </c:otherwise>
</c:choose>
</div>

</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
