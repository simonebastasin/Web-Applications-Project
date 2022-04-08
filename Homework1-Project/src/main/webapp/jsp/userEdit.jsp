<%--
  Created by IntelliJ IDEA.
  User: matte
  Date: 07/04/2022
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="employee" type="it.unipd.dei.wa2122.wadteam.resources.Employee"--%>

<html>
<head>
    <title>Employee ${employee.username}</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>
<form method="post" action="<c:url value="/user/CUSTOMER/modify/${employee.username}"/>">
    <ul>
        <li>Name: <input type="text" name="name" value="${employee.name}" required></li>
        <li>Surname: <input type="text" name="surname" value="${employee.surname}" required></li>

        <select name="role" autofocus required>
            <option value="noTchange" selected>not change</option>
            <c:choose>
                <c:when test='${employee.role.toString()}.equalsIgnoreCase("Technician")'>
                    <option value="Technician" selected>Technician</option>
                </c:when>
                <c:otherwise>
                    <option value="Technician" >Technician</option>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test='${employee.role.toString()}.equalsIgnoreCase("Technician")'>
                    <option value="Seller" selected>Seller</option>
                </c:when>
                <c:otherwise>
                    <option value="Seller" >Seller</option>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test='${employee.role.toString()}.equalsIgnoreCase("Administrator")'>
                    <option value="Administrator" selected>Administrator</option>
                </c:when>
                <c:otherwise>
                    <option value="Administrator" >Administrator</option>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test='${employee.role.toString()}.equalsIgnoreCase("Accountant")'>
                    <option value="Accountant" selected>Accountant</option>
                </c:when>
                <c:otherwise>
                    <option value="Accountant" >Accountant</option>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test='${employee.role.toString()}.equalsIgnoreCase("Warehouse Manager")'>
                    <option value="Warehouse Manager" selected>Warehouse Manager</option>
                </c:when>
                <c:otherwise>
                    <option value="Warehouse Manager" >Warehouse Manager</option>
                </c:otherwise>
            </c:choose>
        </select>

        <input type="hidden" value="${employee.username}" name="username">
        <input type="submit" value="submit">

    </ul>

</form>

</body>
</html>
