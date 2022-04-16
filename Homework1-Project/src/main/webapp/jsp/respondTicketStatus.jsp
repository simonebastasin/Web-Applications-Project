<%--
  Created by IntelliJ IDEA.
  User: GK10
  Date: 03/04/2022
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Respond Ticket Status</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>
<form method="POST" action="">
    <select name="status" id="status">
        <option value ="open">Open</option>
        <option value ="processing">Processing</option>
        <option value ="closed">Closed</option>
        <option value ="return">Return</option>
    </select>
    <br>
    <textarea name ="description" id="description"> </textarea>
    <label for = "description"><b> Description </b></label>
    <br>

    <br>
    <input type ="submit" value = "respond ticket status">
</form>
<%@ include file="/html/include/footer.html"%>
</body>
</html>
