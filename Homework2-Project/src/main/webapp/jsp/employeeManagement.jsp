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
    <c:import url="/jsp/include/head-management.jsp"/>

    <title>Employee List | Electromechanics Shop</title>
</head>

<body>
<c:import url="/jsp/include/header.jsp"/>

<div class="container main-container">
    <nav class="custom-breadcrumb-divider" aria-label="breadcrumb">
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

    <div class="card card-body">
        <table id="employeeTable" class="table table-hover table-hide-md-col-1 table-hide-sm-col-2" >
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Role</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody class="align-middle">
                <c:forEach var="employee" items="${employeeList}">
                    <tr data-id="${employee.username}">

                        <td>${employee.username}</td>
                        <td>${employee.name}</td>
                        <td>${employee.surname}</td>
                        <td>${employee.role}</td>
                        <td>
                            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#addEmployeeModal" data-id="${employee.username}">
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
                                    <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteEmployeeModal" data-id="${employee.username}">
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
                    <h5 class="modal-title" id="addEmployeeModalTitle">Add/Edit</h5>
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
                                <select class="form-select" name="role" id="role" required>
                                    <option value="" hidden selected disabled>Choose</option>
                                    <c:forEach var="role" items="${roleList}">
                                        <option value="${role.name}">
                                                ${role.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="col-form-label" id="password-label">Password:</label>
                            <input type="password" class="form-control" id="password" name="password" required/>
                            <input type="checkbox" onclick="showPassword()" id="password-checkbox"> <label class="col-form-label" id="password-checkbox-label"><i>Show password</i></label>
                        </div>

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" id="addEmployeeButton" form="addEmployeeForm">Add/Edit</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="deleteEmployeeModal" tabindex="-1" aria-labelledby="deleteEmployeeModal"
         aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false">
        <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg modal-fullscreen-md-down">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteEmployeeModalTitle">Delete</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div id="formAlertPlaceholderDelete" class="sticky-top"></div>
                <div class="modal-body">
                    <form id="deleteEmployeeForm" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label for="usernameDelete" class="col-form-label">Username:</label>
                            <input type="text" class="form-control" id="usernameDelete" name="username" required>
                        </div>
                        <div class="mb-3">
                            <label for="nameDelete" class="col-form-label">Name:</label>
                            <input type="text" class="form-control" id="nameDelete" name="name" required/>
                        </div>
                        <div class="mb-3">
                            <label for="surnameDelete" class="col-form-label">Surname:</label>
                            <input type="text" class="form-control" id="surnameDelete" name="surname" required/>
                        </div>
                        <div class="mb-3">
                            <label for="roleDelete" class="col-form-label">Role:</label>
                            <input type="text" class="form-control" id="roleDelete" name="role" required/>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-danger" id="deleteEmployeeButton" form="deleteEmployeeForm">Delete</button>
                </div>
            </div>
        </div>
    </div>

</div>
<c:import url="/jsp/include/footer-management.jsp"/>

<script type="text/javascript" src="<c:url value="/js/employee-management.js"/>"></script>
</body>
</html>