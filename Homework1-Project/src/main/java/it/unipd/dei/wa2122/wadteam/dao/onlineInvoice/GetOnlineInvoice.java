package it.unipd.dei.wa2122.wadteam.dao.onlineInvoice;

import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.GetOnlineOrderByIdDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetOnlineInvoice {


    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM Online_Invoice WHERE ID = ?";

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
     *            the id of the Online Invoice.
     */
    public GetOnlineInvoice(final Connection con, final int id) {
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
    public OnlineInvoice getOnlineInvoice() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the read employee
        OnlineInvoice resultOnlineInvoice = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                int id = resultSet.getInt("ID");
                int idOrder = resultSet.getInt("ID_Order");
                String transactionId = resultSet.getString("Transaction_ID");
                PaymentMethodOnlineEnum paymentType = PaymentMethodOnlineEnum.fromString(resultSet.getString("Payment_Type "));
                DateTime date = resultSet.getObject("OI_Date  ", DateTime.class);
                Double totalPrice=resultSet.getDouble("Total_Price");

                resultSet.close();
                preparedStatement.close();

                resultOnlineInvoice = new OnlineInvoice(id,new GetOnlineOrderByIdDatabase(con,idOrder).getOnlineOrderId(),transactionId,paymentType,date,totalPrice);
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
