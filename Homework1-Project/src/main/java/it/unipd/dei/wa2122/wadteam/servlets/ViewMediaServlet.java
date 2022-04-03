package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.media.GetMediaByteFromMediaDatabase;
import it.unipd.dei.wa2122.wadteam.dao.media.GetMediaDatabase;
import it.unipd.dei.wa2122.wadteam.dao.media.ListMediaDatabase;
import it.unipd.dei.wa2122.wadteam.resources.ErrorMessage;
import it.unipd.dei.wa2122.wadteam.resources.Media;
import it.unipd.dei.wa2122.wadteam.resources.Message;
import it.unipd.dei.wa2122.wadteam.resources.Resource;
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

                writeResource(request,response, "/jsp/media.jsp", false, mediaList.toArray(Resource[]::new));
            } catch (SQLException e) {
                writeError(request, response, new ErrorMessage.SqlInternalError(e.getMessage()));
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
                        writeError(request,response, new ErrorMessage.EmptyMediaError("The media is empty"));
                    }

                } else {
                    writeError(request, response, new ErrorMessage.MediaNotFoundError("The media wasn't found"));
                }

            } catch (SQLException e) {
                writeError(request, response, new ErrorMessage.SqlInternalError(e.getMessage()));
            } catch (NumberFormatException e) {
                writeError(request, response, new ErrorMessage.IncorrectlyFormattedPathError("The media ID is not in the correct format"));
            }
        }
    }

}