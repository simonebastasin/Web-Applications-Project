package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.resources.ErrorMessage;
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
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public abstract class AbstractServlet extends HttpServlet {
    /**
     * The JSON UTF-8 MIME media type
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";


    /**
     * Please migrate to writeError with ErrorMessage and not with Message
     */
    @Deprecated
    public void writeError(HttpServletRequest request, HttpServletResponse response, Message message, int errorCode) throws IOException, ServletException {
        response.setStatus(errorCode);
        if(request.getHeader("Accept").contains("application/json")) {
            writeJSON(response, message.toJSON());
        } else {
            request.setAttribute("errorCode", errorCode);
            request.setAttribute("message", message);
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);

        }

    }

    /**
     * write ErrorMessage, the operation is similar to writeresource
     */
    public void writeError(HttpServletRequest request, HttpServletResponse response, ErrorMessage message) throws IOException, ServletException {
        response.setStatus(message.getHttpErrorCode());
        if(request.getHeader("Accept").contains("application/json")) {
            writeJSON(response, message.toJSON());
        } else {
            request.setAttribute("errorCode", message.getErrorCode());
            request.setAttribute("message", message);
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);

        }

    }

    /**
     * write a json object to output
     */
    public void writeJSON(HttpServletResponse response, JSONObject jsonObject) throws IOException {
        response.setContentType(JSON_UTF_8_MEDIA_TYPE);

        response.getWriter().write(jsonObject.toString(2));
    }

    /**
     * write a json array to output
     */
    public void writeJSON(HttpServletResponse response, JSONArray jsonArray) throws IOException {
        response.setContentType(JSON_UTF_8_MEDIA_TYPE);

        response.getWriter().write(jsonArray.toString(2));
    }

    /**
     * Please add showOneItemAsArray parameter to the call function!
     * if the value of showOneItemAsArray is false in the case of a
     * single element of a certain type it is passed as object, if it is true in the
     * case of a single element it is anyway encapsulated in a list
     */
    @Deprecated
    public void writeResource(HttpServletRequest request, HttpServletResponse response, String jsp, Resource... resources) throws IOException, ServletException {
        writeResource(request,response, jsp, false, resources);
    }

    /**
     * a version of writeresource that does not print parameters in ouput or even json, only jsp
     * even if the header accept is application/json
     *
     * use only in the service pages not useful in the rest api. in case you need to pass parameters to the jsp pass
     * them before calling this function
     *
     * the session info is passed anyway
     */
    public void writeJsp(HttpServletRequest request, HttpServletResponse response, String jsp) throws IOException, ServletException {
        if(request.getSession(false) != null)
            request.setAttribute("user", request.getSession(false).getAttribute("user"));

        request.getRequestDispatcher(jsp).forward(request, response);
    }

    /**
     * Pass true if the designated value if you need a single objet, pass false if you need a list
     *
     *
     * writes the output resources in html or json according to the header accept: if there is application/json
     * it returns a json, in all other cases it delegates the creation of the page to the jsp
     *
     * pass the jsp relative url in the parameter <code>jsp</code>, with a starter slash, like <code>/jsp/index.jsp</code>
     *
     * pass all the resources to be passed through the varargs <code>resources</code> at the end of the function,
     * in the case of several resources of different types merge them into an array before sending them.
     * in the case of lists use the  method <code>.toArray(Resource[]::new)</code>
     * {@link java.util.List#toArray(Object[])} to pass the resources
     * (modify <code>Resource[]</code> with the actual type)
     *
     * the variable names in the jsp are automatically generated according to the last derivation of the
     * {@link Resource} class:
     * If it is a list of  {@link it.unipd.dei.wa2122.wadteam.resources.Product} it is called
     * <code>productList</code>, with the initial lowercase {@link AbstractServlet#decapitalize(String)}
     * and with the fixed post <code>List</code>.
     *
     * If it is a single item and the boolean value <code>showOneItemAsItem</code> is set to <code>false</code> it is
     * always added to a list, if it is set to <code>true</false> it is exposed as a single item named like the
     * lowercase class,  like <code>product</code>
     *
     * the class {@link it.unipd.dei.wa2122.wadteam.resources.UserCredential} was automatically send to jsp if
     * it is present in the session and is called <code>user</code> TODO
     *
     * @param request the HttpServletRequest istance
     * @param response the HttpServletResponse istance
     * @param jsp the relative url of the jsp
     * @param showOneItemAsItem a boolean value telling what to do in case of a single item of a class:
     *                          whether to put it in a list of a single item <code>false</code> or not <code>true</code>
     *                          the resource name will always be with the postfix in the jsp name <code>List</code>
     *                          in the case of <code>false</code>, otherwise it can be either with the postfix in the
     *                          case of several items or without in the case of a single item
     * @param resources the resoruce varargs. Accept 0, 1, 2, ..., infty item of resource or a array.
     *                  if you need to send a list use use the  method <code>.toArray(Resource[]::new)</code>
     *                  {@link java.util.List#toArray(Object[])} to pass the resources. If you have more than one array
     *                  merge the array before sending.
     */
    public void writeResource(HttpServletRequest request, HttpServletResponse response, String jsp, boolean showOneItemAsItem, Resource... resources) throws IOException, ServletException {
        var resourcesMap = Arrays.stream(resources).collect(groupingBy(Resource::getClass));

        if(request.getHeader("Accept").contains("application/json")) {
            response.setContentType(JSON_UTF_8_MEDIA_TYPE);

            JSONObject jsonObject = new JSONObject();

            if(resourcesMap.entrySet().size() == 1) {
                writeJSON(response, new JSONArray(Arrays.stream(resources).map(Resource::toJSON).toArray()));
            } else {
                for (var item : resourcesMap.entrySet()) {
                    if (showOneItemAsItem && item.getValue().size() == 1) {
                        jsonObject.put(decapitalize(item.getKey().getSimpleName()), item.getValue().get(0).toJSON());
                    } else {
                        jsonObject.put(decapitalize(item.getKey().getSimpleName()) + "List", new JSONArray(item.getValue().stream().map(Resource::toJSON).toArray()));
                    }
                }
                writeJSON(response, jsonObject);
            }
        } else {
            for (var item : resourcesMap.entrySet()) {
                if (showOneItemAsItem && item.getValue().size() == 1) {
                    request.setAttribute(decapitalize(item.getKey().getSimpleName()), item.getValue().get(0));
                } else {
                    request.setAttribute(decapitalize(item.getKey().getSimpleName()) + "List", item.getValue());
                }
            }
            if(request.getSession(false) != null)
                request.setAttribute("user", request.getSession(false).getAttribute("user"));
            request.getRequestDispatcher(jsp).forward(request, response);
        }
    }

    /**
     * write a blob (binary large object) to the output in servlet
     * @param response the HttpServletResponse instance
     * @param blob the blob to send to output
     * @param mimetype the mimetype
     * @param filename the filename that uses for helper the browser
     * @param inline true if the Content-disposition are inline, false otherwise.
     */
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

    /**
     * read json post data
     */
    public JSONObject readJSON(HttpServletRequest request) throws IOException {
        String requestData = request.getReader().lines().collect(Collectors.joining());
        return new JSONObject(requestData);
    }

    /**
     * this function simplifies and unifies the management of the reading of input parameters on post
     *
      *depending on the header conten-type of the body of the post reads the data and saves it in a json object which
     * can be used as a key-value array or as an object to  send to the readjson functions of the various resources.
     * @param request the HttpServletRequest istance
     * @return a JSONObject in key-value notation that ca be use as key-value array or to  send to the readjson
     * functions of the various resources
     */
    public JSONObject readInputParameters(HttpServletRequest request) throws IOException, ServletException {
        String header = request.getHeader("Content-Type");
        if(header.contains("application/json")) {
            return readJSON(request);
        } else if(header.contains("application/x-www-form-urlencoded")){
            JSONObject jsonObject = new JSONObject();
            request.getParameterMap().forEach((key, value) -> jsonObject.put(key, (value != null && value.length == 1) ? value[0] : value));
            return jsonObject;
        }
        return  null;
    }

    /**
     * a high speed funciont that convert the first letter of a string to upper case to lower case, used for send
     * a java-like name of value to jsp
     * @param string the string to be decapitalize
     * @return a string with first lettere in lower case
     */
    private static String decapitalize(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }

        char[] c = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);

        return new String(c);
    }
}
