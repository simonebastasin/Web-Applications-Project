package it.unipd.dei.wa2122.wadteam.dao.discount;

import it.unipd.dei.wa2122.wadteam.resources.DateTime;
import it.unipd.dei.wa2122.wadteam.resources.Discount;
import it.unipd.dei.wa2122.wadteam.resources.Owns;
import it.unipd.dei.wa2122.wadteam.resources.Product;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeleteDiscountDatabase {
    /**
     * The SQL statement to be executed. Cascade deletion is handled
     */
    private static final String STATEMENT_DELETE_DISCOUNT = "DELETE FROM Discount WHERE ID = ? RETURNING *";
    private static final String STATEMENT_DELETE_OWNS = "DELETE FROM Owns WHERE ID_Discount = ? RETURNING ID_Discount, Product_Alias";


    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The discount of the discount
     */
    private final Discount discount;

    /**
     * Creates a new object for deleting a discount.
     *
     * @param con
     *            the connection to the database.
     * @param discount
     *            the id of the discount.
     */
    public DeleteDiscountDatabase(final Connection con, final Discount discount) {
        this.con = con;
        this.discount = discount;
    }

    /**
     * Deletes a discount from the database.
     *
     * @return the {@code discount} object matching the id.
     *
     * @throws SQLException
     *             if any error occurs while deleting the discount.
     */
    public Discount deleteDiscount() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the deleted discount
        List<String> resultProduct = new ArrayList<>();
        Discount resultDiscount = null;


        try {
            preparedStatement = con.prepareStatement(STATEMENT_DELETE_OWNS);
            preparedStatement.setInt(1, discount.getId());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                resultProduct.add(resultSet.getString("product_alias"));
            }

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        try {
            preparedStatement = con.prepareStatement(STATEMENT_DELETE_DISCOUNT);
            preparedStatement.setInt(1, discount.getId());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultDiscount = new Discount(resultSet.getInt("ID"),
                        resultSet.getInt("Percentage"),
                        new DateTime(resultSet.getObject("Start_Date", LocalDateTime.class)),
                        new DateTime(resultSet.getObject("End_Date", LocalDateTime.class)));
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
        return resultDiscount;
    }
}