<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 31/03/2022
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User <c:out value="${userCredentials.identification}"/></title>
</head>
<body>
<h1>User <c:out value="${userCredentials.identification}"/></h1>
<hr/>
<ul>
    <li>User <c:out value="${userCredentials.identification}"/></li>
    <li>Role <c:out value="${userCredentials.role}"/></li>
</ul>
</body>
</html>