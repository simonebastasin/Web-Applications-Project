package it.unipd.dei.wa2122.wadteam.dao.media;

import it.unipd.dei.wa2122.wadteam.resources.Media;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateMediaDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT_WITH_THUMB = "INSERT INTO media (mimetype, filename, media, thumb) VALUES (?, ?, ?, ?) RETURNING id, mimetype, filename";
    private static final String STATEMENT_WITHOUT_THUMB = "INSERT INTO media (mimetype, filename, media) VALUES (?, ?, ?) RETURNING id, mimetype, filename";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The item of the employee
     */
    private final String filename;

    private final String mimetype;

    private final byte[] media;

    private final byte[] thumb;

    /**
     * Update  a  employee item.
     *
     * @param con
     *            the connection to the database.
     * @param media
     *            the media to be create.
     */
    public CreateMediaDatabase(final Connection con, final String filename, final String mimetype, final byte[] media, final byte[] thumb) {
        this.con = con;
        this.filename = filename;
        this.mimetype = mimetype;
        this.media = media;
        this.thumb = thumb;
    }

    public Media createMedia() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the read media
        Media resultMedia = null;
        try {
            boolean hasThumb = thumb != null && thumb.length > 0;

            preparedStatement = con.prepareStatement(hasThumb ? STATEMENT_WITH_THUMB : STATEMENT_WITHOUT_THUMB);

            preparedStatement.setString(1, mimetype);
            preparedStatement.setString(2, filename);
            preparedStatement.setBytes(3, media);
            if(hasThumb) preparedStatement.setBytes(4, thumb);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultMedia = new Media(resultSet.getInt("id"),
                        resultSet.getString("mimetype"),
                        resultSet.getString("filename")
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

        return resultMedia;

    }
}
