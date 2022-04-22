<%--
  Created by IntelliJ IDEA.
  User: Gianpietro
  Date: 14/04/2022
  Time: 08:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="Electromechanics Shop">
    <meta name="author" content="WAD Team">

    <title>Add new Ticket | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Add new Ticket</h1>

<form method="POST" action="">
    <label for="transactionId">Transaction ID:</label><br>
    <input type="text" id="transactionId" name="transactionId"/><br>
    <label for="paymentType">Payment Type:</label><br>
    <input type="text" id="paymentType" name="paymentType"/><br>
    <label for="totalPrice">total Price:</label><br>
    <input type="text" id="totalPrice" name="totalPrice"/><br>

    <input type="hidden" value="${pageContext.request.getParameter('idOrder')}" name="idOrder">
    <input type ="submit" value = "Submit">
</form>

<%@ include file="/html/include/footer.html"%>
</body>
</html>
