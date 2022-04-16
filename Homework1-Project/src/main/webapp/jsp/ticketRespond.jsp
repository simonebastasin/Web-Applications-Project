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

<html>
<head>
    <title>Ticket Respond</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>
<h1> Ticket List </h1>
<ul>
    <style>
        .ticket-OPEN{color: green;}
        .ticket-PROCESSING{color: blue;}
        .ticket-CLOSED{color: red;}
        .ticket-RETURN{color: black;}
    </style>

    <c:forEach var="assistanceTicket" items="${assistanceTicketList}">
        <b>Ticket ID: ${assistanceTicket.id}</b>
            <ul>
                <li>${assistanceTicket.description}</li>
                <li>Customer: ${assistanceTicket.idCustomer}</li>
                <li><a href="<c:url value="/products/details/${assistanceTicket.productAlias}"/>">Product ${assistanceTicket.productAlias}</a></li>

                <c:forEach var="item" items="${assistanceTicket.ticketStatusList}">
                    <li><b> Ticket Status : <span class = "ticket-${item.status}">${item.status}</span></b></li>
                    <ul><li><i>${item.description}</i></li>
                        <li>${item.tsDate}</li></ul>
                </c:forEach>
            </ul>

        <c:choose>
            <c:when test="${empty assistanceTicket.ticketStatusList}" >
                <a href="<c:url value="/ticket/respond/${assistanceTicket.id}"/>">Respond</a>
            </c:when>
            <c:when test="${not (assistanceTicket.ticketStatusList[0].status eq TicketStatusEnum.CLOSED) }">
                <a href="<c:url value="/ticket/respond/${assistanceTicket.id}"/>">Respond</a>
            </c:when>
            <c:otherwise>The ticket is closed</c:otherwise>
        </c:choose>

        </li>
        <br>
    </c:forEach>
</ul>
<%@ include file="/html/include/footer.html"%>
</body>
</html>
