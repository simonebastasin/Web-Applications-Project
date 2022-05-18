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
    <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
        <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
            <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
            <li class="breadcrumb-item active" aria-current="page">Login</li>
        </ol>
    </nav>

    <ul class="nav nav-pills nav-justified mb-3" id="pills-tab" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link active" id="pills-profile-tab" data-bs-toggle="pill" data-bs-target="#pills-profile" type="button" role="tab" aria-controls="pills-profile" aria-selected="true">Register</button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="pills-home-tab" data-bs-toggle="pill" data-bs-target="#pills-home" type="button" role="tab" aria-controls="pills-home" aria-selected="false">Login</button>
        </li>

    </ul>

    <div class="tab-content card p-3 mb-3" id="pills-tabContent">
        <div class="tab-pane fade" id="pills-home" role="tabpanel" aria-labelledby="pills-home-tab">
            <form method="POST" action="<c:url value="/session/login"/>" class="row g-3 w-50 mx-auto needs-validation" novalidate>

                    <div class="form-floating mb-4">
                        <input type="text" class="form-control" id="logIdentification" name="identification" placeholder="Username or Email" required>
                        <label for="logIdentification">Username or Email</label>
                        <div class="invalid-feedback">Insert your username or email.</div>
                    </div>


                <div class="form-floating mb-4">
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


                <button type="submit" class="btn btn-primary btn-block mb-4">Sign in</button>

            </form>
        </div>

        <div class="tab-pane fade  show active" id="pills-profile" role="tabpanel" aria-labelledby="pills-profile-tab">
            <form method="post" action="<c:url value="/session/register"/>" class="row g-3 w-50 mx-auto needs-validation" novalidate id="formConfirmPassword">

                <div class="form-floating mb-4">
                    <input id="name" type="text" class="form-control" name="name" required placeholder="name">
                    <label for="name">Name</label>
                    <div class="invalid-feedback">Insert your name.</div>
                </div>

                <div class="form-floating mb-3">
                    <input id="surname" type="text" class="form-control" name="surname" required placeholder="surname">
                    <label for="surname">Surname</label>
                    <div class="invalid-feedback">Insert your surname.</div>
                </div>

                <div class="form-floating mb-4">
                    <input id="fiscalCode" type="text" class="form-control" name="fiscalCode" required placeholder="fiscal code">
                    <label for="fiscalCode">Fiscal Code</label>
                    <div class="invalid-feedback">Insert your fiscal code.</div>
                </div>

                <div class="form-floating mb-4">
                    <input id="email" type="email" class="form-control" name="email" required placeholder="name@example.com">
                    <label for="email">Email</label>
                    <div class="invalid-feedback">Insert your email.</div>
                </div>

                <div class="form-floating mb-4">
                    <input id="phoneNumber" type="text" class="form-control" name="phoneNumber" placeholder="+39">
                    <label for="phoneNumber">Phone Number</label>
                </div>

                <div class="form-floating mb-4">
                    <input id="address" type="text" class="form-control" name="address" placeholder="Via Padova 1, Padova (PD)">
                    <label for="address">Address</label>
                </div>

                <div class="form-floating mb-4">
                    <input id="username" type="text" class="form-control" name="username" placeholder="username" required>
                    <label for="username">Username</label>
                    <div class="invalid-feedback">Insert your username.</div>
                </div>

                <div class="form-floating mb-4">
                    <input id="newPassword" type="password" class="form-control" name="newPassword" placeholder="password" required>
                    <label for="newPassword">Password</label>
                    <div class="invalid-feedback" id="newPasswordFeedback">Insert the password</div>
                </div>

                <div class="form-floating mb-4">
                    <input id="confirmPassword" type="password" class="form-control" name="confirmPassword" placeholder="confirm_password" required>
                    <label for="confirmPassword">Confirm Password</label>
                    <div class="invalid-feedback" id="confirmPasswordFeedback">Confirm your password</div>
                </div>

                <button type="submit" class="btn btn-primary btn-block mb-3">Register</button>
            </form>
        </div>
    </div>
    <c:import url="/jsp/include/footer.jsp"/>
    <script src="<c:url value="/js/check-password.js"/>"></script>
</body>
</html>
