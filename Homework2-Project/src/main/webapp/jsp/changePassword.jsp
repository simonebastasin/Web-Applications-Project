<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="customer" type="it.unipd.dei.wa2122.wadteam.resources.Customer"--%>
<%--@elvariable id="user" type="it.unipd.dei.wa2122.wadteam.resources.UserCredential"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Change password ${user.identification} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Change password</li>
  </ol>
</nav>

<form method="post" action="<c:url value="/user/password"/>" class="row g-3 needs-validation" novalidate>
    <div class="form-floating mb-3">
        <input id="oldPassword" type="password" class="form-control" name="oldPassword" placeholder="password" required>
        <label for="oldPassword">Old Password:</label>
        <div class="invalid-feedback">Insert the current password.</div>
    </div>
    <div class="form-floating mb-3">
        <input id="password" type="password" class="form-control" name="newPassword" placeholder="password" required>
        <label for="password">New Password</label>
        <div class="invalid-feedback">Insert the new password.</div>
    </div>
    <div class="form-floating mb-3">
        <input id="confirm_password" type="password" class="form-control" name="confirm_password" placeholder="confirm_password">
        <label for="confirm_password">Confirm New Password</label>
        <div class="invalid-feedback">Confirm your password</div>
    </div>
    <span id="Message"></span><br>

        <input type="submit" id="submit" value="Submit" class = "btn btn-primary">
        <a href="<c:url value="/user/info"/>" class="btn btn-danger">Cancel changes</a>
</form>


</div>
<c:import url="/jsp/include/footer.jsp"/>
<script src="<c:url value="/check-password.js"/>"></script>
</body>
</html>

