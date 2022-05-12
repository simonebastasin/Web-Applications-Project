<%--
  Created by IntelliJ IDEA.
  User: matte
  Date: 01/04/2022
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="productList" type="java.util.List<Product>"--%>
<%--@elvariable id="productCategoryList" type="java.util.List<ProductCategory>"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>
    <title>Electromechanics Shop</title>
</head>

<body>

<c:import url="/jsp/include/header.jsp"/>

<div class="container main-container">

<h1 class="title">Electromechanical <small class="text-primary" >Shop</small></h1>

    <c:set value="${false}" var="showcase"/>
    <c:forEach var="prod" items="${productList}">
        <c:if test="${prod.evidence == true}">
            <c:set value="${true}" var="showcase"/>
        </c:if>
    </c:forEach>

    <c:choose>
    <c:when test="${showcase == true}">

    <div id="featuredCarousel" class="carousel carousel-dark slide featured-carousel" data-bs-ride="carousel">

        <c:set var="i" value="${0}"/>
        <div class="carousel-indicators">
            <c:forEach var="prod" items="${productList}">
                <c:if test="${prod.evidence == true}">
                    <c:choose>
                        <c:when test="${i == 0}">
                            <button type="button" data-bs-target="#featuredCarousel" data-bs-slide-to="0"
                                    class="active" aria-current="true"
                                    aria-label="Slide 1"></button>
                        </c:when>
                        <c:otherwise>
                            <button type="button" data-bs-target="#featuredCarousel"
                                    data-bs-slide-to="${i}" aria-label="Slide ${i+1}"></button>
                        </c:otherwise>
                    </c:choose>
                    <c:set var="i" value="${i+1}"/>
                </c:if>
            </c:forEach>
        </div>

        <button class="carousel-control-prev" type="button" data-bs-target="#featuredCarousel"
                data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#featuredCarousel"
                data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>

        <c:set var="active" value="${true}"/>
        <div class="carousel-inner p-3" role="listbox">

            <c:forEach var="prod" items="${productList}">
            <c:if test="${prod.evidence == true}">

            <c:choose>

            <c:when test="${active == true}">
            <div class="carousel-item active p-3">

                <div class="d-flex justify-content-center">

                        <div class="box">
                            <a href="<c:url value="/products/details/${prod.alias}"/>" class="stretched-link">
                                <div class="img-box rounded shadow shadow-hover rounded">
                                    <c:choose>
                                        <c:when test="${not empty prod.pictures}">

                                            <c:set var="i" value="${0}"/>
                                            <c:forEach var="pic" items="${prod.pictures}">
                                                <c:if test="${i == 0}">
                                                    <c:set var="picture" value="${pic}"/>
                                                </c:if>
                                                <c:set var="i" value="${1}"/>
                                            </c:forEach>
                                            <img src="<c:url value="/media/view/${picture}"/>" alt="${prod.alias}" class="mh-100 w-100 img-thumbnail">

                                        </c:when>
                                        <c:otherwise>

                                            <img src="<c:url value="/images/No_image_available_circle.png"/>" alt="${prod.alias}" class="mh-100 w-100 img-thumbnail" >

                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div class="content shadow shadow-hover">

                                    <div class="col-md-auto text-start">
                                        <h3>${prod.name}</h3>
                                    </div>
                                    <div class="col ms-3 text-start">
                                        <span>${prod.brand}</span>
                                    </div>
                                    <div class="col text-end">
                                        <c:choose>
                                            <c:when test="${not empty prod.discount}">
                                                <span  style="text-decoration: line-through;">${prod.salePrice}€</span> <span style="color: red;">${prod.discountSale}€</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span>${prod.salePrice}€</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </a>
                        </div>
                </div>
            </div>
            </c:when>
            <c:otherwise>

                <div class="carousel-item p-3">
                    <div class="d-flex justify-content-center">

                            <div class="box">
                                <a href="<c:url value="/products/details/${prod.alias}"/>" class="stretched-link">

                                    <div class="img-box rounded shadow shadow-hover rounded">
                                        <c:choose>
                                            <c:when test="${not empty prod.pictures}">

                                                <c:set var="i" value="${0}"/>
                                                <c:forEach var="pic" items="${prod.pictures}">
                                                    <c:if test="${i == 0}">
                                                        <c:set var="picture" value="${pic}"/>
                                                    </c:if>
                                                    <c:set var="i" value="${1}"/>
                                                </c:forEach>
                                                <img src="<c:url value="/media/view/${picture}"/>" alt="${prod.alias}" class="mh-100 w-100 img-thumbnail">

                                            </c:when>
                                            <c:otherwise>

                                                <img src="<c:url value="/images/No_image_available_circle.png"/>" alt="${prod.alias}" class="mh-100 w-100 img-thumbnail">


                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="content shadow shadow-hover">

                                        <div class="col-md-auto text-start">
                                            <h3>${prod.name}</h3>
                                        </div>
                                        <div class="col ms-3 text-start">
                                            <span>${prod.brand}</span>
                                        </div>
                                        <div class="col text-end">
                                            <c:choose>
                                                <c:when test="${not empty prod.discount}">
                                                    <span  style="text-decoration: line-through;">${prod.salePrice}€</span> <span style="color: red;">${prod.discountSale}€</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span>${prod.salePrice}€</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </a>
                            </div>

                    </div>
                </div>

            </c:otherwise>
            </c:choose>

            <c:set var="active" value="${false}"/>

            </c:if>
            </c:forEach>
        </div>
    </div>

        <hr class="mt-3">

