<%--
  Created by IntelliJ IDEA.
  User: matteolando
  Date: 12/04/2022
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.lang.*" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>

<h2>Create new discount</h2>

<%
    LocalDateTime now = LocalDateTime.now();
    String day = "";
    String month = "";
    if((LocalDateTime.now()).getDayOfMonth()<10) {
        day = "0" + (LocalDateTime.now()).getDayOfMonth();
    } else {
        day = ""+(LocalDateTime.now()).getDayOfMonth();
    }

    if((LocalDateTime.now()).getMonthValue()<10) {
        month = "0" + (LocalDateTime.now()).getMonthValue();
    } else {
        month = ""+(LocalDateTime.now()).getMonthValue();
    }


%>

<form method="POST" action="">


    <label for="percentage" >percentage:</label>
    <input type="number" id="percentage" name="percentage" min="1" max="100" value="1">


    <label for="start">Enter start Day:</label>
    <input type="date" id="start" name="start"
           value="<%=(LocalDateTime.now()).getYear()%>-<%= month %>-<%= day %>"
           min="<%=(LocalDateTime.now()).getYear()%>-<%= month %>-<%= day %>"
           max="<%=(LocalDateTime.now()).getYear() + 1%>-<%= month %>-<%= day %>">
    <br>



    <label for="end">Enter end Day:</label>
    <input type="date" id="end" name="end"
           value="<%=(LocalDateTime.now()).getYear()%>-<%= month %>-<%= day %>"
           min="<%=(LocalDateTime.now()).getYear()%>-<%= month %>-<%= day %>"
           max="<%=(LocalDateTime.now()).getYear() + 1%>-<%= month %>-<%= day %>">


    <br>



    <p>Select the products to be discounted</p>
    <c:forEach var="prod" items="${productList}">
        <input type="checkbox" name="productList" value="${prod.alias}">
        ${prod.alias}&nbsp
        <a href="<c:url value="/productDetail/${prod.alias}"/>">${prod.name}</a>&nbsp
        ${prod.brand}&nbsp
        ${prod.salePrice}&nbsp
        ${prod.quantity}&nbsp
        ${prod.category.name}&nbsp
        ${prod.evidence}<br>

    </c:forEach>
    <input type="submit" value="Submit">

</form>



<%@ include file="/html/include/footer.html"%>
</body>
</html>
