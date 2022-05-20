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
    <c:import url="/jsp/include/head.jsp"/>

    <title>Add new Ticket | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav class="custom-breadcrumb-divider" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Add new Ticket</li>
  </ol>
</nav>

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


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
