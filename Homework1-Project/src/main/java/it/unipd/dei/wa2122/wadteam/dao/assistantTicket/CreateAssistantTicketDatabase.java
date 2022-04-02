package it.unipd.dei.wa2122.wadteam.dao.assistantTicket;

import it.unipd.dei.wa2122.wadteam.resources.AssistantTicket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateAssistantTicketDatabase {

    /**
     * The SQL statement to be executed
     */

    private static final String STATEMENT = "INSERT INTO Assistance_Ticket (Description, ID_customer, Product_Alias) VALUES (?, ?, ?) RETURNING ID, Description, ID_Customer,Product_Alias";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The assistant ticket to be updated in the database
     */
    private final AssistantTicket assistantTicket;

    /**
     * Creates a new object for update an assistant ticket.
     *
     * @param con             the connection to the database.
     * @param assistantTicket the assistant ticket to be created in the database.
     */
    public CreateAssistantTicketDatabase(final Connection con, final AssistantTicket assistantTicket) {
        this.con = con;
        this.assistantTicket = assistantTicket;
    }

    /**
     * Creates an assistant ticket in the database.
     *
     * @return the {@code AssistantTicket} object matching the badge.
     * @throws SQLException if any error occurs while reading the assistant ticket .
     */
    public AssistantTicket createAssistantTicket() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the create assistant ticket
        AssistantTicket resultAssistantTicket = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1, assistantTicket.getDescription());
            preparedStatement.setInt(2, assistantTicket.getIdCustomer());
            preparedStatement.setString(3, assistantTicket.getProductAlias());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultAssistantTicket = new AssistantTicket(resultSet.getInt("ID"),
                        resultSet.getString("Description"),
                        resultSet.getInt("ID_Customer"),
                        resultSet.getString("Product_Alias"), null);
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
