<%--
  Created by IntelliJ IDEA.
  User: GK10
  Date: 03/04/2022
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Create Ticket</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>
<h1> Create Ticket </h1>
<hr>

<form method="POST" action="">
  <textarea name ="description" id="description"> </textarea>
  <label for = "description">description</label>
  <br>

  <br>
  <input type ="submit" value = "create ticket">
</form>
<%@ include file="/html/include/footer.html"%>
</body>
</html>
