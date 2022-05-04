<%--
  Created by IntelliJ IDEA.
  User: GK10
  Date: 03/04/2022
  Time: 14:40
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

    <title>Add new Ticket | Electromechanics Shop</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>
<div class="container main-container">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
    <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
    <li class="breadcrumb-item active" aria-current="page">Add new Ticket</li>
  </ol>
</nav>

<form method="POST" action="">
  <textarea name ="description" id="description"> </textarea>
  <label for = "description">description</label>
  <br>

  <br>
  <input type ="submit" value = "Submit">
</form>


</div>
<c:import url="/jsp/include/footer.jsp"/>
</body>
</html>
