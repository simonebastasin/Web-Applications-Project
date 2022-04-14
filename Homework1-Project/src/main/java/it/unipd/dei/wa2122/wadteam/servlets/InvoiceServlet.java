package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.onlineInvoice.CreateOnlineInvoiceDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineInvoice.GetOnlineInvoice;
import it.unipd.dei.wa2122.wadteam.dao.onlineInvoice.ListOnlineInvoiceDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineInvoice.ListOnlineInvoiceFromUserDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.GetOnlineOrderByIdDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class InvoiceServlet extends AbstractDatabaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "create" -> getCreateInvoice(req, resp);
            case "list" -> getListInvoice(req, resp);
            case "detail" -> getDetailInvoice(req, resp, path, param);
            default ->  writeError(req, resp, GenericError.PAGE_NOT_FOUND);
        }

    }

    private void getCreateInvoice(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        var ut = ((UserCredential) req.getSession(false).getAttribute("user")).getType();
        switch (ut) {
            case EMPLOYEE -> writeJsp(req, resp, "/jsp/createInvoice.jsp");
            default ->  writeError(req, resp, new ErrorMessage.NotLogin("not allowed"));
        }
    }

    private void getDetailInvoice(HttpServletRequest req, HttpServletResponse resp, String path, String param) throws IOException, ServletException {
        if(param.chars().allMatch( Character::isDigit ) && !param.equals("")) {
            var ut = ((UserCredential) req.getSession(false).getAttribute("user")).getType();
            switch (ut) {
                case CUSTOMER -> {
                    int id = Integer.parseInt(path);

                    try {
                        OnlineInvoice onlineInvoice = new GetOnlineInvoice(getDataSource().getConnection(), id).getOnlineInvoice();
                        int user = ((UserCredential) req.getSession(false).getAttribute("user")).getId();
                        if(onlineInvoice.getId() == user)
                            writeResource(req, resp, "/jsp/invoice.jsp", true, onlineInvoice);
                        else
                            writeError(req, resp, new ErrorMessage.UserCredentialError("User error"));
                    } catch (SQLException e) {
                        writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                    }

                }
                case EMPLOYEE -> {
                    int id = Integer.parseInt(path);

                    try {
                        OnlineInvoice onlineInvoice = new GetOnlineInvoice(getDataSource().getConnection(), id).getOnlineInvoice();
                        writeResource(req, resp, "/jsp/invoice.jsp", true, onlineInvoice);
                    } catch (SQLException e) {
                        writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                    }
                }
                default ->  writeError(req, resp, GenericError.UNAUTHORIZED);
            }
        }
    }

    private void getListInvoice(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            if (req.getSession(false) != null && req.getSession(false).getAttribute("user") != null) {
                var ut = ((UserCredential) req.getSession(false).getAttribute("user")).getType();

                switch (ut) {
                    case CUSTOMER -> {
                        var listInvoice = new ListOnlineInvoiceFromUserDatabase(getDataSource().getConnection(), ((UserCredential) req.getSession(false).getAttribute("user")).getId()).getOnlineInvoice();
                        writeResource(req, resp, "/jsp/invoice.jsp", false, listInvoice.toArray(AssistanceTicket[]::new));
                    }
                    case EMPLOYEE ->  {
                        var listInvoice = new ListOnlineInvoiceDatabase(getDataSource().getConnection()).getOnlineInvoice();
                        writeResource(req, resp, "/jsp/ticketRespond.jsp", false, listInvoice.toArray(AssistanceTicket[]::new));
                    }
                    default ->  writeError(req, resp, GenericError.UNAUTHORIZED);
                }
            }
        }
        catch (SQLException e) {
            writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "create" -> postCreateInvoice(req, resp,param);
            default ->  writeError(req, resp, GenericError.PAGE_NOT_FOUND);
        }
    }

    private void postCreateInvoice(HttpServletRequest req, HttpServletResponse resp,String param) throws IOException, ServletException {
        try {

            OnlineOrder onlineOrder = new GetOnlineOrderByIdDatabase(getDataSource().getConnection(), Integer.valueOf(param)).getOnlineOrderId();
            String transactionId = req.getParameter("transactionId");
            PaymentMethodOnlineEnum paymentType = PaymentMethodOnlineEnum.valueOf(req.getParameter("paymentType"));
            DateTime oiDate = new DateTime(LocalDateTime.now());
            double totalPrice = Double.valueOf(req.getParameter("totalPrice"));

            OnlineInvoice temp = new OnlineInvoice(null, onlineOrder,transactionId,paymentType,oiDate,totalPrice );

            OnlineInvoice onlineInvoice = new CreateOnlineInvoiceDatabase(getDataSource().getConnection(), temp).createOnlineInvoice();
            writeResource(req, resp, "/jsp/invoice.jsp", false , onlineInvoice);
        } catch (SQLException e) {
            writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

}