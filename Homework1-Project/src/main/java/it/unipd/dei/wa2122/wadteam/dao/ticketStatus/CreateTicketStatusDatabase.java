package it.unipd.dei.wa2122.wadteam.dao.ticketStatus;

import it.unipd.dei.wa2122.wadteam.resources.TicketStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateTicketStatusDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "INSERT INTO ticket_status (status, description, ts_date, id_ticket) VALUES (?, ?, ?, ?) RETURNING id, status, description, ts_date, id_ticket";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The ticket status of the ticket status
     */
    private TicketStatus ticketStatus;

    public CreateTicketStatusDatabase(final Connection con, final TicketStatus ticketStatus) {
        this.con = con;
        this.ticketStatus = ticketStatus;
    }


    public TicketStatus createTicketStatus() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the create ticketstatus
        TicketStatus resultTicketStatus = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1, ticketStatus.getStatus());
            preparedStatement.setString(2, ticketStatus.getDescription());
            preparedStatement.setString(3, ticketStatus.getTsDate());
            preparedStatement.setInt(4, ticketStatus.getIdTicket());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultTicketStatus = new TicketStatus(
                        resultSet.getInt("id"),
                        resultSet.getString("status"),
                        resultSet.getString("description"),
                        resultSet.getString("tsDate"),
                        resultSet.getInt("idTicket")
                );
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return resultTicketStatus;
    }
}
