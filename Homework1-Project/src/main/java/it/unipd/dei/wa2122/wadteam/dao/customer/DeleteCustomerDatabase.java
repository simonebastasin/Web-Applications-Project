package it.unipd.dei.wa2122.wadteam.dao.customer;

import it.unipd.dei.wa2122.wadteam.resources.Customer;
import java.sql.Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteCustomerDatabase {

    private static final String STATEMENT = "DELETE FROM Customer WHERE id = ? RETURNING id, name, surname, fiscal_code,address,email,phone_number,username";

    private final Connection con;

    private final int id;

    public DeleteCustomerDatabase(final Connection con, final int id) {
        this.con = con;
        this.id = id;
    }

    public Customer deleteCustomer() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the deleted employee
        Customer resultCustomer = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1,id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultCustomer = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("fiscal_code"),
                        resultSet.getString("address"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("username"),
                       null
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

        return resultCustomer;
    }
}
