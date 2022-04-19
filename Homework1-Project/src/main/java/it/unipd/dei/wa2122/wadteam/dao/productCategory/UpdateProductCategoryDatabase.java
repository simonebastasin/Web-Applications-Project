package it.unipd.dei.wa2122.wadteam.dao.productCategory;

import it.unipd.dei.wa2122.wadteam.resources.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateProductCategoryDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "UPDATE product_category SET description = ? WHERE name = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The productCategory to be updated in the database
     */
    private final ProductCategory category;

    /**
     * Creates a new object for update a productCategory.
     *
     * @param con
     *            the connection to the database.
     * @param category
     *            the productCategory to be updated in the database.
     */
    public UpdateProductCategoryDatabase(final Connection con, final ProductCategory category) {
        this.con = con;
        this.category = category;
    }

    /**
     * Updates a productCategory from the database.
     *
     * @return the {@code ProductCategory} object matching the name.
     *
     * @throws SQLException
     *             if any error occurs while updating the product.
     */
    public int updateProductCategory() throws SQLException {
        PreparedStatement preparedStatement = null;
        int result;

        ProductCategory resultCategory = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1,category.getDescription());
            preparedStatement.setString(2,category.getName());

            result = preparedStatement.executeUpdate();

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        con.close();

        return result;
    }
}
