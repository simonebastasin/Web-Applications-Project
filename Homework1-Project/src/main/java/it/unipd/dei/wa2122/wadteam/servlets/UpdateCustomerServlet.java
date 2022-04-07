package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.assistanceTicket.GetAssistanceTicketDatabase;
import it.unipd.dei.wa2122.wadteam.dao.assistanceTicket.ListAssistanceTicketDatabase;
import it.unipd.dei.wa2122.wadteam.resources.AssistanceTicket;
import it.unipd.dei.wa2122.wadteam.resources.ErrorMessage;
import it.unipd.dei.wa2122.wadteam.resources.Message;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateCustomerServlet extends  AbstractDatabaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param=req.getParameter("username");
        System.out.println("doge");
    }
}
