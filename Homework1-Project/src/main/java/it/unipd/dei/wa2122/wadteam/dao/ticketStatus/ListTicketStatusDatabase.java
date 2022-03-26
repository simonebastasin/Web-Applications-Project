package it.unipd.dei.wa2122.wadteam.dao.ticketStatus;

import it.unipd.dei.wa2122.wadteam.resources.Employee;
import it.unipd.dei.wa2122.wadteam.resources.Role;
import it.unipd.dei.wa2122.wadteam.resources.TicketStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListTicketStatusDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT id, status, description, ts_date, id_ticket FROM ticketstatus WHERE id_ticket = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    private final int idTicket;

    /**
     * Creates a new object for reading a ticket status.
     *
     * @param con
     *            the connection to the database.
     */
    public ListTicketStatusDatabase(final Connection con, final int idTicket) {
        this.con = con;
        this.idTicket = idTicket;
    }

    /**
     * Reads a ticket status from the database.
     *
     * @return the {@code TicketStatus} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the ticket status.
     */
    public List<TicketStatus> getTicketStatus() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the current ticket status
        TicketStatus resultTicketStatusItem = null;

        List<TicketStatus> resultTicketStatus = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, idTicket);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                resultTicketStatusItem = new TicketStatus(resultSet.getInt("id"),
                        resultSet.getString("status"),
                        resultSet.getString("description"),
                        resultSet.getString("tsDate"),
                        resultSet.getInt("idTicket")
                );
                resultTicketStatus.add(resultTicketStatusItem);
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
