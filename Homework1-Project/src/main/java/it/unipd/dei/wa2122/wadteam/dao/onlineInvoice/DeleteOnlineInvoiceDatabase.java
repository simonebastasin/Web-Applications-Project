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

public class DeleteOnlineInvoiceDatabase {

    /**
     * SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM Online_Invoice WHERE id = ? RETURNING id, id_order, transaction_id, payment_type, oi_date, total_price";

    /**
     * connection to the database
     */
    private final Connection con;

    /**
     * id of the OnlineInvoice instance to delete
     */
    private final int id;

    /**
     * creates an object to delete the online invoice desired from the database
     *
     * @param con   connection to the database
     * @param id    id of the OnlineInvoice instance to delete
     */
    public DeleteOnlineInvoiceDatabase(final Connection con, final int id) {
        this.con = con;
        this.id = id;
    }

    /**
     * delete from the database the Online_Invoice instance with matching id
     *
     * @return the {@code OnlineInvoice} object removed from the database
     * @throws SQLException if any SQL error using "DELETE FROM" occurs
     */
    public OnlineInvoice deleteOnlineInvoice() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        OnlineInvoice resultOnlineInvoice = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int idOrder = resultSet.getInt("id_order");
                resultOnlineInvoice = new OnlineInvoice(
                        resultSet.getInt("id"),
                        null,
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
