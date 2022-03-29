package it.unipd.dei.wa2122.wadteam.dao.customer;

import java.sql.Connection;
import it.unipd.dei.wa2122.wadteam.resources.Customer;
import it.unipd.dei.wa2122.wadteam.resources.Employee;
import it.unipd.dei.wa2122.wadteam.resources.Role;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class CreateCustomerDatabase {

    private static final String STATEMENT = "INSERT INTO Customer (id,name,surname,fiscal_code,address,email,phone_number,username,password)" +
            " VALUES (?,?, ?, ?, ?,?,?,?, sha384(?::bitea))" +
            " RETURNING id,name,surname,fiscal_code,address,email,phone_number,username";

    private final Connection con;
    private final Customer customer;

    public CreateCustomerDatabase(final Connection con, final Customer customer) {
        this.con = con;
        this.customer=customer;
    }

    public Customer createCustomer() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the create employee
       Customer resultCustomer = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getSurname());
            preparedStatement.setString(4, customer.getFiscal_code());
            preparedStatement.setString(5, customer.getAddress());
            preparedStatement.setString(6, customer.getEmail());
            preparedStatement.setString(7, customer.getPhone_number());
            preparedStatement.setString(8, customer.getUsername());
            preparedStatement.setString(9, customer.getPassword());

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
