<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 21/04/2022
  Time: 01:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="customer" type="it.unipd.dei.wa2122.wadteam.resources.Customer"--%>
<%--@elvariable id="customerList" type="java.util.List<it.unipd.dei.wa2122.wadteam.resources.Customer>"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head-management.jsp"/>

    <title>Customer List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>

<div class="container main-container">
    <nav class="custom-breadcrumb-divider" aria-label="breadcrumb">
      <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
        <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
        <li class="breadcrumb-item active" aria-current="page">Customer List</li>
      </ol>
    </nav>
    <div id="liveAlertPlaceholder" class="sticky-top"></div>

    <div class="card card-body">
        <table id="customerTable" class="table table-hover">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Fiscal Code</th>
                    <th>Address</th>
                    <th>Email</th>
                    <th>Phone Number</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody class="align-middle">
                <c:forEach var="customer" items="${customerList}">
                    <tr data-id="${customer.username}">

                        <td>${customer.id}</td>
                        <td>${customer.username}</td>
                        <td>${customer.name}</td>
                        <td>${customer.surname}</td>
                        <td>${customer.fiscalCode}</td>
                        <td>${customer.address}</td>
                        <td>${customer.email}</td>
                        <td>${customer.phoneNumber}</td>
                        <td>
                            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#editCustomerModal" data-id="${customer.username}">
                                <i class="fa-solid fa-pen-to-square text-primary"></i>
                            </button>
                        </td>
                        <td>
                            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteCustomerModal" data-id="${customer.username}">
                                <i class="fa-solid fa-trash-can text-danger"></i>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>


    <div class="modal fade" id="editCustomerModal" tabindex="-1" aria-labelledby="editCustomerModal" aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false" >
        <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg modal-fullscreen-md-down">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editCustomerModalTitle">Edit</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div id="formAlertPlaceholder" class="sticky-top"></div>
                <div class="modal-body">
                    <form id="editCustomerForm" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label for="id" class="col-form-label">ID:</label>
                            <input type="text" class="form-control" id="id" name="id" required>
                        </div>
                        <div class="mb-3">
                            <label for="username" class="col-form-label">Username:</label>
                            <input type="text" class="form-control" id="username" name="username" required/>
                        </div>
                        <div class="mb-3">
                            <label for="name" class="col-form-label">Name:</label>
                            <input type="text" class="form-control" id="name" name="name" required/>
                        </div>
                        <div class="mb-3">
                            <label for="surname" class="col-form-label">Surname:</label>
                            <input type="text" class="form-control" id="surname" name="surname" required/>
                        </div>
                        <div class="mb-3">
                            <label for="fiscalCode" class="col-form-label">Fiscal Code:</label>
                            <input type="text" class="form-control" id="fiscalCode" name="fiscalCode" required/>
                        </div>
                        <div class="mb-3">
                            <label for="address" class="col-form-label">Address:</label>
                            <input type="text" class="form-control" id="address" name="address" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="col-form-label">Email:</label>
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>
                        <div class="mb-3">
                            <label for="phoneNumber" class="col-form-label">Phone Number:</label>
                            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" required>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" id="editCustomerButton" form="editCustomerForm">Edit</button>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="deleteCustomerModal" tabindex="-1" aria-labelledby="deleteCustomerModal"
         aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false">
        <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg modal-fullscreen-md-down">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteCustomerModalTitle">Delete</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div id="formAlertPlaceholderDelete" class="sticky-top"></div>
                <div class="modal-body">
                    <form id="deleteCustomerForm" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label for="usernameDelete" class="col-form-label">Username:</label>
                            <input type="text" class="form-control" id="usernameDelete" name="username" required>
                        </div>
                        <div class="mb-3">
                            <label for="nameDelete" class="col-form-label">Name:</label>
                            <input type="text" class="form-control" id="nameDelete" name="name" required/>
                        </div>
                        <div class="mb-3">
                            <label for="surnameDelete" class="col-form-label">Surname:</label>
                            <input type="text" class="form-control" id="surnameDelete" name="surname" required/>
                        </div>
                        <div class="mb-3">
                            <label for="fiscalCodeDelete" class="col-form-label">Fiscal Code:</label>
                            <input type="text" class="form-control" id="fiscalCodeDelete" name="fiscalCode" required/>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-danger" id="deleteCustomerButton" form="deleteCustomerForm">Delete</button>
                </div>
            </div>
        </div>
    </div>

</div>
<c:import url="/jsp/include/footer-management.jsp"/>

<script type="text/javascript" src="<c:url value="/js/customer-management.js"/>"></script>
</body>
</html>