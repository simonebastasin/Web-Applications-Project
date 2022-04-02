package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.assistantTicket.ListAssistantTicketDatabase;
import it.unipd.dei.wa2122.wadteam.resources.AssistantTicket;
import it.unipd.dei.wa2122.wadteam.resources.Message;
import it.unipd.dei.wa2122.wadteam.resources.TicketStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AssistantTicketAdminServlet extends AbstractDatabaseServlet{

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ListAssistantTicketDatabase assistantTicket = null;
        List<AssistantTicket> assistantTicketList = null;
        try{
            assistantTicket = new ListAssistantTicketDatabase(getDataSource().getConnection());
        }catch(SQLException e){

            writeError(req, resp, new Message("Error", "EV05", e.getMessage()),HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }

        try {
            assistantTicketList = assistantTicket.getAssistantTicket();
            writeResource(req, resp,"jsp/AssistantTicketAdmin.jsp" , assistantTicketList.toArray(TicketStatus[]::new));
        } catch(SQLException e){
            writeError(req, resp, new Message("Error", "EV05", e.getMessage()),HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

}
