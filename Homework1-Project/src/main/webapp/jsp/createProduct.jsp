<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="product" type="it.unipd.dei.wa2122.wadteam.resources.Product"--%>
<%--@elvariable id="media" type="it.unipd.dei.wa2122.wadteam.resources.Media"--%>
<%--@elvariable id="mediaList" type="java.util.List<it.unipd.dei.wa2122.wadteam.resources.Media>"--%>

<html>
<head>
    <title>Electromechanics shop</title>
</head>
<body>

    <c:import url="/jsp/include/header.jsp"/>
    <h1>Add product</h1>

    <form method="POST" action="">
        <label for="alias">Alias:</label><br>
        <input type="text" id="alias" name="alias" required/><br>
        <label for="name">Name:</label><br>
        <input type="text" id="name" name="name" required/><br>
        <label for="brand">Brand:</label><br>
        <input type="text" id="brand" name="brand" required/><br>
        <label for="description">Description:</label><br>
        <input type="text" id="description" name="description"/><br>
        <label for="purchase">Purchase price:</label><br>
        €<input type="number" min="0.01" step="0.01" max="2500" placeholder="10.00" id="purchase" name="purchase" required/><br>
        <label for="sale">Sale price:</label><br>
        €<input type="number" min="0.01" step="0.01" max="2500" placeholder="10.00" id="sale" name="sale" required/><br>
        <label for="quantity">Quantity:</label><br>
        <input type="number" min="1" step="1" placeholder="1" id="quantity" name="quantity" required/><br>
        <label for="category">Category:</label><br>
        <select  name="category" id="category" required>
            <option value=""  hidden selected disabled>Choose</option>
            <c:forEach var="cat" items="${categories}">
                <option value="${cat.name}">
                        ${cat.name}
                </option>
            </c:forEach>
        </select>
        <a href="<c:url value="/management/productManagement/createCategory"/>">Add new category</a>
        <div>
            Evidence:
            <input type="radio" id="yes"
                   name="evidence" value="yes">
            <label for="yes">Yes</label>

            <input type="radio" id="no"
                   name="evidence" value="no" checked>
            <label for="no">No</label>
        </div>

        <div>
            <label for="media">Select one or more media (hold down ctrl/cmd to select multiple media):</label><br>
            <select name="media" id="media" multiple>
                <c:if test="${not empty media}">
                    <option value="${media.id}">${media.id}</option>
                </c:if>
                <c:forEach var="media" items="${mediaList}">
                    <option value="${media.id}">${media.id}</option>
                </c:forEach>
            </select>
            <br><br>
        </div>


        <input type="submit" value="Submit">
    </form>
    <%@ include file="/html/include/footer.html"%>
</body>
</html>
