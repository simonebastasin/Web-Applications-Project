package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.onlineInvoice.*;
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
            case "list" -> getListInvoice(req, resp);
            case "detail" -> getDetailInvoice(req, resp, param);
            case "order" -> getDetailInvoiceFromOrderId(req, resp, param);
            default ->  writeError(req, resp, GenericError.PAGE_NOT_FOUND);
        }

    }

    /**
     * get detailInvoice.jsp page given an order id
     * @param req
     * @param resp
     * @param param
     * @throws ServletException
     * @throws IOException
     */
    private void getDetailInvoiceFromOrderId(HttpServletRequest req, HttpServletResponse resp, String param) throws IOException, ServletException {
        if(param.chars().allMatch( Character::isDigit ) && !param.equals("")) {
            var ut = ((UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE)).getType();
            switch (ut) {
                case CUSTOMER -> {
                    int id = Integer.parseInt(param);

                    try {
                        OnlineInvoice onlineInvoice = new GetOnlineInvoiceFromOrderDatabase(getDataSource().getConnection(), id).getOnlineInvoice();
                        if(onlineInvoice == null) writeError(req, resp, GenericError.PAGE_NOT_FOUND);
                        else {
                            var uid = ((UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE)).getId();

                            if (onlineInvoice.getIdOrder().getIdCustomer().equals(uid)) {
                                writeResource(req, resp, "/jsp/detailInvoice.jsp", true, onlineInvoice);
                            } else {
                                writeError(req, resp, GenericError.PAGE_NOT_FOUND);
                            }
                        }
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                        writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                    }

                }
                case EMPLOYEE -> {
                    int id = Integer.parseInt(param);

                    try {
                        OnlineInvoice onlineInvoice = new GetOnlineInvoiceFromOrderDatabase(getDataSource().getConnection(), id).getOnlineInvoice();
                        if(onlineInvoice == null) writeError(req, resp, GenericError.PAGE_NOT_FOUND);
                        else {
                            writeResource(req, resp, "/jsp/detailInvoice.jsp", true, onlineInvoice);
                        }
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                        writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                    }
                }
                default ->  writeError(req, resp, GenericError.UNAUTHORIZED);
            }
        }
    }

    private void getCreateInvoice(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        var ut = ((UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE)).getType();
        switch (ut) {
            case EMPLOYEE -> writeJsp(req, resp, "/jsp/createInvoice.jsp");
            default ->  writeError(req, resp, new ErrorMessage.NotLoggedInError("not allowed"));
        }
    }

    /**
     * get detailInvoice.jsp page
     * @param req
     * @param resp
     * @param param
     * @throws ServletException
     * @throws IOException
     */
    private void getDetailInvoice(HttpServletRequest req, HttpServletResponse resp, String param) throws IOException, ServletException {
        if(param.chars().allMatch( Character::isDigit ) && !param.equals("")) {
            var ut = ((UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE)).getType();
            switch (ut) {
                case CUSTOMER -> {
                    int id = Integer.parseInt(param);

                    try {
                        OnlineInvoice onlineInvoice = new GetOnlineInvoiceDatabase(getDataSource().getConnection(), id).getOnlineInvoice();
                        if(onlineInvoice == null) writeError(req, resp, GenericError.PAGE_NOT_FOUND);
                        else {
                            var uid = ((UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE)).getId();

                            if (onlineInvoice.getIdOrder().getIdCustomer().equals(uid)) {
                                writeResource(req, resp, "/jsp/detailInvoice.jsp", true, onlineInvoice);
                            } else {
                                writeError(req, resp, GenericError.PAGE_NOT_FOUND);
                            }
                        }
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                        writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                    }

                }
                case EMPLOYEE -> {
                    int id = Integer.parseInt(param);

                    try {
                        OnlineInvoice onlineInvoice = new GetOnlineInvoiceDatabase(getDataSource().getConnection(), id).getOnlineInvoice();
                        if(onlineInvoice == null) writeError(req, resp, GenericError.PAGE_NOT_FOUND);
                        else {
                            writeResource(req, resp, "/jsp/detailInvoice.jsp", true, onlineInvoice);
                        }
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                        writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                    }
                }
                default ->  writeError(req, resp, GenericError.UNAUTHORIZED);
            }
        }
    }

    /**
     * get invoice.jsp page
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void getListInvoice(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            if (req.getSession(false) != null && req.getSession(false).getAttribute(USER_ATTRIBUTE) != null) {
                var ut = ((UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE)).getType();

                switch (ut) {
                    case CUSTOMER -> {
                        var listInvoice = new ListOnlineInvoiceFromUserDatabase(getDataSource().getConnection(), ((UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE)).getId()).getOnlineInvoice();
                        writeResource(req, resp, "/jsp/invoice.jsp", false, listInvoice.toArray(OnlineInvoice[]::new));
                    }
                    case EMPLOYEE ->  {
                        var listInvoice = new ListOnlineInvoiceDatabase(getDataSource().getConnection()).getOnlineInvoice();
                        writeResource(req, resp, "/jsp/invoice.jsp", false, listInvoice.toArray(OnlineInvoice[]::new));
                    }
                    default ->  writeError(req, resp, GenericError.UNAUTHORIZED);
                }
            }
        }
        catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}