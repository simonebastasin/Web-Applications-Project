package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public enum GenericError implements Resource{
    PAGE_NOT_FOUND("page not found", "/jsp/pagenotfound.jsp", 404),
    SERVER_ERROR("server error", "/jsp/servererror.jsp", 500),
    UNAUTHORIZED("unauthorized", "/jsp/unauthorized.jsp", 401);

    private final int httpErrorCode;
    private final String jsp;
    private final String name;

    GenericError(String name, String jsp, int httpErrorCode) {
        this.httpErrorCode = httpErrorCode;
        this.jsp = jsp;
        this.name = name;
    }

    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    public String getJsp() {
        return jsp;
    }

    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorName", name);
        return jsonObject;
    }
}