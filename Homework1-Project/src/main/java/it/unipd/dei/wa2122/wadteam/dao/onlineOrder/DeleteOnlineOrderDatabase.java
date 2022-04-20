package it.unipd.dei.wa2122.wadteam.dao.onlineOrder;

import it.unipd.dei.wa2122.wadteam.resources.OnlineOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteOnlineOrderDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "UPDATE Order_Status SET status = 'Cancelled', description = 'Deleted' WHERE id_order = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The id of the onlineOrder
     */
    private final Integer id;

    /**
     * Creates a new object for deleting an onlineOrder.
     *
     * @param con
     *            the connection to the database.
     * @param id
     *            the id of the onlineOrder.
     */
    public DeleteOnlineOrderDatabase(final Connection con, final Integer id) {
        this.con = con;
        this.id = id;
    }

    /**
     * Deletes an onlineOrder from the database.
     *
     * @return the {@code OnlineOrder} object matching the id.
     *
     * @throws SQLException
     *             if any error occurs while deleting the onlineOrder.
     */
    public int deleteOnlineOrder() throws SQLException {

        PreparedStatement pstmt = null;
        int result;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, id);

            result = pstmt.executeUpdate();

        } finally {

            if (pstmt != null) {
                pstmt.close();
            }
        }
        con.close();

        return result;
    }
}
