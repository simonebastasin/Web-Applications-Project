package it.unipd.dei.wa2122.wadteam.dao.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdatePasswordEmployeeDatabase {

    private static final String STATEMENT = "UPDATE Employee " +
            "SET password=sha384(?::bytea) "+
            "WHERE username = ? and password=sha384(?::bytea)";

    private final Connection con;
    private final String oldPassword;
    private final String newPassword;
    private final String username;
    public UpdatePasswordEmployeeDatabase(final Connection con,final String oldPassword, final String newPassword,final String username)
    {
        this.con=con;
        this.oldPassword=oldPassword;
        this.newPassword=newPassword;
        this.username=username;
    }
    public int updatePassword() throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        int result;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1,newPassword);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3,oldPassword);



            result = preparedStatement.executeUpdate();


        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return result;

    }

}
