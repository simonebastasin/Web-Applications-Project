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
<nav class="custom-breadcrumb-divider" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Your Ticket List</li>
  </ol>
</nav>


    <c:forEach var="assistanceTicket" items="${assistanceTicketList}">
    <div class="card w-lg-50 mx-auto bg-white rounded">
        <h3 class="card-header">Ticket ID: ${assistanceTicket.id}</h3>
        <div class="card-body">
            <ul class="list-group list-group-flush">
                <li class="list-group-item text-start">
                    <div class="row align-items-start">
                        <div class="col">Description: </div>
                        <div class="col">${assistanceTicket.description}</div>
                    </div>
                </li>
                <li class="list-group-item text-start">
                    <div class="row align-items-start">
                        <div class="col">Product: </div>
                        <div class="col"><a href="<c:url value="/products/details/${assistanceTicket.productAlias}"/>">Product ${assistanceTicket.productAlias}</a></div>
                    </div>
                </li>

                <c:forEach var="item" items="${assistanceTicket.ticketStatusList}">
                <li class="list-group-item text-start">
                        <div class="row align-items-start">
                            <div class="col"><b> Ticket Status :</b></div>
                            <div class="col"><b> <span class = "ticket-${item.status}">${item.status}</span></b></div>
                        </div>
                        <div class="row align-items-start">
                            <div class="col"></div>
                            <div class="col"><i>${item.description}</i></div>
                        </div>
                        <div class="row align-items-start">
                            <div class="col"></div>
                            <div class="col">${item.tsDate.humanDate}</div>
                        </div>
                </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <br>
    </c:forEach>



</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
