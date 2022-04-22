<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 31/03/2022
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="Electromechanics Shop">
    <meta name="author" content="WAD Team">

    <title>Upload Media | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<h1>Upload Media</h1>

<form method="post" action="<c:url value="/media/upload"/>" enctype="multipart/form-data">
    <input type="file" name="file" />
    <input type="submit" value="Upload" />
</form>

<%@ include file="/html/include/footer.html"%>
</body>
</html>