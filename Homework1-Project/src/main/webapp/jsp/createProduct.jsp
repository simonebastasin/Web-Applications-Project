<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="product" type="Product"--%>

<html>
<head>
    <title>Electromechanics shop</title>
</head>
<body>

    <c:import url="/jsp/include/header.jsp"/>
    <h1>Add product</h1>

    <form method="POST" action="">
        <label for="alias">Alias:</label><br>
        <input type="text" id="alias" name="alias"/><br>
        <label for="name">Name:</label><br>
        <input type="text" id="name" name="name"/><br>
        <label for="brand">Brand:</label><br>
        <input type="text" id="brand" name="brand"/><br>
        <label for="description">Description:</label><br>
        <input type="text" id="description" name="description"/><br>
        <label for="purchase">Purchase price:</label><br>
        €<input type="number" min="0.01" step="0.01" max="2500" value="25.67" id="purchase" name="purchase"/><br>
        <label for="sale">Sale price:</label><br>
        €<input type="number" min="0.01" step="0.01" max="2500" value="25.67" id="sale" name="sale"/><br>
        <label for="quantity">Quantity:</label><br>
        <input type="number" min="1" step="1" value="1" id="quantity" name="quantity"/><br>
        <label for="category">Category:</label><br>
        <select  name="category" id="category">
            <c:forEach var="cat" items="${categories}">
                <option value="${cat.name}">
                        ${cat.name}
                </option>
            </c:forEach>
        </select>
        <a href="<c:url value="/management/productManagement/createCategory"/>">Add a new category</a>
        <div>
            Evidence:
            <input type="radio" id="yes"
                   name="evidence" value="yes">
            <label for="yes">Yes</label>

            <input type="radio" id="no"
                   name="evidence" value="no" checked>
            <label for="no">No</label>
        </div>
        <input type="submit" value="Submit">
    </form>
    <%@ include file="/html/include/footer.html"%>
</body>
</html>
