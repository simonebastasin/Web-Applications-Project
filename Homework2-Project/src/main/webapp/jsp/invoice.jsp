<%--
  Created by IntelliJ IDEA.
  User: Gianpietro
  Date: 14/04/2022
  Time: 09:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="onlineInvoiceList" type="List<it.unipd.dei.wa2122.wadteam.resources.OnlineInvoice>"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Your Invoice List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
    <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
        <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
            <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
            <li class="breadcrumb-item active" aria-current="page">Invoice List</li>
        </ol>
    </nav>

    <c:forEach var="invoice" items="${onlineInvoiceList}">
        <div class="mx-auto w-lg-50">
            <div class="card mt-3 mb-3">
                <div class="card-header text-end">
                    ID INVOICE # ${invoice.id} <br>
                </div>
                <div class="card-body">
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item text-start">
                            <div class="row align-items-start">
                                <div class="col">
                                    Date:
                                </div>
                                <div class="col">
                                    ${invoice.oiDate.humanDateTimeless}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item text-start">
                            <div class="row align-items-start">
                                <div class="col">
                                    Price:
                                </div>
                                <div class="col">
                                    ${String.format("%.2f",invoice.totalPrice)}€
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="card-footer text-start">
                    <a href="<c:url value="/invoice/detail/${invoice.id}"/>" class="btn btn-primary">Detail Invoice</a>
                </div>
            </div>
        </div>
        <br>
    </c:forEach>
</div>

<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>