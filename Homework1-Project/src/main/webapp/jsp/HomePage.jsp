<%--
  Created by IntelliJ IDEA.
  User: matte
  Date: 01/04/2022
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="productList" type="java.util.List<Product>"--%>
<%--@elvariable id="productCategoryList" type="java.util.List<ProductCategory>"--%>

<html>
<head>
    <title>Electromechanics Shop</title>
</head>

<body>

<c:import url="/jsp/include/header.jsp"/>
<h1>Electromechanics shop</h1>

<hr>

<h3>Featured products</h3>
<ul>
    <c:forEach var="prod" items="${productList}">
        <c:if test="${prod.evidence == true}">
            <c:choose>
                <c:when test="${not empty prod.discount}">
                    <li>Product name: <a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a>  - Brand: ${prod.brand} - Quantity: ${prod.quantity} - Price: <span  style="text-decoration: line-through;">${prod.salePrice}€</span> <span style="color: red;">${prod.discountSale}€</span><br>
                        <c:forEach var="picture" items="${prod.pictures}">
                            <img src="<c:url value="/viewmedia/${picture.id}"/>" alt="${picture.filename}" width="100px"/>
                        </c:forEach>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>Product name: <a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a>  - Brand: ${prod.brand} - Quantity: ${prod.quantity} - Price: ${prod.salePrice}€<br>
                        <c:forEach var="picture" items="${prod.pictures}">
                            <img src="<c:url value="/viewmedia/${picture.id}"/>" alt="${picture.filename}" width="100px"/>
                        </c:forEach>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:if>
    </c:forEach>
</ul>

<hr>

<h3>Available products</h3>
<ul>
    <c:forEach var="item" items="${productCategoryList}">
        <li><b><a href="<c:url value="products/category/${item.name}"/>">${item.name}</a></b></li>

        <c:set value="${true}" var="empty_cat"/>

        <ul>
            <c:forEach var="prod" items="${productList}">
                <c:if test="${prod.category.name.equals(item.name)}">

                    <c:set value="${false}" var="empty_cat"/>

                    <c:choose>
                        <c:when test="${not empty prod.discount}">
                            <li>Product name: <a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a>  - Brand: ${prod.brand} - Quantity: ${prod.quantity} - Price: <span  style="text-decoration: line-through;">${prod.salePrice}€</span> <span style="color: red;">${prod.discountSale}€</span><br>
                                <c:forEach var="picture" items="${prod.pictures}">
                                    <img src="<c:url value="/viewmedia/${picture.id}"/>" alt="${picture.filename}" width="100px"/>
                                </c:forEach>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li>Product name: <a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a>  - Brand: ${prod.brand} - Quantity: ${prod.quantity} - Price${prod.salePrice}€<br>
                                <c:forEach var="picture" items="${prod.pictures}">
                                    <img src="<c:url value="/viewmedia/${picture.id}"/>" alt="${picture.filename}" width="100px"/>
                                </c:forEach>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </c:forEach>

            <c:if test="${empty_cat}">
                <li>There are no more products for this category! =(</li>
            </c:if>
        </ul>
    </c:forEach>
</ul>

<%@ include file="/html/include/footer.html"%>

</body>
</html>
