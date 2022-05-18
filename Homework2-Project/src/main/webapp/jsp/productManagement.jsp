<%--
  Created by IntelliJ IDEA.
  User: matteolando
  Date: 07/04/2022
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="productList" type="java.util.List<it.unipd.dei.wa2122.wadteam.resources.Product>"--%>
<%--@elvariable id="mediaList" type="java.util.List<it.unipd.dei.wa2122.wadteam.resources.Media>"--%>
<%--@elvariable id="media" type="it.unipd.dei.wa2122.wadteam.resources.Media"--%>
<%--@elvariable id="productCategoryList" type="java.util.List<it.unipd.dei.wa2122.wadteam.resources.ProductCategory>"--%>


<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head-management.jsp"/>

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
    <div id="liveAlertPlaceholder"></div>
    <div id="liveAlertPlaceholderDelete"></div>

<div>
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addProductModal">
        <i class="fa-solid fa-circle-plus"></i>
        Add new product
    </button>
</div><br>

    <div class="card">
        <table id="productTable" class="table table-hover table-hide-lg-col-7 table-hide-lg-col-8 table-hide-md-col-4 table-hide-md-col-5 table-hide-md-col-6 table-hide-sm-col-2 table-hide-sm-col-3" >
            <thead>
                <tr>
                        <th>Name</th>
                        <th>Alias</th>
                        <th>Brand</th>
                        <th>Category</th>
                        <th>Sale Price</th>
                        <th>Quantity</th>
                        <th>Evidence</th>

                </tr>
            </thead>
            <tbody>
                <c:forEach var="prod" items="${productList}">

                    <tr id="${prod.alias}">
                        <td><a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a></td>
                        <td>${prod.alias}</td>
                        <td>${prod.brand}</td>
                        <td>${prod.category.name}</td>
                        <td>${prod.salePrice}</td>
                        <td >${prod.quantity}</td>
                        <td>${prod.evidence}</td>
                        <td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#addProductModal" data-bs-whatever="${prod.alias}">
                            <i class="fa-solid fa-pen-to-square text-primary"></i>
                        </button></td>
                        <td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteProductModal" data-bs-whatever="${prod.alias}">
                            <i class="fa-solid fa-trash-can text-danger"></i>
                        </button></td>
                        <%--<td><a class="btn btn-outline-danger" href="<c:url value="/management/productManagement/deleteProduct/${prod.alias}"/>">Delete</a></td>--%>
                    </tr>

                </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="modal fade" id="addProductModal" tabindex="-1" aria-labelledby="addProductModal" aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false" >
        <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg modal-fullscreen-md-down">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addProductModalTitle">Add Product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div id="formAlertPlaceholder"></div>
                <div class="modal-body">
                    <form id="addProductForm" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label for="alias" class="col-form-label">Alias:</label>
                            <input type="text" class="form-control" id="alias" name="alias" required>
                        </div>
                        <div class="mb-3">
                            <label for="name" class="col-form-label">Name:</label>
                            <input type="text" class="form-control" id="name" name="name" required/>
                        </div>
                        <div class="mb-3">
                            <label for="brand" class="col-form-label">Brand:</label>
                            <input type="text" class="form-control" id="brand" name="brand" required/>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="col-form-label">Description:</label>
                            <textarea class="form-control" id="description" name="description" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="purchase" class="col-form-label">Purchase price:</label>
                            <div class="input-group">
                                <span class="input-group-text">€</span>
                                <input type="number" class="form-control" min="0.01" step="0.01" max="2500" placeholder="10.00" id="purchase" name="purchase" required/>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="sale" class="col-form-label">Sale price:</label>
                            <div class="input-group">
                                <span class="input-group-text">€</span>
                                <input type="number" class="form-control" min="0.01" step="0.01" max="2500" placeholder="10.00" id="sale" name="sale" required/>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="quantity" class="col-form-label">Quantity:</label>
                            <input type="number" class="form-control" min="1" step="1" placeholder="1" id="quantity" name="quantity" required/>
                        </div>
                        <div class="mb-3">
                            <label for="category">Category:</label>
                            <div class="input-group">
                                <select class="form-control" name="category" id="category" required>
                                    <option value=""  hidden selected disabled>Choose</option>
                                    <c:forEach var="cat" items="${productCategoryList}">
                                        <option value="${cat.name}">
                                                ${cat.name}
                                        </option>
                                    </c:forEach>
                                </select>
                                <a class="btn btn-outline-secondary toggle" href="#example">+</a>


                            </div>


                            <div class="toggle-content" id="example" style="display: none;">

                                <div class="mb-3">
                                    <form id="addCategoryForm" class="mb-3 needs-validation" novalidate>

                                        <div class="mb-3">
                                            <label for="nameCategoryName" class="col-form-label">new category name:</label>
                                            <input type="text" class="form-control" id="nameCategoryName" name="name" required/>
                                        </div>

                                        <div class="mb-3">
                                            <label for="nameCategoryDescription" class="col-form-label">new category description:</label>
                                            <input type="text" class="form-control" id="nameCategoryDescription" name="description" required/>
                                        </div>

                                    </form>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-primary" id="addNewCategory" form="addCategoryForm">Add category</button>
                                    </div>
                                </div>
                            </div>


                        </div>


                        <div class="mb-3">
                            Evidence:
                            <div class="form-check form-check-inline" >
                                <input class="form-check-input" type="radio" id="yes"
                                       name="evidence" value="true" required>
                                <label class="form-check-label" for="yes">Yes</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" id="no"
                                       name="evidence" value="false" required>
                                <label class="form-check-label" for="no">No</label>
                            </div>
                        </div>
                        <div class="mb-3 image-check-box">
                            <ul>
                                <c:if test="${not empty media}">
                                    <li><input type="checkbox" id="media-${media.id}" name="pictures" value="${media.id}"/>
                                        <label for="media-${media.id}"><img src="<c:url value="/media/thumb/${media.id}" />" alt="${media.filename}" /></label>
                                    </li>
                                </c:if>
                                <c:forEach var="media" items="${mediaList}">
                                    <li><input type="checkbox" id="media-${media.id}" name="pictures" value="${media.id}"/>
                                        <label for="media-${media.id}"><img src="<c:url value="/media/thumb/${media.id}" />" alt="${media.filename}" /></label>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </form>
                    <form id="uploadImageForm" class="mb-3 needs-validation" novalidate>
                        <label for="formFile" class="form-label">Upload image</label>
                        <div class="input-group">
                            <input class="form-control" type="file" name="file" id="formFile" required accept="image/*">
                            <button type="submit" class="btn btn-outline-secondary">Upload</button>
                        </div>
                        <div class="progress mt-3 mb-3" id="uploadImageProgress" style="display: none;">
                            <div id="uploadImageProgressBar" class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%"></div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" id="addProductButton" form="addProductForm">Add product</button>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="deleteProductModal" tabindex="-1" aria-labelledby="deleteProductModal" aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false" >
        <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg modal-fullscreen-md-down">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteProductModalTitle">Set product as finished</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div id="formAlertPlaceholderDelete"></div>
                <div class="modal-body">
                    <form id="deleteProductForm" class="needs-validation"  novalidate>

                        <div class="mb-3">
                            <label for="aliasDelete" class="col-form-label">Alias:</label>
                            <input type="text" class="form-control" id="aliasDelete" name="alias" required>
                        </div>
                        <div class="mb-3">
                            <label for="nameDelete" class="col-form-label">Name:</label>
                            <input type="text" class="form-control" id="nameDelete" name="name" required/>
                        </div>
                        <div class="mb-3">
                            <label for="brandDelete" class="col-form-label">Brand:</label>
                            <input type="text" class="form-control" id="brandDelete" name="brand" required/>
                        </div>
                        <div class="mb-3">
                            <label for="descriptionDelete" class="col-form-label">Description:</label>
                            <textarea class="form-control" id="descriptionDelete" name="description" required></textarea>
                        </div>
                        <div class="mb-3">
                            <div class="input-group">
                                <input type="hidden" class="form-control" min="0.01" step="0.01" max="2500" placeholder="10.00" id="purchaseDelete" name="purchase" required/>
                            </div>
                        </div>
                        <div class="mb-3">
                            <div class="input-group">
                                <input type="hidden" class="form-control" min="0.01" step="0.01" max="2500" placeholder="10.00" id="saleDelete" name="sale" required/>
                            </div>
                        </div>
                        <div class="mb-3">
                            <input type="hidden" class="form-control" min="1" step="1" placeholder="1" id="quantityDelete" name="quantity" required/>
                        </div>
                        <div class="mb-3">
                            <label for="category">Category:</label>
                            <div class="input-group">
                                <select class="form-control" name="category" id="categoryDelete" required>
                                    <option value=""  hidden selected disabled>Choose</option>
                                    <c:forEach var="cat" items="${productCategoryList}">
                                        <option value="${cat.name}">
                                                ${cat.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="mb-3">
                            Evidence:
                            <div class="form-check form-check-inline" >
                                <input class="form-check-input" type="hidden" id="yesDelete"
                                       name="evidence" value="true" required>

                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="hidden" id="noDelete"
                                       name="evidence" value="false" required>
                            </div>
                        </div>
                        <div class="mb-3 image-check-box">
                            <ul>
                                <c:if test="${not empty media}">
                                    <li><input type="checkbox" id="media-${media.id}" name="pictures" value="${media.id}"/>
                                        <label for="media-${media.id}"><img src="<c:url value="/media/thumb/${media.id}" />" alt="${media.filename}" /></label>
                                    </li>
                                </c:if>
                                <c:forEach var="media" items="${mediaList}">
                                    <li><input type="checkbox" id="media-${media.id}" name="pictures" value="${media.id}"/>
                                        <label for="media-${media.id}"><img src="<c:url value="/media/thumb/${media.id}" />" alt="${media.filename}" /></label>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" id="deleteProductButton" form="deleteProductForm">Delete product</button>
                </div>
            </div>
        </div>
    </div>

</div>
<c:import url="/jsp/include/footer-management.jsp"/>
<script src="<c:url value="/js/product-management.js"/>"></script>
</body>
</html>
