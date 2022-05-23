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
<nav class="custom-breadcrumb-divider" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Order ID: ${onlineOrder.idOrder}</li>
  </ol>
</nav>

<div id="liveAlertPlaceholder"></div>
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
                        <a href="#" type="button" class="card-link mt-5" data-bs-toggle="modal" data-bs-target="#createTicketModal" data-id="${prod.alias}">
                            Open Ticket
                        </a>
                    </div>

            </li>
            </c:forEach>
            </ul>
        </div>
        <div class="card-footer text-start">
            <div class="col">
                Date: ${onlineOrder.ooDateTime.getHumanDate()} <br>
                Status: <span id="orderStatus">${onlineOrder.status.status.text}</span> <br>
                Total price: ${String.format("%.2f",onlineOrder.getTotalPrice())}€<br>
            </div>
        </div>
    </div>
    <div class="mt-3 mb-3 d-flex justify-content-end">
        <c:if test="${onlineOrder.status.status ne OrderStatusEnum.OPEN }">
            <a href="<c:url value="/invoice/order/${onlineOrder.idOrder}" />" class="btn btn-primary me-3">Invoice</a>
        </c:if>
        <c:if test="${onlineOrder.status.status eq OrderStatusEnum.OPEN }">
            <a href="<c:url value="/buy/pay/${onlineOrder.idOrder}"/>" class="btn btn-primary me-3 float-end">Pay now</a>
            <a href="#" data-bs-toggle="modal" data-bs-target="#deleteOrderModal" class="btn btn-primary me-3">Cancel</a>
        </c:if>
        <c:if test="${onlineOrder.status.status eq OrderStatusEnum.PAYMENT_ACCEPTED }">
            <a href="#" data-bs-toggle="modal" data-bs-target="#deleteOrderModal" class="btn btn-primary me-3">Cancel</a>
        </c:if>
    </div>
</div>

</div>

<div class="modal fade" id="createTicketModal" tabindex="-1" aria-labelledby="createTicketModal" aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false" >
    <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg modal-fullscreen-md-down">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="createTicketModalTitle">Create Ticket</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div id="formAlertPlaceholderCreateTicket" class="sticky-top"></div>
            <div class="modal-body">
                <form id="createTicketForm" class="needs-validation" novalidate>
                    <div class="mb-3">
                        <label for="description" class="col-form-label">Description:</label>
                        <textarea class="form-control" id="description" name="description" required></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer p-3">
                <button type="submit" class="btn btn-primary" form="createTicketForm">Create</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="deleteOrderModal" tabindex="-1" aria-labelledby="deleteOrderModal" aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false" >
    <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg modal-fullscreen-md-down">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Delete order Ticket ${onlineOrder.idOrder}</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <h5>Order you are deleting:</h5>

                <div class="row">
                    <div class="col">ID Order:</div>
                    <div class="col">${onlineOrder.idOrder}</div>
                    <div class="col"></div>
                </div>
                <div class="row">
                    <div class="col">Order Date:</div>
                    <div class="col">${onlineOrder.ooDateTime.getHumanDate()} </div>
                    <div class="col"></div>
                </div>
                <div class="row">
                    <div class="col">Status:</div>
                    <div class="col">${onlineOrder.status}</div>
                    <div class="col"></div>
                </div>
                <div class="row">
                    <div class="col">Description:</div>
                    <div class="col">${onlineOrder.status.description}</div>
                    <div class="col"></div>
                </div>

                <div class="card row m-3">
                    <div class="card-header">
                        <div class="row">
                            <div class="col">
                                Alias
                            </div>
                            <div class="col">
                                Name
                            </div>
                            <div class="col">
                                Brand
                            </div>
                            <div class="col">
                                Price
                            </div>
                            <div class="col">
                                Quantity
                            </div>
                        </div>
                    </div>
                    <ul class="list-group list-group-flush">
                        <c:forEach var="product" items="${onlineOrder.products}">
                            <li class="list-group-item row px-0">
                                <div class="col text-break">
                                        ${product.alias}
                                </div>
                                <div class="col text-break">
                                        ${product.name}
                                </div>
                                <div class="col text-break">
                                        ${product.brand}
                                </div>
                                <div class="col text-break">
                                        ${product.quantity}
                                </div>
                                <div class="col text-break">
                                        ${String.format("%.2f", product.salePrice)}€
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="modal-footer p-3">
                <form id="deleteOrderForm" data-id=${onlineOrder.idOrder}>
                    <input type="submit" value="Delete order"  class="btn btn-danger mx-3"/>
                </form>
            </div>
        </div>
    </div>
</div>

<c:import url="/jsp/include/footer.jsp"/>
<script src="<c:url value="/js/order.js"/>"></script>
</body>
</html>
