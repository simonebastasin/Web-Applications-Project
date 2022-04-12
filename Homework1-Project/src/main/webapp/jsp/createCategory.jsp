<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="product" type="Product"--%>

<html>
<head>
    <title>Electromechanics shop</title>
</head>
<body>

    <c:import url="/jsp/include/header.jsp"/>
    <h1>Add a category</h1>

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
