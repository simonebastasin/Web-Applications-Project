package it.unipd.dei.wa2122.wadteam.dao.ticketStatus;

import it.unipd.dei.wa2122.wadteam.resources.TicketStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetTicketStatusDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT id, status, description, ts_date, id_ticket FROM ticket_status WHERE id = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The id of the ticket status
     */
    private final int id;

    public GetTicketStatusDatabase(Connection con, int id) {
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
