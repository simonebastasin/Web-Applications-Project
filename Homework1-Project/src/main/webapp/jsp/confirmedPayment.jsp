<%--
  Created by IntelliJ IDEA.
  User: gioel
  Date: 08/04/2022
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="productList" type="java.util.List<Product>"--%>
<%--@elvariable id="onlineOrderList" type="java.util.List<OnlineOrder>"--%>

<html>
<head>
    <title>Electromechanics Shop</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>

<c:forEach var="prod" items="${productList}">
    <c:forEach var="order" items="${onlineOrderList}">

        <h1>Product purchased: ${prod.name}</h1>

        You have just successfully completed your purchase. <br>
        Check out your <a href="<c:url value="/order/detail/${order.idOrder}"/>">order.</a>

    </c:forEach>
</c:forEach>
</body>
</html>
