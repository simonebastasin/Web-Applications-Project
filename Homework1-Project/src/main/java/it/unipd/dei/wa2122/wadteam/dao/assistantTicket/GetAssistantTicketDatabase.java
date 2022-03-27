package it.unipd.dei.wa2122.wadteam.dao.assistantTicket;

import it.unipd.dei.wa2122.wadteam.resources.AssistantTicket;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetAssistantTicketDatabase {

        /**
         * The SQL statement to be executed
         */
        private static final String STATEMENT = "SELECT ID, Description, ID_Custumer, Product_Alias FROM Assistance_Ticket WHERE ID = ?";

        /**
         * The connection to the database
         */
        private final Connection con;

        /**
         * The id of the assistant ticket
         */
        private final int id;

        /**
         * Creates a new object for reading an assistant ticket.
         *
         * @param con
         *            the connection to the database.
         * @param id
         *            the id of the assistant ticket.
         */
        public GetAssistantTicketDatabase(final Connection con, final int id) {
            this.con = con;
            this.id = id;
        }

        /**
         * Reads an assistant ticket from the database.
         *
         * @return the {@code AssistantTicket} object matching the badge.
         *
         * @throws SQLException
         *             if any error occurs while reading the assistant ticket.
         */
        public AssistantTicket getAssistantTicket() throws SQLException {

            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            // the read employee
            AssistantTicket resultAssistantTicket = null;

            try {
                preparedStatement = con.prepareStatement(STATEMENT);
                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    resultAssistantTicket = new AssistantTicket(resultSet.getInt("ID"),
                            resultSet.getString("Description"),
                            resultSet.getInt("ID_Customer"),
                            resultSet.getString("Product_Alias"));
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
