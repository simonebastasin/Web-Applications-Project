package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.assistanceTicket.GetAssistanceTicketDatabase;
import it.unipd.dei.wa2122.wadteam.resources.AssistanceTicket;
import it.unipd.dei.wa2122.wadteam.resources.Message;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class TicketServlet extends AbstractDatabaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        writeResource(req, resp, "/jsp/getEmployee.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("identification"));

        try {
            AssistanceTicket assistantTicket = new GetAssistanceTicketDatabase(getDataSource().getConnection(), id).getAssistanceTicket();

            writeResource(req, resp, "/jsp/ticket.jsp",assistantTicket);
        } catch (SQLException e) {
            writeError(req, resp, new Message("Error get", "ET02", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }
}
