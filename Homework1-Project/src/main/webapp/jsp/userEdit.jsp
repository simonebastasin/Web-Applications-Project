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
<form method="post" action="<c:url value="/user/EMPLOYEE/modify"/>">
    <ul>
        <li>Name: <input type="text" name="name" value="${employee.name}" required></li>
        <li>Surname: <input type="text" name="surname" value="${employee.surname}" required></li>

        <li>Role: <select name="role" autofocus required>
            <option value="notchange" selected>not change</option>
            <option value="Technician" >Technician</option>
            <option value="Seller" >Seller</option>
            <option value="Administrator" >Administrator</option>
            <option value="Accountant" >Accountant</option>
            <option value="Warehouse Manager" >Warehouse Manager</option>
        </select></li>

        <input type="hidden" value="${employee.username}" name="username">
        <input type="submit" value="submit">

    </ul>

</form>
<%@ include file="/html/include/footer.html"%>
</body>
</html>
