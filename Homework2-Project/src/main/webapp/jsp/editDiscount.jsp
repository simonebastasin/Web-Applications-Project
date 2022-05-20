<%--
  Created by IntelliJ IDEA.
  User: matteolando
  Date: 19/04/2022
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.lang.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Edit Discount | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav class="custom-breadcrumb-divider" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Edit Discount</li>
  </ol>
</nav>

<form method="POST" action="">

    <label for="idDiscount">id:</label>
    <input type="text" id="idDiscount" name="id" value="${discountListProduct.discount.id}" disabled>
    <br>
    <label for="percentage"  >percentage:</label>
    <input type="number" id="percentage" name="percentage" min="1" max="100" value="${discountListProduct.discount.percentage}">
    <br>
    <label for="startDate">Enter start Day:</label>
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
    <br>
    <input type="date" id="startDate" name="startDate"
           value="${discountListProduct.discount.startDate.getInputDateFormat()}"
           value="<%=(LocalDateTime.now()).getYear()%>-<%= month %>-<%= day %>"
           min="${discountListProduct.discount.startDate.getInputDateFormat()}"
           max="<%=(LocalDateTime.now()).getYear() + 1%>-<%= month %>-<%= day %>">
    <br>

    <label for="endDate">Enter end Day:</label>
    <input type="date" id="endDate" name="endDate"
           value="${discountListProduct.discount.endDate.getInputDateFormat()}"
           min="${discountListProduct.discount.endDate.getInputDateFormat()}"
           max="<%=(LocalDateTime.now()).getYear() + 1%>-<%= month %>-<%= day %>">

    <br>
    <p>Select the products to be discounted</p>
    <c:forEach var="prod" items="${productList}">
        <c:choose>
            <c:when test =  "${discountListProduct.cointainsProduct(prod.alias)}">
                <input type="checkbox" name="productList" value="${prod.alias}" checked id="selectProduct-${prod.alias}">
                <label for="selectProduct-${prod.alias}">${prod.alias}
                <a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a>&nbsp
                ${prod.brand}&nbsp
                ${prod.salePrice}&nbsp
                ${prod.quantity}&nbsp
                ${prod.category.name}&nbsp
                        ${prod.evidence}</label><br>
            </c:when>
            <c:otherwise>
                <input type="checkbox" name="productList" value="${prod.alias}" id="selectProduct-${prod.alias}">
                <label for="selectProduct-${prod.alias}">${prod.alias}&nbsp
                <a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a>&nbsp
                ${prod.brand}&nbsp
                ${prod.salePrice}&nbsp
                ${prod.quantity}&nbsp
                ${prod.category.name}&nbsp
                ${prod.evidence}</label><br>
            </c:otherwise>
        </c:choose>

    </c:forEach>
    <input type="submit" value="Submit">
</form>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>