package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.media.GetMediaByteFromMediaDatabase;
import it.unipd.dei.wa2122.wadteam.dao.media.GetMediaDatabase;
import it.unipd.dei.wa2122.wadteam.dao.media.ListMediaDatabase;
import it.unipd.dei.wa2122.wadteam.resources.Media;
import it.unipd.dei.wa2122.wadteam.resources.Message;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.json.JSONArray;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ViewMediaServlet extends AbstractDatabaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo().substring(1);
        Connection connection = null;

        if (path.equals("list")) {
            try {
                connection = getDataSource().getConnection();

                List<Media> mediaList = new ListMediaDatabase(connection).getMedia();

                writeJson(response,  new JSONArray(mediaList.stream().map(Media::toJSON).toArray()));

            } catch (SQLException e) {
                Message m = new Message("Error media list", "EV04", e.getMessage());

                writeError(response, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            boolean thumb = false;

            if (path.startsWith("thumb")) {
                path = request.getPathInfo().substring(7);
                thumb = true;
            }


            try {
                int id = Integer.parseInt(path);

                Media media = new GetMediaDatabase(getDataSource().getConnection(), id).getMedia();
                if(media != null) {
                    byte[] blob = new GetMediaByteFromMediaDatabase(getDataSource().getConnection(), id, thumb).getMedia();

                    if (blob != null && blob.length > 0) {
                        writeBlob(response, blob, media.getMimetype(), thumb ? media.getFilename() + "_thumb.png" : media.getFilename(), true);
                    } else {
                        Message m = new Message("Media no content", "EV01", "Media not found");

                        writeError(response, m, HttpServletResponse.SC_NO_CONTENT);
                    }

                } else {
                    Message m = new Message("Media not found", "EV01", "Media not found");

                    writeError(response, m, HttpServletResponse.SC_NOT_FOUND);
                }

            } catch (SQLException e) {
                Message m = new Message("Media error", "EV02", "There was a problem with find media: " + e.getMessage());

                writeError(response, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (NumberFormatException e) {
                Message m = new Message("Error media id", "EV03", "The media ID is not in the correct format");

                writeError(response, m, HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

}