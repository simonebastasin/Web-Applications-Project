package it.unipd.dei.wa2122.wadteam.dao.onlineInvoice;

import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.GetOnlineOrderByIdDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ListOnlineInvoiceDatabase {
    private static final String STATEMENT = "SELECT * FROM Online_Invoice";

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
    public ListOnlineInvoiceDatabase(final Connection con) {
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
    ResultSet resultSet = null;

    public List<OnlineInvoice> getOnlineInvoice() throws SQLException {
        List<OnlineInvoice> resultOnlineInvoice = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("ID");
                int idOrder = resultSet.getInt("ID_Order");
                String transactionId = resultSet.getString("Transaction_ID");
                PaymentMethodOnlineEnum paymentType = PaymentMethodOnlineEnum.fromString(resultSet.getString("Payment_Type"));
                Double totalPrice=resultSet.getDouble("Total_Price");

                resultOnlineInvoice.add(new OnlineInvoice(id,new GetOnlineOrderByIdDatabase(con,idOrder).getOnlineOrderId(),transactionId,paymentType,totalPrice));

            }

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            }
        con.close();
        return resultOnlineInvoice;
        }
    }
