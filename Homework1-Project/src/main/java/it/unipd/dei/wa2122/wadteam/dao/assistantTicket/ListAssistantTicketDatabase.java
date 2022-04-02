package it.unipd.dei.wa2122.wadteam.dao.assistantTicket;

import it.unipd.dei.wa2122.wadteam.resources.AssistantTicket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListAssistantTicketDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM Assistance_Ticket";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Creates a new object for reading an assistant ticket.
     *
     * @param con
     *            the connection to the database.
     */
    public ListAssistantTicketDatabase(final Connection con) {
        this.con = con;
    }

    /**
     * Reads an asssistant ticket from the database.
     *
     * @return the {@code AssistantTicket} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the assistant ticket.
     */
    public List<AssistantTicket> getAssistantTicket() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the current employee
        AssistantTicket resultAssistantTicketItem = null;

        List<AssistantTicket> resultAssistantTicket = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                resultAssistantTicketItem = new AssistantTicket(resultSet.getInt("ID"),
                        resultSet.getString("Description"),
                        resultSet.getInt("ID_Customer"),
                        resultSet.getString("Product_Alias"));
                resultAssistantTicket.add(resultAssistantTicketItem);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return resultAssistantTicket;
    }
}


