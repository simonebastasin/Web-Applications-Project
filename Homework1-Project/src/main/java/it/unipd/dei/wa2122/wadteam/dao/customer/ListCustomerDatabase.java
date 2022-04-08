package it.unipd.dei.wa2122.wadteam.dao.customer;

import it.unipd.dei.wa2122.wadteam.resources.Customer;
import it.unipd.dei.wa2122.wadteam.resources.Employee;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListCustomerDatabase {

    private static final String STATEMENT = "SELECT id,name,surname,fiscal_code,address,email,phone_number,username" +
            " FROM Customer";

    private final Connection con;

    public ListCustomerDatabase(final Connection con) {
        this.con = con;
    }

    public List<Customer> getCustomer() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Customer resultCustomerItem = null;
        List<Customer> resultCustomer = new ArrayList<>();


        try {
            preparedStatement = con.prepareStatement(STATEMENT);



            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultCustomerItem = new Customer(
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
                resultCustomer.add(resultCustomerItem);
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

        return resultCustomer;
    }
}
