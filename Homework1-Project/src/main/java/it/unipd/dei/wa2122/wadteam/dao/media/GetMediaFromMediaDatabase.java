package it.unipd.dei.wa2122.wadteam.dao.media;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetMediaFromMediaDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT media FROM media WHERE id = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The id of the media
     */
    private final int id;

    /**
     * Creates a new object for reading an employee.
     *
     * @param con
     *            the connection to the database.
     * @param id
     *            the username of the employee.
     */
    public GetMediaFromMediaDatabase(final Connection con, final int id) {
        this.con = con;
        this.id = id;
    }

    /**
     * Reads an media from the database.
     *
     * @return the {@code Employee} object matching the id.
     *
     * @throws SQLException
     *             if any error occurs while reading the employee.
     */
    public byte[] getMedia() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        byte[] resultMedia = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultMedia = resultSet.getBytes(1);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return resultMedia;
    }
}
