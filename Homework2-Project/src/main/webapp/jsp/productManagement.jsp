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
<div>
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addProductModal">
        Add new product
    </button>
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
            <td><button type="button" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#addProductModal" data-bs-whatever="${prod.alias}">
                Edit
            </button></td>
            <!--<td><button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteProductModal" data-bs-whatever="${prod.alias}">
                Delete product
            </button></td>-->
            <td><a class="btn btn-outline-danger" href="<c:url value="/management/productManagement/deleteProduct/${prod.alias}"/>">Delete</a></td>
        </tr>

    </c:forEach>
</table>

    <div class="modal fade" id="addProductModal" tabindex="-1" aria-labelledby="addProductModal" aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false" >
        <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addProductModalTitle">Add Product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div id="formAlertPlaceholder"></div>
                <div class="modal-body">
                    <form id="addProductForm">
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
                            <textarea class="form-control" id="description" name="description"></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="purchase" class="col-form-label">Purchase price:</label>
                            <div class="input-group">
                                <span class="input-group-text">€</span>
                                <input type="number" class="form-control" min="0.01" step="0.01" max="2500" placeholder="10.00" id="purchase" name="purchase" required/>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="purchase" class="col-form-label">Sale price:</label>
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
                                <button class="btn btn-outline-secondary" type="button">+</button>
                            </div>
                        </div>
                        <div class="mb-3">
                            Evidence:
                            <div class="form-check form-check-inline" >
                                <input class="form-check-input" type="radio" id="yes"
                                       name="evidence" value="true">
                                <label class="form-check-label" for="yes">Yes</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" id="no"
                                       name="evidence" value="false">
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
                    <form id="uploadImageForm" class="mb-3">
                        <label for="formFile" class="form-label">Upload image</label>
                        <div class="input-group">
                            <input class="form-control" type="file" name="file" id="formFile" required>
                            <button type="submit" class="btn btn-outline-secondary">Upload</button>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="addProductButton">Add product</button>
                </div>
            </div>
        </div>
    </div>

</div>
<c:import url="/jsp/include/footer-management.jsp"/>
<script>
    const addProductButton = document.getElementById('addProductButton');
    const addProductForm = document.getElementById('addProductForm');
    const addProductModal = document.getElementById('addProductModal');
    const uploadImageForm = document.getElementById('uploadImageForm');
    let recipient;
    addProductButton.addEventListener('click', (e) => {

        console.log(recipient);
        const urlencoded_data = new URLSearchParams(new FormData(addProductForm));

        const xmlhttp = new XMLHttpRequest();
        if(recipient  === null)
            xmlhttp.open("POST", "<c:url value="/rest/management/productManagement/createProduct" />", true);
        else
            xmlhttp.open("POST", "<c:url value="/rest/management/productManagement/editProduct/" />"+recipient, true);
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState === XMLHttpRequest.DONE) {
                if(xmlhttp.status === 200) {
                    const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                    if(recipient  === null)
                        bootstrapAlert("The product was created", 'success', alertPlaceholder);
                    else
                        bootstrapAlert("The product was modified", 'success', alertPlaceholder);
                    bootstrap.Modal.getOrCreateInstance(addProductModal).hide();
                    // TODO refresh list
                } else {
                    const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                    bootstrapAlert(xmlhttp.responseText, 'error', alertPlaceholder);
                }
            }
        }
        xmlhttp.send(urlencoded_data);
    })
    uploadImageForm.addEventListener('submit', (e) => {
        e.preventDefault();

        const multipart_data = new FormData(uploadImageForm);

        var xmlhttp = new XMLHttpRequest();   e
        xmlhttp.open("POST", "<c:url value="/rest/media/upload" />", true);
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState === XMLHttpRequest.DONE) {
                if(xmlhttp.status === 200) {
                    const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                    const response = JSON.parse(xmlhttp.responseText)[0];
                    const imageCheckBox = document.querySelectorAll('.image-check-box ul');
                    [...imageCheckBox].forEach(
                        (element, index, array) => {
                            const li = document.createElement('li');
                            li.innerHTML = '<input type="checkbox" id="media-'+response.resourceId+'" name="media" value="'+response.resourceId+' checked"/>'+
                                '<label for="media-'+response.resourceId+'"><img src="<c:url value="/media/thumb/" />'+response.resourceId+'" /></label>';
                            element.appendChild(li);
                        }
                    );
                    bootstrapAlert(response.message, 'success', alertPlaceholder);
                } else {
                    const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                    bootstrapAlert(xmlhttp.responseText, 'danger', alertPlaceholder);
                }
            }
        }
        xmlhttp.send(multipart_data);
    })
    addProductModal.addEventListener('show.bs.modal', (e) => {
        // Button that triggered the modal
        var button = e.relatedTarget;
        // Extract info from data-bs-* attributes
        recipient = button.getAttribute('data-bs-whatever');

        if(recipient === null) {
            addProductForm.reset();


            let modalTitle = addProductModal.querySelector('.modal-title');
            modalTitle.textContent = 'Add product';
            addProductButton.textContent = 'Add product';
        }
        else {
            const xmlhttp = new XMLHttpRequest();
            xmlhttp.open("GET", "<c:url value="/rest/management/productManagement/editProduct/" />"+recipient, true);
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState === XMLHttpRequest.DONE) {
                    if(xmlhttp.status === 200) {
                        const response = JSON.parse(xmlhttp.responseText);
                        populateForm(addProductForm, response["product"],)

                    } else {
                        const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                        bootstrapAlert('product not found', 'danger', alertPlaceholder);
                        bootstrap.Modal.getOrCreateInstance(addProductModal).hide();
                    }
                }
            }
            xmlhttp.send();

            let modalTitle = addProductModal.querySelector('.modal-title');
            modalTitle.textContent = 'Edit product ' + recipient;
            addProductButton.textContent = 'Edit product';
        }

    })
</script>
</body>
</html>
