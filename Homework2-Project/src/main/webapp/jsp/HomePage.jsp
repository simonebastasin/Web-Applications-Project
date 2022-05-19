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

<h1 class="title">Electromechanical <small class="text-secondary" >Shop</small></h1>

    <c:set value="${false}" var="showcase"/>
    <c:forEach var="prod" items="${productList}">
        <c:if test="${prod.evidence == true}">
            <c:set value="${true}" var="showcase"/>
        </c:if>
    </c:forEach>

    <c:choose>
    <c:when test="${showcase == true}">

    <div id="featuredCarousel" class="carousel carousel-dark slide " data-bs-ride="carousel">

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

                                <div class="img-box rounded shadow shadow-hover rounded">
                                    <a href="<c:url value="/products/details/${prod.alias}"/>" class="stretched-link"> </a>
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

                                    <div class="col-md-auto text-start text-black">
                                        <h3>${prod.name}</h3>
                                    </div>
                                    <div class="col-md-auto ms-3 em text-start">
                                        <span class="roboto">${prod.brand}</span>
                                    </div>
                                    <div class="col text-end">
                                        <c:choose>
                                            <c:when test="${not empty prod.discount}">
                                                <span class="text-decoration-line-through roboto">${String.format("%.2f",prod.salePrice)}€</span> <span class="roboto text-red">${String.format("%.2f",prod.discountSale)}€</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="roboto">${String.format("%.2f",prod.salePrice)}€</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                </div>

                        </div>
                </div>
            </div>
            </c:when>
            <c:otherwise>

                <div class="carousel-item p-3">
                    <div class="d-flex justify-content-center">

                            <div class="box">


                                    <div class="img-box rounded shadow shadow-hover rounded">
                                        <a href="<c:url value="/products/details/${prod.alias}"/>" class="stretched-link"> </a>
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

                                        <div class="col-md-auto text-start text-black">
                                            <h3>${prod.name}</h3>
                                        </div>
                                        <div class="col-md-auto ms-3 text-start">
                                            <span class="roboto">${prod.brand}</span>
                                        </div>
                                        <div class="col text-end">
                                            <c:choose>
                                                <c:when test="${not empty prod.discount}">
                                                    <span class="text-decoration-line-through roboto">${String.format("%.2f",prod.salePrice)}€</span> <span class="roboto text-red">${String.format("%.2f",prod.discountSale)}€</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="roboto">${String.format("%.2f",prod.salePrice)}€</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>

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


</c:when>

    </c:choose>

<h3>Our products</h3>
    <c:set var="count" value="0" scope="page" />

<c:forEach var="item" items="${productCategoryList}">



    <c:set value="${true}" var="empty_cat"/>
    <c:set value="${true}" var="only_row"/>
    <c:set value="${0}" var="counter"/>
    <c:forEach var="prod" items="${productList}">
        <c:if test="${prod.category.name.equals(item.name)}">
            <c:set value="${false}" var="empty_cat"/>
            <c:set value="${counter+1}" var="counter"/>
        </c:if>
    </c:forEach>
    <c:if test="${counter > 4}">
        <c:set value="${false}" var="only_row"/>
    </c:if>
    <c:choose>
        <c:when test="${empty_cat eq true}">
    <div class="row mx-auto justify-content-center border mt-3 mb-3 rounded bg-light">
        <div class="d-inline p-3">
            <h3 class="title d-inline pe-3">${item.name}</h3>
            <span>Sorry, there are no products at the moment for this category</span>
        </div>
    </div>
        </c:when>
        <c:otherwise>

            <div class="row mx-auto justify-content-center border mt-3 mb-3 rounded bg-light">
            <div class="d-inline p-3">
                <h3 class="title d-inline">${item.name}</h3>
                <a href="<c:url value="products/category/${item.name}"/>" class="d-inline p-3 fw-bold">Shop now</a>
            </div>

            <div class="carousel carousel-category carousel-dark slide" id="categoryCarousel${count}"  data-bs-interval="false" data-bs-ride="carousel">

                <c:if test="${only_row}">
                    <div class="only_row">
                </c:if>
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
                <c:if test="${only_row}">
                    </div>
                </c:if>



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

                        <div class="carousel-inner ps-5 pe-5" role="listbox">

                            <c:forEach var="prod" items="${productList}">
                            <c:if test="${prod.category.name.equals(item.name)}">

                                <c:choose>
                                    <c:when test="${active == true}">


                                        <div class="carousel-item active p-3">

                                            <div class="text-center">
                                                <div class="square one img-cat">

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
                                                                    <a href="<c:url value="/products/details/${prod.alias}"/>" class="stretched-link fw-bold"></a>
                                                                    <div class="text-center text-white mt-3 mb-3">
                                                                        <b>${prod.name}</b><br>
                                                                        <span class="roboto">${prod.brand}</span> <br> <br>
                                                                        <span class="text-decoration-line-through roboto">${String.format("%.2f",prod.salePrice)}€ </span> <span class="roboto text-red">${String.format("%.2f", prod.discountSale)}€</span>
                                                                    </div>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="mask">
                                                                    <a href="<c:url value="/products/details/${prod.alias}"/>" class="stretched-link fw-bold"></a>
                                                                    <div class="text-center text-white mt-3 mb-3">
                                                                        <b>${prod.name}</b><br>
                                                                        <span class="roboto">${prod.brand}</span> <br> <br>
                                                                        <span class="roboto">${String.format("%.2f", prod.salePrice)}€</span>
                                                                    </div>

                                                                </div>

                                                    </c:otherwise>
                                                        </c:choose>
                                                        </div>

                                                </div>
                                            </div>
                                        </div>
                                        <c:set var="active" value="${false}"/>

                                    </c:when>
                                    <c:otherwise>
                                        <div class="carousel-item p-3">

                                            <div class="text-center">
                                                <div class="square one img-cat">

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
                                                                    <a href="<c:url value="/products/details/${prod.alias}"/>" class="stretched-link fw-bold"></a>
                                                                    <div class="text-center text-white mt-3 mb-3">
                                                                        <b>${prod.name}</b><br>
                                                                        <span class="roboto">${prod.brand}</span> <br> <br>
                                                                        <span class="text-decoration-line-through roboto">${String.format("%.2f", prod.salePrice)}€</span> <span class="roboto text-red">${String.format("%.2f", prod.discountSale)}€</span>
                                                                    </div>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <div class="mask">
                                                                <a href="<c:url value="/products/details/${prod.alias}"/>" class="stretched-link"></a>
                                                                <div class="text-center text-white mt-3 mb-3">
                                                                    <b>${prod.name}</b><br>
                                                                    <span class="roboto">${prod.brand}</span> <br> <br>
                                                                    <span class="roboto">${String.format("%.2f", prod.salePrice)}€</span>
                                                                </div>
                                                            </div>

                                                            </c:otherwise>
                                                        </c:choose>
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

</body>
</html>
