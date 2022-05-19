<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 12/04/2022
  Time: 05:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="onlineOrder" type="it.unipd.dei.wa2122.wadteam.resources.OnlineOrder"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Delete Order | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Delete Order</li>
  </ol>
</nav>

    <div class="mx-auto border rounded bg-white w-lg-50 p-3">

        <h5>Order you are deleting:</h5>

        <div class="row">
            <div class="col">ID Order:</div>
            <div class="col">${onlineOrder.idOrder}</div>
            <div class="col"></div>
        </div>
        <div class="row">
            <div class="col">Order Date:</div>
            <div class="col">${onlineOrder.ooDateTime.getHumanDate()} </div>
            <div class="col"></div>
        </div>
        <div class="row">
            <div class="col">Status:</div>
            <div class="col">${onlineOrder.status}</div>
            <div class="col"></div>
        </div>
        <div class="row">
            <div class="col">Description:</div>
            <div class="col">${onlineOrder.status.description}</div>
            <div class="col"></div>
        </div>



        <div class="card row m-3">
            <div class="card-header">
                <div class="row">
                    <div class="col">
                        Alias
                    </div>
                    <div class="col">
                        Name
                    </div>
                    <div class="col">
                        Brand
                    </div>
                    <div class="col">
                        Price
                    </div>
                    <div class="col">
                        Quantity
                    </div>
                </div>
            </div>
            <ul class="list-group list-group-flush">
                <c:forEach var="product" items="${onlineOrder.products}">
                    <li class="list-group-item row px-0">
                        <div class="col text-break">
                                ${product.alias}
                        </div>
                        <div class="col text-break">
                                ${product.name}
                        </div>
                        <div class="col text-break">
                                ${product.brand}
                        </div>
                        <div class="col text-break">
                                ${product.quantity}
                        </div>
                        <div class="col text-break">
                                ${product.salePrice}
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>


    </div>

    <h3 class="text-center">Sure to delete?</h3>


    <div class="d-flex justify-content-center">
    <form method="POST" action="">
        <input type="submit" value="Yes" class="btn btn-primary mx-3"/>
    </form>
    <a href="<c:url value="/management/orderManagement"/>">
        <input type="submit" value="No" class="btn btn-primary mx-3"/>
    </a>
    </div>




</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>