<%--
  Created by IntelliJ IDEA.
  User: gioel
  Date: 05/04/2022
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="productCategoryList" type="java.util.List<ProductCategory>"--%>
<%--@elvariable id="productList" type="java.util.List<Product>"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Category: ${category.name} | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">

<c:forEach var="category" items="${productCategoryList}">

    <div class="d-flex align-items-baseline">
        <h1 class="title me-3"> ${category.name}</h1>
        <h3>${category.description}</h3>
    </div>



    <c:choose>
        <c:when test="${productList.size() > 0}">
            <c:forEach var="prod" items="${productList}">

                        <div class="mx-auto w-lg-75" >
                            <div class="card mt-3 bg-light">
                                <div class="row g-0 justify-content-center">
                                    <div class="col-md-4 ratio ratio-1x1 product-detail">
                                        <c:choose>
                                            <c:when test="${not empty prod.pictures}">

                                                <c:set var="i" value="${0}"/>
                                                <c:forEach var="pic" items="${prod.pictures}">
                                                    <c:if test="${i == 0}">
                                                        <c:set var="picture" value="${pic}"/>
                                                    </c:if>
                                                    <c:set var="i" value="${1}"/>
                                                </c:forEach>
                                                <img src="<c:url value="/media/view/${picture}"/>" alt="${prod.alias}" class="card-img" >

                                            </c:when>
                                            <c:otherwise>

                                                <img src="<c:url value="/images/No_image_available_circle.png"/>" alt="${prod.alias}" class="card-img" >

                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <a class="stretched-link" href="<c:url value="/products/details/${prod.alias}"/>"></a>
                                            <h2 class="title">${prod.name}</h2>
                                            <span class="roboto-thin text-primary">Brand: ${prod.brand}</span>
                                            <hr/>
                                            <div class="row card-text">
                                            <c:choose>
                                                <c:when test="${not empty prod.discount}">
                                                <div class="col">
                                                    Price:
                                                    <span  class="text-decoration-line-through">${String.format("%.2f",prod.salePrice)}€</span>
                                                    <span class="text-red display-6">${String.format("%.2f",prod.discountSale)}€</span>
                                                </div>
                                                <div class="col text-end">
                                                    -${prod.discount.percentage}% (until ${prod.discount.endDate.humanDate})
                                                </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="col">
                                                    Price:
                                                    <span class="display-6">
                                                    ${String.format("%.2f",prod.salePrice)}€
                                                    </span>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                            </div>
                                            <c:if test="${prod.quantity <= 10}">
                                                <div class="text-center mt-5 text-red bold">
                                                    Only ${prod.quantity} left in stock.
                                                </div>
                                            </c:if>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="row mx-auto justify-content-center border mt-3 mb-3 rounded bg-light">
                <div class="d-inline p-3">
                    <h3 class="title d-inline pe-3">${item.name}</h3>
                    <span>Sorry, there are no products at the moment for this category</span>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

</c:forEach>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
