<%--
  Created by IntelliJ IDEA.
  User: matteolando
  Date: 07/04/2022
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Product List</title>
</head>

<body>

<c:import url="/jsp/include/header.jsp"/>
<h1>Electromechanics shop</h1>

<table style="width:80%">

    <tr>

        <th>Name</th>
        <th>Brand</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Evidence</th>
        <th>Details</th>
    </tr>


    <c:forEach var="prod" items="${productList}">

        <tr>
            <td valign="middle"><a href="<c:url value="/productDetail/${prod.alias}"/>">${prod.name}</a></td>
            <td valign="middle">${prod.brand}</td>
            <td valign="middle">${prod.sale}</td>
            <td valign="middle">${prod.quantity}</td>
            <td valign="middle">${prod.evidence}</td>
            <td valign="middle"><button type="button">i</button></td>
        </tr>

    </c:forEach>

</table>

</body>
</html>
