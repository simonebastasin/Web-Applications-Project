package it.unipd.dei.wa2122.wadteam.dao.product;

import it.unipd.dei.wa2122.wadteam.resources.Product;
import it.unipd.dei.wa2122.wadteam.resources.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UpdateProductQuantityByAliasDatabase {
    /**
     * The SQL statements to be executed
     */
    private static final String STATEMENT_UPDATE_QUANTITY = "UPDATE product SET quantity = ? WHERE product_alias = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The quantity to be updated
     */
    private final int quantity;

    /**
     * The alias of the product to be updated
     */
    private final String alias;

    /**
     * Creates a new object for updating a product.
     *
     * @param con
     *            the connection to the database.
     * @param quantity
     *            the new quantity.
     * @param alias
     *            the alias of the product to be updated.
     *
     */
    public UpdateProductQuantityByAliasDatabase(final Connection con, final String alias,final int quantity) {
        this.con = con;
        this.quantity = quantity;
        this.alias = alias;
    }

    /**
     * Updates a product from the database.
     *
     * @return the {@code Product} object matching the product_alias.
     *
     * @throws SQLException
     *             if any error occurs while updating the product.
     */
    public int updateProductQuantity() throws SQLException {
        PreparedStatement preparedStatement = null;

        int affectedRows = 0;
        try {
            preparedStatement = con.prepareStatement(STATEMENT_UPDATE_QUANTITY);
            preparedStatement.setInt(1,quantity);
            preparedStatement.setString(2,alias);


            affectedRows = preparedStatement.executeUpdate();

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        con.close();

        return affectedRows;
    }
}
