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

<a href="<c:url value="/"/>">Home</a>
<a href="<c:url value="/media/list"/>">View Media</a>
<a href="<c:url value="/order/list"/>">Your orders</a>
<a href="<c:url value="/ticket/list"/>">View Ticket</a>
<c:choose>
    <c:when test="${not empty user}">
        Hello, <a href="<c:url value="/user/info/${user.type.toString()}/${user.identification}"/>">${user.identification}</a>
        <a href="<c:url value="/user/logout"/>">Logout</a>
    </c:when>
    <c:otherwise>
        <a href="<c:url value="/login"/>">Login</a>
        <a href="<c:url value="/user/register"/>">Register</a>
    </c:otherwise>
</c:choose>



<hr />
