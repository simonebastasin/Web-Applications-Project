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

    /** SQL statement to be executed */
    private static final String STATEMENT = "INSERT INTO Online_Invoice (id_order, transaction_id, payment_type, oi_date, total_price) VALUES (?, ?, ?::paymentmethodonline, ?, ?) RETURNING id, id_order, transaction_id, payment_type, oi_date, total_price";

    /** connection to database */
    private final Connection con;

    /** object OnlineInvoice instance to create */
    private final OnlineInvoice onlineInvoice;

    /** class constructor */
    public CreateOnlineInvoiceDatabase(final Connection con, final OnlineInvoice onlineInvoice) {
        this.con = con;
        this.onlineInvoice = onlineInvoice;
    }

    /**
     * add to database the Online_Invoice instance specified
     *
     * @return the {@code OnlineInvoice} object added to database
     * @throws SQLException if any SQL error using "INSERT INTO" occurs
     */
    public OnlineInvoice createOnlineInvoice() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        OnlineInvoice roi = null; // OnlineInvoice instance managed

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, onlineInvoice.getIdOrder().getIdOrder());
            pstmt.setString(2, onlineInvoice.getTransactionId());
            pstmt.setString(3, onlineInvoice.getPaymentType().toString()); // get user-friendly enum text
            pstmt.setObject(4, onlineInvoice.getOiDate().getLocalDateTime());
            pstmt.setDouble(5, onlineInvoice.getTotalPrice());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int idOrder = rs.getInt("id_order");
                roi = new OnlineInvoice(
                        rs.getInt("id"),
                        new GetOnlineOrderByIdDatabase(con, idOrder).getOnlineOrderId(), //rs.getInt("id_order"),
                        rs.getString("transaction_id"),
                        PaymentMethodOnlineEnum.valueOf(rs.getString("payment_type")),
                        new DateTime(rs.getObject("oi_date", LocalDateTime.class)),
                        rs.getDouble("total_price")
                );

            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }
        con.close();

        return roi;
    }
}