<%--
  Created by IntelliJ IDEA.
  User: pasto
  Date: 03/04/2022
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="user" type="it.unipd.dei.wa2122.wadteam.resources.UserCredential"--%>
<%--@elvariable id="employee" type="it.unipd.dei.wa2122.wadteam.resources.Employee"--%>
<%--@elvariable id="categories" type="java.util.List<ProductCategory>"--%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container header-container">
        <a class="navbar-brand" href="<c:url value="/"/>">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <c:choose>
                <c:when test="${not empty categories}">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
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
                    </ul>
                </c:when>
            </c:choose>
            <form class="input-group w-25" method="GET" action="<c:url value="/products/search"/>">
                <input class="form-control"  type="text" id="q" name="q" pattern="[A-Za-z0-9 ]{1,20}" placeholder="Write here to search" aria-describedby="button-search">
                <input class="btn btn-outline-success" type="submit" value="Go" id="button-search">
            </form>
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <c:choose>
                    <c:when test="${not empty user}">
                        <c:choose>
                            <c:when test="${empty user.role}">
                                <li class="nav-item">
                                    <a class="nav-link" href="<c:url value="/order/list"/>">View Orders</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="<c:url value="/invoice/list"/>">View Invoices</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="<c:url value="/ticket/list"/>">View Tickets</a>
                                </li>
                            </c:when>
                            <c:when test="${not empty user.role}">
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownManagement" role="button" data-bs-toggle="dropdown" aria-expanded="false"  href="#">Management</a>
                                    <ul class="dropdown-menu" aria-labelledby="navbarDropdownManagement">
                                        <li><a class="dropdown-item" href="<c:url value="/ticket/list"/>">View Tickets</a></li>
                                        <li><a class="dropdown-item" href="<c:url value="/invoice/list"/>">View Invoices</a></li>
                                        <li><a class="dropdown-item" href="<c:url value="/management/productManagement/"/>">Product Management</a></li>
                                        <li><a class="dropdown-item" href="<c:url value="/management/discountManagement/"/>">Discount Management</a></li>
                                        <li><a class="dropdown-item" href="<c:url value="/media/list"/>">View Media</a></li>
                                        <li><a class="dropdown-item" href="<c:url value="/media/upload"/>">Upload Media</a></li>
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
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownUser" role="button" data-bs-toggle="dropdown" aria-expanded="false"  href="#">Hello, ${user.identification}</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdownUser">
                                <li><a class="dropdown-item" href="<c:url value="/user/info"/>">Info</a></li>
                                <li><a class="dropdown-item" href="<c:url value="/session/logout"/>">Logout</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/session/login"/>">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"  href="<c:url value="/session/register"/>">Register</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
