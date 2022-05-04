<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 11/04/2022
  Time: 22:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Add new Employee | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Add new Employee</li>
  </ol>
</nav>

<form method="POST" action="">
    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username"><br>
    <label for="name">Name:</label><br>
    <input type="text" id="name" name="name"><br>
    <label for="surname">Surname:</label><br>
    <input type="text" id="surname" name="surname"><br>
    <label for="role">Role:</label><br>
    <select id="role" name="role">
        <c:forEach var="role" items="${roleList}">
            <option value="${role.name}">
                    ${role.name}
            </option>
        </c:forEach>
    </select><br>
    <label for="password">Password:</label><br>
    <input type="text" id="password" name="password"><br>
    <br>
    <input type="submit" value="Submit">
</form>

<a href="<c:url value="/management/employeeManagement"/>">
    Cancel changes
</a>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>