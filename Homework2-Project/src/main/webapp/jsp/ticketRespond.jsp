<%--
  Created by IntelliJ IDEA.
  User: GK10
  Date: 09/04/2022
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
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
<nav class="custom-breadcrumb-divider" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Ticket List</li>
  </ol>
</nav>

    <div id="liveAlertPlaceholder"></div>

    <c:forEach var="assistanceTicket" items="${assistanceTicketList}">
    <div class="card w-lg-50 mx-auto bg-white rounded" data-id="${assistanceTicket.id}">
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
                            <div class="col">Customer: </div>
                            <div class="col">${assistanceTicket.idCustomer}</div>
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

                <c:choose>
                    <c:when test="${empty assistanceTicket.ticketStatusList}" >
                    <div class="p-3">
                        <button type="button" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#respondTicketModal" data-id="${assistanceTicket.id}">
                            Respond
                        </button>
                    </div>
                    </c:when>
                    <c:when test="${not (assistanceTicket.ticketStatusList[fn:length(assistanceTicket.ticketStatusList)-1].status eq TicketStatusEnum.CLOSED) }">
                    <div class="p-3">
                        <button type="button" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#respondTicketModal" data-id="${assistanceTicket.id}">
                            Respond
                        </button>
                    </div>
                    </c:when>

                    <c:otherwise>
                        <span class="list-group-item">The ticket is closed</span>
                    </c:otherwise>
                </c:choose>

            </div>
    </div>
    <br>
    </c:forEach>

</div>

<div class="modal fade" id="respondTicketModal" tabindex="-1" aria-labelledby="respondTicketModal" aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false" >
    <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg modal-fullscreen-md-down">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="respondTicketModalTitle">Respond Ticket</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div id="formAlertPlaceholder" class="sticky-top"></div>
            <div class="modal-body">
                <form id="respondTicketForm" class="needs-validation" novalidate>
                    <div class="mb-3"> Ticket Status:
                        <div class="form-check form-check-inline" >
                            <input class="form-check-input" type="radio" id="${TicketStatusEnum.OPEN}"
                                   name="status" value="${TicketStatusEnum.OPEN}" required>
                            <label class="form-check-label ticket-OPEN" for="${TicketStatusEnum.OPEN}">Open</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="${TicketStatusEnum.PROCESSING}"
                                   name="status" value="${TicketStatusEnum.PROCESSING}" required>
                            <label class="form-check-label ticket-PROCESSING" for="${TicketStatusEnum.PROCESSING}">Processing</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="${TicketStatusEnum.CLOSED}"
                                   name="status" value="${TicketStatusEnum.CLOSED}" required>
                            <label class="form-check-label ticket-CLOSED" for="${TicketStatusEnum.CLOSED}">Closed</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="${TicketStatusEnum.RETURN}"
                                   name="status" value="${TicketStatusEnum.RETURN}" required>
                            <label class="form-check-label ticket-RETURN" for="${TicketStatusEnum.RETURN}">Return</label>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="description" class="col-form-label">Description:</label>
                        <textarea class="form-control" id="description" name="description" required></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer p-3">
                <button type="submit" class="btn btn-primary" form="respondTicketForm">Respond</button>
            </div>
        </div>
    </div>
</div>

<c:import url="/jsp/include/footer.jsp"/>
<script src="<c:url value="/js/respond-ticket.js"/>"></script>
</body>
</html>
