package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.customer.GetIdCustomerDatabase;
import it.unipd.dei.wa2122.wadteam.dao.customer.UpdateCustomerDatabase;
import it.unipd.dei.wa2122.wadteam.dao.employee.GetEmployeeDatabase;
import it.unipd.dei.wa2122.wadteam.resources.Customer;
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
       //todo controlli sui parametri

        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String [] param1=req.getPathInfo() != null ? req.getPathInfo().split("/"):null;
        String username="";
        if(param1.length>3)
            username=param1[3].trim();
        String type=param1[1];



        switch (param1[2]) {
            case "info" -> {
                System.out.println("ok");
                if ("EMPLOYEE".equals(type))
                {

                    Employee em=null;
                    try {
                         em=new GetEmployeeDatabase(getDataSource().getConnection(),username).getEmployee();


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    writeResource(req,resp,"/jsp/user.jsp",true,em);
                }
                else
                {
                    Customer cu=null;
                    try {

                        cu= new GetIdCustomerDatabase(getDataSource().getConnection(),username).getIdCustomer();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    writeResource(req,resp,"/jsp/CustomerDetail.jsp",true,cu);
                }
            }
            case "modify"->
                    {
                        System.out.println("ciao");
                        Customer cu=null;
                        try {

                            cu= new GetIdCustomerDatabase(getDataSource().getConnection(),username).getIdCustomer();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        writeResource(req,resp,"/jsp/customerEdit.jsp",true,cu);
                    }
            case "logout" -> writeJsp(req, resp, "/jsp/user.jsp"); // TODO change
            case "register" -> writeJsp(req, resp, "/jsp/user.jsp"); // TODO change
            default -> writeError(req, resp, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));

        }
        }
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            String[] param1 = req.getPathInfo() != null ? req.getPathInfo().split("/") : null;


            switch (param1[2]) {

                case "modify" -> {
                    System.out.println("ciao");
                    Customer cu = new Customer(null, req.getParameter("name"), req.getParameter("surname"), req.getParameter("fiscalCode"), req.getParameter("address"), req.getParameter("email"), req.getParameter("phoneNumber"), req.getParameter("username"),"ciao");
                    try {

                        cu = new UpdateCustomerDatabase(getDataSource().getConnection(), cu).updateCustomer();
                        cu=new GetIdCustomerDatabase(getDataSource().getConnection(),req.getParameter("username")).getIdCustomer();
                        System.out.println(req.getParameter("username"));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if(cu==null)
                        System.out.print("noooo");
                    writeResource(req, resp, "/jsp/CustomerDetail.jsp", true, cu);
                }
                case "logout" -> writeJsp(req, resp, "/jsp/user.jsp"); // TODO change
                case "register" -> writeJsp(req, resp, "/jsp/user.jsp"); // TODO change
                default -> writeError(req, resp, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));

            }
        }

}
