<%--
  Created by IntelliJ IDEA.
  User: GK10
  Date: 02/04/2022
  Time: 10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Ticket</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>

<form method="POST" action="<c:url value="/ticket"/>">
    <input type="text" name ="identification" id="identification">
    <label for = "identification">employee id</label>
    <br>
    <br>
    <input type ="submit" value = "login">
</form>
<%@ include file="/html/include/footer.html"%>
</body>
</html>
