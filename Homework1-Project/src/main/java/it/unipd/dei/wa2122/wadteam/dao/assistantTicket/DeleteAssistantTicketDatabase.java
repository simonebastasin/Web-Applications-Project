package it.unipd.dei.wa2122.wadteam.dao.assistantTicket;

import it.unipd.dei.wa2122.wadteam.resources.AssistantTicket;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteAssistantTicketDatabase {
        /**
         * The SQL statement to be executed
         */
        private static final String STATEMENT = "DELETE FROM Assisistant_Ticket WHERE ID = ? RETURNING *";

        /**
         * The connection to the database
         */
        private final Connection con;

        /**
         * The id of the assistant ticket
         */
        private final int id;

        /**
         * Creates a new object for deleting an assistant ticket.
         *
         * @param con
         *            the connection to the database.
         * @param id
         *            the id of the assistant ticket.
         */
        public DeleteAssistantTicketDatabase(final Connection con, final int id) {
            this.con = con;
            this.id = id;
        }

        /**
         * Deletes an assistanti ticket from the database.
         *
         * @return the {@code AssistantTicket} object matching the id.
         *
         * @throws SQLException
         *             if any error occurs while deleting the assistant ticket.
         */
        public AssistantTicket getAssistantTicket() throws SQLException {

            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            // the deleted employee
            AssistantTicket resultAssistantiTicket = null;

            try {
                preparedStatement = con.prepareStatement(STATEMENT);
                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    resultAssistantiTicket = new AssistantTicket(resultSet.getInt("ID"),
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

            return resultAssistantiTicket;
        }
}

