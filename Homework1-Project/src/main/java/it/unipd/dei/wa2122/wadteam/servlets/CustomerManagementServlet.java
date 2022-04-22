package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.customer.DeleteCustomerByUsernameDatabase;
import it.unipd.dei.wa2122.wadteam.dao.customer.GetIdCustomerDatabase;
import it.unipd.dei.wa2122.wadteam.dao.customer.ListCustomerDatabase;
import it.unipd.dei.wa2122.wadteam.dao.customer.UpdateCustomerDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerManagementServlet extends AbstractDatabaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1, req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/') + 1) : "" : "";

        switch (path) {
            case "" -> getCustomerList(req, res);
            case "editCustomer" -> getEditCustomer(req, res, param);
            case "deleteCustomer" -> getDeleteCustomer(req, res, param);
            default -> writeError(req, res, GenericError.PAGE_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1, req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/') + 1) : "" : "";

        switch (path) {
            case "editCustomer" -> postEditCustomer(req, res, param);
            case "deleteCustomer" -> postDeleteCustomer(req, res, param);
            default -> writeError(req, res, GenericError.PAGE_NOT_FOUND);
        }
    }

    /**
     * show the list of all the customers in the database
     *
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    private void getCustomerList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            List<Customer> customerList = new ListCustomerDatabase(getDataSource().getConnection()).getCustomer();
            writeResource(req, res, "/jsp/customerManagement.jsp", false, customerList.toArray(Resource[]::new));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    /**
     * get editCustomer.jsp page to edit an existing customer
     *
     * @param req
     * @param res
     * @param param username of selected customer to confirm editing
     * @throws IOException
     * @throws ServletException
     */
    private void getEditCustomer(HttpServletRequest req, HttpServletResponse res, String param) throws IOException, ServletException {
        if (!param.equals("")) {
            try {
                Customer customer = new GetIdCustomerDatabase(getDataSource().getConnection(), param).getIdCustomer();
                writeResource(req, res, "/jsp/editCustomer.jsp", false, customer);
            } catch (SQLException e) {
                logger.error(e.getMessage());
                writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
            }
        } else {
            writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("last path parameter cannot be empty"));
        }
    }

    /**
     * edit an existing customer to the database
     *
     * @param req
     * @param res
     * @param param username of selected customer to edit
     * @throws IOException
     * @throws ServletException
     */
    private void postEditCustomer(HttpServletRequest req, HttpServletResponse res, String param) throws IOException, ServletException {
        if (!param.equals("")) {
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            String fiscalCode = req.getParameter("fiscalCode");
            String address = req.getParameter("address");
            String email = req.getParameter("email");
            String phoneNumber = req.getParameter("phoneNumber");
            try {
                Customer customer = new Customer(null, name, surname, fiscalCode, address, email, phoneNumber, param, null);
                customer = new UpdateCustomerDatabase(getDataSource().getConnection(), customer).updateCustomer();
                logger.info("Edit completed successfully for customer " + customer.toString());
                Message m = new Message("edit customer ok");
                writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") + "/management/customerManagement");
            } catch (SQLException e) {
                logger.error(e.getMessage());
                writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
            }
        } else {
            writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("last path parameter cannot be empty"));
        }
    }

    /**
     * get deleteCustomer.jsp page to confirm deletion of selected customer
     *
     * @param req
     * @param res
     * @param param username of selected customer to confirm deletion
     * @throws ServletException
     * @throws IOException
     */
    private void getDeleteCustomer(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException {
        if (!param.equals("")) {
            try {
                Customer customer = new GetIdCustomerDatabase(getDataSource().getConnection(), param).getIdCustomer();
                if (customer != null) {
                    writeResource(req, res, "/jsp/deleteCustomer.jsp", true, customer);
                } else {
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
     * delete selected customer from the database
     *
     * @param req
     * @param res
     * @param param username of selected customer to delete
     * @throws ServletException,
     * @throws IOException
     */
    private void postDeleteCustomer(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException {
        if (!param.equals("")) {
            try {
                Customer customer = new DeleteCustomerByUsernameDatabase((getDataSource().getConnection()), param).deleteCustomer();
                logger.info("Delete completed successfully for customer " + customer.toString());
                Message m = new Message("delete customer ok");
                writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") + "/management/customerManagement");
            } catch (SQLException e) {
                logger.error(e.getMessage());
                writeError(req, res, new ErrorMessage.DeleteCustomerError(e.getMessage()));
            }
        } else {
            writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("last path parameter cannot be empty"));
        }
    }
}
