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
<body>

<h2>Add a product</h2>

<form action="/action_page.php">
    <label for="alias">Alias:</label><br>
    <input type="text" id="alias" name="alias"><br>
    <label for="name">Name:</label><br>
    <input type="text" id="name" name="name"><br>
    <label for="brand">Brand:</label><br>
    <input type="text" id="brand" name="brand"><br>
    <label for="description">Description:</label><br>
    <input type="text" id="description" name="description"><br>
    <label for="purchasePrice">Purchase price:</label><br>
    €<input type="number" min="0.01" step="0.01" max="2500" value="25.67" id="purchasePrice" name="purchasePrice"/><br>
    <label for="salePrice">Sale price:</label><br>
    €<input type="number" min="0.01" step="0.01" max="2500" value="25.67" id="salePrice" name="salePrice"/><br>
    <label for="category">Category:</label><br>
    <select  name="category" id="category">
        <option value="drills">drills</option>
        <option value="screwdrivers">screwdrivers</option>
        <option value="hammers">hammers</option>
    </select><br>
    <label for="category">Evidence:</label><br>
    <input type="radio" id="evidence" name="evidence"><br>
    <input type="submit" value="Submit">

</form>

<p>If you click the "Submit" button, the form-data will be sent to a page called "/action_page.php".</p>

</body>
</html>
