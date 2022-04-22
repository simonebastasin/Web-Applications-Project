<%--
  Created by IntelliJ IDEA.
  User: pasto
  Date: 20/04/2022
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="categories" type="java.util.List<ProductCategory>"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="Electromechanics Shop">
    <meta name="author" content="WAD Team">

    <title>Category List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Category List</h1>

<c:forEach var="cat" items="${categories}">
    <li>
        <a href="<c:url value="/products/category/${cat.name}"/>">${cat.name}</a>
    </li>
</c:forEach>

<%@ include file="/html/include/footer.html"%>
</body>
</html>
