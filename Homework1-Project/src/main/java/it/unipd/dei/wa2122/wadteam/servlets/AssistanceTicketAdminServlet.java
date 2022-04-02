package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.assistanceTicket.ListAssistanceTicketDatabase;
import it.unipd.dei.wa2122.wadteam.resources.AssistanceTicket;
import it.unipd.dei.wa2122.wadteam.resources.Message;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AssistanceTicketAdminServlet extends AbstractDatabaseServlet{

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ListAssistanceTicketDatabase assistanceTicket = null;
        List<AssistanceTicket> assistantTicketList = null;
        try{
            assistanceTicket = new ListAssistanceTicketDatabase(getDataSource().getConnection());
        }catch(SQLException e){

            writeError(req, resp, new Message("Error", "EV05", e.getMessage()),HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }

        try {
            assistantTicketList = assistanceTicket.getAssistanceTicket();
            writeResource(req, resp,"jsp/AssistantTicketAdmin.jsp" , assistantTicketList.toArray(AssistanceTicket[]::new));
        } catch(SQLException e){
            writeError(req, resp, new Message("Error", "EV05", e.getMessage()),HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

}
