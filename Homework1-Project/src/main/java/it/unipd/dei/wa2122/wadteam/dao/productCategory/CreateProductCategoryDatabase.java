package it.unipd.dei.wa2122.wadteam.dao.productCategory;

import it.unipd.dei.wa2122.wadteam.resources.Product;
import it.unipd.dei.wa2122.wadteam.resources.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateProductCategoryDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "INSERT INTO product_category (name, description) VALUES (?, ?) RETURNING name, description";

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
     *            the productCategory to be created in the database.
     */
    public CreateProductCategoryDatabase(final Connection con, final ProductCategory category) {
        this.con = con;
        this.category = category;
    }

    /**
     * Creates a productCategory in the database.
     *
     * @return the {@code ProductCategory} object matching the name.
     *
     * @throws SQLException
     *             if any error occurs while reading the product.
     */
    public ProductCategory createProductCategory() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ProductCategory resultCategory = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1,category.getName());
            preparedStatement.setString(2,category.getDescription());

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

        return resultCategory;
    }
}
