package it.unipd.dei.wa2122.wadteam.dao.media;

import it.unipd.dei.wa2122.wadteam.resources.Media;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListMediaDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT id, filename, mimetype FROM media";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Creates a new object for reading a media.
     *
     * @param con
     *            the connection to the database.
     */
    public ListMediaDatabase(final Connection con) {
        this.con = con;
    }

    /**
     * Reads a media from the database.
     *
     * @return the {@code Media} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the media.
     */
    public List<Media> getMedia() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the current media
        Media resultMediaItem = null;

        List<Media> resultMedia = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                resultMediaItem = new Media(resultSet.getInt("id"),
                        resultSet.getString("filename"),
                        resultSet.getString("mimetype"));
                resultMedia.add(resultMediaItem);
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
