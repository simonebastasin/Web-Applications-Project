<%--
  Created by IntelliJ IDEA.
  User: simonebastasin
  Date: 12/04/2022
  Time: 00:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="employee" type="it.unipd.dei.wa2122.wadteam.resources.Employee"--%>
<%--@elvariable id="employeeList" type="java.util.List<it.unipd.dei.wa2122.wadteam.resources.Employee>"--%>
<%--@elvariable id="role" type="it.unipd.dei.wa2122.wadteam.resources.Role"--%>
<%--@elvariable id="roleList" type="java.util.List<it.unipd.dei.wa2122.wadteam.resources.Role>"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/jsp/include/head.jsp"/>

    <title>Employee List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>

<div class="container main-container">
    <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
      <ol class="breadcrumb bg-secondary bg-opacity-25 p-3 mt-3 rounded">
        <li class="breadcrumb-item"><a href="<c:url value="/"/>">Electromechanics Shop</a></li>
        <li class="breadcrumb-item active" aria-current="page">Employee List</li>
      </ol>
    </nav>
    <div id="liveAlertPlaceholder"></div>

    <div>
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addEmployeeModal">
            <i class="fa-solid fa-circle-plus"></i>
            Add new employee
        </button>
    </div><br>

    <div class="card">
        <table id="employeeTable" class="table table-hover">
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Role</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="employee" items="${employeeList}">
                    <tr id="${employee.username}">

                        <td>${employee.username}</td>
                        <td>${employee.name}</td>
                        <td>${employee.surname}</td>
                        <td>${employee.role}</td>
                        <td>
                            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#addEmployeeModal" data-bs-whatever="${employee.username}">
                                <i class="fa-solid fa-pen-to-square text-primary"></i>
                            </button>
                        </td>

                        <c:choose>
                            <c:when test="${employee.role=='Administrator'}">
                                <td>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteEmployeeModal" data-bs-whatever="${employee.username}">
                                        <i class="fa-solid fa-trash-can text-danger"></i>
                                    </button>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="modal fade" id="addEmployeeModal" tabindex="-1" aria-labelledby="addEmployeeModal" aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false" >
        <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg modal-fullscreen-md-down">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addEmployeeModalTitle">Add/Edit Employee</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div id="formAlertPlaceholder" class="sticky-top"></div>
                <div class="modal-body">
                    <form id="addEmployeeForm" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label for="username" class="col-form-label">Username:</label>
                            <input type="text" class="form-control" id="username" name="username" required/>
                        </div>
                        <div class="mb-3">
                            <label for="name" class="col-form-label">Name:</label>
                            <input type="text" class="form-control" id="name" name="name" required/>
                        </div>
                        <div class="mb-3">
                            <label for="surname" class="col-form-label">Surname:</label>
                            <input type="text" class="form-control" id="surname" name="surname" required/>
                        </div>

                        <div class="mb-3">
                            <label for="role">Role:</label>
                            <div class="input-group">
                                <select class="form-control" name="role" id="role" required>
                                    <option value="" hidden selected disabled>Choose</option>
                                    <c:forEach var="role" items="${roleList}">
                                        <option value="${role.name}">
                                                ${role.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" id="addEmployeeButton" form="addEmployeeForm">Edit employee</button>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="/jsp/include/footer-management.jsp"/>

<script type="text/javascript" src="<c:url value="/js/employee-management.js"/>"></script>
</body>
</html>