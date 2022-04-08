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
    private static final String STATEMENT_DELETE_PRODUCT = "DELETE FROM product WHERE product_alias = ? RETURNING product_alias, name, brand, description, quantity, purchase_price, sale_price, category_name, evidence";

    private static final String STATEMENT_DELETE_PICTURE = "DELETE FROM Represented_by WHERE product_alias = ? RETURNING product_alias, id_media";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The product to be deleted
     */
    private final Product product;

    /**
     * Creates a new object for deleting a product.
     *
     * @param con
     *            the connection to the database.
     * @param product
     *            the product.
     */
    public DeleteProductDatabase(final Connection con, final Product product){
        this.con = con;
        this.product = product;
    }

    /**
     * Deletes a product from the database.
     *
     * @return the {@code Product} object matching the alias.
     *
     * @throws SQLException
     *             if any error occurs while deleting the media.
     */
    public Product deleteProduct() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Integer> resultPicture = new ArrayList<>();
        Product resultProduct = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT_DELETE_PICTURE);
            preparedStatement.setString(1,product.getAlias());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                resultPicture.add(resultSet.getInt("id_media"));
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
            preparedStatement = con.prepareStatement(STATEMENT_DELETE_PRODUCT);
            preparedStatement.setString(1,product.getAlias());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                resultProduct = new Product(
                        resultSet.getString("product_alias"),
                        resultSet.getString("name"),
                        resultSet.getString("brand"),
                        resultSet.getString("description"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("purchase_price"),
                        resultSet.getDouble("sale_price"),
                        new ProductCategory(resultSet.getString("category_name"),null),
                        resultSet.getBoolean("evidence"),
                        resultPicture);
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

        return resultProduct;
    }
}
