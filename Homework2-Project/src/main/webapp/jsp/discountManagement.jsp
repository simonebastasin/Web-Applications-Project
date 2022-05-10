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
<%--@elvariable id="productList" type="java.util.List<it.unipd.dei.wa2122.wadteam.resources.Product>"--%>



<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Discount List | Electromechanics Shop</title>



    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
</head>





<script>
    $(document).ready(function () {
        $('.it-date-datepicker').datepicker({
            inputFormat: ['dd/MM/yyyy'],
            outputFormat: 'dd/MM/yyyy',
        })
    })
    </script>


<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Discount List</li>
  </ol>
</nav>
    <div id="liveAlertPlaceholder"></div>

    <div>
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addDiscountModal">
            Add new Discount
        </button>
    </div>
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





    <div class="modal fade" id="addDiscountModal" tabindex="-1" aria-labelledby="addDiscountModal" aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false" >
        <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addDiscountModalTitle">Add Product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div id="formAlertPlaceholder"></div>
                <div class="modal-body">
                    <form id="addDiscountForm">
                        <div class="mb-3">
                            <label for="percentage" class="col-form-label">Percentage:</label>
                            <div class="input-group">
                                <span class="input-group-text">%</span>
                                <input type="number" class="form-control" min="1" step="1" max="100" placeholder="10" id="percentage" name="percentage" required/>
                            </div>
                        </div>





                        <label for="startDate" class="col-2 col-form-label">Start Date</label>
                        <div class="col-5">
                            <div class="input-group date" id="startDatepicker">
                                <input type="text" class="form-control" id="startDate" required/>
                                <span class="input-group-append">
                                    <span class="input-group-text bg-light d-block">
                                        <i class="fa fa-calendar"></i>
                                    </span>
                                </span>
                            </div>
                        </div>

                        <label for="endDate" class="col-2 col-form-label">End Date</label>
                        <div class="col-5">
                            <div class="input-group date" id="endDatepicker">
                                <input type="text" class="form-control" id="endDate" required />
                                <span class="input-group-append">
                                    <span class="input-group-text bg-light d-block">
                                        <i class="fa fa-calendar"></i>
                                    </span>
                                </span>
                            </div>
                        </div>



                        <p>Select the products to be discounted</p>
                        <ul>
                        <c:forEach var="prod" items="${productList}">


                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="${prod.alias}" id="flexCheckDefault">
                                <label class="form-check-label" for="flexCheckDefault">
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
                        </ul>


                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="addProductButton">Add product</button>
                </div>
            </div>
        </div>
    </div>

</div>
<c:import url="/jsp/include/footer.jsp"/>
<script type="text/javascript"  src="<c:url value="/js/discount-management.js"/>"></script>

</body>
</html>