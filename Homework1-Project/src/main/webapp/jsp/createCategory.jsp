<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="product" type="Product"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="Electromechanics Shop">
    <meta name="author" content="WAD Team">

    <title>Add new Category | Electromechanics Shop</title>
</head>
<body>

<c:import url="/jsp/include/header.jsp"/>
<h1>Add new Category</h1>

<form method="POST" action="">
    <label for="name">Name:</label><br>
    <input type="text" id="name" name="name"><br>
    <label for="description">Description:</label><br>
    <input type="text" id="description" name="description"><br><br>

    <input type="submit" value="Submit">
</form>

<%@ include file="/html/include/footer.html"%>
</body>
</html>
