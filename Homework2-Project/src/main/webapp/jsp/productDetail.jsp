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
    <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
        <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
            <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
            <li class="breadcrumb-item active" aria-current="page">Product: ${product.name}</li>
        </ol>
    </nav>
    <div class="clearfix product-detail">
        <div class="col-md-6 me-md-4 float-start ratio ratio-1x1 overflow-hidden rounded border">
            <c:choose>
                <c:when test="${not empty product.pictures}">

                    <c:set var="pic_number" value="${0}"/>
                    <c:forEach var="picture" items="${product.pictures}">
                        <c:set var="pic_number" value="${pic_number + 1}"/>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${pic_number > 1}">
                            <div id="picCarousel" class="carousel slide product-carousel" data-bs-ride="carousel">

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
                                                    <img src="<c:url value="/media/view/${picture}"/>" alt="${product.alias}" class="d-block w-100"/>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="carousel-item ratio ratio-1x1 ">
                                                    <img src="<c:url value="/media/view/${picture}"/>" alt="${product.alias}" class="d-block w-100"/>
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
                                <div class="ratio ratio 1x1">
                                    <img src="<c:url value="/media/view/${picture}"/>" alt="${product.alias}"
                                         class="d-block w-100"/>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <div>
                        <img src="${pageContext.request.contextPath}/images/No_image_available_poster.jpg" alt="${product.alias}" class="d-block w-100"/>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <div>
            <div>
                <h3 class="title">${product.name}</h3>
                <h5 class="text-primary">Brand: ${product.brand}</h5>
                <hr/>

                <c:choose>
                    <c:when test="${not empty product.discount}">
                    <div class="row">
                        <div class="col-md-6">Price: <span style="text-decoration: line-through;">${product.salePrice}€</span> <span style="color: red;">${product.discountSale}€</span></div>
                        <div class="col-md-6 text-end"> ${product.discount.percentage}% discount (until ${product.discount.endDate.humanDate})</div>
                    </div>
                    </c:when>
                    <c:otherwise>
                        <p>Price: ${product.salePrice}€</p>
                    </c:otherwise>
                </c:choose>
                <br>
                <p>${product.description}</p>

                <%--
                <ul>
                    <li>Brand: ${product.brand}</li>
                    <li>Description: ${product.description}</li>
                    <li>Quantity: ${product.quantity}</li>

                    <c:choose>
                        <c:when test="${not empty product.discount}">
                            <li>Price: <span style="text-decoration: line-through;">${product.salePrice}€</span> <span
                                    style="color: red;">${product.discountSale}€</span></li>
                            <li>Discount: ${product.discount.percentage}%
                                (until ${product.discount.endDate.humanDate})
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li>Price: ${product.salePrice}€</li>
                        </c:otherwise>
                    </c:choose>
                </ul>
                --%>
                <c:choose>
                    <c:when test="${product.quantity <= 0}">
                        <span class="fw-bold text-danger">The product is no longer available.</span>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${not empty user && empty user.role}">
                                <form method="POST" action="<c:url value="/buy/product/${product.alias}"/>" id="formSend" data-product-alias="${product.alias}" data-product-name="${product.name}">
                                    <label for="quantity">Selected quantity</label>
                                    <input type="number" name="quantity" max="${product.quantity}" min="1" id="quantity" required>
                                    <br>
                                    <br>

                                    <input type="submit" value="Buy now" class="btn btn-primary">
                                    <input type="submit" value="Add to cart" class="btn btn-primary" onclick="cart()">
                                </form>
                                <br>

                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${not empty user && not empty user.role}">
                                        <hr/>
                                        <span class="fw-bold text-danger">Employees are not authorized to make purchases.</span>
                                    </c:when>
                                    <c:otherwise>
                                        <hr/>
                                        Please, <a href="<c:url value="/session/login"/>" class="btn btn-primary">login</a> to buy the product
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

<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
