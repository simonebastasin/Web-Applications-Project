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

import static it.unipd.dei.wa2122.wadteam.resources.UserCredential.TypeUser.CUSTOMER;
import static it.unipd.dei.wa2122.wadteam.resources.UserCredential.TypeUser.EMPLOYEE;

public class TicketServlet extends AbstractDatabaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "create" ->   writeJsp(req, resp, "/jsp/createTicket.jsp");
            case "list" -> getListTicket(req, resp);
            case "detail" -> getDetailTicket(req, resp, path, param);
            case "respond" -> {
                if(param.chars().allMatch( Character::isDigit ) && !param.equals("")) {
                    writeJsp(req, resp, "/jsp/respondTicketStatus.jsp");
                }
            }
            default -> writeError(req, resp, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
        }

    }

    private void getDetailTicket(HttpServletRequest req, HttpServletResponse resp, String path, String param) throws IOException, ServletException {
        if(param.chars().allMatch( Character::isDigit ) && !param.equals("")) {
            var ut = ((UserCredential) req.getSession(false).getAttribute("user")).getType();
            switch (ut) {
                case CUSTOMER -> {
                    int id = Integer.parseInt(path);

                    try {
                        AssistanceTicket assistanceTicket = new GetAssistanceTicketDatabase(getDataSource().getConnection(), id).getAssistanceTicket();

                        writeResource(req, resp, "/jsp/ticket.jsp", true, assistanceTicket);
                    } catch (SQLException e) {
                        writeError(req, resp, new Message("Error get", "ET02", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }

                }
                case EMPLOYEE -> writeError(req, resp, new ErrorMessage.UserCredentialError("User credential error"));
            }
        }
    }

    private void getListTicket(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            if (req.getSession(false) != null && req.getSession(false).getAttribute("user") != null) {
                var ut = ((UserCredential) req.getSession(false).getAttribute("user")).getType();

                switch (ut) {
                    case CUSTOMER -> {
                        var listTicket = new ListAssistanceTicketFromUserDatabase(getDataSource().getConnection(), ((UserCredential) req.getSession(false).getAttribute("user")).getId()).getAssistanceTicketFromUser();
                        writeResource(req, resp, "/jsp/ticket.jsp", false, listTicket.toArray(AssistanceTicket[]::new));
                    }
                    case EMPLOYEE ->  {
                        var listTicket = new ListAssistanceTicketDatabase(getDataSource().getConnection()).getAssistanceTicket();
                        writeResource(req, resp, "/jsp/ticket.jsp", false, listTicket.toArray(AssistanceTicket[]::new));
                    }
                    default ->  writeError(req, resp, new ErrorMessage.UserCredentialError("User credential error"));
                }
            }
        }
        catch (SQLException e) {
            writeError(req, resp, new Message("Error get", "ET02", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "create" -> postCreateTicket(req, resp, param);
            case "detail" -> postDetailTicket(req, resp, param);
            case "respond" -> postRespondTicket(req, resp, param);
            default -> writeError(req, resp, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
        }
    }

    private void postRespondTicket(HttpServletRequest req, HttpServletResponse resp, String param) throws IOException, ServletException {
        if (param.chars().allMatch(Character::isDigit) && !param.equals("")) {
            var ut = ((UserCredential) req.getSession(false).getAttribute("user")).getType();
            switch (ut) {
                case EMPLOYEE -> {
                    int id = Integer.parseInt(req.getParameter("identification"));

                    try {
                        AssistanceTicket assistanceTicket = new GetAssistanceTicketDatabase(getDataSource().getConnection(), id).getAssistanceTicket();

                        writeResource(req, resp, "/jsp/ticket.jsp", true, assistanceTicket);
                    } catch (SQLException e) {
                        writeError(req, resp, new Message("Error get", "ET02", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }

                }
                case CUSTOMER -> writeError(req, resp, new ErrorMessage.UserCredentialError("User credential error"));
            }
        }
    }

    private void postDetailTicket(HttpServletRequest req, HttpServletResponse resp, String param) throws IOException, ServletException {
        if (param.chars().allMatch(Character::isDigit) && !param.equals("")) {
            var ut = ((UserCredential) req.getSession(false).getAttribute("user")).getType();
            switch (ut) {
                case CUSTOMER -> {
                    int idTicket = Integer.parseInt(param);
                    String status = String.valueOf(req.getParameter("status"));
                    String description = req.getParameter("description");

                    TicketStatus temp = new TicketStatus(null, TicketStatusEnum.valueOf(status), description, null, idTicket);
                    try {
                        TicketStatus ticketstatus = new CreateTicketStatusDatabase(getDataSource().getConnection(), temp).createTicketStatus();
                        Message m = new Message("Ticket Status create", ticketstatus.getId());
                        writeResource(req, resp, "jsp/message.jsp", true, m);
                    } catch (SQLException e) {
                        writeError(req, resp, new Message("Error ticket status", "ET02", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                }
                case EMPLOYEE -> writeError(req, resp, new ErrorMessage.UserCredentialError("User credential error"));
            }
        }
    }

    private void postCreateTicket(HttpServletRequest req, HttpServletResponse resp, String param) throws IOException, ServletException {
        int username = ((UserCredential) req.getSession(false).getAttribute("user")).getId();
        String description = req.getParameter("description");

        AssistanceTicket temp = new AssistanceTicket(null, description, username, param, null);

        try {
            AssistanceTicket assistanceTicket = new CreateAssistanceTicketDatabase(getDataSource().getConnection(), temp).createAssistantTicket();
            writeResource(req, resp, "/jsp/ticket.jsp", true , assistanceTicket);
        } catch (SQLException e) {
            writeError(req, resp, new Message("Error create ticket", "ET02", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
