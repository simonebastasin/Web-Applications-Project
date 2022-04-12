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

<a href="<c:url value="/"/>">Home</a>

<select  name="category" id="category" onchange="location = this.value;" autofocus>
    <option value="label">-- categories --</option>
    <c:forEach var="cat" items="${categories}">
        <option value="<c:url value="/products/category"/>/${cat.name}">
        ${cat.name}
        </option>
    </c:forEach>
</select>

<form method="GET" action="<c:url value="/products/search"/>">
<input type="text" id="q" name="q" pattern="[A-Za-z0-9]{1,20}">
<input type="submit" value="Submit">
</form>


<c:choose>
    <c:when test="${not empty user}">
        <a href="<c:url value="/ticket/list"/>">View Ticket</a>

        <c:choose>
            <c:when test="${empty user.role}">
                <a href="<c:url value="/order/list"/>">Your orders</a>
            </c:when>
        </c:choose>
        &nbsp | &nbsp
        Hello, <a href="<c:url value="/user/${user.type.toString()}/info"/>">${user.identification}</a>
        <c:choose>
            <c:when test="${not empty user.role}">
            <select  name="management" id="management" onchange="location = this.value;" autofocus>
                <option value="label">-- infos --</option>
                <option value="<c:url value="/management/productManagement"/>">Product Management</option>
                <option value="<c:url value="/management/discountManagement"/>">Discount Management</option>
                <option value="<c:url value="/management/orderManagement"/>">Order Management</option>
                <option value="<c:url value="/media/list"/>">View Media</option>
                <c:choose>
                    <c:when test="${user.role == 'Administrator'}">
                        <option value="<c:url value="/management/userManagement"/>">User Management</option>
                        <option value="<c:url value="/management"/>">Data Export</option>
                    </c:when>
                </c:choose>
            </select>
            </c:when>
        </c:choose>
        <a href="<c:url value="/session/logout"/>">Logout</a>
    </c:when>
    <c:otherwise>
        &nbsp | &nbsp
        <a href="<c:url value="/session/login"/>">Login</a>
        <a href="<c:url value="/session/register"/>">Register</a>
    </c:otherwise>
</c:choose>

<hr />
