<%--
  Created by IntelliJ IDEA.
  User: pasto
  Date: 12/04/2022
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="productList" type="java.util.List<Product>"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="Electromechanics Shop">
    <meta name="author" content="WAD Team">

    <title>Search Results | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Search Results</h1>

<c:choose>
    <c:when test="${productList.size() > 0}">
        <c:forEach var="prod" items="${productList}">
            <li>Product name: <a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a>  - Brand: ${prod.brand} - Quantity: ${prod.quantity} - Price: ${prod.salePrice}<br>
                <c:forEach var="picture" items="${prod.pictures}">
                    <img src="<c:url value="/viewmedia/${picture.id}"/>" alt="${picture.filename}" width="100px"/>
                </c:forEach>
            </li>
        </c:forEach>
    </c:when>
    <c:otherwise>
        There are no products that seems to match your search terms...
    </c:otherwise>
</c:choose>

<%@ include file="/html/include/footer.html"%>
</body>
</html>
