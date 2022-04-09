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

<html>
<head>
    <title>ticket</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>
<ul>
    <c:forEach var="assistanceTicket" items="${assistanceTicketList}">
        <li><b>Ticket ID: ${assistanceTicket.id}</b>
        <ul>
        <li>${assistanceTicket.description}</li>
        <li>${assistanceTicket.idCustomer}</li>
        <li>${assistanceTicket.productAlias}</li>

        <c:forEach var="item" items="${assistanceTicket.ticketStatusList}">
            <li><b> Ticket Status : ${item.status}</b></li>
            <ul><li><i>${item.description}</i></li>
            <li>${item.tsDate}</li></ul>
        </c:forEach>
        </ul>
        </li>
    </c:forEach>
</ul>
</body>
</html>
