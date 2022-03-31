package it.unipd.dei.wa2122.wadteam.dao.login;

import it.unipd.dei.wa2122.wadteam.resources.UserCredentials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static it.unipd.dei.wa2122.wadteam.resources.UserCredentials.*;

public class CheckUserCredential {

    /**
     * The SQL statement to be executed
     */
    private final String STATEMENT_EMPLOYEE = "Select * from employee where (username = ? AND password = sha384(?::bytea))";
    private final String STATEMENT_CUSTOMER = "Select * from customer where (username = ? AND password = sha384(?::bytea)) OR (email = ? and password = sha384(?::bytea))";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The object of the UserCredential
     */
    private final UserCredentials userCredentials;

    /**
     * Creates a new object for reading user credential.
     *
     * @param con
     *            the connection to the database.
     * @param userCredentials
     *            the user credentials that have to be checked.
     */
    public CheckUserCredential(final Connection con, UserCredentials userCredentials) {
        this.con = con;
        this.userCredentials = userCredentials;
    }



    /**
     * Reads an media from the database.
     *
     * @return the {@code Employee} object matching the id.
     *
     * @throws SQLException
     *             if any error occurs while reading the employee.
     */
    public UserCredentials getUserCredentials() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the read employee
        UserCredentials resultUserCredentials = null;

        try {
            if(userCredentials.getType()== TypeUser.EMPLOYEE){
                preparedStatement = con.prepareStatement(STATEMENT_EMPLOYEE);
                preparedStatement.setString(1, userCredentials.getIdentification());
                preparedStatement.setString(2, userCredentials.getPassword());

            }
            else{
                preparedStatement = con.prepareStatement(STATEMENT_CUSTOMER);
                preparedStatement.setString(1, userCredentials.getIdentification());
                preparedStatement.setString(2, userCredentials.getPassword());
                preparedStatement.setString(3, userCredentials.getIdentification());
                preparedStatement.setString(4, userCredentials.getPassword());
            }

            String debug = preparedStatement.toString();

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if(userCredentials.getType()== TypeUser.EMPLOYEE) {
                    resultUserCredentials = new UserCredentials(resultSet.getString("username"), null,
                        TypeUser.EMPLOYEE,resultSet.getString("role_name"));
                }
                else if (userCredentials.getType()== TypeUser.CUSTOMER) {
                    resultUserCredentials = new UserCredentials(resultSet.getString("username"), null,
                            TypeUser.CUSTOMER, null);
                }
            }
            else{
                resultUserCredentials = new UserCredentials(null, null,
                        null,null);

            }

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return resultUserCredentials;
    }
}
