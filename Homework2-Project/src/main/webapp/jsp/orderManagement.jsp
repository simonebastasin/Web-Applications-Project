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
    <div id="liveAlertPlaceholder"></div>

    <div class="card">
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
            <tbody>
                <c:forEach var="onlineOrder" items="${onlineOrderList}">
                    <tr data-id="${onlineOrder.idOrder}">
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
                            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#editOrderModal" data-id="${onlineOrder.idOrder}">
                                <i class="fa-solid fa-pen-to-square text-primary"></i>
                            </button>
                            <!--<a href="<c:url value="/management/orderManagement/editOrder/${onlineOrder.idOrder}"/>">Edit</a>-->
                        </td>
                        <td>
                            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteOrderModal" data-id="${onlineOrder.idOrder}">
                                <i class="fa-solid fa-trash-can text-danger"></i>
                            </button></td>
                            <!--<a href="<c:url value="/management/orderManagement/deleteOrder/${onlineOrder.idOrder}"/>">Delete</a>-->
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
                            <input type="text" class="form-control" id="ooDateTime" name="ooDateTime" required/>
                        </div>

                        Products:
                        <table>
                            <tr>
                                <th>Alias</th>
                                <th>Brand</th>
                                <th>Name</th>
                                <th>Quantity</th>
                                <th>Sale Price</th>
                            </tr>
                            <c:forEach var="product" items="${products}">
                                <tr data-id="${product.alias}">

                                    <td>1${product.alias}</td>
                                    <td>2${product.brand}</td>
                                    <td>3${product.name}</td>
                                    <td>4${product.quantity}</td>
                                    <td>5${product.salePrice}</td>
                                </tr>
                            </c:forEach>
                        </table>

                        <div class="mb-3">
                            <label for="status">Status:</label>
                            <div class="input-group">
                                <select class="form-control" name="status" id="status" required>
                                    <option value="" hidden selected disabled>Choose</option>
                                    <option value="${OrderStatusEnum.OPEN}" selected>${status.status}</option>
                                    <option value="${OrderStatusEnum.PAYMENT_ACCEPTED}" selected>${OrderStatusEnum.PAYMENT_ACCEPTED}</option>
                                    <option value="${OrderStatusEnum.SHIPPED}" selected>Shipped</option>
                                    <option value="${OrderStatusEnum.DELIVERED}" selected>Delivered</option>
                                    <option value="${OrderStatusEnum.CANCELLED}" selected>Cancelled</option>
                                </select>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="osDateTime" class="col-form-label">Last Status Update:</label>
                            <input class="form-control" id="osDateTime" name="osDateTime" required>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="col-form-label">Description:</label>
                            <input class="form-control" id="description" name="description" required>
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
                            <input type="text" class="form-control" id="ooDateTimeDelete" name="ooDateTime" required/>
                        </div>

                        Products:
                        <table>
                            <tr>
                                <th>Alias</th>
                                <th>Brand</th>
                                <th>Name</th>
                                <th>Quantity</th>
                                <th>Sale Price</th>
                            </tr>
                            <c:forEach var="product" items="${products}">
                                <tr data-id="${product.alias}">

                                    <td>${product.alias}</td>
                                    <td>${product.brand}</td>
                                    <td>${product.name}</td>
                                    <td>${product.quantity}</td>
                                    <td>${product.salePrice}</td>
                                </tr>
                            </c:forEach>
                        </table>

                        <div class="mb-3">
                            <label for="statusDelete" class="col-form-label">Status:</label>
                            <input type="text" class="form-control" id="statusDelete" name="status" required/>
                        </div>

                        <div class="mb-3">
                            <label for="osDateTimeDelete" class="col-form-label">Last Status Update:</label>
                            <input class="form-control" id="osDateTimeDelete" name="osDateTime" required>
                        </div>
                        <div class="mb-3">
                            <label for="descriptionDelete" class="col-form-label">Description:</label>
                            <input class="form-control" id="descriptionDelete" name="description" required>
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