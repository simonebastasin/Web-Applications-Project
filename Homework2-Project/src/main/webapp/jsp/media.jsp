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

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head-management.jsp"/>

    <title>Media Management | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
    <nav class="custom-breadcrumb-divider" aria-label="breadcrumb">
        <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
            <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
            <li class="breadcrumb-item active" aria-current="page">Media Management</li>
        </ol>
    </nav>
    <div id="liveAlertPlaceholder"></div>

    <div class="card p-3 no-highlight" id="dropArea">
        <div class="mask bg-info bg-opacity-50 border border-5 border-info rounded-3">
            <div class="d-flex justify-content-center align-items-center h-100">
                <p class="text-white mb-0 text-center"><i class="fa-solid fa-upload fa-5x"></i><br>Drag your files</p>
            </div>
        </div>
        <div class="image-check-box">
            <ul>
                <c:forEach var="item" items="${mediaList}">
                    <li><label><img src="<c:url value="/media/view"/>/${item.id}" alt="${item.filename}"/></label>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <form id="uploadImageForm" class="mb-3 needs-validation" novalidate>
            <label for="formFile" class="form-label">Upload image</label>
            <div class="input-group">
                <input class="form-control" type="file" name="file" id="formFile" required accept="image/*">
                <button type="submit" class="btn btn-outline-secondary">Upload</button>
            </div>
            <div class="progress mt-3 mb-3 d-none" id="uploadImageProgress">
                <div id="uploadImageProgressBar" class="progress-bar progress-bar-striped progress-bar-animated"
                     role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0"></div>
            </div>
        </form>
    </div>


</div>
<c:import url="/jsp/include/footer-management.jsp"/>
<script src="<c:url value="/js/media.js"/>"></script>
</body>
</html>
