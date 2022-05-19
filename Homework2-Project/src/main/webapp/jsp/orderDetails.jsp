<%--
  Created by IntelliJ IDEA.
  User: pasto
  Date: 03/04/2022
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.unipd.dei.wa2122.wadteam.resources.OrderStatusEnum" %>
<%--@elvariable id="onlineOrder" type="OnlineOrder"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Order details | Electromechanics Shop</title>
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
        <div class="card-body ms-10">
            <ul class="list-group list-group-flush">
            <c:forEach var="prod" items="${onlineOrder.products}">
            <li class="list-group-item">
                <h5 class="card-title">${prod.name}</h5>
                <div class="container">
                    <div class="row">
                        <div class="col-4">
                            Price:
                        </div>
                        <div class="col-4">
                                ${String.format("%.2f",prod.salePrice)}€
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-4">
                            Quantity:
                        </div>
                        <div class="col-4">
                                ${prod.quantity}
                        </div>
                    </div>
                </div>

                    <div class="col text-end">
                        <a href="<c:url value="/ticket/create"/>/${prod.alias}" class="card-link mt-5">Open Ticket</a>
                    </div>

            </li>
            </c:forEach>
            </ul>
        </div>
        <div class="card-footer text-start">
            <div class="col">
                Date: ${onlineOrder.ooDateTime.getHumanDate()} <br>
                Status: ${onlineOrder.status.status.text} <br>
                Total price: ${String.format("%.2f",onlineOrder.getTotalPrice())}€<br>
            </div>
        </div>
    </div>

    <c:if test="${onlineOrder.status.status ne OrderStatusEnum.OPEN }">

            <a href="<c:url value="/invoice/order/${onlineOrder.idOrder}" />" class="btn btn-primary me-3 float-end">Invoice</a>

    </c:if>
    <c:if test="${onlineOrder.status.status eq OrderStatusEnum.OPEN }">

            <a href="<c:url value="/buy/pay/${onlineOrder.idOrder}"/>" class="btn btn-primary me-3 float-end">Pay</a>
            <a href="<c:url value="/buy/cancel/${onlineOrder.idOrder}"/>" class="btn btn-primary me-3 float-end">Cancel</a>

    </c:if>
    <c:if test="${onlineOrder.status.status eq OrderStatusEnum.PAYMENT_ACCEPTED }">

            <a href="<c:url value="/buy/cancel/${onlineOrder.idOrder}"/>" class="btn btn-primary me-3 float-end">Cancel</a>

    </c:if>

</div>







</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
