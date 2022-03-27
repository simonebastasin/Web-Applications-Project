package it.unipd.dei.wa2122.wadteam.dao.product;

import it.unipd.dei.wa2122.wadteam.resources.Product;
import it.unipd.dei.wa2122.wadteam.resources.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateProductDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT_INSERT_PRODUCT = "INSERT INTO product (alias, name, brand, description, quantity, purchase, sale, category, evidence) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING alias, name, brand, description, quantity, purchase, sale, category, evidence";

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
    public Product createProduct() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Product resultProduct = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT_INSERT_PRODUCT);
            preparedStatement.setString(1, product.getAlias());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getBrand());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setInt(5, product.getQuantity());
            preparedStatement.setDouble(6, product.getPurchase());
            preparedStatement.setDouble(7, product.getSale());
            preparedStatement.setString(8, product.getCategory().getName());
            preparedStatement.setBoolean(9, product.getEvidence());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultProduct = new Product(resultSet.getString("product_alias"),
                                            resultSet.getString("name"),
                                            resultSet.getString("brand"),
                                            resultSet.getString("description"),
                                            resultSet.getInt("quantity"),
                                            resultSet.getDouble("purchase_price"),
                                            resultSet.getDouble("sale_price"),
                                            new ProductCategory(resultSet.getString("category"),null),
                                            resultSet.getBoolean("evidence"),
                                            product.getPicture());
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return resultProduct;
    }
}
