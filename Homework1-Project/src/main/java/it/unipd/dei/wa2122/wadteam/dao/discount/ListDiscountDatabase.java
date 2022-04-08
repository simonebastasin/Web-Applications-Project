package it.unipd.dei.wa2122.wadteam.dao.discount;

import it.unipd.dei.wa2122.wadteam.resources.DateTime;
import it.unipd.dei.wa2122.wadteam.resources.Discount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class ListDiscountDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT ID, Percentage, Start_Date, End_Date FROM Discount";

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
    public ListDiscountDatabase(final Connection con) {
        this.con = con;
    }

    /**
     * Reads discounts from the database.
     *
     * @return the {@code Discount} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the discount.
     */


    public List<Discount> getDiscounts() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the read discount
        Discount resultDiscountItem = null;

        List<Discount> resultDiscount = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);

            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {

                resultDiscountItem = new Discount(resultSet.getInt("id"),
                        resultSet.getInt("Percentage"),
                        new DateTime(resultSet.getObject("Start_Date", LocalDateTime.class)),
                        new DateTime(resultSet.getObject("End_Date", LocalDateTime.class)));
                resultDiscount.add(resultDiscountItem);
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

        return resultDiscount;
    }
}
