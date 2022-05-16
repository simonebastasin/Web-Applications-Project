<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 21/04/2022
  Time: 01:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="customer" type="it.unipd.dei.wa2122.wadteam.resources.Customer"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Customer List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
    <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
      <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
        <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
        <li class="breadcrumb-item active" aria-current="page">Customer List</li>
      </ol>
    </nav>
    <div id="liveAlertPlaceholder"></div>

    <div class="card">
        <table id="customerTable" class="table table-hover">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Fiscal_Code</th>
                    <th>Address</th>
                    <th>Email</th>
                    <th>Phone_Number</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="customer" items="${customerList}">
                    <tr>
                        <td>${customer.id}</td>
                        <td>${customer.username}</td>
                        <td>${customer.name}</td>
                        <td>${customer.surname}</td>
                        <td>${customer.fiscalCode}</td>
                        <td>${customer.address}</td>
                        <td>${customer.email}</td>
                        <td>${customer.phoneNumber}</td>
                        <td>
                            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#editCustomerModal" data-bs-whatever="${customer.username}">
                                <i class="fa-solid fa-pen-to-square text-primary"></i>
                            </button>
                            <!--<a href="<c:url value="/management/customerManagement/editCustomer/${customer.username}"/>">
                                Edit
                            </a>-->
                        </td>
                        <td>
                            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteCustomerModal" data-bs-whatever="${customer.username}">
                                <i class="fa-solid fa-trash-can text-danger"></i>
                            </button>
                            <!--<a href="<c:url value="/management/customerManagement/deleteCustomer/${customer.username}"/>">
                                Delete
                            </a>-->
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>