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
<form method="POST" action="<c:url value="/ticket/create"/>">
  <input type="text" name ="username" id="username">
  <label for = "username">username id</label>
  <br>
  <input type="text" name ="productalias" id="productalias">
  <label for = "productalias">product alias</label>
  <br>
  <textarea name ="description" id="description"> </textarea>
  <label for = "description">description</label>
  <br>

  <br>
  <input type ="submit" value = "create ticket">
</form>
</body>
</html>
