package it.unipd.dei.wa2122.wadteam.dao.onlineInvoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckIfCanBeCreatedOnlineInvoiceDatabase {
    private final String STATEMENT = "select min(p.quantity - c.quantity) >= 0 from online_order as o " +
            "inner join contains as c on o.id = c.id_order " +
            "inner join product as p on p.product_alias=c.product_alias " +
            "where o.id = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The id of the onlineOrder
     */
    private final Integer idOrder;

    /**
     * Creates a new object for getting an onlineOrder.
     *
     * @param con
     *            the connection to the database.
     * @param idOrder
     *            the id of the order.
     */
    public CheckIfCanBeCreatedOnlineInvoiceDatabase(final Connection con, final Integer idOrder) {
        this.con = con;
        this.idOrder = idOrder;
    }

    public Boolean checkIfCanBeCreatedOnlineInvoice() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PreparedStatement pstmtProduct = null;
        Boolean result = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, idOrder);

            rs = pstmt.executeQuery();

            if(rs.next()) {
                result = rs.getBoolean(1);
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

        return result;
    }
}
