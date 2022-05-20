<%--
  Created by IntelliJ IDEA.
  User: gioel
  Date: 04/04/2022
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="product" type="Product"--%>
<%--@elvariable id="user" type="it.unipd.dei.wa2122.wadteam.resources.UserCredential"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Product: ${product.name} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>

<div class="container main-container">

    <div class="clearfix product-detail">
            <div class="col-md-6 me-md-4 float-start ratio ratio-1x1 rounded border mt-3 mb-5 bg-light">
                <c:choose>
                    <c:when test="${not empty product.pictures}">

                        <c:set var="pic_number" value="${0}"/>
                        <c:forEach var="picture" items="${product.pictures}">
                            <c:set var="pic_number" value="${pic_number + 1}"/>
                        </c:forEach>

                        <c:choose>
                            <c:when test="${pic_number > 1}">
                                <div id="picCarousel" class="carousel carousel-dark slide product-carousel" data-bs-ride="carousel">

                                    <c:set var="i" value="${0}"/>
                                    <div class="carousel-indicators">

                                        <c:forEach var="picture" items="${product.pictures}">

                                            <c:choose>
                                                <c:when test="${i == 0}">
                                                    <button type="button" data-bs-target="#picCarousel" data-bs-slide-to="0"
                                                            class="active" aria-current="true"
                                                            aria-label="Slide 1"></button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button type="button" data-bs-target="#picCarousel"
                                                            data-bs-slide-to="${i}" aria-label="Slide ${i+1}"></button>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:set var="i" value="${i+1}"/>

                                        </c:forEach>

                                    </div>

                                    <button class="carousel-control-prev" type="button" data-bs-target="#picCarousel"
                                            data-bs-slide="prev">
                                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Previous</span>
                                    </button>
                                    <button class="carousel-control-next" type="button" data-bs-target="#picCarousel"
                                            data-bs-slide="next">
                                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Next</span>
                                    </button>

                                    <c:set var="first" value="${true}"/>
                                    <div class="carousel-inner" role="listbox">

                                        <c:forEach var="picture" items="${product.pictures}">

                                            <c:choose>
                                                <c:when test="${first == true}">
                                                    <div class="carousel-item active ratio ratio-1x1 ">
                                                        <img src="<c:url value="/media/view/${picture}"/>" alt="${product.alias}" class="d-block w-100 img-thumbnail img-fluid"/>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="carousel-item ratio ratio-1x1 ">
                                                        <img src="<c:url value="/media/view/${picture}"/>" alt="${product.alias}" class="d-block w-100 img-fluid"/>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>

                                            <c:set var="first" value="${false}"/>
                                        </c:forEach>

                                    </div>

                                </div>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="picture" items="${product.pictures}">
                                    <div class="ratio ratio-1x1">
                                        <img src="<c:url value="/media/view/${picture}"/>" alt="${product.alias}"
                                             class="d-block w-100 img-fluid"/>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <div class="ratio ratio-1x1">
                                <img src="${pageContext.request.contextPath}/images/No_image_available_poster.jpg" alt="${product.alias}" class="d-block w-100 img-fluid"/>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-md-5 me-md-4 float-end mt-3 mb-4">
                <div class="product-card">
                <div class="card mt-5">
                    <div class="card-body ms-10">
                    <h3 class="title">${product.name}</h3>
                    <span class="roboto-thin text-primary">Brand: ${product.brand}</span>
                    <hr/>

                    <c:choose>
                        <c:when test="${not empty product.discount}">
                            <div class="row">
                                <div class="col-md-6">Price: <span class="text-decoration-line-through">${String.format("%.2f",product.salePrice)}€</span> <span class="text-red display-6">${String.format("%.2f",product.discountSale)}€</span></div>
                                <div class="col-md-6 text-end"> -${product.discount.percentage}% (until ${product.discount.endDate.humanDate})</div>
                            </div>
                        </c:when>
                        <c:otherwise>
                                Price: <span class="display-6"> ${String.format("%.2f",product.salePrice)}€</span>
                        </c:otherwise>
                    </c:choose>

                    <p class="mt-3">${product.description}</p>

                    <hr/>

                    <c:choose>
                        <c:when test="${product.quantity <= 0}">
                            <span class="fw-bold text-danger">The product is no longer available.</span>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${not empty user && empty user.role}">

                                   <%-- <p class="text-danger">Available quantity: ${product.quantity}</p>--%>

                                    <form method="POST" action="<c:url value="/buy/product/${product.alias}"/>" id="formSend" data-product-alias="${product.alias}" data-product-name="${product.name}" class="needs-validation input-group" novalidate>
                                        <div class="row w-100 align-items-center g-3 mb-3 pl-0">
                                            <div class="col-auto pl-0">
                                                <label class="col-form-label" for="quantity">Quantity</label>
                                            </div>
                                            <div class="col-auto">
                                                <input type="number" name="quantity" max="${product.quantity}" min="1" id="quantity" value="1" required class="form-control">
                                            </div>
                                            <div class="col-auto">
                                                <span class="form-text text-danger">Available quantity: ${product.quantity}</span>
                                            </div>
                                        </div>

                                        <div class="row w-100 align-items-center">
                                            <div class="col text-end">
                                                <button type="button" class="btn btn-outline-primary mt-1" id="addToCart" >
                                                    Add to   <i class="fa-solid fa-cart-plus"></i>
                                                </button>
                                                <button type="submit" class="btn btn-outline-primary mt-1">
                                                    Buy now   <i class="fa-solid fa-money-check"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </form>

                                    <br>

                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${not empty user && not empty user.role}">
                                            <span class="fw-bold text-danger">Employees are not authorized to make purchases</span>
                                        </c:when>
                                        <c:otherwise>
                                            Please <a class="btn btn-outline-primary" id="loginForBuy">login</a> to buy the product
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    </div>
                </div>
                </div>
            </div>
    </div>
</div>

<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
