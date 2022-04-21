package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.employee.*;
import it.unipd.dei.wa2122.wadteam.dao.role.ListRoleDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagementServlet extends AbstractDatabaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "" -> getEmployeeList(req,res);
            case "createEmployee" -> getCreateEmployee(req, res);
            case "editEmployee" -> getEditEmployee(req, res, param);
            case "deleteEmployee" -> getDeleteEmployee(req, res, param);
            default -> writeError(req, res, GenericError.PAGE_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "createEmployee" -> postCreateEmployee(req, res);
            case "editEmployee" -> postEditEmployee(req, res, param);
            case "deleteEmployee" -> postDeleteEmployee(req, res, param);
            default -> writeError(req, res, GenericError.PAGE_NOT_FOUND);
        }
    }

    /**
     * show the list of all the employees in the database
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    private void getEmployeeList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Employee> employeeList;
        try {
            employeeList = new ListEmployeeDatabase(getDataSource().getConnection()).getEmployee();
            writeResource(req, res, "/jsp/employeeManagement.jsp", false, employeeList.toArray(Resource[]::new));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    /**
     * get createEmployee.jsp page to create a new employee
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    private void getCreateEmployee(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        List<Role> roleList;
        try {
            roleList = new ListRoleDatabase(getDataSource().getConnection()).getRole();
            writeResource(req, res, "/jsp/createEmployee.jsp", false, roleList.toArray(Resource[]::new));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
        //writeJsp(req, res,"/jsp/createEmployee.jsp");
    }

    /**
     * add a new employee to the database
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    private void postCreateEmployee(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String username = req.getParameter("username");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        Role role = new Role(req.getParameter("role"));
        String password = req.getParameter("password");

        Employee employee = new Employee(username, name, surname, role, password);
        try {
            employee = new CreateEmployeeDatabase(getDataSource().getConnection(), employee).createEmployee();
            //writeResource(req, res, "/jsp/employeeDetail.jsp", true , product); //view result
            Message m = new Message("create ok");
            writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") + "/management/employeeManagement");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    /**
     * get editEmployee.jsp page to edit an existing employee
     * @param req
     * @param res
     * @param param     username of selected employee to confirm editing
     * @throws IOException
     * @throws ServletException
     */
    private void getEditEmployee(HttpServletRequest req, HttpServletResponse res, String param) throws IOException, ServletException {
        Employee employee;
        List<Role> roleList;
        List<Resource> lists = new ArrayList<>();
        if (!param.equals("")) {
            try {
                roleList = new ListRoleDatabase(getDataSource().getConnection()).getRole();
                employee = new GetEmployeeDatabase(getDataSource().getConnection(), param).getEmployee();
                if(employee != null) {
                    lists.add(employee);
                    lists.addAll(roleList);
                    writeResource(req, res, "/jsp/editEmployee.jsp", false, lists.toArray(roleList.toArray(Resource[]::new)));
                }
                else {
                    writeError(req, res, GenericError.PAGE_NOT_FOUND);
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
                writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
            }
        } else {
            writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("last path parameter cannot be empty"));
        }
    }

    /**
     * edit an existing employee to the database
     * @param req
     * @param res
     * @param param     username of selected employee to edit
     * @throws IOException
     * @throws ServletException
     */
    private void postEditEmployee(HttpServletRequest req, HttpServletResponse res, String param) throws IOException, ServletException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        Role role = new Role(req.getParameter("role"));

        Employee employee;
        try {
            employee = new Employee(param, name, surname, role, null);
            employee = new UpdateEmployeeDatabase(getDataSource().getConnection(), employee).updateEmployee();
            //writeResource(req, res, "/jsp/employeeDetail.jsp", true , product); //view result
            Message m = new Message("edit ok");
            writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") + "/management/employeeManagement");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    /**
     * get deleteEmployee.jsp page to confirm deletion of selected employee
     * @param req
     * @param res
     * @param param     username of selected employee to confirm deletion
     * @throws ServletException
     * @throws IOException
     */
    private void getDeleteEmployee(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException {
        Employee employee;
        if (!param.equals("")) {
            try {
                employee = new GetEmployeeDatabase(getDataSource().getConnection(), param).getEmployee();
                if(employee != null) {
                    writeResource(req, res, "/jsp/deleteEmployee.jsp", true, employee);
                }
                else {
                    writeError(req, res, GenericError.PAGE_NOT_FOUND);
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
                writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
            }
        } else {
            writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("last path parameter cannot be empty"));
        }
    }

    /**
     * delete selected employee from the database
     * @param req
     * @param res
     * @param param     username of selected employee to delete
     * @throws ServletException,
     * @throws IOException
     */
    private void postDeleteEmployee(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException {
        Employee employee;
        try {
            employee = new DeleteEmployeeDatabase((getDataSource().getConnection()), param).deleteEmployee();
            Message m = new Message("delete ok");
            writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") + "/management/employeeManagement");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }
}