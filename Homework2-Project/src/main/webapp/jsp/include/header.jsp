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
            <form class="input-group w-25" method="GET" id="searchForm" action="<c:url value="/products/search"/>">
                <input class="form-control" type="text" id="searchAutocompleteInput" name="q"  pattern="[A-Za-z0-9 ]{1,20}" placeholder="Write here to search" aria-describedby="button-search" autocomplete="off">
                <ul class="dropdown-menu" id="searchAutocompleteMenu">
                </ul>

                <input class="btn btn-outline-success" type="submit" value="Go" id="button-search">
            </form>
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <c:choose>
                    <c:when test="${not empty user}">
                        <c:choose>
                            <c:when test="${empty user.role}">
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownCart" role="button" data-bs-toggle="dropdown" aria-expanded="false" onclick="presentCart()" href="#">Cart</a>
                                    <ul class="dropdown-menu" id="cart" aria-labelledby="navbarDropdownCart">

                                    </ul>
                                    <script>

                                            function invalidate()
                                            {
                                                localStorage.clear();
                                            }

                                        function presentCart()
                                        {
                                            var text="";
                                            if(localStorage.length==0)
                                                text='<li><a class="dropdown-item" >Empty</li>';
                                            for (let i = 0; i < localStorage.length; i++) {
                                                console.log(localStorage.length)
                                                if(localStorage.key(i).substring(0,3)=="cart")
                                                    console.log(localStorage.getItem(localStorage.key(i)));
                                                const element=localStorage.getItem(localStorage.key(i)).split(";")
                                                const qta=element[0];
                                                const name=element[1];
                                                text+='<li><a class="dropdown-item" >'+ name+" qt"+qta +'</li>';
                                            }
                                            console.log(text)
                                            const list=document.getElementById("cart");
                                            list.innerHTML=text;
                                        }

                                    </script>
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
                                <li><a class="dropdown-item" href="<c:url value="/session/logout"/>" onclick="invalidate()">Logout</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item dropdown">
                                <a href="#" class="nav-link dropdown-toggle" type="button" id="dropdownMenuLogin" data-bs-toggle="dropdown" data-bs-auto-close="false" aria-expanded="false">
                                    Login
                                </a>
                                <div class="dropdown-menu dropdown-menu-lg-end dropdown-menu-login" aria-labelledby="dropdownMenuLogin">
                                    <form class="px-4 py-3 g-3 needs-validation" novalidate method="POST" action="<c:url value="/session/login"/>">
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
                                    <a class="btn btn-outline-primary"  href="<c:url value="/session/register"/>">Register</a>
                                </div>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
