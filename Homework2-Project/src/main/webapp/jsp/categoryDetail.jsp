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
    <c:import url="/jsp/include/head.jsp"/>

    <title>Category: ${category.name} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">

<c:forEach var="category" items="${productCategoryList}">

    <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Category: ${category.name}</li>
  </ol>
</nav>

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


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
