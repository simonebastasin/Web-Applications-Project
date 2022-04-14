<%--
  Created by IntelliJ IDEA.
  User: Gianpietro
  Date: 14/04/2022
  Time: 09:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="onlineInvoiceList" type="List<it.unipd.dei.wa2122.wadteam.resources.OnlineInvoice>"--%>
<html>
<head>
    <title>Invoice List</title>
</head>
<body>
    <c:import url="/jsp/include/header.jsp"/>

        <c:forEach var="invoice" items="${onlineInvoiceList}">
            <il><b>Invoice ID: ${invoice.id}</b></il>
            <li>Transaction ID: ${invoice.transactionId}</li>
            <li>Payment Type: ${invoice.paymentType}</li>
            <li>Date: ${invoice.oiDate}</li>
            <li>Price: ${invoice.totalPrice}</li>

            <h2>Product list:</h2>

            <c:forEach var="product" items="${invoice.idOrder}">

                <il><b>Product ID: ID: ${product.alias}</b></il>

            </c:forEach>

        </c:forEach>


<%@ include file="/html/include/footer.html"%>
</body>
</html>