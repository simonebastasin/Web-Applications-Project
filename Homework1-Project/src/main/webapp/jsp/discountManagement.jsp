<%--
  Created by IntelliJ IDEA.
  User: matteolando
  Date: 11/04/2022
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="discountList" type="it.unipd.dei.wa2122.wadteam.resources.Discount"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="Electromechanics Shop">
    <meta name="author" content="WAD Team">

    <title>Discount List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Discount List</h1>

<div>
    <a href="<c:url value="/management/discountManagement/createDiscount"/>">Add new discount</a>
</div><br>

<table>
    <tr>
        <th>Id</th>
        <th>Percentage</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Product List</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>


    <c:forEach var="discountListProduct" items="${discountListProductList}">
        <tr>
            <td>${discountListProduct.discount.id}</td>
            <td>${discountListProduct.discount.percentage}%</td>
            <td>${discountListProduct.discount.startDate.getHumanDateTimeless()}</td>
            <td>${discountListProduct.discount.endDate.getHumanDateTimeless()}</td>

            <td>
            <c:forEach var="prod" items="${discountListProduct.productList}">
                <a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}, </a>&nbsp
            </c:forEach>
            </td>

            <td>
                <a href="<c:url value="/management/discountManagement/editDiscount/${discountListProduct.discount.id}"/>">
                    Edit
                </a>
            </td>
            <td>
                <a href="<c:url value="/management/discountManagement/deleteDiscount/${discountListProduct.discount.id}"/>">
                    Delete
                </a>

            </td>
        </tr>
    </c:forEach>

</table>
<%@ include file="/html/include/footer.html"%>
</body>
</html>