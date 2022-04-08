package it.unipd.dei.wa2122.wadteam.dao.customer;

import it.unipd.dei.wa2122.wadteam.resources.Customer;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateCustomerDatabase {

    private static final String STATEMENT = "UPDATE Customer " +
            "SET name = ?, surname = ?, fiscal_code = ?," +
            "address=?, email=?, phone_number=?," +
            "username=?" +
            " WHERE username = ?";
    private final Connection con;

    private final Customer customer;


    public UpdateCustomerDatabase(final Connection con, final Customer customer) {
        this.con = con;
        this.customer=customer;
    }
    public Customer updateCustomer() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the read employee
        Customer resultCustomer = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setString(3, customer.getFiscalCode());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.setString(5, customer.getEmail());
            preparedStatement.setString(6, customer.getPhoneNumber());
            preparedStatement.setString(7, customer.getUsername());
            preparedStatement.setString(8,customer.getUsername());


            int i = preparedStatement.executeUpdate();

           /* if (resultSet.next()) {
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
            }*/
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
