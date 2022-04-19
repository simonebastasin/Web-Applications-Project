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
<html>
<head>
    <title>Invoice List</title>
</head>
<body>
    <c:import url="/jsp/include/header.jsp"/>
    <h1> Invoice List </h1>
    <hr>

        <c:forEach var="invoice" items="${onlineInvoiceList}">
            <li><b>Invoice ID: ${invoice.id}</b></li>
            <li>Date: ${invoice.oiDate.getHumanDate()}</li>
            <li>Price: ${invoice.totalPrice}</li>

            <li><a href="<c:url value="/invoice/detail/${invoice.id}"/>">Detail Invoice</a></li>

        </c:forEach>


<%@ include file="/html/include/footer.html"%>
</body>
</html>