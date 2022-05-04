
package it.unipd.dei.wa2122.wadteam.dao.owns;

import it.unipd.dei.wa2122.wadteam.resources.Owns;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DeleteOwnsFromDiscountIdDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM Owns WHERE ID_Discount = ? RETURNING ID_Discount, Product_Alias";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The id of the discount
     */
    private final int idDiscount;

    /**
     * Creates a new object for deleting an own.
     *
     * @param con
     *            the connection to the database.
     * @param idDiscount
     *            the id of the Discount Collection.
     */
    public DeleteOwnsFromDiscountIdDatabase(final Connection con, final int idDiscount) {
        this.con = con;
        this.idDiscount = idDiscount;
    }

    /**
     * Deletes an own from the database from ioDiscount.
     *
     * @return the {@code Owns} object.
     *
     * @throws SQLException
     *             if any error occurs while deleting the own.
     */
    public void deleteOwn() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Owns> ownsList;
        // the deleted owns
        Owns resultOwn = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, idDiscount);

            rs = pstmt.executeQuery();
            while (rs.next()){

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

        return ;
    }
}
