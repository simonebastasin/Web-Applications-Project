package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.resources.Message;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractServlet extends HttpServlet {
    /**
     * The JSON UTF-8 MIME media type
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";



    public void writeError(HttpServletResponse response, Message message, int errorCode) throws IOException {

        response.setContentType(JSON_UTF_8_MEDIA_TYPE);

        response.setStatus(errorCode);
        response.getWriter().write(message.toJSON().toString(2));
    }

    public void writeJson(HttpServletResponse response, JSONObject jsonObject) throws IOException {
        response.setContentType(JSON_UTF_8_MEDIA_TYPE);

        response.getWriter().write(jsonObject.toString(2));
    }

    public void writeJson(HttpServletResponse response, JSONArray jsonArray) throws IOException {
        response.setContentType(JSON_UTF_8_MEDIA_TYPE);

        response.getWriter().write(jsonArray.toString(2));
    }

    public void writeBlob(HttpServletResponse response, byte[] blob, String mimetype, String filename, boolean inline) throws IOException {
        response.setContentType(mimetype);
        if(inline)
            response.setHeader("Content-disposition", "inline; filename=\"" + filename + "\"");
        else
            response.setHeader("Content-disposition", "attachment; filename=\"" + filename + "\"");

        OutputStream out = response.getOutputStream();
        out.write(blob);
        out.flush();
        out.close();

    }
}
