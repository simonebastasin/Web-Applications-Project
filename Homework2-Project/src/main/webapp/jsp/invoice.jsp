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
    <li class="breadcrumb-item active" aria-current="page">Your Invoice List</li>
  </ol>
</nav>

<c:forEach var="invoice" items="${onlineInvoiceList}">
    <h2>Invoice ID: ${invoice.id}</h2>
    <li>Date: ${invoice.oiDate.humanDateTimeless}</li>
    <li>Price: ${invoice.totalPrice}€</li>
    <li><a href="<c:url value="/invoice/detail/${invoice.id}"/>">Detail Invoice</a></li>
</c:forEach>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>