<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="product" type="it.unipd.dei.wa2122.wadteam.resources.Product"--%>
<%--@elvariable id="media" type="it.unipd.dei.wa2122.wadteam.resources.Media"--%>
<%--@elvariable id="mediaList" type="java.util.List<it.unipd.dei.wa2122.wadteam.resources.Media>"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Add new Product | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Add new Product</li>
  </ol>
</nav>

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
               name="evidence" value="true">
        <label for="yes">Yes</label>

        <input type="radio" id="no"
               name="evidence" value="false" checked>
        <label for="no">No</label>
    </div>

    <div>
        <label for="pictures">Select one or more media (hold down ctrl/cmd to select multiple media):</label><br>
        <select name="pictures" id="pictures" multiple>
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


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
