package it.unipd.dei.wa2122.wadteam.dao.employee;

import it.unipd.dei.wa2122.wadteam.resources.Employee;
import it.unipd.dei.wa2122.wadteam.resources.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class GetEmployeeDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT username, name, surname, role_name FROM Employee WHERE username = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The badge of the employee
     */
    private final String username;

    /**
     * Creates a new object for reading an employee.
     *
     * @param con
     *            the connection to the database.
     * @param username
     *            the username of the employee.
     */
    public GetEmployeeDatabase(final Connection con, final String username) {
        this.con = con;
        this.username = username;
    }

    /**
     * Reads an employee from the database.
     *
     * @return the {@code Employee} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the employee.
     */
    public Employee getEmployee() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the read employee
        Employee resultEmployee = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultEmployee = new Employee(resultSet.getString("username"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        new Role(resultSet.getString("role_name"))
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

        return resultEmployee;
    }
}
