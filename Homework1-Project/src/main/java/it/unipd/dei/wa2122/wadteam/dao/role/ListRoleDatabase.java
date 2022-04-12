package it.unipd.dei.wa2122.wadteam.dao.role;

import it.unipd.dei.wa2122.wadteam.resources.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListRoleDatabase {

    /**
     * The SQL statements to be executed
     */
    private static final String STATEMENT = "SELECT name, description FROM role";

    /**
     * connection to the database
     */
    private final Connection con;

    /**
     * creates an object to read all roles already in the database
     *
     * @param con   connection to the database
     */
    public ListRoleDatabase(final Connection con) {
        this.con = con;
    }

    /**
     * Lists all the productCategories in the database
     *
     * @return the {@code List<Role>} object read from the database
     * @throws SQLException if any error occurs while listing roles
     */
    public List<Role> getRole() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Role resultRoleItem = null;
        List<Role> resultRoleList = new ArrayList<>();

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resultRoleItem = new Role(
                        resultSet.getString("name"),
                        resultSet.getString("description"));
                resultRoleList.add(resultRoleItem);
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

        return resultRoleList;
    }
}