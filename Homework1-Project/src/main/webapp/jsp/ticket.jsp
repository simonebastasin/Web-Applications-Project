<%--
  Created by IntelliJ IDEA.
  User: GK10
  Date: 02/04/2022
  Time: 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="assistantTicket" type="it.unipd.dei.wa2122.wadteam.resources.AssistanceTicket"--%>

<html>
<head>
    <title>ticket</title>
</head>
<body>
    <p>${assistantTicket.id}</p>
    <p>${assistantTicket.description}</p>
    <p>${assistantTicket.idCustomer}</p>
    <p>${assistantTicket.productAlias}</p>

    <c:forEach var="item" items="${assistantTicket.ticketStatusList}">
        <p>${item.status}</p>
        <p>${item.description}</p>
        <p>${item.tsDate}</p>
    </c:forEach>

</body>
</html>
