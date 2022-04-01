<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 31/03/2022
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="userCredential" type="it.unipd.dei.wa2122.wadteam.resources.UserCredential"--%>
<html>
<head>
    <title>User
    <c:out value="${userCredential.identification}"/></title>
</head>
<body>
<h1>User <c:out value="${userCredential.identification}"/></h1>
<hr/>
<ul>
    <li>User <c:out value="${userCredential.identification}"/></li>
    <li>Role <c:out value="${userCredential.role}"/></li>
</ul>
</body>
</html>