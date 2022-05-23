<%--
  Created by IntelliJ IDEA.
  User: pasto
  Date: 03/04/2022
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.unipd.dei.wa2122.wadteam.resources.TypeUserEnum" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%--@elvariable id="user" type="it.unipd.dei.wa2122.wadteam.resources.UserCredential"--%>
<%--@elvariable id="employee" type="it.unipd.dei.wa2122.wadteam.resources.Employee"--%>
<%--@elvariable id="categories" type="java.util.List<ProductCategory>"--%>


<nav class="navbar sticky-top navbar-expand-lg navbar-light bg-light">
    <div class="container header-container">
        <a class="navbar-brand" href="<c:url value="/"/>">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <c:choose>
                <c:when test="${not empty categories}">

                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownCategories" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Categories
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdownCategories">
                                <c:forEach var="cat" items="${categories}">
                                    <li><a class="dropdown-item" href="<c:url value="/products/category/${cat.name}"/>">${cat.name}</a></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:when>
                </c:choose>
            </ul>
            <form class="input-group w-100 w-lg-25" method="GET" id="searchForm" action="<c:url value="/products/search"/>">
                <input class="form-control" type="text" id="searchAutocompleteInput" name="q"  pattern="[A-Za-z0-9 ]{1,20}" placeholder="Write here to search" aria-describedby="button-search" autocomplete="off">
                <ul class="dropdown-menu" id="searchAutocompleteMenu">
                </ul>

                <button class="btn btn-outline-primary" type="submit" id="button-search">
                    <i class="fa-solid fa-search"></i>
                </button>
                </form>
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <c:choose>
                    <c:when test="${not empty user}">
                        <c:choose>
                            <c:when test="${empty user.role}">
                                <li class="nav-item dropdown">
                                    <a class="nav-link " href="#" id="navbarDropdownCart"  data-bs-auto-close="false" role="button"  data-bs-toggle="dropdown" aria-expanded="false"  href="#">
                                        <i class="fa-solid fa-cart-shopping position-relative ">
                                            <span id="numberOfElementCart"  class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary"><span class="visually-hidden">Distinct product</span></span>
                                        </i>
                                    </a>
                                    <ul class="dropdown-menu dropdown-menu-lg-end dropdown-menu-cart drop-cart-menu" id="cart" aria-labelledby="navbarDropdownCart">

                                    </ul>

                                </li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownView" role="button" data-bs-toggle="dropdown" aria-expanded="false"  href="#">View</a>
                                    <ul class="dropdown-menu" aria-labelledby="navbarDropdownManagement">
                                        <li><a class="dropdown-item" href="<c:url value="/order/list"/>">View Orders</a></li>
                                        <li><a class="dropdown-item" href="<c:url value="/invoice/list"/>">View Invoices</a></li>
                                        <li><a class="dropdown-item" href="<c:url value="/ticket/list"/>">View Tickets</a></li>
                                    </ul>
                                </li>
                            </c:when>
                            <c:when test="${not empty user.role}">
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownManagement" role="button" data-bs-toggle="dropdown" aria-expanded="false"  href="#">Management</a>
                                    <ul class="dropdown-menu" aria-labelledby="navbarDropdownManagement">
                                        <li><a class="dropdown-item" href="<c:url value="/ticket/list"/>">View Tickets</a></li>
                                        <li><a class="dropdown-item" href="<c:url value="/invoice/list"/>">View Invoices</a></li>
                                        <li><hr class="dropdown-divider"></li>
                                        <li><a class="dropdown-item" href="<c:url value="/management/productManagement/"/>">Product Management</a></li>
                                        <li><a class="dropdown-item" href="<c:url value="/management/discountManagement/"/>">Discount Management</a></li>
                                        <li><a class="dropdown-item" href="<c:url value="/media/list"/>">Media Management</a></li>
                                        <li><a class="dropdown-item" href="<c:url value="/management/orderManagement/"/>">Order Management</a></li>
                                        <c:choose>
                                            <c:when test="${user.role == 'Administrator'}">
                                                <li><a class="dropdown-item" href="<c:url value="/management/employeeManagement/"/>">Employee Management</a></li>
                                                <li><a class="dropdown-item" href="<c:url value="/management/customerManagement/"/>">Customer Management</a></li>
                                            </c:when>
                                        </c:choose>
                                    </ul>
                                </li>
                            </c:when>
                        </c:choose>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownUser" role="button" data-bs-toggle="dropdown" aria-expanded="false"  href="#">
                                  <span class="fa-stack fa-1x">
                                    <i class="fa fa-circle fa-stack-2x"></i>
                                    <span class="fa-stack-1x fa-inverse">${fn:toUpperCase(fn:substring(user.identification, 0, 2))}</span>
                                  </span>
                                <span>Hello, ${user.identification}</span>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdownUser">
                                <li><a class="dropdown-item" href="<c:url value="/user/info"/>">
                                    <i class="fa-solid fa-gear"></i>
                                    Info</a></li>
                                <li><a class="dropdown-item" href="<c:url value="/session/logout"/>" id="logoutButton" >
                                    <i class="fa-solid fa-right-from-bracket"></i>
                                    Logout</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item dropdown">
                                <a href="#" class="nav-link" type="button" id="dropdownMenuLogin" data-bs-toggle="dropdown" data-bs-auto-close="false" aria-expanded="false">
                                    <i class="fa-solid fa-right-to-bracket"></i>
                                    Login
                                </a>
                                <div class="dropdown-menu dropdown-menu-lg-end dropdown-menu-login" aria-labelledby="dropdownMenuLogin">
                                    <div id="liveAlertPlaceholderDrop" class="sticky-top"></div>
                                    <form class="px-4 py-3 g-3 needs-validation" novalidate id="loginFormDrop">
                                        <div class="form-floating mb-3">
                                            <input type="text" class="form-control" id="identification" name="identification" placeholder="Username or Email" required>
                                            <label for="identification">Username or Email</label>
                                            <div class="invalid-feedback">Insert your username or email.</div>
                                        </div>
                                        <div class="form-floating mb-3">
                                            <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
                                            <label for="password">Password</label>
                                            <div class="invalid-feedback">Insert your password.</div>
                                        </div>

                                        <div class="mb-3">
                                            <div class="form-check form-check-inline">
                                                <input type="radio" class="form-check-input" id="CUSTOMER" name="usertype" required value="${TypeUserEnum.CUSTOMER}">
                                                <label class="form-check-label" for="CUSTOMER">Customer</label>
                                            </div>
                                            <div class="form-check form-check-inline">
                                                <input type="radio" class="form-check-input" id="EMPLOYEE" name="usertype" required value="${TypeUserEnum.EMPLOYEE}">
                                                <label class="form-check-label" for="EMPLOYEE">Employee</label>
                                            </div>
                                            <div class = "form-check">
                                                <input type="radio" class="form-check-input d-none" id="HIDDEN" name="usertype" required value="HIDDEN">
                                                <div class="invalid-feedback">Select the type of user you are.</div>
                                            </div>
                                        </div>
                                            <button class="btn btn-outline-primary" type="submit">Login</button>
                                    </form>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item"  href="<c:url value="/session/register"/>">Register</a>
                                </div>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
