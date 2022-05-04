package it.unipd.dei.wa2122.wadteam.dao.assistanceTicket;

import it.unipd.dei.wa2122.wadteam.resources.AssistanceTicket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateAssistanceTicketDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "UPDATE Assistance_Ticket SET Description = ?, ID_customer = ?, Product_Alias = ? WHERE ID = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The item of the assistant ticket
     */
    private final AssistanceTicket assistantTicket;

    /**
     * Update  a assistant ticket item.
     *
     * @param con
     *            the connection to the database.
     * @param assistantTicket
     *            the assistant ticket  to be update.
     */
    public UpdateAssistanceTicketDatabase(final Connection con, final AssistanceTicket assistantTicket) {
        this.con = con;
        this.assistantTicket = assistantTicket;
    }

    /**
     * Reads an employee from the database.
     *
     * @return the {@code AssitantTicket} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the employee.
     */
    public int getAssistaceTicket() throws SQLException {

        PreparedStatement preparedStatement = null;
        int result;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(2, assistantTicket.getDescription()); //TODO: check param numbers
            preparedStatement.setInt(3, assistantTicket.getIdCustomer());
            preparedStatement.setString(4, assistantTicket.getProductAlias());

            result = preparedStatement.executeUpdate();


        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        con.close();

        return result;
    }
}
