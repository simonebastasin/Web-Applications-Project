<%--
  Created by IntelliJ IDEA.
  User: pasto
  Date: 02/04/2022
  Time: 19:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="onlineOrderList" type="java.util.List<OnlineOrder>"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Your Order List | Electromechanics Shop</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Your Order List</li>
  </ol>
</nav>

<c:forEach var="order" items="${onlineOrderList}">
    <div class="mx-auto" style="width: 600px;">
        <div class="card text-center mt-3 mb-3">
            <div class="card-header text-end">
                ID ORDER # ${order.idOrder} <br>
                <a href="<c:url value="/order/detail"/>/${order.idOrder}" class="card-link mt-5">View Order Details</a>
            </div>
            <ul class="list-group list-group-flush">
                <c:forEach var="prod" items="${order.products}">
                    <li class="list-group-item">
                        <h5 class="card-title">${prod.name}</h5>
                            Price: ${prod.salePrice}€ <br>
                            Quantity: ${prod.quantity}
                    </li>

                </c:forEach>
            </ul>
            <div class="card-body">
                <div class="row justify-content-start">
                    <div class="col">
                        Date: <br>
                            ${order.ooDateTime.getHumanDate()}
                    </div>
                    <div class="col">
                        Status: <br>
                            ${order.status.status.text}
                    </div>
                    <div class="col">
                        Total price: <br>
                            ${order.getTotalPrice()}€
                    </div>
                </div>
            </div>
        </div>
    </div>


</c:forEach>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
