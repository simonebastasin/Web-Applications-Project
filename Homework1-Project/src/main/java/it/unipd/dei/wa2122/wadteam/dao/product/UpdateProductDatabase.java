package it.unipd.dei.wa2122.wadteam.dao.product;

import it.unipd.dei.wa2122.wadteam.resources.Product;
import it.unipd.dei.wa2122.wadteam.resources.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UpdateProductDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT_UPDATE_PRODUCT = "UPDATE product SET name = ?, brand = ?, description = ?, quantity = ?, purchase_price = ?, sale_price = ?, category_name = ?, evidence = ? WHERE product_alias = ?";

    private static final String STATEMENT_UPDATE_PICTURE = "UPDATE Represented_by SET product_alias = ?, id_media = ? WHERE product_alias = ?";
    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The product to be updated
     */
    private final Product product;

    /**
     * Creates a new object for updating a product.
     *
     * @param con
     *            the connection to the database.
     * @param product
     *            the product.
     */
    public UpdateProductDatabase(final Connection con, final Product product) {
        this.con = con;
        this.product = product;
    }

    /**
     * Updates a product from the database.
     *
     * @return the {@code Product} object matching the product_alias.
     *
     * @throws SQLException
     *             if any error occurs while updating the product.
     */
    public int updateProduct() throws SQLException {
        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            preparedStatement = con.prepareStatement(STATEMENT_UPDATE_PRODUCT);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2, product.getBrand());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setDouble(5, product.getPurchasePrice());
            preparedStatement.setDouble(6, product.getSalePrice());
            preparedStatement.setString(7, product.getCategory().getName());
            preparedStatement.setBoolean(8, product.getEvidence());
            preparedStatement.setString(9, product.getAlias());

            result += preparedStatement.executeUpdate();

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        if(product.getPictures() != null)
            for(var item : product.getPictures()) {
                try {
                    preparedStatement = con.prepareStatement(STATEMENT_UPDATE_PICTURE);
                    preparedStatement.setString(1, product.getAlias());
                    preparedStatement.setInt(2, item);
                    preparedStatement.setString(3, product.getAlias());

                    result += preparedStatement.executeUpdate();


                } finally {

                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                }
            }

        con.close();

        return result;
    }
}
