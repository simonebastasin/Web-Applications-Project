<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 31/03/2022
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="user" type="it.unipd.dei.wa2122.wadteam.resources.UserCredential"--%>
<%--@elvariable id="employee" type="it.unipd.dei.wa2122.wadteam.resources.Employee"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>User: <c:out value="${employee.username}"/> | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav class="custom-breadcrumb-divider" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">User: <c:out value="${employee.username}"/></li>
  </ol>
</nav>

    <div id="liveAlertPlaceholder"></div>

    <h5>User Info</h5>
        <ul class="list-group list-group-flush" data-id="${employee.username}">
            <li class="list-group-item">Name: ${employee.name}</li>
            <li class="list-group-item">Surname: ${employee.surname}</li>
            <li class="list-group-item">Role: ${employee.role.toString()}</li>
        </ul>
        <br>

    <a href="<c:url value="/user/modify"/>" class="btn" type="button" data-bs-toggle="modal" data-bs-target="#editUserModal" data-id="${employee.username}">
        <i class="fa-solid fa-pen-to-square text-primary"></i>
        <span class="text-primary">Edit</span>
    </a>

    <a href="<c:url value="/user/password"/>" class="btn" type="button" data-bs-toggle="modal" data-bs-target="#changePasswordModal" data-id="${employee.username}">
        <i class="fa-solid fa-key text-danger"></i>
        <span class="text-danger">Change Password</span>
    </a>

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
                        <input type="text" id="name" name="name" class="form-control" value="${employee.name}" required placeholder="name">
                        <label for="name">Name </label>
                        <div class="invalid-feedback">Insert your name.</div>
                    </div>

                    <div class="form-floating mb-3">
                        <input type="text" id="surname" name="surname" class="form-control" value="${employee.surname}" required placeholder="surname">
                        <label for="surname">Surname </label>
                        <div class="invalid-feedback">Insert your surname.</div>
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
                <form class="row g-3 needs-validation" novalidate id="formConfirmPassword">
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
<script src="<c:url value="/js/check-password.js"/>"></script>
<script src="<c:url value="/js/user.js"/>"></script>
</body>
</html>