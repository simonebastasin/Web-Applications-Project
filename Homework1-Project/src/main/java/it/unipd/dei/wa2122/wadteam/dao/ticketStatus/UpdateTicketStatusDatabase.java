package it.unipd.dei.wa2122.wadteam.dao.ticketStatus;

import it.unipd.dei.wa2122.wadteam.resources.DateTime;
import it.unipd.dei.wa2122.wadteam.resources.TicketStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UpdateTicketStatusDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "UPDATE ticket_status SET status = ?::ticketstatus, description = ? , ts_date = ?, id_ticket = ? WHERE id = ? ";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The ticket status of the ticket status
     */
    private TicketStatus ticketStatus;

    public UpdateTicketStatusDatabase(final Connection con, final TicketStatus ticketStatus) {
        this.con = con;
        this.ticketStatus = ticketStatus;
    }


    public TicketStatus updateTicketStatus() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the update ticket status
        TicketStatus resultTicketStatus = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1, ticketStatus.getStatus());
            preparedStatement.setString(2, ticketStatus.getDescription());
            preparedStatement.setObject(3, ticketStatus.getTsDate().getLocalDateTime());
            preparedStatement.setInt(4, ticketStatus.getIdTicket());
            preparedStatement.setInt(5, ticketStatus.getId());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultTicketStatus = new TicketStatus(
                        resultSet.getInt("id"),
                        resultSet.getString("status"),
                        resultSet.getString("description"),
                        new DateTime(resultSet.getObject("ts_Date", LocalDateTime.class)),
                        resultSet.getInt("id_Ticket")
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
