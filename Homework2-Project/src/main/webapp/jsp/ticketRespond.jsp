<%--
  Created by IntelliJ IDEA.
  User: GK10
  Date: 09/04/2022
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.unipd.dei.wa2122.wadteam.resources.TicketStatusEnum" %>
<%--@elvariable id="assistanceTicketList" type="List<it.unipd.dei.wa2122.wadteam.resources.AssistanceTicket>"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Ticket List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Ticket List</li>
  </ol>
</nav>

    <style>
        .ticket-OPEN{color: green;}
        .ticket-PROCESSING{color: blue;}
        .ticket-CLOSED{color: red;}
        .ticket-RETURN{color: black;}
    </style>

    <c:forEach var="assistanceTicket" items="${assistanceTicketList}">
    <div class="card">
        <div class="card-body">
            <h5 class="card-title">Ticket ID: ${assistanceTicket.id}</h5>
            <ul class="list-group">
            <li class="list-group-item">${assistanceTicket.description}</li>
            <li class="list-group-item">Customer: ${assistanceTicket.idCustomer}</li>
            <li class="list-group-item"><a href="<c:url value="/products/details/${assistanceTicket.productAlias}"/>">Product ${assistanceTicket.productAlias}</a></li>

                <c:forEach var="item" items="${assistanceTicket.ticketStatusList}">
                    <li class="list-group-item"><b> Ticket Status : <span class = "ticket-${item.status}">${item.status}</span></b></li>
                    <li class="list-group-item"><i>${item.description}</i></li>
                    <li class="list-group-item">${item.tsDate.humanDate}</li>
                </c:forEach>
            </ul>

        <c:choose>
            <c:when test="${empty assistanceTicket.ticketStatusList}" >
                <a href="<c:url value="/ticket/respond/${assistanceTicket.id}"/>" class="btn btn-primary">Respond</a>
            </c:when>
            <c:when test="${not (assistanceTicket.ticketStatusList[0].status eq TicketStatusEnum.CLOSED) }">
                <a href="<c:url value="/ticket/respond/${assistanceTicket.id}"/>" class="btn btn-primary">Respond</a>
            </c:when>
            <c:otherwise>
                <li class="list-group-item">The ticket is closed</li>
            </c:otherwise>
        </c:choose>

        </div>
    </div>
    <br>
    </c:forEach>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
