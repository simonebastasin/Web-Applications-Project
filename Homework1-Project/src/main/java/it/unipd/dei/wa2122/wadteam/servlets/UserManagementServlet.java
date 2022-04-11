package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.employee.DeleteEmployeeDatabase;
import it.unipd.dei.wa2122.wadteam.dao.employee.GetEmployeeDatabase;
import it.unipd.dei.wa2122.wadteam.dao.employee.ListEmployeeDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserManagementServlet extends AbstractDatabaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";


        switch (path){
            case "" -> getTable(req,res);
            case "delete" -> getDelete(req,res,param);
            //case "category" -> productCategory(req,res,param);
        }

    }

    private void getTable(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        List<Employee> employeeListmployees = null;

        try{
            employeeListmployees = new ListEmployeeDatabase(getDataSource().getConnection()).getEmployee();

            List<Resource> lists = new ArrayList<>();
            for(var employee : employeeListmployees){
                lists.add(employee);
            }

            writeResource(req, res, "/jsp/userManagement.jsp", false, lists.toArray(Resource[]::new));

        }catch (SQLException e) {
            Message m = new Message("Couldn't execute the query", "EU01", e.getMessage());
            writeError(req, res, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void getDelete(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException {

        Employee emp = null;

        try{
            emp = new GetEmployeeDatabase(getDataSource().getConnection(),param).getEmployee();
            List<Resource> list = new ArrayList<>();
            list.add(emp);
            writeResource(req, res, "/jsp/deleteEmployee.jsp", false, list.toArray(Resource[]::new));

        }catch (SQLException e) {
            Message m = new Message("Couldn't execute the query", "EU01", e.getMessage());
            writeError(req, res, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }




    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        String[] paths = req.getPathInfo() != null ? req.getPathInfo().substring(1).split("/") : null;

        assert paths != null;
        String path = paths[0];

        switch (path){
            case "delete" -> {
                String userName = paths[1];
                deleteUser(req,res, userName);
            }
            default -> writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));

        }
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse res, String userName) throws  ServletException, IOException {

        Employee employee = null;

        try {


            employee = new DeleteEmployeeDatabase((getDataSource().getConnection()), userName).deleteEmployee();
            res.sendRedirect(req.getContextPath() + "/management/userManagementServlet");


        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }


}

