package it.unipd.dei.wa2122.wadteam.dao.ticketStatus;

import it.unipd.dei.wa2122.wadteam.resources.DateTime;
import it.unipd.dei.wa2122.wadteam.resources.TicketStatus;
import it.unipd.dei.wa2122.wadteam.resources.TicketStatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DeleteTicketStatusDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM ticket_status WHERE id = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The id of the ticket status
     */
    private final int id;

    public DeleteTicketStatusDatabase(final Connection con, final int id) {
        this.con = con;
        this.id = id;
    }


    public TicketStatus getTicketStatus() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the read ticketstatus
        TicketStatus resultTicketStatus = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultTicketStatus = new TicketStatus(
                        resultSet.getInt("id"),
                        TicketStatusEnum.valueOf(resultSet.getString("status")),
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
