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
<html>
<head>
    <title>Error <c:out value="${errorCode}"/></title>
</head>
<body>
<h1>Error <c:out value="${errorCode}"/></h1>
<hr />
<br />
<c:import url="/jsp/include/message.jsp"/>
</body>
</html>
