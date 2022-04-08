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

    private static final String STATEMENT_GET_PICTURES = "SELECT id_media FROM Represented_by WHERE product_alias = ?";

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
    public Product updateProductQuantity() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Product resultProduct = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT_UPDATE_QUANTITY);
            preparedStatement.setInt(1,quantity);
            preparedStatement.setString(2,alias);


            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                resultProduct = new Product(resultSet.getString("product_alias"),
                        resultSet.getString("name"),
                        resultSet.getString("brand"),
                        resultSet.getString("description"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("purchase_price"),
                        resultSet.getDouble("sale_price"),
                        new ProductCategory(resultSet.getString("category_name"),null),
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


        try {
            preparedStatement = con.prepareStatement(STATEMENT_GET_PICTURES);
            preparedStatement.setString(1, alias);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                assert resultProduct != null;
                resultProduct.getPictures().add(resultSet.getInt("id_media"));
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
