<%--
  Created by IntelliJ IDEA.
  User: gioel
  Date: 04/04/2022
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="product" type="Product"--%>
<%--@elvariable id="user" type="it.unipd.dei.wa2122.wadteam.resources.UserCredential"--%>

<html>
<head>
    <title>Electromechanics shop</title>
</head>

<body>

    <c:import url="/jsp/include/header.jsp"/>
    <h1>Product: ${product.name}</h1>

    <ul>
        <li>Brand: ${product.brand}</li>
        <li>Description: ${product.description}</li>
        <li>Quantity: ${product.quantity}</li>
        <c:choose>
            <c:when test="${not empty product.discount}">
                <li>Price: <span  style="text-decoration: line-through;">${product.salePrice}€</span> <span style="color: red;">${product.discountSale}€</span></li>
                <li>Discount: ${product.discount.percentage}% (until ${product.discount.endDate.humanDate})</li>
            </c:when>
            <c:otherwise>
                <li>Price: ${product.salePrice}€</li>
            </c:otherwise>
        </c:choose>
    </ul>

<hr />

<c:choose>
    <c:when test="${not empty user && empty user.role}">
        <form method="POST" action="<c:url value="/buy/product/${product.alias}"/>">
            <label for="quantity">Selected quantity</label>
            <input type="number" name ="quantity" max="${product.quantity}" min="1" id="quantity" required> <br>

            <input type ="submit" value = "Buy now">
        </form>
    </c:when>
</c:choose>

    <%@ include file="/html/include/footer.html"%>
</body>
</html>
