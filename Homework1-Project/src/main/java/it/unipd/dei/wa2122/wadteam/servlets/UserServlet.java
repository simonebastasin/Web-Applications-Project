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

        if (req.getSession(false) != null && req.getSession(false).getAttribute(USER_ATTRIBUTE) != null) {
            UserCredential us = (UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE);
            String username = us.getIdentification();
            TypeUserEnum ut = us.getType();

            switch (path) {
                case "info" -> {
                    switch (ut) {
                        case EMPLOYEE -> {
                            Employee em;
                            try {
                                em = new GetEmployeeDatabase(getDataSource().getConnection(), username).getEmployee();
                                writeResource(req, resp, "/jsp/user.jsp", true, em);
                            } catch (SQLException e) {
                                logger.error(e.getMessage());
                                writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                            }
                        }
                        case CUSTOMER -> {
                            Customer cu;
                            try {
                                cu = new GetIdCustomerDatabase(getDataSource().getConnection(), username).getIdCustomer();
                                writeResource(req, resp, "/jsp/customerDetail.jsp", true, cu);
                            } catch (SQLException e) {
                                logger.error(e.getMessage());
                                writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                            }
                        }
                        default -> writeError(req, resp, new ErrorMessage.NotLoggedInError("not allowed"));
                    }
                }
                case "modify" -> {
                    switch (ut) {
                        case CUSTOMER -> {
                            Customer cu;
                            try {
                                cu = new GetIdCustomerDatabase(getDataSource().getConnection(), username).getIdCustomer();
                                writeResource(req, resp, "/jsp/customerEdit.jsp", true, cu);
                            } catch (SQLException e) {
                                logger.error(e.getMessage());
                                writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                            }
                        }
                        case EMPLOYEE -> {
                            Employee em;
                            try {
                                em = new GetEmployeeDatabase(getDataSource().getConnection(), username).getEmployee();
                                writeResource(req, resp, "/jsp/userEdit.jsp", true, em);
                            } catch (SQLException e) {
                                logger.error(e.getMessage());
                                writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                            }
                        }
                        default -> writeError(req, resp, GenericError.UNAUTHORIZED);
                    }
                }
                case "password" -> {
                    switch (ut) {
                        case CUSTOMER -> {
                            Customer cu;
                            try {
                                cu = new GetIdCustomerDatabase(getDataSource().getConnection(), username).getIdCustomer();
                                writeResource(req, resp, "/jsp/changePassword.jsp", true, cu);
                            } catch (SQLException e) {
                                logger.error(e.getMessage());
                                writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                            }
                        }
                        case EMPLOYEE -> {
                            Employee em;
                            try {
                                em = new GetEmployeeDatabase(getDataSource().getConnection(), username).getEmployee();
                                writeResource(req, resp, "/jsp/changePassword.jsp", true, em);

                            } catch (SQLException e) {
                                logger.error(e.getMessage());
                                writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                            }
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

        if (req.getSession(false) != null && req.getSession(false).getAttribute(USER_ATTRIBUTE) != null) {
            UserCredential us = (UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE);
            String username = us.getIdentification();
            TypeUserEnum ut = us.getType();

            switch (path) {
                case "modify" -> {
                    switch (ut) {
                        case CUSTOMER -> {

                            Customer cu = new Customer(us.getId(), req.getParameter("name"), req.getParameter("surname"), req.getParameter("fiscalCode"), req.getParameter("address"), us.getEmail(), req.getParameter("phoneNumber"), username, null);
                            try {
                                cu = new UpdateCustomerDatabase(getDataSource().getConnection(), cu).updateCustomer();

                            } catch (SQLException e) {
                                logger.error(e.getMessage());
                                writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));

                            }
                            if (cu == null)
                                writeError(req,resp,GenericError.SERVER_ERROR);
                            else writeResource(req, resp, "/jsp/customerDetail.jsp", true, cu);
                        }
                        case EMPLOYEE -> {
                            Employee emNew;
                            try {
                                Employee emOld = new GetEmployeeDatabase(getDataSource().getConnection(), username).getEmployee();

                                    emNew = new Employee(emOld.getUsername(), req.getParameter("name"), req.getParameter("surname"), emOld.getRole());

                                Employee em = new UpdateEmployeeDatabase(getDataSource().getConnection(), emNew).updateEmployee();
                                writeResource(req, resp, "/jsp/user.jsp", true, em);
                            } catch (SQLException e) {
                                logger.error(e.getMessage());
                                writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                            }

                        }
                        default -> writeError(req, resp, GenericError.UNAUTHORIZED);
                    }
                }
                case "password" -> {
                    switch (ut) {
                        case CUSTOMER -> {
                            try {
                                int result = new UpdatePasswordCustomerDatabase(getDataSource().getConnection(), req.getParameter("oldPassword"), req.getParameter("newPassword"), username).updatePassword();
                                if (result == 0)
                                    writeError(req, resp, new ErrorMessage.ChangePasswordError("old password wrong"));
                                else {
                                    Message m = new Message("password changed");
                                    writeResource(req, resp, "/jsp/message.jsp", true, m);
                                }
                            } catch (SQLException e) {
                                logger.error(e.getMessage());
                                writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                            }
                        }
                        case EMPLOYEE -> {
                            try {
                                int result = new UpdatePasswordEmployeeDatabase(getDataSource().getConnection(), req.getParameter("oldPassword"), req.getParameter("newPassword"), username).updatePassword();
                                if (result == 0)
                                    writeError(req, resp, new ErrorMessage.ChangePasswordError("old password wrong"));
                                else {
                                    Message m = new Message("password changed");
                                    writeResource(req, resp, "/jsp/message.jsp", true, m);
                                }
                            } catch (SQLException e) {
                                logger.error(e.getMessage());
                                writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                            }
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

