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

import java.io.IOException;
import java.sql.SQLException;

public class UserServlet extends AbstractDatabaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/') + 1) : "" : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/') + 1) : "" : "";

        if (req.getSession(false) != null && req.getSession(false).getAttribute("user") != null) {
            UserCredential us = (UserCredential) req.getSession(false).getAttribute("user");
            String username = us.getIdentification();
            UserCredential.TypeUser ut = us.getType();

            switch (path) {
                case "info" -> {
                    switch (ut) {
                        case EMPLOYEE -> {
                            Employee em = null;
                            try {
                                em = new GetEmployeeDatabase(getDataSource().getConnection(), username).getEmployee();


                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            writeResource(req, resp, "/jsp/user.jsp", true, em);
                        }
                        case CUSTOMER -> {
                            Customer cu = null;
                            try {

                                cu = new GetIdCustomerDatabase(getDataSource().getConnection(), username).getIdCustomer();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            writeResource(req, resp, "/jsp/CustomerDetail.jsp", true, cu);

                        }
                        default -> writeError(req, resp, new ErrorMessage.NotLogin("not allowed"));
                    }
                }
                case "modify" -> {
                    switch (ut) {
                        case CUSTOMER -> {
                            Customer cu = null;
                            try {

                                cu = new GetIdCustomerDatabase(getDataSource().getConnection(), username).getIdCustomer();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            writeResource(req, resp, "/jsp/customerEdit.jsp", true, cu);
                        }
                        case EMPLOYEE -> {
                            Employee em = null;
                            try {

                                em = new GetEmployeeDatabase(getDataSource().getConnection(), username).getEmployee();
                                System.out.println(em.getUsername());


                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            writeResource(req, resp, "/jsp/userEdit.jsp", true, em);

                        }
                        default -> writeError(req, resp, GenericError.UNAUTHORIZED);
                    }
                }
                case "password" -> {
                    switch (ut) {
                        case CUSTOMER -> {
                            Customer cu = null;
                            try {

                                cu = new GetIdCustomerDatabase(getDataSource().getConnection(), username).getIdCustomer();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            writeResource(req, resp, "/jsp/changePassword.jsp", true, cu);
                        }
                        case EMPLOYEE -> {
                            Employee em = null;
                            try {


                                em = new GetEmployeeDatabase(getDataSource().getConnection(), username).getEmployee();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            writeResource(req, resp, "/jsp/changePassword.jsp", true, em);
                        }
                        default -> writeError(req, resp, GenericError.UNAUTHORIZED);
                    }
                }
                default -> writeError(req, resp, GenericError.PAGE_NOT_FOUND);
            }
        } else {
            writeError(req, resp, GenericError.UNAUTHORIZED);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/') + 1) : "" : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/') + 1) : "" : "";

        if (req.getSession(false) != null && req.getSession(false).getAttribute("user") != null) {
            UserCredential us = (UserCredential) req.getSession(false).getAttribute("user");
            String username = us.getIdentification();
            UserCredential.TypeUser ut = us.getType();

            switch (path) {
                case "modify" -> {
                    switch (ut) {
                        case CUSTOMER -> {

                            Customer cu = new Customer(null, req.getParameter("name"), req.getParameter("surname"), req.getParameter("fiscalCode"), req.getParameter("address"), req.getParameter("email"), req.getParameter("phoneNumber"), username, "ciao");
                            try {

                                cu = new UpdateCustomerDatabase(getDataSource().getConnection(), cu).updateCustomer();
                                cu = new GetIdCustomerDatabase(getDataSource().getConnection(), username).getIdCustomer();
                                System.out.println(req.getParameter("username"));

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            if (cu == null)
                                System.out.print("noooo");
                            writeResource(req, resp, "/jsp/CustomerDetail.jsp", true, cu);
                        }
                        case EMPLOYEE -> {
                            Employee emNew = null;
                            try {
                                Employee emOld = new GetEmployeeDatabase(getDataSource().getConnection(), username).getEmployee();
                                String role = req.getParameter("role");
                                System.out.println("Matteo");
                                if ("notchange".equals(role))
                                    emNew = new Employee(req.getParameter("username"), req.getParameter("name"), req.getParameter("surname"), emOld.getRole());
                                else
                                    emNew = new Employee(req.getParameter("username"), req.getParameter("name"), req.getParameter("surname"), new Role(req.getParameter("role")));
                                Employee em = new UpdateEmployeeDatabase(getDataSource().getConnection(), emNew).updateEmployee();
                                writeResource(req, resp, "/jsp/user.jsp", true, emNew);
                            } catch (SQLException e) {
                                writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                            }

                        }
                        default -> writeError(req, resp, GenericError.UNAUTHORIZED);
                    }
                }
                case "password" -> {
                    switch (ut) {
                        case CUSTOMER -> {
                            Customer cu = null;
                            try {
                                System.out.println("imin");
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
                        case EMPLOYEE -> {
                            Employee em = null;
                            try {

                                em = new GetEmployeeDatabase(getDataSource().getConnection(), username).getEmployee();
                                int result = new UpdatePasswordEmployeeDatabase(getDataSource().getConnection(), req.getParameter("oldPassword"), req.getParameter("newPassword"), username).updatePassword();
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
                        default -> writeError(req, resp, GenericError.UNAUTHORIZED);
                    }
                }
                default -> writeError(req, resp, GenericError.PAGE_NOT_FOUND);
            }
        } else {
            writeError(req, resp, GenericError.UNAUTHORIZED);
        }
    }
}

