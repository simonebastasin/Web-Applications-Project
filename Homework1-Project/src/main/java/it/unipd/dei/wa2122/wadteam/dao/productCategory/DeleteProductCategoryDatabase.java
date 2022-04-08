package it.unipd.dei.wa2122.wadteam.dao.productCategory;

import it.unipd.dei.wa2122.wadteam.resources.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteProductCategoryDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM product_category WHERE name = ? RETURNING name, description";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The productCategory to be created in the database
     */
    private final ProductCategory category;

    /**
     * Creates a new object for update a productCategory.
     *
     * @param con
     *            the connection to the database.
     * @param category
     *            the productCategory to be deleted in the database.
     */
    public DeleteProductCategoryDatabase(final Connection con, final ProductCategory category) {
        this.con = con;
        this.category = category;
    }

    /**
     * Deletes a productCategory in the database.
     *
     * @return the {@code ProductCategory} object matching the name.
     *
     * @throws SQLException
     *             if any error occurs while reading the product.
     */
    public ProductCategory deleteProductCategory() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ProductCategory resultCategory = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1,category.getName());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultCategory = new ProductCategory(
                        resultSet.getString("name"),
                        resultSet.getString("description"));
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

        return resultCategory;
    }
}
