package it.unipd.dei.wa2122.wadteam.dao.onlineOrder;

import it.unipd.dei.wa2122.wadteam.resources.OnlineOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateOnlineOrderDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "UPDATE Order_Status SET status = ?::orderstatus WHERE id_order = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The id of the onlineOrder
     */
    private final OnlineOrder onlineOrder;

    /**
     * Creates a new object for updating an onlineOrder.
     *
     * @param con
     *            the connection to the database.
     * @param onlineOrder
     *            the new onlineOrder.
     */
    public UpdateOnlineOrderDatabase(final Connection con, final OnlineOrder onlineOrder) {
        this.con = con;
        this.onlineOrder = onlineOrder;
    }

    /**
     * Gets an int from the update of the database.
     *
     * @return the {@code OnlineOrder} object matching the id.
     *
     * @throws SQLException
     *             if any error occurs while deleting the onlineOrder.
     */
    public int updateOnlineOrder() throws SQLException {

        PreparedStatement pstmt = null;
        int rs;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, onlineOrder.getStatus().getStatus().toString());
            pstmt.setInt(2, onlineOrder.getIdOrder());

            rs = pstmt.executeUpdate();

        } finally {

            if (pstmt != null) {
                pstmt.close();
            }
        }
        con.close();

        return rs;
    }
}
