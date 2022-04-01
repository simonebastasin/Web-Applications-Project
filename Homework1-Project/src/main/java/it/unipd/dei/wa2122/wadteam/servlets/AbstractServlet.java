package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.resources.Message;
import it.unipd.dei.wa2122.wadteam.resources.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import static java.util.stream.Collectors.groupingBy;

public abstract class AbstractServlet extends HttpServlet {
    /**
     * The JSON UTF-8 MIME media type
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";



    public void writeError(HttpServletRequest request, HttpServletResponse response, Message message, int errorCode) throws IOException, ServletException {
        if(request.getHeader("Accept").contains("application/json")) {
            response.setStatus(errorCode);
            writeJson(response, message.toJSON());
        } else {
            request.setAttribute("errorCode", errorCode);
            request.setAttribute("message", message);
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);

        }

    }

    public void writeJson(HttpServletResponse response, JSONObject jsonObject) throws IOException {
        response.setContentType(JSON_UTF_8_MEDIA_TYPE);

        response.getWriter().write(jsonObject.toString(2));
    }

    public void writeJson(HttpServletResponse response, JSONArray jsonArray) throws IOException {
        response.setContentType(JSON_UTF_8_MEDIA_TYPE);

        response.getWriter().write(jsonArray.toString(2));
    }

    public void writeResource(HttpServletRequest request, HttpServletResponse response, String jsp, Resource... resources) throws IOException, ServletException {
        var resourcesMap = Arrays.stream(resources).collect(groupingBy(Resource::getClass));

        if(request.getHeader("Accept").contains("application/json")) {
            response.setContentType(JSON_UTF_8_MEDIA_TYPE);

            JSONObject jsonObject = new JSONObject();

            if(resourcesMap.entrySet().size() == 1) {
                writeJson(response, new JSONArray(Arrays.stream(resources).map(Resource::toJSON).toArray()));
            } else {
                for (var item : resourcesMap.entrySet()) {
                    if (item.getValue().size() == 1) {
                        jsonObject.put(decapitalize(item.getKey().getSimpleName()), item.getValue().get(0).toJSON());
                    } else {
                        jsonObject.put(decapitalize(item.getKey().getSimpleName()) + "List", new JSONArray(item.getValue().stream().map(Resource::toJSON).toArray()));
                    }
                }
                writeJson(response, jsonObject);
            }
        } else {
            for (var item : resourcesMap.entrySet()) {
                if (item.getValue().size() == 1) {
                    request.setAttribute(decapitalize(item.getKey().getSimpleName()), item.getValue().get(0));
                } else {
                    request.setAttribute(decapitalize(item.getKey().getSimpleName()) + "List", item.getValue());
                }
            }
            request.getRequestDispatcher(jsp).forward(request, response);
        }
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

    public static String decapitalize(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }

        char[] c = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);

        return new String(c);
    }
}
