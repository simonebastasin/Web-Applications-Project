package it.unipd.dei.wa2122.wadteam.dao.media;

import it.unipd.dei.wa2122.wadteam.resources.Media;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetMediaByteFromMediaDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT_MEDIA = "SELECT media FROM media WHERE id = ?";

    private static final String STATEMENT_THUMB = "SELECT thumb FROM media WHERE id = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The id of the media
     */
    private final int id;

    private final boolean thumb;

    /**
     * Creates a new object for reading an employee.
     *  @param con
     *            the connection to the database.
     * @param id
     * @param thumb
     */
    public GetMediaByteFromMediaDatabase(final Connection con, final int id, boolean thumb) {
        this.con = con;
        this.id = id;
        this.thumb = thumb;
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


        byte[] resultByte = null;

        try {
            preparedStatement = con.prepareStatement(thumb ? STATEMENT_THUMB : STATEMENT_MEDIA);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultByte = resultSet.getBytes(1);
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

        return resultByte;
    }
}
