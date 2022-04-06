<%--
  Created by IntelliJ IDEA.
  User: gioel
  Date: 05/04/2022
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="productCategory" type="ProductCategory"--%>

<html>
<head>
    <title>Electromechanics shop</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Category: ${productCategory.name}</h1>

${productCategory.description}
<hr />


</body>
</html>
