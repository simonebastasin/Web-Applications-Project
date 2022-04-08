package it.unipd.dei.wa2122.wadteam.dao.media;

import it.unipd.dei.wa2122.wadteam.resources.Media;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetMediaDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT id, filename, mimetype FROM media WHERE id = ?";

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
    public GetMediaDatabase(final Connection con, final int id) {
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
    public Media getMedia() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the read employee
        Media resultMedia = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultMedia = new Media(resultSet.getInt("id"),
                                        resultSet.getString("filename"),
                                        resultSet.getString("mimetype")
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

        return resultMedia;
    }

}
