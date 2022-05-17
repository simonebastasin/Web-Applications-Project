<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 31/03/2022
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Upload Media | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Upload Media</li>
  </ol>
</nav>

<form method="post" action="<c:url value="/media/upload"/>" enctype="multipart/form-data">
    <input type="file" name="file" accept="image/*"/>
    <input type="submit" value="Upload" />
</form>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>