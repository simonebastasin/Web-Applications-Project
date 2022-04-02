package it.unipd.dei.wa2122.wadteam.dao.assistantTicket;

import it.unipd.dei.wa2122.wadteam.resources.AssistantTicket;
import it.unipd.dei.wa2122.wadteam.resources.TicketStatus;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetAssistantTicketDatabase {

        /**
         * The SQL statement to be executed
         */
        private static final String STATEMENT = "SELECT ID, Description, ID_Customer, Product_Alias FROM Assistance_Ticket WHERE ID = ?";

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

                    int id = resultSet.getInt("ID");
                    String description = resultSet.getString("Description");
                    int idCustomer = resultSet.getInt("ID_Customer");
                    String productAlias =  resultSet.getString("Product_Alias");

                    resultSet.close();
                    preparedStatement.close();

                    preparedStatement = con.prepareStatement(STATEMENT_STATUS);
                    preparedStatement.setInt(1, id);

                    resultSet = preparedStatement.executeQuery();

                    List<TicketStatus> resultTicketStatus = new ArrayList<>();

                    while (resultSet.next()) {

                        var resultTicketStatusItem = new TicketStatus(resultSet.getInt("id"),
                                resultSet.getString("status"),
                                resultSet.getString("description"),
                                resultSet.getString("ts_Date"),
                                resultSet.getInt("idTicket")
                        );
                        resultTicketStatus.add(resultTicketStatusItem);
                    }

                    resultAssistantTicket = new AssistantTicket(id,
                            description,
                            idCustomer,
                            productAlias, resultTicketStatus);
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
