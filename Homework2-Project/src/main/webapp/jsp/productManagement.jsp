<%--
  Created by IntelliJ IDEA.
  User: matteolando
  Date: 07/04/2022
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Product List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Product List</li>
  </ol>
</nav>

<div>
    <a href="<c:url value="/management/productManagement/createProduct"/>">Add new product</a>
</div><br>

<table>
    <tr>
        <th>Name</th>
        <th>Alias</th>
        <th>Brand</th>
        <th>Category</th>
        <th>Sale Price</th>
        <th>Quantity</th>
        <th>Evidence</th>
        <th>Media</th>
    </tr>

    <c:forEach var="prod" items="${productList}">

        <tr>
            <td><a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a></td>
            <td>${prod.alias}</td>
            <td>${prod.brand}</td>
            <td>${prod.category.name}</td>
            <td>${prod.salePrice}</td>
            <td>${prod.quantity}</td>
            <td>${prod.evidence}</td>
            <td>
                <c:forEach var="picture" items="${prod.pictures}">
                    ${picture} 
                </c:forEach>
            </td>
            <td><a href="<c:url value="/management/productManagement/editProduct/${prod.alias}"/>">Edit</a></td>
            <td><a href="<c:url value="/management/productManagement/deleteProduct/${prod.alias}"/>">Delete</a></td>
        </tr>

    </c:forEach>
</table>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
