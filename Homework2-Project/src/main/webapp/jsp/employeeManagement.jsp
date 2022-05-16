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
    <c:import url="/jsp/include/head.jsp"/>

    <title>Employee List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Employee List</li>
  </ol>
</nav>

<div>
    <a href="<c:url value="/management/employeeManagement/createEmployee"/>">
        Add new employee
    </a>
</div><br>

    <table class="table">
        <thead>
            <tr>
                <th>Username</th>
                <th>Name</th>
                <th>Surname</th>
                <th>Role</th>
            </tr>
        </thead>
        <tbody>
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
        </tbody>
</table>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>