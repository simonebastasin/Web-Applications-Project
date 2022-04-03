package it.unipd.dei.wa2122.wadteam.dao.owns;

import it.unipd.dei.wa2122.wadteam.resources.Product;
import it.unipd.dei.wa2122.wadteam.resources.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListProductsCurrentlyOnSaleDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT product_alias, name, brand, description, quantity, purchase_price, sale_price, category_name, evidence " +
            "FROM Owns "+
            "INNER JOIN Discount ON Owns.ID_Discount=Discount.id "+
            "INNER JOIN Product ON Owns.Product_Alias=Product.Product_Alias "+
            "WHERE End_Date >= CURRENT_DATE() AND Start_Date <= CURRENT_DATE() ";

    private static final String STATEMENT_LIST_PICTURE = "SELECT id_media FROM represented_by WHERE product_alias = ?";


    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Creates a new object for reading discounts.
     *
     * @param con
     *            the connection to the database.
     */
    public ListProductsCurrentlyOnSaleDatabase(final Connection con) {
        this.con = con;
    }
    /**
     * Reads documents that are actually in discounts from the database.
     *
     * @return the {@code Discount} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the discount.
     */


    public List<Product> getListProductsCurrentlyOnSale() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the read discount
        Product resultProductItem = null;

        List<Product> resultProduct = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                resultProductItem = new Product(
                        resultSet.getString("product_alias"),
                        resultSet.getString("name"),
                        resultSet.getString("brand"),
                        resultSet.getString("description"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("purchase_price"),
                        resultSet.getDouble("sale_price"),
                        new ProductCategory(resultSet.getString("category_name"), null),
                        resultSet.getBoolean("evidence"),
                        new ArrayList<>());
                resultProduct.add(resultProductItem);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        for (var product : resultProduct) {
            try {
                preparedStatement = con.prepareStatement(STATEMENT_LIST_PICTURE);
                preparedStatement.setString(1, product.getAlias());

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    product.getPictures().add(resultSet.getInt("id_media"));
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

        con.close();

        return resultProduct;
    }


}
