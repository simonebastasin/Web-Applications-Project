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

public class CreateOnlineInvoiceDatabase {

    /**
     * SQL statement to be executed
     */
    private static final String STATEMENT = "INSERT INTO Online_Invoice (id_order, transaction_id, payment_type, total_price) VALUES (?, ?, ?::paymentmethodonline, ?) RETURNING id, id_order, transaction_id, payment_type, oi_date, total_price";

    /**
     * connection to the database
     */
    private final Connection con;

    /**
     * object OnlineInvoice instance to create
     */
    private final OnlineInvoice onlineInvoice;

    /**
     * creates an object to create the online invoice desired in the database
     *
     * @param con   connection to the database
     * @param onlineInvoice object OnlineInvoice instance to create
     */
    public CreateOnlineInvoiceDatabase(final Connection con, final OnlineInvoice onlineInvoice) {
        this.con = con;
        this.onlineInvoice = onlineInvoice;
    }

    /**
     * add to the database the Online_Invoice instance specified
     *
     * @return the {@code OnlineInvoice} object added to the database
     * @throws SQLException if any SQL error using "INSERT INTO" occurs
     */
    public OnlineInvoice createOnlineInvoice() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        OnlineInvoice resultOnlineInvoice = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, onlineInvoice.getIdOrder().getIdOrder());
            preparedStatement.setString(2, onlineInvoice.getTransactionId());
            preparedStatement.setString(3, onlineInvoice.getPaymentType().toString()); // get user-friendly enum text
            preparedStatement.setDouble(4, onlineInvoice.getTotalPrice());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int idOrder = resultSet.getInt("id_order");
                resultOnlineInvoice = new OnlineInvoice(
                        resultSet.getInt("id"),
                        onlineInvoice.getIdOrder(), //rs.getInt("id_order"),
                        resultSet.getString("transaction_id"),
                        PaymentMethodOnlineEnum.fromString(resultSet.getString("payment_type")),
                        new DateTime(resultSet.getObject("oi_date", LocalDateTime.class)),
                        resultSet.getDouble("total_price")
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
        con.close();

        return resultOnlineInvoice;
    }
}