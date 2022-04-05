package it.unipd.dei.wa2122.wadteam.dao.owns;

import it.unipd.dei.wa2122.wadteam.resources.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateOwnsDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "INSERT INTO Owns(ID_Discount, Product_Alias) VALUES (?, ?) RETURNING ID_Discount, Product_Alias";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The employee to be updated in the database
     */
    private final Owns own;

    /**
     * Creates a new object for update an employee.
     *
     * @param con
     *            the connection to the database.
     * @param own
     *            the employee to be created in the database.
     */
    public CreateOwnsDatabase(final Connection con, final Owns own) {
        this.con = con;
        this.own = own;
    }

    /**
     * Creates an Own in the database.
     *
     * @return the {@code Owns} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the employee.
     */
    public Owns createOwn() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the create employee
        Owns resultOwn = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, own.getDiscount());
            preparedStatement.setString(2, own.getProduct());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultOwn = new Owns(
                        resultSet.getInt("ID_Discount"),
                        resultSet.getString("Product_Alias")
                );

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
        return resultOwn;
    }

}
