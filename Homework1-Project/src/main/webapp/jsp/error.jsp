<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 31/03/2022
  Time: 18:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="errorCode" type="Integer"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="Electromechanics Shop">
    <meta name="author" content="WAD Team">

    <title>Error <c:out value="${errorCode}"/> | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Error <c:out value="${errorCode}"/></h1>

<br>
<c:import url="/jsp/include/message.jsp"/>

<%@ include file="/html/include/footer.html"%>
</body>
</html>
