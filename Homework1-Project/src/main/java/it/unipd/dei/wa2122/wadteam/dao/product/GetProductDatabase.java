package it.unipd.dei.wa2122.wadteam.dao.product;

import it.unipd.dei.wa2122.wadteam.resources.Product;
import it.unipd.dei.wa2122.wadteam.resources.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetProductDatabase {
    /**
     * The SQL statements to be executed
     */
    private static final String STATEMENT_GET_PRODUCT = "SELECT product_alias, name, brand, description, quantity, purchase_price, sale_price, category, evidence) FROM product WHERE product_alias = ? RETURNING alias, name, brand, description, quantity, purchase, sale, category, evidence";

    private static final String STATEMENT_GET_PICTURE = "SELECT product_alias, id_media FROM rappresented_by WHERE product_alias = ? AND id_media = ? RETURNING alias, id";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The product_alias of the product to be retrieved
     */
    private final Product product;

    /**
     * Creates a new object for getting a product.
     *
     * @param con     the connection to the database.
     * @param product the product.
     */
    public GetProductDatabase(final Connection con, final Product product) {
        this.con = con;
        this.product = product;
    }

    /**
     * Gets a product from the database.
     *
     * @return the {@code Product} object matching the product_alias.
     * @throws SQLException if any error occurs while retrieving the product.
     */
    public Product getProduct() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Product resultProduct = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT_GET_PRODUCT);
            preparedStatement.setString(1, product.getAlias());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultProduct = new Product(resultSet.getString("product_alias"),
                        resultSet.getString("name"),
                        resultSet.getString("brand"),
                        resultSet.getString("description"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("purchase_price"),
                        resultSet.getDouble("sale_price"),
                        new ProductCategory(resultSet.getString("category"), null),
                        resultSet.getBoolean("evidence"),
                        new ArrayList<>());
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        for (var item : product.getPicture()) {
            try {
                preparedStatement = con.prepareStatement(STATEMENT_GET_PICTURE);
                preparedStatement.setString(1, product.getAlias());
                preparedStatement.setInt(2, item);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    assert resultProduct != null;
                    resultProduct.getPicture().add(resultSet.getInt("id_media"));
                }

            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        }

        return resultProduct;
    }
}
