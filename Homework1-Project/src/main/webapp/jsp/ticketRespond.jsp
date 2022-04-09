<%--
  Created by IntelliJ IDEA.
  User: GK10
  Date: 09/04/2022
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="assistanceTicketList" type="List<it.unipd.dei.wa2122.wadteam.resources.AssistanceTicket>"--%>

<html>
<head>
    <title>Ticket Respond</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>
<ul>
    <c:forEach var="assistanceTicket" items="${assistanceTicketList}">
        <li>
                ${assistanceTicket.id}
            <ul>
                <li>${assistanceTicket.description}</li>
                <li>${assistanceTicket.idCustomer}</li>
                <li>${assistanceTicket.productAlias}</li>

                <c:forEach var="item" items="${assistanceTicket.ticketStatusList}">
                    <li>${item.status}</li>
                    <ul><li>${item.description}</li>
                        <li>${item.tsDate}</li></ul>
                </c:forEach>
            </ul>
            <a href="<c:url value="/ticket/respond/${assistanceTicket.id}"/>">Respond</a>
        </li>
    </c:forEach>
</ul>

</body>
</html>
