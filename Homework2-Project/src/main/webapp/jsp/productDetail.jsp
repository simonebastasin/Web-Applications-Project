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
    <style>
        @import "productDetail.css";
    </style>

    <title>Product: ${product.name} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Product: ${product.name}</li>
  </ol>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-xl-6 col-lg-6 col-md-6">
            <c:choose>
                <c:when test="${not empty product.pictures}">

                    <c:set var="pic_number" value="${0}"/>
                    <c:forEach var="picture" items="${product.pictures}">
                        <c:set var="pic_number" value="${pic_number + 1}"/>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${pic_number > 1}">
                            <div class="container">
                                <div id="picCarousel" class="carousel slide" data-bs-ride="carousel">

                                    <c:set var="i" value="${0}"/>
                                    <div class="carousel-indicators">
                                        <c:forEach var="picture" items="${product.pictures}">
                                            <c:choose>
                                                <c:when test="${i == 0}">
                                                    <button type="button" data-bs-target="#picCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button type="button" data-bs-target="#picCarousel" data-bs-slide-to="${i}" aria-label="Slide ${i+1}"></button>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:set var="i" value="${i+1}"/>
                                        </c:forEach>
                                    </div>

                                    <c:set var="j" value="${0}"/>
                                    <div class="carousel-inner" role="listbox">
                                        <c:forEach var="picture" items="${product.pictures}">
                                            <c:choose>
                                                <c:when test="${j == 0}">
                                                    <div class="carousel-item active">
                                                        <img src="<c:url value="/media/view/${picture}"/>" alt="${product.alias}" class="d-block w-100"/>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="carousel-item">
                                                        <img src="<c:url value="/media/view/${picture}"/>" alt="${product.alias}" class="d-block w-100"/>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:set var="j" value="${1}"/>
                                        </c:forEach>
                                    </div>

                                    <button class="carousel-control-prev" type="button" data-bs-target="#picCarousel" data-bs-slide="prev">
                                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Previous</span>
                                    </button>
                                    <button class="carousel-control-next" type="button" data-bs-target="#picCarousel" data-bs-slide="next">
                                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Next</span>
                                    </button>

                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="picture" items="${product.pictures}">
                                <div>
                                    <img src="<c:url value="/media/view/${picture}"/>" alt="${product.alias}" width="400px"/>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <div>
                        <img src="${pageContext.request.contextPath}/images/No_image_available_poster.jpg" alt="${product.alias}" width="400px"/>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-xl-6 col-lg-6 col-md-6">
            <div>
                <ul>
                    <li>Brand: ${product.brand}</li>
                    <li>Description: ${product.description}</li>
                    <li>Quantity: ${product.quantity}</li>

                    <c:choose>
                        <c:when test="${not empty product.discount}">
                            <li>Price: <span  style="text-decoration: line-through;">${product.salePrice}€</span> <span style="color: red;">${product.discountSale}€</span></li>
                            <li>Discount: ${product.discount.percentage}% (until ${product.discount.endDate.humanDate})</li>
                        </c:when>
                        <c:otherwise>
                            <li>Price: ${product.salePrice}€</li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </div>
</div>

<c:choose>
    <c:when test="${product.quantity <= 0}">
        <span style="color: red;font-weight: bold">The product is no longer available.</span>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${not empty user && empty user.role}">
                <hr>

                <form method="POST" action="<c:url value="/buy/product/${product.alias}"/>">
                    <label for="quantity">Selected quantity</label>
                    <input type="number" name ="quantity" max="${product.quantity}" min="1" id="quantity" required> <br>

                    <input type ="submit" value = "Buy now">
                </form>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${not empty user && not empty user.role}">
                        <span style="color: red;font-weight: bold">Employees are not authorized to make purchases.</span>
                    </c:when>
                    <c:otherwise>
                        Please, <a href="<c:url value="/session/login"/>">login</a> to buy the product
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
