<%--
  Created by IntelliJ IDEA.
  User: GK10
  Date: 02/04/2022
  Time: 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="assistanceTicketList" type="List<it.unipd.dei.wa2122.wadteam.resources.AssistanceTicket>"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Your Ticket List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Your Ticket List</li>
  </ol>
</nav>

<ul>
    <style>
        .ticket-OPEN{color: green;}
        .ticket-PROCESSING{color: blue;}
        .ticket-CLOSED{color: red;}
        .ticket-RETURN{color: black;}
    </style>

    <c:forEach var="assistanceTicket" items="${assistanceTicketList}">
        <li><h2>Ticket ID: ${assistanceTicket.id}</h2>
        <ul>
        <li>${assistanceTicket.description}</li>
            <li><a href="<c:url value="/products/details/${assistanceTicket.productAlias}"/>">Product ${assistanceTicket.productAlias}</a></li>

        <c:forEach var="item" items="${assistanceTicket.ticketStatusList}">
            <li><b> Ticket Status : <span class = "ticket-${item.status}">${item.status}</span></b></li>
            <ul><li><i>${item.description}</i></li>
            <li>${item.tsDate.humanDate}</li></ul>
        </c:forEach>
        </ul>
        </li>
        <br>
    </c:forEach>
</ul>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
