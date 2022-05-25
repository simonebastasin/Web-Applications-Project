<%--
  Created by IntelliJ IDEA.
  User: matte
  Date: 07/04/2022
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="customer" type="it.unipd.dei.wa2122.wadteam.resources.Customer"--%>
<%--@elvariable id="user" type="it.unipd.dei.wa2122.wadteam.resources.UserCredential"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Customer: ${customer.username} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav class="custom-breadcrumb-divider" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Customer: ${customer.username}</li>
  </ol>
</nav>

    <div id="liveAlertPlaceholder"></div>
    <div class="card w-lg-50 mx-auto bg-white rounded">
        <div class="card-header">
            <h5 class="card-title">My Account</h5>
        </div>
        <ul class="list-group list-group-flush" data-id="${customer.username}">
            <li class="list-group-item"><b>Name</b>: ${customer.name}</li>
            <li class="list-group-item"><b>Surname</b>: ${customer.surname}</li>
            <li class="list-group-item"><b>Address</b>: ${customer.address}</li>
            <li class="list-group-item"><b>Phone number</b>: ${customer.phoneNumber}</li>
            <li class="list-group-item"><b>Email</b>: ${customer.email}</li>
            <li class="list-group-item"><b>Fiscal code</b>: ${customer.fiscalCode}</li>
        </ul>
        <div class="card-footer">
            <a href="<c:url value="/user/modify"/>" class="btn" type="button" data-bs-toggle="modal" data-bs-target="#editUserModal" data-id="${customer.username}">
                <i class="fa-solid fa-pen-to-square text-primary"></i>
                <span class="text-primary">Edit</span>
            </a>
            <a href="<c:url value="/user/password"/>" class="btn" type="button" data-bs-toggle="modal" data-bs-target="#changePasswordModal" data-id="${customer.username}">
                <i class="fa-solid fa-key text-danger"></i>
                <span class="text-danger">Change Password</span>
            </a>
        </div>
    </div>

</div>

<div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModal" aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false" >
    <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg modal-fullscreen-md-down">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editUserModalTitle">Edit User</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div id="formAlertPlaceholder" class="sticky-top"></div>
            <div class="modal-body">
                <form class="row g-3 needs-validation" novalidate id="editUserForm">
                    <div class="form-floating mb-3">
                        <input type="text" id="name" name="name" class="form-control" value="${customer.name}" required placeholder="name">
                        <label for="name">Name </label>
                        <div class="invalid-feedback">Insert your name.</div>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" id="surname" name="surname" class="form-control" value="${customer.surname}" required placeholder="surname">
                        <label for="surname">Surname </label>
                        <div class="invalid-feedback">Insert your surname.</div>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" id="fiscalCode" name="fiscalCode" class="form-control" value="${customer.fiscalCode}" required placeholder="xxxxxxxxxxxxxxxx">
                        <label for="fiscalCode">Fiscal Code </label>
                        <div class="invalid-feedback">Insert your fiscal code.</div>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" id="phoneNumber" name="phoneNumber" class="form-control" value="${customer.phoneNumber}" placeholder="+39">
                        <label for="phoneNumber">Phone Number </label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" id="address" name="address" class="form-control" value="${customer.address}" placeholder="Via Padova 1, Padova (PD)">
                        <label for="address">Address </label>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="submit" class = "btn btn-primary" form="editUserForm">Edit info</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="changePasswordModal" tabindex="-1" aria-labelledby="changePasswordModal" aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false" >
    <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg modal-fullscreen-md-down">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="changePasswordModalTitle">Change Password</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div id="formPasswordAlertPlaceholder" class="sticky-top"></div>
            <div class="modal-body">
                <form class="row g-3 needs-validation" novalidate id="formConfirmPassword" data-confirm-password>
                    <div class="form-floating mb-3">
                        <input id="oldPassword" type="password" class="form-control" name="oldPassword" placeholder="Old Password" required>
                        <label for="oldPassword">Old Password</label>
                        <div class="invalid-feedback" id="oldPasswordFeedback">Insert the current password.</div>
                    </div>
                    <div class="form-floating mb-3">
                        <input id="newPassword" type="password" class="form-control" name="newPassword" placeholder="New Password" required>
                        <label for="newPassword">New Password</label>
                        <div class="invalid-feedback"  id="newPasswordFeedback">Insert the new password.</div>
                    </div>
                    <div class="form-floating mb-3">
                        <input id="confirmPassword" type="password" class="form-control" name="confirmPassword" placeholder="Confirm Password" required>
                        <label for="confirmPassword">Confirm New Password</label>
                        <div class="invalid-feedback"  id="confirmPasswordFeedback">Confirm your password.</div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="submit" class = "btn btn-danger" form="formConfirmPassword">Change Password</button>
            </div>
        </div>
    </div>
</div>

<c:import url="/jsp/include/footer.jsp"/>

<script src="<c:url value="/js/user.js"/>"></script>
</body>
</html>
