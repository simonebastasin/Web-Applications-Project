package it.unipd.dei.wa2122.wadteam.dao.owns;

import it.unipd.dei.wa2122.wadteam.resources.Owns;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteOwnDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM Owns WHERE ID_Discount = ? AND Product_Alias = ? RETURNING ID_Discount, Product_Alias";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The id of the own
     */
    private final Owns own;

    /**
     * Creates a new object for deleting an own.
     *
     * @param con
     *            the connection to the database.
     * @param own
     *            the own of the Owns Collection.
     */
    public DeleteOwnDatabase(final Connection con, final Owns own) {
        this.con = con;
        this.own = own;
    }

    /**
     * Deletes an own from the database.
     *
     * @return the {@code Owns} object.
     *
     * @throws SQLException
     *             if any error occurs while deleting the own.
     */
    public Owns deleteOwn() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the deleted owns
        Owns resultOwn = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, own.getDiscount());
            pstmt.setString(2, own.getProduct());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                resultOwn = new Owns(
                        rs.getInt("ID_Discount"),
                        rs.getString("Product_Alias")
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

        return resultOwn;
    }
}
