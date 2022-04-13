<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 12/04/2022
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="employee" type="it.unipd.dei.wa2122.wadteam.resources.Employee"--%>

<html>
<head>
    <title>Edit Employee</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Edit employee</h1>

<form method="POST" action="">
    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username" value="${employee.username}" readonly><br>
    <label for="name">Name:</label><br>
    <input type="text" id="name" name="name" value="${employee.name}"><br>
    <label for="surname">Surname:</label><br>
    <input type="text" id="surname" name="surname" value="${employee.surname}"><br>
    <label for="role">Role:</label><br>
    <select id="role" name="role" selected="${employee.role}">
        <c:forEach var="role" items="${roleList}">
            <option value="${role.name}">
                    ${role.name}
            </option>
        </c:forEach>
    </select><br>
    <label for="password">Password:</label><br>
    <input type="text" id="password" name="password" value="${employee.surname}" readonly><br>
    <input type="submit" value="Submit">
</form>
<%@ include file="/html/include/footer.html"%>
</body>
</html>