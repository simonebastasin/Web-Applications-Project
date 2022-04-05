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

<html>
<head>
    <title>Electromechanics shop</title>
</head>
<body>
    Product name: ${product.name}
    <ul>
        <li>Brand: ${product.brand}</li>
        <li>Description: ${product.description}</li>
        <li>Price: ${product.sale}</li>
        <li>Quantity: ${product.quantity}</li>
    </ul>
</body>
</html>
