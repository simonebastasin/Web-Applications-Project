<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%--@elvariable id="product" type="it.unipd.dei.wa2122.wadteam.resources.Product"--%>
<%--@elvariable id="mediaList" type="java.util.List<it.unipd.dei.wa2122.wadteam.resources.Media>"--%>
<%--@elvariable id="media" type="it.unipd.dei.wa2122.wadteam.resources.Media"--%>

<html>
<head>
    <title>Electromechanics shop</title>
</head>
<body>

    <c:import url="/jsp/include/header.jsp"/>
    <h1>Edit product</h1>

    <form method="POST" action="">
        <label for="alias">Alias:</label><br>
        <input type="text" id="alias" name="alias" value="${product.alias}" disabled/><br>
        <label for="name">Name:</label><br>
        <input type="text" id="name" name="name" value="${product.name}"/><br>
        <label for="brand">Brand:</label><br>
        <input type="text" id="brand" name="brand" value="${product.brand}"/><br>
        <label for="description">Description:</label><br>
        <input type="text" id="description" name="description" value="${product.description}"/><br>
        <label for="purchase">Purchase price:</label><br>
        €<input type="number" min="0.01" step="0.01" max="2500" id="purchase" name="purchase" value="${product.purchasePrice}"/><br>
        <label for="sale">Sale price:</label><br>
        €<input type="number" min="0.01" step="0.01" max="2500" id="sale" name="sale" value="${product.salePrice}"/><br>
        <label for="quantity">Quantity:</label><br>
        <input type="number" min="0" step="1" id="quantity" name="quantity" value="${product.quantity}"/><br>
        <label for="category">Category:</label><br>
        <select  name="category" id="category"">
        <c:forEach var="cat" items="${categories}">
            <c:choose>
                <c:when test="${cat.name != product.category.name}">
                    <option value="${cat.name}">
                            ${cat.name}
                    </option>
                </c:when>
                <c:otherwise>
                    <option value="${cat.name}" selected>
                            ${cat.name}
                    </option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        </select>
        <a href="<c:url value="/management/productManagement/createCategory"/>">Add a new category</a>
        <div>
            Evidence:

            <c:choose>
                <c:when test="${product.evidence == true}">
                    <input type="radio" id="yes"
                           name="evidence" value="yes" checked>
                    <label for="yes">Yes</label>
                    <input type="radio" id="no"
                           name="evidence" value="no">
                    <label for="no">No</label>
                </c:when>
                <c:otherwise>
                    <input type="radio" id="yes"
                           name="evidence" value="yes">
                    <label for="yes">Yes</label>
                    <input type="radio" id="no"
                           name="evidence" value="no" checked>
                    <label for="no">No</label>
                </c:otherwise>
            </c:choose>
        </div>

        <div>
            <label for="media">Select one or more media (hold down ctrl/cmd to select multiple media):</label><br>
            <select name="media" id="media" multiple>
                <c:if test="${not empty media}">
                    <c:choose>
                        <c:when test="${fn:contains(product.pictures, media.id)}">
                            <option value="${media.id}" selected>${media.id}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${media.id}">${media.id}</option>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:forEach var="media" items="${mediaList}">
                    <c:choose>
                        <c:when test="${fn:contains(product.pictures, media.id)}">
                            <option value="${media.id}" selected>${media.id}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${media.id}">${media.id}</option>

                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            <br><br>
        </div>


        <input type="submit" value="Submit">
    </form>
    <%@ include file="/html/include/footer.html"%>
</body>
</html>
