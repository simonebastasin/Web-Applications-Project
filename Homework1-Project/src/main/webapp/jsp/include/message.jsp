<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 31/03/2022
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="message" type="it.unipd.dei.wa2122.wadteam.resources.Message"--%>
<c:choose>
    <c:when test="${message.error}">
        <ul>
            <li>error code: <c:out value="${message.errorCode}"/></li>
            <li>message: <c:out value="${message.message}"/></li>
            <li>details: <c:out value="${message.errorDetails}"/></li>
        </ul>
    </c:when>
    <c:otherwise>
        <ul>
            <li>message: <c:out value="${message.message}"/></li>
            <c:choose>
                <c:when test="not empty ${message.resourceId}">
                    <li>resource id: <c:out value="${message.resourceId}"/></li>
                </c:when>
            </c:choose>
        </ul>
    </c:otherwise>
</c:choose>
