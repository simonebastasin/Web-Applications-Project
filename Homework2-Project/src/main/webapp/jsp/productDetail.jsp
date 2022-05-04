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

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Product: ${product.name} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Product: ${product.name}</li>
  </ol>
</nav>

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
    <c:choose>
        <c:when test="${not empty product.pictures}">
            <li>Pictures:
                <c:forEach var="picture" items="${product.pictures}">
                    <img src="<c:url value="/media/view/${picture}"/>" alt="${product.alias}" width="400px"/>
                </c:forEach>
            </li>
        </c:when>
    </c:choose>
</ul>

<c:choose>
    <c:when test="${product.quantity <= 0}">
        <span style="color: red;font-weight: bold">The product is no longer available.</span>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${not empty user && empty user.role}">
                <hr>

                <form method="POST" action="<c:url value="/buy/product/${product.alias}"/>">
                    <label for="quantity">Selected quantity</label>
                    <input type="number" name ="quantity" max="${product.quantity}" min="1" id="quantity" required> <br>

                    <input type ="submit" value = "Buy now">
                </form>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${not empty user && not empty user.role}">
                        <span style="color: red;font-weight: bold">Employees are not authorized to make purchases.</span>
                    </c:when>
                    <c:otherwise>
                        Please, <a href="<c:url value="/session/login"/>">login</a> to buy the product
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