</c:when>
    <c:otherwise>
        <hr>
    </c:otherwise>
    </c:choose>

<h3>Available products</h3>
    <c:set var="count" value="0" scope="page" />

<c:forEach var="item" items="${productCategoryList}">

    <b><a href="<c:url value="products/category/${item.name}"/>">${item.name}</a></b>

    <c:set value="${true}" var="empty_cat"/>
    <c:forEach var="prod" items="${productList}">
        <c:if test="${prod.category.name.equals(item.name)}">
            <c:set value="${false}" var="empty_cat"/>
        </c:if>
    </c:forEach>
    <c:choose>
        <c:when test="${empty_cat eq true}">
            <p>No prod here</p>
        </c:when>
        <c:otherwise>
    <div class="row mx-auto justify-content-center border">
            <div class="carousel carousel-category carousel-dark slide" id="categoryCarousel${count}"  data-bs-interval="false" data-bs-ride="carousel">


                <button class="carousel-control-prev" type="button" data-bs-target="#categoryCarousel${count}"
                        data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#categoryCarousel${count}"
                        data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>

                <c:set var="i" value="${0}"/>
                <div class="carousel-indicators">
                    <c:forEach var="prod" items="${productList}">
                        <c:if test="${prod.category.name.equals(item.name)}">
                            <c:choose>
                                <c:when test="${i == 0}">
                                    <button type="button" data-bs-target="#categoryCarousel${count}" data-bs-slide-to="0"
                                            class="active" aria-current="true"
                                            aria-label="Slide 1"></button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" data-bs-target="#categoryCarousel${count}"
                                            data-bs-slide-to="${i}" aria-label="Slide ${i+1}"></button>
                                </c:otherwise>
                            </c:choose>
                            <c:set var="i" value="${i+1}"/>
                        </c:if>
                    </c:forEach>
                </div>

                <c:set var="active" value="${true}"/>

                        <div class="carousel-inner p-5" role="listbox">

                            <c:forEach var="prod" items="${productList}">
                            <c:if test="${prod.category.name.equals(item.name)}">

                                <c:choose>
                                    <c:when test="${active == true}">


                                        <div class="carousel-item active p-3">

                                            <div class="col-md-3 text-center">
                                                <div class="square one">
                                                    <div class="img-cat">
                                                        <div class="overlay-hover">
                                                        <c:choose>
                                                        <c:when test="${not empty prod.pictures}">
                                                            <c:set var="i" value="${0}"/>
                                                            <c:forEach var="picture" items="${prod.pictures}">
                                                                <c:if test="${i == 0}">
                                                                    <c:set var="pic" value="${picture}"/>
                                                                </c:if>
                                                                <c:set var="i" value="${1}"/>
                                                            </c:forEach>
                                                            <img src="<c:url value="/media/view/${pic}"/>" alt="${prod.alias}" class="mh-100 w-auto img-thumbnail"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="<c:url value="/images/No_image_available_circle.png"/>" alt="${prod.alias}" class="mh-100 w-auto img-thumbnail" >
                                                        </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                    <c:when test="${not empty prod.discount}">
                                                    <div class="mask">
                                                        <div class="text-center text-white fw-bold">
                                                                Product name: <a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a>  <br>
                                                                Brand: ${prod.brand} <br>
                                                                Quantity: ${prod.quantity} <br>
                                                                Price: <div class="text-decoration-line-through"> ${prod.salePrice}€</div> <div class="text-red">${prod.discountSale}€</div>
                                                        </div>
                                                    </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="mask">

                                                            <div class="text-center text-white fw-bold">
                                                                Product name: <a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a>  <br>
                                                                Brand: ${prod.brand} <br>
                                                                Quantity: ${prod.quantity} <br>
                                                                Price: ${prod.salePrice}€
                                                            </div>

                                                        </div>

                                                    </c:otherwise>
                                                    </c:choose>
                                                    </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <c:set var="active" value="${false}"/>

                                    </c:when>
                                    <c:otherwise>
                                        <div class="carousel-item p-3">

                                            <div class="col-md-3 text-center">
                                                <div class="square one">
                                                    <div class="img-cat">
                                                    <div class="overlay-hover">
                                                    <c:choose>
                                                        <c:when test="${not empty prod.pictures}">
                                                            <c:set var="i" value="${0}"/>
                                                            <c:forEach var="picture" items="${prod.pictures}">
                                                                <c:if test="${i == 0}">
                                                                    <c:set var="pic" value="${picture}"/>
                                                                </c:if>
                                                                <c:set var="i" value="${1}"/>
                                                            </c:forEach>
                                                            <img src="<c:url value="/media/view/${pic}"/>" alt="${prod.alias}" class="mh-100 w-auto img-thumbnail"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="<c:url value="/images/No_image_available_circle.png"/>" alt="${prod.alias}" class="mh-100 w-auto img-thumbnail" >
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${not empty prod.discount}">
                                                            <div class="mask">
                                                                <div class="text-center text-white fw-bold">
                                                                Product name: <a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a>  <br>
                                                                Brand: ${prod.brand} <br>
                                                                Quantity: ${prod.quantity} <br>
                                                                Price: <div  class="text-decoration-line-through">${prod.salePrice}€</div> <div class="text-red">${prod.discountSale}€</div>
                                                                </div>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <div class="mask">
                                                            <div class="text-center text-white fw-bold">
                                                                Product name: <a href="<c:url value="/products/details/${prod.alias}"/>">${prod.name}</a>  <br>
                                                                Brand: ${prod.brand} <br>
                                                                Quantity: ${prod.quantity} <br>
                                                                Price: ${prod.salePrice}€
                                                            </div>
                                                        </div>

                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                                </div>
                                            </div>
                                        </div>

                                    </c:otherwise>
                                </c:choose>
                            </c:if>

                        </c:forEach>
                            </div>
                        </div>


    </div>
        </c:otherwise>
    </c:choose>

    <c:set var="count" value="${count + 1}" scope="page"/>

</c:forEach>
</div>


<c:import url="/jsp/include/footer.jsp"/>

<script>
    let carousels = document.querySelectorAll('.carousel-category')

    carousels.forEach((it) => {
        let items = it.querySelectorAll('.carousel-item')

        items.forEach((el) => {
            const minPerSlide = 4 // number of slides per carousel-item

            let next = el.nextElementSibling
            for (var i=1; i<minPerSlide && i< items.length; i++) {
                if (!next && items.length > 1) {
                    // wrap carousel by using first child
                    next = items[0]
                }
                if(next) {
                    let cloneChild = next.cloneNode(true)
                    el.appendChild(cloneChild.children[0])
                    next = next.nextElementSibling
                }
            }
        })
    })
</script>
</body>
</html>
