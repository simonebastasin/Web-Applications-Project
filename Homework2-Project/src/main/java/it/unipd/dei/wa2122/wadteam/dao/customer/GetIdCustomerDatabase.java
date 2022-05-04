package it.unipd.dei.wa2122.wadteam.dao.customer;

import java.sql.Connection;
import java.sql.Connection;
import it.unipd.dei.wa2122.wadteam.resources.Customer;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class GetIdCustomerDatabase {

    private static final String STATEMENT = "SELECT id,name,surname,fiscal_code,address,email,phone_number,username" +
            " FROM Customer WHERE username = ?";

    private final Connection con;
    private final String username;

    public GetIdCustomerDatabase(final Connection con, final String username) {
        this.con = con;
        this.username=username;
    }
    public Customer getIdCustomer() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        Customer resultCustomer = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1,username);


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
        con.close();


        return resultCustomer;
    }
}
