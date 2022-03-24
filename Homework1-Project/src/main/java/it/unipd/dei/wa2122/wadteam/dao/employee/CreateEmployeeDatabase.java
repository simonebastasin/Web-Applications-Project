package it.unipd.dei.wa2122.wadteam.dao.employee;

import it.unipd.dei.wa2122.wadteam.resources.Employee;
import it.unipd.dei.wa2122.wadteam.resources.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateEmployeeDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "INSERT INTO Employee (username, name, surname, role_name, password) VALUES (?, ?, ?, ?, sha384(?)) RETURNING username, name, surname, role_name";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The employee to be updated in the database
     */
    private final Employee employee;

    /**
     * Creates a new object for updat an employee.
     *
     * @param con
     *            the connection to the database.
     * @param employee
     *            the employee to be created in the database.
     */
    public CreateEmployeeDatabase(final Connection con, final Employee employee) {
        this.con = con;
        this.employee = employee;
    }

    /**
     * Creates an employee in the database.
     *
     * @return the {@code Employee} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the employee.
     */
    public Employee createEmployee() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the create employee
        Employee resultEmployee = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1, employee.getUsername());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getSurname());
            preparedStatement.setString(4, employee.getRole().getName());
            preparedStatement.setString(5, employee.getPassword());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultEmployee = new Employee(resultSet.getString("username"),
                                              resultSet.getString("name"),
                                              resultSet.getString("surname"),
                                              new Role(resultSet.getString("role"))
                                             );
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            con.close();
        }

        return resultEmployee;
    }
}

