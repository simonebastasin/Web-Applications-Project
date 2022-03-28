package it.unipd.dei.wa2122.wadteam.dao.discount;

import it.unipd.dei.wa2122.wadteam.resources.Discount;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteDiscount {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT_DELETE_DISCOUNT = "DELETE FROM Discount WHERE ID = ? RETURNING *";
    //private static final String STATEMENT_DELETE_OWNS = "DELETE FROM Owns WHERE ID_Discount = ? AND Product_Alias = ? RETURNING ID_Discount,id_media";
    //ToDO: Delete Discount from list OWNS

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The id of the discount
     */
    private final int id;

    /**
     * Creates a new object for deleting a discount.
     *
     * @param con
     *            the connection to the database.
     * @param id
     *            the id of the discount.
     */
    public DeleteDiscount(final Connection con, final int id) {
        this.con = con;
        this.id = id;
    }

    /**
     * Deletes a discount from the database.
     *
     * @return the {@code discount} object matching the id.
     *
     * @throws SQLException
     *             if any error occurs while deleting the discount.
     */
    public Discount getDiscount() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the deleted employee
        Discount resultDiscount = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT_DELETE_DISCOUNT);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultDiscount = new Discount(resultSet.getInt("ID"),
                        resultSet.getInt("Percentage"),
                        resultSet.getString("Start_Date"),
                        resultSet.getString("End_Date"));
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return resultDiscount;
    }
}