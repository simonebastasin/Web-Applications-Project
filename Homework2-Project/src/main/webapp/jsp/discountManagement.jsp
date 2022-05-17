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
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.lang.*" %>
<%--@elvariable id="discountList" type="it.unipd.dei.wa2122.wadteam.resources.Discount"--%>
<%--@elvariable id="productList" type="java.util.List<it.unipd.dei.wa2122.wadteam.resources.Product>"--%>



<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head-management.jsp"/>

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

    <%
        LocalDateTime now = LocalDateTime.now();
        String day = "";
        String month = "";
        if((LocalDateTime.now()).getDayOfMonth()<10) {
            day = "0" + (LocalDateTime.now()).getDayOfMonth();
        } else {
            day = ""+(LocalDateTime.now()).getDayOfMonth();
        }

        if((LocalDateTime.now()).getMonthValue()<10) {
            month = "0" + (LocalDateTime.now()).getMonthValue();
        } else {
            month = ""+(LocalDateTime.now()).getMonthValue();
        }
    %>

    <div id="liveAlertPlaceholder"></div>
    <div>
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addDiscountModal">
            <i class="fa-solid fa-circle-plus"></i>
            Add new discount
        </button>
    </div><br>

    <div class="card">
        <table id="discountTable" class="table table-hover table-hide-lg-col-5 table-hide-sm-col-1 table-hide-sm-col-3" >
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Percentage</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Product List</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="discountListProduct" items="${discountListProductList}">
                    <tr id="${discountListProduct.discount.id}">
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
                            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#addDiscountModal" data-bs-whatever="${discountListProduct.discount.id}">
                                <i class="fa-solid fa-pen-to-square text-primary"></i>
                            </button>
                        </td>
                        <td>
                            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteDiscountModal" data-bs-whatever="${discountListProduct.discount.id}">
                                <i class="fa-solid fa-trash-can text-danger"></i>
                            </button>
                            <!--<a href="<c:url value="/management/discountManagement/deleteDiscount/${discountListProduct.discount.id}"/>">
                                <i class="fa-solid fa-trash-can text-danger"></i>
                            </a>-->
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>





    <div class="modal fade" id="addDiscountModal" tabindex="-1" aria-labelledby="addDiscountModal" aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false" >
        <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg modal-fullscreen-md-down">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addDiscountModalTitle">Add Discount</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div id="formAlertPlaceholder"></div>
                <div class="modal-body">
                    <form id="addDiscountForm" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label for="percentage" class="col-form-label">Percentage:</label>
                            <div class="input-group">
                                <span class="input-group-text">%</span>
                                <input type="number" class="form-control" min="1" step="1" max="100" placeholder="10" id="percentage" name="percentage" required/>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="startDate">Start Date</label>
                            <input type="date" class="form-control" id="startDate" required name="startDate"
                                   value="<%=(LocalDateTime.now()).getYear()%>-<%= month %>-<%= day %>"
                                   min="<%=(LocalDateTime.now()).getYear()%>-<%= month %>-<%= day %>"
                                   max="<%=(LocalDateTime.now()).getYear() + 1%>-<%= month %>-<%= day %>">

                        </div>
                        <div class="mb-3">
                            <label for="endDate" class="col-2 col-form-label">End Date</label>
                            <input type="date" class="form-control" id="endDate" required name="endDate"
                                   value="<%=(LocalDateTime.now()).getYear()%>-<%= month %>-<%= day %>"
                                   min="<%=(LocalDateTime.now()).getYear()%>-<%= month %>-<%= day %>"
                                   max="<%=(LocalDateTime.now()).getYear() + 1%>-<%= month %>-<%= day %>">
                        </div>

                        <p>Select the products to be discounted</p>
                        <c:forEach var="prod" items="${productList}">
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="checkbox" role="switch" value="${prod.alias}" id="selectProduct-${prod.alias}" name="productList" >
                                <label class="form-check-label" for="selectProduct-${prod.alias}">
                                        ${prod.alias}&nbsp
                                    <a href="<c:url value="/productDetail/${prod.alias}"/>">${prod.name}</a>&nbsp
                                        ${prod.brand}&nbsp
                                        ${prod.salePrice}&nbsp
                                        ${prod.quantity}&nbsp
                                        ${prod.category.name}&nbsp
                                        ${prod.evidence}<br>
                                </label>
                            </div>
                        </c:forEach>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" id="addDiscountButton" form="addDiscountForm">Add discount</button>
                </div>
            </div>
        </div>
    </div>

</div>
<c:import url="/jsp/include/footer-management.jsp"/>
<script type="text/javascript"  src="<c:url value="/js/discount-management.js"/>"></script>

</body>
</html>