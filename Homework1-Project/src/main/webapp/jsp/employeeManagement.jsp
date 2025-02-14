<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 12/04/2022
  Time: 00:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="employee" type="it.unipd.dei.wa2122.wadteam.resources.Employee"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="Electromechanics Shop">
    <meta name="author" content="WAD Team">

    <title>Employee List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Employee List</h1>

<div>
    <a href="<c:url value="/management/employeeManagement/createEmployee"/>">
        Add new employee
    </a>
</div><br>

<table>
    <tr>
        <th>Username</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Role</th>
    </tr>

    <c:forEach var="employee" items="${employeeList}">
        <tr>
            <td>${employee.username}</td>
            <td>${employee.name}</td>
            <td>${employee.surname}</td>
            <td>${employee.role}</td>
            <td>
                <a href="<c:url value="/management/employeeManagement/editEmployee/${employee.username}"/>">
                    Edit
                </a>
            </td>

            <c:choose>
                <c:when test =  "${employee.role == 'Administrator'}">
                    <td>
                    </td>
                </c:when>
                <c:otherwise>
                    <td>
                        <a href="<c:url value="/management/employeeManagement/deleteEmployee/${employee.username}"/>" aria-disabled="false">
                            Delete
                        </a>
                    </td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
</table>

<%@ include file="/html/include/footer.html"%>
</body>
</html>