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

<html>
<head>
    <title>Electromechanics shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>

<c:forEach var="category" items="${productCategoryList}">
<h1>Category: ${category.name}</h1>

${category.description}
<hr />
<c:choose>
    <c:when test="${productList.size() > 0}">
        <c:forEach var="prod" items="${productList}">
            <li>Product name: <a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a>  - Brand: ${prod.brand} - Quantity: ${prod.quantity} - Price: ${prod.sale}<br>
                <c:forEach var="picture" items="${prod.pictures}">
                    <img src="<c:url value="/viewmedia/${picture.id}"/>" alt="${picture.filename}" width="100px"/>
                </c:forEach>
            </li>
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
