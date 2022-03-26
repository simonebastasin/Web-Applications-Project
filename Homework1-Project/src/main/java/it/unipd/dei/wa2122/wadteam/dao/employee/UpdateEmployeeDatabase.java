package it.unipd.dei.wa2122.wadteam.dao.employee;

import it.unipd.dei.wa2122.wadteam.resources.Employee;
import it.unipd.dei.wa2122.wadteam.resources.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateEmployeeDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "UPDATE Employee SET name = ?, surname = ?, role_name = ? WHERE username = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The item of the employee
     */
    private final Employee employee;

    /**
     * Update  a  employee item.
     *
     * @param con
     *            the connection to the database.
     * @param employee
     *            the employee to be update.
     */
    public UpdateEmployeeDatabase(final Connection con, final Employee employee) {
        this.con = con;
        this.employee = employee;
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
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSurname());
            preparedStatement.setString(3, employee.getRole().getName());
            preparedStatement.setString(4, employee.getUsername());

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
        }

        return resultEmployee;
    }
}
