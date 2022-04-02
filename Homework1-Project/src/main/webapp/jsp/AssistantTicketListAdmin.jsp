<%--
  Created by IntelliJ IDEA.
  User: Gianpietro
  Date: 02/04/2022
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Assistant Ticket</title>
    <c:forEach var="item" items="${assistantTicketList}">
        <li>${item.id}</li>
        <li>${item.description}</li>
        <li>${item.idCustomer}</li>
        <li>${item.productAlias}</li>
    </c:forEach>

</head>
<body>

</body>
</html>
