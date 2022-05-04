package it.unipd.dei.wa2122.wadteam.dao.discount;

import it.unipd.dei.wa2122.wadteam.resources.DateTime;
import it.unipd.dei.wa2122.wadteam.resources.Discount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public final class GetDiscountDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT ID, Percentage, Start_Date, End_Date FROM Discount WHERE ID = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The badge of the discount
     */
    private final int id;

    /**
     * Creates a new object for reading a discount.
     *
     * @param con
     *            the connection to the database.
     * @param id
     *            the username of the discount.
     */
    public GetDiscountDatabase(final Connection con, final int id) {
        this.con = con;
        this.id = id;
    }

    /**
     * Reads an discount from the database.
     *
     * @return the {@code Discount} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the discount.
     */
    public Discount getDiscount() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the read discount
        Discount resultDiscount = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultDiscount = new Discount(resultSet.getInt("id"),
                        resultSet.getInt("Percentage"),
                        new DateTime(resultSet.getObject("Start_Date", LocalDateTime.class)),
                        new DateTime(resultSet.getObject("End_Date", LocalDateTime.class)));

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
