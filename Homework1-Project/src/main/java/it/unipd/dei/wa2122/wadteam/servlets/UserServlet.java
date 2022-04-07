package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.employee.GetEmployeeDatabase;
import it.unipd.dei.wa2122.wadteam.resources.Employee;
import it.unipd.dei.wa2122.wadteam.resources.ErrorMessage;
import it.unipd.dei.wa2122.wadteam.resources.UserCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class UserServlet extends AbstractDatabaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String [] param1=req.getPathInfo() != null ? req.getPathInfo().split("/"):null;
        String username=param1[2].trim();
        String type=param1[3];
        System.out.println(param1[1]);


        switch (param1[1]) {
            case "info" -> {
                System.out.println("ok");
                if ("EMPLOYEE".equals(type))
                {
                    System.out.println(username);
                    Employee em=null;
                    try {
                         em=new GetEmployeeDatabase(getDataSource().getConnection(),username).getEmployee();

                        System.out.println(em.getSurname());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    writeResource(req,resp,"/jsp/user.jsp",true,em);
                }
                else
                {

                }
            }

            case "logout" -> writeJsp(req, resp, "/jsp/user.jsp"); // TODO change
            case "register" -> writeJsp(req, resp, "/jsp/user.jsp"); // TODO change
            default -> writeError(req, resp, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));

        }
    }
}
