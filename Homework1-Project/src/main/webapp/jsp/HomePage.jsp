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

    <ul>
        <c:forEach var="item" items="${productCategoryList}">
            <li><b><a href="<c:url value="/categoryDetail/${item.name}"/>">${item.name}</a></b></li>

            <%! boolean empty = true;  %>

            <ul>
                <c:forEach var="prod" items="${productList}">
                    <c:if test="${prod.category.name.equals(item.name)}">

                        <% empty = false; %>

                        <li>Product name: <a href="<c:url value="/productDetail/${prod.alias}"/>">${prod.name}</a>  - price: ${prod.sale} - quantity: ${prod.quantity}<br>
                            <c:forEach var="picture" items="${prod.pictures}">
                                <img src="<c:url value="/viewmedia/${picture.id}"/>" alt="${picture.filename}" width="100px"/>
                            </c:forEach>
                        </li>
                    </c:if>
                </c:forEach>

                <% if (empty) {

                }%>

            </ul>
        </c:forEach>
    </ul>

</body>
</html>
