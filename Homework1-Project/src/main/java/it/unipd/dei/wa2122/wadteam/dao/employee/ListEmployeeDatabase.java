package it.unipd.dei.wa2122.wadteam.dao.employee;

import it.unipd.dei.wa2122.wadteam.resources.Employee;
import it.unipd.dei.wa2122.wadteam.resources.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListEmployeeDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT username, name, surname, role_name FROM Employee ORDER BY username";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Creates a new object for reading an employee.
     *
     * @param con
     *            the connection to the database.
     */
    public ListEmployeeDatabase(final Connection con) {
        this.con = con;
    }

    /**
     * Reads an employee from the database.
     *
     * @return the {@code Employee} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the employee.
     */
    public List<Employee> getEmployee() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the current employee
        Employee resultEmployeeItem = null;

        List<Employee> resultEmployee = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                resultEmployeeItem = new Employee(resultSet.getString("username"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        new Role(resultSet.getString("role_name"))
                );
                resultEmployee.add(resultEmployeeItem);
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