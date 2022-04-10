package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.employee.ListEmployeeDatabase;
import it.unipd.dei.wa2122.wadteam.resources.Employee;
import it.unipd.dei.wa2122.wadteam.resources.Message;
import it.unipd.dei.wa2122.wadteam.resources.Resource;
import it.unipd.dei.wa2122.wadteam.resources.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserManagmentServlet extends AbstractDatabaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        List<Employee> employeeListmployees = null;

        try{
            employeeListmployees = new ListEmployeeDatabase(getDataSource().getConnection()).getEmployee();

            List<Resource> lists = new ArrayList<>();
            for(var employee : employeeListmployees){
                lists.add(employee);
            }

            writeResource(req, res, "/jsp/UserManagment.jsp", false, lists.toArray(Resource[]::new));

        }catch (SQLException e) {
            Message m = new Message("Couldn't execute the query", "EU01", e.getMessage());
            writeError(req, res, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        this.doGet(req,res);
    }
}

