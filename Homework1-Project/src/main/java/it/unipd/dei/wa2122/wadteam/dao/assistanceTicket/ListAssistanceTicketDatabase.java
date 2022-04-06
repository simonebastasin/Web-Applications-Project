package it.unipd.dei.wa2122.wadteam.dao.assistanceTicket;

import it.unipd.dei.wa2122.wadteam.resources.AssistanceTicket;
import it.unipd.dei.wa2122.wadteam.resources.DateTime;
import it.unipd.dei.wa2122.wadteam.resources.TicketStatus;
import it.unipd.dei.wa2122.wadteam.resources.TicketStatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ListAssistanceTicketDatabase {
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
    public ListAssistanceTicketDatabase(final Connection con) {
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
    public List<AssistanceTicket> getAssistanceTicket() throws SQLException {

        PreparedStatement preparedStatement = null;
        PreparedStatement innerPreparedStatement = null;

        ResultSet resultSet = null;
        ResultSet innerResultSet = null;

        List<AssistanceTicket> resultAssistantTicket = new ArrayList<>();

        try {
            preparedStatement = con.prepareStatement(STATEMENT);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("ID");
                String description = resultSet.getString("Description");
                int idCustomer = resultSet.getInt("ID_Customer");
                String productAlias =  resultSet.getString("Product_Alias");

                innerPreparedStatement = con.prepareStatement(STATEMENT_STATUS);
                innerPreparedStatement.setInt(1, id);
                innerResultSet = innerPreparedStatement.executeQuery();

                List<TicketStatus> resultTicketStatus = new ArrayList<>();

                while (innerResultSet.next()) {

                    resultTicketStatus.add(new TicketStatus(innerResultSet.getInt("id"),
                            TicketStatusEnum.valueOf(innerResultSet.getString("status")),
                            innerResultSet.getString("description"),
                            new DateTime(innerResultSet.getObject("ts_Date", LocalDateTime.class)),
                            innerResultSet.getInt("id_Ticket"))
                    );
                }
                resultAssistantTicket.add(new AssistanceTicket(id,description,idCustomer, productAlias,resultTicketStatus));
                innerResultSet.close();
                innerPreparedStatement.close();
            }

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (innerResultSet != null) {
                innerResultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (innerPreparedStatement != null) {
                innerPreparedStatement.close();
            }
        }

        return resultAssistantTicket;
    }
}


