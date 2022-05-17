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
    <c:import url="/jsp/include/head.jsp"/>

    <title>Category List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Category List</li>
  </ol>
</nav>

<c:forEach var="cat" items="${categories}">

    <div class="row w-50 mx-auto justify-content-center border mt-3 mb-3 rounded bg-light">
        <div class="d-inline p-3">
            <h3 class="title d-inline">${cat.name}</h3>
            <div class="text-end">
                <span><a href="<c:url value="/products/category/${cat.name}"/>" class="d-inline p-3 fw-bold">Shop now</a></span>
            </div>
        </div>
    </div>

</c:forEach>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>