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

</div>
<c:import url="/jsp/include/footer-management.jsp"/>
</body>
</html>