package it.unipd.dei.wa2122.wadteam.dao.product;

import it.unipd.dei.wa2122.wadteam.resources.Product;
import it.unipd.dei.wa2122.wadteam.resources.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeleteProductDatabase {
    /**
     * The SQL statements to be executed
     */
    private static final String STATEMENT_DELETE_PRODUCT = "DELETE FROM product WHERE product_alias = ?";

    private static final String STATEMENT_DELETE_PICTURE = "DELETE FROM Represented_by WHERE product_alias = ? ";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The product to be deleted
     */
    private final String product_alias;

    /**
     * Creates a new object for deleting a product.
     *
     * @param con
     *            the connection to the database.
     * @param product_alias
     *            the product.
     */
    public DeleteProductDatabase(final Connection con, final String product_alias){
        this.con = con;
        this.product_alias = product_alias;
    }

    /**
     * Deletes a product from the database.
     *
     * @return the {@code Product} object matching the alias.
     *
     * @throws SQLException
     *             if any error occurs while deleting the product.
     */
    public int deleteProduct() throws SQLException {
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            preparedStatement = con.prepareStatement(STATEMENT_DELETE_PICTURE);
            preparedStatement.setString(1,product_alias);

            result += preparedStatement.executeUpdate();

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        try {
            preparedStatement = con.prepareStatement(STATEMENT_DELETE_PRODUCT);
            preparedStatement.setString(1,product_alias);

            result += preparedStatement.executeUpdate();

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        con.close();

        return result;
    }
}
