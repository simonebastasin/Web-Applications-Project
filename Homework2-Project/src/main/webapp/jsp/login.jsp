
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="it.unipd.dei.wa2122.wadteam.resources.TypeUserEnum" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Login | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">

<form method="POST" action="<c:url value="/session/login"/>" class="row g-3 w-lg-50 mx-auto needs-validation" novalidate>
    <div class="form-floating mb-3">
        <input type="text" class="form-control" id="logIdentification" name="identification" placeholder="Username or Email" required>
        <label for="logIdentification">Username or Email</label>
        <div class="invalid-feedback">Insert your username or email.</div>

    </div>
    <div class="form-floating mb-3">
        <input type="password" class="form-control" id="logPassword" name="password" placeholder="Password" required>
        <label for="logPassword">Password</label>
        <div class="invalid-feedback">Insert your password.</div>

    </div>
    <div class="mb-3">
        <div class="form-check form-check-inline">
            <input type="radio" class="form-check-input" id="logCUSTOMER" name="usertype" required value="${TypeUserEnum.CUSTOMER}">
            <label class="form-check-label" for="logCUSTOMER">Customer</label>
        </div>
        <div class="form-check form-check-inline">
            <input type="radio" class="form-check-input" id="logEMPLOYEE" name="usertype" required value="${TypeUserEnum.EMPLOYEE}">
            <label class="form-check-label" for="logEMPLOYEE">Employee</label>
        </div>
        <div class = "form-check">
            <input type="radio" class="form-check-input d-none" id="logHIDDEN" name="usertype" required value="HIDDEN">
            <div class="invalid-feedback">Select the type of user you are.</div>
        </div>
    </div>
    <div class="mb-3">
        <button class="btn btn-outline-primary" type="submit" >Login</button>
    </div>


</form>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
