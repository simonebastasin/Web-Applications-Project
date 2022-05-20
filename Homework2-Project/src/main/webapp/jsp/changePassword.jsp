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
<nav class="custom-breadcrumb-divider" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Change password</li>
  </ol>
</nav>
    <div id="liveAlertPlaceholder" class="sticky-top"></div>
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

    <div class="mb-3">
        <input type="submit" id="submit" value="Submit" class = "btn btn-primary">
        <a href="<c:url value="/user/info"/>" class="btn btn-danger" id="cancel">Cancel changes</a>
    </div>
</form>


</div>
<c:import url="/jsp/include/footer.jsp"/>
<script src="<c:url value="/js/check-password.js"/>"></script>
<script src="<c:url value="/js/password-change-error.js"/>"></script>
</body>
</html>

