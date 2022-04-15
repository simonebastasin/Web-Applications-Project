package it.unipd.dei.wa2122.wadteam.dao.product;

import it.unipd.dei.wa2122.wadteam.resources.DateTime;
import it.unipd.dei.wa2122.wadteam.resources.Discount;
import it.unipd.dei.wa2122.wadteam.resources.Product;
import it.unipd.dei.wa2122.wadteam.resources.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SearchProductListDatabase {

    /**
     * The SQL statements to be executed
     */
    private static final String STATEMENT = "SELECT p.product_alias, p.name, p.brand, p.description, p.quantity, p.purchase_price, p.sale_price, p.category_name, p.evidence, d.*  FROM product AS p LEFT JOIN (SELECT DISTINCT ON (o.product_alias) o.product_alias, d.* FROM owns o LEFT JOIN discount d on o.id_discount = d.id WHERE d.start_date <= now() AND d.end_date >= now() ORDER BY o.product_alias, d.percentage DESC) as d ON d.product_alias = p.product_alias WHERE UPPER(p.name) LIKE ?";

    private static final String STATEMENT_GET_PICTURE = "SELECT id_media FROM Represented_by WHERE product_alias = ?";


    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The product_alias of the product to be retrieved
     */
    private final String partialName;

    public SearchProductListDatabase(Connection con, String partialName) {
        this.con = con;
        this.partialName = partialName;
    }

    /**
     * Gets a list of products from the database.
     *
     * @return a list of {@code Product} objects matching the partialAlias.
     * @throws SQLException if any error occurs while retrieving the product.
     */
    public List<Product> searchProductList() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Product resultProductItem = null;
        List<Product> resultProduct = new ArrayList<>();


        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1,partialName.toUpperCase()+"%");

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
                        new ArrayList<>(), resultSet.getObject("percentage") != null ? new Discount(resultSet.getInt("id"),
                        resultSet.getInt("percentage"),
                        new DateTime(resultSet.getObject("start_date", LocalDateTime.class)),
                        new DateTime(resultSet.getObject("end_date", LocalDateTime.class))) : null);
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
                preparedStatement = con.prepareStatement(STATEMENT_GET_PICTURE);
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
