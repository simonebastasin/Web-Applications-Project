package it.unipd.dei.wa2122.wadteam.dao.owns;

import it.unipd.dei.wa2122.wadteam.resources.Discount;
import it.unipd.dei.wa2122.wadteam.resources.Owns;
import it.unipd.dei.wa2122.wadteam.resources.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class createOwnsDiscountFromListProduct {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "INSERT INTO Owns(ID_Discount, Product_Alias) VALUES (?, ?) RETURNING ID_Discount, Product_Alias";
    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The list of products to be updated in the database
     */
    private final List<Product> products;

    /**
     * The list of products to be updated in the database
     */
    private final Discount discount;

    /**
     * Creates a new object for update an employee.
     *  @param con
     *            the connection to the database.
     * @param products
     * @param discount
     */
    public createOwnsDiscountFromListProduct(Connection con, List<Product> products, Discount discount) {
        this.con = con;
        this.products = products;
        this.discount = discount;
    }
    /**
     * Creates an Own in the database.
     *
     * @return the {@code Owns} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the employee.
     */

    public List<Owns> createOwnsDiscountFromList() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the create employee
        List<Owns> resultOwns =  null;

        try {

            for(var product : products){
                preparedStatement = con.prepareStatement(STATEMENT);
                preparedStatement.setInt(1, discount.getId());
                preparedStatement.setString(2, product.getAlias());

                resultSet = preparedStatement.executeQuery();

                resultOwns.add(new Owns(
                        resultSet.getInt("ID_Discount"),
                        resultSet.getString("Product_Alias")
                ));

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
        return resultOwns;
    }
}
