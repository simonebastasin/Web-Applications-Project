package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.customer.GetIdCustomerDatabase;
import it.unipd.dei.wa2122.wadteam.dao.customer.UpdateCustomerDatabase;
import it.unipd.dei.wa2122.wadteam.dao.customer.UpdatePasswordCustomerDatabase;
import it.unipd.dei.wa2122.wadteam.dao.employee.GetEmployeeDatabase;
import it.unipd.dei.wa2122.wadteam.dao.employee.UpdateEmployeeDatabase;
import it.unipd.dei.wa2122.wadteam.dao.employee.UpdatePasswordEmployeeDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserServlet extends AbstractDatabaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //todo controlli sui parametri

        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String [] param1=req.getPathInfo() != null ? req.getPathInfo().split("/"):null;
        String username="";
        if(param1==null || param1.length<2) {
            writeError(req, resp, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
            return;
            }
        String action=param1[2];
        String type=param1[1];
        UserCredential us=(UserCredential)req.getSession(false).getAttribute("user");
        username=us.getIdentification();





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
                        if("CUSTOMER".equals(type)) {
                            Customer cu = null;
                            try {

                                cu = new GetIdCustomerDatabase(getDataSource().getConnection(), username).getIdCustomer();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            writeResource(req, resp, "/jsp/customerEdit.jsp", true, cu);
                        }
                        else
                        {
                            Employee em=null;
                            try {

                               em=new GetEmployeeDatabase(getDataSource().getConnection(),username).getEmployee();
                               System.out.println(em.getUsername());


                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            writeResource(req, resp, "/jsp/userEdit.jsp", true, em);
                        }
                    }
            case "password" -> {
                if("CUSTOMER".equals(type)) {
                    Customer cu = null;
                    try {

                        cu = new GetIdCustomerDatabase(getDataSource().getConnection(), username).getIdCustomer();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    writeResource(req, resp, "/jsp/changePassword.jsp", true, cu);
                }
                else
                {
                    Employee em=null;
                    try {


                        em=new GetEmployeeDatabase(getDataSource().getConnection(),username).getEmployee();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    writeResource(req, resp, "/jsp/changePassword.jsp", true, em);
                }
            }
            case "register" -> writeJsp(req, resp, "/jsp/user.jsp"); // TODO change
            default -> writeError(req, resp, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));

        }
        }
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            String[] param1 = req.getPathInfo() != null ? req.getPathInfo().split("/") : null;
            String username="";
            UserCredential us=(UserCredential)req.getSession(false).getAttribute("user");
            username=us.getIdentification();

            switch (param1[2]) {

                case "modify" -> {
                    if("CUSTOMER".equals(param1[1]))
                    {
                    System.out.println("ciao");
                    Customer cu = new Customer(null, req.getParameter("name"), req.getParameter("surname"), req.getParameter("fiscalCode"), req.getParameter("address"), req.getParameter("email"), req.getParameter("phoneNumber"), req.getParameter("username"),"ciao");
                    try {

                        cu = new UpdateCustomerDatabase(getDataSource().getConnection(), cu).updateCustomer();
                        cu=new GetIdCustomerDatabase(getDataSource().getConnection(),username).getIdCustomer();
                        System.out.println(req.getParameter("username"));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if(cu==null)
                        System.out.print("noooo");
                    writeResource(req, resp, "/jsp/CustomerDetail.jsp", true, cu);
                }
                    else
                    {
                        Employee emNew=null;
                        try {
                            Employee emOld=new GetEmployeeDatabase(getDataSource().getConnection(),username).getEmployee();
                            String role=req.getParameter("role");
                            System.out.println("Matteo");
                            if("notchange".equals(role))
                                emNew=new Employee(req.getParameter("username"),req.getParameter("name"),req.getParameter("surname"),emOld.getRole());
                            else
                                emNew=new Employee(req.getParameter("username"),req.getParameter("name"),req.getParameter("surname"),new Role(req.getParameter("role")));
                            Employee em=new UpdateEmployeeDatabase(getDataSource().getConnection(),emNew).updateEmployee();
                            writeResource(req, resp, "/jsp/user.jsp", true, emNew);
                        } catch (SQLException e) {
                            writeError(req,resp,new ErrorMessage.SqlInternalError(e.getMessage()));
                        }

                    }
                }

                case "password" -> {
                    if("CUSTOMER".equals(param1[1])) {
                        Customer cu = null;
                        try {

                            cu = new GetIdCustomerDatabase(getDataSource().getConnection(), username).getIdCustomer();
                            int result = new UpdatePasswordCustomerDatabase(getDataSource().getConnection(), req.getParameter("oldPassword"), req.getParameter("newPassword"), username).updatePassword();
                            if (result == 0)
                                writeError(req, resp, new ErrorMessage.ChangePasswordError("old password wrong"));
                            else {
                                Message m = new Message("password changed");
                                writeResource(req, resp, "/jsp/message.jsp", true, m);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        writeResource(req, resp, "/jsp/CustomerDetail.jsp", true, cu);
                    }
                    else
                    {
                        Employee em=null;
                        try {

                            em= new GetEmployeeDatabase(getDataSource().getConnection(),username).getEmployee();
                            int result=new UpdatePasswordEmployeeDatabase(getDataSource().getConnection(),req.getParameter("oldPassword"),req.getParameter("newPassword"),username).updatePassword();
                            if (result == 0)
                                writeError(req, resp, new ErrorMessage.ChangePasswordError("old password wrong"));
                            else {
                                Message m = new Message("password changed");
                                writeResource(req, resp, "/jsp/message.jsp", true, m);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        writeResource(req, resp, "/jsp/user.jsp", true, em);
                    }

                }
                case "register" -> writeJsp(req, resp, "/jsp/user.jsp"); // TODO change
                default -> writeError(req, resp, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));

            }
        }

}
