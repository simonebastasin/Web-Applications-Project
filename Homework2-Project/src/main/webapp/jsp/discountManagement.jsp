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
    <c:import url="/jsp/include/head.jsp"/>

    <title>Discount List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Discount List</li>
  </ol>
</nav>

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

</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>