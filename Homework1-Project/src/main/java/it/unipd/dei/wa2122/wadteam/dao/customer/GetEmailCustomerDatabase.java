package it.unipd.dei.wa2122.wadteam.dao.customer;

import java.sql.Connection;
import java.sql.Connection;
import it.unipd.dei.wa2122.wadteam.resources.Customer;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class GetEmailCustomerDatabase {

    private static final String STATEMENT = "SELECT id,name,surname,fiscal_code,address,email,phone_number,username" +
            " FROM Customer WHERE email = ?";

    private final Connection con;
    private final String email;

    public GetEmailCustomerDatabase(final Connection con, final String email) {
        this.con = con;
        this.email=email;
    }
    public Customer getEmailCustomer() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        Customer resultCustomer = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1,email);


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



