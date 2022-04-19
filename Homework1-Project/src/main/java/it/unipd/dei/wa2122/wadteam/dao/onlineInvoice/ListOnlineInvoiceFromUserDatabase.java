package it.unipd.dei.wa2122.wadteam.dao.onlineInvoice;

import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.GetOnlineOrderByIdDatabase;
import it.unipd.dei.wa2122.wadteam.resources.DateTime;
import it.unipd.dei.wa2122.wadteam.resources.OnlineInvoice;
import it.unipd.dei.wa2122.wadteam.resources.PaymentMethodOnlineEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ListOnlineInvoiceFromUserDatabase {
    private static final String STATEMENT = "SELECT oi.ID, oi.ID_Order,oi.Transaction_ID,oi.Payment_Type,oi.OI_Date,oi.Total_Price FROM " +
            "Online_Invoice as oi inner join " +
            "online_order as oo on oi.ID_Order = oo.ID where ID_Customer = ?";

    /**
     * The connection to the database
     */
    private final Connection con;
    private final int id;

    /**
     * Creates a new object for reading an assistant ticket.
     *
     * @param con
     *            the connection to the database.
     */
    public ListOnlineInvoiceFromUserDatabase(final Connection con, int id) {
        this.con = con;
        this.id = id;
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

                int id = resultSet.getInt("oi.ID");
                int idOrder = resultSet.getInt("oi.ID_Order");
                String transactionId = resultSet.getString("oi.Transaction_ID");
                PaymentMethodOnlineEnum paymentType = PaymentMethodOnlineEnum.fromString(resultSet.getString("oi.Payment_Type "));
                Double totalPrice=resultSet.getDouble("oi.Total_Price");

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
