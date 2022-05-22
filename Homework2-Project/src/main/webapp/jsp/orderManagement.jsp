<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 12/04/2022
  Time: 03:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.unipd.dei.wa2122.wadteam.resources.OrderStatusEnum" %>
<%--@elvariable id="onlineOrder" type="it.unipd.dei.wa2122.wadteam.resources.OnlineOrder"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head-management.jsp"/>

    <title>Order List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
    <nav class="custom-breadcrumb-divider" aria-label="breadcrumb">
      <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
        <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
        <li class="breadcrumb-item active" aria-current="page">Order List</li>
      </ol>
    </nav>
    <div id="liveAlertPlaceholder"></div>

    <div class="card card-body">
        <table id="orderTable" class="table table-hover">
            <thead>
                <tr>
                    <th>ID Order</th>
                    <th>ID Customer</th>
                    <th>Order Date</th>
                    <th>Products</th>
                    <th>Status</th>
                    <th>Last Status Update</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody class="align-middle">
                <c:forEach var="onlineOrder" items="${onlineOrderList}">
                    <tr data-id="${onlineOrder.idOrder}">
                        <td>${onlineOrder.idOrder}</td>
                        <td>${onlineOrder.idCustomer}</td>
                        <td>${onlineOrder.ooDateTime.getHumanDate()}</td>
                        <td>
                            <c:forEach var="product" items="${onlineOrder.products}" varStatus="loop">
                                <a href="<c:url value="/products/details/${product.alias}"/>">${product.name}</a> &times; ${product.quantity}<c:if test="${!loop.last}">, &nbsp</c:if>
                            </c:forEach>
                        </td>
                        <td>${onlineOrder.status}</td>
                        <td>${onlineOrder.status.osDateTime.getHumanDate()}</td>
                        <td>
                            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#editOrderModal" data-id="${onlineOrder.idOrder}">
                                <i class="fa-solid fa-pen-to-square text-primary"></i>
                            </button>
                        </td>
                        <td>
                            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteOrderModal" data-id="${onlineOrder.idOrder}">
                                <i class="fa-solid fa-trash-can text-danger"></i>
                            </button></td>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>


    <div class="modal fade" id="editOrderModal" tabindex="-1" aria-labelledby="editOrderModal" aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false" >
        <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg modal-fullscreen-md-down">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editOrderModalTitle">Edit</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div id="formAlertPlaceholder" class="sticky-top"></div>
                <div class="modal-body">
                    <form id="editOrderForm" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label for="idOrder" class="col-form-label">ID Order:</label>
                            <input type="text" class="form-control" id="idOrder" name="idOrder" required>
                        </div>
                        <div class="mb-3">
                            <label for="idCustomer" class="col-form-label">ID Customer:</label>
                            <input type="text" class="form-control" id="idCustomer" name="idCustomer" required/>
                        </div>
                        <div class="mb-3">
                            <label for="ooDateTime" class="col-form-label">Order Date:</label>
                            <input type="datetime-local" class="form-control" id="ooDateTime" name="ooDateTime" required/>
                        </div>

                        <div class="mb-3">
                            <label for="osDateTime" class="col-form-label">Last Status Update:</label>
                            <input type="datetime-local" class="form-control" id="osDateTime" name="osDateTime" required>
                        </div>

                        Products:
                        <table id="editOrderTable" class="table">
                            <thead>
                                <tr>
                                    <th>Alias</th>
                                    <th>Brand</th>
                                    <th>Name</th>
                                    <th>Quantity</th>
                                    <th>Sale Price</th>
                                </tr>
                            </thead>
                            <tbody></tbody>
                        </table>

                        <div class="mb-3">
                            <label for="status">Status:</label>
                            <div class="input-group">
                                <select class="form-select" name="status" id="status" required>
                                    <option value="" hidden selected disabled>Choose</option>
                                    <option value="${OrderStatusEnum.OPEN}">Open</option>
                                    <option value="${OrderStatusEnum.PAYMENT_ACCEPTED}">Payment accepted</option>
                                    <option value="${OrderStatusEnum.SHIPPED}">Shipped</option>
                                    <option value="${OrderStatusEnum.DELIVERED}">Delivered</option>
                                    <option value="${OrderStatusEnum.CANCELLED}">Cancelled</option>
                                </select>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="col-form-label">Description:</label>
                            <input type="text" class="form-control" id="description" name="description" required>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" id="editOrderButton" form="editOrderForm">Edit</button>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="deleteOrderModal" tabindex="-1" aria-labelledby="deleteOrderModal"
         aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false">
        <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg modal-fullscreen-md-down">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteOrderModalTitle">Delete</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div id="formAlertPlaceholderDelete" class="sticky-top"></div>
                <div class="modal-body">
                    <form id="deleteOrderForm" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label for="idOrderDelete" class="col-form-label">ID Order:</label>
                            <input type="text" class="form-control" id="idOrderDelete" name="idOrder" required>
                        </div>
                        <div class="mb-3">
                            <label for="idCustomerDelete" class="col-form-label">ID Customer:</label>
                            <input type="text" class="form-control" id="idCustomerDelete" name="idCustomer" required/>
                        </div>
                        <div class="mb-3">
                            <label for="ooDateTimeDelete" class="col-form-label">Order Date:</label>
                            <input type="datetime-local" class="form-control" id="ooDateTimeDelete" name="ooDateTime" required/>
                        </div>


                        <div class="mb-3">
                            <label for="osDateTimeDelete" class="col-form-label">Last Status Update:</label>
                            <input type="datetime-local" class="form-control" id="osDateTimeDelete" name="osDateTime" disabled>
                        </div>

                        Products:
                        <table  id="deleteOrderTable" class="table">
                            <thead>
                                <tr>
                                    <th>Alias</th>
                                    <th>Brand</th>
                                    <th>Name</th>
                                    <th>Quantity</th>
                                    <th>Sale Price</th>
                                </tr>
                            </thead>
                            <tbody></tbody>
                        </table>

                        <div class="mb-3">
                            <label for="statusDelete">Status:</label>
                            <div class="input-group">
                                <select class="form-select" name="status" id="statusDelete" disabled>
                                    <option value="${OrderStatusEnum.OPEN}">Open</option>
                                    <option value="${OrderStatusEnum.PAYMENT_ACCEPTED}">Payment accepted</option>
                                    <option value="${OrderStatusEnum.SHIPPED}">Shipped</option>
                                    <option value="${OrderStatusEnum.DELIVERED}">Delivered</option>
                                    <option value="${OrderStatusEnum.CANCELLED}">Cancelled</option>
                                </select>
                            </div>
                        </div>

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-danger" id="deleteOrderButton" form="deleteOrderForm">Delete</button>
                </div>
            </div>
        </div>
    </div>

</div>
<c:import url="/jsp/include/footer-management.jsp"/>

<script type="text/javascript" src="<c:url value="/js/order-management.js"/>"></script>
</body>
</html>