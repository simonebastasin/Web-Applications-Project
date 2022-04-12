package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.employee.CreateEmployeeDatabase;
import it.unipd.dei.wa2122.wadteam.dao.employee.DeleteEmployeeDatabase;
import it.unipd.dei.wa2122.wadteam.dao.employee.GetEmployeeDatabase;
import it.unipd.dei.wa2122.wadteam.dao.employee.ListEmployeeDatabase;
import it.unipd.dei.wa2122.wadteam.dao.role.ListRoleDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserManagementServlet extends AbstractDatabaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getParameter("employeeToDelete");

        switch (path) {
            case "" -> getEmployeeList(req,res);
            case "createEmployee" -> getCreateEmployee(req, res);
            case "deleteEmployee" -> getDeleteEmployee(req, res, param);
            default -> writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        String[] paths = req.getPathInfo() != null ? req.getPathInfo().substring(1).split("/") : null;
        String path = paths[0];
        String param = req.getParameter("employeeToDelete");

        switch (path) {
            case "createEmployee" -> postCreateEmployee(req, res,"param");
            case "deleteEmployee" -> postDeleteEmployee(req, res, param);
            default -> writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
        }
    }

    private void getEmployeeList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Employee> employeeList = null;
        try{
            employeeList = new ListEmployeeDatabase(getDataSource().getConnection()).getEmployee();
            List<Resource> lists = new ArrayList<>();
            for(var employee : employeeList){
                lists.add(employee);
            }
            writeResource(req, res, "/jsp/userManagement.jsp", false, lists.toArray(Resource[]::new));
        } catch (SQLException e) {
            Message m = new Message("Couldn't execute the query", "EU01", e.getMessage());
            writeError(req, res, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
        List<Role> roles = null;
        try {
            roles = new ListRoleDatabase(getDataSource().getConnection()).getRole();
            writeResource(req, res, "/jsp/createEmployee.jsp", false, roles.toArray(Resource[]::new));
        } catch (SQLException e) {
            Message m = new Message("Couldn't execute the query", "EU01", e.getMessage());
            writeError(req, res, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        writeJsp(req, res,"/jsp/createEmployee.jsp");
    }

    /**
     * add a new employee in the database
     * @param req
     * @param res
     * @param param
     * @throws IOException
     * @throws ServletException
     */
    private void postCreateEmployee(HttpServletRequest req, HttpServletResponse res, String param) throws IOException, ServletException {
        String username = req.getParameter("username");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        Role role = new Role(req.getParameter("role"));
        //Role role = new Role("Administrator", "Admin of the software who has all authorization and authentication.");
        String password = req.getParameter("password");

        Employee temp = new Employee(username,name,surname,role,password);
        try {
            Employee employee = new CreateEmployeeDatabase(getDataSource().getConnection(), temp).createEmployee();
            //writeResource(req, res, "/jsp/employeeDetail.jsp", true , product); //view result
            res.sendRedirect(req.getContextPath() + "/management/userManagement");
        } catch (SQLException e) {
            writeError(req, res, new Message("Error trying to create the employee", "ET02", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
        Employee employee = null;
        try {
            employee = new GetEmployeeDatabase(getDataSource().getConnection(), param).getEmployee();
            List<Employee> list = new ArrayList<>();
            list.add(employee);
            writeResource(req, res, "/jsp/deleteEmployee.jsp", false, list.toArray(Resource[]::new));
        } catch (SQLException e) {
            Message m = new Message("Couldn't execute the query", "EU01", e.getMessage());
            writeError(req, res, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
    private void postDeleteEmployee(HttpServletRequest req, HttpServletResponse res, String param) throws  ServletException, IOException {
        Employee employee = null;
        try {
            employee = new DeleteEmployeeDatabase((getDataSource().getConnection()), param).deleteEmployee();
            res.sendRedirect(req.getContextPath() + "/management/userManagement");
        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }
}

