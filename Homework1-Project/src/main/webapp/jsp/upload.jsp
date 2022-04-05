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
    <meta charset="UTF-8">
    <title>Upload Media</title>
</head>
<body>
<h1>Login</h1>
<hr/>
<form method="post" action="<c:url value="/media/upload"/>" enctype="multipart/form-data">
    <input type="file" name="file" />
    <input type="submit" value="Upload" />
</form>

</body>
</html>