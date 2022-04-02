package it.unipd.dei.wa2122.wadteam.dao.assistantTicket;

import it.unipd.dei.wa2122.wadteam.resources.AssistantTicket;
import it.unipd.dei.wa2122.wadteam.resources.Product;
import it.unipd.dei.wa2122.wadteam.resources.TicketStatus;

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
    private static final String STATEMENT_STATUS = "SELECT id, status, description, ts_date, id_ticket FROM ticket_status WHERE id_ticket = ?";

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
        ResultSet resultTicket= null;

        // the current employee
        AssistantTicket resultAssistantTicketItem = null;

        List<AssistantTicket> resultAssistantTicket = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("ID");
                String description = resultSet.getString("Description");
                int idCustomer = resultSet.getInt("ID_Customer");
                String productAlias =  resultSet.getString("Product_Alias");

                preparedStatement.close();

                preparedStatement = con.prepareStatement(STATEMENT_STATUS);
                preparedStatement.setInt(1, id);
                resultTicket = preparedStatement.executeQuery();
                List<TicketStatus> resultTicketStatus = new ArrayList<>();

                while (resultSet.next()) {

                    var resultTicketStatusItem = new TicketStatus(resultTicket.getInt("id"),
                            resultTicket.getString("status"),
                            resultTicket.getString("description"),
                            resultTicket.getString("ts_Date"),
                            resultTicket.getInt("idTicket")
                    );
                    resultTicketStatus.add(resultTicketStatusItem);
                }
                resultAssistantTicket.add(new AssistantTicket(id,description,idCustomer, productAlias,resultTicketStatus));

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


