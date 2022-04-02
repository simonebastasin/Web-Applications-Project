package it.unipd.dei.wa2122.wadteam.dao.onlineInvoice;

import it.unipd.dei.wa2122.wadteam.resources.OnlineInvoice;
import it.unipd.dei.wa2122.wadteam.resources.PaymentMethodOnlineEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteOnlineInvoiceDatabase {

    /** SQL statement to be executed */
    private static final String STATEMENT = "DELETE FROM Online_Invoice WHERE id = ? RETURNING id, id_order, transaction_id, payment_type, oi_date, total_price";

    /** connection to database */
    private final Connection con;

    /** id of the OnlineInvoice instance to delete */
    private final int id;

    /** class constructor */
    public DeleteOnlineInvoiceDatabase(final Connection con, final int id) {
        this.con = con;
        this.id = id;
    }

    /**
     * delete from database the Online_Invoice instance with matching id
     *
     * @return the {@code OnlineInvoice} object removed from database
     * @throws SQLException if any SQL error using "DELETE FROM" occurs
     */
    public OnlineInvoice deleteOnlineInvoice() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        OnlineInvoice roi = null; // OnlineInvoice instance managed

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                roi = new OnlineInvoice(
                        rs.getInt("id"),
                        rs.getInt("id_order"),
                        rs.getString("transaction_id"),
                        PaymentMethodOnlineEnum.valueOf(rs.getString("payment_type")),
                        rs.getString("oi_date"),
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

            con.close();
        }

        return roi;
    }
}
