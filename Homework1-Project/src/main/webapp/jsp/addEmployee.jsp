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

<h2>Add an employee</h2>

<form action="/action_page.php">
    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username"><br>
    <label for="name">Name:</label><br>
    <input type="text" id="name" name="name"><br>
    <label for="surname">Surname:</label><br>
    <input type="text" id="surname" name="surname"><br>
    <label for="role">Role:</label><br>
    <select  name="role" id="role">
        <option value="admin">Admin</option>
        <option value="sale">Sale</option>
    </select><br><br>

    <input type="submit" value="Submit">

</form>

<p>If you click the "Submit" button, the form-data will be sent to a page called "/action_page.php".</p>

</body>
</html>

