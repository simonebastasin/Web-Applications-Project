<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 31/03/2022
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="mediaList" type="java.util.List<Media>"--%>
<html>
<head>
    <title>View media | Electromechanics Shop</title>
</head>
<body>
    <c:import url="/jsp/include/header.jsp"/>
    <h1>List media</h1>

    <ul>
    <c:forEach var="item" items="${mediaList}">
        <li><img src="<c:url value="/media/view"/>/${item.id}" alt="${item.filename}" width="400px"/></li>
    </c:forEach>
    </ul>
    <%@ include file="/html/include/footer.html"%>
</body>
</html>
