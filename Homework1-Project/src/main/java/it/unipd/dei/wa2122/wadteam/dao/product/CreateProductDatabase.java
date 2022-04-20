package it.unipd.dei.wa2122.wadteam.dao.product;

import it.unipd.dei.wa2122.wadteam.resources.Product;
import it.unipd.dei.wa2122.wadteam.resources.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateProductDatabase {
    /**
     * The SQL statements to be executed
     */
    private static final String STATEMENT_INSERT_PRODUCT = "INSERT INTO product (product_alias, name, brand, description, quantity, purchase_price, sale_price, category_name, evidence) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String STATEMENT_INSERT_PICTURE = "INSERT INTO Represented_by (product_alias, id_media) VALUES (? , ?)";
    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The product to be created in the database
     */
    private final Product product;

    /**
     * Creates a new object for update a product.
     *
     * @param con
     *            the connection to the database.
     * @param product
     *            the product to be created in the database.
     */
    public CreateProductDatabase(final Connection con, final Product product) {
        this.con = con;
        this.product = product;
    }

    /**
     * Creates a product in the database.
     *
     * @return the {@code Product} object matching the alias.
     *
     * @throws SQLException
     *             if any error occurs while reading the product.
     */
    public int createProduct() throws SQLException {

        PreparedStatement preparedStatement = null;

        int result = 0;

        try {
            preparedStatement = con.prepareStatement(STATEMENT_INSERT_PRODUCT);
            preparedStatement.setString(1, product.getAlias());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getBrand());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setInt(5, product.getQuantity());
            preparedStatement.setDouble(6, product.getPurchasePrice());
            preparedStatement.setDouble(7, product.getSalePrice());
            preparedStatement.setString(8, product.getCategory().getName());
            preparedStatement.setBoolean(9, product.getEvidence());

            result += preparedStatement.executeUpdate();

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        if(product.getPictures()!=null){ //if there are images associated to the product to be created
            try {
                preparedStatement = con.prepareStatement(STATEMENT_INSERT_PICTURE);

                for(var item : product.getPictures()) {
                    preparedStatement.setString(1, product.getAlias());
                    preparedStatement.setInt(2, item);

                    preparedStatement.addBatch();
                }
                result += Arrays.stream(preparedStatement.executeBatch()).sum();
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
