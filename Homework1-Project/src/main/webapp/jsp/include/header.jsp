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

<c:choose>
    <c:when test="${not empty user}">
        <a href="<c:url value="/ticket/list"/>">View Ticket</a>

        <c:choose>
            <c:when test="${user.role == 'Administrator'}">
                <a href="<c:url value="/management"/>">Product Management</a>>
                <a href="<c:url value="/management"/>">User Management</a>
                <a href="<c:url value="/management"/>">Discount Management</a
                <a href="<c:url value="/management"/>">Data Export</a>
                <a href="<c:url value="/media/list"/>">View Media</a>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${empty user.role}">
                        <a href="<c:url value="/order/list"/>">Your orders</a>
                    </c:when>
                </c:choose>
            </c:otherwise>

        </c:choose>
        &nbsp | &nbsp
        Hello, <a href="<c:url value="/user/${user.type.toString()}/info"/>">${user.identification}</a>

        <a href="<c:url value="/session/logout"/>">Logout</a>
    </c:when>
    <c:otherwise>
        &nbsp | &nbsp
        <a href="<c:url value="/session/login"/>">Login</a>
        <a href="<c:url value="/session/register"/>">Register</a>
    </c:otherwise>
</c:choose>

<hr />
