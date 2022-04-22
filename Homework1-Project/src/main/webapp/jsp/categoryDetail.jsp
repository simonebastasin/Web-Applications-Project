<%--
  Created by IntelliJ IDEA.
  User: gioel
  Date: 05/04/2022
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="productCategoryList" type="java.util.List<ProductCategory>"--%>
<%--@elvariable id="productList" type="java.util.List<Product>"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="Electromechanics Shop">
    <meta name="author" content="WAD Team">

    <title>Category: ${category.name} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>

<c:forEach var="category" items="${productCategoryList}">

    <h1>Category: ${category.name}</h1>

    ${category.description}

    <c:choose>
        <c:when test="${productList.size() > 0}">
            <c:forEach var="prod" items="${productList}">
                <c:choose>
                    <c:when test="${not empty prod.discount}">
                        <li>Product name: <a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a>  - Brand: ${prod.brand} - Quantity: ${prod.quantity} - Price: <span  style="text-decoration: line-through;">${prod.salePrice}€</span> <span style="color: red;">${prod.discountSale}€</span><br>
                            <c:forEach var="picture" items="${prod.pictures}">
                                <img src="<c:url value="/media/view/${picture}"/>" alt="${prod.alias}" width="250px"/>
                            </c:forEach>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>Product name: <a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a>  - Brand: ${prod.brand} - Quantity: ${prod.quantity} - Price: ${prod.salePrice}€<br>
                            <c:forEach var="picture" items="${prod.pictures}">
                                <img src="<c:url value="/media/view/${picture}"/>" alt="${prod.alias}" width="250px"/>
                            </c:forEach>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:when>
        <c:otherwise>
            There are no more products for this category! =(
        </c:otherwise>
    </c:choose>

</c:forEach>

<%@ include file="/html/include/footer.html"%>
</body>
</html>
