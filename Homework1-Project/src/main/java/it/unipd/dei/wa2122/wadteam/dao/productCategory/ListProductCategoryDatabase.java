package it.unipd.dei.wa2122.wadteam.dao.productCategory;

import it.unipd.dei.wa2122.wadteam.resources.Product;
import it.unipd.dei.wa2122.wadteam.resources.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListProductCategoryDatabase {
    /**
     * The SQL statements to be executed
     */
    private static final String STATEMENT = "SELECT name, description FROM product_category";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Creates a new object for reading a productCategory.
     *
     * @param con the connection to the database.
     */
    public ListProductCategoryDatabase(final Connection con) {
        this.con = con;
    }

    /**
     * Lists all the productCategories in the database
     *
     * @return the {@code Product}
     * @throws SQLException if any error occurs while listing the products.
     */
    public List<ProductCategory> getProductCategory() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ProductCategory resultProductCategoryItem;
        List<ProductCategory> resultProductCategory = new ArrayList<>();

        try {
            preparedStatement = con.prepareStatement(STATEMENT);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resultProductCategoryItem = new ProductCategory(
                        resultSet.getString("name"),
                        resultSet.getString("description"));
                resultProductCategory.add(resultProductCategoryItem);
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

        return resultProductCategory;
    }
}
