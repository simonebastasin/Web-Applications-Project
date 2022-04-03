package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.assistanceTicket.CreateAssistanceTicketDatabase;
import it.unipd.dei.wa2122.wadteam.dao.assistanceTicket.GetAssistanceTicketDatabase;
import it.unipd.dei.wa2122.wadteam.dao.assistanceTicket.ListAssistanceTicketDatabase;
import it.unipd.dei.wa2122.wadteam.dao.ticketStatus.CreateTicketStatusDatabase;
import it.unipd.dei.wa2122.wadteam.resources.AssistanceTicket;
import it.unipd.dei.wa2122.wadteam.resources.Message;
import it.unipd.dei.wa2122.wadteam.resources.TicketStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TicketServlet extends AbstractDatabaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ?  req.getPathInfo().substring(1) : "";
        String[] paths = path.split("/");
        if(path.equals("create")) {
            writeResource(req, resp, "/jsp/createTicket.jsp");

        } else if(path.equals("list")) {
            try {
                var listTicket = new ListAssistanceTicketDatabase(getDataSource().getConnection()).getAssistanceTicket();
                writeResource(req, resp, "/jsp/ticket.jsp", listTicket.toArray(AssistanceTicket[]::new));
            } catch (SQLException e) {
                writeError(req, resp, new Message("Error get", "ET02", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if(path.chars().allMatch( Character::isDigit ) && !path.equals("")) {
            int id = Integer.parseInt(path);

            try {
                AssistanceTicket assistanceTicket = new GetAssistanceTicketDatabase(getDataSource().getConnection(), id).getAssistanceTicket();

                writeResource(req, resp, "/jsp/ticket.jsp", assistanceTicket);
            } catch (SQLException e) {
                writeError(req, resp, new Message("Error get", "ET02", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if(paths.length == 2 && paths[0].chars().allMatch( Character::isDigit )) {
            writeResource(req, resp, "/jsp/respondTicketStatus.jsp");

        }
        else {
            writeResource(req, resp, "/jsp/getEmployee.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ?  req.getPathInfo().substring(1) : "";
        String[] paths = path.split("/");
        if(path.equals("create")) {
            int username = Integer.parseInt(req.getParameter("username"));
            String productalias = req.getParameter("productalias");
            String description = req.getParameter("description");

            AssistanceTicket temp = new AssistanceTicket(null, description, username, productalias, null);

            try {
                AssistanceTicket assistanceTicket = new CreateAssistanceTicketDatabase(getDataSource().getConnection(), temp).createAssistantTicket();
                writeResource(req, resp, "/jsp/ticket.jsp", assistanceTicket);
            } catch (SQLException e) {
                writeError(req, resp, new Message("Error create ticket", "ET02", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if(paths.length == 2 && paths[0].chars().allMatch( Character::isDigit )){
            int idTicket = Integer.parseInt(paths[0]);
            String status = req.getParameter("status");
            String description = req.getParameter("description");

            TicketStatus temp = new TicketStatus(null, status, description,null, idTicket);
            try {
                TicketStatus ticketstatus = new CreateTicketStatusDatabase(getDataSource().getConnection(), temp).createTicketStatus();
            } catch (SQLException e) {
                writeError(req, resp, new Message("Error ticket status", "ET02", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
        else {
            int id = Integer.parseInt(req.getParameter("identification"));

            try {
                AssistanceTicket assistanceTicket = new GetAssistanceTicketDatabase(getDataSource().getConnection(), id).getAssistanceTicket();

                writeResource(req, resp, "/jsp/ticket.jsp", assistanceTicket);
            } catch (SQLException e) {
                writeError(req, resp, new Message("Error get", "ET02", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        }
    }
}
