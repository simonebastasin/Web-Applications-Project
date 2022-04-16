package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.assistanceTicket.CreateAssistanceTicketDatabase;
import it.unipd.dei.wa2122.wadteam.dao.assistanceTicket.GetAssistanceTicketDatabase;
import it.unipd.dei.wa2122.wadteam.dao.assistanceTicket.ListAssistanceTicketDatabase;
import it.unipd.dei.wa2122.wadteam.dao.assistanceTicket.ListAssistanceTicketFromUserDatabase;
import it.unipd.dei.wa2122.wadteam.dao.ticketStatus.CreateTicketStatusDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;


public class TicketServlet extends AbstractDatabaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "create" -> getCreateTicket(req, resp);
            case "list" -> getListTicket(req, resp);
            case "detail" -> getDetailTicket(req, resp, path, param);
            case "respond" -> getRespondTicket(req, resp, param);
            default ->  writeError(req, resp, GenericError.PAGE_NOT_FOUND);
        }

    }

    private void getCreateTicket(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        var ut = ((UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE)).getType();
        switch (ut) {
            case CUSTOMER -> writeJsp(req, resp, "/jsp/createTicket.jsp");
            default ->  writeError(req, resp, new ErrorMessage.NotLogin("not allowed"));
        }
    }

    private void getRespondTicket(HttpServletRequest req, HttpServletResponse resp, String param) throws IOException, ServletException {
        if(param.chars().allMatch( Character::isDigit ) && !param.equals("")) {
            var ut = ((UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE)).getType();
            switch (ut) {
                case EMPLOYEE -> writeJsp(req, resp, "/jsp/respondTicketStatus.jsp");
                default ->  writeError(req, resp, GenericError.UNAUTHORIZED);
            }
        }
    }

    private void getDetailTicket(HttpServletRequest req, HttpServletResponse resp, String path, String param) throws IOException, ServletException {
        if(param.chars().allMatch( Character::isDigit ) && !param.equals("")) {
            var ut = ((UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE)).getType();
            switch (ut) {
                case CUSTOMER -> {
                    int id = Integer.parseInt(path);

                    try {
                        AssistanceTicket assistanceTicket = new GetAssistanceTicketDatabase(getDataSource().getConnection(), id).getAssistanceTicket();
                        int user = ((UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE)).getId();
                        if(assistanceTicket.getId() == user)
                            writeResource(req, resp, "/jsp/ticket.jsp", true, assistanceTicket);
                        else
                            writeError(req, resp, new ErrorMessage.UserCredentialError("User error"));
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                        writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                    }

                }
                case EMPLOYEE -> {
                    int id = Integer.parseInt(path);

                    try {
                        AssistanceTicket assistanceTicket = new GetAssistanceTicketDatabase(getDataSource().getConnection(), id).getAssistanceTicket();
                        writeResource(req, resp, "/jsp/ticket.jsp", true, assistanceTicket);
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                        writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                    }
                }
                default ->  writeError(req, resp, GenericError.UNAUTHORIZED);
            }
        }
    }

    private void getListTicket(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            if (req.getSession(false) != null && req.getSession(false).getAttribute(USER_ATTRIBUTE) != null) {
                var ut = ((UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE)).getType();

                switch (ut) {
                    case CUSTOMER -> {
                        var listTicket = new ListAssistanceTicketFromUserDatabase(getDataSource().getConnection(), ((UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE)).getId()).getAssistanceTicketFromUser();
                        writeResource(req, resp, "/jsp/ticket.jsp", false, listTicket.toArray(AssistanceTicket[]::new));
                    }
                    case EMPLOYEE ->  {
                        var listTicket = new ListAssistanceTicketDatabase(getDataSource().getConnection()).getAssistanceTicket();
                        writeResource(req, resp, "/jsp/ticketRespond.jsp", false, listTicket.toArray(AssistanceTicket[]::new));
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
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "create" -> postCreateTicket(req, resp, param);
            case "detail" -> getDetailTicket(req, resp, path, param);
            case "respond" -> postRespondTicket(req, resp, param);
            default ->  writeError(req, resp, GenericError.PAGE_NOT_FOUND);
        }
    }

    private void postRespondTicket(HttpServletRequest req, HttpServletResponse resp, String param) throws IOException, ServletException {
        if (param.chars().allMatch(Character::isDigit) && !param.equals("")) {
            var ut = ((UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE)).getType();
            switch (ut) {
                case EMPLOYEE -> {
                    int id = Integer.parseInt(param);
                    TicketStatusEnum status = TicketStatusEnum.fromString(req.getParameter("status"));
                    String description = req.getParameter("description");

                    TicketStatus temp = new TicketStatus(null, status, description, null, id);

                    try {
                        TicketStatus ticketstatus = new CreateTicketStatusDatabase(getDataSource().getConnection(), temp).createTicketStatus();
                        AssistanceTicket assistanceTicket = new GetAssistanceTicketDatabase(getDataSource().getConnection(), id).getAssistanceTicket();

                        writeResource(req, resp, "/jsp/ticketRespond.jsp", false, assistanceTicket);
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                        writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
                    }

                }
                default ->  writeError(req, resp, GenericError.UNAUTHORIZED);
            }
        }
    }

    private void postCreateTicket(HttpServletRequest req, HttpServletResponse resp, String param) throws IOException, ServletException {
        int username = ((UserCredential) req.getSession(false).getAttribute(USER_ATTRIBUTE)).getId();
        String description = req.getParameter("description");

        AssistanceTicket temp = new AssistanceTicket(null, description, username, param, null);

        try {
            AssistanceTicket assistanceTicket = new CreateAssistanceTicketDatabase(getDataSource().getConnection(), temp).createAssistantTicket();
            writeResource(req, resp, "/jsp/ticket.jsp", false , assistanceTicket);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }
}
